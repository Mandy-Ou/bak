/**
 * 随借随还----《息费返还》界面
 * @author chengmingwei
 * @date 2014-02-19
 */
define(function(require, exports) {
	exports.WinEdit = {
		setHold : false,
		parentCfg : null,
		params : null,
		parent : null,
		apptbar : null,
		appDetailPnl : null,
		mainPanel : null,
		appFrm : null,
		appWin : null,
		bussKey : null,/* 钥匙KEY */
		RANDOM_ALGORITHM : null,/*随借随还利息算法 参考系统参数，由./fcCurrent_get.action 方法传入 */
		sysId : null,
		cellIdPrefix : Ext.id(null,"currentDetail_amountCellId_"),
		vtempCode : VOUCHERTEMP_CODE.NOMALDEDUCT,/*正常扣收凭证模板编号 constant.js定义*/
		payDate : null,/*放款日期*/
		lastDate : null,/*上次收款日期*/
		submitDatas : null,
		display_dateSpanId : Ext.id(null,"display_dateSpanId_"),/*表单标题，右边日期显示Span ID*/
		errArr : null,/*错误信息数组*/
		/**
		 * 设置参数
		 */
		setParams : function(parentCfg){
			this.parentCfg = parentCfg;
			this.parent = parentCfg.parent;
			this.bussKey = parentCfg.bussKey;
			this.sysId =  parentCfg.parent.sysId;
		},
		/**
		 * 创建window 窗口
		 */
		createAppWindow : function(){
			this.apptbar = this.createAppTbar();
			this.appDetailPnl = this.createAppDetailPn();
			this.appFrm = this.createAppFrm();
			this.mainPanel = new Ext.Panel({items:[this.appDetailPnl,this.appFrm]});
			this.appWin = new Ext.ux.window.MyWindow({
						resizable:false,
						width : 865,
						height : 420,
						autoScroll : true,
						tbar : this.apptbar,
						items : [this.mainPanel],
						listeners : {
							'hide' : function(win) {
								LockManager.releaseLock();
							}
						}
					});
		},
		/**
		 * 显示弹出窗口
		 * 
		 * @param _parentCfg
		 *            弹出之前传入的参数
		 */
		show : function(_parentCfg) {
			var _this = this;
			if (_parentCfg)this.setParams(_parentCfg);
			var isFirstLoad = false;
			if (!this.appWin) {
				this.createAppWindow();
			}
			this.appWin.show(this.parent.getEl(),function(){
				_this.load();
			});
		},
		/**
		 * 创建工具栏
		 */
		createAppTbar : function(){
			var _this = this;
			var barItems = [{type:'chk',name:'isVoucher',checked:true,boxLabel:Finsys_Btn_Cfgs.AUTOVOUCHER_BOXLABEL},{
				text : Btn_Cfgs.DO_NOMAL_BTN_TXT, /*-- 收款 --*/
				iconCls : Btn_Cfgs.DO_NOMAL_CLS,
				tooltip : Btn_Cfgs.DO_NOMAL_TIP_BTN_TXT,
				handler : function() {
					_this.saveData();
				}
			}, {
				text : Btn_Cfgs.CLOSE_BTN_TXT, /*-- 关闭 --*/
				iconCls : Btn_Cfgs.CLOSE_CLS,
				tooltip : Btn_Cfgs.CLOSE_TIP_BTN_TXT,
				handler : function() {
					_this.appWin.hide();
				}
			}];
			var appBar = new Ext.ux.toolbar.MyCmwToolbar({aligin : 'right',controls : barItems});
			return appBar;	
		},
		/**
		 * 创建借款合同详情面板
		 */
		createAppDetailPn : function(){
			var _this = this;
			var selId = this.parent.getSelId();
			var idPrefix = this.cellIdPrefix;
			var htmlArrs_1 = [
					'<tr>',
						'<th col="code">借款合同号</th> <td col="code" >&nbsp;</td>',
						'<th col="name">客户名称</th> <td colspan="4" col="name" >&nbsp;</td>',
					'<tr>',
					'<tr>',
						'<th col="payBank">还款银行</th> <td col="payBank" >&nbsp;</td>',
						'<th col="payAccount">还款帐号</th> <td col="payAccount" >&nbsp;</td>',
						'<th col="accName">还款人</th> <td col="accName" >&nbsp;</td>',
					'<tr>',
					'<tr>',
						'<th col="appAmount">贷款金额</th> <td col="appAmount" >&nbsp;</td>',
						'<th col="payType">还款方式</th> <td col="payType" >&nbsp;</td>',
						'<th col="loanLimit">贷款期限</th> <td col="loanLimit" >&nbsp;</td>',
					'<tr>',
					'<tr>',
						'<th col="totalPhases">期数</th> <td col="totalPhases" >&nbsp;</td>',
						'<th col="breedName">业务品种</th> <td col="breedName" >&nbsp;</td>',
						'<th col="realDate">放款日期</th> <td col="realDate" >&nbsp;</td>',
					'<tr>',
					'<tr>',
					'<th col="mrate">管理费率</th> <td col="mrate" >&nbsp;</td>',
						'<th col="urate">罚息利率</th> <td col="urate" >&nbsp;</td>',
						'<th col="frate">滞纳金利率</th> <td col="frate" >&nbsp;</td>',
					'<tr>',
					'<tr><th colspan="6" style="font-weight:bold;text-align:left;" >&nbsp;&nbsp;累计可返还的金额（可返还的金额=实收金额-已返还的金额）>>></th><tr>',
					'<tr>',
						'<th col="lastDate">上次收款日期</th> <td col="lastDate">&nbsp;</td>',
						'<th col="zinterest">利息</th> <td col="yinterest" id="'+idPrefix+'yinterest">&nbsp;</td>',
						'<th col="zmgrAmount">管理费</th> <td col="ymgrAmount" id="'+idPrefix+'ymgrAmount">&nbsp;</td>',
					'<tr>',
					'<tr>',
						'<th col="zpenAmount">罚息</th> <td col="ypenAmount" id="'+idPrefix+'ypenAmount">&nbsp;</td>',
						'<th col="zdelAmount">滞纳金</th> <td col="ydelAmount" id="'+idPrefix+'ydelAmount">&nbsp;</td>',
						'<th col="ztotalAmount">合计</th> <td col="ytotalAmount" id="'+idPrefix+'ytotalAmount">&nbsp;</td>',
					'<tr>'
					];

			var detailCfgs_1 = [{
				cmns : 'THREE',
				/* ONE , TWO , THREE */
				model : 'single',
				labelWidth : 90,
				title : '',
				// 详情面板标题
				/* i18n : UI_Menu.appDefault, */
				// 国际化资源对象
				htmls : htmlArrs_1,
				url : './fcCurrent_get.action',
				params : {
					id : selId,
					key : this.bussKey
				},
				callback : {
					sfn : function(jsonData) {
						_this.callback(jsonData);
					}
				}
			}];
			var detailPanel = new Ext.ux.panel.DetailPanel({
				frame : false,
				width : 850,
				isLoad : false,
				border : false,
				detailCfgs : detailCfgs_1
			});
			return detailPanel;
		},
		/**
		 * 创建form面板
		 */
		createAppFrm : function(){
			var _this = this;
			var txt_contractId = FormUtil.getHidField({fieldLabel : '合同ID',name : 'contractId'});
		
			var txt_zinterest = FormUtil.getHidField({
					fieldLabel : '实收利息',
					name : 'yinterest'
				});
			var txt_zmgrAmount = FormUtil.getHidField({
					fieldLabel : '实收管理费',
					name : 'ymgrAmount'
				});
			var txt_zpenAmount = FormUtil.getHidField({
				fieldLabel : '实收罚息',
				name : 'ypenAmount'
			});
			var txt_zdelAmount = FormUtil.getHidField({
				fieldLabel : '实收滞纳金',
				name : 'ydelAmount'
			}); 
			var txt_ztotalAmount = FormUtil.getHidField({
					fieldLabel : '实收合计',
					name : 'ytotalAmount'
				});
				
				var callback = function(cboGrid, selVals) {
				var grid = cboGrid.grid;
				var record = grid.getSelRow();
				var account = record.get("account");
				txt_bankAccount.setValue(account);
			}
			
			var cbog_accountId = ComboxControl.getAccountCboGrid({
				fieldLabel : '收款银行',
				name : 'accountId',
				allowBlank : false,
				readOnly : true,
				isAll : false,
				width : 150,
				callback : callback,
				params : {
					isIncome : 1,
					isenabled:1,
					sysId : _this.sysId
				}
			});

			var txt_bankAccount = FormUtil.getReadTxtField({
				fieldLabel : '银行帐号',
				name : 'bankAccount',
				width : 160
			});
					
			var dat_rectDate = FormUtil.getDateField({
				fieldLabel : '返还日期',
				name : 'rectDate',
				"width" : 125,
				"allowBlank" : false,
				editable : false,
				"maxLength" : 50
			});
		
			var num_interest = FormUtil.getMoneyField({
						fieldLabel : '利息',
						name : 'rat',
						allowBlank : false,
						width : 100,
						unitText : '元',
						listeners : {
							change : function(field,newVal, oldVal){_this.collectAmounts();}
						}
					});
			
			var num_mgrAmount = FormUtil.getMoneyField({
				fieldLabel : '管理费',
				name : 'mat',
				allowBlank : false,
				width : 100,
				unitText : '元',
				listeners : {
					change : function(field,newVal, oldVal){_this.collectAmounts();}
				}
			});
			
			var num_penAmount = FormUtil.getMoneyField({
				fieldLabel : '罚息',
				name : 'pat',
				allowBlank : false,
				width : 100,
				unitText : '元',
				listeners : {
					change : function(field,newVal, oldVal){_this.collectAmounts();}
				}
			});
			
			var num_delAmount = FormUtil.getMoneyField({
				fieldLabel : '滞纳金',
				name : 'dat',
				allowBlank : false,
				width : 100,
				unitText : '元',
				listeners : {
					change : function(field,newVal, oldVal){_this.collectAmounts();}
				}
			});
	
			var num_tat = FormUtil.getMoneyField({
				fieldLabel : '返还合计',
				name : 'tat',
				allowBlank : false,
				width : 100,
				readOnly : true,
				unitText : '元'
			});
			
			var layout_fields = [txt_contractId,txt_zinterest,txt_zmgrAmount,txt_zpenAmount,txt_zdelAmount,txt_ztotalAmount,{
						cmns : FormUtil.CMN_THREE,
						fields : [cbog_accountId,txt_bankAccount,dat_rectDate,num_interest,num_mgrAmount,num_penAmount,num_delAmount,num_tat]
			}];
				
			var frm_cfg = {
				title : '本次息费返还信息录入<span style="float:right;color:red;" id="'+_this.display_dateSpanId+'"></span>',
				labelWidth : 95,
				url : './fcReturn_save.action'
			};
			var appform = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform;
		},
		/**
		 * 计算实收合计金额
		 */
		collectAmounts : function(msg,val){
			var txt_tat = this.appFrm.findFieldByName("tat");
			var zvals = this.appFrm.getValuesByNames("yinterest,ymgrAmount,ypenAmount,ydelAmount");
			var cvals = this.appFrm.getValuesByNames("rat,mat,pat,dat");
			//---> 计算实收
			var tat = 0;
			for(var key in cvals){
				var val = cvals[key] || 0;
				tat += parseFloat(val);
			}
			tat = StringHandler.forDight(tat,2);
			txt_tat.setValue(tat);
		},
		/**
		 * 	加载数据
		 */
		load : function(){
			var selId = null;
			if(this.parent){
				selId = this.parent.getSelId();
			}
			this.clear();
			this.appDetailPnl.reload({id : selId});
		},
		/**
		 * 当数据保存成功后，执行刷新方法 [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data) {
			var _this = exports.WinEdit;
			if (_this.parentCfg.self.refresh) _this.parentCfg.self.refresh(_this.optionType, data);
		},
		/**
		 * 加载借款合同详情数据
		 */
		callback : function(jsonData){
			this.appFrm.reset();
			this.RANDOM_ALGORITHM = jsonData["RANDOM_ALGORITHM"];
			var realDate = jsonData["realDate"];
			this.payDate = realDate;
			this.lastDate = jsonData["lastDate"];
			this.setRealYamounts(jsonData);
			this.setValueData(jsonData);
			this.setDisplayDateInfo();
			var custType = Render_dataSource.custTypeRender(jsonData.custType);
			jsonData['name'] = jsonData['name'] +'<span style="color:red;">('+custType+')</span>';
			var totalPhases = jsonData['totalPhases'];
			var totalOverPharses = jsonData['totalOverPharses'] || 0;
			var paydPhases = jsonData['paydPhases'];
			jsonData['totalPhases'] = "共"+totalPhases+"期,逾期"+totalOverPharses+"期,已还"+paydPhases+"期";
			var principal = parseFloat(jsonData['principal']);
			var appAmount = parseFloat(jsonData["appAmount"]);
			
			
			var isadvance = jsonData["isadvance"];
			if(isadvance && isadvance == "1"){
				isadvance = '<span style="color:red;">(提前收息)</span>';
			}else{
				isadvance = "";
			}
			var inRateType = jsonData["inRateType"];
			if(inRateType){
				inRateType = Render_dataSource.rateTypeRender(inRateType)
			}else{
				inRateType = "";
			}
			
			realDate = realDate + isadvance;
			jsonData["realDate"] = realDate;
			jsonData["appAmount"] = Cmw.getThousandths(appAmount)+ '元<br/><span style="color:red;">('+ Cmw.cmycurd(appAmount) + ')</span>';
			jsonData["payType"] = Render_dataSource.payTypeRender(jsonData["payType"])+
			'<br/><span style="color:red;">(利率类型:'+Render_dataSource.rateTypeRender(jsonData["rateType"])+'&nbsp;'+jsonData["rate"]+'%)</span>';
			jsonData["loanLimit"] = jsonData["loanLimit"]+ '<br/><span style="color:red;">(内部利率类型:'+inRateType+'&nbsp;'+jsonData["inRate"]+'%)</span>';
			var array = ['mrate','prate','urate','frate'];
			for (var index = 0; index < array.length; index++) {
				jsonData[array[index]] = jsonData[array[index]] +'%';
			}
			var amountArr = ["yinterest","ymgrAmount","ypenAmount","ydelAmount","ytotalAmount"];
			for(var i=0,count=amountArr.length; i<count; i++){
				var key = amountArr[i];
				var val = jsonData[key];
				val = (val) ? Cmw.getThousandths(val)+ '元' : '0元';
				jsonData[key] = val;
			}
		},
		/**
		 * 
		 * 设置真正的实收 (实收 = 实收 - 返还)
		 */
		setRealYamounts : function(jsonData){
			var keys = ["yinterest","ymgrAmount","ypenAmount","ydelAmount"];
			var fieldNames = ["riamount","rmamount","rpamount","rdamount"];
			var ytotalAmount_val = 0;
			for(var i=0,count=keys.length; i<count; i++){
				var key = keys[i];
				var val = jsonData[key];
				var fieldName = fieldNames[i];
				var rval = jsonData[fieldName];
				if(!val) val = 0;
				if(!rval) rval = 0;
				if(Ext.isString(val)) val = parseFloat(val);
				if(Ext.isString(rval)) rval = parseFloat(rval);
				val -= rval;
				jsonData[key] = StringHandler.forDight(val,2);
			}
		},
		setDisplayDateInfo : function(){
			var ele = Ext.get(this.display_dateSpanId);
			if(!ele) return;
			var htmlArr = ["放款日期:",this.payDate];
			if(this.lastDate){
				htmlArr[htmlArr.length] = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				htmlArr[htmlArr.length] = "上次收款日期:"+this.lastDate;
			}
			ele.update("("+htmlArr.join(" ")+")");
		},
		/**
		 * 为formPnl 赋值
		 */
		setValueData : function(jsonData){
			this.appFrm.setFieldValues(jsonData);
			var keys = ["yinterest","ymgrAmount","ypenAmount","ydelAmount"];
			var fieldNames = ["rat","mat","pat","dat"];
			var ytotalAmount_val = 0;
			var formDatas = {};
			for(var i=0,count=keys.length; i<count; i++){
				var key = keys[i];
				var val = jsonData[key];
				var fieldName = fieldNames[i];
				formDatas[fieldName] = val;
				if(!val) val = 0;
				ytotalAmount_val += parseFloat(val);
			}
			if(ytotalAmount_val > 0)ytotalAmount_val = StringHandler.forDight(ytotalAmount_val,2);
			formDatas["ytotalAmount"] = ytotalAmount_val;
			formDatas["tat"] = ytotalAmount_val;
			this.appFrm.setJsonVals(formDatas);
		},
			/**
		 * 检查收款日期是否合法
		 */
		checkDate : function(rectDate){
		//	if(!this.errArr || this.errArr.length == 0) this.errArr = [];
			this.errArr = [];
			var rectDate_time = rectDate.getTime();
			var emsg = null;
			if(this.payDate && Ext.isString(this.payDate)){
				var date_payDate = Date.parseDate(this.payDate, "Y-m-d");
				payDate_time = date_payDate.getTime();
				if(rectDate_time < payDate_time){
					var payDateStr = date_payDate.dateFormat('Y-m-d');
					emsg = "息费返还日期不得小于"+payDateStr+"(即：不能小于放款日期)!";
					ExtUtil.alert({msg:emsg});
					this.errArr[this.errArr.length] = emsg;
					return false;
				}
			}
			if(this.lastDate && Ext.isString(this.lastDate)){
				var date_lastDate = Date.parseDate(this.lastDate, "Y-m-d");
				lastDate_time = date_lastDate.getTime();
				if(rectDate_time < lastDate_time){
					var lastDateStr = date_lastDate.dateFormat('Y-m-d');
					emsg = "息费返还日期不得小于"+lastDateStr+"(即：不能小于上次收款日期)!";
					ExtUtil.alert({msg:emsg});
					this.errArr[this.errArr.length] = emsg;
					return false;
				}
			}
			return true;
		},
		checkItemAmounts : function(){
			var flag = true;
			var chkCfgArr = [{fld:'yinterest',fld2:'rat',msg:'利息'},
				{fld:'ymgrAmount',fld2:'mat',msg:'管理费'},
				{fld:'ypenAmount',fld2:'pat',msg:'罚息'},
				{fld:'ydelAmount',fld2:'dat',msg:'滞纳金'}];
			for(var i=0,count=chkCfgArr.length; i<count; i++){
				var cfg = chkCfgArr[i];
				var fld = cfg.fld;
				var fld2 = cfg.fld2;
				var msg = cfg.msg;
				var data = this.appFrm.getValuesByNames(fld+","+fld2);
				var val = data[fld] || 0;
				var val2 = data[fld2] || 0;
				if(Ext.isString(val)) val = parseFloat(val);
				if(Ext.isString(val2)) val2 = parseFloat(val2);
				var result = val2 - val;
				if(result > 0){
					ExtUtil.alert({msg:"返还<b>"+msg+"</b>不能超过"+val+"元!"});
					return false;
				}
			}
			return flag;
		},
		/**
		 * 保存数据
		 */
		saveData : function() {
			var _this = this;
			var errMsg = [];
			if(this.errArr && this.errArr.length > 0) errMsg = errMsg.concat(this.errArr);
			
			var dat_rectDate = this.appFrm.findFieldByName("rectDate");
			var rectDateVal = dat_rectDate.getValue();
			if (!rectDateVal) {
				errMsg[errMsg.length] =  '返还日期不能为空!';
			}
			
			var num_tat = this.appFrm.findFieldByName("tat");
			var tat = num_tat.getValue();
			if(!tat || tat<=0){
				errMsg[errMsg.length] =  '本次返还合计必须大于0!';
			}
			var num_ytotalAmount = this.appFrm.findFieldByName("ytotalAmount");
			var ytotalAmount = num_ytotalAmount.getValue();
			if(!tat) tat = 0;
			if(!ytotalAmount) ytotalAmount = 0;
			if(parseFloat(tat) > parseFloat(ytotalAmount)){
				errMsg[errMsg.length] =  '本次返还合计不能超过:"'+ytotalAmount+'"元!';
			}
			
			if(!this.checkItemAmounts()) return;
			
			if(errMsg && errMsg.length > 0){
				ExtUtil.alert({msg : '以下项不正确:'+errMsg.join("&nbsp;&nbsp;&nbsp;&nbsp;<br/>")});
				return;
			}
			
			ExtUtil.confirm({msg : '本次返还合计：<span style="color:blue;font-weight:bold;">'+tat+'</span>元，确定返还?',fn:function(){
				if(arguments && arguments[0] != 'yes') return;
				save();
			}});
			function save(saveOddamount){
				_this.apptbar.disable();
				EventManager.frm_save(_this.appFrm, {
					beforeMake : function(formDatas) {
						formDatas.key = _this.bussKey;
						formDatas.RANDOM_ALGORITHM = _this.RANDOM_ALGORITHM;
						formDatas.bussType = 1;/*业务类型:随借随还息费返还*/
						var isVoucher = _this.apptbar.getValue("isVoucher");
						formDatas.isVoucher = isVoucher ? 1 : 0;/* 1 : 表示自动生成财务凭证*/
						formDatas.vtempCode = _this.vtempCode;
						formDatas.sysId = _this.sysId;
						if(_this.submitDatas){
							Ext.apply(formDatas,_this.submitDatas);
						}
					},
					sfn : function(formDatas) {
						_this.refresh();
						_this.close();
						_this.apptbar.enable();
					},ffn : function(formDatas) {
						_this.apptbar.enable();
					}
				});
			}
		},
		clear : function(){
			this.submitDatas = null;
			this.errArr = null;
			this.payDate = null;
 			this.lastDate = null;
 			this.RANDOM_ALGORITHM = null;
		},
		/**
		 * 重置数据
		 */
		resetData : function() {
			this.clear();
			this.appFrm.reset();
		},
		/**
		 * 关闭窗口
		 */
		close : function() {
			this.resetData();
			this.appWin.hide();
		}
	}
});
