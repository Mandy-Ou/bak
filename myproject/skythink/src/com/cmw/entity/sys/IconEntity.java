package com.cmw.entity.sys;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 系统图标
 * @author 程明卫
 * @date 2013-08-24T00:00:00
 */
@Description(remark="系统图标实体",createDate="2013-08-24T00:00:00",author="程明卫")
@Entity
@Table(name="ts_Icon")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class IconEntity extends IdBaseEntity {
	
	
	 @Description(remark="图标类型")
	 @Column(name="iconType" ,nullable=false )
	 private Integer iconType;

	 @Description(remark="文件名称")
	 @Column(name="fileName" ,nullable=false ,length=100 )
	 private String fileName;

	 @Description(remark="文件路径")
	 @Column(name="filePath" ,nullable=false ,length=225)
	 private String filePath;

	 @Description(remark="文件大小")
	 @Column(name="fileSize" ,nullable=false )
	 private Long fileSize = 0L;

	 @Description(remark="最后修改日期")
	 @Column(name="lastmod" )
	 private Date lastmod;


	public IconEntity() {

	}

	
	/**
	  * 设置图标类型的值
	 * @param 	iconType	 图标类型
	**/
	public void setIconType(Integer  iconType){
		 this.iconType=iconType;
 	}

	/**
	  * 获取图标类型的值
	 * @return 返回图标类型的值
	**/
	public Integer getIconType(){
		 return iconType;
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

	/**
	  * 设置最后修改日期的值
	 * @param 	lastmod	 最后修改日期
	**/
	public void setLastmod(Date  lastmod){
		 this.lastmod=lastmod;
 	}

	/**
	  * 获取最后修改日期的值
	 * @return 返回最后修改日期的值
	**/
	public Date getLastmod(){
		 return lastmod;
 	}

	@Override
	public Object[] getDatas() {
		return new Object[]{getId(),iconType,fileName,filePath,fileSize,lastmod};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","iconType","fileName","filePath","fileSize","lastmod#yyyy-MM-dd HH:mm:ss"};
	}

}
