package com.cmw.entity.crm;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 第三方担保人
 * @author 李听
 * @date 2013-12-31T00:00:00
 */
@Description(remark="第三方担保人实体",createDate="2013-12-31T00:00:00",author="李听")
@Entity
@Table(name="crm_GuaCustomer")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class GuaCustomerEntity extends IdBaseEntity {
	
	
//	 @Description(remark="客户分类表ID")
//	 @Column(name="categoryId" ,nullable=false )
//	 private Long categoryId;

	 @Description(remark="客户信息基础表Id")
	 @Column(name="baseId" ,nullable=false )
	 private Long baseId;
	 
	 @Description(remark="与被担保人关系")
	 @Column(name="relation" ,length=50 )
	 private String relation;

	 @Description(remark="是否无限连带担保责任")
	 @Column(name="isgua" ,nullable=false )
	 private Integer isgua = 1;
	 
	 @Description(remark="担保人名称")
	 @Column(name="name " ,nullable=false ,length=100)
	 private String name ;
	 
	 @Description(remark="联系人")
	 @Column(name="contactor " ,length=20 )
	 private String contactor ;
	 
	 @Description(remark="证件类型	")
	 @Column(name="cardType " ,nullable=false )
	 private Integer cardType ;
	 
	 @Description(remark="证件类型	")
	 @Column(name="cardNum" ,nullable=false ,length=50 )
	 private String cardNum ;
	 
	 @Description(remark="联系人电话")
	 @Column(name="contactTel" ,length=30 )
	 private String contactTel ;
	 
	 @Description(remark="担保人手机")
	 @Column(name="phone" ,length=30 )
	 private String phone ;
 
	@Description(remark="现居住地区")
	@Column(name="inArea" ,length=20 )
	 private String inArea;

	 @Description(remark="现居住详细地址")
	 @Column(name="inAddress" ,length=100 )
	 private String inAddress;
	 
	public GuaCustomerEntity() {

	}

	
	
	/**
	 * @return the baseId
	 */
	public Long getBaseId() {
		return baseId;
	}



	/**
	 * @param baseId the baseId to set
	 */
	public void setBaseId(Long baseId) {
		this.baseId = baseId;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @return the contactor
	 */
	public String getContactor() {
		return contactor;
	}



	/**
	 * @param contactor the contactor to set
	 */
	public void setContactor(String contactor) {
		this.contactor = contactor;
	}



	/**
	 * @return the cardType
	 */
	public Integer getCardType() {
		return cardType;
	}



	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}



	/**
	 * @return the cardNum
	 */
	public String getCardNum() {
		return cardNum;
	}



	/**
	 * @param cardNum the cardNum to set
	 */
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}



	/**
	 * @return the contactTel
	 */
	public String getContactTel() {
		return contactTel;
	}



	/**
	 * @param contactTel the contactTel to set
	 */
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}



	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}



	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}



	/**
	 * @return the inArea
	 */
	public String getInArea() {
		return inArea;
	}



	/**
	 * @param inArea the inArea to set
	 */
	public void setInArea(String inArea) {
		this.inArea = inArea;
	}



	/**
	 * @return the inAddress
	 */
	public String getInAddress() {
		return inAddress;
	}



	/**
	 * @param inAddress the inAddress to set
	 */
	public void setInAddress(String inAddress) {
		this.inAddress = inAddress;
	}

//
//
//	/**
//	  * 设置客户分类表ID的值
//	 * @param 	categoryId	 客户分类表ID
//	**/
//	public void setCategoryId(Long  categoryId){
//		 this.categoryId=categoryId;
// 	}
//
//	/**
//	  * 获取客户分类表ID的值
//	 * @return 返回客户分类表ID的值
//	**/
//	public Long getCategoryId(){
//		 return categoryId;
// 	}

	/**
	  * 设置与被担保人关系的值
	 * @param 	relation	 与被担保人关系
	**/
	public void setRelation(String  relation){
		 this.relation=relation;
 	}

	/**
	  * 获取与被担保人关系的值
	 * @return 返回与被担保人关系的值
	**/
	public String getRelation(){
		 return relation;
 	}

	/**
	  * 设置是否无限连带担保责任的值
	 * @param 	isgua	 是否无限连带担保责任
	**/
	public void setIsgua(Integer  isgua){
		 this.isgua=isgua;
 	}

	/**
	  * 获取是否无限连带担保责任的值
	 * @return 返回是否无限连带担保责任的值
	**/
	public Integer getIsgua(){
		 return isgua;
 	}


	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,baseId,/*categoryId,*/relation,isgua,name ,contactor,cardType,cardNum,contactTel,phone,inArea,inAddress};
	}
	
	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","baseId",/*"categoryId",*/"relation","isgua","name" ,"contactor","cardType","cardNum","contactTel","phone","inArea","inAddress"};
	}

}
