package com.cmw.entity.sys;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 消息通知
 * @author pdh
 * @date 2013-10-08T00:00:00
 */
@Description(remark="消息通知实体",createDate="2013-10-08T00:00:00",author="pdh")
@Entity
@Table(name="ts_MsgNotify")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class MsgNotifyEntity extends IdBaseEntity {
	
	
	 @Description(remark="业务类型")
	 @Column(name="bussType" ,nullable=false )
	 private Integer bussType;

	 @Description(remark="消息通知类型")
	 @Column(name="notifysType" ,nullable=false ,length=10 )
	 private String notifysType;

	 @Description(remark="手机号码")
	 @Column(name="phoneNum" ,length=20 )
	 private String phoneNum;

	 @Description(remark="Email")
	 @Column(name="email" ,length=50 )
	 private String email;

	 @Description(remark="电话号码")
	 @Column(name="telNum" ,length=20 )
	 private String telNum;

	 @Description(remark="通知发送人")
	 @Column(name="notifyMan" ,nullable=false )
	 private Long notifyMan;

	 @Description(remark="通知日期")
	 @Column(name="notifyDate" ,nullable=false )
	 private Date notifyDate;

	 @Description(remark="接收人")
	 @Column(name="receiveMan" ,nullable=false ,length=150 )
	 private String receiveMan;

	 @Description(remark="通知内容")
	 @Column(name="content" ,nullable=false ,length=1500 )
	 private String content;

	 @Description(remark="消息状态")
	 @Column(name="status" ,nullable=false )
	 private Integer status = 0;

	 @Description(remark="接收人类型")
	 @Column(name="notifySendMan" ,nullable=false )
	 private Integer notifySendMan = 1;
	 
	 @Description(remark="通知保存后，由系统自动发送")
	 @Column(name="autoSend" ,nullable=false )
	 private Integer autoSend = 1;
	 

	public MsgNotifyEntity() {

	}

	
	/**
	 * @return the autoSend
	 */
	public Integer getAutoSend() {
		return autoSend;
	}


	/**
	 * @param autoSend the autoSend to set
	 */
	public void setAutoSend(Integer autoSend) {
		this.autoSend = autoSend;
	}


	/**
	  * 设置业务类型的值
	 * @param 	bussType	 业务类型
	**/
	public void setBussType(Integer  bussType){
		 this.bussType=bussType;
 	}

	/**
	  * 获取业务类型的值
	 * @return 返回业务类型的值
	**/
	public Integer getBussType(){
		 return bussType;
 	}

	/**
	  * 设置消息通知类型的值
	 * @param 	notifysType	 消息通知类型
	**/
	public void setNotifysType(String  notifysType){
		 this.notifysType=notifysType;
 	}

	/**
	  * 获取消息通知类型的值
	 * @return 返回消息通知类型的值
	**/
	public String getNotifysType(){
		 return notifysType;
 	}

	/**
	  * 设置手机号码的值
	 * @param 	phoneNum	 手机号码
	**/
	public void setPhoneNum(String  phoneNum){
		 this.phoneNum=phoneNum;
 	}

	/**
	  * 获取手机号码的值
	 * @return 返回手机号码的值
	**/
	public String getPhoneNum(){
		 return phoneNum;
 	}

	/**
	  * 设置Email的值
	 * @param 	email	 Email
	**/
	public void setEmail(String  email){
		 this.email=email;
 	}

	/**
	  * 获取Email的值
	 * @return 返回Email的值
	**/
	public String getEmail(){
		 return email;
 	}

	/**
	  * 设置电话号码的值
	 * @param 	telNum	 电话号码
	**/
	public void setTelNum(String  telNum){
		 this.telNum=telNum;
 	}

	/**
	  * 获取电话号码的值
	 * @return 返回电话号码的值
	**/
	public String getTelNum(){
		 return telNum;
 	}

	/**
	  * 设置通知发送人的值
	 * @param 	notifyMan	 通知发送人
	**/
	public void setNotifyMan(Long  notifyMan){
		 this.notifyMan=notifyMan;
 	}

	/**
	  * 获取通知发送人的值
	 * @return 返回通知发送人的值
	**/
	public Long getNotifyMan(){
		 return notifyMan;
 	}

	/**
	  * 设置通知日期的值
	 * @param 	notifyDate	 通知日期
	**/
	public void setNotifyDate(Date  notifyDate){
		 this.notifyDate=notifyDate;
 	}

	/**
	  * 获取通知日期的值
	 * @return 返回通知日期的值
	**/
	public Date getNotifyDate(){
		 return notifyDate;
 	}

	/**
	  * 设置接收人的值
	 * @param 	receiveMan	 接收人
	**/
	public void setReceiveMan(String  receiveMan){
		 this.receiveMan=receiveMan;
 	}

	/**
	  * 获取接收人的值
	 * @return 返回接收人的值
	**/
	public String getReceiveMan(){
		 return receiveMan;
 	}

	/**
	  * 设置通知内容的值
	 * @param 	content	 通知内容
	**/
	public void setContent(String  content){
		 this.content=content;
 	}

	/**
	  * 获取通知内容的值
	 * @return 返回通知内容的值
	**/
	public String getContent(){
		 return content;
 	}

	/**
	  * 设置消息状态的值
	 * @param 	status	 消息状态
	**/
	public void setStatus(Integer  status){
		 this.status=status;
 	}

	/**
	  * 获取消息状态的值
	 * @return 返回消息状态的值
	**/
	public Integer getStatus(){
		 return status;
 	}


	/**
	  * 设置是否通知发送客户的值
	 * @param 	notifySendMan	 是否通知发送客户
	**/
	public void setNotifySendMan(Integer  notifySendMan){
		 this.notifySendMan=notifySendMan;
 	}

	/**
	  * 获取是否通知发送客户的值
	 * @return 返回是否通知发送客户的值
	**/
	public Integer getNotifySendMan(){
		 return notifySendMan;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,bussType,notifysType,phoneNum,email,telNum,notifyMan,notifyDate,receiveMan,content,status,notifySendMan,autoSend};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","bussType","notifysType","phoneNum","email","telNum","notifyMan","notifyDate","receiveMan","content","status","notifySendMan","autoSend"};
	}

}
