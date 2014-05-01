package com.cmw.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.BaseEntity;
/**
 * 菜单实体类
 * @author 程明卫
 *
 */
@Description(remark="菜单实体",createDate="20011-08-08",author="程明卫")
@Entity
@Table(name="ts_menu")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class MenuEntity extends BaseEntity {
	@Description(remark="菜单ID")
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long menuId;
	
	@Description(remark="卡片ID	")
	@Column(name="accordionId",nullable=false)
	private Long accordionId;
	
	@Description(remark="父菜单ID",defaultVals="0:代表根节点ID")
	@Column(name="pid",nullable=false)
	private Long pid=0L;
	
	
	@Description(remark="菜单编码")
	@Column(name="code",length=20,nullable=false)
	private String code;
	
	@Description(remark="菜单名称")
	@Column(name="name",length=50,nullable=false)
	private String name;
	
	@Description(remark="菜单类型",defaultVals="1:卡片菜单, 2: 节点菜单")
	@Column(name="type",nullable=false)
	private Integer type=2;
	
	@Description(remark="菜单样式")
	@Column(name="iconCls",length=200)
	private String iconCls;
	
	@Description(remark="附加参数")
	@Column(name="params",length=200)
	private String params;
	
	@Description(remark="TabId")
	@Column(name="tabId",length=50)
	private String tabId;
	
	@Description(remark="缓存UI")
	@Column(name="cached",nullable=false)
	private Integer cached=0;
	
	@Description(remark="Js文件")
	@Column(name="jsArray",length=200)
	private String jsArray;
	
	@Description(remark="是否系统菜单",defaultVals="0:否,1:是")
	@Column(name="isSystem",nullable=false)
	private Byte isSystem=0;
	
	@Description(remark="是否叶子",defaultVals="true:是,false:否")
	@Column(name="leaf",nullable=false)
	private String leaf = "true";
	@Description(remark="菜单大图标")
	@Column(name="biconCls",length=200)
	private String biconCls;
	
	@Description(remark="url加载方式")
	@Column(name="loadType",nullable=false)
	private Integer loadType = 1;
	
	@Description(remark="排列顺序")
	@Column(name="orderNo")
	private Integer orderNo = 0;
	
	public String getBiconCls() {
		return biconCls;
	}


	public void setBiconCls(String biconCls) {
		this.biconCls = biconCls;
	}


	public Long getMenuId() {
		return menuId;
	}


	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}


	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	
	public String getTabId() {
		return tabId;
	}


	public void setTabId(String tabId) {
		this.tabId = tabId;
	}

	

	


	public Integer getCached() {
		return cached;
	}


	public void setCached(Integer cached) {
		this.cached = cached;
	}


	public String getJsArray() {
		return jsArray;
	}

	public void setJsArray(String jsArray) {
		this.jsArray = jsArray;
	}
	
	
	public Byte getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Byte isSystem) {
		this.isSystem = isSystem;
	}
	
	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	public Long getAccordionId() {
		return accordionId;
	}

	public void setAccordionId(Long accordionId) {
		this.accordionId = accordionId;
	}

	
	public Integer getLoadType() {
		return loadType;
	}


	public void setLoadType(Integer loadType) {
		this.loadType = loadType;
	}


	public Integer getOrderNo() {
		return orderNo;
	}


	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}


	public Object[] getDatas() {
		return new Object[]{menuId,accordionId,pid,code,name,type,iconCls,params,tabId,jsArray,leaf,biconCls,orderNo,loadType,cached,isenabled};
	}

	@Override
	public String[] getFields() {
		return new String[]{"menuId","accordionId","pid","code","name","type","iconCls","params","tabId","jsArray","leaf","biconCls","orderNo","loadType","cached","isenabled"};
	}
	
	/**
	 * 菜单类型 ----> 1 : 卡片菜单
	 */
	public static final Integer MENU_TYPE_1 = 1;
	/**
	 * 菜单类型 ----> 1 : 节点菜单
	 */
	public static final Integer MENU_TYPE_2 = 2;
	/**
	 * 菜单是否叶子节点 ----> true : 叶子节点
	 */
	public static final String MENU_LEAF_TRUE = "true";
	/**
	 * 菜单是否叶子节点 ----> false : 父节点
	 */
	public static final String MENU_LEAF_FALSE = "false";
}
