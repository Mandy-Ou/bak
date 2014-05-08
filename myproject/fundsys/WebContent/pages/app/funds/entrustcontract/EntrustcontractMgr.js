/*同心日科技公司Erp 系统 命名空间*/
Ext.namespace("skythink.cmw.workflow.bussforms");
/**
 * 委托合同
 */
skythink.cmw.workflow.bussforms.EntrustcontractMgr = function(){
	this.init(arguments[0]);
}
/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.workflow.bussforms.EntrustcontractMgr,Ext.util.MyObservable,{
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
		var _this=this;
		var applyId=this.params.applyId;
		EventManager.get('./fuAmountApply_get.action', {//fuEntrustContract_list
			params : {
				id : applyId
			},
			sfn : function(json_data) {
			_this.globalMgr.onel=json_data;
			}
		});
		var appPanel = new Ext.Panel({border : false,autoScroll:true});
		appPanel.add({items:[this.getToolBar(),this.createDetailPnl()]});
		var params ={sysId:-1,formType:ATTACHMENT_FORMTYPE.FType_41,formId:_this.params.applyId}
		_this.attachMentFs.reload(params);
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
		if(activeKey =="添加委托合同"||activeKey == "编辑委托合同"){
			if(activeKey =="添加委托合同"){
				_this.globalMgr.show();
				_this.globalMgr.AddBtn.disable();
				_this.globalMgr.EditBtn.enable();
			}
			_this.globalMgr.id = data.id;
			var params ={sysId:-1,formType:ATTACHMENT_FORMTYPE.FType_41,formId:_this.params.applyId}
			_this.attachMentFs.reload(params);
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
			token :"添加委托合同", 
			text :Btn_Cfgs.LOANCON_ADD_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.LOANCON_ADD_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"添加委托合同",self:self});
			}
		},{
			token :"编辑委托合同", 
			text : Btn_Cfgs.LOANCON_EDIT_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.LOANCON_EDIE_TIP_BTN_TXT,
			handler : function(){
					self.globalMgr.winEdit.show({key:"编辑委托合同",optionType:OPTION_TYPE.EDIT,self:self});
			}
		},
			{
			text : Btn_Cfgs.LOANCON_DATEIL_BTN_TXT,
			iconCls:Btn_Cfgs.PRINT_CLS,
			tooltip:Btn_Cfgs.LOANCON_DATEIL_TIP_BTN_TXT,
			token : "打印委托合同",
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
			if(btnCfg.text ==Btn_Cfgs.LOANCON_ADD_TXT){
				self.globalMgr.AddBtn=btnCfg;
			}
			if(btnCfg.text==Btn_Cfgs.LOANCON_EDIT_BTN_TXT){
				self.globalMgr.EditBtn=btnCfg;
			}
			/*	if(btnCfg.text==Btn_Cfgs.LOANCON_DEL_BTN_TXT){
				self.globalMgr.DelBtn=btnCfg;
			}*/
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
					'<tr><th col="code">合同编号</th> <td col="code" >&nbsp;</td><th col="payBank">收款银行</th> <td col="payBank" >&nbsp;</td><th col="payAccount">收款账号</th> <td col="payAccount" >&nbsp;</td></tr>',
					'<tr><th col="accName">账户名</th> <td col="accName" >&nbsp;</td><th col="appAmount">委托金额</th> <td col="appAmount" >&nbsp;</td><th col="productsId">委托产品</th> <td col="productsId" >&nbsp;</td></tr>',
					'<tr><th col="doDate">合同签订日期</th> <td col="doDate" >&nbsp;</td><th col="payDate">委托生效日期</th> <td col="payDate" >&nbsp;</td><th col="endDate">委托失效日期</th> <td col="endDate" >&nbsp;</td></tr></tr>',
					'<tr><th col="payDay">结算日</th> <td col="payDay">&nbsp;</td><th col="prange">委托产品范围</th><td col="prange">&nbsp;</td><th col="iamount">每月收益金额</th> <td col="iamount" >&nbsp;</td></tr></tr>',
					'<tr><th col="rate">利率</th> <td col="rate">&nbsp;</td><th col="delayCount">延期次数</th><td col="delayCount">&nbsp;</td><th col="backDate">撤资日期</th> <td col="backDate" >&nbsp;</td></tr></tr>',
					'<tr><th col="bstatus">撤资状态</th> <td col="bstatus">&nbsp;</td><th col="bamount">撤资金额</th><td col="bamount">&nbsp;</td><th col="uamount">可用委托金额</th> <td col="uamount" >&nbsp;</td></tr></tr>'];
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
		    url: './fuEntrustContract_get.action',
//		    prevUrl: '#PREURLCFG#',
//		    nextUrl: '#NEXTURLCFG#',
		    params: {
		        applyId: _this.globalMgr.formId
		    },
//     	formDiyCfg : {sysId:sysId,formdiyCode:FORMDIY_IND.FORMDIY_LOAN,formIdName:'id'},
	    callback: {
	        sfn: function(jsonData) {
	          _this.globalMgr.AddBtn.disable();
		  	 _this.globalMgr.show();
		      if(jsonData["prange"]='2'){
		       	  jsonData["prange"]='指定产品';
		      }
		      if(jsonData["prange"]='1'){
		       	  jsonData["prange"]='所有产品';
		      }
		      jsonData["appAmount"]= Cmw.getThousandths(jsonData["appAmount"])+'元';
		      jsonData["payDay"] = jsonData["payDay"]+'号';
		      jsonData["iamount"] = Cmw.getThousandths(jsonData["iamount"])+'元';
		      jsonData["uamount"] = Cmw.getThousandths(jsonData["uamount"])+'元';
		      var tdote=new Date(jsonData["doDate"]);
		      jsonData["doDate"]=Ext.util.Format.date(tdote,'Y-m-d');
		      var tpayDate=new Date(jsonData["payDate"]);
		      jsonData["payDate"]=Ext.util.Format.date(tpayDate,'Y-m-d');
		      var tendDate=new Date(jsonData["endDate"]);
		      jsonData["endDate"]=Ext.util.Format.date(tendDate,'Y-m-d');
		       jsonData["rate"] =jsonData["rate"]+Render_dataSource.rateUnit_datas(jsonData["rate"]);
		  	  jsonData["bstatus"]=Render_dataSource.sDetailRender(jsonData["bstatus"]);
		      }
		      
	        }
	    }
	];
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
//				var uuid=Cmw.getUuid();
				var uuid=_this.params.applyId
				var attachMentFs = new Ext.ux.AppAttachmentFs({
							title : '相关材料附件',
							isLoad : false,
							dir : 'mort_path',
							isSave : true,
							isNotDisenbaled : true,
							params:{sysId:-1,formType:ATTACHMENT_FORMTYPE.FType_41,formId:uuid}
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
		onel:null,
		entrustCustId :  this.params.entrustCustId,
		activeKey: null,
		formId:this.params.applyId,
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
					applyid:_this.globalMgr.formId,
					onel:_this.globalMgr.onel,
					entrustCustId : _this.globalMgr.entrustCustId
				};
				parentCfg.parent = parent;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule=null;
					if(winkey=="添加委托合同" || winkey=="编辑委托合同"){
						winModule="EntrustcustEdit";
					}
					Cmw.importPackage('pages/app/funds/entrustcontract/'+winModule,function(module) {
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

