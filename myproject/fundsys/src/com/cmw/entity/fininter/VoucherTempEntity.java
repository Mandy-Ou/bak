package com.cmw.entity.fininter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 凭证模板
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="凭证模板实体",createDate="2013-03-28T00:00:00",author="程明卫")
@Entity
@Table(name="fs_VoucherTemp")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class VoucherTempEntity extends IdBaseEntity {
	
	
	 @Description(remark="财务系统ID")
	 @Column(name="finsysId" ,nullable=false )
	 private Long finsysId;

	 @Description(remark="凭证模板编号")
	 @Column(name="code" ,nullable=false ,length=30 )
	 private String code;

	 @Description(remark="凭证模板名称")
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;

	 @Description(remark="凭证字ID")
	 @Column(name="groupId" ,nullable=false ,length=50 )
	 private String groupId;

	 @Description(remark="默认币别")
	 @Column(name="currencyId" ,nullable=false ,length=50 )
	 private String currencyId;

	 @Description(remark="分录方向")
	 @Column(name="entry" ,nullable=false )
	 private Integer entry;

	 @Description(remark="批量业务策略")
	 @Column(name="tactics" ,nullable=false )
	 private Integer tactics = 1;

	 @Description(remark="分录最大条数")
	 @Column(name="maxcount" ,nullable=false )
	 private Integer maxcount = 500;


	public VoucherTempEntity() {

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

	/**
	  * 设置凭证模板编号的值
	 * @param 	code	 凭证模板编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取凭证模板编号的值
	 * @return 返回凭证模板编号的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置凭证模板名称的值
	 * @param 	name	 凭证模板名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取凭证模板名称的值
	 * @return 返回凭证模板名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置凭证字ID的值
	 * @param 	groupId	 凭证字ID
	**/
	public void setGroupId(String  groupId){
		 this.groupId=groupId;
 	}

	/**
	  * 获取凭证字ID的值
	 * @return 返回凭证字ID的值
	**/
	public String getGroupId(){
		 return groupId;
 	}

	/**
	  * 设置默认币别的值
	 * @param 	currencyId	 默认币别
	**/
	public void setCurrencyId(String  currencyId){
		 this.currencyId=currencyId;
 	}

	/**
	  * 获取默认币别的值
	 * @return 返回默认币别的值
	**/
	public String getCurrencyId(){
		 return currencyId;
 	}

	/**
	  * 设置分录方向的值
	 * @param 	entry	 分录方向
	**/
	public void setEntry(Integer  entry){
		 this.entry=entry;
 	}

	/**
	  * 获取分录方向的值
	 * @return 返回分录方向的值
	**/
	public Integer getEntry(){
		 return entry;
 	}

	/**
	  * 设置批量业务策略的值
	 * @param 	tactics	 批量业务策略
	**/
	public void setTactics(Integer  tactics){
		 this.tactics=tactics;
 	}

	/**
	  * 获取批量业务策略的值
	 * @return 返回批量业务策略的值
	**/
	public Integer getTactics(){
		 return tactics;
 	}

	/**
	  * 设置分录最大条数的值
	 * @param 	maxcount	 分录最大条数
	**/
	public void setMaxcount(Integer  maxcount){
		 this.maxcount=maxcount;
 	}

	/**
	  * 获取分录最大条数的值
	 * @return 返回分录最大条数的值
	**/
	public Integer getMaxcount(){
		 return maxcount;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{finsysId,code,name,groupId,currencyId,entry,tactics,maxcount,isenabled};
	}

	@Override
	public String[] getFields() {
		return new String[]{"finsysId","code","name","groupId","currencyId","entry","tactics","maxcount","isenabled"};
	}

}
