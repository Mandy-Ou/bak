package com.cmw.action.dialog;

import javax.annotation.Resource;

import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.service.inter.crm.CustBaseService;

/**
 * @author 程明卫 E-mail:chengmingwei_1984122@126.com
 * @version 创建时间：2010-6-15 下午12:14:31
 * 类说明 	弹出选择框  ACTION   
 */
@Description(remark="卡片菜单 ACTION",createDate="2010-6-15",defaultVals="dgDialogbox_")
@SuppressWarnings("serial")
public class DialogboxAction extends BaseAction {
	@Resource(name="custBaseService")
	private CustBaseService custBaseService;
	
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取个人/企业客户弹出Grid列表
	 * @return
	 * @throws Exception
	 */
	public String custlist()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("name,custType#I,sysId#L");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = custBaseService.getDialogResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getSize() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
}
