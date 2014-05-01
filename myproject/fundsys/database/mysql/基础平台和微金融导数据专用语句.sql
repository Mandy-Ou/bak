/*--------  pdh_test 基础平台系统初始化数据START  -------*/

/*--------  系统表-------*/
drop table [pdh_test].dbo.ts_System
go
SELECT * INTO [pdh_test].dbo.ts_System FROM [txr_platform_v2.5_test].dbo.ts_System 
go
select * from [pdh_test].dbo.ts_system
go
alter table [pdh_test].dbo.ts_system add primary key (id)

/*--------  卡片菜单表-------*/
drop table [pdh_test].dbo.ts_accordion
go
SELECT * INTO [pdh_test].dbo.ts_accordion FROM [txr_platform_v2.5_test].dbo.ts_accordion
go
select * from [pdh_test].dbo.ts_accordion
go
alter table [pdh_test].dbo.ts_accordion add primary key (id)

/*--------  菜单表-------*/
drop table [pdh_test].dbo.ts_menu
go
SELECT * INTO [pdh_test].dbo.ts_menu FROM [skythink].dbo.ts_menu
go
select * from [pdh_test].dbo.ts_menu
go
alter table [pdh_test].dbo.ts_menu add primary key (menuId)

/*--------  用户表-------*/
drop table [pdh_test].dbo.ts_User
go
SELECT * INTO [pdh_test].dbo.ts_User FROM [skythink].dbo.ts_User where userName ='admin' or userName ='administrator'
go
select * from [pdh_test].dbo.ts_User
go
alter table [pdh_test].dbo.ts_User add primary key (UserId)
/*-------- 基础数据表-------*/
drop table [pdh_test].dbo.ts_Gvlist
go
SELECT * INTO [pdh_test].dbo.ts_Gvlist FROM [txr_platform_v2.5_test].dbo.ts_Gvlist 
go
select * from [pdh_test].dbo.ts_Gvlist
go
alter table [pdh_test].dbo.ts_Gvlist add primary key (id)
/*-------- 资源表-------*/
drop table [pdh_test].dbo.ts_Restype
go
SELECT * INTO [pdh_test].dbo.ts_Restype FROM [txr_platform_v2.5_test].dbo.ts_Restype 
go
select * from [pdh_test].dbo.ts_Restype
go
alter table [pdh_test].dbo.ts_Restype add primary key (id)
/*-------- 桌面模块表-------*/
drop table [pdh_test].dbo.ts_DeskMod
go
SELECT * INTO [pdh_test].dbo.ts_DeskMod FROM [txr_platform_v2.5_test].dbo.ts_DeskMod 
go
select * from [pdh_test].dbo.ts_DeskMod
go
alter table [pdh_test].dbo.ts_DeskMod add primary key (id)

