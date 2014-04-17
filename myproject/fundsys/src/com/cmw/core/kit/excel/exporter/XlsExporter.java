package com.cmw.core.kit.excel.exporter;

import java.io.IOException;
import java.io.OutputStream;

import com.cmw.core.util.DataTable;

/**
 * Excel 导出接口
 * @author chengmingwei
 *
 */
public interface XlsExporter {
	/**
	 * 导出Excel
	 * @param os 输出流对象
	 * @param dataSource 要导出的数据源对象
	 * @param cfg Excel 模板参数配置对象
	 */
	public void exporter(OutputStream os,DataTable dataSource,XlsTempCfgModel cfg)throws IOException;
}
