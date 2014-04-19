package com.cmw.action.sys;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.PrepaymentEntity;
import com.cmw.entity.sys.AuditRecordsEntity;
import com.cmw.entity.sys.MsgNotifyEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.finance.PrepaymentService;
import com.cmw.service.inter.sys.AuditRecordsService;
import com.cmw.service.inter.sys.MsgNotifyService;
import com.cmw.service.inter.sys.UserService;


/**
 * 消息通知  ACTION类
 * @author pdh
 * @date 2013-10-08T00:00:00
 */
@Description(remark="消息通知ACTION",createDate="2013-10-08T00:00:00",author="pdh",defaultVals="sysMsgNotify_")
@SuppressWarnings("serial")
public class MsgNotifyAction extends BaseAction {
	@Resource(name="msgNotifyService")
	private MsgNotifyService msgNotifyService;
	@Resource(name="prepaymentService")
	private PrepaymentService prepaymentService;
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="auditRecordsService")
	private AuditRecordsService auditRecordsService;
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 消息通知 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = msgNotifyService.getResultList(map,getStart(),getLimit());
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
	 * 获取 消息通知 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			Integer bussType = getIVal("bussType");
			Long applyId = getLVal("applyId");
			Long id = null;
			if(!StringHandler.isValidObj(applyId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			if(StringHandler.isValidObj(bussType) && bussType == BussStateConstant.MSGNOTIFY_BUSSTYPE_2){
				PrepaymentEntity entity = prepaymentService.getEntity(applyId);
				if(entity != null){
					id = entity.getNotifyMan();
				}
				result = FastJsonUtil.convertJsonToStr(entity);
			}else if(StringHandler.isValidObj(bussType) && bussType == BussStateConstant.MSGNOTIFY_BUSSTYPE_1){
				AuditRecordsEntity auditRecordsEntity = auditRecordsService.getEntity(applyId);
				/*这里要进行添加*/
			}
			
			if(!StringHandler.isValidObj(id)) {
				outJsonString("-1");
				return null;
			}
			 
			MsgNotifyEntity entity = msgNotifyService.getEntity(id);
			Long notifyMan = entity.getNotifyMan();
			String empName = "";
			if(StringHandler.isValidObj(notifyMan)){
				UserEntity user= userService.getEntity(notifyMan);
				String empname = user.getEmpName();
				empName = empname;
			}
			 Date notifyDate = entity.getNotifyDate();
			 String notifydate = "";
			 if(StringHandler.isValidObj(notifyDate)){
				 notifydate = DateUtil.dateFormatToStr(notifyDate);
			 }
			 Integer status = entity.getStatus();
			HashMap<String, Object> appParams  = new HashMap<String, Object>();
			appParams.put("notifyDate", notifyDate);
			if(StringHandler.isValidObj(status)){
				switch (status) {
				case BussStateConstant.MSGNOTIFY_STATUS_0:
					appParams.put("statusTxt", "未发送");
					break;
				case BussStateConstant.MSGNOTIFY_STATUS_1:
					appParams.put("statusTxt", "发送成功");
					break;
				case BussStateConstant.MSGNOTIFY_STATUS_2:
					appParams.put("statusTxt", "发送失败");
					break;
				}
			}
			appParams.put("notifyDate", notifydate);
			appParams.put("notifyMan", empName);
			result = ResultMsg.getSuccessMsg(entity,appParams);
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
	 * 保存 消息通知 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			Long formId = getLVal("formId");
			String autoSend = getVal("autoSend");
			if(!StringHandler.isValidObj(formId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			MsgNotifyEntity entity = BeanUtil.copyValue(MsgNotifyEntity.class,getRequest());
			msgNotifyService.saveOrUpdateEntity(entity);
			if(entity.getBussType() == BussStateConstant.MSGNOTIFY_BUSSTYPE_2){
				PrepaymentEntity prepaymentEntity =  prepaymentService.getEntity(formId);
				prepaymentEntity.setNotifyMan(entity.getId());
				prepaymentService.saveOrUpdateEntity(prepaymentEntity);
			}
			/*这里自动发送消息*/
			if(StringHandler.isValidStr(autoSend)){
				
			}
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
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
	 * 新增  消息通知 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = msgNotifyService.getMaxID();
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
	 * 删除  消息通知 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  消息通知 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  消息通知 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  消息通知 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			msgNotifyService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 消息通知 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			MsgNotifyEntity entity = msgNotifyService.navigationPrev(params);
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
	 * 获取指定的 消息通知 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			MsgNotifyEntity entity = msgNotifyService.navigationNext(params);
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
