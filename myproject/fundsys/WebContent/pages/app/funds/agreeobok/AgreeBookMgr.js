/*同心日科技公司Erp 系统 命名空间*/
Ext.namespace("skythink.cmw.workflow.bussforms");
/**
 * 借款承诺书
 */
skythink.cmw.workflow.bussforms.AgreeBookMgr = function(){
	this.init(arguments[0]);
}

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.workflow.bussforms.AgreeBookMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		/*由必做或选做业务菜单传入的回調函数，主要功能：
		 * finishBussCallback : 当业务表单保存后，更新必做或选做事项为已做,
		 * unFinishBussCallback : 当删除业务表单后，取消已做标识
		 * */
		var finishBussCallback = tab.finishBussCallback;
		var unFinishBussCallback = tab.unFinishBussCallback;
		this.module = new Cmw.app.widget.AbsPanelView({
			tab : tab,
			params : params,/*apptabtreewinId,tabId,sysid*/
			getAppCmpt : this.getAppCmpt,
			getToolBar : this.getToolBar,
			createDetailPnl: this.createDetailPnl,
			changeSize : this.changeSize,
			destroyCmpts : this.destroyCmpts,
			globalMgr : this.globalMgr,
			refresh : this.refresh,
			prefix : Ext.id(),
			finishBussCallback : finishBussCallback,
			unFinishBussCallback : unFinishBussCallback
		});
	},
	/**
	 * 获取组件方法
	 */
	getAppCmpt : function(){
		var appPanel = new Ext.Panel({border : false,autoScroll:true});
		appPanel.add({items:[this.getToolBar(),this.createDetailPnl()]});
		return appPanel;
	},
	/**
	 * 刷新方法
	 * @param {} optionType
	 * @param {} data
	 */
	refresh:function(optionType,data){
		var _this = this;
		var activeKey = _this.globalMgr.activeKey;
		if(activeKey =="添加借款承诺书"||activeKey == "编辑借款承诺书"){
			if(activeKey =="添加借款承诺书"){
				_this.globalMgr.show();
				_this.globalMgr.AddBtn.disable();
				_this.globalMgr.EditBtn.enable();
				_this.globalMgr.DelBtn.enable();
			}
			_this.globalMgr.id = data.id;
			_this.globalMgr.detailPanel_1.reload({id:data.id});
			if(data.id) _this.finishBussCallback(data);
		}
		_this.globalMgr.activeKey = null;
	},
	/**
	 * 组件大小改变方法
	 * @param {} whArr
	 */
	changeSize : function(whArr){
		var width = CLIENTWIDTH - Ext.getCmp(ACCORDPNLID);
		var height = CLIENTHEIGHT - 180;
		this.appPanel.setWidth(width);
		this.appPanel.setHeight(height);
	},
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var self = this;
		var barItems = [{
			token :"添加借款承诺书", 
			text :Btn_Cfgs.AGREEBOOK_ADD_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.AGREEBOOK_ADD_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"添加借款承诺书",self:self});
			}
		},{
			token :"编辑借款承诺书", 
			text : Btn_Cfgs.AGREEBOOK_EDIT_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.AGREEBOOK_EDIE_TIP_BTN_TXT,
			handler : function(){
				EventManager.get('./fuAgreeBook_get.action',{params:{formId :self.globalMgr.applyId,customerId:self.globalMgr.customerId},sfn:function(json_data){
							self.globalMgr.winEdit.show({key:"编辑借款承诺书",optionType:OPTION_TYPE.EDIT,self:self});
				}});
			}
		},
			{
			text : Btn_Cfgs.AGREEBOOK_DATEIL_BTN_TXT,
			iconCls:Btn_Cfgs.PRINT_CLS,
			tooltip:Btn_Cfgs.AGREEBOOK_DATEIL_TIP_BTN_TXT,
			token : "打印借款承诺书",
			handler : function(){
				self.globalMgr.openPrintDialog({key:this.token,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		toolBar.hideButtons(Btn_Cfgs.LOAN_DOWNLOAD_BTN_TXT);
		/**
		 * 获取button 配置信息
		 * @return {}
		 */
		var buttons = toolBar.getButtons();
		for(var i=0,count=buttons.length; i<count; i++){
			var btnCfg = buttons[i];//.enable();
			if(btnCfg.text ==Btn_Cfgs.AGREEBOOK_ADD_TXT){
				self.globalMgr.AddBtn=btnCfg;
			}
			if(btnCfg.text==Btn_Cfgs.AGREEBOOK_EDIT_BTN_TXT){
				self.globalMgr.EditBtn=btnCfg;
			}
		}
		return toolBar;
	},
	/**
	 * 创建详情面板
	 */
	createDetailPnl : function(){
		var _this = this;
		var sysId = this.globalMgr.sysId;
		var htmlArrs_1 = [
						'<tr><th col="custType">客户类型</th> <td col="custType" >&nbsp;</td><th col="bman">出借人</th> <td col="bman" >&nbsp;</td><th col="yearLoan">借款期限(年)</th> <td col="yearLoan" >&nbsp;</td></tr>',
						'<tr><th col="monthLoan">借款期限(月)</th> <td col="monthLoan" >&nbsp;</td><th col="dayLoan">借款期限(日)</th> <td col="dayLoan" >&nbsp;</td><th col="overyLoan">超过期限(年)</th> <td col="overyLoan" >&nbsp;</td></tr>',
						'<tr><th col="overmLoan">超过期限(月)</th> <td col="overmLoan" >&nbsp;</td><th col="overdLoan">超过期限(日)</th> <td col="overdLoan" >&nbsp;</td><th col="uprate">上浮利率1</th> <td col="uprate" >&nbsp;</td></tr>',
						'<tr><th col="unint">上浮利率单位1</th> <td col="unint" >&nbsp;</td><th col="overyLoan2">超过期限2(年)</th> <td col="overyLoan2" >&nbsp;</td><th col="overmLoan2">超过期限2(月)</th> <td col="overmLoan2" >&nbsp;</td></tr>',
						'<tr><th col="overdLoan2">超过期限2(日)</th> <td col="overdLoan2" >&nbsp;</td><th col="uprate2">上浮利率2</th> <td col="uprate2" >&nbsp;</td><th col="unint2">上浮利率单位2</th> <td col="unint2" >&nbsp;</td></tr>',
						'<tr><th col="lateDays">逾期天数</th> <td col="lateDays" >&nbsp;</td><th col="pprate">罚息利率</th> <td col="pprate" >&nbsp;</td><th col="punint">罚息利率单位1</th> <td col="punint" >&nbsp;</td></tr>',
						'<tr><th col="domonthes">处置期限(月)</th> <td col="domonthes" >&nbsp;</td><th col="comitMan">承诺人</th> <td col="comitMan" >&nbsp;</td><th col="comitDate">承诺日期</th> <td col="comitDate" >&nbsp;</td></tr>'];
		var detailCfgs_1 = [{
		    cmns: 'THREE',
		    /* ONE , TWO , THREE */
		    model: 'single',
		    labelWidth: 110,
		    title : '委托合同详情', 
		    //详情面板标题
		    /*i18n : UI_Menu.appDefault,*/
		    //国际化资源对象
		    htmls: htmlArrs_1,
		    url: './fuAgreeBook_get.action',
//		    prevUrl: '#PREURLCFG#',
//		    nextUrl: '#NEXTURLCFG#',
	    params: {
	        formId: _this.globalMgr.applyId,
	        customerId:_this.globalMgr.customerId
	    },
//     	formDiyCfg : {sysId:sysId,formdiyCode:FORMDIY_IND.FORMDIY_LOAN,formIdName:'id'},
	    callback: {
	        sfn: function(jsonData) {
	        	_this.globalMgr.detailPanel_1.show();
	        	_this.globalMgr.AddBtn.disable();
	        }
	    }
	}];
	
	var detailPanel_1 = new Ext.ux.panel.DetailPanel({
	    autoWidth : true,
	    detailCfgs: detailCfgs_1,
	    hidden : true,
	    border : false,
	    attachLoad: function(params, cmd) {
	        /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
	        //Cmw.print(params);
	        //Cmw.print(cmd);
	    }
	});
		_this.globalMgr.detailPanel_1 = detailPanel_1;
		this.attachMentFs = this.globalMgr.createAttachMentFs(this);
		detailPanel_1.add(this.attachMentFs);
		return detailPanel_1;
	},
	/**
	 * 组件销毁方法
	 */
	destroyCmpts : function(){
		
	},
	globalMgr : {
		/**
		 * 创建附件FieldSet 对象
		 * 
		 * @return {}
		 */
			createAttachMentFs : function(_this) {
				/*
				 * ----- 参数说明： isLoad 是否实例化后马上加载数据 dir : 附件上传后存放的目录，
				 * mortgage_path 定义在 resource.properties 资源文件中 isSave :
				 * 附件上传后，是否保存到 ts_attachment 表中。 true : 保存 params :
				 * {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment
				 * 中的说明,formId:业务单ID，如果有多个用","号分隔} 当 isLoad = false 时，
				 * params 可在 reload 方法中提供 ----
				 */
				var uuid=Cmw.getUuid();
				var attachMentFs = new Ext.ux.AppAttachmentFs({
							title : '相关材料附件',
							isLoad : false,
							dir : 'mort_path',
							isSave : true,
							isNotDisenbaled : true,
							params:{sysId:-1,formType:ATTACHMENT_FORMTYPE.FType_40,formId:uuid}
						});
				return attachMentFs;
			},
		/**
		 * 显示面板详情
		 * @return {}
		 */
		show: function(){
			this.detailPanel_1.show();
		},
//		hide : function(){
//			this.detailPanel_1.hide();
//		},
		AddBtn :null,
		DelBtn : null,
		EditBtn : null,
		id : null,
		customerId :this.params.customerId,
		activeKey: null,
		applyId:this.params.applyId,
		sysId : this.params.sysid,
		detailPanel_1 : null,
		winEdit : {
			show : function(parentCfg){
				var _this = parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent = null;
				parent={
					sysid : _this.globalMgr.sysId,
					applyid:_this.globalMgr.applyId,
					customerId : _this.globalMgr.customerId
				};
				parentCfg.parent = parent;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule=null;
					if(winkey=="添加借款承诺书" || winkey=="编辑借款承诺书"){
						winModule="AgreeBookEdit";
					}
					Cmw.importPackage('pages/app/funds/agreeobok/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		},
		/**
		 * 打开打印窗口
		 */
		openPrintDialog : function(parentCfg){
			var _this = parentCfg.self;
			Cmw.print(_this.params);
			var winkey=parentCfg.key;
			_this.globalMgr.activeKey = winkey;
			var printType = 2;/*打印有数据的合同文档*/
			var contractId = _this.globalMgr.sysId;
			var _params = {contractId:contractId,printType:printType};
			parentCfg.params = _params;
			if(_this.appCmpts[winkey]){
				_this.appCmpts[winkey].show(parentCfg);
			}else{
				EventManager.get("./fcFuntempCfg_getTidByMid.action",{params:{menuId:_this.params.nodeId},sfn : function(jsonData){
					Cmw.print(jsonData);
					parentCfg.tempId=jsonData.list[0].tempId;
					Cmw.importPackage('pages/app/dialogbox/PrintDialogbox',function(module) {
							 	_this.appCmpts[winkey] = module.DialogBox;
							 	_this.appCmpts[winkey].show(parentCfg);
					  		});
						}});
			}
		}
	}
});

