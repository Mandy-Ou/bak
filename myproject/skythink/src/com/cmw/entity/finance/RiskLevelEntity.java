package com.cmw.entity.finance;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 风险等级
 * @author pdt
 * @date 2012-12-23T00:00:00
 */
@Description(remark="风险等级实体",createDate="2012-12-23T00:00:00",author="pdt")
@Entity
@Table(name="fc_RiskLevel")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class RiskLevelEntity extends IdBaseEntity {
	
	
	 @Description(remark="编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;

	 @Description(remark="风险等级名称")
	 @Column(name="name" ,nullable=false ,length=30 )
	 private String name;

	 @Description(remark="最小逾期天数")
	 @Column(name="mindays" ,nullable=false )
	 private Integer mindays = 0;

	 @Description(remark="最大逾期天数")
	 @Column(name="maxdays" ,nullable=false )
	 private Integer maxdays = 0;

	 @Description(remark="预警颜色")
	 @Column(name="color",length=20 )
	 private String color;
	 
	public RiskLevelEntity() {
	}

	
	/**
	  * 设置编号的值
	 * @param 	code	 编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取编号的值
	 * @return 返回编号的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置风险等级名称的值
	 * @param 	name	 风险等级名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取风险等级名称的值
	 * @return 返回风险等级名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置最小逾期天数的值
	 * @param 	mindays	 最小逾期天数
	**/
	public void setMindays(Integer  mindays){
		 this.mindays=mindays;
 	}

	/**
	  * 获取最小逾期天数的值
	 * @return 返回最小逾期天数的值
	**/
	public Integer getMindays(){
		 return mindays;
 	}

	/**
	  * 设置最大逾期天数的值
	 * @param 	maxdays	 最大逾期天数
	**/
	public void setMaxdays(Integer  maxdays){
		 this.maxdays=maxdays;
 	}

	/**
	  * 获取最大逾期天数的值
	 * @return 返回最大逾期天数的值
	**/
	public Integer getMaxdays(){
		 return maxdays;
 	}
	
	

	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,code,name,mindays,maxdays,color,createTime,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","code","name","mindays","maxdays","color","createTime#yyyy-MM-dd","remark"};
	}

}
