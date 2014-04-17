package com.cmw.core.kit.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.AttachmentEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.sys.AttachmentService;

/**
 * Servlet implementation class for Servlet: UploadServlet1
 *	
 */
 public class UploadServlet_bak extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   private WebApplicationContext ctx = null;
   AttachmentService attachmentTempService = null;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public UploadServlet_bak() {
		super();
	}   	

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext sc = request.getSession().getServletContext();
		this.ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
		doUpload(request,response);
	}
	
	/**
	 * 文件上传方法
	 * @throws IOException 
	 */
	public void doUpload(HttpServletRequest request,HttpServletResponse response){
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
		
		PrintWriter out = null;
		String msg = "上传成功";
		boolean success = true;
		JSONArray jsonArr = null;
		try {
			jsonArr = startUpload(items, path, absPath);
			saveAttachments(request,jsonArr);
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/html");  
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			success = false;
			msg = "上传失败";
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
			msg = "上传失败";
		}
		
		JSONObject json = new JSONObject();
		json.put("success", success);
		json.put("msg", msg);
		json.put("fileInfos", jsonArr);
		String jsonStr = json.toJSONString();
		StringHandler.P("jsonStr="+jsonStr);
		out.write(jsonStr);
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
		UserEntity user =(UserEntity)request.getSession().getAttribute(SysConstant.USER_KEY);
		attachmentTempService = (AttachmentService)ctx.getBean("attachmentService");
		int count = arr.size(); 
//		List<AttachmentEntity> list = new ArrayList<AttachmentEntity>(count);
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
			BeanUtil.setCreateInfo(user, entity);
//			list.add(entity);
			attachmentTempService.saveEntity(entity);
			obj.put("attachmentId", entity.getId());
		}
//		attachmentTempService.batchSaveEntitys(list);
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
			for(FileItem item : items){
				if(item.isFormField()) continue;
				String custName = item.getName();
				String fileName = createFileName(custName);
				String newFileName = path+fileName;
				double size = item.getSize()/1024; //--> 转 KB//
				File newFile = new File(absPath,fileName);
				item.write(newFile);
				
				JSONObject json = new JSONObject();
				json.put("custName", custName);
				json.put("serverPath", newFileName);
				json.put("size", size);
				jsonArr.add(json);
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
		if(!isMeltipart) return null; //如果不是则返回
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
						break;
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