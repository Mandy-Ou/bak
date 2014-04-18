/*同心日科技公司Erp 系统 命名空间*/
Ext.namespace("skythink.cmw.workflow.bussforms");
/**
 * 展期协议书子流程
 */
skythink.cmw.workflow.bussforms.ExtContractMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(skythink.cmw.workflow.bussforms.ExtContractMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		var finishBussCallback = tab.finishBussCallback;
		this.module = new Cmw.app.widget.AbsPanelView({
			tab : tab,
			params : params,
			getToolBar :this.getToolBar,
			globalMgr : this.globalMgr,
			getAppCmpt : this.getAppCmpt,
			changeSize : this.changeSize,
			destroyCmpts : this.destroyCmpts,
			finishBussCallback : finishBussCallback,/*由必做或选做业务菜单传入的回調函数，主要功能：当业务表单保存后，更新必做或选做事项为已做*/
			prefix : Ext.id()
		});
	},
	
	getAppCmpt :function(){
		var that = this ; 
		var baseId = this.params.baseId;
		this.appPanel = new Ext.Panel();
		this.appPanel.add(this.globalMgr.toolBar);
		var CmptPnl = this.globalMgr.Cmpt(that);
		this.appPanel.doLayout();
		return this.appPanel;
	},
	getToolBar : function(){
		var _this = this;
		var toolBar = null;
		var barItems = [{
			token : "添加展期申请单",
			text : Btn_Cfgs.EXTCONTRACT_ADD_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.EXTCONTRACT_ADD_BTN_TIP_TXT,
			handler : function(){
				_this.globalMgr.installation(_this,OPTION_TYPE.ADD);
				_this.globalMgr.toolBar.enableButtons(Btn_Cfgs.EXTCONTRACT_SAVE_BTN_TXT);
				_this.globalMgr.toolBar.disableButtons(Btn_Cfgs.EXTCONTRACT_EDIT_BTN_TXT);
			}
		},{
			token : "编辑展期协议书",
			text : Btn_Cfgs.EXTCONTRACT_EDIT_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.EXTCONTRACT_EDIT_BTN_TIP_TXT,
			handler : function(){
				_this.globalMgr.btnClick(_this,OPTION_TYPE.EDIT);
//				_this.globalMgr.toolBar.enableButtons(Btn_Cfgs.EXTCONTRACT_SAVE_BTN_TXT);
			}
		},{
			token : "保存展期协议书",
			text : Btn_Cfgs.EXTCONTRACT_SAVE_BTN_TXT,
			iconCls:'page_save',
			tooltip:Btn_Cfgs.EXTCONTRACT_SAVE_BTN_TIP_TXT,
			handler : function(){
				_this.globalMgr.btnClick(_this,OPTION_TYPE.SAVE);
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
		var toolBtns = toolBar.getButtons(Btn_Cfgs.EXTCONTRACT_ADD_BTN_TXT,Btn_Cfgs.EXTCONTRACT_EDIT_BTN_TXT,Btn_Cfgs.EXTCONTRACT_SAVE_BTN_TXT);
		this.globalMgr.addBtn = toolBtns[0];
		this.globalMgr.editBtn = toolBtns[1];
		this.globalMgr.saveBtn = toolBtns[2];
		this.globalMgr.toolBar = toolBar;
		return toolBar;
	},
	
	globalMgr : {
		subMainPnl : null,
		extensionId : this.params.applyId,
		contractId : _this.params.contractId,
		sysId : this.params.sysId,
		toolBar : null,
		MainMod : null,
		addBtn : null,
		editBtn : null,
		saveBtn : null,
		Cmpt : function(_this,optionType){
			params = {
				extensionId : _this.globalMgr.extensionId,
				contractId:_this.params.contractId,
				sysId:_this.params.sysId,optionType : optionType,
				addBtn:_this.globalMgr.addBtn,editBtn:_this.globalMgr.editBtn,
				saveBtn :_this.globalMgr.saveBtn,
				id : null
			},
			_this.params = params;
			EventManager.get('./fcExtContract_get.action',{params:{extensionId:_this.globalMgr.extensionId},sfn:function(jsonData){
				if(jsonData){
					if(jsonData!=-1){
						_this.params.optionType = OPTION_TYPE.DETAIL;
						_this.params.id = jsonData.id;
						_this.globalMgr.installation(_this,_this.params.optionType);
						_this.globalMgr.toolBar.enableButtons(Btn_Cfgs.EXTCONTRACT_EDIT_BTN_TXT);
						_this.globalMgr.toolBar.disableButtons(Btn_Cfgs.EXTCONTRACT_SAVE_BTN_TXT);
						_this.globalMgr.toolBar.disableButtons(Btn_Cfgs.EXTCONTRACT_ADD_BTN_TXT);
					}else{
						_this.globalMgr.toolBar.disableButtons(Btn_Cfgs.EXTCONTRACT_EDIT_BTN_TXT);
						_this.globalMgr.toolBar.disableButtons(Btn_Cfgs.EXTCONTRACT_SAVE_BTN_TXT);
						return;
					}
				}
			},ffn:function(jsonData){
				ExtUtil.alert({msg:"加载数据出错！"});
				return;
			}});
		},
		installation : function(_this,optionType){
			_this.params.optionType=optionType;
			if(!_this.globalMgr.subMainPnl){
				Cmw.importPackage('pages/app/workflow/bussProcc/formUIs/extcontract/ExtContractCmp',function(module) {
				 	_this.globalMgr.MainMod =  module.moduleUI;
				 	if(_this.finishBussCallback)_this.globalMgr.MainMod.finishBussCallback = _this.finishBussCallback;
				 	_this.globalMgr.subMainPnl = _this.globalMgr.MainMod.getModule(_this);
				 	_this.appPanel.add(_this.globalMgr.subMainPnl);
				 	if(_this.params.optionType==OPTION_TYPE.DETAIL){
				 		_this.globalMgr.btnClick(_this,optionType);
				 	}
				 	_this.appPanel.doLayout();
	  			});
			}else{
				_this.globalMgr.btnClick(_this,optionType);
				_this.appPanel.doLayout();
			}
		},
		btnClick : function(_this,optionType){
			_this.params.optionType=optionType;
			_this.globalMgr.MainMod.btnHandler(_this);
		}
	},	
	/**
	 * 组件大小改变方法
	 * @param {} whArr
	 */
	changeSize : function(whArr){
		var width = whArr[0];
		var height = whArr[1];
		this.appPanel.setWidth(width);
		this.appPanel.setHeight(height);
	},	
	/**
	 * 组件销毁方法
	 */
	destroyCmpts : function(){
		if(this.globalMgr.MainMod){
			this.globalMgr.MainMod.destroy();
			this.globalMgr.MainMod = null;
			
		}
		if(this.globalMgr.subMainPnl){
//			this.globalMgr.subMainPn.destroy();
			this.globalMgr.subMainPn = null;
		}
		if(this.appPanel){
			this.appPanel.destroy();
			this.appPanel = null;
		}
		
	}
	
});