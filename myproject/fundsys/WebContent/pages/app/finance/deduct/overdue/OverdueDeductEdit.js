/**
 * 逾期还款金额收取 窗口
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
		appGrid : null,
		detailPanlId : Ext.id(null, 'OverdueDeductDetailPanl'),
		bussKey : null,/* 钥匙KEY */
		sysId : null,
		setParams : function(parentCfg) {
			this.parentCfg = parentCfg;
			// --> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.sysId = parentCfg.parent.sysId;
			this.bussKey = parentCfg.bussKey;
			if (!this.bussKey) {
				ExtUtil.error({msg : 'bussKey 不能为空!'});
			}
		},
		createAppWindow : function() {
			this.apptbar = this.createToolBar();
			this.mainPanel = this.createMainPanel();
			this.appWin = new Ext.ux.window.MyWindow({
						width : 850,
						height : 610,
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
			if (_parentCfg)this.setParams(_parentCfg);
			var isFirstLoad = false;
			if (!this.appWin) {
				isFirstLoad = true;
				this.createAppWindow();
			}
			this.appWin.optionType = this.optionType;
			this.appWin.show();
			if (isFirstLoad)
				return;
			this.loadData();
		},
		/**
		 * 加载数据
		 */
		loadData : function() {
			var loanDetailPnl = Ext.getCmp(this.detailPanlId);
			var selId = this.parent.getSelId();
			loanDetailPnl.reload({id : selId,key : this.bussKey});
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
			var appBar = new Ext.ux.toolbar.MyCmwToolbar({aligin : 'right',controls : barItems});
			return appBar;
		},
		/**
		 * 创建Form表单
		 */
		createMainPanel : function() {
			var _this = this;
			this.appGrid = this.createAppGrid();
			this.appFrm = this.createAppFrm();
			var selId = this.parent.getSelId();
			var htmlArrs_1 = [
					'<tr><th col="code">借款合同号</th> <td col="code" >&nbsp;</td><th col="custName">客户名称</th> <td colspan="4" col="custName" >&nbsp;</td><tr>',
					'<tr><th col="payBank">还款银行</th> <td col="payBank" >&nbsp;</td><th col="payAccount">还款帐号</th> <td col="payAccount" >&nbsp;</td><th col="accName">还款人</th> <td col="accName" >&nbsp;</td><tr>',
					'<tr><th col="fldate">首期逾期时间</th> <td col="fldate" >&nbsp;</td><th col="flamount">首期逾期金额</th> <td col="flamount" >&nbsp;</td><th col="totalPharses">累计逾期期次</th> <td col="totalPharses" >&nbsp;</td><tr>',
					'<tr><th col="monthPharses">本月收款期次</th> <td col="monthPharses" >&nbsp;</td><th col="paydPhases">已还期数</th> <td col="paydPhases" >&nbsp;</td><th col="totalPhases">总期数</th> <td col="totalPhases" >&nbsp;</td><tr>',
					'<tr><th colspan="6" style="font-weight:bold;text-align:left;" >&nbsp;&nbsp;累计应收(逾期金额) >>></th><tr>',
					'<tr><th col="amounts">逾期本金</th> <td col="amounts" >&nbsp;</td><th col="iamounts">利息</th> <td col="iamounts" >&nbsp;</td><th col="mamounts">管理费</th> <td col="mamounts" >&nbsp;</td><tr>',
					'<tr><th col="pamounts">罚息</th> <td col="pamounts" >&nbsp;</td><th col="damounts">滞纳金</th> <td col="damounts" >&nbsp;</td><th col="totalAmounts">应收合计</th> <td col="totalAmounts" >&nbsp;</td><tr>'
					];

			var detailCfgs_1 = [{
				cmns : 'THREE',
				/* ONE , TWO , THREE */
				model : 'single',
				labelWidth : 115,
				title : '逾期还款信息',
				// 详情面板标题
				/* i18n : UI_Menu.appDefault, */
				// 国际化资源对象
				htmls : htmlArrs_1,
				url : './fcOverdueDeduct_obtain.action',
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
			detailPanel.add(this.appGrid);
			detailPanel.add(this.appFrm);
			return detailPanel;
		},
		/**
		 * 创建逾期还款Grid
		 */
		createAppGrid : function(){
			
			var structure_1 = [{
			    header: '期数',
			    name: 'phases',
			    width: 35
			},
			{
			    header: '应还款日期',
			    name: 'xpayDate',
			    width: 80
			},
			{
			    header: '应还',
			    name: 'zprincipal',
			    width: 70,
			    renderer : function(val){return Cmw.getThousandths(val);}
			},
			{
			    header: '实还',
			    name: 'cat',
			    width: 70,
			    renderer : function(val){return Cmw.getThousandths(val);}
			},
			{
			    header: '应还',
			    name: 'zinterest',
			     width: 70,
			    renderer : function(val){return Cmw.getThousandths(val);}
			},{
			    header: '实还',
			    name: 'rat',
			     width: 70,
			    renderer : function(val){return Cmw.getThousandths(val);}
			},
			{
			    header: '应还',
			    name: 'zmgrAmount',
			     width: 70,
			    renderer : function(val){return Cmw.getThousandths(val);}
			},{
			    header: '实还',
			    name: 'mat',
			     width: 70,
			    renderer : function(val){return Cmw.getThousandths(val);}
			},
			{
			    header: '应还',
			    name: 'zpenAmount',
			     width: 70,
			    renderer : function(val){return Cmw.getThousandths(val);}
			},{
			    header: '实还',
			    name: 'pat',
			     width: 70,
			    renderer : function(val){return Cmw.getThousandths(val);}
			},
			{
			    header: '应还',
			    name: 'zdelAmount',
			     width: 70,
			    renderer : function(val){return Cmw.getThousandths(val);}
			},{
			    header: '实还',
			    name: 'dat',
			     width: 70,
			    renderer : function(val){return Cmw.getThousandths(val);}
			},
			{
			    header: '应还',
			    name: 'ztotalAmount',
			     width: 70,
			    renderer : function(val){return Cmw.getThousandths(val);}
			},{
			    header: '实还',
			    name: 'tat',
			     width: 70,
			    renderer : function(val){return Cmw.getThousandths(val);}
			},
			{ 
			    header: '本息',
			    name: 'latedays',
			     width: 45
			},
			{
			    header: '本金逾期天数',
			    name: 'pdays',
			    hidden: true ,hideable : true
			},{
			    header: '利息逾期天数',
			    name: 'ratedays',
			    hidden: true ,hideable : true
			},
			{
			    header: '管理费',
			    name: 'mgrdays',
			    width: 45
			},{
			    header: '最后收款日期',
			    name: 'lastDate',
			    width: 80
			},{
			    header: '豁免状态',
			    name: 'exempt',
			     width: 80,
			    renderer : function(val){return Render_dataSource.exemptRender(val);}
			},{ 
			    header: '计划罚息',
			    name: 'penAmount',
			     hidden: true ,hideable : true
			},{ 
			    header: '计划滞纳金',
			    name: 'delAmount',
			     hidden: true ,hideable : true
			}];
		
			var continentGroupRow = [{header: '', colspan: 3, align: 'center'},
				{header: '本金', colspan: 2, align: 'center'},
				{header: '利息', colspan: 2, align: 'center'},
				{header: '管理费', colspan: 2, align: 'center'},
				{header: '罚息金额', colspan: 2, align: 'center'},
				{header: '滞纳金', colspan: 2, align: 'center'},
				{header: '本息费合计', colspan: 2, align: 'center'},
				{header: '逾期天数(天)', colspan: 4, align: 'center'},
				{header: ' ', colspan: 4, align: 'center'}];
			 var group = new Ext.ux.grid.ColumnHeaderGroup({
		        rows: [continentGroupRow]
		    });
	    
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    title: '逾期还款记录列表',
			    structure: structure_1,
			    url: './fcOverdueDeduct_planlist.action',
			    height:180,
			    needPage: false,
			    isLoad: false,
			    plugins: group,
			    keyField: 'id'
			});
			return appgrid_1;
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
			var txt_contractId = FormUtil.getHidField({
					fieldLabel : '合同ID',
					name : 'contractId'
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
			var txt_zpatAmount = FormUtil.getHidField({
				fieldLabel : '未还罚息',
				name : 'zpatAmount'
			});
			var txt_zdatAmount = FormUtil.getHidField({
				fieldLabel : '未还滞纳金',
				name : 'zdatAmount'
			}); 
			var txt_ztotalAmount = FormUtil.getHidField({
					fieldLabel : '未还合计',
					name : 'ztotalAmount'
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
						width : 130,
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
			dat_rectDate.addListener('select',function(field,newVal){_this.caclutePdAmountsByRectDate(field,newVal);});
			
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
			
			var num_pat = FormUtil.getMoneyField({
				fieldLabel : '罚息',
				name : 'pat',
				allowBlank : false,
				width : 100,
				unitText : '元'
			});
			num_pat.addListener('change',function(field,newVal,oldVal){_this.fieldChangeListener(field,newVal,oldVal);});
			
			var num_dat = FormUtil.getMoneyField({
				fieldLabel : '滞纳金',
				name : 'dat',
				allowBlank : false,
				width : 100,
				unitText : '元'
			});
			num_dat.addListener('change',function(field,newVal,oldVal){_this.fieldChangeListener(field,newVal,oldVal);});
	
			var num_tat = FormUtil.getMoneyField({
				fieldLabel : '实收合计',
				name : 'tat',
				allowBlank : false,
				width : 100,
				readOnly : true,
				unitText : '元'
			});
			num_tat.addListener('change',function(field,newVal,oldVal){_this.calculateByOrder(field,newVal,oldVal);});
			
			var num_oddAmount = FormUtil.getMoneyField({
				fieldLabel : '应找回金额',
				name : 'oddAmount',
				allowBlank : false,
				width : 100,
				readOnly : true,
				unitText : '元'
			});
			var layout_fields = [txt_id,txt_contractId,txt_zprincipal,txt_zinterest,txt_zmgrAmount,txt_ztotalAmount,txt_zpatAmount,txt_zdatAmount,{
						cmns : FormUtil.CMN_THREE,
						fields : [cbog_accountId, txt_bankAccount,
								dat_rectDate,rad_order,num_cat,num_rat,num_mat,num_pat,num_dat,num_tat,num_oddAmount]
					}];
			var frm_cfg = {
				title : '本次收款信息录入',
				labelWidth : 95,
				url : './fcOverdueDeduct_receivables.action'
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
			var num_pat = this.appFrm.findFieldByName("pat");
			var num_dat = this.appFrm.findFieldByName("dat");
			var num_tat = this.appFrm.findFieldByName("tat");
			
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
				var contractId = this.appFrm.findFieldByName("contractId").getValue();
				this.appGrid.reload({contractId:contractId});
				var txt_zprincipal = this.appFrm.findFieldByName("zprincipal");
				var txt_zinterest = this.appFrm.findFieldByName("zinterest");
				var txt_zmgrAmount = this.appFrm.findFieldByName("zmgrAmount");
				var txt_zpatAmount = this.appFrm.findFieldByName("zpatAmount");
				var txt_zdatAmount = this.appFrm.findFieldByName("zdatAmount");
				var txt_ztotalAmount = this.appFrm.findFieldByName("ztotalAmount");
				num_cat.setValue(txt_zprincipal.getValue() || 0);
				num_rat.setValue(txt_zinterest.getValue() || 0);
				num_mat.setValue(txt_zmgrAmount.getValue() || 0);
				num_pat.setValue(txt_zpatAmount.getValue() || 0);
				num_dat.setValue(txt_zdatAmount.getValue() || 0);
				num_tat.setValue(txt_ztotalAmount.getValue() || 0);
				
			}
		
			num_cat.setReadOnly(flag1);
			num_rat.setReadOnly(flag1);
			num_mat.setReadOnly(flag1);
			num_pat.setReadOnly(flag1);
			num_dat.setReadOnly(flag1);
			num_tat.setReadOnly(flag2);
		},
		/**
		 * 根据收款日期计算罚息和滞纳金
		 */
		caclutePdAmountsByRectDate : function(field,newVal){
			if(!newVal){
				ExtUtil.alert({msg : '请选择收款日期!'});
				return;
			}
			var _this = this;
			var txt_contractId = this.appFrm.findFieldByName('contractId');
			var contractId = txt_contractId.getValue();
			var newVal = newVal.format('Y-m-d');
			this.mask('正在计算逾期应收合计金额...');
			var params = {contractId:contractId,lastDate : newVal};
			EventManager.get('./fcOverdueDeduct_getPdamounts.action',{params:params,sfn:function(json_data){
				if(!json_data){
					ExtUtil.alert({msg : '无法获取计算后的罚息和滞纳金数据!'});
					return;
				}
				var list = json_data[contractId+""];
				if(null == list || list.length == 0) return;
			
				var totalPenAmounts= 0;
				var totalDelAmounts = 0; 
				var store = _this.appGrid.getStore();
				var count = store.getCount();
				var len = list.length;
				for(var i=0; i<count; i++){
					var record = store.getAt(i);
					var id = record.id;
					for(var j=0; j<len; j++){
						var data = list[j];
						var _id = data.id;
						if(id == _id){
							//id,penAmount,delAmount,ypenAmount,ydelAmount,mgrdays,latedays,exempt
							var penAmount = data.penAmount;
							var delAmount = data.delAmount;
							var ypenAmount = data.ypenAmount;
							var ydelAmount = data.ydelAmount;
							var mgrdays = data.mgrdays;
							var latedays = data.latedays;
							var exempt = data.exempt;
							var zpenAmount = parseFloat(penAmount) - parseFloat(ypenAmount);
							var zdelAmount = parseFloat(delAmount) - parseFloat(ydelAmount);
							
							var ztotalAmount = parseFloat(record.get('zprincipal')) +  parseFloat(record.get('zinterest')) +  parseFloat(record.get('zmgrAmount')) +  zpenAmount +  zdelAmount;
							ztotalAmount =  StringHandler.forDight(ztotalAmount,2);
							totalPenAmounts += zpenAmount;
							totalDelAmounts += zdelAmount;
							//zpenAmount,pat,zdelAmount,dat,ztotalAmount,tat,latedays,mgrdays,exempt,penAmount,delAmount
							record.set('zpenAmount',zpenAmount);
							record.set('pat',zpenAmount);
							record.set('zdelAmount',zdelAmount);
							record.set('dat',zdelAmount);
							record.set('ztotalAmount',ztotalAmount);
							record.set('tat',ztotalAmount);
							record.set('latedays',latedays);
							record.set('mgrdays',mgrdays);
							record.set('exempt',exempt);
							record.set('penAmount',penAmount);
							record.set('delAmount',delAmount);
							break;
						}
					}
				}
				totalPenAmounts = parseFloat(totalPenAmounts);
				totalDelAmounts = parseFloat(totalDelAmounts);
				totalPenAmounts = StringHandler.forDight(totalPenAmounts,2);
				totalDelAmounts = StringHandler.forDight(totalDelAmounts,2);
				//zprincipal,zinterest,zmgrAmount,zpatAmount,zdatAmount,ztotalAmount
				//cat,rat,mat,pat,dat,tat,oddAmount
				_this.appFrm.findFieldByName('zpatAmount').setValue(totalPenAmounts);
				_this.appFrm.findFieldByName('zdatAmount').setValue(totalDelAmounts);
				var objVal = _this.appFrm.getValuesByNames('zprincipal,zinterest,zmgrAmount,zpatAmount,zdatAmount');
				var ztotalAmount = 0;
				if(objVal){
					ztotalAmount = parseFloat(objVal.zprincipal||0) + parseFloat(objVal.zinterest||0) + parseFloat(objVal.zmgrAmount||0) + parseFloat(objVal.zpatAmount||0) + parseFloat(objVal.zdatAmount||0);
					ztotalAmount = StringHandler.forDight(ztotalAmount,2);
				}
				_this.appFrm.findFieldByName('ztotalAmount').setValue(ztotalAmount);
				
				_this.appFrm.setFieldValue('cat',objVal.zprincipal);
				_this.appFrm.setFieldValue('rat',objVal.zinterest);
				_this.appFrm.setFieldValue('mat',objVal.zmgrAmount);
				_this.appFrm.setFieldValue('pat',totalPenAmounts);
				_this.appFrm.setFieldValue('dat',totalDelAmounts);
				
				var tat = 0;
				objVal = _this.appFrm.getValuesByNames('cat,rat,mat,pat,dat');
				if(objVal){
					tat = parseFloat(objVal.cat||0) + parseFloat(objVal.rat||0) + parseFloat(objVal.mat||0) + parseFloat(objVal.pat||0) + parseFloat(objVal.dat||0);
					tat = StringHandler.forDight(tat,2);
				}
				_this.appFrm.findFieldByName('tat').setValue(tat);
				var oddAmount = 0;
				if(tat>ztotalAmount){
					oddAmount = tat-ztotalAmount;
				}
				_this.appFrm.findFieldByName('oddAmount').setValue(oddAmount);
				_this.unmask();
			},ffn:function(){
				_this.unmask();
			}});
		},
		/**
		 * 本金、利息、管理费 改变事件
		 */
		fieldChangeListener : function(field,newVal,oldVal){
			var num_cat = this.appFrm.findFieldByName("cat");
			var num_rat = this.appFrm.findFieldByName("rat");
			var num_mat = this.appFrm.findFieldByName("mat");
			var num_pat = this.appFrm.findFieldByName("pat");
			var num_dat = this.appFrm.findFieldByName("dat");
			var num_tat = this.appFrm.findFieldByName("tat");
			var cat = num_cat.getValue() || 0.00;
			var rat = num_rat.getValue() || 0.00;
			var mat = num_mat.getValue() || 0.00;
			var pat = num_pat.getValue() || 0.00;
			var dat = num_dat.getValue() || 0.00;
			var tat = num_tat.getValue() || 0.00;
			
			var tat = StringHandler.forDight(parseFloat(cat) + parseFloat(rat) + parseFloat(mat)+ parseFloat(pat) + parseFloat(dat),2);
			num_tat.setValue(tat);
			
			var txt_ztotalAmount = this.appFrm.findFieldByName("ztotalAmount");
			var ztotalAmount = txt_ztotalAmount.getValue();
			var oddAmount = 0;
			if(tat > ztotalAmount){
				oddAmount = tat - ztotalAmount;
			}
			var num_oddAmount = this.appFrm.findFieldByName("oddAmount");
			num_oddAmount.setValue(oddAmount);
			var name = field["name"];
			var store = this.appGrid.getStore();
			var count = store.getCount();
			if(count == 0) return;
			var amount = parseFloat(newVal);
			if(amount == 0){
				for(var i=0; i<count; i++){
					var record = store.getAt(i);
					record.set(name,0);
					var _tat =  parseFloat(record.get('cat')||0)+parseFloat(record.get('rat')||0)+parseFloat(record.get('mat')||0)+parseFloat(record.get('pat')||0)+parseFloat(record.get('dat')||0);
					_tat =  StringHandler.forDight(_tat,2);
					record.set('tat',_tat);
				}
			}else{
				for(var i=0; i<count; i++){
					var record = store.getAt(i);
					var zname = this.getZfieldName(name);
					var _currAmount = record.get(zname);
					_currAmount = parseFloat(_currAmount);
					var result = amount - _currAmount; 
					if(result >= 0){
						amount -= _currAmount;
						record.set(name,_currAmount);
					}else{
						if(amount<0){
							amount = 0;
						}
						record.set(name,amount);
						amount = 0;
					}
					var _tat =  parseFloat(record.get('cat')||0)+parseFloat(record.get('rat')||0)+parseFloat(record.get('mat')||0)+parseFloat(record.get('pat')||0)+parseFloat(record.get('dat')||0);
					_tat =  StringHandler.forDight(_tat,2);
					record.set('tat',_tat);
				}
			}
		},
		/**
		 * 根据实收字段名获取对应的应收字段名
		 */
		getZfieldName : function(rfieldName){
			var zfieldName = null;
			switch(rfieldName){
				case 'cat' :{
					zfieldName = 'zprincipal';
				    break;
				}case 'rat' :{
					zfieldName = 'zinterest';
				    break;
				}case 'mat' :{
					zfieldName = 'zmgrAmount';
				    break;
				}case 'pat' :{
					zfieldName = 'zpenAmount';
				    break;
				}case 'dat' :{
					zfieldName = 'zdelAmount';
				    break;
				}
			}
			return zfieldName;
		},
		/**
		 * 按扣收顺序计算金额
		 */
		calculateByOrder : function(field,newVal,oldVal){
			var _this = this;
			var rad_order = this.appFrm.findFieldByName("isOrder");
			var num_tat = this.appFrm.findFieldByName("tat");
			
			var tat = num_tat.getValue();//实收合计
			if(tat<=0){
				ExtUtil.alert({msg : '实收合计必须大于0!'});
				return;
			}
			
			var order = rad_order.getValue();
			if(!order || order != 2) return;
			this.mask('正在按扣收顺序计算收款金额...');
			/*-- 按扣收顺序收款 --*/
			EventManager.get('./fcOrder_getDatas.action',{sfn:function(json_data){
				var totalSize = json_data.totalSize;
				if(!totalSize || totalSize == 0){
					ExtUtil.alert({msg : '扣收顺序基础数据未设置，请通知管理员 或 到 系统初始化--> 扣款优先级 功能中进行设置'});
					return;
				}
				var list = json_data.list;
				var fieldsArr = _this.groupOrders(list);
				_this.setGridDatas(fieldsArr);
				_this.unmask();
			},ffn:function(){
				_this.unmask();
			}});
		},
		/**
		 * 为Grid 上的实收赋值
		 */
		setGridDatas : function(fieldsArr){
			var num_tat = this.appFrm.findFieldByName("tat");
			var tat = num_tat.getValue();//实收合计
			tat = parseFloat(tat);
			var maxLevelArr = fieldsArr[0];
			var nomalLevelArr = fieldsArr[1];
			this.resetGridRamouts();
			var store = this.appGrid.getStore();
			tat = this.calculateAmountsByOrder(maxLevelArr,store,tat);
			if(tat>0) this.calculateAmountsByOrder(nomalLevelArr,store,tat);
			this.calculateRtotalAmounts();
		},
		/**
		 * 根据扣收顺序计算金额
		 */
		calculateAmountsByOrder : function(fieldArr,store,tat){
			if(tat<=0) return 0;
			var len = store.getCount();
			if(len == 0) return 0;
		
			for(var j=0; j<len; j++){
				var record = store.getAt(j);
				for(var i=0,count=fieldArr.length; i<count; i++){
					var fieldKv = fieldArr[i];
					fieldKv = fieldKv.split(",");
					var zfield = fieldKv[0];
					var rfield = fieldKv[1];
					var amount = record.get(zfield) || 0.00;
					if(amount == 0) continue;
					var result = tat - amount;
					if(result >= 0){
						tat -= amount;
						record.set(rfield,amount);
					}else{
						if(tat>0){
							record.set(rfield,tat);
						}
						tat = 0;
						break;
					}
				}
				if(tat<=0) break;
			}
			return tat;
		},
		/**
		 * 计算 Grid 实收合计
		 */
		calculateRtotalAmounts : function(){
			var appGrid = this.appGrid;
			var store = appGrid.getStore();
			var count = store.getCount();
			if(count == 0) return;
			var sum_cat = 0,sum_rat = 0,sum_mat = 0, sum_pat = 0,sum_dat = 0;
			for(var i=0; i<count; i++){
				var record = store.getAt(i);
				var cat = record.get('cat') || 0.00;
				var rat = record.get('rat') || 0.00;
				var mat = record.get('mat') || 0.00;
				var pat = record.get('pat') || 0.00;
				var dat = record.get('dat') || 0.00;
				sum_cat += parseFloat(cat);
				sum_rat += parseFloat(rat);
				sum_mat += parseFloat(mat);
				sum_pat += parseFloat(pat);
				sum_dat += parseFloat(dat);
				var totalAmount = parseFloat(cat)+parseFloat(rat)+parseFloat(mat)+parseFloat(pat)+parseFloat(dat);
				totalAmount = StringHandler.forDight(totalAmount,2);
				//sum_tat += totalAmount;
				record.set('tat',totalAmount);
			}
			this.appFrm.findFieldByName('cat').setValue(sum_cat);
			this.appFrm.findFieldByName('rat').setValue(sum_rat);
			this.appFrm.findFieldByName('mat').setValue(sum_mat);
			this.appFrm.findFieldByName('pat').setValue(sum_pat);
			this.appFrm.findFieldByName('dat').setValue(sum_dat);
			//sum_tat = StringHandler.forDight(sum_tat,2);
			//this.appFrm.findFieldByName('tat').setValue(sum_tat);
			var sum_tat = this.appFrm.getValueByName('tat');
			var ztotalAmount = this.appFrm.findFieldByName('ztotalAmount').getValue();
			var oddAmount = parseFloat(sum_tat) - parseFloat(ztotalAmount);
			if(oddAmount < 0) oddAmount = 0;
			this.appFrm.findFieldByName('oddAmount').setValue(oddAmount);
		},
		/**
		 * 重置Grid 实还为0
		 */
		resetGridRamouts : function(){
			var appGrid = this.appGrid;
			var store = appGrid.getStore();
			var count = store.getCount();
			if(count == 0) return;
			for(var i=0; i<count; i++){
				var record = store.getAt(i);
				record.set('cat',0.00);
				record.set('rat',0.00);
				record.set('mat',0.00);
				record.set('pat',0.00);
				record.set('dat',0.00);
				record.set('tat',0.00);
			}
		},
		groupOrders : function(list){
			var maxLevelArr = [];
			var nomalLevelArr = [];
			//cat,rat,mat,pat,dat,tat
			var totalSize = list.length;
			for(var i=0; i<totalSize; i++){
				var orderData = list[i];
				var code = orderData.code;
				var level = orderData.level;
				switch(code){
					case ORDER_ENUM.L0001: {/*利息*/
						this.setOrderToArr(maxLevelArr, nomalLevelArr, level, 'zinterest,rat');
						break;
					}case ORDER_ENUM.L0002: {/*本金*/
						this.setOrderToArr(maxLevelArr, nomalLevelArr, level, 'zprincipal,cat');
						break;
					}case ORDER_ENUM.L0003: {/*管理费*/
						this.setOrderToArr(maxLevelArr, nomalLevelArr, level, 'zmgrAmount,mat');
						break;
					}case ORDER_ENUM.L0004: {/*罚息*/
						this.setOrderToArr(maxLevelArr, nomalLevelArr, level, 'zpenAmount,pat');
						break;
					}case ORDER_ENUM.L0005: {/*滞纳金*/
						this.setOrderToArr(maxLevelArr, nomalLevelArr, level, 'zdelAmount,dat');
						break;
					}
				}
			}
			return [maxLevelArr,nomalLevelArr];
		},
		setOrderToArr : function(maxLevelArr, nomalLevelArr, level, field){
			if(level==2){/*最高优先级*/
				maxLevelArr[maxLevelArr.length] = field;
			}else{
				nomalLevelArr[nomalLevelArr.length] = field;
			}
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
			var custType = Render_dataSource.custTypeRender(jsonData.custType);;
			jsonData["custName"] = jsonData["name"]+'<span style="color:red;">('+custType+')</span>';
			var appAmount = jsonData["appAmount"];
			jsonData["appAmount"] = Cmw.getThousandths(appAmount)
					+ '元<br/><span style="color:red;">(大写：'
					+ Cmw.cmycurd(appAmount) + ')</span>';
			var flamount = jsonData["flamount"];
			jsonData["flamount"] = Cmw.getThousandths(flamount);
			//表内表外
			jsonData["inouttype"] = Render_dataSource.inouttypeRender(jsonData["inouttype"]);
			//amounts,iamounts,mamounts,pamounts,damounts,totalAmounts
			var amounts = jsonData["amounts"] || 0.00; 
			var iamounts = jsonData["iamounts"] || 0.00; 
			var mamounts = jsonData["mamounts"] || 0.00; 
			var pamounts = jsonData["pamounts"] || 0.00; 
			var damounts = jsonData["damounts"] || 0.00; 
			var totalAmounts = jsonData["totalAmounts"] || 0.00; 
			
			//为表单中的本次实收项赋值
			jsonData["cat"] = amounts;
			jsonData["rat"] = iamounts;
			jsonData["mat"] = mamounts;
			jsonData["pat"] = pamounts;
			jsonData["dat"] = damounts;
			jsonData["tat"] = totalAmounts;

			jsonData["zprincipal"] = amounts;
			jsonData["zinterest"] = iamounts;
			jsonData["zmgrAmount"] = mamounts;
			jsonData["zpatAmount"] = pamounts;
			jsonData["zdatAmount"] = damounts;
			jsonData["ztotalAmount"] = totalAmounts;
			this.setFormValues(jsonData);
			
			//千分位格式化
			jsonData["amounts"] = Cmw.getThousandths(amounts);
			jsonData["iamounts"] = Cmw.getThousandths(iamounts);
			jsonData["mamounts"] = Cmw.getThousandths(mamounts);
			jsonData["pamounts"] = Cmw.getThousandths(pamounts);
			jsonData["damounts"] = Cmw.getThousandths(damounts);
			jsonData["totalAmounts"] = Cmw.getThousandths(totalAmounts);
		},
		/**
		 * 为表单元素赋值
		 */
		setFormValues : function(jsonData) {
			this.resetData();
			jsonData["isOrder"] = 2;
			var contractId = jsonData["contractId"];
			this.appGrid.reload({contractId:contractId});
			this.appFrm.setFieldValues(jsonData);
		},
		/**
		 * 保存数据
		 */
		saveData : function() {
			var _this = this;
			var errMsg = [];
			var cbodAccountId = this.appFrm.findFieldByName("accountId");
			var accountId = cbodAccountId.getValue();
			if (!accountId) {
				errMsg[errMsg.length] =  '未选择收款银行!';
			}
			var rectDate = this.appFrm.findFieldByName('rectDate').getValue();
			if(!rectDate) errMsg[errMsg.length] =  '收款日期不能为空!';
			var num_tat = this.appFrm.findFieldByName("tat");
			var tat = num_tat.getValue();
			if(!tat || tat<=0){
				errMsg[errMsg.length] =  '本次实收金额不能小于0!';
			}
			if(errMsg && errMsg.length > 0){
				ExtUtil.alert({msg : '以下项输入不正确:<br/>'+errMsg.join("&nbsp;&nbsp;&nbsp;&nbsp;<br/>")});
				return;
			}
			var tat = this.appFrm.getValueByName("tat");
			var num_oddAmount = this.appFrm.findFieldByName("oddAmount");
			var oddAmount = num_oddAmount.getValue();
			if(oddAmount>0){
				 //Ext.MessageBox.show(
				 ExtUtil.confirm({title:'提示',msg:'本次收款实收合计：<span style="color:blue;font-weight:bold;">'+tat+'</span>元，' +
				 		'应找回金额:<span style="color:blue;font-weight:bold;">'+oddAmount+'</span>元,确定收款?',
				 		icon : Ext.MessageBox.QUESTION,
				 buttons: Ext.MessageBox.YESNOCANCEL,fn:function(btn){
				 	if(btn != 'yes') return;
					save();
//				 	if(btn == 'yes'){
//				 		save(true);
//				 	}else if(btn == 'no'){
//				 		save();
//				 	}
				 }});
			}else{
				ExtUtil.confirm({msg : '本次收款实收合计：<span style="color:blue;font-weight:bold;">'+tat+'</span>元，确定收款?',fn:function(){
					if(arguments && arguments[0] != 'yes') return;
					save();
				}});
			}
			function save(saveOddamount){
				var batchDatas = _this.getBatchDatas();
				_this.mask('正在保存数据...');
				EventManager.frm_save(_this.appFrm, {
					beforeMake : function(formDatas) {
						formDatas.key = _this.bussKey;
						var isVoucher = _this.apptbar.getValue("isVoucher");
						formDatas.isVoucher = isVoucher ? 1 : 0;/* 1 : 表示自动生成财务凭证*/
						formDatas.sysId = _this.sysId;
						formDatas.batchDatas = Ext.encode(batchDatas);
						if(!saveOddamount){ /*不需要将 应找回金额保存为预收金额就清空*/
							formDatas.oddAmount = 0;
						}
					},
					sfn : function(formDatas) {
						_this.close();
						_this.refresh();
						_this.unmask();
					},ffn:function(){_this.unmask();}});
			}
		},
		/**
		 * 获取本次实收款金额
		 */
		getBatchDatas : function(){
			var store = this.appGrid.getStore();
			var arr = [];
			for(var i=0,count = store.getCount(); i<count; i++){
				var record = store.getAt(i);
				var id = record.id;
				var cat = record.get('cat');
				var rat = record.get('rat');
				var mat = record.get('mat');
				var pat = record.get('pat');
				var dat = record.get('dat');
				var tat = record.get('tat');
				var penAmount = record.get('penAmount');
				var delAmount = record.get('delAmount');
				var pdays = record.get('pdays');
				var ratedays = record.get('ratedays');
				var mgrdays = record.get('mgrdays');
				arr[arr.length] = {id:id, cat:cat, rat:rat, mat:mat, pat:pat, dat:dat, tat:tat, penAmount:penAmount, delAmount:delAmount,
					pdays:pdays,ratedays:ratedays,mgrdays:mgrdays};
			}
			return arr;
		},
		mask : function(msg){
			Cmw.mask(this.appFrm,msg);
			this.apptbar.disable();
		},
		unmask : function(){
			Cmw.unmask(this.appFrm);
			this.apptbar.enable();
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
			this.resetData();
			this.appWin.hide();
		},
		/**
		 * 销毁组件
		 */
		destroy : function() {
			this.appWin.close(); // 关闭并销毁窗口
			this.appWin = null; // 释放当前窗口对象引用
		}
	};
});
