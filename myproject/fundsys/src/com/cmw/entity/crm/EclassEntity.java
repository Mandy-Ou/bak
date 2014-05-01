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
 * 企业领导班子
 * @author pdh
 * @date 2012-12-26T00:00:00
 */
@Description(remark="企业领导班子实体",createDate="2012-12-26T00:00:00",author="pdh")
@Entity
@Table(name="crm_Eclass")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class EclassEntity extends IdBaseEntity {
	
	
	 @Description(remark="企业客户ID")
	 @Column(name="ecustomerId" ,nullable=false )
	 private Long ecustomerId;

	 @Description(remark="领导名称")
	 @Column(name="fugleName" ,nullable=false ,length=30 )
	 private String fugleName;

	 @Description(remark="性别")
	 @Column(name="sex" ,nullable=false )
	 private Integer sex;

	 @Description(remark="出生日期")
	 @Column(name="birthday" ,nullable=false )
	 private Date birthday;

	 @Description(remark="证件类型")
	 @Column(name="cardType" ,nullable=false )
	 private Long cardType;

	 @Description(remark="证件号码")
	 @Column(name="cardNumer" ,nullable=false ,length=20 )
	 private String cardNumer;

	 @Description(remark="籍贯")
	 @Column(name="hometown" )
	 private Long hometown;

	 @Description(remark="民族")
	 @Column(name="nation" )
	 private Long nation;

	 @Description(remark="学历")
	 @Column(name="degree" )
	 private Long degree;

	 @Description(remark="职务")
	 @Column(name="job" ,length=20 )
	 private String job;

	 @Description(remark="是否董事成员")
	 @Column(name="isMember" )
	 private Integer isMember = 0;

	 @Description(remark="联系电话")
	 @Column(name="Tel" ,length=20 )
	 private String Tel;

	 @Description(remark="手机")
	 @Column(name="phone" ,length=20 )
	 private String phone;

	 @Description(remark="任职时间")
	 @Column(name="incomeTime" )
	 private Date incomeTime;


	public EclassEntity() {

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
	  * 设置领导名称的值
	 * @param 	fugleName	 领导名称
	**/
	public void setFugleName(String  fugleName){
		 this.fugleName=fugleName;
 	}

	/**
	  * 获取领导名称的值
	 * @return 返回领导名称的值
	**/
	public String getFugleName(){
		 return fugleName;
 	}

	/**
	  * 设置性别的值
	 * @param 	sex	 性别
	**/
	public void setSex(Integer  sex){
		 this.sex=sex;
 	}

	/**
	  * 获取性别的值
	 * @return 返回性别的值
	**/
	public Integer getSex(){
		 return sex;
 	}

	/**
	  * 设置出生日期的值
	 * @param 	birthday	 出生日期
	**/
	public void setBirthday(Date  birthday){
		 this.birthday=birthday;
 	}

	/**
	  * 获取出生日期的值
	 * @return 返回出生日期的值
	**/
	public Date getBirthday(){
		 return birthday;
 	}

	/**
	  * 设置证件类型的值
	 * @param 	cardType	 证件类型
	**/
	public void setCardType(Long  cardType){
		 this.cardType=cardType;
 	}

	/**
	  * 获取证件类型的值
	 * @return 返回证件类型的值
	**/
	public Long getCardType(){
		 return cardType;
 	}

	/**
	  * 设置证件号码的值
	 * @param 	cardNumer	 证件号码
	**/
	public void setCardNumer(String  cardNumer){
		 this.cardNumer=cardNumer;
 	}

	/**
	  * 获取证件号码的值
	 * @return 返回证件号码的值
	**/
	public String getCardNumer(){
		 return cardNumer;
 	}

	/**
	  * 设置籍贯的值
	 * @param 	hometown	 籍贯
	**/
	public void setHometown(Long  hometown){
		 this.hometown=hometown;
 	}

	/**
	  * 获取籍贯的值
	 * @return 返回籍贯的值
	**/
	public Long getHometown(){
		 return hometown;
 	}

	/**
	  * 设置民族的值
	 * @param 	nation	 民族
	**/
	public void setNation(Long  nation){
		 this.nation=nation;
 	}

	/**
	  * 获取民族的值
	 * @return 返回民族的值
	**/
	public Long getNation(){
		 return nation;
 	}

	/**
	  * 设置学历的值
	 * @param 	degree	 学历
	**/
	public void setDegree(Long  degree){
		 this.degree=degree;
 	}

	/**
	  * 获取学历的值
	 * @return 返回学历的值
	**/
	public Long getDegree(){
		 return degree;
 	}

	/**
	  * 设置职务的值
	 * @param 	job	 职务
	**/
	public void setJob(String  job){
		 this.job=job;
 	}

	/**
	  * 获取职务的值
	 * @return 返回职务的值
	**/
	public String getJob(){
		 return job;
 	}

	/**
	  * 设置是否董事成员的值
	 * @param 	isMember	 是否董事成员
	**/
	public void setIsMember(Integer  isMember){
		 this.isMember=isMember;
 	}

	/**
	  * 获取是否董事成员的值
	 * @return 返回是否董事成员的值
	**/
	public Integer getIsMember(){
		 return isMember;
 	}

	/**
	  * 设置联系电话的值
	 * @param 	Tel	 联系电话
	**/
	public void setTel(String  Tel){
		 this.Tel=Tel;
 	}

	/**
	  * 获取联系电话的值
	 * @return 返回联系电话的值
	**/
	public String getTel(){
		 return Tel;
 	}

	/**
	  * 设置手机的值
	 * @param 	phone	 手机
	**/
	public void setPhone(String  phone){
		 this.phone=phone;
 	}

	/**
	  * 获取手机的值
	 * @return 返回手机的值
	**/
	public String getPhone(){
		 return phone;
 	}

	/**
	  * 设置任职时间的值
	 * @param 	incomeTime	 任职时间
	**/
	public void setIncomeTime(Date  incomeTime){
		 this.incomeTime=incomeTime;
 	}

	/**
	  * 获取任职时间的值
	 * @return 返回任职时间的值
	**/
	public Date getIncomeTime(){
		 return incomeTime;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,ecustomerId,fugleName,sex,birthday,cardType,cardNumer,hometown,nation,degree,job,isMember,Tel,phone,incomeTime,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","ecustomerId","fugleName","sex","birthday#yyyy-MM-dd","cardType","cardNumer","hometown","nation","degree","job","isMember","Tel","phone","incomeTime#yyyy-MM-dd","remark"};
	}

}
