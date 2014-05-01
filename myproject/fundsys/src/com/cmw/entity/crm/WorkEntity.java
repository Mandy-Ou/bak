package com.cmw.entity.crm;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 职业信息
 * @author pdh
 * @date 2012-12-15T00:00:00
 */
@Description(remark="职业信息实体",createDate="2012-12-15T00:00:00",author="pdh")
@Entity
@Table(name="crm_work")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class WorkEntity extends IdBaseEntity {
	
	
	 @Description(remark="个人客户ID")
	 @Column(name="customerId" ,nullable=false )
	 private Long customerId;

	 @Description(remark="单位名称")
	 @Column(name="orgName" ,nullable=false ,length=100 )
	 private String orgName;

	 @Description(remark="所在部门")
	 @Column(name="dept" ,nullable=false ,length=100 )
	 private String dept;

	 @Description(remark="职务")
	 @Column(name="job" ,nullable=false ,length=50 )
	 private String job;

	 @Description(remark="服务年数")
	 @Column(name="syears" ,nullable=false )
	 private Integer syears;

	 @Description(remark="每月收入")
	 @Column(name="income" ,nullable=false ,scale=2)
	 private BigDecimal income;

	 @Description(remark="每月支薪日")
	 @Column(name="payDay" )
	 private Integer payDay;

	 @Description(remark="支付方式")
	 @Column(name="payment" ,length=50 )
	 private String payment;

	 @Description(remark="行业类别")
	 @Column(name="industry" ,length=50 )
	 private String industry;

	 @Description(remark="单位地址")
	 @Column(name="address" ,length=200 )
	 private String address;

	 @Description(remark="邮编")
	 @Column(name="zipcode" ,length=10 )
	 private String zipcode;

	 @Description(remark="单位性质")
	 @Column(name="nature" )
	 private Long nature;

	 @Description(remark="单位电话")
	 @Column(name="tel" ,length=20 )
	 private String tel;

	 @Description(remark="单位传真")
	 @Column(name="fax" ,length=20 )
	 private String fax;

	 @Description(remark="网址")
	 @Column(name="url" ,length=100 )
	 private String url;


	public WorkEntity() {

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
	  * 设置单位名称的值
	 * @param 	orgName	 单位名称
	**/
	public void setOrgName(String  orgName){
		 this.orgName=orgName;
 	}

	/**
	  * 获取单位名称的值
	 * @return 返回单位名称的值
	**/
	public String getOrgName(){
		 return orgName;
 	}

	/**
	  * 设置所在部门的值
	 * @param 	dept	 所在部门
	**/
	public void setDept(String  dept){
		 this.dept=dept;
 	}

	/**
	  * 获取所在部门的值
	 * @return 返回所在部门的值
	**/
	public String getDept(){
		 return dept;
 	}

	/**
	  * 设置职务的值
	 * @param 	job	 职务
	**/
	public void setJob(String  job){
		 this.job=job;
 	}

	/**
	  * 获取职务的值
	 * @return 返回职务的值
	**/
	public String getJob(){
		 return job;
 	}

	/**
	  * 设置服务年数的值
	 * @param 	syears	 服务年数
	**/
	public void setSyears(Integer  syears){
		 this.syears=syears;
 	}

	/**
	  * 获取服务年数的值
	 * @return 返回服务年数的值
	**/
	public Integer getSyears(){
		 return syears;
 	}

	/**
	 * 设置每月收入
	 * @return
	 */
	public BigDecimal getIncome() {
		return income;
	}

	/**
	 * 获取每月收入
	 * @param income
	 */
	public void setIncome(BigDecimal income) {
		this.income = income;
	}


	/**
	  * 设置每月支薪日的值
	 * @param 	payDay	 每月支薪日
	**/
	public void setPayDay(Integer  payDay){
		 this.payDay=payDay;
 	}

	/**
	  * 获取每月支薪日的值
	 * @return 返回每月支薪日的值
	**/
	public Integer getPayDay(){
		 return payDay;
 	}

	/**
	  * 设置支付方式的值
	 * @param 	payment	 支付方式
	**/
	public void setPayment(String  payment){
		 this.payment=payment;
 	}

	/**
	  * 获取支付方式的值
	 * @return 返回支付方式的值
	**/
	public String getPayment(){
		 return payment;
 	}

	/**
	  * 设置行业类别的值
	 * @param 	industry	 行业类别
	**/
	public void setIndustry(String  industry){
		 this.industry=industry;
 	}

	/**
	  * 获取行业类别的值
	 * @return 返回行业类别的值
	**/
	public String getIndustry(){
		 return industry;
 	}

	/**
	  * 设置单位地址的值
	 * @param 	address	 单位地址
	**/
	public void setAddress(String  address){
		 this.address=address;
 	}

	/**
	  * 获取单位地址的值
	 * @return 返回单位地址的值
	**/
	public String getAddress(){
		 return address;
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
	  * 设置单位性质的值
	 * @param 	nature	 单位性质
	**/
	public void setNature(Long  nature){
		 this.nature=nature;
 	}

	/**
	  * 获取单位性质的值
	 * @return 返回单位性质的值
	**/
	public Long getNature(){
		 return nature;
 	}

	/**
	  * 设置单位电话的值
	 * @param 	tel	 单位电话
	**/
	public void setTel(String  tel){
		 this.tel=tel;
 	}

	/**
	  * 获取单位电话的值
	 * @return 返回单位电话的值
	**/
	public String getTel(){
		 return tel;
 	}

	/**
	  * 设置单位传真的值
	 * @param 	fax	 单位传真
	**/
	public void setFax(String  fax){
		 this.fax=fax;
 	}

	/**
	  * 获取单位传真的值
	 * @return 返回单位传真的值
	**/
	public String getFax(){
		 return fax;
 	}

	/**
	  * 设置网址的值
	 * @param 	url	 网址
	**/
	public void setUrl(String  url){
		 this.url=url;
 	}

	/**
	  * 获取网址的值
	 * @return 返回网址的值
	**/
	public String getUrl(){
		 return url;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,customerId,orgName,dept,job,syears,income,payDay,payment,industry,address,zipcode,nature,tel,fax,url};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","customerId","orgName","dept","job","syears","income","payDay","payment","industry","address","zipcode","nature","tel","fax","url"};
	}

}
