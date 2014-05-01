package com.cmw.entity.finance;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 还款计划备份总表
 * @author 程明卫
 * @date 2014-01-07T00:00:00
 */
@Description(remark="还款计划备份总表实体",createDate="2014-01-07T00:00:00",author="程明卫")
@Entity
@Table(name="fc_OveralLog")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class OveralLogEntity extends IdBaseEntity {
	
	
	 @Description(remark="合同ID")
	 @Column(name="contractId" ,nullable=false )
	 private Long contractId;

	 @Description(remark="业务类型")
	 @Column(name="bussType" ,nullable=false )
	 private Integer bussType;

	 @Description(remark="变动之前还款方式")
	 @Column(name="bpayType" ,length=50 )
	 private String bpayType;

	 @Description(remark="变动后的还款方式")
	 @Column(name="epayType" ,nullable=false ,length=50 )
	 private String epayType;

	 @Description(remark="变动日期")
	 @Column(name="changeDate" ,nullable=false )
	 private Date changeDate;


	public OveralLogEntity() {

	}

	
	/**
	  * 设置合同ID的值
	 * @param 	contractId	 合同ID
	**/
	public void setContractId(Long  contractId){
		 this.contractId=contractId;
 	}

	/**
	  * 获取合同ID的值
	 * @return 返回合同ID的值
	**/
	public Long getContractId(){
		 return contractId;
 	}

	/**
	  * 设置业务类型的值
	 * @param 	bussType	 业务类型
	**/
	public void setBussType(Integer  bussType){
		 this.bussType=bussType;
 	}

	/**
	  * 获取业务类型的值
	 * @return 返回业务类型的值
	**/
	public Integer getBussType(){
		 return bussType;
 	}

	/**
	  * 设置变动之前还款方式的值
	 * @param 	bpayType	 变动之前还款方式
	**/
	public void setBpayType(String  bpayType){
		 this.bpayType=bpayType;
 	}

	/**
	  * 获取变动之前还款方式的值
	 * @return 返回变动之前还款方式的值
	**/
	public String getBpayType(){
		 return bpayType;
 	}

	/**
	  * 设置变动后的还款方式的值
	 * @param 	epayType	 变动后的还款方式
	**/
	public void setEpayType(String  epayType){
		 this.epayType=epayType;
 	}

	/**
	  * 获取变动后的还款方式的值
	 * @return 返回变动后的还款方式的值
	**/
	public String getEpayType(){
		 return epayType;
 	}

	/**
	  * 设置变动日期的值
	 * @param 	changeDate	 变动日期
	**/
	public void setChangeDate(Date  changeDate){
		 this.changeDate=changeDate;
 	}

	/**
	  * 获取变动日期的值
	 * @return 返回变动日期的值
	**/
	public Date getChangeDate(){
		 return changeDate;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{contractId,bussType,bpayType,epayType,changeDate,id,isenabled};
	}

	@Override
	public String[] getFields() {
		return new String[]{"contractId","bussType","bpayType","epayType","changeDate","id","isenabled"};
	}

}
