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
		custType : null,
		appGrid : null,
		isCheck : true,	/*是否显示复选框*/
		createAppWindow : function(){
			this.appGrid = this.createAppGrid();
			this.appWin = new Ext.ux.window.MyWindow({width:500,height:350,items:this.appGrid});
		},
		createAppGrid : function(){
			var _this = this;
			var structure_1 = [{
				    header: 'id',
				    hidden : true,
				    name: 'id'
				},{
				    header: '客户名称',
				    name: 'name'
				},
				{
				    header: '性别',
				    name: 'sex',
				    renderer :Render_dataSource.sexRender
				},
				{
				    header: '证件类型',
				    name: 'cardType',
				     renderer :function(val){
				     	return Render_dataSource.gvlistRender('100002',val)	
				     }
				},
				{
				    header: '证件号码',
				    name: 'cardNum'
				},
				{
				    header: '出生日期',
				    name: 'birthday'
				},
				{
				    header: '婚姻状况',
				    name: 'maristal',
				     renderer :function(val){
				     	return Render_dataSource.gvlistRender('100003',val)	
				     }
				},
				{
				    header: '手机',
				    name: 'phone'
				},
				{
				    header: '联系电话',
				    name: 'contactTel'
				},
				{
				    header: '联系人',
				    name: 'contactor'
				}
				];
				var appgrid = new Ext.ux.grid.AppGrid({
					tbar : _this.getToolBar(),
				    structure: structure_1,
				    url: './crmGuaCustomer_guaContlist.action',
				    needPage: false,
				    selectType : "check",
				    isLoad: true,
				    height : 200,
				    keyField: 'id'
				});
				
				return appgrid;
		},
		getToolBar : function(){
			var _this = this;
			var toolBar = null;
			var barItems = [{
				type : 'label',
				text : '担保人名称'
			},{
				type : 'search',
				name : 'name',
				onTrigger2Click : function(){
					var params = toolBar.getValues();
					if(!params) params = {};
					Ext.apply(params,_this.params);
					EventManager.query(_this.appGrid,params);
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
			var ids = this.appGrid.getSelIds();
			if(!ids){
				return;
			}
			var record = this.appGrid.getSelRows();
			var data = {guarantorIds:ids,record:record};
			this.parentCfg.callback(data);
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
				this.appWin.show(_parentCfg.parent.appWin.getEl());
			}else{
				this.appWin.show();
			}
			this.appGrid.reload(this.params);
			
		},
		setParentCfg : function(_parentCfg){
			this.parentCfg = _parentCfg;
			this.custType = _parentCfg.params.custType;
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