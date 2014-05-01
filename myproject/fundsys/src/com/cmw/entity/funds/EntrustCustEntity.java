package com.cmw.entity.funds;


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
 * 委托客户资料
 * @author 李听
 * @date 2014-01-15T00:00:00
 */
@Description(remark="委托客户资料实体",createDate="2014-01-15T00:00:00",author="李听")
@Entity
@Table(name="fu_EntrustCust")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class EntrustCustEntity extends IdBaseEntity {
	
	
	 @Description(remark="编号")
	 @Column(name="code" ,length=20 )
	 private String code;

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
	 @Column(name="birthday" ,nullable=false )
	 private Date birthday;

	 @Description(remark="身份证号码")
	 @Column(name="cardNum" ,nullable=false ,length=50 )
	 private String cardNum;

	 @Description(remark="委托金额")
	 @Column(name="appAmount" ,nullable=false )
	 private BigDecimal appAmount = new BigDecimal(0);

	 @Description(remark="委托期限")
	 @Column(name="deadline" ,nullable=false )
	 private Integer deadline = 0;

	 @Description(remark="期限类型")
	 @Column(name="dlimit" ,nullable=false )
	 private Integer dlimit;
	 

	 @Description(remark="委托人类型")
	 @Column(name="ctype" ,nullable=false )
	 private Integer ctype;

	 public Integer getCtype() {
		return ctype;
	}


	public void setCtype(Integer ctype) {
		this.ctype = ctype;
	}

	@Description(remark="委托产品范围")
	 @Column(name="prange" ,nullable=false )
	 private Integer prange = 1;

	 @Description(remark="委托产品")
	 @Column(name="products" ,length=50 )
	 private String products;

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

	 @Description(remark="现居住地址")
	 @Column(name="inAddress" ,length=150 )
	 private String inAddress;

	 @Description(remark="住宅号")
	 @Column(name="homeNo" ,length=50 )
	 private String homeNo;

	 @Description(remark="邮政编码")
	 @Column(name="zipcode" ,length=10 )
	 private String zipcode;

	 @Description(remark="传真")
	 @Column(name="fax" ,length=20 )
	 private String fax;

	 @Description(remark="QQ或MSN号码")
	 @Column(name="qqmsnNum" ,length=20 )
	 private String qqmsnNum;

	 @Description(remark="子业务流程ID")
	 @Column(name="breed" ,nullable=false )
	 private Long breed;

	 @Description(remark="流程实例ID")
	 @Column(name="procId",length=50 )
	 private String procId;

	 @Description(remark="状态")
	 @Column(name="status" ,nullable=false )
	 private Long status = 0L;


	public EntrustCustEntity() {

	}

	
	/**
	  * 设置编号的值
	 * @param 	code	 编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取编号的值
	 * @return 返回编号的值
	**/
	public String getCode(){
		 return code;
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
	  * 设置身份证号码的值
	 * @param 	cardNum	 身份证号码
	**/
	public void setCardNum(String  cardNum){
		 this.cardNum=cardNum;
 	}

	/**
	  * 获取身份证号码的值
	 * @return 返回身份证号码的值
	**/
	public String getCardNum(){
		 return cardNum;
 	}

	/**
	  * 设置委托金额的值
	 * @param 	appAmount	 委托金额
	**/
	public void setAppAmount(BigDecimal  appAmount){
		 this.appAmount=appAmount;
 	}

	/**
	  * 获取委托金额的值
	 * @return 返回委托金额的值
	**/
	public BigDecimal getAppAmount(){
		 return appAmount;
 	}

	/**
	  * 设置委托期限的值
	 * @param 	deadline	 委托期限
	**/
	public void setDeadline(Integer  deadline){
		 this.deadline=deadline;
 	}

	/**
	  * 获取委托期限的值
	 * @return 返回委托期限的值
	**/
	public Integer getDeadline(){
		 return deadline;
 	}

	/**
	  * 设置期限类型的值
	 * @param 	dlimit	 期限类型
	**/
	public void setDlimit(Integer  dlimit){
		 this.dlimit=dlimit;
 	}

	/**
	  * 获取期限类型的值
	 * @return 返回期限类型的值
	**/
	public Integer getDlimit(){
		 return dlimit;
 	}

	/**
	  * 设置委托产品范围的值
	 * @param 	prange	 委托产品范围
	**/
	public void setPrange(Integer  prange){
		 this.prange=prange;
 	}

	/**
	  * 获取委托产品范围的值
	 * @return 返回委托产品范围的值
	**/
	public Integer getPrange(){
		 return prange;
 	}

	/**
	  * 设置委托产品的值
	 * @param 	products	 委托产品
	**/
	public void setProducts(String  products){
		 this.products=products;
 	}

	/**
	  * 获取委托产品的值
	 * @return 返回委托产品的值
	**/
	public String getProducts(){
		 return products;
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
	  * 设置现居住地址的值
	 * @param 	inAddress	 现居住地址
	**/
	public void setInAddress(String  inAddress){
		 this.inAddress=inAddress;
 	}

	/**
	  * 获取现居住地址的值
	 * @return 返回现居住地址的值
	**/
	public String getInAddress(){
		 return inAddress;
 	}

	/**
	  * 设置住宅号的值
	 * @param 	homeNo	 住宅号
	**/
	public void setHomeNo(String  homeNo){
		 this.homeNo=homeNo;
 	}

	/**
	  * 获取住宅号的值
	 * @return 返回住宅号的值
	**/
	public String getHomeNo(){
		 return homeNo;
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
	  * 设置子业务流程ID的值
	 * @param 	breed	 子业务流程ID
	**/
	public void setBreed(Long  breed){
		 this.breed=breed;
 	}

	/**
	  * 获取子业务流程ID的值
	 * @return 返回子业务流程ID的值
	**/
	public Long getBreed(){
		 return breed;
 	}


	public String getProcId() {
		return procId;
	}


	public void setProcId(String procId) {
		this.procId = procId;
	}


	/**
	  * 设置状态的值
	 * @param 	status	 状态
	**/
	public void setStatus(Long  status){
		 this.status=status;
 	}
	/**
	  * 获取状态的值
	 * @return 返回状态的值
	**/
	public Long getStatus(){
		 return status;
 	}


	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,code,name,mnemonic,ctype,sex,birthday,cardNum,appAmount,deadline,dlimit,prange,products,maristal,hometown,nation,degree,job,workOrg,workAddress,phone,contactTel,contactor,email,inAddress,homeNo,zipcode,fax,qqmsnNum,breed,procId,status};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","code","name","mnemonic","ctype","sex","birthday#yyyy-MM-dd","cardNum","appAmount","deadline","dlimit","prange","products","maristal","hometown","nation","degree","job","workOrg","workAddress","phone","contactTel","contactor","email","inAddress","homeNo","zipcode","fax","qqmsnNum","breed","procId","status"};
	}

}
