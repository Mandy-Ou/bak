/**
 * 展期协议书子流程组装面板
 * @author 彭登浩
 * @date 2013-9-23
 */
define(function(require, exports) {
	exports.moduleUI = {
		isHistoryReload : false,
		extensionId : null,
		extContractDetailPanel : null,
		optionType : null,/* 默认为新增  */
		extContId : null,
		contractId : null,
		sysId : null,
		editid : null,
		finishBussCallback : null,
		isNotHidden :true,//是否隐藏extplan 和extContractPnl
		saveBtn : null,
		editBtn : null,
		addBtn : null,
		
		parentPanel : null,
		appMainPanel : null,
		customerModule : null,
		customerPanel : null,
		extContractDetailMod : null,
		extContractFrmMod : null,
		applyPanel : null,
		extPlanGrid : null,
		attachMentFs : null,
		params : null,
		uuid : Cmw.getUuid(),/*用于新增时，临时代替申请单ID*/
		applyId : null,/*申请单ID*/
		seDateDisplayId : Ext.id(null,'seDateDisplayId'),/*显示展期期限的控件ID*/
		btnIdObj : {
			btnAdd : Ext.id(null,"btnAddId"),//添加
			btnEdit : Ext.id(null,"btnEditId"),//编辑
			btnSave : Ext.id(null, 'btnSave'),/* 保存 */
			
			btnChoseCust : Ext.id(null, 'btnChoseCust'),/* 展期客户选择 */
			btnAddGopinion : Ext.id(null, 'btnAddGopinion'),/*添加意见 */
			btnEditGopinion : Ext.id(null, 'btnEditGopinion'),/* 修改意见 */
			btnDelGopinion : Ext.id(null, 'btnDelGopinion'),/* 删除意见 */
			btnTempSave : Ext.id(null, 'btnTempSave')/* 暂存申请单 */
			
		},
		setParams : function(_this) {
			this.optionType = _this.params.optionType ? _this.params.optionType : OPTION_TYPE.ADD;
			if(this.optionType == OPTION_TYPE.DETAIL){
				this.isNotHidden = false;
				this.editid =  _this.params.id;
			}
			this.extensionId = _this.params.extensionId;
			this.params = params;
			this.contractId = _this.params.contractId;
			this.sysId = _this.params.sysId;
			this.addBtn = _this.params.addBtn;
			this.saveBtn = _this.params.saveBtn;
			this.editBtn = _this.params.editBtn;
		},
		/**
		 * 获取主面板
		 * @param tab Tab 面板对象
		 * @param params 由菜单点击所传过来的参数
		 * 	{tabId : tab 对象ID,apptabtreewinId : 系统程序窗口ID,sysid : 系统ID} 
		 */
		getModule : function(that){
			this.setParams(that);
			if(!this.appMainPanel){
				this.createCmpts();
			}
			return this.appMainPanel;
		},
		btnHandler : function(that){
			this.setParams(that);
			if(this.optionType == OPTION_TYPE.ADD){
				if(this.applyPanel){
					this.applyPanel.show();
				}
				if(this.extContractDetailPanel){
					this.extContractDetailPanel.hide();
				}
				this.showCustomerDialog();
			}else if(this.optionType==OPTION_TYPE.EDIT){
				if(this.extContractDetailPanel){
					this.extContractDetailPanel.hide();
				}
				this.applyPanel.show();
				if(!this.customerPanel){
					this.createCustomerPanel();
				}else{
					this.customerPanel.show();
					this.editExtContrac();
					this.appMainPanel.doLayout();
				}
				
			}else if(this.optionType == OPTION_TYPE.DETAIL ){
				this.detailReload();
			}else{
				this.saveExtContactData(this);
			}
		},
		/**
		 * 创建组件
		 */
		createCmpts : function(){
			this.appMainPanel = new Ext.Container();
			this.createCustomerPanel();
			this.crateExtContDateilPnl();
			this.createFormPanel();
		},
		createCustomerPanel : function() {
			var _this = this;
			var isNotHidden = false;
			if(this.optionType  == OPTION_TYPE.DETAIL  ){
				isNotHidden = true;
			}
			params = {that : _this,isNotHidden:isNotHidden};
				if(!_this.customerPanel){
					Cmw.importPackage('pages/app/workflow/bussProcc/formUIs/extcontract/CustomerDateil',function(module) {
				 	_this.customerModule =  module.moduleUI;
				 	_this.customerPanel = _this.customerModule.getModule(params);
				 	_this.appMainPanel.add(_this.customerPanel);
				 	_this.customerPanel.addListener('afterrender', function(panel) {
			 			 _this.showCustomerDialog(_this);
					});
	  			});
			}
		},
		/**
		 * 创建展期协议书详情面板
		 */
		crateExtContDateilPnl : function(){
			var _this = this;
			var isNotHidden = true;
			if(this.optionType  == OPTION_TYPE.DETAIL  ){
				isNotHidden = false;
			}
			var params = {that:_this,isNotHidden:isNotHidden};
			if(!_this.extContractDetailPanel){
				Cmw.importPackage('pages/app/workflow/bussProcc/formUIs/extcontract/ExtContDateilMod',function(module) {
			 		_this.extContractDetailMod =  module.moduleUI;
					 	_this.extContractDetailPanel = _this.extContractDetailMod.getModule(params);
					 	_this.extContractDetailPanel.addListener('afterrender', function(panel) {
					 		_this.extContractDetailPanel.reload({id:_this.editid});
						});
				 	_this.appMainPanel.add(_this.extContractDetailPanel);
				 	_this.appMainPanel.doLayout();
  				});
			}
		},
		createFormPanel : function(){
			var _this = this;
			this.createAttachMentFs();
			var isNotHidden = false;
			if(this.optionType  == OPTION_TYPE.DETAIL  ){
				isNotHidden = true;
			}
			var params = {
							attachMentFs : _this.attachMentFs,
							seDateDisplayId :_this.seDateDisplayId,
							parent : _this,
							extensionId  : _this.extensionId,
							isNotHidden :isNotHidden
						};
			if(!_this.applyPanel ){
				Cmw.importPackage('pages/app/workflow/bussProcc/formUIs/extcontract/ExtContactFrm',function(module) {
				 	 _this.extContractFrmMod =  module.moduleUI;
				 	_this.applyPanel =  _this.extContractFrmMod.getModule(params);
				 	_this.appMainPanel.add(_this.applyPanel);
				 	_this.appMainPanel.doLayout();
	  			});
			}
		},
		/** 
		 * 加载详情面板
		 */
		detailReload: function(){
			var _this = this;
			if(!this.customerPanel){
				this.createCustomerPanel();
			}
			if(this.extContractDetailPanel){
				this.extContractDetailPanel.show();
				this.extContractDetailPanel.reload({id:_this.editid});
			}
			this.appMainPanel.doLayout();
		},
		/**
		 * 设置显示的展期起始和截止日期
		 * @param estartDate 展期起始日期
		 * @param eendDate 展期截止日期
		 */
		setSeDate : function(estartDate,eendDate){
			var el = Ext.get(this.seDateDisplayId);
			if(!el) return;
			
			var dispVal = "";
			if(estartDate && eendDate){/*修改时调用*/
				dispVal = estartDate + "起&nbsp;&nbsp;至&nbsp;&nbsp;" + eendDate;
			}else{
				var oendDate = this.applyPanel.getValueByName("oendDate");
				if(!oendDate){
					ExtUtil.alert({msg:'原借款截止日期为空!'});
					return;
				}
				estartDate = new Date(oendDate);
				estartDate = estartDate.add(Date.DAY, 1);
				eendDate = estartDate;
				var limitLoanVal = this.applyPanel.getValueByName("limitLoan");
				var limitLoan = limitLoanVal.yearLoan;
				if(limitLoan && limitLoan>0){
					eendDate = eendDate.add(Date.YEAR,limitLoan);
				}
				limitLoan = limitLoanVal.monthLoan;
				if(limitLoan && limitLoan>0){
					eendDate = eendDate.add(Date.MONTH,limitLoan);
				}
				limitLoan = limitLoanVal.dayLoan;
				if(limitLoan && limitLoan>0){
					eendDate = eendDate.add(Date.DAY,limitLoan);
				}
				eendDate = eendDate.add(Date.DAY,-1);
				var fmt = 'Y-m-d';
				estartDate = estartDate.format(fmt);
				eendDate = eendDate.format(fmt);
				this.applyPanel.setJsonVals({estartDate:estartDate,eendDate:eendDate});
				dispVal = estartDate + "起&nbsp;&nbsp;至&nbsp;&nbsp;" + eendDate;
			}
			el.update("&nbsp;&nbsp;<span style='font-weight:bold;color:red;'>(自"+dispVal+"止)</span>");
		},
		/**
		 * 创建附件FieldSet 对象
		 * @return {}
		 */
		createAttachMentFs : function(){
			var _this = this;
			/* ----- 参数说明： isLoad 是否实例化后马上加载数据 
			 * dir : 附件上传后存放的目录， mortgage_path 定义在 resource.properties 资源文件中
			 * isSave : 附件上传后，是否保存到 ts_attachment 表中。 true : 保存
			 * params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔}
			 *    当 isLoad = false 时， params 可在 reload 方法中提供
			 *  ---- */
			this.attachMentFs = new  Ext.ux.AppAttachmentFs({title:'展期相关附件上传',isLoad:false,params:{dir : 'extension_path',isSave:true}});
		},
		/**
		 * 编辑展期协议书
		 */
		editExtContrac : function(){
			var _this = this;
			this.customerPanel.reload({contractId : _this.contractId});
			var id = _this.applyPanel.getValueByName("id") || _this.editid ;
			EventManager.get('./fcExtContract_get.action',{params:{id:id},sfn:function(jsonData){
				_this.addBtn.setDisabled(true);
				_this.saveBtn.setDisabled(false);
				_this.applyPanel.setVs(jsonData);
				_this.setSeDate(jsonData.estartDate,jsonData.eendDate);
				_this.attachMentFs.params = _this.getAttachParams(jsonData.id);
				_this.attachMentFs.reload(_this.attachMentFs.params);
			},ffn:function(jsonData){
				ExtUtil.alert({msg:"加载数据出错！"});
				return;
			}});
		},     
		/**
		 * 将展期客户数据赋给 展期客户资料面板
		 */
		showCustomerDialog : function(_this){
			var params = {contractId : _this.contractId};
			EventManager.get('./fcExtension_getContract.action',{params:params,sfn:function(jsonData){
					_this.setChooseVal(jsonData);
					_this.attachMentFs.params = _this.getAttachParams(_this.uuid);
			},ffn:function(jsonData){
				ExtUtil.alert({msg:"加载数据出错！"});
				return;
			}});
		
			
		},
		/**
		 * 将选择的客户数据赋给展期客户资料详情面板
		 */
		setChooseVal : function(data){
			var _this = this;
			var formData = {
				contractId : data.contractId,loanCode : data.contractCode,guaCode : data.guacontractCode,
				ostartDate : data.payDate,oendDate : data.endDate,endAmount : data.appAmount,
				extAmount : data.zprincipals,payType : data.payType,phAmount : data.phAmount,
				rateType : data.rateType,rate : data.rate,isadvance : data.isadvance,
				mgrtype : data.mgrtype,mrate : data.mrate,comp_extrate:'',compt_payType:''
			};
			this.customerPanel.reload({json_data:data},true);
			EventManager.get('./fcExtension_get.action',{params:{id:_this.extensionId},sfn:function(jsonDate){
				if(_this.optionType==OPTION_TYPE.ADD){
					if(_this.saveBtn && _this.editBtn){
						_this.saveBtn.setDisabled(false);
						_this.editBtn.setDisabled(true);
					}
				}
				var yearLoan  = jsonDate.yearLoan;
				var monthLoan = jsonDate.monthLoan;
				var dayLoan = jsonDate.dayLoan;
				var applyMan = jsonDate.applyMan;
				var applyDate = jsonDate.applyDate;
				formData.yearLoan = yearLoan;
				formData.monthLoan = monthLoan;
				formData.dayLoan = dayLoan;
				formData.applyMan = applyMan;
				formData.asignDate = applyDate;
				formData.code = jsonDate.ecode;
				formData.estartDate = jsonDate.estartDate;
				formData.eendDate = jsonDate.eendDate;
				formData.extensionId =_this.extensionId;
				_this.applyPanel.setVs(formData);
				_this.setSeDate(jsonDate.estartDate,jsonDate.eendDate);
			}});
			
		},
		/**
		 * 保存单据数据
		 * @param	opType	[0:暂存,1:提交]
		 */
		saveExtContactData : function(){
			var _this = this;
			if (!_this.validExtContactForm()) return;
			var saveBtn = this.saveBtn;
			var editBtn = this.editBtn;
			editBtn.setDisabled(true);
			var addBtn = this.addBtn;
			EventManager.frm_save(_this.applyPanel, {
				sfn : function(formDatas) {
					saveBtn.setDisabled(true);
					editBtn.setDisabled(false);
					addBtn.setDisabled(true);
					
					_this.editid  = formDatas.id;
					if(_this.editid ){
						var attachParams = _this.getAttachParams(_this.editid );
						_this.attachMentFs.updateTempFormId(attachParams);
					}
					if(_this.applyPanel){
						_this.applyPanel.hide();
						_this.applyPanel.findFieldByName("id").setValue(_this.editid);
					}
					
					if(_this.customerPanel){
						_this.customerPanel.hide();
					}
					if(_this.extContractDetailPanel){
						_this.extContractDetailPanel.show();
						_this.extContractDetailPanel.reload({id:_this.editid});
					}
//					if(_this.extPlanGrid){
//						_this.extPlanGrid.show();
//						_this.extPlanGrid.reload({actionType:2,formId:_this.editid });
//					}
					
					if(_this.finishBussCallback) _this.finishBussCallback(formDatas);
				},ffn:function(){
					ExtUtil.alert({msg:"数据保存错误！"});
					editBtn.setDisabled(false);
					return;
				}
			});
		},
		/**
		 * 回调函数
		 */
		reloadExtPanlbank : function(that,id){
			that.extPlanGrid.show();
			that.extPlanGrid.reload({actionType:2,formId:id });
		},
		/**
		 * 展期协议书表单数据验证
		 */
		validExtContactForm : function() {
			var errMsg = [];
			var applyMan = this.applyPanel.getValueByName("applyMan");//借款人
			if(!applyMan){
				errMsg[errMsg.length] = "借款人不能为空!";
			}
			var asignDate = this.applyPanel.getValueByName("asignDate");//借款人签字日期
			if(!asignDate){
				errMsg[errMsg.length] = "借款人签字日期不能为空!";
			}
			var guarantor = this.applyPanel.getValueByName("guarantor");//担保人
			if(!guarantor){
				errMsg[errMsg.length] = "担保人不能为空!";
			}
			var gsignDate = this.applyPanel.getValueByName("gsignDate");//担保人签字日期
			if(!gsignDate){
				errMsg[errMsg.length] = "担保人签字日期不能为空！";
			}
			
			// 申请展期金额
			var extAmount_val = this.applyPanel.getValueByName("extAmount");
			if (!extAmount_val || parseFloat(extAmount_val) <= 0) {
				errMsg[errMsg.length] = "展期金额必须大于0!";
			}
			
			// 贷款期限 yearLoan monthLoan dayLoan
			var limitLoan_val = this.applyPanel.getValueByName("limitLoan");
			if (!limitLoan_val) {
				errMsg[errMsg.length] = "展期期限必须大于0!";
			} else {
				var yearLoan = limitLoan_val.yearLoan;
				var monthLoan = limitLoan_val.monthLoan;
				var dayLoan = limitLoan_val.dayLoan;
				var flag = true;
				if ((!yearLoan || yearLoan <= 0)
						&& (!monthLoan || monthLoan <= 0)
						&& (!dayLoan || dayLoan <= 0)) {
					flag = false;
				}
				if (!flag) {
					errMsg[errMsg.length] = "展期期限的年,月,日至少有一个必须大于0!";
				}
				if(yearLoan && yearLoan>100){
					errMsg[errMsg.length] = "展期期限的年份最大数值不能超过100!";
				}
				if(monthLoan && monthLoan>12){
					errMsg[errMsg.length] = "展期期限的月份最大数值不能超过12!";
				}
				if(dayLoan && dayLoan>31){
					errMsg[errMsg.length] = "展期期限的月份最大数值不能超过31!";
				}
			}
			
			// 还款方式 payType phAmount
			var compt_payType_val = this.applyPanel.getValueByName("compt_payType");
			if (!compt_payType_val) {
				errMsg[errMsg.length] = "还款方式不能为空!";
			} else {
				var payType = compt_payType_val.payType;
				if (!payType) errMsg[errMsg.length] = "还款方式不能为空!";
				var phAmount = compt_payType_val.phAmount;
				if (!phAmount || phAmount <= 0) {
					if (payType == "P0002") {// "按月付息，分期还本"
						errMsg[errMsg.length] = "选择按月付息，分期还本；分期还本金额必须大于0!";
					} else if (payType == "P0004") {// "按季付息，分期还本"
						errMsg[errMsg.length] = "按月付息，分季还本；分期还本金额必须大于0!";
					}
				}
			}
			// 贷款利率
			var extrate_val = this.applyPanel.getValueByName("comp_extrate");
			var rateType = extrate_val.rateType;
			if (!rateType) {
				errMsg[errMsg.length] = "展期利率类型不能为空!";
			}
			var rate_val = extrate_val.rate;
			if (!rate_val || parseFloat(rate_val) <= 0) {
				errMsg[errMsg.length] = "展期贷款利率必须大于0!";
			}
			// 是否预收息
			var isadvance_val = this.applyPanel.getValueByName("isadvance");
			if(!isadvance_val) isadvance_val = 0;
			// 管理费收取方式
			var mgrtype_val = this.applyPanel.getValueByName("mgrtype");
			if(!mgrtype_val) mgrtype_val = 0;
			// 管理费率
			var mrate_val = this.applyPanel.getValueByName("mrate");
			if (mrate_val < 0) {
				errMsg[errMsg.length] = "管理费收取方式不能为空!";
			} else {
				if (mgrtype_val == 1 && (!mrate_val || mrate_val <= 0)) {
					errMsg[errMsg.length] = "按还款方式算法收取管理费时；管理费率必须大于0!";
				}
			}
			
			//签字负责人
			var manager_val = this.applyPanel.getValueByName("manager");
			if(!manager_val){
				errMsg[errMsg.length] = "签字负责人不能为空！";
			}
			//签字日期
			var signDate_val = this.applyPanel.getValueByName("signDate");
			if(!signDate_val){
				errMsg[errMsg.length] = "签字日期不能为空！";
			}
			
			if (null != errMsg && errMsg.length > 0) {
				var msg = errMsg.join("</br>");
				ExtUtil.alert({
							msg : msg
						});
				return false;
			}
			return true;
		},
		/**
		 * 获取自定义按钮 HTML CODE
		 * 
		 * @param id
		 *            按钮ID
		 * @param text
		 *            按钮文本
		 */
		getButtonHml : function(id, text) {
			var html = "&nbsp;&nbsp;<span class='btnBussNodeCls' id='" + id
					+ "'>" + text + "</span>&nbsp;&nbsp;";
			return html;
		},
		/**
		 * 为HTML按钮绑定事件
		 * 
		 * @param btnCfgArr
		 *            按钮配置数组 [{btnId : this.btnIdObj.btnSaveId,fn :
		 *            function(e,targetEle,obj){}}]
		 */
		addListenersToCustButtons : function(btnCfgArr) {
			var _this = this;
			for (var i = 0, count = btnCfgArr.length; i < count; i++) {
				var btnCfg = btnCfgArr[i];
				var btnId = btnCfg.btnId;
				var btnEle = addClass(btnId);
				btnEle.on('click', btnCfg.fn, this);
			}
			/**
			 * 为按钮添加点击和鼠标经过样式
			 */
			function addClass(eleId) {
				var btnEle = Ext.get(eleId);
				btnEle.addClassOnClick("nodeClickCls");
				btnEle.addClassOnOver("x-btn-text");
				return btnEle;
			}
		},
		
		refresh : function() {
			if (!this.appMainPanel.rendered) {
				var _this = this;
				this.appMainPanel.addListener('render', function(cmpt) {
					_this.loadDatas();
				});
			} else {
				this.loadDatas();
			}
		},
		/**
		 * 在这里加载数据
		 */
		loadDatas : function(){
			var _this = this;
			var sysId = this.params.sysId;
			var optionType = this.params.optionType;
			var breed = this.params.breed;
			var applyId = this.params.applyId;
			var contractId = this.params.contractId;
			if(optionType && optionType == OPTION_TYPE.EDIT){/*修改*/
				var errMsg = [];//["修改时传参发生错误："];
				if(!applyId){
					errMsg[errMsg.length] = "必须传入参数\"applyId\"!<br/>";			
				}
				if(!contractId){
					errMsg[errMsg.length] = "必须传入参数\"contractId\"!<br/>";			
				}
				if(null != errMsg && errMsg.length > 0){
					errMsg = "修改时传参发生错误：<br/>"+errMsg.join(" ");
					ExtUtil.alert({msg:errMsg});
					return;
				}
				this.applyPanel.enable();
				this.applyPanel.reset();
				this.customerPanel.reload({contractId:contractId});
				this.applyPanel.setValues('./fcExtension_get.action', {params : {id : applyId},sfn: function(json_data){
					_this.setSeDate(json_data.estartDate,json_data.eendDate);
				}});
				this.gopinionGrid.reload({extensionId:applyId});
				this.attachMentFs.reload(this.getAttachParams(applyId));
			}else{/*新增*/
				this.attachMentFs.params = this.getAttachParams(this.uuid);
				this.applyPanel.setJsonVals({breed:breed});
				this.applyPanel.enable();
				this.applyPanel.disable();
			}
		},
		/**
		 * 获取附件参数
		 * @param formId 业务单ID
		 */
		getAttachParams : function(formId){
			var sysId = this.sysId;
			return {sysId:sysId,formType:ATTACHMENT_FORMTYPE.FType_34,formId:formId};
		},
		doResize : function(){
		
		},
		resize : function(adjWidth,adjHeight){
			this.appMainPanel.setWidth(adjWidth);
			this.appMainPanel.setHeight(adjHeight);
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function(){
			if(null != this.customerModule) {
				this.customerModule.destroy(); 
				this.customerModule = null;
			}
			if(this.customerPanel){
				this.customerPanel.destroy();
				this.customerPanel = null;
			}
			if( null != this.extContractFrmMod){
				this.extContractFrmMod.destroy();
				this.extContractFrmMod = null;
			}
			if(this.applyPanel != null){
				this.applyPanel.destroy();
				this.applyPanel = null;
			}
			if(this.extContractDetailMod != null){
				this.extContractDetailMod.destroy();
				this.extContractDetailMod = null;
			}
			if(this.extContractDetailPanel != null){
				this.extContractDetailPanel.destroy();
				this.extContractDetailPanel = null;
			}
				
			if(null != this.appMainPanel){
				 this.appMainPanel.destroy();
				 this.appMainPanel = null;
			}
		}
	}
});
