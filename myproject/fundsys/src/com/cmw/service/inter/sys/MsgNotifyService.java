package com.cmw.service.inter.sys;


import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.entity.sys.MsgNotifyEntity;


/**
 * 消息通知  Service接口
 * @author pdh
 * @date 2013-10-08T00:00:00
 */
@Description(remark="消息通知业务接口",createDate="2013-10-08T00:00:00",author="pdh")
public interface MsgNotifyService extends IService<MsgNotifyEntity, Long> {
	/**
	 * (流程审批用到)向下一审批人发送通知
	 * @param submitDatas	流程提交的数据	
	 * @param nextTrans	下一流转方向
	 * @throws ServiceException 
	 */
	public void sendNotify(Map<String,Object> submitDatas,JSONArray nextTrans) throws ServiceException;
	
	/**
	 * 向下一审批人发送通知
	 * @param submitDatas	流程提交的数据	
	 * @param actorIds	消息接收人
	 * @throws ServiceException 
	 */
	public void sendNotify(Map<String,Object> submitDatas,String actorIds) throws ServiceException;
}
