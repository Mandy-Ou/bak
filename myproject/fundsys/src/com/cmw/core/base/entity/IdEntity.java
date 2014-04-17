package com.cmw.core.base.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
/**
 * 提供ID共用的抽象实体类
 * @author chengmingwei
 *
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class IdEntity implements Serializable,BaseInter{
	//菜单ID
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
