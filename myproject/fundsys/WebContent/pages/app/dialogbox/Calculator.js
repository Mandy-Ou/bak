/**
 * 公式计算器
 * @author 彭登浩
 */
define(function(require, exports) {
	exports.DialogBox = {
		parentCfg : null,
		ButsPanel : null,
		params : null,
		appWin : null,
		appTree : null,
		appGrid : null,
		Calculator :null,
		callbackdata :null,
		btnsPnlId : Ext.id(null, 'btnsPnlId'),/* 按钮面板ID */
		appGridPnlId : Ext.id(null, 'btnsPnlId'),/* 创建字段集表面板ID */
		appTreePnlId : Ext.id(null, 'appTreePnlId'),/* 创建字段集表面板ID */
		setParentCfg:function(parentCfg){
//			this.parentCfg=parentCfg;
//			this.params = parentCfg.params;
//			this.sysid = parentCfg.sysid;
		},
		createAppWindow : function(){
			this.ButsPanel = this.butsPanel();//创建按钮面板
//			this.appGrid = this.createAppGrid();//创建字段集表面板
//			this.appTree = this.createAppTree();//创建关联对象树
//			this.Calculator = this.cmpt();//创建组装方法
			this.appWin = new Ext.ux.window.MyWindow({width:570,height:390,items:[this.treegridpanel]});
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
			this.appWin.optionType = this.optionType;
			this.appWin.show(this.parent.getEl());
		},
		/*--> 这是组装方法<--*/
		cmpt : function(){
			var btnspanel = new Ext.Panel();
			btnspanel.add(this.ButsPanel);
//			var gridpanel = new Ext.Panel();
//			gridpanel.add(this.appGrid);
//			var treepanel = new Ext.Panel();
//			treepanel.add(this.appTree);
//			var calculatorPanel = FormUtil.getLayoutPanel([.5,.5],[treepanel,gridpanel]);
			return btnspanel;
		},
		/*
		 * 按钮面板组件
		 */
		butsPanel :function(){
			var _this = this;
			var btn = [];
			var btnText = [
					'0','1','2','3','4','5',
					'6','7','5','9','+','-',
					'*','/','.','=','&&','||',
					'?',':','<','>','(',')',
					'效验','保存','清除'
			]
			for(var i = 0,len=btnText.length;i<len;i++){
				btn[i] = new Ext.Button({text:btnText[i]});
			}
			var btnPanel = this.getBtnPanel(btn);
			return btnPanel;
		},
		getBtnPanel:function (buttons){
			var panel = new Ext.Panel({ buttonAlign : 'center',buttons : buttons});
			return panel;
		},
		/**
		 * 字段集表格面板组件
		 */
		createAppGrid : function(){
			return appgrid;
		},
		/**
		 * 关联对象树面板
		 */
		createAppTree : function(){
			return apptree;
		},
		
		hide: function(){
			this.appWin.hide();
		},
		setValue: function(){
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