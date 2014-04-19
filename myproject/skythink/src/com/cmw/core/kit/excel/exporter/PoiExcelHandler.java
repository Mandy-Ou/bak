package com.cmw.core.kit.excel.exporter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
/**
 * POI Excel 操作类
 * @author chengmingwei
 * @date 2013-01-18 21:32
 */
public class PoiExcelHandler {
	/**
	 * 从指定的Excel 模板文件中得到一个 工作薄对象
	 * @param sourceFile	Excel 模板文件路径
	 * @return	返回 工作薄对象[HSSFWorkbook]
	 * @throws IOException
	 */
	public HSSFWorkbook getWorkbook(String sourceFile) throws IOException{
		FileInputStream fis = new FileInputStream(sourceFile);
		POIFSFileSystem poiFs = new POIFSFileSystem(fis);
		HSSFWorkbook wb = new HSSFWorkbook(poiFs);
		fis.close();
		return wb;
	}
	
	/**
	 * 根据单元格数据类型，获取单元格的值
	 * @param type	Cell 数据类型
	 * @return
	 */
	public Object getVal(HSSFCell cell){
		int type = cell.getCellType();
		switch (type) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
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
	 * 根据单元格数据类型，获取指定类型的值
	 * @param type	Cell 数据类型
	 * @return
	 */
	public Object getValByType(int type,HSSFCell cell){
		switch (type) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
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
	 * 将工作薄以指定的Excel 文件名写入文件
	 * @param wb	工作薄对象
	 * @param outputFile	要写入的新文件名
	 * @throws IOException
	 */
	public void writeExls(HSSFWorkbook wb,String outputFile) throws IOException{
		FileOutputStream os = new FileOutputStream(outputFile);
		wb.write(os);
		os.close();
	}
	
	/**
	 * 插入数据
	 * @param sheet
	 * @param rowAction
	 */
	public void insertDatas(HSSFSheet sheet,RowAction rowAction){
		int start = 3;
		int rownum = sheet.getLastRowNum();
		if(rownum == start) rownum++;
		List<String> sqls = new ArrayList<String>();
		HSSFRow row = null;
		HSSFCell cell = null;
		for(; start<rownum; start++){
			row = sheet.getRow(start);
			rowAction.doRow(row);
		}
	}
	
	public void updateCells(HSSFSheet sheet,int start, int cmnIndex,CellAction action){
		int rownum = sheet.getLastRowNum();
		HSSFRow row = null;
		HSSFCell cell = sheet.getRow(start).getCell(cmnIndex);
		int cellType = cell.getCellType();
		for(; start <= rownum; start++){
			row = sheet.getRow(start);
			cell = row.getCell(cmnIndex);
			if(null == cell || cell.getRichStringCellValue() == null) continue;
			action.doCell(cell,cellType);
		}
	}
	
	public void updateCells(HSSFSheet sheet,int start,int end,int cmnIndex,CellAction action){
		int rownum = end;
		HSSFRow row = null;
		HSSFCell cell = sheet.getRow(start).getCell(cmnIndex);
		int cellType = cell.getCellType();
		for(; start <= rownum; start++){
			row = sheet.getRow(start);
			cell = row.getCell(cmnIndex);
			if(null == cell || row == null) continue;
			action.doCell(cell,cellType);
		}
	}

	/**
	 * 验证字符串是否为空
	 * @param str	要验证的字符串
	 * @return	true : 不为空，	false : 为空
	 */
	public static boolean validStr(String str){
		return (null == str || str.equals("")) ? false : true;
	}
	
	public interface CellAction{
		public HSSFCell doCell(HSSFCell cell,int cellType);
	}
	
	public interface RowAction{
		public HSSFRow doRow(HSSFRow row);
	}
	
}
