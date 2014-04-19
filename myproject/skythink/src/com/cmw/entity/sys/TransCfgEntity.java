package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 流转路径配置
 * @author 程明卫
 * @date 2012-11-28T00:00:00
 */
@Description(remark="流转路径配置实体",createDate="2012-11-28T00:00:00",author="程明卫")
@Entity
@Table(name="ts_TransCfg")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class TransCfgEntity extends IdBaseEntity {
	
	
	 @Description(remark="节点ID")
	 @Column(name="nodeId" ,nullable=false )
	 private Long nodeId;

	 @Description(remark="流转路径ID")
	 @Column(name="transId" ,nullable=false )
	 private Long transId;

	 @Description(remark="参与者类型")
	 @Column(name="acType" ,nullable=false )
	 private Integer acType;



	 @Description(remark="参与者ID")
	 @Column(name="actorIds" ,length=150 )
	 private String actorIds;

	 @Description(remark="启用条件限制")
	 @Column(name="stint")
	 private Integer stint=1;

	 @Description(remark="限制值")
	 @Column(name="limitVals" ,length=150 )
	 private String limitVals;
	 
	 /*---- V2.7 ADD START  ---*/
	 @Description(remark="流转路径类型")
	 @Column(name="tpathType")
	 private Integer tpathType=0;
	 
	 @Description(remark="目标任务处理方式")
	 @Column(name="tagWay")
	 private Integer tagWay=0;
	 
	 @Description(remark="并行令牌类型")
	 @Column(name="tokenType")
	 private Integer tokenType=1;
	 
	 @Description(remark="令牌")
	 @Column(name="token" ,length=50 )
	 private String token;
	 /*---- V2.7 ADD END  ---*/
	 
	public TransCfgEntity() {

	}

	
	/**
	  * 设置节点ID的值
	 * @param 	nodeId	 节点ID
	**/
	public void setNodeId(Long  nodeId){
		 this.nodeId=nodeId;
 	}

	/**
	  * 获取节点ID的值
	 * @return 返回节点ID的值
	**/
	public Long getNodeId(){
		 return nodeId;
 	}

	/**
	  * 获取流转路径ID的值
	 * @return 返回流转路径ID的值
	**/
	public Long getTransId() {
		return transId;
	}

	/**
	  * 设置流转路径ID的值
	 * @param 	transId	 流转路径ID
	**/
	public void setTransId(Long transId) {
		this.transId = transId;
	}


	/**
	  * 设置参与者类型的值
	 * @param 	acType	 参与者类型
	**/
	public void setAcType(Integer  acType){
		 this.acType=acType;
 	}

	/**
	  * 获取参与者类型的值
	 * @return 返回参与者类型的值
	**/
	public Integer getAcType(){
		 return acType;
 	}

	/**
	  * 设置参与者ID的值
	 * @param 	actorIds	 参与者ID
	**/
	public void setActorIds(String  actorIds){
		 this.actorIds=actorIds;
 	}

	/**
	  * 获取参与者ID的值
	 * @return 返回参与者ID的值
	**/
	public String getActorIds(){
		 return actorIds;
 	}


	/**
	  * 获取的值
	 * @return 返回的值
	**/
	public Integer getStint() {
		return stint;
	}

	/**
	  * 设置的值
	 * @param 		 
	**/
	public void setStint(Integer stint) {
		this.stint = stint;
	}

	/**
	  * 获取限制值的值
	 * @return 返回限制值的值
	**/
	public String getLimitVals() {
		return limitVals;
	}

	/**
	  * 设置限制值的值
	 * @param limitVals	限制值	 
	**/
	public void setLimitVals(String limitVals) {
		this.limitVals = limitVals;
	}


	
	/**
	  * 获取流转路径类型的值
	 * @return 返回流转路径类型的值
	**/
	public Integer getTpathType() {
		return tpathType;
	}

	/**
	  * 设置流转路径类型的值
	 * @param tpathType	流转路径类型	 
	**/
	public void setTpathType(Integer tpathType) {
		this.tpathType = tpathType;
	}

	/**
	  * 获取目标任务处理方式的值
	 * @return 返回目标任务处理方式的值
	**/
	public Integer getTagWay() {
		return tagWay;
	}

	/**
	  * 设置目标任务处理方式的值
	 * @param 	tagWay	 目标任务处理方式
	**/
	public void setTagWay(Integer tagWay) {
		this.tagWay = tagWay;
	}

	
	/**
	  * 获取并行令牌类型的值
	 * @return 返回并行令牌类型的值
	**/
	public Integer getTokenType() {
		return tokenType;
	}

	/**
	  * 设置并行令牌类型的值
	 * @param 	tokenType	 并行令牌类型
	**/
	public void setTokenType(Integer tokenType) {
		this.tokenType = tokenType;
	}


	/**
	  * 获取令牌的值
	 * @return 返回令牌的值
	**/
	public String getToken() {
		return token;
	}

	/**
	  * 设置令牌的值
	 * @param 	token	 令牌
	**/
	public void setToken(String token) {
		this.token = token;
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{id,nodeId,transId,acType,actorIds,stint,limitVals,tpathType,tagWay,tokenType,token};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","nodeId","transId","acType","actorIds","stint","limitVals","tpathType","tagWay","tokenType","token"};
	}
	
	/**
	 * 参与者类型 [0:不需要参与者]
	 */
	public static final int ACTYPE_0 = 0;
	/**
	 * 参与者类型 [1:角色]
	 */
	public static final int ACTYPE_1 = 1;
	/**
	 * 参与者类型 [2:用户]
	 */
	public static final int ACTYPE_2 = 2;
	/**
	 * 参与者类型 [3:上一环节处理人]
	 */
	public static final int ACTYPE_3 = 3;
	/**
	 * 参与者类型 [4:上级领导]
	 */
	public static final int ACTYPE_4 = 4;
	/**
	 * 参与者类型 [5:流程发起人]
	 */
	public static final int ACTYPE_5 = 5;
	
	/**
	 * 参与者类型 [0:不需要参与者]
	 */
	public static final String ACTYPE_0_NAME = "不需要参与者";
	/**
	 * 参与者类型 [1:角色]
	 */
	public static final String ACTYPE_1_NAME = "角色";
	/**
	 * 参与者类型 [2:用户]
	 */
	public static final String ACTYPE_2_NAME = "用户";
	/**
	 * 参与者类型 [3:上一环节处理人]
	 */
	public static final String ACTYPE_3_NAME = "上一环节处理人";
	/**
	 * 参与者类型 [4:上级领导]
	 */
	public static final String ACTYPE_4_NAME = "上级领导";
	/**
	 * 参与者类型 [5:流程发起人]
	 */
	public static final String ACTYPE_5_NAME = "流程发起人";
	
	/**
	 * 启用条件限制 [1:无条件限制]
	 */
	public static final int STINT_1 = 1;
	/**
	 * 启用条件限制 [2:按角色限制]
	 */
	public static final int STINT_2 = 2;
	/**
	 * 启用条件限制 [3:按用户限制]
	 */
	public static final int STINT_3 = 3;
	/**
	 * 目标节点无审批人取消任务 [4:目标节点无审批人取消任务]
	 */
	public static final int STINT_4 = 4;
}
