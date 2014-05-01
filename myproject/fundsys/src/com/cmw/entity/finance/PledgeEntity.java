package com.cmw.entity.finance;


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 质押物
 * @author pdh
 * @date 2013-01-08T00:00:00
 */
@Description(remark="质押物实体",createDate="2013-01-08T00:00:00",author="pdh")
@Entity
@Table(name="fc_Pledge ")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class PledgeEntity extends IdBaseEntity {
	
	
	 @Description(remark="贷款申请单ID")
	 @Column(name="formId" ,nullable=false )
	 private Long formId;

	 @Description(remark="质押物类型")
	 @Column(name="gtype" ,nullable=false )
	 private Long gtype;

	 @Description(remark="质押物名称")
	 @Column(name="name" ,nullable=false ,length=60 )
	 private String name;

	 @Description(remark="数量")
	 @Column(name="quantity" ,nullable=false )
	 private Integer quantity = 0;

	 @Description(remark="单位")
	 @Column(name="unint" ,length=20 )
	 private String unint;

	 @Description(remark="原价值(元)")
	 @Column(name="oldVal" ,nullable=false ,scale=0)
	 private BigDecimal oldVal ;

	 @Description(remark="质押价值")
	 @Column(name="mpVal" ,nullable=false )
	 private BigDecimal mpVal ;

	 @Description(remark="质押人")
	 @Column(name="mman" ,length=30 )
	 private String mman;

	 @Description(remark="联系电话")
	 @Column(name="conTel" ,length=20 )
	 private String conTel;

	 @Description(remark="质押时间")
	 @Column(name="morTime" )
	 private Date morTime;

	 @Description(remark="质押地点")
	 @Column(name="address" ,length=150 )
	 private String address;

	 @Description(remark="落实时间")
	 @Column(name="carrTime" )
	 private Date carrTime;

	 @Description(remark="落实人")
	 @Column(name="carrMan" ,length=30 )
	 private String carrMan;

	 @Description(remark="解押时间")
	 @Column(name="charTime" )
	 private Date charTime;

	 @Description(remark="解押人")
	 @Column(name="charMan" ,length=30 )
	 private String charMan;

	 @Description(remark="状态")
	 @Column(name="state" ,nullable=false )
	 private Integer state = 0;
	
	 @Description(remark="质押物编号")
	 @Column(name="code " ,length=30 )
	 private String code ;

	 @Description(remark="解押编号")
	 @Column(name="charCode " ,length=30 )
	 private String charCode ;
	 
	 @Description(remark="落实办理部门")
	 @Column(name="carrDept"  )
	 private Long carrDept;
	 
	 @Description(remark="解押办理部门")
	 @Column(name="charDept" )
	 private Long charDept;

	public PledgeEntity() {

	}
	

	
	/**
	  * 设置贷款申请单ID的值
	 * @param 	formId	 贷款申请单ID
	**/
	public void setFormId(Long  formId){
		 this.formId=formId;
 	}

	/**
	  * 获取贷款申请单ID的值
	 * @return 返回贷款申请单ID的值
	**/
	public Long getFormId(){
		 return formId;
 	}

	/**
	  * 设置质押物类型的值
	 * @param 	gtype	 质押物类型
	**/
	public void setGtype(Long  gtype){
		 this.gtype=gtype;
 	}

	/**
	  * 获取质押物类型的值
	 * @return 返回质押物类型的值
	**/
	public Long getGtype(){
		 return gtype;
 	}

	/**
	  * 设置质押物名称的值
	 * @param 	name	 质押物名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取质押物名称的值
	 * @return 返回质押物名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置数量的值
	 * @param 	quantity	 数量
	**/
	public void setQuantity(Integer  quantity){
		 this.quantity=quantity;
 	}

	/**
	  * 获取数量的值
	 * @return 返回数量的值
	**/
	public Integer getQuantity(){
		 return quantity;
 	}

	/**
	  * 设置单位的值
	 * @param 	unint	 单位
	**/
	public void setUnint(String  unint){
		 this.unint=unint;
 	}

	/**
	  * 获取单位的值
	 * @return 返回单位的值
	**/
	public String getUnint(){
		 return unint;
 	}

	/**
	  * 设置原价值(元)的值
	 * @param 	oldVal	 原价值(元)
	**/
	public void setOldVal(BigDecimal  oldVal){
		 this.oldVal=oldVal;
 	}

	/**
	  * 获取原价值(元)的值
	 * @return 返回原价值(元)的值
	**/
	public BigDecimal getOldVal(){
		 return oldVal;
 	}

	/**
	  * 设置质押价值的值
	 * @param 	mpVal	 质押价值
	**/
	public void setMpVal(BigDecimal  mpVal){
		 this.mpVal=mpVal;
 	}

	/**
	  * 获取质押价值的值
	 * @return 返回质押价值的值
	**/
	public BigDecimal getMpVal(){
		 return mpVal;
 	}

	/**
	  * 设置质押人的值
	 * @param 	mman	 质押人
	**/
	public void setMman(String  mman){
		 this.mman=mman;
 	}

	/**
	  * 获取质押人的值
	 * @return 返回质押人的值
	**/
	public String getMman(){
		 return mman;
 	}

	/**
	  * 设置联系电话的值
	 * @param 	conTel	 联系电话
	**/
	public void setConTel(String  conTel){
		 this.conTel=conTel;
 	}

	/**
	  * 获取联系电话的值
	 * @return 返回联系电话的值
	**/
	public String getConTel(){
		 return conTel;
 	}

	/**
	  * 设置质押时间的值
	 * @param 	morTime	 质押时间
	**/
	public void setMorTime(Date  morTime){
		 this.morTime=morTime;
 	}

	/**
	  * 获取质押时间的值
	 * @return 返回质押时间的值
	**/
	public Date getMorTime(){
		 return morTime;
 	}

	/**
	  * 设置质押地点的值
	 * @param 	address	 质押地点
	**/
	public void setAddress(String  address){
		 this.address=address;
 	}

	/**
	  * 获取质押地点的值
	 * @return 返回质押地点的值
	**/
	public String getAddress(){
		 return address;
 	}

	/**
	  * 设置落实时间的值
	 * @param 	carrTime	 落实时间
	**/
	public void setCarrTime(Date  carrTime){
		 this.carrTime=carrTime;
 	}

	/**
	  * 获取落实时间的值
	 * @return 返回落实时间的值
	**/
	public Date getCarrTime(){
		 return carrTime;
 	}

	/**
	  * 设置落实人的值
	 * @param 	carrMan	 落实人
	**/
	public void setCarrMan(String  carrMan){
		 this.carrMan=carrMan;
 	}

	/**
	  * 获取落实人的值
	 * @return 返回落实人的值
	**/
	public String getCarrMan(){
		 return carrMan;
 	}

	/**
	  * 设置解押时间的值
	 * @param 	charTime	 解押时间
	**/
	public void setCharTime(Date  charTime){
		 this.charTime=charTime;
 	}

	/**
	  * 获取解押时间的值
	 * @return 返回解押时间的值
	**/
	public Date getCharTime(){
		 return charTime;
 	}

	/**
	  * 设置解押人的值
	 * @param 	charMan	 解押人
	**/
	public void setCharMan(String  charMan){
		 this.charMan=charMan;
 	}

	/**
	  * 获取解押人的值
	 * @return 返回解押人的值
	**/
	public String getCharMan(){
		 return charMan;
 	}

	/**
	  * 设置状态的值
	 * @param 	state	 状态
	**/
	public void setState(Integer  state){
		 this.state=state;
 	}

	/**
	  * 获取状态的值
	 * @return 返回状态的值
	**/
	public Integer getState(){
		 return state;
 	}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public String getCharCode() {
		return charCode;
	}



	public void setCharCode(String charCode) {
		this.charCode = charCode;
	}



	public Long getCarrDept() {
		return carrDept;
	}



	public void setCarrDept(Long carrDept) {
		this.carrDept = carrDept;
	}



	public Long getCharDept() {
		return charDept;
	}



	public void setCharDept(Long charDept) {
		this.charDept = charDept;
	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,formId,gtype,name,quantity,unint,oldVal,mpVal,mman,conTel,morTime,address,carrTime,carrMan,charTime,charMan,state,code,charCode,carrDept,charDept};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","formId","gtype","name","quantity","unint","oldVal","mpVal","mman","conTel","morTime#yyyy-MM-dd","address","carrTime#yyyy-MM-dd","carrMan","charTime#yyyy-MM-dd","charMan","state","code","charCode","carrDept","charDept"};
	}

}
