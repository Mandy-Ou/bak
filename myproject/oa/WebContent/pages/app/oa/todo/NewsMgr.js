/**
 * 新闻主页面
 * 
 * @author shilong
 */
define(function(require, exports) {
	exports.viewUI = {
		appMainPanel : null,
		appGrid: null,
		appForm:null,
		tbar:null,
		tab : null,
		params : null,
		createAttachMentFs : null,
		/**
		 * 设置参数
		 */
		setParams : function(tab, params) {
			this.tab = tab;
			this.params = params;
		},
		/**
		 * 主UI 界面开始点
		 */
		getMainUI : function(tab, params) {
			this.setParams(tab, params);
			if (!this.appMainPanel) {
				this.craeteMainPnl();
			}
			var _this = this;
			tab.notify = function(_tab) {
				_this.setParams(_tab, _tab.params);
				_this.refresh();
			};
			return this.appMainPanel;
		},
		/**
		 * 创建主面板
		 */
		craeteMainPnl : function() {
			this.tbar=this.getToolBar();
			this.appGrid=this.createGrid();
			this.appForm=this.createForm();
			var appPanel = new Ext.Panel();
			appPanel.add(this.appForm);
			appPanel.add(this.appGrid);
			this.appMainPanel = appPanel;
			return this.appMainPanel;
		},
		/**
		 * 刷新方法
		 */
		refresh : function() {

		},
		/**
		 * 创建Grid
		 */
		createGrid : function(){
		
			var structure_1 = [{
			    header: '新闻标题',
			    name: 'title'
			},
			{
			    header: '类型',
			    name: 'newsType'
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
			}];
			var _this=this;
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    structure: structure_1,
			    url: './oaNews_list.action',
			    needPage: false,
			    isLoad: true,
			    tbar:_this.tbar,
			    needPage: true,
			    height:screen.height-350,
			    keyField: 'id'
			});
			
			return appgrid_1;
		},

		/**
		 * 创建form表单
		 */
		createForm : function(){
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
			
			var bdat_pubDate = FormUtil.getDateField({
			    fieldLabel: '发布日期',
			    name: 'pubDate',
			    "width": 125
			});
			
			
			var layout_fields = [{
			    cmns: FormUtil.CMN_THREE,
			    fields: [txt_title,cbo_columnId, bdat_pubDate]
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
					tooltip:Btn_Cfgs.QUERY_NEWS_PLAN_TIP_BTN_TXT,
					handler : function(){
						EventManager.enabledData('./fcOrder_enabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
					}
				},{type:"sp"},{
					token : '未读新闻',
					text : Btn_Cfgs.UNREAD_NEWS_PLAN_BTN_TXT,
					tooltip:Btn_Cfgs.UNREAD_NEWS_PLAN_TIP_BTN_TXT,
					handler : function(){
						self.globalMgr.winEdit.show({key:"添加",self:self});
					}
				},{type:"sp"},{
					token : '全部新闻',
					text : Btn_Cfgs.WHOLE_NEWS_PLAN_BTN_TXT,
					tooltip:Btn_Cfgs.WHOLE_NEWS_PLAN_TIP_BTN_TXT,
					handler : function(){
						self.globalMgr.winEdit.show({key:"编辑",optionType:OPTION_TYPE.EDIT,self:self});
					}
				},{type:"sp"},{
					token : '查看详情',
					text : Btn_Cfgs.NEWS_DETAIL_BTN_TXT,
					tooltip:Btn_Cfgs.NEWS_DETAIL_TIP_BTN_TXT,
					handler : function(){
						self.globalMgr.winEdit.show({key:"查看详情",optionType:OPTION_TYPE.EDIT,self:self});
					}
				},{type:"sp"},{
					token : '评论',
					text : Btn_Cfgs.NEWS_COMMENT_BTN_TXT,
					tooltip:Btn_Cfgs.NEWS_COMMENT_TIP_BTN_TXT,
					handler : function(){
						self.globalMgr.winEdit.show({key:"评论",optionType:OPTION_TYPE.EDIT,self:self});
					}
				}];
				toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
				return toolBar;
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
	}
});