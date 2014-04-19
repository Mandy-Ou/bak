package com.cmw.core.kit.flexpaper;

import java.net.ConnectException;

import org.springframework.web.context.WebApplicationContext;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.cmw.core.util.StringHandler;
import com.cmw.service.inter.sys.AttachmentService;

public class DocumentConvertHandler {
	public WebApplicationContext ctx;
	public AttachmentService attachmentTempService;
	public OpenOfficeConnection connection;
	public DocumentConverter converter;
	public Runtime runtime;

	/**
	 * swftools 转换工具所在的安装目录
	 */
	private static final String SWFTOOLS_EXE_PATH = StringHandler.GetResValue("swftools_exe_path");
	/**
	 * openoffice 主机地址
	 */
	private static final String OPENOFFICE_HOST = StringHandler.GetResValue("openoffice_host");
	/**
	 * openoffice 端口地址
	 */
	private static final String OPENOFFICE_PORT = StringHandler.GetResValue("openoffice_port");
	/**
	 * openoffice 所在的安装目录
	 */
	private static final String OPENOFFICE_DIR = StringHandler.GetResValue("openoffice_dir");
	/**
	 * openoffice 服务命令行
	 */
	private static final String OPENOFFICE_SERVICE = StringHandler.GetResValue("openoffice_service");
	/**
	 * 轉換后的PDF文件存放目錄
	 */
	private static final String CONVERT_PDF_PATH = StringHandler.GetResValue("convert_pdf_path");
	/**
	 * 轉換后的 SWF 文件存放目錄
	 */
	private static final String CONVERT_SWF_PATH = StringHandler.GetResValue("convert_swf_path");
	
	public DocumentConvertHandler(WebApplicationContext ctx,
			AttachmentService attachmentTempService,
			OpenOfficeConnection connection, DocumentConverter converter,
			Runtime runtime) {
		this.ctx = ctx;
		this.attachmentTempService = attachmentTempService;
		this.connection = connection;
		this.converter = converter;
		this.runtime = runtime;
	}
	
	private void initialize(){
		connection = new SocketOpenOfficeConnection(OPENOFFICE_HOST, Integer.parseInt(OPENOFFICE_PORT));
		try {
			connection.connect();
		} catch (ConnectException e) {
			e.printStackTrace();
		}
		converter = new OpenOfficeDocumentConverter(connection);
		runtime = Runtime.getRuntime();
	}
	
}