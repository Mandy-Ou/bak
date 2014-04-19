package com.cmw.service.impl.fininter.external.generic;

import java.io.Serializable;

/**
 * 财务通用分录模型类
 * @author chengmingwei
 *
 */
@SuppressWarnings("serial")
public class EntryModel implements Serializable {
	//凭证ID
	private Long voucherId;
	//分录ID
	private Long entryId;
	//摘要
	private String explanation;
	//科目ID或编号
	private String accountId;
	//对方科目ID或编号
	private String accountId2;
	//币别ID或编号
	private String currencyId;
	//汇率
	private Float exchangeRate;
	//余额方向	0:贷方,1:借方
	private Integer dc;
	//外币金额
	private Double amountFor;
	//本位币金额
	private Double amount;
	//结算方式
	private String settleTypeId;
	//结算方式名称
	private String settleType;
	//结算号
	private String settleNo;
	//核算项目ID
	private Long detailId = 0L;
	//核算项对象
	private ItemModel item;
	
	/**
	 * 获取 凭证ID
	 * @return 返回   凭证ID
	 */
	public Long getVoucherId() {
		return voucherId;
	}
	
	/**
	 * 设置  凭证ID
	 * @param voucherId  凭证ID
	 */
	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
	}
	
	/**
	 * 获取 分录ID
	 * @return 返回   分录ID
	 */
	public Long getEntryId() {
		return entryId;
	}
	
	/**
	 * 设置 分录ID
	 * @param entryId 分录ID
	 */
	public void setEntryId(Long entryId) {
		this.entryId = entryId;
	}
	
	/**
	 * 获取 摘要
	 * @return 返回  摘要
	 */
	public String getExplanation() {
		return explanation;
	}
	
	/**
	 * 设置 摘要
	 * @param explanation 摘要
	 */
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	
	/**
	 * 获取 科目
	 * @return 返回   科目
	 */
	public String getAccountId() {
		return accountId;
	}
	
	/**
	 *  设置 科目
	 * @param accountId  科目
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	/**
	 * 获取 对方科目
	 * @return 返回  对方科目
	 */
	public String getAccountId2() {
		return accountId2;
	}
	
	/**
	 * 设置  对方科目
	 * @param accountId2  对方科目
	 */
	public void setAccountId2(String accountId2) {
		this.accountId2 = accountId2;
	}
	
	/**
	 * 获取 币别
	 * @return  返回  币别
	 */
	public String getCurrencyId() {
		return currencyId;
	}
	
	/**
	 *  设置  币别  
	 * @param currencyId   币别
	 */
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	
	/**
	 * 获取 汇率
	 * @return 返回   汇率
	 */
	public Float getExchangeRate() {
		return exchangeRate;
	}
	
	/**
	 *  设置  汇率
	 * @param exchangeRate  汇率
	 */
	public void setExchangeRate(Float exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	/**
	 * 获取	 余额方向
	 * @return 返回   余额方向
	 */
	public Integer getDc() {
		return dc;
	}
	
	/**
	 *  设置   余额方向
	 * @param dc 余额方向
	 */
	public void setDc(Integer dc) {
		this.dc = dc;
	}
	
	/**
	 * 获取 外币金额
	 * @return 返回  外币金额
	 */
	public Double getAmountFor() {
		return amountFor;
	}
	
	/**
	 *  设置  外币金额
	 * @param amountFor 外币金额
	 */
	public void setAmountFor(Double amountFor) {
		this.amountFor = amountFor;
	}
	
	/**
	 * 获取	本位币金额
	 * @return 返回   本位币金额
	 */
	public Double getAmount() {
		return amount;
	}
	
	/**
	 *  设置  本位币金额
	 * @param amount 本位币金额
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	/**
	 * 获取	结算方式
	 * @return 返回   结算方式
	 */
	public String getSettleTypeId() {
		return settleTypeId;
	}
	
	/**
	 *  设置  结算方式
	 * @param settleTypeId 结算方式
	 */
	public void setSettleTypeId(String settleTypeId) {
		this.settleTypeId = settleTypeId;
	}
	
	/**
	 * 获取	结算方式名称
	 * @return 返回   结算方式名称
	 */
	public String getSettleType() {
		return settleType;
	}
	
	/**
	 *  设置  结算方式名称
	 * @param settleType 结算方式名称
	 */
	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}

	/**
	 * 获取	结算号
	 * @return 返回   结算号
	 */
	public String getSettleNo() {
		return settleNo;
	}
	
	/**
	 *  设置  结算号
	 * @param settleNo 结算号
	 */
	public void setSettleNo(String settleNo) {
		this.settleNo = settleNo;
	}
	
	/**
	 * 获取	核算项目ID
	 * @return 返回   	核算项目ID
	 */
	public Long getDetailId() {
		return detailId;
	}
	
	/**
	 *  设置   	核算项目ID
	 * @param detailId 	核算项目ID
	 */
	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}
	
	/**
	 * 获取 	核算项目对象
	 * @return 返回   	核算项目对象
	 */
	public ItemModel getItem() {
		return item;
	}
	
	/**
	 *  设置   核算项目对象
	 * @param item 	核算项目对象
	 */
	public void setItem(ItemModel item) {
		this.item = item;
	}
	
	
}
