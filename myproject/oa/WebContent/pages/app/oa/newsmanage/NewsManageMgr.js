Ext.namespace("skythink.cmw.oa");
/**
 * 新闻管理UI
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
			    header: '新闻标题',
			    name: 'title'
			},
			{
			    header: '发表人',
			    name: 'creator'
			},
			{
			    header: '新闻栏目',
			    name: 'columnId'
			},
			{
			    header: '发布日期',
			    name: 'pubDate'
			},
			{
			    header: '点击次数',
			    name: 'clickCount'
			},
			{
			    header: '评论（条）',
			    name: 'comments'
			},
			{
			    header: '发布范围',
			    name: 'pubLimit'
			},{
			    header: '状态',
			    name: 'status'
			},{
			    header: '内容简介',
			    name: 'summary'
			},
			{
			    header: '评论权限',
			    name: 'ctype'
			},
			{
			    header: '操作',
			    name: 'operate'
			}];
			var _this=this;
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    structure: structure_1,
			    url: './oaNews_list.action',
			    tbar:_this.getToolBar(),
			    isLoad: true,
			    needPage: true,
			    height:screen.height-500,
			    keyField: 'id'
			});
			
			return appgrid_1;
		},
			/**
		 * 创建Form表单
		 */
		getQueryFrm : function(){
			var txt_title = FormUtil.getTxtField({
			    fieldLabel: '新闻标题',
			    name: 'title',
			    "width": 125,
			    "maxLength": "100"
			});
			
			var cbo_columnId = FormUtil.getLCboField({
			    fieldLabel: '新闻栏目',
			    name: 'columnId',
			    "width": 125,
			    "data": [["1", "所有新闻"], ["2", "图片新闻"], ["3", "视频新闻"]]
			});
			
			var cbo_status = FormUtil.getLCboField({
			    fieldLabel: '状态',
			    name: 'status',
			    "width": 125,
			    "maxLength": 10,
			    "data": [["1", "未发布"], ["2", "已发布"]]
			});
			
			var txt_startDate1 = FormUtil.getDateField({name:'startDate1',width:120});
			var txt_endDate1 = FormUtil.getDateField({name:'endDate1',width:120});
			var comp_estartDate = FormUtil.getMyCompositeField({
				itemNames : 'startDate1,endDate1',
				sigins : null,
				 fieldLabel: '发布时间',width:310,sigins:null,
				 name:'comp_estartDate',
				 items : [txt_startDate1,{xtype:'displayfield',value:'至'},txt_endDate1]
			});
			
			var min_click = FormUtil.getTxtField({name:'minClick',width:60});
			var max_click = FormUtil.getTxtField({name:'maxClick',width:60});
			var comp_click = FormUtil.getMyCompositeField({
				itemNames : 'minClick,maxClick',
				sigins : null,
				 fieldLabel: '点击次数',width:210,sigins:null,
				 name:'comp_click',
				 items : [min_click,{xtype:'displayfield',value:'至'},max_click]
			});
			var layout_fields = [{
			    cmns: FormUtil.CMN_THREE,
			    fields: [txt_title, cbo_columnId, cbo_status]
			},{
			    cmns: FormUtil.CMN_TWO,
			    fields: [comp_click,comp_estartDate]
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
				var self = this;
				var barItems = [{type:"sp"},{
					token : '查询',
					text : Btn_Cfgs.QUERY_NEWS_PLAN_BTN_TXT,
					icon:Btn_Cfgs.QUERY_NEWS_CLS,
					tooltip:Btn_Cfgs.QUERY_NEWS_PLAN_TIP_BTN_TXT,
					handler : function(){
						EventManager.enabledData('./oaNews_.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
					}
				},{type:"sp"},{
					token : '添加',
					text : Btn_Cfgs.CREATE_NEWS_PLAN_BTN_TXT,
					icon: Btn_Cfgs.CREATE_NEWS_BTN_CLS,
					tooltip:Btn_Cfgs.CREATE_NEWS_PLAN_TIP_BTN_TXT,
					handler : function(){
						self.globalMgr.winEdit.show({key:"添加",optionType:OPTION_TYPE.EDIT,self:self});
					}
				},{type:"sp"},{
					token : '编辑',
					text : Btn_Cfgs.NEWS_EDIT_BTN_TXT,
					icon:Btn_Cfgs.NEWS_EDIT_BTN_CLS,
					tooltip:Btn_Cfgs.NEWS_EDIT_TIP_BTN_TXT,
					handler : function(){
						self.globalMgr.winEdit.show({key:"编辑",optionType:OPTION_TYPE.EDIT,self:self});
					}
				},{type:"sp"},{
					token : '删除',
					text : Btn_Cfgs.NEWS_DEL_BTN_TXT,
					icon:Btn_Cfgs.NEWS_DEL_BTN_CLS,
					tooltip:Btn_Cfgs.NEWS_DEL_TIP_BTN_TXT,
					handler : function(){
						self.globalMgr.winEdit.show({key:"删除",optionType:OPTION_TYPE.EDIT,self:self});
					}
				},{type:"sp"},{
					token : '查看详情',
					text : Btn_Cfgs.NEWS_DETAIL_BTN_TXT,
					icon:Btn_Cfgs.NEWS_DETAIL_BTN_CLS,
					tooltip:Btn_Cfgs.NEWS_DETAIL_TIP_BTN_TXT,
					handler : function(){
						self.globalMgr.winEdit.show({key:"查看详情",optionType:OPTION_TYPE.EDIT,self:self});
					}
				},{type:"sp"},{
					token : '启用',
					text : Btn_Cfgs.NEWS_ENABLED_BTN_TXT,
					icon: Btn_Cfgs.NEWS_ENABLED_BTN_CLS,
					tooltip:Btn_Cfgs.NEWS_ENABLED_TIP_BTN_TXT,
					handler : function(){
						self.globalMgr.winEdit.show({key:"启用",optionType:OPTION_TYPE.EDIT,self:self});
					}
				},{type:"sp"},{
					token : '禁用',
					text : Btn_Cfgs.NEWS_DISABLED_BTN_TXT,
					icon: Btn_Cfgs.NEWS_DISABLED_BTN_CLS,
					tooltip:Btn_Cfgs.NEWS_DISABLED_TIP_BTN_TXT,
					handler : function(){
						self.globalMgr.winEdit.show({key:"失效",optionType:OPTION_TYPE.EDIT,self:self});
					}
				},{type:"sp"},{
					token : '置顶',
					text : Btn_Cfgs.NEWS_TOP_BTN_TXT,
					icon: Btn_Cfgs.NEWS_TOP_BTN_CLS,
					tooltip:Btn_Cfgs.NEWS_TOP_TIP_BTN_TXT,
					handler : function(){
						self.globalMgr.winEdit.show({key:"置顶",optionType:OPTION_TYPE.EDIT,self:self});
					}
				},{type:"sp"},{
					token : '取消置顶',
					text : Btn_Cfgs.NEWS_NOTOP_BTN_TXT,
					icon: Btn_Cfgs.NEWS_NOTOP_BTN_CLS,
					tooltip:Btn_Cfgs.NEWS_NOTOP_TIP_BTN_TXT,
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
					tabId=OA_CUSTTAB_ID.newsEdit_auditMainUITab.id;
					url=OA_CUSTTAB_ID.newsEdit_auditMainUITab.url;
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