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
 * 部门
 * @author 彭登浩
 * @date 2012-11-09T00:00:00
 */
@Description(remark="部门实体",createDate="2012-11-09T00:00:00",author="彭登浩")
@Entity
@Table(name="ts_department")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class DepartmentEntity extends IdBaseEntity {
	/**
	 * 上级单位类型————>1：表示公司
	 */
	public static final Integer DEPARTMENT_POTYPE_1 = 1;
	/**
	 * 上级单位类型————>2：表示部门
	 */
	public static final Integer DEPARTMENT_POTYPE_2 = 2;
	/**
	 * 上级单位ID————>1:存放公司ID
	 */
	public static final Integer DEPARTMENT_POID_1 =  1;
	/**
	 * 上级单位ID————>2:存放部门ID
	 */
	public static final Integer DEPARTMENT_POID_2 = 2;
	 @Description(remark="编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;



	 @Description(remark="名称")
	 @Column(name="name" ,nullable=false ,length=100 )
	 private String name;



	 @Description(remark="上级单位类型")
	 @Column(name="potype" )
	 private Integer potype;



	 @Description(remark="上级单位ID")
	 @Column(name="poid" )
	 private Long poid;



	 @Description(remark="拼音助记码")
	 @Column(name="mnemonic" ,length=100 )
	 private String mnemonic;



	 @Description(remark="部门负责人")
	 @Column(name="mperson" ,length=50 )
	 private String mperson;



	 @Description(remark="成立时间")
	 @Column(name="builddate" )
	 private Date builddate;



	 @Description(remark="联系人")
	 @Column(name="contactor" ,length=20 )
	 private String contactor;



	 @Description(remark="联系电话")
	 @Column(name="tel" ,length=20 )
	 private String tel;



	 @Description(remark="邮编")
	 @Column(name="zipcode" ,length=20 )
	 private String zipcode;



	 @Description(remark="邮箱")
	 @Column(name="email" ,length=50 )
	 private String email;



	 @Description(remark="地址")
	 @Column(name="address" ,length=200 )
	 private String address;



	 @Description(remark="网址")
	 @Column(name="url" ,length=150 )
	 private String url;




	public DepartmentEntity() {

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
	  * 设置名称的值
	 * @param 	name	 名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取名称的值
	 * @return 返回名称的值
	**/
	public String getName(){
		 return name;
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
	  * 设置部门负责人的值
	 * @param 	mperson	 部门负责人
	**/
	public void setMperson(String  mperson){
		 this.mperson=mperson;
 	}

	/**
	  * 获取部门负责人的值
	 * @return 返回部门负责人的值
	**/
	public String getMperson(){
		 return mperson;
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
	  * 设置邮编的值
	 * @param 	zipcode	 邮编
	**/
	public void setZipcode(String  zipcode){
		 this.zipcode=zipcode;
 	}

	/**
	  * 获取邮编的值
	 * @return 返回邮编的值
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



	@Override
	public Object[] getDatas() {
		return new Object[]{code,name,potype,poid,mnemonic,mperson,builddate,contactor,tel,zipcode,email,address,url};
	}

	@Override
	public String[] getFields() {
		return new String[]{"code","name","potype","poid","mnemonic","mperson","builddate","contactor","tel","zipcode","email","address","url"};
	}

}
