/**
 * 项目调查报告页面
 * @author 程明卫
 * @date 2013-11-18
 */
define(function(require, exports) {
	exports.viewUI = {
		appMainPanel : null,
		tab : null,
		params : null,
		iframeId :　null,
		/**
		 * 获取主面板
		 * @param tab Tab 面板对象
		 * @param params 由菜单点击所传过来的参数
		 * 	{tabId : tab 对象ID,apptabtreewinId : 系统程序窗口ID,sysid : 系统ID} 
		 */
		getMainUI : function(tab,params){
			/*由必做或选做业务菜单传入的回調函数，主要功能：
			 * finishBussCallback : 当业务表单保存后，更新必做或选做事项为已做,
			 * unFinishBussCallback : 当删除业务表单后，取消已做标识
			 * */
			var finishBussCallback = tab.finishBussCallback;
			this.setParams(tab, params);
			if(!this.appMainPanel){
				this.createCmpts();
			}
			var _this = this;
			tab.notify = function(_tab) {
				_this.setParams(_tab, _tab.params);
				_this.refresh();
			};
			return this.appMainPanel;
		},
		/**
		 * 创建组件
		 */
		createCmpts : function(){
			this.iframeId = Ext.id(null,"sureyAuditIframeId");
			var htmlArr = [
				'<iframe id="'+this.iframeId+'" name="'+this.iframeId+'"  style="width:100%; height:100%;overflow:auto;" frameborder="0"></iframe>'//,
			];
			var tbar = this.getToolBar();
			this.appMainPanel = new Ext.Panel({tbar:tbar,html : htmlArr.join(" ")});//
		},
		getToolBar : function(){
			var _this = this;
			var toolBar = null;
			var barItems = [{
				token:"编辑",
				text:Btn_Cfgs.MODIFY_BTN_TXT,
				iconCls:Btn_Cfgs.MODIFY_CLS,
				tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
				handler : function(){
					toolBar.disableButtons(Btn_Cfgs.MODIFY_BTN_TXT+","+Btn_Cfgs.PRINT_BTN_TXT);
					toolBar.enableButtons(Btn_Cfgs.SAVE_BTN_TXT);
					_this.makeDatas(1);
				}
			},{
				token:"保存",
				text:Btn_Cfgs.SAVE_BTN_TXT,
				iconCls:Btn_Cfgs.SAVE_CLS,
				tooltip:Btn_Cfgs.SAVE_TIP_BTN_TXT,
				handler : function(){
					toolBar.enableButtons(Btn_Cfgs.MODIFY_BTN_TXT+","+Btn_Cfgs.PRINT_BTN_TXT);
					toolBar.disableButtons(Btn_Cfgs.SAVE_BTN_TXT);
					_this.makeDatas(2);
				}
			},{
				token:"打印",
				text:Btn_Cfgs.PRINT_BTN_TXT,
				iconCls:Btn_Cfgs.PRINT_CLS,
				tooltip:Btn_Cfgs.PRINT_TIP_BTN_TXT,
				handler : function(){
					_this.makeDatas(3);
				}
			}];
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
			return toolBar;
		},
		setParams : function(tab, params) {
			this.tab = tab;
			this.params = params;
		},
		refresh : function() {
			if(!this.appMainPanel){
				return null;
			}
			if (!this.appMainPanel.rendered) {
				var _this = this;
				this.appMainPanel.addListener('render', function(cmpt) {
						_this.loadDatas();
				});
			} else {
				this.loadDatas();
			}
		},
		/**
		 * 处理数据
		 * @param actionType 动作类型 [1:编辑,2:保存,3:打印]
		 */
		makeDatas : function(actionType){
			var tab = this.tab;
			var iframeEle = window.parent.frames[this.iframeId];
			if(!iframeEle) return;
			switch(actionType){
				case 1 :{/*编辑*/
					iframeEle.AuditProjectMgr.doEdit();
					break;
				}case 2 :{/*保存*/
					var finishBussCallback = tab.finishBussCallback;
					iframeEle.AuditProjectMgr.doSave(finishBussCallback);
					break;
				}case 3 :{/*打印*/
					iframeEle.AuditProjectMgr.doPrint();
					break;
				}
			}
		},
		/**
		 * 在这里加载数据
		 */
		loadDatas : function(printaction){
			this.appMainPanel.doLayout();
			var projectId = this.params.applyId;
			var custType = this.params.custType;
			var customerId = this.params.customerId;
			var module = (custType && custType == 1) ? "EntAuditSurvey.jsp" : "OneAuditSurvey.jsp";
			var url = "./pages/app/workflow/variety/formUIs/survey/"+module;
			var parentContainerId = this.appMainPanel.getId();
			var pars = "?parentContainerId="+parentContainerId+"&projectId="+projectId+"&custType="+custType+"&customerId="+customerId;
			if(printaction) pars += "&printaction="+printaction;
			var iframeEle = Ext.get(this.iframeId);
			if(!iframeEle) return;
			Cmw.mask(this.appMainPanel,Msg_SysTip.msg_loadingUI);
			window.parent.frames[this.iframeId].location= url + pars;
		},
		resize : function(adjWidth,adjHeight){
			this.appMainPanel.setWidth(adjWidth);
			this.appMainPanel.setHeight(adjHeight);
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function(){
			if(null != this.appMainPanel){
				 this.appMainPanel.destroy();
				 this.appMainPanel = null;
			}
			if(this.iframeId){
				var iframeEle = Ext.get(this.iframeId);
				if(iframeEle) iframeEle.remove();
			}
		}
	}
});