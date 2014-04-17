package com.cmw.action.finance;

import javax.annotation.Resource;

import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.service.inter.finance.LedgerDeatilService;

/**民汇明细报表
 *Title: LedgerDeatilAction.java
 *@作者： 彭登浩
 *@ 创建时间：2013-12-11上午11:54:03
 *@ 公司：	同心日信科技有限公司
 */

@Description(remark="明细报表ACTION",createDate="2013-01-15T00:00:00",author="彭登浩",defaultVals="fcLedgerDeatil_")
@SuppressWarnings("serial")
public class LedgerDeatilAction extends BaseAction {
	@Resource(name = "ledgerDeatilService")
	private LedgerDeatilService ledgerDeatilService;
	
	private String result = ResultMsg.GRID_NODATA;
	
	public String list() throws ServiceException{
		try {
			
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("sysId", getLVal("sysId"));
			DataTable dt = ledgerDeatilService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}

}
