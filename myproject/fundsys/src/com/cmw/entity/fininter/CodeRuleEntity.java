package com.cmw.entity.fininter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdEntity;


/**
 * 凭证编号规则
 * @author 程明卫
 * @date 2013-09-01T00:00:00
 */
@Description(remark="凭证编号规则实体",createDate="2013-09-01T00:00:00",author="程明卫")
@Entity
@Table(name="fs_CodeRule")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class CodeRuleEntity extends IdEntity {
	
	
	 @Description(remark="会计年度")
	 @Column(name="fyear" ,nullable=false )
	 private Integer fyear;

	 @Description(remark="会计期间")
	 @Column(name="fperiod" ,nullable=false )
	 private Integer fperiod;

	 @Description(remark="凭证字")
	 @Column(name="fgroupId" ,nullable=false ,length=50 )
	 private String fgroupId;

	 @Description(remark="最大凭证号")
	 @Column(name="fnumber" ,nullable=false )
	 private Integer fnumber = 0;
	 
	 @Description(remark="是否发生更改")
	 @Column(name="change" ,nullable=false )
	 private Integer change = 0;

	public CodeRuleEntity() {

	}

	
	/**
	  * 设置会计年度的值
	 * @param 	fyear	 会计年度
	**/
	public void setFyear(Integer  fyear){
		 this.fyear=fyear;
 	}

	/**
	  * 获取会计年度的值
	 * @return 返回会计年度的值
	**/
	public Integer getFyear(){
		 return fyear;
 	}

	/**
	  * 设置会计期间的值
	 * @param 	fperiod	 会计期间
	**/
	public void setFperiod(Integer  fperiod){
		 this.fperiod=fperiod;
 	}

	/**
	  * 获取会计期间的值
	 * @return 返回会计期间的值
	**/
	public Integer getFperiod(){
		 return fperiod;
 	}

	/**
	  * 设置凭证字的值
	 * @param 	fgroupId	 凭证字
	**/
	public void setFgroupId(String  fgroupId){
		 this.fgroupId=fgroupId;
 	}

	/**
	  * 获取凭证字的值
	 * @return 返回凭证字的值
	**/
	public String getFgroupId(){
		 return fgroupId;
 	}

	/**
	  * 设置最大凭证号的值
	 * @param 	fnumber	 最大凭证号
	**/
	public void setFnumber(Integer  fnumber){
		 this.fnumber=fnumber;
 	}

	/**
	  * 获取最大凭证号的值
	 * @return 返回最大凭证号的值
	**/
	public Integer getFnumber(){
		 return fnumber;
 	}



	public Integer getChange() {
		return change;
	}


	public void setChange(Integer change) {
		this.change = change;
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{getId(),fyear,fperiod,fgroupId,fnumber,change};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","fyear","fperiod","fgroupId","fnumber","change"};
	}

}
