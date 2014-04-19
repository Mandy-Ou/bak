package com.cmw.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;
/**
 * 卡片菜单实体类
 * @author chengmingwei
 *
 */
@Description(remark="卡片菜单实体",createDate="20011-08-08",author="程明卫")
@Entity
@Table(name="ts_accordion")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class AccordionEntity extends IdBaseEntity {
	@Description(remark="卡片编码")
	@Column(name="code",length=20,nullable=false)
	private String code;
	@Description(remark="名称")
	@Column(name="name",length=50,nullable=false)
	private String name;
	@Description(remark="图标样式")
	@Column(name="iconCls",length=50)
	private String iconCls;
	@Description(remark="加载类型",defaultVals="0:树,1:按钮")
	@Column(name="type",length=50)
	private Integer type = 0;
	@Description(remark="数据UR")
	@Column(name="url",length=200)
	private String url;
	@Description(remark="排列顺序")
	@Column(name="orderNo",nullable=false)
	private Integer orderNo=1;
	@Description(remark="系统ID")
	@Column(name="sysid",nullable=false)
	private Long sysid;

	public AccordionEntity() {

	}

	
	public Long getSysid() {
		return sysid;
	}


	public void setSysid(Long sysid) {
		this.sysid = sysid;
	}


	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getIconCls() {
		return iconCls;
	}



	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}



	public Integer getType() {
		return type;
	}



	public void setType(Integer type) {
		this.type = type;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{id,sysid,code,name,iconCls,type,url,orderNo,getRemark(),getIsenabled()};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","sysid","code","name","iconCls","type","url","orderNo","remark","isenabled"};
	}

}
