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
 * 审贷评审
 * @author 李听
 * @date 2014-01-04T00:00:00
 */
@Description(remark="审贷评审实体",createDate="2014-01-04T00:00:00",author="李听")
@Entity
@Table(name="fc_Appraise")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class AppraiseEntity extends IdBaseEntity {
	
	
	 @Description(remark="贷款申请单ID")
	 @Column(name="applyId" ,nullable=false )
	 private Long applyId;

	 @Description(remark="客户类型")
	 @Column(name="custType" ,nullable=false )
	 private Integer custType;

	 @Description(remark="客户ID")
	 @Column(name="customerId" ,nullable=false )
	 private Long customerId;

	 @Description(remark="业务种类")
	 @Column(name="breed" ,nullable=false )
	 private Long breed;

	 @Description(remark="贷款金额")
	 @Column(name="appAmount " ,nullable=false )
	 private BigDecimal appAmount=new BigDecimal(0) ;

	 @Description(remark="贷款期限(年)")
	 @Column(name="yearLoan" ,nullable=false )
	 private Integer yearLoan;

	 @Description(remark="贷款期限(月)")
	 @Column(name="monthLoan" ,nullable=false )
	 private Integer monthLoan;

	 @Description(remark="贷款期限(日)")
	 @Column(name="dayLoan" ,nullable=false )
	 private Integer dayLoan;

	 @Description(remark="利率类型")
	 @Column(name="rateType" ,nullable=false )
	 private Integer rateType;

	 @Description(remark="贷款利率")
	 @Column(name="rate" ,nullable=false ,scale=2)
	 private Double rate;

	 @Description(remark="企业基本情况")
	 @Column(name="situation" ,nullable=false ,length=2500 )
	 private String situation;

	 @Description(remark="评审会时间")
	 @Column(name="auditDate" )
	 private Date auditDate;

	 @Description(remark="评审会地点")
	 @Column(name="address" ,length=200 )
	 private String address;

	 @Description(remark="主持人")
	 @Column(name="hostMan" ,nullable=false )
	 private Long hostMan;

	 @Description(remark="记录人")
	 @Column(name="recordMan" ,nullable=false )
	 private Long recordMan;

	 @Description(remark="小组成员意见")
	 @Column(name="opinion" ,length=1500 )
	 private String opinion;

	 @Description(remark="结论")
	 @Column(name="resultTag" )
	 private Integer resultTag;


	public AppraiseEntity() {

	}

	
	/**
	  * 设置贷款申请单ID的值
	 * @param 	applyId	 贷款申请单ID
	**/
	public void setApplyId(Long  applyId){
		 this.applyId=applyId;
 	}

	/**
	  * 获取贷款申请单ID的值
	 * @return 返回贷款申请单ID的值
	**/
	public Long getApplyId(){
		 return applyId;
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
	  * 设置客户ID的值
	 * @param 	customerId	 客户ID
	**/
	public void setCustomerId(Long  customerId){
		 this.customerId=customerId;
 	}

	/**
	  * 获取客户ID的值
	 * @return 返回客户ID的值
	**/
	public Long getCustomerId(){
		 return customerId;
 	}


	/**
	  * 设置业务种类的值
	 * @param 	breed	 业务种类
	**/
	public void setBreed(Long  breed){
		 this.breed=breed;
 	}

	/**
	  * 获取业务种类的值
	 * @return 返回业务种类的值
	**/
	public Long getBreed(){
		 return breed;
 	}

	/**
	  * 设置贷款金额的值
	 * @param 	appAmount 	 贷款金额
	**/
	public void setAppAmount (BigDecimal  appAmount ){
		 this.appAmount =appAmount ;
 	}

	/**
	  * 获取贷款金额的值
	 * @return 返回贷款金额的值
	**/
	public BigDecimal getAppAmount (){
		 return appAmount ;
 	}

	/**
	  * 设置贷款期限(年)的值
	 * @param 	yearLoan	 贷款期限(年)
	**/
	public void setYearLoan(Integer  yearLoan){
		 this.yearLoan=yearLoan;
 	}

	/**
	  * 获取贷款期限(年)的值
	 * @return 返回贷款期限(年)的值
	**/
	public Integer getYearLoan(){
		 return yearLoan;
 	}

	/**
	  * 设置贷款期限(月)的值
	 * @param 	monthLoan	 贷款期限(月)
	**/
	public void setMonthLoan(Integer  monthLoan){
		 this.monthLoan=monthLoan;
 	}

	/**
	  * 获取贷款期限(月)的值
	 * @return 返回贷款期限(月)的值
	**/
	public Integer getMonthLoan(){
		 return monthLoan;
 	}

	/**
	  * 设置贷款期限(日)的值
	 * @param 	dayLoan	 贷款期限(日)
	**/
	public void setDayLoan(Integer  dayLoan){
		 this.dayLoan=dayLoan;
 	}

	/**
	  * 获取贷款期限(日)的值
	 * @return 返回贷款期限(日)的值
	**/
	public Integer getDayLoan(){
		 return dayLoan;
 	}

	/**
	  * 设置利率类型的值
	 * @param 	rateType	 利率类型
	**/
	public void setRateType(Integer  rateType){
		 this.rateType=rateType;
 	}

	/**
	  * 获取利率类型的值
	 * @return 返回利率类型的值
	**/
	public Integer getRateType(){
		 return rateType;
 	}

	/**
	  * 设置贷款利率的值
	 * @param 	rate	 贷款利率
	**/
	public void setRate(Double  rate){
		 this.rate=rate;
 	}

	/**
	  * 获取贷款利率的值
	 * @return 返回贷款利率的值
	**/
	public Double getRate(){
		 return rate;
 	}

	/**
	  * 设置企业基本情况的值
	 * @param 	situation	 企业基本情况
	**/
	public void setSituation(String  situation){
		 this.situation=situation;
 	}

	/**
	  * 获取企业基本情况的值
	 * @return 返回企业基本情况的值
	**/
	public String getSituation(){
		 return situation;
 	}

	/**
	  * 设置评审会时间的值
	 * @param 	auditDate	 评审会时间
	**/
	public void setAuditDate(Date  auditDate){
		 this.auditDate=auditDate;
 	}

	/**
	  * 获取评审会时间的值
	 * @return 返回评审会时间的值
	**/
	public Date getAuditDate(){
		 return auditDate;
 	}

	/**
	  * 设置评审会地点的值
	 * @param 	address	 评审会地点
	**/
	public void setAddress(String  address){
		 this.address=address;
 	}

	/**
	  * 获取评审会地点的值
	 * @return 返回评审会地点的值
	**/
	public String getAddress(){
		 return address;
 	}

	/**
	  * 设置主持人的值
	 * @param 	hostMan	 主持人
	**/
	public void setHostMan(Long  hostMan){
		 this.hostMan=hostMan;
 	}

	/**
	  * 获取主持人的值
	 * @return 返回主持人的值
	**/
	public Long getHostMan(){
		 return hostMan;
 	}

	/**
	  * 设置记录人的值
	 * @param 	recordMan	 记录人
	**/
	public void setRecordMan(Long  recordMan){
		 this.recordMan=recordMan;
 	}

	/**
	  * 获取记录人的值
	 * @return 返回记录人的值
	**/
	public Long getRecordMan(){
		 return recordMan;
 	}

	/**
	  * 设置小组成员意见的值
	 * @param 	opinion	 小组成员意见
	**/
	public void setOpinion(String  opinion){
		 this.opinion=opinion;
 	}

	/**
	  * 获取小组成员意见的值
	 * @return 返回小组成员意见的值
	**/
	public String getOpinion(){
		 return opinion;
 	}

	/**
	  * 设置结论的值
	 * @param 	resultTag	 结论
	**/
	public void setResultTag(Integer  resultTag){
		 this.resultTag=resultTag;
 	}

	/**
	  * 获取结论的值
	 * @return 返回结论的值
	**/
	public Integer getResultTag(){
		 return resultTag;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{applyId,custType,customerId,breed,appAmount ,yearLoan,monthLoan,dayLoan,rateType,rate,situation,auditDate,address,hostMan,recordMan,opinion,resultTag};
	}

	@Override
	public String[] getFields() {
		return new String[]{"applyId","custType","customerId","breed","appAmount ","yearLoan","monthLoan","dayLoan","rateType","rate","situation","auditDate#yyyy-MM-dd","address","hostMan","recordMan","opinion","resultTag"};
	}

}
