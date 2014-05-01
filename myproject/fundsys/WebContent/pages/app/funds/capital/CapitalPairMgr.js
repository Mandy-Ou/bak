/*同心日科技公司Erp 系统 命名空间*/
Ext.namespace("skythink.cmw.workflow.bussforms");
/**
 * 资金池管理
 */
skythink.cmw.workflow.bussforms.CapitalPairMgr = function(){
	this.init(arguments[0]);
}

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.workflow.bussforms.CapitalPairMgr,Ext.util.MyObservable,{
		initModule : function(tab,params){
			/*由必做或选做业务菜单传入的回調函数，主要功能：
			 * finishBussCallback : 当业务表单保存后，更新必做或选做事项为已做,
			 * unFinishBussCallback : 当删除业务表单后，取消已做标识
			 * */
			var finishBussCallback = tab.finishBussCallback;
			var unFinishBussCallback = tab.unFinishBussCallback;
			this.module = new Cmw.app.widget.AbsPanelView({
				tab : tab,
				params : params,/*apptabtreewinId,tabId,sysid*/
				getAppCmpt : this.getAppCmpt,
				getToolBar : this.getToolBar,
				getBatchDatas:this.getBatchDatas,
				getDatas:this.getDatas,
				createForm : this.createForm,
				getAppGrid : this.getAppGrid,
				getDetailPanel:this.getDetailPanel,
				showCustomerDialog:this.showCustomerDialog,
				changeSize : this.changeSize,
				destroyCmpts : this.destroyCmpts,
				globalMgr : this.globalMgr,
				refresh : this.refresh,
				finishBussCallback : finishBussCallback,
				unFinishBussCallback : unFinishBussCallback
			});
		},
		/**
		 * 获取组件方法
		 */
		getAppCmpt : function(){
			var _this = this;
			var selId  = null;
			var appPanel = new Ext.Panel({autoScroll:true,border : false});
			this.globalMgr.appgrid=this.getAppGrid();
			var heightVal=400;
			appPanel.add({items:[this.getDetailPanel(),this.getToolBar(),this.globalMgr.appgrid]});
			return appPanel;
		},
		/**
		 * 刷新方法
		 * @param {} optionType
		 * @param {} data
		 */
		refresh:function(optionType,data){
			
		},
		/**
		 * 组件大小改变方法
		 * @param {} whArr
		 */
		changeSize : function(whArr){
			var width = CLIENTWIDTH - Ext.getCmp(ACCORDPNLID);
			var height = CLIENTHEIGHT - 180;
			this.appPanel.setWidth(width);
			this.appPanel.setHeight(height);
		},
		/**
		 * 查询工具栏
		 */
		getToolBar : function(){
			var self = this;
			var barItems = [{
				text : Btn_Cfgs.CAPIATL_LABEL_TEXT,
				iconCls:'page_add',
				tooltip:Btn_Cfgs.CAPIATL_LABEL_TEXT,
				token : '选择委托人',
				handler : function(){
					    var that = this;
						self.globalMgr.winEdit.show({key:that.token,self:self});
					}
			},{
				text : Btn_Cfgs.CAPSAVE_LABEL_TEXT,
				iconCls:'page_edit',
				tooltip:Btn_Cfgs.CAPSAVE_LABEL_TEXT,
				token : '保存',
				handler : function(){
					    var that = this;
						self.globalMgr.winEdit.show({key:that.token,self:self});
					}
			},{
				text : Btn_Cfgs.DELETE_BTN_TXT,
				iconCls:'page_delete',
				tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
				token : '删除',
				handler : function(){/*
					var that = this;
						self.globalMgr.winEdit.show({key:that.token,self:self});
					*/
					var store = self.globalMgr.appgrid.getStore();//获取grid存储的数据
					var id = self.globalMgr.appgrid.getSelRows();//返回选中的行对象:数组的形式
					store.remove(id);//从对象中删除选中的
					
					}
			}];
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
			return toolBar;
		},
		 getAppGrid:function(){
			var _this = this;
			var structure_1 = [{
			    header: '客户编号',
			    name: 'code',
			    width: 120
			},{
			    header: '用戶名',
			    name: 'name',
			    width: 120
			},
				{
			    header: '委托金额',
			    name: 'uamount',
			    width: 120,
					renderer: function(val) {
				    return (val && val>0) ? Cmw.getThousandths(val)+'元' : '';
				    }

			},{
			    header: 'contractId',
			    name: 'contractId',
			    width: 120,
			    hidden:true
			},{
			    header: 'id',
			    name: 'entrustCustId',
			    width: 120,
			    hidden:true
			},{
			    header: 'entrusContid',
			    name: 'eid',
			    width: 120,
			    hidden:true
			}];
			
			var hid_formId = FormUtil.getMoneyField({
				    name: 'name',
				    "width": 135,
				    "allowBlank": false,id:'appountId'
				});
			var appgrid = new Ext.ux.grid.MyEditGrid({
			    structure: structure_1,
			    needPage: false,
			    isLoad: true,
    	  		url : './fuCapitalPair_list.action',
			  	editEles : {2:hid_formId},
			  	height:600,
			    clicksToEdit : 1,selModel: new Ext.grid.RowSelectionModel({singleSelect:false})
			});
			return appgrid;
		},
		/**	
		 * 创建详情面板
		 */
		 getDetailPanel : function() {
				var _this = this;
				var htmlArrs_1 = ['<tr><th col="code">客户编号</th> <td col="code" >&nbsp;</td><th col="appAmount">贷款金额</th> <td col="appAmount" style="color:red;" >&nbsp;</td><th col="payDate">放款日期</th> <td col="payDate" >&nbsp;</td></tr>',
			'<tr><th col="borBank">借款银行</th> <td col="borBank" >&nbsp;</td><th col="borAccount">借款帐号</th> <td col="borAccount">&nbsp;</td><th col="accName">帐户户名</th> <td col="accName" >&nbsp;</td></tr>',
			'<tr><th col="payDay">每期结算日</th> <td col="payDay" >&nbsp;</td><th col="mgrtype">管理费收取方式</th> <td col="mgrtype">&nbsp;</td><th col="mrate">管理费率</th> <td col="mrate" >&nbsp;</td></tr>'];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 150,
			    /* title : '#TITLE#', */
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './fuCpairDetail_getCusName.action',
			    params: {
			        id: _this.params.applyId
			    },
			    reload:true,
			    callback: {
			        sfn: function(jsonData) {
			        var appAmount = jsonData["appAmount"];
	         	      var tdote=new Date(jsonData["payDate"]);//转换为时间格式（返回值类型是时间）
	    		      jsonData["payDate"]=Ext.util.Format.date(tdote,'Y-m-d');//进行时间的格式化
						jsonData["appAmount"] = Cmw.getThousandths(appAmount)
								+ '元&nbsp;&nbsp;<span style="color:red;"></br>(大写：'
								+ Cmw.cmycurd(appAmount) + ')</span>';
								_this.globalMgr.LoanId=jsonData["id"];
//								var store = _this.globalMgr.appgrid.getStore();//获取grid所存储的字段信息
								_this.globalMgr.appAmount=appAmount;
								}}
			}];
			var detailPanel = new Ext.ux.panel.DetailPanel({
			    detailCfgs: detailCfgs_1
			});
			return detailPanel;
		},
		showCustomerDialog : function() {
			var _this = this;
			var parentCfg = {
				callback : function(record) {
							for(var i=0;i<record.length;i++){
								_this.globalMgr.date=record[i].data;
								record[i].data.entrustCustId=record[i].data.id;
								record[i].data.amt=record[i].data.appAmount;
								record[i].data.contractId=_this.globalMgr.LoanId;
								_this.globalMgr.appgrid.addRecord(record[i].data);
							}
				}
			};
			if (this.customerDialog) {
				this.customerDialog.show(parentCfg);
			} else {
			var _this = this;
			Cmw.importPackage('pages/app/dialogbox/CpairDetailDialogbox',
			function(module) {_this.customerDialog = module.DialogBox;_this.customerDialog.show(parentCfg);});
			}
		},
	/**
	 * 组件销毁方法
	 */
	destroyCmpts : function(){
	},
	getBatchDatas : function(){
			var _this=this;
			var store = _this.globalMgr.appgrid.getStore();
			var arr = [];
			for(var i=0,count = store.getCount(); i<count; i++){
				var record = store.getAt(i);
				var id = record.id;
				var entrustCustId = record.get('entrustCustId');
				var contractId = record.get('contractId');
				var amt = record.get('amt');
				arr[arr.length] = {entrustCustId:entrustCustId,contractId:contractId,amt:amt};
			}
			return arr;
		},
	getDatas : function(){
			var _this=this;
			var store = _this.globalMgr.appgrid.getStore();
			var arr = [];
			for(var i=0,count = store.getCount(); i<count; i++){
				var record = store.getAt(i);
				var id = record.get('eid');
				var amt = record.get('amt');
				arr[arr.length] = {id:id,amt:amt};
			}
			return arr;
		},
	globalMgr : {
		LoanId:null,
		appform_1:null,
		date:null,
		AddButton:null,
		 height : null,
	 	custType:null,
		AddBtn : null,
		Dialog:null,
		appAmount:null,
		DelBtn : null,
		EditBtn : null,
		TJBtn : null,
		id : null,
		activeKey: null,
		formId:this.params.applyId,
		appgrid :null,
		appgrid2 :null,
		unAmount : null,
		sysId : this.params.sysid,
		detailPanel_1 : null,
			winEdit : {
			show : function(parentCfg){
				var _this = parentCfg.self;
				var winkey=parentCfg.key;
				var parent ={};
				_this.globalMgr.formId = _this.params.applyId;
				parent.formId = _this.globalMgr.formId;
//				parent.appgrid = _this.globalMgr.appgrid;
				parent.AddBtn  = _this.globalMgr.AddBtn;
				parent.sysId = _this.globalMgr.sysId;
				parentCfg.parent = parent;
				_this.globalMgr.activeKey = winkey;
				if(winkey=="选择委托人"){
//					_this.globalMgr.appform_1.setDisabled(false);
					_this.showCustomerDialog();
				}else if(winkey=="保存"){
							var params = _this.globalMgr.date;
							var RowVal = _this.globalMgr.appgrid.getModRowVals();
							if (params) {
								var Edit=_this.getBatchDatas();
								var content=_this.getDatas();//返回的数组对象:Ext.encode( Mixed o ) : String ;把对象转换为字符串，用这个方法可以在ajax提交时返回数据
								if(content){
									var sum=0;
									for(var i=0;i<content.length;i++){
										var amt=content[i].amt;
										sum=parseInt(sum);
										sum+=parseInt(amt);
										}
										alert(sum);
										if(sum!=_this.globalMgr.appAmount){
											Ext.MessageBox.alert("提示","没有正确匹配金额:"+_this.globalMgr.appAmount+"<span style='color:red;'>元</span>");
										}else{
										EventManager.get('./fuCpairDetail_save.action',
										{
											params : {
												Edit : Ext.encode(Edit),
												content : Ext.encode(content)
											},
											sfn : function(json_data) {
												Ext.MessageBox.alert("提示:","数据保存成功！");
												},
											ffn : function() {
												Ext.MessageBox.alert("提示:","数据保存失败！");
											}})}}
							}
				}
			}			
		}
	}
});
