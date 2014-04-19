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
 * 房产物业信息
 * @author pdh
 * @date 2012-12-15T00:00:00
 */
@Description(remark="房产物业信息实体",createDate="2012-12-15T00:00:00",author="pdh")
@Entity
@Table(name="crm_estate")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class EstateEntity extends IdBaseEntity {
	
	
	 @Description(remark="个人客户ID")
	 @Column(name="customerId" ,nullable=false )
	 private Long customerId;

	 @Description(remark="房产地址")
	 @Column(name="address" ,nullable=false ,length=200 )
	 private String address;

	 @Description(remark="购买方式")
	 @Column(name="buyType" ,nullable=false )
	 private Integer buyType;

	 @Description(remark="购买日期")
	 @Column(name="buyDate" ,nullable=false )
	 private Date buyDate;

	 @Description(remark="建筑年份")
	 @Column(name="houseYear" )
	 private Integer houseYear;

	 @Description(remark="建筑面积(平方米）")
	 @Column(name="area" ,scale=2)
	 private Double area;

	 @Description(remark="购买价格")
	 @Column(name="price" ,scale=2)
	 private BigDecimal  price= new BigDecimal(0.00);

	 @Description(remark="按揭银行")
	 @Column(name="mortBank" ,length=100 )
	 private String mortBank;

	 @Description(remark="贷款年限")
	 @Column(name="loanYear" ,length=10 )
	 private String loanYear;

	 @Description(remark="每月供款")
	 @Column(name="contributions" ,scale=2)
	 private BigDecimal  contributions = new BigDecimal(0);

	 @Description(remark="贷款总额")
	 @Column(name="loanAmount" ,scale=2)
	 private BigDecimal   loanAmount= new BigDecimal(0);

	 @Description(remark="尚欠余额")
	 @Column(name="zAmount" ,scale=2)
	 private BigDecimal  zAmount= new BigDecimal(0);

	 @Description(remark="已付分期")
	 @Column(name="installments" )
	 private Integer installments;

	 @Description(remark="总分期期数")
	 @Column(name="totalInstall" )
	 private Integer totalInstall;

	 @Description(remark="居住时长")
	 @Column(name="runtime" ,length=10 )
	 private String runtime;

	 @Description(remark="受供养人数")
	 @Column(name="supCount" )
	 private Integer supCount;

	 @Description(remark="与谁居住")
	 @Column(name="whoLived" ,length=50 )
	 private String whoLived;


	public EstateEntity() {

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
	  * 设置房产地址的值
	 * @param 	address	 房产地址
	**/
	public void setAddress(String  address){
		 this.address=address;
 	}

	/**
	  * 获取房产地址的值
	 * @return 返回房产地址的值
	**/
	public String getAddress(){
		 return address;
 	}

	/**
	  * 设置购买方式的值
	 * @param 	buyType	 购买方式
	**/
	public void setBuyType(Integer  buyType){
		 this.buyType=buyType;
 	}

	/**
	  * 获取购买方式的值
	 * @return 返回购买方式的值
	**/
	public Integer getBuyType(){
		 return buyType;
 	}

	/**
	  * 设置购买日期的值
	 * @param 	buyDate	 购买日期
	**/
	public void setBuyDate(Date  buyDate){
		 this.buyDate=buyDate;
 	}

	/**
	  * 获取购买日期的值
	 * @return 返回购买日期的值
	**/
	public Date getBuyDate(){
		 return buyDate;
 	}

	/**
	  * 设置建筑年份的值
	 * @param 	houseYear	 建筑年份
	**/
	public void setHouseYear(Integer  houseYear){
		 this.houseYear=houseYear;
 	}

	/**
	  * 获取建筑年份的值
	 * @return 返回建筑年份的值
	**/
	public Integer getHouseYear(){
		 return houseYear;
 	}

	/**
	  * 设置建筑面积(平方米）的值
	 * @param 	area	 建筑面积(平方米）
	**/
	public void setArea(Double  area){
		 this.area=area;
 	}

	/**
	  * 获取建筑面积(平方米）的值
	 * @return 返回建筑面积(平方米）的值
	**/
	public Double getArea(){
		 return area;
 	}

	/**
	  * 设置购买价格的值
	 * @param 	price	 购买价格
	**/
	public void setPrice(BigDecimal   price){
		 this.price=price;
 	}

	/**
	  * 获取购买价格的值
	 * @return 返回购买价格的值
	**/
	public BigDecimal  getPrice(){
		 return price;
 	}

	/**
	  * 设置按揭银行的值
	 * @param 	mortBank	 按揭银行
	**/
	public void setMortBank(String  mortBank){
		 this.mortBank=mortBank;
 	}

	/**
	  * 获取按揭银行的值
	 * @return 返回按揭银行的值
	**/
	public String getMortBank(){
		 return mortBank;
 	}

	/**
	  * 设置贷款年限的值
	 * @param 	loanYear	 贷款年限
	**/
	public void setLoanYear(String  loanYear){
		 this.loanYear=loanYear;
 	}

	/**
	  * 获取贷款年限的值
	 * @return 返回贷款年限的值
	**/
	public String getLoanYear(){
		 return loanYear;
 	}

	/**
	  * 设置每月供款的值
	 * @param 	contributions	 每月供款
	**/
	public void setContributions(BigDecimal   contributions){
		 this.contributions=contributions;
 	}

	/**
	  * 获取每月供款的值
	 * @return 返回每月供款的值
	**/
	public BigDecimal  getContributions(){
		 return contributions;
 	}

	/**
	  * 设置贷款总额的值
	 * @param 	loanAmount	 贷款总额
	**/
	public void setLoanAmount(BigDecimal    loanAmount){
		 this.loanAmount=loanAmount;
 	}

	/**
	  * 获取贷款总额的值
	 * @return 返回贷款总额的值
	**/
	public BigDecimal   getLoanAmount(){
		 return loanAmount;
 	}

	/**
	  * 设置尚欠余额的值
	 * @param 	zAmount	 尚欠余额
	**/
	public void setZAmount(BigDecimal   zAmount){
		 this.zAmount=zAmount;
 	}

	/**
	  * 获取尚欠余额的值
	 * @return 返回尚欠余额的值
	**/
	public BigDecimal  getZAmount(){
		 return zAmount;
 	}

	/**
	  * 设置已付分期的值
	 * @param 	installments	 已付分期
	**/
	public void setInstallments(Integer  installments){
		 this.installments=installments;
 	}

	/**
	  * 获取已付分期的值
	 * @return 返回已付分期的值
	**/
	public Integer getInstallments(){
		 return installments;
 	}

	/**
	  * 设置总分期期数的值
	 * @param 	totalInstall	 总分期期数
	**/
	public void setTotalInstall(Integer  totalInstall){
		 this.totalInstall=totalInstall;
 	}

	/**
	  * 获取总分期期数的值
	 * @return 返回总分期期数的值
	**/
	public Integer getTotalInstall(){
		 return totalInstall;
 	}

	/**
	  * 设置居住时长的值
	 * @param 	runtime	 居住时长
	**/
	public void setRuntime(String  runtime){
		 this.runtime=runtime;
 	}

	/**
	  * 获取居住时长的值
	 * @return 返回居住时长的值
	**/
	public String getRuntime(){
		 return runtime;
 	}

	/**
	  * 设置受供养人数的值
	 * @param 	supCount	 受供养人数
	**/
	public void setSupCount(Integer  supCount){
		 this.supCount=supCount;
 	}

	/**
	  * 获取受供养人数的值
	 * @return 返回受供养人数的值
	**/
	public Integer getSupCount(){
		 return supCount;
 	}

	/**
	  * 设置与谁居住的值
	 * @param 	whoLived	 与谁居住
	**/
	public void setWhoLived(String  whoLived){
		 this.whoLived=whoLived;
 	}

	/**
	  * 获取与谁居住的值
	 * @return 返回与谁居住的值
	**/
	public String getWhoLived(){
		 return whoLived;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,customerId,address,buyType,buyDate,houseYear,area,price,mortBank,loanYear,contributions,loanAmount,zAmount,installments,totalInstall,runtime,supCount,whoLived};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","customerId","address","buyType","buyDate#yyyy-MM-dd","houseYear","area","price","mortBank","loanYear","contributions","loanAmount","zAmount","installments","totalInstall","runtime","supCount","whoLived"};
	}

}
