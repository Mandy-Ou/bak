package com.cmw.entity.sys;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 公司
 * @author chengmingwei
 * @date 2011-09-24T00:00:00
 */
@Description(remark="公司实体",createDate="2011-09-24T00:00:00",author="chengmingwei")
@Entity
@Table(name="ts_company")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class CompanyEntity extends IdBaseEntity {
	
	public static final Integer COMPANY_POID_2 = 2;
	//公司编号
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;



	 //公司名称
	 @Column(name="name" ,nullable=false ,length=150 )
	 private String name;



	 //隶属关系
	 @Column(name="affiliation" ,nullable=false )
	 private Integer affiliation;



	 //上级单位类型
	 @Column(name="potype" )
	 private Integer potype;



	 //上级单位ID
	 @Column(name="poid" )
	 private Long poid = 0L;



	 //拼音助记码
	 @Column(name="mnemonic" ,length=100 )
	 private String mnemonic;



	 //组织机构码
	 @Column(name="orgcode" ,length=20 )
	 private String orgcode;



	 //商登记号
	 @Column(name="offnum" ,length=50 )
	 private String offnum;



	 //成立时间
	 @Column(name="builddate" )
	 private Date builddate;



	 //公司法人
	 @Column(name="legal" ,length=50 )
	 private String legal;



	 //联系人
	 @Column(name="contactor" ,length=20 )
	 private String contactor;



	 //联系电话
	 @Column(name="tel" ,length=20 )
	 private String tel;



	 //公司邮编
	 @Column(name="zipcode" ,length=20 )
	 private String zipcode;



	 //邮箱
	 @Column(name="email" ,length=50 )
	 private String email;



	 //公司地址
	 @Column(name="address" ,length=200 )
	 private String address;



	 //公司网址
	 @Column(name="url" ,length=200 )
	 private String url;




	public CompanyEntity() {

	}

	
	/**
	  * 设置公司编号的值
	 * @param 	code	 公司编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取公司编号的值
	 * @return 返回公司编号的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置公司名称的值
	 * @param 	name	 公司名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取公司名称的值
	 * @return 返回公司名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置隶属关系的值
	 * @param 	affiliation	 隶属关系
	**/
	public void setAffiliation(Integer  affiliation){
		 this.affiliation=affiliation;
 	}

	/**
	  * 获取隶属关系的值
	 * @return 返回隶属关系的值
	**/
	public Integer getAffiliation(){
		 return affiliation;
 	}

	/**
	  * 设置上级单位类型的值
	 * @param 	potype	 上级单位类型
	**/
	public void setPotype(Integer  potype){
		 this.potype=potype;
 	}

	/**
	  * 获取上级单位类型的值
	 * @return 返回上级单位类型的值
	**/
	public Integer getPotype(){
		 return potype;
 	}

	/**
	  * 设置上级单位ID的值
	 * @param 	poid	 上级单位ID
	**/
	public void setPoid(Long  poid){
		 this.poid=poid;
 	}

	/**
	  * 获取上级单位ID的值
	 * @return 返回上级单位ID的值
	**/
	public Long getPoid(){
		 return poid;
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
	  * 设置组织机构码的值
	 * @param 	orgcode	 组织机构码
	**/
	public void setOrgcode(String  orgcode){
		 this.orgcode=orgcode;
 	}

	/**
	  * 获取组织机构码的值
	 * @return 返回组织机构码的值
	**/
	public String getOrgcode(){
		 return orgcode;
 	}

	/**
	  * 设置商登记号的值
	 * @param 	offnum	 商登记号
	**/
	public void setOffnum(String  offnum){
		 this.offnum=offnum;
 	}

	/**
	  * 获取商登记号的值
	 * @return 返回商登记号的值
	**/
	public String getOffnum(){
		 return offnum;
 	}

	/**
	  * 设置成立时间的值
	 * @param 	builddate	 成立时间
	**/
	public void setBuilddate(Date  builddate){
		 this.builddate=builddate;
 	}

	/**
	  * 获取成立时间的值
	 * @return 返回成立时间的值
	**/
	public Date getBuilddate(){
		 return builddate;
 	}

	/**
	  * 设置公司法人的值
	 * @param 	legal	 公司法人
	**/
	public void setLegal(String  legal){
		 this.legal=legal;
 	}

	/**
	  * 获取公司法人的值
	 * @return 返回公司法人的值
	**/
	public String getLegal(){
		 return legal;
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
	  * 设置联系电话的值
	 * @param 	tel	 联系电话
	**/
	public void setTel(String  tel){
		 this.tel=tel;
 	}

	/**
	  * 获取联系电话的值
	 * @return 返回联系电话的值
	**/
	public String getTel(){
		 return tel;
 	}

	/**
	  * 设置公司邮编的值
	 * @param 	zipcode	 公司邮编
	**/
	public void setZipcode(String  zipcode){
		 this.zipcode=zipcode;
 	}

	/**
	  * 获取公司邮编的值
	 * @return 返回公司邮编的值
	**/
	public String getZipcode(){
		 return zipcode;
 	}

	/**
	  * 设置邮箱的值
	 * @param 	email	 邮箱
	**/
	public void setEmail(String  email){
		 this.email=email;
 	}

	/**
	  * 获取邮箱的值
	 * @return 返回邮箱的值
	**/
	public String getEmail(){
		 return email;
 	}

	/**
	  * 设置公司地址的值
	 * @param 	address	 公司地址
	**/
	public void setAddress(String  address){
		 this.address=address;
 	}

	/**
	  * 获取公司地址的值
	 * @return 返回公司地址的值
	**/
	public String getAddress(){
		 return address;
 	}

	/**
	  * 设置公司网址的值
	 * @param 	url	 公司网址
	**/
	public void setUrl(String  url){
		 this.url=url;
 	}

	/**
	  * 获取公司网址的值
	 * @return 返回公司网址的值
	**/
	public String getUrl(){
		 return url;
 	}

	/**
	 * 公司类型————>0:表示总公司
	 * 
	 */
	 public static final Integer COMPANY_AFFILIATION_0 = 0;
	 /**
	 * 公司类型————>1:表示分公司
	 * 
	*/
	public static final Integer COMPANY_AFFILIATION_1 = 1;
	/**
	 * 上级单位类型————>0:表示无上级
	 * 
	*/
	 public static final Integer COMPANY_POTYPE_0 = 0;
	 /**
	 * 上级单位类型————>1:表示公司
	 * 
	*/
	 public static final Integer COMPANY_POTYPE_1 = 1;
	 /**
	 * 公司类型————>1:表示部门
	 * 
	*/
	public static final Integer COMPANY_POTYPE_2 = 2;
	/**
	 * 上级单位类型————>0:表示无上级
	 * 
	*/
	 public static final Integer COMPANY_POID_0 = 0;
	 /**
	 * 上级单位类型————>1:表示公司iD
	 * 
	*/
	 public static final Integer COMPANY_POID_1 = 1;
	 /**
	 * 公司类型————>1:表示部门id
	 * 
	*/
	 
	@Override
	public Object[] getDatas() {
		return new Object[]{id,code,name,affiliation,potype,poid,mnemonic,orgcode,offnum,builddate,legal,contactor,tel,zipcode,email,address,url};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","code","name","affiliation","potype","poid","mnemonic","orgcode","offnum","builddate","legal","contactor","tel","zipcode","email","address","url"};
	}

}
