Ext.namespace("skythink.cmw.oa");
/**
 * 公告管理UI
 * 
 * @author shilong
 */
skythink.cmw.oa.NewsManageMgr = function(){
	this.init(arguments[0]);
};

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.oa.NewsManageMgr,Ext.util.MyObservable,{
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
		 * 刷新方法
		 */
		refresh : function() {

		},
		/**
		 * 创建Grid
		 */
		getAppGrid : function(){
			var structure_1 = [{
			    header: '发布人',
			    name: 'creator'
			},
			{
			    header: '公告标题',
			    name: 'title'
			},
			{
			    header: '公告类型',
			    name: 'btype'
			},
			{
			    header: '发布范围',
			    name: 'pubLimit'
			},
			{
			    header: '生效日期',
			    name: 'startDate'
			},
			{
			    header: '失效日期',
			    name: 'endDate'
			},
			{
			    header: '是否置顶',
			    name: 'istop'
			},
			{
			    header: '置顶失效日期',
			    name: 'topDate'
			},
			{
			    header: '内容路径',
			    name: 'path'
			},
			{
			    header: '审批通过自动发布',
			    name: 'autoPub'
			},
			{
			    header: '状态',
			    name: 'status'
			},
			{
			    header: '操作',
			    name: 'operating'
			}];
			var _this=this;
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    structure: structure_1,
			    url: 'undefined',
				isLoad: true,
			    needPage: true,
			    height:screen.height-500,
			    tbar:_this.getToolBar(),
			    keyField: 'id'
			});
			return appgrid_1;
	},
		/**
		 * 创建Form表单
		 */
		getQueryFrm : function(){
			var txt_title = FormUtil.getTxtField({
			    fieldLabel: '公告标题',
			    name: 'title',
			    "width": 275,
			    "maxLength": "100"
			});
			
			var txt_btype  = FormUtil.getLCboField({
			    fieldLabel: '公告类型',
			    name: 'columnId',
			    "width": 125,
			    "data": [["0", "选择类型"],["1", "行政公告"], ["2", "人事公告"]]
			});
			
			var int_status = FormUtil.getLCboField({
			    fieldLabel: '状态',
			    name: 'status',
			    "width": 125,
			    "data": [["0", "选择状态"],["1", "已发布"], ["2", "未发布"]],
			    "maxLength": 10
			});
			
			var int_istop = FormUtil.getLCboField({
			    fieldLabel: '是否置顶',
			    name: 'istop',
			    "width": 125,
			    "data": [["0", "选择置顶"],["1", "是"], ["2", "否"]],
			    "maxLength": 10
			});
			
			var txt_startDate1 = FormUtil.getDateField({name:'startDate1',width:125});
			var txt_endDate1 = FormUtil.getDateField({name:'endDate1',width:125});
			var comp_startDate = FormUtil.getMyCompositeField({
				itemNames : 'startDate1,endDate1',
				sigins : null,
				 fieldLabel: '生效日期',width:310,sigins:null,
				 name:'comp_estartDate',
				 items : [txt_startDate1,{xtype:'displayfield',value:'至'},txt_endDate1]
			});
			
			
			var bdat_endDate = FormUtil.getDateField({
			    fieldLabel: '失效日期',
			    name: 'endDate',
			    "width": 125,
			    "allowBlank": false
			});
			var txt_startDate2 = FormUtil.getDateField({name:'startDate2',width:125});
			var txt_endDate2 = FormUtil.getDateField({name:'endDate2',width:125});
			var comp_endDate = FormUtil.getMyCompositeField({
				itemNames : 'startDate2,endDate2',
				sigins : null,
				 fieldLabel: '失效日期',width:310,sigins:null,
				 name:'comp_endDate',
				 items : [txt_startDate2,{xtype:'displayfield',value:'至'},txt_endDate2]
			});
			
			var layout_fields = [
			{
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_title, txt_btype,comp_startDate, int_status,  comp_endDate,int_istop]
			}];
			var frm_cfg = {
			    url: '#SAVEURL#'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform_1;
		},
			/**
			 * 工具栏
			 */
			getToolBar : function(){
				Cmw.print(this.params);
				var self = this;
				var barItems = [{type:"sp"},{
					token : '查询',
					text : Btn_Cfgs.QUERY_BULLETIN_PLAN_BTN_TXT,
					icon:Btn_Cfgs.QUERY_BULLETIN_CLS,
					tooltip:Btn_Cfgs.QUERY_BULLETIN_PLAN_TIP_BTN_TXT,
					handler : function(){
						EventManager.enabledData('./oaBULLETIN_.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
					}
				},{type:"sp"},{
					token : '添加',
					text : Btn_Cfgs.CREATE_BULLETIN_PLAN_BTN_TXT,
					icon: Btn_Cfgs.CREATE_BULLETIN_BTN_CLS,
					tooltip:Btn_Cfgs.CREATE_BULLETIN_PLAN_TIP_BTN_TXT,
					handler : function(){
						self.globalMgr.winEdit.show({key:"添加",optionType:OPTION_TYPE.EDIT,self:self});
					}
				},{type:"sp"},{
					token : '编辑',
					text : Btn_Cfgs.BULLETIN_EDIT_BTN_TXT,
					icon:Btn_Cfgs.BULLETIN_EDIT_BTN_CLS,
					tooltip:Btn_Cfgs.BULLETIN_EDIT_TIP_BTN_TXT,
					handler : function(){
						self.globalMgr.winEdit.show({key:"编辑",optionType:OPTION_TYPE.EDIT,self:self});
					}
				},{type:"sp"},{
					token : '删除',
					text : Btn_Cfgs.BULLETIN_DEL_BTN_TXT,
					icon:Btn_Cfgs.BULLETIN_DEL_BTN_CLS,
					tooltip:Btn_Cfgs.BULLETIN_DEL_TIP_BTN_TXT,
					handler : function(){
						self.globalMgr.winEdit.show({key:"删除",optionType:OPTION_TYPE.EDIT,self:self});
					}
				},{type:"sp"},{
					token : '查看详情',
					text : Btn_Cfgs.BULLETIN_DETAIL_BTN_TXT,
					icon:Btn_Cfgs.BULLETIN_DETAIL_BTN_CLS,
					tooltip:Btn_Cfgs.BULLETIN_DETAIL_TIP_BTN_TXT,
					handler : function(){
						self.globalMgr.winEdit.show({key:"查看详情",optionType:OPTION_TYPE.EDIT,self:self});
					}
				},{type:"sp"},{
					token : '启用',
					text : Btn_Cfgs.BULLETIN_ENABLED_BTN_TXT,
					icon: Btn_Cfgs.BULLETIN_ENABLED_BTN_CLS,
					tooltip:Btn_Cfgs.BULLETIN_ENABLED_TIP_BTN_TXT,
					handler : function(){
						self.globalMgr.winEdit.show({key:"启用",optionType:OPTION_TYPE.EDIT,self:self});
					}
				},{type:"sp"},{
					token : '禁用',
					text : Btn_Cfgs.BULLETIN_DISABLED_BTN_TXT,
					icon: Btn_Cfgs.BULLETIN_DISABLED_BTN_CLS,
					tooltip:Btn_Cfgs.BULLETIN_DISABLED_TIP_BTN_TXT,
					handler : function(){
						self.globalMgr.winEdit.show({key:"失效",optionType:OPTION_TYPE.EDIT,self:self});
					}
				},{type:"sp"},{
					token : '取消置顶',
					text : Btn_Cfgs.BULLETIN_TOP_BTN_TXT,
					icon: Btn_Cfgs.BULLETIN_TOP_BTN_CLS,
					tooltip:Btn_Cfgs.BULLETIN_TOP_TIP_BTN_TXT,
					handler : function(){
						self.globalMgr.winEdit.show({key:"取消置顶",optionType:OPTION_TYPE.EDIT,self:self});
					}
				},{type:"sp"},{
					token : '取消置顶',
					text : Btn_Cfgs.BULLETIN_NOTOP_BTN_TXT,
					icon: Btn_Cfgs.BULLETIN_NOTOP_BTN_CLS,
					tooltip:Btn_Cfgs.BULLETIN_NOTOP_TIP_BTN_TXT,
					handler : function(){
						self.globalMgr.winEdit.show({key:"取消置顶",optionType:OPTION_TYPE.EDIT,self:self});
					}
				}];
				toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
				return toolBar;
			},
	globalMgr : {
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params = _this.queryFrm.getValues();
			Cmw.print(params);
			if(params) {
				EventManager.query(_this.appgrid,params);
			}
		},
			
		isFormula :null,
		createAttachMentFs:null,
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		activeKey : null,
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				Cmw.print(_this.params);
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent = _this.appgrid;
				parentCfg.parent = parent;
				var apptabtreewinId = _this.params["apptabtreewinId"];
				_this.params.apptabtreewinId = apptabtreewinId;
				_this.params.dispaly  = false;
				var title=null;
				var tabId=null;
				var url=null;
				if(winkey=="添加"||winkey=="编辑"){
					tabId=CUSTTAB_ID.BULLETINEdit_auditMainUITab.id;
					url=CUSTTAB_ID.BULLETINEdit_auditMainUITab.url;
					title="新闻编辑";
				}
				Cmw.activeTab(apptabtreewinId,tabId,_this.params,url,title);
			}
		}
	},
		/**
		 * 组件销毁方法
		 */
		destroy : function() {
			if (this.appMainPanel) {
				this.appMainPanel.destroy();
				this.appMainPanel = null;
			}
		}
});