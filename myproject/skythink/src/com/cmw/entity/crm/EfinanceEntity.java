package com.cmw.entity.crm;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 企业财务状况
 * @author 彭登浩
 * @date 2012-12-24T00:00:00
 */
@Description(remark="企业财务状况实体",createDate="2012-12-24T00:00:00",author="彭登浩")
@Entity
@Table(name="crm_Efinance")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class EfinanceEntity extends IdBaseEntity {
	
	
	 @Description(remark="企业客户ID")
	 @Column(name="ecustomerId" )
	 private Long ecustomerId;

	 @Description(remark="报表类型")
	 @Column(name="reportType" ,nullable=false )
	 private Integer reportType = 1;

	 @Description(remark="截止月份")
	 @Column(name="endDate" ,nullable=false )
	 private Date endDate ;

	 @Description(remark="资产负债表")
	 @Column(name="hasBalance" ,nullable=false )
	 private Byte hasBalance = 1;

	 @Description(remark="现金流量表")
	 @Column(name="hasCash" ,nullable=false )
	 private Byte hasCash = 1;

	 @Description(remark="利润表")
	 @Column(name="hasProfit" ,nullable=false )
	 private Byte hasProfit = 1;


	public EfinanceEntity() {

	}

	
	/**
	  * 设置企业客户ID的值
	 * @param 	ecustomerId	 企业客户ID
	**/
	public void setEcustomerId(Long  ecustomerId){
		 this.ecustomerId=ecustomerId;
 	}

	/**
	  * 获取企业客户ID的值
	 * @return 返回企业客户ID的值
	**/
	public Long getEcustomerId(){
		 return ecustomerId;
 	}

	/**
	  * 设置报表类型的值
	 * @param 	reportType	 报表类型
	**/
	public void setReportType(Integer  reportType){
		 this.reportType=reportType;
 	}

	/**
	  * 获取报表类型的值
	 * @return 返回报表类型的值
	**/
	public Integer getReportType(){
		 return reportType;
 	}

	/**
	  * 设置截止月份的值
	 * @param 	endDate	 截止月份
	**/
	public void setEndDate(Date  endDate){
		 this.endDate=endDate;
 	}

	/**
	  * 获取截止月份的值
	 * @return 返回截止月份的值
	**/
	public Date getEndDate(){
		 return endDate;
 	}

	/**
	  * 设置资产负债表的值
	 * @param 	hasBalance	 资产负债表
	**/
	public void setHasBalance(Byte  hasBalance){
		 this.hasBalance=hasBalance;
 	}

	/**
	  * 获取资产负债表的值
	 * @return 返回资产负债表的值
	**/
	public Byte getHasBalance(){
		 return hasBalance;
 	}

	/**
	  * 设置现金流量表的值
	 * @param 	hasCash	 现金流量表
	**/
	public void setHasCash(Byte  hasCash){
		 this.hasCash=hasCash;
 	}

	/**
	  * 获取现金流量表的值
	 * @return 返回现金流量表的值
	**/
	public Byte getHasCash(){
		 return hasCash;
 	}

	/**
	  * 设置利润表的值
	 * @param 	hasProfit	 利润表
	**/
	public void setHasProfit(Byte  hasProfit){
		 this.hasProfit=hasProfit;
 	}

	/**
	  * 获取利润表的值
	 * @return 返回利润表的值
	**/
	public Byte getHasProfit(){
		 return hasProfit;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{ecustomerId,reportType,endDate,hasBalance,hasCash,hasProfit};
	}

	@Override
	public String[] getFields() {
		return new String[]{"ecustomerId","reportType","endDate","hasBalance","hasCash","hasProfit"};
	}

}
