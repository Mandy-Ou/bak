package com.cmw.core.kit.excel.exporter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.cmw.core.util.DataTable;

/**
 * Excel 导出 ---- 单条数据模板导出
 * @author chengmingwei
 *
 */
public class POISingleTempExporter implements XlsExporter {

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
		//往Excel中写入数据
		writeDataToXls();
		//清除 Excel 参数配置信息
		clearXlsCfgInfo();
		//写入到输出流当中
		exportWb.write(os);
	}
	
	/**
	 * 往 Excel 中写入数据
	 */
	private void writeDataToXls(){
		 Map<String, Integer[]> map = cfg.getCellIndexMap();
		 Set<String> keySet = map.keySet();
		
		 for(String key : keySet){
			 String val = dataSource.getString(key);
			 Integer[] cellOffest = map.get(key);
			 int rowIndex = cellOffest[0];
			 int cellIndex = cellOffest[1];
			 HSSFRow row = sheet.getRow(rowIndex);
			 if(null == row) continue;
			 HSSFCell cell = row.getCell(cellIndex);
			 if(null == cell) continue;
			 cell.setCellValue(val);
		 }
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
		int lastrownum = sheet.getLastRowNum();
		sheet.shiftRows(1, lastrownum, -1);
		sheet.shiftRows(cfg.getCmnRownum(), lastrownum, -1);
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
