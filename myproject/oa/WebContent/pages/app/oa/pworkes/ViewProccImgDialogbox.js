/**
 * 查看流程设计图
 * @author 程明卫
 * @date 2013-12-27
 */
define(function(require, exports) {
	exports.DialogBox = {
		parentCfg : null,
		params : null,
		appWin : null,
		processDivId : Ext.id(null,'viewProccDesinger'),
		processImageId : Ext.id(null,'viewProccDesingerImg'),
		createAppWindow : function(){
			var html = '<div id="'+this.processDivId+'" style="overflow:auto;"><image id="'+this.processImageId+'"  style="position:relative; left:0px; top:0px;"></div>';
			this.appWin = new Ext.ux.window.MyWindow({width:700,height:500,html:html});
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
			this.reload();	
		},
		reload : function(){
			var winTitle = this.params.winTitle;
			this.appWin.setTitle("当前流程图【"+winTitle+"】");
			var pdid = this.params.pdid;
			if(!pdid){
				ExtUtil.error({msg:'参数 params 中没有 pdid 属性值!'});
				return;
			}
			var url = "./sysBussNode_processImage.action?pdid="+pdid;
			var imgEle = Ext.get(this.processImageId);
			imgEle.set({src:url});
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