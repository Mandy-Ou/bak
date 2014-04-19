package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 流程节点配置
 * @author 程明卫
 * @date 2012-12-05T00:00:00
 */
@Description(remark="流程节点配置实体",createDate="2012-12-05T00:00:00",author="程明卫")
@Entity
@Table(name="ts_NodeCfg")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class NodeCfgEntity extends IdBaseEntity {
	
	
	 @Description(remark="节点ID")
	 @Column(name="nodeId" )
	 private Long nodeId;



	 @Description(remark="流转方式")
	 @Column(name="transWay" ,nullable=false )
	 private Integer transWay = 1;
	 
	 /*---- V2.7 ADD START  ---*/
	 @Description(remark="并行类型")
	 @Column(name="eventType")
	 private Integer eventType = 1;
	 
	 
	 @Description(remark="是否允许补签")
	 @Column(name="sign" )
	 private Integer sign=0;
	 

	 /*---- V2.7 END START  ---*/
	 
	 @Description(remark="是否会签")
	 @Column(name="counterrsign" ,nullable=false )
	 private Integer counterrsign;



	 @Description(remark="超时设置")
	 @Column(name="isTimeOut" ,nullable=false )
	 private Integer isTimeOut;



	 @Description(remark="超时时长")
	 @Column(name="timeOut" ,length=10 )
	 private String timeOut;



	 @Description(remark="超时提前提醒时间")
	 @Column(name="btime" ,length=10 )
	 private String btime;



	 @Description(remark="提醒设置")
	 @Column(name="reminds" ,length=10 )
	 private String reminds;




	public NodeCfgEntity() {

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
	  * 设置流转方式的值
	 * @param 	transType	 流转方式
	**/
	public void setTransWay(Integer  transWay){
		 this.transWay=transWay;
 	}

	/**
	  * 获取流转方式的值
	 * @return 返回流转方式的值
	**/
	public Integer getTransWay(){
		 return transWay;
 	}
	
	
	/**
	  * 获取是否允许补签的值
	 * @return 返回是否允许补签的值
	**/
	public Integer getSign() {
		return sign;
	}

	/**
	  * 设置是否允许补签的值
	 * @param 	sign	 是否允许补签
	**/
	public void setSign(Integer sign) {
		this.sign = sign;
	}


	/**
	  * 设置是否会签的值
	 * @param 	counterrsign	 是否会签
	**/
	public void setCounterrsign(Integer  counterrsign){
		 this.counterrsign=counterrsign;
 	}

	/**
	  * 获取是否会签的值
	 * @return 返回是否会签的值
	**/
	public Integer getCounterrsign(){
		 return counterrsign;
 	}

	/**
	  * 获取并行类型的值
	 * @return 返回并行类型的值
	**/
	public Integer getEventType() {
		return eventType;
	}

	/**
	  * 设置并行类型的值
	 * @param eventType	并行类型
	**/
	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}



	/**
	  * 设置超时设置的值
	 * @param 	isTimeOut	 超时设置
	**/
	public void setIsTimeOut(Integer  isTimeOut){
		 this.isTimeOut=isTimeOut;
 	}

	/**
	  * 获取超时设置的值
	 * @return 返回超时设置的值
	**/
	public Integer getIsTimeOut(){
		 return isTimeOut;
 	}

	/**
	  * 设置超时时长的值
	 * @param 	timeOut	 超时时长
	**/
	public void setTimeOut(String  timeOut){
		 this.timeOut=timeOut;
 	}

	/**
	  * 获取超时时长的值
	 * @return 返回超时时长的值
	**/
	public String getTimeOut(){
		 return timeOut;
 	}

	/**
	  * 设置超时提前提醒时间的值
	 * @param 	btime	 超时提前提醒时间
	**/
	public void setBtime(String  btime){
		 this.btime=btime;
 	}

	/**
	  * 获取超时提前提醒时间的值
	 * @return 返回超时提前提醒时间的值
	**/
	public String getBtime(){
		 return btime;
 	}

	/**
	  * 设置提醒设置的值
	 * @param 	reminds	 提醒设置
	**/
	public void setReminds(String  reminds){
		 this.reminds=reminds;
 	}

	/**
	  * 获取提醒设置的值
	 * @return 返回提醒设置的值
	**/
	public String getReminds(){
		 return reminds;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{nodeId,transWay,sign,counterrsign,isTimeOut,timeOut,btime,reminds,eventType};
	}

	@Override
	public String[] getFields() {
		return new String[]{"nodeId","transWay","sign","counterrsign","isTimeOut","timeOut","btime","reminds","eventType"};
	}
	
	/**
	 * 超时设置   [0 : 不启用]
	 */ 
	public static final int TIMEOUT_0 = 0;
	/**
	 * 超时设置   [1 : 启用]
	 */ 
	public static final int TIMEOUT_1 = 1;
	/**
	 * 是否会签   [0 : 否]
	 */ 
	public static final int COUNTERRSIGN_0 = 0;
	/**
	 * 是否会签   [0 : 是]
	 */ 
	public static final int COUNTERRSIGN_1 = 1;
}
