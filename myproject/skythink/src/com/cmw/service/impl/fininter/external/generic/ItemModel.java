package com.cmw.service.impl.fininter.external.generic;

import java.io.Serializable;

/**
 * 财务通用核算项模型类
 * @author chengmingwei
 *
 */
@SuppressWarnings("serial")
public class ItemModel implements Serializable {
	//核算项ID
	private String itemId;
	//核算项类别ID
	private String itemClassId;
	//核算项编号
	private String number;
	//核算项类型
	private Integer itemClassType = ITEMCLASSTYPE_1;
	/**
	 * 获取 核算项ID 
	 * @return 返回  核算项ID
	 */
	public String getItemId() {
		return itemId;
	}
	
	/**
	 * 设置 核算项ID 
	 * @param itemId 核算项ID
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	/**
	 * 获取 核算项类别ID
	 * @return 返回 核算项类别ID
	 */
	public String getItemClassId() {
		return itemClassId;
	}
	
	/**
	 * 设置 核算项类别ID
	 * @param itemClassId 核算项类别ID
	 */
	public void setItemClassId(String itemClassId) {
		this.itemClassId = itemClassId;
	}
	
	/**
	 * 获取 核算项编号
	 * @return 返回 核算项编号
	 */
	public String getNumber() {
		return number;
	}
	
	/**
	 * 设置  核算项编号 (默认值：ITEMCLASSTYPE_1 客户)
	 * @param number  核算项编号
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	
	/**
	 * 获取 核算项类型  (默认值：ITEMCLASSTYPE_1 客户)
	 * @return 返回 核算项类型
	 */
	public Integer getItemClassType() {
		return itemClassType;
	}
	
	/**
	 * 设置  核算项类型
	 * @param itemClassType  核算项类型
	 */
	public void setItemClassType(Integer itemClassType) {
		this.itemClassType = itemClassType;
	}
	
	/**
	 * 核算项类型 [1:客户]
	 */
	public static final int ITEMCLASSTYPE_1 = 1;
	/**
	 * 核算项类型 [2:银行]
	 */
	public static final int ITEMCLASSTYPE_2 = 2;
}
