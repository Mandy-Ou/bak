package com.cmw.constant;
/**
 * 业务状态常量
 * @author chengmingwei
 *
 */
public class BussStateConstant {
	/**========================== 系统基础平台模块 常量定义 START CODE  ============================**/
	
	/**
	 * 桌面模块 是否默认 [1:默认]
	 */
	public static int DESKMOD_ISDEFAULT_1 = 1;
	/**
	 * 桌面模块 是否默认[2:非默认]
	 */
	public static int DESKMOD_ISDEFAULT_2 = 2;
	/*---------------- 业务品种表 (ts_Variety) START CODE  --------------------*/
	/**
	 *  业务性质  bussType [0:无限制]
	 */
	public static int VARIETY_BUSSTYPE_0 = 0;
	/**
	 *  业务性质  bussType [1:个人客户]
	 */
	public static int VARIETY_BUSSTYPE_1 = 1;
	/**
	 *  业务性质  bussType [2:企业客户]
	 */
	public static int VARIETY_BUSSTYPE_2 = 2;
	
	
	/**
	 *  是否授信品种  isCredit [0:否]
	 */
	public static int VARIETY_ISCREDIT_0 = 0;
	/**
	 *  是否授信品种  isCredit [1:是]
	 */
	public static int VARIETY_ISCREDIT_1 = 1;
	
	
	/**
	 *  适用机构  useorg [0:所有公司]
	 */
	public static int VARIETY_USEORG_0 = 0;
	/**
	 *  适用机构  useorg [1:指定公司]
	 */
	public static int VARIETY_USEORG_1 = 1;
	
	/**
	 *  限制贷款金额  isLoan [0:不限制]
	 */
	public static int VARIETY_ISLOAN_0 = 0;
	/**
	 *  限制贷款金额  isLoan [1:限制]
	 */
	public static int VARIETY_ISLOAN_1 = 1;
	
	/**
	 *  限制贷款期限 isLimit [0:不限制]
	 */
	public static int VARIETY_ISLIMIT_0 = 0;
	/**
	  * 限制贷款期限   isLimit [1:限制]
	 */
	public static int VARIETY_ISLIMIT_1 = 1;
	/*---------------- 业务品种表 (ts_Variety) END CODE  --------------------*/
	
	
	/*---------------- 流程节点配置 (ts_NodeCfg) START CODE  --------------------*/
	/**
	  *  流转方式  transWay [1:人工流转]
	 */
	public static final int NODECFG_TRANSWAY_1 = 1;
	/**
	  *  流转方式  transWay [2:自动流转]
	 */
	public static final int NODECFG_TRANSWAY_2 = 2;
	
	
	/**
	  *  并行类型  eventType [1:非并行]
	 */
	public static final int NODECFG_EVENTTYPE_1 = 1;
	/**
	  *  并行类型  eventType [2:分发]
	 */
	public static final int NODECFG_EVENTTYPE_2 = 2;
	/**
	  *  并行类型  eventType [3:汇总]
	 */
	public static final int NODECFG_EVENTTYPE_3 = 3;
	
	/**
	  * 是否允许补签   sign [0:否]
	 */
	public static final int NODECFG_SIGN_0 = 0;
	/**
	  * 是否允许补签   sign [1:是]
	 */
	public static final int NODECFG_SIGN_1 = 1;
	/*---------------- 流程节点配置 (ts_NodeCfg) END CODE  --------------------*/

	/*---------------- 会签配置 (ts_CountersignCfg) START CODE  --------------------*/
	/**
	  *  会签类型  ctype [1:并行]
	 */
	public static final int COUNTERSIGNCFG_CTYPE_1 = 1;
	/**
	  *  会签类型  ctype [2:串行]
	 */
	public static final int COUNTERSIGNCFG_CTYPE_2 = 2;
	
	/**
	  *  决策方式  pdtype [1:通过]
	 */
	public static final int COUNTERSIGNCFG_PDTYPE_1 = 1;
	/**
	  *  决策方式   pdtype [2:拒绝]
	 */
	public static final int COUNTERSIGNCFG_PDTYPE_2 = 2;
	/**
	  *  决策方式  pdtype [3:弃权]
	 */
	public static final int COUNTERSIGNCFG_PDTYPE_3 = 3;
	
	/**
	  *  投票类型  voteType [1:绝对票数]
	 */
	public static final int COUNTERSIGNCFG_VOTETYPE_1 = 1;
	/**
	  *  投票类型  voteType [2:百分比]
	 */
	public static final int COUNTERSIGNCFG_VOTETYPE_2 = 2;
	
	/**
	  *  比较方式  eqlogic [1:大于]
	 */
	public static final int COUNTERSIGNCFG_EQLOGIC_1 = 1;
	/**
	  *   比较方式 eqlogic [2:大于等于]
	 */
	public static final int COUNTERSIGNCFG_EQLOGIC_2 = 2;
	/**
	  *  比较方式  eqlogic [3:等于]
	 */
	public static final int COUNTERSIGNCFG_EQLOGIC_3 = 3;
	/**
	  *   比较方式 eqlogic [4:小于]
	 */
	public static final int COUNTERSIGNCFG_EQLOGIC_4 = 4;
	/**
	  *   比较方式 eqlogic [5:小于等于]
	 */
	public static final int COUNTERSIGNCFG_EQLOGIC_5 = 5;
	
