/**
 * 流程审批主页面 ---> 审批意见列表面板
 * @author 程明卫
 * @date 2012-12-26
 */
define(function(require, exports) {
	exports.moduleUI = {
		appPanel : null,
		auditFormParentPanel : null,	/*放审批表单的父面板*/
		auditRecordsPanel : null,	/*放审批历史记录的父面板*/
		auditFormMod : null,	/*审批表单模块*/
		auditRecordsMod : null,/*审批记录模块*/
		params : null,
		callback : null,/*回调函数*/
		/**
		 * 获取业务模块
		 * @param params 由菜单点击所传过来的参数
		 * 	{tabId : tab 对象ID,apptabtreewinId : 系统程序窗口ID,sysid : 系统ID} 
		 */
		getModule : function(params){
			if(!this.appPanel){
				this.setParams(params);
				this.createCmpts();
				this.buildCmpts();
			}
			return this.appPanel;
		},	
		/**
		 * 设置参数
		 */
		setParams : function(params) {
			this.params = params;
		},
		/**
		 * 显示面板并加载数据
		 */
		show : function(params){
			this.setParams(params);
			this.loadData();
		},
		/**
		 * 加载客户信息数据
		 */
		loadData : function() {
			if(this.auditFormMod) this.auditFormMod.show(this.params);
			if(this.auditRecordsMod) this.auditRecordsMod.show(this.params);
		},
		/**
		 * 显示审批面板
		 */
		showAuditForm : function(_params){
			if(this.auditFormMod) this.auditFormMod.show(_params);
		},
		/**
		 * 创建组件
		 */
		createCmpts : function(){
			var height = CLIENTHEIGHT - 190;
			this.auditFormParentPanel = new Ext.Panel({autoScroll:true,autoWidth:true,border: false});
			this.auditRecordsPanel =  new Ext.Panel({autoScroll:true,border: false});
			this.appPanel = new Ext.Panel({border:false,height:height,autoScroll:true,items:[this.auditFormParentPanel,this.auditRecordsPanel]});
		},
		/**
		 * 组装控件
		 */
		buildCmpts : function(){
		 	var _params = this.params;
		 	var _this = this;
		 	if(!this.auditFormMod){
				_params.parent = this.auditFormParentPanel;
				Cmw.importPackage('/pages/app/workflow/variety/mainUIs/AuditFormMod',function(module) {
				 	_this.auditFormMod = module.moduleUI;
				 	_this.auditFormMod.callback = function(data){
						_this.refresh(data);
					};
				 	var cmpt = _this.auditFormMod.getModule(_params);
				 	_this.auditFormParentPanel.add(cmpt);
				 	_this.auditFormParentPanel.doLayout();
		  		});
			}
			if(!this.auditRecordsMod){
				_params.parent = this.auditRecordsPanel;
				Cmw.importPackage('/pages/app/workflow/variety/mainUIs/AuditRecordsMod',function(module) {
				 	_this.auditRecordsMod = module.moduleUI;
				 	var cmpt = _this.auditRecordsMod.getModule(_params);
				 	_this.auditRecordsPanel.add(cmpt);
				 	_this.auditRecordsPanel.doLayout();
		  		});
			}
		},
		refresh : function(data){
			if(!this.params.procId){
				this.params.procId = data.procId;
			}
			var el = this.auditRecordsPanel.el;
			//alert("height="+el.getComputedHeight() +",width="+el.getComputedWidth() );
			/*刷新审核记录面板*/
			this.auditRecordsMod.show(this.params);
			if(this.callback){
				this.callback(data);
			}
		},
		/**
		 * 设置参数
		 */
		setParams : function(params){
			this.params = params;
		},
		resize : function(adjWidth,adjHeight){
			this.appPanel.setWidth(adjWidth);
			this.appPanel.setHeight(adjHeight);
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function(){
			if(null != this.appPanel){
				 this.appPanel.destroy();
				 this.appPanel = null;
			}
			
			if(null != this.auditFormMod){
				 this.auditFormMod.destroy();
				 this.auditFormMod = null;
			}
			
			if(null != this.auditRecordsMod){
				 this.auditRecordsMod.destroy();
				 this.auditRecordsMod = null;
			}
			
		}
	};
});