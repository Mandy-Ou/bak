package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 业务品种
 * @author 程明卫
 * @date 2012-11-28T00:00:00
 */
@Description(remark="业务品种实体",createDate="2012-11-28T00:00:00",author="程明卫")
@Entity
@Table(name="ts_Variety")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class VarietyEntity extends IdBaseEntity {
	
	
	 @Description(remark="系统Id")
	 @Column(name="sysId" ,nullable=false )
	 private Long sysId;


	 @Description(remark="品种编号")
	 @Column(name="code" ,nullable=false ,length=50 )
	 private String code;


	 @Description(remark="品种名称")
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;


	 @Description(remark="业务性质")
	 @Column(name="bussType" ,nullable=false )
	 private Integer bussType;



	 @Description(remark="是否授信品种")
	 @Column(name="isCredit" ,nullable=false )
	 private Integer isCredit = 0;



	 @Description(remark="适用机构")
	 @Column(name="useorg" )
	 private Integer useorg = 0;



	 @Description(remark="指定公司ID")
	 @Column(name="companyIds" ,length=50 )
	 private String companyIds;



	 @Description(remark="指定公司名称")
	 @Column(name="companyName" ,length=50 )
	 private String companyName;



	 @Description(remark="限制贷款金额")
	 @Column(name="isLoan" )
	 private Integer isLoan = 0;



	 @Description(remark="金额范围")
	 @Column(name="amountRange" ,length=50 )
	 private String amountRange;



	 @Description(remark="限制贷款期限")
	 @Column(name="isLimit" )
	 private Integer isLimit;



	 @Description(remark="期限范围")
	 @Column(name="limitRange" ,length=50 )
	 private String limitRange;



	 @Description(remark="流程定义ID")
	 @Column(name="pdid" ,length=50 )
	 private String pdid;

	 @Description(remark="icon")
	 @Column(name="icon" ,length=200 )
	 private String icon;


	public VarietyEntity() {

	}

	
	/**
	  * 设置系统Id的值
	 * @param 	sysId	 系统Id
	**/
	public void setSysId(Long  sysId){
		 this.sysId=sysId;
 	}

	/**
	  * 获取系统Id的值
	 * @return 返回系统Id的值
	**/
	public Long getSysId(){
		 return sysId;
 	}

	/**
	  * 设置品种编号的值
	 * @param 	code	 品种编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取品种编号的值
	 * @return 返回品种编号的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置品种名称的值
	 * @param 	name	 品种名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取品种名称的值
	 * @return 返回品种名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置业务性质的值
	 * @param 	bussType	 业务性质
	**/
	public void setBussType(Integer  bussType){
		 this.bussType=bussType;
 	}

	/**
	  * 获取业务性质的值
	 * @return 返回业务性质的值
	**/
	public Integer getBussType(){
		 return bussType;
 	}

	/**
	  * 设置是否授信品种的值
	 * @param 	isCredit	 是否授信品种
	**/
	public void setIsCredit(Integer  isCredit){
		 this.isCredit=isCredit;
 	}

	/**
	  * 获取是否授信品种的值
	 * @return 返回是否授信品种的值
	**/
	public Integer getIsCredit(){
		 return isCredit;
 	}

	/**
	  * 设置适用机构的值
	 * @param 	useorg	 适用机构
	**/
	public void setUseorg(Integer  useorg){
		 this.useorg=useorg;
 	}

	/**
	  * 获取适用机构的值
	 * @return 返回适用机构的值
	**/
	public Integer getUseorg(){
		 return useorg;
 	}

	/**
	  * 设置指定公司ID的值
	 * @param 	companyIds	 指定公司ID
	**/
	public void setCompanyIds(String  companyIds){
		 this.companyIds=companyIds;
 	}

	/**
	  * 获取指定公司ID的值
	 * @return 返回指定公司ID的值
	**/
	public String getCompanyIds(){
		 return companyIds;
 	}

	/**
	  * 设置指定公司名称的值
	 * @param 	companyName	 指定公司名称
	**/
	public void setCompanyName(String  companyName){
		 this.companyName=companyName;
 	}

	/**
	  * 获取指定公司名称的值
	 * @return 返回指定公司名称的值
	**/
	public String getCompanyName(){
		 return companyName;
 	}

	/**
	  * 设置限制贷款金额的值
	 * @param 	isLoan	 限制贷款金额
	**/
	public void setIsLoan(Integer  isLoan){
		 this.isLoan=isLoan;
 	}

	/**
	  * 获取限制贷款金额的值
	 * @return 返回限制贷款金额的值
	**/
	public Integer getIsLoan(){
		 return isLoan;
 	}

	/**
	  * 设置金额范围的值
	 * @param 	amountRange	 金额范围
	**/
	public void setAmountRange(String  amountRange){
		 this.amountRange=amountRange;
 	}

	/**
	  * 获取金额范围的值
	 * @return 返回金额范围的值
	**/
	public String getAmountRange(){
		 return amountRange;
 	}

	/**
	  * 设置限制贷款期限的值
	 * @param 	isLimit	 限制贷款期限
	**/
	public void setIsLimit(Integer  isLimit){
		 this.isLimit=isLimit;
 	}

	/**
	  * 获取限制贷款期限的值
	 * @return 返回限制贷款期限的值
	**/
	public Integer getIsLimit(){
		 return isLimit;
 	}

	/**
	  * 设置期限范围的值
	 * @param 	limitRange	 期限范围
	**/
	public void setLimitRange(String  limitRange){
		 this.limitRange=limitRange;
 	}

	/**
	  * 获取期限范围的值
	 * @return 返回期限范围的值
	**/
	public String getLimitRange(){
		 return limitRange;
 	}

	/**
	  * 设置流程定义ID的值
	 * @param 	pdid	 流程定义ID
	**/
	public void setPdid(String  pdid){
		 this.pdid=pdid;
 	}

	/**
	  * 获取流程定义ID的值
	 * @return 返回流程定义ID的值
	**/
	public String getPdid(){
		 return pdid;
 	}



	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{id,sysId,code,name,bussType,isCredit,useorg,companyIds,companyName,isLoan,amountRange,isLimit,limitRange,pdid,isenabled,icon,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","sysId","code","name","bussType","isCredit","useorg","companyIds","companyName","isLoan","amountRange","isLimit","limitRange","pdid","isenabled","icon","remark"};
	}

}