	/**
	  *   流转方式 transType [1:所有参会人员给出意见后，才往下流转]
	 */
	public static final int COUNTERSIGNCFG_TRANSTYPE_1 = 1;
	/**
	  *  流转方式  transType [2:只要满足投票数量，即可向下流转]
	 */
	public static final int COUNTERSIGNCFG_TRANSTYPE_2 = 2;
	
	/**
	  *  参与者指定方式  acway [1:审批人指定]
	 */
	public static final int COUNTERSIGNCFG_ACWAY_1 = 1;
	/**
	  * 参与者指定方式   acway [2:后台指定]
	 */
	public static final int COUNTERSIGNCFG_ACWAY_2 = 2;
	/*---------------- 会签配置 (ts_CountersignCfg) END CODE  --------------------*/
	
	/*---------------- 流转路径配置 (ts_TransCfg) START CODE  --------------------*/
	/**
	  * 参与者类型   acType [0:不需要参与者]
	 */
	public static final int TRANSCFG_ACTYPE_0 = 0;
	/**
	  * 参与者类型   acType [1:角色]
	 */
	public static final int TRANSCFG_ACTYPE_1 = 1;
	/**
	  * 参与者类型   acType [2:用户]
	 */
	public static final int TRANSCFG_ACTYPE_2 = 2;
	/**
	  * 参与者类型   acType [3:上一环节处理人]
	 */
	public static final int TRANSCFG_ACTYPE_3 = 3;
	/**
	  * 参与者类型   acType [4:上级领导]
	 */
	public static final int TRANSCFG_ACTYPE_4 = 4;
	/**
	  * 参与者类型   acType [5:流程发起人]
	 */
	public static final int TRANSCFG_ACTYPE_5 = 5;
	
	/**
	  * 流转路径类型   tpathType [0:未设置]
	 */
	public static final int TRANSCFG_TPATHTYPE_0 = 0;
	/**
	  * 流转路径类型   tpathType [1:默认流转路径]
	 */
	public static final int TRANSCFG_TPATHTYPE_1 = 1;
	/**
	  * 流转路径类型   tpathType [2:退回路径]
	 */
	public static final int TRANSCFG_TPATHTYPE_2 = 2;
	/**
	  * 流转路径类型   tpathType [3:终止流程路径]
	 */
	public static final int TRANSCFG_TPATHTYPE_3 = 3;
	
	/**
	  * 启用条件限制   stint [1:无条件限制]
	 */
	public static final int TRANSCFG_STINT_1 = 1;
	/**
	  * 启用条件限制   stint [2:按角色限制]
	 */
	public static final int TRANSCFG_STINT_2 = 2;
	/**
	  * 启用条件限制   stint [3:按用户限制]
	 */
	public static final int TRANSCFG_STINT_3 = 3;
	
	/**
	  * 目标任务处理方式   tagWay [0:不需要处理方式]
	 */
	public static final int TRANSCFG_TAGWAY_0 = 0;
	/**
	  * 目标任务处理方式   tagWay [1:目标节点有任务时,不创建新任务]
	 */
	public static final int TRANSCFG_TAGWAY_1 = 1;
	/**
	  * 目标任务处理方式   tagWay [2:目标节点有任务时,等原任务完成再创建新任务]
	 */
	public static final int TRANSCFG_TAGWAY_2 = 2;
	
	/**
	  * 并行令牌类型   tokenType [1:无令牌]
	 */
	public static final int TRANSCFG_TOKENTYPE_1 = 1;
	/**
	  * 并行令牌类型   tokenType [2:开始]
	 */
	public static final int TRANSCFG_TOKENTYPE_2 = 2;
	/**
	  * 并行令牌类型   tokenType [3:汇总]
	 */
	public static final int TRANSCFG_TOKENTYPE_3 = 3;
	/**
	  * 并行令牌类型   tokenType [4:完成]
	 */
	public static final int TRANSCFG_TOKENTYPE_4 = 4;
	/*---------------- 流转路径配置 (ts_TransCfg) END CODE  --------------------*/
	
	/*---------------- 审批记录 (ts_AuditRecords) START CODE  --------------------*/
	/**
	  * 记录类型   recordType [0:审批记录]
	 */
	public static final int AUDITRECORDS_RECORDTYPE_0 = 0;
	/**
	  * 记录类型   recordType [1:补签记录]
	 */
	public static final int AUDITRECORDS_RECORDTYPE_1 = 1;
	/*---------------- 审批记录 (ts_AuditRecords) END CODE  --------------------*/
	
	/*---------------- 并行令牌记录表 (ts_TokenRecords) START CODE  --------------------*/
	/**
	  * 状态   status [1:开始]
	 */
	public static final int TOKENRECORDS_STATUS_1 = 1;
	/**
	  * 状态   status [2:汇总]
	 */
	public static final int TOKENRECORDS_STATUS_2 = 2;
	/**
	  * 状态   status [3:完成]
	 */
	public static final int TOKENRECORDS_STATUS_3 = 3;
	/*---------------- 并行令牌记录表 (ts_TokenRecords) END CODE  --------------------*/
	
