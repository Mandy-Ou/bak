package com.cmw.service.impl.fininter.external.generic;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class VoucherModel implements Serializable {
	//系统ID
	private Long sysId;
	//凭证模板ID
	private Long vtempId;
	//UUID
	private String uuid;
	//凭证ID
	private String voucherId;
	//实收金额日志ID --> fs_AmountLog(实收金额日志表) 表ID
	private Long amountLogId;
	//公司代码
	private String brno;
	//登记日期
	private Date regDate;
	//业务日期
	private Date transDate;
	//会计年度
	private Integer year;
	//所属月份或会计期间
	private Integer period;
	//凭证字ID
	private String groupId;
	//凭证编号
	private String number;
	//参考信息
	private String reference;
	//摘要
	private String explanation;
	//附件数
	private Integer attachments;
	//凭证分录行数
	private Integer entryCount=0;
	//借方总金额
	private Double debitTotal;
	//贷方总金额
	private Double creditTotal;
	//凭证分录记录
	private List<EntryModel> entrys;
	
	
	/**
	 * 获取	系统ID
	 * @return 返回   系统ID
	 */
	public Long getSysId() {
		return sysId;
	}
	
	/**
	 *  设置  sysId
	 * @param sysId sysId
	 */
	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	/**
	 * 获取	凭证模板ID
	 * @return 返回   凭证模板ID
	 */
	public Long getVtempId() {
		return vtempId;
	}
	
	/**
	 *  设置 凭证模板ID
	 * @param vtempId 凭证模板ID
	 */
	public void setVtempId(Long vtempId) {
		this.vtempId = vtempId;
	}

	/**
	 * 获取	UUID
	 * @return 返回   UUID
	 */
	public String getUuid() {
		return uuid;
	}
	
	/**
	 *  设置  UUID
	 * @param uuid UUID
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	/**
	 * 获取 凭证ID
	 * @return 返回   凭证ID
	 */
	public String getVoucherId() {
		return voucherId;
	}
	
	/**
	 *  设置  凭证ID
	 * @param voucherId 凭证ID
	 */
	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}
	
	/**
	 * 获取 实收金额日志ID
	 * @return 返回   实收金额日志ID
	 */
	public Long getAmountLogId() {
		return amountLogId;
	}
	
	/**
	 *  设置 实收金额日志ID
	 * @param amountLogId 实收金额日志ID
	 */
	public void setAmountLogId(Long amountLogId) {
		this.amountLogId = amountLogId;
	}
	
	/**
	 * 获取 	公司代码
	 * @return 返回  	公司代码
	 */
	public String getBrno() {
		return brno;
	}
	
	/**
	 *  设置 	公司代码
	 * @param brno	公司代码
	 */
	public void setBrno(String brno) {
		this.brno = brno;
	}
	
	/**
	 * 获取	登记日期
	 * @return 返回  	登记日期
	 */
	public Date getRegDate() {
		return regDate;
	}
	
	/**
	 *  设置 	登记日期
	 * @param regDate	登记日期
	 */
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	/**
	 * 获取	业务日期
	 * @return 返回  	业务日期
	 */
	public Date getTransDate() {
		return transDate;
	}
	
	/**
	 *  设置 	业务日期
	 * @param transDate	业务日期
	 */
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	
	/**
	 * 获取	会计年度
	 * @return 返回  	会计年度
	 */
	public Integer getYear() {
		return year;
	}
	
	/**
	 *  设置 	会计年度
	 * @param year	会计年度
	 */
	public void setYear(Integer year) {
		this.year = year;
	}
	
	/**
	 * 获取	所属月份或会计期间
	 * @return 返回  	所属月份或会计期间
	 */
	public Integer getPeriod() {
		return period;
	}
	
	/**
	 *  设置 	所属月份或会计期间
	 * @param period	所属月份或会计期间
	 */
	public void setPeriod(Integer period) {
		this.period = period;
	}
	
	/**
	 * 获取	凭证字ID
	 * @return 返回  	凭证字ID
	 */
	public String getGroupId() {
		return groupId;
	}
	
	/**
	 *  设置 	凭证字ID
	 * @param groupId	凭证字ID
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	/**
	 * 获取	凭证编号
	 * @return 返回  凭证编号
	 */
	public String getNumber() {
		return number;
	}
	
	/**
	 *  设置 凭证编号
	 * @param number	凭证编号
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	
	/**
	 * 获取	参考信息
	 * @return 返回  	参考信息
	 */
	public String getReference() {
		return reference;
	}
	
	/**
	 *  设置 	参考信息
	 * @param reference	参考信息
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	/**
	 * 获取	摘要
	 * @return 返回  	摘要
	 */
	public String getExplanation() {
		return explanation;
	}
	
	/**
	 *  设置 	摘要
	 * @param explanation	摘要
	 */
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	
	/**
	 * 获取	附件数
	 * @return 返回  	附件数
	 */
	public Integer getAttachments() {
		return attachments;
	}
	
	/**
	 *  设置 	附件数
	 * @param attachments	附件数
	 */
	public void setAttachments(Integer attachments) {
		this.attachments = attachments;
	}
	
	/**
	 * 获取	凭证分录行数
	 * @return 返回  	凭证分录行数
	 */
	public Integer getEntryCount() {
		return entryCount;
	}
	
	/**
	 *  设置 凭证分录行数
	 * @param entryCount	凭证分录行数
	 */
	public void setEntryCount(Integer entryCount) {
		this.entryCount = entryCount;
	}
	
	/**
	 * 获取	借方总金额
	 * @return 返回  	借方总金额
	 */
	public Double getDebitTotal() {
		return debitTotal;
	}
	
	/**
	 *  设置 	借方总金额
	 * @param debitTotal	借方总金额
	 */
	public void setDebitTotal(Double debitTotal) {
		this.debitTotal = debitTotal;
	}
	
	/**
	 * 获取	贷方总金额
	 * @return 返回  	贷方总金额
	 */
	public Double getCreditTotal() {
		return creditTotal;
	}
	
	/**
	 *  设置 贷方总金额
	 * @param creditTotal 贷方总金额
	 */
	public void setCreditTotal(Double creditTotal) {
		this.creditTotal = creditTotal;
	}
	
	/**
	 * 获取	凭证分录记录
	 * @return 返回  	凭证分录记录
	 */
	public List<EntryModel> getEntrys() {
		return entrys;
	}
	
	/**
	 *  设置 	凭证分录记录
	 * @param entrys 凭证分录记录
	 */
	public void setEntrys(List<EntryModel> entrys) {
		this.entrys = entrys;
	}
	
	
}
