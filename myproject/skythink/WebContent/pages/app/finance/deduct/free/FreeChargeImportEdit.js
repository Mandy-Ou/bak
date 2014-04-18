/**
 * 放款手续费收取导入文件批量放款
 * @author 彭登浩
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
		gridTitle : '导入的待收手续客户列表',
		bussKey : null,/*钥匙KEY*/
		ids : null,/*待收手续列表*/
		sysId : null,
		vtempCode : VOUCHERTEMP_CODE.LOANFREE,/*放款手续费收取凭证模板编号 constant.js定义*/
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
			this.appWin = new Ext.ux.window.MyWindow({width:860,height:370,tbar:this.apptbar,items : [this.mainPanel],
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
			this.appFrm.findFieldByName('lastDate').setValue(CURENT_DATE());
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
					text : Btn_Cfgs.DO_FREE_BTN_TXT, /*-- 收款 --*/
					iconCls : Btn_Cfgs.DO_FREE_CLS,
					tooltip : Btn_Cfgs.DO_FREE_TIP_BTN_TXT,
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
			var mainPanel = new Ext.Panel({border:false,items:[batchGrid,appForm]});
			return mainPanel;
		},
		/**
		 * 创建批处理GRID
		 */
		createBatchGrid : function(){
			var structure_1 = [
			{
			    header: '借款合同编号',
			    name: 'code',
			    width:135
			},
			{
			    header: '实际放款日期',
			    name: 'realDate',
			    width:100
			},
			{
			    header: '放款 单编号',
			    name: 'ccode',
			    width:135
			},
			{
			    header: '已放款金额',
			    name: 'payAmount',
			    width:100,
				renderer: function(val) {
			       return (val && val>0) ? Cmw.getThousandths(val)+'元' : '';
			    }
			},
			{
			    header: '客户姓名',
			    name: 'name',
			    width:135
			},
			{
			    header: '手续费率',
			    name: 'prate',
			    width:135,
			    renderer: function(val) {
			        return val ? val+'%' : '0';
			    }
			},
			{
			    header: '应收手续费',
			    name: 'freeamount',
			    width:135,
				renderer: function(val) {
			       return (val && val>0) ?/*Cmw.getThousandths(val)*/val+'元' : '';
			    }
			},
			{
			    header: '已收手续费',
			    name: 'yamount',
			    width:135,
				renderer: function(val) {
			       return (val && val>0) ?/*Cmw.getThousandths(val)*/val+'元' : '';
			    }
			},
			{
			    header: '本次实收手续费',
			    name: 'notamount',
			    width:135,
				renderer: function(val,metaData,record) {
					metaData.style='color:blue;font-weight:bold;';
			       return (val && val>0) ? /*Cmw.getThousandths(val)*/val+'元' : '';
			    }
			}, { header: '合同ID', name: 'contractId', hideable : true, hidden : true}];
			var num_notamount =  FormUtil.getMoneyField({name : 'notamount',allowBlank : false,width:135});
			var appgrid =new Ext.ux.grid.MyEditGrid({
				title : '待收手续费客户列表',
			    structure: structure_1,
			    url: './fcFree_get.action',
			    needPage: false,
			    isLoad: false,
			    autoScroll:true,
			    autoWidth : true,
			    height:250,
			    editEles : {8:num_notamount},
			    keyField: 'id'
			});
			appgrid.on('afteredit', afterEdit, this );
			function afterEdit(e) {
			    e.record.commit();
			    var store = appgrid.getStore();
			    var records = store.getRange(0,store.getCount());
			    this.gatherTotalAmount(records);
			};
			this.appGrid = appgrid;
			return appgrid;
		},
		/**
		 * 汇总金额
		 */
		gatherTotalAmount : function(records){
			var totalnotamount = 0;
			var count = records.length;
			var idArr = [];
			for(var i=0; i<count; i++){
				var recrod = records[i];
				var id = recrod.id;
				idArr[idArr.length] = id;
				var notamount = recrod.get("notamount");
				if(!notamount) continue;
				totalnotamount += parseFloat(notamount);
			}
			this.ids = idArr.join(",");
			this.updateGridTitle(count, totalnotamount);
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
				txt_bankAccount.setValue(account);
			}
			var cbog_accountId = ComboxControl.getAccountCboGrid({
				fieldLabel:'收款银行',name:'accountId',
				allowBlank : false,readOnly:true,
				isAll:false,width:130,callback:callback,
				params : {isIncome:1,sysId : _this.sysId}});
			
			var txt_bankAccount = FormUtil.getReadTxtField({
			    fieldLabel: '银行帐号',
			    name: 'bankAccount',
			    "width": 160
			});
			
			var bdat_lastDate = FormUtil.getDateField({
			    fieldLabel: '收款日期',
			    name: 'lastDate',
			    "width": 125,
			    "allowBlank": false,
			    editable:false,
			    "maxLength": 50
			});

			var layout_fields = [{
			    cmns: FormUtil.CMN_THREE,
			    fields: [cbog_accountId, txt_bankAccount, bdat_lastDate]
			}];
			var frm_cfg = {
				title:'手续费信息录入',
				labelWidth : 95,
			    url: './fcFree_chargefree.action'
			};
			var appform = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform;
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
			EventManager.get('./fcFree_readxls.action',{params:{key:_this.bussKey,filePath : filePath},sfn:function(json_data){
				_this.appGrid.loadJsonDatas(json_data,false);
				_this.gatherData(json_data);
			}});
		},
		/**
		 * 汇总数据
		 */
		gatherData : function(json_data){
			var _this = this;
			var totalnotamount = 0;
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
				var notamount = recrod["notamount"];
				if(!notamount) continue;
				totalnotamount += parseFloat(notamount);
			}
			_this.ids = idArr.join(",");
			_this.updateGridTitle(count, totalnotamount);
		},
		updateGridTitle : function(count,totalnotamount){
			var title = this.gridTitle + 
			"&nbsp;&nbsp;<span style='color:red;font-weight:normal;'>【成功导入<span style='font-weight:bold;'>"+count+"</span>待收款记录条，" +
			" 累计收款金额：<span style='font-weight:bold;'>"+totalnotamount.toFixed(2)+"元(大写："+Cmw.cmycurd(totalnotamount)+")</span>】</span>";
			this.appGrid.setTitle(title);
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
				ExtUtil.alert({msg:'请选择收款银行!'});
				return;
			}
			var batchDatas = this.getBatchDatas();
			this.mask('正在收款...');
			EventManager.frm_save(this.appFrm,{beforeMake : function(formDatas){
				formDatas.ids = _this.ids;
				formDatas.key = _this.bussKey;
				var isVoucher = _this.apptbar.getValue("isVoucher");
				formDatas.isVoucher = isVoucher ? 1 : 0;/* 1 : 表示自动生成财务凭证*/
				formDatas.vtempCode = _this.vtempCode;
				formDatas.sysId = _this.sysId;
				formDatas.batchDatas = Ext.encode(batchDatas);
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
			this.apptbar.disableButtons(Btn_Cfgs.FILE_IMPORT_BTN_TXT+","+Btn_Cfgs.DO_FREE_BTN_TXT+","+Btn_Cfgs.CLOSE_BTN_TXT);
		},
		unmask : function(){
			Cmw.unmask(this.mainPanel);
			this.apptbar.enableButtons(Btn_Cfgs.FILE_IMPORT_BTN_TXT+","+Btn_Cfgs.DO_FREE_BTN_TXT+","+Btn_Cfgs.CLOSE_BTN_TXT);
		},
		/**
		 * 获取本次批量收款金额
		 */
		getBatchDatas : function(){
			var store = this.appGrid.getStore();
			var arr = [];
			for(var i=0,count = store.getCount(); i<count; i++){
				var record = store.getAt(i);
				var id = record.id;
				var notamount = record.get('notamount');
				if(!notamount || notamount<0) continue;
				var contractId = record.get('contractId');
				arr[arr.length] = {id : id,contractId : contractId, yamount : notamount};
			}
			return arr;
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
