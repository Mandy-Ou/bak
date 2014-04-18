/**
 * 九洲科技公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 最低收费标准设置 UI smartplatform_auto 2013-03-01 09:48:16
 */ 
cmw.skythink.MinAmountMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.MinAmountMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGContainerView({
			hasTopSys : false,
			tab : tab,
			params : params,
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
  		var cbo_status = FormUtil.getLCboField({fieldLabel : '审批状态',name:'status',data: Lcbo_dataSource.getAllDs(Lcbo_dataSource.minamount_status_datas)});
		var txt_startDate = FormUtil.getDateField({name:'startDate',width:90});
		var txt_endDate = FormUtil.getDateField({name:'endDate',width:90});
		var comp_opDate = FormUtil.getMyCompositeField({
			itemNames : 'startDate,endDate',
			sigins : null,
			 fieldLabel: '生效日期',width:210,sigins:null,
			 name:'comp_opDate',
			 items : [txt_startDate,{xtype:'displayfield',value:'至'},txt_endDate]
		});
		var txt_creatorMan = FormUtil.getTxtField({fieldLabel : '设定人',name:'creatorMan'});
		var layout_fields = [{cmns:FormUtil.CMN_THREE,fields:[cbo_status,comp_opDate,txt_creatorMan]}]
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
			token : '提交',
			text : Btn_Cfgs.SUBMIT_BTN_TXT,
			iconCls:Btn_Cfgs.SUBMIT_CLS,
			tooltip:Btn_Cfgs.SUBMIT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.audit(self,1);
			}
		},{type:"sp"},{
			token : '审批通过',
			text : Btn_Cfgs.VIAAUDIT_BTN_TXT,
			iconCls:Btn_Cfgs.VIAAUDIT_CLS,
			tooltip:Btn_Cfgs.VIAAUDIT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.audit(self,3);
			}
		},{
			token : '取消审批',
			text : Btn_Cfgs.CANCELAUDIT_BTN_TXT,
			iconCls:Btn_Cfgs.CANCELAUDIT_CLS,
			tooltip:Btn_Cfgs.CANCELAUDIT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.audit(self,2);
			}
		},{type:"sp"},{
			token : '起用',
			text : Btn_Cfgs.ENABLED_BTN_TXT,
			iconCls:'page_enabled',
			tooltip:Btn_Cfgs.ENABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.enabledData('./fcMinAmount_enabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
			}
		},{
			token : '禁用',
			text : Btn_Cfgs.DISABLED_BTN_TXT,
			iconCls:'page_disabled',
			tooltip:Btn_Cfgs.DISABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.disabledData('./fcMinAmount_disabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});
			}
		},{
			token : '删除',
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./fcMinAmount_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 * 获取Grid 对象
	 */
	//创建表的列
	getAppGrid : function(){
		  var _this = this;
	      var structure_1 = [
	      	{header : '可用标识',name : 'isenabled',width:60,renderer : Render_dataSource.isenabledRender},
			{header: '状态',name: 'status',renderer: Render_dataSource.minAmountStatusRender},
			{header: '生效日期',name: 'opdate'},
			{header: '罚息(元)',name: 'mpamount',width: 75},
			{header: '滞纳金(元)',name: 'moamount',width: 75},
			{header: '设定人',name: 'creatorMan'},
			{header: '设定日期', name: 'createTime'},
			{header: '说明',name: 'remark',width: 200},
			{
			    header: '审核人',
			    name: 'adman'
			},
			{
			    header: '审核日期',
			    name: 'adate'
			},
			{
			    header: '审核意见',
			    name: 'adresult'
			}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    title: '罚息、滞纳金最低收费标准设置列表',
			    structure: structure_1,
			    tbar : this.toolBar,
			    url: './fcMinAmount_list.action',
			    needPage: true,
			    keyField: 'id',
			    isLoad: false,
			   	listeners : {
			   		render : function(grid){
			   	 		_this.globalMgr.query(_this);
			   		}
			   	}
			});
		return appgrid_1;
	},
	refresh:function(optionType,data){//刷新
		var activeKey = this.globalMgr.activeKey;
			if(activeKey =="添加风险等级"||activeKey =="编辑风险等级"){
				this.appgrid.reload();
			}else{
				this.globalMgr.query(this);
			}
		this.globalMgr.activeKey = null;
	},
	globalMgr : {
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
		/**
		 * 提交/审批通过/取消审批 最低金额配置 
		 * @param {} _this
		 * @param {} opType
		 */
		audit : function(_this,status){
		//id,status,adresult
			var id = _this.appgrid.getSelId();
			var selObj = _this.appgrid.getCmnVals("status,opdate");
			if(!id) return;
			var params = {id:id,status:status};
			if(status == 1){
				if(sel_status && sel_status != 0){
					ExtUtil.alert({msg:'要执行提交操作，必须选择状态为"未提交"的记录'});
					return;
				}
				Ext.Msg.confirm("提示","确定提交选中的记录?",function(btn){
					if(btn != 'yes') return;
					auditData();
				});
			}else{
				var title = "审批通过";
				
				var sel_status = selObj.status;
				var sel_opdate = selObj.opdate;
				if(status == 2){//取消审批
					title = "取消审批";
					if(sel_status != 3){
						ExtUtil.alert({msg:'要取消审批，必须选择状态为"审批通过"的记录'});
						return;
					}
					var sel_opdate = Date.parseDate(sel_opdate,'Y-m-d');
					var today = new Date();
					if(sel_opdate <= today){
						today = today.format('Y-m-d');
						ExtUtil.alert({msg:'只有生效日期大于"'+today+'"的记录才能取消审批!'});
						return;
					}
				}else{
					if(sel_status != 1){
						ExtUtil.alert({msg:'要审批通过，必须选择状态为"审批中"的记录'});
						return;
					}
				}
				Ext.Msg.prompt("提示","确定"+title+"选中的记录?<br/>如有审批意见填写，可在文本框中输入。",function(btn,text){
					if(btn != 'ok') return;
					params.adresult = text;
					auditData();
				},false);
			}
			function auditData(){
				EventManager.get('./fcMinAmount_audit.action',{params:params,sfn:function(json_data){
				  	var msg = "";
				  	switch(status){
				  		case 1 :{ 
					  		msg = "提交成功!";
					  		break;
				  		}case 2 :{ 
					  		msg = "取消审批成功!";
					  		break;
				  		}case 3 :{ 
					  		msg = "审批通过成功!";
					  		break;
				  		}
				  	}
				    Ext.tip.msg(Msg_SysTip.title_appconfirm, msg);
				    _this.globalMgr.query(_this);
				 }});
			}
		},
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		activeKey : null,
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent = _this.appgrid;
				parentCfg.parent = parent;
				if(winkey == "编辑"){
					if(!parent.getSelId()) return;
					var selObj = _this.appgrid.getCmnVals("status");
					if(selObj && selObj.status != 0){
						ExtUtil.alert({msg:'只能编辑状态为"未提交"的记录'});
						return;
					}
				}
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule=null;
					if(winkey=="添加"||winkey=="编辑"){
						winModule="MinAmountEdit";
					}
					Cmw.importPackage('pages/app/finance/fcinit/minamount/'+winModule,function(module) {//导入包的，有重构
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		}
	}
});

