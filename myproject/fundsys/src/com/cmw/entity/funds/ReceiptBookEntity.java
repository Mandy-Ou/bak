package com.cmw.entity.funds;


import java.math.BigDecimal;
import java.util.Date;

import javassist.expr.NewArray;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 汇票转让承诺书表
 * @author 郑符明
 * @date 2014-02-20T00:00:00
 */
@Description(remark="汇票转让承诺书表实体",createDate="2014-02-20T00:00:00",author="郑符明")
@Entity
@Table(name="fu_ReceiptBook")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class ReceiptBookEntity extends IdBaseEntity {
	
	
	 @Description(remark="编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;

	 @Description(remark="汇票总张数")
	 @Column(name="tcount" ,nullable=false )
	 private String tcount = "0";

	 @Description(remark="总金额")
	 @Column(name="tamount" ,nullable=false )
	 private BigDecimal tamount = new BigDecimal(0) ;

	 @Description(remark="收款人姓名")
	 @Column(name="name" ,nullable=false ,length=80 )
	 private String name;

	 @Description(remark="收款人身份证号")
	 @Column(name="cardNum" ,nullable=false ,length=50 )
	 private String cardNum;

	 @Description(remark="收款人联系电话")
	 @Column(name="tel" ,nullable=false ,length=20 )
	 private String tel;

	 @Description(remark="收款人账户名")
	 @Column(name="rtacname" ,nullable=false ,length=50 )
	 private String rtacname;

	 @Description(remark="收款人账号")
	 @Column(name="rtaccount" ,nullable=false ,length=30 )
	 private String rtaccount;

	 @Description(remark="收款人开户行")
	 @Column(name="rtbank" ,nullable=false ,length=80 )
	 private String rtbank;

	 @Description(remark="子业务流程ID")
	 @Column(name="breed" ,nullable=false )
	 private Long breed;

	 @Description(remark="流程实例ID")
	 @Column(name="procId" ,length=50 )
	 private String procId;

	 @Description(remark="转让方名称")
	 @Column(name="tman" ,length=50 )
	 private String tman;

	 @Description(remark="经办人")
	 @Column(name="mman" ,length=50 )
	 private String mman;

	 @Description(remark="转让日期")
	 @Column(name="tdate" )
	 private Date tdate;

	 @Description(remark="状态")
	 @Column(name="status" ,nullable=false )
	 private Integer status;


	public ReceiptBookEntity() {

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
	  * 设置汇票总张数的值
	 * @param 	tcount	 汇票总张数
	**/
	public void setTcount(String  tcount){
		 this.tcount=tcount;
 	}

	/**
	  * 获取汇票总张数的值
	 * @return 返回汇票总张数的值
	**/
	public String getTcount(){
		 return tcount;
 	}

	/**
	  * 设置总金额的值
	 * @param 	tamount	 总金额
	**/
	public void setTamount(BigDecimal  tamount){
		 this.tamount=tamount;
 	}

	/**
	  * 获取总金额的值
	 * @return 返回总金额的值
	**/
	public BigDecimal getTamount(){
		 return tamount;
 	}

	/**
	  * 设置收款人姓名的值
	 * @param 	name	 收款人姓名
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取收款人姓名的值
	 * @return 返回收款人姓名的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置收款人身份证号的值
	 * @param 	cardNum	 收款人身份证号
	**/
	public void setCardNum(String  cardNum){
		 this.cardNum=cardNum;
 	}

	/**
	  * 获取收款人身份证号的值
	 * @return 返回收款人身份证号的值
	**/
	public String getCardNum(){
		 return cardNum;
 	}

	/**
	  * 设置收款人联系电话的值
	 * @param 	tel	 收款人联系电话
	**/
	public void setTel(String  tel){
		 this.tel=tel;
 	}

	/**
	  * 获取收款人联系电话的值
	 * @return 返回收款人联系电话的值
	**/
	public String getTel(){
		 return tel;
 	}

	/**
	  * 设置收款人账户名的值
	 * @param 	rtacname	 收款人账户名
	**/
	public void setRtacname(String  rtacname){
		 this.rtacname=rtacname;
 	}

	/**
	  * 获取收款人账户名的值
	 * @return 返回收款人账户名的值
	**/
	public String getRtacname(){
		 return rtacname;
 	}

	/**
	  * 设置收款人账号的值
	 * @param 	rtaccount	 收款人账号
	**/
	public void setRtaccount(String  rtaccount){
		 this.rtaccount=rtaccount;
 	}

	/**
	  * 获取收款人账号的值
	 * @return 返回收款人账号的值
	**/
	public String getRtaccount(){
		 return rtaccount;
 	}

	/**
	  * 设置收款人开户行的值
	 * @param 	rtbank	 收款人开户行
	**/
	public void setRtbank(String  rtbank){
		 this.rtbank=rtbank;
 	}

	/**
	  * 获取收款人开户行的值
	 * @return 返回收款人开户行的值
	**/
	public String getRtbank(){
		 return rtbank;
 	}

	/**
	  * 设置子业务流程ID的值
	 * @param 	breed	 子业务流程ID
	**/
	public void setBreed(Long  breed){
		 this.breed=breed;
 	}

	/**
	  * 获取子业务流程ID的值
	 * @return 返回子业务流程ID的值
	**/
	public Long getBreed(){
		 return breed;
 	}

	/**
	  * 设置流程实例ID的值
	 * @param 	procId	 流程实例ID
	**/
	public void setProcId(String  procId){
		 this.procId=procId;
 	}

	/**
	  * 获取流程实例ID的值
	 * @return 返回流程实例ID的值
	**/
	public String getProcId(){
		 return procId;
 	}

	/**
	  * 设置转让方名称的值
	 * @param 	tman	 转让方名称
	**/
	public void setTman(String  tman){
		 this.tman=tman;
 	}

	/**
	  * 获取转让方名称的值
	 * @return 返回转让方名称的值
	**/
	public String getTman(){
		 return tman;
 	}

	/**
	  * 设置经办人的值
	 * @param 	mman	 经办人
	**/
	public void setMman(String  mman){
		 this.mman=mman;
 	}

	/**
	  * 获取经办人的值
	 * @return 返回经办人的值
	**/
	public String getMman(){
		 return mman;
 	}

	/**
	  * 设置转让日期的值
	 * @param 	tdate	 转让日期
	**/
	public void setTdate(Date  tdate){
		 this.tdate=tdate;
 	}

	/**
	  * 获取转让日期的值
	 * @return 返回转让日期的值
	**/
	public Date getTdate(){
		 return tdate;
 	}

	/**
	  * 设置状态的值
	 * @param 	status	 状态
	**/
	public void setStatus(Integer  status){
		 this.status=status;
 	}

	/**
	  * 获取状态的值
	 * @return 返回状态的值
	**/
	public Integer getStatus(){
		 return status;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{code,tcount,tamount,name,cardNum,tel,rtacname,rtaccount,rtbank,breed,procId,tman,mman,tdate,status};
	}

	@Override
	public String[] getFields() {
		return new String[]{"code","tcount","tamount","name","cardNum","tel","rtacname","rtaccount","rtbank","breed","procId","tman","mman","tdate","status"};
	}

}
