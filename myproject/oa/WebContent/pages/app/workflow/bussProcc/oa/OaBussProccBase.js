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
			tab.cached = 0;/*指示Tab 不缓存*/
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
			if(this.optionType && this.optionType == OPTION_TYPE.EDIT){/*当修改时，将条款内容清空。以便跳过条款显示面板，直接显示申请页面*/
				this.showUIByBussProcc();
			}else{
				this.loadClauseInfo();
			}
		},
		/**
		 * 加载条款信息
		 */
		loadClauseInfo : function(){
			var _this = this;
			var currTabId = this.currTabId;
			var bussProccId = this.params.bussProccId;
			if(!bussProccId){
				ExtUtil.error({msg:'在调用 "loadClauseInfo"方法时，要求必须传入 bussProccId[子业务流程ID] 参数值!'});
				return;
			}
			var isShowNavContent = this.params.isShowNavContent;
			if(isShowNavContent){/*显示导航内容和条款说明*/
				this.showUIByBussProcc();
			}else{
				var formUrl = this.params.formUrl;
				var pdid =  this.params.pdid;
				var json_data = {formUrl:formUrl,pdid:pdid};
				_this.showUI(json_data);
			}
		},
		/**
		 * 根据子业务流程数据显示表单
		 */
		showUIByBussProcc : function(){
			var _this = this;
			var bussProccId = this.params.bussProccId;
			if(!bussProccId){
				ExtUtil.error({msg:'在调用 "loadClauseInfo"方法时，要求必须传入 bussProccId[子业务流程ID] 参数值!'});
				return;
			}
			EventManager.get('./sysBussProcc_getClause.action',{params:{id:bussProccId},sfn:function(json_data){
				_this.showUI(json_data);		  			
		  	},ffn:function(json_data){
		  		var isSessionTimeOut = EventManager.existSystem({responseText:Ext.encode(json_data)});
		  		if(!isSessionTimeOut) ExtUtil.alert({msg:json_data.msg});
		  	}});
		},
		/**
		 * 显示申请UI
		 */
		showUI : function(bpData){
			//name,pdid,formUrl,icon,content
			var _appMainPanel = Ext.getCmp(this.tab.appMainPanelId);
			var formUrl = bpData.formUrl;
			var content = bpData.content;
			var menuId = bpData.menuId;
			var dcode = bpData.dcode;
			var pdid = bpData.pdid;
			this.params.pdid = pdid;
			this.params.formUrl = formUrl;
			if(menuId) this.params.menuId = menuId;
			if(dcode) this.params.dcode = dcode;
			var optionType = this.params.optionType;
			
			if(optionType && optionType == OPTION_TYPE.EDIT){/*当修改时，将条款内容清空。以便跳过条款显示面板，直接显示申请页面*/
				content = null;
			}
			if(content){/*有条款，显示条款信息*/
				_appMainPanel.params = this.params;
				this.showClauseUI(_appMainPanel,content,pdid);
			}else{
				this.showApplyUI(_appMainPanel,formUrl);
			}
		},
		/**
		 * 显示条款UI
		 */
		showClauseUI : function(appMainPanel,content,pdid){
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
			var url = "./sysBussNode_processImage.action?pdid="+pdid;
			var htmlArr = ["<div style='text-align:center;overflow:auto;padding:10px;'>",
							content,"</div>",
							'<div><span style="display:block;width:100%;height:25px;font-weight:bold;font-size:16px;background-color:#FFB361;padding:5px;padding-left:20px;">流程图&nbsp;&nbsp;&gt&gt</span><br/>',
							'<image src="'+url+'"  style="position:relative; left:0px; top:0px;"></div>'
						];
			clausePanel.update(htmlArr.join(" "));
			clausePanel.doLayout();
		},
		/**
		 * 创建申请单工具栏对象
		 * @param applyUI 申请单UI界面
		 */
		createAppToolBar : function(applyMod){
			var _this = this;
			var tabId = this.currTabId;
			var barItems = [
							{type:'label',text:'单据名称'},
							{type:'txt',name:'piname',width:250},
							{type:'label',text:'&nbsp;&nbsp;紧急程度'},
							{type:'lcbo',name:'degree',data:[["1","一般"],["2","重要"],["3","紧急"]],width:100},
							{type:'sp'},{token : '暂存',text:Btn_Cfgs.TEMP_SAVE_BTN_TXT,iconCls:Btn_Cfgs.TEMP_SAVE_CLS,handler:function(){
								_this.saveData(applyMod,toolBar,Buss_Constant.ActionType_0);
							}},
							{token : '提交',text:Btn_Cfgs.SUBMIT_BTN_TXT,iconCls:Btn_Cfgs.SUBMIT_CLS,handler:function(){
								_this.saveData(applyMod,toolBar,Buss_Constant.ActionType_1);
							}}
							];
			var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
			toolBar.addListener('render',function(tbar){
				_this.setPiValues(toolBar);
			});
			return toolBar;
		},
		/**
		 * 为工具栏上的流程对象赋值
		 */
		setPiValues : function(toolBar){
			var c_pars = this.getCurrParams();
			var data = null;
			if(this.optionType && this.optionType == OPTION_TYPE.EDIT){/*修改*/
				var oaprocInstanceId = c_pars.oaprocInstanceId;
				if(!oaprocInstanceId){
					ExtUtil.error({msg:'当修改时，必须提供为参数"oaprocInstanceId"提供一个不为空的值！'});
					return;
				}
				EventManager.get('./oaProcInstance_get.action',{params:{id:oaprocInstanceId},sfn:function(json_data){
				  var piname = json_data.piname;
				  var degree = json_data.degree;
				  data = {piname:piname,degree:degree};
				  toolBar.setValues(data);
				}});
			}else{/*添加*/
				var name = c_pars.name;
				var today = new Date();
				var todayStr = today.format('Y-m-d H:i');
				var piname = "("+CURENT_EMP+")"+name+"["+todayStr+"]";
				data = {piname:piname,degree:"1"};
				toolBar.setValues(data);
			}
		},
		/**
		 * 保存数据
		 * @param applyUI 申请单对象
		 * @param toolBar 工具栏
		 * @param actionType 动作类型
		 * @param validKey 要检查的KEY
		 * @param msg 验证不通过的消息
		 */
		saveData : function(applyUI,toolBar,actionType){
			var _this = this;
			applyUI.saveData(function(applyId,entityName){_this.savePiData(toolBar,actionType,applyId,entityName);});
		},
		/**
		 * 保存 OA 工作流程实例数据
		 * @param toolBar 工具栏对象
		 */
		savePiData : function(toolBar, actionType, applyId, entityName){
			var _this = this;
			var c_pars = this.getCurrParams();
			var datas = toolBar.getValues();
			var procId = c_pars.procId;
			var bussProccId = c_pars.bussProccId; 
			datas.id = procId;
			datas.bussProccId = bussProccId;
			if(!this.optionType || this.optionType == OPTION_TYPE.ADD){/*当新增时，才更新申请单中的OA工作流程实例ID*/
				datas.applyId = applyId;
				datas.entityName = entityName;
			}
			if(!applyId){
				ExtUtil.error({msg:'在保存表单数据时，必须提供 applyId 值，即主键ID值！'});
				return;
			}
			if(!entityName){
				ExtUtil.error({msg:'在保存表单数据时，必须提供 entityName 值，即该表单对应的实体名称！'});
				return;
			}
			
			var callback = function(){
				switch(actionType){
					case Buss_Constant.ActionType_0:{/*转向暂存页面*/
						var menuId = c_pars.menuId;
						c_pars.uiActiveType = OA_UIActiveType.activeInstance;
						Cmw.activeTabByMenuId(menuId,c_pars);
						break;
					}case Buss_Constant.ActionType_1:{/*转向审批页面*/
						var tabId = OA_CUSTTAB_ID.bussProcc_auditMainUITab.id;
						var url = OA_CUSTTAB_ID.bussProcc_auditMainUITab.url;
						var name= c_pars["name"];
						var title =  'name';
						if(name){
							title = title.replace('申请单','审批').replace('申请','审批');
						}
						var bussProccCode = c_pars["dcode"];
						c_pars["bussProccCode"] = bussProccCode;
						c_pars.uiActiveType = OA_UIActiveType.newInstance;
						Cmw.activeTabByOa(tabId,c_pars,url,title,OA_UIActiveType.newInstance);
						break;
					}
				}
				var _tabId = _this.currTabId;
				var currTab = Ext.getCmp(_tabId);
				if(currTab) currTab.destroy();
			}
			
			EventManager.get('./oaProcInstance_save.action',{params:datas,sfn:function(json_data){
				callback();
			}});
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
				 	var tbar = _this.createAppToolBar(applyMod);
				 	appMainPanel.add(tbar);
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
		 * 获取当前Tab 对象所提供的参数
		 */
		getCurrParams : function(){
			var tabId = this.currTabId;
			if(!tabId){
				ExtUtil.error({msg:"参数: \"currTabId\" 的值为空!"});
				return null;
			}
			var _tab =  Ext.getCmp(tabId);
			var _params = _tab.params;
			return _params;
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
		}
	}
});