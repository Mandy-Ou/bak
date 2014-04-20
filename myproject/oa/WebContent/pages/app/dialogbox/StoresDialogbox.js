/**
 * 可选门店信息选择弹出窗口
 * 牛将军 --> 商品管理 --> 添加商品用到
 * @author 程明卫
 * @date 2013-04-01
 */
define(function(require, exports) {
	exports.DialogBox = {
		parentCfg : null,
		params : null,
		appWin : null,
		queryFrm  : null,
		appGrid : null,
		isCheck : false,	/*是否显示复选框*/
		createAppWindow : function(){
			this.queryFrm = this.getQueryFrm();
			this.appGrid = this.createAppGrid();
			this.queryFrm.add(this.appGrid);
			this.appWin = new Ext.ux.window.MyWindow({width:720,height:550,items:[this.queryFrm]});
		},
		/**
		 * 获取查询Form 表单
		 */
		getQueryFrm : function(){
	 		var txt_dmbh = FormUtil.getTxtField({fieldLabel : '门店编号',name:'code'});
	 		var txt_dmqy = FormUtil.getTxtField({fieldLabel : '店面区域',name:'areaName'});
	 		var txt_mdzj = FormUtil.getTxtField({fieldLabel : '门店总监',name:'directorName'});
	 		var txt_qyjl = FormUtil.getTxtField({fieldLabel : '区域经理',name:'mgrName'});
	 		var txt_dmmc = FormUtil.getTxtField({fieldLabel : '门店名称',name:'name',width:250});
	 		
			var layout_fields = [{cmns:FormUtil.CMN_TWO,fields:[txt_dmbh,txt_dmqy,txt_mdzj,txt_qyjl,txt_dmmc]}]
	
			var queryFrm = FormUtil.createLayoutFrm({title : '查询条件'},layout_fields);
			return queryFrm;
		},
		createAppGrid : function(){
			var _this = this;
			
			var structure_1 = [{
			    header: 'ID',
			    hidden:true,
			    name: 'id'
			},
			{
			    header: '门店编号',
			    name: 'code'
			},
			{
			    header: '店面名称',
			    name: 'name'
			},
			{
			    header: '门店区域',
			    name: 'areaName'
			},
			{
			    header: '店长',
			    name: 'dzName'
			},
			{
			    header: '区域经理',
			    name: 'mgrName'
			},
			{
			    header: '门店总监',
			    name: 'directorName'
			}];
			var appTbar = this.createToolBar();
			this.appTbar = appTbar;
			var appgrid = new Ext.ux.grid.AppGrid({
			    title: '门店信息列表',
			    tbar : appTbar,
			    structure: structure_1,
			    url: './oaStores_list.action?isenabled=1',
			    needPage: true,
			    isLoad: false,
			    height:406,
			    autoScroll : true,
			    selectType : "check",
			    keyField: 'id'
			});
			appgrid.addListener('dblclick',function(e){
				_this.setValue();
			});
			return appgrid;
		},
		createToolBar : function(){
			var _this = this;
			var toolBar = null;
			var barItems = [{
				text : Btn_Cfgs.QUERY_BTN_TXT,
				iconCls:'page_query',
				tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
				handler : function(){
					var params = _this.queryFrm.getValues();
					if(!params) params = {};
					Ext.apply(params,_this.params);
					EventManager.query(_this.appGrid,params);
				}
			},{
				text : Btn_Cfgs.RESET_BTN_TXT,
				iconCls:'page_reset',
				tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
				handler : function(){
					_this.queryFrm.reset();
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
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems});
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
			var records = this.appGrid.getSelRows();
			this.parentCfg.callback(ids,records);
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