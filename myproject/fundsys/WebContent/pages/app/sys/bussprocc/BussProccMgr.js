/**
 * 同心日信息科技有限责任公司产品 
 *  联系电话：18064179050 程明卫[系统设计师]
 *  联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * @description 子业务流程管理 UI 
 * @date 2013-08-26 18:13
 */ 
cmw.skythink.BussProccMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.BussProccMgr,Ext.util.MyObservable,{
	queryFields : null,
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGContainerView({
			tab:tab,
			params:params,
			getQueryFrm : this.getQueryFrm,
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			globalMgr : this.globalMgr,
			notify : this.notify,
			hasTopSys : true,
			refresh : this.refresh
		});
	},
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var _this = this;
		var tokenMgr = _this.globalMgr.tokenMgr;
		var barItems = [{token:tokenMgr.QUERY_TXT,text:Btn_Cfgs.QUERY_BTN_TXT,iconCls:Btn_Cfgs.QUERY_CLS,handler:function(){
						_this.globalMgr.query(_this);
					}},
					{token:tokenMgr.RESET_TXT,text:Btn_Cfgs.RESET_BTN_TXT,iconCls:Btn_Cfgs.RESET_CLS,handler:function(){
						_this.queryFrm.reset();
					}},
					{type:'sp'},/*添加子流程*/
					{token :tokenMgr.INSERT_BUSSPROCC_TXT,text:Btn_Cfgs.INSERT_BUSSPROCC_BTN_TXT,iconCls:Btn_Cfgs.INSERT_BUSSPROCC_CLS,handler:function(){
						var sysid = _this.globalMgr.sysId;
						_this.globalMgr.winEdit.show({key:this.token,self:_this,sysid:sysid});
					}},/*编辑子流程*/
					{token:tokenMgr.EDIT_BUSSPROCC_TXT,text:Btn_Cfgs.EDIT_BUSSPROCC_BTN_TXT,iconCls:Btn_Cfgs.EDIT_BUSSPROCC_CLS,handler:function(){
						_this.globalMgr.winEdit.show({key:this.token,optionType:OPTION_TYPE.EDIT,self:_this});
					}},/*复制子流程*/
					/*
					{token:'复制子流程',text:Btn_Cfgs.COPY_BUSSPROCC_BTN_TXT,iconCls:Btn_Cfgs.COPY_BUSSPROCC_CLS,handler:function(){
						self.globalMgr.winEdit.show({key:Btn_Cfgs.COPY_VARIETY_BTN_TXT,optionType:OPTION_TYPE.DETAIL,self:self});
					}},*/
					{type:'sp'},/*查看流程图*/	
					{token:tokenMgr.VIEW_PROCESS_TXT,text:Btn_Cfgs.VIEW_PROCESS_BTN_TXT,iconCls:Btn_Cfgs.VIEW_PROCESS_CLS,handler:function(){
						_this.globalMgr.winEdit.show({key:this.token,optionType:OPTION_TYPE.EDIT,self:_this});
					}},/*配置流程*/ 
					{token:tokenMgr.CFG_PROCESS_TXT,text:Btn_Cfgs.CFG_PROCESS_BTN_TXT,iconCls:Btn_Cfgs.CFG_PROCESS_CLS,handler:function(){
						_this.globalMgr.winEdit.show({key:this.token,optionType:OPTION_TYPE.ADD,self:_this});
					}},
					{type:'sp'},
					{token:tokenMgr.ENABLED_TXT,text:Btn_Cfgs.ENABLED_BTN_TXT,iconCls:Btn_Cfgs.ENABLED_CLS,handler:function(){
						EventManager.enabledData('./sysBussProcc_enabled.action',{type:'grid',cmpt:_this.appgrid,optionType:OPTION_TYPE.ENABLED,self:_this});
					}},
					{token:tokenMgr.DISABLED_TXT,text:Btn_Cfgs.DISABLED_BTN_TXT,iconCls:Btn_Cfgs.DISABLED_CLS,handler:function(){
						EventManager.disabledData('./sysBussProcc_disabled.action',{type:'grid',cmpt:_this.appgrid,optionType:OPTION_TYPE.DISABLED,self:_this});
					}},
					{token:tokenMgr.DELETE_TXT,text:Btn_Cfgs.DELETE_BTN_TXT,iconCls:Btn_Cfgs.DELETE_CLS,handler:function(){
						EventManager.deleteData('./sysBussProcc_delete.action',{type:'grid',cmpt:_this.appgrid,optionType:OPTION_TYPE.DEL,self:_this});
					}},
					{type:'sp'},
					/*编辑条款*/
					{token:tokenMgr.EDIT_CLAUSE_TXT,text:Btn_Cfgs.EDIT_CLAUSE_BTN_TXT,iconCls:Btn_Cfgs.EDIT_CLAUSE_CLS,handler:function(){
						_this.globalMgr.winEdit.show({key:this.token,optionType:OPTION_TYPE.ADD,self:_this});
					}},/*删除条款*/
					{token:tokenMgr.DELETE_CLAUSE_TXT,text:Btn_Cfgs.DELETE_CLAUSE_BTN_TXT,iconCls:Btn_Cfgs.DELETE_CLAUSE_CLS,handler:function(){
						EventManager.deleteData('./sysBussProcc_delClause.action',{type:'grid',cmpt:_this.appgrid,optionType:OPTION_TYPE.DEL,self:_this});
					}}
					];
		var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 * 获取查询Form 表单
	 */
	getQueryFrm : function(){
		var hid_sysid = FormUtil.getHidField({fieldLabel : 'sysid',name:'sysId'});
 		var txt_name = FormUtil.getTxtField({fieldLabel : '子流程名称',name:'name'});
 		var rad_bussType = FormUtil.getRadioGroup({fieldLabel : '业务性质',name:'bussType',width:200,
							items : [{boxLabel : '无限制', name:'bussType',inputValue:0,width:90},
								{boxLabel : '个人客户', name:'bussType',inputValue:1,width:90},
							    {boxLabel : '企业客户', name:'bussType',inputValue:2,width:90}]});
				
 		var txt_menuName = FormUtil.getTxtField({fieldLabel : '关联功能',name:'menuName'});
							    
		 var layout_fields = [hid_sysid,{cmns:FormUtil.CMN_THREE,fields:[txt_name,rad_bussType,txt_menuName]}]

		var queryFrm = FormUtil.createLayoutFrm(null,layout_fields);
		return queryFrm;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
		
		var structure_1 = [	{
			    header: '可用标识',
			    name: 'isenabled',
			    width: 60,
			    renderer: Render_dataSource.isenabledRender
			},{
			    header: '子流程编号',
			    name: 'code'
			},
			{
			    header: '子流程名称',
			    name: 'name',
			    width: 115
			},
			{
			    header: '是否配置流程',
			    name: 'pdid',
			    width: 90,
			    renderer: function(val) {
			        return !val ? '否' : '是';
			    }
			},
			{
			    header: '业务性质',
			    name: 'bussType',
			    width: 80,
			    renderer: function(val) {
			        switch (val) {
			        case '0':
			            val = '无限制';
			            break;
			        case '1':
			            val = '个人客户';
			            break;
			        case '2':
			            val = '企业客户';
			            break;
			        }
			        return val;
			    }
			},
			{
			    header: '适用机构',
			    name: 'useorg',
			    width: 80,
			  	renderer: function(val) {
			        switch (val) {
			        case '0':
			            val = '所有公司';
			            break;
			        case '1':
			            val = '指定公司';
			            break;
			        }
			        return val;
				}
			},
			{
			    header: '指定公司名称',
			    name: 'companyNames',
			    width: 125
			},
			{
			    header: '关联功能',
			    name: 'menuId'
			},
			{
			    header: '关联表单地址',
			    name: 'formUrl',
			    width: 150
			},
			{
			    header: '添加条款说明',
			    name: 'txtPath',
			    width: 100,
			    renderer: function(val) {
			        return val ? "已添加条款" : "未添加条款";
			    }
			},{
			    header: '流程定义ID',
			    name: 'pdid',
			    hidden: true
			}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    title: '子业务流程列表',
			    tbar : this.toolBar,
			    structure: structure_1,
			    url: './sysBussProcc_list.action',
			    needPage: false,
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
		this.globalMgr.query(this);
		this.globalMgr.activeKey = null;
	},
	/**
	 * 当点击顶部的 系统图标 时，会触发 notify 方法。
	 * @param {} data 选中的 系统图标数据。
	 * 			{id : '',name:'',code:'',url:''} 等。
	 * 			具体值请看 SystemEntity 的 getFields() 方法
	 */
	notify : function(data){
		/*--> 当点击子系统图标时，会触发此事件 <--*/
		var self = this;
		var sysId = data.id;
		this.globalMgr.sysId=sysId;
		//this.appgrid.reload({sysId:sysId});
		this.globalMgr.query(this);
		this.globalMgr.activeKey = null;
	},
	globalMgr : {
		sysId : null,	/*系统ID*/
		/**
		 * 按钮唯一标识码
		 * @type 
		 */
		tokenMgr : {
			QUERY_TXT : '查询',
			RESET_TXT : '重置',
			INSERT_BUSSPROCC_TXT : '添加子流程',
			EDIT_BUSSPROCC_TXT : '编辑子流程',
			VIEW_PROCESS_TXT : '查看流程图',
			CFG_PROCESS_TXT : '配置流程',
			ENABLED_TXT : '启用',
			DISABLED_TXT : '禁用',
			DELETE_TXT : '删除',
			EDIT_CLAUSE_TXT : '编辑条款',
			DELETE_CLAUSE_TXT : '删除条款'
		},
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		activeKey : null,
		sysId : null,
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				var tokenMgr = _this.globalMgr.tokenMgr;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent = _this.appgrid;
				var selId = null;
				if(winkey != tokenMgr.INSERT_BUSSPROCC_TXT){
					selId = parent.getSelId();
					if(!selId) return;
				}
				
				parentCfg.parent = parent;
				parentCfg.sysId = _this.globalMgr.sysId; /* 业务品种类型标识 */
				parentCfg.bussType = 2;/*子业务流程*/
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule=null;
					switch(winkey){
						case tokenMgr.INSERT_BUSSPROCC_TXT :
						case tokenMgr.EDIT_BUSSPROCC_TXT :{
							winModule="pages/app/sys/bussprocc/BussProccEdit";
							break;
						}case tokenMgr.VIEW_PROCESS_TXT :/*查看流程图*/
						 case tokenMgr.CFG_PROCESS_TXT :{/*配置流程*/
							winModule="pages/app/sys/variety/BussProccEdit";
							break;
						}case tokenMgr.EDIT_CLAUSE_TXT :{/*编辑条款*/
							winModule="pages/app/sys/bussprocc/ClauseEdit";
							break;
						}
					}
					Cmw.importPackage(winModule,function(module) {
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
			var params = _this.queryFrm.getValues();
			if(params.bussType ==""){
				params.bussType=0;
			}
			params.sysId=_this.globalMgr.sysId;
			if(params) {
				EventManager.query(_this.appgrid,params);
			}
		}
	}
});

