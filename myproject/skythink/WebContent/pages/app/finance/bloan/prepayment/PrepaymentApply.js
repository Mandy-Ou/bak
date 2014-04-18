/**
 * 提前还款申请页面
 * @author 程明卫
 * @date 2013-09-11
 */
define(function(require, exports) {
	exports.viewUI = {
		parentPanel : null,/**/
		appMainPanel : null,
		customerPanel : null,
		applyPanel : null,
		pcDetailPanel : null,
		attachMentFs : null,
		customerDialog : null,/*展期客户选择弹窗*/
		gopinionDialog : null,/*担保人意见弹窗*/
		params : null,
		uuid : Cmw.getUuid(),/*用于新增时，临时代替申请单ID*/
		applyId : null,/*申请单ID*/
		amountData : null,/*缓存提前还款计算相关金额*/
		freeamountTdId :  Ext.id(null, 'freeamount'),/*提前还款手续费TD id*/
		imamountTdId :  Ext.id(null, 'imamount'),/*应退息费 TD id*/
		totalAmountTdId :  Ext.id(null, 'totalAmount'),/*应收合计 TD id*/
		errArr : [],/*错误信息*/
		btnIdObj : {
			btnChoseCust : Ext.id(null, 'btnChoseCust'),/* 客户选择 */
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
			            jsonData["rate"] = jsonData["rate"]+"%&nbsp;&nbsp;<span style='color:red;font-weight:bold;'>("+rateTypeName+")</span>";
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
			var btnChoseCustHtml = this.getButtonHml(this.btnIdObj.btnChoseCust, '选择客户');
			this.customerPanel = new Ext.ux.panel.DetailPanel({
		    	title : '提前还款客户资料' + btnChoseCustHtml,
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
			var txt_id = FormUtil.getHidField({fieldLabel: '提前还款申请单ID',name: 'id'});
			
			var txt_code = FormUtil.getHidField({fieldLabel: '提前还款申请单编号',name: 'code'});
			
			var txt_isenabled = FormUtil.getHidField({fieldLabel: '可用标识',name: 'isenabled'});
			
			var txt_totalAmount = FormUtil.getHidField({fieldLabel: '提前还款额合计',name: 'totalAmount'});
			
			var txt_payplanId = FormUtil.getHidField({fieldLabel: '还款计划ID',name: 'payplanId'});
			
			var txt_freeamount = FormUtil.getHidField({fieldLabel: '提前还款手续费',name: 'freeamount'});
			
			var txt_managerId = FormUtil.getHidField({fieldLabel: '经办人ID', name: 'managerId',value:CURRENT_USERID});
			
			var txt_contractId = FormUtil.getHidField({fieldLabel: '借款合同ID', name: 'contractId'});
			
			var txt_procId = FormUtil.getHidField({fieldLabel: '流程实例ID',name: 'procId'});
			
			var txt_breed = FormUtil.getHidField({fieldLabel: '子业务流程ID',name: 'breed'});
			
			var txt_imamount = FormUtil.getHidField({fieldLabel: '退息费金额',name: 'imamount'});
			
			var cbo_ptype = FormUtil.getLCboField({
			    fieldLabel: '提前还款类别',
			    name: 'ptype',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": 10,
			    value : 1,
			    data:Lcbo_dataSource.prepayment_ptype_datas,
			    listeners : {change : function(field,newVal,oldVal){
			    	_this.showOrHideFields(newVal);
			    }}
			});
			
			
			var txt_manager = FormUtil.getTxtField({
			    fieldLabel: '经办人',
			    name: 'manager',
			    "width": 125,
			    "allowBlank": false,
			    readOnly : true,
			    value : CURENT_EMP
			});
			
			var bdat_mgrDate = FormUtil.getDateField({
			    fieldLabel: '办理日期',
			    name: 'mgrDate',
			    "width": 125,
			    "allowBlank": false,
			    isdeaulft : true
			});
			
			/**
			 * 重新加载提前还款计算面板数据
			 */
			function reloadDetail(){
				var contractId = txt_contractId.getValue();
				var date = bdat_adDate.getValue();
		    	var xpayDate = date.format('Y-m-d');
		    	var params = {contractId:contractId,xpayDate:xpayDate};
		    	_this.pcDetailPanel.reload(params);
			}
			
			var bdat_adDate = FormUtil.getDateField({
			    fieldLabel: '提前还款日期',
			    name: 'adDate',
			    "width": 125,
			    "allowBlank": false,
			    listeners : {select : function(field, date){
			    	reloadDetail();
			    }}
			});
			
			var cbo_treatment = FormUtil.getLCboField({
			    fieldLabel: FormUtil.REQUIREDHTML +'部分提前还款方式',
			    name: 'treatment',
			    "width": 180,
			    //"allowBlank": false,
			    hidden : true,
			    "data": [["2", "缩短还款年限，月还款额基本不变"], ["3", "减少月还款额，还款期不变"]]
			});
			
			var mon_adamount = FormUtil.getMoneyField({
			    fieldLabel: '预计提前还款额',
			    name: 'adamount',
			    "width": 125,
			    hidden : true,
			    "allowBlank": false,
			    "value": "0",
			    unitText : "元"
			});
			
			var dob_frate = FormUtil.getIntegerField({
			    fieldLabel: '提前还款手续费率',
			    name: 'frate',
			    "width": 125,
			    "value": "0",
			    "decimalPrecision": "2",
			     unitText : "%",
			    listeners : {change : function(field, newVal, oldVal){
			    	reloadDetail();
			    }}
			});
			
			var rad_isretreat = FormUtil.getRadioGroup({
			    fieldLabel: '是否退息费',
			    name: 'isretreat',
			    "width": 150,
			    "allowBlank": false,
			    "maxLength": 10,
			    "items": [{
			        "boxLabel": "不退息费",
			        "name": "isretreat",
			        "inputValue": 1,
			        checked : true
			    },
			    {
			        "boxLabel": "退息费",
			        "name": "isretreat",
			        "inputValue": 2
			    }],
			    listeners : {change : function(field, newVal, oldVal){
			    	reloadDetail();
			    }}
			});
			
			var txt_tel = FormUtil.getTxtField({
			    fieldLabel: '联系电话',
			    name: 'tel',
			    "width": 125,
			    "maxLength": "30"
			});
			
			var bdat_appDate = FormUtil.getDateField({
			    fieldLabel: '申请日期',
			    name: 'appDate',
			    "width": 125,
			    "allowBlank": false
			});
			
			var txt_appMan = FormUtil.getTxtField({
			    fieldLabel: '申请人',
			    name: 'appMan',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "30"
			});
			
			var txt_reason = FormUtil.getTAreaField({
			    fieldLabel: '提前还款原因',
			    name: 'reason',
			    "width": 550,
			    "allowBlank": false,
			    "maxLength": 800
			});
			
			this.createAttachMentFs();
			this.createPcDetailPanel();
			var layout_fields = [
			txt_id, txt_code, txt_isenabled, txt_totalAmount, txt_payplanId, txt_freeamount, txt_managerId, txt_contractId, txt_procId, txt_breed, txt_imamount, {
			    cmns: FormUtil.CMN_THREE,
			    fields: [cbo_ptype, txt_manager, bdat_mgrDate, bdat_adDate, cbo_treatment, mon_adamount, dob_frate, rad_isretreat, txt_tel, bdat_appDate, txt_appMan]
			},
			{
			    cmns: FormUtil.CMN_TWO_LEFT,
			    fields: [txt_reason,this.attachMentFs]
			},this.pcDetailPanel];
			
			var btnTempSaveHtml = this.getButtonHml(this.btnIdObj.btnTempSave,'暂存申请单');
			var btnSaveHtml = this.getButtonHml(this.btnIdObj.btnSave, '提交申请单');

			var title = '提前还款申请单信息&nbsp;&nbsp;'
					+ btnTempSaveHtml
					+ btnSaveHtml
					+ '提示：[<span style="color:red;">带"*"的为必填项，金额默认单位为 "元"</span>]';
			var frm_cfg = {
				title : title,
				autoScroll : true,
				url : './fcPrepayment_save.action',
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
		 *根据指定的值显示或隐藏 部分提前还款方式下拉框，预计提前还款额字段
		 */
		showOrHideFields : function(newVal){
			var cbo_treatment = this.applyPanel.findFieldByName("treatment");
			var mon_adamount = this.applyPanel.findFieldByName("adamount");
			var flag = true;
	    	if(newVal == "3"){
	    		flag = false;
	    		cbo_treatment.show();
	    		mon_adamount.show();
	    	}else{
	    		cbo_treatment.reset();
	    		cbo_treatment.hide();
	    		mon_adamount.reset();
	    		mon_adamount.hide();
	    	}
			this.applyPanel.setAllowBlanks("treatment,adamount",flag);
		},
		/**
		 * 计算提前还款手续费或应退息费
		 * @param 取值字段
		 * @param  isPrefreet 是否提前还款手续费计算 true : 是, false : 退息费计算
		 */
		calculateFree : function(field,isPrefreet){
			var objVal = this.applyPanel.getValuesByNames('payplanId,frate,adDate');
			var _params = {planId : objVal.payplanId};
			var val = field.getValue();
			var isRemoteCal = true;/*当不允许退息费时，此标记为假*/
			if(isPrefreet){/*计算提前还款手续费*/
				_params["freeType"] = 1;
				_params["frate"] = objVal.frate;
				isRemoteCal = true;
			}else{/*计算应退息费*/
				if(val && val == 2){/*允许退息费*/
					_params["freeType"] = 2;
					_params["adDate"] = objVal.adDate;
					isRemoteCal = true;
				}else{
					isRemoteCal = false;
				}
			}
			if(isRemoteCal){
				var _this = this;
				EventManager.get('./fcPrepayment_getFreeAmount.action',{params:_params,sfn:function(json_data){
					var amount = json_data.amount;
			 		if(isPrefreet){
			 			_this.calTotalAmount("freeamount",_this.freeamountTdId,amount);
			 		}else{
			 			_this.calTotalAmount("imamount",_this.imamountTdId,amount);
			 		}
				}});
			}else{
				this.amountData["imamount"] = 0;
				this.calTotalAmount("imamount",this.imamountTdId,0);
			}
		},
		/**
		 * 计算提前还款手续费或应退息费并更应提前还款应收合计
		 */
		calTotalAmount : function(fieldName,labelId,upAmount){
			this.amountData[fieldName] = upAmount;
			var plusKeys = ["reprincipal","principal","interest","mgrAmount","zinterests","zmgrAmounts","zpenAmounts","zdelAmounts","freeamount"];
			var totalAmount = 0;
			for(var i=0,count=plusKeys.length; i<count; i++){
				var val = this.amountData[plusKeys[i]] || 0;
				totalAmount += parseFloat(val);
			}
			
			var imamount = this.amountData["imamount"] || 0;
			totalAmount -= parseFloat(imamount);
			totalAmount = StringHandler.forDight(totalAmount,2);
			this.applyPanel.setFieldValue("totalAmount",totalAmount);
			var labelEl = Ext.get(this.totalAmountTdId);
			if(labelEl){
				totalAmount =  Cmw.getThousandths(totalAmount)+"元&nbsp;&nbsp;<span style='font-weight:bold;color:red;'>("+Cmw.cmycurd(totalAmount)+")</span>";
				labelEl.update(totalAmount);
			}
			
			this.applyPanel.setFieldValue(fieldName,upAmount);
			labelEl = Ext.get(labelId);
			var upAmount = Cmw.getThousandths(upAmount)+"元";
			if(labelEl) labelEl.update(upAmount);
		},
		/**
		 * 创建提前还款计算详情面板
		 */
		createPcDetailPanel : function(){
			var _this = this;
			var htmlArrs_1 = [
						'<tr><th col="xpayDate">最近一期还款日</th> <td col="xpayDate" >&nbsp;</td><th col="xphases">总期数</th> <td col="xphases" >&nbsp;</td><th col="yphases">已还期数</th> <td col="yphases" >&nbsp;</td></tr>',
						'<tr><th col="yprincipals">已还本金</th> <td col="yprincipals" >&nbsp;</td><th col="reprincipal">剩余本金</th> <td col="reprincipal" >&nbsp;</td><th col="phases">当期期数</th> <td col="phases" >&nbsp;</td></tr>',
						'<tr><th col="principal">当期本金</th> <td col="principal" >&nbsp;</td><th col="interest">当期利息</th> <td col="interest" >&nbsp;</td><th col="mgrAmount">当期管理费</th> <td col="mgrAmount" >&nbsp;</td></tr>',
						'<tr><th col="overCount">逾期期数</th> <td col="overCount" >&nbsp;</td><th col="zinterests">逾期利息</th> <td col="zinterests" >&nbsp;</td><th col="zmgrAmounts">逾期管理费</th> <td col="zmgrAmounts" >&nbsp;</td></tr>',
						'<tr><th col="zpenAmounts">逾期罚息</th> <td col="zpenAmounts" >&nbsp;</td><th col="zdelAmounts">逾期滞纳金</th> <td col="zdelAmounts" >&nbsp;</td><th col="freeamount">提前还款手续费</th> <td id="'+this.freeamountTdId+'" col="freeamount" >&nbsp;</td></tr>',
						'<tr><th col="imamount">应退息费</th> <td id="'+this.imamountTdId+'" col="imamount" >&nbsp;</td><th col="totalAmount">应收合计</th> <td id="'+this.totalAmountTdId+'" col="totalAmount" colspan=3>&nbsp;</td></tr></tr>'];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 115,
			    /* title : '#TITLE#', */
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './fcPrepayment_getCal.action',
			    params: {
			        id: -1
			    },
			    callback: {
			        sfn: function(jsonData) {
			        	var planId = jsonData["planId"] || "";
			        	_this.applyPanel.setFieldValue("payplanId",planId);
			        	if(_this.checkError(jsonData)) return;
			        	_this.setAmountData(jsonData);
			        	var frateField = _this.applyPanel.findFieldByName("frate");
			        	var isretreatField = _this.applyPanel.findFieldByName("isretreat");
			      		_this.calculateFree(frateField,true);
			      		_this.calculateFree(isretreatField,false);
			      		_this.fmtJsonData(jsonData);
			        }
			    }
			}];
			this.pcDetailPanel = new Ext.ux.panel.DetailPanel({
				title : '提前还款计算 >> ',
			    detailCfgs: detailCfgs_1,
			    isLoad : false
			});
		},
		/**
		 * 检查提前还款计算结查
		 */
		checkError : function(jsonData){
			var planId = jsonData["planId"];
			var phases = jsonData["phases"];
			var yphases = jsonData["yphases"];
			var dat_adDate = this.applyPanel.findFieldByName("adDate");
			var adDate = dat_adDate.getValueAsStr();
			this.errArr = [];
			var hasErr = false;
			if(!planId){
				this.errArr[this.errArr.length] = "经过提前还款试算后发现["+adDate+"]不能进行提前还款!";
				hasErr = true;
			}else{
				if(phases <= yphases){
					this.errArr[this.errArr.length] = "经过提前还款试算后发现["+adDate+"]所对应的第["+phases+"]期应还本息费已经结清，请选择一个未结清的日期来提前还款!";
					hasErr = true;
				}
			}
			if(!hasErr) this.errArr = [];
			return hasErr;
		},
		/**
		 * 格式化 jsonData 数据
		 */
		fmtJsonData : function(jsonData){
			var arr = ["phases","xphases","yphases"];
			for(var i=0,count=arr.length; i<count; i++){
				var key = arr[i];
				var val = jsonData[key];
				if(val) jsonData[key] = val + "期";
			}
			jsonData["frate"] = (jsonData["frate"]) ? jsonData["frate"]+"%" : "";
			
			arr = ["yprincipals","reprincipal","principal","interest","mgrAmount","zinterests","zmgrAmounts","zpenAmounts","zdelAmounts","freeamount","imamount","totalAmount"];
			for(var i=0,count=arr.length; i<count; i++){
				var key = arr[i];
				var val = jsonData[key];
				if(val) jsonData[key] = Cmw.getThousandths(val)+"元";
			}
		},
		/**
		 * 设置金额数据
		 */
		setAmountData : function(jsonData){
			var keys = ["reprincipal","principal","interest","mgrAmount","zinterests","zmgrAmounts","zpenAmounts","zdelAmounts","freeamount","imamount","totalAmount"];
			if(!this.amountData) this.amountData = {};
			for(var i=0,count=keys.length; i<count; i++){
				key = keys[i];
				this.amountData[key] = jsonData[key];
			}
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
			this.attachMentFs = new  Ext.ux.AppAttachmentFs({title:'相关附件上传',isLoad:false,dir : 'prepayment_path',isSave:true});
		},
		/**
		 * 打开展期客户选择窗口
		 */
		openCustomerDialog : function(){
			var _this = this;
		    var _contractId = this.applyPanel.getValueByName("contractId");
		    if(_contractId){
		    	ExtUtil.confirm({title:'提示',msg:'确定重新选择客户吗?',fn:function(btn){
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
			var contractId = id;
			var formData = {contractId : id,frate:data.arate};
			this.customerPanel.reload({json_data:data},true);
			this.applyPanel.enable();
			this.applyPanel.setJsonVals(formData);
			var adDate = this.applyPanel.getValueByName("adDate");
			if(adDate) this.pcDetailPanel.reload({contractId:contractId,xpayDate:adDate});
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
						tabId = CUSTTAB_ID.tempPrepaymentApplyMgrTabId;
						Cmw.activeTab(apptabtreewinId, tabId, params);
					} else {  /* 跳转到 业务审批页面 */
						var code = formDatas["code"];
						var procId = _this.applyPanel.getValueByName("procId");
						 ExtUtil.confirm({title:'提示',msg:'确定提交当前提前还款申请单?',fn:function(){
						 		tabId = CUSTTAB_ID.bussProcc_auditMainUITab.id;
								params = {applyId:_this.applyId,procId:procId};
								url = CUSTTAB_ID.bussProcc_auditMainUITab.url;
								var title =  '提前还款业务审批';
								Cmw.activeTab(apptabtreewinId, tabId, params, url, title);
						 }});
					}
					_this.parentPanel.refresh();
				}
			});
		},
		/**
		 * 申请表单数据验证
		 */
		validApplyForm : function() {
			var errMsg = [];
			if(this.errArr && this.errArr.length > 0) errMsg = errMsg.concat(this.errArr);
			//提前还款类别
			var ptype_val = this.applyPanel.getValueByName("ptype");
			var adamount = this.applyPanel.getValueByName("adamount");
			if (ptype_val && ptype_val == 3 && (!adamount || adamount<=0)) {/*部分提前还款*/
				errMsg[errMsg.length] = "当选择的是\"部分提前还款\"时，输入的\"预计提前还款额\"必须大于0!";
			}
			if (null != errMsg && errMsg.length > 0) {
				var msg = errMsg.join("</br>");
				ExtUtil.alert({msg : msg});
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
				this.applyPanel.setValues('./fcPrepayment_get.action', {params : {id : applyId},sfn: function(json_data){
					contractId = json_data["contractId"];
					var adDate = _this.applyPanel.getValueByName("adDate");
					_this.pcDetailPanel.reload({contractId:contractId,xpayDate:adDate});
					_this.showOrHideFields(json_data.treatment);
				}});
			
				this.attachMentFs.reload(this.getAttachParams(applyId));
			}else{/*新增*/
				this.attachMentFs.params = this.getAttachParams(this.uuid);
				this.applyPanel.setJsonVals({breed:breed});
				this.applyPanel.enable();/*不能少，如果少了Flash上传控件会报错*/
				this.applyPanel.disable();
			}
		},
		/**
		 * 获取附件参数
		 * @param formId 业务单ID
		 */
		getAttachParams : function(formId){
			var sysId = this.params.sysid;
			return {sysId:sysId,formType:ATTACHMENT_FORMTYPE.FType_32,formId:formId};
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
				 this.appMainPanel.destroy();
				 this.appMainPanel = null;
			}
		}
	}
});