	/*---------------- 图标表 (ts_Icon) START CODE  --------------------*/
	/**
	  * 图标类型   iconType [1:标准48*48大图标]
	 */
	public static final int ICON_ICONTYPE_1 = 1; 
	/**
	  * 图标类型   iconType [2:扁平大图标]
	 */
	public static final int ICON_ICONTYPE_2 = 2; 
	/**
	  * 图标类型   iconType [3:小图标]
	 */
	public static final int ICON_ICONTYPE_3 = 3; 
	/*---------------- 图标表 (ts_Variety) END CODE  --------------------*/
	/**========================== 系统基础平台模块 常量定义 END CODE  ============================**/
	/*---------------- 5A项目业务流程单据状态  START CODE  --------------------*/
	/**
	 * 子业务流程单据状态	 [0 : 待提交]
	 */
	public final static int BUSS_PROCC_DJZT_0 = 0;
	/**
	 * 子业务流程单据状态	 [1:审批中]
	 */
	public final static int BUSS_PROCC_DJZT_1 = 1;
	/**
	 * 子业务流程单据状态	 [2:审批通过]
	 */
	public final static int BUSS_PROCC_DJZT_2 = 2;
	/**
	 * 子业务流程单据状态	 [3:审批未通过]
	 */
	public final static int BUSS_PROCC_DJZT_3 = 3;
	/*---------------- 5A项目业务流程单据状态    END CODE  --------------------*/
	
	/*---------------- 审批记录表 (ts_AuditRecords) START CODE  --------------------*/
	/**
	 * 贷款申请   formType [1:贷款申请单ID]
	 */
	public static int SYS_AUDITRECORDS_FORMTYPE_1 = 1;
	/**
	 * 提前还款申请   formType [2:提前还款申请单ID]
	 */
	public static int SYS_AUDITRECORDS_FORMTYPE_2 = 2;
	/**
	 * 展期申请   formType [3:展期申请单ID]
	 */
	public static int SYS_AUDITRECORDS_FORMTYPE_3 = 3;
	/**
	 * 客户授信申请   formType [4:客户授信申请 单ID]
	 */
	public static int SYS_AUDITRECORDS_FORMTYPE_4 = 4;
	/*---------------- 审批记录表 (ts_AuditRecords) END CODE  --------------------*/
	
	/*---------------- 最低金额配置表 (fc_MinAmount) START CODE  --------------------*/
	/**
	 * 审批状态 	status [0:未提交]
	 */
	public static int MINAMOUNT_STATUS_0 = 0;
	/**
	 * 审批状态 	status [1:审批中]
	 */
	public static int MINAMOUNT_STATUS_1 = 1;
	/**
	 * 审批状态 	status [2:取消审批]
	 */
	public static int MINAMOUNT_STATUS_2 = 2;
	/**
	 * 审批状态 	status [3:审批通过]
	 */
	public static int MINAMOUNT_STATUS_3 = 3;
	/*---------------- 最低金额配置表  END CODE   --------------------*/
	
	/*---------------- 贷款申请表  (fc_Apply) START CODE  --------------------*/
	/**
	 * 贷款申请表  state [0:未申请]
	 */
	public static int FIN_APPLY_STATE_0 = 0;
	/**
	 * 贷款申请表  state [1:审批中]
	 */
	public static int FIN_APPLY_STATE_1 = 1;
	/**
	 * 贷款申请表  state [2:已签合同]
	 */
	public static int FIN_APPLY_STATE_2 = 2;
	/**
	 * 贷款申请表  state [3:已放款]
	 */
	public static int FIN_APPLY_STATE_3 = 3;
	/**
	 * 贷款申请表  state [4:收款中]
	 */
	public static int FIN_APPLY_STATE_4 = 4;
	/**
	 * 贷款申请表  state [5:愈期]
	 */
	public static int FIN_APPLY_STATE_5 = 5;
	/**
	 * 贷款申请表  state [6:结清]
	 */
	public static int FIN_APPLY_STATE_6 = 6;
	/**
	 * 贷款申请表  state [14:审批不通过]
	 */
	public static int FIN_APPLY_STATE_14 = 14;
	/**
	 * 贷款申请表  state [15:已结束]
	 */
	public static int FIN_APPLY_STATE_15 = 15;
	/*---------------- 贷款申请表 (fc_Apply)  END CODE  --------------------*/
	
	/*---------------- 放款单表 (fc_LoanInvoce)  START CODE  --------------------*/
	/**
	 * 放款单审批状态  auditState [-1:驳回]
	 */
	public static int LOANCONTRACT_AUDITSTATE_LEN1 = -1;
	/**
	 * 放款单审批状态  auditState [0:待提交]
	 */
	public static int LOANINVOCE_AUDITSTATE_0 = 0;
	/**
	 * 放款单审批状态  auditState [1:待审批]
	 */
	public static int LOANINVOCE_AUDITSTATE_1 = 1;
	/**
	 * 放款单审批状态  auditState [2:审批通过]
	 */
	public static int LOANINVOCE_AUDITSTATE_2 = 2;
	
	/**
	 * 放款单状态  state [0:待放款]
	 */
	public static int LOANINVOCE_STATE_0 = 0;
	/**
	 * 放款单状态  state [1:已放款]
	 */
	public static int LOANINVOCE_STATE_1 = 1;
	/**
	 * 审批通过的
	 */
	public static int LOANINVOCE_STATE_2 = 2;
	
	/**
	 * 计息起始日方式  startWay[1:以合约放款日作为还款计划表计息起始日]
	 */
	public static int LOANINVOCE_STARTWAY_1 = 1;
	/**
	 * 计息起始日方式  startWay[2:以实际放款日作为计息起始日]
	 */
	public static int LOANINVOCE_STARTWAY_2 = 2;
	/*---------------- 放款单表 (fc_LoanInvoce)  END CODE  --------------------*/
	
