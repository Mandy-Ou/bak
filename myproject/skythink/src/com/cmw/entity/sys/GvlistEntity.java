package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 基础数据
 * @author 彭登浩
 * @date 2012-11-19T00:00:00
 */
@Description(remark="基础数据实体",createDate="2012-11-19T00:00:00",author="彭登浩")
@Entity
@Table(name="ts_gvlist")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class GvlistEntity extends IdBaseEntity {
	
	
	 @Description(remark="编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;

	 @Description(remark="名称")
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;

	 @Description(remark="菜单图标")
	 @Column(name="biconCls" ,length=200 )
	 private String biconCls;


	@Description(remark="资源ID")
	 @Column(name="restypeId" ,nullable=false )
	 private Long restypeId;



	 @Description(remark="英文名称")
	 @Column(name="ename" ,length=50 )
	 private String ename;



	 @Description(remark="日文名称")
	 @Column(name="jname" ,length=50 )
	 private String jname;



	 @Description(remark="繁体中文名称")
	 @Column(name="twname" ,length=50 )
	 private String twname;



	 @Description(remark="法文名称")
	 @Column(name="fname" ,length=50 )
	 private String fname;



	 @Description(remark="韩文名称")
	 @Column(name="koname" ,length=50 )
	 private String koname;




	public GvlistEntity() {

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
	 * 获取菜单图标的值
	 * @return
	 */
	 public String getBiconCls() {
			return biconCls;
		}

	 /**
	  * 设置菜单图标的值
	  * @param biconCls
	  */
	public void setBiconCls(String biconCls) {
		this.biconCls = biconCls;
	}
	/**
	  * 设置资源ID的值
	 * @param 	restypeId	 资源ID
	**/
	public void setRestypeId(Long  restypeId){
		 this.restypeId=restypeId;
 	}

	/**
	  * 获取资源ID的值
	 * @return 返回资源ID的值
	**/
	public Long getRestypeId(){
		 return restypeId;
 	}

	/**
	  * 设置英文名称的值
	 * @param 	ename	 英文名称
	**/
	public void setEname(String  ename){
		 this.ename=ename;
 	}

	/**
	  * 获取英文名称的值
	 * @return 返回英文名称的值
	**/
	public String getEname(){
		 return ename;
 	}

	/**
	  * 设置日文名称的值
	 * @param 	jname	 日文名称
	**/
	public void setJname(String  jname){
		 this.jname=jname;
 	}

	/**
	  * 获取日文名称的值
	 * @return 返回日文名称的值
	**/
	public String getJname(){
		 return jname;
 	}

	/**
	  * 设置繁体中文名称的值
	 * @param 	twname	 繁体中文名称
	**/
	public void setTwname(String  twname){
		 this.twname=twname;
 	}

	/**
	  * 获取繁体中文名称的值
	 * @return 返回繁体中文名称的值
	**/
	public String getTwname(){
		 return twname;
 	}

	/**
	  * 设置法文名称的值
	 * @param 	fname	 法文名称
	**/
	public void setFname(String  fname){
		 this.fname=fname;
 	}

	/**
	  * 获取法文名称的值
	 * @return 返回法文名称的值
	**/
	public String getFname(){
		 return fname;
 	}

	/**
	  * 设置韩文名称的值
	 * @param 	koname	 韩文名称
	**/
	public void setKoname(String  koname){
		 this.koname=koname;
 	}

	/**
	  * 获取韩文名称的值
	 * @return 返回韩文名称的值
	**/
	public String getKoname(){
		 return koname;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,code,name,biconCls,restypeId,ename,jname,twname,fname,koname};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","code","name","biconCls","restypeId","ename","jname","twname","fname","koname"};
	}

}
