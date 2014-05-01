package com.cmw.service.impl.sys;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.kit.email.SendMail;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;

import com.cmw.entity.sys.AuditRecordsEntity;
import com.cmw.entity.sys.MsgNotifyEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.dao.inter.sys.MsgNotifyDaoInter;
import com.cmw.service.impl.workflow.BussFlowService;
import com.cmw.service.inter.sys.MsgNotifyService;
import com.cmw.service.inter.sys.UserService;


/**
 * 消息通知  Service实现类
 * @author pdh
 * @date 2013-10-08T00:00:00
 */
@Description(remark="消息通知业务实现类",createDate="2013-10-08T00:00:00",author="pdh")
@Service("msgNotifyService")
public class MsgNotifyServiceImpl extends AbsService<MsgNotifyEntity, Long> implements  MsgNotifyService {
	@Autowired
	private MsgNotifyDaoInter msgNotifyDao;
	@Resource(name="userService")
	private UserService userService;
	@Override
	public GenericDaoInter<MsgNotifyEntity, Long> getDao() {
		return msgNotifyDao;
	}

	/**
	 * 向下一审批人发送通知
	 * @param notifyTypes
	 * @param actorIds
	 * @throws ServiceException 
	 */
	@Override
	public void sendNotify(Map<String,Object> submitDatas,JSONArray nextTrans) throws ServiceException{
		if(null == nextTrans || nextTrans.size() == 0) return;
		StringBuffer sb = new StringBuffer();
		for(int i=0,count=nextTrans.size(); i<count; i++){
			JSONObject nextTran = nextTrans.getJSONObject(i);
			String actorId = nextTran.getString("actorId");
			if(!StringHandler.isValidStr(actorId)) continue;
			if(actorId.equals(BussFlowService.DEL_ASSIGNEEID)) continue;
			sb.append(actorId).append(",");
		}
		String actorIds = StringHandler.RemoveStr(sb);
		if(!StringHandler.isValidStr(actorIds)) return;
		final AuditRecordsEntity recordEntity = (AuditRecordsEntity)submitDatas.get("recordEntity");
		//final AuditAmountEntity auditAmountEntity = (AuditAmountEntity)submitDatas.get("auditAmountEntity");
		UserEntity currUser = (UserEntity)submitDatas.get(SysConstant.USER_KEY);
		String notifyTypes = recordEntity.getNotifys();
		if(!StringHandler.isValidStr(notifyTypes)) return;
		List<UserEntity> nextUsers = null;
		
		if(!StringHandler.isValidStr(actorIds)) return;
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("userId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + actorIds);
		nextUsers = userService.getEntityList(map);
		/* step 4 : 发送通知消息  */
		String[] types = notifyTypes.split(",");
		for(String ntype : types){
			if(Integer.parseInt(ntype) == SysConstant.NOTYFY_TYPE_PHONE_2){	/*手机短消息*/
				System.out.println("短消息发送未实现 ....... ");
			}else if(Integer.parseInt(ntype) == SysConstant.NOTYFY_TYPE_EMAIL_3){/*邮件短消息*/
				//System.out.println("邮件发送未实现 ....... ");
				sendMail(currUser,nextUsers,recordEntity);
			}
		}
	}
	
	/**
	 * 向下一审批人发送通知
	 * @param notifyTypes
	 * @param actorIds
	 * @throws ServiceException 
	 */
	@Override
	public void sendNotify(Map<String,Object> submitDatas,String actorIds) throws ServiceException{
		final AuditRecordsEntity recordEntity = (AuditRecordsEntity)submitDatas.get("recordEntity");
		//final AuditAmountEntity auditAmountEntity = (AuditAmountEntity)submitDatas.get("auditAmountEntity");
		UserEntity currUser = (UserEntity)submitDatas.get(SysConstant.USER_KEY);
		String notifyTypes = recordEntity.getNotifys();
		if(!StringHandler.isValidStr(notifyTypes)) return;
		List<UserEntity> nextUsers = null;
		if(!StringHandler.isValidStr(actorIds)) return;
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("userId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + actorIds);
		nextUsers = userService.getEntityList(map);
		/* step 4 : 发送通知消息  */
		String[] types = notifyTypes.split(",");
		for(String ntype : types){
			if(Integer.parseInt(ntype) == SysConstant.NOTYFY_TYPE_PHONE_2){	/*手机短消息*/
				System.out.println("短消息发送未实现 ....... ");
			}else if(Integer.parseInt(ntype) == SysConstant.NOTYFY_TYPE_EMAIL_3){/*邮件短消息*/
				//System.out.println("邮件发送未实现 ....... ");
				sendMail(currUser,nextUsers,recordEntity);
			}
		}
	}
	
	private void sendMail(UserEntity currUser, List<UserEntity> nextUsers, AuditRecordsEntity recordEntity){
		if(null == nextUsers || nextUsers.size() == 0) return;
		String userName = currUser.getEmpName();
		if(!StringHandler.isValidStr(userName)) userName = currUser.getUserName();
		String approval = recordEntity.getApproval();
		List<String> emailList = new ArrayList<String>();
		List<String> displayNameList = new ArrayList<String>();
		for(UserEntity nextUser : nextUsers){
			String actorName = nextUser.getEmpName();
			if(!StringHandler.isValidStr(actorName)) actorName = nextUser.getUserName();
			String email = nextUser.getEmail();
			if(!StringHandler.isValidStr(email)) continue;
			emailList.add(email);
			displayNameList.add(actorName);
		}
		if(null != emailList && emailList.size() > 0){
			String msg = "你好!上一任务节点审批人<span style='color:red;font-weight:bold'>"+userName+" </span>完成了其审批任务，你是下一任务节点的审批人，请及时登录系统进行审批！" +
					"<br><span style='color:red;font-weight:bold'>"+userName+"</span>给出的审批意见如下：" +
					"<div style='padding-left:30px;color:blue;'>"+approval+"</div>";
			String system_mail_accounts = StringHandler.GetResValue("system_mail_accounts");
	    	try {
	    		if(!StringHandler.isValidStr(system_mail_accounts)) throw new Exception("请在 resoruce.propterties 文件中配置邮件发送帐号，其格式如下：" +
	    				" system_mail_accounts = smtp.126.com,niujiangjun_server@126.com,niujiangjun_server,niujiangjun_2013 ");
				String[] mailAccounts = system_mail_accounts.split(",");
				SendMail mail = new SendMail(mailAccounts[0],mailAccounts[1],"系统邮件",mailAccounts[2],mailAccounts[3]);
	    		String[] reEmals = new String[1];
	    		reEmals = emailList.toArray(reEmals);
	    		String[] displayNames = new String[1];
	    		displayNames = displayNameList.toArray(displayNames);
	    		System.out.println("正在发送邮件，请等待 ....... ");
				mail.send(reEmals,  displayNames,  "任务审批",msg);
				System.out.println("邮件发送成功....... ");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("邮件发送失败....... ");
			}
		}
	}
}
