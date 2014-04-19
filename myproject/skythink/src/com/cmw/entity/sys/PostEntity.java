package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 职位
 * @author 彭登浩
 * @date 2012-11-10T00:00:00
 */
@Description(remark="职位实体",createDate="2012-11-10T00:00:00",author="彭登浩")
@Entity
@Table(name="ts_post")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class PostEntity extends IdBaseEntity {
	
	
	 @Description(remark="岗位编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;



	 @Description(remark="岗位名称")
	 @Column(name="name" ,nullable=false ,length=100 )
	 private String name;



	 @Description(remark="拼音助记码")
	 @Column(name="mnemonic" ,length=100 )
	 private String mnemonic;



	 @Description(remark="主要职责")
	 @Column(name="mtask" ,length=200 )
	 private String mtask;

	 
	 @Description(remark="是否为财务放款人员")
	 @Column(name="isLoan")
	 private Integer isLoan;



	public PostEntity() {

	}
	
	
	/**
	 * @return the isLoan
	 */
	public Integer getIsLoan() {
		return isLoan;
	}


	/**
	 * @param isLoan the isLoan to set
	 */
	public void setIsLoan(Integer isLoan) {
		this.isLoan = isLoan;
	}


	/**
	  * 设置岗位编号的值
	 * @param 	code	 岗位编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取岗位编号的值
	 * @return 返回岗位编号的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置岗位名称的值
	 * @param 	name	 岗位名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取岗位名称的值
	 * @return 返回岗位名称的值
	**/
	public String getName(){
		 return name;
 	}


	/**
	  * 设置拼音助记码的值
	 * @param 	mnemonic	 拼音助记码
	**/
	public void setMnemonic(String  mnemonic){
		 this.mnemonic=mnemonic;
 	}

	/**
	  * 获取拼音助记码的值
	 * @return 返回拼音助记码的值
	**/
	public String getMnemonic(){
		 return mnemonic;
 	}

	/**
	  * 设置主要职责的值
	 * @param 	mtask	 主要职责
	**/
	public void setMtask(String  mtask){
		 this.mtask=mtask;
 	}

	/**
	  * 获取主要职责的值
	 * @return 返回主要职责的值
	**/
	public String getMtask(){
		 return mtask;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{isLoan,id,code,name,mnemonic,mtask,creator,createTime,remark,isenabled};
	}

	@Override
	public String[] getFields() {
		return new String[]{"isLoan","id","code","name","mnemonic","mtask","creator","createTime#yyyy-MM-dd","remark","isenabled"};
	}

}
