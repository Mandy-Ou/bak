package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 流转路径
 * @author 程明卫
 * @date 2012-11-28T00:00:00
 */
@Description(remark="流转路径实体",createDate="2012-11-28T00:00:00",author="程明卫")
@Entity
@Table(name="ts_BussTrans")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class BussTransEntity extends IdBaseEntity {
	
	
	 @Description(remark="起始节点ID")
	 @Column(name="bnodeId" ,nullable=false )
	 private Long bnodeId;



	 @Description(remark="目标节点ID")
	 @Column(name="enodeId" ,nullable=false )
	 private Long enodeId;



	 @Description(remark="路径名称")
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;



	 @Description(remark="表达式")
	 @Column(name="express" ,length=200 )
	 private String express;



	 @Description(remark="表达式参数")
	 @Column(name="exparams" ,length=200 )
	 private String exparams;




	public BussTransEntity() {

	}

	
	/**
	  * 设置起始节点ID的值
	 * @param 	bnodeId	 起始节点ID
	**/
	public void setBnodeId(Long  bnodeId){
		 this.bnodeId=bnodeId;
 	}

	/**
	  * 获取起始节点ID的值
	 * @return 返回起始节点ID的值
	**/
	public Long getBnodeId(){
		 return bnodeId;
 	}

	/**
	  * 设置目标节点ID的值
	 * @param 	enodeId	 目标节点ID
	**/
	public void setEnodeId(Long  enodeId){
		 this.enodeId=enodeId;
 	}

	/**
	  * 获取目标节点ID的值
	 * @return 返回目标节点ID的值
	**/
	public Long getEnodeId(){
		 return enodeId;
 	}

	/**
	  * 设置路径名称的值
	 * @param 	name	 路径名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取路径名称的值
	 * @return 返回路径名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置表达式的值
	 * @param 	express	 表达式
	**/
	public void setExpress(String  express){
		 this.express=express;
 	}

	/**
	  * 获取表达式的值
	 * @return 返回表达式的值
	**/
	public String getExpress(){
		 return express;
 	}

	/**
	  * 设置表达式参数的值
	 * @param 	exparams	 表达式参数
	**/
	public void setExparams(String  exparams){
		 this.exparams=exparams;
 	}

	/**
	  * 获取表达式参数的值
	 * @return 返回表达式参数的值
	**/
	public String getExparams(){
		 return exparams;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{bnodeId,enodeId,name,express,exparams};
	}

	@Override
	public String[] getFields() {
		return new String[]{"bnodeId","enodeId","name","express","exparams"};
	}

}
