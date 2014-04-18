/**
 * 借款合同编辑页面
 * #DESCRIPTION#
 * @author smartplatform_auto
 * @date 2013-01-06 07:00:32
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		payDayId : Ext.id(null,'payDay'),
		a_flexPlanId : Ext.id(null,'flexPlanListId'),/*添加灵活还款的超链接*/
		params : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		custType : null,
		customerId : null,
		sysid : null,
		dialogbox : null,/*灵活还款弹出窗口*/
		setParams:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.sysid = parentCfg.parent.sysid;
			this.custType =  parentCfg.parent.custType;
			this.customerId =  parentCfg.parent.customerId;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			var _this = this;
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({autoScroll : true,width:950,getUrls:this.getUrls,appFrm : this.appFrm,isNotSetVs : true,
			optionType : this.optionType, refresh : this.refresh,eventMgr:{saveData:this.saveData}
			});
		},
		/**
		 * 显示弹出窗口
		 * @param _parentCfg 弹出之前传入的参数
		 */
		show : function(_parentCfg){
			if(_parentCfg) this.setParams(_parentCfg);
			if(!this.appWin){
				this.createAppWindow();
			}
			this.appWin.optionType = this.optionType;
			this.appWin.show();
		},
		/**
		 * 销毁组件
		 */
		destroy : function(){
			if(!this.appWin) return;
			this.appWin.close();	//关闭并销毁窗口
			this.appWin = null;		//释放当前窗口对象引用
			if(!this.dialogbox) return;
			this.dialogbox.destroy();
			this.dialogbox = null;
		},
		/**
		 * 获取各种URL配置
		 * addUrlCfg : 新增 URL
		 * editUrlCfg : 修改URL
		 * preUrlCfg : 上一条URL
		 * preUrlCfg : 下一条URL
		 */
		getUrls : function(){
			var urls = {};
			var _this = exports.WinEdit; 
			var parent = exports.WinEdit.parent;
			var formId = parent.formId;
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{formId:formId},defaultVal:{formId:formId,custType:_this.custType,customerId:_this.customerId}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fcLoanContract_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				cfg = {params : {formId:formId,custType:_this.custType,customerId:_this.customerId},sfn:function(json_data){
					var payDay = Ext.getCmp(_this.payDayId);
					if(payDay){
						var setdayType = json_data.setdayType;
						if(setdayType){
							if(setdayType==1 || setdayType ==2){
								payDay.setDisabled(true);
							}
						}
					}
					_this.appFrm.setVs(json_data);
					var doDate = _this.appFrm.findFieldByName('doDate');
					doDate.enable();
				}};
				urls[URLCFG_KEYS.GETURLCFG] = {url :'./fcLoanContract_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './fcLoanContract_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fcLoanContract_next.action',cfg :cfg};
			this.apptbar.hideButtons(Btn_Cfgs.PREVIOUS_BTN_TXT+","+Btn_Cfgs.NEXT_BTN_TXT);
			return urls;
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var _this = exports.WinEdit;
			if(_this.parentCfg.self.refresh)_this.parentCfg.self.refresh(_this.optionType,data);	
		},
		/**
		 * 创建Form表单
		 */
		createForm : function(){
			var _this = this;
				var hid_custType = FormUtil.getHidField({
				    fieldLabel: '客户类型',
				    name: 'custType',
				    "width": 135
				});
				var hid_customerId = FormUtil.getHidField({
				    fieldLabel: 'customerId',
				    name: 'customerId',
				    "width": 135
				});
				var hid_formId = FormUtil.getHidField({
				    fieldLabel: '客户Id',
				    name: 'formId',
				    "width": 135
				});
				var txt_id = FormUtil.getTxtField({
				    fieldLabel: '借款合同ID',
				    name: 'id',
				    hidden : true,
				    "width": 135
				});
				var hid_flexDatas = FormUtil.getHidField({
				    fieldLabel: '灵活还款数据',
				    name: 'flexDatas'
				});
				
				var txt_code = FormUtil.getTxtField({
				    fieldLabel: '合同编号',
				    name: 'code',
				    "width": 135,
				    "maxLength": "20"
				});
				
				var txt_borBank = FormUtil.getTxtField({
				    fieldLabel: '借款人银行',
				    name: 'borBank',
				    "width": 135,
				    "allowBlank": false,
				    "maxLength": "50"
				});
				
				var txt_borAccount = FormUtil.getTxtField({
				    fieldLabel: '借款人帐号',
				    name: 'borAccount',
				    "width": 135,
				    "allowBlank": false,
				    "maxLength": "50"
				});
				
				var txt_payBank = FormUtil.getTxtField({
				    fieldLabel: '还款银行',
				    name: 'payBank',
				    "width": 135,
				    "maxLength": "50"
				});
				
				var txt_payAccount = FormUtil.getTxtField({
				    fieldLabel: '还款帐号',
				    name: 'payAccount',
				    "width": 135,
				    "maxLength": "50"
				});
				
				var txt_accName = FormUtil.getTxtField({
				    fieldLabel: '帐户户名',
				    name: 'accName',
				    "width": 135,
				    "allowBlank": false,
				    "maxLength": "50"
				});
				
				var setdayTypeChangeListener = function(combox,newValue,oldValue){
					var disabled = false;
					int_payDay.reset();
					if(newValue == "1"){
						disabled = true;
						int_payDay.setDisabled(disabled);
					} /*以实际放款日作为结算日时，禁用*/
					else if(newValue == "2"){
						EventManager.get('./sysSysparams_getParamsName.action',{params:{recode:'PAYDAY_SET',sysid : _this.sysid},
							sfn:function(jsondate){
								if(jsondate){
									var val = jsondate.val;
									if(val){
										int_payDay.setValue(val);
									}else {
										int_payDay.setDisabled(false)
									}
								}else{
									return;
								}
						}});
						disabled = true;
					}
					
					int_payDay.setDisabled(disabled);
				}
					
				var cbo_setdayType = FormUtil.getLCboField({
					fieldLabel : '结算日',
					name : 'setdayType',
					"allowBlank": false,
					width : 140,
					maxLength : 50,
					data : Lcbo_dataSource.setdayType_datas,
					listeners : {
						'change' : setdayTypeChangeListener
					}
				});
				
				var int_payDay = FormUtil.getIntegerField({
				    fieldLabel: '结算日',
				    id : _this.payDayId,
				    name: 'payDay',
				    "width": 25,
				    "allowBlank": false,
				    "maxLength": 10
				});
				var compt_payDay = FormUtil.getMyCompositeField({
					fieldLabel : '结算日',
					sigins : null,
					itemNames : 'setdayType,payDay',
					name : 'compt_payDay',
					"allowBlank": false,
					width : 220,
					items : [cbo_setdayType,int_payDay, {
								xtype : 'displayfield',
								html : '号'
							}]
				});
				/*根据放款日期算出贷款截止日期*/
				var payDateChangeListener = function(){
					var year = int_yearLoan.getValue();//贷款期限年
					var month = int_monthLoan.getValue();//贷款期限月
					var day = int_dayLoan.getValue();//贷款期限日
					var payDate = bdat_payDate.getValue();
					int_endDate.reset();
					if(payDate || payDate !=""){
						bdat_doDate.reset();
						bdat_doDate.enable();
						if(year){
							payDate.setFullYear(payDate.getFullYear()+year);
						}
						if(month){
							payDate.setMonth(payDate.getMonth()+month);
						}
						payDate.setDate(payDate.getDate()+day-1);
						payDate =  new Date(payDate).format("Y-m-d"); ;
						int_endDate.setValue(payDate);
					}else{
						bdat_doDate.reset();
						bdat_doDate.disable();
					}
					
				}
				
				var bdat_payDate = FormUtil.getDateField({
				    fieldLabel: '合约放款日期',
				    name: 'payDate',
				    "width": 135,
				    "allowBlank": false,
				    listeners : {
						'change' : payDateChangeListener
					}
				});
				
				var int_endDate = FormUtil.getDateField({
				    fieldLabel: '贷款截止日期',
				    name: 'endDate',
				    "width": 135,
				    allowBlank: false,
				    "maxLength": 16
				});
				
				var txt_appAmount = FormUtil.getMoneyField({
						fieldLabel : '贷款金额',
						name : 'appAmount',
						width : 180,
						allowBlank : false,
						value : 0,
						autoBigAmount : true,
						unitText : '此处将显示大写金额',
						unitStyle : 'margin-left:4px;color:red;font-weight:bold'
					});
				
				var int_yearLoan = FormUtil.getIntegerField({
				    fieldLabel: '贷款期限(年)',
				    name: 'yearLoan',
				    width: 30,
				    listeners : {
						'change' : payDateChangeListener
					}
				});
				
				var int_monthLoan = FormUtil.getIntegerField({
				    fieldLabel: '贷款期限(月)',
				    name: 'monthLoan',
				     width: 30,
				     listeners : {
						'change' : payDateChangeListener
					}
				});
				
				var int_dayLoan = FormUtil.getIntegerField({
				    fieldLabel: '贷款期限(日)',
				    name: 'dayLoan',
				    width: 30,
				    listeners : {
						'change' : payDateChangeListener
					}
				});
				
				var comp_loanLimit = FormUtil.getMyCompositeField({
					 fieldLabel: '贷款期限',name:'limitLoan',width:200,sigins:null,itemNames :'yearLoan,monthLoan,dayLoan',
					 items : [int_yearLoan,
					 	{xtype : 'displayfield',value : '年',width : 6},
					 	int_monthLoan,
					 	{xtype : 'displayfield',value : '月',width : 6},
					 	int_dayLoan,
					 	{xtype : 'displayfield',value : '日',width : 6}
					 	]
				});
		
				var cbo_rateType = FormUtil.getLCboField({
						fieldLabel : '利率类型',
						name : 'rateType',
						data:[["1","月利率"],["2","日利率"],["3","年利率"]],allowBlank : false,width:65});
						
				var dob_rate = FormUtil.getDoubleField({
					fieldLabel : '贷款利率',
					name : 'rate',
					"width" : 60,
					"allowBlank" : false,
					"value" : 0,
					decimalPrecision:4
				});
				var cbo_unint = FormUtil.getUnitLCbox('贷款利率单位','unint');
				var compt_rateLimit = FormUtil.getMyCompositeField({
				 fieldLabel: '贷款利率',name:'compt_rateLimit',width:220,sigins:null,allowBlank : false,itemNames : 'rateType,rate,unint',
				 items : [cbo_rateType,dob_rate,cbo_unint]
				});
				
		
				var rad_isadvance = FormUtil.getRadioGroup({
						fieldLabel : '是否提前收息',
						name : 'isadvance',
						"width" : 135,
						"allowBlank" : false,
						disabled  : true,
						"maxLength" : 10,
						"items" : [{
									"boxLabel" : "否",
									"name" : "isadvance",
									"inputValue" : 0,
									checked : true
								}, {
									"boxLabel" : "是",
									"name" : "isadvance",
									"inputValue" : 1
								}]
					});
				
			/*----------- 管理费率信息设置 ------------*/
			var hidenMgrRate = SYSPARAMS_DATA["ENABLE_MGRAMOUNT"];//根据系统参数来确定是否启用管理费选项[1:显示,2:禁用]
			hidenMgrRate = (hidenMgrRate && hidenMgrRate == 2); 
			var cbo_mgrtype = FormUtil.getLCboField({
				fieldLabel : '管理费收取方式',
				name : 'mgrtype',
				width : 135,
				"allowBlank" : false,
				"maxLength" : 10,
				hidden : hidenMgrRate,
				data:[["0","不收管理费"],["1","按还息方式收取"]],
				value : (hidenMgrRate?"0":"1"),
				listeners : {
					change : function(comBox,newValue,oldValue ){
						var disabled = false;
						if(newValue==0){
							dob_mrate.setValue(0);
							disabled = true;
						}else{
							disabled = false;
						}
						dob_mrate.setDisabled(disabled);
					}
				}
			});
			var dob_mrate = FormUtil.getDoubleField({
				fieldLabel : '管理费率',
				name : 'mrate',
				"width" : 60,
				"allowBlank" : false,
				"value" : "0",
				decimalPrecision : 4
			});
			var cbo_munint = FormUtil.getUnitLCbox('管理费率单位','munint');
			
			var compt_mrate = FormUtil.getMyCompositeField({
				fieldLabel : '管理费率',
				sigins : null,
				"allowBlank" : false,
				itemNames : 'mrate,munint',
				name : 'compt_mrate',
				width : 125,
				hidden : hidenMgrRate,
				items : [dob_mrate, cbo_munint]
			});
			
			/*------ 放款手续费率配置  ------*/
			var dob_prate = FormUtil.getDoubleField({
				fieldLabel : '放款手续费率',
				name : 'prate',
				"width" : 60,
				"allowBlank" : false,
				"value" : "0",
				decimalPrecision : 4
			});
			var cbo_punint = FormUtil.getUnitLCbox('放款手续费率单位','punint');
			
			var hidenPRate = SYSPARAMS_DATA["ENABLE_PAY_FREEAMOUNT"];//根据系统参数来确定是否启用放款手续费率选项[1:显示,2:禁用]
			hidenPRate = (hidenPRate && hidenPRate == 2); 
			var compt_prate = FormUtil.getMyCompositeField({
				fieldLabel : '放款手续费率',
				sigins : null,
				"allowBlank" : false,
				itemNames : 'prate,punint',
				name : 'compt_prate',
				width : 125,
				hidden : hidenPRate,
				items : [dob_prate, cbo_punint]
			});
			
					
			var dob_arate = FormUtil.getDoubleField({
				fieldLabel : '提前还款费率',
				name : 'arate',
				"width" : 60,
				"allowBlank" : false,
				"value" : "0",
				decimalPrecision : 4
			});
			var cbo_aunint = FormUtil.getUnitLCbox('提前还款费率单位','aunint');
			
			var hidenArate = SYSPARAMS_DATA["ENABLE_BEFORE_AMOUNT"];//根据系统参数来确定是否启用提前还款费率选项[1:显示,2:禁用]
			hidenArate = (hidenArate && hidenArate == 2); 
			var compt_arate = FormUtil.getMyCompositeField({
				fieldLabel : '提前还款费率',
				sigins : null,
				"allowBlank" : false,
				itemNames : 'arate,aunint',
				name : 'compt_arate',
				width : 125,
				hidden : hidenArate,
				items : [dob_arate, cbo_aunint]
			});
			
			/*---- 罚息利率相关 ---*/
				var cbo_utype = FormUtil.getRateTypeLCbox("罚息利率类型","utype");
				var dob_urate = FormUtil.getDoubleField({
					fieldLabel : '罚息利率',name : 'urate',"width" : 50,"allowBlank" : false,"value" : "0",
					decimalPrecision : 4
				});
				var cbo_uunint = FormUtil.getUnitLCbox('罚息费率单位','uunint');
				var compt_urate = FormUtil.getMyCompositeField({
					fieldLabel : '罚息利率',
					sigins : null,
					"allowBlank" : false,
					itemNames : 'utype,urate,uunint',
					name : 'compt_urate',
					width : 160,
					items : [cbo_utype, dob_urate, cbo_uunint]
				});
				
			/*---- 滞纳金利率相关 ---*/
			var cbo_ftype = FormUtil.getRateTypeLCbox("滞纳金利率类型","ftype");
			var dob_frate = FormUtil.getDoubleField({
						fieldLabel : '滞纳金利率',name : 'frate',"width" : 50,"allowBlank" : false,
						"value" : "0","decimalPrecision" : 4
					});
			var cbo_funint = FormUtil.getUnitLCbox('滞纳金费率单位','funint');
			var compt_frate = FormUtil.getMyCompositeField({
				fieldLabel : '滞纳金利率',
				sigins : null,
				"allowBlank" : false,
				hidden : hidenMgrRate,
				itemNames : 'ftype,frate,funint',
				name : 'compt_urate',
				width : 160,
				items : [cbo_ftype, dob_frate, cbo_funint]
			});
			
			var bdat_doDate = FormUtil.getDateField({
			    fieldLabel: '合同签订日期',
			    name: 'doDate',
			    "width": 135
			});
			
			/*---- 还款方式相关 ---*/
			var txt_phAmount = FormUtil.getMoneyField({
				fieldLabel : '每期还本金额',
				name : 'phAmount',
				width : 80,
				value : 0
			});
			
			var disp_leftSigins = new Ext.form.DisplayField({html : '<span style="color:red;">(</span>'});
			
			var disp_rightSigins = new Ext.form.DisplayField({width : 100,html : '<span style="color:red;">分期还本金额)</span>'});
			var btnAddFlex = new Ext.Button({text:"添加灵活还款本金",handler:function(){
				_this.openFlexPlanDialogbox(btnAddFlex.el);
			}});
			
			var cbo_payType = FormUtil.getRCboField({
				fieldLabel : '还款方式',
				name : 'payType',
				width : 160,
				maxLength : 50,
				url: "./fcPayType_cbodatas.action",
				listeners : {change : function(field,newVal,oldVal){
					oldFlexDatas = hid_flexDatas.getValue();
					var flag1 = false;
					var flag2 = false;
					switch(newVal){
						case 'P0008': {/*灵活还本*/
							flag2 = true;
							ExtUtil.alert({msg:'您选择的还款方式:"灵活还本",请点击后面的"添加灵活还款本金"按钮，输入每月还款本金信息!'});
							break;
						}case 'P0002':/*按月付息，分期还本*/
						case 'P0004':{/*按月付息，分季还本*/
							flag1 = true;
							break;
						}
					}
					txt_phAmount.setVisible(flag1);
					disp_leftSigins.setVisible(flag1);
					disp_rightSigins.setVisible(flag1);
					btnAddFlex.setVisible(flag2);
				}}
			});
		
		 var compt_payType = FormUtil.getMyCompositeField({
				fieldLabel : '还款方式',
				allowBlank : false,
				sigins : null,
				itemNames : 'payType,phAmount',
				name : 'compt_payType',
				width : 550,
				items : [cbo_payType,disp_leftSigins, txt_phAmount, disp_rightSigins,btnAddFlex]
			});
			
				var txt_clause = FormUtil.getTAreaField({
				    fieldLabel: '合同中未涉及条款',
				    name: 'clause',
				    height : 50,
				    "width": 730
				});
				var formDiyContainer = new Ext.Container({layout:'fit'});
				var layout_fields = [
					txt_id,hid_formId,hid_custType,hid_customerId,hid_flexDatas,
					{cmns: FormUtil.CMN_THREE,fields: [ txt_code, txt_borBank, txt_borAccount, txt_payBank, txt_payAccount, txt_accName]}, 
					{cmns: FormUtil.CMN_TWO_LEFT,fields: [txt_appAmount,comp_loanLimit]},
					{cmns: FormUtil.CMN_THREE,fields: [compt_payDay,bdat_payDate, int_endDate,compt_rateLimit,compt_urate,bdat_doDate,/*rad_isadvance,*/cbo_mgrtype,compt_mrate,compt_prate,compt_frate,compt_arate]},
					compt_payType,
					formDiyContainer,txt_clause
				];
				
				var frm_cfg = {
				    title: '借款合同信息编辑'+'<span style="color:red">（提示：如果合同编号不输入，系统会自动生成合同编号；否则，就以用户输入的合同编号为准）</span>',
				    labelWidth: 110,
				    autoHeight : true,
				    url: './fcLoanContract_save.action',
				     params :{custType : _this.custType,customerId : _this.customerId},	
				    formDiyCfg : {
				    	sysId : _this.sysid,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_LOAN,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id',/*对应表单中的ID Hidden 值*/
				    	container : formDiyContainer /*自定义字段存放容器*/
			    	}
				};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			txt_appAmount.setWidth(250);
			return appform_1;
		},
		openFlexPlanDialogbox : function(ele){
			var _this = this;
			var data = this.appFrm.getValuesByNames("id,payDate,endDate,isadvance,appAmount");
			var payDateVal = data.payDate;
			var endDateVal = data.endDate;
			if(!payDateVal){
				ExtUtil.alert({msg:'请输入合约放款日!'});
				return;
			}
			if(!endDateVal){
				ExtUtil.alert({msg:'请输入贷款截止日!'});
				return;
			}
			var contractId = data.id;
			var hid_flexDatas = _this.appFrm.findFieldByName("flexDatas");
			var batchFlexDatas = hid_flexDatas.getValue();/*获取灵活还款数据*/
			if(batchFlexDatas){
				open();
			}else{
				Ext.Msg.prompt('结息日','请输入每月结息日',function(btn,text){
					if(btn != 'ok') return;
					if(!text){
						ExtUtil.alert({msg:'请输入每月结息日!'});
						return;
					}
					if(Ext.isNumber(text)){
						ExtUtil.alert({msg:'每月结息日必须是整数数字格式!'});
						return;
					}
					open(text);
				});
			}
			
			function open(payDay){
				var _pars = {contractId:contractId,payDay:payDay};
				Ext.apply(_pars,data);
				var parentCfg = {params:_pars,parent:ele,batchFlexDatas:batchFlexDatas,callback:function(dataArr){
					var flexdataVal = "";
					if(dataArr && dataArr.length > 0){
						flexdataVal = Ext.encode(dataArr);
					}
					hid_flexDatas.setValue(flexdataVal);
				}};
				if(!_this.dialogbox){
					Cmw.importPackage('pages/app/dialogbox/FlexPlanDialogbox',function(module) {
						_this.dialogbox = module.DialogBox;
						_this.dialogbox.show(parentCfg);
			  		});
				}else{
					_this.dialogbox.show(parentCfg);
				}
				
			}
		},
		/**
		 * 为表单元素赋值
		 */
		setFormValues : function(){
		
			//if(cfg && cfg.defaultVal) self.appFrm.setFieldValues(cfg.defaultVal);
		},
		/**
		 * 上一条
		 */
		preData : function(){
			
		},
		/**
		 * 下一条
		 */
		nextData : function(){
			
		},
		/**
		 * 保存数据
		 */
		saveData : function(){
			var _this = exports.WinEdit;
			var frmVal = _this.appFrm.getValues();
			var appAmount = frmVal.appAmount;
			if(appAmount<=0){
				ExtUtil.alert({msg:"输入的贷款金额必须大于0！"});
				return;
			}
			var payDay = frmVal.payDay;
			var setdayType = frmVal.setdayType;
			if(setdayType==3 || setdayType ==2){
				if(!payDay || payDay==""|| payDay==null ){
					ExtUtil.alert({msg:"当结算日期<span style='color : red'>为其他结算日</span>或者<br/>" +
							"<span style='color : red'>为公司规定结算日</span>的时候，<br/><span style='color : red'>每期结算日不能为空!</span>"});
					return ;
				}
			}
//			var doDate = frmVal.doDate;
//			var payDate = frmVal.payDate;
//			var newDate = new Date().format('Y-m-d');
//			if(doDate<newDate || payDate<newDate ){
//				ExtUtil.alert({msg:"<span style='color : red'>合约放款日或者<br/>合同签订日期<br/>不能早于今天!</span>"});
//				return ;
//			}
			EventManager.frm_save(_this.appFrm,{beforeMake : function(formData){
					formData.payDaySet = "";
					if(formData.setdayType==2){
						if(!formData.payDay || formData.payDay==""|| formData.payDay==null ){
							formData.payDaySet = "PAYDAY_SET";//如果结算日期为空，则取系统默认结算日参数
						}
					}
				},sfn:function(data){
					if(data == -1){
						ExtUtil.alert({msg:"借款合同编号必须唯一！"});
						return;
					}
				_this.appWin.hide();
				_this.refresh(data);
			 }});
			
		},
		/**
		 *  重置数据 
		 *  
		 */
		resetData : function(){
		}
	};
});
