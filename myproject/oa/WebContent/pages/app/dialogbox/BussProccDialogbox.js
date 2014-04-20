/**
 * 子业务流程选择弹出窗口
 * 系统基础平台 -> 系统功能列表 -> 流程分类 用到
 * @author 程明卫
 * @date 2013-12-24
 */
define(function(require, exports) {
	exports.DialogBox = {
		parentCfg : null,
		params : null,
		appWin : null,
		appGrid : null,
		isCheck : true,	/*是否显示复选框*/
		createAppWindow : function(){
			this.appGrid = this.createAppGrid();
			this.appWin = new Ext.ux.window.MyWindow({width:500,height:350,items:this.appGrid});
		},
		createAppGrid : function(){
			var _this = this;
			var structure_1 = [	{
			    header: '可用标识',
			    name: 'isenabled',
			    width: 60,
			    renderer: Render_dataSource.isenabledRender
			},{
			    header: '流程编号',
			    name: 'code'
			},
			{
			    header: '流程名称',
			    name: 'name',
			    width: 115
			},
			{
			    header: '是否配置流程',
			    name: 'pdid',
			    width: 90,
			    renderer: function(val) {
			        return !val ? '否' : '是';
			    }
			},
			{
			    header: '适用机构',
			    name: 'useorg',
			    width: 80,
			  	renderer: function(val) {
			        switch (val) {
			        case '0':
			            val = '所有公司';
			            break;
			        case '1':
			            val = '指定公司';
			            break;
			        }
			        return val;
				}
			},
			{
			    header: '指定公司名称',
			    name: 'companyNames',
			    width: 125
			},
			{
			    header: '流程定义ID',
			    name: 'pdid',
			    hidden: true
			}];
			this.toolBar = this.getToolBar();
			var appgrid = new Ext.ux.grid.AppGrid({
			    title: '业务流程列表',
			    tbar : this.toolBar,
			    structure: structure_1,
			    url: './sysBussProcc_list.action?isenabled=1',
			    needPage: false,
			    isLoad: false,
			    selectType : this.isCheck ? 'check' : '',
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
				text : '流程名称'
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
			var ids = this.isCheck ? this.appGrid.getSelIds() : this.appGrid.getSelId();
			if(!ids){
				return;
			}
			var records = this.appGrid.getSelRows();
			this.parentCfg.callback(ids,records);
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