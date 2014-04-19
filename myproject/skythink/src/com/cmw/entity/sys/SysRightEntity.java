package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdEntity;


/**
 * 系统权限
 * @author 程明卫
 * @date 2012-12-28T00:00:00
 */
@Description(remark="系统权限实体",createDate="2012-12-28T00:00:00",author="程明卫")
@Entity
@Table(name="ts_SysRight")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class SysRightEntity extends IdEntity {
	
	
	 @Description(remark="对象类型")
	 @Column(name="objtype" ,nullable=false )
	 private Integer objtype;

	 @Description(remark="对象ID")
	 @Column(name="objId" ,nullable=false )
	 private Long objId;

	 @Description(remark="系统ID")
	 @Column(name="sysId" ,nullable=false )
	 private Long sysId;
	 

	public SysRightEntity() {

	}

	
	/**
	  * 设置对象类型的值
	 * @param 	objtype	 对象类型
	**/
	public void setObjtype(Integer  objtype){
		 this.objtype=objtype;
 	}

	/**
	  * 获取对象类型的值
	 * @return 返回对象类型的值
	**/
	public Integer getObjtype(){
		 return objtype;
 	}

	/**
	  * 设置对象ID的值
	 * @param 	objId	 对象ID
	**/
	public void setObjId(Long  objId){
		 this.objId=objId;
 	}

	/**
	  * 获取对象ID的值
	 * @return 返回对象ID的值
	**/
	public Long getObjId(){
		 return objId;
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
	 * 对象类型	【角色:0】
	 */
	public static final int OBJTYPE_0 = 0;
	/**
	 *  对象类型	【用户:1】
	 */
	public static final int OBJTYPE_1 = 1;


	@Override
	public String[] getFields() {
		return new String[]{"id","objtype","objId","sysId"};
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{getId(),objtype,objId,sysId};
	}
}
