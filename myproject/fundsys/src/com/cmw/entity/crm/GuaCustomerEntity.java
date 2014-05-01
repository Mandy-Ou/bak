package com.cmw.entity.crm;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdEntity;


/**
 * 第三方担保人
 * @author 李听
 * @date 2013-12-31T00:00:00
 */
@Description(remark="第三方担保人实体",createDate="2013-12-31T00:00:00",author="李听")
@Entity
@Table(name="crm_GuaCustomer")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class GuaCustomerEntity extends IdEntity {
	
	
	 @Description(remark="客户分类表ID")
	 @Column(name="categoryId" ,nullable=false )
	 private Long categoryId;

	 @Description(remark="与被担保人关系")
	 @Column(name="relation" ,nullable=false ,length=50 )
	 private String relation;

	 @Description(remark="是否无限连带担保责任")
	 @Column(name="isgua" ,nullable=false )
	 private Integer isgua;


	public GuaCustomerEntity() {

	}

	
	/**
	  * 设置客户分类表ID的值
	 * @param 	categoryId	 客户分类表ID
	**/
	public void setCategoryId(Long  categoryId){
		 this.categoryId=categoryId;
 	}

	/**
	  * 获取客户分类表ID的值
	 * @return 返回客户分类表ID的值
	**/
	public Long getCategoryId(){
		 return categoryId;
 	}

	/**
	  * 设置与被担保人关系的值
	 * @param 	relation	 与被担保人关系
	**/
	public void setRelation(String  relation){
		 this.relation=relation;
 	}

	/**
	  * 获取与被担保人关系的值
	 * @return 返回与被担保人关系的值
	**/
	public String getRelation(){
		 return relation;
 	}

	/**
	  * 设置是否无限连带担保责任的值
	 * @param 	isgua	 是否无限连带担保责任
	**/
	public void setIsgua(Integer  isgua){
		 this.isgua=isgua;
 	}

	/**
	  * 获取是否无限连带担保责任的值
	 * @return 返回是否无限连带担保责任的值
	**/
	public Integer getIsgua(){
		 return isgua;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{categoryId,relation,isgua};
	}

	@Override
	public String[] getFields() {
		return new String[]{"categoryId","relation","isgua"};
	}

}
