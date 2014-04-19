package com.cmw.entity.crm;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 客户住宅信息
 * @author pdh
 * @date 2012-12-15T00:00:00
 */
@Description(remark="客户住宅信息实体",createDate="2012-12-15T00:00:00",author="pdh")
@Entity
@Table(name="crm_Address")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class AddressEntity extends IdBaseEntity {
	
	
	 @Description(remark="个人客户ID")
	 @Column(name="customerId" ,nullable=false )
	 private Long customerId;

	 @Description(remark="住宅地址")
	 @Column(name="address" ,nullable=false ,length=50 )
	 private String address;

	 @Description(remark="默认通讯地址")
	 @Column(name="isDefault" ,nullable=false )
	 private Integer isDefault = 0;

	 @Description(remark="住宅类别")
	 @Column(name="housType" ,nullable=false )
	 private Long housType;

	 @Description(remark="居住方式")
	 @Column(name="resideType" ,nullable=false )
	 private Long resideType;

	 @Description(remark="主要联系人")
	 @Column(name="contactor" ,nullable=false ,length=30 )
	 private String contactor;

	 @Description(remark="与本人关系")
	 @Column(name="relation" ,length=50 )
	 private String relation;

	 @Description(remark="主要联系人电话 ")
	 @Column(name="tel" ,length=20 )
	 private String tel;

	 @Description(remark="主要联系人手机")
	 @Column(name="phone" ,length=20 )
	 private String phone;

	 @Description(remark="邮编")
	 @Column(name="zipcode" ,length=10 )
	 private String zipcode;

	 @Description(remark="住宅居住人数")
	 @Column(name="manCount" )
	 private Integer manCount;


	public AddressEntity() {

	}

	
	/**
	  * 设置个人客户ID的值
	 * @param 	customerId	 个人客户ID
	**/
	public void setCustomerId(Long  customerId){
		 this.customerId=customerId;
 	}

	/**
	  * 获取个人客户ID的值
	 * @return 返回个人客户ID的值
	**/
	public Long getCustomerId(){
		 return customerId;
 	}

	/**
	  * 设置住宅地址的值
	 * @param 	address	 住宅地址
	**/
	public void setAddress(String  address){
		 this.address=address;
 	}

	/**
	  * 获取住宅地址的值
	 * @return 返回住宅地址的值
	**/
	public String getAddress(){
		 return address;
 	}

	/**
	  * 设置默认通讯地址的值
	 * @param 	isDefault	 默认通讯地址
	**/
	public void setIsDefault(Integer  isDefault){
		 this.isDefault=isDefault;
 	}

	/**
	  * 获取默认通讯地址的值
	 * @return 返回默认通讯地址的值
	**/
	public Integer getIsDefault(){
		 return isDefault;
 	}

	/**
	  * 设置住宅类别的值
	 * @param 	housType	 住宅类别
	**/
	public void setHousType(Long  housType){
		 this.housType=housType;
 	}

	/**
	  * 获取住宅类别的值
	 * @return 返回住宅类别的值
	**/
	public Long getHousType(){
		 return housType;
 	}

	/**
	  * 设置居住方式的值
	 * @param 	resideType	 居住方式
	**/
	public void setResideType(Long  resideType){
		 this.resideType=resideType;
 	}

	/**
	  * 获取居住方式的值
	 * @return 返回居住方式的值
	**/
	public Long getResideType(){
		 return resideType;
 	}

	/**
	  * 设置主要联系人的值
	 * @param 	contactor	 主要联系人
	**/
	public void setContactor(String  contactor){
		 this.contactor=contactor;
 	}

	/**
	  * 获取主要联系人的值
	 * @return 返回主要联系人的值
	**/
	public String getContactor(){
		 return contactor;
 	}

	/**
	  * 设置与本人关系的值
	 * @param 	relation	 与本人关系
	**/
	public void setRelation(String  relation){
		 this.relation=relation;
 	}

	/**
	  * 获取与本人关系的值
	 * @return 返回与本人关系的值
	**/
	public String getRelation(){
		 return relation;
 	}

	/**
	  * 设置主要联系人电话 的值
	 * @param 	tel	 主要联系人电话 
	**/
	public void setTel(String  tel){
		 this.tel=tel;
 	}

	/**
	  * 获取主要联系人电话 的值
	 * @return 返回主要联系人电话 的值
	**/
	public String getTel(){
		 return tel;
 	}

	/**
	  * 设置主要联系人手机的值
	 * @param 	phone	 主要联系人手机
	**/
	public void setPhone(String  phone){
		 this.phone=phone;
 	}

	/**
	  * 获取主要联系人手机的值
	 * @return 返回主要联系人手机的值
	**/
	public String getPhone(){
		 return phone;
 	}

	/**
	  * 设置邮编的值
	 * @param 	zipcode	 邮编
	**/
	public void setZipcode(String  zipcode){
		 this.zipcode=zipcode;
 	}

	/**
	  * 获取邮编的值
	 * @return 返回邮编的值
	**/
	public String getZipcode(){
		 return zipcode;
 	}

	/**
	  * 设置住宅居住人数的值
	 * @param 	manCount	 住宅居住人数
	**/
	public void setManCount(Integer  manCount){
		 this.manCount=manCount;
 	}

	/**
	  * 获取住宅居住人数的值
	 * @return 返回住宅居住人数的值
	**/
	public Integer getManCount(){
		 return manCount;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,customerId,address,isDefault,housType,resideType,contactor,relation,tel,phone,zipcode,manCount};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","customerId","address","isDefault","housType","resideType","contactor","relation","tel","phone","zipcode","manCount"};
	}

}
