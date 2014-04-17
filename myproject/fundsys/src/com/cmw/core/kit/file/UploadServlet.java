package com.cmw.core.kit.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.AttachmentEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.cache.UserCache;
import com.cmw.service.inter.sys.AttachmentService;

/**
 * Servlet implementation class for Servlet: UploadServlet1
 *	
 */
 public class UploadServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   private WebApplicationContext ctx = null;
   AttachmentService attachmentTempService = null;
   private String state = null;
   private String[] allowFiles = null;//允许上传的文件格式
   private HashMap<String, String> errorInfo = new HashMap<String, String>();
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public UploadServlet() {
		super();
		HashMap<String, String> tmp = this.errorInfo;
		tmp.put("SUCCESS", "SUCCESS"); //默认成功
		tmp.put("NOFILE", "未包含文件上传域");
		tmp.put("TYPE", "不允许的文件格式");
		tmp.put("SIZE", "文件大小超出限制");
		tmp.put("ENTYPE", "请求类型ENTYPE错误");
		tmp.put("REQUEST", "上传请求异常");
		tmp.put("IO", "IO异常");
		tmp.put("DIR", "目录创建失败");
		tmp.put("UNKNOWN", "未知错误");
		String allowFiles_ext = StringHandler.GetResValue("allowFiles_ext");
		if(StringHandler.isValidStr(allowFiles_ext)){
			allowFiles = allowFiles_ext.split(",");
		}
	}   	