	/*-------------------- 借款合同表(fc_LoanContract) ----------------------------------*/
	/**
	 *  结算日类型  setdayType [1:实际放款日作为结算日]
	 */
	public static int LOANCONTRACT_SETDAYTYPE_1 = 1;
	/**
	 *  结算日类型  setdayType [2:公司规定的结算日]
	 */
	public static int LOANCONTRACT_SETDAYTYPE_2 = 2;
	/**
	 *  结算日类型  setdayType [3:其它结算日]
	 */
	public static int LOANCONTRACT_SETDAYTYPE_3 = 3;
	
	/**
	 * 利率类型 	rateType [1:月利率]
	 */
	public final static int RATETYPE_1 = 1;
	/**
	 * 利率类型 	rateType [2:日利率]
	 */
	public final static int RATETYPE_2 = 2;
	/**
	 * 利率类型 	rateType [3:年利率]
	 */
	public final static int RATETYPE_3 = 3;
	
	/**
	 * 是否预收息 	isadvance [0:否]
	 */
	public static int ISADVANCE_0 = 0;
	/**
	 * 是否预收息 	isadvance [1:是]
	 */
	public static int ISADVANCE_1 = 1;
	
	/**
	 * 管理费收取方式 	mgrtype [0:不收管理费]
	 */
	public static int MGRTYPE_0 = 0;
	/**
	 * 管理费收取方式 	mgrtype [1:按还款方式算法收取]
	 */
	public static int MGRTYPE_1 = 1;
	
	/*---------------- 还款计划总表 (fc_Plan)  START CODE  --------------------*/
	/**
	 * 总状态 	status [0:未到还款日]
	 */
	public static final int PLAN_STATUS_0 = 0;
	/**
	 * 总状态 	status [1:部分收款]
	 */
	public static final int PLAN_STATUS_1 = 1;
	/**
	 * 总状态 	status [2:结清]
	 */
	public static final int PLAN_STATUS_2 = 2;
	/**
	 * 总状态 	status [3:预收]
	 */
	public static final int PLAN_STATUS_3 = 3;
	/**
	 * 总状态 	status [4:表内逾期]
	 */
	public static final int PLAN_STATUS_4 = 4;
	/*
	 * 总状态 	status [5:表外逾期]
	 */
	public static final int PLAN_STATUS_5 = 5;
	/**
	 * 总状态 	status [6:核销]
	 */
	public static final int PLAN_STATUS_6 = 6;
	/**
	 * 总状态 	status [7:表外核销结清]
	 */
	public static final int PLAN_STATUS_7 = 7;
	/**
	 * 总状态 	status [8:部分提前还款]
	 */
	public static final int PLAN_STATUS_8 = 8;
	/**
	 * 总状态 	status [9:提前结清]
	 */
	public static final int PLAN_STATUS_9 = 9;
	/**
	 * 总状态 	status [10:其它期提前还款]
	 */
	public static final int PLAN_STATUS_10 = 10;
	
	/**
	 * 本息状态 	pistatus [0:未到还款日]
	 */
	public static final int PLAN_PISTATUS_0 = 0;
	/**
	 * 本息状态 	pistatus [1:部分收款]
	 */
	public static final int PLAN_PISTATUS_1 = 1;
	/**
	 * 本息状态 	pistatus [2:结清]
	 */
	public static final int PLAN_PISTATUS_2 = 2;
	/**
	 * 本息状态 	pistatus [3:愈期]
	 */
	public static final int PLAN_PISTATUS_3 = 3;
	
	/**
	 * 管理费状态 	pistatus [0:未到还款日]
	 */
	public static final int PLAN_MGRSTATUS_0 = 0;
	/**
	 * 管理费状态 	pistatus [1:部分收款]
	 */
	public static final int PLAN_MGRSTATUS_1 = 1;
	/**
	 * 管理费状态 	pistatus [2:结清]
	 */
	public static final int PLAN_MGRSTATUS_2 = 2;
	/**
	 * 管理费状态 	pistatus [3:愈期]
	 */
	public static final int PLAN_MGRSTATUS_3 = 3;
	
	/*---------------- 还款计划总表 (fc_Plan)  END CODE  --------------------*/
	
	/*---------------- 表内表外表 (fc_Taboutside)  START CODE  --------------------*/
	/**
	 * 表内表外 	inouttype [0:表内]
	 */
	public final static int TABOUTSIDE_INOUTTYPE_0 = 0;
	/**
	 * 表内表外 	inouttype [1:表外]
	 */
	public final static int TABOUTSIDE_INOUTTYPE_1 = 1;
	
	/*---------------- 表内表外表 (fc_Taboutside)  END CODE  --------------------*/
	
	/*---------------- 实收金额表 (fc_AmountRecords)  START CODE  --------------------*/
	/**
	 * 业务标识 	bussTag [0:正常扣收标识]
	 */
	public static final int AMOUNTRECORDS_BUSSTAG_0 = 0;
	/**
	 * 业务标识 	bussTag [1:豁免标识]
	 */
	public static final int AMOUNTRECORDS_BUSSTAG_1 = 1;
	/**
	 * 业务标识 	bussTag [2:预收结清]
	 */
	public static final int AMOUNTRECORDS_BUSSTAG_2 = 2;
	/**
	 * 业务标识 	bussTag [3:表外核销]
	 */
	public static final int AMOUNTRECORDS_BUSSTAG_3 = 3;
	/**
	 * 业务标识 	bussTag [4:表内愈期]
	 */
	public static final int AMOUNTRECORDS_BUSSTAG_4 = 4;
	/**
	 * 业务标识 	bussTag [5:逾期豁免]
	 */
	public static final int AMOUNTRECORDS_BUSSTAG_5 = 5;
	/**
	 * 业务标识 	bussTag [6:表外逾期]
	 */
	public static final int AMOUNTRECORDS_BUSSTAG_6 = 6;
	/**
	 * 业务标识 	bussTag [7:提前还款]
	 */
	public static final int AMOUNTRECORDS_BUSSTAG_7 = 7;
	/**
	 * 业务标识 	bussTag [8:随借随还扣收]
	 */
	public static final int AMOUNTRECORDS_BUSSTAG_8 = 8;
	/**
	 * 业务标识 	bussTag [9:随借随还息费返还]
	 */
	public static final int AMOUNTRECORDS_BUSSTAG_9 = 9;
	/**
	 * 业务标识 	bussTag [10:随借随还息费豁免]
	 */
	public static final int AMOUNTRECORDS_BUSSTAG_10 = 10;
	/*---------------- 实收金额表 (fc_AmountRecords)  END CODE  --------------------*/
	
