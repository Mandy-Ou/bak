package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 桌面模块配置
 * @author 程明卫
 * @date 2013-03-08T00:00:00
 */
@Description(remark="桌面模块配置实体",createDate="2013-03-08T00:00:00",author="程明卫")
@Entity
@Table(name="ts_DeskMod")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class DeskModEntity extends IdBaseEntity {
	
	
	 @Description(remark="系统ID")
	 @Column(name="sysId" ,nullable=false )
	 private Long sysId;

	 @Description(remark="模块编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;

	 @Description(remark="模块标题")
	 @Column(name="title" ,nullable=false ,length=50 )
	 private String title;

	 @Description(remark="标题样式")
	 @Column(name="cls" ,length=20 )
	 private String cls;

	 @Description(remark="排列顺序")
	 @Column(name="orderNo" ,nullable=false )
	 private Integer orderNo = 0;

	 @Description(remark="消息条数")
	 @Column(name="msgCount" ,nullable=false )
	 private Integer msgCount = 7;

	 @Description(remark="展示方式")
	 @Column(name="dispType" ,nullable=false )
	 private Integer dispType = 1;

	 @Description(remark="链接地址")
	 @Column(name="url" ,length=50 )
	 private String url;

	 @Description(remark="链接参数")
	 @Column(name="urlparams" ,length=50 )
	 private String urlparams;

	 @Description(remark="默认模块")
	 @Column(name="isdefault" ,nullable=false )
	 private Integer isdefault = 1;

	 @Description(remark="显示更多")
	 @Column(name="ismore" ,nullable=false )
	 private Integer ismore = 1;

	 @Description(remark="更多url")
	 @Column(name="moreUrl" ,length=100 )
	 private String moreUrl;

	 @Description(remark="加载方式")
	 @Column(name="loadType" ,nullable=false )
	 private Integer loadType;

	 @Description(remark="数据代码")
	 @Column(name="dataCode" ,length=1000 )
	 private String dataCode;

	 @Description(remark="显示列名")
	 @Column(name="dispcmns" ,length=80 )
	 private String dispcmns;

	 @Description(remark="数据模板")
	 @Column(name="dataTemp" ,length=500 )
	 private String dataTemp;


	public DeskModEntity() {

	}

	
	/**
	  * 设置系统ID的值
	 * @param 	sysId	 系统ID
	**/
	public void setSysId(Long  sysId){
		 this.sysId=sysId;
 	}

	/**
	  * 获取系统ID的值
	 * @return 返回系统ID的值
	**/
	public Long getSysId(){
		 return sysId;
 	}

	/**
	  * 设置模块编号的值
	 * @param 	code	 模块编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取模块编号的值
	 * @return 返回模块编号的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置模块标题的值
	 * @param 	title	 模块标题
	**/
	public void setTitle(String  title){
		 this.title=title;
 	}

	/**
	  * 获取模块标题的值
	 * @return 返回模块标题的值
	**/
	public String getTitle(){
		 return title;
 	}

	/**
	  * 设置标题样式的值
	 * @param 	cls	 标题样式
	**/
	public void setCls(String  cls){
		 this.cls=cls;
 	}

	/**
	  * 获取标题样式的值
	 * @return 返回标题样式的值
	**/
	public String getCls(){
		 return cls;
 	}

	/**
	  * 设置排列顺序的值
	 * @param 	orderNo	 排列顺序
	**/
	public void setOrderNo(Integer  orderNo){
		 this.orderNo=orderNo;
 	}

	/**
	  * 获取排列顺序的值
	 * @return 返回排列顺序的值
	**/
	public Integer getOrderNo(){
		 return orderNo;
 	}

	/**
	  * 设置消息条数的值
	 * @param 	msgCount	 消息条数
	**/
	public void setMsgCount(Integer  msgCount){
		 this.msgCount=msgCount;
 	}

	/**
	  * 获取消息条数的值
	 * @return 返回消息条数的值
	**/
	public Integer getMsgCount(){
		 return msgCount;
 	}

	/**
	  * 设置展示方式的值
	 * @param 	dispType	 展示方式
	**/
	public void setDispType(Integer  dispType){
		 this.dispType=dispType;
 	}

	/**
	  * 获取展示方式的值
	 * @return 返回展示方式的值
	**/
	public Integer getDispType(){
		 return dispType;
 	}

	/**
	  * 设置链接地址的值
	 * @param 	url	 链接地址
	**/
	public void setUrl(String  url){
		 this.url=url;
 	}

	/**
	  * 获取链接地址的值
	 * @return 返回链接地址的值
	**/
	public String getUrl(){
		 return url;
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
	  * 设置默认模块的值
	 * @param 	isdefault	 默认模块
	**/
	public void setIsdefault(Integer  isdefault){
		 this.isdefault=isdefault;
 	}

	/**
	  * 获取默认模块的值
	 * @return 返回默认模块的值
	**/
	public Integer getIsdefault(){
		 return isdefault;
 	}

	/**
	  * 设置显示更多的值
	 * @param 	ismore	 显示更多
	**/
	public void setIsmore(Integer  ismore){
		 this.ismore=ismore;
 	}

	/**
	  * 获取显示更多的值
	 * @return 返回显示更多的值
	**/
	public Integer getIsmore(){
		 return ismore;
 	}

	/**
	  * 设置更多url的值
	 * @param 	moreUrl	 更多url
	**/
	public void setMoreUrl(String  moreUrl){
		 this.moreUrl=moreUrl;
 	}

	/**
	  * 获取更多url的值
	 * @return 返回更多url的值
	**/
	public String getMoreUrl(){
		 return moreUrl;
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
	  * 设置显示列名的值
	 * @param 	dispcmns	 显示列名
	**/
	public void setDispcmns(String  dispcmns){
		 this.dispcmns=dispcmns;
 	}

	/**
	  * 获取显示列名的值
	 * @return 返回显示列名的值
	**/
	public String getDispcmns(){
		 return dispcmns;
 	}

	/**
	  * 设置数据模板的值
	 * @param 	dataTemp	 数据模板
	**/
	public void setDataTemp(String  dataTemp){
		 this.dataTemp=dataTemp;
 	}

	/**
	  * 获取数据模板的值
	 * @return 返回数据模板的值
	**/
	public String getDataTemp(){
		 return dataTemp;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,sysId,code,title,cls,orderNo,msgCount,dispType,url,urlparams,isdefault,ismore,moreUrl,loadType,dataCode,dispcmns,dataTemp,isenabled};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","sysId","code","title","cls","orderNo","msgCount","dispType","url","urlparams","isdefault","ismore","moreUrl","loadType","dataCode","dispcmns","dataTemp","isenabled"};
	}

}
