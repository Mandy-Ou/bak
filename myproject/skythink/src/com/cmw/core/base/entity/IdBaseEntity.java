package com.cmw.core.base.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.cmw.core.base.annotation.Description;

/**
 * 实体基类
 * 当实体中有 "id,isenabled,remark,creator,createTime,deptId,orgid,modifier,modifytime"
 * 这些共有属性时可实现此类
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class IdBaseEntity extends BaseEntity {
	// base Fields
	@Description(remark="主键ID")
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;	//ID

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
