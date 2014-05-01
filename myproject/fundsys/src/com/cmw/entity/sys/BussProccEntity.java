package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 子业务流程
 * @author 程明卫
 * @date 2013-08-26T00:00:00
 */
@Description(remark="子业务流程实体",createDate="2013-08-26T00:00:00",author="程明卫")
@Entity
@Table(name="ts_BussProcc")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class BussProccEntity extends IdBaseEntity {
	
	
	 @Description(remark="系统Id")
	 @Column(name="sysId" ,nullable=false )
	 private Long sysId;

	 @Description(remark="流程编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;

	 @Description(remark="流程名称")
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;

	 @Description(remark="业务性质")
	 @Column(name="bussType" ,nullable=false )
	 private Integer bussType = 0;

	 @Description(remark="适用机构")
	 @Column(name="useorg" ,nullable=false )
	 private Integer useorg = 0;

	 @Description(remark="指定公司ID")
	 @Column(name="companyIds" ,length=80 )
	 private String companyIds;

	 @Description(remark="指定公司名称")
	 @Column(name="companyNames" ,length=200 )
	 private String companyNames;

	 @Description(remark="流程定义ID")
	 @Column(name="pdid" ,length=50 )
	 private String pdid;

	 @Description(remark="关联功能")
	 @Column(name="menuId" )
	 private Long menuId;

	 @Description(remark="关联表单地址")
	 @Column(name="formUrl" ,length=150 )
	 private String formUrl;
	 
	 @Description(remark="流程图标")
	 @Column(name="icon" ,length=150 )
	 private String icon;

	 @Description(remark="条款文件地址")
	 @Column(name="txtPath" ,length=200 )
	 private String txtPath;


	public BussProccEntity() {

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
	  * 设置流程编号的值
	 * @param 	code	 流程编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取流程编号的值
	 * @return 返回流程编号的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置流程名称的值
	 * @param 	name	 流程名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取流程名称的值
	 * @return 返回流程名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置业务性质的值
	 * @param 	bussType	 业务性质
	**/
	public void setBussType(Integer  bussType){
		 this.bussType=bussType;
 	}

	/**
	  * 获取业务性质的值
	 * @return 返回业务性质的值
	**/
	public Integer getBussType(){
		 return bussType;
 	}

	/**
	  * 设置适用机构的值
	 * @param 	useorg	 适用机构
	**/
	public void setUseorg(Integer  useorg){
		 this.useorg=useorg;
 	}

	/**
	  * 获取适用机构的值
	 * @return 返回适用机构的值
	**/
	public Integer getUseorg(){
		 return useorg;
 	}

	/**
	  * 设置指定公司ID的值
	 * @param 	companyIds	 指定公司ID
	**/
	public void setCompanyIds(String  companyIds){
		 this.companyIds=companyIds;
 	}

	/**
	  * 获取指定公司ID的值
	 * @return 返回指定公司ID的值
	**/
	public String getCompanyIds(){
		 return companyIds;
 	}

	/**
	  * 设置指定公司名称的值
	 * @param 	companyNames	 指定公司名称
	**/
	public void setCompanyNames(String  companyNames){
		 this.companyNames=companyNames;
 	}

	/**
	  * 获取指定公司名称的值
	 * @return 返回指定公司名称的值
	**/
	public String getCompanyNames(){
		 return companyNames;
 	}

	/**
	  * 设置流程定义ID的值
	 * @param 	pdid	 流程定义ID
	**/
	public void setPdid(String  pdid){
		 this.pdid=pdid;
 	}

	/**
	  * 获取流程定义ID的值
	 * @return 返回流程定义ID的值
	**/
	public String getPdid(){
		 return pdid;
 	}

	/**
	  * 设置关联功能的值
	 * @param 	menuId	 关联功能
	**/
	public void setMenuId(Long  menuId){
		 this.menuId=menuId;
 	}

	/**
	  * 获取关联功能的值
	 * @return 返回关联功能的值
	**/
	public Long getMenuId(){
		 return menuId;
 	}

	/**
	  * 设置关联表单地址的值
	 * @param 	formUrl	 关联表单地址
	**/
	public void setFormUrl(String  formUrl){
		 this.formUrl=formUrl;
 	}

	/**
	  * 获取关联表单地址的值
	 * @return 返回关联表单地址的值
	**/
	public String getFormUrl(){
		 return formUrl;
 	}

	
	/**
	  * 获取流程图标的值
	 * @return 返回流程图标的值
	**/
	public String getIcon() {
		return icon;
	}

	/**
	  * 设置流程图标的值
	 * @param 	icon	流程图标值
	**/
	public void setIcon(String icon) {
		this.icon = icon;
	}


	/**
	  * 设置条款文件地址的值
	 * @param 	txtPath	 条款文件地址
	**/
	public void setTxtPath(String  txtPath){
		 this.txtPath=txtPath;
 	}

	/**
	  * 获取条款文件地址的值
	 * @return 返回条款文件地址的值
	**/
	public String getTxtPath(){
		 return txtPath;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,sysId,code,name,bussType,useorg,companyIds,companyNames,pdid,menuId,formUrl,icon,txtPath,isenabled};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","sysId","code","name","bussType","useorg","companyIds","companyNames","pdid","menuId","formUrl","icon","txtPath","isenabled"};
	}

}
