/**
 * 展期协议书表单面板
 */
define(function(require, exports) {
	exports.moduleUI = {
		extContactFrmPanel : null,
		params :null,
		extPlanGrid : null,
		attachMentFs : null,
		seDateDisplayId : null,
		parent : null,
		changeG:{},
		extensionId : null,
		isNotHidden : null,
		getModule:function(params){
			this.serParams(params);
			if(!this.extContactFrmPanel){
				this.createFrmPnl();
			}
			return this.extContactFrmPanel;
		},
		serParams : function(params){
			this.params = params;
			this.seDateDisplayId  = params.seDateDisplayId;
			this.attachMentFs = params.attachMentFs;
			this.parent  = params.parent;
			this.extensionId = params.extensionId;
			this.isNotHidden = params.isNotHidden;
		},
		createFrmPnl : function(){
			var _this = this;
			var txt_extensionId = FormUtil.getHidField({fieldLabel: '展期申请单ID',name: 'extensionId',value:_this.extensionId});
			var txt_procId = FormUtil.getHidField({fieldLabel: '流程实例ID',name: 'procId'});
			var txt_breed = FormUtil.getHidField({fieldLabel: '子业务流程ID', name: 'breed'});
			var txt_isenabled = FormUtil.getHidField({fieldLabel: '可用标识',name: 'isenabled'});
			var txt_id = FormUtil.getHidField({fieldLabel: '展期协议书ID',name: 'id'	});
			var txt_contractId = FormUtil.getHidField({fieldLabel: '借款合同ID',name: 'contractId'});
			var txt_loanCode = FormUtil.getHidField({fieldLabel: '借款合同号',name: 'loanCode'});
			var txt_guaCode = FormUtil.getHidField({fieldLabel: '担保合同号',name: 'guaCode'});
			var txt_ostartDate = FormUtil.getHidField({fieldLabel: '原借款起始日期',name: 'ostartDate'});
			var txt_oendDate = FormUtil.getHidField({fieldLabel: '原借款截止日期',name: 'oendDate'});
			var txt_endAmount = FormUtil.getHidField({fieldLabel: '原贷款金额',name: 'endAmount'});
			var txt_estartDate = FormUtil.getHidField({fieldLabel: '展期起始日期',name: 'estartDate'});
			var txt_eendDate = FormUtil.getHidField({fieldLabel: '展期截止日期',name: 'eendDate'});
			
			var txt_code = FormUtil.getReadTxtField({
			    fieldLabel: '协议书编号',
			    name: 'code',
			    "width": 150,
			    "maxLength": "20",
			    allowBlank : false
//			    ,
//			    emptyText : '如不填写系统自动生成编号'
			});
			var txt_applyMan = FormUtil.getTxtField({
			    fieldLabel: '借款人',
			    name: 'applyMan',
			    allowBlank : false,
			    "width": 150
			});
			var txt_asignDate = FormUtil.getDateField({
			    fieldLabel: '借款人签字日期',
			    name: 'asignDate',
			    allowBlank : false,
			    "width": 150
			});
			
			var cmb_guarantor = FormUtil.getTxtField({
			    fieldLabel: '担保人',
			    name: 'guarantor',
			    allowBlank : false,
			    "width": 230
			});
			
			
			var callback = function(cboGrid,selVals){
				var grid = cboGrid.grid;
				var record = grid.getSelRow();
				var signDate = record.get("signDate");
				Date_gsignDate.setValue(signDate);
			}
//			var cmb_guarantor = ComboxControl.getGuaGrid({
//				fieldLabel:'担保人',name:'guarantor',
//				allowBlank : false,
//				isGetTxt : true,
//				width:230,
//				listeners  :{
//					'change' :function(){
//						alert("www");
//					}
//				},
//				callback:callback,
//				params : {extensionId:_this.extensionId}}	);
				
			var Date_gsignDate = FormUtil.getDateField({
			    fieldLabel: '担保人签字日期',
			    name: 'gsignDate',
			    "width": 150
			});
			
			var txt_extAmount = FormUtil.getMoneyField({
			    fieldLabel: '申请展期金额',
			    name: 'extAmount',
			     "width": 150,
			    fieldWidth:80,
			    allowBlank: false
			});
			
			var int_yearLoan = FormUtil.getIntegerField({
				fieldLabel : '展期期限(年)',
				name : 'yearLoan',
				width : 30,
				"allowBlank" : false,
				"value" : 0,
				"maxLength" : 10,
				maxValue : 100,
				listeners : {change:function(){
					_this.parent.setSeDate();
				}}
			});

			var int_monthLoan = FormUtil.getIntegerField({
				fieldLabel : '展期期限(月)',
				name : 'monthLoan',
				width : 30,
				"allowBlank" : false,
				"value" : 0,
				"maxLength" : 10,
				maxValue : 12,
				listeners : {change:function(){
				_this.parent.setSeDate();
				
				}}
			});

			var int_dayLoan = FormUtil.getIntegerField({
				fieldLabel : '展期期限(日)',
				name : 'dayLoan',
				width : 30,
				"allowBlank" : false,
				"value" : 0,
				maxValue : 31,
				"maxLength" : 10,
				listeners : {change:function(){
				_this.parent.setSeDate();
				}}
			});
			var disp_seDate = new Ext.form.DisplayField({id:_this.seDateDisplayId,value:''});
			var comp_loanLimit = FormUtil.getMyCompositeField({
				fieldLabel : '展期期限',
				name : 'limitLoan',
				width : 500,
				allowBlank : false,
				sigins : null,
				itemNames : 'yearLoan,monthLoan,dayLoan',
				items : [int_yearLoan, {
							xtype : 'displayfield',
							value : '年',
							width : 6
						}, int_monthLoan, {
							xtype : 'displayfield',
							value : '月',
							width : 6
						}, int_dayLoan, {
							xtype : 'displayfield',
							value : '日',
							width : 6
						},disp_seDate]
			});
			
			
			var cbo_rateType = FormUtil.getLCboField({
			    fieldLabel: '展期利率类型',
			    hiddenLabel : true,
			    name: 'rateType',
			    "width": 60,
			    "allowBlank": false,
			    "maxLength": 10,
			    "data": [["1", "月利率"], ["2", "日利率"], ["3", "年利率"]]
			});
			
			var int_rateType = FormUtil.getDoubleField({
			    fieldLabel: '展期贷款利率',
			    name: 'rate',
			    "width": 50,
			    "allowBlank": false,
			    "maxLength": 10
			});
			
			var comp_extrate = FormUtil.getMyCompositeField({
				fieldLabel : '展期贷款利率',
				name : 'comp_extrate',
				width : 150,
				allowBlank : false,
				sigins : null,
				itemNames : 'rateType,rate',
				items :[cbo_rateType,int_rateType, {
							xtype : 'displayfield',
							value : '%',
							width : 6
						}]
			});
			
			var cbo_payType = FormUtil.getRCboField({
				fieldLabel : '还款方式',
				name : 'payType',
				width : 140,
				maxLength : 50,
				url: "./fcPayType_cbodatas.action"
			});
			
			var txt_phAmount = FormUtil.getMoneyField({
				fieldLabel : '每期固定还本额',
				name : 'phAmount',
				width : 80,
				value : 0
			});

			var compt_payType = FormUtil.getMyCompositeField({
				fieldLabel : '还款方式',
				"allowBlank": false,
				sigins : null,
				itemNames : 'payType,phAmount',
				name : 'compt_payType',
				width : 300,
				items : [cbo_payType, {
							xtype : 'displayfield',
							html : '<span style="color:red;">(</span>'
						}, txt_phAmount, {
							xtype : 'displayfield',
							width : 100,
							html : '<span style="color:red;">分期还本金额)</span>'
						}]
			});

					
			var rad_isadvance = FormUtil.getRadioGroup({
			    fieldLabel: '是否预收息',
			    name: 'isadvance',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": 10,
			    "items": [{
			        "boxLabel": "否",
			        "name": "isadvance",
			        "inputValue": 0
			    },
			    {
			        "boxLabel": "是",
			        "name": "isadvance",
			        "inputValue": 1
			    }]
			});
			
			
			
			
			var cbo_mgrtype = FormUtil.getLCboField({
			    fieldLabel: '管理费收取方式',
			    name: 'mgrtype',
			    "width": 150,
			    "allowBlank": false,
			    "maxLength": 10,
			    "data": Lcbo_dataSource.mgrtype_dates
			});
			
			var dob_mrate = FormUtil.getDoubleField({
			    fieldLabel: '管理费率',
			    name: 'mrate',
			    "width": 150,
			    "allowBlank": false,
			    "value": "0",
			    "maxLength": 10,
			    unitText : '%'
			});
			
			var txt_manager = FormUtil.getTxtField({
			    fieldLabel: '签约负责人',
			    name: 'manager',
				allowBlank : false,
			    "width": 150,
			    value : CURENT_EMP
			});
			
			var bdat_applyDate = FormUtil.getDateField({
			    fieldLabel: '签约日期',
			    name: 'signDate',
				allowBlank : false,
			    "width": 150
			});
			
			var txt_otherRemark = FormUtil.getTAreaField({
			    fieldLabel: '其他事项',
			    name: 'otherRemark',
			    "width": 475,
			    "maxLength": "500"
			});
			
			var firstPanel = new Ext.Panel({layout:'form',items:[ txt_otherRemark]});
	 		var twoPanel = new Ext.Panel({layout:'form',items:[_this.attachMentFs]});
	 		var layoutCmnPanel = FormUtil.getLayoutPanel([.66,.33],[firstPanel,twoPanel]);
	 
			var layout_fields = [
			txt_id,txt_extensionId, txt_isenabled, txt_eendDate, txt_estartDate, txt_endAmount, txt_oendDate, txt_ostartDate, txt_guaCode, txt_loanCode, txt_contractId, txt_procId, txt_breed, {
			    cmns: FormUtil.CMN_THREE,
			    fields: [txt_code, txt_applyMan, txt_asignDate]
			},
			{
			    cmns: FormUtil.CMN_TWO_LEFT,
			    fields: [cmb_guarantor, Date_gsignDate]
			},
			{
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_extAmount,comp_loanLimit,comp_extrate,compt_payType]
			},
			{
			    cmns: FormUtil.CMN_THREE,
			    fields: [ rad_isadvance, cbo_mgrtype, dob_mrate,txt_manager,bdat_applyDate]
			}
			,layoutCmnPanel
			];

			var title = '展期协议书';
			var frm_cfg = {
				title : title,
				autoScroll : true,
				hidden:_this.isNotHidden,
				url : './fcExtContract_save.action',
				labelWidth : 115
			};
			var extContactFrmPanel = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			var _this = this;
			this.extContactFrmPanel = extContactFrmPanel ;
			return extContactFrmPanel;
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function() {
			if (null != this.extContactFrmPanel) {
				this.extContactFrmPanel.destroy();
				this.extContactFrmPanel = null;
			}
		}
	}
});