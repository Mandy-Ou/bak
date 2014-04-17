package com.cmw.core.kit.excel.exporter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

import com.cmw.core.kit.excel.exporter.XlsTempCfgModel.GatherInfo;
import com.cmw.core.kit.excel.exporter.XlsTempCfgModel.ParamValue;
import com.cmw.core.kit.excel.exporter.XlsTempCfgModel.Renderer;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.StringHandler;
/**
 * Excel 导出 ---- 多条数据模板导出
 * @author chengmingwei
 *
 */
public class POIListTempExporter implements XlsExporter {
	//最后的总行数
	private int lastRownum = 0;
	
	private OutputStream os;
	private DataTable dataSource;
	private XlsTempCfgModel cfg;
	
	//导出的工作薄
	protected HSSFWorkbook exportWb = null;
	//工作薄 Sheet 对象
	private HSSFSheet sheet = null;
	
	private PoiExcelHandler handler = new PoiExcelHandler();
	
	@Override
	public void exporter(OutputStream os,DataTable dataSource,XlsTempCfgModel cfg) throws IOException{
		this.os = os;
		this.dataSource = dataSource;
		this.cfg = cfg;
		executeExport();
	}

	/**
	 * 执行导出数据的加载
	 */
	private void executeExport() throws IOException{
		//获取Excel工作薄对象
		createExportWb();
		//将模板中的参数替换成相应的参数值
		replaceParams();
		//往Excel中写入数据
		writeDataToXls();
		//清除 Excel 参数配置信息
		clearXlsCfgInfo();
		//写入到输出流当中
		exportWb.write(os);
	}
	
	/**
	 * 如果提供了参数值则
	 * 将模板中的参数替换成相应的参数值
	 */
	private void replaceParams(){
		List<ParamValue> paramValues = cfg.getParamValues();
		if(null == paramValues || paramValues.size() == 0) return;
		for(ParamValue paramValue : paramValues){
			int rowOffset = paramValue.getRowOffset();
			if(rowOffset>0)  rowOffset -= 1;
			int cellOffset = paramValue.getCellOffset();
			if(cellOffset>0)  cellOffset -= 1;
			String value = paramValue.getValue();
			HSSFRow row = sheet.getRow(rowOffset);
			if(null == row) sheet.createRow(rowOffset);
			HSSFCell cell = row.getCell(cellOffset);
			if(null == cell) cell = row.createCell(cellOffset);
			cell.setCellValue(value);
		}
	}
	
	/**
	 * 往 Excel 中写入数据
	 */
	private void writeDataToXls(){
		if(null==dataSource || dataSource.getRowCount()==0) return;
		 Map<String, Integer[]> map = cfg.getCellIndexMap();
		 GatherInfo gatherInfo = cfg.getGahter();
		 Set<String> keySet = map.keySet();
		 HSSFRow row = null;
		 lastRownum = cfg.getCmnCellfirst()-1;
		 Map<String, Renderer> renderMap = cfg.getRenderMap();
		 Map<Integer, CellStyle> cmnStyleMap = getCmnStyles(sheet.getRow(lastRownum),map);
		 if(null==gatherInfo){
			 for(int i=0,count=dataSource.getRowCount(); i<count; i++){
				 row = sheet.getRow(lastRownum);
				 if(null==row) row=sheet.createRow(lastRownum);
				 Object[] data = dataSource.getCellDatas(i);
				 for(String cmn : keySet){
//					 System.out.println("cmn="+cmn);
					 int valindex = dataSource.getCellIndex(cmn);
					 String val = (cmn.indexOf("#yyyy-MM-dd") != -1) ? dataSource.getDateString(valindex, cmn,data) : dataSource.getString(valindex,cmn,data);
					 val = getRenererVals(renderMap, cmn, val);
					 Integer cellIndex = getCellIndex(cmn, map);
					 if(-1==cellIndex) continue;
					 HSSFCell cell = row.getCell(cellIndex);
					 cell = createCell(row, cmnStyleMap, cellIndex, cell); 
					 cell.setCellValue(val);
				 }
				 lastRownum++;
			 }
		 }else{
			 String sumtitle = gatherInfo.getTitle();
			 int titleIndex = gatherInfo.getTitleIndex();
			 String[] sumcmns = gatherInfo.getSumcmns();
			 Map<String, Object[]> sumMap = getSumCmnsMap(sumcmns,map);
			 for(int i=0,count=dataSource.getRowCount(); i<count; i++){
				 row = sheet.getRow(lastRownum);
				 if(null == row) row = sheet.createRow(lastRownum);
				 Object[] data = dataSource.getCellDatas(i);
				 for(String cmn : keySet){
//					 System.out.println("sum ---> cmn="+cmn);
					 int valindex = dataSource.getCellIndex(cmn);
					 String val = (cmn.indexOf("#yyyy-MM-dd") != -1) ? dataSource.getDateString(valindex, cmn,data) : dataSource.getString(valindex,cmn,data);
//					 System.out.println("val="+val);
					 val = getRenererVals(renderMap, cmn, val);
					 int cellIndex = getCellIndex(cmn, map);
					 if(-1==cellIndex) continue;
					 HSSFCell cell = row.getCell(cellIndex);
					 cell = createCell(row, cmnStyleMap, cellIndex, cell); 
					 cell.setCellValue(val);
					 if(StringHandler.isValidStr(val) && sumMap.containsKey(cmn)){
						 Object[] sumInf = sumMap.get(cmn);
						 sumInf[1] = (Double)sumInf[1]+ Double.parseDouble(val);
						 sumMap.put(cmn, sumInf);
					 }
				 }
				 lastRownum++;
			 }
			 
			 createSumRow(cmnStyleMap, sumtitle, titleIndex, sumMap);
		 }
	}
	
