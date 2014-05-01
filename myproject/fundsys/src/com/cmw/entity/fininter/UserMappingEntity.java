package com.cmw.entity.fininter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 用户帐号映射
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="用户帐号映射实体",createDate="2013-03-28T00:00:00",author="程明卫")
@Entity
@Table(name="fs_UserMapping ")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class UserMappingEntity extends IdBaseEntity {
	
	
	 @Description(remark="财务系统ID")
	 @Column(name="finsysId" ,nullable=false )
	 private Long finsysId;

	 @Description(remark="本系统用户ID")
	 @Column(name="userId" ,nullable=false )
	 private Long userId=0L;

	 @Description(remark="财务系统账号")
	 @Column(name="fuserName" ,nullable=false ,length=50 )
	 private String fuserName;

	 @Description(remark="制单人")
	 @Column(name="fsman" ,length=50 )
	 private String fsman;

	 @Description(remark="组织编号")
	 @Column(name="forgcode" ,length=30 )
	 private String forgcode;

	 @Description(remark="财务系统用户ID")
	 @Column(name="refId" )
	 private Long refId;


	public UserMappingEntity() {

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
	  * 设置本系统用户ID的值
	 * @param 	userId	 本系统用户ID
	**/
	public void setUserId(Long  userId){
		 this.userId=userId;
 	}

	/**
	  * 获取本系统用户ID的值
	 * @return 返回本系统用户ID的值
	**/
	public Long getUserId(){
		 return userId;
 	}

	/**
	  * 设置财务系统账号的值
	 * @param 	fuserName	 财务系统账号
	**/
	public void setFuserName(String  fuserName){
		 this.fuserName=fuserName;
 	}

	/**
	  * 获取财务系统账号的值
	 * @return 返回财务系统账号的值
	**/
	public String getFuserName(){
		 return fuserName;
 	}

	/**
	  * 设置制单人的值
	 * @param 	fsman	 制单人
	**/
	public void setFsman(String  fsman){
		 this.fsman=fsman;
 	}

	/**
	  * 获取制单人的值
	 * @return 返回制单人的值
	**/
	public String getFsman(){
		 return fsman;
 	}

	/**
	  * 设置组织编号的值
	 * @param 	forgcode	 组织编号
	**/
	public void setForgcode(String  forgcode){
		 this.forgcode=forgcode;
 	}

	/**
	  * 获取组织编号的值
	 * @return 返回组织编号的值
	**/
	public String getForgcode(){
		 return forgcode;
 	}

	/**
	  * 设置财务系统用户ID的值
	 * @param 	refId	 财务系统用户ID
	**/
	public void setRefId(Long  refId){
		 this.refId=refId;
 	}

	/**
	  * 获取财务系统用户ID的值
	 * @return 返回财务系统用户ID的值
	**/
	public Long getRefId(){
		 return refId;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{finsysId,userId,fuserName,fsman,forgcode,refId};
	}

	@Override
	public String[] getFields() {
		return new String[]{"finsysId","userId","fuserName","fsman","forgcode","refId"};
	}

}
