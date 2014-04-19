package com.cmw.entity.fininter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 分录模板
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="分录模板实体",createDate="2013-03-28T00:00:00",author="程明卫")
@Entity
@Table(name="fs_EntryTemp")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class EntryTempEntity extends IdBaseEntity {
	
	
	 @Description(remark="凭证模板ID")
	 @Column(name="voucherId" )
	 private Long voucherId;

	 @Description(remark="摘要说明")
	 @Column(name="summary" ,length=200 )
	 private String summary;

	 @Description(remark="科目ID")
	 @Column(name="accountId" ,length=30,nullable=false )
	 private String accountId;

	 @Description(remark="对方科目ID")
	 @Column(name="accountId2" ,length=30 )
	 private String accountId2;

	 @Description(remark="金额计算公式")
	 @Column(name="formulaId" )
	 private Long formulaId;

	 @Description(remark="余额方向")
	 @Column(name="fdc" )
	 private Byte fdc;

	 @Description(remark="币别")
	 @Column(name="currencyId" ,length=50 )
	 private String currencyId;

	 @Description(remark="结算方式")
	 @Column(name="settleId" ,length=50 )
	 private String settleId;

	 @Description(remark="条件公式ID")
	 @Column(name="conditionId" )
	 private Long conditionId;

	 @Description(remark="是否挂核算项")
	 @Column(name="isItemClass" )
	 private Byte isItemClass;


	public EntryTempEntity() {

	}

	
	/**
	  * 设置凭证模板ID的值
	 * @param 	voucherId	 凭证模板ID
	**/
	public void setVoucherId(Long  voucherId){
		 this.voucherId=voucherId;
 	}

	/**
	  * 获取凭证模板ID的值
	 * @return 返回凭证模板ID的值
	**/
	public Long getVoucherId(){
		 return voucherId;
 	}

	/**
	  * 设置摘要说明的值
	 * @param 	summary	 摘要说明
	**/
	public void setSummary(String  summary){
		 this.summary=summary;
 	}

	/**
	  * 获取摘要说明的值
	 * @return 返回摘要说明的值
	**/
	public String getSummary(){
		 return summary;
 	}

	/**
	  * 设置科目ID的值
	 * @param 	accountId	 科目ID
	**/
	public void setAccountId(String  accountId){
		 this.accountId=accountId;
 	}

	/**
	  * 获取科目ID的值
	 * @return 返回科目ID的值
	**/
	public String getAccountId(){
		 return accountId;
 	}

	/**
	  * 设置对方科目ID的值
	 * @param 	accountId2	 对方科目ID
	**/
	public void setAccountId2(String  accountId2){
		 this.accountId2=accountId2;
 	}

	/**
	  * 获取对方科目ID的值
	 * @return 返回对方科目ID的值
	**/
	public String getAccountId2(){
		 return accountId2;
 	}

	/**
	  * 设置金额计算公式的值
	 * @param 	formulaId	 金额计算公式
	**/
	public void setFormulaId(Long  formulaId){
		 this.formulaId=formulaId;
 	}

	/**
	  * 获取金额计算公式的值
	 * @return 返回金额计算公式的值
	**/
	public Long getFormulaId(){
		 return formulaId;
 	}

	/**
	  * 设置余额方向的值
	 * @param 	fdc	 余额方向
	**/
	public void setFdc(Byte  fdc){
		 this.fdc=fdc;
 	}

	/**
	  * 获取余额方向的值
	 * @return 返回余额方向的值
	**/
	public Byte getFdc(){
		 return fdc;
 	}

	/**
	  * 设置币别的值
	 * @param 	currencyId	 币别
	**/
	public void setCurrencyId(String  currencyId){
		 this.currencyId=currencyId;
 	}

	/**
	  * 获取币别的值
	 * @return 返回币别的值
	**/
	public String getCurrencyId(){
		 return currencyId;
 	}

	/**
	  * 设置结算方式的值
	 * @param 	settleId	 结算方式
	**/
	public void setSettleId(String  settleId){
		 this.settleId=settleId;
 	}

	/**
	  * 获取结算方式的值
	 * @return 返回结算方式的值
	**/
	public String getSettleId(){
		 return settleId;
 	}

	/**
	  * 设置条件公式ID的值
	 * @param 	conditionId	 条件公式ID
	**/
	public void setConditionId(Long  conditionId){
		 this.conditionId=conditionId;
 	}

	/**
	  * 获取条件公式ID的值
	 * @return 返回条件公式ID的值
	**/
	public Long getConditionId(){
		 return conditionId;
 	}

	/**
	  * 设置是否挂核算项的值
	 * @param 	isItemClass	 是否挂核算项
	**/
	public void setIsItemClass(Byte  isItemClass){
		 this.isItemClass=isItemClass;
 	}

	/**
	  * 获取是否挂核算项的值
	 * @return 返回是否挂核算项的值
	**/
	public Byte getIsItemClass(){
		 return isItemClass;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,voucherId,summary,accountId,accountId2,formulaId,fdc,currencyId,settleId,conditionId,isItemClass,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","voucherId","summary","accountId","accountId2","formulaId","fdc","currencyId","settleId","conditionId","isItemClass","remark"};
	}
	
	/**
	 * 是否挂核算项 [1:不挂核算项]
	 */
	public static final byte ISITEMCLASS_1 = 1;
	/**
	 * 是否挂核算项 [2:挂核算项]
	 */
	public static final byte ISITEMCLASS_2 = 2;
}
