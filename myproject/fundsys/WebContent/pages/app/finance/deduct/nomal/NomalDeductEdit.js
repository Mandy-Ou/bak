/**
 * 正常还款金额收取 窗口
 * 
 * @author 程明卫
 * @date 2013-01-30 20:46:32
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		params : null,
		parent : null,
		apptbar : null,
		mainPanel : null,
		appFrm : null,
		appWin : null,
		detailPanlId : Ext.id(null, 'NomalDeductDetailPanl'),
		bussKey : null,/* 钥匙KEY */
		sysId : null,
		contractIds : null,
		uamount : null,
		total :  null,
		errArr : null,/*错误消息*/
		closeFlag : false,/*用来标识是否是关闭窗口事件*/
		vtempCode : VOUCHERTEMP_CODE.NOMALDEDUCT,/*正常扣收凭证模板编号 constant.js定义*/
		setParams : function(parentCfg) {
			this.parentCfg = parentCfg;
			// --> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.sysId = parentCfg.parent.sysId;
			this.contractIds = parentCfg.contractIds;
			this.bussKey = parentCfg.bussKey;
			if (!this.bussKey) {
				ExtUtil.error({msg : 'bussKey 不能为空!'});
			}
			this.errArr = null;
		},
		createAppWindow : function() {
			this.apptbar = this.createToolBar();
			this.mainPanel = this.createMainPanel();
			this.appWin = new Ext.ux.window.MyWindow({
						width : 850,
						height : 475,
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
			this.closeFlag = false;
			if (_parentCfg)this.setParams(_parentCfg);
			var isFirstLoad = false;
			if (!this.appWin) {
				isFirstLoad = true;
				this.createAppWindow();
			}
			this.appWin.optionType = this.optionType;
			this.appWin.show();
			if (isFirstLoad) return;
			this.loadData();
		},
		/**
		 * 加载数据
		 */
		loadData : function() {
			var loanDetailPnl = Ext.getCmp(this.detailPanlId);
			var selId = this.parent.getSelId();
			loanDetailPnl.reload({
						id : selId,
						key : this.bussKey
					});
		},
		/**
		 * 当数据保存成功后，执行刷新方法 [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data) {
			var _this = exports.WinEdit;
			if (_this.parentCfg.self.refresh)
				_this.parentCfg.self.refresh(_this.optionType, data);
		},
		createToolBar : function() {
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
							_this.close();
						}
					}];
			var appBar = new Ext.ux.toolbar.MyCmwToolbar({
						aligin : 'right',
						controls : barItems
					});
			return appBar;
		},
		/**
		 * 创建Form表单
		 */
		createMainPanel : function() {
			var _this = this;
			this.appFrm = this.createAppFrm();
			var selId = this.parent.getSelId();
			var htmlArrs_1 = [
					'<tr><th colspan="2" col="code" style="width:10%;">借款合同号</th> <td col="code" >&nbsp;</td><th colspan="2" col="custName">客户名称</th> <td colspan="6" col="custName" >&nbsp;</td><tr>',
					'<tr><th colspan="2" col="payBank">还款银行</th> <td col="payBank" >&nbsp;</td><th colspan="2" col="payAccount">还款帐号</th> <td colspan="3" col="payAccount" >&nbsp;</td><th colspan="2" col="accName">还款人</th> <td col="accName" >&nbsp;</td><tr>',
					'<tr><th colspan="2" col="appAmount">贷款金额</th> <td col="appAmount" >&nbsp;</td><th colspan="2" col="loanLimit">贷款期限</th> <td colspan="3" col="loanLimit" >&nbsp;</td><th colspan="2" col="xpayDate">应还日期</th> <td col="xpayDate" >&nbsp;</td><tr>',
					'<tr><th colspan="2" col="lastDate">最后还款日</th> <td col="lastDate" >&nbsp;</td><th colspan="2" col="status">还款状态</th> <td colspan="3" col="status" >&nbsp;</td><th colspan="2" col="exempt">豁免状态</th> <td col="exempt" >&nbsp;</td><tr>',
					'<tr><th colspan="2" col="phases">本期期数</th> <td col="phases" >&nbsp;</td><th colspan="2" col="paydPhases">已还期数</th> <td colspan="3" col="paydPhases">&nbsp;</td><th colspan="2" col="totalPhases">总期数</th> <td col="totalPhases" >&nbsp;</td><tr>',
					'<tr><th width="20" rowspan="4">本<br/>期<br/>应<br/>还</th>',
					  '<th width="30" col="principal">本金</th>',
					  '<td col="principal" >&nbsp;</td>',
					  '<th rowspan="4">本<br/>期<br/>实<br/>还</th>',
					  '<th col="yprincipal">本金</th>',
					  '<td col="yprincipal" >&nbsp;</td>',
					  
					  ' <th colspan="2" style="text-align:center;">本期豁免</th>',
					  
					  '<th rowspan="4">本<br/>期<br/>未<br/>还</th>',
					  '<th col="zprincipal">本金</th>',
					  '<td col="zprincipal" >&nbsp;</td></tr>',
					'<tr><th width="30" col="interest">利息</th>',
					 ' <td col="interest" >&nbsp;</td>',
					 ' <th col="yinterest">利息</th>',
					 ' <td col="yinterest" >&nbsp;</td>',
					 
					 '<th width="30" col="trinterAmount">利息</th>',
      				 '<td col="trinterAmount">&nbsp;</td>',
					 
					 ' <th col="zinterest">利息</th>',
					 ' <td col="zinterest" >&nbsp;</td></tr>',
					'<tr><th width="30" col="mgrAmount">管理费</th>',
					 ' <td col="mgrAmount" >&nbsp;</td>',
					 ' <th col="ymgrAmount">管理费</th>',
					 ' <td col="ymgrAmount" >&nbsp;</td>',
					 
					 '<th col="trmgrAmount">管理费</th>',
					 '<td col="trmgrAmount">&nbsp;</td>',
					 
					'  <th col="zmgrAmount">管理费</th>',
					 ' <td col="zmgrAmount" >&nbsp;</td></tr>',
					'<tr><th col="totalAmount">合计</th> ',
				   ' <td col="totalAmount" >&nbsp;</td>',
				   ' <th col="ytotalAmount">合计</th> ',
				   ' <td col="ytotalAmount" >&nbsp;</td>',
				   
				   '<th col="trtotalAmount">合计</th>',
				   '<td col="trtotalAmount">&nbsp;</td>',
				   
				   ' <th col="ztotalAmount">合计</th>',
				   ' <td col="ztotalAmount" >&nbsp;</td><tr> </tr>'];

			var detailCfgs_1 = [{
				cmns : 'THREE',
				/* ONE , TWO , THREE */
				model : 'single',
				labelWidth : 95,
				title : '正常还款信息',
				// 详情面板标题
				/* i18n : UI_Menu.appDefault, */
				// 国际化资源对象
				noautoTdWidth : true,/*自动计算每列单元格宽度，true : 不需要自动计算，false/undefined ：系统默认自动计算*/
				htmls : htmlArrs_1,
				url : './fcNomalDeduct_obtain.action',
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
						id : this.detailPanlId,
						frame : false,
						width : 850,
						detailCfgs : detailCfgs_1
					});
			detailPanel.add(this.appFrm);
			return detailPanel;
		},
		/**
		 * 创建收款表单
		 */
		createAppFrm : function(){
			var _this = this;
			var txt_id = FormUtil.getHidField({
					fieldLabel : '还款计划ID',
					name : 'id'
				});
			var txt_zprincipal = FormUtil.getHidField({
					fieldLabel : '未还本金',
					name : 'zprincipal'
				});
			var txt_zinterest = FormUtil.getHidField({
					fieldLabel : '未还利息',
					name : 'zinterest'
				});
			var txt_zmgrAmount = FormUtil.getHidField({
					fieldLabel : '未还管理费',
					name : 'zmgrAmount'
				});
			var txt_ztotalAmount = FormUtil.getHidField({
					fieldLabel : '未还合计',
					name : 'ztotalAmount'
				});
				
			var callback = function(cboGrid, selVals) {
				var grid = cboGrid.grid;
				var record = grid.getSelRow();
				var account = record.get("account");
				_this.uamount = record.get("uamount");
				if(parseFloat(_this.appAmount)>parseFloat(_this.uamount)){
					ExtUtil.warn({msg:"选的放款账户余额已不足，请选择其他账号进行放款！"});
					return;
				}
				txt_bankAccount.setValue(account);
			}
			var cbog_accountId = ComboxControl.getAccountCboGrid({
						fieldLabel : '收款银行',
						name : 'accountId',
						allowBlank : false,
						readOnly : true,
						isAll : false,
						width : 130,
						callback : callback,
						params : {
							isIncome : 1,
							sysId : _this.sysId
						}
					});

			var txt_bankAccount = FormUtil.getReadTxtField({
						fieldLabel : '银行帐号',
						name : 'bankAccount',
						"width" : 160
					});

			var dat_rectDate = FormUtil.getDateField({
						fieldLabel : '收款日期',
						name : 'rectDate',
						"width" : 125,
						"allowBlank" : false,
						editable : false,
						"maxLength" : 50
					});
					
			var rad_order = FormUtil.getRadioGroup({fieldLabel : '按扣收顺序收款',allowBlank : false,name:'isOrder',
			items : [{boxLabel : '否', name:'isOrder',inputValue:1, checked: true},{boxLabel : '是', name:'isOrder',inputValue:2}]});
			rad_order.addListener('change',function(radgroup,checked){_this.orderChangeListener(radgroup,checked);});
			var num_cat = FormUtil.getMoneyField({
						fieldLabel : '本金',
						name : 'cat',
						allowBlank : false,
						width : 100,
						unitText : '元'
					});
			num_cat.addListener('change',function(field,newVal,oldVal){_this.fieldChangeListener(field,newVal,oldVal);});
			
			var num_rat = FormUtil.getMoneyField({
						fieldLabel : '利息',
						name : 'rat',
						allowBlank : false,
						width : 100,
						unitText : '元'
					});
			num_rat.addListener('change',function(field,newVal,oldVal){_this.fieldChangeListener(field,newVal,oldVal);});
			
			var num_mat = FormUtil.getMoneyField({
				fieldLabel : '管理费',
				name : 'mat',
				allowBlank : false,
				width : 100,
				unitText : '元'
			});
			num_mat.addListener('change',function(field,newVal,oldVal){_this.fieldChangeListener(field,newVal,oldVal);});
	
			var num_tat = FormUtil.getMoneyField({
				fieldLabel : '实收合计',
				name : 'tat',
				allowBlank : false,
				width : 100,
				readOnly : true,
				unitText : '元'
			});
			num_tat.addListener('change',function(field,newVal,oldVal){_this.calculateByOrder();});
			
			var num_oddAmount = FormUtil.getMoneyField({
				fieldLabel : '应找回金额',
				name : 'oddAmount',
				allowBlank : false,
				width : 100,
				readOnly : true,
				unitText : '元'
			});
			var layout_fields = [txt_id,txt_zprincipal,txt_zinterest,txt_zmgrAmount,txt_ztotalAmount,{
						cmns : FormUtil.CMN_THREE,
						fields : [cbog_accountId, txt_bankAccount,
								dat_rectDate,rad_order,num_cat,num_rat,num_mat,num_tat,num_oddAmount]
					}];
			var frm_cfg = {
				title : '本次收款信息录入',
				labelWidth : 95,
				url : './fcNomalDeduct_receivables.action'
			};
			var appform = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform;
		},
		/**
		 * 扣收顺序改变时
		 */
		orderChangeListener : function(radgroup,checked){
			var num_cat = this.appFrm.findFieldByName("cat");
			var num_rat = this.appFrm.findFieldByName("rat");
			var num_mat = this.appFrm.findFieldByName("mat");
			var num_tat = this.appFrm.findFieldByName("tat");
			var num_oddAmount = this.appFrm.findFieldByName("oddAmount");
			num_cat.reset();
			num_rat.reset();
			num_mat.reset();
			num_tat.reset();
			num_oddAmount.reset();
			
			var order = checked.getRawValue();
			var flag1 = false;
			var flag2 = true; 
			if(order && order == 2){/*按扣收顺序收款*/
				flag1 = true;
				flag2 = false;
			}else{
				flag1 = false;
				flag2 = true;
				/*非扣收顺序的话，默认赋上未还金额*/
				var txt_zprincipal = this.appFrm.findFieldByName("zprincipal");
				var txt_zinterest = this.appFrm.findFieldByName("zinterest");
				var txt_zmgrAmount = this.appFrm.findFieldByName("zmgrAmount");
			
				num_cat.setValue(txt_zprincipal.getValue() || 0);
				num_rat.setValue(txt_zinterest.getValue() || 0);
				num_mat.setValue(txt_zmgrAmount.getValue() || 0);
				
			}
			
			var txt_ztotalAmount = this.appFrm.findFieldByName("ztotalAmount");
			num_tat.setValue(txt_ztotalAmount.getValue() || 0);
			
			num_cat.setReadOnly(flag1);
			num_rat.setReadOnly(flag1);
			num_mat.setReadOnly(flag1);
			num_tat.setReadOnly(flag2);
			if(this.closeFlag){/*当关闭窗口时，不调用 按扣收顺序计算方法。原因：因为关闭窗口时会重置表单数据，导致实收合计会等于0*/
				return;
			}
			this.calculateByOrder();
		},
		/**
		 * 本金、利息、管理费 改变事件
		 */
		fieldChangeListener : function(field,newVal,oldVal){
			var num_cat = this.appFrm.findFieldByName("cat");
			var num_rat = this.appFrm.findFieldByName("rat");
			var num_mat = this.appFrm.findFieldByName("mat");
			var num_tat = this.appFrm.findFieldByName("tat");
			var cat = num_cat.getValue() || 0.00;
			var rat = num_rat.getValue() || 0.00;
			var mat = num_mat.getValue() || 0.00;
			var tat = StringHandler.forDight(parseFloat(cat) + parseFloat(rat) + parseFloat(mat),2);
			num_tat.setValue(tat);
			
			var txt_ztotalAmount = this.appFrm.findFieldByName("ztotalAmount");
			var ztotalAmount = txt_ztotalAmount.getValue();
			var oddAmount = 0;
			if(tat > ztotalAmount){
				oddAmount = tat - ztotalAmount;
			}
			var num_oddAmount = this.appFrm.findFieldByName("oddAmount");
			num_oddAmount.setValue(oddAmount);
		},
		/**
		 * 按扣收顺序计算金额
		 */
		calculateByOrder : function(){
			var _this = this;
			var txt_zprincipal = this.appFrm.findFieldByName("zprincipal");
			var txt_zinterest = this.appFrm.findFieldByName("zinterest");
			var txt_zmgrAmount = this.appFrm.findFieldByName("zmgrAmount");
			var txt_ztotalAmount = this.appFrm.findFieldByName("ztotalAmount");
			var zprincipal = txt_zprincipal.getValue();
			var zinterest = txt_zinterest.getValue();
			var zmgrAmount = txt_zmgrAmount.getValue();
			var ztotalAmount = txt_ztotalAmount.getValue();
			
			var rad_order = this.appFrm.findFieldByName("isOrder");
			var num_cat = this.appFrm.findFieldByName("cat");
			var num_rat = this.appFrm.findFieldByName("rat");
			var num_mat = this.appFrm.findFieldByName("mat");
			var num_tat = this.appFrm.findFieldByName("tat");
			var tat = num_tat.getValue();//实收合计
			if(tat<=0){
				ExtUtil.alert({msg : '实收合计必须大于0!'});
				return;
			}
			
			var order = rad_order.getValue();
			if(!order || order != 2){
				this.errArr = null;
				return;
			}
			this.apptbar.disableButtons(Btn_Cfgs.DO_NOMAL_BTN_TXT);
			/*-- 按扣收顺序收款 --*/
			EventManager.get('./fcOrder_getDatas.action',{sfn:function(json_data){
				_this.apptbar.enableButtons(Btn_Cfgs.DO_NOMAL_BTN_TXT);
				var totalSize = json_data.totalSize;
				if(!totalSize || totalSize == 0){
					var errMsg = '扣收顺序基础数据未设置，请通知管理员 或 到 系统初始化--> 扣款优先级 功能中进行设置';
					ExtUtil.alert({msg : errMsg});
					if(!_this.errArr) _this.errArr = [];
					_this.errArr[_this.errArr.length] = errMsg;
					return;
				}
				
				var list = json_data.list;
				for(var i=0; i<totalSize; i++){
					var orderData = list[i];
					var code = orderData.code;
					switch(code){
						case ORDER_ENUM.L0001: {/*利息*/
							tat = _this.calculateAmounts(num_rat,tat,zinterest);
							break;
						}case ORDER_ENUM.L0002: {/*本金*/
							tat = _this.calculateAmounts(num_cat,tat,zprincipal);
							break;
						}case ORDER_ENUM.L0003: {/*管理费*/
							tat = _this.calculateAmounts(num_mat,tat,zmgrAmount);
							break;
						}
					
//						case ORDER_ENUM.L0004: {/*罚息*/
//							tat = this.calculateAmounts(,tat,);
//							break;
//						}case ORDER_ENUM.L0005: {/*滞纳金*/
//							tat = this.calculateAmounts(,tat,);
//							break;
//						}
					}
					if(tat<=0) break;
				}
				if(tat<=0) tat=0;
				var num_oddAmount = _this.appFrm.findFieldByName("oddAmount");
				num_oddAmount.setValue(tat);
			}});
		},
		calculateAmounts : function(num_field,tat,minusAmount){
			var result = tat - minusAmount;
			var amount = 0;
			if(result>=0){
				amount = minusAmount;	
			}
			if(result<0 && tat>0){
				amount = tat;
			}
			num_field.setValue(amount);
			tat = result;
			return tat;
		},
		/**
		 * 当详情面板数据加载完成后触发的回调函数
		 */
		callback : function(jsonData){
			var custType = Render_dataSource.custTypeRender(jsonData.custType);
			jsonData["custName"] = jsonData["name"]+'<span style="color:red;">('+custType+')</span>';
			var appAmount = jsonData["appAmount"];
			this.total = appAmount;
			jsonData["appAmount"] = Cmw.getThousandths(appAmount)
					+ '元<br/><span style="color:red;">(大写：'
					+ Cmw.cmycurd(appAmount) + ')</span>';
			//还款状态
			jsonData["status"] = Render_dataSource.planStatusRender(jsonData["status"]);
			//豁免状态
			jsonData["exempt"] = Render_dataSource.exemptRender(jsonData["exempt"]);
			//zprincipal,zinterest,zmgrAmount,ztotalAmount
			var principal = jsonData["principal"] || 0.00; 
			var yprincipal = jsonData["yprincipal"] || 0.00; 
			
			var interest = jsonData["interest"] || 0.00; 
			var yinterest = jsonData["yinterest"] || 0.00; 
			var trinterAmount = jsonData["trinterAmount"] || 0.00; 
			
			var mgrAmount = jsonData["mgrAmount"] || 0.00; 
			var ymgrAmount = jsonData["ymgrAmount"] || 0.00; 
			var trmgrAmount = jsonData["trmgrAmount"] || 0.00; 
			
			var totalAmount = jsonData["totalAmount"] || 0.00; 
			var ytotalAmount = jsonData["ytotalAmount"] || 0.00; 
			var trtotalAmount = jsonData["trtotalAmount"] || 0.00; 
			
			var zprincipal = principal - yprincipal;
			jsonData["zprincipal"] = zprincipal;
			var zinterest = interest - yinterest - trinterAmount;
			jsonData["zinterest"] = zinterest;
			var zmgrAmount = mgrAmount - ymgrAmount - trmgrAmount;
			jsonData["zmgrAmount"] = zmgrAmount;
			var ztotalAmount = totalAmount - ytotalAmount - trtotalAmount;
			jsonData["ztotalAmount"] = ztotalAmount;
			//为表单中的本次实收项赋值
			jsonData["cat"] = zprincipal;
			jsonData["rat"] = zinterest;
			jsonData["mat"] = zmgrAmount;
			jsonData["tat"] = ztotalAmount;
			this.setFormValues(jsonData);
			
			//千分位格式化
			jsonData["principal"] = Cmw.getThousandths(principal);
			jsonData["interest"] = Cmw.getThousandths(interest);
			jsonData["mgrAmount"] = Cmw.getThousandths(mgrAmount);
			jsonData["totalAmount"] = Cmw.getThousandths(totalAmount);
			
			jsonData["yprincipal"] = Cmw.getThousandths(yprincipal);
			jsonData["yinterest"] = Cmw.getThousandths(yinterest);
			jsonData["ymgrAmount"] = Cmw.getThousandths(ymgrAmount);
			jsonData["ytotalAmount"] = Cmw.getThousandths(ytotalAmount);
			
			jsonData["zprincipal"] = Cmw.getThousandths(zprincipal);
			jsonData["zinterest"] = Cmw.getThousandths(zinterest);
			jsonData["zmgrAmount"] = Cmw.getThousandths(zmgrAmount);
			jsonData["ztotalAmount"] = Cmw.getThousandths(ztotalAmount);
		},
		/**
		 * 为表单元素赋值
		 */
		setFormValues : function(jsonData) {
			this.resetData();
			jsonData["isOrder"] = 1;
			jsonData["regman"] = CURENT_USER;
			this.appFrm.setFieldValues(jsonData);
			var dat_rectDate = this.appFrm.findFieldByName('rectDate');
			dat_rectDate.setValue(new Date());
		},
		/**
		 * 保存数据
		 */
		saveData : function() {
			var _this = this;
			var errMsg = [];
			if(this.errArr && this.errArr.length > 0) errMsg = errMsg.concat(this.errArr);
			var cbodAccountId = this.appFrm.findFieldByName("accountId");
			var accountId = cbodAccountId.getValue();
			if (!accountId) {
				errMsg[errMsg.length] =  '未选择收款银行!';
			}
			var num_tat = this.appFrm.findFieldByName("tat");
			var tat = num_tat.getValue();
			if(!tat || tat<=0){
				errMsg[errMsg.length] =  '本次实收金额不能小于0!';
			}
			if(errMsg && errMsg.length > 0){
				ExtUtil.alert({msg : '以下项不正确:'+errMsg.join("&nbsp;&nbsp;&nbsp;&nbsp;<br/>")});
				return;
			}
			var txt_bankAccount = this.appFrm.findFieldByName("bankAccount");
			var bankAccount = txt_bankAccount.getValue();
			if(!bankAccount){
				ExtUtil.alert({msg:'没有银行账号，请重新选择银行!'});
				return;
			}
			var num_oddAmount = this.appFrm.findFieldByName("oddAmount");
			var oddAmount = num_oddAmount.getValue();
			if(oddAmount>0){
				 Ext.MessageBox.show({title:'提示',msg:'本次收款应找回金额为:'+oddAmount+'元,是否将找回金额存入预收款帐户?',icon : Ext.MessageBox.QUESTION,
				 buttons: Ext.MessageBox.YESNOCANCEL,fn:function(btn){
				 	if(btn == 'yes'){
				 		save(true);
				 	}else if(btn == 'no'){
				 		save();
				 	}
				 }});
			}else{
				save();
			}
			function save(saveOddamount){
				_this.apptbar.disable();
				EventManager.frm_save(_this.appFrm, {
					beforeMake : function(formDatas) {
						formDatas.key = _this.bussKey;
						if(!saveOddamount){ /*不需要将 应找回金额保存为预收金额就清空*/
							formDatas.oddAmount = 0;
						}
						var isVoucher = _this.apptbar.getValue("isVoucher");
						formDatas.isVoucher = isVoucher ? 1 : 0;/* 1 : 表示自动生成财务凭证*/
						formDatas.vtempCode = _this.vtempCode;
						formDatas.sysId = _this.sysId;
						formDatas.contractIds = _this.contractIds;
					},
					sfn : function(formDatas) {
						_this.close();
						_this.refresh();
						_this.apptbar.enable();
					},ffn : function(formDatas) {
						_this.apptbar.enable();
					}
				});
			}
		},
		/**
		 * 重置数据
		 */
		resetData : function() {
			this.appFrm.reset();
		},
		/**
		 * 关闭窗口
		 */
		close : function() {
			this.closeFlag = true;
			this.resetData();
			this.appWin.hide();
		},
		/**
		 * 销毁组件
		 */
		destroy : function() {
			if(!this.appWin) return;
			this.appWin.close(); // 关闭并销毁窗口
			this.appWin = null; // 释放当前窗口对象引用
		}
	};
});
