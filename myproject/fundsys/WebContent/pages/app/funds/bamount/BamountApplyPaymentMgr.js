/*同心日科技公司Erp 系统 命名空间*/
Ext.namespace("skythink.cmw.workflow.bussforms");
/**
 * 委托合同
 */
skythink.cmw.workflow.bussforms.EntrustcontractMgr = function(){
	this.init(arguments[0]);
}
/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.workflow.bussforms.EntrustcontractMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		/*由必做或选做业务菜单传入的回調函数，主要功能：
		 * finishBussCallback : 当业务表单保存后，更新必做或选做事项为已做,
		 * unFinishBussCallback : 当删除业务表单后，取消已做标识
		 * */
		
		var finishBussCallback = tab.finishBussCallback;
		var unFinishBussCallback = tab.unFinishBussCallback;
		this.module = new Cmw.app.widget.AbsPanelView({
			tab : tab,
			params : params,/*apptabtreewinId,tabId,sysid*/
			getAppCmpt : this.getAppCmpt,
			createDetailPnl: this.createDetailPnl,
			changeSize : this.changeSize,
			destroyCmpts : this.destroyCmpts,
			globalMgr : this.globalMgr,
			saveBamApply:this.saveBamApply,
			refresh : this.refresh,
			getButtonHml:this.getButtonHml,
			prefix : Ext.id(),
			createFormPanel:this.createFormPanel,
			addListenersToCustButtons:this.addListenersToCustButtons,
			finishBussCallback : finishBussCallback,
			unFinishBussCallback : unFinishBussCallback,
			btnIdObj : {
			btnChoseCust : Ext.id(null, 'btnChoseCust'),/* 客户选择 */
			btnTempSave : Ext.id(null, 'btnTempSave'),/* 暂存申请单 */
			btnSave : Ext.id(null, 'btnSave') /* 提交申请单 */
		}
		});
	},
	/**
	 * 获取组件方法
	 */
	getAppCmpt : function(){
		var _this=this;
		var applyId=this.params.applyId;
		EventManager.get('./fuBamountApply_get.action', {
			params : {id : applyId},
			sfn : function(json_data) {
				_this.globalMgr.customerPanel.reload({id:json_data.entrustContractId},false);
				_this.globalMgr.appFrm.setVs(json_data);
				_this.globalMgr.brId=json_data.accountId;
			}
		});
		_this.createDetailPnl();
		_this.globalMgr.appFrm=this.createFormPanel();
		var appPanel = new Ext.Panel({border : false,autoScroll:true});
		appPanel.add({items:[this.globalMgr.customerPanel,_this.globalMgr.appFrm]});
//		var params ={sysId:-1,formType:ATTACHMENT_FORMTYPE.FType_41,formId:_this.params.applyId}
//		_this.attachMentFs.reload(params);
		this.globalMgr.brId=null;
		return appPanel;
	},
	/**
	 * 刷新方法
	 * @param {} optionType
	 * @param {} data
	 */
	refresh:function(optionType,data){
//		var _this = this;
//		var activeKey = _this.globalMgr.activeKey;
//		if(activeKey =="添加委托合同"||activeKey == "编辑委托合同"){
//			if(activeKey =="添加委托合同"){
//				_this.globalMgr.show();
//				_this.globalMgr.AddBtn.disable();
//				_this.globalMgr.EditBtn.enable();
//			}
//			_this.globalMgr.id = data.id;
//			var params ={sysId:-1,formType:ATTACHMENT_FORMTYPE.FType_41,formId:_this.params.applyId}
//			_this.attachMentFs.reload(params);
//			_this.globalMgr.detailPanel_1.reload({id:data.id});
//			if(data.id) _this.finishBussCallback(data);
//		}
//		_this.globalMgr.activeKey = null;
	},
		/**
		 * 为HTML按钮绑定事件
		 * 
		 * @param btnCfgArr
		 *            按钮配置数组 [{btnId : this.btnIdObj.btnSaveId,fn :
		 *            function(e,targetEle,obj){}}]
		 */
		addListenersToCustButtons : function(btnCfgArr) {
			var _this = this;
			for (var i = 0, count = btnCfgArr.length; i < count; i++) {
				var btnCfg = btnCfgArr[i];
				var btnId = btnCfg.btnId;
				var btnEle = Ext.get(btnId);
				btnEle.addClassOnClick("nodeClickCls");
				btnEle.addClassOnOver("x-btn-text");
				btnEle.on('click', btnCfg.fn, this);
			}
		},
	/**
	 * 组件大小改变方法
	 * @param {} whArr
	 */
	changeSize : function(whArr){
		var width = CLIENTWIDTH - Ext.getCmp(ACCORDPNLID);
		var height = CLIENTHEIGHT - 180;
		this.appPanel.setWidth(width);
		this.appPanel.setHeight(height);
	},
	createFormPanel : function() {
			var _this = this;
			var hide_id = FormUtil.getHidField({
						name : 'id',
						fieldLabel : 'ID'
					});// 隐藏字段id
			var hide_entrustCustId = FormUtil.getHidField({
						name : 'entrustCustId',
						fieldLabel : '委托客户ID'
					});// 隐藏字段id
			var hide_entrustContractId = FormUtil.getHidField({
						name : 'entrustContractId',
						fieldLabel : '委托合同ID'
					});// 隐藏字段id
	
			var txt_procId = FormUtil.getHidField({
						fieldLabel : '流程实例ID',
						name : 'procId'
					});
			var txt_breed = FormUtil.getHidField({
						fieldLabel : '子业务流程ID',
						name : 'breed'
					});
			
			var txt_code = FormUtil.getReadTxtField({
				fieldLabel : '委托人姓名',
				name : 'name',
				"width" : "125"
			});
			var mon_appAmount = FormUtil.getMoneyField({
						fieldLabel : '委托金额',
						name : 'appAmount',
						"allowBlank" : false,
						"width" : 160,
						autoBigAmount : true,
						unitText : '大写金额',
						unitStyle : 'margin-left:2px;color:red;font-weight:bold'
					});
			mon_appAmount.disable();
			var mon_bamount = FormUtil.getMoneyField({
				fieldLabel : '撤资金额',
				name : 'bamount',
				"allowBlank" : false,
				"width" : 160,
				autoBigAmount : true,
				unitText : '大写金额',
				unitStyle : 'margin-left:2px;color:red;font-weight:bold',
				listeners : {
							'change' : payDateChangeListener
						}
			});
			var txt_prange = FormUtil.getRadioGroup({
						fieldLabel : '撤资类型',
						name : 'isNotExpiration',
						"allowBlank" : false,
						"width" : '150',
						"maxLength" : 50,
						"items" : [{
									"boxLabel" : "正常撤资",
									"name" : "isNotExpiration",
									"inputValue" : 1
									},{
									"boxLabel" : "异常撤资",
									"name" : "isNotExpiration",
									"inputValue" : 0
								}]
			});			
			var bdat_backDate = FormUtil.getDateField({
						fieldLabel : '撤资日期',
						name : 'backDate',
						"allowBlank" : false,
						"width" : 125,
						listeners : {
							'change' : payDateChangeListener
						}
					});
			
			var mon_wamount = FormUtil.getMoneyField({
				fieldLabel : '违约金额',
				name : 'wamount',
				"allowBlank" : false,
				"width" : 160,
				autoBigAmount : true,
				unitText : '大写金额',
				unitStyle : 'margin-left:2px;color:red;font-weight:bold',
				listeners : {
							'change' : payDateChangeListener
						}
			});
			/* 退还利息=撤资金额*委托利率/30*天数 */
			function payDateChangeListener() {
					var entrustContractId=hide_entrustCustId.getValue();//委托合同ID
					var wamount=mon_wamount.getValue();//违约金额
					var backDate=bdat_backDate.getValue();//撤资日期
					var bamount=mon_bamount.getValue(); //撤资金额-->表单元素名获取对应值 返回 code 文本框对象的值
				if(wamount){
					params = {
							entrustContractId:entrustContractId,
							backDate: Ext.util.Format.date(backDate,'Y-m-d'),
							bamount:bamount,
							wamount:wamount
						};
					EventManager.get("./fuBamountApply_biamount.action",{params:params,
						sfn:function(json_data){
					}});
				}
				
			}
			
			var mon_biamount = FormUtil.getMoneyField({
				fieldLabel : '退还预付利息',
				name : 'biamount',
				"allowBlank" : false,
				"width" : 160,
				autoBigAmount : true,
				unitStyle : 'margin-left:2px;color:red;font-weight:bold'
			});
			
			//3、实际支付金额=撤资金额-退还预付息金额-违约金额（自动计算）
			var mon_rpamount = FormUtil.getMoneyField({
				fieldLabel : '实际支付金额',
				name : 'rpamount',
				"allowBlank" : false,
				"width" : 160,
				autoBigAmount : true,
				unitStyle : 'margin-left:2px;color:red;font-weight:bold'
			});
			
			var callback = function(cboGrid,selVals){
				var grid = cboGrid.grid;
				var record = grid.getSelRow();
				var account = record.get("account");
				var uamount = record.get("uamount");
				if(parseFloat(_this.appAmount)>parseFloat(uamount)){
					ExtUtil.warn({msg:"选的放款账户余额已不足，请选择其他账号进行放款！"});
					return;
				}
				txt_bankAccount.setValue(account);
			}
			
			var cbog_accountId = ComboxControl.getAccountCboGrid({
				fieldLabel:'支付银行',name:'accountId',
				allowBlank : false,readOnly:true,
				isAll:false,width:130,callback:callback,
				params : {isPay:1,sysId : _this.sysId}});
				
			var cbo_settleType = FormUtil.getLCboField({
				fieldLabel : '付款方式',
				name:'settleType',
				data:[["1","银行转账"],["2","现金"]],
				value:1,
				allowBlank : false,
				width:125
			});
			
			var txt_bankAccount = FormUtil.getReadTxtField({
			    fieldLabel: '支付银行帐号',
			    name: 'bankAccount',
			    "width": 160
			});
			
			var bdat_realDate = FormUtil.getDateField({
			    fieldLabel: '财务付款日期',
			    name: 'rectDate',
			    "width": 125,
			    "allowBlank": false,
			    editable:false,
			    "maxLength": 50
			});
			
			
			/*----------- 基本合同信息设置 ------------*/
			var fset_1 = FormUtil.createLayoutFieldSet({
						title : '基本合同信息'/* ,height:800 */
					}, [{
						/**
						 * 	txt_code,mon_appAmount,mon_bamount,txt_prange,bdat_backDate,mon_wamount,
						 * 	mon_biamount,mon_rpamount,mon_paymentAmount,mon_accountId,mon_rectDate
						 */
							cmns : FormUtil.CMN_THREE,
							fields : [
								txt_code,mon_appAmount,mon_bamount,bdat_backDate,txt_prange,mon_wamount,
						 		mon_biamount,mon_rpamount,cbo_settleType,cbog_accountId,txt_bankAccount,bdat_realDate
							]
						},hide_id,hide_entrustCustId,hide_entrustContractId,txt_procId,txt_breed]);
			var formDiyContainer = new Ext.Container({
						layout : 'fit'
					});
			var layout_fields = [fset_1, formDiyContainer];
			var btnTempSaveHtml = this.getButtonHml(this.btnIdObj.btnTempSave,
					'付款');
			var title = '撤资申请&nbsp;&nbsp;' + btnTempSaveHtml ;
			/* + '提示：[<span style="color:red;">带"*"的为必填项，金额默认单位为 "元"</span>]' */;
			var frm_cfg = {
				title : title,
				formDiyCfg : {
					sysId : _this.sysId,/* 系统ID */
					formdiyCode : FORMDIY_IND.EntrustContract,
					/* * 引用Code --> 对应 * ts_Formdiy * 中的业务引用键：recode
																 */
					formIdName : 'id',/* 对应表单中的ID Hidden 值 */
					container : formDiyContainer
					/* 自定义字段存放容器 */
				},
				autoScroll : true,
				url : './fuBamountApply_savePay.action',
				labelWidth : 115
			};
			var applyForm = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			applyForm.addListener('afterRender', function(panel) {
						_this.addListenersToCustButtons([{
									btnId : _this.btnIdObj.btnTempSave,
									fn : function(e, targetEle, obj) {
										if(this.globalMgr.brId)return;
										_this.saveBamApply();
									}
								}]);
					});
			return applyForm;
		},
	/**
	 * 创建详情面板
	 */
	createDetailPnl : function(){	
			var _this = this;
			var htmlArrs_1 = [
					'<tr><th colspan="7" style="font-weight:bold;text-align:left;font-size:35px" ><center>撤资申请</center></th><tr>',	
					'<tr><th col="code">合同编号</th> <td col="code" >&nbsp;</td><th col="yearLoan">委托期限</th> <td col="yearLoan" >&nbsp;</td><th col="rate">贷款利率</th> <td col="rate" >&nbsp;</td></tr>',
					'<tr><th col="payDate">委托生效日期</th> <td col="payDate" >&nbsp;</td><th col="endDate">失效日期</th> <td col="endDate" >&nbsp;</td><th col="iamount">每月收益金额</th> <td col="iamount" >&nbsp;</td></tr></tr>',
					'<tr><th col="doDate">合同签订日期</th> <td col="doDate">&nbsp;</td><th col=""></th> <td col="" >&nbsp;</td><th col=""></th> <td col="" >&nbsp;</td></tr></tr>'];
			var detailCfgs_1 = [{
				cmns : 'THREE',
				/* ONE , TWO , THREE */
				model : 'single',
				labelWidth : 90,
				/* title : '#TITLE#', */
				// 详情面板标题
				/* i18n : UI_Menu.appDefault, */
				// 国际化资源对象
				htmls : htmlArrs_1,
				url : './fuEntrustContract_get.action',
				params : {
					 id : _this.globalMgr.entrustId
				},
				callback : {
					sfn : function(jsonData) {
//							_this.applyPanel.setFieldValue("contractId",jsonData.id);
							if(jsonData["unint"]==1){
								jsonData["rate"]=jsonData["rate"]+"%";
							}else if(jsonData["unint"]==2){
								jsonData["rate"]=jsonData["rate"]+"‰";
							}
							if(jsonData["monthLoan"]==0){
								jsonData["yearLoan"]=jsonData["yearLoan"]+"&nbsp;年&nbsp;";
							}else{
								jsonData["yearLoan"]=jsonData["yearLoan"]+"&nbsp;年&nbsp;"+jsonData["monthLoan"]+"&nbsp;月";
							}
							jsonData['iamount']=Render_dataSource.moneyRender(jsonData['iamount']);
					}
				}
			}];
			var customerPanel = new Ext.ux.panel.DetailPanel({
						title : '委托合同信息',
						collapsible : true,
						border : false,
						isLoad : false,
						detailCfgs : detailCfgs_1
					});

			_this.globalMgr.customerPanel=customerPanel;
		},
	/**
	 * 组件销毁方法
	 */
	destroyCmpts : function(){
		
	},
	getButtonHml : function(id, text) {
			var html = "&nbsp;&nbsp;<span class='btnBussNodeCls' id='" + id
					+ "'>" + text + "</span>&nbsp;&nbsp;";
			return html;
		},
	/**
	 * 点击付款后保存信息
	 */
	saveBamApply : function(){
		var _this=this;
		EventManager.frm_save(this.globalMgr.appFrm,{
			sfn:function(json_data){
				_this.globalMgr.brId=json_data.id;	
			}
		});
		
	},
	globalMgr : {
//		/**
//		 * 创建附件FieldSet 对象
//		 * 
//		 * @return {}
//		 */
//			createAttachMentFs : function(_this) {
//				/*
//				 * ----- 参数说明： isLoad 是否实例化后马上加载数据 dir : 附件上传后存放的目录，
//				 * mortgage_path 定义在 resource.properties 资源文件中 isSave :
//				 * 附件上传后，是否保存到 ts_attachment 表中。 true : 保存 params :
//				 * {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment
//				 * 中的说明,formId:业务单ID，如果有多个用","号分隔} 当 isLoad = false 时，
//				 * params 可在 reload 方法中提供 ----
//				 */
////				var uuid=Cmw.getUuid();
//				var uuid=_this.params.applyId
//				var attachMentFs = new Ext.ux.AppAttachmentFs({
//							title : '相关材料附件',
//							isLoad : false,
//							dir : 'mort_path',
//							isSave : true,
//							isNotDisenbaled : true,
//							params:{sysId:-1,formType:ATTACHMENT_FORMTYPE.FType_41,formId:uuid}
//						});
//				return attachMentFs;
//			},
//		
		/**
		 * 显示面板详情
		 * @return {}
		 */
		show: function(){
			this.detailPanel_1.show();
		},
//		hide : function(){
//			this.detailPanel_1.hide();
//		},
		customerPanel:null,
		applyId:null,
		entrustId:null,
		brId:null,
		appFrm:null,
		jsonData:null,
		AddBtn :null,
		DelBtn : null,
		EditBtn : null,
		id : null,
		entrustCustId :  this.params.entrustCustId,
		activeKey: null,
		formId:this.params.applyId,
		sysId : this.params.sysid,
		detailPanel_1 : null,
		winEdit : {
			show : function(parentCfg){
				var _this = parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent = null;
				parent={
					sysid : _this.globalMgr.sysId,
					applyid:_this.globalMgr.formId,
					onel:_this.globalMgr.onel,
					entrustCustId : _this.globalMgr.entrustCustId
				};
				parentCfg.parent = parent;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule=null;
					if(winkey=="添加委托合同" || winkey=="编辑委托合同"){
						winModule="EntrustcustEdit";
					}
					Cmw.importPackage('pages/app/funds/entrustcontract/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		},
		/**
		 * 打开打印窗口
		 */
		openPrintDialog : function(parentCfg){
			var _this = parentCfg.self;
			var winkey=parentCfg.key;
			_this.globalMgr.activeKey = winkey;
				var printType = 2;/*打印有数据的合同文档*/
			var contractId = _this.globalMgr.sysId;
			var _params = {contractId:contractId,printType:printType};
			parentCfg.params = _params;
			if(_this.appCmpts[winkey]){
				_this.appCmpts[winkey].show(parentCfg);
			}else{
				EventManager.get("./fcFuntempCfg_getTidByMid.action",{params:{menuId:_this.params.nodeId},sfn : function(jsonData){
					parentCfg.tempId=jsonData.list[0].tempId;
					Cmw.importPackage('pages/app/dialogbox/PrintDialogbox',function(module) {
							 	_this.appCmpts[winkey] = module.DialogBox;
							 	_this.appCmpts[winkey].show(parentCfg);
					  		});
						}});
			}
		}
	}
});

