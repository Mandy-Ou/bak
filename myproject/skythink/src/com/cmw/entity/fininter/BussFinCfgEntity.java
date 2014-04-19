package com.cmw.entity.fininter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 业务财务映射
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="业务财务映射实体",createDate="2013-03-28T00:00:00",author="程明卫")
@Entity
@Table(name="fs_BussFinCfg")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class BussFinCfgEntity extends IdBaseEntity {
	
	
	 @Description(remark="业务系统ID")
	 @Column(name="sysId" ,nullable=false )
	 private Long sysId;

	 @Description(remark="财务系统ID")
	 @Column(name="finsysId" ,nullable=false )
	 private Long finsysId;


	public BussFinCfgEntity() {

	}

	
	/**
	  * 设置业务系统ID的值
	 * @param 	sysId	 业务系统ID
	**/
	public void setSysId(Long  sysId){
		 this.sysId=sysId;
 	}

	/**
	  * 获取业务系统ID的值
	 * @return 返回业务系统ID的值
	**/
	public Long getSysId(){
		 return sysId;
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



	@Override
	public Object[] getDatas() {
		return new Object[]{sysId,finsysId};
	}

	@Override
	public String[] getFields() {
		return new String[]{"sysId","finsysId"};
	}

}
