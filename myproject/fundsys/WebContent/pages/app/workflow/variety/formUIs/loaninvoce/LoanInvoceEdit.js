/**
 * #DESCRIPTION#
 * @author smartplatform_auto
 * @date 2013-01-17 03:53:48
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		unAmount : null,
		payDate : null,
		sysId : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.sysId = parentCfg.parent.sysId;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:780,getUrls:this.getUrls,appFrm : this.appFrm,isNotSetVs:true,
			optionType : this.optionType, refresh : this.refresh,hidden : true,eventMgr:{saveData:this.saveData}
			});
		},
		/**
		 * 显示弹出窗口
		 * @param _parentCfg 弹出之前传入的参数
		 */
		show : function(_parentCfg){
			if(_parentCfg) this.setParentCfg(_parentCfg);
			if(!this.appWin){
				this.createAppWindow();
			}
			this.appWin.optionType = this.optionType;
			this.appWin.show(this.parent.AddBtn.getEl());
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
			var parent = exports.WinEdit.parent;
			var appWin = exports.WinEdit.appWin;
			var formId = parent.formId;
			var appgrid = parent.appgrid;
			var cfg = null;
			var _this = this;
			// 1 : 新增 , 2:修改
			if(_this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{formId:formId},defaultVal:{formId:formId},sfn:function(json_data){
					if(json_data==-1){
						ExtUtil.alert({msg:"请添加借款合同！",fn:function(){
								appWin.hide();
							}});
						return;
					}else{
						exports.WinEdit.unAmount = json_data.unAmount;
						exports.WinEdit.payDate = json_data.payDate;
						if(json_data.payAmount==0.00){
							ExtUtil.alert({msg:"放款已经完成，不能进行再次放款！",fn:function(){
								appWin.hide();
							}});
							return;
						}
					}
					appWin.show();
				}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fcLoanInvoce_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var  selId = appgrid.getSelId();
				 cfg = {params : {id:selId},sfn : function(json_data){
				 exports.WinEdit.unAmount = json_data.unAmount+json_data.payAmount;}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './fcLoanInvoce_get.action',cfg : cfg};
			}
			var id = _this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './fcLoanInvoce_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fcLoanInvoce_next.action',cfg :cfg};
			_this.apptbar.hideButtons(Btn_Cfgs.PREVIOUS_BTN_TXT+","+Btn_Cfgs.NEXT_BTN_TXT);
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
			var txt_id = FormUtil.getHidField({
			    fieldLabel: '放款单id',
			    name: 'id',
			    "width": 135
			});
			
			var txt_formId = FormUtil.getHidField({
			    fieldLabel: '贷款申请单ID',
			    name: 'formId',
			    "width": 135
			});
			
			var txt_contractId = FormUtil.getHidField({
			    fieldLabel: '借款合同ID',
			    name: 'contractId',
			    "width": 135,
			    "allowBlank": false,
			    "maxLength": 50
			});
			var txt_loancode = FormUtil.getReadTxtField({
			    fieldLabel: '借款合同编号',
			    name: 'loancode',
			    "width": 135,
			    "allowBlank": false,
			    "maxLength": 50
			});
			
			var txt_code = FormUtil.getReadTxtField({
			    fieldLabel: '通知书编号',
			    name: 'code',
			    "width": 135,
			    "allowBlank": false,
			    "maxLength": 50
			});
			
			var txt_appAmount = FormUtil.getMoneyField({
				fieldLabel : '贷款金额',
				name : 'appAmount',
				width : 220,
				readOnly:true,
				allowBlank : false,
				value : 0,
				autoBigAmount : true,
				unitText : '此处将显示大写金额',
				unitStyle : 'margin-left:4px;color:red;font-weight:bold'
			});
			var int_yearLoan = FormUtil.getReadTxtField({
				    fieldLabel: '贷款期限(年)',
				    name: 'yearLoan',
				    width: 30
				});
				
				var int_monthLoan = FormUtil.getReadTxtField({
				    fieldLabel: '贷款期限(月)',
				    name: 'monthLoan',
				     width: 30
				});
				
				var int_dayLoan = FormUtil.getReadTxtField({
				    fieldLabel: '贷款期限(日)',
				    name: 'dayLoan',
				    width: 30
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
				
			var dob_rate = FormUtil.getDoubleField({
				fieldLabel : '贷款利率',
				name : 'rate',
				"width" : 135,
				readOnly:true,
				"allowBlank" : false,
				"value" : "0",
				"decimalPrecision" : "2",
				unitText : '%'
			});
			var dob_mrate = FormUtil.getDoubleField({
						fieldLabel : '管理费率',
						readOnly:true,
						name : 'mrate',
						"width" : 135,
						"allowBlank" : false,
						"value" : "0",
						"decimalPrecision" : "2",
						unitText : '%'
					});
			var txt_payName = FormUtil.getTxtField({
			    fieldLabel: '收款人名称',
			    name: 'payName',
			      maxLength:50,
			    allowBlank : false,
			    "width": 135
			});
			
			var txt_regBank = FormUtil.getTxtField({
			    fieldLabel: '开户行',
			    name: 'regBank',
			    maxLength:50,
			    "width": 135,
			    "allowBlank": false,
			    "maxLength": 50
			});
			
			var txt_account = FormUtil.getTxtField({
			    fieldLabel: '收款帐号',
			    name: 'account',
			    "width": 135,
			      maxLength:30,
			    "allowBlank": false,
			    "maxLength": 50
			});
			
			var bdat_payDate = FormUtil.getDateField({
			    fieldLabel: '合约放款日期',
			    name: 'payDate',
			    "width": 135,
			    "allowBlank": false,
			    "maxLength": 50
			});

			
			var txt_unAmount = FormUtil.getMoneyField({
			    fieldLabel: '未放款金额',
			    name: 'unAmount',
			    "width": 135,
			     readOnly:true,
			    "allowBlank": false,
			    "maxLength": 50
			});
			var dob_payAmount = FormUtil.getMoneyField({
			    fieldLabel: '本次放款金额',
			    name: 'payAmount',
			    readOnly:true,
			    allowBlank : false,
			    "width": 135,
			    listeners:{'change':function(){
			    	_this.setFormValues();
			    }}
			});
				/*出纳人*/
			var barItems = [{type:'label',text:'姓名'},{type:'txt',name:'empName'}];
			var structure = [
				{header: '姓名', name: 'empName',width:100},{header: '性别',name: 'sex',width:60,renderer: function(val) {
					return Render_dataSource.sexRender(val);
				}},{header: '手机', name: 'phone',width:80},{header: '联系电话', name: 'tel',width:90}];
					
			
			var dob_prate = FormUtil.getDoubleField({
			    fieldLabel: '放款手续费率',
			    name: 'prate',
			    readOnly:true,
			    allowBlank : false,
			    "width": 135,
			    unitText : '%'
			});
			
			var dob_fee= FormUtil.getMoneyField({
			    fieldLabel: '放款手续费',
			    name: 'fee',
			    readOnly:true,
			    allowBlank : false,
			    "width": 135
			});
			
			var txt_cashier = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '出纳人员',
			    name: 'cashier',
			    barItems : barItems,
			    structure:structure,
			    dispCmn:'empName',
			    isAll:false,
			    allowBlank : false,
			    url : './sysUser_cashierlist.action',
			    isLoad:true,
			    params : {isLoan : 1},
			    needPage : true,
			    keyField : 'userId',
			    width: 135
			});
			var formDiyContainer = new Ext.Container({layout:'fit'});
			var txt_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    height : 50,
			    width : 520
			});
					
			
			var layout_fields = [txt_id, txt_formId, txt_contractId,{
			    cmns: FormUtil.CMN_TWO,
			    fields: [ txt_loancode,txt_code,
			    		txt_appAmount,comp_loanLimit,dob_rate,dob_mrate,txt_unAmount,dob_payAmount,dob_prate,dob_fee,txt_payName,	txt_regBank,txt_account,bdat_payDate,
			    		txt_cashier
		    			]
			},formDiyContainer,txt_remark];
			var frm_cfg = {
				labelWidth: 110,
			    url: './fcLoanInvoce_save.action',
			    formDiyCfg : {
				    	sysId : _this.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_LOANINVOCE,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id',/*对应表单中的ID Hidden 值*/
				    	container : formDiyContainer /*自定义字段存放容器*/
			    	}
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform_1;
		},
		/**
		 * 为表单元素赋值
		 */
		setFormValues : function(){
			var _this = this;
			var payAmount = this.appFrm.getValueByName("payAmount");
			var unAmount = this.appFrm.findFieldByName("unAmount");
			var fields = this.appFrm.findFieldsByName("payAmount,fee");
//	    	var  unAmount =parseFloat( this.appFrm.getValueByName("unAmount"));
	    	if((payAmount<0)||(payAmount>_this.unAmount)){
	    		ExtUtil.confirm({msg:"对不起，您输入放款金额可能是负数或者超过了未放款金额，请重新输入！",fn:function(){
    			unAmount.reset();
    			unAmount.setValue(_this.unAmount)
	    		fields[0].reset();
	    		fields[1].reset();
	    		}});
	    		return;
	    	}else{
	    		var prate = _this.appFrm.getValueByName("prate");
	    		unAmount.reset();
	    		unAmount.setValue(_this.unAmount-payAmount);
	    		fields[1].reset();
	    		fields[1].setValue(payAmount*prate*0.01);
	    	}
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
			var payDate = frmVal.payDate;
			EventManager.frm_save(_this.appFrm,{sfn:function(data){
				_this.appWin.hide();
				_this.refresh(data);
			 },ffn:function(data){
			 	ExtUtil.alert({msg:"保存数据失败！"});
			 }});
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		}
	};
});
