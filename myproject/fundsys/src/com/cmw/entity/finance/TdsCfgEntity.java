package com.cmw.entity.finance;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 模板数据源配置表
 * @author 赵世龙
 * @date 2013-11-21T00:00:00
 */
@Description(remark="模板数据源配置表实体",createDate="2013-11-21T00:00:00",author="赵世龙")
@Entity
@Table(name="fc_TdsCfg")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class TdsCfgEntity extends IdBaseEntity {
	
	
	 @Description(remark="模板ID")
	 @Column(name="tempId" ,nullable=false )
	 private Long tempId;

	 @Description(remark="数据源标识")
	 @Column(name="dstag" ,nullable=false ,length=50 )
	 private String dstag;

	 @Description(remark="加载方式")
	 @Column(name="loadType" ,nullable=false )
	 private Integer loadType;

	 @Description(remark="数据代码")
	 @Column(name="dataCode" ,length=2000 )
	 private String dataCode;

	 @Description(remark="链接参数")
	 @Column(name="urlparams" ,length=50 )
	 private String urlparams;

	 @Description(remark="数据加载顺序")
	 @Column(name="orderNo" ,nullable=false )
	 private Integer orderNo;

	 @Description(remark="数据展示方式")
	 @Column(name="dispType" ,nullable=false )
	 private Integer dispType;

	 @Description(remark="依赖数据源")
	 @Column(name="relydsId" )
	 private Long relydsId;

	 @Description(remark="依赖数据源列")
	 @Column(name="relyCmns" ,length=150 )
	 private String relyCmns;
	 
	 @Description(remark="Html代码")
	 @Column(name="htmlCode" ,length=500 )
	 private String htmlCode;


	public String getHtmlCode() {
		return htmlCode;
	}


	public void setHtmlCode(String htmlCode) {
		this.htmlCode = htmlCode;
	}


	public TdsCfgEntity() {

	}


	/**
	  * 设置模板ID的值
	 * @param 	tempId	 模板ID
	**/
	public void setTempId(Long  tempId){
		 this.tempId=tempId;
 	}

	/**
	  * 获取模板ID的值
	 * @return 返回模板ID的值
	**/
	public Long getTempId(){
		 return tempId;
 	}

	/**
	  * 设置数据源标识的值
	 * @param 	dstag	 数据源标识
	**/
	public void setDstag(String  dstag){
		 this.dstag=dstag;
 	}

	/**
	  * 获取数据源标识的值
	 * @return 返回数据源标识的值
	**/
	public String getDstag(){
		 return dstag;
 	}

	/**
	  * 设置加载方式的值
	 * @param 	loadType	 加载方式
	**/
	public void setLoadType(Integer  loadType){
		 this.loadType=loadType;
 	}

	/**
	  * 获取加载方式的值
	 * @return 返回加载方式的值
	**/
	public Integer getLoadType(){
		 return loadType;
 	}

	/**
	  * 设置数据代码的值
	 * @param 	dataCode	 数据代码
	**/
	public void setDataCode(String  dataCode){
		 this.dataCode=dataCode;
 	}

	/**
	  * 获取数据代码的值
	 * @return 返回数据代码的值
	**/
	public String getDataCode(){
		 return dataCode;
 	}

	/**
	  * 设置链接参数的值
	 * @param 	urlparams	 链接参数
	**/
	public void setUrlparams(String  urlparams){
		 this.urlparams=urlparams;
 	}

	/**
	  * 获取链接参数的值
	 * @return 返回链接参数的值
	**/
	public String getUrlparams(){
		 return urlparams;
 	}

	/**
	  * 设置数据加载顺序的值
	 * @param 	orderNo	 数据加载顺序
	**/
	public void setOrderNo(Integer  orderNo){
		 this.orderNo=orderNo;
 	}

	/**
	  * 获取数据加载顺序的值
	 * @return 返回数据加载顺序的值
	**/
	public Integer getOrderNo(){
		 return orderNo;
 	}

	/**
	  * 设置数据展示方式的值
	 * @param 	dispType	 数据展示方式
	**/
	public void setDispType(Integer  dispType){
		 this.dispType=dispType;
 	}

	/**
	  * 获取数据展示方式的值
	 * @return 返回数据展示方式的值
	**/
	public Integer getDispType(){
		 return dispType;
 	}

	/**
	  * 设置依赖数据源的值
	 * @param 	relydsId	 依赖数据源
	**/
	public void setRelydsId(Long  relydsId){
		 this.relydsId=relydsId;
 	}

	/**
	  * 获取依赖数据源的值
	 * @return 返回依赖数据源的值
	**/
	public Long getRelydsId(){
		 return relydsId;
 	}

	/**
	  * 设置依赖数据源列的值
	 * @param 	relyCmns	 依赖数据源列
	**/
	public void setRelyCmns(String  relyCmns){
		 this.relyCmns=relyCmns;
 	}

	/**
	  * 获取依赖数据源列的值
	 * @return 返回依赖数据源列的值
	**/
	public String getRelyCmns(){
		 return relyCmns;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{tempId,dstag,loadType,dataCode,urlparams,orderNo,dispType,relydsId,relyCmns};
	}

	@Override
	public String[] getFields() {
		return new String[]{"tempId","dstag","loadType","dataCode","urlparams","orderNo","dispType","relydsId","relyCmns"};
	}

}
