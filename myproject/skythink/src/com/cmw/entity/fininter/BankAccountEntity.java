package com.cmw.entity.fininter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 财务系统银行账号
 * @author 程明卫
 * @date 2013-04-20T00:00:00
 */
@Description(remark="财务系统银行账号实体",createDate="2013-04-20T00:00:00",author="程明卫")
@Entity
@Table(name="fs_BankAccount")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class BankAccountEntity extends IdBaseEntity {
	
	
	 @Description(remark="财务系统ID")
	 @Column(name="finsysId" )
	 private Long finsysId;

	 @Description(remark="编号")
	 @Column(name="code" ,length=50 )
	 private String code;

	 @Description(remark="账户名称")
	 @Column(name="name" ,length=60 )
	 private String name;

	 @Description(remark="银行账号")
	 @Column(name="actNumber" ,length=30 )
	 private String actNumber;

	 @Description(remark="开户行")
	 @Column(name="bankName" ,length=100 )
	 private String bankName;

	 @Description(remark="结算方式ID")
	 @Column(name="refId" )
	 private Long refId;


	public BankAccountEntity() {

	}

	
	/**
	  * 设置财务系统ID的值
	 * @param 	finsysId	 财务系统ID
	**/
	public void setFinsysId(Long  finsysId){
		 this.finsysId=finsysId;
 	}

	/**
	  * 获取财务系统ID的值
	 * @return 返回财务系统ID的值
	**/
	public Long getFinsysId(){
		 return finsysId;
 	}

	/**
	  * 设置编号的值
	 * @param 	code	 编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取编号的值
	 * @return 返回编号的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置账户名称的值
	 * @param 	name	 账户名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取账户名称的值
	 * @return 返回账户名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置银行账号的值
	 * @param 	actNumber	 银行账号
	**/
	public void setActNumber(String  actNumber){
		 this.actNumber=actNumber;
 	}

	/**
	  * 获取银行账号的值
	 * @return 返回银行账号的值
	**/
	public String getActNumber(){
		 return actNumber;
 	}

	/**
	  * 设置开户行的值
	 * @param 	bankName	 开户行
	**/
	public void setBankName(String  bankName){
		 this.bankName=bankName;
 	}

	/**
	  * 获取开户行的值
	 * @return 返回开户行的值
	**/
	public String getBankName(){
		 return bankName;
 	}

	/**
	  * 设置结算方式ID的值
	 * @param 	refId	 结算方式ID
	**/
	public void setRefId(Long  refId){
		 this.refId=refId;
 	}

	/**
	  * 获取结算方式ID的值
	 * @return 返回结算方式ID的值
	**/
	public Long getRefId(){
		 return refId;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,finsysId,code,name,actNumber,bankName,refId,createTime,isenabled};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","finsysId","code","name","actNumber","bankName","refId","createTime#yyyy-MM-dd HH:mm","isenabled"};
	}

}
