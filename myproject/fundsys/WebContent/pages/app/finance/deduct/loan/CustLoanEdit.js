/**
 * 个人贷款单笔放款
 * @author 程明卫
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
		sysId : null,
		height : null,
		appAmount : null,
		vtempCode : VOUCHERTEMP_CODE.LOANINVOCE,/*放款凭证模板编号*/
		detailPanlId : Ext.id(null,'OneCustLoanDetailPanl'),
		bussKey : null,/*钥匙KEY*/
		setParams:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.sysId = parentCfg.parent.sysId;
			this.bussKey = parentCfg.bussKey;
			if(!this.bussKey){
				ExtUtil.error({msg:'bussKey 不能为空!'});
			}
		},
		createAppWindow : function(){
			this.apptbar = this.createToolBar();
			this.mainPanel = this.createMainPanel();
			var _this = this;
			this.appWin = new Ext.ux.window.MyWindow({width:850,height:460,modal:true,
				tbar:this.apptbar,items : [this.mainPanel],
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
			var isFirstLoad = false;
			if(!this.appWin){
				isFirstLoad = true;
				this.createAppWindow();
				
			}
			this.appWin.optionType = this.optionType;
			this.appWin.show(this.parent.getEl());
			if(isFirstLoad) return;
			
			this.loadData();
		},
		/**
		 * 加载数据
		 */
		loadData : function(){
			var loanDetailPnl = Ext.getCmp(this.detailPanlId);
			var selId = this.parent.getSelId();
			loanDetailPnl.reload({id : selId,key:this.bussKey,custType : 0});
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
			var barItems = [{type:'chk',name:'isVoucher',checked:true,boxLabel:Finsys_Btn_Cfgs.AUTOVOUCHER_BOXLABEL},{type:'sp'},{
				text : Btn_Cfgs.DO_LOAN_BTN_TXT,	/*-- 放款 --*/ 
				iconCls:Btn_Cfgs.DO_LOAN_CLS,
				tooltip:Btn_Cfgs.DO_LOAN_TIP_BTN_TXT,
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
			'<tr><th col="code">放款单编号</th> <td col="code" >&nbsp;</td><th col="ccode">借款合同号</th> <td col="ccode" >&nbsp;</td><th col="name">客户姓名</th> <td col="name" >&nbsp;</td></tr>',
			'<tr><th col="cardType">证件类型</th> <td col="cardType" >&nbsp;</td><th col="cardNum">证件号码</th> <td col="cardNum" >&nbsp;</td><th col="appAmount">贷款金额</th> <td col="appAmount" >&nbsp;</td></tr>',
			'<tr><th col="loanLimit">贷款期限</th> <td col="loanLimit" >&nbsp;</td><th col="rate">贷款利率</th> <td col="rate" >&nbsp;</td><th col="payName">收款人名称</th> <td col="payName" >&nbsp;</td></tr>',
			'<tr><th col="regBank">开户行</th> <td col="regBank" >&nbsp;</td><th col="account">收款帐号</th> <td col="account" >&nbsp;</td><th col="payAmount">放款金额</th> <td col="payAmount" >&nbsp;</td></tr>',
			'<tr><th col="prate">放款手续费率</th> <td col="prate" >&nbsp;</td><th col="payDate">合约放款日</th> <td col="payDate"  colspan=3 >&nbsp;</td></tr>',
			FORMDIY_DETAIL_KEY
			];
			
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 95,
			    title : '个人客户放贷信息',
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './fcLoanInvoce_obtain.action',
			    params: {
			        id: selId,
			        custType : 0,
			        key:this.bussKey
			    },
			    formDiyCfg : {sysId:_this.sysId,formdiyCode:FORMDIY_IND.FORMDIY_LOANINVOCE,formIdName:'id'},
			    callback: {
			        sfn: function(jsonData) {
			           jsonData["cardType"] =  Render_dataSource.gvlistRender('100002',jsonData["cardType"]);
			        	var loanLimit = Render_dataSource.loanLimitRender(jsonData);
			        	jsonData["loanLimit"] = loanLimit;
						_this.appAmount = jsonData["appAmount"];
						jsonData["appAmount"] =  Cmw.getThousandths(_this.appAmount)+'元<br/><span style="color:red;">(大写：'+Cmw.cmycurd(_this.appAmount)+')</span>';
						jsonData["rate"] = jsonData["rate"] +"%&nbsp;&nbsp;<span style='color:red;'>("+ Render_dataSource.rateTypeRender(jsonData["rateType"])+")</span>";
						var payAmount = jsonData["payAmount"];
						jsonData["payAmount"] =  Cmw.getThousandths(payAmount)+'元<br/><span style="color:red;">(大写：'+Cmw.cmycurd(payAmount)+')</span>';
						
						var prate = jsonData["prate"];
						if(prate && prate > 0) jsonData["prate"] = prate +"%";
						_this.setFormValues(jsonData);
						if(parseFloat(jsonData["ysMat"])){
							var ysMat = jsonData["ysMat"];
			        		jsonData["ysMat"] =ysMat+'元&nbsp;&nbsp;<span style="color:red;font-weight:bold;">(大写：'+Cmw.cmycurd(ysMat)+')</span>';
			        	}else {
			        		jsonData["ysMat"] = "";
			        	}
			        	if(parseFloat(jsonData["ysRat"])){
			        		var ysRat = jsonData["ysRat"];
			        		jsonData["ysRat"] =ysRat+'元&nbsp;&nbsp;<span style="color:red;font-weight:bold;">(大写：'+Cmw.cmycurd(ysRat)+')</span>';
			        	}else {
			        		jsonData["ysRat"] = "";
			        	}
			        }
			    }
			}];
			var detailPanel = new Ext.ux.panel.DetailPanel({
				id : this.detailPanlId,
				frame : false,
				autoScroll : true,
				autoHeight : true,
			    width: 800,
			    detailCfgs: detailCfgs_1
			});
			
			var txt_id = FormUtil.getHidField({
			    fieldLabel: '放款单ID',
			    name: 'id',
			    "width": "125"
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
				fieldLabel:'放款银行',name:'accountId',
				allowBlank : false,readOnly:true,
				isAll:false,width:130,callback:callback,
				params : {isPay:1,sysId :_this.sysId}});
			
			var txt_bankAccount = FormUtil.getReadTxtField({
			    fieldLabel: '银行帐号',
			    name: 'bankAccount',
			    "width": 160
			});
			
			var bdat_realDate = FormUtil.getDateField({
			    fieldLabel: '放款日期',
			    name: 'realDate',
			    "width": 125,
			    "allowBlank": false,
			    editable:false,
			    "maxLength": 50
			});
			var formDiyContainer = new Ext.Container({layout:'fit'});
			var layout_fields = [txt_id,{
			    cmns: FormUtil.CMN_THREE,
			    fields: [cbog_accountId, txt_bankAccount, bdat_realDate]
			},formDiyContainer];
			var frm_cfg = {
				title:'放款信息录入', 
				height : 150,
			    url: './fcLoanInvoce_loans.action',
			     formDiyCfg : {
				    	sysId : _this.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_LOANINVOCE,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id',/*对应表单中的ID Hidden 值*/
				    	container : formDiyContainer /*自定义字段存放容器*/
			    	}
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
			this.resetData();
			this.appFrm.setFieldValues(jsonData);
			var dat_realDate = this.appFrm.findFieldByName('realDate');
			dat_realDate.setValue(jsonData.payDate);
			var ele =["ysMat","ysRat","ysMatMonth","ysRatMonth","mrateBank"];
			this.setFormDiyEleSetVl(jsonData,ele);
		},
		/**
		 * 为表单个性化字段赋值
		 */
		setFormDiyEleSetVl : function(jsonData,ele){
			for(var i=0,len = ele.length;i<len;i++){
				var element = this.appFrm.findFieldByName("FORMDIY_"+ele[i]);
				if(element){
					element.setValue(jsonData[ele[i]]);
				}
			}
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
			 	_this.refresh();
			}});
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
