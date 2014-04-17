package com.cmw.core.kit.flexpaper;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONArray;
import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFamily;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.kit.file.FileUtil;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.AttachmentEntity;
import com.cmw.service.inter.sys.AttachmentService;


/** 
 * Servlet implementation class ConvertServlet
 */
public class ConvertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private WebApplicationContext ctx = null;
	AttachmentService attachmentTempService = null;
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
	
    
	OpenOfficeConnection connection = null;
	
	DocumentConverter converter = null;
	Runtime runtime = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConvertServlet() {
        super();
    }

	@Override
	public void init() throws ServletException {
		super.init();
		startOpenOfficeService();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		startConvert(request, response);
		request.getRequestDispatcher("./controls/flexpaper/online.jsp").forward(request, response);
	}
	
	private void startConvert(HttpServletRequest request, HttpServletResponse response) {
		initialize();
		JSONArray jsonArr = null;
		ServletContext sc = request.getSession().getServletContext();
		this.ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
		attachmentTempService = (AttachmentService)ctx.getBean("attachmentService");
		String attachmentIds = request.getParameter("ids");
		if(StringHandler.isValidStr(attachmentIds)){
			try {
				List<AttachmentEntity> attachments = loadAttachments(request, attachmentIds);
				if(null != attachments && attachments.size() > 0){
					convertAttachments(request,attachments);
					updateAttachments(attachments);
					jsonArr = convertAttachmentsToArr(attachmentIds);
				}
				destory();
			} catch (com.cmw.core.base.exception.ServiceException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
//			finally{
//				destory();
//			}
		}else{/*通過文件路徑方式轉換，后期實現  filePath*/
//			loadFiles(request);
//			for(File file : sourceList){
//				if(isSwf(file)) continue;
//				if(isPdf(file)){
//					convertToSWF(request,file);
//					continue;
//				}
//				file = convertToPDF(request,file);
//				convertToSWF(request,file);
//			}
		}
		request.setAttribute("documentDatas", jsonArr);
		//destory();
	}
	
	private void convertAttachments(HttpServletRequest request,List<AttachmentEntity> attachments){
		for(AttachmentEntity attachment : attachments){
			String filePath = attachment.getFilePath();
			String  absfilePath = FileUtil.getFilePath(request, filePath);
			File sourceFile = new File(absfilePath);
//			File sourceFile = new File("F:/dev/skythink/WebContent/controls/flexpaper/data.wps");
			if(isSwf(sourceFile)){
				attachment.setSwfPath(filePath);
				continue;
			}
			if(!isPdf(sourceFile)){
				sourceFile = convertToPDF(request,sourceFile);
			}
			String swfPath = convertToSWF(request,sourceFile);
			attachment.setSwfPath(swfPath);
		}
	}
	
	private void updateAttachments(List<AttachmentEntity> attachments) throws com.cmw.core.base.exception.ServiceException{
		attachmentTempService.batchUpdateEntitys(attachments);
	}
	
	
	private boolean isPdf(File sourceFile){
		boolean flag = false;
		String fileName = sourceFile.getName();
		flag = (sourceFile.exists() && (fileName.endsWith(".pdf") || fileName.endsWith(".PDF")));
		return flag;
	}
	
	private boolean isSwf(File sourceFile){
		boolean flag = false;
		String fileName = sourceFile.getName();
		flag = (sourceFile.exists() && (fileName.endsWith(".swf") || fileName.endsWith(".SWF")));
		return flag;
	}
	
	private File convertToPDF(HttpServletRequest request,File sourceFile){
		String[] pathArr = getAbsPath(request,CONVERT_PDF_PATH);
		String serverPath = pathArr[1];
		String fileName = getFileName(sourceFile, ".pdf");
		File pdfFile = new File(serverPath+fileName);
		converter.convert(sourceFile,pdfFile);
		try {
			pdfFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pdfFile;
	}

	private String[] getAbsPath(HttpServletRequest request,String rootDir) {
		String[] pathArr = new String[2];
		Calendar c = Calendar.getInstance();
		if(rootDir.lastIndexOf("/")<(rootDir.length()-1)) rootDir += "/";
		String ymDir = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/";
		String filePath = rootDir + ymDir;
		String absserverPath = FileUtil.getFilePath(request, rootDir)+ymDir;
		File file = new File(absserverPath);
		if(!file.exists()) file.mkdirs();
		pathArr[0] = filePath;
		pathArr[1] = absserverPath;
		return pathArr;
	}
	
	private String getFileName(File sourceFile,String fileEndWidth){
		String absPath = sourceFile.getAbsolutePath();
		absPath = absPath.replaceAll("[\\\\]", "/");
		String fileName = absPath.substring(absPath.lastIndexOf("/")+1,absPath.lastIndexOf("."));
		return fileName + fileEndWidth;
	}
	
	private String convertToSWF(HttpServletRequest request,File pdfFile){
		String[] pathArr = getAbsPath(request,CONVERT_SWF_PATH);
		String serverPath = pathArr[1];
		String fileName = getFileName(pdfFile, ".swf");
		File swfFile = new File(serverPath+fileName);
		try {
			Process process = runtime.exec(SWFTOOLS_EXE_PATH+" "+pdfFile.getPath() + " -o " + swfFile.getPath() + " -T 9");
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			swfFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pathArr[0]+fileName;
	}

	private JSONArray convertAttachmentsToArr(String attachmentIds) throws ServiceException{
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("attachmentIds", attachmentIds);
		DataTable dt = attachmentTempService.getResultList(params);
		if(null == dt || dt.getRowCount() == 0) return null;
		//"id,sysId,formType,formId,atype,fileName,filePath,swfPath,fileSize,creator,createTime#yyyy-MM-dd";
		return 	dt.getJsonList();
	}
	
	private List<AttachmentEntity> loadAttachments(HttpServletRequest request,String attachmentIds) throws com.cmw.core.base.exception.ServiceException{
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + attachmentIds);
		List<AttachmentEntity> list = attachmentTempService.getEntityList(params);
		if(null == list || list.size() == 0) return null;
		List<AttachmentEntity> unList = new ArrayList<AttachmentEntity>();
		for(AttachmentEntity entity : list){
			String filePath = entity.getFilePath();
			String swfPath =  entity.getSwfPath();
			if(!StringHandler.isValidStr(swfPath)){
				unList.add(entity);
				continue;
			}
			if(isSameFileName(filePath, swfPath)) continue;
			unList.add(entity);
		}
		return unList;
	}
	
	private boolean isSameFileName(String filePath, String swfPath){
		filePath = filePath.replaceAll("[\\\\]", "/");
		swfPath = swfPath.replaceAll("[\\\\]", "/");
		filePath = filePath.substring(filePath.lastIndexOf("/")+1,filePath.lastIndexOf("."));
		swfPath = swfPath.substring(swfPath.lastIndexOf("/")+1,swfPath.lastIndexOf("."));
		return (filePath.equals(swfPath));
	} 
	
//	private void loadFiles(HttpServletRequest request){
//	//	sourceList.add(new File("F:/dev/flexpaper/WebContent/readonline/财务基础（非财务人员）.ppt"));
//	//	sourceList.add(new File("F:/dev/flexpaper/WebContent/readonline/财务基础知识.ppt"));
//		sourceList.add(new File("F:/dev/flexpaper/WebContent/readonline/财务基础_非财务人员.ppt"));
//	}
	
	private void initialize(){
//		startOpenOfficeService();
//		connection = new SocketOpenOfficeConnection(Integer.parseInt(OPENOFFICE_PORT));
		connection = new SocketOpenOfficeConnection(OPENOFFICE_HOST, Integer.parseInt(OPENOFFICE_PORT));
		try {
			connection.connect();
		} catch (ConnectException e) {
			e.printStackTrace();
		}
		converter = new OpenOfficeDocumentConverter(connection);
		runtime = Runtime.getRuntime();
	}
	
	/**
	 * 开始启动 OpenOffice 服务
	 */
	private void startOpenOfficeService(){
		/*-- step 1 : 验证服务是否已启动  --*/
		boolean isOpen = CmdProcess.isOpen(OPENOFFICE_HOST, Integer.parseInt(OPENOFFICE_PORT));
		if(!isOpen){ /*-- step 2 : 服务未启动，开始启动服务  --*/
			StringBuffer sb = new StringBuffer();
			sb.append(OPENOFFICE_DIR).append("\n");
			String openofficeServiceStr = OPENOFFICE_SERVICE.replace("#HOST#", OPENOFFICE_HOST).replace("#PORT#", OPENOFFICE_PORT);
			sb.append(openofficeServiceStr);
			CmdProcess process = new CmdProcess();
			process.startService(openofficeServiceStr);
		}
		
	}
	
	
	private void destory(){
		if(null != connection){
			connection.disconnect();
			connection = null;
		}
		if(null != runtime)runtime.exit(0);
	}
	
}
