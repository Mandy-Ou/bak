package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 报表模板表
 * @author 程明卫
 * @date 2013-01-19T00:00:00
 */
@Description(remark="报表模板表实体",createDate="2013-01-19T00:00:00",author="程明卫")
@Entity
@Table(name="ts_ReportTemp")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class ReportTempEntity extends IdBaseEntity {
	
	
	 @Description(remark="系统ID")
	 @Column(name="sysId" ,nullable=false )
	 private Long sysId;

	 @Description(remark="报表名称")
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;

	 @Description(remark="报表类型")
	 @Column(name="type" ,nullable=false )
	 private Integer type = 1;

	 @Description(remark="功能名称")
	 @Column(name="funName" ,nullable=false ,length=60 )
	 private String funName;

	 @Description(remark="功能标识符")
	 @Column(name="token" ,nullable=false ,length=50 )
	 private String token;

	 @Description(remark="模板路径")
	 @Column(name="url" ,nullable=false ,length=150 )
	 private String url;


	public ReportTempEntity() {

	}

	
	/**
	  * 设置系统ID的值
	 * @param 	sysId	 系统ID
	**/
	public void setSysId(Long  sysId){
		 this.sysId=sysId;
 	}

	/**
	  * 获取系统ID的值
	 * @return 返回系统ID的值
	**/
	public Long getSysId(){
		 return sysId;
 	}

	/**
	  * 设置报表名称的值
	 * @param 	name	 报表名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取报表名称的值
	 * @return 返回报表名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置报表类型的值
	 * @param 	type	 报表类型
	**/
	public void setType(Integer  type){
		 this.type=type;
 	}

	/**
	  * 获取报表类型的值
	 * @return 返回报表类型的值
	**/
	public Integer getType(){
		 return type;
 	}

	/**
	  * 设置功能名称的值
	 * @param 	funName	 功能名称
	**/
	public void setFunName(String  funName){
		 this.funName=funName;
 	}

	/**
	  * 获取功能名称的值
	 * @return 返回功能名称的值
	**/
	public String getFunName(){
		 return funName;
 	}

	/**
	  * 设置功能标识符的值
	 * @param 	token	 功能标识符
	**/
	public void setToken(String  token){
		 this.token=token;
 	}

	/**
	  * 获取功能标识符的值
	 * @return 返回功能标识符的值
	**/
	public String getToken(){
		 return token;
 	}

	/**
	  * 设置模板路径的值
	 * @param 	url	 模板路径
	**/
	public void setUrl(String  url){
		 this.url=url;
 	}

	/**
	  * 获取模板路径的值
	 * @return 返回模板路径的值
	**/
	public String getUrl(){
		 return url;
 	}


	@Override
	public Object[] getDatas() {
		return new Object[]{getId(),sysId,name,type,funName,token,url,isenabled,creator,createTime,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","sysId","name","type","funName","token","url","isenabled","creator","createTime#yyyy-MM-dd","remark"};
	}
	/**
	 * 报表类型 [1:Excel]
	 */
	public static final int TYPE_1 = 1;

}
