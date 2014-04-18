Ext.namespace("skythink.cmw.fininter");
/**
 * 组架机构管理 UI
 * @author 彭登浩
 * @date 2012-11-02
 * */ 
skythink.cmw.fininter.FinSysCfgMgr = function(){
	this.init(arguments[0]);
};

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.fininter.FinSysCfgMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsTreePanelView({
			tab : tab,
			params : params,
			hasTopSys : false,
			getToolBar : this.getToolBar,
			getAppPanel : this.getAppPanel,
			getAppTree : this.getAppTree,
			globalMgr : this.globalMgr,
			refresh : this.refresh
		});
	},
	getToolBar : function(){
		var self = this;
		var barItems = [{
			token : '添加',
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls : Btn_Cfgs.INSERT_CLS,
			tooltip : Btn_Cfgs.INSERT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.add(self);
			}
		},{
			token : '编辑',
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:Btn_Cfgs.MODIFY_CLS,
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			handler : function(){
				var nodeId = self.apptree.getSelId();
				if(!nodeId){
					ExtUtil.alert({msg:Msg_SysTip.msg_noSelNode});
					return;
				}
				self.globalMgr.edit(self);
			}
		},{
			token : '连接测试',
			text : Fs_Btn_Cfgs.CONNECTION_TEST_BTN_TXT,
			iconCls:Fs_Btn_Cfgs.CONNECTION_TEST_BTN_CLS,
			tooltip:Fs_Btn_Cfgs.CONNECTION_TEST_TIP_BTN_TXT,
			handler : function(){
				var nodeId = self.apptree.getSelId();
				if(!nodeId){
					ExtUtil.alert({msg:Fs_Msg_SysTip. msg_chosesystem});
					return;
				}
				self.globalMgr.test(self);
			}
		},{type:"sp"},{
			token : '启用',
			text : Btn_Cfgs.ENABLED_BTN_TXT,
			iconCls:Btn_Cfgs.ENABLED_CLS,
			tooltip:Btn_Cfgs.ENABLED_TIP_BTN_TXT,
			handler : function(){
				var nodeId = self.apptree.getSelId();
				if(!nodeId){
					ExtUtil.alert({msg:Msg_SysTip.msg_noSelNode});
					return;
				}
				EventManager.enabledData('./fsFinSysCfg_enabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.ENABLED,self:self});
			}
		},{
			token : '禁用',
			text : Btn_Cfgs.DISABLED_BTN_TXT,
			iconCls:Btn_Cfgs.DISABLED_CLS,
			tooltip:Btn_Cfgs.DISABLED_TIP_BTN_TXT,
			handler : function(){
				var nodeId = self.apptree.getSelId();
				if(!nodeId){
					ExtUtil.alert({msg:Msg_SysTip.msg_noSelNode});
					return;
				}
				EventManager.disabledData('./fsFinSysCfg_disabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DISABLED,self:self});
			}
		},{
			token : '删除',
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:Btn_Cfgs.DELETE_CLS,
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				var nodeId = self.apptree.getSelId();
				if(!nodeId){
					ExtUtil.alert({msg:Msg_SysTip.msg_noSelNode});
					return;
				}
				EventManager.get('./fsFinSysCfg_isNotLink.action',{params:{id:nodeId},sfn:function(json_data){
					if(json_data && json_data["success"]){
						EventManager.deleteData('./fsFinSysCfg_delete.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DISABLED,self:self});
					}else{
						ExtUtil.info({msg:Fs_Msg_SysTip.msg_isnotlink});
					}
				},ffn:function(){
					ExtUtil.info({msg:Fs_Msg_SysTip.msg_isnotlink});
				}});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 *  获取 按钮 Grid 对象
	 */
	getAppPanel : function(){
		var _this = this;
		var self = this;
		var appPanel = new Ext.Panel({border:false});
		appPanel.addListener('render',function(pnl){
			_this.globalMgr.appFrm = _this.globalMgr.createAppFrom(_this);
			_this.globalMgr.appDetailPnl = _this.globalMgr.createAppDetailPanel(_this);
			appPanel.add(_this.globalMgr.appFrm);
			appPanel.add(_this.globalMgr.appDetailPnl);
		});
		return appPanel;
	},
	/**
	 * 当新增、修改、删除、起用、禁用 
	 * 数据保存成功后会执行此方法刷新父页面
	 * @param optionType 操作类型 参考 constant.js 文件中的 "OPTION_TYPE" 常量值
	 * 	
	 * @param {} data 
	 * 	1).如果是 新增、修改表单数据保存成功的话. data 参数代表的是表单数据json对象
	 *  2).如果是 删除、起用、禁用 数据处理成功的话.data 参数是 ids 值。例如:{ids:'1001,1002,1003'}
	 */
	refresh:function(optionType,data){
		var activeKey = this.globalMgr.activeKey;
		this.apptree.reload();
		this.globalMgr.appFrm.hide();
		this.globalMgr.appDetailPnl.hide();
		this.globalMgr.activeKey = null;
	},
	/**
	 *  获取 TreePanel 对象
	 */
	getAppTree : function(){
		var _this = this;
		var _apptree = new Ext.ux.tree.MyTree({url:'./fsFinSysCfg_tree.action',
		isLoad : true,width:200,enableDD:false,autoScroll :true});
		_apptree.addListener('click',function(node, e){
			_this.globalMgr.appFrm.hide();
			var appDetailPnl = _this.globalMgr.appDetailPnl;
			appDetailPnl.show();
			var id = node.id;
			appDetailPnl.reload({id:id});
		});
		_apptree.expandAll(); 
		return _apptree
	},
	/**
	 * 当点击顶部的 系统图标 时，会触发 notify 方法。
	 * @param {} data 选中的 系统图标数据。
	 * 			{id : '',name:'',code:'',url:''} 等。
	 * 			具体值请看 SystemEntity 的 getFields() 方法
	 */
	globalMgr : {
		activeKey : null,
		appFrm : null,
		appDetailPnl : null,
		/**
		 * 添加
		 * @param {} _this
		 */
		add : function(_this){
			this.appFrm.reset();
			this.appFrm.show();
			this.appDetailPnl.hide();
		},
		/**
		 * 编辑
		 * @param {} _this
		 */
		edit : function(_this){
			this.appDetailPnl.hide();
			var nodeId = _this.apptree.getSelId();
			this.appFrm.show();
			this.appFrm.setValues ('./fsFinSysCfg_get.action',{params:{id:nodeId}});
		},
		/**
		 * 财务系统连接测试
		 * @param {} _this
		 */
		test : function(_this){
			var nodeId = _this.apptree.getSelId();
			var selText = _this.apptree.getSelText();
			Cmw.mask(_this,'正在测试连接...');
			 EventManager.get('./fsFinSysCfg_test.action',{params:{id:nodeId},
			 sfn:function(json_data){
				_this.globalMgr.appDetailPnl.reload({id:nodeId});
			 	Cmw.unmask(_this);
			 	var server = json_data.server;
			 	var spAccgroup = json_data.spAccgroup;
			 	var spUserName = json_data.spUserName;
			 	var spCurrency = json_data.spCurrency;
			 	var spSettle = json_data.spSettle;
			 	var spVhgroup = json_data.spVhgroup;
			 	var spImclass = json_data.spImclass;
			 	var spCustomer = json_data.spCustomer;
			 	var msg = '财务系统:"'+selText+'"与服务器"'+server+'"的连接测试成功!<br/>该系统支持的接口有:<br/>';
			 	var spArr = [];
			 	if(spAccgroup == 1){
			 		spArr[spArr.length] = "&nbsp;&nbsp;&nbsp;&nbsp;科目组数据读取 ---> <span style='font-weight:bold;'>支持</span>";
			 	}
			 	if(spUserName == 1){
			 		spArr[spArr.length] = "&nbsp;&nbsp;&nbsp;&nbsp;用户帐号读取 ---> <span style='font-weight:bold;'>支持</span>";
			 	}
			 	if(spCurrency == 1){
			 		spArr[spArr.length] = "&nbsp;&nbsp;&nbsp;&nbsp;币别数据读取 ---> <span style='font-weight:bold;'>支持</span>";
			 	}
			 	if(spSettle == 1){
			 		spArr[spArr.length] = "&nbsp;&nbsp;&nbsp;&nbsp;结算方式读取 ---> <span style='font-weight:bold;'>支持</span>";
			 	}
			 	if(spVhgroup == 1){
			 		spArr[spArr.length] = "&nbsp;&nbsp;&nbsp;&nbsp;凭证字数据读取 ---> <span style='font-weight:bold;'>支持</span>";
			 	}
			 	if(spImclass == 1){
			 		spArr[spArr.length] = "&nbsp;&nbsp;&nbsp;&nbsp;核算项目读取 ---> <span style='font-weight:bold;'>支持</span>";
			 	}
			 	if(spCustomer == 1){
			 		spArr[spArr.length] = "&nbsp;&nbsp;&nbsp;&nbsp;客户数据写入 ---> <span style='font-weight:bold;'>支持</span>";
			 	}
			 	msg += spArr.join("<br/>");
			 	ExtUtil.alert({msg:msg});
			 },ffn:function(json_data){
			 	Cmw.unmask(_this);
			 	ExtUtil.alert({msg:'财务系统:"'+selText+'"连接测试失败,请检查配置是否正确或网络是否存在异常!'});
			 }});
		},
		/**
		 * 创建表单面板
		 * @param {} _this
		 */
		createAppFrom : function(_this){
			var txt_id = FormUtil.getHidField({
				fieldLabel: 'ID',
				name: 'id',
				"width": "125"
			});
			
			var txt_isenabled = FormUtil.getHidField({
			    fieldLabel: 'isenabled',
			    name: 'isenabled',
			    "width": "125"
			});
			
			var txt_code = FormUtil.getTxtField({
			    fieldLabel: '财务系统编号',
				name: 'code',
				"width": 125,
				"allowBlank": false,
				"maxLength": "50"
			});
			
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '财务系统名称',
				name: 'name',
				"width": 125,
				"allowBlank": false,
				"maxLength": "50"
			});
			
			var txt_server = FormUtil.getTxtField({
			    fieldLabel: '服务器地址',
				name: 'server',
				"width": 125,
				"allowBlank": false,
				"maxLength": "80"
			});
			
			var int_port = FormUtil.getIntegerField({
			    fieldLabel: '端口号',
				name: 'port',
				"width": 125,
				"allowBlank": false,
				"maxLength": 10
			});
			
			var rad_interType = FormUtil.getRadioGroup({
			    fieldLabel: '接口类型',
				name: 'interType',
				"width": 180,
				"allowBlank": false,
				"items": [{
				    "boxLabel": "数据库接口",
				    "name": "interType",
				    "inputValue": "1"
				},
				{
				    "boxLabel": "webService",
				    "name": "interType",
				    "inputValue": "2"
				}],
				listeners : {
					change : function(rdgp,rd){
						var val = rdgp.getValue();
						var allowBlank = true;
						if(parseInt(val) == 1){
						  allowBlank=false;
						}else{
						  allowBlank=true;
						}
						appform.setAllowBlanks("dbType,dbName,loginName,passWord,maxSize",allowBlank);
					}
				}
			});
			
			var cbo_dbType = FormUtil.getLCboField({fieldLabel : '数据库类型',name:'dbType',
			data:[["1","SqlServer"],["2","Mysql"],["2","Oracle"]]});
			
			var txt_dbName = FormUtil.getTxtField({
			    fieldLabel: '数据库名',
				name: 'dbName',
				"width": 125,
				"maxLength": "50"
			});
			
			var txt_loginName = FormUtil.getTxtField({
			    fieldLabel: '登录帐号',
				name: 'loginName',
				"width": 125,
				"maxLength": "50"
			});
			
			var txt_passWord = FormUtil.getTxtField({
			    fieldLabel: '登录密码',
				name: 'passWord',
				"width": 125,
				"maxLength": "50"
			});
			
			var txt_maxSize = FormUtil.getIntegerField({
			    fieldLabel: '最大连接数',
				name: 'maxSize',
				"width": 125,
				"maxLength": "50",
				value:"1"
			});
			
			
			var txt_dataCode = FormUtil.getTxtField({
			    fieldLabel: '数据中心代码',
				name: 'dataCode',
				"width": 125,
				"maxLength": "50"
			});
			
			var txt_language = FormUtil.getTxtField({
			    fieldLabel: '国际化语言',
				name: 'language',
				"width": 125,
				"maxLength": "50"
			});
			
			var txt_validType = FormUtil.getTxtField({
			    fieldLabel: '验证方式',
				name: 'validType',
				"width": 125,
				"maxLength": "50"
			});
			
			var rad_spAccgroup = FormUtil.getRadioGroup({
			    fieldLabel: '支持科目组',
				name: 'spAccgroup',
				"width": 125,
				"allowBlank": false,
				"items": [{
				    "boxLabel": "支持",
				    "name": "spAccgroup",
				    "inputValue": "1"
				},
				{
				    "boxLabel": "不支持",
				    "name": "spAccgroup",
				    "inputValue": "2"
				}]
			});
			
			var rad_spUserName = FormUtil.getRadioGroup({
			    fieldLabel: '用户帐号读取',
				name: 'spUserName',
				"width": 125,
				"allowBlank": false,
				"items": [{
				    "boxLabel": "支持",
				    "name": "spUserName",
				    "inputValue": "1"
				},
				{
				    "boxLabel": "不支持",
				    "name": "spUserName",
				    "inputValue": "2"
			    }]
			});
			
			var rad_spCurrency = FormUtil.getRadioGroup({
			    fieldLabel: '币别数据读取',
				name: 'spCurrency',
				"width": 125,
				"allowBlank": false,
				"items": [{
				    "boxLabel": "支持",
				    "name": "spCurrency",
				    "inputValue": "1"
				},
				{
				    "boxLabel": "不支持",
				    "name": "spCurrency",
				    "inputValue": "2"
			    }]
			});
			
			var rad_spSettle = FormUtil.getRadioGroup({
			    fieldLabel: '结算方式读取',
				name: 'spSettle',
				"width": 125,
				"allowBlank": false,
				"items": [{
				    "boxLabel": "支持",
				    "name": "spSettle",
				    "inputValue": "1"
				},
				{
				    "boxLabel": "不支持",
				    "name": "spSettle",
				    "inputValue": "2"
			    }]
			});
			
			var rad_spVhgroup = FormUtil.getRadioGroup({
			    fieldLabel: '凭证字数据读取',
				name: 'spVhgroup',
				"width": 125,
				"allowBlank": false,
				"items": [{
				    "boxLabel": "支持",
				    "name": "spVhgroup",
				    "inputValue": "1"
				},
				{
				    "boxLabel": "不支持",
				    "name": "spVhgroup",
				    "inputValue": "2"
			    }]
			});
			
			var rad_spImclass = FormUtil.getRadioGroup({
			    fieldLabel: '核算项目读取',
				name: 'spImclass',
				"width": 125,
				"allowBlank": false,
				"items": [{
				    "boxLabel": "支持",
				    "name": "spImclass",
				    "inputValue": "1"
				},
				{
				    "boxLabel": "不支持",
				    "name": "spImclass",
				    "inputValue": "2"
			    }]
			});
			
			var rad_spCustomer = FormUtil.getRadioGroup({
			    fieldLabel: '客户写入',
				name: 'spCustomer',
				"width": 125,
				"allowBlank": false,
				"items": [{
				    "boxLabel": "支持",
				    "name": "spCustomer",
				    "inputValue": "1"
				},
				{
				    "boxLabel": "不支持",
				    "name": "spCustomer",
				    "inputValue": "2"
			    }]
			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
				name: 'remark',
				"width": 680,
				"maxLength": 200
			});
			
			var layout_fields = [
			txt_id,txt_isenabled, {cmns: FormUtil.CMN_THREE,fields: [txt_code,txt_name, txt_server,int_port, rad_interType,
			cbo_dbType, txt_dbName, txt_loginName, txt_passWord, txt_maxSize,
			txt_dataCode, txt_language, txt_validType, rad_spAccgroup, rad_spUserName,
			rad_spCurrency, rad_spSettle, rad_spVhgroup, rad_spImclass, rad_spCustomer]},
			txa_remark];
			var btnSave = this.getBtnSave(_this);
			var frm_cfg = {
			    title: '财务系统信息编辑',
			    labelWidth : 110,
			    buttonAlign : 'center',
			    buttons : [btnSave],
			    hidden : true,
				url: './fsFinSysCfg_save.action'
			};
			var appform = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform;
		},
		getBtnSave : function(_this){
			var globalMgr = _this.globalMgr;
			var btnTxt = Btn_Cfgs.SAVE_BTN_TXT;
			var btnSave = new Ext.Button({text:btnTxt,handler: function(){
				globalMgr.activeKey = btnTxt;
				var cfg = {
					tb:_this.apptbar,
					sfn : function(formData){
						if(_this.refresh) _this.refresh(formData);
						
					},ffn : function(formData){
							ExtUtil.info({msg:formData.code+Fs_Msg_SysTip.msg_codenotonly});
						//Cmw.unmask(_this);
					}
				};
				EventManager.frm_save(globalMgr.appFrm,cfg);
			}});
			return btnSave;
		},
		/**
		 * 创建详情面板
		 * @param {} _this
		 */
		createAppDetailPanel : function(_this){
			var el = _this.el;
			var width = 880;
			if(el){
				width = el.getComputedWidth();
			}
			var htmlArrs_1 = [
					'<tr><th ><span>*</span><label col="code">财务系统编号</label></th> <td col="code" >&nbsp;</td><th ><span>*</span><label col="name">财务系统名称</label></th> <td col="name" >&nbsp;</td><th ><span>*</span><label col="interType">接口类型</label></th> <td col="interType" >&nbsp;</td></tr>',
					'<tr><th ><span>*</span><label col="server">服务器地址</label></th> <td col="server">&nbsp;</td><th ><span>*</span><label col="port">端口号</label></th> <td col="port" >&nbsp;</td><th col="dbType">数据库类型</th> <td col="dbType" >&nbsp;</td></tr>',
					'<tr><th col="dbName">数据库名</th> <td col="dbName" >&nbsp;</td><th col="loginName">登录帐号</th> <td col="loginName" >&nbsp;</td><th col="passWord">登录密码</th> <td col="passWord" >&nbsp;</td></tr>',
					'<tr><th col="maxSize">最大连接数</th> <td col="maxSize" >&nbsp;</td><th col="dataCode">数据中心代码</th> <td col="dataCode" >&nbsp;</td><th col="language">国际化语言</th> <td col="language" >&nbsp;</td></tr>',
					'<tr><th col="validType">验证方式</th> <td col="validType" >&nbsp;</td><th ><span>*</span><label col="spAccgroup">支持科目组</label></th> <td col="spAccgroup" >&nbsp;</td><th ><span>*</span><label col="spUserName">用户帐号读取</label></th> <td col="spUserName" >&nbsp;</td></tr>',
					'<tr><th ><span>*</span><label col="spCurrency">币别数据读取</label></th> <td col="spCurrency" >&nbsp;</td><th ><span>*</span><label col="spSettle">结算方式读取</label></th> <td col="spSettle" >&nbsp;</td><th ><span>*</span><label col="spVhgroup">凭证字数据读取</label></th> <td col="spVhgroup" >&nbsp;</td></tr>',
					'<tr><th ><span>*</span><label col="spImclass">核算项目读取</label></th> <td col="spImclass" >&nbsp;</td><th ><span>*</span><label col="spCustomer">客户写入</label></th> <td col="spCustomer" colSpan="3">&nbsp;</td></tr></tr>',
					'<tr><th col="remark">备注</th> <td col="remark"  colspan=5 >&nbsp;</td></tr>'];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 110,
			    title : '财务系统详细信息',
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './fsFinSysCfg_get.action',
			    params: {
			        id: -1
			    },
			    callback: {
			        sfn: function(jsonData) {
			        	jsonData["interType"] = (jsonData["interType"]=="1") ? "数据库接口" : "webService接口";
			        	var dbType = jsonData["dbType"];
			        	switch(parseInt(dbType)){
			        		case 0 :{
			        			dbType = '未设置';
			        			break;
			        		}case 1 :{
			        			dbType = 'SqlServer';
			        			break;
			        		}case 2 :{
			        			dbType = 'Mysql';
			        			break;
			        		}case 3 :{
			        			dbType = 'Oracle';
			        			break;
			        		} 
			        	}
			        	jsonData["dbType"] = dbType; 
			            jsonData["spAccgroup"] = (jsonData["spAccgroup"]=="1") ? "支持" : "不支持";
			            jsonData["spUserName"] = (jsonData["spUserName"]=="1") ? "支持" : "不支持";
			            jsonData["spCurrency"] = (jsonData["spCurrency"]=="1") ? "支持" : "不支持";
			            jsonData["spSettle"] = (jsonData["spSettle"]=="1") ? "支持" : "不支持";
			            jsonData["spVhgroup"] = (jsonData["spVhgroup"]=="1") ? "支持" : "不支持";
			            jsonData["spImclass"] = (jsonData["spImclass"]=="1") ? "支持" : "不支持";
			            jsonData["spCustomer"] = (jsonData["spCustomer"]=="1") ? "支持" : "不支持";
			        }
			    }
			}];
			
			var detailPanel = new Ext.ux.panel.DetailPanel({
			    width: width,
			    hidden : true,
			    autoScroll : true,
			    detailCfgs: detailCfgs_1
			});
			return detailPanel;
		}
	}
});

