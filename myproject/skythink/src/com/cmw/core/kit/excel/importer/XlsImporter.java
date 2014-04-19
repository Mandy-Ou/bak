package com.cmw.core.kit.excel.importer;

import java.util.List;

/**
 * Excel 数据导出处理接口
 * @author chengmingwei
 *
 */
public interface XlsImporter {
	/**
	 * 读取Excel  工作薄 中第一个 Sheet 中的数据
	 * @return 返回一个 List 列表数据
	 */
	public List<List<Object>> readDatas();
	
	/**
	 * 读取Excel  工作薄 中某个 Sheet 中的数据
	 * @param sheetIndex  Sheet 索引，标识是 Excel 表格中第几个 sheet 
	 * @return 返回一个 List 列表数据
	 */
	public List<List<Object>> readDatas(int sheetIndex);
	/**
	 * 读取Excel  工作薄 中指定名称 Sheet 中的数据
	 * @param sheetName Sheet 名称
	 * @return 返回一个 List 列表数据
	 */
	public List<List<Object>> readDatas(String sheetName);
}
