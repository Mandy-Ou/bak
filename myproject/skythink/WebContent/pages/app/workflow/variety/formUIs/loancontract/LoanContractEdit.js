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
		params : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		custType : null,
		customerId : null,
		sysid : null,
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
				var hid_formId = FormUtil.getHidField({
				    fieldLabel: '贷款申请单ID',
				    name: 'formId',
				    hidden : false,
				    "width": 135,
				    "allowBlank": false
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
				
				var int_endDate = FormUtil.getReadTxtField({
				    fieldLabel: '贷款截止日期',
				    name: 'endDate',
				    "width": 135,
				    "value": "0",
				    "maxLength": 10
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
						data:[["1","月利率"],["2","日利率"],["3","年利率"]],allowBlank : false,width:70});
						
					var dob_rate = FormUtil.getDoubleField({
						fieldLabel : '贷款利率',
						name : 'rate',
						"width" : 60,
						"allowBlank" : false,
						"value" : 0,
						unitText : '%',
						"decimalPrecision" : 2
					});
				var compt_rateLimit = FormUtil.getMyCompositeField({
				 fieldLabel: '贷款利率',name:'compt_rateLimit',width:200,sigins:null,allowBlank : false,itemNames : 'rateType,rate',
				 items : [cbo_rateType,dob_rate,{xtype : 'displayfield',value : '%',width : 6}]
				});
				var cbo_payType = FormUtil.getRCboField({
					fieldLabel : '还款方式',
					name : 'payType',
					width : 160,
					maxLength : 50,
					url: "./fcPayType_cbodatas.action"
				});
		
				var txt_phAmount = FormUtil.getMoneyField({
						fieldLabel : '每期还本金额',
						name : 'phAmount',
						width : 80,
						value : 0
					});
				var compt_payType = FormUtil.getMyCompositeField({
					fieldLabel : '还款方式',
					sigins : null,
					itemNames : 'payType,phAmount',
					name : 'compt_payType',
					width : 550,
					items : [cbo_payType, {
								xtype : 'displayfield',
								html : '<span style="color:red;">(</span>'
							}, txt_phAmount, {
								xtype : 'displayfield',
								width : 200,
								html : '<span style="color:red;">分期还本金额)</span>'
							}]
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
				
				
				var cbo_mgrtype = FormUtil.getLCboField({
					fieldLabel : '管理费收取方式',
					name : 'mgrtype',
					width : 135,
					"allowBlank" : false,
					"maxLength" : 10,
					data:[["0","不收管理费"],["1","按还息方式收取"]],
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
						"width" : 135,
						"allowBlank" : false,
						"value" : "0",
						"decimalPrecision" : "2",
						unitText : '%'
					});
				var dob_prate = FormUtil.getDoubleField({
					fieldLabel : '放款手续费率',
					name : 'prate',
					disabled  : true,
					"width" : 135,
					"allowBlank" : false,
					hidden : true,
					"value" : "0",
					"decimalPrecision" : "2",
					unitText : '%'
				});

			var dob_arate = FormUtil.getDoubleField({
						fieldLabel : '提前还款费率',
						hidden : true,
						name : 'arate',
						"width" : 135,
						"allowBlank" : false,
						"value" : "0",
						"decimalPrecision" : "2",
						unitText : '%'
					});

			var dob_urate = FormUtil.getDoubleField({
						fieldLabel : '罚息利率',
						name : 'urate',
						"width" : 135,
						"allowBlank" : false,
						"value" : "0",
						"decimalPrecision" : "2",
						unitText : '%'
					});

			var dob_frate = FormUtil.getDoubleField({
						fieldLabel : '滞纳金利率',
						name : 'frate',
						"width" : 135,
						"allowBlank" : false,
						"value" : "0",
						"decimalPrecision" : "2",
						unitText : '%'
					});
				var bdat_doDate = FormUtil.getDateField({
				    fieldLabel: '合同签订日期',
				    name: 'doDate',
				     "allowBlank": false,
				     disabled : true,
				    "width": 135,
				    listeners : {
				    	select : function(bdat,newValue){
				    		var payDate = bdat_payDate.getValue();
				    		if(!payDate){
				    			bdat.reset(); 
				    			ExtUtil.alert({msg:"请先选择合约放款日期！"});
				    			return;
				    		}else{
				    			payDate = Date.parse(payDate);
				    			newValue = Date.parse(newValue);
//				    			if(payDate < newValue){
//				    				bdat.reset(); 
//				    				ExtUtil.alert({msg:"合约放款日期不能小于合同签订日期！"});
//				    				return;
//				    			}
				    		}
				    	}
				    }
				});
				var txt_clause = FormUtil.getTAreaField({
				    fieldLabel: '合同中未涉及条款',
				    name: 'clause',
				    height : 50,
				    "width": 730
				});
				var formDiyContainer = new Ext.Container({layout:'fit'});
				var layout_fields = [
					txt_id,hid_formId,hid_custType,hid_customerId,dob_arate,
					{cmns: FormUtil.CMN_THREE,fields: [ txt_code, txt_borBank, txt_borAccount, txt_payBank, txt_payAccount, txt_accName]}, 
					{cmns: FormUtil.CMN_TWO_LEFT,fields: [txt_appAmount,comp_loanLimit,compt_payType,compt_rateLimit]},
					{cmns: FormUtil.CMN_THREE,fields: [compt_payDay,bdat_payDate, int_endDate,/*rad_isadvance,*/cbo_mgrtype,dob_mrate,/*dob_prate ,*/dob_urate,dob_frate,bdat_doDate]},
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
