package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 公式
 * @author pdh
 * @date 2012-12-06T00:00:00
 */
@Description(remark="公式实体",createDate="2012-12-06T00:00:00",author="pdh")
@Entity
@Table(name="ts_formula")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class FormulaEntity extends IdBaseEntity {
	
	
	 @Description(remark="编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;



	 @Description(remark="显示公式")
	 @Column(name="dispExpress" ,nullable=false ,length=255 )
	 private String dispExpress;



	 @Description(remark="实际公式表达式")
	 @Column(name="express" ,nullable=false ,length=255 )
	 private String express;



	 @Description(remark="公式字段ID列表")
	 @Column(name="fieldIds" ,length=150 )
	 private String fieldIds;




	public FormulaEntity() {

	}

	
	/**
	  * 设置编号的值
	 * @param 	code	 编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取编号的值
	 * @return 返回编号的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置显示公式的值
	 * @param 	dispExpress	 显示公式
	**/
	public void setDispExpress(String  dispExpress){
		 this.dispExpress=dispExpress;
 	}

	/**
	  * 获取显示公式的值
	 * @return 返回显示公式的值
	**/
	public String getDispExpress(){
		 return dispExpress;
 	}

	/**
	  * 设置实际公式表达式的值
	 * @param 	express	 实际公式表达式
	**/
	public void setExpress(String  express){
		 this.express=express;
 	}

	/**
	  * 获取实际公式表达式的值
	 * @return 返回实际公式表达式的值
	**/
	public String getExpress(){
		 return express;
 	}

	/**
	  * 设置公式字段ID列表的值
	 * @param 	fieldIds	 公式字段ID列表
	**/
	public void setFieldIds(String  fieldIds){
		 this.fieldIds=fieldIds;
 	}

	/**
	  * 获取公式字段ID列表的值
	 * @return 返回公式字段ID列表的值
	**/
	public String getFieldIds(){
		 return fieldIds;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,code,dispExpress,express,fieldIds};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","code","dispExpress","express","fieldIds"};
	}

}
