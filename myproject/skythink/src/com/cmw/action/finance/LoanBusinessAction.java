package com.cmw.action.finance;

import javax.annotation.Resource;

import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.service.inter.finance.LoanBusinessService;

/**
 * 业务统计报表 
 *Title: LoanBusiness.java
 *@作者： 彭登浩
 *@ 创建时间：2013-12-6下午8:34:12
 *@ 公司：	同心日信科技有限公司
 */
@Description(remark="业务统计报表 ACTION",createDate="2013-08-05 20:10:00",author="彭登浩",defaultVals="fcLoanBusiness_")
@SuppressWarnings("serial")
public class LoanBusinessAction extends BaseAction {
	@Resource(name="loanBusinessService")
	private LoanBusinessService loanBusinessService;
	
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 业务统计报 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("startDate,sysId#L");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = loanBusinessService.getResultList(map);
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

}
