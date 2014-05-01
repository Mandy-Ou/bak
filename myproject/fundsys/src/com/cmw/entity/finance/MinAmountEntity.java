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
import com.cmw.core.util.DateUtil;


/**
 * 最低金额配置
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark="最低金额配置实体",createDate="2013-02-28T00:00:00",author="程明卫")
@Entity
@Table(name="fc_MinAmount")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class MinAmountEntity extends IdBaseEntity {
	
	
	 @Description(remark="生效日期")
	 @Column(name="opdate" ,nullable=false )
	 private Date opdate;

	 @Description(remark="罚息")
	 @Column(name="mpamount" ,nullable=false )
	 private BigDecimal mpamount = new BigDecimal(0);

	 @Description(remark="滞纳金")
	 @Column(name="moamount" ,nullable=false )
	 private BigDecimal moamount = new BigDecimal(0);

	 @Description(remark="审核人")
	 @Column(name="adman" ,length=50 )
	 private String adman;

	 @Description(remark="审核日期")
	 @Column(name="adate" )
	 private Date adate;

	 @Description(remark="审核意见")
	 @Column(name="adresult" ,length=50 )
	 private String adresult;

	 @Description(remark="状态")
	 @Column(name="status" ,nullable=false )
	 private Integer status = 0;


	public MinAmountEntity() {

	}

	
	/**
	  * 设置生效日期的值
	 * @param 	opdate	 生效日期
	**/
	public void setOpdate(Date  opdate){
		 this.opdate=opdate;
 	}

	/**
	  * 获取生效日期的值
	 * @return 返回生效日期的值
	**/
	public Date getOpdate(){
		 return opdate;
 	}

	/**
	  * 设置罚息的值
	 * @param 	mpamount	 罚息
	**/
	public void setMpamount(BigDecimal  mpamount){
		 this.mpamount=mpamount;
 	}

	/**
	  * 获取罚息的值
	 * @return 返回罚息的值
	**/
	public BigDecimal getMpamount(){
		 return mpamount;
 	}

	/**
	  * 设置滞纳金的值
	 * @param 	moamount	 滞纳金
	**/
	public void setMoamount(BigDecimal  moamount){
		 this.moamount=moamount;
 	}

	/**
	  * 获取滞纳金的值
	 * @return 返回滞纳金的值
	**/
	public BigDecimal getMoamount(){
		 return moamount;
 	}

	/**
	  * 设置审核人的值
	 * @param 	adman	 审核人
	**/
	public void setAdman(String  adman){
		 this.adman=adman;
 	}

	/**
	  * 获取审核人的值
	 * @return 返回审核人的值
	**/
	public String getAdman(){
		 return adman;
 	}

	/**
	  * 设置审核日期的值
	 * @param 	adate	 审核日期
	**/
	public void setAdate(Date  adate){
		 this.adate=adate;
 	}

	/**
	  * 获取审核日期的值
	 * @return 返回审核日期的值
	**/
	public Date getAdate(){
		 return adate;
 	}

	/**
	  * 设置审核意见的值
	 * @param 	adresult	 审核意见
	**/
	public void setAdresult(String  adresult){
		 this.adresult=adresult;
 	}

	/**
	  * 获取审核意见的值
	 * @return 返回审核意见的值
	**/
	public String getAdresult(){
		 return adresult;
 	}

	/**
	  * 设置状态的值
	 * @param 	status	 状态
	**/
	public void setStatus(Integer  status){
		 this.status=status;
 	}

	/**
	  * 获取状态的值
	 * @return 返回状态的值
	**/
	public Integer getStatus(){
		 return status;
 	}



	@Override
	public Object[] getDatas() {
		String opdateStr = (null == opdate) ? null : DateUtil.dateFormatToStr(opdate);
		return new Object[]{id,opdateStr,mpamount,moamount,adman,adate,adresult,status,creator,createTime,isenabled,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","opdate","mpamount","moamount","adman","adate#yyyy-MM-dd","adresult","status","creator","createTime#yyyy-MM-dd","isenabled","remark"};
	}

}