/*-------- 组织架构公司表-------*/
drop table [pdh_test].dbo.ts_Company
go
SELECT * INTO [pdh_test].dbo.ts_Company FROM [txr_platform_v2.5_test].dbo.ts_Company 
go
select * from [pdh_test].dbo.ts_Company
go
alter table [pdh_test].dbo.ts_Company add primary key (id)
/*-------- 组织架构部门表-------*/
drop table [pdh_test].dbo.ts_Department
go
SELECT * INTO [pdh_test].dbo.ts_Department FROM [txr_platform_v2.5_test].dbo.ts_Department 
go
select * from [pdh_test].dbo.ts_Department
go
alter table [pdh_test].dbo.ts_Department add primary key (id)
/*-------- 组织架构职位表-------*/
drop table [pdh_test].dbo.ts_Post
go
SELECT * INTO [pdh_test].dbo.ts_Post FROM [txr_platform_v2.5_test].dbo.ts_Post 
go
select * from [pdh_test].dbo.ts_Post
go
alter table [pdh_test].dbo.ts_Post add primary key (id)
/*--------角色表-------*/
drop table [pdh_test].dbo.ts_Role
go
SELECT * INTO [pdh_test].dbo.ts_Role FROM [txr_platform_v2.5_test].dbo.ts_Role 
go
select * from [pdh_test].dbo.ts_Role
go
alter table [pdh_test].dbo.ts_Role add primary key (id)
/*--------系统参数表-------*/
drop table [pdh_test].dbo.ts_Sysparams
go
SELECT * INTO [pdh_test].dbo.ts_Sysparams FROM [skythink].dbo.ts_Sysparams 
go
select * from [pdh_test].dbo.ts_Sysparams
go
alter table [pdh_test].dbo.ts_Sysparams add primary key (id)
/*--------编号规则表-------*/
drop table [pdh_test].dbo.ts_Busscode
go
SELECT * INTO [pdh_test].dbo.ts_Busscode FROM [txr_platform_v2.5_test].dbo.ts_Busscode 
go
select * from [pdh_test].dbo.ts_Busscode
go
alter table [pdh_test].dbo.ts_Busscode add primary key (id)
/*--------表单DIY表-------*/
drop table [pdh_test].dbo.ts_Formdiy
go
SELECT * INTO [pdh_test].dbo.ts_Formdiy FROM [txr_platform_v2.5_test].dbo.ts_Formdiy 
go
select * from [pdh_test].dbo.ts_Formdiy
go
alter table [pdh_test].dbo.ts_Formdiy add primary key (id)
/*-------字段属性表-------*/
drop table [pdh_test].dbo.ts_FieldProp
go
SELECT * INTO [pdh_test].dbo.ts_FieldProp FROM [txr_platform_v2.5_test].dbo.ts_FieldProp 
go
select * from [pdh_test].dbo.ts_FieldProp
go
alter table [pdh_test].dbo.ts_FieldProp add primary key (id)
/*-------自定义字段表-------*/
drop table [pdh_test].dbo.ts_FieldCustom
go
SELECT * INTO [pdh_test].dbo.ts_FieldCustom FROM [txr_platform_v2.5_test].dbo.ts_FieldCustom 
go
select * from [pdh_test].dbo.ts_FieldCustom
go
alter table [pdh_test].dbo.ts_FieldCustom add primary key (id)
/*-------资料模板表-------*/
drop table [pdh_test].dbo.ts_MatTemp
go
SELECT * INTO [pdh_test].dbo.ts_MatTemp FROM [txr_platform_v2.5_test].dbo.ts_MatTemp 
go
select * from [pdh_test].dbo.ts_MatTemp
go
alter table [pdh_test].dbo.ts_MatTemp add primary key (id)
/*-------资料标题表-------*/
drop table [pdh_test].dbo.ts_MatSubjec
go
SELECT * INTO [pdh_test].dbo.ts_MatSubjec FROM [txr_platform_v2.5_test].dbo.ts_MatSubjec 
go
select * from [pdh_test].dbo.ts_MatSubjec
go
alter table [pdh_test].dbo.ts_MatSubjec add primary key (id)
/*-------资料项表-------*/
drop table [pdh_test].dbo.ts_MatParams
go
SELECT * INTO [pdh_test].dbo.ts_MatParams FROM [txr_platform_v2.5_test].dbo.ts_MatParams 
go
select * from [pdh_test].dbo.ts_MatParams
go
alter table [pdh_test].dbo.ts_MatParams add primary key (id)
/*-------资料确认结果表-------*/
drop table [pdh_test].dbo.ts_MatResult
go
SELECT * INTO [pdh_test].dbo.ts_MatResult FROM [txr_platform_v2.5_test].dbo.ts_MatResult 
go
select * from [pdh_test].dbo.ts_MatResult
go
alter table [pdh_test].dbo.ts_MatResult add primary key (id)
/*-------报表模板表-------*/
drop table [pdh_test].dbo.ts_ReportTemp
go
SELECT * INTO [pdh_test].dbo.ts_ReportTemp FROM [txr_platform_v2.5_test].dbo.ts_ReportTemp 
go
select * from [pdh_test].dbo.ts_ReportTemp
go
alter table [pdh_test].dbo.ts_ReportTemp add primary key (id)
/*-------角色菜单权限表-------*/
drop table [pdh_test].dbo.ts_Right
go
SELECT * INTO [pdh_test].dbo.ts_Right FROM [txr_platform_v2.5_test].dbo.ts_Right 
go
select * from [pdh_test].dbo.ts_Right
go
alter table [pdh_test].dbo.ts_Right add primary key (id)
/*-------系统权限表-------*/
drop table [pdh_test].dbo.ts_SysRight
go
SELECT * INTO [pdh_test].dbo.ts_SysRight FROM [txr_platform_v2.5_test].dbo.ts_SysRight 
go
select * from [pdh_test].dbo.ts_SysRight
go
alter table [pdh_test].dbo.ts_SysRight add primary key (id)
/*-------数据访问权限表-------*/
drop table [pdh_test].dbo.ts_DataAccess
go
SELECT * INTO [pdh_test].dbo.ts_DataAccess FROM [txr_platform_v2.5_test].dbo.ts_DataAccess 
go
select * from [pdh_test].dbo.ts_DataAccess
go
alter table [pdh_test].dbo.ts_DataAccess add primary key (id)
/*-------业务品种表-------*/
drop table [pdh_test].dbo.ts_Variety
go
SELECT * INTO [pdh_test].dbo.ts_Variety FROM [txr_platform_v2.5_test].dbo.ts_Variety 
go
select * from [pdh_test].dbo.ts_Variety
go
alter table [pdh_test].dbo.ts_Variety add primary key (id)
/*-------公司账户表-------*/
drop table [pdh_test].dbo.ts_Account
go
SELECT * INTO [pdh_test].dbo.ts_Account FROM [skythink].dbo.ts_Account 
go
select * from [pdh_test].dbo.ts_Account
go
alter table [pdh_test].dbo.ts_Account add primary key (id)
/*-------公式表-------*/
drop table [pdh_test].dbo.ts_Formula
go
SELECT * INTO [pdh_test].dbo.ts_Formula FROM [skythink].dbo.ts_Formula 
go
select * from [pdh_test].dbo.ts_Formula
go
alter table [pdh_test].dbo.ts_Formula add primary key (id)


