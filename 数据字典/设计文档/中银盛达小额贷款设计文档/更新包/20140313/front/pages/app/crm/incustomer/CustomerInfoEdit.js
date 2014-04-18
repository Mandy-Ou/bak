/*同心日科技公司Erp 系统 命名空间*/
Ext.namespace("skythink.cmw.crm");
/**
 * 卡片菜单管理
 */
skythink.cmw.crm.CustomerInfoEdit = function(){
	this.init(arguments[0]);
}

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.crm.CustomerInfoEdit,Ext.util.MyObservable,{
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
		var chosePanel  = this.globalMgr.getChosePanel(this);
		var appPanel = new Ext.Panel({border:true,width : 200,height:600});
		appPanel.add(chosePanel);
		var _this = this;
		this.tab.notify = function(_tab){
			_this.refresh();
		};
		return appPanel;
	},
	/**
	 * 刷新方法
	 * @param {} optionType
	 * @param {} data
	 */
	refresh:function(){
		var baseId = this.params.baseId;
		var customerInfoId = this.params.customerId;
		var appgrid = this.params.appgrid;
		var code = this.params.code;
		if(baseId){	/*修改 */
			var registerTime = Ext.util.Format.date(this.params.registerTime,'Y-m-d');
			var parentCfg = {parent : this.appPanel,appgrid:appgrid,
			optionType : OPTION_TYPE.EDIT,params : {baseId:baseId,id : customerInfoId,sysId : this.params.sysid,code:this.params.code,
			name : this.params.name,serialNum:this.params.serialNum,cardType:this.params.cardType,cardNum:this.params.cardNum,sex:this.params.sex,
			registerTime:registerTime,regman:this.params.regman}};
			this.globalMgr.showCustInfoAppMainPanel(parentCfg,this);
		}else{
			var custInfoAppMainPanel = this.appCmpts["custInfoAppMainPanel"];
			if(custInfoAppMainPanel){
				custInfoAppMainPanel.resets();
				custInfoAppMainPanel.hide();
				this.globalMgr.chosePanel.show();
				var appform = Ext.getCmp('frmid'+this.prefix);
				EventManager.frm_reset(appform);
				var htmlDiv = Ext.get(this.globalMgr.formHtml);
				htmlDiv.update("",true,function(){
					appform.doLayout();
				});
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
		formHtml : Ext.id(null,"formHtml"),
		chosePanel : null,
		custInfoAppMainPanel : null,
		/**
		 * 获取客户选择面板 
		 * @return {}
		 */
		getChosePanel : function(_this){
			var hid_isNon = FormUtil.getHidField({
			    fieldLabel: 'isNon',
			    name: 'isNon',
			    "width": 200,
			    "maxLength": "50"
			});
			
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '姓名',
			    name: 'name',
			    "width": 200,
			    "allowBlank": false,
			    "maxLength": "50"
			});

			var cbo_cardType = FormUtil.getRCboField({
			    fieldLabel: '证件类型',
			    name: 'cardType',
			    "width": 200,
			     "allowBlank": false,
			    register : REGISTER.GvlistDatas, 
			    restypeId : '100002'
			});
			
			var txt_cardNum = FormUtil.getTxtField({
			    fieldLabel: '证件号码',
			    name: 'cardNum',
			    "width": 200,
			    "allowBlank": false,
			    "maxLength": '50'
			});
			
			var frm_cfg = {
				id : 'frmid'+_this.prefix,
			    title: '提示：[<span style="color:red;">主要检查新客户是否是已存在或系统中的黑名单客户</span>]',
			    width:500,
			    html :'<div><span style="display:bolk;color:red;algin:center" id='+_this.globalMgr.formHtml+'></span></div>',
			    url: './crmCustBase_add.action',
			    buttonAlign : 'center'
			};
			
			cbo_cardType.on('select',function(){
				if(cbo_cardType.getValue()==7){
					txt_cardNum.vtype = 'IDCard';
				}else{
					txt_cardNum.vtype = null;
				}
			});
			
			var layout_fields = [ hid_isNon,
			    txt_name,cbo_cardType,
			txt_cardNum];
			
			var appform = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			
			var flag = false;
			var btnNext = new Ext.Button({text:'下一步[客户资料录入]',handler:function(){
				var formDatas = appform.getValues();
				if(!appform.isValid()){
					return;
				}
				EventManager.frm_save(appform,{sfn:function(formDatas){
					var parentCfg = {parent : _this.appPanel,appgrid:_this.params.appgrid,
					optionType : OPTION_TYPE.ADD,params : {formDatas:formDatas,sysId : _this.params.sysid,regman:CURENT_EMP,registerTime:new Date()}};
					_this.globalMgr.showCustInfoAppMainPanel(parentCfg,_this);
				},ffn:function(json_data){
					if(json_data){
						var custName = json_data.custName;
						var cardNum = json_data.cardNum ;
						var cardType = json_data.cardType;
						var html = '客户资料库:'+custName+cardType+':'+cardNum+'<br>已存在!请核对是否是同一个客户。<br>若是不是一个人请直接点击下一步按钮,否则点击取消按钮！';
						var htmlDiv = Ext.get(_this.globalMgr.formHtml);
						htmlDiv.update(html,true,function(){
							htmlDiv.show();
							appform.doLayout();
							hid_isNon.setValue(1);
						});
						
					}
				}});
			}});
			
			var btnCancel = new Ext.Button({text:'取消[客户资料录入]',handler:function(){
				_this.tab.destroy();
			}});
			appform.addButton(btnNext);
			appform.addButton(btnCancel);
			var chosePanel  = new Ext.Panel({width:400,title:'客户资料检查',cls:'choseCenterCls'});
			chosePanel.add(appform);
			_this.globalMgr.chosePanel = chosePanel;
			return chosePanel;	
		},
		showCustInfoAppMainPanel : function(parentCfg,_this){
			if(_this.globalMgr.chosePanel) _this.globalMgr.chosePanel.hide();
			var custInfoAppMainPanel = _this.appCmpts["custInfoAppMainPanel"]
			if(custInfoAppMainPanel){
				custInfoAppMainPanel.setParentCfg(parentCfg);
				custInfoAppMainPanel.show();
				custInfoAppMainPanel.setValues();
				_this.appPanel.doLayout();
			}else{
				Cmw.importPackage('pages/app/crm/incustomer/CustomerInfoMainPanel',function(module) {
					_this.appCmpts["custInfoAppMainPanel"] =  module.MainEdit;
				 	var appMainPanel = _this.appCmpts["custInfoAppMainPanel"].getAppMainPanel(parentCfg);
				 	_this.appPanel.add(appMainPanel);
				 	_this.appPanel.doLayout();
	  			});
			}
	}
	}
});

