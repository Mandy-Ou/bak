package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 公司账户
 * @author 彭登浩
 * @date 2012-12-08T00:00:00
 */
@Description(remark="公司账户实体",createDate="2012-12-08T00:00:00",author="彭登浩")
@Entity
@Table(name="ts_account")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class AccountEntity extends IdBaseEntity {
	
	 @Description(remark="系统ID")
	 @Column(name="sysId" ,nullable=false)
	 private Long sysId;
	 
	 @Description(remark="科目编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;



	 @Description(remark="银行名称")
	 @Column(name="bankName" ,nullable=false ,length=150 )
	 private String bankName;



	 @Description(remark="银行帐号")
	 @Column(name="account" ,nullable=false ,length=30 )
	 private String account;
	 
	 @Description(remark="放款账户")
	 @Column(name="isPay" ,nullable=false )
	 private Integer isPay = 0;
	 
	 @Description(remark="收款账户")
	 @Column(name="isIncome" ,nullable=false )
	 private Integer isIncome = 1;


	 @Description(remark="账户类型")
	 @Column(name="atype" ,nullable=false )
	 private Integer atype = 1;

	 @Description(remark="财务系统银行账号ID")
	 @Column(name="fsbankAccountId")
	 private Long fsbankAccountId;
	 
	 @Description(remark="财务系统银行帐号ID")
	 @Column(name="refId")
	 private String refId;

	public AccountEntity() {

	}

	
	/**获取放款账户
	 * @return the isPay
	 */
	public Integer getIsPay() {
		return isPay;
	}


	/**设置放款账户
	 * @param isPay the isPay to set
	 */
	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}


	/**获取收款账户
	 * @return the isIncome
	 */
	public Integer getIsIncome() {
		return isIncome;
	}


	/**设置收款账户
	 * @param isIncome the isIncome to set
	 */
	public void setIsIncome(Integer isIncome) {
		this.isIncome = isIncome;
	}


	/**
	 * 获取系统ID的值
	 * @return
	 */
	public Long getSysId() {
		return sysId;
	}


	/**
	 * 设置系统ID的值
	 * @param sysId	系统ID
	 */
	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}





	/**
	  * 设置科目编号的值
	 * @param 	code	 科目编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取科目编号的值
	 * @return 返回科目编号的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置银行名称的值
	 * @param 	bankName	 银行名称
	**/
	public void setBankName(String  bankName){
		 this.bankName=bankName;
 	}

	/**
	  * 获取银行名称的值
	 * @return 返回银行名称的值
	**/
	public String getBankName(){
		 return bankName;
 	}

	/**
	  * 设置银行帐号的值
	 * @param 	account	 银行帐号
	**/
	public void setAccount(String  account){
		 this.account=account;
 	}

	/**
	  * 获取银行帐号的值
	 * @return 返回银行帐号的值
	**/
	public String getAccount(){
		 return account;
 	}

	/**
	  * 设置账户类型的值
	 * @param 	atype	 账户类型
	**/
	public void setAtype(Integer  atype){
		 this.atype=atype;
 	}

	/**
	  * 获取账户类型的值
	 * @return 返回账户类型的值
	**/
	public Integer getAtype(){
		 return atype;
 	}

	/**
	  * 获取银行账号  fs_BankAccount 表ID值
	 * @return 返回银行账号  fs_BankAccount 表ID值
	**/
	public Long getFsbankAccountId() {
		return fsbankAccountId;
	}

	/**
	  * 设置银行账号  fs_BankAccount 表ID值
	 * @param 	fsbankAccountId	FK 银行账号  fs_BankAccount 表ID
	**/
	public void setFsbankAccountId(Long fsbankAccountId) {
		this.fsbankAccountId = fsbankAccountId;
	}


	/**
	  * 获取财务系统客户ID的值
	 * @return 返回财务系统客户ID的值
	**/
	public String getRefId() {
		return refId;
	}

	/**
	  * 设置财务系统客户ID的值
	 * @param 	refId	财务系统客户ID
	**/
	public void setRefId(String refId) {
		this.refId = refId;
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{id,isPay,isIncome,sysId,isenabled,code,bankName,account,atype,remark,fsbankAccountId,refId};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isPay","isIncome","sysId","isenabled","code","bankName","account","atype","remark","fsbankAccountId","refId"};
	}

}
