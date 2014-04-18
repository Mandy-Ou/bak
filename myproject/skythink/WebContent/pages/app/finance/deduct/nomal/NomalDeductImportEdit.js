/**
 * 个人贷款导入文件批量放款
 * @author 程明卫
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
		gridTitle : '导入的待收款客户列表',
		bussKey : null,/*钥匙KEY*/
		ids : null,/*待放款的放款单ID列表*/
		sysId : null,
		uamount : null,
		total :  null,
		vtempCode : VOUCHERTEMP_CODE.NOMALDEDUCT,/*正常扣收凭证模板编号 constant.js定义*/
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
			this.appWin = new Ext.ux.window.MyWindow({width:900,height:372,tbar:this.apptbar,items : [this.mainPanel],
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
			this.appWin.show();
			this.resetData();
			this.appGrid.setTitle(this.gridTitle);
			this.appFrm.findFieldByName('rectDate').setValue(CURENT_DATE());
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
					text : Btn_Cfgs.DO_NOMAL_BTN_TXT, /*-- 收款 --*/
					iconCls : Btn_Cfgs.DO_NOMAL_CLS,
					tooltip : Btn_Cfgs.DO_NOMAL_TIP_BTN_TXT,
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
			var structure_1 = [{header: '借款合同号', name: 'code'},
			{header: '客户类型',name: 'custType',width: 60,renderer : function(val){return Render_dataSource.custTypeRender(val);}},
			{header: '客户名称', name: 'name'},
			{header: '本期期数',	name: 'phases',width: 60},
			{header: '应还日期',name: 'xpayDate', width: 90},
			{ header: 'Excel应收合计',name: 'ztotalAmount', width: 90,renderer : function(val){return Cmw.getThousandths(val);}},
			{ header: '实际应收合计',name: 'sysztotalAmount', width: 90,renderer : function(val){return Cmw.getThousandths(val);}},
			{header: '本次实收合计', name: 'rtotalAmount',width: 90,renderer : function(val,metaData){
				metaData.style='color:blue;font-weight:bold;';
				return Cmw.getThousandths(val);
			}},
			{ header: '收款状态',name: 'status',width: 75,renderer : function(val){return Render_dataSource.planStatusRender(val);}},
			{ header: '最后收款日期',name: 'lastDate', width: 90},
			{ header: '合同ID', name: 'contractId', hideable : true, hidden : true}
			];
			var num_rtotalAmount = FormUtil.getMoneyField({name : 'rtotalAmount',allowBlank : false});
			var appgrid = new Ext.ux.grid.MyEditGrid({
				title : '待收款客户列表',
			    structure: structure_1,
			    url: './fcNomalDeduct_obtain.action',
			    needPage: false,
			    isLoad: false,
			    autoWidth : true,
			    height:250,
			    editEles : {7:num_rtotalAmount},
			    keyField: 'id'
			});
			
			appgrid.on('afteredit', afterEdit, this );
			function afterEdit(e) {
			    e.record.commit();
			    var store = appgrid.getStore();
			    var records = store.getRange(0,store.getCount());
			    this.gatherTotalAmount(records);
			};
			return appgrid;
		},
		/**
		 * 汇总金额
		 */
		gatherTotalAmount : function(records){
			var totalAmount = 0;
			var count = records.length;
			var idArr = [];
			for(var i=0; i<count; i++){
				var recrod = records[i];
				var id = recrod.id;
				idArr[idArr.length] = id;
				var rtotalAmount = recrod.get("rtotalAmount");
				if(!rtotalAmount) continue;
				totalAmount += parseFloat(rtotalAmount);
			}
			this.total = totalAmount;
			this.ids = idArr.join(",");
			this.updateGridTitle(count, totalAmount);
		},
		/**
		 * 创建表单
		 */
		createAppForm : function(){
			var  _this = this;
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
				params : {isIncome:1,sysId :_this.sysId}});
			
			var txt_bankAccount = FormUtil.getReadTxtField({
			    fieldLabel: '银行帐号',
			    name: 'bankAccount',
			    "width": 160
			});
			
			var dat_rectDate = FormUtil.getDateField({
				fieldLabel : '收款日期',
				name : 'rectDate',
				"width" : 125,
				"allowBlank" : false,
				editable : false,
				"maxLength" : 50
			});
					
			var layout_fields = [{
			    cmns: FormUtil.CMN_THREE,
			    fields: [cbog_accountId, txt_bankAccount, dat_rectDate]
			}];
		
			var frm_cfg = {
				title:'收款信息录入', 
			    url: './fcNomalDeduct_receivables.action'
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
			EventManager.get('./fcNomalDeduct_readxls.action',{params:{key:_this.bussKey,filePath : filePath},sfn:function(json_data){
				_this.appGrid.loadJsonDatas(json_data);
				_this.gatherData(json_data);
				_this.checkData();
			}});
		},
		/**
		 * 检查数据是否已收款，防止重复导入收款的情况
		 */
		checkData : function(){
			var store = this.appGrid.getStore();
			var count = store.getCount();
			var errMsg = [];
			var errNum = 0;
			for(var i=0; i<count; i++){
				var record = store.getAt(i);
				var name = record.get("name");
				var phases = record.get("phases");
				var status = record.get("status");
				var lastDate = record.get("lastDate");
				var sysztotalAmount = record.get("sysztotalAmount") || 0;
				var rtotalAmount = record.get("rtotalAmount") || 0;
				if(status && parseInt(status) == 2){
					errNum++;
					errMsg[errMsg.length] = errNum+":客户"+name+"第"+phases+"期已于<strong>"+lastDate+"</strong>结清";	
				}else{
					if(parseFloat(rtotalAmount)-parseFloat(sysztotalAmount) > 0){
						errNum++;
						errMsg[errMsg.length] = errNum+":客户"+name+"第"+phases+"期的\"本次实收合计\"不能超过\"实际应收合计\"";	
					}
				}
			}
			var isValid = true;
			if(errNum && errNum > 0){
				isValid = false;
				var errMsgStr = "本次导入的Excel数据共有<span style='color:red;font-weight:bold;'>"
				+errNum+"</span>问题待处理，<br/>请将以下问题数据在Excel中处理后，再重新导入并收款：<br/>&nbsp;&nbsp;&nbsp;&nbsp;"+errMsg.join("<br/>&nbsp;&nbsp;&nbsp;&nbsp;");
				ExtUtil.alert({msg:errMsgStr});
			}
			return isValid;
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
				var rtotalAmount = recrod["rtotalAmount"];
				if(!rtotalAmount) continue;
				totalAmount += parseFloat(rtotalAmount);
			}
			_this.ids = idArr.join(",");
			_this.updateGridTitle(count, totalAmount);
		},
		updateGridTitle : function(count,totalAmount){
			var title = this.gridTitle + 
			"&nbsp;&nbsp;<span style='color:red;font-weight:normal;'>【成功导入<span style='font-weight:bold;'>"+count+"</span>待收款记录条，" +
			" 累计收款金额：<span style='font-weight:bold;'>"+Cmw.getThousandths(totalAmount)+"元(大写："+Cmw.cmycurd(totalAmount)+")</span>】</span>";
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
				ExtUtil.alert({msg: "没有数据,请导入数据！"});
				return;
			}
			if(!this.checkData()) return;
			var cbodAccountId = this.appFrm.findFieldByName("accountId");
			var accountId = cbodAccountId.getValue();
			if(!accountId){
				ExtUtil.alert({msg:'请选择收款银行!'});
				return;
			}
			if(parseFloat(_this.appAmount)>parseFloat(_this.uamount)){
				ExtUtil.warn({msg:"选的放款账户余额已不足，请选择其他账号进行放款！"});
				return;
			}
			var txt_bankAccount = this.appFrm.findFieldByName("bankAccount");
			var bankAccount = txt_bankAccount.getValue();
			if(!bankAccount){
				ExtUtil.alert({msg:'没有银行账号，请重新选择银行!'});
				return;
			}
			this.mask('正在收款...');
			var batchDatas = this.getBatchDatas();
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
			this.apptbar.disable();
		},
		unmask : function(){
			Cmw.unmask(this.mainPanel);
			this.apptbar.enable();
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
				var contractId = record.get('contractId');
				var rtotalAmount = record.get('rtotalAmount');
				if(!rtotalAmount || rtotalAmount<0) continue;
				arr[arr.length] = {id : id, contractId : contractId, rtotalAmount : rtotalAmount};
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