	/*---------------- 实收随借随还表 (fc_CasualRecords)  START CODE  --------------------*/
	/**
	 * 业务标识 	bussTag [0:正常扣收标识]
	 */
	public static final int CASUALRECORDS_BUSSTAG_0 = 0;
	/**
	 * 业务标识 	bussTag [1:豁免标识]
	 */
	public static final int CASUALRECORDS_BUSSTAG_1 = 1;
	/**
	 * 业务标识 	bussTag [2:息费返还]
	 */
	public static final int CASUALRECORDS_BUSSTAG_2 = 2;
	/*---------------- 实收随借随还表 (fc_CasualRecords)  END CODE  --------------------*/
	
	/*---------------- 实收提前还款金额表 (fc_PrepaymentRecords)  START CODE  --------------------*/
	/**
	 * 业务标识 	bussTag [0:正常扣收标识]
	 */
	public static final int PREPAYMENTRECORDS_BUSSTAG_0 = 0;
	/**
	 * 业务标识 	bussTag [1:豁免标识]
	 */
	public static final int PREPAYMENTRECORDS_BUSSTAG_1 = 1;
	/*---------------- 实收提前还款金额表 (fc_PrepaymentRecords)  END CODE  --------------------*/
	
	/*---------------- 放款手续费(fc_Free)  START CODE  --------------------*/
	/**
	 * 收款状态	status [0:未收]
	 */
	public static int FREE_STATUS_0 = 0;
	/**
	 * 收款状态	status [1:部分收款]
	 */
	public static int FREE_STATUS_1 = 1;
	/**
	 * 收款状态	status [2:结清]
	 */
	public static int FREE_STATUS_2 = 2;
	
	/**
	 * 豁免状态	exempt [0:未豁免]
	 */
	public static int FREE_EXEMPT_0 = 0;
	/**
	 * 豁免状态	exempt [1:已豁免]
	 */
	public static int FREE_EXEMPT_1 = 1;
	/*---------------- 放款手续费(fc_Free)  END CODE  --------------------*/
	
	/*---------------- 资金流水表(fc_FundsWater)  START CODE  --------------------*/
	/**
	 * 流水类型	waterType [1:放款]
	 */
	public static int FUNDSWATER_WATERTYPE_1 = 1;
	/**
	 * 流水类型 	waterType [2:收款]
	 */
	public static int FUNDSWATER_WATERTYPE_2  = 2;
	/**
	 * 流水类型 	waterType [3:预收]
	 */
	public static int FUNDSWATER_WATERTYPE_3  = 3;
	/**
	 * 流水类型 	waterType [4:逾期收款]
	 */
	public static int FUNDSWATER_WATERTYPE_4  = 4;
	/**
	 * 流水类型 	waterType [5:提前还款收款]
	 */
	public static int FUNDSWATER_WATERTYPE_5  = 5;
	/**
	 * 流水类型 	waterType [6:其他费用]
	 */
	public static int FUNDSWATER_WATERTYPE_6  = 6;
	/**
	 * 流水类型 	waterType [7:随借随还收款]
	 */
	public static int FUNDSWATER_WATERTYPE_7  = 7;
	/**
	 * 流水类型 	waterType [8:项目杂费]
	 */
	public static int FUNDSWATER_WATERTYPE_8  = 8;
	/**
	 * 流水类型 	waterType [9:自有资金(存/支流水）]
	 */
	public static int FUNDSWATER_WATERTYPE_9  = 9;
	/**
	 * 流水类型 	waterType [10:外部融资(存/支流水)]
	 */
	public static int FUNDSWATER_WATERTYPE_10  = 10;
	/**
	 * 流水类型 	waterType [11:随借随还息费返还]
	 */
	public static int FUNDSWATER_WATERTYPE_11  = 11;
	/**
	 * 流水类型 	waterType [12:正常息费返还]
	 */
	public static int FUNDSWATER_WATERTYPE_12  = 12;
	/**
	 * 流水类型 	waterType [13:逾期息费返还]
	 */
	public static int FUNDSWATER_WATERTYPE_13  = 13;
	/**
	 * 业务标识 	bussTag [0:放款]
	 */
	public static int FUNDSWATER_BUSSTAG_0 = 0;
	/**
	 * 业务标识 	bussTag [1:放款手续费]
	 */
	public static int FUNDSWATER_BUSSTAG_1  = 1;
	
	/**
	 * 业务标识 	bussTag [2:放款手续费]
	 */
	public static int FUNDSWATER_BUSSTAG_2 = 2;
	/**
	 * 业务标识 	bussTag [3:预收]
	 */
	public static int FUNDSWATER_BUSSTAG_3  = 3;
	
