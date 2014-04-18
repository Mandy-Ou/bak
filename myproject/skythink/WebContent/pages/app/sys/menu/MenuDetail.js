/**
 * 菜单新增或修改页面
 * @author 程明卫
 * @date 2012-07-12
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		detailPnl : null,	//详情面板
		appWin : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.detailPnl = this.createDetailPnl();
			this.appWin = new Ext.ux.window.AbsDetailWindow({width:600,getParams:this.getParams,appDetailPanel : this.detailPnl,
			optionType : this.optionType, refresh : this.refresh
			});
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
		/**
		 * 销毁组件
		 */
		destroy : function(){
			this.appWin.close();	//关闭并销毁窗口
			this.appWin = null;		//释放当前窗口对象引用
		},
		/**
		 * 获取选中的记录参数
		 */
		getParams : function(){
			var parent = exports.WinEdit.parent;
			var selId = parent.getSelId();
			var params = {menuId:selId};
			return params;
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var _this = exports.WinEdit;
			if(_this.parentCfg.self.refresh)_this.parentCfg.self.refresh(_this.optionType,data);	
		},
		/**
		 * 创建详情面板
		 */
		createDetailPnl : function(){
			var parent = exports.WinEdit.parent;
			var selId = parent.getSelId();
			var htmlArrs = [
				'<tr>',
				'    <th><span>*</span><label col="code">菜单编码</label> </th>',
				'    <td col="code">&nbsp;</td>',
				'    <th col="parentName">父菜单:</th>',
				'    <td col="parentName">&nbsp;</td>',
				'  </tr>',
				'  <tr>',
				'  <tr>',
				'    <th col="name"><span>*</span>菜单名称: </th>',
				'    <td col="name">&nbsp;</td>',
				'    <th col="leaf">是否叶子: </th>',
				'    <td col="leaf">&nbsp;</td>',
				'  </tr>',
				'    <th col="iconCls"> 菜单样式: </th>',
				'    <td col="iconCls">&nbsp;</td>',
				'    <th col="isSystem"><span>*</span>是否系统菜单：</th>',
				'    <td col="isSystem">&nbsp;</td>',
				'  </tr>',
				'  <tr>',
				'    <th col="biconCls">系统大图标:</th>',
				'    <td  colspan="3"><image col="biconCls" /></td>',
				'  </tr>',
				'  <tr>',
				'    <th col="jsArray">URL:</th>',
				'    <td col="jsArray" colspan="3">&nbsp;</td>',
				'  </tr>',
				'  <tr>',
				'    <th col="params">附加参数:</th>',
				'    <td col="params" colspan="3">&nbsp;</td>',
				'  </tr>',
				'  <tr>',
				'    <th col="remark">备注:</th>',
				'    <td colspan="3"><div col="remark"></div></td>',
				'   </tr>'];
			
			var  detailCfgs = [{
				 cmns : 'TWO', 
				 model : 'single',
				 labelWidth : 90,
				 title : '菜单详情',
				 i18n : UI_Menu.appDefault,		//国际化资源对象
				 htmls : htmlArrs,
				 url : './sysMenu_get.action',
				 prevUrl : './sysMenu_prev.action',
				 nextUrl : './sysMenu_next.action',
				 params : {menuId:selId},
				 callback : {sfn:function(jsonData){
				 	jsonData["leaf"] = (jsonData["leaf"]=="false") ? "否" : "是";
				 	jsonData["isSystem"] = (jsonData["isSystem"]=="1") ? "是" : "否";
				 }}
			}];
			var attachLoad = function(params,cmd){
				/* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
				//Cmw.print(params);
				//Cmw.print(cmd);
			}
			var cfg = {width:600,detailCfgs:detailCfgs,attachLoad:attachLoad};
			return new Ext.ux.panel.DetailPanel(cfg);
		}
	};
});