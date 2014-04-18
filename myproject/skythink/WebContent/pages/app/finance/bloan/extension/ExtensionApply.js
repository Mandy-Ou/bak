/**
 * 展期申请页面
 * @author 程明卫
 * @date 2013-09-06
 */
define(function(require, exports) {
	exports.viewUI = {
		parentPanel : null,/**/
		appMainPanel : null,
		customerPanel : null,
		applyPanel : null,
		gopinionGrid : null,
		attachMentFs : null,
		customerDialog : null,/*展期客户选择弹窗*/
		gopinionDialog : null,/*担保人意见弹窗*/
		params : null,
		uuid : Cmw.getUuid(),/*用于新增时，临时代替申请单ID*/
		applyId : null,/*申请单ID*/
		seDateDisplayId : Ext.id(null,'seDateDisplayId'),/*显示展期期限的控件ID*/
		btnIdObj : {
			btnChoseCust : Ext.id(null, 'btnChoseCust'),/* 展期客户选择 */
			btnAddGopinion : Ext.id(null, 'btnAddGopinion'),/* 添加意见 */
			btnEditGopinion : Ext.id(null, 'btnEditGopinion'),/* 修改意见 */
			btnDelGopinion : Ext.id(null, 'btnDelGopinion'),/* 删除意见 */
			btnTempSave : Ext.id(null, 'btnTempSave'),/* 暂存申请单 */
			btnSave : Ext.id(null, 'btnSave')/* 提交申请单 */
		},
		/**
		 * 获取主面板
		 * @param tab Tab 面板对象
		 * @param params 由菜单点击所传过来的参数
		 * 	{tabId : tab 对象ID,apptabtreewinId : 系统程序窗口ID,sysid : 系统ID} 
		 */
		getMainUI : function(parentPanel,params){
			this.setParams(parentPanel,params);
			if(!this.appMainPanel){
				this.createCmpts();
			}
			return this.appMainPanel;
		},
		/**
		 * 创建组件
		 */
		createCmpts : function(){
			this.createCustomerPanel();
			this.createFormPanel();
			this.appMainPanel = new Ext.Container({items:[this.customerPanel,this.applyPanel]});
		},
		createCustomerPanel : function() {
			var htmlArrs_1 = [
					'<tr><th col="custType">客户类型</th> <td col="custType" >&nbsp;</td><th col="custName">客户名称</th> <td col="custName" >&nbsp;</td><th col="cardNum" id="label_cardType">证件类型</th> <td col="cardNum" >&nbsp;</td></tr>',
					'<tr><th col="contractCode">借款合同号</th> <td col="contractCode" >&nbsp;</td><th col="guacontractCode">保证合同号</th> <td col="guacontractCode" >&nbsp;</td><th col="breedName">业务品种</th> <td col="breedName" >&nbsp;</td></tr>',
					'<tr><th col="payTypeName">还款方式</th> <td col="payTypeName" >&nbsp;</td><th col="appAmount">贷款金额</th> <td col="appAmount" >&nbsp;</td><th col="zprincipals">贷款余额</th> <td col="zprincipals" >&nbsp;</td></tr>',
					'<tr><th col="rate">贷款利率</th> <td col="rate" >&nbsp;</td><th col="mrate">管理费率</th> <td col="mrate" >&nbsp;</td><th col="payDate">贷款期限</th> <td col="payDate" >&nbsp;</td></tr></tr>'];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 90,
			    /* title : '#TITLE#', */
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './fcExtension_getContract.action',
			    prevUrl: './fcExtension_getContract.action',
			    nextUrl: './fcExtension_getContract.action',
			    params: {
			        contractId: -1
			    },
			    callback: {
			        sfn: function(jsonData) {
			            jsonData["custType"] = Render_dataSource.custTypeRender(jsonData["custType"]);
			            Ext.get('label_cardType').update(jsonData["cardType"]);
			            var rateType = jsonData["rateType"];
			            var rateTypeName = Render_dataSource.rateTypeRender(rateType);
			            // 民汇内部利率
			            var inRate = jsonData.inRate;
			            var inRateType = jsonData.inRateType;
			            if(inRate){
			            	inRate = inRate+"%";
			            }else{
			            	inRate = "";
			            }
			            if(inRateType){
			            	inRateType = Render_dataSource.rateTypeRender(inRateType);
			            	inRateType = "&nbsp;&nbsp;<span style='color:bule;font-weight:bold;'>(内部利率类型："+inRateType+"&nbsp; :"+inRate+")</span>"
			            }else {
			            	inRateType = "";
			            }
			            jsonData["rate"] = jsonData["rate"]+"%&nbsp;&nbsp;<span style='color:red;font-weight:bold;'>("+rateTypeName+")</span>"+inRateType;
			            var mgrtypeName = Render_dataSource.mgrtypeRender(jsonData["mgrtype"]);
			            jsonData["mrate"] = jsonData["mrate"]+"%&nbsp;&nbsp;<span style='color:red;font-weight:bold;'>("+mgrtypeName+")</span>";
			            jsonData["payDate"] =  jsonData["payDate"] +"至"+ jsonData["endDate"];
			            var appAmount = jsonData["appAmount"];
			            jsonData["appAmount"] =  Cmw.getThousandths(appAmount)+"元&nbsp;&nbsp;<br/><span style='color:red;font-weight:bold;'>("+Cmw.cmycurd(appAmount)+")</span>";
			             var zprincipals = jsonData["zprincipals"];
			            jsonData["zprincipals"] =  Cmw.getThousandths(zprincipals)+"元&nbsp;&nbsp;<br/><span style='color:red;font-weight:bold;'>("+Cmw.cmycurd(zprincipals)+")</span>";
			        }
			    }
			}];
			var btnChoseCustHtml = this.getButtonHml(this.btnIdObj.btnChoseCust, '选择展期客户');
			this.customerPanel = new Ext.ux.panel.DetailPanel({
		    	title : '展期客户资料' + btnChoseCustHtml,
				collapsible : true,
				//collapsed : false,
				border : false,
			  //  width: 780,
			    isLoad : false,
			    detailCfgs: detailCfgs_1
			});
    
			var _this = this;
			this.customerPanel.addListener('afterrender', function(panel) {
				_this.addListenersToCustButtons([{
					btnId : _this.btnIdObj.btnChoseCust,
					fn : function(e, targetEle, obj) {
						/* 选择展期客户 */
						_this.openCustomerDialog();
					}
				}]);
			});
			this.customerPanel.on('expand', function(cmpt) {
				_this.doResize();
			});
			this.customerPanel.on('collapse', function(cmpt) {
				_this.doResize();
			});
		},
		createFormPanel : function(){
			var _this = this;
			var txt_id = FormUtil.getHidField({fieldLabel: '展期申请单ID',name: 'id'	});
			
			var txt_isenabled = FormUtil.getHidField({fieldLabel: '可用标识',name: 'isenabled'});
			
			var txt_status = FormUtil.getHidField({fieldLabel: '状态',name: 'status'});
			
			var txt_managerId = FormUtil.getHidField({fieldLabel: '经办人ID',name: 'managerId',value:CURRENT_USERID});
			
			var txt_eendDate = FormUtil.getHidField({fieldLabel: '展期截止日期',name: 'eendDate'});
			
			var txt_estartDate = FormUtil.getHidField({fieldLabel: '展期起始日期',name: 'estartDate'});
			
			var txt_endAmount = FormUtil.getHidField({fieldLabel: '到期贷款金额',name: 'endAmount'});
			
			var txt_oendDate = FormUtil.getHidField({fieldLabel: '原借款截止日期',name: 'oendDate'});
			
			var txt_ostartDate = FormUtil.getHidField({fieldLabel: '原借款起始日期',name: 'ostartDate'});
			
			var txt_guaCode = FormUtil.getHidField({fieldLabel: '担保合同号',name: 'guaCode'});
			
			var txt_loanCode = FormUtil.getHidField({fieldLabel: '借款合同号',name: 'loanCode'});
			
			var txt_contractId = FormUtil.getHidField({fieldLabel: '借款合同ID',name: 'contractId'});
			
			var txt_procId = FormUtil.getHidField({fieldLabel: '流程实例ID',name: 'procId'});
			
			var txt_breed = FormUtil.getHidField({fieldLabel: '子业务流程ID', name: 'breed'});
			
			var txt_code = FormUtil.getTxtField({
			    fieldLabel: '编号',
			    name: 'code',
			    "width": 150,
			    "maxLength": "20",
			    emptyText : '如不填写系统自动生成编号'
			});
			
			var txt_extAmount = FormUtil.getMoneyField({
			    fieldLabel: '展期金额',
			    name: 'extAmount',
			    width: 500,
			    fieldWidth:80,/*文本框长度*/
			    allowBlank: false,
			   	value : 0,
				autoBigAmount : true,
				unitText : '此处将显示大写金额',
				unitStyle : 'margin-left:4px;color:red;font-weight:bold'
			});
			
			var cbo_mgrtype = FormUtil.getLCboField({
			    fieldLabel: '利率类型',
			    hiddenLabel : true,
			    name: 'rateType',
			    "width": 60,
			    "allowBlank": false,
			    "maxLength": 10,
			    "data": [["1", "月利率"], ["2", "日利率"], ["3", "年利率"]]
			});
			
			var int_rateType = FormUtil.getDoubleField({
			    fieldLabel: '贷款利率',
			    name: 'rate',
			    "width": 50,
			    "allowBlank": false,
			    "maxLength": 10
			});
			
			var comp_extrate = FormUtil.getMyCompositeField({
				fieldLabel : '展期贷款利率',
				name : 'comp_extrate',
				width : 140,
				allowBlank : false,
				sigins : null,
				itemNames : 'rateType,rate',
				items :[cbo_mgrtype,int_rateType, {
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
				fieldLabel : '每期还本金额',
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

				// 民汇公司内部利率	
			var dob_inRate = FormUtil.getDoubleField({
						fieldLabel : '展期内部利率',
						name : 'inRate',
						"width" : 125,
						"allowBlank" : false,
						"value" : "0",
						decimalPrecision : 2,
						unitText : '%'
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
			
			var int_yearLoan = FormUtil.getIntegerField({
				fieldLabel : '展期期限(年)',
				name : 'yearLoan',
				width : 30,
				"allowBlank" : false,
				"value" : 0,
				"maxLength" : 10,
				maxValue : 100,
				listeners : {change:function(){_this.setSeDate();}}
			});

			var int_monthLoan = FormUtil.getIntegerField({
				fieldLabel : '展期期限(月)',
				name : 'monthLoan',
				width : 30,
				"allowBlank" : false,
				"value" : 0,
				"maxLength" : 10,
				maxValue : 12,
				listeners : {change:function(){_this.setSeDate();}}
			});

			var int_dayLoan = FormUtil.getIntegerField({
				fieldLabel : '展期期限(日)',
				name : 'dayLoan',
				width : 30,
				"allowBlank" : false,
				"value" : 0,
				maxValue : 31,
				"maxLength" : 10,
				listeners : {change:function(){_this.setSeDate();}}
			});
			var disp_seDate = new Ext.form.DisplayField({id:this.seDateDisplayId,value:'&nbsp;'});
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
			
			
			var cbo_mgrtype = FormUtil.getLCboField({
			    fieldLabel: '管理费收取方式',
			    name: 'mgrtype',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": 10,
			    "data": Lcbo_dataSource.mgrtype_dates
			});
			
			var dob_mrate = FormUtil.getDoubleField({
			    fieldLabel: '管理费率',
			    name: 'mrate',
			    "width": 125,
			    "allowBlank": false,
			    "value": "0",
			    "maxLength": 10,
			    unitText : '%'
			});
			
			var txt_applyMan = FormUtil.getTxtField({
			    fieldLabel: '申请人',
			    name: 'applyMan',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "10"
			});
			
			var bdat_applyDate = FormUtil.getDateField({
			    fieldLabel: '申请日期',
			    name: 'applyDate',
				allowBlank : false,
				value:new Date(),
			    "width": 125
			});
			
			var bdat_comeDate = FormUtil.getDateField({
			    fieldLabel: '收到申请书日期',
			    name: 'comeDate',
			    value:new Date(),
			    "width": 125
			});
			
			var txt_manager = FormUtil.getTxtField({
			    fieldLabel: '经办人',
			    name: 'manager',
			    "allowBlank": false,
			    "width": 125,
			    disabled : true,
			    value : CURENT_EMP
			});
			
			var txt_paySource = FormUtil.getTxtField({
			    fieldLabel: '还款来源',
			    name: 'paySource',
			    "width": 475,
			    "maxLength": "255"
			});
			
			var txt_extReason = FormUtil.getTAreaField({
			    fieldLabel: '展期原因',
			    name: 'extReason',
			    "width": 475,
			    "maxLength": "1000"
			});
			
			this.createGopinionGrid();
			this.createAttachMentFs();
			var firstPanel = new Ext.Panel({layout:'form',items:[txt_paySource, txt_extReason]});
	 		var twoPanel = new Ext.Panel({layout:'form',items:[this.attachMentFs]});
	 		var layoutCmnPanel = FormUtil.getLayoutPanel([.66,.33],[firstPanel,twoPanel]);
	 
			var layout_fields = [
			txt_id, txt_isenabled, txt_status, txt_managerId, txt_eendDate, txt_estartDate, txt_endAmount, txt_oendDate, txt_ostartDate, txt_guaCode, txt_loanCode, txt_contractId, txt_procId, txt_breed, {
			    cmns: FormUtil.CMN_THREE,
			    fields: [txt_code, txt_extAmount, comp_loanLimit]
			},{
				 cmns: FormUtil.CMN_THREE,
			    fields: [comp_extrate, compt_payType,dob_inRate]
			},
			{
			    cmns: FormUtil.CMN_TWO,
			    fields: [rad_isadvance,comp_loanLimit]
			},
			{
			    cmns: FormUtil.CMN_THREE,
			    fields: [cbo_mgrtype, dob_mrate, txt_applyMan, bdat_applyDate, bdat_comeDate, txt_manager]
			},layoutCmnPanel,this.gopinionGrid];

			
			var btnTempSaveHtml = this.getButtonHml(this.btnIdObj.btnTempSave,'暂存申请单');
			var btnSaveHtml = this.getButtonHml(this.btnIdObj.btnSave, '提交申请单');

			var title = '展期申请单信息&nbsp;&nbsp;'
					+ btnTempSaveHtml
					+ btnSaveHtml
					+ '提示：[<span style="color:red;">带"*"的为必填项，金额默认单位为 "元"</span>]';
			var frm_cfg = {
				title : title,
				autoScroll : true,
				url : './fcExtension_save.action',
				labelWidth : 115
			};
			var applyForm = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			var _this = this;
			applyForm.addListener('afterrender', function(panel) {
				_this.addListenersToCustButtons([{
							btnId : _this.btnIdObj.btnTempSave,
							fn : function(e, targetEle, obj) {
								_this.saveApplyData(0);
							}
						}, {
							btnId : _this.btnIdObj.btnSave,
							fn : function(e, targetEle, obj) {
								_this.saveApplyData(1);
							}
						}]);
			});

			this.applyPanel = applyForm;
		},
		/**
		 * 设置显示的展期起始和截止日期
		 * @param estartDate 展期起始日期
		 * @param eendDate 展期截止日期
		 */
		setSeDate : function(estartDate,eendDate){
			var el = Ext.get(this.seDateDisplayId);
			if(!el) return;
			
			var dispVal = "";
			if(estartDate && eendDate){/*修改时调用*/
				dispVal = estartDate + "起&nbsp;&nbsp;至&nbsp;&nbsp;" + eendDate;
			}else{
				var oendDate = this.applyPanel.getValueByName("oendDate");
				if(!oendDate){
					ExtUtil.alert({msg:'原借款截止日期为空!'});
					return;
				}
				estartDate = Date.parseDate(oendDate,'Y-m-d');
				estartDate = estartDate.add(Date.DAY, 1);
				eendDate = estartDate;
				var limitLoanVal = this.applyPanel.getValueByName("limitLoan");
				var limitLoan = limitLoanVal.yearLoan;
				if(limitLoan && limitLoan>0){
					eendDate = eendDate.add(Date.YEAR,limitLoan);
				}
				limitLoan = limitLoanVal.monthLoan;
				if(limitLoan && limitLoan>0){
					eendDate = eendDate.add(Date.MONTH,limitLoan);
				}
				limitLoan = limitLoanVal.dayLoan;
				if(limitLoan && limitLoan>0){
					eendDate = eendDate.add(Date.DAY,limitLoan);
				}
				eendDate = eendDate.add(Date.DAY,-1);
				var fmt = 'Y-m-d';
				estartDate = estartDate.format(fmt);
				eendDate = eendDate.format(fmt);
				this.applyPanel.setJsonVals({estartDate:estartDate,eendDate:eendDate});
				dispVal = estartDate + "起&nbsp;&nbsp;至&nbsp;&nbsp;" + eendDate;
			}
			el.update("&nbsp;&nbsp;<span style='font-weight:bold;color:red;'>(自"+dispVal+"止)</span>");
		},
		/**
		 * 创建担保人意见Grid
		 */
		createGopinionGrid : function(){
			var structure_1 = [
			{
			    header: '担保人',
			    name: 'guarantor'
			},
			{
			    header: '法定/授权代表人',
			    name: 'legal',
			    width: 120
			},
			{
			    header: '担保人意见',
			    name: 'opinion',
			    width: 500
			},
			{
			    header: '签字日期',
			    name: 'signDate',
			    width: 80
			}];
			
			var btnAddGopinionHtml = this.getButtonHml(this.btnIdObj.btnAddGopinion,'添加意见');
			var btnEditGopinionHtml = this.getButtonHml(this.btnIdObj.btnEditGopinion, '修改意见');
			var btnDelGopinionHtml = this.getButtonHml(this.btnIdObj.btnDelGopinion, '删除意见');

			var title = '担保人意见列表&nbsp;&nbsp;' + btnAddGopinionHtml + btnEditGopinionHtml + btnDelGopinionHtml;
					
			this.gopinionGrid = new Ext.ux.grid.AppGrid({
			    title: title,
			    collapsible : true,
			    structure: structure_1,
			    height:250,
			    url: './fcGopinion_list.action',
			    needPage: false,
			    isLoad: false,
			    autoScroll : true,
			    keyField: 'id'
			});
			var _this = this;
			this.gopinionGrid.addListener('afterrender', function(panel) {
				_this.addListenersToCustButtons([{
					btnId : _this.btnIdObj.btnAddGopinion,
					fn : function(e, targetEle, obj) {
						_this.openGopinionDialog(OPTION_TYPE.ADD);
					}
				}, {
					btnId : _this.btnIdObj.btnEditGopinion,
					fn : function(e, targetEle, obj) {
						_this.openGopinionDialog(OPTION_TYPE.EDIT);
					}
				}, {
					btnId : _this.btnIdObj.btnDelGopinion,
					fn : function(e, targetEle, obj) {
						var extensionId = _this.applyId || _this.uuid;
						EventManager.deleteData('./fcGopinion_delete.action',{type:'grid',cmpt:_this.gopinionGrid,optionType:OPTION_TYPE.DEL,self : {
							refresh : function(){_this.gopinionGrid.reload({extensionId:extensionId});}
						}});
					}
				}]);
			});
		},
		/**
		 * 创建附件FieldSet 对象
		 * @return {}
		 */
		createAttachMentFs : function(){
			/* ----- 参数说明： isLoad 是否实例化后马上加载数据 
			 * dir : 附件上传后存放的目录， mortgage_path 定义在 resource.properties 资源文件中
			 * isSave : 附件上传后，是否保存到 ts_attachment 表中。 true : 保存
			 * params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔}
			 *    当 isLoad = false 时， params 可在 reload 方法中提供
			 *  ---- */
			this.attachMentFs = new  Ext.ux.AppAttachmentFs({title:'展期相关附件上传',isLoad:false,dir : 'extension_path',isSave:true});
		},
		/**
		 * 打开展期客户选择窗口
		 */
		openCustomerDialog : function(){
			var _this = this;
		    var _contractId = this.applyPanel.getValueByName("contractId");
		    if(_contractId){
		    	ExtUtil.confirm({title:'提示',msg:'确定重新选择新的客户吗?',fn:function(btn){
					if(btn != 'yes') return;
					_this.showCustomerDialog();
				}});
		    }else{
		    	this.showCustomerDialog();
		    }
		},
		/**
		 * 将展期客户数据赋给 展期客户资料面板
		 */
		showCustomerDialog : function(){
			var _this = this;
			var el = Ext.get(this.btnIdObj.btnChoseCust);
			var parentCfg = {parent:el,callback:function(id,record){
				_this.setChooseVal(id,record);
			}};
			if(this.customerDialog){
				this.customerDialog.show(parentCfg);
			}else{
				var _this = this;
				Cmw.importPackage('pages/app/dialogbox/ExtensionDialogbox',function(module) {
				 	_this.customerDialog = module.DialogBox;
				 	_this.customerDialog.show(parentCfg);
		  		});
			}
		},
		/**
		 * 将选择的客户数据赋给展期客户资料详情面板
		 */
		setChooseVal : function(id,record){
			var data = record.data;
			var formData = {contractId : id,loanCode : data.contractCode,guaCode : data.guacontractCode,
			ostartDate : data.payDate,oendDate : data.endDate,endAmount : data.appAmount,
			extAmount : data.zprincipals,payType : data.payType,phAmount : data.phAmount,
			rateType : data.rateType,rate : data.rate,isadvance : data.isadvance,
			mgrtype : data.mgrtype,mrate : data.mrate,comp_extrate:'',compt_payType:'',applyMan:data.custName,
			inRateType : data.inRateType,inRate : data.inRate};
			this.customerPanel.reload({json_data:data},true);
			this.applyPanel.enable();
			this.applyPanel.setJsonVals(formData);
		},
		/**
		 * 打开担保人意见弹窗
		 */
		openGopinionDialog : function(opType){
			if((opType && opType == OPTION_TYPE.EDIT)){/*修改*/
				var selId = this.gopinionGrid.getSelId();
				if(!selId) return;
			}
			var extensionId = this.applyId || this.uuid;
			var params = {extensionId : extensionId};
			var parentCfg = {parent:this.gopinionGrid,optionType:opType,params:params};
			if(this.gopinionDialog){
				this.gopinionDialog.show(parentCfg);
			}else{
				var _this = this;
				Cmw.importPackage('pages/app/finance/bloan/extension/GopinionEdit',function(module) {
				 	_this.gopinionDialog = module.WinEdit;
				 	_this.gopinionDialog.show(parentCfg);
		  		});
			}
		},
		/**
		 * 保存单据数据
		 * @param	opType	[0:暂存,1:提交]
		 */
		saveApplyData : function(submitType){
			if (!this.validApplyForm()) return;
			var _this = this;
			var currTabId = this.params.tabId;
			var apptabtreewinId = this.params.apptabtreewinId;
			var _this = this;
			EventManager.frm_save(this.applyPanel, {
				beforeMake : function(formDatas) {
					formDatas.submitType = submitType;
					if(_this.uuid) formDatas.uuid = _this.uuid;
				},
				sfn : function(formDatas) {
					if(formDatas["applyId"]){
						_this.applyId = formDatas["applyId"];
					}else{
						_this.applyId = null;
					}
					if(_this.applyId){
						var attachParams = _this.getAttachParams(_this.applyId);
						_this.attachMentFs.updateTempFormId(attachParams);
					}
					if(_this.uuid) _this.uuid = null;
					if(currTabId){
						var currTab = Ext.getCmp(currTabId);
						if(currTab) currTab.destroy();					
					}
					var tabId = null;
					var params = null;
					if (submitType == 0) {
						tabId = CUSTTAB_ID.tempExtensionApplyMgrTabId;
						Cmw.activeTab(apptabtreewinId, tabId, params);
					} else {  /* 跳转到 业务审批页面 */
						var code = formDatas["code"];
						var procId = _this.applyPanel.getValueByName("procId");
						var sysId = _this.params.sysid;
						var contractId = _this.applyPanel.getValueByName('contractId');
					
						var msg = '';
						if(code){
							msg = '确定提交编号为："'+code+'"的展期申请单?';
						}else{
							msg = '确定提交当前展期申请单?';
						}
						 ExtUtil.confirm({title:'提示',msg:msg,fn:function(){
						 	   var params = {isnewInstance:true,sysId:sysId,applyId:_this.applyId,contractId:contractId,procId:procId,bussProccCode:'B0001'};
						 		tabId = CUSTTAB_ID.bussProcc_auditMainUITab.id;
								url = CUSTTAB_ID.bussProcc_auditMainUITab.url;
								var title =  '展期业务审批';
								Cmw.activeTab(apptabtreewinId, tabId, params, url, title);
						 }});
					}
				}
			});
		},
		/**
		 * 申请表单数据验证
		 */
		validApplyForm : function() {
			var errMsg = [];
			// 展期金额
			var extAmount_val = this.applyPanel.getValueByName("extAmount");
			if (!extAmount_val || parseFloat(extAmount_val) <= 0) {
				errMsg[errMsg.length] = "展期金额必须大于0!";
			}
			// 贷款期限 yearLoan monthLoan dayLoan
			var limitLoan_val = this.applyPanel.getValueByName("limitLoan");
			if (!limitLoan_val) {
				errMsg[errMsg.length] = "展期期限必须大于0!";
			} else {
				var yearLoan = limitLoan_val.yearLoan;
				var monthLoan = limitLoan_val.monthLoan;
				var dayLoan = limitLoan_val.dayLoan;
				var flag = true;
				if ((!yearLoan || yearLoan <= 0)
						&& (!monthLoan || monthLoan <= 0)
						&& (!dayLoan || dayLoan <= 0)) {
					flag = false;
				}
				if (!flag) {
					errMsg[errMsg.length] = "展期期限的年,月,日至少有一个必须大于0!";
				}
				if(yearLoan && yearLoan>100){
					errMsg[errMsg.length] = "展期期限的年份最大数值不能超过100!";
				}
				if(monthLoan && monthLoan>12){
					errMsg[errMsg.length] = "展期期限的月份最大数值不能超过12!";
				}
				if(dayLoan && dayLoan>31){
					errMsg[errMsg.length] = "展期期限的月份最大数值不能超过31!";
				}
			}
			
			// 还款方式 payType phAmount
			var compt_payType_val = this.applyPanel.getValueByName("compt_payType");
			if (!compt_payType_val) {
				errMsg[errMsg.length] = "还款方式不能为空!";
			} else {
				var payType = compt_payType_val.payType;
				if (!payType) errMsg[errMsg.length] = "还款方式不能为空!";
				var phAmount = compt_payType_val.phAmount;
				if (!phAmount || phAmount <= 0) {
					if (payType == "P0002") {// "按月付息，分期还本"
						errMsg[errMsg.length] = "选择按月付息，分期还本；分期还本金额必须大于0!";
					} else if (payType == "P0004") {// "按季付息，分期还本"
						errMsg[errMsg.length] = "按月付息，分季还本；分期还本金额必须大于0!";
					}
				}
			}
			// 贷款利率
			var extrate_val = this.applyPanel.getValueByName("comp_extrate");
			var rateType = extrate_val.rateType;
			if (!rateType) {
				errMsg[errMsg.length] = "展期利率类型不能为空!";
			}
			var rate_val = extrate_val.rate;
			if (!rate_val || parseFloat(rate_val) <= 0) {
				errMsg[errMsg.length] = "展期贷款利率必须大于0!";
			}
			// 管理费收取方式
			var mgrtype_val = this.applyPanel.getValueByName("mgrtype");
			if(!mgrtype_val) mgrtype_val = 0;
			// 管理费率
			var mrate_val = this.applyPanel.getValueByName("mrate");
			if (mrate_val < 0) {
				errMsg[errMsg.length] = "管理费收取方式不能为空!";
			} else {
				if (mgrtype_val == 1 && (!mrate_val || mrate_val <= 0)) {
					errMsg[errMsg.length] = "按还款方式算法收取管理费时；管理费率必须大于0!";
				}
			}
			if (null != errMsg && errMsg.length > 0) {
				var msg = errMsg.join("</br>");
				ExtUtil.alert({
							msg : msg
						});
				return false;
			}
			return true;
		},
		/**
		 * 获取自定义按钮 HTML CODE
		 * 
		 * @param id
		 *            按钮ID
		 * @param text
		 *            按钮文本
		 */
		getButtonHml : function(id, text) {
			var html = "&nbsp;&nbsp;<span class='btnBussNodeCls' id='" + id
					+ "'>" + text + "</span>&nbsp;&nbsp;";
			return html;
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
				var btnEle = addClass(btnId);
				btnEle.on('click', btnCfg.fn, this);
			}
			/**
			 * 为按钮添加点击和鼠标经过样式
			 */
			function addClass(eleId) {
				var btnEle = Ext.get(eleId);
				btnEle.addClassOnClick("nodeClickCls");
				btnEle.addClassOnOver("x-btn-text");
				return btnEle;
			}
		},
		setParams : function(parentPanel, params) {
			this.parentPanel = parentPanel;
			this.params = params;
			this.applyId = params.applyId;
		},
		refresh : function() {
			if (!this.appMainPanel.rendered) {
				var _this = this;
				this.appMainPanel.addListener('render', function(cmpt) {
					_this.loadDatas();
				});
			} else {
				this.loadDatas();
			}
		},
		/**
		 * 在这里加载数据
		 */
		loadDatas : function(){
			var _this = this;
			var sysId = this.params.sysid;
			var optionType = this.params.optionType;
			var breed = this.params.breed;
			var applyId = this.params.applyId;
			var contractId = this.params.contractId;
			if(optionType && optionType == OPTION_TYPE.EDIT){/*修改*/
				var errMsg = [];//["修改时传参发生错误："];
				if(!applyId){
					errMsg[errMsg.length] = "必须传入参数\"applyId\"!<br/>";			
				}
				if(!contractId){
					errMsg[errMsg.length] = "必须传入参数\"contractId\"!<br/>";			
				}
				if(null != errMsg && errMsg.length > 0){
					errMsg = "修改时传参发生错误：<br/>"+errMsg.join(" ");
					ExtUtil.alert({msg:errMsg});
					return;
				}
				this.applyPanel.enable();
				this.applyPanel.reset();
				this.customerPanel.reload({contractId:contractId});
				this.applyPanel.setValues('./fcExtension_get.action', {params : {id : applyId},sfn: function(json_data){
					_this.setSeDate(json_data.estartDate,json_data.eendDate);
				}});
				this.gopinionGrid.reload({extensionId:applyId});
				this.attachMentFs.reload(this.getAttachParams(applyId));
			}else{/*新增*/
				this.attachMentFs.params = this.getAttachParams(this.uuid);
				this.applyPanel.setJsonVals({breed:breed});
				this.applyPanel.enable();
				this.applyPanel.disable();
			}
		},
		/**
		 * 获取附件参数
		 * @param formId 业务单ID
		 */
		getAttachParams : function(formId){
			var sysId = this.params.sysid;
			return {sysId:sysId,formType:ATTACHMENT_FORMTYPE.FType_31,formId:formId};
		},
		doResize : function(){
		
		},
		resize : function(adjWidth,adjHeight){
			this.appMainPanel.setWidth(adjWidth);
			this.appMainPanel.setHeight(adjHeight);
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function(){
			if(null != this.appMainPanel){
				if(this.customerDialog) this.customerDialog.destroy();
				if(this.gopinionDialog) this.gopinionDialog.destroy();
				 this.appMainPanel.destroy();
				 this.appMainPanel = null;
			}
		}
	}
});
