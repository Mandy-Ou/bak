package com.cmw.core.kit.excel.importer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.cmw.core.base.exception.UtilException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.StringHandler;

/**
 * POI 操作 Excel 工具类
 * @author cmw_1984122
 *
 */
public class POIXlsImporter implements XlsImporter{
	private String fileName;
	private HSSFWorkbook wb;
	
	public POIXlsImporter(String fileName) {
		this.fileName = fileName;
		this.createWorkBook();	//创建Excel 工作薄
	}
	
	/**
	 * 创建 Excel 工作薄
	 */
	public void createWorkBook(){
		if(!StringHandler.isValidStr(this.fileName)) return;
		FileInputStream fi = null;
		try {
			fi = new FileInputStream(this.fileName);
			this.wb = new HSSFWorkbook(fi);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fi.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 读取Excel  工作薄 中第一个 Sheet 中的数据
	 * @return
	 */
	public List<List<Object>> readDatas(){
		HSSFSheet sheet = wb.getSheetAt(0);
		return this.readDatas(sheet);
	}
	/**
	 * 读取Excel  工作薄 中第一个 Sheet 中的数据
	 * @param startRowIndex 读数据的起始索尼
	 * 
	 * @return
	 */
	public DataTable readDatasToDt(int startRowNum,int endRowNum,String columnNames)throws UtilException{
		HSSFSheet sheet = wb.getSheetAt(0);
		DataTable dt = new DataTable(columnNames);
		HSSFRow row = null;
		HSSFCell cell = null;
		List<Object> rowsdatas = new ArrayList<Object>();
		int i=startRowNum>0 ? startRowNum-1 : 0;
		row = sheet.getRow(i);
		int cellCount = row.getLastCellNum();
		int count = endRowNum > 0 ? endRowNum : sheet.getLastRowNum();
		if(count<=0) return null;
		try{
			for(; i<count; i++){
				row = sheet.getRow(i);
				Object[] cellDatas = new Object[cellCount];
				for(int j=0; j<cellCount; j++){
					cell = row.getCell(j);
					int cellType = cell.getCellType();
					Object val = this.getValByType(cellType, cell);
					cellDatas[j] = val;
				}
				rowsdatas.add(cellDatas);
			}
			dt.setDataSource(rowsdatas);
			return dt;
		}catch (Exception e) {
			throw new UtilException(e.getMessage());
		}
	}
	
	/**
	 * 读取Excel  工作薄 中第一个 Sheet 中的数据
	 * @param startRowIndex 读数据的起始索尼
	 * 
	 * @return
	 */
	public DataTable readDatasToDt(int startRowNum,BreakAction action,String columnNames)throws UtilException{
		HSSFSheet sheet = wb.getSheetAt(0);
		DataTable dt = new DataTable(columnNames);
		HSSFRow row = null;
		HSSFCell cell = null;
		List<Object> rowsdatas = new ArrayList<Object>();
		int i=startRowNum>0 ? startRowNum-1 : 0;
		row = sheet.getRow(i);
		int cellCount = row.getLastCellNum();
		int count = sheet.getLastRowNum();
		if(count<=0) return null;
		try{
			boolean flag = false;
			for(; i<count; i++){
				row = sheet.getRow(i);
				Object[] cellDatas = new Object[cellCount];
				for(int j=0; j<cellCount; j++){
					cell = row.getCell(j);
					int cellType = cell.getCellType();
					Object val = this.getValByType(cellType, cell);
					if(action.exit(i, val)){
						flag = true;
						break;
					}
					cellDatas[j] = val;
				}
				if(flag) break;
				rowsdatas.add(cellDatas);
			}
			dt.setDataSource(rowsdatas);
			return dt;
		}catch (Exception e) {
			throw new UtilException(e.getMessage());
		}
	}
	
	/**
	 * 读取Excel  工作薄 中第一个 Sheet 中的数据
	 * @param startRowIndex 读数据的起始索引
	 * @param action 退出对象
	 * @param columnNames DataTable 对象列名
	 * @param cellIndexes 要读取的 Excel 列索引数组字符串
	 * @return 返回 DataTable 对象
	 */
	public DataTable readDatasToDt(int startRowNum,BreakAction action,String columnNames, String cellIndexes)throws UtilException{
		String[] cmnArr = columnNames.split(",");
		String[] cellIndexArr = cellIndexes.split(",");
		if(cmnArr.length != cellIndexArr.length) throw new UtilException("参数 columnNames 中的列数量必须与 cellIndexes 中的数量相同!");
		HSSFSheet sheet = wb.getSheetAt(0);
		DataTable dt = new DataTable(columnNames);
		HSSFRow row = null;
		HSSFCell cell = null;
		List<Object> rowsdatas = new ArrayList<Object>();
		int i=startRowNum>0 ? startRowNum-1 : 0;
		row = sheet.getRow(i);
		int cellCount = cellIndexArr.length;
		int count = sheet.getLastRowNum();
		if(count<=0) return null;
		try{
			boolean flag = false;
			for(; i<count; i++){
				row = sheet.getRow(i);
				Object[] cellDatas = new Object[cellCount];
				for(int j=0; j<cellCount; j++){
					int index = Integer.parseInt(cellIndexArr[j]);
					cell = row.getCell(index);
					if(!StringHandler.isValidObj(cell)){
						flag = true;
						break;
					}
					int cellType = cell.getCellType();
					Object val = this.getValByType(cellType, cell);
					if(action.exit(i, val)){
						flag = true;
						break;
					}
					cellDatas[j] = val;
				}
				if(flag) break;
				rowsdatas.add(cellDatas);
			}
			dt.setDataSource(rowsdatas);
			return dt;
		}catch (Exception e) {
			e.printStackTrace();
			throw new UtilException(e.getMessage());
		}
	}
	
	
	/**
	 * 读取指定行的值
	 * @param rowIndex
	 * @return 返回指定行的所有单元格值
	 */
	public List<Object> getRowData(int rowIndex){
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(rowIndex);
		HSSFCell cell = null;
		int cellCount = row.getLastCellNum();
		List<Object> cellDatas = new ArrayList<Object>();
		for(int j=0; j<cellCount; j++){
			cell = row.getCell(j);
			int cellType = cell.getCellType();
			Object val = this.getValByType(cellType, cell);
			cellDatas.add(val);
		}
		return cellDatas;
	}
	
	/**
	 * 读取指定行的值
	 * @param rowIndex	行索引
	 * @param cellIndex	列索引
	 * @return 返回指定单元格的值
	 */
	public Object getCellData(int rowIndex,int cellIndex){
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(rowIndex);
		HSSFCell cell = null;
		cell = row.getCell(cellIndex);
		int cellType = cell.getCellType();
		Object val = this.getValByType(cellType, cell);
		return val;
	}
	
	/**
	 * 读取Excel  工作薄 中指定索引处 Sheet 中的数据
	 * @param index Sheet 索引，标识是第几个Excel 表格
	 * @return
	 */
	public List<List<Object>> readDatas(int index){
		HSSFSheet sheet = wb.getSheetAt(index);
		return this.readDatas(sheet);
	}
	
	/**
	 * 读取Excel  工作薄 中指定名称 Sheet 中的数据
	 * @param sheetName Sheet 名称
	 * @return
	 */
	public List<List<Object>> readDatas(String sheetName){
		HSSFSheet sheet = wb.getSheet(sheetName);
		return this.readDatas(sheet);
	}
	
	/**
	 * 读取指定 Sheet 表中的数据，并以List 返回
	 * @param sheet	指定的 sheet 对象
	 * @return
	 */
	public List<List<Object>> readDatas(HSSFSheet sheet){
		HSSFRow row = null;
		HSSFCell cell = null;
		List<List<Object>> rowsdatas = new ArrayList<List<Object>>();
		int count = sheet.getLastRowNum();
		if(count<=0) return null;
		row = sheet.getRow(0);
		int cellCount = row.getLastCellNum();
		for(int i=0; i<=count; i++){
			row = sheet.getRow(i);
			List<Object> cellDatas = new ArrayList<Object>();
			for(int j=0; j<cellCount; j++){
				cell = row.getCell(j);
				int cellType = cell.getCellType();
				Object val = this.getValByType(cellType, cell);
				cellDatas.add(val);
			}
			rowsdatas.add(cellDatas);
		}
		return rowsdatas;
	}
	
	/**
	 * 根据单元格数据类型，获取指定类型的值
	 * @param type	Cell 数据类型
	 * @return
	 */
	public Object getValByType(int type,HSSFCell cell){
		switch (type) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			return (int)cell.getNumericCellValue();
		case HSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case HSSFCell.CELL_TYPE_BLANK:	
			return "";
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		case HSSFCell.CELL_TYPE_ERROR:
			return cell.getErrorCellValue();
		default:
			return cell.getDateCellValue();
		}
	}
	
	/**
	 * 退出接口
	 * @author chengmingwei
	 *
	 */
	public interface BreakAction{
		/**
		 * 
		 * @param rowNum
		 * @param cellVal
		 * @return
		 */
		boolean exit(int rowNum, Object cellVal);
	}
	
	public static void main(String[] args){
		String fileName = "E:/test.xls";
		POIXlsImporter poiObj = new POIXlsImporter(fileName);
		List<List<Object>> datas = poiObj.readDatas();
		for(List<Object> data : datas){
			for(Object val : data){
				System.out.println(val);
			}
			System.out.println();
		}
	}
}
