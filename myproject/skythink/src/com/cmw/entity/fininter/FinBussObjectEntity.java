package com.cmw.entity.fininter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 自定义业务对象
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="自定义业务对象实体",createDate="2013-03-28T00:00:00",author="程明卫")
@Entity
@Table(name="fs_FinBussObject")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class FinBussObjectEntity extends IdBaseEntity {
	
	
	 @Description(remark="财务系统ID")
	 @Column(name="finsysId" ,nullable=false )
	 private Long finsysId;

	 @Description(remark="对象名称")
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;

	 @Description(remark="业务类名")
	 @Column(name="className" ,nullable=false ,length=80 )
	 private String className;

	 @Description(remark="业务表名")
	 @Column(name="tabName" ,length=30 )
	 private String tabName;


	public FinBussObjectEntity() {

	}

	
	/**
	  * 设置财务系统ID的值
	 * @param 	finsysId	 财务系统ID
	**/
	public void setFinsysId(Long  finsysId){
		 this.finsysId=finsysId;
 	}

	/**
	  * 获取财务系统ID的值
	 * @return 返回财务系统ID的值
	**/
	public Long getFinsysId(){
		 return finsysId;
 	}

	/**
	  * 设置对象名称的值
	 * @param 	name	 对象名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取对象名称的值
	 * @return 返回对象名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置业务类名的值
	 * @param 	className	 业务类名
	**/
	public void setClassName(String  className){
		 this.className=className;
 	}

	/**
	  * 获取业务类名的值
	 * @return 返回业务类名的值
	**/
	public String getClassName(){
		 return className;
 	}

	/**
	  * 设置业务表名的值
	 * @param 	tabName	 业务表名
	**/
	public void setTabName(String  tabName){
		 this.tabName=tabName;
 	}

	/**
	  * 获取业务表名的值
	 * @return 返回业务表名的值
	**/
	public String getTabName(){
		 return tabName;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{finsysId,name,className,tabName,remark,isenabled};
	}

	@Override
	public String[] getFields() {
		return new String[]{"finsysId","name","className","tabName","remark","isenabled"};
	}

}