	/**
	 * 业务标识	bussTag [4:逾期收款]
	 */
	public static int FUNDSWATER_BUSSTAG_4 = 4;
	/**
	 * 业务标识	bussTag [5:提前还款收款]
	 */
	public static int FUNDSWATER_BUSSTAG_5 = 5;
	/**
	 * 业务标识	bussTag [6:其他费用]
	 */
	public static int FUNDSWATER_BUSSTAG_6 = 6;
	/**
	 * 业务标识	bussTag [7:随借随还收款]
	 */
	public static int FUNDSWATER_BUSSTAG_7 = 7;
	/**
	 * 业务标识	bussTag [7:随借随还息费返还]
	 */
	public static int FUNDSWATER_BUSSTAG_8 = 8;
	
	/*---------------- 资金流水表(fc_FundsWater)  END CODE  --------------------*/
	
	/*---------------- 展期计划表表(fc_ExtPlan)  START CODE  --------------------*/
	/**
	 * 动作类型actionType ：1 展期申请单生成计划表;
	 */
	public final static int EXTPLAN_ACTIONTYPE_1 = 1;
	/**
	 * 动作类型actionType ：2 签展期协议书生成计划表
	 */
	public final static int EXTPLAN_ACTIONTYPE_2 = 2;
	/*---------------- 展期计划表表(fc_ExtPlan)  END CODE  --------------------*/
	
	/*---------------- 提前还款申请表(fc_Prepayment)  START CODE  --------------------*/
	/**
	 * 收款状态	xstatus [0:未收款]
	 */
	public static int PREPAYMENT_XSTATUS_0 = 0;
	/**
	 * 收款状态	xstatus [1:部分收款]
	 */
	public static int PREPAYMENT_XSTATUS_1 = 1;
	/**
	 * 收款状态	xstatus [2:结清]
	 */
	public static int PREPAYMENT_XSTATUS_2 = 2;
	/*---------------- 提前还款申请表(fc_Prepayment)  END CODE  --------------------*/
	
	/*---------------- 息费豁免申请表(fc_Exempt)  START CODE  --------------------*/
	/**
	 * 豁免类别	etype [1:放款手续费豁免]
	 */
	public final static int EXEMPT_ETYPE_1 = 1;
	/**
	 * 豁免类别	etype [2:提前还款息费豁免]
	 */
	public final static int EXEMPT_ETYPE_2 = 2;
	/**
	 * 豁免类别	etype [3:正常/逾期还款息费豁免]
	 */
	public final static int EXEMPT_ETYPE_3 = 3;
	
	/**
	 * 豁免项目	exeItems [1:放款手续费]
	 */
	public final static String EXEMPT_EXEITEMS_1 = "1";
	/**
	 * 豁免项目	exeItems [2:利息]
	 */
	public final static String EXEMPT_EXEITEMS_2 = "2";
	/**
	 * 豁免项目	exeItems [3:管理费]
	 */
	public final static String EXEMPT_EXEITEMS_3 = "3";
	/**
	 * 豁免项目	exeItems [4:罚息]
	 */
	public final static String EXEMPT_EXEITEMS_4 = "4";
	/**
	 * 豁免项目	exeItems [5:滞纳金]
	 */
	public final static String EXEMPT_EXEITEMS_5 = "5";
	/**
	 * 豁免项目	exeItems [6:提前还款手续费]
	 */
	public final static String EXEMPT_EXEITEMS_6 = "6";
	/*---------------- 息费豁免申请表(fc_Exempt)  END CODE  --------------------*/
	
	/**========================== 财务系统模块 常量定义 START CODE  ============================**/
	/*---------------- (财务系统模块) 自定义业务对象表 (fs_FinBussObject)  START CODE  --------------------*/
	/**
	 * 自定义业务对象---业务类名	className [CustBaseEntity:客户基础信息实体]
	 */
	public final static String FINBUSSOBJECT_CLASSNAME_CUSTBASEENTITY = "CustBaseEntity";
	/**
	 * 自定义业务对象---业务类名	className [LoanContractEntity:借款合同实体]
	 */
	public final static String FINBUSSOBJECT_CLASSNAME_LOANCONTRACTENTITY = "LoanContractEntity";
	/**
	 * 自定义业务对象---业务类名	className [LoanInvoceEntity:放款单实体]
	 */
	public final static String FINBUSSOBJECT_CLASSNAME_LOANINVOCEENTITY = "LoanInvoceEntity";
	/**
	 * 自定义业务对象---业务类名	className [FreeRecordsEntity:实收放款手续费实体]
	 */
	public final static String FINBUSSOBJECT_CLASSNAME_FREERECORDSENTITY = "FreeRecordsEntity";
	/**
	 * 自定义业务对象---业务类名	className [AmountRecordsEntity:实收金额实体]
	 */
	public final static String FINBUSSOBJECT_CLASSNAME_AMOUNTRECORDSENTITY = "AmountRecordsEntity";
	/**
	 * 自定义业务对象---业务类名	className [PrepaymentRecordsEntity:实收提前还款金额实体]
	 */
	public final static String FINBUSSOBJECT_CLASSNAME_PREPAYMENTRECORDSENTITY = "PrepaymentRecordsEntity";
	/**
	 * 自定义业务对象---业务类名	className [AmountLogEntity:实收金额日志实体]
	 */
	public final static String FINBUSSOBJECT_CLASSNAME_AMOUNTLOGENTITY = "AmountLogEntity";
	/**
	 * 自定义业务对象---业务类名	className [AccountEntity:公司银行帐号实体]
	 */
	public final static String FINBUSSOBJECT_CLASSNAME_ACCOUNTENTITY = "AccountEntity";
	
