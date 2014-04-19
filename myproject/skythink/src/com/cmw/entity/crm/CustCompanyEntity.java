package com.cmw.entity.crm;


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
 * 个人旗下/企业关联公司
 * @author pdh
 * @date 2012-12-15T00:00:00
 */
@Description(remark="个人旗下/企业关联公司实体",createDate="2012-12-15T00:00:00",author="pdh")
@Entity
@Table(name="crm_custcompany")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class CustCompanyEntity extends IdBaseEntity {
	
	
	 @Description(remark="客户类型")
	 @Column(name="custType" ,nullable=false )
	 private Integer custType;

	 @Description(remark="个人/企业客户ID")
	 @Column(name="customerId" ,nullable=false )
	 private Long customerId;

	 @Description(remark="公司名称")
	 @Column(name="cconame" ,nullable=false ,length=50 )
	 private String cconame;

	 @Description(remark="公司性质")
	 @Column(name="ccokind" ,nullable=false )
	 private Long ccokind;

	 @Description(remark="法人")
	 @Column(name="legal" ,nullable=false ,length=50 )
	 private String legal;

	 @Description(remark="工商登记号")
	 @Column(name="offNum" ,nullable=false ,length=50 )
	 private String offNum;

	 @Description(remark="组织机构代码证")
	 @Column(name="orgNum" ,length=50 )
	 private String orgNum;

	 @Description(remark="国税编号")
	 @Column(name="nationNum" ,length=50 )
	 private String nationNum;

	 @Description(remark="地税编号")
	 @Column(name="landNum" ,length=50 )
	 private String landNum;

	 @Description(remark="成立时间")
	 @Column(name="regDate" )
	 private Date regDate;

	 @Description(remark="注册资本")
	 @Column(name="regcaptial" ,scale=2)
	 private BigDecimal regcaptial;

	 @Description(remark="注册币种")
	 @Column(name="currency" )
	 private Long currency;

	 @Description(remark="联系人")
	 @Column(name="linkman" ,length=50 )
	 private String linkman;

	 @Description(remark="联系电话")
	 @Column(name="ccoTel" ,length=50 )
	 private String ccoTel;

	 @Description(remark="经营场所")
	 @Column(name="premises" ,length=50 )
	 private String premises;

	 @Description(remark="月供/月租金")
	 @Column(name="monthly" ,scale=2)
	 private BigDecimal monthly;

	 @Description(remark="员工人数")
	 @Column(name="empCount" )
	 private Integer empCount;

	 @Description(remark="全年盈利")
	 @Column(name="profit" )
	 private BigDecimal profit;

	 @Description(remark="经营地址")
	 @Column(name="ccoaaddress" ,length=50 )
	 private String ccoaaddress;


	public CustCompanyEntity() {

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
	  * 设置个人/企业客户ID的值
	 * @param 	customerId	 个人/企业客户ID
	**/
	public void setCustomerId(Long  customerId){
		 this.customerId=customerId;
 	}

	/**
	  * 获取个人/企业客户ID的值
	 * @return 返回个人/企业客户ID的值
	**/
	public Long getCustomerId(){
		 return customerId;
 	}

	/**
	  * 设置公司名称的值
	 * @param 	cconame	 公司名称
	**/
	public void setCconame(String  cconame){
		 this.cconame=cconame;
 	}

	/**
	  * 获取公司名称的值
	 * @return 返回公司名称的值
	**/
	public String getCconame(){
		 return cconame;
 	}

	/**
	  * 设置公司性质的值
	 * @param 	ccokind	 公司性质
	**/
	public void setCcokind(Long  ccokind){
		 this.ccokind=ccokind;
 	}

	/**
	  * 获取公司性质的值
	 * @return 返回公司性质的值
	**/
	public Long getCcokind(){
		 return ccokind;
 	}

	/**
	  * 设置法人的值
	 * @param 	legal	 法人
	**/
	public void setLegal(String  legal){
		 this.legal=legal;
 	}

	/**
	  * 获取法人的值
	 * @return 返回法人的值
	**/
	public String getLegal(){
		 return legal;
 	}

	/**
	  * 设置工商登记号的值
	 * @param 	offNum	 工商登记号
	**/
	public void setOffNum(String  offNum){
		 this.offNum=offNum;
 	}

	/**
	  * 获取工商登记号的值
	 * @return 返回工商登记号的值
	**/
	public String getOffNum(){
		 return offNum;
 	}

	/**
	  * 设置组织机构代码证的值
	 * @param 	orgNum	 组织机构代码证
	**/
	public void setOrgNum(String  orgNum){
		 this.orgNum=orgNum;
 	}

	/**
	  * 获取组织机构代码证的值
	 * @return 返回组织机构代码证的值
	**/
	public String getOrgNum(){
		 return orgNum;
 	}

	/**
	  * 设置国税编号的值
	 * @param 	nationNum	 国税编号
	**/
	public void setNationNum(String  nationNum){
		 this.nationNum=nationNum;
 	}

	/**
	  * 获取国税编号的值
	 * @return 返回国税编号的值
	**/
	public String getNationNum(){
		 return nationNum;
 	}

	/**
	  * 设置地税编号的值
	 * @param 	landNum	 地税编号
	**/
	public void setLandNum(String  landNum){
		 this.landNum=landNum;
 	}

	/**
	  * 获取地税编号的值
	 * @return 返回地税编号的值
	**/
	public String getLandNum(){
		 return landNum;
 	}

	/**
	  * 设置成立时间的值
	 * @param 	regDate	 成立时间
	**/
	public void setRegDate(Date  regDate){
		 this.regDate=regDate;
 	}

	/**
	  * 获取成立时间的值
	 * @return 返回成立时间的值
	**/
	public Date getRegDate(){
		 return regDate;
 	}

	/**
	  * 设置注册资本的值
	 * @param 	regcaptial	 注册资本
	**/
	public void setRegcaptial(BigDecimal  regcaptial){
		 this.regcaptial=regcaptial;
 	}

	/**
	  * 获取注册资本的值
	 * @return 返回注册资本的值
	**/
	public BigDecimal getRegcaptial(){
		 return regcaptial;
 	}

	/**
	  * 设置注册币种的值
	 * @param 	currency	 注册币种
	**/
	public void setCurrency(Long  currency){
		 this.currency=currency;
 	}

	/**
	  * 获取注册币种的值
	 * @return 返回注册币种的值
	**/
	public Long getCurrency(){
		 return currency;
 	}

	/**
	  * 设置联系人的值
	 * @param 	linkman	 联系人
	**/
	public void setLinkman(String  linkman){
		 this.linkman=linkman;
 	}

	/**
	  * 获取联系人的值
	 * @return 返回联系人的值
	**/
	public String getLinkman(){
		 return linkman;
 	}

	/**
	  * 设置联系电话的值
	 * @param 	ccoTel	 联系电话
	**/
	public void setCcoTel(String  ccoTel){
		 this.ccoTel=ccoTel;
 	}

	/**
	  * 获取联系电话的值
	 * @return 返回联系电话的值
	**/
	public String getCcoTel(){
		 return ccoTel;
 	}

	/**
	  * 设置经营场所的值
	 * @param 	premises	 经营场所
	**/
	public void setPremises(String  premises){
		 this.premises=premises;
 	}

	/**
	  * 获取经营场所的值
	 * @return 返回经营场所的值
	**/
	public String getPremises(){
		 return premises;
 	}

	/**
	  * 设置月供/月租金的值
	 * @param 	monthly	 月供/月租金
	**/
	public void setMonthly(BigDecimal  monthly){
		 this.monthly=monthly;
 	}

	/**
	  * 获取月供/月租金的值
	 * @return 返回月供/月租金的值
	**/
	public BigDecimal getMonthly(){
		 return monthly;
 	}

	/**
	  * 设置员工人数的值
	 * @param 	empCount	 员工人数
	**/
	public void setEmpCount(Integer  empCount){
		 this.empCount=empCount;
 	}

	/**
	  * 获取员工人数的值
	 * @return 返回员工人数的值
	**/
	public Integer getEmpCount(){
		 return empCount;
 	}

	/**
	  * 设置全年盈利的值
	 * @param 	profit	 全年盈利
	**/
	public void setProfit(BigDecimal  profit){
		 this.profit=profit;
 	}

	/**
	  * 获取全年盈利的值
	 * @return 返回全年盈利的值
	**/
	public BigDecimal getProfit(){
		 return profit;
 	}

	/**
	  * 设置经营地址的值
	 * @param 	ccoaaddress	 经营地址
	**/
	public void setCcoaaddress(String  ccoaaddress){
		 this.ccoaaddress=ccoaaddress;
 	}

	/**
	  * 获取经营地址的值
	 * @return 返回经营地址的值
	**/
	public String getCcoaaddress(){
		 return ccoaaddress;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,custType,customerId,cconame,ccokind,legal,offNum,orgNum,nationNum,landNum,regDate,regcaptial,currency,linkman,ccoTel,premises,monthly,empCount,profit,ccoaaddress};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","custType","customerId","cconame","ccokind","legal","offNum","orgNum","nationNum","landNum","regDate#yyyy-MM-dd","regcaptial","currency","linkman","ccoTel","premises","monthly","empCount","profit","ccoaaddress"};
	}

}
