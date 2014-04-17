package com.cmw.core.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.cmw.entity.finance.ExtPlanEntity;

/**
 * 实体基类	
 * 当实体中有 "isenabled,remark,creator,createTime,deptId,orgid,modifier,modifytime"
 * 这些共有属性时可实现此类
 * @author Administrator
 *	
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class BaseEntity implements Serializable,BaseInter ,Cloneable{
	// base Fields
	@Column(name="orgid",nullable=false,updatable=false)
	protected Long orgid;			//机构ID
	@Column(name="deptId",nullable=false,updatable=false)
	protected Long deptId;		//部门ID
	@Column(name="isenabled",nullable=false)
	protected Byte isenabled=1;	//可用标识	
	@Column(name="remark",length=500)
	protected String remark;		//备注
	@Column(name="creator",nullable=false,updatable=false)
	protected Long creator;		//创建人
	@Column(name="createTime",nullable=false,updatable=false)
	protected Date createTime=new Date();	//创建时间
	@Column(name="modifier",insertable=false)
	protected Long modifier;		//修改人
	@Column(name="modifytime",insertable=false)
	protected Date modifytime;	//修改时间
	@Column(name="empId")
	protected Long empId;	//员工ID 
	public Long getOrgid() {
		return orgid;
	}
	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Byte getIsenabled() {
		return isenabled;
	}
	public void setIsenabled(Byte isenabled) {
		this.isenabled = isenabled;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getCreator() {
		return creator;
	}
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getModifier() {
		return modifier;
	}
	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}
	public Date getModifytime() {
		return modifytime;
	}
	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	/**
	 * 实现克隆对象
	 */
	  public  Object clone(){
        try{
             return   (Object) super .clone();
        }catch(CloneNotSupportedException e){
            return   null ;
       } 
      }
}
