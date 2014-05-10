package com.cmw.action.sys;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.AttachmentEntity;
import com.cmw.entity.sys.MatResultEntity;
import com.cmw.service.inter.sys.AttachmentService;
import com.cmw.service.inter.sys.MatResultService;


/**
 * 资料确认结果  ACTION类
 * @author pdt
 * @date 2012-12-26T00:00:00
 */
@Description(remark="资料确认结果ACTION",createDate="2012-12-26T00:00:00",author="pdt",defaultVals="sysMatResult_")
@SuppressWarnings("serial")
public class MatResultAction extends BaseAction {
	@Resource(name="matResultService")
	private MatResultService matResultService;
	
	@Resource(name="attachmentService")
	private AttachmentService attachmentService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 资料确认结果 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = matResultService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取 资料确认结果 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			Long formId = getLVal("formId");
			if(!StringHandler.isValidObj(formId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("formId", formId);
			params.put("isenabled", SysConstant.OPTION_ENABLED);
			List<MatResultEntity> listentity= matResultService.getEntityList(params);
			JSONArray subjectArr = new JSONArray();
			if(!listentity.isEmpty()&& listentity.size()>0){
				for(MatResultEntity entity : listentity){
					params.clear();
					String formId2 = entity.getFormId2();
					String result = entity.getResult();
					String remark = entity.getRemark();
					params.put("formId2", formId2);
					params.put("formId", formId.toString());
					params.put("isenabled", SysConstant.OPTION_ENABLED);
					List<AttachmentEntity> AttachmentEntityList= attachmentService.getEntityList(params);
					Integer size = AttachmentEntityList.size();
					String returnsize  = null;
					if(StringHandler.isValidObj(size) && size!=0){
						 returnsize  = formId2+"##"+size;
					}
					JSONObject obj = new JSONObject();
					obj.put("size", returnsize);
					obj.put("formId2", formId2);
					obj.put("result", result);
					obj.put("remark", remark);
					subjectArr.add(obj);
				}
			}
			result = subjectArr.toJSONString();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 保存 资料确认结果 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			MatResultEntity entity = BeanUtil.copyValue(MatResultEntity.class,getRequest());
			String idss = getVal("ids");
			String remarks = getVal("remarks");
			String results = getVal("results");
			String formIds2 = getVal("formIds2");
			Long sysId = getLVal("sysId");
			Long formId = getLVal("formId");
			StringBuffer formIdsSb = new StringBuffer();
//			if(!StringHandler.isValidObj(formIds2)){
				if(StringHandler.isValidStr(results)){
					if(results.indexOf(",")!=-1){
						String[] resultsArrray = results.split(",");
						for(String x : resultsArrray){
							if(StringHandler.isValidStr(x)){
								if(x.indexOf("##")!=-1){
									String[] xArray = x.split("##");
									if(formIdsSb.indexOf(xArray[0]) == -1){
										formIdsSb.append(xArray[0]+",");
									}
								}
							}
						}
					}else{
						getfromId2(results, formIdsSb);
					}
				}
				if(StringHandler.isValidStr(remarks)) {
					if(remarks.indexOf("☆")!=-1){
						String[] resultsArrray = remarks.split("☆");
						for(String x : resultsArrray){
							if(StringHandler.isValidStr(x)){
								if(x.indexOf("##")!=-1){
									String[] xArray = x.split("##");
									if(formIdsSb.indexOf(xArray[0]) == -1){
										formIdsSb.append(xArray[0]+",");
									}
								}
							}
						}
					}else{
						getfromId2(remarks, formIdsSb);
					}
				}
				formIds2 = StringHandler.RemoveStr(formIdsSb);
				SHashMap<String, Object> params = new SHashMap<String, Object>();
				params.put("formId2", SqlUtil.LOGIC_IN+SqlUtil.LOGIC+formIds2);
				params.put("sysId", sysId);
				params.put("formId", formId);
				matResultService.deleteEntitys(params);
//			}
			
			
			String[] isNotUpdata = idss.split(",");
			String[] remark = null;
			if(StringHandler.isValidStr(remarks) && remarks.indexOf("☆")!=-1){
				remark = remarks.split("☆");
			}
		
			String[] rest1 = results.split(",");
			String[] formId2 = formIds2.split(",");
			/*---->查看是否有更新的FormId2,若有则formId2从删除<----*/
			ArrayList<String> list = new ArrayList<String>();
			for(String x : formId2){
					list.add(x);
			}
//			if(isNotUpdata.length>0){
//				for(int i=0;i<isNotUpdata.length;i++){
//					for(int j =0;j<list.size();j++){
//						if(isNotUpdata[i].equals(list.get(j))){
//							list.remove(list.get(j));
//						}
//					}
//				}
//			}
			ArrayList<MatResultEntity> ListEntiy = new ArrayList<MatResultEntity>();
			Collection<MatResultEntity> updateEntiy = new ArrayList<MatResultEntity>();
			int i = 0;
			SHashMap<String, Object> append = new SHashMap<String, Object>();
			/*------------->既要更新一部分，又要保存一部分<----------*/
			if(list.size()>0&& !list.isEmpty()){
				i = addsave(entity, remark, rest1, list, ListEntiy, i);
				/*-------->更新部分<----------*/
				
//				for(String x : isNotUpdata){
//					append.put("formId2", x);
//					updateEntiy.addAll(matResultService.getEntityList(append));
//				}
//				if(updateEntiy.size()>0 && !updateEntiy.isEmpty()){
//					i = getsave(remark, rest1, i, updateEntiy);
//				}
				ListEntiy.addAll(updateEntiy);
			}else{ /*--->更新保存新添加的材料<---*/
				for(String x : formId2){
						if(StringHandler.isValidStr(x)){
							list.add(x);
							append.put("formId2", x);
						}
				}
				updateEntiy.addAll(matResultService.getEntityList(append));
				if(!updateEntiy.isEmpty() && updateEntiy.size()>0){
					i = getsave(remark, rest1, i, updateEntiy);
					ListEntiy.addAll(updateEntiy);
				}else{
					i = addsave(entity, remark, rest1, list, ListEntiy, i);
				}
			}
			matResultService.batchSaveOrUpdateEntitys(ListEntiy);
			StringBuffer ids = new StringBuffer();
			for(MatResultEntity idsEntity: ListEntiy){
				String Fid2 = idsEntity.getFormId2().toString();
				ids.append(Fid2+",");
			}
			String saveAfterIds  = StringHandler.RemoveStr(ids);
			HashMap<String, Object> appParams = new HashMap<String, Object>();
			appParams.put("saveAfterIds", saveAfterIds);
			result = ResultMsg.getSuccessMsg(appParams);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}

	/**
	 * @param remarks
	 * @param formIdsSb
	 */
	private void getfromId2(String remarks, StringBuffer formIdsSb) {
		if(remarks.indexOf("##")!=-1){
			String[] resultsArrray = remarks.split(",");
			for(String x : resultsArrray){
				if(StringHandler.isValidStr(x)){
					if(x.indexOf("##")!=-1){
						String[] xArray = x.split("##");
						if(formIdsSb.indexOf(xArray[0]) == -1){
							formIdsSb.append(xArray[0]+",");
						}
					}
				}
			}
		}
	}

	/**
	 * @param remark
	 * @param rest1
	 * @param i
	 * @param updateEntiy
	 * @return
	 */
	public int getsave(String[] remark, String[] rest1, int i,
			Collection<MatResultEntity> updateEntiy) {
		for(MatResultEntity updateentiy :updateEntiy ){
			String fId2 = updateentiy.getFormId2();
			StringBuffer resultId = new StringBuffer();
			for(String x:  rest1){
				String[] rest2 =  x.split("##");
					if(rest2[0].equals(fId2)){
						resultId.append(rest2[1]+",");
					}
			}
			StringBuffer remarkss = new StringBuffer();
			if(StringHandler.isValidObj(remark) && remark.length>0){
				for(String y : remark){
					if(StringHandler.isValidStr(y) && y.indexOf("##")!=-1){
						String[] remark2 = y.split("##");
//						if(remark2[0].equals(fId2)){
							remarkss.append(remark2[1]+",");
//						}
					}
				}
			}
			String result = StringHandler.RemoveStr(resultId);
			String rmark = StringHandler.RemoveStr(remarkss);
			updateentiy.setResult(result);
			updateentiy.setRemark(rmark);
			i++;
		}
		return i;
	}

	/**
	 * @param entity
	 * @param remark
	 * @param rest1
	 * @param list
	 * @param ListEntiy
	 * @param i
	 * @return
	 */
	public int addsave(MatResultEntity entity, String[] remark, String[] rest1,
			ArrayList<String> list, ArrayList<MatResultEntity> ListEntiy, int i) {
		for(String z : list){
			MatResultEntity listentiy = (MatResultEntity) entity.clone();
			ListEntiy.add(listentiy);
			ListEntiy.get(i).setFormId2(z);
			StringBuffer resultId = new StringBuffer();
			StringBuffer remarkss = new StringBuffer();
			for(String x:  rest1){
				String[] rest2 =  x.split("##");
					if(rest2[0].equals(z)){
						resultId.append(rest2[1]+",");
					}
			}
			
			if(StringHandler.isValidObj(remark) && remark.length>0){
				for(String y : remark){
					if(StringHandler.isValidStr(y) && y.indexOf("##")!=-1){
						String[] remark2 = y.split("##");
						if(remark2[0].equals(z)){
							remarkss.append(remark2[1]+",");
						}
					}
				}
			}
			String result = StringHandler.RemoveStr(resultId);
			String rmark = StringHandler.RemoveStr(remarkss);
			ListEntiy.get(i).setResult(result);
			ListEntiy.get(i).setRemark(rmark);
			i++;
		}
		return i;
	}
	
	
	/**
	 * 新增  资料确认结果 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = matResultService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("M", num);
			result = JsonUtil.getJsonString("code",code);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 删除  资料确认结果 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  资料确认结果 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  资料确认结果 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  资料确认结果 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			matResultService.enabledEntitys(ids, isenabled);
			result = ResultMsg.getSuccessMsg(this,sucessMsg);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取指定的 资料确认结果 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			MatResultEntity entity = matResultService.navigationPrev(params);
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
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取指定的 资料确认结果 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			MatResultEntity entity = matResultService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
				result = ResultMsg.getSuccessMsg(entity, appendParams);
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
}
