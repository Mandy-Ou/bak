package com.cmw.entity.fininter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 财务系统配置
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="财务系统配置实体",createDate="2013-03-28T00:00:00",author="程明卫")
@Entity
@Table(name="fs_FinSysCfg")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class FinSysCfgEntity extends IdBaseEntity {
	
	 @Description(remark="财务系统编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;
	
	 @Description(remark="财务系统名称")
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;

	 @Description(remark="服务器地址")
	 @Column(name="server" ,nullable=false ,length=80 )
	 private String server;

	 @Description(remark="端口号")
	 @Column(name="port" ,nullable=false )
	 private Integer port;

	 @Description(remark="接口类型")
	 @Column(name="interType" ,nullable=false )
	 private Byte interType;
	 
	 @Description(remark="数据库类型")
	 @Column(name="dbType" ,nullable=false )
	 private Byte dbType=0;

	 @Description(remark="数据库名")
	 @Column(name="dbName" ,length=50 )
	 private String dbName;

	 @Description(remark="登录帐号")
	 @Column(name="loginName" ,length=50 )
	 private String loginName;

	 @Description(remark="登录密码")
	 @Column(name="passWord" ,length=50 )
	 private String passWord;

	 @Description(remark="最大连接数")
	 @Column(name="maxSize" ,nullable=false )
	 private Integer maxSize=1;
	 
	 @Description(remark="数据中心代码")
	 @Column(name="dataCode" ,length=50 )
	 private String dataCode;

	 @Description(remark="国际化语言")
	 @Column(name="language" ,length=50 )
	 private String language;

	 @Description(remark="验证方式")
	 @Column(name="validType" ,length=50 )
	 private String validType;

	 @Description(remark="支持科目组")
	 @Column(name="spAccgroup" ,nullable=false )
	 private Byte spAccgroup = 2;

	 @Description(remark="用户帐号读取")
	 @Column(name="spUserName" ,nullable=false )
	 private Byte spUserName = 2;

	 @Description(remark="用户帐号读取")
	 @Column(name="spCurrency" ,nullable=false )
	 private Byte spCurrency = 2;

	 @Description(remark="结算方式读取")
	 @Column(name="spSettle" ,nullable=false )
	 private Byte spSettle = 2;

	 @Description(remark="凭证字数据读取")
	 @Column(name="spVhgroup" ,nullable=false )
	 private Byte spVhgroup = 2;

	 @Description(remark="核算项目读取")
	 @Column(name="spImclass" ,nullable=false )
	 private Byte spImclass = 2;

	 @Description(remark="客户写入")
	 @Column(name="spCustomer" ,nullable=false )
	 private Byte spCustomer = 2;


	public FinSysCfgEntity() {

	}

	
	/**
	  * 设置财务系统名称的值
	 * @param 	name	 财务系统名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取财务系统名称的值
	 * @return 返回财务系统名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置服务器地址的值
	 * @param 	server	 服务器地址
	**/
	public void setServer(String  server){
		 this.server=server;
 	}

	/**
	  * 获取服务器地址的值
	 * @return 返回服务器地址的值
	**/
	public String getServer(){
		 return server;
 	}

	/**
	  * 设置端口号的值
	 * @param 	port	 端口号
	**/
	public void setPort(Integer  port){
		 this.port=port;
 	}

	/**
	  * 获取端口号的值
	 * @return 返回端口号的值
	**/
	public Integer getPort(){
		 return port;
 	}

	/**
	  * 设置接口类型的值
	 * @param 	interType	 接口类型
	**/
	public void setInterType(Byte  interType){
		 this.interType=interType;
 	}

	/**
	  * 获取接口类型的值
	 * @return 返回接口类型的值
	**/
	public Byte getInterType(){
		 return interType;
 	}

	/**
	  * 设置数据库名的值
	 * @param 	dbName	 数据库名
	**/
	public void setDbName(String  dbName){
		 this.dbName=dbName;
 	}

	/**
	  * 获取数据库名的值
	 * @return 返回数据库名的值
	**/
	public String getDbName(){
		 return dbName;
 	}

	/**
	  * 设置登录帐号的值
	 * @param 	loginName	 登录帐号
	**/
	public void setLoginName(String  loginName){
		 this.loginName=loginName;
 	}

	/**
	  * 获取登录帐号的值
	 * @return 返回登录帐号的值
	**/
	public String getLoginName(){
		 return loginName;
 	}

	/**
	  * 设置登录密码的值
	 * @param 	passWord	 登录密码
	**/
	public void setPassWord(String  passWord){
		 this.passWord=passWord;
 	}

	/**
	  * 获取登录密码的值
	 * @return 返回登录密码的值
	**/
	public String getPassWord(){
		 return passWord;
 	}

	/**
	  * 设置数据中心代码的值
	 * @param 	dataCode	 数据中心代码
	**/
	public void setDataCode(String  dataCode){
		 this.dataCode=dataCode;
 	}

	/**
	  * 获取数据中心代码的值
	 * @return 返回数据中心代码的值
	**/
	public String getDataCode(){
		 return dataCode;
 	}

	/**
	  * 设置国际化语言的值
	 * @param 	language	 国际化语言
	**/
	public void setLanguage(String  language){
		 this.language=language;
 	}

	/**
	  * 获取国际化语言的值
	 * @return 返回国际化语言的值
	**/
	public String getLanguage(){
		 return language;
 	}

	/**
	  * 设置验证方式的值
	 * @param 	validType	 验证方式
	**/
	public void setValidType(String  validType){
		 this.validType=validType;
 	}

	/**
	  * 获取验证方式的值
	 * @return 返回验证方式的值
	**/
	public String getValidType(){
		 return validType;
 	}

	/**
	  * 设置支持科目组的值
	 * @param 	spAccgroup	 支持科目组
	**/
	public void setSpAccgroup(Byte  spAccgroup){
		 this.spAccgroup=spAccgroup;
 	}

	/**
	  * 获取支持科目组的值
	 * @return 返回支持科目组的值
	**/
	public Byte getSpAccgroup(){
		 return spAccgroup;
 	}

	/**
	  * 设置用户帐号读取的值
	 * @param 	spUserName	 用户帐号读取
	**/
	public void setSpUserName(Byte  spUserName){
		 this.spUserName=spUserName;
 	}

	/**
	  * 获取用户帐号读取的值
	 * @return 返回用户帐号读取的值
	**/
	public Byte getSpUserName(){
		 return spUserName;
 	}

	/**
	  * 设置用户帐号读取的值
	 * @param 	spCurrency	 用户帐号读取
	**/
	public void setSpCurrency(Byte  spCurrency){
		 this.spCurrency=spCurrency;
 	}

	/**
	  * 获取用户帐号读取的值
	 * @return 返回用户帐号读取的值
	**/
	public Byte getSpCurrency(){
		 return spCurrency;
 	}

	/**
	  * 设置结算方式读取的值
	 * @param 	spSettle	 结算方式读取
	**/
	public void setSpSettle(Byte  spSettle){
		 this.spSettle=spSettle;
 	}

	/**
	  * 获取结算方式读取的值
	 * @return 返回结算方式读取的值
	**/
	public Byte getSpSettle(){
		 return spSettle;
 	}

	/**
	  * 设置凭证字数据读取的值
	 * @param 	spVhgroup	 凭证字数据读取
	**/
	public void setSpVhgroup(Byte  spVhgroup){
		 this.spVhgroup=spVhgroup;
 	}

	/**
	  * 获取凭证字数据读取的值
	 * @return 返回凭证字数据读取的值
	**/
	public Byte getSpVhgroup(){
		 return spVhgroup;
 	}

	/**
	  * 设置核算项目读取的值
	 * @param 	spImclass	 核算项目读取
	**/
	public void setSpImclass(Byte  spImclass){
		 this.spImclass=spImclass;
 	}

	/**
	  * 获取核算项目读取的值
	 * @return 返回核算项目读取的值
	**/
	public Byte getSpImclass(){
		 return spImclass;
 	}

	/**
	  * 设置客户写入的值
	 * @param 	spCustomer	 客户写入
	**/
	public void setSpCustomer(Byte  spCustomer){
		 this.spCustomer=spCustomer;
 	}

	/**
	  * 获取客户写入的值
	 * @return 返回客户写入的值
	**/
	public Byte getSpCustomer(){
		 return spCustomer;
 	}



	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public Byte getDbType() {
		return dbType;
	}


	public void setDbType(Byte dbType) {
		this.dbType = dbType;
	}


	public Integer getMaxSize() {
		return maxSize;
	}


	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{id,code,name,server,port,interType,dbType,dbName,loginName,passWord,maxSize,dataCode,language,validType,spAccgroup,spUserName,spCurrency,spSettle,spVhgroup,spImclass,spCustomer,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","code","name","server","port","interType","dbType","dbName","loginName","passWord","maxSize","dataCode","language","validType","spAccgroup","spUserName","spCurrency","spSettle","spVhgroup","spImclass","spCustomer","remark"};
	}
	/**
	 * 1:SqlServer
	 */
	public static final int DBTYPE_1 = 1;
	/**
	 * 2:Mysql
	 */
	public static final int DBTYPE_2 = 2;
	/**
	 * 3:Oracle
	 */
	public static final int DBTYPE_3 = 3;
}
