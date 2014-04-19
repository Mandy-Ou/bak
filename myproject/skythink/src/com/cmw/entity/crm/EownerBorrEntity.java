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
 * 所有者借款情况
 * @author pdh
 * @date 2012-12-26T00:00:00
 */
@Description(remark="所有者借款情况实体",createDate="2012-12-26T00:00:00",author="pdh")
@Entity
@Table(name="crm_EownerBorr")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class EownerBorrEntity extends IdBaseEntity {
	
	
	 @Description(remark="企业客户ID")
	 @Column(name="ecustomerId" ,nullable=false )
	 private Long ecustomerId;

	 @Description(remark="所有者类型")
	 @Column(name="onwerType" ,nullable=false )
	 private Long onwerType;

	 @Description(remark="所有者名称")
	 @Column(name="onwer" ,nullable=false ,length=50 )
	 private String onwer;

	 @Description(remark="银行名称")
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;

	 @Description(remark="借款金额")
	 @Column(name="amount" ,nullable=false ,scale=2)
	 private BigDecimal amount;

	 @Description(remark="借款期限")
	 @Column(name="limits" ,nullable=false ,length=10 )
	 private String limits;

	 @Description(remark="信贷品种")
	 @Column(name="creditBreed" ,length=80 )
	 private String creditBreed;

	 @Description(remark="担保方式")
	 @Column(name="asstype" ,length=80 )
	 private String asstype;

	 @Description(remark="贷款分类结果")
	 @Column(name="result" ,length=100 )
	 private String result;


	public EownerBorrEntity() {

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
	  * 设置所有者类型的值
	 * @param 	onwerType	 所有者类型
	**/
	public void setOnwerType(Long  onwerType){
		 this.onwerType=onwerType;
 	}

	/**
	  * 获取所有者类型的值
	 * @return 返回所有者类型的值
	**/
	public Long getOnwerType(){
		 return onwerType;
 	}

	/**
	  * 设置所有者名称的值
	 * @param 	onwer	 所有者名称
	**/
	public void setOnwer(String  onwer){
		 this.onwer=onwer;
 	}

	/**
	  * 获取所有者名称的值
	 * @return 返回所有者名称的值
	**/
	public String getOnwer(){
		 return onwer;
 	}

	/**
	  * 设置银行名称的值
	 * @param 	name	 银行名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取银行名称的值
	 * @return 返回银行名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置借款金额的值
	 * @param 	amount	 借款金额
	**/
	public void setAmount(BigDecimal  amount){
		 this.amount=amount;
 	}

	/**
	  * 获取借款金额的值
	 * @return 返回借款金额的值
	**/
	public BigDecimal getAmount(){
		 return amount;
 	}

	/**
	  * 设置借款期限的值
	 * @param 	limits	 借款期限
	**/
	public void setLimits(String  limits){
		 this.limits=limits;
 	}

	/**
	  * 获取借款期限的值
	 * @return 返回借款期限的值
	**/
	public String getLimits(){
		 return limits;
 	}

	/**
	  * 设置信贷品种的值
	 * @param 	creditBreed	 信贷品种
	**/
	public void setCreditBreed(String  creditBreed){
		 this.creditBreed=creditBreed;
 	}

	/**
	  * 获取信贷品种的值
	 * @return 返回信贷品种的值
	**/
	public String getCreditBreed(){
		 return creditBreed;
 	}

	/**
	  * 设置担保方式的值
	 * @param 	asstype	 担保方式
	**/
	public void setAsstype(String  asstype){
		 this.asstype=asstype;
 	}

	/**
	  * 获取担保方式的值
	 * @return 返回担保方式的值
	**/
	public String getAsstype(){
		 return asstype;
 	}

	/**
	  * 设置贷款分类结果的值
	 * @param 	result	 贷款分类结果
	**/
	public void setResult(String  result){
		 this.result=result;
 	}

	/**
	  * 获取贷款分类结果的值
	 * @return 返回贷款分类结果的值
	**/
	public String getResult(){
		 return result;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,ecustomerId,onwerType,onwer,name,amount,limits,creditBreed,asstype,result,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","ecustomerId","onwerType","onwer","name","amount","limits","creditBreed","asstype","result","remark"};
	}

}
