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
 * 月份
 * @author 彭登涛
 * @date 2013-02-25T00:00:00
 */
@Description(remark="月份实体",createDate="2013-02-25T00:00:00",author="彭登涛")
@Entity
@Table(name="ts_moth")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class mothEntity extends IdBaseEntity {
	
	
	 @Description(remark="月份")
	 @Column(name="moth" ,nullable=false )
	 private Date moth;
	 
	 @Description(remark="编号")
	 @Column(name="code" ,nullable=false )
	 private String code;
	 
	 @Description(remark="填报人")
	 @Column(name="tbr" ,nullable=false ,length=50 )
	 private String tbr;

	 @Description(remark="填报日期")
	 @Column(name="date" )
	 private Date date;

	 @Description(remark="填报单位")
	 @Column(name="tbdw" ,nullable=false ,length=50 )
	 private String tbdw;

	 @Description(remark="经营品牌")
	 @Column(name="jypp" ,length=50 )
	 private String jypp;

	 @Description(remark="所在地区")
	 @Column(name="szdq" ,length=50 )
	 private String szdq;

	 @Description(remark="新车台数")
	 @Column(name="xcts" )
	 private Integer xcts;

	 @Description(remark="新车批发数量 ")
	 @Column(name="xcpfsl" )
	 private Integer xcpfsl;

	 @Description(remark="维修总台次")
	 @Column(name="wxzts" )
	 private Integer wxzts;

	 @Description(remark="维修总产值")
	 @Column(name="wxzcz" ,scale=2)
	 private Double wxzcz;


	public mothEntity() {

	}

	
	/**
	  * 设置月份的值
	 * @param 	moth	 月份
	**/
	public void setMoth(Date  moth){
		 this.moth=moth;
 	}

	/**
	  * 获取月份的值
	 * @return 返回月份的值
	**/
	public Date getMoth(){
		 return moth;
 	}
	
	/**
	  * 设置月份的值
	 * @param 	moth	 月份
	**/
	public void setCode(String  code){
		 this.code=code;
	}

	/**
	  * 获取月份的值
	 * @return 返回月份的值
	**/
	public String getCode(){
		 return code;
	}
	/**
	  * 设置填报人的值
	 * @param 	tbr	 填报人
	**/
	public void setTbr(String  tbr){
		 this.tbr=tbr;
 	}

	/**
	  * 获取填报人的值
	 * @return 返回填报人的值
	**/
	public String getTbr(){
		 return tbr;
 	}

	/**
	  * 设置填报日期的值
	 * @param 	date	 填报日期
	**/
	public void setDate(Date  date){
		 this.date=date;
 	}

	/**
	  * 获取填报日期的值
	 * @return 返回填报日期的值
	**/
	public Date getDate(){
		 return date;
 	}

	/**
	  * 设置填报单位的值
	 * @param 	tbdw	 填报单位
	**/
	public void setTbdw(String  tbdw){
		 this.tbdw=tbdw;
 	}

	/**
	  * 获取填报单位的值
	 * @return 返回填报单位的值
	**/
	public String getTbdw(){
		 return tbdw;
 	}

	/**
	  * 设置经营品牌的值
	 * @param 	jypp	 经营品牌
	**/
	public void setJypp(String  jypp){
		 this.jypp=jypp;
 	}

	/**
	  * 获取经营品牌的值
	 * @return 返回经营品牌的值
	**/
	public String getJypp(){
		 return jypp;
 	}

	/**
	  * 设置所在地区的值
	 * @param 	szdq	 所在地区
	**/
	public void setSzdq(String  szdq){
		 this.szdq=szdq;
 	}

	/**
	  * 获取所在地区的值
	 * @return 返回所在地区的值
	**/
	public String getSzdq(){
		 return szdq;
 	}

	/**
	  * 设置新车台数的值
	 * @param 	xcts	 新车台数
	**/
	public void setXcts(Integer  xcts){
		 this.xcts=xcts;
 	}

	/**
	  * 获取新车台数的值
	 * @return 返回新车台数的值
	**/
	public Integer getXcts(){
		 return xcts;
 	}

	/**
	  * 设置新车批发数量 的值
	 * @param 	xcpfsl	 新车批发数量 
	**/
	public void setXcpfsl(Integer  xcpfsl){
		 this.xcpfsl=xcpfsl;
 	}

	/**
	  * 获取新车批发数量 的值
	 * @return 返回新车批发数量 的值
	**/
	public Integer getXcpfsl(){
		 return xcpfsl;
 	}

	/**
	  * 设置维修总台次的值
	 * @param 	wxzts	 维修总台次
	**/
	public void setWxzts(Integer  wxzts){
		 this.wxzts=wxzts;
 	}

	/**
	  * 获取维修总台次的值
	 * @return 返回维修总台次的值
	**/
	public Integer getWxzts(){
		 return wxzts;
 	}

	/**
	  * 设置维修总产值的值
	 * @param 	wxzcz	 维修总产值
	**/
	public void setWxzcz(Double  wxzcz){
		 this.wxzcz=wxzcz;
 	}

	/**
	  * 获取维修总产值的值
	 * @return 返回维修总产值的值
	**/
	public Double getWxzcz(){
		 return wxzcz;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,moth,code,tbr,date,tbdw,jypp,szdq,xcts,xcpfsl,wxzts,wxzcz};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","moth","code","tbr","date","tbdw","jypp","szdq","xcts","xcpfsl","wxzts","wxzcz"};
	}

}
