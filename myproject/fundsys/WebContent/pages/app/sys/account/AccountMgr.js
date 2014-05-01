/**
 * 九洲科技公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 公司帐户设置 UI smartplatform_auto 2012-08-10 09:48:16
 */ 
cmw.skythink.AccountMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.AccountMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGContainerView({
			tab : tab,
			params : params,
			hasTopSys : true,
			notify : this.notify,
			getQueryFrm : this.getQueryFrm,
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			globalMgr : this.globalMgr,
			refresh : this.refresh
		});
	},
	notify : function(data){
		var self = this;
		var sysId = data.id;
		this.globalMgr.sysId=sysId;
		this.globalMgr.tb.enable(); 
		this.globalMgr.query(this);
	},
	/**
	 * 获取查询Form 表单
	 */
	getQueryFrm : function(){
		var self =this;
		var cbo_isenabled = FormUtil.getLCboField({fieldLabel : '可用标识',name:'isenabled',data: Lcbo_dataSource.getAllDs(Lcbo_dataSource.isenabled_datas)});
		var txt_bankName = FormUtil.getTxtField({fieldLabel: '银行名称',name: 'bankName'});
		var cbo_atype = FormUtil.getLCboField({fieldLabel : '账户类型',name:'atype',data: Lcbo_dataSource.getAllDs(Lcbo_dataSource.account_atype_datas)});
		var layout_fields = [{cmns:'THREE',fields:[cbo_isenabled,txt_bankName,cbo_atype]}]
		var queryFrm = FormUtil.createLayoutFrm(null,layout_fields);
		return queryFrm;
	},
	
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var self = this;
		var toolBar = null;
		var barItems = [{
			token : '查询',
			text : Btn_Cfgs.QUERY_BTN_TXT,
			iconCls:'page_query',
			tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.query(self);
			}
		},{
			token : '重置',
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls:'page_reset',
			tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
			handler : function(){
				self.queryFrm.reset();
			}
		},{type:"sp"},{
			token : '添加',
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls:Btn_Cfgs.INSERT_CLS,
			tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"添加",self:self});
			}
		},{
			token : '编辑',
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:Btn_Cfgs.MODIFY_CLS,
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"编辑",optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{
			token : '账号映射',
			text : Btn_Cfgs.BANK_ACCOUNT_MAPPING_BTN_TXT,
			iconCls:Btn_Cfgs.BANK_ACCOUNT_MAPPING_BTN_CLS,
			tooltip:Btn_Cfgs.BANK_ACCOUNT_MAPPING_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.showFinDialog(self,"财务系统账号映射",this);
			}
		}
		,{type:"sp"},{
			token : '起用',
			text : Btn_Cfgs.ENABLED_BTN_TXT,
			iconCls:'page_enabled',
			tooltip:Btn_Cfgs.ENABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.enabledData('./sysAccount_enabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
			}
		},{
			token : '禁用',
			text : Btn_Cfgs.DISABLED_BTN_TXT,
			iconCls:'page_disabled',
			tooltip:Btn_Cfgs.DISABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.disabledData('./sysAccount_disabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});
			}
		},{
			token : '删除',
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./sysAccount_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		this.globalMgr.tb = toolBar;
		return toolBar;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
		var _this = this;
			var structure_1 = [{
		    header: '可用标识',
		    name: 'isenabled',
		    width: 60,
		    renderer : Render_dataSource.isenabledRender},
			{
			    header: '科目编号',
			    name: 'code',
			    width: 130
			},
			{
			    header: '银行名称',
			    name: 'bankName',
			    width: 130
			},
			{
			    header: '银行帐号',
			    name: 'account',
			    width: 155
			},
			{
				header: '放款账户',
			    name: 'isPay',
			    width: 60,
			    renderer: Render_dataSource.isRequiredRender
			},
			{
				header: '收款账户',
			    name: 'isIncome',
			    width: 60,
			    renderer: Render_dataSource.isRequiredRender
			},
			{
			    header: '账户类型',
			    name: 'atype',
			    width: 125,
			    renderer: Render_dataSource.account_atypeRender
			},
			{
			    header: '财务账号ID',
			    name: 'refId',
			    width: 100
			}
			,{header: '说明',name: 'remark',width: 200}];
			
			var appgrid_1 = new Ext.ux.grid.AppGrid({
//			    title: '公司帐户设置',
			    tbar : this.toolBar,
			    structure: structure_1,
			    url: './sysAccount_list.action',
			    needPage: false,
			    keyField: 'id',
			    isLoad: false
			});
		return appgrid_1;
	},
	refresh:function(optionType,data){
		this.globalMgr.query(this);
		this.globalMgr.activeKey = null;
	},
	
	/**
	 *  获取 Form 表单对象
	 */
	globalMgr : {
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params = _this.queryFrm.getValues();
			params.sysId = _this.globalMgr.sysId;
			if(params) {
				EventManager.query(_this.appgrid,params);
			}
		},
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		tb : null,
		sysId : null,
		activeKey : null,
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent = _this.appgrid;
				parentCfg.parent = parent;
				parentCfg.sysId = _this.globalMgr.sysId;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule=null;
					if(winkey=="添加"|| winkey=="编辑"){
						winModule="AccountEdit";
					}
					Cmw.importPackage('pages/app/sys/account/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		},
		/**
		 * 弹出财务系统帐号关联窗口
		 * @param {} _this
		 * @param winkey Window 窗口唯一标识KEY
		 * @param {} button
		 */
		showFinDialog : function(_this,winkey,button){
			var id = _this.appgrid.getSelId();
			if(!id) return;
			var btnEl = button.el;
			var parentCfg = {
				params : {isenabled:1},
				parent : btnEl,
				callback : function(selIds,record){
					var refId = record.get("refId");
					var code = record.get("code");
					 EventManager.get('./sysAccount_update.action',{params:{id:id,fsbankAccountId:selIds,refId:refId,code:code},sfn:function(json_data){
					 	_this.globalMgr.query(_this);
					 	ExtUtil.alert({msg:Msg_SysTip.msg_dataSucess});
					 },ffn:function(json_data){
					 	ExtUtil.alert({msg:Msg_SysTip.msg_dataFailure});
					 }});
				}
			};
			
			if(_this.appCmpts[winkey]){
				_this.appCmpts[winkey].show(parentCfg);
			}else{
				Cmw.importPackage('pages/app/dialogbox/BankAccountDialogbox',function(module) {
				 	_this.appCmpts[winkey] = module.DialogBox;
				 	_this.appCmpts[winkey].show(parentCfg);
		  		});
			}
		}
	}
});