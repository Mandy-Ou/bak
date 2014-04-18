/**
 * 凭证模板选择弹出窗口
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
		createAppWindow : function(){
			this.appGrid = this.createAppGrid();
			this.appWin = new Ext.ux.window.MyWindow({width:500,height:350,items:this.appGrid});
		},
		createAppGrid : function(){
			var _this = this;
			var structure_1 = [{header: '凭证模板编号',name: 'code',width: 100},
								{header: '凭证模板名称', name: 'name',width: 180},
								{header: '分录方向',name: 'entry',renderer: Fs_Render_dataSource.vt_entryRender},
								{header: '分录最大条数',name: 'maxcount'},
								{header: '批量业务策略',name: 'tactics',width:120,
								 renderer: Fs_Render_dataSource.vt_tacticsRender
								}];
			var appgrid = new Ext.ux.grid.AppGrid({
			    title: '凭证模板列表',
			    structure: structure_1,
			    tbar : this.getToolBar(),
			    url: './fsVoucherTemp_list.action?isenabled=1',
			    needPage: true,
			    isLoad: false,
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
				text : '模板名称'
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