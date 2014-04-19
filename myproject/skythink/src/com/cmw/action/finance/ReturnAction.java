package com.cmw.action.finance;

import javax.annotation.Resource;

import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.service.inter.finance.ReturnService;

/**
 * 息费返还管理action
 *Title: ReturnAction.java
 *@作者： 程明卫
 *@ 创建时间：2014-02-19 21:02:22
 *@ 公司：	同心日信科技有限公司
 */
@Description(remark="息费返还管理ACTION",createDate="2014-02-19 21:02:22",author="程明卫",defaultVals="fcReturn_")
@SuppressWarnings("serial")
public class ReturnAction extends BaseAction{
	@Resource(name="returnService")
	private ReturnService returnService;
	private String result = ResultMsg.GRID_NODATA;
	
	/**
	 * 获取息费返还列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("id#L,custType#I,name,code,accName,payBank,payAccount");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = returnService.getResultList(map,getStart(),getLimit());
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
	 * 息费返还数据保存
	 * @return
	 * @throws ServiceException
	 */
    public String save() throws ServiceException {
    	try {
    		String cmns = "bussType#I,contractId#L,rat,mat,pat,dat,rectDate,accountId#L," +
    				"key,isVoucher#I,vtempCode,sysId#L,accountId#L,rectDate,RANDOM_ALGORITHM";
    		SHashMap<String, Object> params  = getQParams(cmns);
    		Long contractId = params.getvalAsLng("contractId");
    		Integer bussType = params.getvalAsInt("bussType");
    		if(!StringHandler.isValidObj(contractId))  new ServiceException(ServiceException.ID_IS_NULL);
    		if(!StringHandler.isValidObj(bussType))  new ServiceException("params bussType is null !");
    		returnService.doComplexBusss(params);
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
}
