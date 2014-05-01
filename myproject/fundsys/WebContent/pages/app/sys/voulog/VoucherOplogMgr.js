/**
 * 九洲科技公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 凭证日志总查询UI smartplatform_auto 2012-08-10 09:48:16
 */ 
cmw.skythink.VoucherOplogMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.VoucherOplogMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		var hasTopSys = true;
		if(params.hasOwnProperty("hasTopSys")){
			 hasTopSys = params.hasTopSys;
			 if(Ext.isString(hasTopSys) && hasTopSys == 'false') hasTopSys = false;
		}
		this.module = new Cmw.app.widget.AbsPanelView({
			tab : tab,
			params : params,
			hasTopSys : hasTopSys,
			notify : this.notify,
			getToolBar : this.getToolBar,
			getQueryFrm :this.getQueryFrm,
			getAppGrid1 : this.getAppGrid1,
			getAppGrid2 : this.getAppGrid2,
			getAppCmpt : this.getAppCmpt,
			changeSize : this.changeSize,
			destroyCmpts :this.destroyCmpts,
			globalMgr : this.globalMgr,
			getCenterPnl: this.getCenterPnl,
			refresh : this.refresh
		});
	},
	notify : function(data){
		var self = this;
		var sysId = data.id;
		this.globalMgr.sysId=sysId;
		this.globalMgr.query(self);
	},
	getAppCmpt : function(){
		var _this = this;
		var panelMain = new Ext.Panel({
			border : false,autoScroll:false,
			items:[_this.getQueryFrm(),_this.getToolBar(),_this.getCenterPnl()]
		});
		return panelMain;
	},
	getCenterPnl : function(){
		var _this = this;
		var centerPnl = new Ext.Panel({
			layout:'border',border : false,
			items :[_this.getAppGrid1(),_this.getAppGrid2()]
		});
		return centerPnl;
	},
	/**
	 * 获取查询Form 表单
	 */
	getQueryFrm : function(){
		var self =this;
		var cbo_status = FormUtil.getLCboField({fieldLabel : '状态码',name:'status',
			data: Lcbo_dataSource.getAllDs(Lcbo_dataSource.voustatus_datas)});
		var cbo_errCode = FormUtil.getLCboField({fieldLabel: '错误代码',name: 'errCode',width : 180,
			data: Lcbo_dataSource.getAllDs(Lcbo_dataSource.vouerrCode_datas)});
		var cbo_bussTag = FormUtil.getLCboField({fieldLabel: '凭证类型',name: 'bussTag',width : 180,
			data: Lcbo_dataSource.getAllDs(Lcbo_dataSource.AmountLogbussTag_datas)});
		var layout_fields = [{cmns:'THREE',fields:[cbo_status,cbo_errCode,cbo_bussTag]}]
		var queryFrm = FormUtil.createLayoutFrm({region:'north',title:'查询条件'},layout_fields);
		this.globalMgr.queryFrm = queryFrm;
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
				self.globalMgr.queryFrm.reset();
			}
		},{type:"sp"},{
			token : '绑定凭证模板',
			text : Btn_Cfgs.BINDVTEMP_BTN_TXT,
			iconCls:Btn_Cfgs.INSERT_CLS,
			tooltip:Btn_Cfgs.BINDVTEMP_TIP_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({self:self,key:this.token});
			}
		},{
			token : '重新生成凭证',
			text : Btn_Cfgs.VOUCHEROPLOG_BTN_TXT,
			iconCls:Btn_Cfgs.INSERT_CLS,
			tooltip:Btn_Cfgs.VOUCHEROPLOG_TIP_TXT,
			handler : function(){
				self.globalMgr.rebuild(self);
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		this.globalMgr.tb = toolBar;
		return toolBar;
	},
	getAppGrid1 : function(){
		var _this = this;
			var structure_1 = [
			{
				 header: '凭证模板ID',
			    name: 'vtempId',
			    hidden : true
			},{
				 header: '实收金额日志ID',
			    name: 'amountLogId',
			     hidden : true
			},
			{
			    header: '状态',
			    name: 'status',
			    width: 75,
			    renderer: Render_dataSource.voustatus
			},
			{header: '凭证模板编号',name: 'code',width: 95},
			{header: '凭证模板',name: 'tempName',width: 120},
			{
			    header: '错误代码',
			    name: 'errCode',
			    width: 150,
		        renderer: Render_dataSource.vouerrCode
			},
			{
				 header: '凭证类型',
			    name: 'bussTag',
			    width: 100,
			    renderer : Render_dataSource.bussTagRender
			},
			{
			    header: '创建人',
			    name: 'empName',
			    width: 60
			},
			{
			    header: '创建日期',
			    name: 'opdate',
			    width: 125
			},
			{
			    header: '异常原因',
			    name: 'reason',
			    width: 150
			}
			];
			var isLoad = (this.hasTopSys) ? false : true;
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				title : "凭证日志表",
			    structure: structure_1,
			    url: './fsVoucherOplog_list.action',
			    needPage: true,
			    width : 650,
			    region: 'west',
			    keyField: 'id',
			    isLoad: isLoad
			});
			appgrid_1.on('rowclick',function(appgrid_1,rowIndex,e){
				_this.globalMgr.loadGrid2();
			});
		this.globalMgr.appgrid_1 = appgrid_1;
		return appgrid_1;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid2: function(){
		var self =this;
	    var structure = [
			{header : '业务标识',name : 'bussTag',hidden:true},
			{header : '银行',name : 'bankName',width:125},
			{header : '帐号',name : 'account',width:125},
			{header : '本金',name : 'amount',width:90,renderer : function(val){
				return Cmw.getThousandths(val);
			}},
			{header : '利息',name : 'ramount',width:90,renderer : function(val){
				return Cmw.getThousandths(val)+"元";
			}},
			{header : '管理费',name : 'mamount',width:90,renderer : function(val){
				return Cmw.getThousandths(val)+"元";
			}},
			{header : '罚息',name : 'pamount',width:90,renderer : function(val){
				return Cmw.getThousandths(val)+"元";
			}},
			{header : '滞纳金',name : 'oamount',width:90,renderer : function(val){
				return Cmw.getThousandths(val)+"元";
			}},
			{header : '手续费',name : 'famount',width:90,renderer : function(val){
				return Cmw.getThousandths(val)+"元";
			}},
			{header : '合计',name : 'sumamount',width:90,renderer : function(val){
				return Cmw.getThousandths(val)+"元";
			}},
			{header: '业务日期',name: 'opdate',width: 100}						
		];
		var appgrid_2 = new Ext.ux.grid.AppGrid({
			title : "凭证明细金额",
			region: 'center',
			 width : 550,
			structure : structure,
			url : './fsAmountLog_bussTaglist.action',
			keyField : 'id',
			needPage : true,
			isLoad: false
		});
		this.globalMgr.appgrid_2 = appgrid_2;
		return appgrid_2;
	},
	refresh:function(optionType,data){
		this.globalMgr.query(this);
		this.globalMgr.activeKey = null;
	},
	changeSize : function(whArr){
		var width = whArr[0];
		var height = whArr[1];
		width-=20;
		this.appPanel.setWidth(width);
		this.appPanel.setHeight(height);
		
		var appgrid_1 = this.globalMgr.appgrid_1;
		var grid1Width = 650;
		appgrid_1.setWidth(grid1Width);
		var grid2Width = width-grid1Width;
		if(!grid2Width || grid2Width < 0) grid2Width = 550;
		this.globalMgr.appgrid_2.setWidth(grid2Width);
		
		var gridHeight = 400;
		var tbHeight = 27;//工具栏高度
		if(this.globalMgr.tb.rendered){
			tbHeight = this.globalMgr.tb.getHeight();
		}
		var formHeight = 63;//表单高度
		if(this.globalMgr.queryFrm.rendered){
			formHeight = this.globalMgr.queryFrm.getHeight();
		}
		var gridHeight = height - tbHeight - formHeight;
		var centerPnl = appgrid_1.ownerCt;
		centerPnl.setWidth(width);
		centerPnl.setHeight(gridHeight);
		appgrid_1.setHeight(gridHeight);
		this.globalMgr.appgrid_2.setHeight(gridHeight);
	},
	/**
	 * 组件销毁方法
	 */
	destroyCmpts : function(){
	},
	/**
	 *  获取 Form 表单对象
	 */
	globalMgr : {
		/**
		 * 获取当前系统ID
		 * @param {} _this
		 * @return {}
		 */
		getSysId : function(_this){
			var sysId = null;
			if(!_this.hasTopSys){
				sysId = _this.params.sysid;
			}else{
				sysId = _this.globalMgr.sysId;
			}
			return sysId;
		},
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params = _this.globalMgr.queryFrm.getValues();
			if(!_this.hasTopSys){
				_this.globalMgr.sysId = _this.params.sysid;
				params["actionType"] = 1;/*表示是小额贷款中资金收付的菜单功能(要加用户权限过滤)*/
			}
			params.sysId = _this.globalMgr.sysId;
			EventManager.query(_this.globalMgr.appgrid_1,params);
		},
		rebuild : function(_this){
			var opLogIds = _this.globalMgr.appgrid_1.getSelId();
			var dataObj = _this.globalMgr.appgrid_1.getCmnVals("code,amountLogId");
			var codes = dataObj.code;
			var amountLogIds = dataObj.amountLogId;
			var sysId = _this.globalMgr.getSysId(_this);
			if(!codes){
				ExtUtil.alert({msg:"选中的凭证日志的“凭证模板编号”不存在，无法重新生成凭证！"});
				return ;
			}else{
				var appGrid1Id = _this.globalMgr.appgrid_1.getSelIds();
				var status = _this.globalMgr.appgrid_1.getCmnVals("status").status;
				if(status==0){
					ExtUtil.alert({msg:"选中的凭证日志的状态码为：“凭证生成成功”，不需要再重新生成凭证!"});
					return ;
				}
				if(!appGrid1Id){
					ExtUtil.alert({msg:"请选择凭证日志表中的数据！"});
					return ;
				}
				ExtUtil.confirm({msg:"确定重新生成凭证？",fn:function(btn){
					if(!btn || btn != 'yes') return;
					var msg = Ext.MessageBox.wait('正在生成财务凭证,请等待...', '提示');
					// msg = ExtUtil.progress({title : '请等待',msg:'正在生成财务凭证...'});
					EventManager.get('./fsVoucherOplog_rebuild.action',{params :{code:codes,opLogIds:opLogIds,amountLogIds:amountLogIds,sysId:sysId},sfn:function(data){
						msg.hide();
						ExtUtil.alert({msg:"操作成功!"});
						_this.globalMgr.query(_this);
					},ffn:function(){
						msg.hide();
						ExtUtil.alert({msg:"操作失败!"});
					}});
				}});
			}
		},
		/**
		 * 加载凭证明细金额数据
		 */
		loadGrid2 : function(){
			var data = this.appgrid_1.getCmnVals("amountLogId,bussTag");
			var amountLogId = data.amountLogId;
			var bussTag = data.bussTag;
			var showCmns = null;
			var hidCmns = null;
			var headerCfgs = null;
			switch(parseInt(bussTag)){
				case 0 : {/*放款*/
					showCmns = "amount";
					hidCmns = "ramount,mamount,pamount,oamount,famount";
					headerCfgs = {amount:'放款金额'};
					break;
				}case 1 : {/*放款手续费*/
					showCmns = "famount";
					hidCmns = "amount,ramount,mamount,pamount,oamount";
					headerCfgs = {famount:'放款手续费'};
					break;
				}case 2 : 
				 case 4 : {/*2:正常收款,4:预收结清*/
					showCmns = "amount,ramount,mamount";
					hidCmns = "pamount,oamount,famount";
					break;
				}case 3 :/*正常收款豁免*/
				 case 8 :/*逾期豁免*/
				 case 10 :{/*提前还款豁免*/
					showCmns = "ramount,mamount,pamount,oamount,famount";
					hidCmns = "amount";
					headerCfgs = {famount:'手续费'};
					break;
				}case 6 :/*表内愈期收款*/
				 case 7 :{/*表外愈期收款*/
					showCmns = "amount,ramount,mamount,pamount,oamount";
					hidCmns = "famount";
					headerCfgs = {amount:'本金'};
					break;
				}case 9 :{/*提前还款*/
					showCmns = "amount,ramount,mamount,pamount,oamount,famount";
					headerCfgs = {amount:'本金',famount:'提前还款手续费'};
					break;
				}default : {
					showCmns = "amount,ramount,mamount,pamount,oamount,famount";
					headerCfgs = {amount:'本金',famount:'手续费'};
				}
			}
			if(showCmns) this.appgrid_2.setHeadersVisible(false,showCmns);
			if(headerCfgs) this.appgrid_2.setHeaders(headerCfgs);
			if(hidCmns) this.appgrid_2.setHeadersVisible(true,hidCmns);
			this.appgrid_2.reload({amountLogId:amountLogId});
		},
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		appgrid_2 : null,
		appgrid_1 : null,
		queryFrm : null,
		tb : null,
		sysId : null,
		activeKey : null,
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				var winkey=parentCfg.key;
				var parent = _this.globalMgr.appgrid_1;
				var vhopLogId = parent.getSelId();
				if(!vhopLogId) return;
				var data = parent.getCmnVals("code");
				if(data.code){
					ExtUtil.confirm({msg:'选中的凭证日志记录已包含了凭证模板，确定需要再重新绑定凭证模板?',fn:function(){
						 if(arguments && arguments[0] != 'yes') return;
						 openDialog();
					}});
					return;
				}else{
					openDialog();
				}
				
				function openDialog(){
					parentCfg.parent = parent;
					parentCfg.callback = function(id,record){
						var _pars = {id:vhopLogId,vtempId:id};
						 EventManager.get('./fsVoucherOplog_update.action',{params:_pars,sfn:function(json_data){
						 	_this.globalMgr.query(_this);
						 	ExtUtil.alert({msg:'凭证模板绑定成功!'});
						 }});
					}
					if(_this.appCmpts[winkey]){
						_this.appCmpts[winkey].show(parentCfg);
					}else{
						Cmw.importPackage('pages/app/dialogbox/VoucherTempDialogbox',function(module) {
						 	_this.appCmpts[winkey] = module.DialogBox;
						 	_this.appCmpts[winkey].show(parentCfg);
				  		});
					}
				}
			}
		}
	}
});