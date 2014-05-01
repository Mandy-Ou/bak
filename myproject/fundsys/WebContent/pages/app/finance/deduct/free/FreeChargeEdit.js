/**
 * 手续费收取
 * @author 彭登浩
 * @date 2013-01-17 15:57:32
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
		mount :null,
//		optionType : OPTIONTYPE.LOAN,
		detailPanlId : Ext.id(null,'OneCustLoanDetailPanl'),
		bussKey : null,/*钥匙KEY*/
		sysId : null,
		contractId : null,
		vtempCode : VOUCHERTEMP_CODE.LOANFREE,/*放款手续费收取凭证模板编号 constant.js定义*/
		setParams:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.sysId =  parentCfg.parent.sysId;
			this.contractId = parentCfg.parent.contractId;
			var yamount = parentCfg.parent.getCmnVals("yamount");//已手续收金额
			var freeamount = parentCfg.parent.getCmnVals("freeamount");//应收手续费金额
			if(!yamount){
				yamount = 0.00;
			}
			var mt={};
			this.mount={freeamount:freeamount,yamount:yamount};
//			this.mount = mt;
			this.bussKey = parentCfg.bussKey;
			if(!this.bussKey){
				ExtUtil.error({msg:'bussKey 不能为空!'});
			}
		},
		createAppWindow : function(){
			this.apptbar = this.createToolBar();
			this.mainPanel = this.createMainPanel();
			this.appWin = new Ext.ux.window.MyWindow({width:820,height :370,modal:true,tbar:this.apptbar,items : [this.mainPanel],
			listeners : {
				'hide':function(win){
					LockManager.releaseLock();
				}
			}});
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
			this.appWin.show(this.parent.getEl());
			this.loadData();
		},
		/**
		 * 加载数据
		 */
		loadData : function(){
			var loanDetailPnl = Ext.getCmp(this.detailPanlId);
			var selId = this.parent.getSelId();
			loanDetailPnl.reload({id : selId,key:this.bussKey});
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var _this = exports.WinEdit;
			if(_this.parentCfg.self.refresh)_this.parentCfg.self.refresh(_this.optionType,data);	
		},
		createToolBar : function(){
			var _this = this;
			var barItems = [{type:'chk',name:'isVoucher',checked:true,boxLabel:Finsys_Btn_Cfgs.AUTOVOUCHER_BOXLABEL},{
				text : Btn_Cfgs.DO_FREE_BTN_TXT,
				iconCls:Btn_Cfgs.DO_FREE_CLS,
				tooltip:Btn_Cfgs.DO_FREE_TIP_BTN_TXT,
				handler : function(){
					_this.saveData();
				}
			},{
			text : Btn_Cfgs.CLOSE_BTN_TXT,  /*-- 关闭 --*/
			iconCls:Btn_Cfgs.CLOSE_CLS,
			tooltip:Btn_Cfgs.CLOSE_TIP_BTN_TXT,
			handler : function(){
				_this.close();
			}
		}];
			var appBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems});
			return appBar;
		},
		/**
		 * 创建Form表单
		 */
		createMainPanel : function(){
			var _this = this;
			var selId = this.parent.getSelId();
			var htmlArrs_1 = [
			'<tr><th col="status">收款状态</th> <td col="status" >&nbsp;</td><th col="code">借款合同号</th> <td col="code" >&nbsp;</td><th col="name">客户姓名</th> <td col="name" >&nbsp;</td></tr>',
			'<tr><th col="accName">还款人名称</th> <td col="accName" >&nbsp;</td><th col="appAmount">贷款金额</th> <td col="appAmount" colspan=3>&nbsp;</td></tr>',
			'<tr><th col="yamount">已收手续费</th> <td col="yamount" >&nbsp;</td><th col="prate">手续费率</th> <td col="prate" >&nbsp;</td><th col="freeamount">应收手续费</th> <td col="freeamount" >&nbsp;</td></tr>',
			'<tr><th col="regBank">开户行</th> <td col="regBank" >&nbsp;</td><th col="payAccount">还款帐号</th> <td col="payAccount" >&nbsp;</td><th col="payAmount">已放款金额</th> <td col="payAmount" >&nbsp;</td></tr>',
			'<tr><th col="loanLimit">贷款期限</th> <td col="loanLimit" >&nbsp;</td><th col="realDate">合约放款日</th> <td col="realDate"  colspan=3 >&nbsp;</td></tr>'];
			
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 95,
			    title : '客户放款手续费信息',
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './fcFree_get.action',
			    params: {
			        id: selId,
			        key:this.bussKey
			    },
			    callback: {
			        sfn: function(jsonData) {
			           jsonData["status"] =  Render_dataSource.statusRender(jsonData["status"]);
			           if(jsonData["yamount"]){
			           		jsonData["yamount"] = jsonData["yamount"] +"元";
			           }
			           jsonData["name"] =  Render_dataSource.custTypeRender(jsonData["name"])+'<span style="color:red;">('+Render_dataSource.custTypeRender(jsonData["custType"])+')</span>';
			        	var loanLimit = Render_dataSource.loanLimitRender(jsonData);
			        	jsonData["loanLimit"] = loanLimit;
						var appAmount = jsonData["appAmount"];
						jsonData["appAmount"] =  Cmw.getThousandths(appAmount)+'元<br/><span style="color:red;">(大写：'+Cmw.cmycurd(appAmount)+')</span>';
						var payAmount = jsonData["payAmount"];
						jsonData["payAmount"] =  Cmw.getThousandths(payAmount)+'元<br/><span style="color:red;">(大写：'+Cmw.cmycurd(payAmount)+')</span>';
						var prate = jsonData["prate"];
						if(prate && prate > 0) jsonData["prate"] = prate +"%";
						_this.setFormValues(jsonData);
			        }
			    }
			}];
			var detailPanel = new Ext.ux.panel.DetailPanel({
				id : this.detailPanlId,
				frame : false,
			    width: 800,
			    detailCfgs: detailCfgs_1
			});
			
			var txt_id = FormUtil.getHidField({
			    fieldLabel: '手续费ID',
			    name: 'id',
			    "width": "160"
			});
			
			var txt_contractId = FormUtil.getHidField({
			    fieldLabel: '借款合同ID',
			    name: 'contractId',
			    "width": "160"
			});
			
			var callback = function(cboGrid,selVals){
				var grid = cboGrid.grid;
				var record = grid.getSelRow();
				var account = record.get("account");
				txt_bankAccount.setValue(account);
			}
			var cbog_accountId = ComboxControl.getAccountCboGrid({
				fieldLabel:'放款银行',name:'accountId',
				allowBlank : false,readOnly:true,
				isAll:false,width:160,callback:callback,
				params : {isIncome:1,sysId : _this.sysId}});
				
			var txt_bankAccount = FormUtil.getReadTxtField({
			    fieldLabel: '银行帐号',
			    name: 'bankAccount',
			    "width": 160
			});
			
			
			var txt_freeamount = FormUtil.getMoneyField({
			    fieldLabel: '应收手续费',
			    name: 'freeamount',
			    readOnly : true,
			    "width": 160
			});
			
			var txt_yamount = FormUtil.getMoneyField({
			    fieldLabel: '已收手续费',
			    name: 'yamount',
			    readOnly : true,
			    "width": 160
			});
			
			var txt_amount = FormUtil.getMoneyField({
			    fieldLabel: '本次实收手续费',
			    name: 'amount',
			    "width": 160
			});
			
			txt_amount.addListener('change',function(){
				_this.change();
			});
			
			var bdat_lastDate = FormUtil.getDateField({
			    fieldLabel: '收款日期',
			    name: 'lastDate',
			    "width": 160,
			    "allowBlank": false,
			    editable:false,
			    "maxLength": 50
			});

			var layout_fields = [txt_id,txt_contractId,{
			    cmns: FormUtil.CMN_THREE,
			    fields: [cbog_accountId, txt_bankAccount,bdat_lastDate, txt_freeamount,txt_yamount,txt_amount]
			}];
			var frm_cfg = {
				title:'手续费信息录入',
				labelWidth : 95,
				height : 140,
			    url: './fcFree_chargefree.action'
			};
			var appform = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			this.appFrm = appform;
			detailPanel.add(appform);
			return detailPanel;
		},
		/**
		 * 为表单元素赋值
		 */
		setFormValues : function(jsonData){
			var _this = this;
			this.resetData();
			this.appFrm.setFieldValues(jsonData);
			var dat_lastDate = this.appFrm.findFieldByName('lastDate');
			dat_lastDate.setValue(new Date());
			var amount =  parseFloat(jsonData.freeamount) -  parseFloat(jsonData.yamount);
			var txt_amount = this.appFrm.findFieldByName('amount');
			txt_amount.setValue(amount);
			var txt_contractId = this.appFrm.findFieldByName('contractId');
			txt_contractId.setValue(_this.contractId);
		},
		/**
		 * 保存数据
		 */
		saveData : function(){
			var _this = this;
			var cbodAccountId = this.appFrm.findFieldByName("accountId");
			var accountId = cbodAccountId.getValue();
			if(!accountId){
				ExtUtil.alert({msg:'请选择放款银行!'});
				return;
			}
			EventManager.frm_save(this.appFrm,{beforeMake : function(formDatas){
				formDatas.key = _this.bussKey;
				var isVoucher = _this.apptbar.getValue("isVoucher");
				formDatas.isVoucher = isVoucher ? 1 : 0;/* 1 : 表示自动生成财务凭证*/
				formDatas.vtempCode = _this.vtempCode;
				formDatas.sysId = _this.sysId;
			},sfn:function(formDatas){
				_this.close();
			 	_this.parent.reload();
			}});
		},
		/**
		 * 本次实收手续费--》 失去焦点的是方法
		 */
		change : function(){
			var yamount = this.appFrm.getValueByName('yamount');
			var amount = this.appFrm.getValueByName('amount');
			var txt_yamount = this.appFrm.findFieldByName('yamount');
			var txt_amount = this.appFrm.findFieldByName('amount');
			var freeamount = this.appFrm.getValueByName('freeamount');
			var yam = this.mount.yamount.yamount;
			var am = this.mount.yamount.freeamount;
			var ya =  parseFloat(yam)+parseFloat(amount);
			if(ya>freeamount){
				var yf = parseFloat(ya)-parseFloat(freeamount);
				 ExtUtil.confirm({title:'提示',msg:'对不起，您输入收取手续费金额超出<span style=color:red;font-weight:bold;>'+yf+'</span>元，请重新输入！',
				 fn:function(btn){
				 	if(btn == 'yes' || btn == 'no'){
				 		txt_amount.reset();
				 		txt_yamount.reset();
				 		txt_yamount.setValue(yam);
				 		return;
				 	}
				 }});
			}else{
				txt_yamount.reset();
				txt_yamount.setValue(ya);
			}
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
			this.appFrm.reset();
		},
		/**
		 * 关闭窗口
		 */
		close : function(){
			this.resetData();
			this.appWin.hide();
		},
		/**
		 * 销毁组件
		 */
		destroy : function(){
			this.appWin.close();	//关闭并销毁窗口
			this.appWin = null;		//释放当前窗口对象引用
		}
	};
});
