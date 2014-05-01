package com.cmw.entity.fininter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 科目
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="科目实体",createDate="2013-03-28T00:00:00",author="程明卫")
@Entity
@Table(name="fs_Subject")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class SubjectEntity extends IdBaseEntity {
	
	
	 @Description(remark="财务系统ID")
	 @Column(name="finsysId" ,nullable=false )
	 private Long finsysId;

	 @Description(remark="科目号")
	 @Column(name="code" ,nullable=false ,length=50 )
	 private String code;

	 @Description(remark="科目名称")
	 @Column(name="name" ,nullable=false ,length=60 )
	 private String name;

	 @Description(remark="科目级次")
	 @Column(name="levels" )
	 private Integer levels;

	 @Description(remark="明细科目")
	 @Column(name="detail" )
	 private Byte detail;

	 @Description(remark="科目组ID")
	 @Column(name="groupId" )
	 private Long groupId;

	 @Description(remark="借贷方向")
	 @Column(name="dc" )
	 private Byte dc;

	 @Description(remark="币别ID")
	 @Column(name="currencyId" )
	 private Long currencyId;

	 @Description(remark="科目类型")
	 @Column(name="atype" )
	 private Integer atype;

	 @Description(remark="下挂核算项目类型")
	 @Column(name="itemClassId" )
	 private Long itemClassId;

	 @Description(remark="上级科目ID")
	 @Column(name="rootId" )
	 private Long rootId;

	 @Description(remark="科目ID")
	 @Column(name="refId" )
	 private Long refId;


	public SubjectEntity() {

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
	  * 设置科目号的值
	 * @param 	code	 科目号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取科目号的值
	 * @return 返回科目号的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置科目名称的值
	 * @param 	name	 科目名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取科目名称的值
	 * @return 返回科目名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置科目级次的值
	 * @param 	levels	 科目级次
	**/
	public void setLevels(Integer  levels){
		 this.levels=levels;
 	}

	/**
	  * 获取科目级次的值
	 * @return 返回科目级次的值
	**/
	public Integer getLevels(){
		 return levels;
 	}

	/**
	  * 设置明细科目的值
	 * @param 	detail	 明细科目
	**/
	public void setDetail(Byte  detail){
		 this.detail=detail;
 	}

	/**
	  * 获取明细科目的值
	 * @return 返回明细科目的值
	**/
	public Byte getDetail(){
		 return detail;
 	}

	/**
	  * 设置科目组ID的值
	 * @param 	groupId	 科目组ID
	**/
	public void setGroupId(Long  groupId){
		 this.groupId=groupId;
 	}

	/**
	  * 获取科目组ID的值
	 * @return 返回科目组ID的值
	**/
	public Long getGroupId(){
		 return groupId;
 	}

	/**
	  * 设置借贷方向的值
	 * @param 	dc	 借贷方向
	**/
	public void setDc(Byte  dc){
		 this.dc=dc;
 	}

	/**
	  * 获取借贷方向的值
	 * @return 返回借贷方向的值
	**/
	public Byte getDc(){
		 return dc;
 	}

	/**
	  * 设置币别ID的值
	 * @param 	currencyId	 币别ID
	**/
	public void setCurrencyId(Long  currencyId){
		 this.currencyId=currencyId;
 	}

	/**
	  * 获取币别ID的值
	 * @return 返回币别ID的值
	**/
	public Long getCurrencyId(){
		 return currencyId;
 	}

	/**
	  * 设置科目类型的值
	 * @param 	atype	 科目类型
	**/
	public void setAtype(Integer  atype){
		 this.atype=atype;
 	}

	/**
	  * 获取科目类型的值
	 * @return 返回科目类型的值
	**/
	public Integer getAtype(){
		 return atype;
 	}

	/**
	  * 设置下挂核算项目类型的值
	 * @param 	itemClassId	 下挂核算项目类型
	**/
	public void setItemClassId(Long  itemClassId){
		 this.itemClassId=itemClassId;
 	}

	/**
	  * 获取下挂核算项目类型的值
	 * @return 返回下挂核算项目类型的值
	**/
	public Long getItemClassId(){
		 return itemClassId;
 	}

	/**
	  * 设置上级科目ID的值
	 * @param 	rootId	 上级科目ID
	**/
	public void setRootId(Long  rootId){
		 this.rootId=rootId;
 	}

	/**
	  * 获取上级科目ID的值
	 * @return 返回上级科目ID的值
	**/
	public Long getRootId(){
		 return rootId;
 	}

	/**
	  * 设置科目ID的值
	 * @param 	refId	 科目ID
	**/
	public void setRefId(Long  refId){
		 this.refId=refId;
 	}

	/**
	  * 获取科目ID的值
	 * @return 返回科目ID的值
	**/
	public Long getRefId(){
		 return refId;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{finsysId,code,name,levels,detail,groupId,dc,currencyId,atype,itemClassId,rootId,refId,isenabled,createTime};
	}

	@Override
	public String[] getFields() {
		return new String[]{"finsysId","code","name","levels","detail","groupId","dc","currencyId","atype","itemClassId","rootId","refId","isenabled","createTime#yyyy-MM-dd HH:mm"};
	}

}
