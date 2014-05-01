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
 * 系统
 * @author chengmingwei
 * @date 2012-10-28T00:00:00
 */
@Description(remark="系统实体",createDate="2012-10-28T00:00:00",author="chengmingwei")
@Entity
@Table(name="ts_System")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class SystemEntity extends IdBaseEntity {
	
	
	 //系统编号
	 @Column(name="code" ,nullable=false ,length=30 )
	 private String code;



	 //系统名称
	 @Column(name="name" ,nullable=false ,length=30 )
	 private String name;



	 //拼音助记码
	 @Column(name="mnemonic" ,length=30 )
	 private String mnemonic;



	 //系统图标
	 @Column(name="icon" ,length=200 )
	 private String icon;

	 //系统地址
	 @Column(name="url" ,length=200 )
	 private String url;


	 //系统简介
	 @Column(name="synopsis" ,length=255 )
	 private String synopsis;



	 //是否有更新
	 @Column(name="hasup" ,nullable=false )
	 private Integer hasup = 0;



	 //更新方式
	 @Column(name="typeup" ,nullable=false )
	 private Integer typeup = 0;



	 //自动更新时间
	 @Column(name="autotime" )
	 private Integer autotime;



	 //最后更新时间
	 @Column(name="lastupdate" )
	 private Date lastupdate;



	 //系统类型
	 @Column(name="systype" )
	 private Integer systype;

	 //排列顺序
	 @Column(name="orderNo" )
	 private Integer orderNo;

	public SystemEntity() {

	}
	
	
	/** 获取排列顺序的值
	 * @return the orderNo
	 */
	public Integer getOrderNo() {
		return orderNo;
	}


	/** 设置排列顺序的值
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}


	/**
	  * 设置系统编号的值
	 * @param 	code	 系统编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取系统编号的值
	 * @return 返回系统编号的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置系统名称的值
	 * @param 	name	 系统名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取系统名称的值
	 * @return 返回系统名称的值
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
	  * 设置系统图标的值
	 * @param 	icon	 系统图标
	**/
	public void setIcon(String  icon){
		 this.icon=icon;
 	}

	/**
	  * 获取系统图标的值
	 * @return 返回系统图标的值
	**/
	public String getIcon(){
		 return icon;
 	}

	/**
	  * 设置系统简介的值
	 * @param 	desc	 系统简介
	**/
	public void setSynopsis(String  synopsis){
		 this.synopsis=synopsis;
 	}

	/**
	  * 获取系统简介的值
	 * @return 返回系统简介的值
	**/
	public String getSynopsis(){
		 return synopsis;
 	}

	/**
	  * 设置是否有更新的值
	 * @param 	hasup	 是否有更新
	**/
	public void setHasup(Integer  hasup){
		 this.hasup=hasup;
 	}

	/**
	  * 获取是否有更新的值
	 * @return 返回是否有更新的值
	**/
	public Integer getHasup(){
		 return hasup;
 	}

	/**
	  * 设置更新方式的值
	 * @param 	typeup	 更新方式
	**/
	public void setTypeup(Integer  typeup){
		 this.typeup=typeup;
 	}

	/**
	  * 获取更新方式的值
	 * @return 返回更新方式的值
	**/
	public Integer getTypeup(){
		 return typeup;
 	}

	/**
	  * 设置自动更新时间的值
	 * @param 	autotime	 自动更新时间
	**/
	public void setAutotime(Integer  autotime){
		 this.autotime=autotime;
 	}

	/**
	  * 获取自动更新时间的值
	 * @return 返回自动更新时间的值
	**/
	public Integer getAutotime(){
		 return autotime;
 	}

	/**
	  * 设置最后更新时间的值
	 * @param 	lastupdate	 最后更新时间
	**/
	public void setLastupdate(Date  lastupdate){
		 this.lastupdate=lastupdate;
 	}

	/**
	  * 获取最后更新时间的值
	 * @return 返回最后更新时间的值
	**/
	public Date getLastupdate(){
		 return lastupdate;
 	}

	/**
	  * 设置系统类型的值
	 * @param 	systype	 系统类型
	**/
	public void setSystype(Integer  systype){
		 this.systype=systype;
 	}

	/**
	  * 获取系统类型的值
	 * @return 返回系统类型的值
	**/
	public Integer getSystype(){
		 return systype;
 	}


	/**
	  * 获取系统地址的值
	 * @return 返回系统地址
	**/
	public String getUrl() {
		return url;
	}

	/**
	 * 设置系统地址的值
	 * @param 	url	 系统地址
	**/
	public void setUrl(String url) {
		this.url = url;
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{id,code,name,mnemonic,icon,url,synopsis,hasup,typeup,autotime,lastupdate,systype,isenabled,orderNo};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","code","name","mnemonic","icon","url","synopsis","hasup","typeup","autotime","lastupdate","systype","isenabled","orderNo"};
	}

}
