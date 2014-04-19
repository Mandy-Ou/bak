package com.cmw.entity.fininter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 币别
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="币别实体",createDate="2013-03-28T00:00:00",author="程明卫")
@Entity
@Table(name="fs_Currency")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class CurrencyEntity extends IdBaseEntity {
	
	
	 @Description(remark="财务系统ID")
	 @Column(name="finsysId" )
	 private Long finsysId;

	 @Description(remark="币别代码")
	 @Column(name="code" ,length=20 )
	 private String code;

	 @Description(remark="币别名称")
	 @Column(name="name" ,length=50 )
	 private String name;

	 @Description(remark="汇率")
	 @Column(name="erate" ,scale=2)
	 private Double erate = 1D;

	 @Description(remark="小数位数")
	 @Column(name="fscale" )
	 private Integer fscale = 2;

	 @Description(remark="财务币别ID")
	 @Column(name="refId" )
	 private Long refId;


	public CurrencyEntity() {

	}

	
	/**
	  * 设置财务系统ID的值
	 * @param 	finsysId	 财务系统ID
	**/
	public void setFinsysId(Long  finsysId){
		 this.finsysId=finsysId;
 	}

	/**
	  * 获取财务系统ID的值
	 * @return 返回财务系统ID的值
	**/
	public Long getFinsysId(){
		 return finsysId;
 	}

	/**
	  * 设置币别代码的值
	 * @param 	code	 币别代码
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取币别代码的值
	 * @return 返回币别代码的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置币别名称的值
	 * @param 	name	 币别名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取币别名称的值
	 * @return 返回币别名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置汇率的值
	 * @param 	erate	 汇率
	**/
	public void setErate(Double  erate){
		 this.erate=erate;
 	}

	/**
	  * 获取汇率的值
	 * @return 返回汇率的值
	**/
	public Double getErate(){
		 return erate;
 	}

	/**
	  * 设置小数位数的值
	 * @param 	fscale	 小数位数
	**/
	public void setFscale(Integer  fscale){
		 this.fscale=fscale;
 	}

	/**
	  * 获取小数位数的值
	 * @return 返回小数位数的值
	**/
	public Integer getFscale(){
		 return fscale;
 	}

	/**
	  * 设置财务币别ID的值
	 * @param 	refId	 财务币别ID
	**/
	public void setRefId(Long  refId){
		 this.refId=refId;
 	}

	/**
	  * 获取财务币别ID的值
	 * @return 返回财务币别ID的值
	**/
	public Long getRefId(){
		 return refId;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{finsysId,getId(),code,name,erate,fscale,refId,isenabled,createTime};
	}

	@Override
	public String[] getFields() {
		return new String[]{"finsysId","id","code","name","erate","fscale","refId","isenabled","createTime#yyyy-MM-dd HH:mm"};
	}

}
