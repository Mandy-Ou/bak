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
 * 企业担保
 * @author pdt
 * @date 2012-12-24T00:00:00
 */
@Description(remark="企业担保实体",createDate="2012-12-24T00:00:00",author="pdt")
@Entity
@Table(name="crm_Eassure")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class EassureEntity extends IdBaseEntity {
	
	
	 @Description(remark="企业客户ID")
	 @Column(name="ecustomerId" ,nullable=false )
	 private Long ecustomerId;

	 @Description(remark="担保对象")
	 @Column(name="object" ,nullable=false ,length=50 )
	 private String object;

	 @Description(remark="担保金额")
	 @Column(name="amount" ,nullable=false ,scale=2)
	 private BigDecimal amount;

	 @Description(remark="起始日期")
	 @Column(name="asstartDate" )
	 private Date asstartDate;

	 @Description(remark="解除日期")
	 @Column(name="asendDate" )
	 private Date asendDate;

	 @Description(remark="期限")
	 @Column(name="term" ,length=50 )
	 private String term;

	 @Description(remark="责任比例")
	 @Column(name="inverse" )
	 private Double inverse;

	 @Description(remark="责任余额")
	 @Column(name="asbalance" )
	 private BigDecimal asbalance;

	 @Description(remark="运营情况")
	 @Column(name="thing" ,length=225 )
	 private  String thing;


	public EassureEntity() {

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
	  * 设置担保对象的值
	 * @param 	object	 担保对象
	**/
	public void setObject(String  object){
		 this.object=object;
 	}

	/**
	  * 获取担保对象的值
	 * @return 返回担保对象的值
	**/
	public String getObject(){
		 return object;
 	}

	/**
	  * 设置担保金额的值
	 * @param 	amount	 担保金额
	**/
	public void setAmount(BigDecimal  amount){
		 this.amount=amount;
 	}

	/**
	  * 获取担保金额的值
	 * @return 返回担保金额的值
	**/
	public BigDecimal getAmount(){
		 return amount;
 	}

	/**
	  * 设置起始日期的值
	 * @param 	asstartDate	 起始日期
	**/
	public void setAsstartDate(Date  asstartDate){
		 this.asstartDate=asstartDate;
 	}

	/**
	  * 获取起始日期的值
	 * @return 返回起始日期的值
	**/
	public Date getAsstartDate(){
		 return asstartDate;
 	}

	/**
	  * 设置解除日期的值
	 * @param 	asendDate	 解除日期
	**/
	public void setAsendDate(Date  asendDate){
		 this.asendDate=asendDate;
 	}

	/**
	  * 获取解除日期的值
	 * @return 返回解除日期的值
	**/
	public Date getAsendDate(){
		 return asendDate;
 	}

	/**
	  * 设置期限的值
	 * @param 	term	 期限
	**/
	public void setTerm(String  term){
		 this.term=term;
 	}

	/**
	  * 获取期限的值
	 * @return 返回期限的值
	**/
	public String getTerm(){
		 return term;
 	}

	/**
	  * 设置责任比例的值
	 * @param 	inverse	 责任比例
	**/
	public void setInverse(Double  inverse){
		 this.inverse=inverse;
 	}

	/**
	  * 获取责任比例的值
	 * @return 返回责任比例的值
	**/
	public Double getInverse(){
		 return inverse;
 	}

	/**
	  * 设置责任余额的值
	 * @param 	asbalance	 责任余额
	**/
	public void setAsbalance(BigDecimal  asbalance){
		 this.asbalance=asbalance;
 	}

	/**
	  * 获取责任余额的值
	 * @return 返回责任余额的值
	**/
	public BigDecimal getAsbalance(){
		 return asbalance;
 	}

	/**
	  * 设置运营情况的值
	 * @param 	thing	 运营情况
	**/
	public void setThing(String  thing){
		 this.thing=thing;
 	}

	/**
	  * 获取运营情况的值
	 * @return 返回运营情况的值
	**/
	public  String getThing(){
		 return thing;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,ecustomerId,object,amount,asstartDate,asendDate,term,inverse,asbalance,thing,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","ecustomerId","object","amount","asstartDate##yyyy-MM-dd","asendDate##yyyy-MM-dd","term","inverse","asbalance","thing","remark"};
	}

}
