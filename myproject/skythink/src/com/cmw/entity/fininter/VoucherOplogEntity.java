package com.cmw.entity.fininter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 凭证日志
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
/**
 * @author chengmingwei
 *
 */
/**
 * @author chengmingwei
 *
 */
@Description(remark="凭证日志实体",createDate="2013-03-28T00:00:00",author="程明卫")
@Entity
@Table(name="fs_VoucherOplog")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class VoucherOplogEntity extends IdBaseEntity {
	
	
	 @Description(remark="系统ID")
	 @Column(name="sysId" ,nullable=false )
	 private Long sysId;

	 @Description(remark="凭证模板ID")
	 @Column(name="vtempId" ,nullable=false )
	 private Long vtempId;

	 @Description(remark="实收金额日志ID")
	 @Column(name="amountLogId" ,nullable=false )
	 private Long amountLogId=0L;
	 
	 @Description(remark="状态码")
	 @Column(name="status" )
	 private Integer status;

	 @Description(remark="错误代码")
	 @Column(name="errCode" ,nullable=false )
	 private Integer errCode;

	 @Description(remark="异常原因")
	 @Column(name="reason" ,length=500 )
	 private String reason;


	public VoucherOplogEntity() {

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
	  * 设置凭证模板ID的值
	 * @param 	vtempId	 凭证模板ID
	**/
	public void setVtempId(Long  vtempId){
		 this.vtempId=vtempId;
 	}

	/**
	  * 获取凭证模板ID的值
	 * @return 返回凭证模板ID的值
	**/
	public Long getVtempId(){
		 return vtempId;
 	}

	
	public Long getAmountLogId() {
		return amountLogId;
	}


	public void setAmountLogId(Long amountLogId) {
		this.amountLogId = amountLogId;
	}


	/**
	  * 设置状态码的值
	 * @param 	status	 状态码
	**/
	public void setStatus(Integer  status){
		 this.status=status;
 	}

	/**
	  * 获取状态码的值
	 * @return 返回状态码的值
	**/
	public Integer getStatus(){
		 return status;
 	}

	/**
	  * 设置错误代码的值
	 * @param 	errCode	 错误代码
	**/
	public void setErrCode(Integer  errCode){
		 this.errCode=errCode;
 	}

	/**
	  * 获取错误代码的值
	 * @return 返回错误代码的值
	**/
	public Integer getErrCode(){
		 return errCode;
 	}

	/**
	  * 设置异常原因的值
	 * @param 	reason	 异常原因
	**/
	public void setReason(String  reason){
		 this.reason=reason;
 	}

	/**
	  * 获取异常原因的值
	 * @return 返回异常原因的值
	**/
	public String getReason(){
		 return reason;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{sysId,vtempId,amountLogId,status,errCode,reason};
	}

	@Override
	public String[] getFields() {
		return new String[]{"sysId","vtempId","amountLogId","status","errCode","reason"};
	}

}
