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
		remarkContentId : Ext.id(null,"remarkContentId"),
		createAppWindow : function(){
			var html = '<div id="'+this.remarkContentId+'" style="overflow:auto;background-color:white;"></div>';
			this.appWin = new Ext.ux.window.MyWindow({width:800,height:550,autoScroll : true,maximizable:true,modal:false,html:html});
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
			this.appWin.setTitle("当前流程说明【"+winTitle+"】");
			var bussProccId = this.params.bussProccId;
			var html = "<h1 style='color:red;text-align:center;'>无"+winTitle+"流程的相关说明...</h1>";
			var _this = this;
			
			EventManager.get('./sysBussProcc_getClause.action',{params:{id:bussProccId},sfn:function(json_data){
			 	if(json_data && json_data.content){
			 		html = json_data.content;
			 	}
			 	var remarkContentEle = Ext.get(_this.remarkContentId);
			 	if(remarkContentEle) remarkContentEle.update(html);
			},ffn:function(json_data){
				var remarkContentEle = Ext.get(_this.remarkContentId);
			 	if(remarkContentEle) remarkContentEle.update(html);
			}});
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