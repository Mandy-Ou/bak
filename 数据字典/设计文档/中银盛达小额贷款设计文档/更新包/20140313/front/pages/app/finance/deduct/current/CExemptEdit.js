/**
 * 随借随还收款界面
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
						height : 400,
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
					'<tr><th colspan="6" style="font-weight:bold;text-align:left;" >&nbsp;&nbsp;累计未收金额（可豁免的金额）>>></th><tr>',
					'<tr>',
						'<th col="lastDate">上次收款日期</th> <td col="lastDate">&nbsp;</td>',
						'<th col="zinterest">利息</th> <td col="zinterest" id="'+idPrefix+'zinterest">&nbsp;</td>',
						'<th col="zmgrAmount">管理费</th> <td col="zmgrAmount" id="'+idPrefix+'zmgrAmount">&nbsp;</td>',
					'<tr>',
					'<tr>',
						'<th col="zpenAmount">罚息</th> <td col="zpenAmount" id="'+idPrefix+'zpenAmount">&nbsp;</td>',
						'<th col="zdelAmount">滞纳金</th> <td col="zdelAmount" id="'+idPrefix+'zdelAmount">&nbsp;</td>',
						'<th col="ztotalAmount">未收合计</th> <td col="ztotalAmount" id="'+idPrefix+'ztotalAmount">&nbsp;</td>',
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
					fieldLabel : '未还利息',
					name : 'zinterest'
				});
			var txt_zmgrAmount = FormUtil.getHidField({
					fieldLabel : '未还管理费',
					name : 'zmgrAmount'
				});
			var txt_zpenAmount = FormUtil.getHidField({
				fieldLabel : '未还罚息',
				name : 'zpenAmount'
			});
			var txt_zdelAmount = FormUtil.getHidField({
				fieldLabel : '未还滞纳金',
				name : 'zdelAmount'
			}); 
			var txt_ztotalAmount = FormUtil.getHidField({
					fieldLabel : '未还合计',
					name : 'ztotalAmount'
				});
				

			var dat_rectDate = FormUtil.getDateField({
						fieldLabel : '豁免日期',
						name : 'rectDate',
						"width" : 125,
						"allowBlank" : false,
						editable : false,
						"maxLength" : 50,
//						format :'Y-m-d',
//						value : new Date().dateFormat('Y-m-d'),
						listeners : {
							select : function(field,date){
								if(!_this.checkDate(date)) return;
							//	_this.fieldChangeListener(date);
							}
						}
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
				fieldLabel : '豁免合计',
				name : 'tat',
				allowBlank : false,
				width : 100,
				readOnly : true,
				unitText : '元'
			});
			
			var layout_fields = [txt_contractId,txt_zinterest,txt_zmgrAmount,txt_zpenAmount,txt_zdelAmount,txt_ztotalAmount,{
						cmns : FormUtil.CMN_THREE,
						fields : [dat_rectDate,num_interest,num_mgrAmount,num_penAmount,num_delAmount,num_tat]
					}];
				
			var frm_cfg = {
				title : '本次豁免信息录入<span style="float:right;color:red;" id="'+_this.display_dateSpanId+'"></span>',
				labelWidth : 95,
				url : './fcCurrent_settle.action'
			};
			var appform = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform;
		},
		/**
		 * 计算实收合计金额
		 */
		collectAmounts : function(){
			var hid_ztotalAmount = this.appFrm.findFieldByName("ztotalAmount");
			var txt_tat = this.appFrm.findFieldByName("tat");
			var zvals = this.appFrm.getValuesByNames("zinterest,zmgrAmount,zpenAmount,zdelAmount");
			var cvals = this.appFrm.getValuesByNames("rat,mat,pat,dat");
			//---> 计算应收
			var fat = cvals.fat || 0;
			var ztotalAmount = 0;
			for(var key in zvals){
				var val = zvals[key] || 0;
				ztotalAmount += parseFloat(val);
			}
			ztotalAmount = StringHandler.forDight(ztotalAmount,2);
			hid_ztotalAmount.setValue(ztotalAmount);
			
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
			this.setValueData(jsonData);
			this.setDisplayDateInfo();
//			this.fieldChangeListener(null);
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
			jsonData["loanLimit"] = jsonData["loanLimit"] +'<br/><span style="color:red;">(内部利率类型:'+inRateType+'&nbsp;'+jsonData["inRate"]+'%)</span>';
			realDate = realDate + isadvance;
			jsonData["realDate"] = realDate;
			jsonData["appAmount"] = Cmw.getThousandths(appAmount)+ '元<br/><span style="color:red;">('+ Cmw.cmycurd(appAmount) + ')</span>';
			jsonData["payType"] = Render_dataSource.payTypeRender(jsonData["payType"])+
			'<br/><span style="color:red;">(利率类型:'+Render_dataSource.rateTypeRender(jsonData["rateType"])+'&nbsp;'+jsonData["rate"]+'%)</span>';
			var array = ['mrate','prate','urate','frate'];
			for (var index = 0; index < array.length; index++) {
				jsonData[array[index]] = jsonData[array[index]] +'%';
			}
			var zprincipal = jsonData["zprincipal"] || 0;
			zprincipal = parseFloat(zprincipal);
			var ztotalAmount = jsonData["ztotalAmount"] || 0;
			ztotalAmount = parseFloat(ztotalAmount);
			if(zprincipal > 0){
				ztotalAmount -= zprincipal;
				ztotalAmount = StringHandler.forDight(ztotalAmount,2);
				jsonData["ztotalAmount"] = ztotalAmount;
			}
			var amountArr = ["zinterest","zmgrAmount","zpenAmount","zdelAmount","ztotalAmount"];
			for(var i=0,count=amountArr.length; i<count; i++){
				var key = amountArr[i];
				var val = jsonData[key];
				val = (val) ? Cmw.getThousandths(val)+ '元' : '0元';
				jsonData[key] = val;
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
			var keys = ["zinterest","zmgrAmount","zpenAmount","zdelAmount"];
			var fieldNames = ["rat","mat","pat","dat"];
			var ztotalAmount_val = 0;
			var formDatas = {};
			for(var i=0,count=keys.length; i<count; i++){
				var key = keys[i];
				var val = jsonData[key];
				var fieldName = fieldNames[i];
				formDatas[fieldName] = val;
				if(!val) val = 0;
				ztotalAmount_val += parseFloat(val);
			}
			if(ztotalAmount_val > 0)ztotalAmount_val = StringHandler.forDight(ztotalAmount_val,2);
			formDatas["ztotalAmount"] = ztotalAmount_val;
			formDatas["tat"] = ztotalAmount_val;
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
					emsg = "豁免日期不得小于"+payDateStr+"(即：不能小于放款日期)!";
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
					emsg = "豁免日期不得小于"+lastDateStr+"(即：不能小于上次收款日期)!";
					ExtUtil.alert({msg:emsg});
					this.errArr[this.errArr.length] = emsg;
					return false;
				}
			}
			return true;
		},
		/**
		 * [未用到]
		 * 计算到指定日期的利息、管理费、罚息、滞纳金
		 * 此方法暂未用到，后期应调整为可以计算 罚息、滞纳金
		 */
		fieldChangeListener : function(rectDate){
			if(this.RANDOM_ALGORITHM == '3'){/*直接扣取还款计划，不需要重新生成还款计划*/
				return;
			}
			this.apptbar.disableButtons(Btn_Cfgs.DO_NOMAL_BTN_TXT); 
			var _this = this;
			if(!rectDate){
				rectDate = new Date();
			}
			
			if(!this.checkDate(rectDate)) return;
			rectDate = rectDate.dateFormat('Y-m-d');
			
			Cmw.mask(this.appFrm,"正在计算,请稍等...");
			var contractId = this.appFrm.getValueByName("contractId");
			var url = './fcCurrent_calculate.action';
			_pars = {contractId:contractId,rectDate:rectDate};
			var _this = this;
			EventManager.get(url,{params:_pars,
			  	sfn:function(json_data){
			  		_this.apptbar.enableButtons(Btn_Cfgs.DO_NOMAL_BTN_TXT); 
			  		_this.setCalculateResult(json_data);
			  		Cmw.unmask(_this.appFrm);
			  		_this.errArr = null;
				},
				ffn:function(json_data){
					_this.apptbar.enableButtons(Btn_Cfgs.DO_NOMAL_BTN_TXT);
					Cmw.unmask(_this.appFrm);
					var msg = json_data.msg;
					ExtUtil.alert({msg:msg});
					if(!_this.errArr){
						_this.errArr = [];
						_this.errArr[_this.errArr.length] = msg;
					}
				}
			});
		},
		/**
		 * 设置计算后的结果[未用到]
		 */
		setCalculateResult : function(json_data){
			var hidFieldNames = "zinterest,zmgrAmount,zpenAmount,zdelAmount,ztotalAmount";
			var txtFieldNames = "interest,mgrAmount,penAmount,delAmount,fat,tat";
			if(!json_data){
				this.appFrm.reset(hidFieldNames+txtFieldNames+",oddAmount");
			}
			if(!this.submitDatas) this.submitDatas = {};
			var t_iamount = json_data.t_iamount || 0;
			var t_mamount = json_data.t_mamount || 0;
			if(Ext.isString(t_iamount)) t_iamount = parseFloat(t_iamount);
			if(Ext.isString(t_mamount)) t_mamount = parseFloat(t_mamount);
			
			var lateDatas = json_data.lateDatas;
			
			if(json_data.planId) this.submitDatas.planId = json_data.planId;
			if(json_data.c_iamount) this.submitDatas.c_iamount = json_data.c_iamount;
			if(json_data.c_mamount) this.submitDatas.c_mamount = json_data.c_mamount;
			if(lateDatas) this.submitDatas.lateDatas = Ext.encode(lateDatas);
			
			var t_penAmount = 0;
			var t_delAmount = 0;
			
			if(lateDatas && lateDatas.length > 0){
				json_data.lateDatas = Ext.encode(lateDatas);
				for(var i=0,count=lateDatas.length; i<count; i++){
					var amountData = lateDatas[i];
					var penAmount = amountData.penAmount || 0;
					var delAmount = amountData.delAmount || 0;
					var ypenAmount = amountData.ypenAmount || 0;
					var ydelAmount = amountData.ydelAmount || 0;
					if(Ext.isString(penAmount)) penAmount = parseFloat(penAmount);
					if(Ext.isString(delAmount)) delAmount = parseFloat(delAmount);
					if(Ext.isString(ypenAmount)) ypenAmount = parseFloat(ypenAmount);
					if(Ext.isString(ydelAmount)) ydelAmount = parseFloat(ydelAmount);
					
					var zpenAmount = penAmount-ypenAmount;
					if(zpenAmount<0) zpenAmount=0;
					t_penAmount += zpenAmount;
					
					var zdelAmount = delAmount-ydelAmount;
					if(zdelAmount<0) zdelAmount=0;
					t_delAmount += zdelAmount;
				}
				if(t_penAmount>0) t_penAmount = StringHandler.forDight(t_penAmount,2);
				if(t_delAmount>0) t_delAmount = StringHandler.forDight(t_delAmount,2);
			}
			
			var zprincipal = this.appFrm.getValueByName("zprincipal");
			var principal = this.appFrm.getValueByName("principal");
			if(principal > 0) principal = parseFloat(principal);
			if(Ext.isString(zprincipal)) zprincipal = parseFloat(zprincipal);
			if(Ext.isString(principal)) principal = parseFloat(principal);
			
			var fat = this.appFrm.getValueByName("fat");
			if(fat) fat = parseFloat(fat);
			var cmmon_amounts = t_iamount + t_mamount + t_penAmount + t_delAmount + fat;
			var totalAmount = zprincipal + cmmon_amounts;
			totalAmount = StringHandler.forDight(totalAmount,2);
			
			var c_totalAmount = principal + cmmon_amounts;
			c_totalAmount = StringHandler.forDight(c_totalAmount,2);
			
			var oddAmount = c_totalAmount - totalAmount;
			if(oddAmount<0) oddAmount = 0;
			oddAmount = StringHandler.forDight(oddAmount,2);
			
			var formDatas = {
				zinterest : t_iamount,
				zmgrAmount: t_mamount,
				zpenAmount : t_penAmount,
				zdelAmount : t_delAmount,
				ztotalAmount : totalAmount,
				interest : t_iamount,
				mgrAmount: t_mamount,
				penAmount : t_penAmount,
				delAmount : t_delAmount,
				tat : c_totalAmount,
				oddAmount : oddAmount
			};
			this.appFrm.setJsonVals(formDatas);
			formDatas.zprincipal = zprincipal;
			var detailCellIds = "zprincipal,zinterest,zmgrAmount,zpenAmount,zdelAmount,ztotalAmount";
			var cellIdArr = detailCellIds.split(",");
			for(var i=0,count=cellIdArr.length; i<count; i++){
				var fieldName = cellIdArr[i];
				var cellId = this.cellIdPrefix + fieldName;
				var ele =  Cmw.$(cellId);
				if(!cellId) continue;
				var val = formDatas[fieldName];
				val = val ? Cmw.getThousandths(val)+ '元' : '0元';
				ele.innerHTML = val;
			}
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
				errMsg[errMsg.length] =  '豁免日期不能为空!';
			}
			
			var num_tat = this.appFrm.findFieldByName("tat");
			var tat = num_tat.getValue();
			if(!tat || tat<=0){
				errMsg[errMsg.length] =  '本次豁免合计必须大于0!';
			}
			var num_ztotalAmount = this.appFrm.findFieldByName("ztotalAmount");
			var ztotalAmount = num_ztotalAmount.getValue();
			if(!tat) tat = 0;
			if(!ztotalAmount) ztotalAmount = 0;
			if(parseFloat(tat) > parseFloat(ztotalAmount)){
				errMsg[errMsg.length] =  '本次豁免合计不能超过未收合计:"'+ztotalAmount+'"元!';
			}
			
			if(errMsg && errMsg.length > 0){
				ExtUtil.alert({msg : '以下项不正确:'+errMsg.join("&nbsp;&nbsp;&nbsp;&nbsp;<br/>")});
				return;
			}
			
			
			ExtUtil.confirm({msg : '本次豁免合计：<span style="color:blue;font-weight:bold;">'+tat+'</span>元，确定豁免?',fn:function(){
				if(arguments && arguments[0] != 'yes') return;
				save();
			}});
		
			function save(saveOddamount){
				_this.apptbar.disable();
				EventManager.frm_save(_this.appFrm, {
					beforeMake : function(formDatas) {
						formDatas.key = _this.bussKey;
						formDatas.RANDOM_ALGORITHM = _this.RANDOM_ALGORITHM;
						formDatas.bussType = 1;/*业务类型:部分豁免*/
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
