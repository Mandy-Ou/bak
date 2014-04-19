package com.cmw.entity.finance;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 合同模板表
 * @author 赵世龙
 * @date 2013-11-19T00:00:00
 */
@Description(remark="打印模板表实体",createDate="2013-11-19T00:00:00",author="赵世龙")
@Entity
@Table(name="fc_PrintTemp")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class PrintTempEntity extends IdBaseEntity {

	 @Description(remark="模板编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;

	 @Description(remark="模板名称")
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;

	 @Description(remark="适用客户类型")
	 @Column(name="custType" ,nullable=false )
	 private Integer custType = 0;

	 @Description(remark="模板路径")
	 @Column(name="tempPath" ,length=200 )
	 private String tempPath;

	 @Description(remark="模板图标")
	 @Column(name="icon" ,length=150 )
	 private String icon;

	 @Description(remark="模板类型")
	 @Column(name="tempType" ,length=150 )
	 private String tempType;
	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	public PrintTempEntity() {

	}

	
	public String getTempType() {
		return tempType;
	}


	public void setTempType(String tempType) {
		this.tempType = tempType;
	}


	/**
	  * 设置ID的值
	 * @param 	id	 ID
	**/
	public void setId(Long  id){
		 this.id=id;
 	}

	/**
	  * 获取ID的值
	 * @return 返回ID的值
	**/
	public Long getId(){
		 return id;
 	}

	/**
	  * 设置模板编号的值
	 * @param 	code	 模板编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取模板编号的值
	 * @return 返回模板编号的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置模板名称的值
	 * @param 	name	 模板名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取模板名称的值
	 * @return 返回模板名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置适用客户类型的值
	 * @param 	custType	 适用客户类型
	**/
	public void setCustType(Integer  custType){
		 this.custType=custType;
 	}

	/**
	  * 获取适用客户类型的值
	 * @return 返回适用客户类型的值
	**/
	public Integer getCustType(){
		 return custType;
 	}

	/**
	  * 设置模板路径的值
	 * @param 	tempPath	 模板路径
	**/
	public void setTempPath(String  tempPath){
		 this.tempPath=tempPath;
 	}

	/**
	  * 获取模板路径的值
	 * @return 返回模板路径的值
	**/
	public String getTempPath(){
		 return tempPath;
 	}

	/**
	  * 设置创建人的值
	 * @param 	creator	 创建人
	**/
	public void setCreator(Long  creator){
		 this.creator=creator;
 	}

	/**
	  * 获取创建人的值
	 * @return 返回创建人的值
	**/
	public Long getCreator(){
		 return creator;
 	}

	/**
	  * 设置创建日期的值
	 * @param 	createTime	 创建日期
	**/
	public void setCreateTime(Date  createTime){
		 this.createTime=createTime;
 	}

	/**
	  * 获取创建日期的值
	 * @return 返回创建日期的值
	**/
	public Date getCreateTime(){
		 return createTime;
 	}

	/**
	  * 设置说明的值
	 * @param 	remark	 说明
	**/
	public void setRemark(String  remark){
		 this.remark=remark;
 	}

	/**
	  * 获取说明的值
	 * @return 返回说明的值
	**/
	public String getRemark(){
		 return remark;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,code,name,custType,tempPath,creator,createTime,remark,isenabled};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","code","name","custType","tempPath","creator","createTime","remark","isenabled"};
	}

}
