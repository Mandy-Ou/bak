/*同心日科技公司Erp 系统 命名空间*/
Ext.namespace("skythink.cmw.workflow.bussforms");
/**
 * 借款合同
 */
skythink.cmw.workflow.bussforms.LoancontractMgr = function(){
	this.init(arguments[0]);
}

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.workflow.bussforms.LoancontractMgr,Ext.util.MyObservable,{
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
		var appPanel = new Ext.Panel({border : false});
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
		if(activeKey =="添加借款合同"||activeKey == "编辑借款合同"){
			if(activeKey =="添加借款合同"){
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
		/*var width = whArr[0];
		var height = whArr[1]-2;
		this.appPanel.setWidth(width);
		this.appPanel.setHeight(height);*/
	},
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var self = this;
		var barItems = [{
			text :Btn_Cfgs.LOAN_ADD_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.LOAN_ADD_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"添加借款合同",self:self});
			}
		},{type:"sp"},{
			text : Btn_Cfgs.LOAN_EDIT_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.LOAN_EDIE_TIP_BTN_TXT,
			handler : function(){
				EventManager.get('./fcLoanInvoce_list.action',{params:{formId :self.globalMgr.formId,custType:self.globalMgr.custType},sfn:function(json_data){
					if(json_data.totalSize==0 || json_data.list == null){
							self.globalMgr.winEdit.show({key:"编辑借款合同",optionType:OPTION_TYPE.EDIT,self:self});
					}else{
						ExtUtil.alert({msg:"已经有放款单,不能进行修改和删除！"});
						return;
					}
				}});
				
			}
		},{type:"sp"},{
			text : Btn_Cfgs.LOAN_DEL_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.LOAN_DEL_TIP_BTN_TXT,
			handler : function(){
				EventManager.get('./fcLoanInvoce_list.action',{params:{formId :self.globalMgr.formId,custType:self.globalMgr.custType},sfn:function(json_data){
					if(json_data.totalSize==0 || json_data.list == null){
							ExtUtil.confirm({title:'系统提示',msg:'确定删除现有的借款合同？',fn:btuclick});
							function btuclick(){
							if(arguments && arguments[0] != 'yes') return;
								var _params = {formId : self.globalMgr.formId};
								EventManager.get('./fcLoanContract_delete.action',{params:_params,sfn:function(jsondata){
									if(jsondata==-1){
										ExtUtil.alert({msg:"该借款合同已有借款单不能进行删除！"});
										return;
									}
									self.globalMgr.hide();
									self.globalMgr.AddBtn.enable();
									self.globalMgr.EditBtn.disable();
									self.globalMgr.DelBtn.disable();
									self.unFinishBussCallback(_params);
								}},{optionType:OPTION_TYPE.DEL,self:self});
						};
					}else{
						ExtUtil.alert({msg:"已经有放款单,不能进行修改和删除！"});
						return;
					}
				}});
				}
		},{
			text : Btn_Cfgs.LOAN_SC_BTN_TXT,
			iconCls:'page_query',
			tooltip:Btn_Cfgs.LOAN_SC_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.query(self);
			}
		},
			{
			text : Btn_Cfgs.LOAN_DATEIL_TIP_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.LOAN_DATEIL_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"打印借款合同",self:self});
			}
		},{
			text : Btn_Cfgs.LOAN_DOWNLOAD_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.LOAN_DOWNLOAD_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"借款合同模板下载",optionType:OPTION_TYPE.EDIT,self:self});
			}
		}];
		
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
		toolBar.hideButtons(Btn_Cfgs.LOAN_SC_BTN_TXT+','+Btn_Cfgs.LOAN_DATEIL_TIP_BTN_TXT+','+Btn_Cfgs.LOAN_DOWNLOAD_BTN_TXT);
		/**
		 * 获取button 配置信息
		 * @return {}
		 */
		var buttons = toolBar.getButtons();
		for(var i=0,count=buttons.length; i<count; i++){
			var btnCfg = buttons[i];//.enable();
			if(btnCfg.text ==Btn_Cfgs.LOAN_ADD_TXT){
				self.globalMgr.AddBtn=btnCfg;
			}
			if(btnCfg.text==Btn_Cfgs.LOAN_EDIT_BTN_TXT){
				self.globalMgr.EditBtn=btnCfg;
			}
			if(btnCfg.text==Btn_Cfgs.LOAN_DEL_BTN_TXT){
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
			var htmlArrs_1 = [
							'<tr>' +
								'<th col="code">合同编号</th> <td col="code" >&nbsp;</td>' +
								'<th col="borBank">借款银行</th> <td col="borBank" >&nbsp;</td>' +
								'<th col="borAccount">借款帐号</th> <td col="borAccount" >&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								'<th col="payBank">还款银行</th> <td col="payBank" >&nbsp;</td>' +
								'<th col="payAccount">还款帐号</th> <td col="payAccount" >&nbsp;</td>' +
								'<th col="accName">帐户户名</th> <td col="accName" >&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								'<th col="doDate">合同签订日期</th> <td col="doDate" >&nbsp;</td>' +
								'<th col="loanLimit">贷款期限(年)</th> <td col="loanLimit" >&nbsp;</td>' +
								'<th col="payDate">合约放款日期</th> <td col="payDate" >&nbsp;</td>' +
							'<tr>'+ 
								'<th col="payDay">每期还款日</th> <td col="payDay" >&nbsp;</td>' +
								'<th col="payType">还款方式</th> <td col="payType" >&nbsp;</td>' +
								'<th col="endDate">贷款截止日期</th> <td col="endDate" >&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								'<th col="rateType">利率类型</th> <td col="rateType" >&nbsp;</td>' +
								'<th col="rate">贷款利率</th> <td col="rate" >&nbsp;</td>' +
								'<th col="isadvance">是否预收息</th> <td col="isadvance" >&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								'<th col="mgrtype">管理费收取方式</th> <td col="mgrtype" >&nbsp;</td>' +
								'<th col="mrate">管理费率</th> <td col="mrate" >&nbsp;</td>' +
								'<th col="prate">放款手续费率</th> <td col="prate" >&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								'<th col="arate">提前还款费率</th> <td col="arate" >&nbsp;</td>' +
								'<th col="urate">罚息利率</th> <td col="urate" >&nbsp;</td>' +
								'<th col="frate">滞纳金利率</th> <td col="frate" >&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								'<th col="appAmount">贷款金额</th> <td col="appAmount" colspan=5>&nbsp;</td>' +
							'</tr>', 
							'<tr height = "80">' +
								'<th col="clause">合同中未涉及条款</th> <td col="clause"  colspan=5 >&nbsp;</td>' +
							'</tr>'
							];
		var detailCfgs_1 = [{
		    cmns: 'THREE',
		    /* ONE , TWO , THREE */
		    model: 'single',
		    labelWidth: 110,
		    title : '借款合同详情', 
		    //详情面板标题
		    /*i18n : UI_Menu.appDefault,*/
		    //国际化资源对象
		    htmls: htmlArrs_1,
		    url: './fcLoanContract_get.action',
//		    prevUrl: '#PREURLCFG#',
//		    nextUrl: '#NEXTURLCFG#',
		    params: {
		        formId: _this.globalMgr.formId
		    },
		    callback: {
		        sfn: function(jsonData) {
		            /* 可在对页面进行赋值前，对数据进行转换处理 to do .. */
		            //jsonData["leaf"] = (jsonData["leaf"]=="false") ? "否" : "是";
		        	if(jsonData==-1){
		        		_this.globalMgr.EditBtn.disable();
						_this.globalMgr.DelBtn.disable();
		        	}else{
		        		_this.globalMgr.AddBtn.disable();
		        		_this.globalMgr.show();
		        	}
		        	var appAmount = jsonData["appAmount"];
		        	var yearLoan = jsonData["yearLoan"];
		        	var monthLoan = jsonData["monthLoan"];
		        	var dayLoan = jsonData["dayLoan"];
		        	var arr = [];
				 	if(yearLoan && yearLoan>0){
				 		arr[arr.length] = yearLoan+'年';
				 	}
				 	if(monthLoan && monthLoan>0){
				 		arr[arr.length] = monthLoan+'个月';
				 	}
				 	if(dayLoan && dayLoan>0){
				 		arr[arr.length] = dayLoan+'天';
				 	}
		        	jsonData["loanLimit"] = (arr.length > 0) ? arr.join("") : "";
		        	
		        	jsonData["rate"] =  jsonData["rate"]+'%';
		        	jsonData["mrate"] =  jsonData["mrate"]+'%';
		        	jsonData["prate"] =  jsonData["prate"]+'%';
		        	jsonData["arate"] =  jsonData["arate"]+'%';
		        	jsonData["urate"] =  jsonData["urate"]+'%';
		        	jsonData["frate"] =  jsonData["frate"]+'%';
		        	jsonData["payType"] =  Render_dataSource.payTypeRender(jsonData["payType"]);
		        	jsonData["rateType"] =  Render_dataSource.rateTypeRender(jsonData["rateType"]);
		        	jsonData["isadvance"] = (jsonData["isadvance"]) ? "是":"否";
		        	jsonData["mgrtype"] =  Render_dataSource.rateTypeRender(jsonData["mgrtype"]);
		        	jsonData["appAmount"] =  Cmw.getThousandths(appAmount)+'元&nbsp;&nbsp;<span style="color:red;"><br><b>(大写：'+Cmw.cmycurd(appAmount)+')</b></span>';
		        	var payDay = jsonData["payDay"];
		        	var setdayType = jsonData["setdayType"];
		        	switch (setdayType){
		        		case 1 : setdayType = "实际放款日作为结算日";break;
		        		case 2 : setdayType = "公司规定的结算日";break;
		        		case 3 : setdayType = "其它结算日";break;
		        		default : setdayType = "";break;
		        	}
		        	if(payDay){
		        		jsonData["payDay"] =  setdayType+"("+payDay+")"+'号&nbsp;&nbsp';
		        	}else{
		        		jsonData["payDay"] = setdayType+"";
		        	}
		        	
		        	var sysId = _this.params.sysid;
		        	var formId = jsonData.id;
		        	if(!formId){
		        		formId = -1;
		        	}
		        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_8,sysId:sysId,isNotDisenbaled:true};
		        	_this.attachMentFs.reload(params);
		        }
		    }
		}];
		
		var attachLoad = function(params, cmd) {
		    /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
		    //Cmw.print(params);
		    //Cmw.print(cmd);
		}
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
		 * @return {}
		 */
		createAttachMentFs : function(_this){
			/* ----- 参数说明： isLoad 是否实例化后马上加载数据 
			 * dir : 附件上传后存放的目录， mortgage_path 定义在 resource.properties 资源文件中
			 * isSave : 附件上传后，是否保存到 ts_attachment 表中。 true : 保存
			 * params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔}
			 *    当 isLoad = false 时， params 可在 reload 方法中提供
			 *  ---- */
			var attachMentFs = new  Ext.ux.AppAttachmentFs({title:'相关材料附件',isLoad:false,dir : 'mort_path',isSave:true,isNotDisenbaled:true});
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
		AddBtn :null,
		DelBtn : null,
		EditBtn : null,
		id : null,
		custType : this.params.custType,
		customerId :  this.params.customerId,
		activeKey: null,
		formId:this.params.applyId,
		sysId : this.params.sysId,
		detailPanel_1 : null,
			winEdit : {
			show : function(parentCfg){
				var _this = parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent =null;
				parent={
					sysid : _this.globalMgr.sysId,
					formId:_this.globalMgr.formId,
					AddBtn:_this.globalMgr.AddBtn,
					custType:_this.globalMgr.custType,
					customerId : _this.globalMgr.customerId
				};
				parentCfg.parent = parent;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{ 
					var winModule=null;
					if(winkey=="添加借款合同" || winkey=="编辑借款合同"){
						winModule="LoanContractEdit";
					}
					Cmw.importPackage('/pages/app/workflow/variety/formUIs/loancontract/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		}
	}
});

