package com.cmw.service.impl.cache;

/**
 * 缓存名枚举
 * @author chengmingwei
 *
 */
public enum CacheName{
	/**
	 * 用户缓存器 
	 */
	userCache,
	/**
	 *角色缓存器 
	 */
	roleCache,
	/**
	 * 业务品种缓存器 
	 */
	varietyCache,
	/**
	 * 子业务流程缓存器
	 */
	bussProccCache,
	/**
	 * 业务流程节点缓存器
	 */
	bussNodeCache,
	/**
	 * 流转路径缓存器
	 */
	bussTransCache,
	/**
	 * 流程节点配置缓存器
	 */
	nodeCfgCache,
	/**
	 * 会签配置 配置缓存器
	 */
	countersignCfgCache,
	/**
	 * 流转路径配置 配置缓存器
	 */
	transCfgCache,
	/**
	 * 节点表单配置  配置缓存器
	 */
	formCfgCache;
}
