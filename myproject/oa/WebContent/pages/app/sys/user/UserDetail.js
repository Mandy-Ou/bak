/**
 * 菜单新增或修改页面
 * @author 程明卫
 * @date 2012-07-12
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		detailPnl : null,	//详情面板
		appWin : null,
		selId : null,
		parent : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.detailPnl = this.createDetailPnl();
			this.appWin = new Ext.ux.window.AbsDetailWindow({width:660,getParams:this.getParams,appDetailPanel : this.detailPnl,
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
			this.selId = parent.getSelId();
			var params = {userId:this.selId};
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
			var selId = this.selId;
			var htmlArrs = [
				'<tr>',
				'    <th width="140"><span>*</span><label col="empName">姓名:</label></th>',
				'    <td width="147" col="empName">&nbsp;</td>',
				'    <th width="152"><span>*</span><label col="userName">账号:</label></th>',
				'   <td width="158" col="userName"><label >&nbsp;</td>',
				'    <th width="130"><span>*</span><label col="passWord">密码:</label></th>',
				'   <td width="186" col="passWord">&nbsp;</td>',
				' </tr>',
				' <tr>',
				'    <th width="140"><span>*</span><label col="sex">性别:</label></th>',
				'    <td width="147" col="sex">&nbsp;</td>',
				'    <th col="postName">职位:</th>',
				'    <td col="postName">&nbsp;</td>',
				'    <th col="leaderName">直属领导:</th>',
				'    <td col="leaderName">&nbsp;</td>',
				'  </tr>',
				'  <tr>',
				'   <th col="phone">手机:</th>',
				'   <td col="phone">&nbsp;</td>',
				'    <th col="tel">联系电话:</th>',
				'    <td col="tel">&nbsp;</td>',
				'    <th col="email">邮箱:</th>',
				'   <td col="email">&nbsp;</td>',
				' </tr>',
				'  <tr>',
				'    <th col="dataLevel">数据访问级别:</th>',
				'   <td col="dataLevel">&nbsp;</td>',
				'   <th col="accessIds">自定义列表:</th>',
				'   <td colspan="3" col="accessIds">&nbsp;</td>',
				'  </tr>',
				'  <tr>',
				'    <th col="pwdtip">首次密码提示;</th>',
				'    <td col="pwdtip">&nbsp;</td>',
				'    <th col="pwdfail">启用密码过期:</th>',
				'    <td col="pwdfail">&nbsp;</td>',
				'    <th col="pwdcycle">过期周期（天）</th>',
				'    <td col="pwdcycle">&nbsp;</td>',
				'  </tr>',
				'  <tr>',
				'    <th col="loglimit">登录限制</th>',
				'   <td col="loglimit">&nbsp;</td>',
				'    <th col="iplimit">限制IP段或机器名</th>',
				'    <td col="iplimit" colspan="3">&nbsp;</td>',
				'  </tr>',
				'  <tr>',
				'    <th rowspan="3" col="roles">用户角色配置</th>',
				'    <td rowspan="3" colspan="5" height="60" col="roles">&nbsp;</td>',
				' </tr>'];
			
			var  detailCfgs = [{
				 cmns : 'TREE', 
				 model : 'single',
				 labelWidth : 110,
				 title : '用户详情',
				 i18n : UI_Menu.appDefault,		//国际化资源对象
				 htmls : htmlArrs,
				 url : './sysUser_get.action',
				 prevUrl : './sysUser_prev.action',
				 nextUrl : './sysUser_next.action',
				 params : {userId:selId},
				 callback : {sfn:function(jsonData){
				 	switch(jsonData["dataLevel"]){
				 		case -1:
				 			jsonData["dataLevel"]="无";break;
				 		case 0:
				 			jsonData["dataLevel"]="本地数据";break;
				 		case 1:
				 			jsonData["dataLevel"]="用户自定义";break;
				 		case 2:
				 			jsonData["dataLevel"]="本部门数据";break;
				 		case 3:
				 			jsonData["dataLevel"]="本部门及子部门";break;
				 		case 4:
				 			jsonData["dataLevel"]="自定义部门";break;
				 		case 5:
				 			jsonData["dataLevel"]="本公司数据";break;
				 		case 6:
				 			jsonData["dataLevel"]="自定义公司";break;
				 		case 7:
				 			jsonData["dataLevel"]="无限制";break;
				 	}
				 	if(jsonData["accessIds"]){
				 		var jdata=jsonData["accessIds"].split("##");
				 		jsonData["accessIds"]=jdata[1];
				 	}
				 	if(jsonData["leaderName"]){
				 		var jdata=jsonData["leaderName"].split("##");
				 		jsonData["leaderName"]=jdata[1];
				 	}
				 	switch(jsonData["pwdtip"]){
				 		case 0:
				 			jsonData["pwdtip"]="是";break;
				 		case 1:
				 			jsonData["pwdtip"]="否";break;
				 	}
				 	switch(jsonData["sex"]){
				 		case 0:
				 			jsonData["sex"]="男";break;
				 		case 1:
				 			jsonData["sex"]="女";break;
				 	}
				 	switch(jsonData["pwdfail"]){
				 		case 0:
				 			jsonData["pwdfail"]="是";break;
				 		case 1:
				 			jsonData["pwdfail"]="否";break;
				 	}
				 	switch(jsonData["loglimit"]){
				 		case 0:
				 			jsonData["loglimit"]="无限制";break;
				 		case 1:
				 			jsonData["loglimit"]="限制IP段";break;
				 	}
				 	
				 	var empName = jsonData["empName"];
				 	if(empName && empName.indexOf("##") != -1){
						empName = empName.split("##")[1];
					}
					jsonData["empName"] = empName;
				 }}
			}];
			
			var cfg = {width:650,detailCfgs:detailCfgs};
			return new Ext.ux.panel.DetailPanel(cfg);
		}
	};
});