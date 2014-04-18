/**
 * 客户选择弹出窗口
 * 小额贷款系统 --> 客户资料第三方担保人---> 选择客户 用到
 * @author 彭登浩
 * @date 2013-11-09
 */
define(function(require, exports) {
	exports.DialogBox = {
		parentCfg : null,
		params : null,
		appWin : null,
		appGrid : null,
		toolBar : null,
		createAppWindow : function(){
			this.appGrid = this.createAppGrid();
			this.appWin = new Ext.ux.window.MyWindow({width:700,height:400,items:this.appGrid});
		},
		/**
		 * 创建客户表格
		 */
		createAppGrid : function(){
			var _this = this;
			var structure_1 = [{
			    header: '客户编号',
			    name: 'code',
			    width : 150
			},
			{
			    header: '客户类型',
			    name: 'custType',
			    renderer: function(val) {
			    	 return	Render_dataSource.custTypeRender(val);
			    }
			},
			{
			    header: '客户名称',
			    name: 'name'
			},
			{
			    header: '联系人',
			    width:60,
			    name: 'contactor'
			},
			{
			    header: '联系人手机',
			    name: 'phone'
			},
			{
			    header: '联系电话',
			    name: 'contactTel'
			}
			];
			var appgrid = new Ext.ux.grid.AppGrid({
			    title: '可选客户列表',
			    structure: structure_1,
			    url: './crmCustBase_list.action',
			    needPage: true,
			    isLoad: false,
			    tbar : this.getToolBar(),
			    keyField: 'bid'
			});
			appgrid.addListener('dblclick',function(e){
				_this.setValue();
			});
			return appgrid;
		},
		getToolBar : function(){
			var _this = this;
			var barItems = [{
				header : '客户信息ID',
				name : 'bid',
				hidden : true
			},{
				type : 'label',
				text : '客户类型'
			},{
				type : 'lcbo',
				name : 'custType',
				data:Lcbo_dataSource.getAllDs(Lcbo_dataSource.custType_datas),
				width:100
			},{
				type : 'label',
				text : '客户名称'
			},{
				type : 'txt',
				name : 'name'
			},{
				text : Btn_Cfgs.QUERY_BTN_TXT,
				iconCls:'page_query',
				tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
				handler : function(){
					var params = _this.toolBar.getValues();
					if(!params.custType) params.custType = 0;
					EventManager.query(_this.appGrid,params);
				}
			},{
				text : Btn_Cfgs.RESET_BTN_TXT,
				iconCls:'page_reset',
				tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
				handler : function(){
					_this.toolBar.resets();
				}
			},{text:Btn_Cfgs.CONFIRM_BTN_TXT,iconCls:Btn_Cfgs.CONFIRM_CLS,handler:function(){
				_this.setValue();
			}},{
				text : Btn_Cfgs.CLOSE_BTN_TXT,
				iconCls:Btn_Cfgs.CLOSE_CLS,
				tooltip:Btn_Cfgs.CLOSE_TIP_BTN_TXT,
				handler : function(){
					_this.appWin.hide();
				}
			}];
			this.toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
			return this.toolBar;
		},
		/**
		 * 赋值
		 */
		setValue : function(){
			var id   = this.appGrid.getSelId();
			if(!id) return;
			var data = this.appGrid.getCmnVals("custType");
			this.parentCfg.callback(id,data);
			this.appWin.hide();
		},
		/**
		 * 显示弹出窗口
		 * @param _parentCfg 弹出之前传入的参数
		 */
		show : function(_parentCfg){
			if(_parentCfg) this.setParentCfg(_parentCfg);
			if(!this.appWin){
				this.createAppWindow();
			}
			if(_parentCfg.parent){
				this.appWin.show(_parentCfg.parent.getEl());
			}else{
				this.appWin.show();
			}
			
			var params = this.toolBar.getValues();
			if(!params.custType) params = {custType : 0};
			this.appGrid.reload(params);
		},
		setParentCfg : function(_parentCfg){
			this.parentCfg = _parentCfg;
		},
		/**
		 * 销毁组件
		 */
		destroy : function(){
			if(null != this.appWin){
				this.appWin.close();	//关闭并销毁窗口
				this.appWin = null;		//释放当前窗口对象引用
			}
		}
	}
})