	/*---------------- (财务系统模块) 自定义业务对象表 (fs_FinBussObject)  END CODE  --------------------*/
	
	
	/*---------------- (财务系统模块) 财务自定义字段表 (fs_FinCustField)  START CODE  --------------------*/
	/**
	 * 财务自定义字段 ---数据类型	dataType [1:字符串]
	 */
	public final static int FINCUSTFIELD_DATATYPE_1 = 1;
	/**
	 * 财务自定义字段 ---数据类型	dataType [2:日期]
	 */
	public final static int FINCUSTFIELD_DATATYPE_2 = 2;
	/**
	 * 财务自定义字段 ---数据类型	dataType [3:金额]
	 */
	public final static int FINCUSTFIELD_DATATYPE_3 = 3;
	/**
	 * 财务自定义字段 ---数据类型	dataType [4:整数]
	 */
	public final static int FINCUSTFIELD_DATATYPE_4 = 4;
	/*---------------- (财务系统模块) 财务自定义字段表 (fs_FinCustField)  END CODE  --------------------*/
	/*---------------- (财务系统模块) 凭证模板表 (fs_VoucherTemp)  START CODE  --------------------*/
	/**
	 * 分录方向	entry [1:借方多分录]
	 */
	public final static int VOUCHERTEMP_ENTRY_1 = 1;
	/**
	 * 分录方向	entry [2:贷方多分录]
	 */
	public final static int VOUCHERTEMP_ENTRY_2 = 2;
	/**
	 * 分录方向	entry [3:双方多分录]
	 */
	public final static int VOUCHERTEMP_ENTRY_3 = 3;
	
	/**
	 * 批量业务策略	tactics [1:一笔业务生成一张凭证]
	 */
	public final static int VOUCHERTEMP_TACTICS_1 = 1;
	/**
	 * 批量业务策略	tactics [2:多笔业务生成一张凭证]
	 */
	public final static int VOUCHERTEMP_TACTICS_2 = 2;
	

	/*---==> 凭证编号常量---*/
	/**
	 * 凭证模板编号	code [V00017:逾期扣收（表内）]
	 */
	public final static String VOUCHERTEMP_CODE_V00017 = "V00017";
	/**
	 * 凭证模板编号	code [V00037:表外逾期扣收]
	 */
	public final static String VOUCHERTEMP_CODE_V00037 = "V00037";
	/**
	 * 凭证模板编号	code [V00036:表外逾期扣收-备查登记类账户余额]
	 */
	public final static String VOUCHERTEMP_CODE_V00036 = "V00036";
	
	/*---------------- (财务系统模块) 凭证模板表 (fs_VoucherTemp)  END CODE  --------------------*/
	
	/*---------------- (财务系统模块) 分录模板表 (fs_EntryTemp)  END CODE  --------------------*/
	/**
	 * 余额方向	fdc [0:贷方]
	 */
	public final static byte ENTRYTEMP_FDC_0 = 0;
	/**
	 * 余额方向	fdc [1:借方]
	 */
	public final static byte ENTRYTEMP_FDC_1 = 1;
	/*---------------- (财务系统模块) 分录模板表 (fs_EntryTemp)  END CODE  --------------------*/
	
	/*---------------- (财务系统模块) 实收金额日志 (fs_AmountLog)  START CODE  --------------------*/
	/**
	 * 业务分类	category [0:放款 --> fc_LoanInvoce]
	 */
	public final static int AMOUNTLOG_CATEGORY_0 = 0;
	/**
	 * 业务分类	category [1:放款手续费 --> fc_FreeRecords]
	 */
	public final static int AMOUNTLOG_CATEGORY_1 = 1;
	/**
	 * 业务分类	category [2:还款计划 --> fc_AmountRecords]
	 */
	public final static int AMOUNTLOG_CATEGORY_2 = 2;
	/**
	 * 业务分类	category [3:预收 --> fc_BfRecords]
	 */
	public final static int AMOUNTLOG_CATEGORY_3 = 3;
	/**
	 * 业务分类	category [4:提前还款 --> fc_PrepaymentRecords]
	 */
	public final static int AMOUNTLOG_CATEGORY_4 = 4;
	/**
	 * 业务分类	category [5:日常收支费用 --> fc_FundsWater]
	 */
	public final static int AMOUNTLOG_CATEGORY_5 = 5;
	/**
	 * 业务分类	category [6:随借随还  --> fc_CasualRecords]
	 */
	public final static int AMOUNTLOG_CATEGORY_6 = 6;
	/**
	 * 业务分类	category [7:项目费用 --> fc_PamountRecords(实收项目费用表)]
	 */
	public final static int AMOUNTLOG_CATEGORY_7 = 7;	
	/**
	 * 业务分类	category [8:外部融资--> fc_OinterestRecords]
	 */
	public final static int AMOUNTLOG_CATEGORY_8 = 8;
	
