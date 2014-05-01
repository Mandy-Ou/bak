package com.cmw.entity.funds;


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 借款承诺书
 * @author 李听
 * @date 2014-01-20T00:00:00
 */
@Description(remark="股东资料交接表实体",createDate="2014-01-20T00:00:00",author="李听")
@Entity
@Table(name="fu_ShareData")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class ShareDataEntity extends IdBaseEntity {
	
	
	 @Description(remark="委托客户ID")
	 @Column(name="entrustCustId" ,nullable=false )
	 private Long entrustCustId;
	 
	 @Description(remark="收件日期")
	 @Column(name="getDate" )
	 private Date getDate;
	 
	 @Description(remark="交接日期")
	 @Column(name="tranDate" )
	 private Date tranDate;
	 
	 @Description(remark="资料保管人")
	 @Column(name="dataSaveMan" ,nullable=false ,length=50 )
	 private String dataSaveMan;
	

	public Long getEntrustCustId() {
		return entrustCustId;
	}

	public void setEntrustCustId(Long entrustCustId) {
		this.entrustCustId = entrustCustId;
	}

	public Date getGetDate() {
		return getDate;
	}

	public void setGetDate(Date getDate) {
		this.getDate = getDate;
	}

	public Date getTranDate() {
		return tranDate;
	}

	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
	}

	public String getDataSaveMan() {
		return dataSaveMan;
	}

	public void setDataSaveMan(String dataSaveMan) {
		this.dataSaveMan = dataSaveMan;
	}

	@Override
	public Object[] getDatas() {
		return new Object[]{id,entrustCustId,getDate,tranDate,dataSaveMan};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","entrustCustId","getDate","tranDate","dataSaveMan"};
	}


}
