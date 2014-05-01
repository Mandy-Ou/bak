package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 附件
 * @author 程明卫
 * @date 2012-12-04T00:00:00
 */
@Description(remark="附件实体",createDate="2012-12-04T00:00:00",author="程明卫")
@Entity
@Table(name="ts_Attachment")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class AttachmentEntity extends IdBaseEntity {
	
	
	 @Description(remark="系统Id")
	 @Column(name="sysId" ,nullable=false )
	 private Long sysId;



	 @Description(remark="业务类型")
	 @Column(name="formType" ,nullable=false )
	 private Integer formType;



	 @Description(remark="业务单ID")
	 @Column(name="formId" ,nullable=false ,length=50 )
	 private String formId;



	 @Description(remark="附件类型")
	 @Column(name="atype" ,nullable=false )
	 private Integer atype;



	 @Description(remark="文件名称")
	 @Column(name="fileName" ,nullable=false ,length=255 )
	 private String fileName;



	 @Description(remark="文件路径")
	 @Column(name="filePath" ,nullable=false ,length=255 )
	 private String filePath;

	 @Description(remark="swfPath")
	 @Column(name="swfPath" ,length=255 )
	 private String swfPath;

	 @Description(remark="文件大小")
	 @Column(name="fileSize" )
	 private Long fileSize;
	 
	 @Description(remark="备用formId2")
	 @Column(name="formId2" )
	 private String formId2;

	 

	


	/**
	 * @return the formId2
	 */
	public String getFormId2() {
		return formId2;
	}


	/**
	 * @param formId2 the formId2 to set
	 */
	public void setFormId2(String formId2) {
		this.formId2 = formId2;
	}


	public AttachmentEntity() {

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
	  * 设置业务类型的值
	 * @param 	formType	 业务类型
	**/
	public void setFormType(Integer  formType){
		 this.formType=formType;
 	}

	/**
	  * 获取业务类型的值
	 * @return 返回业务类型的值
	**/
	public Integer getFormType(){
		 return formType;
 	}

	/**
	  * 设置业务单ID的值
	 * @param 	formId	 业务单ID
	**/
	public void setFormId(String  formId){
		 this.formId=formId;
 	}

	/**
	  * 获取业务单ID的值
	 * @return 返回业务单ID的值
	**/
	public String getFormId(){
		 return formId;
 	}

	/**
	  * 设置附件类型的值
	 * @param 	atype	 附件类型
	**/
	public void setAtype(Integer  atype){
		 this.atype=atype;
 	}

	/**
	  * 获取附件类型的值
	 * @return 返回附件类型的值
	**/
	public Integer getAtype(){
		 return atype;
 	}

	/**
	  * 设置文件名称的值
	 * @param 	fileName	 文件名称
	**/
	public void setFileName(String  fileName){
		 this.fileName=fileName;
 	}

	/**
	  * 获取文件名称的值
	 * @return 返回文件名称的值
	**/
	public String getFileName(){
		 return fileName;
 	}

	/**
	  * 设置文件路径的值
	 * @param 	filePath	 文件路径
	**/
	public void setFilePath(String  filePath){
		 this.filePath=filePath;
 	}

	/**
	  * 获取文件路径的值
	 * @return 返回文件路径的值
	**/
	public String getFilePath(){
		 return filePath;
 	}

	
	public String getSwfPath() {
		return swfPath;
	}

	public void setSwfPath(String swfPath) {
		this.swfPath = swfPath;
	}


	/**
	  * 设置文件大小的值
	 * @param 	fileSize	 文件大小
	**/
	public void setFileSize(Long  fileSize){
		 this.fileSize=fileSize;
 	}

	/**
	  * 获取文件大小的值
	 * @return 返回文件大小的值
	**/
	public Long getFileSize(){
		 return fileSize;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,sysId,isenabled,formType,formId,atype,fileName,filePath,swfPath,fileSize,formId2,createTime};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","sysId","isenabled","formType","formId","atype","fileName","filePath","swfPath","fileSize","formId2","createTime##yyyy-MM-dd"};
	}

}
