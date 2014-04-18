Ext.namespace("cmw.skythink_zy");
/**
 * @description 融资管理 UI 
 */ 
cmw.skythink_zy.OutFundsMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink_zy.OutFundsMgr,Ext.util.MyObservable,{
	queryFields : null,
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsPanelView({
			tab : tab,
			params : params,
			getAppCmpt : this.getAppCmpt,
			//getQueryFrm : this.getQueryFrm,
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			getAppGrid2 : this.getAppGrid2,
			getCenterPnl: this.getCenterPnl,
			destroyCmpts :this.destroyCmpts,
			changeSize : this.changeSize, 
			globalMgr : this.globalMgr,
			refresh : this.refresh
		});
	},
	/**
	 * 组件销毁方法
	 */
	destroyCmpts : function(){
		if(this.appPanel != null){
			this.appPanel.destroy();
			this.appPanel = null;
		}
	},
	/**
	 * 主面板
	 * @return {}
	 */
	getCenterPnl:function(){
		var gm=this.globalMgr;
		gm.grid1=this.getAppGrid();
		gm.grid2=this.getAppGrid2();
		var pnl=new Ext.Panel({
			layout:'border',height : screen.height-245,border : false,	
			items:[gm.grid1,gm.grid2]
		})
		return pnl;
	},
	getAppCmpt:function(){
		var _this = this;
		//this.globalMgr.frm=this.getQueryFrm();
		var panelMain = new Ext.Panel({
			border : false,
			items:[/*_this.globalMgr.frm,*/_this.getToolBar(),_this.getCenterPnl()]
		});
		return panelMain;
	},
	changeSize : function(whArr){
		var width = whArr[0]-2;
		var height = whArr[1]-3;
		this.appPanel.setWidth(width);
		this.appPanel.setHeight(height);
	},
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var _this = this;
		var tokenMgr = _this.globalMgr.tokenMgr;
		var barItems = [{type : 'label',text : '机构名称'},
						{
						type : 'search',
						name : 'name',
						key : Btn_Cfgs.QUERY_FASTKEY,
						onTrigger2Click : function() {
							var params = toolBar.getValues();
							var name = params.name;
							// 根据输入的值进行查找，如果为空，查询全部
							if (name == "") {
								EventManager.query(_this.globalMgr.grid1, null);
							} else {
								EventManager.query(_this.globalMgr.grid1, {name : name});
							}
						}
					},
					{token:tokenMgr.QUERY_TXT,text:Btn_Cfgs.QUERY_BTN_TXT,iconCls:Btn_Cfgs.QUERY_CLS,handler:function(){
						var params = toolBar.getValues();
							var name = params.name;
							// 根据输入的值进行查找，如果为空，查询全部
							if (name == "") {
								EventManager.query(_this.globalMgr.grid1, null);
							} else {
								EventManager.query(_this.globalMgr.grid1, {name : name});
							}
						}
					},
					{token:tokenMgr.INSERT_BTN_TXT,text:Btn_Cfgs.INSERT_BTN_TXT,iconCls:Btn_Cfgs.INSERT_CLS,handler:function(){
						_this.globalMgr.addFinancial({key:this.token,optionType:OPTION_TYPE.ADD,self:_this});
					}},
					{token:tokenMgr.EDIT_TXT,text:"编辑",iconCls:Btn_Cfgs.MODIFY_CLS,handler:function(){
						_this.globalMgr.editFinancial({key:this.token,optionType:OPTION_TYPE.EDIT,self:_this});
					}},
					{token:'追加资金',text:"追加资金",iconCls:Btn_Cfgs.INSERT_CLS,handler:function(){
						_this.globalMgr.addAmount({key:this.token,optionType:OPTION_TYPE.EDIT,self:_this});
					}},
					//{type:'sp'},/*编辑*/
					/*{token:tokenMgr.EDIT_TXT,text:Btn_Cfgs.MODIFY_BTN_TXT,iconCls:Btn_Cfgs.MODIFY_CLS,handler:function(){
						_this.globalMgr.doApplyByOp(_this);
					}},*//*,详情
					{token:tokenMgr.DETAIL_TXT,text:Btn_Cfgs.DETAIL_BTN_TXT,iconCls:Btn_Cfgs.DETAIL_CLS,handler:function(){
						_this.globalMgr.winEdit.show({key:this.token,optionType:OPTION_TYPE.DETAIL,self:_this});
					}},提交 
					{token:tokenMgr.SUBMIT_TXT,text:Btn_Cfgs.SUBMIT_BTN_TXT,iconCls:Btn_Cfgs.SUBMIT_CLS,handler:function(){
						_this.globalMgr.submitApplyForm(_this);
					}},*/
					{token:tokenMgr.DELETE_TXT,text:Btn_Cfgs.DELETE_BTN_TXT,iconCls:Btn_Cfgs.DELETE_CLS,handler:function(){
						EventManager.deleteData('./fcOutFunds_delete.action',{type:'grid',cmpt:_this.globalMgr.grid1,optionType:OPTION_TYPE.DEL,self:_this});
					}},
					{type:'sp'},
					{type : 'label',text : '付息日期'},
					{type : 'datefield'}];
		var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : false,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 * 获取查询Form 表单
	 */
	getQueryFrm : function(){
		var width = 200;
 		var txt_code = FormUtil.getTxtField({fieldLabel : '编号',name:'code',width:width});
		var txt_overMan = FormUtil.getTxtField({fieldLabel : '申请人',name:'overMan',width:width});
//		var txt_address = FormUtil.getTxtField({fieldLabel : '加班地点',name:'address',width:width});
 		var startDate = FormUtil.getDateField({fieldLabel : '申请时间',name:'startDate',width:90});
		var endDate = FormUtil.getDateField({fieldLabel : '申请时间',name:'endDate',width:90});
		var cmpt_loanDate = FormUtil.getMyCompositeField({fieldLabel:'申请时间',name:'cmpt_loanDate',width:width,
			itemNames : 'startDate,endDate',sigins:null,items:[startDate,{xtype:'displayfield',value:'至'},endDate]});
//		var txt_ohours = FormUtil.getIntegerField({fieldLabel : '加班时长',name:'ohours',width:180,unitText:"小时"});
		var txt_bussTag = FormUtil.getHidField({fieldLabel : '业务类型',name:'bussTag',width:width,value:2});
				
		var layout_fields = [{cmns:FormUtil.CMN_THREE,fields:[txt_code,txt_overMan,cmpt_loanDate,txt_bussTag]}]

		var queryFrm = FormUtil.createLayoutFrm(null,layout_fields);
		return queryFrm;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
		var _this = this;
		var structure_1 = [{
			    header: 'id',
			    name: 'id',
			    hidden:true,
			    width: 150
			},{
			    header: '编号',
			    name: 'code',
			    width: 70
			},
			{
			    header: '机构名称',
			    name: 'name',
			    width: 80
			},
			{
			    header: '机构类型',
			    name: 'orgType',
			    width:80,
			    renderer : function(val){
			    	switch(val){
			    		case "1":
			    			val = "银行";
			    			break;
			    		case "2":
			    			val = "集合信托";
			    			break;
			    	}
			    	return val;
			    }
			},
			{
			    header: '贷款利率',
			    name: 'rate',
			    width: 75,
			    renderer: function(val,metadata ,record) {
			    	var unint;
			    	switch(record.get("unint")){
			    		case "1":
			    			unint = "(%)";
			    			break;
			    		case "2":
			    			unint = "(‰)";
			    			break;
			    	}
			        switch (val) {
				        case 0.0000:
				            val = '0.0000';
				            break;
				        default:
				        	val += unint;
			        }
			        return val;
			    }
			},
			{
				header: '贷款利率单位',
			    name: 'unint',
			    width: 75,
			    hidden : true,
			    renderer: function(val) {
			        switch (val) {
				        case "1":
				            val = '%';
				            break;
				         case "2":
				         	val ="‰";
				         	break;
			        }
			        return val;
			    }
			},
			{
			    header: '担保费率',
			    name: 'xrate',
			    width: 80,
			    renderer: function(val,metadata ,record) {
			    	var unint;
			    	switch(record.get("xunint")){
			    		case "1":
			    			unint = "(%)";
			    			break;
			    		case "2":
			    			unint = "(‰)";
			    			break;
			    	}
			        switch (val) {
				        case 0.0000:
				            val = '0.0000';
				            break;
				        default:
				        	val += unint;
			        }
			        return val;
			    }
			},
			{
				header: '担保费率单位',
			    name: 'xunint',
			    width: 75,
			    hidden : true,
			    renderer: function(val) {
			        switch (val) {
				        case "1":
				            val = '%';
				            break;
				         case "2":
				         	val ="‰";
				         	break;
			        }
			        return val;
			    }
			},
			{
			    header: '贷款金额',
			    name: 'totalAmount',
			    width: 85,
			    renderer: function(val) {
			        switch (val) {
			        case 0.00:
			            val = '0.00';
			            break;
			
			        }
			        return val;
			    }
			},
			{
			    header: '累计贷出金额',
			    name: 'bamount',
			    width: 80,
			    renderer: function(val) {
			        switch (val) {
			        case 0.00:
			            val = '0.00';
			            break;
			
			        }
			        return val;
			    }
			},
			{
			    header: '剩余可用金额',
			    name: 'uamount',
			    width: 85,
			    renderer: function(val) {
			        switch (val) {
			        case 0.00:
			            val = '0.00';
			            break;
			
			        }
			        return val;
			    }
			},
			{
			    header: '账户ID',
			    name: 'accountId',
			    width: 80,
			    hidden :true
			},
			{
			    header: '每月付息日',
			    name: 'payDay',
			    width: 60
			},
			{
			    header: '贷款截止日期',
			    name: 'endDate',
			    width: 125
			},
			{
			    header: '贷款起始日期',
			    name: 'startDate',
			    width: 125
			},
			{
			    header: '最后存入时间',
			    name: 'lastDate',
			    width: 125
			}];
		var appgrid_1 = new Ext.ux.grid.AppGrid({
		    title: '金融机构列表',
		    structure: structure_1,
		    region: 'west',
		    url: './fcOutFunds_list.action',
		  //  needPage: true,
		    isLoad : false,
		    width :650,
		    //height:screen.height-600,
		    keyField: 'id',
		    listeners:{
		    	render : function(grid){_this.globalMgr.query(_this);}
		    }
		});
		appgrid_1.addListener("rowclick",function(appgrid,rowIndex,event){
		   var record = appgrid.getStore().getAt(rowIndex);
		   if(record==null){
		   		return;
		   }
		   var id = appgrid.getSelId();
		   _this.globalMgr.grid1 = appgrid_1;
			_this.globalMgr.grid2.reload({applyId:id});
		});
		
		return appgrid_1;
	},
	getAppGrid2:function(){
		var structure_1 = [{
		    header: '付息日期',
		    name: 'xpayDate'
		},{
		    header: '利息金额',
		    renderer:Render_dataSource.overtime_type,
		    name: 'iamount'
		},{
		    header: '担保费金额',
		    renderer:Render_dataSource.dataRd,
		    name: 'samount'
		},
		{
		    header: '已付息金额',
		    name: 'yamount'
		},
		{
		    header: '已付担保费金额',
		    width:120,
		    name: 'ysamount'
		},
		{
			
		    header: '截止时间',
		    width:120,
		    name: 'endDate'
		}];
		var appgrid_1 = new Ext.ux.grid.AppGrid({
		    title: '息费流水',
		    structure: structure_1,
		   // url: './oa_list.action',
		    region: 'center',
		    needPage: false,
		    width:200,
		   // height:screen.height-600,
		    isLoad: false,
		    keyField: 'id'
		});
		return appgrid_1;
	},
	/**
	 * 当新增、修改、删除、起用、禁用 
	 * 数据保存成功后会执行此方法刷新父页面
	 * @param optionType 操作类型 参考 constant.js 文件中的 "OPTION_TYPE" 常量值
	 * 	
	 * @param {} data 
	 * 	1).如果是 新增、修改表单数据保存成功的话. data 参数代表的是表单数据json对象
	 *  2).如果是 删除、起用、禁用 数据处理成功的话.data 参数是 ids 值。例如:{ids:'1001,1002,1003'}
	 */
	refresh:function(optionType,data){
		if(optionType && optionType == OPTION_TYPE.DEL){/*删除完后减少暂存提醒数量*/
			
		}
		this.globalMgr.query(this);
		this.globalMgr.activeKey = null;
	},
	globalMgr : {
		sysId : null,	/*系统ID*/
		grid1:null,
		grid2:null,
		toolbar:null,
		frm:null,
		/**
		 * 按钮唯一标识码
		 * @type 
		 */
		tokenMgr : {
			QUERY_TXT : '查询',
			INSERT_BTN_TXT : '添加',
			EDIT_TXT : '编辑',
			DETAIL_TXT : '查看详情',
			SUBMIT_TXT : '提交',
			DELETE_TXT : '删除'
		},
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		activeKey : null,
		sysId : null,
		/**
		 * 添加/编辑融资机构	
		 * @param {} _this
		 */
		addFinancial : function(parentCfg){
				var winkey = parentCfg.key;
				var _this =  parentCfg.self;
				var parent = _this.globalMgr.grid1;
				//var selId  = parent.getSelId();
				//if(!selId) return;
				parentCfg.parent = parent;
				//var sysId = _this.params.sysid;
				//parentCfg.params = {
				//	sysId : sysId,
				//	id : selId
				//}
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					Cmw.importPackage('pages/app/workflow/bussProcc/formUIs/financing/AddFinancialInstitutions',function(module) {
				 	_this.appCmpts[winkey] = module.WinEdit;
				 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
		},
		editFinancial :function(parentCfg){
				var winkey = parentCfg.key;
				var _this =  parentCfg.self;
				var parent = _this.globalMgr.grid1;
				var selId  = parent.getSelId();
				if(!selId) return;
				parentCfg.parent = parent;
				var sysId = _this.params.sysid;
				parentCfg.params = {
					sysId : sysId,
					id : selId
				}
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					Cmw.importPackage('pages/app/workflow/bussProcc/formUIs/financing/FinanceEdit',function(module) {
				 	_this.appCmpts[winkey] = module.WinEdit;
				 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
		},
		addAmount : function(parentCfg){
			var winkey = parentCfg.key;
				var _this =  parentCfg.self;
				var parent = _this.globalMgr.grid1;
				var selId  = parent.getSelId();
				if(!selId) return;
				parentCfg.parent = parent;
				var sysId = _this.params.sysid;
				parentCfg.params = {
					sysId : sysId,
					id : selId
				}
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					Cmw.importPackage('pages/app/workflow/bussProcc/formUIs/financing/Additionalfunds',function(module) {
				 	_this.appCmpts[winkey] = module.WinEdit;
				 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
		},
		winEdit : {
			show : function(parentCfg){
				var winkey = parentCfg.key;
				var _this =  parentCfg.self;
				var parent = _this.globalMgr.grid1;
				var selId  = parent.getSelId();
				//if(!selId) return;
				parentCfg.parent = parent;
				var sysId = _this.params.sysid;
				parentCfg.params = {
					sysId : sysId,
					id : selId
				}
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
						Cmw.importPackage('pages/app/workflow/bussProcc/oa/formUIs/detail/OvertimeApplyStoreDetail',function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		},
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params //= _this.globalMgr.frm.getValues();
			if(!params) params = {};
			EventManager.query(_this.globalMgr.grid1,params);
		}
	}
});