	/**
	 * 获取Renerer 值
	 * @param renderMap  Renerer Map 对象
	 * @param cmn 列名
	 * @param val	原始值
	 * @return	替换后的新值
	 */
	private String getRenererVals(Map<String, Renderer> renderMap,String cmn,String val){
		if(null==renderMap || renderMap.size() == 0) return val;
		if(!renderMap.containsKey(cmn)) return val;
		Renderer renderer = renderMap.get(cmn);
		if(null==renderer) return val;
		val = renderer.getTargetVal(val);
		if(!StringHandler.isValidStr(val)) return "";
		return val;
	}

	private void createSumRow(Map<Integer, CellStyle> cmnStyleMap,
			String sumtitle, int titleIndex, Map<String, Object[]> sumMap) {
		//---> 创建汇总行
		 HSSFRow sumRow = sheet.createRow(lastRownum);
		 HSSFCell titlecell = createCell(sumRow, cmnStyleMap, titleIndex, null); 
		 titlecell.setCellValue(sumtitle);
		 Set<String> sumcmnSet = sumMap.keySet();
		 for(String sumcmnKey : sumcmnSet){
			 Object[] indexes = sumMap.get(sumcmnKey);
			 int cellIndex= (Integer)indexes[0];
			 double val = (Double)indexes[1];
			 val=StringHandler.Round(val, 2);
			 HSSFCell cell = createCell(sumRow, cmnStyleMap, cellIndex, null); 
			 cell.setCellValue(val);
		 }
	}
	
	/**
	 * 创建一个包含样式的单元格对象
	 * @param row
	 * @param cmnStyleMap
	 * @param cellIndex
	 * @param cell
	 * @return
	 */
	private HSSFCell createCell(HSSFRow row,
			Map<Integer, CellStyle> cmnStyleMap, Integer cellIndex,
			HSSFCell cell) {
		if(null==cell){
			 cell = row.createCell(cellIndex);
			 CellStyle cellStyle = cmnStyleMap.get(cellIndex);
			 if(null != cellStyle) cell.setCellStyle(cellStyle);
		 }
		return cell;
	}
	
	/**
	 * 获取 Excel 模板中第一行单元格样式
	 * @param firstRow	第一个数据行
	 * @return
	 */
	private Map<Integer, CellStyle> getCmnStyles(HSSFRow firstRow,Map<String, Integer[]> map){
		Map<Integer, CellStyle> stylemap = new HashMap<Integer, CellStyle>();
		if(null==firstRow) return stylemap;
		Set<String> keySet= map.keySet();
		for(String key : keySet){
			Integer[] indexes = map.get(key);
			Integer cellIndex = indexes[1];
			HSSFCell cell = firstRow.getCell(cellIndex);
			if(null==cell) continue;
			HSSFCellStyle cellStyle = cell.getCellStyle();
			if(null != cellStyle) stylemap.put(cellIndex, cellStyle);
		}
		return stylemap;
	}
	
	/**
	 * 返回汇总对象信息
	 * @param sumcmns 汇总列数组	
	 * @param map	列索引 Map 对象
	 * @return 
	 */
	public Map<String, Object[]> getSumCmnsMap( String[] sumcmns, Map<String, Integer[]> map){
		Map<String, Object[]> sumMap = new HashMap<String, Object[]>();
		for(String cmn : sumcmns){
			if(!map.containsKey(cmn)) continue;
			Object[] vals = {map.get(cmn)[1],0.00};
			sumMap.put(cmn, vals);
		}
		return sumMap;
	}
	
	/**
	 * 获取单元格索引
	 * @param key	Map 对象的 键
	 * @param map	用来取索引的 Map 对象
	 * @return	返回单元格所在的索引
	 */
	private Integer getCellIndex(String key, Map<String, Integer[]> map){
		int cellIndex = -1;
		Integer[] r_cIndex = map.get(key);
		cellIndex = r_cIndex[1];
		return cellIndex;
	}
	/**
	 * 根据模板EXCEL 文件创建要导出的 Workbook 对象
	 */
	private void createExportWb(){
		try {
			String sourcefilepath = cfg.getTemplatePath();
			exportWb = handler.getWorkbook(sourcefilepath);
			sheet = exportWb.getSheetAt(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 清除 Excel 参数配置信息
	 */
	private void clearXlsCfgInfo(){
		HSSFRow firstRow = sheet.getRow(0);
		HSSFCell firstCell =  firstRow.getCell(0);
		HSSFCell twoCell = firstRow.getCell(1);
		int cmnrownum = cfg.getCmnRownum();
		boolean isDelCfg=false;
		if(null == twoCell){
			isDelCfg = true;
		}
		if(null != twoCell){
			Object cfgVal= handler.getVal(twoCell);
			if(!StringHandler.isValidObj(cfgVal)) isDelCfg = true;
		}
		if(isDelCfg){
			sheet.shiftRows(1, sheet.getLastRowNum(), -1);
			cmnrownum -= 1;
		}else{
			if(null == firstCell) firstCell = firstRow.createCell(0);
			Object val = handler.getVal(twoCell);
			if(!StringHandler.isValidObj(val)) return;
			firstCell.setCellValue((String)val);
			twoCell.setCellValue("");
		}
		sheet.shiftRows(cmnrownum, sheet.getLastRowNum(), -1);
	}
	
	public OutputStream getOs() {
		return os;
	}

	public void setOs(OutputStream os) {
		this.os = os;
	}

	public DataTable getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataTable dataSource) {
		this.dataSource = dataSource;
	}

	public XlsTempCfgModel getCfg() {
		return cfg;
	}

	public void setCfg(XlsTempCfgModel cfg) {
		this.cfg = cfg;
	}

	
}
