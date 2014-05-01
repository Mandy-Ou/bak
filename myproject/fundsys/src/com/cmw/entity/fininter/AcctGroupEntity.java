package com.cmw.entity.fininter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 科目组
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="科目组实体",createDate="2013-03-28T00:00:00",author="程明卫")
@Entity
@Table(name="fs_AcctGroup")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class AcctGroupEntity extends IdBaseEntity {
	
	
	 @Description(remark="财务系统ID")
	 @Column(name="finsysId" ,nullable=false )
	 private Long finsysId;

	 @Description(remark="科目组编号")
	 @Column(name="code" ,nullable=false ,length=50 )
	 private String code;

	 @Description(remark="科目组名称")
	 @Column(name="name" ,nullable=false ,length=60 )
	 private String name;

	 @Description(remark="科目组名称")
	 @Column(name="refId" )
	 private Long refId;


	public AcctGroupEntity() {

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
	  * 设置科目组编号的值
	 * @param 	code	 科目组编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取科目组编号的值
	 * @return 返回科目组编号的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置科目组名称的值
	 * @param 	name	 科目组名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取科目组名称的值
	 * @return 返回科目组名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置科目组名称的值
	 * @param 	refId	 科目组名称
	**/
	public void setRefId(Long  refId){
		 this.refId=refId;
 	}

	/**
	  * 获取科目组名称的值
	 * @return 返回科目组名称的值
	**/
	public Long getRefId(){
		 return refId;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{finsysId,getId(),code,name,refId};
	}

	@Override
	public String[] getFields() {
		return new String[]{"finsysId","id","code","name","refId"};
	}

}
