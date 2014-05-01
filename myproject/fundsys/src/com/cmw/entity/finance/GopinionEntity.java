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
 * 担保人意见
 * @author 程明卫
 * @date 2013-09-08T00:00:00
 */
@Description(remark="担保人意见实体",createDate="2013-09-08T00:00:00",author="程明卫")
@Entity
@Table(name="fc_Gopinion")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class GopinionEntity extends IdBaseEntity {
	
	
	 @Description(remark="展期申请单ID")
	 @Column(name="extensionId" ,nullable=false )
	 private Long extensionId;

	 @Description(remark="担保人")
	 @Column(name="guarantor" ,nullable=false ,length=30 )
	 private String guarantor;

	 @Description(remark="担保人意见")
	 @Column(name="opinion" ,nullable=false ,length=200 )
	 private String opinion;

	 @Description(remark="法定/授权代表人")
	 @Column(name="legal" ,length=50 )
	 private String legal;

	 @Description(remark="签字日期")
	 @Column(name="signDate" ,nullable=false )
	 private Date signDate;


	public GopinionEntity() {

	}

	
	/**
	  * 设置展期申请单ID的值
	 * @param 	extensionId	 展期申请单ID
	**/
	public void setExtensionId(Long  extensionId){
		 this.extensionId=extensionId;
 	}

	/**
	  * 获取展期申请单ID的值
	 * @return 返回展期申请单ID的值
	**/
	public Long getExtensionId(){
		 return extensionId;
 	}

	/**
	  * 设置担保人的值
	 * @param 	guarantor	 担保人
	**/
	public void setGuarantor(String  guarantor){
		 this.guarantor=guarantor;
 	}

	/**
	  * 获取担保人的值
	 * @return 返回担保人的值
	**/
	public String getGuarantor(){
		 return guarantor;
 	}

	/**
	  * 设置担保人意见的值
	 * @param 	opinion	 担保人意见
	**/
	public void setOpinion(String  opinion){
		 this.opinion=opinion;
 	}

	/**
	  * 获取担保人意见的值
	 * @return 返回担保人意见的值
	**/
	public String getOpinion(){
		 return opinion;
 	}

	/**
	  * 设置法定/授权代表人的值
	 * @param 	legal	 法定/授权代表人
	**/
	public void setLegal(String  legal){
		 this.legal=legal;
 	}

	/**
	  * 获取法定/授权代表人的值
	 * @return 返回法定/授权代表人的值
	**/
	public String getLegal(){
		 return legal;
 	}

	/**
	  * 设置签字日期的值
	 * @param 	signDate	 签字日期
	**/
	public void setSignDate(Date  signDate){
		 this.signDate=signDate;
 	}

	/**
	  * 获取签字日期的值
	 * @return 返回签字日期的值
	**/
	public Date getSignDate(){
		 return signDate;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,extensionId,guarantor,opinion,legal,signDate,isenabled};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","extensionId","guarantor","opinion","legal","signDate#yyyy-MM-dd","isenabled"};
	}

}
