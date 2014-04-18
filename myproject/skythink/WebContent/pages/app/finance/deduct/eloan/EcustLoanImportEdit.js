/**
 * 企业贷款导入文件批量放款
 * @author 彭登浩
 * @date 2013-01-20 15:59:32
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		params : null,
		parent : null,
		apptbar : null,
		mainPanel : null,
		appGrid : null,/*批量数据显示Grid*/
		appFrm : null,
		appWin : null,
		gridTitle : '导入的待放款企业客户列表',
		bussKey : null,/*钥匙KEY*/
		ids : null,/*待放款的放款单ID列表*/
		sysId : null,
		uamount :null,
		total :null,
		vtempCode : VOUCHERTEMP_CODE.LOANINVOCE,/*放款凭证模板编号*/
		setParams:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.sysId = parentCfg.parent.sysId;
			this.bussKey = parentCfg.bussKey;
			if(!this.bussKey){
				ExtUtil.error({msg:'bussKey 不能为空!'});
			}
			this.params = this.parentCfg.params;
		},
		createAppWindow : function(){
			this.apptbar = this.createToolBar();
			this.mainPanel = this.createMainPanel();
			this.appWin = new Ext.ux.window.MyWindow({width:800,height:372,tbar:this.apptbar,items : [this.mainPanel],
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
			this.resetData();
			this.appGrid.setTitle(this.gridTitle);
			this.appWin.show();
			this.appFrm.findFieldByName('realDate').setValue(CURENT_DATE());
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
					text : Btn_Cfgs.FILE_IMPORT_BTN_TXT,	/*-- 导入文件 --*/ 
					iconCls:Btn_Cfgs.FILE_IMPORT_CLS,
					tooltip:Btn_Cfgs.FILE_IMPORT_TIP_BTN_TXT,
					handler : function(){
						_this.uploadFile();
					}
				},{
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
			var batchGrid = this.createBatchGrid();
			this.appGrid = batchGrid;
			var appForm = this.createAppForm();
			this.appFrm = appForm;
			var mainPanel = new Ext.Panel({border:false,autoScroll:true,items:[batchGrid,appForm]});
			return mainPanel;
		},
		/**
		 * 创建批处理GRID
		 */
		createBatchGrid : function(){
			var structure_1 = [{
			    header: '放款单编号',
			    name: 'code'
			},
			{
			    header: '企业名称',
			    name: 'name',
			    width:100
			},
			{
			    header: '贷款金额',
			    name: 'appAmount',
				 renderer: function(val) {
			       return val ? Cmw.getThousandths(val)+'元' : '';
			    }
			},
			{
			    header: '收款人名称',
			    name: 'payName',
			    width:100
			},
			{
			    header: '开户行',
			    name: 'regBank',
			    width:100
			},
			{
			    header: '收款帐号',
			    name: 'account',
			    width:100
			},
			{
			    header: '放款金额',
			    name: 'payAmount',
			    width:100,
				renderer: function(val) {
			       return val ? Cmw.getThousandths(val)+'元' : '';
			    }
			},
			{
			    header: '合约放款日期',
			    name: 'payDate',
			    width:100
			}];
		
			var appgrid = new Ext.ux.grid.AppGrid({
				title : this.gridTitle,
			    structure: structure_1,
			    needPage: false,
			    isLoad: false,
			    autoWidth : true,
			    height:250,
			    keyField: 'id'
			});
			return appgrid;
		},
		/**
		 * 创建表单
		 */
		createAppForm : function(){
			var _this = this;
			var callback = function(cboGrid,selVals){
				var grid = cboGrid.grid;
				var record = grid.getSelRow();
				var account = record.get("account");
				_this.uamount = record.get("uamount");
				txt_bankAccount.setValue(account);
			}
			var cbog_accountId = ComboxControl.getAccountCboGrid({
				fieldLabel:'放款银行',name:'accountId',
				allowBlank : false,readOnly:true,
				isAll:false,width:130,callback:callback,
				params : {isPay:1,sysId : _this.sysId}});
			
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

			var layout_fields = [{
			    cmns: FormUtil.CMN_THREE,
			    fields: [cbog_accountId, txt_bankAccount, bdat_realDate]
			}];
			var frm_cfg = {
				title:'放款信息录入', 
			    url: './fcLoanInvoce_loans.action'
			};
			var appform = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform;
		},
		
		/**
		 * 为表单元素赋值
		 */
		setFormValues : function(jsonData){
			this.resetData();
			this.appFrm.setFieldValues(jsonData);
			var dat_realDate = this.appFrm.findFieldByName('realDate');
			dat_realDate.setValue(jsonData.payDate);
		},
		/**
		 * 导入文件
		 */
		uploadFile : function(){
			if(!this.uploadWin){
				var _this = this;
				this.uploadWin = new Ext.ux.window.MyUpWin({title:'导入文件',label:'请选择文件',width:450,dir:'xls_import_deduct_path',allowFiles:"allowFiles_xlsreport_ext",
				sfn:function(fileInfos){
					_this.submitData(fileInfos);
				}});
			}
			this.uploadWin.show();
		},
		/**
		 * 提交表单数据
		 */
		submitData : function(fileInfos){
			var _this = this;
			var filePath = null;
			if(fileInfos){
				var fileInfo = fileInfos[0];
				filePath = fileInfo.serverPath;
			}
			EventManager.get('./fcLoanInvoce_readxls.action',{params:{key:_this.bussKey,filePath : filePath},sfn:function(json_data){
				_this.appGrid.loadJsonDatas(json_data);
				_this.gatherData(json_data);
			}});
		},
		/**
		 * 汇总数据
		 */
		gatherData : function(json_data){
			var _this = this;
			var totalAmount = 0;
			var records = json_data.list;
			var count = json_data.totalSize;
			var gridView = _this.appGrid.getView();
			var idArr = [];
			for(var i=0; i<count; i++){
				var recrod = records[i];
				var id = recrod["id"];
				var locked = recrod["locked"];
				if(locked){
					gridView.getRow(i).style.color = "red";	
				}
				idArr[idArr.length] = id;
				var payAmount = recrod["payAmount"];
				if(!payAmount) continue;
				totalAmount += parseFloat(payAmount);
			}
			_this.total = totalAmount;
			_this.ids = idArr.join(",");
			var title = _this.gridTitle + 
			"&nbsp;&nbsp;<span style='color:red;font-weight:normal;'>【成功导入<span style='font-weight:bold;'>"+count+"</span>待放款记录条，" +
			" 累计放款金额：<span style='font-weight:bold;'>"+Cmw.getThousandths(totalAmount)+"元(大写："+Cmw.cmycurd(totalAmount)+")</span>】</span>";
			
			_this.appGrid.setTitle(title);
			/* 为实际放款日期赋值*/
			var payDate = CURENT_DATE();
			var dat_realDate = _this.appFrm.findFieldByName("realDate");
			dat_realDate.setValue(payDate);
		},
		/**
		 * 保存数据
		 */
		saveData : function(){
			var _this = this;
			var store =  this.appGrid.getStore();
		    var records = store.getRange(0,store.getCount());
		    var count = records.length;
			if(!count){
				ExtUtil.alert({msg: "没有数据请,请导入数据！"});
				return;
			}
			var cbodAccountId = this.appFrm.findFieldByName("accountId");
			var accountId = cbodAccountId.getValue();
			if(!accountId){
				ExtUtil.alert({msg:'请选择放款银行!'});
				return;
			}
			if(parseFloat(_this.appAmount)>parseFloat(_this.uamount)){
				ExtUtil.warn({msg:"选的放款账户余额已不足，请选择其他账号进行放款！"});
				return;
			}
			this.mask('正在计算放款金额...');
			EventManager.frm_save(this.appFrm,{beforeMake : function(formDatas){
				formDatas.ids = _this.ids;
				formDatas.key = _this.bussKey;
				var isVoucher = _this.apptbar.getValue("isVoucher");
				formDatas.isVoucher = isVoucher ? 1 : 0;/* 1 : 表示自动生成财务凭证*/
				formDatas.vtempCode = _this.vtempCode;
				formDatas.sysId = _this.sysId;
			},sfn:function(formDatas){
				_this.close();
			 	_this.refresh();
			 	_this.unmask();
			},ffn:function(json_data){
				if(json_data && json_data.msg){
					ExtUtil.alert({msg:json_data.msg});
					_this.appWin.hide();
				}
				_this.unmask();
			}});
		},
		mask : function(msg){
			Cmw.mask(this.mainPanel,msg);
			this.apptbar.disableButtons(Btn_Cfgs.FILE_IMPORT_BTN_TXT+","+Btn_Cfgs.DO_LOAN_BTN_TXT+","+Btn_Cfgs.CLOSE_BTN_TXT);
		},
		unmask : function(){
			Cmw.unmask(this.mainPanel);
			this.apptbar.enableButtons(Btn_Cfgs.FILE_IMPORT_BTN_TXT+","+Btn_Cfgs.DO_LOAN_BTN_TXT+","+Btn_Cfgs.CLOSE_BTN_TXT);
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
			this.appFrm.reset();
			this.appGrid.removeAll();
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
