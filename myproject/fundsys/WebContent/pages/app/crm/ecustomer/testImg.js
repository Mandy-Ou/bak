/*同心日科技公司Erp 系统 命名空间*/
Ext.namespace("skythink.cmw.crm");
/**
 * 卡片菜单管理
 */
skythink.cmw.crm.testImg = function(){
	this.init(arguments[0]);
}

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.crm.testImg,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsPanelView({
			tab : tab,
			params : params,/*apptabtreewinId,tabId,sysid*/
			getAppCmpt : this.getAppCmpt,
			changeSize : this.changeSize,
			destroyCmpts : this.destroyCmpts,
			globalMgr : this.globalMgr,
			refresh : this.refresh,
			prefix : Ext.id()
		});
	},
	/**
	 * 获取组件方法
	 */
	getAppCmpt : function(){
		var appPanel = new Ext.ux.MyImgPanel({border:true,width : 600,height:600});
//		this.tab.notify = function(_tab){
//			_this.refresh();
//		};
		return appPanel;
	},
	/**
	 * 刷新方法
	 * @param {} optionType
	 * @param {} data
	 */
	refresh:function(){
		var baseId = this.params.baseId;
		var ecustomerInfoId = this.params.ecustomerId;
		var code = this.params.code;
		var appgrid = this.params.appgrid;
		if(baseId){	/*修改 */
			var parentCfg = {parent : this.appPanel,appgrid : appgrid,
			optionType : OPTION_TYPE.EDIT,params : {baseId:baseId,id : ecustomerInfoId,sysId : this.params.sysid,code:this.params.code,
			name : this.params.name,serialNum:this.params.serialNum,tradNumber:this.params.tradNumber,orgcode:this.params.orgcode,
			registerTime:this.params.registerTime,regman:this.params.regman}};
			this.globalMgr.showCustInfoAppMainPanel(parentCfg,this);
		}else{
			var ecustInfoAppMainPanel = this.appCmpts["ecustInfoAppMainPanel"];
			if(ecustInfoAppMainPanel){
				ecustInfoAppMainPanel.resets();
				ecustInfoAppMainPanel.hide();
				this.globalMgr.chosePanel.show();
				var appform = Ext.getCmp('frmid'+this.prefix);
				EventManager.frm_reset(appform);
			}
		}
	},
	/**
	 * 组件大小改变方法
	 * @param {} whArr
	 */
	changeSize : function(whArr){
		var width = whArr[0]-5;
		var height = whArr[1]-2;
		this.appPanel.setWidth(width);
		this.appPanel.setHeight(height);
	},
	/**
	 * 组件销毁方法
	 */
	destroyCmpts : function(){
	},
	globalMgr : {
		chosePanel : null,
		ecustInfoAppMainPanel : null,
		/**
		 * 获取客户选择面板 
		 * @return {}
		 */
		getChosePanel : function(_this){
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '企业名称',
			    name: 'name',
			    "width": 350,
			    "allowBlank": false,
			    "maxLength": "50"
			});

			var cbo_tradNumber = FormUtil.getTxtField({
			    fieldLabel: '工商登记号',
			    name: 'tradNumber',
			    "width": 350,
			    "allowBlank": false
			});
			
			var txt_code = FormUtil.getTxtField({
			    fieldLabel: '组织机构代码',
			    name: 'orgcode',
			    "allowBlank": false,
			    width: 350,
			    "maxLength": "50"
			});
			
			var frm_cfg = {
				 bodyStyle: {  
	                padding: '10px'   
            	},
				id : 'frmid'+_this.prefix,
			    title: '提示：[<span style="color:red;">主要检查新企业客户是否是已存在或系统中的黑名单企业客户</span>]',
			    width:500,
			    url: './crmCustBase_add.action',
			    buttonAlign : 'center'
			};
			
			var layout_fields = [ txt_name,cbo_tradNumber,txt_code];
			
			var appform = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			
			var btnCheck = new Ext.Button({text:'检查企业客户',handler:function(){
				if(!appform.isValid()){
					return;
				}
				EventManager.frm_save(appform,{sfn:function(formDatas){
					var code = formDatas.code;
					txt_code.setValue(code);
					btnNext.enable();
				},ffn : function(formDatas){
				}});
			}});
			
			var flag = false;
			var btnNext = new Ext.Button({text:'下一步[企业客户资料录入]',handler:function(){
				var formDatas = appform.getValues();
				if(!appform.isValid()){
					return;
				}
				EventManager.frm_save(appform,{sfn:function(formDatas){
					var parentCfg = {parent : _this.appPanel,appgrid: _this.params.appgrid,
					optionType : OPTION_TYPE.ADD,params : {formDatas:formDatas,sysId : _this.params.sysid,regman:CURENT_EMP,registerTime:new Date()}};
					_this.globalMgr.showCustInfoAppMainPanel(parentCfg,_this);
				}});
			}});
			
			var btnCancel = new Ext.Button({text:'取消[企业客户资料录入]',handler:function(){
				_this.tab.destroy();
			}});
			appform.addButton(btnNext);
			appform.addButton(btnCancel);
			
			var chosePanel  = new Ext.Panel({width:500,title:'企业客户资料检查',cls:'choseCenterCls'});
			chosePanel.add(appform);
			_this.globalMgr.chosePanel = chosePanel;
			return chosePanel;	
		},
		showCustInfoAppMainPanel : function(parentCfg,_this){
			if(_this.globalMgr.chosePanel) _this.globalMgr.chosePanel.hide();
			var ecustInfoAppMainPanel = _this.appCmpts["ecustInfoAppMainPanel"]
			if(ecustInfoAppMainPanel){
				ecustInfoAppMainPanel.setParentCfg(parentCfg);
				ecustInfoAppMainPanel.show();
				ecustInfoAppMainPanel.setValues();
				_this.appPanel.doLayout();
			}else{
				Cmw.importPackage('pages/app/crm/ecustomer/EcustomerInfoMainPanel',function(module) {
					_this.appCmpts["ecustInfoAppMainPanel"] =  module.MainEdit;
				 	var appMainPanel = _this.appCmpts["ecustInfoAppMainPanel"].getAppMainPanel(parentCfg);
				 	_this.appPanel.add(appMainPanel);
				 	_this.appPanel.doLayout();
	  			});
			}
	}
	}
});

