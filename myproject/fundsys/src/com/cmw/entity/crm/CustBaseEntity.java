package com.cmw.entity.crm;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 客户基础信息
 * @author 程明卫
 * @date 2012-12-12T00:00:00
 */
@Description(remark="客户基础信息实体",createDate="2012-12-12T00:00:00",author="程明卫")
@Entity
@Table(name="crm_CustBase")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class CustBaseEntity extends IdBaseEntity {
	
	
	 @Description(remark="客户编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;



	 @Description(remark="客户类型")
	 @Column(name="custType" )
	 private Integer custType;



	 @Description(remark="客户名称")
	 @Column(name="name" ,length=100 )
	 private String name;



	 @Description(remark="证件类型")
	 @Column(name="cardType" )
	 private Long cardType;



	 @Description(remark="证件号码")
	 @Column(name="cardNum" ,nullable=false ,length=50 )
	 private String cardNum;



	 @Description(remark="客户级别")
	 @Column(name="custLevel" ,nullable=false )
	 private Integer custLevel = 1;



	 @Description(remark="系统ID")
	 @Column(name="sysId" ,nullable=false )
	 private Long sysId;

	 @Description(remark="财务系统客户ID")
	 @Column(name="refId")
	 private Long refId;


	public CustBaseEntity() {

	}

	
	/**
	  * 设置客户编号的值
	 * @param 	code	 客户编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取客户编号的值
	 * @return 返回客户编号的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置客户类型的值
	 * @param 	custType	 客户类型
	**/
	public void setCustType(Integer  custType){
		 this.custType=custType;
 	}

	/**
	  * 获取客户类型的值
	 * @return 返回客户类型的值
	**/
	public Integer getCustType(){
		 return custType;
 	}

	/**
	  * 设置客户名称的值
	 * @param 	name	 客户名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取客户名称的值
	 * @return 返回客户名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置证件类型的值
	 * @param 	cardType	 证件类型
	**/
	public void setCardType(Long  cardType){
		 this.cardType=cardType;
 	}

	/**
	  * 获取证件类型的值
	 * @return 返回证件类型的值
	**/
	public Long getCardType(){
		 return cardType;
 	}

	/**
	  * 设置证件号码的值
	 * @param 	cardNum	 证件号码
	**/
	public void setCardNum(String  cardNum){
		 this.cardNum=cardNum;
 	}

	/**
	  * 获取证件号码的值
	 * @return 返回证件号码的值
	**/
	public String getCardNum(){
		 return cardNum;
 	}

	/**
	  * 设置客户级别的值
	 * @param 	custLevel	 客户级别
	**/
	public void setCustLevel(Integer  custLevel){
		 this.custLevel=custLevel;
 	}

	/**
	  * 获取客户级别的值
	 * @return 返回客户级别的值
	**/
	public Integer getCustLevel(){
		 return custLevel;
 	}

	/**
	  * 设置系统ID的值
	 * @param 	sysId	 系统ID
	**/
	public void setSysId(Long  sysId){
		 this.sysId=sysId;
 	}

	/**
	  * 获取系统ID的值
	 * @return 返回系统ID的值
	**/
	public Long getSysId(){
		 return sysId;
 	}


	/**
	  * 获取财务系统客户ID的值
	 * @return 返回财务系统客户ID的值
	**/
	public Long getRefId() {
		return refId;
	}

	/**
	  * 设置财务系统客户ID的值
	 * @param 	refId	财务系统客户ID
	**/
	public void setRefId(Long refId) {
		this.refId = refId;
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,code,custType,name,cardType,cardNum,custLevel,sysId,refId};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","code","custType","name","cardType","cardNum","custLevel","sysId","refId"};
	}
	
	/**
	 * 0:黑名单客户
	 */
	public static final int CUSTLEVEL_0 = 0;
	/**
	 * 1：潜在客户
	 */
	public static final int CUSTLEVEL_1 = 1;
	/**
	 * 2：正式客户
	 */
	public static final int CUSTLEVEL_2 = 2;
	/**
	 * 3：优质客户
	 */
	public static final int CUSTLEVEL_3 = 3;
	/**
	 * 4:授信客户
	 */
	public static final int CUSTLEVEL_4 = 4;
	
	/**
	 * 0:个人客户
	 */
	public static final int CUSTTYPE_0 = 0;
	/**
	 * 1:企业客户
	 */
	public static final int CUSTTYPE_1 = 1;

}
