/**
 * 子业务流程基础公共页面
 * @author 程明卫
 * @date 2013-09-06
 */
define(function(require, exports) {
	exports.viewUI = {
		tab : null,
		appMainPanel : null,
		params : null,
		optionType : OPTION_TYPE.ADD,/*默认为新增*/
		currTabId : null,
		idPrefix : 'BussProccBase_',
		idClausePrefix : 'BussProccBaseClause_',/*条款ID前缀*/
		loadMask : null,
		chooseWin : null,/*流程选择窗口*/
		chooseMenusPanel : null,/*流程选择面板*/
		/**
		 * 获取主面板
		 * @param tab Tab 面板对象
		 * @param params 由菜单点击所传过来的参数
		 * 	{tabId : tab 对象ID,apptabtreewinId : 系统程序窗口ID,sysid : 系统ID} 
		 */
		getMainUI : function(tab,params){
			//this.loadMask = new Ext.LoadMask(document.body,{msg : '界面加载中...'});
			//this.loadMask.show();
			this.switchPanel(tab);
			this.setParams(tab, params);
			if(!this.appMainPanel){
				var appMainPanelId = Ext.id(null,this.idPrefix);
				tab.appMainPanelId = appMainPanelId;
				this.createCmpts(appMainPanelId);
				this.buildCmpts();
			}
			var _this = this;
			tab.notify = function(_tab) {
				_this.setParams(_tab, _tab.params);
				_this.refresh();
				//_this.loadMask.hide();
			};
			return this.appMainPanel;
		},
		switchPanel : function(tab){
			var tabId = tab.id;
			if(!this.currTabId) this.currTabId = tabId;
			if(this.currTabId != tabId){
				if(this.appMainPanel) this.appMainPanel = null;
				this.currTabId = tabId;
			}
		},
		/**
		 * 创建组件
		 */
		createCmpts : function(appMainPanelId){
			this.appMainPanel = new Ext.Panel({id:appMainPanelId,border:false,autoScroll:false});
			this.appMainPanel.refresh = function(){
				var idArr = ComptIdMgr.getBussProccApplyRefreshIds();
				if(!idArr || idArr.length == 0) return;
				for(var i=0,count=idArr.length; i<count; i++){/*用于及时刷新跳转过来的各列表页面*/
					var cmptId = idArr[i];
					var cmp = Ext.getCmp(cmptId);
					if(cmp && cmp.refresh) cmp.refresh();
				}
				Cmw.print("appMainPanel.refresh");
			}
		},
		/**
		 * 组装组件
		 */
		buildCmpts : function(){
			this.createClauseCmpt();
		},
		/**
		 * 创建条款面板
		 */
		createClauseCmpt : function(){
			/*如果是修改情况下，不创建条款说明面板*/
			if(this.optionType && this.optionType == OPTION_TYPE.EDIT) return;
			var _this = this;
			var clausePanelId = Ext.id(null,this.idClausePrefix);
			this.appMainPanel.clausePanelId = clausePanelId;
			var tabId = _this.currTabId;
			var clausePanel = new Ext.Panel({id:clausePanelId,border:false,autoScroll:true,
			tbar:[{text:Btn_Cfgs.NEXT_BTN_TXT,iconCls:Btn_Cfgs.NEXT_CLS,handler:function(){
				_this.doNext(tabId);
			}},'-',
			{text:Btn_Cfgs.CLOSE_BTN_TXT,iconCls:Btn_Cfgs.CLOSE_CLS,handler:function(){
				_this.closeClauseCmpt(tabId);
			}}
			]});
			this.appMainPanel.add(clausePanel);
		},
		setParams : function(tab, params) {
			this.tab = tab;
			this.currTabId = tab.id;
			this.params = params;
			this.optionType = params.optionType;
		},
		refresh : function() {
			if(!this.tab.appMainPanelId) return;
			var _appMainPanel = Ext.getCmp(this.tab.appMainPanelId);
			if(!_appMainPanel) return;
			if (!_appMainPanel.rendered) {
				var _this = this;
				_appMainPanel.addListener('render', function(cmpt) {
					_this.loadDatas(_appMainPanel);
				});
			} else {
				this.loadDatas(_appMainPanel);
			}
		},
		/**
		 * 在这里加载数据
		 */
		loadDatas : function(_appMainPanel){
			this.loadClauseInfo();
		},
		/**
		 * 加载条款信息
		 */
		loadClauseInfo : function(){
			var _this = this;
			var currTabId = this.currTabId;
			var menuId = this.params.nodeId;
			EventManager.get('./sysBussProcc_getClauseList.action',{params:{menuId:menuId},sfn:function(json_data){
		  		var totalSize = json_data.totalSize;
		  		var list =  json_data.list;
		  		if(!totalSize || totalSize == 0){
		  			ExtUtil.alert({msg:'当前功能没有配置子业务流程，无法进行业务申请!'});
		  			return;
		  		}
		  		if(totalSize == 1){
					_this.showUI(list[0]);		  			
		  		}else{
		  			_this.openChooseWin(list);
		  		}
		  	},ffn:function(json_data){
		  		var isSessionTimeOut = EventManager.existSystem({responseText:Ext.encode(json_data)});
		  		if(!isSessionTimeOut) ExtUtil.alert({msg:json_data.msg});
		  	}});
		},
		/**
		 * 打开选择窗口
		 */
		openChooseWin : function(list){
			var _this = this;
			for(var i=0,count=list.length; i<count; i++){
				var data = list[i];
				var name = data.name;
				var icon = data.icon;
				data["text"] = name;
				data["bicon"] = icon;
			}
			if(null == this.chooseWin){
			 this.chooseMenusPanel = new Ext.ux.FlatImgPanel({
					isLoad : true,
					dataArr : list,
					eventMgr : {
						clickCallback : function(eleId,selData){
							_this.showUI(selData);
							_this.chooseWin.hide();
						}
					}
				});
			  this.chooseWin = new Ext.ux.window.MyWindow({title:'选择流程',autoHeight:true,items:[this.chooseMenusPanel]});	
			}else{
				this.chooseMenusPanel.dataArr = list;
				this.chooseMenusPanel.loadData();
			}
			this.chooseWin.show();
		},
		/**
		 * 显示申请UI
		 */
		showUI : function(bpData){
			//name,pdid,formUrl,icon,content
			var _appMainPanel = Ext.getCmp(this.tab.appMainPanelId);
			var breed = bpData.breed;
			var formUrl = bpData.formUrl;
			var content = bpData.content;
			this.params.breed = bpData.breed;
			this.params.pdid = bpData.pdid;
			this.params.formUrl = formUrl;
			var optionType = this.params.optionType;
			if(optionType && optionType == OPTION_TYPE.EDIT){/*当修改时，将条款内容清空。以便跳过条款显示面板，直接显示申请页面*/
				content = null;
			}
			if(content){/*有条款，显示条款信息*/
				_appMainPanel.params = this.params;
				this.showClauseUI(_appMainPanel,content);
			}else{
				this.showApplyUI(_appMainPanel,formUrl);
			}
		},
		/**
		 * 显示条款UI
		 */
		showClauseUI : function(appMainPanel,content){
			var clausePanelId = appMainPanel.clausePanelId;
			var clausePanel = (clausePanelId) ? Ext.getCmp(clausePanelId) : null;
			if(!clausePanel) return;
			var tabHeight = this.tab.getHeight() || this.tab.el.getComputedHeight();
			var _h = tabHeight-20;
			if(_h <= 0){
				_h = 500;	
			}
			clausePanel.setHeight(_h);
			clausePanel.show();
			clausePanel.update("<div style='text-align:center;overflow:auto;padding:10px;'>"+content+"</div>");
			clausePanel.doLayout();
		},
		/**
		 * 显示申请表单
		 */
		showApplyUI : function(appMainPanel,formUrl){
			var applyMod = appMainPanel.applyMod;
			if(!applyMod){
				var _this = this;
				formUrl = formUrl.replace(".js","");
				Cmw.importPackage(formUrl,function(module) {
					appMainPanel.removeAll();
					var applyMod = module.viewUI;
				 	appMainPanel.applyMod = applyMod;
				 	var applyUI = applyMod.getMainUI(appMainPanel,_this.params);
				 	appMainPanel.add(applyUI);
				 	appMainPanel.doLayout();
				 	_this.loadApplyData(appMainPanel,applyMod);
	  			});
				return;				
			}
			this.loadApplyData(appMainPanel,applyMod);
		},
		/**
		 * 如果修改情况下，加载表单数据
		 */
		loadApplyData : function(appMainPanel,applyMod){
//			if(this.optionType && this.optionType == OPTION_TYPE.EDIT){
				applyMod.setParams(appMainPanel,this.params);
				applyMod.refresh();
//			}
		},
		/**
		 * 
		 */
		doNext : function(tabId){
			var _tab =  Ext.getCmp(tabId);
			var appMainPanelId = _tab.appMainPanelId;
			if(!appMainPanelId) return;
			var _appMainPanel = Ext.getCmp(appMainPanelId);
			var formUrl = _appMainPanel.params.formUrl;
			this.showApplyUI(_appMainPanel,formUrl);
		},
		/**
		 * 关闭条款面板
		 */
		closeClauseCmpt : function(tabId){
			var _tab = Ext.getCmp(tabId);
			if(_tab) _tab.destroy();
		},
		resize : function(adjWidth,adjHeight,_tab){
			var appMainPanelId = _tab.appMainPanelId;
			if(!appMainPanelId) return;
			var _appMainPanel = null;
			if(this.appMainPanel && this.appMainPanel.id == appMainPanelId){
				_appMainPanel = this.appMainPanel;
			}else{
				_appMainPanel = Ext.getCmp(appMainPanelId);
			}
			if(!_appMainPanel) return;
			_appMainPanel.setWidth(adjWidth);
			_appMainPanel.setHeight(adjHeight);
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function(_tab){
			var _appMainPanelId = _tab.appMainPanelId;
			if(!_appMainPanelId) return;
			if(this.appMainPanel && _appMainPanelId == this.appMainPanel.id){
				 var applyMod = this.appMainPanel.applyMod;
				 if(applyMod) applyMod.destroy();
				 this.appMainPanel.destroy();
				 this.appMainPanel = null;
			}else{
				var _appMainPanel = Ext.getCmp(_appMainPanelId);
				if(!_appMainPanel) return;
				 var applyMod = _appMainPanel.applyMod;
				 if(applyMod) applyMod.destroy();
				_appMainPanel.destroy();
				_appMainPanel = null;
			}
			
			if(this.chooseMenusPanel){
				this.chooseMenusPanel.destroy();
				this.chooseMenusPanel = null;
			}
			
			if(this.chooseWin){
				this.chooseWin.destroy();
				this.chooseWin = null;
			}
		}
	}
});