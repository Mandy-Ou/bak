/**
 * 系统基础平台 UI
 * @author 程明卫
 * @date 2012-07-12
 */
define(function(require, exports) {
	
	exports.SysWinUI = {
		createAppWindow : function(baseConfig){
			var config = {
	                id: self.id,
	                title:self.text,
	                width:900,
	                height:620,
	                iconCls: 'icon-grid',
	                shim:false,
	                animCollapse:false,
	                constrainHeader:true,
	                closeAction : 'close',
	                layout: 'fit'
            	};
            Ext.applyIf(config,baseConfig);
            var winNew = new Ext.ux.cmw.AppTabTreeWin(config);
            return winNew;	
		}
	};
});