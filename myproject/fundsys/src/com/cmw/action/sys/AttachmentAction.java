package com.cmw.action.sys;


import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.kit.file.FileUtil;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.AttachmentEntity;
import com.cmw.service.inter.sys.AttachmentService;


/**
 * 附件  ACTION类
 * @author 程明卫
 * @date 2012-12-04T00:00:00
 */
@Description(remark="附件ACTION",createDate="2012-12-04T00:00:00",author="程明卫",defaultVals="sysAttachment_")
@SuppressWarnings("serial")
public class AttachmentAction extends BaseAction {
	@Resource(name="attachmentService")
	private AttachmentService attachmentService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 附件 列表
	 * @return	
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("formId2", getVal("formId2"));
			map.put("formId", getVal("formId"));
			map.put("formType", getIVal("formType"));
//			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = attachmentService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	/**
	 * 获取 资料清单 列表
	 * @return	
	 * @throws Exception
	 */
	public String listMat()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("formId2", getVal("formId2"));
			DataTable dt = attachmentService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	/**
	 * 获取 附件项 列表
	 * @return	
	 * @throws Exception
	 */
	public String items()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("sysId#L,formType#I");
			Long formId = getLVal("formId");
			if(formId.longValue()==-1 || !StringHandler.isValidObj(formId)){
				result = ResultMsg.NODATA;
				outJsonString(result);
				return null;
			}
			map.put("formId", formId);
//			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = attachmentService.getResultList(map);
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 更新附件
	 */
	public String update() throws Exception {
		try {
			Long sysId = getLVal("sysId");
			Integer old_formType = getIVal("old_formType");
			Long old_formId = getLVal("old_formId");
			Integer formType = getIVal("formType");
			String formId = getVal("formId");
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put(SysConstant.USER_KEY, this.getCurUser());
			params.put("sysId", sysId);
			params.put("formType", old_formType);
			params.put("formId", old_formId);
			List<AttachmentEntity> list = attachmentService.getEntityList(params);
			if(null != list && list.size() > 0){
				for(AttachmentEntity entity : list){
					entity.setFormType(formType);
					entity.setFormId(formId);
				}
				attachmentService.batchUpdateEntitys(list);
			}
			result = ResultMsg.getSuccessMsg();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取 附件 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			AttachmentEntity entity = attachmentService.getEntity(Long.parseLong(id));
			result = JsonUtil.toJson(entity);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 保存 附件 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			AttachmentEntity entity = BeanUtil.copyValue(AttachmentEntity.class,getRequest());
			attachmentService.saveOrUpdateEntity(entity);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 新增  附件 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = attachmentService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("A", num);
			result = JsonUtil.getJsonString("code",code);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 删除  附件 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  附件 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  附件 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  附件 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			attachmentService.enabledEntitys(ids, isenabled);
			result = ResultMsg.getSuccessMsg(this,sucessMsg);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取指定的 附件 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			AttachmentEntity entity = attachmentService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getFirstMsg(appendParams);
			}else{
				result = ResultMsg.getSuccessMsg(entity, appendParams);
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取指定的 附件 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			AttachmentEntity entity = attachmentService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
				result = ResultMsg.getSuccessMsg(entity, appendParams);
			}
		}catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 下载附件
	 * @return
	 * @throws Exception
	 */
	public String download() throws Exception {
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		FileUtil util = new FileUtil();
		try{
			  request.setCharacterEncoding("UTF-8");
			  Long id = getLVal("id");
			  if(!StringHandler.isValidObj(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			  AttachmentEntity entity = attachmentService.getEntity(id);
			  if(!StringHandler.isValidObj(entity)) throw new ServiceException(ServiceException.OBJECT_IS_NULL);
			  String fileName = entity.getFileName();
			  String filePath = entity.getFilePath();
			  filePath = FileUtil.getFilePath(request, filePath);
			  if(!StringHandler.isValidStr(filePath)) throw new ServiceException("文件下载路径：["+filePath+"] 不存在!");
			
			  File f = new File(filePath);
			  if(!f.exists()) throw new ServiceException("文件["+fileName+"] 不存在，无法下载!");
			  response.setHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(fileName, "utf-8") + "\"");
			  response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
			  response.setHeader("Pragma","no-cache"); //HTTP 1.0		
			  response.setContentType("application/x-download");//设置为下载application/x-download
			  response.setStatus(HttpServletResponse.SC_OK); //200
			  OutputStream out = response.getOutputStream();  
			  out.write(util.readFile(f));
			  out.close();
		}catch (ServiceException ex){
			//result = ex.getMessage();
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			outErrMsg(result);
			ex.printStackTrace();
		}catch(Exception ex){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			ex.printStackTrace();
		}
		return null;
	}
	
	private void outErrMsg(String errMsg){
		errMsg = "<html><body><div style='color:red;font-weight:18px;text-align:center;'>" +
				"下载出错!<br/>错误原因："+errMsg
				+"</div></body></html>";
		getResponse().setCharacterEncoding("UTF-8");  
		getResponse().setContentType("text/html"); 
		outString(errMsg);
	}
}
