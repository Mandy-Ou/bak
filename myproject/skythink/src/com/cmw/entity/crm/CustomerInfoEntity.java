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
 * 个人客户基本信息
 * @author 程明卫
 * @date 2012-12-12T00:00:00
 */
@Description(remark="个人客户基本信息实体",createDate="2012-12-12T00:00:00",author="程明卫")
@Entity
@Table(name="crm_CustomerInfo")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class CustomerInfoEntity extends IdBaseEntity {
	
	
	 @Description(remark="客户基础信息ID")
	 @Column(name="baseId" ,nullable=false )
	 private Long baseId;

	 @Description(remark="流水号")
	 @Column(name="serialNum" ,length=20 )
	 private String serialNum;

	 @Description(remark="姓名")
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;

	 @Description(remark="拼音助记码")
	 @Column(name="mnemonic" ,length=20 )
	 private String mnemonic;

	 @Description(remark="性别")
	 @Column(name="sex" ,nullable=false )
	 private Integer sex;

	 @Description(remark="出生日期")
	 @Column(name="birthday" )
	 private Date birthday;

	 @Description(remark="户口性质")
	 @Column(name="accNature" )
	 private Integer accNature;

	 @Description(remark="证件类型")
	 @Column(name="cardType" )
	 private Long cardType;

	 @Description(remark="证件号码")
	 @Column(name="cardNum" ,length=50 )
	 private String cardNum;

	 @Description(remark="证件到期日期")
	 @Column(name="cendDate" )
	 private Date cendDate;

	 @Description(remark="年龄")
	 @Column(name="age" )
	 private Integer age;

	 @Description(remark="婚姻状况")
	 @Column(name="maristal" )
	 private Long maristal;

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

	 @Description(remark="工作单位")
	 @Column(name="workOrg" ,length=50 )
	 private String workOrg;

	 @Description(remark="单位地址")
	 @Column(name="workAddress" ,length=100 )
	 private String workAddress;

	 @Description(remark="手机")
	 @Column(name="phone" ,length=20 )
	 private String phone;

	 @Description(remark="联系电话")
	 @Column(name="contactTel" ,length=20 )
	 private String contactTel;

	 @Description(remark="联系人")
	 @Column(name="contactor" ,length=30 )
	 private String contactor;

	 @Description(remark="电子邮件")
	 @Column(name="email" ,length=30 )
	 private String email;

	 @Description(remark="户籍地址")
	 @Column(name="accAddress" ,length=20 )
	 private String accAddress;
	 
	@Description(remark="现居住地区")
	 @Column(name="inArea" ,length=20 )
	 private String inArea;

	 @Description(remark="现居住详细地址")
	 @Column(name="inAddress" ,length=100 )
	 private String inAddress;

	 @Description(remark="邮政编码")
	 @Column(name="zipcode" ,length=10 )
	 private String zipcode;

	 @Description(remark="传真")
	 @Column(name="fax" ,length=20 )
	 private String fax;

	 @Description(remark="QQ或MSN号码")
	 @Column(name="qqmsnNum" ,length=20 )
	 private String qqmsnNum;

	 @Description(remark="登记时间")
	 @Column(name="registerTime" )
	 private Date registerTime;

	 @Description(remark="登记人")
	 @Column(name="regman" )
	 private Long regman;

	 @Description(remark="状态")
	 @Column(name="state" ,nullable=false )
	 private Integer state = 0;


	public CustomerInfoEntity() {

	}

	
	/**
	  * 设置客户基础信息ID的值
	 * @param 	baseId	 客户基础信息ID
	**/
	public void setBaseId(Long  baseId){
		 this.baseId=baseId;
 	}

	/**
	  * 获取客户基础信息ID的值
	 * @return 返回客户基础信息ID的值
	**/
	public Long getBaseId(){
		 return baseId;
 	}

	/**
	  * 设置流水号的值
	 * @param 	serialNum	 流水号
	**/
	public void setSerialNum(String  serialNum){
		 this.serialNum=serialNum;
 	}

	/**
	  * 获取流水号的值
	 * @return 返回流水号的值
	**/
	public String getSerialNum(){
		 return serialNum;
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
	  * 设置拼音助记码的值
	 * @param 	mnemonic	 拼音助记码
	**/
	public void setMnemonic(String  mnemonic){
		 this.mnemonic=mnemonic;
 	}

	/**
	  * 获取拼音助记码的值
	 * @return 返回拼音助记码的值
	**/
	public String getMnemonic(){
		 return mnemonic;
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
	  * 设置户口性质的值
	 * @param 	accNature	 户口性质
	**/
	public void setAccNature(Integer  accNature){
		 this.accNature=accNature;
 	}

	/**
	  * 获取户口性质的值
	 * @return 返回户口性质的值
	**/
	public Integer getAccNature(){
		 return accNature;
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
	  * 设置证件到期日期的值
	 * @param 	cendDate	 证件到期日期
	**/
	public void setCendDate(Date  cendDate){
		 this.cendDate=cendDate;
 	}

	/**
	  * 获取证件到期日期的值
	 * @return 返回证件到期日期的值
	**/
	public Date getCendDate(){
		 return cendDate;
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
	  * 设置婚姻状况的值
	 * @param 	maristal	 婚姻状况
	**/
	public void setMaristal(Long  maristal){
		 this.maristal=maristal;
 	}

	/**
	  * 获取婚姻状况的值
	 * @return 返回婚姻状况的值
	**/
	public Long getMaristal(){
		 return maristal;
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
	  * 设置工作单位的值
	 * @param 	workOrg	 工作单位
	**/
	public void setWorkOrg(String  workOrg){
		 this.workOrg=workOrg;
 	}

	/**
	 * 获取祖籍地址
	 * @return
	 */
	 public String getAccAddress() {
			return accAddress;
		}

	 /**
	  * 设置祖籍地址
	  * @param accAddress
	  */
	public void setAccAddress(String accAddress) {
		this.accAddress = accAddress;
	}
	
	/**
	  * 获取工作单位的值
	 * @return 返回工作单位的值
	**/
	public String getWorkOrg(){
		 return workOrg;
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
	  * 设置联系电话的值
	 * @param 	contactTel	 联系电话
	**/
	public void setContactTel(String  contactTel){
		 this.contactTel=contactTel;
 	}

	/**
	  * 获取联系电话的值
	 * @return 返回联系电话的值
	**/
	public String getContactTel(){
		 return contactTel;
 	}

	/**
	  * 设置联系人的值
	 * @param 	contactor	 联系人
	**/
	public void setContactor(String  contactor){
		 this.contactor=contactor;
 	}

	/**
	  * 获取联系人的值
	 * @return 返回联系人的值
	**/
	public String getContactor(){
		 return contactor;
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
	  * 设置现居住地区的值
	 * @param 	inArea	 现居住地区
	**/
	public void setInArea(String  inArea){
		 this.inArea=inArea;
 	}

	/**
	  * 获取现居住地区的值
	 * @return 返回现居住地区的值
	**/
	public String getInArea(){
		 return inArea;
 	}

	/**
	  * 设置现居住详细地址的值
	 * @param 	inAddress	 现居住详细地址
	**/
	public void setInAddress(String  inAddress){
		 this.inAddress=inAddress;
 	}

	/**
	  * 获取现居住详细地址的值
	 * @return 返回现居住详细地址的值
	**/
	public String getInAddress(){
		 return inAddress;
 	}

	/**
	  * 设置邮政编码的值
	 * @param 	zipcode	 邮政编码
	**/
	public void setZipcode(String  zipcode){
		 this.zipcode=zipcode;
 	}

	/**
	  * 获取邮政编码的值
	 * @return 返回邮政编码的值
	**/
	public String getZipcode(){
		 return zipcode;
 	}

	/**
	  * 设置传真的值
	 * @param 	fax	 传真
	**/
	public void setFax(String  fax){
		 this.fax=fax;
 	}

	/**
	  * 获取传真的值
	 * @return 返回传真的值
	**/
	public String getFax(){
		 return fax;
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
	  * 设置登记时间的值
	 * @param 	registerTime	 登记时间
	**/
	public void setRegisterTime(Date  registerTime){
		 this.registerTime=registerTime;
 	}

	/**
	  * 获取登记时间的值
	 * @return 返回登记时间的值
	**/
	public Date getRegisterTime(){
		 return registerTime;
 	}

	/**
	  * 设置登记人的值
	 * @param 	regman	 登记人
	**/
	public void setRegman(Long  regman){
		 this.regman=regman;
 	}

	/**
	  * 获取登记人的值
	 * @return 返回登记人的值
	**/
	public Long getRegman(){
		 return regman;
 	}

	/**
	  * 设置状态的值
	 * @param 	state	 状态
	**/
	public void setState(Integer  state){
		 this.state=state;
 	}

	/**
	  * 获取状态的值
	 * @return 返回状态的值
	**/
	public Integer getState(){
		 return state;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,baseId,serialNum,name,mnemonic,sex,birthday,accNature,cardType,cardNum,cendDate,age,maristal,hometown,nation,degree,job,workOrg,workAddress,phone,contactTel,contactor,email,inArea,accAddress,inAddress,zipcode,fax,qqmsnNum,registerTime,regman,state};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","baseId","serialNum","name","mnemonic","sex","birthday#yyyy-MM-dd","accNature","cardType","cardNum","cendDate#yyyy-MM-dd","age","maristal","hometown","nation","degree","job","workOrg","workAddress","phone","contactTel","contactor","email","accAddress","inArea","inAddress","zipcode","fax","qqmsnNum","registerTime#yyyy-MM-dd","regman","state"};
	}

}