//	@Override
//	protected void service(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		ServletContext sc = request.getSession(false).getServletContext();
//		this.ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
//		doUpload(request,response);
//	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req,resp);
	}

	private void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext sc = request.getSession(false).getServletContext();
		this.ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
		try {
			doUpload(request,response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 文件上传方法
	 * @throws ServiceException 
	 * @throws NumberFormatException 
	 * @throws IOException 
	 */
	public void doUpload(HttpServletRequest request,HttpServletResponse response) throws NumberFormatException, ServiceException{
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html");
		String msg = "上传成功";
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		if(checkUserSession(request,out)) return;
		List<FileItem> items = null;
		try {
			items = getFileItems(request);
		} catch (FileUploadException e1) {
			e1.printStackTrace();
		}
		if(null == items || items.size()==0) return;
		//获取上传目录
		String path = this.getUplodDir(request,items);
	
		//获取绝对路径
		String absPath = FileUtil.getFilePath(request, path);
		FileUtil.creatDictory(absPath); //创建上传目录
		
	
		boolean success = true;
		JSONArray jsonArr = null;
		try {
			jsonArr = startUpload(items, path, absPath);
			saveAttachments(request,jsonArr);
		
		} catch (IOException e) {
			e.printStackTrace();
			success = false;
			msg = "上传失败";
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
			msg = "上传失败";
		}
		outJson(request,out,success,msg,jsonArr);
	}

	/**
	 * 检查Session 是否过期
	 * @param request
	 * @return
	 * @throws ServiceException 
	 * @throws NumberFormatException 
	 */
	private boolean checkUserSession(HttpServletRequest request,PrintWriter out) throws NumberFormatException, ServiceException{
		boolean isTimeOut = false;
		UserEntity user = getUser(request);
		if(null == user){
			isTimeOut = true;
			out.write(ResultMsg.USERSESSION_TIMEOUT);
		}
		return isTimeOut;
	}
	
	private void setAllowFiles(String allowFilesFmt) {
		if(StringHandler.isValidStr(allowFilesFmt)){
			allowFilesFmt = StringHandler.GetResValue(allowFilesFmt);
			this.allowFiles = allowFilesFmt.split(",");
		}
	}
	
	private void outJson(HttpServletRequest request,PrintWriter out,boolean success,String msg,JSONArray jsonArr){
		String tagSource = request.getParameter("tagSource");
		JSONObject json = new JSONObject();
		String jsonStr = null;
		if(!StringHandler.isValidStr(tagSource)){
			if(StringHandler.isValidStr(this.state) && !this.state.equals(this.errorInfo.get("SUCCESS"))){
				success = false;
				msg = "上传失败";
			}
			json.put("success", success);
			json.put("msg", msg);
			json.put("fileInfos", jsonArr);
		}else{
			if(tagSource.equals("ueditor")){/*百度冨文本编辑器上传*/
				String original = null;
				String url = null;
				String title = null;
				String fileExt = null;
				String size = "0";
				if(null != jsonArr && jsonArr.size()>0){
					JSONObject fileObj = jsonArr.getJSONObject(0);
					original = fileObj.getString("custName");
					url = fileObj.getString("serverPath");
					title = original; 
					fileExt = this.getFileExt(original);
					size = fileObj.getString("size");
				}
				json.put("original", original);
				json.put("url", url);
				json.put("title", title);
				json.put("fileType", fileExt);
				json.put("size", size);
				json.put("state", this.state);
			}else if(tagSource.equals("kdeditor")){/*KindEditor冨文本编辑器上传*/
				String url = null;
				if(null != jsonArr && jsonArr.size()>0){
					JSONObject fileObj = jsonArr.getJSONObject(0);
					url = fileObj.getString("serverPath");
				}
				json.put("error", 0);
				json.put("url", url);
			}
		}
		jsonStr = json.toJSONString();
		out.write(jsonStr);
	}
	
	/**
	 * 获取文件扩展名
	 * 
	 * @return string
	 */
	private String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}
	private void saveAttachments(HttpServletRequest request,JSONArray arr)throws ServiceException{
		if(null == arr || arr.size() == 0) return;
		String isSaveStr = request.getParameter("isSave");
		if(!StringHandler.isValidStr(isSaveStr)) return;
		boolean isSave = Boolean.parseBoolean(isSaveStr);
		if(!isSave) return;
		String sysId = request.getParameter("sysId");
		String formType = request.getParameter("formType");
		String formId = request.getParameter("formId");
		if(!StringHandler.isValidStr(sysId)) throw new ServiceException("sysId 不能为空!");
		if(!StringHandler.isValidStr(formType)) throw new ServiceException("formType 不能为空!");
		if(!StringHandler.isValidStr(formId)) throw new ServiceException("formId 不能为空!");
		UserEntity user = getUser(request);
		attachmentTempService = (AttachmentService)ctx.getBean("attachmentService");
		int count = arr.size(); 
		for(int i=0; i<count; i++){
			JSONObject obj = arr.getJSONObject(i);
			//custName,serverPath,size
			String custName = obj.getString("custName");
			String serverPath = obj.getString("serverPath");
			double fileSize = obj.getDoubleValue("size");
			AttachmentEntity entity = new AttachmentEntity();
			entity.setSysId(Long.parseLong(sysId));
			entity.setFormType(Integer.parseInt(formType));
			entity.setFormId(formId);
			Integer atype = getAtype(custName);
			entity.setAtype(atype);
			entity.setFileName(custName);
			entity.setFilePath(serverPath);
			entity.setFileSize((long)fileSize);
			String creator = null;
//			if(isWebsiteUser){
//				BeanUtil.setCreateInfo(websiteUser, entity);
//				creator = websiteUser.getUserName();
//			}else{
				BeanUtil.setCreateInfo(user, entity);
				creator = user.getUserName();
//			}
			
//			list.add(entity);
			attachmentTempService.saveEntity(entity);
			obj.put("attachmentId", entity.getId());
			obj.put("fileName", entity.getFileName());
			obj.put("creator", creator);
			obj.put("createTime", DateUtil.dateFormatToStr(entity.getCreateTime()));
		}
//		attachmentTempService.batchSaveEntitys(list);
	}
	
	private UserEntity getUser(HttpServletRequest request) throws NumberFormatException, ServiceException{
		HttpSession session = request.getSession(false);
		UserEntity user = (UserEntity)session.getAttribute(SysConstant.USER_KEY);
		if(null == user){
			String userId = request.getParameter("CURRENT_USERID");
			if(!StringHandler.isValidStr(userId)) return null;
			user = UserCache.getUser(Long.parseLong(userId));
			if(null != user)session.setAttribute(SysConstant.USER_KEY, user);
		}
		return user;
	}
	
	private Integer getAtype(String fileName){
		int atype = 10;
		if(!StringHandler.isValidStr(fileName)) return atype;
		fileName = fileName.toLowerCase();
		if(fileName.endsWith(".doc") || fileName.endsWith(".docx")){
			atype = 0;
		}else if(fileName.endsWith(".xls") || fileName.endsWith(".xlsx")){
			atype = 1;
		}else if(fileName.endsWith(".xls") || fileName.endsWith(".xlsx")){
			atype = 1;
		}else if(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")
				 || fileName.endsWith(".png") || fileName.endsWith(".bmp")|| fileName.endsWith(".ico")){
			atype = 2;
		}else if(fileName.endsWith(".zip") || fileName.endsWith(".rar") || fileName.endsWith(".7z")){
			atype = 3;
		}else if(fileName.endsWith(".xml")){
			atype = 4;
		}else if(fileName.endsWith(".pdf")){
			atype = 5;
		}
		return atype;
	}
	
	/**
	 * 开始上传方法
	 * @param request
	 * @throws Exception 
	 */
	public JSONArray startUpload(List<FileItem> items,String path,String absPath) throws Exception{
			JSONArray jsonArr = new JSONArray(items.size());
			try{
				for(FileItem item : items){
					if(item.isFormField()) continue;
					String custName = item.getName();
					if (!this.checkFileType(custName)) {
						this.state = this.errorInfo.get("TYPE");
						continue;
					}
					String fileName = createFileName(custName);
					String newFileName = path+fileName;
					double size = item.getSize();//1024; //--> 转 KB//
					File newFile = new File(absPath,fileName);
					item.write(newFile);
					
					JSONObject json = new JSONObject();
					json.put("custName", custName);
					json.put("serverPath", newFileName);
					json.put("size", size);
					jsonArr.add(json);
					this.state = this.errorInfo.get("SUCCESS");
				}
			} catch (SizeLimitExceededException e) {
				this.state = this.errorInfo.get("SIZE");
			} catch (InvalidContentTypeException e) {
				this.state = this.errorInfo.get("ENTYPE");
			} catch (FileUploadException e) {
				this.state = this.errorInfo.get("REQUEST");
			} catch (Exception e) {
				this.state = this.errorInfo.get("UNKNOWN");
			}
			return jsonArr;
	}

	private List<FileItem> getFileItems(HttpServletRequest request)
			throws FileUploadException {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		//通过 ServletFileUpload 获取是否是上传表单域
		boolean isMeltipart = ServletFileUpload.isMultipartContent(request);
		if(!isMeltipart){
			this.state = this.errorInfo.get("NOFILE");
			return null; //如果不是则返回
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		@SuppressWarnings("unchecked")
		List<FileItem> items = upload.parseRequest(request);
		return items;
	}
	
	/**
	 * 以年月日时分秒作为文件名
	 * @param custName 客户端文件名
	 * @return
	 */
	public String createFileName(String custName){
		System.currentTimeMillis();
		//String fileName = StringHandler.dateFormatToStr("yyyyMMddkkmmss", new Date());/*多文件上传时文件名会重复*/
		String fileName = System.nanoTime()+"";/*用毫微秒作为文件名*/
		fileName += custName.substring(custName.lastIndexOf("."));
		return fileName;
	}
	
	/**
	 * 文件类型判断
	 * 
	 * @param fileName
	 * @return
	 */
	private boolean checkFileType(String fileName) {
		if(null == this.allowFiles || this.allowFiles.length == 0) return true;
		Iterator<String> type = Arrays.asList(this.allowFiles).iterator();
		while (type.hasNext()) {
			String ext = type.next();
			if (fileName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public String getUplodDir(HttpServletRequest request,List<FileItem> items){
		String dir = request.getParameter("dir");
		if(!StringHandler.isValidStr(dir)){
			for(FileItem item : items){
				if(item.isFormField()){
					String fieldName = item.getFieldName();
					if(StringHandler.isValidStr(fieldName) && fieldName.equals("dir")){
						dir = item.getString();
					}
					if(StringHandler.isValidStr(fieldName) && fieldName.equals("allowFiles")){
						String allowFilesStr = item.getString();
						setAllowFiles(allowFilesStr);
					}
				}
			}
		}
		
		if(!StringHandler.isValidStr(dir)){
			dir = "pic_path";
		}
		dir = StringHandler.GetResValue(dir);
		//构造目录
		Calendar c = Calendar.getInstance();
		dir += c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/";
		
		return dir;
	}
	
}