package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 会签配置
 * @author 程明卫
 * @version V2.7
 * @date 2013-11-22T00:00:00
 */
@Description(remark="会签配置实体",createDate="2013-11-22T00:00:00",author="程明卫")
@Entity
@Table(name="ts_CountersignCfg")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class CountersignCfgEntity extends IdBaseEntity {
	
	
	 @Description(remark="节点配置ID")
	 @Column(name="nodeId" ,nullable=false )
	 private Long nodeId;

	 @Description(remark="会签类型")
	 @Column(name="ctype" ,nullable=false )
	 private Integer ctype;

	 @Description(remark="决策方式")
	 @Column(name="pdtype" ,nullable=false )
	 private Integer pdtype;

	 @Description(remark="投票类型")
	 @Column(name="voteType" ,nullable=false )
	 private Integer voteType;

	 @Description(remark="比较方式")
	 @Column(name="eqlogic" ,nullable=false )
	 private Integer eqlogic;

	 @Description(remark="比较值")
	 @Column(name="eqval" ,nullable=false ,scale=2)
	 private Float eqval = 0f;

	 @Description(remark="流转方向")
	 @Column(name="transId" ,nullable=false)
	 private Long transId;

	 @Description(remark="条件不满足时流转方向")
	 @Column(name="untransId" ,nullable=false)
	 private Long untransId;
	 
	 @Description(remark="流转方式")
	 @Column(name="transType" ,nullable=false )
	 private Integer transType=1;
	 
	 @Description(remark="参与者指定方式")
	 @Column(name="acway" ,nullable=false )
	 private Integer acway;


	public CountersignCfgEntity() {

	}

	
	/**
	  * 设置节点配置ID的值
	 * @param 	nodeId	 节点配置ID
	**/
	public void setNodeId(Long  nodeId){
		 this.nodeId=nodeId;
 	}

	/**
	  * 获取节点配置ID的值
	 * @return 返回节点配置ID的值
	**/
	public Long getNodeId(){
		 return nodeId;
 	}

	/**
	  * 设置会签类型的值
	 * @param 	ctype	 会签类型
	**/
	public void setCtype(Integer  ctype){
		 this.ctype=ctype;
 	}

	/**
	  * 获取会签类型的值
	 * @return 返回会签类型的值
	**/
	public Integer getCtype(){
		 return ctype;
 	}

	/**
	  * 设置决策方式的值
	 * @param 	pdtype	 决策方式
	**/
	public void setPdtype(Integer  pdtype){
		 this.pdtype=pdtype;
 	}

	/**
	  * 获取决策方式的值
	 * @return 返回决策方式的值
	**/
	public Integer getPdtype(){
		 return pdtype;
 	}

	/**
	  * 设置投票类型的值
	 * @param 	voteType	 投票类型
	**/
	public void setVoteType(Integer  voteType){
		 this.voteType=voteType;
 	}

	/**
	  * 获取投票类型的值
	 * @return 返回投票类型的值
	**/
	public Integer getVoteType(){
		 return voteType;
 	}

	/**
	  * 设置比较方式的值
	 * @param 	eqlogic	 比较方式
	**/
	public void setEqlogic(Integer  eqlogic){
		 this.eqlogic=eqlogic;
 	}

	/**
	  * 获取比较方式的值
	 * @return 返回比较方式的值
	**/
	public Integer getEqlogic(){
		 return eqlogic;
 	}

	/**
	  * 设置比较值的值
	 * @param 	eqval	 比较值
	**/
	public void setEqval(Float  eqval){
		 this.eqval=eqval;
 	}

	/**
	  * 获取比较值的值
	 * @return 返回比较值的值
	**/
	public Float getEqval(){
		 return eqval;
 	}

	/**
	  * 设置流转方向的值
	 * @param 	trans	 流转方向
	**/
	public void setTransId(Long  transId){
		 this.transId=transId;
 	}

	/**
	  * 获取流转方向的值
	 * @return 返回流转方向的值
	**/
	public Long getTransId(){
		 return transId;
 	}
	
	
	/**
	  * 获取条件不满足时流转方向的值
	 * @return 返回条件不满足时流转方向的值
	**/
	public Long getUntransId() {
		return untransId;
	}

	/**
	  * 设置条件不满足时流转方向的值
	 * @param 	untransId	 条件不满足时流转方向
	**/
	public void setUntransId(Long untransId) {
		this.untransId = untransId;
	}


	/**
	  * 获取流转方式的值
	 * @return 返回流转方式的值
	**/
	public Integer getTransType() {
		return transType;
	}

	/**
	  * 设置流转方式的值
	 * @param 	transType	流转方式
	**/
	public void setTransType(Integer transType) {
		this.transType = transType;
	}


	/**
	  * 设置参与者指定方式的值
	 * @param 	acway	 参与者指定方式
	**/
	public void setAcway(Integer  acway){
		 this.acway=acway;
 	}

	/**
	  * 获取参与者指定方式的值
	 * @return 返回参与者指定方式的值
	**/
	public Integer getAcway(){
		 return acway;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,nodeId,ctype,pdtype,voteType,eqlogic,eqval,transId,untransId,transType,acway,isenabled};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","nodeId","ctype","pdtype","voteType","eqlogic","eqval","transId","untransId","transType","acway","isenabled"};
	}

}
