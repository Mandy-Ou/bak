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
 * 联系人资料
 * @author pdh
 * @date 2012-12-18T00:00:00
 */
@Description(remark="联系人资料实体",createDate="2012-12-18T00:00:00",author="pdh")
@Entity
@Table(name="crm_contactor")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class ContactorEntity extends IdBaseEntity {
	
	
	 @Description(remark="个人客户ID")
	 @Column(name="customerId" ,nullable=false )
	 private Long customerId;

	 @Description(remark="姓名")
	 @Column(name="name" ,nullable=false ,length=30 )
	 private String name;

	 @Description(remark="身份证")
	 @Column(name="idcrad" ,nullable=false ,length=40 )
	 private String idcrad;
	 
	 @Description(remark="关系")
	 @Column(name="relation" ,nullable=false ,length=30 )
	 private String relation;

	 @Description(remark="出生日期")
	 @Column(name="birthday" )
	 private Date birthday;

	 @Description(remark="年龄")
	 @Column(name="age" )
	 private Integer age;

	 @Description(remark="手机")
	 @Column(name="phone" ,length=20 )
	 private String phone;

	 @Description(remark="住宅电话")
	 @Column(name="tel" ,length=20 )
	 private String tel;

	 @Description(remark="住址")
	 @Column(name="address" ,length=200 )
	 private String address;

	 @Description(remark="单位名称")
	 @Column(name="orgName" ,length=50 )
	 private String orgName;

	 @Description(remark="职务")
	 @Column(name="job" ,length=30 )
	 private String job;

	 @Description(remark="单位电话")
	 @Column(name="orgTel" ,length=20 )
	 private String orgTel;


	public ContactorEntity() {

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

	/**获取身份证号
	 * @return the idcrad
	 */
	public String getIdcrad() {
		return idcrad;
	}


	/**设置身份证号
	 * @param idcrad the idcrad to set
	 */
	public void setIdcrad(String idcrad) {
		this.idcrad = idcrad;
	}


	/**
	  * 设置关系的值
	 * @param 	relation	 关系
	**/
	public void setRelation(String  relation){
		 this.relation=relation;
 	}

	/**
	  * 获取关系的值
	 * @return 返回关系的值
	**/
	public String getRelation(){
		 return relation;
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
	  * 设置住宅电话的值
	 * @param 	tel	 住宅电话
	**/
	public void setTel(String  tel){
		 this.tel=tel;
 	}

	/**
	  * 获取住宅电话的值
	 * @return 返回住宅电话的值
	**/
	public String getTel(){
		 return tel;
 	}

	/**
	  * 设置住址的值
	 * @param 	address	 住址
	**/
	public void setAddress(String  address){
		 this.address=address;
 	}

	/**
	  * 获取住址的值
	 * @return 返回住址的值
	**/
	public String getAddress(){
		 return address;
 	}

	/**
	  * 设置单位名称的值
	 * @param 	orgName	 单位名称
	**/
	public void setOrgName(String  orgName){
		 this.orgName=orgName;
 	}

	/**
	  * 获取单位名称的值
	 * @return 返回单位名称的值
	**/
	public String getOrgName(){
		 return orgName;
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
	  * 设置单位电话的值
	 * @param 	orgTel	 单位电话
	**/
	public void setOrgTel(String  orgTel){
		 this.orgTel=orgTel;
 	}

	/**
	  * 获取单位电话的值
	 * @return 返回单位电话的值
	**/
	public String getOrgTel(){
		 return orgTel;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,customerId,name,idcrad,relation,birthday,age,phone,tel,address,orgName,job,orgTel};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","customerId","name","idcrad","relation","birthday#yyyy-MM-dd","age","phone","tel","address","orgName","job","orgTel"};
	}

}
