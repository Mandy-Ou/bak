/**
 * 用户数据访问权限设置
 * @author 程明卫
 * @date 2012-12-26
 */
define(function(require, exports) {
	exports.viewUI = {
		tab : null,
		params : null,
		appMainPanelHeight : 500,
		appMainPanel : null,
		topPanel : null,/*客户贷款基本信息面板*/
		auditPanel : null,/*审批面板*/
		bussMakePanel : null,/*业务办理面板*/
		processImgPanel : null, /*流程图面板*/
		tabPanel : null,/* TabPanel 面板*/	
		applyDetailMod : null,	/*客户贷款基本信息模块*/
		bussMgrMod : null,	/*业务办理模块*/
		auditMgrMod : null,	/*审批面板模块*/
		processImgMod : null,	/*流程图模块*/
		prevBussProccCode : null,/*前一次的URL*/
		adjHeight : null,
		adjWidth : null,
		/**
		 * 获取业务流程审批主面板
		 * @param tab Tab 面板对象
		 * @param params 由菜单点击所传过来的参数
		 * 	{tabId : tab 对象ID,apptabtreewinId : 系统程序窗口ID,sysid : 系统ID} 
		 */
		getMainUI : function(tab,params){
			var isNew = false;
			this.setParams(tab,params);
			if(!this.appMainPanel){
				this.createCmpts();
				this.buildCmpts();
				isNew = true;
			}
			var _this = this;
			tab.cached = 0;/*指示Tab 不缓存*/
			tab.notify = function(_tab){
				if(!_this.applyDetailMod) return;
				if(!isNew)_this.applyDetailMod.show(_this.params);
				isNew = false;
			}
			return this.appMainPanel;
		},
		/**
		 * 设置参数
		 */
		setParams : function(tab,params){
			this.tab = tab;
			this.params = params;
			this.params.bussProccCode = 'B9999';
		},
		/**
		 * 创建组件
		 */
		createCmpts : function(){
			var height = CLIENTHEIGHT - 170;
			this.topPanel = new Ext.Panel({border:false,id : ApplyActivePnlId.topActivePnlId});
			this.auditPanel = new Ext.Panel({title :'审批信息',height:height,border:false});
			this.bussMakePanel = new Ext.Panel({title :'业务办理',hidden:true,height:height,border:false,autoScroll:true
				});
			this.processImgPanel = new Ext.Panel({title :'流程预览',height:height,autoScroll:true,border:false});
			this.tabPanel = new Ext.TabPanel({
				autoHeight : true,
				border:false,activeItem:0,autoScroll:true,
				items:[this.auditPanel,this.bussMakePanel,this.processImgPanel]
			});
			this.tabPanel.setActiveTab(0);
			var _this = this;
			this.tabPanel.addListener('render',function(tabPnl){
				tabPnl.hideTabStripItem(_this.bussMakePanel);
				var tabHeight = _this.tab.getHeight()-Ext.get(ApplyActivePnlId.topActivePnlId).getHeight();
				tabPnl.setHeight(tabHeight);
				tabPnl.doLayout();
			});
			this.appMainPanel = new Ext.Panel({border:false,items:[this.topPanel,this.tabPanel]});
		},
		/**
		 * 组装控件
		 */
		buildCmpts : function(){
			var _this = this;
			var _params = this.params;
			if(!this.applyDetailMod){
				_params.parent = this.topPanel;
				var bussProccCode = this.params.bussProccCode;
				if(!bussProccCode){
					ExtUtil.error({msg:'在参数 params 中找不到 bussProccCode 的值!'});
					return;
				}
				var url =  Flow_CustForm_Url[bussProccCode];
				if(!url){
					ExtUtil.error({msg:'在常量文件 constant.js 中的 Flow_CustForm_Url 中没有配置 '+bussProccCode+' 所对应的路径!'});
					return;
				}
				url = url.replace(".js","");
				Cmw.importPackage(url,function(module) {
				 	_this.applyDetailMod = module.moduleUI;
				 	_this.applyDetailMod.callback = function(data){
						_this.refresh(data);
					};
				 	var cmpt = _this.applyDetailMod.getModule(_params);
				 	_this.topPanel.add(cmpt);
				 	_this.topPanel.doLayout();
		  		});
			}
		},
		refresh : function(data){
			this.resize();
			this.copyParams(data);
			this.loadBussMgrModInfo();
			this.loadAuditMgrModInfo();
			this.loadProcessImgModInfo();
			
		},
		/**
		 * 加载审核模块信息
		 */
		loadAuditMgrModInfo : function(){
			if(!this.auditMgrMod){
				var _this = this;
				var _params = this.params 
				_params.parent = this.auditPanel;
				Cmw.importPackage('pages/app/workflow/variety/mainUIs/AuditListMod',function(module) {
				 	_this.auditMgrMod = module.moduleUI;
				 	_this.auditMgrMod.callback = function(data){
				 		if(!_this.params.procId){
				 			 _this.params.procId = data.procId;
				 		}
				 		_this.loadProcessImgModInfo();
					};
				 	var cmpt = _this.auditMgrMod.getModule(_params);
				 	_this.auditPanel.add(cmpt);
				 	_this.auditPanel.doLayout();
		  		});
			}else{
				this.auditMgrMod.show(this.params);
			}
		},
		/**
		 * 加载必须/选做业务事项菜单信息
		 */
		loadBussMgrModInfo : function(){
			if(!this.params.bussFormDatas) return;
			if(!this.bussMgrMod){
				var _this = this;
				var _params = this.params
				_params.auditTabPanel = this.auditPanel;
				_params.bussMakeTabPanel = this.bussMakePanel;
				var callback = function(_params){/*当所有必做事项处理完成后，通过回调函数显示审批面板*/
					_this.tabPanel.setActiveTab(0);
					if(null != _this.auditMgrMod) _this.auditMgrMod.showAuditForm(_params);
				};
				Cmw.importPackage('pages/app/workflow/variety/mainUIs/BussMenuListMod',function(module) {
				 	_this.bussMgrMod = module.moduleUI;
				 	_this.bussMgrMod.callback = callback;
				 	_this.bussMgrMod.refresh(_params);
		  		});
			}else{
				this.bussMgrMod.show(this.params);
			}
		},
		/**
		 * 加载流程图信息
		 */
		loadProcessImgModInfo : function(){
			if(!this.processImgMod){
				var _this = this;
				var _params = this.params
				_params.parent = this.processImgPanel;
				Cmw.importPackage('pages/app/workflow/variety/mainUIs/ProcessImgMod',function(module) {
				 	_this.processImgMod = module.moduleUI;
				 	var cmpt = _this.processImgMod.getModule(_params);
				 	_this.processImgPanel.add(cmpt);
				 	_this.processImgPanel.doLayout();
		  		});
			}else{
				this.processImgMod.show(this.params);
			}
		},
		/**
		 * 将项目信息参数复制到 params
		 */
		copyParams : function(data){
			var custType = data.custType;
			var customerId = data.customerId;
			this.params.menuLabelId = Ext.id(null,'bussMakeMenuId');/*菜单事项总提示ID*/
			this.params.custType = custType;
			this.params.customerId = customerId;
			this.params.procId = data.procId;
			this.params.breed = data.breed;
			this.params.pdid = data.pdid;
			this.params.manager = data.manager;
			this.params.hideForm = false;/*是否隐藏审批表单, false : 显示，true:隐藏*/
			var bussFormDatas =data.bussFormDatas;
			this.params.bussFormDatas = bussFormDatas;
			var hideForm = this.isHideAuditForm(bussFormDatas);
			this.params.hideForm = hideForm;
		},
		isHideAuditForm : function(bussFormDatas){
			if(!bussFormDatas) return false;
			if(Ext.isString(bussFormDatas)) bussFormDatas = Ext.decode(bussFormDatas);
			var mustForms = bussFormDatas.mustForms;
			var flag = false;
			if(mustForms && mustForms.length>0){
				for(var i=0,count=mustForms.length; i<count; i++){
					var mustForm = mustForms[i];
					var finish = mustForm.finish;
					if(!finish){/*如果有没完成的业务，则标识审批表单为隐藏*/
						flag = true;
						break;
					}
				}
			}
			return flag;
		},
		resize : function(adjWidth,adjHeight){
			var height = CLIENTHEIGHT - 170;
			if(!adjWidth && !adjHeight){
				adjWidth = this.tab.getWidth();
				var adjHeight = this.tab.getHeight();
			}
			this.appMainPanel.setWidth(adjWidth);
			this.appMainPanel.setHeight(adjHeight);
			if(!this.applyDetailMod) return;
			var detailHeight = this.applyDetailMod.getHeight();
			adjHeight = adjHeight - detailHeight;
			if(adjHeight <= 0) adjHeight = height;
			this.adjHeight = adjHeight;
			this.params.childWidth = adjWidth;
			this.params.childHeight = adjHeight;/* childWidth childHeight*/
			if(this.auditPanel){
				this.auditPanel.setWidth(adjWidth);
				this.auditPanel.setHeight(adjHeight);
			}
			if(this.processImgPanel){
				this.processImgPanel.setWidth(adjWidth);
				this.processImgPanel.setHeight(adjHeight);
			}
			if(this.processImgMod){
				this.processImgMod.resize(adjWidth,adjHeight);
			}
			if(this.auditMgrMod){
				this.auditMgrMod.resize(adjWidth,adjHeight);
			}
			if(this.bussMgrMod){
				this.bussMgrMod.resize(adjWidth,adjHeight);
			}
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function(){
			if(null != this.topPanel){
				 this.topPanel.destroy();
				 this.topPanel = null;
			}
			if(null != this.auditPanel){
				 this.auditPanel.destroy();
				 this.auditPanel = null;
			}
			if(null != this.bussMakePanel){
				 this.bussMakePanel.destroy();
				 this.bussMakePanel = null;
			}
			if(null != this.processImgPanel){
				 this.processImgPanel.destroy();
				 this.processImgPanel = null;
			}
			if(null != this.tabPanel){
				 this.tabPanel.destroy();
				 this.tabPanel = null;
			}
			if(null != this.applyDetailMod){
				 this.applyDetailMod.destroy();
				 this.applyDetailMod = null;
			}
			if(null != this.auditMgrMod){
				 this.auditMgrMod.destroy();
				 this.auditMgrMod = null;
			}
			if(null != this.processImgMod){
				 this.processImgMod.destroy();
				 this.processImgMod = null;
			}
			if(null != this.bussMgrMod){
				 this.bussMgrMod.destroy();
				 this.bussMgrMod = null;
			}
			if(null != this.appMainPanel){
				 this.appMainPanel.destroy();
				 this.appMainPanel = null;
			}
		}
	}
});