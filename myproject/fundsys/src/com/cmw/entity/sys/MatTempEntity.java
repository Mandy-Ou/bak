package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 资料模板
 * @author pdt
 * @date 2012-12-26T00:00:00
 */
@Description(remark="资料模板实体",createDate="2012-12-26T00:00:00",author="pdt")
@Entity
@Table(name="ts_MatTemp")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class MatTempEntity extends IdBaseEntity {
	
	
	 @Description(remark="系统Id")
	 @Column(name="sysId" ,nullable=false )
	 private Long sysId;

	 @Description(remark="模板名称")
	 @Column(name="name" ,nullable=false ,length=100 )
	 private String name;

	 @Description(remark="适合客户类型")
	 @Column(name="custType" ,nullable=false  )
	 private Integer custType;
	 
	 @Description(remark="适用业务品种")
	 @Column(name="breedId" ,nullable=false )
	 private Long breedId;


	public MatTempEntity() {

	}
	
	
	/**得到客户类型
	 * @return the custType
	 */
	public Integer getCustType() {
		return custType;
	}


	/**设置客户类型
	 * @param custType the custType to set
	 */
	public void setCustType(Integer custType) {
		this.custType = custType;
	}


	/**
	  * 设置系统Id的值
	 * @param 	sysId	 系统Id
	**/
	public void setSysId(Long  sysId){
		 this.sysId=sysId;
 	}

	/**
	  * 获取系统Id的值
	 * @return 返回系统Id的值
	**/
	public Long getSysId(){
		 return sysId;
 	}

	/**
	  * 设置模板名称的值
	 * @param 	name	 模板名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取模板名称的值
	 * @return 返回模板名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置适用业务品种的值
	 * @param 	breedId	 适用业务品种
	**/
	public void setBreedId(Long  breedId){
		 this.breedId=breedId;
 	}

	/**
	  * 获取适用业务品种的值
	 * @return 返回适用业务品种的值
	**/
	public Long getBreedId(){
		 return breedId;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,sysId,name,custType,breedId};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","sysId","name","custType","breedId"};
	}

}
