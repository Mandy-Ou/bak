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
 * 个人客户配偶信息
 * @author pdh
 * @date 2012-12-15T00:00:00
 */
@Description(remark="个人客户配偶信息实体",createDate="2012-12-15T00:00:00",author="pdh")
@Entity
@Table(name="crm_Consort")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class ConsortEntity extends IdBaseEntity {
	
	 @Description(remark="个人客户ID")
	 @Column(name="customerId" ,nullable=false )
	 private Long customerId;
	 
	 @Description(remark="姓名")
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;

	 @Description(remark="证件类型")
	 @Column(name="cardType" ,nullable=false )
	 private Long cardType;

	 @Description(remark="证件号码")
	 @Column(name="cardNum" ,nullable=false ,length=50 )
	 private String cardNum;

	 @Description(remark="出生日期")
	 @Column(name="birthday" )
	 private Date birthday;

	 @Description(remark="年龄")
	 @Column(name="age" )
	 private Integer age;

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

	 @Description(remark="手机")
	 @Column(name="phone" ,length=20 )
	 private String phone;

	 @Description(remark="电话")
	 @Column(name="contactTel" ,length=20 )
	 private String contactTel;

	 @Description(remark="电子邮件")
	 @Column(name="email" ,length=30 )
	 private String email;

	 @Description(remark="QQ或MSN号码")
	 @Column(name="qqmsnNum" ,length=20 )
	 private String qqmsnNum;

	 @Description(remark="现工作单位")
	 @Column(name="conjobunit" ,length=60 )
	 private String conjobunit;

	 @Description(remark="单位地址")
	 @Column(name="workAddress" ,length=200 )
	 private String workAddress;

	 @Description(remark="个人爱好")
	 @Column(name="coninterest" ,length=100 )
	 private String coninterest;


	public ConsortEntity() {

	}

	/**
	  * 设置个人客户ID的值
	 * @param 	customerId	 个人客户ID
	**/
	public void setCustomerId(Long  customerId){
		 this.customerId=customerId;
	}

	/**
	  * 获取个人客户ID的值
	 * @return 返回个人客户ID的值
	**/
	public Long getCustomerId(){
		 return customerId;
	}

	
	/**
	  * 设置姓名的值
	 * @param 	name	 姓名
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取姓名的值
	 * @return 返回姓名的值
	**/
	public String getName(){
		 return name;
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
	 * @param 	cardNum	 证件号码
	**/
	public void setCardNum(String  cardNum){
		 this.cardNum=cardNum;
 	}

	/**
	  * 获取证件号码的值
	 * @return 返回证件号码的值
	**/
	public String getCardNum(){
		 return cardNum;
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
	  * 设置年龄的值
	 * @param 	age	 年龄
	**/
	public void setAge(Integer  age){
		 this.age=age;
 	}

	/**
	  * 获取年龄的值
	 * @return 返回年龄的值
	**/
	public Integer getAge(){
		 return age;
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
	  * 设置电话的值
	 * @param 	contactTel	 电话
	**/
	public void setContactTel(String  contactTel){
		 this.contactTel=contactTel;
 	}

	/**
	  * 获取电话的值
	 * @return 返回电话的值
	**/
	public String getContactTel(){
		 return contactTel;
 	}

	/**
	  * 设置电子邮件的值
	 * @param 	email	 电子邮件
	**/
	public void setEmail(String  email){
		 this.email=email;
 	}

	/**
	  * 获取电子邮件的值
	 * @return 返回电子邮件的值
	**/
	public String getEmail(){
		 return email;
 	}

	/**
	  * 设置QQ或MSN号码的值
	 * @param 	qqmsnNum	 QQ或MSN号码
	**/
	public void setQqmsnNum(String  qqmsnNum){
		 this.qqmsnNum=qqmsnNum;
 	}

	/**
	  * 获取QQ或MSN号码的值
	 * @return 返回QQ或MSN号码的值
	**/
	public String getQqmsnNum(){
		 return qqmsnNum;
 	}

	/**
	  * 设置现工作单位的值
	 * @param 	conjobunit	 现工作单位
	**/
	public void setConjobunit(String  conjobunit){
		 this.conjobunit=conjobunit;
 	}

	/**
	  * 获取现工作单位的值
	 * @return 返回现工作单位的值
	**/
	public String getConjobunit(){
		 return conjobunit;
 	}

	/**
	  * 设置单位地址的值
	 * @param 	workAddress	 单位地址
	**/
	public void setWorkAddress(String  workAddress){
		 this.workAddress=workAddress;
 	}

	/**
	  * 获取单位地址的值
	 * @return 返回单位地址的值
	**/
	public String getWorkAddress(){
		 return workAddress;
 	}

	/**
	  * 设置个人爱好的值
	 * @param 	coninterest	 个人爱好
	**/
	public void setConinterest(String  coninterest){
		 this.coninterest=coninterest;
 	}

	/**
	  * 获取个人爱好的值
	 * @return 返回个人爱好的值
	**/
	public String getConinterest(){
		 return coninterest;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{customerId,name,cardType,cardNum,birthday,age,hometown,nation,degree,job,phone,contactTel,email,qqmsnNum,conjobunit,workAddress,coninterest};
	}

	@Override
	public String[] getFields() {
		return new String[]{"customerId","name","cardType","cardNum","birthday","age","hometown","nation","degree","job","phone","contactTel","email","qqmsnNum","conjobunit","workAddress","coninterest"};
	}

}