/*-------- pdh_test 基础平台系统初始化数据END  -------*/


/*-------- pdh_test金融贷款初始化数据START  -------*/
/*-------fc__利率表-------*/
drop table [pdh_test].dbo.fc_Rate
go
SELECT * INTO [pdh_test].dbo.fc_Rate FROM [txr_platform_v2.5_test].dbo.fc_Rate 
go
select * from [pdh_test].dbo.fc_Rate
go
alter table [pdh_test].dbo.fc_Rate add primary key (id)
/*-------fc__扣款优先级-------*/
drop table [pdh_test].dbo.fc_Order
go
SELECT * INTO [pdh_test].dbo.fc_Order FROM [txr_platform_v2.5_test].dbo.fc_Order 
go
select * from [pdh_test].dbo.fc_Order
go
alter table [pdh_test].dbo.fc_Order add primary key (id)
/*-------fc__风险等级表-------*/
drop table [pdh_test].dbo.fc_RiskLevel
go
SELECT * INTO [pdh_test].dbo.fc_RiskLevel FROM [txr_platform_v2.5_test].dbo.fc_RiskLevel 
go
select * from [pdh_test].dbo.fc_RiskLevel
go
alter table [pdh_test].dbo.fc_RiskLevel add primary key (id)
/*-------fc__还款方式-------*/
drop table [pdh_test].dbo.fc_PayType
go
SELECT * INTO [pdh_test].dbo.fc_PayType FROM [txr_platform_v2.5_test].dbo.fc_PayType 
go
select * from [pdh_test].dbo.fc_PayType
go
alter table [pdh_test].dbo.fc_PayType add primary key (id)
/*-------fc__最低金额配置-------*/

