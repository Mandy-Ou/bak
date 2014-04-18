/**
 * 个人客户选择弹出窗口
 * 小额贷款系统 --> 业务办理 --> 贷款申请 用到
 * @author 程明卫
 * @date 2012-12-07
 */
define(function(require, exports) {
	exports.DialogBox = {
		parentCfg : null,
		params : null,
		appWin : null,
		appGrid : null,
		isCheck : false,	/*是否显示复选框*/
		createAppWindow : function(){
			this.appGrid = this.createAppGrid();
			this.appWin = new Ext.ux.window.MyWindow({width:500,height:350,items:this.appGrid});
		},
		createAppGrid : function(){
			var _this = this;
			var structure_1 = [{
				header :'baseId',
				hidden : true,
				 width: 120
			},
			{
			    header: '客户编号',
			    name: 'code',
			    width: 120
			},
			{
			    header: '客户流水号',
			    name: 'serialNum',
			    width: 80,
			    hidden : true,
			    hideable : true
			},
			{
			    header: '姓名',
			    name: 'name'
			},
			{
			    header: '性别',
			    name: 'sex',
			    width: 60,
			    renderer: function(val) {
			       return Render_dataSource.sexRender(val);
			    }
			},
			{
			    header: '证件类型',
			    name: 'cardType',
			    width: 80,
			    renderer: function(val) {
			       return Render_dataSource.gvlistRender('100002',val);
			    }
			},
			{
			    header: '婚姻状况',
			    name: 'maristal',
			    width: 80,
			    renderer: function(val) {
			    	return Render_dataSource.gvlistRender('100003',val);
			    }
			},
			{
			    header: '手机',
			    name: 'phone',
			    width: 90
			},
			{
			    header: '电话',
			    name: 'contactTel',
			    width: 90
			},
			{
			    header: '出生日期',
			    name: 'birthday',
			    width: 90
			},
			{
			    header: '登记时间',
			    name: 'registerTime',
			    width: 90
			},
			{
			    header: '登记人',
			    name: 'regman',
			    width: 90
			}];
			var appgrid = new Ext.ux.grid.AppGrid({
			    title: '个人客户列表',
			    structure: structure_1,
			    tbar : this.getToolBar(),
			    url: '	./dgDialogbox_custlist.action',
			    needPage: true,
			    isLoad: false,
			    selectType : this.isCheck ? 'true' : 'false',
			    keyField: 'customerId'
			});
			appgrid.addListener('dblclick',function(e){
				_this.setValue();
			});
			return appgrid;
		},
		getToolBar : function(){
			var _this = this;
			var toolBar = null;
			var barItems = [{
				type : 'label',
				text : '姓名'
			},{
				type : 'txt',
				name : 'name'
			},{
				text : Btn_Cfgs.QUERY_BTN_TXT,
				iconCls:'page_query',
				tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
				handler : function(){
					var params = toolBar.getValues();
					if(!params) params = {};
					Ext.apply(params,_this.params);
					EventManager.query(_this.appGrid,params);
				}
			},{
				text : Btn_Cfgs.RESET_BTN_TXT,
				iconCls:'page_reset',
				tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
				handler : function(){
					toolBar.resets();
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
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
			return toolBar;
		},
		/**
		 * 赋值
		 */
		setValue : function(){
			var id = this.appGrid.getSelId();
			if(!id){
				return;
			}
			var record = this.appGrid.getSelRow();
			this.parentCfg.callback(id,record);
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
				this.appWin.show(_parentCfg.parent);
			}else{
				this.appWin.show();
			}
			this.appGrid.reload(this.params);
		},
		setParentCfg : function(_parentCfg){
			this.parentCfg = _parentCfg;
			this.params = this.parentCfg.params;
			if(this.parentCfg.isCheck){
				this.isCheck = this.parentCfg.isCheck;
			}
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
	};
});