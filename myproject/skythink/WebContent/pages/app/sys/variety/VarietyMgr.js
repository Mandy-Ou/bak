/**
 * 同心日信息科技责任有限公司产品 
 *  联系电话：18064179050 程明卫[系统设计师]
 *  联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * @description 业务品种管理 UI 
 * @date 2012-11-28
 */ 
cmw.skythink.VarietyMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.VarietyMgr,Ext.util.MyObservable,{
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
		var self = this;
		var barItems = [{token:'查询',text:Btn_Cfgs.QUERY_BTN_TXT,iconCls:Btn_Cfgs.QUERY_CLS,handler:function(){
							self.globalMgr.query(self);
						}},
						{token:'重置',text:Btn_Cfgs.RESET_BTN_TXT,iconCls:Btn_Cfgs.RESET_CLS,handler:function(){
							self.queryFrm.reset();
						}},
						{type:'sp'},/*添加品种*/
						{token :'添加品种',text:Btn_Cfgs.INSERT_VARIETY_BTN_TXT,iconCls:Btn_Cfgs.INSERT_VARIETY_CLS,handler:function(){
							var sysid = self.globalMgr.sysId;
							self.globalMgr.winEdit.show({key:Btn_Cfgs.INSERT_VARIETY_BTN_TXT,self:self,sysid:sysid});
						}},/*编辑品种*/
						{token:'复制品种',text:Btn_Cfgs.EDIT_VARIETY_BTN_TXT,iconCls:Btn_Cfgs.EDIT_VARIETY_CLS,handler:function(){
							self.globalMgr.winEdit.show({key:Btn_Cfgs.EDIT_VARIETY_BTN_TXT,optionType:OPTION_TYPE.EDIT,self:self});
						}},/*复制品种*/
						/*
						{text:Btn_Cfgs.COPY_VARIETY_BTN_TXT,iconCls:Btn_Cfgs.COPY_VARIETY_CLS,handler:function(){
							self.globalMgr.winEdit.show({key:Btn_Cfgs.COPY_VARIETY_BTN_TXT,optionType:OPTION_TYPE.DETAIL,self:self});
						}},*/
						{type:'sp'},/*查看流程图*/	
						{token:'查看那流程图',text:Btn_Cfgs.VIEW_PROCESS_BTN_TXT,iconCls:Btn_Cfgs.VIEW_PROCESS_CLS,handler:function(){
							self.globalMgr.winEdit.show({key:Btn_Cfgs.VIEW_PROCESS_BTN_TXT,optionType:OPTION_TYPE.EDIT,self:self});
						}},/*配置流程*/ 
						{token:'配置流程图',text:Btn_Cfgs.CFG_PROCESS_BTN_TXT,iconCls:Btn_Cfgs.CFG_PROCESS_CLS,handler:function(){
							self.globalMgr.winEdit.show({key:Btn_Cfgs.CFG_PROCESS_BTN_TXT,optionType:OPTION_TYPE.ADD,self:self});
						}},
						{type:'sp'},
						{token:'启用',text:Btn_Cfgs.ENABLED_BTN_TXT,iconCls:Btn_Cfgs.ENABLED_CLS,handler:function(){
							EventManager.enabledData('./sysVariety_enabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
						}},
						{token:'禁用',text:Btn_Cfgs.DISABLED_BTN_TXT,iconCls:Btn_Cfgs.DISABLED_CLS,handler:function(){
							EventManager.disabledData('./sysVariety_disabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});
						}},
						{token:'删除',text:Btn_Cfgs.DELETE_BTN_TXT,iconCls:Btn_Cfgs.DELETE_CLS,handler:function(){
							EventManager.deleteData('./sysVariety_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
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
 		var txt_name = FormUtil.getTxtField({fieldLabel : '品种名称',name:'name'});
 		var rad_bussType = FormUtil.getRadioGroup({fieldLabel : '业务性质',name:'bussType',width:200,
							items : [{boxLabel : '无限制', name:'bussType',inputValue:0,width:90},
								{boxLabel : '个人客户', name:'bussType',inputValue:1,width:90},
							    {boxLabel : '企业客户', name:'bussType',inputValue:2,width:90}]});
							    
 		var rad_isCredit = FormUtil.getRadioGroup({fieldLabel : '是否授信品种',name:'isCredit',
							items : [{boxLabel : '否', name:'isCredit',inputValue:0},
									{boxLabel : '是', name:'isCredit',inputValue:1}]});
							    
		 var layout_fields = [hid_sysid,{cmns:FormUtil.CMN_THREE,fields:[txt_name,rad_bussType,rad_isCredit]}]

		var queryFrm = FormUtil.createLayoutFrm(null,layout_fields);
		return queryFrm;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
	 var structure_1 = [
	 	{
		    header: '可用标识',
		    name: 'isenabled',
		    width: 60,
		    renderer: Render_dataSource.isenabledRender
		},
		{
		    header: '品种编号',
		    name: 'code',
		    width: 80
		},
		{
		    header: '品种名称',
		    name: 'name',
		    width: 120
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
		    header: '是否授信品种',
		    name: 'isCredit',
		    renderer: function(val) {
		        switch (val) {
		        case '0':
		            val = '否';
		            break;
		        case '1':
		            val = '是';
		            break;
		        }
		        return val;
		    }
		},
		{
		    header: '适用机构',
		    name: 'useorg',
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
		    name: 'companyName'
		},
		{
		    header: '限制贷款金额',
		    name: 'isLoan',
		    renderer: function(val) {
		        switch (val) {
		        case '0':
		            val = '不限制';
		            break;
		        case '1':
		            val = '限制';
		            break;
		        }
		        return val;
		    }
		},
		{
		    header: '金额范围(万元)',
		    name: 'amountRange',
		     renderer: function(val) {
		     var val =val.replace(",","至");
		     return val;
		     }
		},
		{
		    header: '限制贷款期限',
		    name: 'isLimit',
		    renderer: function(val) {
		        switch (val) {
		        case '0':
		            val = '不限制';
		            break;
		        case '1':
		            val = '限制';
		            break;
		
		        }
		        return val;
		    }
		},
		{
		    header: '期限范围',
		    name: 'limitRange',
		     renderer: function(val) {
		     	if(val!=null){
			     	var ymd= val.replace(",","至");
			     	var subymd = ymd.substring(ymd.lenght-1,ymd.lenght)
				     	if(subymd="D"){
				     		var ymd = ymd.replace("D","天");
				     	}
				     	if(subymd="M"){
				     		var ymd = ymd.replace("M","月");
				     	}
				     	if(subymd="M"){
				     		var ymd = ymd.replace("Y","年");
				     	}
				     	return ymd;
		     	}else{
		     		return null;
		     	}
		     	
		     } 	
		}];
		var appgrid_1 = new Ext.ux.grid.AppGrid({
		    title: '业务品种列表',
		    tbar : this.toolBar,
		    structure: structure_1,
		    url: './sysVariety_list.action',
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
		this.appgrid.reload({sysId:sysId});
	},
	globalMgr : {
		sysId : null,	/*系统ID*/
		/**
		 * 查询方法
		 * @param {} _this
		 */
		
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		activeKey : null,
		sysId : null,
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent = _this.appgrid;
				var selId = null;
				if(winkey != Btn_Cfgs.INSERT_VARIETY_BTN_TXT){
					selId = parent.getSelId();
					if(!selId) return;
				}
				
				parentCfg.parent = parent;
				parentCfg.bussType = 1; /* 业务品种类型标识 */
				parentCfg.sysId = _this.globalMgr.sysId; /* 业务品种类型标识 */
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule=null;
					switch(winkey){
						case Btn_Cfgs.INSERT_VARIETY_BTN_TXT :
						case Btn_Cfgs.EDIT_VARIETY_BTN_TXT :{
							winModule="VarietyEdit";
							break;
						}case Btn_Cfgs.VIEW_PROCESS_BTN_TXT :/*查看流程图*/
						case Btn_Cfgs.CFG_PROCESS_BTN_TXT :{/*配置流程*/
							winModule="BussProccEdit";
							break;
						}
					}
					Cmw.importPackage('pages/app/sys/variety/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		},
		query : function(_this){
				var params = _this.queryFrm.getValues();
				if(params.bussType ==""){
					params.bussType=0;
				}
				if(params.isCredit==""){
					params.isCredit=0;
				}
				params.sysId=_this.globalMgr.sysId;
				if(params) {
					EventManager.query(_this.appgrid,params);
				}
		}
	}
});

