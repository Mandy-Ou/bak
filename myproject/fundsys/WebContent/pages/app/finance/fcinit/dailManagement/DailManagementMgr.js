 
Ext.namespace("cmw.skythink");
/**
 * 日常收支管理
 * @author liting
 */ 
cmw.skythink.PayTypeMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.PayTypeMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGContainerView({
			tab : tab,
			params : params,
			hasTopSys : false,
			getQueryFrm : this.getQueryFrm,
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			globalMgr : this.globalMgr,
			refresh : this.refresh
		});
	},
	/**
	 * 获取查询Form 表单
	 */
	getQueryFrm : function(){
			var self =this;
			var callback = function(cboGrid,selVals){
					var grid = cboGrid.grid;
					var record = grid.getSelRow();
		}
		
		var cbo_inType = FormUtil.getRCboField({
						fieldLabel : '费用类型',
						name : 'name',
						"width" : 125,
						"allowBlank" : true,
						register : REGISTER.GvlistDatas,
						restypeId : '200006'
						,"url" : "./sysGvlist_cbodatas.action?restypeId=200006"
					});
		
		
			var cbog_accountId = ComboxControl.getAccountCboGrid({
				fieldLabel:'银行',name:'bankName',
				allowBlank : false,readOnly:true,
				isAll:false,width:160,callback:callback,
				params : {isIncome:1,sysId : _this.sysId}});
			var rad_isBackAmount = FormUtil.getRadioGroup({fieldLabel : '收支类型', name:'waterType', items : [
				{boxLabel : '全部', name:'waterType',inputValue:null},
				{boxLabel : '收入', name:'waterType',inputValue:2},
				{boxLabel : '支出', name:'waterType',inputValue:1}
				]});
			var txt_startDate1 = FormUtil.getDateField({name:'startDate',width:90});
			var txt_endDate1 = FormUtil.getDateField({name:'endDate',width:90});
			var comp_appDate = FormUtil.getMyCompositeField({
				itemNames : 'startDate,endDate',
				sigins : null,
				 fieldLabel: '日期',width:210,sigins:null,
				 name:'comp_estartDate',
				 items : [txt_startDate1,{xtype:'displayfield',value:'至'},txt_endDate1]
				});
				var cbo_isenabled = FormUtil.getLCboField({fieldLabel : '费用类型',name:'bussTag',data: Lcbo_dataSource.getAllDs(Lcbo_dataSource.isenabled_datas)});
//				var txt_code = FormUtil.getLCboField({fieldLabel : '银行',name:'isenabled',data: Lcbo_dataSource.getAllDs(Lcbo_dataSource.isenabled_datas)});
				var layout_fields = [{cmns:'THREE',fields:[rad_isBackAmount,cbo_inType/*,cbo_isenabled*//*,txt_code*/,cbog_accountId,comp_appDate]}]
				var queryFrm = FormUtil.createLayoutFrm(null,layout_fields);
				return queryFrm;
	},
	/**
	 * 查询工具栏
	 */
	
	getToolBar : function(){
		var self = this;
		var barItems = [{
			token : '查询',
			text : Btn_Cfgs.QUERY_BTN_TXT,
			iconCls:'page_query',
			tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.query(self);
			}
		},
			{type:"sp"},{
			token : '重置',
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls:'page_reset',
			tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
			handler : function(){
				self.queryFrm.reset();
			}
		},{
			token : '收入',
			text : Btn_Cfgs.Income_BTN_TXT,
			iconCls:Btn_Cfgs.Income_CLS,
			tooltip:Btn_Cfgs.Income_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"收入",optionType:OPTION_TYPE.ADD,self:self});
			}
		}
		,{
			token : '支出',
			text : Btn_Cfgs.Expenses_BTN_TXT,
			iconCls:Btn_Cfgs.Expenses_CLS,
			tooltip:Btn_Cfgs.Expenses_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"支出",optionType:OPTION_TYPE.Add,self:self});
			}
		},{type:"sp"},{
			token : '编辑',
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:Btn_Cfgs.MODIFY_CLS,
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"编辑",optionType:OPTION_TYPE.EDIT,self:self});
			}
		}
		,{
			token : '删除',
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./fcFundsWater_deleteDatile.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
		var self = this;
		var structure_1 = [{
		    header: '收支类型',
		    name: 'waterType',
		    width : 60,
		    renderer : function(val){
		    	return Render_dataSource.DailRender(val);
		    }
		}
		,
		{
		    header: '费用类型',
		    name: 'name',
		    width: 100
		},
		
		{
		    header: '金额',
		    name: 'amounts',
		    width : 100,
		    renderer : Render_dataSource.moneyRender
		},
		{
		    header: '银行',
		    name: 'bankName',
		    width: 200
		},
		{
		    header: '银行帐号',
		    name: 'account',
		    width: 200
		},
		{
		    header: '日期',
		    name: 'opdate',
		    width: 200
		}
		,
		{
		    header: '备注',
		    name: 'remark',
		    width: 200
		}, {
			header : '创建时间',
			name : 'createTime',
			width : 150,
			renderer : function(val){
				val = Ext.util.Format.date(val,'Y-m-d H:i:s');
				return val;
			}
		}];
		var appgrid_1 = new Ext.ux.grid.AppGrid({
		    title: '日常收支情况列表',
		    tbar : this.toolBar,
		    structure: structure_1,
		    url: './fcFundsWater_listDailmanage.action',
		    needPage: true,
		    isLoad: true,
		    keyField: 'id'
//		    ,
//		    listeners : {
//			   	render : function(grid){
//			   	 _this.globalMgr.query(_this);
//			   	}
//		   }

		});
		return appgrid_1;
	},
	refresh:function(optionType,data){
		this.globalMgr.query(this);
		this.globalMgr.activeKey = null;
	},
	
	globalMgr : {
		sysId:this.params.sysid,
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		activeKey : null,
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params = _this.queryFrm.getValues();
			if(params) {
				EventManager.query(_this.appgrid,params);
			}
		},
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				var sysId = _this.globalMgr.sysId;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent = _this.appgrid;
				if(winkey == "编辑"){
					var id = parent.getSelId();
					if(!id) return;
				}
				parentCfg.parent = parent;
				parent.sysId=sysId;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule=null;
					if(winkey == "支出"){
						winModule="DailManagemenEdit";
					}if(winkey == "收入"){winModule="DailManagemenIncomeEdit";}
					if(winkey == "编辑"){winModule="DailManagemenEditorsEdit";}
					Cmw.importPackage('pages/app/finance/fcinit/dailManagement/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		}
	}
});
