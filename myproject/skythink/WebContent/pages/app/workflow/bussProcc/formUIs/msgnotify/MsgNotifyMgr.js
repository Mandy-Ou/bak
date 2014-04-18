Ext.namespace("skythink.cmw.workflow.bussforms");
/**
 * 消息通知FrmUI
 */
skythink.cmw.workflow.bussforms.MsgNotifyMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(skythink.cmw.workflow.bussforms.MsgNotifyMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		var finishBussCallback = tab.finishBussCallback;
		this.module = new Cmw.app.widget.AbsPanelView({
			tab : tab,
			params : params,/*apptabtreewinId,tabId,sysid*/
			getAppCmpt : this.getAppCmpt,/*组装面板*/
			getForm : this.createForm,
			changeSize : this.changeSize,
			destroyCmpts : this.destroyCmpts,
			globalMgr : this.globalMgr,
			refresh : this.refresh,
			finishBussCallback : finishBussCallback,/*由必做或选做业务菜单传入的回調函数，主要功能：当业务表单保存后，更新必做或选做事项为已做*/
			prefix : Ext.id()
		});
	},
	
	refresh :function(){
		var _this = this;
		if (!this.appPanel.rendered) {
			this.appPanel.addListener('render', function(cmpt) {
				_this.globalMgr.loadDatas(_this);
			});
		} else {
			this.globalMgr.loadDatas(_this);
		}
	},
	/**
	 *组装面板
	 */
	getAppCmpt : function(){
		this.appPanel = new Ext.Panel({ border : false});
		var appForm  = this.getForm();
		this.appPanel.add(appForm);
		this.appPanel.doLayout();
		this.refresh();
		return this.appPanel;
	},
	/**
	 * 创建表单面板
	 */
	createForm : function(){
		var _this = this;
		var txt_formId = FormUtil.getHidField({fieldLabel: '提前还款申请单ID',name: 'formId',value:_this.globalMgr.applyId});
		var check_notifysType = FormUtil.getCheckGroup({
			    fieldLabel: '消息通知类型',
			    name: 'notifysType',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "10",
			    items : [
			    	{boxLabel: '手机', name: 'phone',inputValue:1},
			    	{boxLabel: '邮件', name: 'email',inputValue:2},
			    	{boxLabel: '电话', name: 'telNum',inputValue:3}
				]
			});
			check_notifysType.on('change',function(){
				_this.globalMgr.valiDate(check_notifysType,txt_phoneNum,txt_email,txt_telNum);
			});
			var txt_notifyMan = FormUtil.getReadTxtField({
			    fieldLabel: '通知发送人',
			    name: 'notifyMan',
			    "width": 125,
			    "allowBlank": false,
			    value:CURENT_EMP
			});
			var bdat_notifyDate = FormUtil.getDateField({
			    fieldLabel: '通知日期',
			    name: 'notifyDate',
			    "width": 125,
			    "allowBlank": false,
			    value:CURENT_DATE()
			});
			
			var txt_phoneNum = FormUtil.getTxtField({
			    fieldLabel: '<span id = "phoneLabel">手机号码</span>',
			    name: 'phoneNum',
			    "width": 125,
			    "maxLength": "20"
			});
			
			var txt_email = FormUtil.getTxtField({
			    fieldLabel: '<span id = "emailLabel">Email</span>',
			    name: 'email',
			    "width": 125,
			    "maxLength": "50"
			});
			
			var txt_telNum = FormUtil.getTxtField({
			    fieldLabel: '<span id = "telNumLabel">电话号码</span>',
			    name: 'telNum',
			    "width": 125,
			    "maxLength": "20"
			});
			
			var txt_receiveMan = FormUtil.getTxtField({
			    fieldLabel: '接收人',
			    name: 'receiveMan',
			    "width": 600,
			    "allowBlank": false,
			    "maxLength": "150"
			});
			
			var txt_content = FormUtil.getTAreaField({
			    fieldLabel: '通知内容',
			    name: 'content',
			    "width": 600,
			    "allowBlank": false,
			    "maxLength": "1500"
			});
			
			var hid_bussType = FormUtil.getHidField({
			    fieldLabel: '业务类型',
			    name: 'bussType',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": 10,
			    value:'2'
			});
			
		
				
			var hid_notifySendMan = FormUtil.getHidField({
			    fieldLabel: '接收人类型',
			    name: 'notifySendMan',
			     value:'1'
			});
			
			
			var rad_status = FormUtil.getRadioGroup({
			    fieldLabel: '是否已通知客户',
			    name: 'status',
			    "width": 125,
			    "allowBlank": false,
			    items :[{
			    	boxLabel : '否' ,
			    	 name: 'status',
			    	 inputValue:0
			    },{
			    	boxLabel : '是' ,
			    	 name: 'status',
			    	 inputValue:1
			    }],
			    listeners : {
			    	change : function(rdgp,radCk){
			    		var val = rad_status.getValue();
			    		/*如果已通知客户，默认就不需要再让系统发送消息*/
			    		var autoSendVal = "2";
			    		if(val && parseInt(val) == 1){
			    			autoSendVal = "1";
			    		}
			    		check_isNotAutoSend.setValue(autoSendVal);
			    	}
			    } 
			});
			
			
			var check_isNotAutoSend = FormUtil.getCheckGroup({
			    name: 'autoSend',
			    "width": 200,
			    "maxLength": "10",
			    items : [
			    	{boxLabel: '通知保存后，由系统自动发送', name: 'autoSend',inputValue:2,checked:true}
				]
			});
			var btnSave = new Ext.Button({text : '保存',handler : function(){
					EventManager.frm_save(applyForm,{beforeMake:function(data){
						data.formId = _this.globalMgr.applyId;
						data.bussType = _this.globalMgr.bussType;
						data.notifyMan = CURRENT_USERID;
					},sfn:function(data){
						_this.finishBussCallback(data);
				 }});
				}
			});
			var btnPanel = this.globalMgr.getBtnPanel([check_isNotAutoSend,btnSave]);
			var layout_fields = [ hid_bussType,hid_notifySendMan,{
			    cmns: FormUtil.CMN_THREE,
			    fields: [check_notifysType, txt_notifyMan, bdat_notifyDate, txt_phoneNum, txt_email, txt_telNum,rad_status]},
			    {cmns: FormUtil.CMN_TWO_LEFT,fields: [txt_receiveMan,rad_status]},
			     txt_content,btnPanel
			];
			var frm_cfg = {
				autoScroll : true,
				 border : false,
				url : './sysMsgNotify_save.action',
				labelWidth : 115
			};
		var applyForm = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
		this.globalMgr.applyForm = applyForm;
		return applyForm;
	},
	
	/**
	 * 设置主面板的大小
	 * @param {} whArr
	 */
	changeSize :function(whArr){
		var width = whArr[0];
		var height = whArr[1];
		this.appPanel.setWidth(width);
		this.appPanel.setHeight(height);	
	},
	
	globalMgr:{
		bussType : 2 ,	/*1:流程审批记录[ts_AuditRecords],2:提前还款通知[fc_Prepayment]*/
		applyForm : null,
		 applyId : this.params.applyId,
		/**
		 * 加载数据
		 */	
		loadDatas : function(_this){
			EventManager.get('./sysMsgNotify_get.action',
					{params:{bussType:_this.globalMgr.bussType,applyId:_this.globalMgr.applyId},sfn : function(jsondata){
						if(jsondata!=1){
							_this.globalMgr.applyForm.setFieldValues(jsondata);
						}
			}})
		},
		getBtnPanel:function (buttons){
			var panel = new Ext.Panel({ buttonAlign : 'center',buttons : buttons,autoScroll: true});
			return panel;
		},
		/**
		 * 验证
		 * @param {} check_notifysType
		 * @param {} txt_phoneNum
		 * @param {} txt_email
		 * @param {} txt_telNum
		 */
		valiDate : function(check_notifysType,txt_phoneNum,txt_email,txt_telNum){
			var oldFieldLabels =[
					'<span id = "phoneLabel">手机号码</span>',
					'<span id = "emailLabel">Email</span>',
					'<span id = "telNumLabel">电话号码</span>' 
					];
				var value = check_notifysType.getValue();
				if(value){
					var val = [];
					val = value.split(",");
					if(val.indexOf(1)!=-1){
						txt_phoneNum.allowBlank = false;
						Ext.get("phoneLabel").update(FormUtil.REQUIREDHTML+oldFieldLabels[0]);
					}else{
						if(txt_phoneNum.allowBlank == false){
							txt_phoneNum.allowBlank = true;
							Ext.get("phoneLabel").update(oldFieldLabels[0]);
						}
					}
					if(val.indexOf(2)!=-1){
						txt_email.allowBlank = false;
						Ext.get("emailLabel").update(FormUtil.REQUIREDHTML+oldFieldLabels[1]);
					}else{
						if(txt_email.allowBlank == false){
							txt_email.allowBlank = true;
							Ext.get("emailLabel").update(oldFieldLabels[1]);
						}
					}
					if(val.indexOf(3)!=-1){
						txt_telNum.allowBlank = false;
						Ext.get("telNumLabel").update(FormUtil.REQUIREDHTML+oldFieldLabels[2]);
					}else{
						if(txt_telNum.allowBlank == false){
							txt_telNum.allowBlank = true;
							Ext.get("telNumLabel").update(oldFieldLabels[2]);
						}
					}
				}
		}
	},
	destroyCmpts :function(){
		if(null != this.appPanel){
			this.appPanel.destroy();
			this.appPanel = null;
		}
	}
});
