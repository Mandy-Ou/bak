package com.cmw.action.finance;


import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
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
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.MinAmountEntity;
import com.cmw.service.inter.finance.MinAmountService;


/**
 * 最低金额配置  ACTION类
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark="最低金额配置ACTION",createDate="2013-02-28T00:00:00",author="程明卫",defaultVals="fcMinAmount_")
@SuppressWarnings("serial")
public class MinAmountAction extends BaseAction {
	@Resource(name="minAmountService")
	private MinAmountService minAmountService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 最低金额配置 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("status#I,startDate,endDate,creatorMan");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = minAmountService.getResultList(map,getStart(),getLimit());
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
	 * 检查生效日期是否合法
	 * @return
	 * @throws Exception
	 */
	public String validOpdate()throws Exception {
		try {
			boolean success = true;
			String msg = null;
			Long id = getLVal("id");
			String opdate = getVal("opdate");
			if(!StringHandler.isValidStr(opdate)){
				success = false;
				msg = "生效日期不能为空!";
			} 
			Date tempDate = DateUtil.dateFormat(opdate);
			Date today = DateUtil.dateFormat(new Date());
			if(today.compareTo(tempDate) >= 0){
				success = false;
				msg = "生效日期必须大于"+ DateUtil.dateFormatToStr(today)+"!";
			}
			if(success){
				SHashMap<String, Object> map = new SHashMap<String, Object>();
				map.put("isenabled", SqlUtil.LOGIC_NOT_EQ + SqlUtil.LOGIC + SysConstant.OPTION_DEL);
				List<MinAmountEntity> list = minAmountService.getEntityList(map);
				if(null != list && list.size() > 0){
					for(MinAmountEntity entity : list){
						Date _opDate = entity.getOpdate();
						if(StringHandler.isValidObj(id) && entity.getId().equals(id)) continue;
						_opDate = DateUtil.dateFormat(_opDate);
						int result = _opDate.compareTo(tempDate);
						if(result == 0){
							success = false;
							msg = "生效日期："+opdate+" 不能重复 !";
							break;
						}
					}
				}
			}
			Map<String,Object> appendParams = new HashMap<String, Object>();
			appendParams.put("success", success);
			appendParams.put("msg", msg);
			result = FastJsonUtil.convertMapToJsonStr(appendParams);
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
	 * 获取 最低金额配置 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			MinAmountEntity entity = minAmountService.getEntity(Long.parseLong(id));
			result = FastJsonUtil.convertJsonToStr(entity,getJsonCallback());
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
	
	private Callback getJsonCallback(){
		return new Callback(){
			public void execute(JSONObject jsonObj) {
				Timestamp opdate = jsonObj.getTimestamp("opdate");
				if(null != opdate) jsonObj.put("opdate", DateUtil.dateFormatToStr(opdate));
			}
		};
	}
	
	/**
	 * 提交/审批通过/取消审批 最低金额配置 
	 * @return
	 * @throws Exception
	 */
	public String audit()throws Exception {
		try {
			Long id = getLVal("id");
			Integer status = getIVal("status");
			String adresult = getVal("adresult");
			MinAmountEntity entity = minAmountService.getEntity(id);
			entity.setStatus(status);
			if(null != status && status.intValue() != BussStateConstant.MINAMOUNT_STATUS_1){
				entity.setAdate(new Date());
				entity.setAdresult(adresult);
			}
			BeanUtil.setModifyInfo(getCurUser(), entity);
			minAmountService.updateEntity(entity);
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
	 * 保存 最低金额配置 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			MinAmountEntity entity = BeanUtil.copyValue(MinAmountEntity.class,getRequest());
			minAmountService.saveOrUpdateEntity(entity);
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
	 * 新增  最低金额配置 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = minAmountService.getMaxID();
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
	 * 删除  最低金额配置 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  最低金额配置 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  最低金额配置 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  最低金额配置 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			minAmountService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 最低金额配置 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			MinAmountEntity entity = minAmountService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getFirstMsg(appendParams);
			}else{
				result = FastJsonUtil.convertJsonToStr(entity,getJsonCallback());
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
	 * 获取指定的 最低金额配置 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			MinAmountEntity entity = minAmountService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
				result = FastJsonUtil.convertJsonToStr(entity,getJsonCallback());
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
