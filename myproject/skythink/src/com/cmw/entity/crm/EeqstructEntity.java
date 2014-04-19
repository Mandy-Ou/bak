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
 * 股权结构
 * @author pdh
 * @date 2012-12-25T00:00:00
 */
@Description(remark="股权结构实体",createDate="2012-12-25T00:00:00",author="pdh")
@Entity
@Table(name="crm_Eeqstruct")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class EeqstructEntity extends IdBaseEntity {
	
	
	 @Description(remark="企业客户ID")
	 @Column(name="ecustomerId" ,nullable=false )
	 private Long ecustomerId;

	 @Description(remark="出资人名称")
	 @Column(name="name" ,nullable=false ,length=30 )
	 private String name;

	 @Description(remark="出资方式")
	 @Column(name="outType" ,nullable=false ,length=50 )
	 private String outType;

	 @Description(remark="出资额（万）")
	 @Column(name="inAmount" ,scale=2)
	 private BigDecimal inAmount;

	 @Description(remark="占比例（%）")
	 @Column(name="percents" ,scale=2)
	 private Double percents;

	 @Description(remark="出资时间")
	 @Column(name="storderDate" ,nullable=false )
	 private Date storderDate;


	public EeqstructEntity() {

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
	  * 设置出资人名称的值
	 * @param 	name	 出资人名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取出资人名称的值
	 * @return 返回出资人名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置出资方式的值
	 * @param 	outType	 出资方式
	**/
	public void setOutType(String  outType){
		 this.outType=outType;
 	}

	/**
	  * 获取出资方式的值
	 * @return 返回出资方式的值
	**/
	public String getOutType(){
		 return outType;
 	}

	/**
	  * 设置出资额（万）的值
	 * @param 	inAmount	 出资额（万）
	**/
	public void setInAmount(BigDecimal  inAmount){
		 this.inAmount=inAmount;
 	}

	/**
	  * 获取出资额（万）的值
	 * @return 返回出资额（万）的值
	**/
	public BigDecimal getInAmount(){
		 return inAmount;
 	}

	/**
	  * 设置占比例（%）的值
	 * @param 	percents	 占比例（%）
	**/
	public void setPercents(Double  percents){
		 this.percents=percents;
 	}

	/**
	  * 获取占比例（%）的值
	 * @return 返回占比例（%）的值
	**/
	public Double getPercents(){
		 return percents;
 	}

	/**
	  * 设置出资时间的值
	 * @param 	storderDate	 出资时间
	**/
	public void setStorderDate(Date  storderDate){
		 this.storderDate=storderDate;
 	}

	/**
	  * 获取出资时间的值
	 * @return 返回出资时间的值
	**/
	public Date getStorderDate(){
		 return storderDate;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,ecustomerId,name,outType,inAmount,percents,storderDate,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","ecustomerId","name","outType","inAmount","percents","storderDate#yyyy-MM-dd","remark"};
	}

}