	/**
	 * 业务标识	bussTag [0:放款]
	 */
	public final static int AMOUNTLOG_BUSSTAG_0 = 0;
	/**
	 * 业务标识	bussTag [1:放款手续费]
	 */
	public final static int AMOUNTLOG_BUSSTAG_1 = 1;
	/**
	 * 业务标识	bussTag [2:正常收款]
	 */
	public final static int AMOUNTLOG_BUSSTAG_2 = 2;
	/**
	 * 业务标识	bussTag [3:正常收款豁免]
	 */
	public final static int AMOUNTLOG_BUSSTAG_3 = 3;
	/**
	 * 业务标识	bussTag [4:预收结清]
	 */
	public final static int AMOUNTLOG_BUSSTAG_4 = 4;
	/**
	 * 业务标识	bussTag [5:表外核销]
	 */
	public final static int AMOUNTLOG_BUSSTAG_5 = 5;
	/**
	 * 业务标识	bussTag [6:表内愈期收款]
	 */
	public final static int AMOUNTLOG_BUSSTAG_6 = 6;
	/**
	 * 业务标识	bussTag [7:表外愈期收款]
	 */
	public final static int AMOUNTLOG_BUSSTAG_7 = 7;
	/**
	 * 业务标识	bussTag [8:逾期豁免]
	 */
	public final static int AMOUNTLOG_BUSSTAG_8 = 8;
	/**
	 * 业务标识	bussTag [9:提前还款]
	 */
	public final static int AMOUNTLOG_BUSSTAG_9 = 9;
	/**
	 * 业务标识	bussTag [10:提前还款豁免]
	 */
	public final static int AMOUNTLOG_BUSSTAG_10 = 10;
	/**
	 * 业务标识	bussTag [11:日常收支费用]
	 */
	public final static int AMOUNTLOG_BUSSTAG_11 = 11;
	/**
	 * 业务标识	bussTag [12:随借随还]
	 */
	public final static int AMOUNTLOG_BUSSTAG_12 = 12;
	/**
	 * 业务标识	bussTag [13:随借随还息费返还]
	 */
	public final static int AMOUNTLOG_BUSSTAG_13 = 13;
	/**
	 * 业务标识	bussTag [14:随借随还豁免]
	 */
	public final static int AMOUNTLOG_BUSSTAG_14 = 14;
	/**
	 * 业务标识	bussTag [15:项目费用]
	 */
	public final static int AMOUNTLOG_BUSSTAG_15 = 15;
	/**
	 * 业务标识	bussTag [16:外部融资付息]
	 */
	public final static int AMOUNTLOG_BUSSTAG_16 = 16;
	/*---------------- (财务系统模块) 实收金额日志 (fs_AmountLog)  END CODE  --------------------*/
	
	/*---------------- (财务系统模块) 凭证日志表 (fs_VoucherOplog)  START CODE  --------------------*/
	/**
	 * 状态码	status [0:凭证生成成功]
	 */
	public final static int VOUCHEROPLOG_STATUS_0 = 0;
	/**
	 * 状态码	status [1:凭证生成失败]
	 */
	public final static int VOUCHEROPLOG_STATUS_1 = 1;
	
	
	/**
	 * 错误代码	errCode [0:无错误]
	 */
	public final static int VOUCHEROPLOG_ERRCODE_0 = 0;
	/**
	 * 错误代码	errCode [1:服务器连接失败]
	 */
	public final static int VOUCHEROPLOG_ERRCODE_1 = 1;
	/**
	 * 错误代码	errCode [2:用户账号未映射]
	 */
	public final static int VOUCHEROPLOG_ERRCODE_2 = 2;
	/**
	 * 错误代码	errCode [3:找不到对应的凭证模板]
	 */
	public final static int VOUCHEROPLOG_ERRCODE_3 = 3;
	/**
	 * 错误代码	errCode [4:系统业务异常]
	 */
	public final static int VOUCHEROPLOG_ERRCODE_4 = 4;
	/**
	 * 错误代码	errCode [5:业务系统与财务系统未映射]
	 */
	public final static int VOUCHEROPLOG_ERRCODE_5 = 5;
	/**
	 * 错误代码	errCode [6:找不到相关的财务系统配置]
	 */
	public final static int VOUCHEROPLOG_ERRCODE_6 = 6;
	/**
	 * 错误代码	errCode [7:凭证模板中分录未配置或已禁用]
	 */
	public final static int VOUCHEROPLOG_ERRCODE_7 = 7;
	/*---------------- (财务系统模块) 凭证日志表 (fs_VoucherOplog)  END CODE  --------------------*/
	
	/*---------------- (财务系统模块) 凭证编号规则表 (fs_CodeRule)  START CODE  --------------------*/
	/**
	 * 是否发生更改	change [0:否]
	 */
	public final static int CODERULE_CHANGE_0 = 0;
	/**
	 * 是否发生更改	change [1:是]
	 */
	public final static int CODERULE_CHANGE_1 = 1;
	/*---------------- (财务系统模块) 凭证编号规则表 (fs_CodeRule)  END CODE  --------------------*/
	
	/**========================== 消息通知  ============================**/
	/**
	 *1:流程审批记录[ts_AuditRecords],
	 */
	public final static int MSGNOTIFY_BUSSTYPE_1 = 1;
	/**
	 * 2:提前还款通知[fc_Prepayment]
	 */
	public final static int MSGNOTIFY_BUSSTYPE_2 = 2;
	
	/**
	 *0:未发送
	 */
	public final static int MSGNOTIFY_STATUS_0 = 0;
	/**
	 * 1:发送成功
	 */
	public final static int MSGNOTIFY_STATUS_1 = 1;
	/**
	 * 2:发送失败
	 */
	public final static int MSGNOTIFY_STATUS_2 = 2;
	
	
}
