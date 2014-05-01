/**
 * 个人客户选择弹出窗口
 * 小额贷款系统 --> 公司账户 --> 财务财号映射 用到
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
			    header: '科目编号',
			    name: 'code'
			},
			{
			    header: '账户名称',
			    name: 'name'
			},
			{
			    header: '银行账号',
			    name: 'actNumber',
			    width: 120
			},
			{
			    header: '开户行',
			    name: 'bankName',
			    width: 150
			},
			{
			    header: '结算方式ID',
			    name: 'refId',
			    width: 100
			}];
			var appgrid = new Ext.ux.grid.AppGrid({
			    title: '财务系统账号列表',
			    structure: structure_1,
			    tbar : this.getToolBar(),
			    url: '	./fsBankAccount_list.action',
			    needPage: true,
			    isLoad: false,
			    selectType : this.isCheck ? 'true' : 'false',
			    keyField: 'id'
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
				text : '开户行'
			},{
				type : 'txt',
				name : 'bankName'
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