drop table [pdh_test].dbo.fc_MinAmount
go
SELECT * INTO [pdh_test].dbo.fc_MinAmount FROM [txr_platform_v2.5_test].dbo.fc_MinAmount 
go
select * from [pdh_test].dbo.fc_MinAmount
go
alter table [pdh_test].dbo.fc_MinAmount add primary key (id)
/*-------- pdh_test金融贷款初始化数据END  -------*/

/*-------- pdh_test财务系统数据START  -------*/
/*-------fs__财务系统配置-------*/
drop table [pdh_test].dbo.fs_FinSysCfg
go
SELECT * INTO [pdh_test].dbo.fs_FinSysCfg FROM [skythink].dbo.fs_FinSysCfg 
go
select * from [pdh_test].dbo.fs_FinSysCfg
go
alter table [pdh_test].dbo.fs_FinSysCfg add primary key (id)
/*-------fs__业务财务映射-------*/
drop table [pdh_test].dbo.fs_BussFinCfg
go
SELECT * INTO [pdh_test].dbo.fs_BussFinCfg FROM [skythink].dbo.fs_BussFinCfg 
go
select * from [pdh_test].dbo.fs_BussFinCfg
go
alter table [pdh_test].dbo.fs_BussFinCfg add primary key (id)
UPDATE  [pdh_test].dbo.fs_BussFinCfg SET  creator=1
/*-------fs__币别-------*/
drop table [pdh_test].dbo.fs_Currency
go
SELECT * INTO [pdh_test].dbo.fs_Currency FROM [skythink].dbo.fs_Currency 
go
select * from [pdh_test].dbo.fs_Currency
go
alter table [pdh_test].dbo.fs_Currency add primary key (id)
/*-------fs__核算项类别-------*/
drop table [pdh_test].dbo.fs_ItemClass
go
SELECT * INTO [pdh_test].dbo.fs_ItemClass FROM [skythink].dbo.fs_ItemClass 
go
select * from [pdh_test].dbo.fs_ItemClass
go
alter table [pdh_test].dbo.fs_ItemClass add primary key (id)
/*-------fs__科目组-------*/
drop table [pdh_test].dbo.fs_AcctGroup
go
SELECT * INTO [pdh_test].dbo.fs_AcctGroup FROM [skythink].dbo.fs_AcctGroup 
go
select * from [pdh_test].dbo.fs_AcctGroup
go
alter table [pdh_test].dbo.fs_AcctGroup add primary key (id)
/*-------fs__科目-------*/
drop table [pdh_test].dbo.fs_Subject
go
SELECT * INTO [pdh_test].dbo.fs_Subject FROM [skythink].dbo.fs_Subject 
go
select * from [pdh_test].dbo.fs_Subject
go
alter table [pdh_test].dbo.fs_Subject add primary key (id)
/*-------fs__结算方式-------*/
drop table [pdh_test].dbo.fs_Settle
go
SELECT * INTO [pdh_test].dbo.fs_Settle FROM [skythink].dbo.fs_Settle 
go
select * from [pdh_test].dbo.fs_Settle
go
alter table [pdh_test].dbo.fs_Settle add primary key (id)
/*-------fs__银行账号-------*/
drop table [pdh_test].dbo.fs_BankAccount
go
SELECT * INTO [pdh_test].dbo.fs_BankAccount FROM [skythink].dbo.fs_BankAccount 
go
select * from [pdh_test].dbo.fs_BankAccount
go
alter table [pdh_test].dbo.fs_BankAccount add primary key (id)
/*-------fs__凭证字-------*/ 
drop table [pdh_test].dbo.fs_VoucherGroup
go
SELECT * INTO [pdh_test].dbo.fs_VoucherGroup FROM [skythink].dbo.fs_VoucherGroup 
go
select * from [pdh_test].dbo.fs_VoucherGroup
go
alter table [pdh_test].dbo.fs_VoucherGroup add primary key (id)
/*-------fs__凭证模板-------*/ 
drop table [pdh_test].dbo.fs_VoucherTemp
go
SELECT * INTO [pdh_test].dbo.fs_VoucherTemp FROM [skythink].dbo.fs_VoucherTemp 
go
select * from [pdh_test].dbo.fs_VoucherTemp
go
alter table [pdh_test].dbo.fs_VoucherTemp add primary key (id)
/*-------fs__分录模板-------*/ 
drop table [pdh_test].dbo.fs_EntryTemp
go
SELECT * INTO [pdh_test].dbo.fs_EntryTemp FROM [skythink].dbo.fs_EntryTemp 
go
select * from [pdh_test].dbo.fs_EntryTemp
go
alter table [pdh_test].dbo.fs_EntryTemp add primary key (id)
/*-------fs__核算项-------*/
drop table [pdh_test].dbo.fs_ItemTemp
go
SELECT * INTO [pdh_test].dbo.fs_ItemTemp FROM [skythink].dbo.fs_ItemTemp 
go
select * from [pdh_test].dbo.fs_ItemTemp
go
alter table [pdh_test].dbo.fs_ItemTemp add primary key (id)
/*-------fs__自定义业务对象-------*/
drop table [pdh_test].dbo.fs_FinBussObject
go
SELECT * INTO [pdh_test].dbo.fs_FinBussObject FROM [skythink].dbo.fs_FinBussObject 
go
select * from [pdh_test].dbo.fs_FinBussObject
go
alter table [pdh_test].dbo.fs_FinBussObject add primary key (id)
/*-------fs__财务自定义字段-------*/
drop table [pdh_test].dbo.fs_FinCustField 
go
SELECT * INTO [pdh_test].dbo.fs_FinCustField  FROM [skythink].dbo.fs_FinCustField  
go
select * from [pdh_test].dbo.fs_FinCustField 
go
alter table [pdh_test].dbo.fs_FinCustField  add primary key (id)
/*-------fs__用户帐号映射-------*/
drop table [pdh_test].dbo.fs_UserMapping 
go
SELECT * INTO [pdh_test].dbo.fs_UserMapping  FROM [skythink].dbo.fs_UserMapping  
go
select * from [pdh_test].dbo.fs_UserMapping 
go
alter table [pdh_test].dbo.fs_UserMapping  add primary key (id)
/*-------fs__凭证日志-------*/
drop table [pdh_test].dbo.fs_VoucherOplog  
go
SELECT * INTO [pdh_test].dbo.fs_VoucherOplog   FROM [skythink].dbo.fs_VoucherOplog   
go
select * from [pdh_test].dbo.fs_VoucherOplog  
go
alter table [pdh_test].dbo.fs_VoucherOplog   add primary key (id)

