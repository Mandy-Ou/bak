/*同心日科技公司Erp 系统 命名空间*/
Ext.namespace("skythink.cmw.workflow.bussforms");
/**
 * 保证合同
 */
skythink.cmw.workflow.bussforms.GuaContractMgr = function(){
	this.init(arguments[0]);
}
/**
 * 保证合同
 * @class skythink.cmw.workflow.bussforms.GuaContractMgr
 * @extends Ext.util.MyObservable
 */
/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.workflow.bussforms.GuaContractMgr,Ext.util.MyObservable,{
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
		var appPanel = new Ext.Panel();
		var gvlistId = FORMDIY_IND.FORMDIY_GUA;
		this.globalMgr.createMgDetailByGvlistId(this,gvlistId);
		appPanel.add(this.getToolBar());
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
		if(activeKey =="添加保证合同"||activeKey == "编辑保证合同"){
			if(activeKey =="添加保证合同"){
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
			token :"添加保证合同", 
			text :  Btn_Cfgs.GUA_ADD_TXT,
			iconCls:'page_add',
			tooltip: Btn_Cfgs.GUA_ADD_TIP_BTN_TXT,
			handler : function(){
				EventManager.get('./fcLoanContract_get.action',{params:{ formId :  self.globalMgr.formId},sfn :function(data){
					if(data==-1){
						ExtUtil.alert({msg:"没有添加借款合同不能进行添加保证合同！"});
						return;
					}
					self.globalMgr.winEdit.show({key:"添加保证合同",self:self});
				}});
			}
		},{type:"sp"},{
			token :"编辑保证合同", 
			text :  Btn_Cfgs.GUA_EDIT_BTN_TXT,
			iconCls:'page_edit',
			tooltip: Btn_Cfgs.GUA_EDIE_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"编辑保证合同",optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{type:"sp"},{
			token :"删除保证合同", 
			text : Btn_Cfgs.GUA_DEL_BTN_TXT,
			iconCls:'page_delete',
			tooltip: Btn_Cfgs.GUA_DEL_TIP_BTN_TXT,
			handler : function(){
				ExtUtil.confirm({title:'系统提示',msg:'确定删除现有的保证合同？',fn:btuclick});
				function btuclick(){
					if(arguments && arguments[0] != 'yes') return;
					var _params = {id : self.globalMgr.id};
					EventManager.get('./fcGuaContract_delete.action',{params:_params},{optionType:OPTION_TYPE.DEL,self:self});
					self.globalMgr.hide();
					self.globalMgr.EditBtn.disable();
					self.globalMgr.DelBtn.disable();
					self.globalMgr.AddBtn.enable();
					self.unFinishBussCallback(_params);
				};}
		},{type:"sp"},{
			text : Btn_Cfgs.GUA_SC_BTN_TXT,
			iconCls:Btn_Cfgs.PRINT_CLS,
			tooltip: Btn_Cfgs.GUA_SC_TIP_BTN_TXT,
			token : "生成保证合同",
			handler : function(){
				self.globalMgr.openPrintDialog({key:this.token,self:self});
			}
		},
			{
			text : Btn_Cfgs.GUA_DATEIL_BTN_TXT,
			iconCls:Btn_Cfgs.PRINT_CLS,
			tooltip:Btn_Cfgs.GUA_DATEIL_TIP_BTN_TXT,
			token : "打印保证合同",
			handler : function(){
				self.globalMgr.openPrintDialog({key:this.token,self:self});
			}
		},{
			token :"保证合同模板下载", 
			text :  Btn_Cfgs.GUA_DOWNLOAD_BTN_TXT,
			iconCls:'page_edit',
			tooltip: Btn_Cfgs.GUA_DOWNLOAD_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"保证合同模板下载",optionType:OPTION_TYPE.EDIT,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		toolBar.hideButtons(Btn_Cfgs.GUA_DOWNLOAD_BTN_TXT);
		/**
		 * 获取button 配置信息
		 * @return {}
		 */
		var buttons = toolBar.getButtons();
		for(var i=0,count=buttons.length; i<count; i++){
			var btnCfg = buttons[i];//.enable();
			if(btnCfg.text==Btn_Cfgs.GUA_ADD_TXT){
				self.globalMgr.AddBtn=btnCfg;
			}
			if(btnCfg.text==Btn_Cfgs.GUA_EDIT_BTN_TXT){
				self.globalMgr.EditBtn=btnCfg;
			}
			if(btnCfg.text==Btn_Cfgs.GUA_DEL_BTN_TXT){
				self.globalMgr.DelBtn=btnCfg;
			}
		}
		return toolBar;
	},
	
		/**
		 * 创建详情面板
		 */
		createDetailPnl : function(){
			var _this = this;
			var gvlistId = this.globalMgr.gvlistid;
			this.globalMgr.sysId = this.params.sysid;
			sysId = this.globalMgr.sysId;
			var htmlArrs_1 = [
							'<tr>' +
								'<th col="code">保证合同编号</th> <td col="code" >&nbsp;&nbsp;</td>' +
								'<th col="borrCode">借款合同号</th> <td col="code" >&nbsp;&nbsp;</td>' +
								'<th col="assMan" >被担保人</th> <td col="assMan" >&nbsp;&nbsp;</td>' +
							'</tr>',
							'<tr>' +
								'<th col="startDate" >贷款起始日期</th> <td col="startDate" >&nbsp;&nbsp;</td>' +
								'<th col="endDate" >贷款截止日期</th> <td col="endDate" >&nbsp;&nbsp;</td>' +
								'<th col="sdate">合同签订日期</th> <td col="sdate" >&nbsp;&nbsp;</td>' +
							'</tr>',
							'<tr>' +
								'<th col="rate" >贷款利率</th> <td col="rate" >&nbsp;&nbsp;</td>' +
								'<th col="appAmount" >担保金额</th> <td col="appAmount" colspan=3>&nbsp;&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								'<th col="guarantorIds" >保证人列表</th> <td col="guarantorIds" colspan=5>&nbsp;&nbsp;</td>' +
							'</tr>',
							'<tr>' +
								'<th col="clause" >合同中未涉及条款</th> <td col="clause"  colspan=5 >&nbsp;&nbsp;</td>' +
							'</tr>',	
							FORMDIY_DETAIL_KEY,
							'<tr height="50">'+ 
								'<th col="remark">备注</th> <td col="remark"colspan=5 >&nbsp;&nbsp;</td>' +
							'</tr>'
							];
		var detailCfgs_1 = [{
		    cmns: 'THREE',
		    model: 'single',
		    labelWidth: 100,
		    title : '保证合同详情', 
		    htmls: htmlArrs_1,
		    url: './fcGuaContract_detail.action',
		    params: {
		        formId: _this.globalMgr.formId
		    },
		     	formDiyCfg : {sysId:sysId,formdiyCode:FORMDIY_IND.FORMDIY_GUA,formIdName:'id'},
		    callback: {
		        sfn: function(jsonData) {
		        	if(jsonData==-1){
		        		_this.globalMgr.EditBtn.disable();
						_this.globalMgr.DelBtn.disable();
		        	}else{
		        		_this.globalMgr.AddBtn.disable();
		        		_this.globalMgr.show();
		        	}
		        	_this.globalMgr.id = jsonData['id'];
		        	if(jsonData["guarantorIds"]!=null){
		        		jsonData["guarantorIds"] ='<span style="color:blue;font-weight:bold;">'+jsonData["guarantorIds"]+'</span>';
		        	}
		        	var appAmount = jsonData["appAmount"];
		        	if(jsonData["rate"]!=null){
		        		jsonData["rate"] = '<span style="color : #FFB830;font-weight:bold;">'+jsonData["rate"]+'%'+'</span>';
		        	}
		        	if(jsonData["appAmount"]!=null){
		        		jsonData["appAmount"] =  Cmw.getThousandths(appAmount)+'元&nbsp;&nbsp;<span style="color:red;font-weight:bold;"><br>(大写：'+Cmw.cmycurd(appAmount)+')</span>';
		        	}
		        	var sysId = _this.params.sysid;
		        	var formId = jsonData.id;
		        	if(!formId){
		        		formId = -1;
		        	}
		        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_7,sysId:sysId,isNotDisenbaled:true};
		        	_this.attachMentFs.reload(params);
		        }
		    }
		}];
		
		var detailPanel_1 = new Ext.ux.panel.DetailPanel({
		    autoWidth : true,
		    hidden : true,
		    detailCfgs: detailCfgs_1
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
		 * 根据用户点击的抵/质押物基础数据创建详情面板
		 * @param {} _this
		 * @param {} gvlistId
		 */
		createMgDetailByGvlistId : function(_this,gvlistId){
			_this.globalMgr.gvlistid = gvlistId;
			var detailPanel = _this.createDetailPnl();
			_this.globalMgr.detailPanel_1 = detailPanel;
			_this.addListener('render',function(pnl){
				_this.appPanel.add(_this.globalMgr.detailPanel_1);
				_this.appPanel.doLayout();
			});
		},
		/**
		 * 创建附件FieldSet 对象
		 * @return {}
		 */
		createAttachMentFs : function(_this){
			/* ----- 参数说明： isLoad 是否实例化后马上加载数据 
			 * dir : 附件上传后存放的目录， mortgage_path 定义在 resource.properties 资源文件中
			 * isSave : 附件上传后，是否保存到 ts_attachment 表中。 true : 保存
			 * params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔}
			 *    当 isLoad = false 时， params 可在 reload 方法中提供
			 *  ---- */
			var attachMentFs = new  Ext.ux.AppAttachmentFs({title:'保证合同附件上传',isLoad:false,dir : 'gua_path',isSave:true,isNotDisenbaled:true});
			return attachMentFs;
		},
		/**
		 * 显示面板详情
		 * @return {}
		 */
		show: function(){
			this.detailPanel_1.show();
		},
		hide : function(){
			this.detailPanel_1.hide();
		},
		AddBtn : null,
		gvlistId : null,
		DelBtn : null,
		EditBtn : null,
		custType : this.params.custType,
		customerId :  this.params.customerId,
		baseId : this.params.baseId,
		id : null,
		sysId : null,
		activeKey: null,
		formId:this.params.applyId,
		detailPanel_1 : null,
			/**
		 * 打开打印窗口
		 */
		openPrintDialog : function(parentCfg){
			var _this = parentCfg.self;
			var winkey=parentCfg.key;
			_this.globalMgr.activeKey = winkey;
			var printType = 1;/*打印空白合同文档*/
			if(winkey == '生成保证合同'){
				printType = 2;/*打印有数据的合同文档*/
				if(!_this.globalMgr.id){
					ExtUtil.alert({msg : '生成保证合同之前，必须先添加保证合同!'});
					return;				
				}
			}
			var tempFileName = "OneContractDsCfg";/*个人客户XML数据源配置文件名*/
			if(_this.globalMgr.custType == 1){/*企业客户*/
				tempFileName = "EntContractDsCfg";
			}
			var contractId = _this.globalMgr.id;
			var custType = _this.globalMgr.custType;
			var _params = {tempFileName:tempFileName,printType:printType,contractId:contractId,custType:custType};
			parentCfg.params = _params;
			if(_this.appCmpts[winkey]){
				_this.appCmpts[winkey].show(parentCfg);
			}else{
				EventManager.get("./fcFuntempCfg_getTidByMid.action",{params:{menuId:_this.params.nodeId},sfn : function(jsonData){
							parentCfg.tempId=jsonData.list[0].tempId;
							Cmw.importPackage('pages/app/workflow/variety/formUIs/gua/PrintGuaTemp',function(module) {
							 	_this.appCmpts[winkey] = module.DialogBox;
							 	_this.appCmpts[winkey].show(parentCfg);
					  		});
						}});
			}
		},
		winEdit : {
			show : function(parentCfg){
				var _this = parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				parentCfg.gvlistid = _this.globalMgr.gvlistid;
				parentCfg.sysId =_this.params.sysid;
				var parent =null;
				parent={
					formId:_this.globalMgr.formId,detailPanel_1:_this.globalMgr.detailPanel_1,
					custType : _this.globalMgr.custType,customerId : _this.globalMgr.customerId
				};
				parentCfg.parent = parent;
//				if(_this.appCmpts[winkey]){
//					_this.appCmpts[winkey].show(parentCfg);
//				}else{ 
					var winModule=null;
					if(winkey=="添加保证合同" || winkey=="编辑保证合同"){
						winModule="GuaContractEdit";
					}
					Cmw.importPackage('pages/app/workflow/variety/formUIs/gua/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		}
//	}
});

