define(function(require, exports) {
	exports.WinEdit = {
		parent : null,
		sysId : null,
		tbar : null,
		appWin : null,
		appMainPnl : null,
		params : null,
		setParams : function(parentCfg){
			this.params  = 	parentCfg.params;
			this.parent = parentCfg.parent;
			this.sysId = parentCfg.params.sysId;
			
		},
		createWin : function(){
			this.tbar = this.createTbar();
			this.appMainPnl = this.createAppMainPnl();
			this.appWin = new Ext.ux.window.MyWindow({
				
			});
		},
		
		show : function(_parentCfg){
			if(_parentCfg) this.setParams(_parentCfg);
			if(!this.appWin){
				this.createWin();
			}
			this.appWin.show();
		},
		/**
		 * 创建工具栏
		 */
		createTbar : function(){
			
		},
		/**
		 * 创建主面板
		 */
		createAppMainPnl : function(){
			
		}
	}
});