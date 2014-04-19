package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.BaseEntity;


/**
 * 用户
 * @author chengmingwei
 * @date 2011-09-24T00:00:00
 */
@Description(remark="用户实体",createDate="2011-09-24T00:00:00",author="chengmingwei")
@Entity
@Table(name="ts_User")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class UserEntity extends BaseEntity {
	
	 //用户ID
	 @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	 private Long userId;
	 
	 //用户编号
	 @Column(name="code" ,length=20)
	 private String code;

	 //账号
	 @Column(name="userName" ,length=30 )
	 private String userName;



	 //密码
	 @Column(name="passWord" ,length=30 )
	 private String passWord;



	 //直属领导
	 @Column(name="leader" )
	 private Long leader;



	 //岗位ID
	 @Column(name="postId" )
	 private Long postId;



	 //所属部门ID
	 @Column(name="indeptId" )
	 private Long indeptId;



	 //员工ID
	 @Column(name="inempId" )
	 private Long inempId;

	 //公司ID
	 @Column(name="incompId" )
	 private Long incompId;
	 
	 //姓名
	 @Column(name="empName" ,length=30 )
	 private String empName;

	 //性别
	 @Column(name="sex" ,nullable=false )
	 private Integer sex;

	 //拼音助记码
	 @Column(name="mnemonic" ,length=100 )
	 private String mnemonic;



	 //手机
	 @Column(name="phone" ,length=20 )
	 private String phone;



	 //联系电话
	 @Column(name="tel" ,length=20 )
	 private String tel;



	 //邮箱
	 @Column(name="email" ,length=50 )
	 private String email;



	 //数据访问级别
	 @Column(name="dataLevel" )
	 private Byte dataLevel;



	 //数据过滤ID列表
	 @Column(name="accessIds" ,length=255 )
	 private String accessIds;

	 //数据过滤名称列表
	 @Column(name="accessNames" ,length=320 )
	 private String accessNames;

	 //首次密码提示
	 @Column(name="pwdtip" )
	 private Integer pwdtip;



	 //启用密码过期
	 @Column(name="pwdfail" )
	 private Integer pwdfail;



	 //过期周期
	 @Column(name="pwdcycle" )
	 private String pwdcycle="30";



	 //登录限制
	 @Column(name="loglimit" )
	 private Integer loglimit=0;



	 //限制ip段
	 @Column(name="iplimit" ,length=100 )
	 private String iplimit;

	 //是否系统用户
	 @Column(name="isSystem" )
	 private Integer isSystem=0;
	 
	 //国际化资源 
	 @Column(name="i18n",length=10)
	 private String i18n;

	public UserEntity() {

	}

	
	/**
	  * 设置用户ID的值
	 * @param 	userId	 用户ID
	**/
	public void setUserId(Long  userId){
		 this.userId=userId;
 	}

	/**
	  * 获取用户ID的值
	 * @return 返回用户ID的值
	**/
	public Long getUserId(){
		 return userId;
 	}

	
	
	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	/**
	  * 设置账号的值
	 * @param 	userName	 账号
	**/
	public void setUserName(String  userName){
		 this.userName=userName;
 	}

	/**
	  * 获取账号的值
	 * @return 返回账号的值
	**/
	public String getUserName(){
		 return userName;
 	}

	/**
	  * 设置密码的值
	 * @param 	passWord	 密码
	**/
	public void setPassWord(String  passWord){
		 this.passWord=passWord;
 	}

	/**
	  * 获取密码的值
	 * @return 返回密码的值
	**/
	public String getPassWord(){
		 return passWord;
 	}

	/**
	  * 设置直属领导的值
	 * @param 	leader	 直属领导
	**/
	public void setLeader(Long  leader){
		 this.leader=leader;
 	}

	/**
	  * 获取直属领导的值
	 * @return 返回直属领导的值
	**/
	public Long getLeader(){
		 return leader;
 	}

	/**
	  * 设置岗位ID的值
	 * @param 	postId	 岗位ID
	**/
	public void setPostId(Long  postId){
		 this.postId=postId;
 	}

	/**
	  * 获取岗位ID的值
	 * @return 返回岗位ID的值
	**/
	public Long getPostId(){
		 return postId;
 	}


	/**
	  * 获取部门ID的值
	 * @return 返回部门ID的值
	**/
	public Long getIndeptId() {
		return indeptId;
	}

	/**
	  * 设置部门ID的值
	 * @param 	indeptId	 部门ID
	**/
	public void setIndeptId(Long indeptId) {
		this.indeptId = indeptId;
	}

	/**
	  * 获取公司ID的值
	 * @return 返回公司ID的值
	**/
	public Long getIncompId() {
		return incompId;
	}


	public void setIncompId(Long incompId) {
		this.incompId = incompId;
	}


	/**
	  * 获取员工ID的值
	 * @return 返回员工ID的值
	**/
	public Long getInempId() {
		return inempId;
	}

	/**
	  * 设置员工ID的值
	 * @param 	inempId	 员工ID
	**/
	public void setInempId(Long inempId) {
		this.inempId = inempId;
	}


	/**
	  * 设置姓名的值
	 * @param 	empName	 姓名
	**/
	public void setEmpName(String  empName){
		 this.empName=empName;
 	}

	/**
	  * 获取姓名的值
	 * @return 返回姓名的值
	**/
	public String getEmpName(){
		 return empName;
 	}
	/**
	 * 获取性别值
	 * @return
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * 设置性别值
	 * @param sex
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
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
	  * 设置手机的值
	 * @param 	phone	 手机
	**/
	public void setPhone(String  phone){
		 this.phone=phone;
 	}

	/**
	  * 获取手机的值
	 * @return 返回手机的值
	**/
	public String getPhone(){
		 return phone;
 	}

	/**
	  * 设置联系电话的值
	 * @param 	tel	 联系电话
	**/
	public void setTel(String  tel){
		 this.tel=tel;
 	}

	/**
	  * 获取联系电话的值
	 * @return 返回联系电话的值
	**/
	public String getTel(){
		 return tel;
 	}

	/**
	  * 设置邮箱的值
	 * @param 	email	 邮箱
	**/
	public void setEmail(String  email){
		 this.email=email;
 	}

	/**
	  * 获取邮箱的值
	 * @return 返回邮箱的值
	**/
	public String getEmail(){
		 return email;
 	}

	/**
	  * 设置数据访问级别的值
	 * @param 	level	 数据访问级别
	**/
	public void setDataLevel(Byte  dataLevel){
		 this.dataLevel=dataLevel;
 	}

	/**
	  * 获取数据访问级别的值
	 * @return 返回数据访问级别的值
	**/
	public Byte getDataLevel(){
		 return dataLevel;
 	}

	/**
	  * 设置数据过滤ID列表的值
	 * @param 	accessIds	 数据过滤ID列表
	**/
	public void setAccessIds(String  accessIds){
		 this.accessIds=accessIds;
 	}

	/**
	  * 获取数据过滤ID列表的值
	 * @return 返回数据过滤ID列表的值
	**/
	public String getAccessIds(){
		 return accessIds;
 	}
	
	
	/**
	  * 获取数据过滤名称列表的值
	 * @return 返回数据过滤名称列表的值
	**/
	public String getAccessNames() {
		return accessNames;
	}

	/**
	  * 设置数据过滤名称列表的值
	 * @param 	accessNames	 数据过滤名称列表
	**/
	public void setAccessNames(String accessNames) {
		this.accessNames = accessNames;
	}


	/**
	  * 设置首次密码提示的值
	 * @param 	pwdtip	 首次密码提示
	**/
	public void setPwdtip(Integer  pwdtip){
		 this.pwdtip=pwdtip;
 	}

	/**
	  * 获取首次密码提示的值
	 * @return 返回首次密码提示的值
	**/
	public Integer getPwdtip(){
		 return pwdtip;
 	}

	/**
	  * 设置启用密码过期的值
	 * @param 	pwdfail	 启用密码过期
	**/
	public void setPwdfail(Integer  pwdfail){
		 this.pwdfail=pwdfail;
 	}

	/**
	  * 获取启用密码过期的值
	 * @return 返回启用密码过期的值
	**/
	public Integer getPwdfail(){
		 return pwdfail;
 	}

	/**
	  * 设置过期周期的值
	 * @param 	pwdcycle	 过期周期
	**/
	public void setPwdcycle(String  pwdcycle){
		 this.pwdcycle=pwdcycle;
 	}

	/**
	  * 获取过期周期的值
	 * @return 返回过期周期的值
	**/
	public String getPwdcycle(){
		 return pwdcycle;
 	}

	/**
	  * 设置登录限制的值
	 * @param 	loglimit	 登录限制
	**/
	public void setLoglimit(Integer  loglimit){
		 this.loglimit=loglimit;
 	}

	/**
	  * 获取登录限制的值
	 * @return 返回登录限制的值
	**/
	public Integer getLoglimit(){
		 return loglimit;
 	}

	/**
	  * 设置限制ip段的值
	 * @param 	iplimit	 限制ip段
	**/
	public void setIplimit(String  iplimit){
		 this.iplimit=iplimit;
 	}

	/**
	  * 获取限制ip段的值
	 * @return 返回限制ip段的值
	**/
	public String getIplimit(){
		 return iplimit;
 	}

	/**
	  * 获取是否为系统有户
	 * @return 返回是否为系统有户
	**/
	public Integer getIsSystem() {
		return isSystem;
	}

	/**
	  * 设置是否为系统有户
	  * @param 	isSystem	 是否为系统有户
	**/
	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}

	
	/**
	  * 获取用户当前使用的国际化环境
	 * @return 返回用户当前使用的国际化环境
	**/
	public String getI18n() {
		return i18n;
	}

	/**
	  * 设置用户当前使用的国际化环境
	  * @param 	i18n	 国际化环境参数
	**/
	public void setI18n(String i18n) {
		this.i18n = i18n;
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{userId,userName,passWord,leader,postId,
				indeptId,incompId,inempId,empName,sex,mnemonic,phone,
				tel,email,dataLevel,accessIds,pwdtip,
				pwdfail,pwdcycle,loglimit,iplimit,isenabled,i18n};
	}

	@Override
	public String[] getFields() {
		return new String[]{"userId","userName","passWord","leader","postId","indeptId","incompId","inempId","empName","sex","mnemonic","phone","tel","email","dataLevel","accessIds","pwdtip","pwdfail","pwdcycle","loglimit","iplimit","isenabled","i18n"};
	}
	/**
	 *  国际化环境参数  [zh_CN : 中文]
	 */
	public static final String I18N_ZH_CN = "zh_CN";
	/**
	 *  国际化环境参数  [en_US : 英文]
	 */
	public static final String I18N_EN_US = "en_US";
	
	/**
	 *  是否系统用户  [ 0 : 非系统用户] 
	 */
	public static final int ISSYSTEM_0 = 0;
	/**
	 *  是否系统用户  [ 1 : 系统用户] 
	 */
	public static final int ISSYSTEM_1 = 1;
}
