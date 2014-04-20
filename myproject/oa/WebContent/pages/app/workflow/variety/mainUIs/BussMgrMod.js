/**
 * 流程审批主页面 ---> 业务办理主界面
 * @author 程明卫
 * @date 2012-12-26
 */
define(function(require, exports) {
	exports.moduleUI = {
		wh :[],
		bussTabPanel : null,
		model : null,/*业务表单模块*/
		params : null,
		activiteBussFormId : null,/*当前激活的表单*/
		/*当前激活的菜单数据
		 * 	菜单ID,ts_NodeCfg ID,是否已做,菜单名称,菜单URL,参数,小图标,大图标,按钮权限,图像IMG ID
	 	 *	id,nodeId,finish,text,jsArray,params,icon,bicon,modDatas,flatImgId
	 	 **/
		menuData : null,
		loadMask : null,
		finishBussCallback : null,/*当自定义业务保存成功后，要调用的更新事项为已办的方法*/
		unFinishBussCallback : null,/*当自定义业务删除表单后，将已做的事项菜单标识为未做的方法*/
		/**
		 * 设置参数
		 */
		setParams : function(params){
			this.params = params;
			this.menuData = params.menuData;
			this.bussTabPanel = params.bussTabPanel;
		},
		/**
		 * 显示面板并加载数据
		 */
		show : function(params){
			this.setParams(params);
			this.activateBussTabPanel();
			this.menuActionClick(this.menuData);
		},
		activateBussTabPanel : function(){
			if(this.bussTabPanel.isVisible()) this.bussTabPanel.show();
			var parentTabPanel = this.bussTabPanel.ownerCt;
			if(parentTabPanel){
				parentTabPanel.unhideTabStripItem(this.bussTabPanel);
				parentTabPanel.activate(this.bussTabPanel);
			}
		},
		/**
		 * 
		 * 菜单点击事件
		 * @param {} node 点击的节点
		 * @param {} e 点击事件源
		 */
		menuActionClick : function(menuData){
			var tab = this.bussTabPanel;
//			tab.setWidth(this.wh[0]);
//			tab.setHeight(this.wh[1]);
			this.loadMask = new Ext.LoadMask(tab.el,{msg : '界面加载中...'});
			var menuId = menuData.id;
			//if(this.activiteBussFormId && this.activiteBussFormId == menuId) return;
			tab.removeAll();
			if(this.model){
				this.model.destroy();
				this.model = null;
			}
			this.activiteBussFormId = menuId;
			var title = menuData.text;
			tab.setTitle(title);
			var tabId = this.bussTabPanel.id; 
			var jsArr = menuData.jsArray;
			var loadType = menuData.loadType;
			if(!loadType) loadType = 1;	/*标准加载方式*/
			if(!jsArr){
				ExtUtil.error({msg:'找不到此菜单的界面文件，无法加载此功能UI！'});
				return;
			}
			var _loadMask = this.loadMask;
			_loadMask.show();
			var _this = this;
			var model = null;
			if(loadType && loadType == 1){	//标准 localXHR 方式加载
				var jsStr = "";
				//加载模块js预置变量名
				 Ext.Ajax.request({
					method:'POST',//为了不丢失js文件内容，所以要选择post的方式加载js文件
					url: Cmw.getUrl(jsArr),   
					scope: this,   
					success: function(response){
						jsStr = response.responseText;
						//获取模块类  
						this[menuId] = eval(jsStr);  
						//绑定当表单保存成功后，要更新事项菜单为已做的方法
						if(_this.finishBussCallback) tab.finishBussCallback = _this.finishBussCallback;
						if(_this.unFinishBussCallback) tab.unFinishBussCallback = _this.unFinishBussCallback;
						//实例化模块类
						tab = _this.getModelParamsToTab(tab,tabId,menuData);
						model = new this[menuId](tab);
						_this.fireModeListeners(tab,model);
						if(tab["notify"]) tab.notify(tab);
						_loadMask.hide();
					},
					failure:function(response){
						Ext.Msg.show({
							title: "错误信息",
							msg:"加载页面核心文件时发生错误！",
							buttons:Ext.MessageBox.OK,
							icon: Ext.MessageBox.ERROR
						});
						_loadMask.hide();
					}
				 });
			}else{	//用 seaJs 方式加载
				jsArr = jsArr.replace(".js","");
				this.loadUiBysea(jsArr,tab,tabId,node);
			}
		},
		resize : function(whArr){
			this.wh[0] = whArr[0];
			this.wh[1] = whArr[1];
		},
		/**
		 * 用 seaJs 的方式加载UI界面
		 * @param {} url	JS文件地址
		 * @param {} tab	Tab 面板对象
		 * @param {} tabId	Tab Id
		 * @param {} node   Tree Node 对象
		 */
		loadUiBysea : function(url,tab,tabId,node){
			var _this = this;
			Cmw.importPackage(url,function(module) {
				 	model = module.viewUI;
				 	//实例化模块类
					tab = _this.getModelParamsToTab(tab,tabId,node);
				 	var viewUI = model.getMainUI(tab,tab.params);
				 	tab.add(viewUI);
				 	
				 	viewUI.doLayout();
					//TabPannel 改变时，重新初始化
					_this.fireModeListeners(tab,model);
					if(tab["notify"]) tab.notify(tab);
					_this.loadMask.hide();
		  	});
		},
		getModelParamsToTab : function(tab,tabId,node){
			var model_params = this.params;
			model_params["tabId"] = tabId;
			if(node){
				model_params["nodeId"] = node.id;
				/* CURR_NODE_KEY 定义在 constant.js */
				model_params[CURR_NODE_KEY] = node;		
			}
			tab.params = model_params;
			return tab;
		},
		fireModeListeners : function(tab,model){
			var _this = this;
			this.model = model;
			tab.on('resize',function(obj, adjWidth, adjHeight, rawWidth,rawHeight){
				if(model["resize"])model.resize(adjWidth,adjHeight);
			});
			tab.on('destroy',function(_tab){
				if(model["destroy"]){
					model.destroy();
				}
				_this.activiteBussFormId = null;
				if(_this.bussTabPanel) _this.bussTabPanel = null;
			});
		}
	};
});