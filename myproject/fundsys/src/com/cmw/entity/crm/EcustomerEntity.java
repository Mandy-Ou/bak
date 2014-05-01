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
 * 企业客户基本信息
 * @author pdh
 * @date 2012-12-21T00:00:00
 */
@Description(remark="企业客户基本信息实体",createDate="2012-12-21T00:00:00",author="pdh")
@Entity
@Table(name="crm_ecustomer")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class EcustomerEntity extends IdBaseEntity {
	
	
	 @Description(remark="客户基础信息ID")
	 @Column(name="baseId" ,nullable=false )
	 private Long baseId;

	 @Description(remark="流水号")
	 @Column(name="serialNum" ,length=20 )
	 private String serialNum;

	 @Description(remark="企业名称")
	 @Column(name="name" ,nullable=false ,length=100 )
	 private String name;

	 @Description(remark="企业简称")
	 @Column(name="shortName",length=50 )
	 private String shortName;

	 @Description(remark="拼音助记码")
	 @Column(name="mnemonic" ,length=20 )
	 private String mnemonic;

	 @Description(remark="工商登记号")
	 @Column(name="tradNumber" ,nullable=false ,length=50 )
	 private String tradNumber;

	 @Description(remark="组织机构代码")
	 @Column(name="orgcode" ,nullable=false ,length=20 )
	 private String orgcode;

	 @Description(remark="所属行业")
	 @Column(name="trade" )
	 private Long trade;

	 @Description(remark="企业性质")
	 @Column(name="kind" )
	 private Long kind;

	 @Description(remark="地址")
	 @Column(name="address" ,length=200 )
	 private String address;
	 
	 @Description(remark="街道地址")
	 @Column(name="inAddress" ,length=200 )
	 private String inAddress;
	 
	 @Description(remark="成立时间")
	 @Column(name="comeTime" )
	 private Date comeTime;

	 @Description(remark="联系人")
	 @Column(name="contactor" ,length=30 )
	 private String contactor;

	 @Description(remark="联系人手机")
	 @Column(name="phone" ,length=30 )
	 private String phone;

	 @Description(remark="联系电话")
	 @Column(name="contacttel" ,length=20 )
	 private String contacttel;

	 @Description(remark="传真")
	 @Column(name="fax" ,length=20 )
	 private String fax;

	 @Description(remark="电子邮件")
	 @Column(name="email" ,length=30 )
	 private String email;

	 @Description(remark="邮编")
	 @Column(name="zipCode" ,length=10 )
	 private String zipCode;

	 @Description(remark="法定代表人")
	 @Column(name="legalMan" ,length=30 )
	 private String legalMan;

	 @Description(remark="法人身份证")
	 @Column(name="legalIdCard" ,length=20 )
	 private String legalIdCard;

	 @Description(remark="法人联系电话")
	 @Column(name="legalTel" ,length=20 )
	 private String legalTel;

	 @Description(remark="总经理")
	 @Column(name="managerName" ,length=30 )
	 private String managerName;

	 @Description(remark="总经理身份证 ")
	 @Column(name="managerIdCard" ,length=20 )
	 private String managerIdCard;

	 @Description(remark="总经理联系电话")
	 @Column(name="managerTel" ,length=20 )
	 private String managerTel;

	 @Description(remark="财务经理")
	 @Column(name="finaManager" ,length=30 )
	 private String finaManager;

	 @Description(remark="身份证")
	 @Column(name="finaIdCard" ,length=20 )
	 private String finaIdCard;

	 @Description(remark="联系电话")
	 @Column(name="finaTel" ,length=20 )
	 private String finaTel;

	 @Description(remark="网址")
	 @Column(name="url" ,length=100 )
	 private String url;

	 @Description(remark="国税登记号")
	 @Column(name="taxNumber" ,length=30 )
	 private String taxNumber;

	 @Description(remark="地税登记号")
	 @Column(name="areaNumber" ,length=30 )
	 private String areaNumber;

	 @Description(remark="经营许可证")
	 @Column(name="licence" ,length=30 )
	 private String licence;

	 @Description(remark="许可证颁发时间")
	 @Column(name="licencedate" )
	 private Date licencedate;

	 @Description(remark="注册地址")
	 @Column(name="regaddress" ,length=100 )
	 private String regaddress;

	 @Description(remark="注册资本")
	 @Column(name="regcapital" ,scale=2)
	 private BigDecimal  regcapital;

	 @Description(remark="注册资金币种")
	 @Column(name="currency" )
	 private Long currency;

	 @Description(remark="实收资本")
	 @Column(name="incapital" ,scale=2)
	 private BigDecimal  incapital= new BigDecimal(0);

	 @Description(remark="专利证书号码")
	 @Column(name="patentNumber" ,length=30 )
	 private String patentNumber;

	 @Description(remark="月租金")
	 @Column(name="rent",scale=2)
	 private BigDecimal rent = new BigDecimal(0);

	 @Description(remark="贷款银行")
	 @Column(name="loanBank" ,length=100 )
	 private String loanBank;

	 @Description(remark="贷款卡号")
	 @Column(name="loanNumber" ,length=30 )
	 private String loanNumber;

	 @Description(remark="贷款卡记录")
	 @Column(name="loanLog" ,length=10 )
	 private String loanLog;

	 @Description(remark="企业人数")
	 @Column(name="manamount" )
	 private Integer manamount=0;

	 @Description(remark="在职员工人数")
	 @Column(name="empCount" )
	 private Integer empCount=0;

	 @Description(remark="社保参保人数")
	 @Column(name="insCount" )
	 private Integer insCount=0;

	 @Description(remark="登记时间")
	 @Column(name="registerTime" )
	 private Date registerTime;

	 @Description(remark="登记人")
	 @Column(name="regman" )
	 private Long regman;

	 @Description(remark="状态")
	 @Column(name="state" ,nullable=false )
	 private Integer state = 0;


	public EcustomerEntity() {

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
	  * 设置企业名称的值
	 * @param 	name	 企业名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取企业名称的值
	 * @return 返回企业名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置企业简称的值
	 * @param 	shortName	 企业简称
	**/
	public void setShortName(String  shortName){
		 this.shortName=shortName;
 	}

	/**
	  * 获取企业简称的值
	 * @return 返回企业简称的值
	**/
	public String getShortName(){
		 return shortName;
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
	  * 设置工商登记号的值
	 * @param 	tradNumber	 工商登记号
	**/
	public void setTradNumber(String  tradNumber){
		 this.tradNumber=tradNumber;
 	}

	/**
	  * 获取工商登记号的值
	 * @return 返回工商登记号的值
	**/
	public String getTradNumber(){
		 return tradNumber;
 	}

	/**
	  * 设置组织机构代码的值
	 * @param 	orgcode	 组织机构代码
	**/
	public void setOrgcode(String  orgcode){
		 this.orgcode=orgcode;
 	}

	/**
	  * 获取组织机构代码的值
	 * @return 返回组织机构代码的值
	**/
	public String getOrgcode(){
		 return orgcode;
 	}

	/**
	  * 设置所属行业的值
	 * @param 	trade	 所属行业
	**/
	public void setTrade(Long  trade){
		 this.trade=trade;
 	}

	/**
	  * 获取所属行业的值
	 * @return 返回所属行业的值
	**/
	public Long getTrade(){
		 return trade;
 	}

	/**
	  * 设置企业性质的值
	 * @param 	kind	 企业性质
	**/
	public void setKind(Long  kind){
		 this.kind=kind;
 	}

	/**
	  * 获取企业性质的值
	 * @return 返回企业性质的值
	**/
	public Long getKind(){
		 return kind;
 	}

	/**
	  * 设置地址的值
	 * @param 	address	 地址
	**/
	public void setAddress(String  address){
		 this.address=address;
 	}

	/**
	  * 获取地址的值
	 * @return 返回地址的值
	**/
	public String getAddress(){
		 return address;
 	}
	
	/**
	  * 设置地址的值
	 * @param 	address	 地址
	**/
	public void setInAddress(String  inAddress){
		 this.inAddress=inAddress;
	}

	/**
	  * 获取地址的值
	 * @return 返回地址的值
	**/
	public String getInAddress(){
		 return inAddress;
	}
	
	/**
	  * 设置成立时间的值
	 * @param 	comeTime	 成立时间
	**/
	public void setComeTime(Date  comeTime){
		 this.comeTime=comeTime;
 	}

	/**
	  * 获取成立时间的值
	 * @return 返回成立时间的值
	**/
	public Date getComeTime(){
		 return comeTime;
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
	  * 设置联系人手机的值
	 * @param 	phone	 联系人手机
	**/
	public void setPhone(String  phone){
		 this.phone=phone;
 	}

	/**
	  * 获取联系人手机的值
	 * @return 返回联系人手机的值
	**/
	public String getPhone(){
		 return phone;
 	}

	/**
	  * 设置联系电话的值
	 * @param 	contacttel	 联系电话
	**/
	public void setContacttel(String  contacttel){
		 this.contacttel=contacttel;
 	}

	/**
	  * 获取联系电话的值
	 * @return 返回联系电话的值
	**/
	public String getContacttel(){
		 return contacttel;
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
	  * 设置邮编的值
	 * @param 	zipCode	 邮编
	**/
	public void setZipCode(String  zipCode){
		 this.zipCode=zipCode;
 	}

	/**
	  * 获取邮编的值
	 * @return 返回邮编的值
	**/
	public String getZipCode(){
		 return zipCode;
 	}

	/**
	  * 设置法定代表人的值
	 * @param 	legalMan	 法定代表人
	**/
	public void setLegalMan(String  legalMan){
		 this.legalMan=legalMan;
 	}

	/**
	  * 获取法定代表人的值
	 * @return 返回法定代表人的值
	**/
	public String getLegalMan(){
		 return legalMan;
 	}

	/**
	  * 设置法人身份证的值
	 * @param 	legalIdCard	 法人身份证
	**/
	public void setLegalIdCard(String  legalIdCard){
		 this.legalIdCard=legalIdCard;
 	}

	/**
	  * 获取法人身份证的值
	 * @return 返回法人身份证的值
	**/
	public String getLegalIdCard(){
		 return legalIdCard;
 	}

	/**
	  * 设置法人联系电话的值
	 * @param 	legalTel	 法人联系电话
	**/
	public void setLegalTel(String  legalTel){
		 this.legalTel=legalTel;
 	}

	/**
	  * 获取法人联系电话的值
	 * @return 返回法人联系电话的值
	**/
	public String getLegalTel(){
		 return legalTel;
 	}

	/**
	  * 设置总经理的值
	 * @param 	managerName	 总经理
	**/
	public void setManagerName(String  managerName){
		 this.managerName=managerName;
 	}

	/**
	  * 获取总经理的值
	 * @return 返回总经理的值
	**/
	public String getManagerName(){
		 return managerName;
 	}

	/**
	  * 设置总经理身份证 的值
	 * @param 	managerIdCard	 总经理身份证 
	**/
	public void setManagerIdCard(String  managerIdCard){
		 this.managerIdCard=managerIdCard;
 	}

	/**
	  * 获取总经理身份证 的值
	 * @return 返回总经理身份证 的值
	**/
	public String getManagerIdCard(){
		 return managerIdCard;
 	}

	/**
	  * 设置总经理联系电话的值
	 * @param 	managerTel	 总经理联系电话
	**/
	public void setManagerTel(String  managerTel){
		 this.managerTel=managerTel;
 	}

	/**
	  * 获取总经理联系电话的值
	 * @return 返回总经理联系电话的值
	**/
	public String getManagerTel(){
		 return managerTel;
 	}

	/**
	  * 设置财务经理的值
	 * @param 	finaManager	 财务经理
	**/
	public void setFinaManager(String  finaManager){
		 this.finaManager=finaManager;
 	}

	/**
	  * 获取财务经理的值
	 * @return 返回财务经理的值
	**/
	public String getFinaManager(){
		 return finaManager;
 	}

	/**
	  * 设置身份证的值
	 * @param 	finaIdCard	 身份证
	**/
	public void setFinaIdCard(String  finaIdCard){
		 this.finaIdCard=finaIdCard;
 	}

	/**
	  * 获取身份证的值
	 * @return 返回身份证的值
	**/
	public String getFinaIdCard(){
		 return finaIdCard;
 	}

	/**
	  * 设置联系电话的值
	 * @param 	finaTel	 联系电话
	**/
	public void setFinaTel(String  finaTel){
		 this.finaTel=finaTel;
 	}

	/**
	  * 获取联系电话的值
	 * @return 返回联系电话的值
	**/
	public String getFinaTel(){
		 return finaTel;
 	}

	/**
	  * 设置网址的值
	 * @param 	url	 网址
	**/
	public void setUrl(String  url){
		 this.url=url;
 	}

	/**
	  * 获取网址的值
	 * @return 返回网址的值
	**/
	public String getUrl(){
		 return url;
 	}

	/**
	  * 设置国税登记号的值
	 * @param 	taxNumber	 国税登记号
	**/
	public void setTaxNumber(String  taxNumber){
		 this.taxNumber=taxNumber;
 	}

	/**
	  * 获取国税登记号的值
	 * @return 返回国税登记号的值
	**/
	public String getTaxNumber(){
		 return taxNumber;
 	}

	/**
	  * 设置地税登记号的值
	 * @param 	areaNumber	 地税登记号
	**/
	public void setAreaNumber(String  areaNumber){
		 this.areaNumber=areaNumber;
 	}

	/**
	  * 获取地税登记号的值
	 * @return 返回地税登记号的值
	**/
	public String getAreaNumber(){
		 return areaNumber;
 	}

	/**
	  * 设置经营许可证的值
	 * @param 	licence	 经营许可证
	**/
	public void setLicence(String  licence){
		 this.licence=licence;
 	}

	/**
	  * 获取经营许可证的值
	 * @return 返回经营许可证的值
	**/
	public String getLicence(){
		 return licence;
 	}

	/**
	  * 设置许可证颁发时间的值
	 * @param 	licencedate	 许可证颁发时间
	**/
	public void setLicencedate(Date  licencedate){
		 this.licencedate=licencedate;
 	}

	/**
	  * 获取许可证颁发时间的值
	 * @return 返回许可证颁发时间的值
	**/
	public Date getLicencedate(){
		 return licencedate;
 	}

	/**
	  * 设置注册地址的值
	 * @param 	regaddress	 注册地址
	**/
	public void setRegaddress(String  regaddress){
		 this.regaddress=regaddress;
 	}

	/**
	  * 获取注册地址的值
	 * @return 返回注册地址的值
	**/
	public String getRegaddress(){
		 return regaddress;
 	}

	/**
	  * 设置注册资本的值
	 * @param 	regcapital	 注册资本
	**/
	public void setRegcapital(BigDecimal   regcapital){
		 this.regcapital=regcapital;
 	}

	/**
	  * 获取注册资本的值
	 * @return 返回注册资本的值
	**/
	public BigDecimal  getRegcapital(){
		 return regcapital;
 	}

	/**
	  * 设置注册资金币种的值
	 * @param 	currency	 注册资金币种
	**/
	public void setCurrency(Long  currency){
		 this.currency=currency;
 	}

	/**
	  * 获取注册资金币种的值
	 * @return 返回注册资金币种的值
	**/
	public Long getCurrency(){
		 return currency;
 	}

	/**
	  * 设置实收资本的值
	 * @param 	incapital	 实收资本
	**/
	public void setIncapital(BigDecimal   incapital){
		 this.incapital=incapital;
 	}

	/**
	  * 获取实收资本的值
	 * @return 返回实收资本的值
	**/
	public BigDecimal  getIncapital(){
		 return incapital;
 	}

	/**
	  * 设置专利证书号码的值
	 * @param 	patentNumber	 专利证书号码
	**/
	public void setPatentNumber(String  patentNumber){
		 this.patentNumber=patentNumber;
 	}

	/**
	  * 获取专利证书号码的值
	 * @return 返回专利证书号码的值
	**/
	public String getPatentNumber(){
		 return patentNumber;
 	}

	/**
	  * 设置月租金的值
	 * @param 	rent	 月租金
	**/
	public void setRent(BigDecimal  rent){
		 this.rent=rent;
 	}

	/**
	  * 获取月租金的值
	 * @return 返回月租金的值
	**/
	public BigDecimal getRent(){
		 return rent;
 	}

	/**
	  * 设置贷款银行的值
	 * @param 	loanBank	 贷款银行
	**/
	public void setLoanBank(String  loanBank){
		 this.loanBank=loanBank;
 	}

	/**
	  * 获取贷款银行的值
	 * @return 返回贷款银行的值
	**/
	public String getLoanBank(){
		 return loanBank;
 	}

	/**
	  * 设置贷款卡号的值
	 * @param 	loanNumber	 贷款卡号
	**/
	public void setLoanNumber(String  loanNumber){
		 this.loanNumber=loanNumber;
 	}

	/**
	  * 获取贷款卡号的值
	 * @return 返回贷款卡号的值
	**/
	public String getLoanNumber(){
		 return loanNumber;
 	}

	/**
	  * 设置贷款卡记录的值
	 * @param 	loanLog	 贷款卡记录
	**/
	public void setLoanLog(String  loanLog){
		 this.loanLog=loanLog;
 	}

	/**
	  * 获取贷款卡记录的值
	 * @return 返回贷款卡记录的值
	**/
	public String getLoanLog(){
		 return loanLog;
 	}

	/**
	  * 设置企业人数的值
	 * @param 	manamount	 企业人数
	**/
	public void setManamount(Integer  manamount){
		 this.manamount=manamount;
 	}

	/**
	  * 获取企业人数的值
	 * @return 返回企业人数的值
	**/
	public Integer getManamount(){
		 return manamount;
 	}

	/**
	  * 设置在职员工人数的值
	 * @param 	empCount	 在职员工人数
	**/
	public void setEmpCount(Integer  empCount){
		 this.empCount=empCount;
 	}

	/**
	  * 获取在职员工人数的值
	 * @return 返回在职员工人数的值
	**/
	public Integer getEmpCount(){
		 return empCount;
 	}

	/**
	  * 设置社保参保人数的值
	 * @param 	insCount	 社保参保人数
	**/
	public void setInsCount(Integer  insCount){
		 this.insCount=insCount;
 	}

	/**
	  * 获取社保参保人数的值
	 * @return 返回社保参保人数的值
	**/
	public Integer getInsCount(){
		 return insCount;
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
		return new Object[]{id,baseId,serialNum,name,shortName,mnemonic,tradNumber,orgcode,trade,kind,address,inAddress,comeTime,contactor,phone,contacttel,fax,email,zipCode,legalMan,legalIdCard,legalTel,managerName,managerIdCard,managerTel,finaManager,finaIdCard,finaTel,url,taxNumber,areaNumber,licence,licencedate,regaddress,regcapital,currency,incapital,patentNumber,rent,loanBank,loanNumber,loanLog,manamount,empCount,insCount,registerTime,regman,state};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","baseId","serialNum","name","shortName","mnemonic","tradNumber","orgcode","trade","kind","address","inAddress","comeTime","contactor","phone","contacttel","fax","email","zipCode","legalMan","legalIdCard","legalTel","managerName","managerIdCard","managerTel","finaManager","finaIdCard","finaTel","url","taxNumber","areaNumber","licence","licencedate","regaddress","regcapital","currency","incapital","patentNumber","rent","loanBank","loanNumber","loanLog","manamount","empCount","insCount","registerTime#yyyy-MM-dd","regman","state"};
	}

}