/*-------fs__实收金额日志-------*/
drop table [pdh_test].dbo.fs_AmountLog  
go
SELECT * INTO [pdh_test].dbo.fs_AmountLog   FROM [skythink].dbo.fs_AmountLog   
go
select * from [pdh_test].dbo.fs_AmountLog  
go
alter table [pdh_test].dbo.fs_AmountLog   add primary key (id)

/*-------- pdh_test财务系统数据END  -------*/

/***************************************************
Activiti 5.10 在 Sql Server 2005 中调用 startProcessInstanceById 报不能插入重复值
**************************************************/
DROP INDEX ACT_RU_EXECUTION.ACT_UNIQ_RU_BUS_KEY
DROP INDEX ACT_HI_PROCINST.ACT_UNIQ_HI_BUS_KEY
   create index ACT_UNIQ_RU_BUS_KEY on ACT_RU_EXECUTION (PROC_DEF_ID_, BUSINESS_KEY_); 
  create index ACT_UNIQ_HI_BUS_KEY on ACT_HI_PROCINST (PROC_DEF_ID_, BUSINESS_KEY_);
  
  批量删除SQL Server中拥有指定前缀或后缀的表
sql serrver 方法：
 declare @name varchar(20) 
while(exists(select * from sysobjects where name like 'crm_%')) 
begin 
select @name=name from sysobjects where name like 'crm_%' 
exec ('drop table '+@name) 
end 
