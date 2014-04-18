/**
 * 菜单新增或修改页面
 * 
 * @author 程明卫
 * @date 2012-07-12
 */
define(function(require, exports) {
	exports.viewUI = {
		leabHtmlId: Ext.id(null,'mainLoanType'),
		mainLoanType : "",
		isFirstGet : false,
		baseId : null,
		isExpand : null,
		appMainPanel : null, /* 主面板 */
		customerPanel : null, /* 客户基本资料面板 */
		coborrManPanel : null, /* 共同借款人面板 */
		applyPanel : null,/* 借款申请单面板 */
		tab : null,
		params : null,
		uuid : null,/*用来临时绑定共同借款人和贷款申请单的唯一标识*/
		rateData : null,/*利率数据，{rate:{month:0,day:0,year:0},mrate:{month:0,day:0,year:0}}*/
		sysId : null, /* 系统ID */
		customerId : null, /* 客户ID */
		custType : null, /* 客户类型 */
		applyId : null, /* 贷款申请单ID */
		customerDialog : null,/* 个人客户弹出框 */
		entCustomerDialog : null,/* 企业客户弹出框 */
		oneCustDetailPnlId : Ext.id(null, 'oneCustDetailPnlId'),/* 个人客户详情面板ID */
		entCustDetailPnlId : Ext.id(null, 'entCustDetailPnlId'),/* 企业客户详情面板ID */
		companyName : null,
		appGrid : null,//暂存申请单中的表格
		btnIdObj : {
			btnChoseCust : Ext.id(null, 'btnChoseCust'),/* 选择个人客户 */
			btnChoseEntCust : Ext.id(null, 'btnChoseEntCust'),/* 选择企业客户 */
			btnChoseCoMan : Ext.id(null, 'btnChoseCoMan'),/* 选择共同借款人 */
			btnAddCoMan : Ext.id(null, 'btnAddCoMan'),/* 添加共同借款人 */
			btnEditCoMan : Ext.id(null, 'btnEditCoMan'),/* 修改共同借款人 */
			btnDelCoMan : Ext.id(null, 'btnDelCoMan'),/* 删除共同借款人 */
			btnDetailCoMan : Ext.id(null, 'btnDetailCoMan'),/* 查看共同借款人详情 */
			btnTempSave : Ext.id(null, 'btnTempSave'),/* 暂存申请单 */
			btnSave : Ext.id(null, 'btnSave')
			/* 提交申请单 */
		},
		/**
		 * 获取主面板
		 * 
		 * @param tab
		 *            Tab 面板对象
		 * @param params
		 *            由菜单点击所传过来的参数 {tabId : tab 对象ID,apptabtreewinId :
		 *            系统程序窗口ID,sysid : 系统ID}
		 */
		getMainUI : function(tab, params) {
			this.setParams(tab, params);
			var _this = this;
			if (!this.appMainPanel) {
				this.createCustomerPanel();
				this.createCoborrManPanel();
				this.createApplyPanel();
				this.appMainPanel = new Ext.Panel({
							items : [this.customerPanel, this.coborrManPanel,
									this.applyPanel]
						});
				this.appMainPanel.addListener('afterrender', function(cmpt) {
							_this.doResize();
						});
						this.appMainPanel.on('collapse',function(cmpt){
							if(isExpand){
								_this.doResize();
							}
						});
			}
			tab.notify = function(_tab) {
				_this.setParams(_tab, _tab.params);
				_this.refresh();
			};
			return this.appMainPanel;
		},
		doResize : function() {
			var tabWidth = this.tab.getWidth();
			var tabHeight = this.tab.getHeight();
			this.resize(tabWidth, tabHeight);
		},
		setParams : function(tab, params) {
			this.tab = tab;
			this.params = params;
			this.appGrid = params["appGrid"] ? params["appGrid"] : null;
			this.sysId = params["sysid"] ? params["sysid"] : null;
			this.customerId = params["customerId"] ? params["customerId"] : null;
			this.custType = params["custType"] ? params["custType"] : null;
			this.applyId = params["applyId"] ? params["applyId"] : null;
		},
		refresh : function() {
			if (!this.applyPanel.rendered) {
				var _this = this;
				this.applyPanel.addListener('render', function(cmpt) {
							_this.loadDatas();
						});
			} else {
				this.loadDatas();
			}
		},
		createCustomerPanel : function() {
			var _this = this;
			var btnChoseCustHtml = this.getButtonHml(
					this.btnIdObj.btnChoseCust, '选择个人客户');
			var btnChoseEntCustHtml = this.getButtonHml(
					this.btnIdObj.btnChoseEntCust, '选择企业客户');
			this.customerPanel = new Ext.Panel({
						title : '申请人资料' + btnChoseCustHtml
								+ btnChoseEntCustHtml,
						collapsible : true,
						collapsed : false,
						border : false
					});
			
			this.customerPanel.addListener('afterrender', function(panel) {
						_this.addListenersToCustButtons([{
							btnId : _this.btnIdObj.btnChoseCust,
							fn : function(e, targetEle, obj) {
								/* 选择个人客户 */
								_this.openDialog(0,_this.btnIdObj.btnChoseCust);
							}
						}, {
							btnId : _this.btnIdObj.btnChoseEntCust,
							fn : function(e, targetEle, obj) {
								/* 选择企业客户 */
								_this.openDialog(1,_this.btnIdObj.btnChoseCust);
							}
						}]);
					});
			this.customerPanel.on('expand', function(cmpt) {
						_this.doResize();
					});
			this.customerPanel.on('collapse', function(cmpt) {
						_this.doResize();
					});
		},
		/**
		 * 申请人 个人客户或企业客户弹出选择框
		 * 
		 * @param custType
		 *            客户类型 0:个人，1：企业
		 * @param btnId
		 *            当前点击的按钮ID
		 */
		openDialog : function(custType, btnId) {
			var _this = this;
			var errMsg = [];
			if((this.custType || this.custType === 0) && this.custType != custType){
				if(this.custType === 0){
					errMsg[errMsg.length] = '已经选择了个人客户，确定要取消个人客户而改为选择企业客户吗?';
				}else{
					errMsg[errMsg.length] = '已经选择了企业客户，确定要取消企业客户而改为选择个人客户吗?';
				}
			}
			if(null != errMsg && errMsg.length > 0){
				ExtUtil.confirm({msg:errMsg.join(""),fn:function(btn){
					if(btn != 'yes') return;
					_this.startShowDialog(custType, btnId);
				}});
			}else{
				this.startShowDialog(custType, btnId);
			}
		},
		/**
		 * 开始显示客户弹出框
		 */
		startShowDialog : function(custType,btnId){
			var _this = this;
			var parentCfg = {
				parent : btnId,
				params : {
					sysId : this.params.sysid,
					custType : custType
				},
				isCheck : true,
				callback : function(id, record) {
					var baseId = record.get("baseId");
					_this.loadCustomerData(id, custType,baseId);
				}
			};
			var customerDialog = null;
			var dialogModule = null;
			switch (custType) {	
				case 0 : { /* 个人客户 */
					if (!this.customerDialog) {
						dialogModule = 'OneCustDialogbox';
					} else {
						this.customerDialog.show(parentCfg);
					}
					customerDialog = this.customerDialog;
					break;
				}
				case 1 : { /* 企业客户 */
					if (!this.entCustomerDialog) {
						dialogModule = 'EntCustDialogbox';
					} else {
						this.entCustomerDialog.show(parentCfg);
					}
						customerDialog = this.entCustomerDialog;
					break;
				}
			};
			if (!dialogModule) return;
			Cmw.importPackage('/pages/app/dialogbox/' + dialogModule, function(module) {
				customerDialog = module.DialogBox;
				customerDialog.show(parentCfg);
			});
		},
		/**
		 * 重置组件和数据
		 */
		resets : function(){
			this.uuid = null;
			/*--> step 1 隐藏个人客户和企业客户详情面板<--*/
			var oneCustDetailPnl = Ext.getCmp(this.oneCustDetailPnlId);
			if(oneCustDetailPnl) oneCustDetailPnl.hide();
			var entCustDetailPnl = Ext.getCmp(this.entCustDetailPnlId);
			if(entCustDetailPnl) entCustDetailPnl.hide();
			/*--> step 2 重置共同借款人面板数据<--*/
			this.coborrManPanel.removeAll();
			this.coborrManPanel.collapse();
			/*--> step 3  重置申请单面板数据 <--*/
			this.applyPanel.reset();
			this.applyPanel.disable();
		},
		/**
		 * 加载数据
		 */
		loadDatas : function() {
			this.resets();
			if(!this.applyId) return;
			var id = this.applyPanel.getValueByName("id");
			// 已经加载过数据，就不需要重复加载
			if (id && id == this.applyId) return;
			this.loadCustomerData(this.customerId, this.custType,this.baseId);
			this.loadCoborrManData(this.customerId, this.custType,this.baseId);
			var _this = this;
			this.applyPanel.setValues('./fcApply_get.action', {
				params : {
					id : _this.applyId
				},
				sfn : function(jsonObj) {
					_this.isFirstGet = true;
					_this.mainLoanType = jsonObj.mainLoanType;
				}
					// defaultVal : {name : '企业客户管理',parentName : '客户管理'}
				});
		},
		/**
		 * 加载客户信息数据
		 */
		loadCustomerData : function(customerId, custType,baseId) {
			this.baseId = baseId;
			this.sysId = this.params.sysid;
			var isSameCustType = true;
			if(this.custType && this.custType != custType){
				isSameCustType = false;
			}
			
			this.custType = custType;
			this.customerId = customerId;
			var activePanel = this.showCustPanle(custType);
			activePanel.reload({customerId : customerId,custType : custType});
			this.isExpand = true;
			this.applyPanel.enable();/*当有客户数据时，启用申请表单面板*/
			this.applyPanel.reset();
			var comb_mainLoanType = this.applyPanel.findFieldByName('mainLoanType');
			comb_mainLoanType.disable();	
			this.loadRateDatas();
			this.doResize();
			if(!isSameCustType){/*当从个人客户改为选择企业客户或从企业客户改为个人客户时，业务品种须清空，重新选择*/
				var img_breed = this.applyPanel.findFieldByName('breed');
				img_breed.reset();
			}
		},
		/**
		 * 加载利率数据
		 */
		loadRateDatas : function(){
			if(this.applyId) return;
			var _this = this;
			if(this.rateData){
				this.setRatesToForm();
				return;
			}
			EventManager.get('./fcRate_read.action', {
				sfn : function(json_data) {
					var count = json_data.totalSize;
					if(count == 0){
						_this.rateData = null;
						return;
					}
					var rateData = {};
					var list = json_data.list;
					for(var i=0; i<count; i++){
						var data = list[i];
						var types = data["types"];
						var limits = data["limits"];
						var val = data["val"];
						switch(parseInt(types)){
							case 1 :{
								setYmdRate(rateData,'rate',limits,val);	
								break;
							}case 2 :{
								setYmdRate(rateData,'mrate',limits,val);	
								break;
							}case 3 :{
								setYmdRate(rateData,'urate',limits,val);	
								break;
							}case 4 :{
								setYmdRate(rateData,'frate',limits,val);
								break;
							}case 5 :{
								setYmdRate(rateData,'prate',limits,val);
								break;
							}case 6 :{
								setYmdRate(rateData,'arate',limits,val);
								break;
							}
						}
					}
					_this.rateData = rateData;
					_this.setRatesToForm();
					
					function setYmdRate(rateData,prop,limits,val){
						if(!rateData[prop]) rateData[prop] = {};
						switch(parseInt(limits)){
							case 1 :{
								if(!rateData[prop].month) rateData[prop].month = val;	
								break;
							}case 2 :{
								if(!rateData[prop].day) rateData[prop].day = val;	
								break;
							}case 3 :{
								if(!rateData[prop].year) rateData[prop].year = val;	
								break;
							}case 4 :{
								if(rateData[prop]) rateData[prop] = val;	
								break;
							}default:{
								if(rateData[prop]) rateData[prop] = val;
							}
						}
					}
				}
			});
		},
		/**
		 * 为表单利率加上默认值
		 */
		setRatesToForm : function(){
			var rateData = this.rateData;
			var data = {};
			//贷款利率,管理费率,罚息利率,滞纳金,放款手续费率,提前还款费率
			//rate,mrate,urate,frate,prate,arate
			if(!rateData){
				data = {rate:0,mrate:0,urate:0,frate:0,prate:0,arate:0};
			}else{
				var rateType = this.applyPanel.getValueByName('rateType');
				if(!rateType) rateType = 1;/*默认加载月利率值*/
				var rate = 0;
				var mrate = 0;
				var urate = 0;
				var frate = 0;
				switch(parseInt(rateType)){
					case 1 :{/*月利率*/
						rate = getValidRate(rateData,'rate','month');
						mrate = getValidRate(rateData,'mrate','month');
						urate = getValidRate(rateData,'urate','day');
						frate = getValidRate(rateData,'frate','day');
						break;
					}case 2 :{/*日利率*/
						rate = getValidRate(rateData,'rate','day');
						mrate = getValidRate(rateData,'mrate','day');
						urate = getValidRate(rateData,'urate','day');
						frate = getValidRate(rateData,'frate','day');
						break;
					}case 3 :{/*年利率*/
						rate = getValidRate(rateData,'rate','year');
						mrate = getValidRate(rateData,'mrate','year');
						urate = getValidRate(rateData,'urate','day');
						frate = getValidRate(rateData,'frate','day');
						break;
					}
				}
				data = {rate:rate,mrate:mrate,urate:urate,frate:frate,
				prate:rateData.prate||0,arate:rateData.arate||0};
			}
			this.applyPanel.setFieldValue('rate',data.rate);
			this.applyPanel.setFieldValue('mrate',data.mrate);
			this.applyPanel.setFieldValue('urate',data.urate);
			this.applyPanel.setFieldValue('frate',data.frate);
			this.applyPanel.setFieldValue('prate',data.prate);
			this.applyPanel.setFieldValue('arate',data.arate);
			this.applyPanel.setFieldValue('isadvance',0);
			
			//获取验证后的利率
			function getValidRate(rateData,prop1,prop2){
				var rate = 0;
				var data = rateData[prop1];
				if(data && data[prop2]) rate = data[prop2];
				return rate;
			}
		},
		showCustPanle : function(custType) {
			var activePnl = null;
			var activePnlId = null;
			var isAdd = true;
			if (!Ext.isNumber(custType)) {
				custType = parseInt(custType);
			}
			switch (custType) {
				case 0 : { /* 个人客户 */
					activePnlId = this.oneCustDetailPnlId;
					var activePnl = Ext.getCmp(activePnlId);
					if (!activePnl) {
						activePnl = this.getOneCustDetailPanel();
						isAdd = true;
					} else {
						isAdd = false;
					}
					break;
				}
				case 1 : { /* 企业客户 */
					activePnlId = this.entCustDetailPnlId;
					var activePnl = Ext.getCmp(activePnlId);
					if (!activePnl) {
						activePnl = this.getEntCustDetailPanel();
						isAdd = true;
					} else {
						isAdd = false;
					}
					break;
				}
			}
			if (isAdd) {
				this.customerPanel.add(activePnl);
				this.customerPanel.doLayout();
			}
			activePnl.show();
			var hidPnlId = null;
			if (activePnlId == this.oneCustDetailPnlId) {
				hidPnlId = this.entCustDetailPnlId;
			} else {
				hidPnlId = this.oneCustDetailPnlId;
			}
			var hidPnl = Ext.getCmp(hidPnlId);
			if (hidPnl)
				hidPnl.hide();
			return activePnl;
		},
		/**
		 * 获取个人客户详情面板
		 */
		getOneCustDetailPanel : function() {
			var _this = this;
				var htmlArrs_1 = ['<tr><th col="code">客户编号</th> <td col="code" >&nbsp;</td><th col="name">客户名称</th> <td col="name" >&nbsp;</td><th col="custLevel">客户级别</th> <td col="custLevel" >&nbsp;</td></tr>',
			'<tr><th col="cardType">证件类型</th> <td col="cardType" >&nbsp;</td><th col="cardNum">证件号码</th> <td col="cardNum" >&nbsp;</td><th col="accAddress">户籍地址</th> <td col="accAddress" >&nbsp;</td></tr>',
			'<tr><th col="sex">性别</th> <td col="sex" >&nbsp;</td><th col="maristal">婚姻状况</th> <td col="maristal" >&nbsp;</td><th col="birthday">出生日期</th> <td col="birthday" >&nbsp;</td></tr></tr>',
			'<tr><th col="phone">手机</th> <td col="phone" >&nbsp;</td><th col="contactTel">联系电话</th> <td col="contactTel">&nbsp;</td><th col="inArea">现居住地区</th> <td col="inArea" >&nbsp;</td></tr>',
			'<tr><th col="workOrg">工作单位</th> <td col="workOrg" >&nbsp;</td><th col="workAddress">单位地址</th> <td col="workAddress"  colspan=3 >&nbsp;</td></tr></tr>'];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 150,
			    /* title : '#TITLE#', */
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './crmCustBase_get.action',
			    params: {
			        customerId: -1
			    },
			    callback: {
			        sfn: function(jsonData) {
			        	
			
			        	var cardType = jsonData["cardType"];
			     		jsonData["cardType"] = Render_dataSource.gvlistRender('100002',cardType);
			     		
			     		var guaIdAppComBox = _this.applyPanel.findFieldByName("GuaId");
						guaIdAppComBox.setParams({custType:Buss_Constant.CustType_0,baseId:jsonData['baseId']});
						
						
			     		var custLevel = jsonData["custLevel"];
			     		if(!custLevel && custLevel != 0){
			     		  jsonData["custLevel"] = '暂未设置';
			     		}else{
			     		  jsonData["custLevel"] = Render_dataSource.custLevelRender(''+custLevel+'');
			     		}
			     		
			            jsonData["sex"] = Render_dataSource.sexRender(''+jsonData["sex"]+'');
			            var maristal = jsonData["maristal"];
			     		jsonData["maristal"] =  Render_dataSource.gvlistRender('100003',maristal);
			     		
			     		
			            var ia = "";
			            if(jsonData["inArea"]!=null){
			            	var inArea = [];
			            	inArea = jsonData["inArea"].split("##");
			            	ia = inArea[1];
			            }
			            if(jsonData["inAddress"]==null){
			            	jsonData["inAddress"] = "";
			            }
			            jsonData["inArea"] = ia+jsonData["inAddress"];
			        }
			    }
			}];
			var width = this.customerPanel.getWidth();
			var detailPanel = new Ext.ux.panel.DetailPanel({
				id : this.oneCustDetailPnlId,
			    width: width,
			    detailCfgs: detailCfgs_1
			});
			return detailPanel;
		},
		/**
		 * 获取企业客户详情面板
		 */
		getEntCustDetailPanel : function() {
			var  _this = this;
			var htmlArrs_1 = [
					'<tr><th col="code">客户编号</th> <td col="code" >&nbsp;</td><th col="name">企业名称</th> <td col="name"  colspan=3 >&nbsp;</td></tr>',
					'<tr><th col="serialNum">流水号</th> <td col="serialNum" >&nbsp;</td><th col="custLevel">客户级别</th> <td col="custLevel" >&nbsp;</td><th col="kind">企业性质</th> <td col="kind" >&nbsp;</td></tr>',
					'<tr><th col="regcapital">注册资本</th> <td col="regcapital" >&nbsp;</td><th col="regaddress">注册地址</th> <td col="regaddress"  colspan=3 >&nbsp;</td></tr>',
					'<tr><th col="contactor">联系人</th> <td col="contactor" >&nbsp;</td><th col="phone">联系人手机</th> <td col="phone" >&nbsp;</td><th col="contacttel">联系电话</th> <td col="contacttel" >&nbsp;</td></tr>',
					'<tr><th col="tradNumber">营业执照号</th> <td col="tradNumber" >&nbsp;</td><th col="address">企业地址</th> <td col="address"  colspan=3 >&nbsp;</td></tr>'];
			var detailCfgs_1 = [{
				cmns : 'THREE',
				/* ONE , TWO , THREE */
				model : 'single',
				labelWidth : 90,
				// 详情面板标题
				/* i18n : UI_Menu.appDefault, */
				// 国际化资源对象
				htmls : htmlArrs_1,
				url : './crmCustBase_get.action',
				params : {
					customerId : -1
				},
				callback : {
					sfn : function(jsonData) {
						var guaIdAppComBox = _this.applyPanel.findFieldByName("GuaId");
						guaIdAppComBox.setParams({custType:Buss_Constant.CustType_1,baseId:jsonData['baseId']});
						
						_this.companyName = jsonData["name"];
						var kind = jsonData["kind"];
						jsonData["kind"] = Render_dataSource.gvlistRender('100009', kind);
						var custLevel = jsonData["custLevel"];
			     		if(!custLevel && custLevel != 0){
			     		  jsonData["custLevel"] = '暂未设置';
			     		}else{
			     		  jsonData["custLevel"] = Render_dataSource.custLevelRender(''+custLevel+'');
			     		}
						var regcapital = jsonData["regcapital"];
						if(regcapital){
							var currency = jsonData["currency"];
							currency = !currency ? "人民币" : Render_dataSource.gvlistRender('100014', currency);
							jsonData["regcapital"] =  Cmw.getThousandths(regcapital)+'元&nbsp;&nbsp;<span style="color:red;">(大写：'+Cmw.cmycurd(regcapital)+')</span>';
						}
						  var ia = "";
			            if(jsonData["inArea"]!=null){
			            	var inArea = [];
			            	inArea = jsonData["inArea"].split("##");
			            	ia = inArea[1];
			            }
			            if(jsonData["inAddress"]==null){
			            	jsonData["inAddress"] = "";
			            }
			            jsonData["address"] = ia+jsonData["inAddress"];
					}
				}
			}];
			var width = this.customerPanel.getWidth();
			var detailPanel = new Ext.ux.panel.DetailPanel({
						id : this.entCustDetailPnlId,
						width : width,
						detailCfgs : detailCfgs_1,
						border : false
					});
			return detailPanel;
		},
		/**
		 * 验证申请人资料是否已存在
		 */
		validCustomer : function() {
			if (!this.customerId) {
				ExtUtil.alert({
							msg : '请先选择申请人资料!'
						});
				return false;
			}
			return true;
		},
		createCoborrManPanel : function() {
			var btnChoseCoManHtml = this.getButtonHml(
					this.btnIdObj.btnChoseCoMan, '选择共同借款人');
			var btnAddCoManHtml = this.getButtonHml(this.btnIdObj.btnAddCoMan,
					'添加共同借款人');
			var btnEditCoManHtml = this.getButtonHml(
					this.btnIdObj.btnEditCoMan, '修改共同借款人');
			var btnDelCoManHtml = this.getButtonHml(this.btnIdObj.btnDelCoMan,
					'删除共同借款人');
			var btnDetailCoManHtml = this.getButtonHml(
					this.btnIdObj.btnDetailCoMan, '查看共同借款人详情');
//			var title = '共同借款人资料' + btnChoseCoManHtml + btnAddCoManHtml
//					+ btnEditCoManHtml + btnDelCoManHtml + btnDetailCoManHtml;
			var title = '共同借款人资料' + btnChoseCoManHtml + btnDelCoManHtml;
			
			var structure_1 = [{
						header : '姓名',
						name : 'name'
					}, {
						header : '性别',
						name : 'sex',
						width : 60,
						renderer : function(val) {
							return Render_dataSource.sexRender(val);
						}
					}, {
						header : '出生日期',
						name : 'birthday',
						width : 80
					}, {
						header : '户口性质',
						name : 'accNature',
						width : 60,
						renderer : function(val) {
							return Render_dataSource.accNatureRender(val);
						}
					}, {
						header : '证件类型',
						name : 'cardType',
						width : 65,
						renderer : function(val) {
							return Render_dataSource.gvlistRender('100002', val);
						}
					}, {
						header : '证件号码',
						name : 'cardNum'
					}, {
						header : '婚姻状况',
						name : 'maristal',
						width : 60,
						renderer : function(val) {
							return Render_dataSource.gvlistRender('100003', val);
						}
					}, {
						header : '学历',
						name : 'degree',
						width : 60,
						renderer : function(val) {
							return Render_dataSource.gvlistRender('100006', val);
							
						}
					}, {
						header : '职务',
						name : 'job',
						width : 80
					}, {
						header : '工作单位',
						name : 'workOrg'
					}, {
						header : '单位地址',
						name : 'workAddress',
						width : 120
					}, {
						header : '手机',
						name : 'phone',
						width : 95
					}, {
						header : '联系电话',
						name : 'contactTel',
						width : 95
					}];
			this.coborrManPanel = new Ext.ux.grid.AppGrid({
						title : title,
						collapsible : true,
						collapsed : true,
						structure : structure_1,
						url : './crmCategory_list.action',
						needPage : false,
						isLoad : false,
						keyField : 'categoryId',
						height : 125
					});
			var _this = this;
			this.coborrManPanel.addListener('afterrender', function(panel) {
				_this.addListenersToCustButtons([{
					btnId : _this.btnIdObj.btnChoseCoMan,
					fn : function(e, targetEle, obj) {
						_this.openCoborrManDialog(_this.btnIdObj.btnChoseCoMan);
					}
				},/* {
					btnId : _this.btnIdObj.btnAddCoMan,
					fn : function(e, targetEle, obj) {
						alert('添加共同借款人');
					}
				}, {
					btnId : _this.btnIdObj.btnEditCoMan,
					fn : function(e, targetEle, obj) {
						alert('修改共同借款人');
					}
				}, */{
					btnId : _this.btnIdObj.btnDelCoMan,
					fn : function(e, targetEle, obj) {
						_this.delCoborrMan();
					}
				}/*, {
					btnId : _this.btnIdObj.btnDetailCoMan,
					fn : function(e, targetEle, obj) {
						alert('查看共同借款人详情');
					}
				}*/]);
			});
			this.coborrManPanel.on('expand', function(cmpt) {
						_this.doResize();
					});
			this.coborrManPanel.on('collapse', function(cmpt) {
						_this.doResize();
					});
		},
		/**
		 * 共同借款人 个人客户弹出选择框
		 * 
		 * @param custType
		 *            客户类型 0:个人，1：企业
		 * @param btnId
		 *            当前点击的按钮ID
		 */
		openCoborrManDialog : function(btnId) {
			if (!this.validCustomer()) return;
			var _this = this;
			var parentCfg = {
				parent : btnId,
				params : {
					sysId : this.params.sysid,
					custType : 0
				},
				isCheck : true,
				callback : function(id, record) {
					_this.saveCoborrMan(id);
				}
			};
			if (this.customerDialog) {
				this.customerDialog.show(parentCfg);
				return;
			}
			Cmw.importPackage('/pages/app/dialogbox/OneCustDialogbox', function(module) {
						_this.customerDialog = module.DialogBox;
						_this.customerDialog.show(parentCfg);
					});
		},
		/**
		 * 保存共同借款人
		 */
		saveCoborrMan : function(id){
			var _this = this;
			if(!this.uuid) this.uuid = SYSTEM_MARK.FINANCE +'_'+ Cmw.getUuid();
			var customerId = this.customerId;
			var custType = this.custType;
			EventManager.get('./crmCategory_save.action', {
						params : {
							custType : custType,
							inCustomerId : customerId,
							relCustomerId : id,
							uuid : this.uuid,
							category : 1,
							projectId : this.applyId
						},
						sfn : function(json_data) {
							_this.loadCoborrManData(customerId,custType);
						}
			});
		},
		/**
		 * 删除共同借款人
		 */
		delCoborrMan : function(){
			var id = this.coborrManPanel.getSelId();
			if(!id) return;
			var data = this.coborrManPanel .getCmnVals("name");
			var name = data.name;
			var _this = this;
			ExtUtil.confirm({title:'提示',msg:'确定删除共同借款人['+name+']?',fn:function(){
		 	 	  if(arguments && arguments[0] != 'yes') return;
		 	 	  var params = {ids:id};
		 	 	  EventManager.get('./crmCategory_delete.action',{params:params,sfn:function(json_data){
		 	 	  	_this.loadCoborrManData(_this.customerId, _this.custType);
		 	 	  }});
			}});
		},
		/**
		 * 加载共同借款人数据
		 * 
		 * @param customerId
		 *            客户ID
		 */
		loadCoborrManData : function(customerId, custType) {
			var uuid = this.uuid || null;
			var projectId = this.applyId || null;
			var _this = this;
			this.coborrManPanel.reload({
						custType : custType,
						inCustomerId : customerId,
						category : 1,
						uuid : uuid,
						projectId : projectId
					},_this.callbackExpand());
		},
		callbackExpand : function(){
			var _this = this;
			var delay = new Ext.util.DelayedTask(function(){
				var count = _this.coborrManPanel.getStore().getCount();
				if(count){
					_this.coborrManPanel.expand();
				}
			}).delay(500);
			
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
		 * 为节点配置表单中的按钮绑定事件
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
		/**
		 * 
		 */
		setDisableCustBtns : function(isDisabled) {

		},
		createApplyPanel : function() {
			var _this = this;
			var txt_id = FormUtil.getHidField({// FormUtil.getTxtField({
				fieldLabel : '申请单ID',
				name : 'id',
				"width" : "125"
			});
			var txt_procId = FormUtil.getHidField({
						fieldLabel : '流程实例ID',
						name : 'procId',
						"width" : 125,
						"maxLength" : "20"
					});

			var txt_code = FormUtil.getTxtField({
						fieldLabel : '申请单编号',
						name : 'code',
						"width" : 135,
						"maxLength" : "20"
					});

			var btn_autoCode = new Ext.Button({
						text : '生成编号',
						name : 'btn',
						handler : function() {
							EventManager.get("./fcApply_add.action",{sfn:function(json_data){
								txt_code.reset();
								txt_code.setValue(json_data.code);
							}});
						}
					});

			var comp_code = FormUtil.getMyCompositeField({
						fieldLabel : '申请单编号',
						name : 'code',
						sigins:null,
						itemNames : 'code,btn',
						width : 210,
						allowBlank : false,
						items : [txt_code,btn_autoCode]
					});

			var rad_loanMain = FormUtil.getRadioGroup({
						fieldLabel : '借款主体',
						name : 'loanMain',
						"allowBlank" : false,
						register : REGISTER.GvlistDatas,
						type : '200000'
					});

			var cbo_inType = FormUtil.getRCboField({
						fieldLabel : '行业分类',
						name : 'inType',
						"width" : 125,
						"allowBlank" : false,
						register : REGISTER.GvlistDatas,
						restypeId : '200001'
						//"url" : "./sysGvlist_cbodatas.action?restypeId=200001"
					});

			var rad_loanType = FormUtil.getCheckGroup({
						fieldLabel : '贷款方式',
						name : 'loanType',
						"width" : 125,
						"allowBlank" : false,
						register : REGISTER.GvlistDatas,
						type : '200002',
						listeners :{
							change : function(el,checked){
								if(checked){
									var length = checked.length;
									if(length > 1){
										_this.isFirstGet = false;
										Ext.get(_this.leabHtmlId).update(FormUtil.REQUIREDHTML+"主贷款方式");
										comb_mainLoanType.allowBlank = false;
										comb_mainLoanType.enable();
									}else{
										Ext.get(_this.leabHtmlId).update("主贷款方式");
										comb_mainLoanType.allowBlank = true;
										comb_mainLoanType.clearInvalid();
										if(_this.isFirstGet) comb_mainLoanType.reset();
										comb_mainLoanType.disable();
									}
								}
								
							}
						}
					});	
			var comb_mainLoanType = FormUtil.getRCboField({
						fieldLabel : '<span id ="'+_this.leabHtmlId+'">主贷款方式</span>',
						name : 'mainLoanType',
						"width" : 125,
						register : REGISTER.GvlistDatas,
						restypeId : '200002'
					});
			var rad_limitType = FormUtil.getRadioGroup({
						fieldLabel : '期限种类',
						name : 'limitType',
						"width" : 250,
						"allowBlank" : false,
						register : REGISTER.GvlistDatas,
						type : '200003'
				});
			var barItems = [{type:'label',text:'姓名'},{type:'txt',name:'name'}];
			var structure = [ {
						header : '客户基础信息ID',
						name : 'baseId',
						hidden : true
					},{
						header : '担保人名称',
						name : 'name',
						width : 200
					},{
						header : '是否连带担保责任',
						name : 'isgua',
						width : 160,
						renderer : Render_dataSource.isguaDetailRender
					},{
						header : '联系人',
						name : 'contactor'
					}, {
						header : '证件类型',
						name : 'cardType',
						renderer : Render_dataSource.guaCardTypeRender
					}, {
						header : '证件号码',
						name : 'cardNum'
					}, {
						header : '担保人电话',
						name : 'contactTel'
					}, {
						header : '担保人手机',
						name : 'phone'
					}];
			
					
			
			var guaComboxAppGird = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '担保人',
			    name: 'GuaId',
			    barItems : barItems,
			    structure:structure,
			    dispCmn:'name',
			    isCheck : true,
			    showLoad : true,
			    isAll:true,
			    url : './crmGuaCustomer_list.action',
			    params : {baseId :_this.baseId},
			    needPage : true,
			    keyField : 'id',
			    width: 200
			});
			var txt_appAmount = FormUtil.getMoneyField({
						fieldLabel : '贷款金额',
						name : 'appAmount',
						width : 200,
						allowBlank : false,
						value : 0,
						autoBigAmount : true,
						unitText : '此处将显示大写金额',
						unitStyle : 'margin-left:4px;color:red;font-weight:bold'
					});
			var int_yearLoan = FormUtil.getIntegerField({
						fieldLabel : '贷款期限(年)',
						name : 'yearLoan',
						width : 30,
						"allowBlank" : false,
						"value" : 0,
						"maxLength" : 10
					});

			var int_monthLoan = FormUtil.getIntegerField({
						fieldLabel : '贷款期限(月)',
						name : 'monthLoan',
						width : 30,
						"allowBlank" : false,
						"value" : 0,
						"maxLength" : 10
					});

			var int_dayLoan = FormUtil.getIntegerField({
						fieldLabel : '贷款期限(日)',
						name : 'dayLoan',
						width : 30,
						"allowBlank" : false,
						"value" : 0,
						"maxLength" : 10
					});

			var comp_loanLimit = FormUtil.getMyCompositeField({
						fieldLabel : '贷款期限',
						name : 'limitLoan',
						width : 140,
						allowBlank : false,
						sigins : null,
						itemNames : 'yearLoan,monthLoan,dayLoan',
						items : [int_yearLoan, {
									xtype : 'displayfield',
									value : '年',
									width : 6
								}, int_monthLoan, {
									xtype : 'displayfield',
									value : '月',
									width : 6
								}, int_dayLoan, {
									xtype : 'displayfield',
									value : '日',
									width : 6
								}]
					});
						
			var txt_borBank = FormUtil.getTxtField({
				    fieldLabel: '借款人银行',
				    name: 'borBank',
				    "width": 200,
				    "allowBlank" : false,
				    "maxLength": "50"
				});
				
				var txt_borAccount = FormUtil.getTxtField({
				    fieldLabel: '借款帐号',
				    name: 'borAccount',
				    "allowBlank" : false,
				    "width": 200,
				    "maxLength": "50"
				});
				
//				var txt_payBank = FormUtil.getTxtField({
//				    fieldLabel: '还款银行',
//				    name: 'payBank',
//				    "width": 135,
////				    "allowBlank": false,
//				    "maxLength": "50"
//				});
//				
//				var txt_payAccount = FormUtil.getTxtField({
//				    fieldLabel: '还款帐号',
//				    name: 'payAccount',
//				    "width": 135,
////				    "allowBlank": false,
//				    "maxLength": "50"
//				});
				
					
			var txt_breed = FormUtil.getAppComboxImg({
				fieldLabel : '业务品种',
				name : 'breed',
				"width" : 200,
				"allowBlank" : false,
				valueField : 'id',
				displayField : 'name',
				url : './sysVariety_enablelist.action',
				isLoad : false,
				params : {
					sysId : this.params.sysid
				},
				clickCallback : function(cmpt){
					var params = {sysId:_this.params.sysid,custType:_this.custType};
					if(_this.companyName){
						params.companyName = _this.companyName;
					}
					var loanLimitVal = comp_loanLimit.getValue();
					var appAmountVal = txt_appAmount.getValue();
					var errMsg = [];
					if((!loanLimitVal.yearLoan || loanLimitVal.yearLoan==0) &&
					(!loanLimitVal.monthLoan || loanLimitVal.monthLoan==0) &&
					(!loanLimitVal.dayLoan || loanLimitVal.dayLoan==0)){
						errMsg[errMsg.length] = '&nbsp;&nbsp;&nbsp;&nbsp;贷款期限必须大于0!';
					}
					if(!appAmountVal || appAmountVal<0){
						errMsg[errMsg.length] = '&nbsp;&nbsp;&nbsp;&nbsp;贷款金额必须大于0!';
					}
					if(errMsg && errMsg.length > 0){
						ExtUtil.alert({msg:'在选择业务品种之前，须处理以下错误项:<br/>'+errMsg.join("<br/>")});
						return false;
					}
					Ext.apply(params,loanLimitVal);
					params.appAmount = appAmountVal;
					cmpt.setParams(params);
					cmpt.reload();
					return true;
				}
			});

			var cbo_payType = FormUtil.getRCboField({
					fieldLabel : '还款方式',
					name : 'payType',
					width : 140,
					maxLength : 50,
					url: "./fcPayType_cbodatas.action"
				});
			var txt_phAmount = FormUtil.getMoneyField({
						fieldLabel : '每期还本金额',
						name : 'phAmount',
						width : 80,
						value : 0
					});

			var compt_payType = FormUtil.getMyCompositeField({
						fieldLabel : '还款方式',
						sigins : null,
						"allowBlank" : false,
						itemNames : 'payType,phAmount',
						name : 'compt_payType',
						width : 600,
						items : [cbo_payType, {
									xtype : 'displayfield',
									html : '<span style="color:red;">(</span>'
								}, txt_phAmount, {
									xtype : 'displayfield',
									width : 220,
									html : '<span style="color:red;">分期还本金额)</span>'
								}]
					});

			var bdat_appdate = FormUtil.getDateField({
						fieldLabel : '申请日期',
						name : 'appdate',
						"width" : 125,
						"allowBlank" : false
					});

			var hid_manager = FormUtil.getHidField({
				fieldLabel : '业务经理Id',
				name : 'manager',
				value : CURRENT_USERID
			});
			
			var txt_managerName = FormUtil.getReadTxtField({
						fieldLabel : '业务经理',
						name : 'managerName',
						"width" : 125,
						value : CURENT_EMP
			});

			var rad_sourceType = FormUtil.getRadioGroup({
						fieldLabel : '业务来源方式',
						name : 'sourceType',
						register : REGISTER.GvlistDatas,
						type : '200004'
					});

			var txt_referrals = FormUtil.getTxtField({
						fieldLabel : '业务介绍人',
						name : 'referrals',
						"width" : "125"
					});
			/*----------- 基本申请信息设置 ------------*/
			var fset_1 = FormUtil.createLayoutFieldSet({
						title : '基本申请信息'
					}, [{
						cmns : FormUtil.CMN_TWO,
						fields : [comp_code, rad_loanMain, cbo_inType,
								rad_loanType,comb_mainLoanType,rad_limitType,guaComboxAppGird,  comp_loanLimit,
								 txt_breed,txt_appAmount,txt_borBank,compt_payType,txt_borAccount]
					}, {
						cmns : FormUtil.CMN_THREE,
						fields : [ bdat_appdate,txt_referrals]
					}, {
						cmns : FormUtil.CMN_TWO,
						fields : [txt_managerName, rad_sourceType]
					}]);

			var rad_rateType = FormUtil.getRadioGroup({
						fieldLabel : '利率类型',
						name : 'rateType',
						"width" : 180,
						"allowBlank" : false,
						"maxLength" : 10,
						listeners : {
							change : function(rdgp,checked){
								_this.loadRateDatas();
							}
						},
						items : [{
									"boxLabel" : "月利率",
									"name" : "rateType",
									"inputValue" : 1,
									checked : true
								}, {
									"boxLabel" : "日利率",
									"name" : "rateType",
									"inputValue" : 2
								}, {
									"boxLabel" : "年利率",
									"name" : "rateType",
									"inputValue" : 3
								}]
					});

			var dob_rate = FormUtil.getDoubleField({
						fieldLabel : '贷款利率',
						name : 'rate',
						"width" : 125,
						"allowBlank" : false,
						"value" : 0,
						"decimalPrecision" : 2,
						unitText : '%'
					});

			var rad_isadvance = FormUtil.getRadioGroup({
						fieldLabel : '是否提前收息',
						name : 'isadvance',
						hidden:true,
						"width" : 125,
						"allowBlank" : false,
						"maxLength" : 10,
						"items" : [{
									"boxLabel" : "否",
									"name" : "isadvance",
									"inputValue" : 0,
									checked : true
								}, {
									"boxLabel" : "是",
									"name" : "isadvance",
									"inputValue" : 1
								}]
					});

			/*----------- 利率信息设置 ------------*/
			var mrateVal = 0;
			var rad_mgrtype = FormUtil.getRadioGroup({
						fieldLabel : '管理费收取方式',
						name : 'mgrtype',
						width : 250,
						allowBlank : false,
						maxLength : 10,
						items : [{
									"boxLabel" : "不收管理费",
									"name" : "mgrtype",
									"inputValue" : 0
								}, {
									"boxLabel" : "按还息方式收取",
									"name" : "mgrtype",
									"inputValue" : 1
								}],
						listeners : {
							change : function(rdgp,checked){
									var val = rad_mgrtype.getValue();
									var oldMrate = dob_mrate.getValue();
									if(oldMrate && oldMrate>0) mrateVal = oldMrate;
									if(!val || val == 0){
										dob_mrate.setValue(0);
									}else{
										dob_mrate.setValue(mrateVal);
									}
							}
						}
					});

			var dob_mrate = FormUtil.getDoubleField({
						fieldLabel : '管理费率',
						name : 'mrate',
						"width" : 125,
						"allowBlank" : false,
						"value" : "0",
						decimalPrecision : 2,
						unitText : '%'
					});

			var dob_prate = FormUtil.getDoubleField({
						fieldLabel : '放款手续费率',
						name : 'prate',
						"width" : 125,
						"allowBlank" : false,
						hidden : true,
						"value" : "0",
						decimalPrecision : 2,
						unitText : '%'
					});
			
			var rad_inRateType = FormUtil.getRadioGroup({
						fieldLabel : '内部利率类型',
						name : 'inRateType',
						"width" : 180,
						"maxLength" : 10,
						items : [{
									"boxLabel" : "月利率",
									"name" : "inRateType",
									"inputValue" : 1
								}, {
									"boxLabel" : "日利率",
									"name" : "inRateType",
									"inputValue" : 2
								}, {
									"boxLabel" : "年利率",
									"name" : "inRateType",
									"inputValue" : 3
								}]
					});
				// 民汇公司内部利率	
			var dob_inRate = FormUtil.getDoubleField({
						fieldLabel : '内部利率',
						name : 'inRate',
						"width" : 125,
						"allowBlank" : false,
						"value" : "0",
						decimalPrecision : 2,
						unitText : '%'
					});
					
			var dob_arate = FormUtil.getDoubleField({
						fieldLabel : '提前还款费率',
						name : 'arate',
						"width" : 125,
						hidden : true,
						"allowBlank" : false,
						"value" : "0",
						decimalPrecision : 2,
						unitText : '%'
					});

			var dob_urate = FormUtil.getDoubleField({
						fieldLabel : '罚息利率',
						name : 'urate',
						"width" : 125,
						"allowBlank" : false,
						"value" : "0",
						decimalPrecision : 2,
						unitText : '%'
					});

			var dob_frate = FormUtil.getDoubleField({
						fieldLabel : '滞纳金利率',
						name : 'frate',
						"width" : 125,
						"allowBlank" : false,
						"value" : "0",
						"decimalPrecision" : "2",
						unitText : '%'
					});

			var fset_2 = FormUtil.createLayoutFieldSet({
				title : '利率信息设置&nbsp;&nbsp;(注：<span style="color:red" > 利率默认单位为  "%" </span > )'
			}, [rad_isadvance,dob_prate,dob_arate,{
				cmns : FormUtil.CMN_TWO,
				fields : [rad_rateType, dob_rate, rad_inRateType,dob_inRate,  rad_mgrtype,
						dob_mrate,  dob_urate, dob_frate]
			}]);
			var formDiyContainer = new Ext.Container({layout:'fit'});
			/*----------- 借款情况说明  ------------*/
			var TXA_WIDTH = 600;
			var txa_reason = FormUtil.getTAreaField({
						fieldLabel : '申请原因',
						name : 'reason',
						maxLength : 500,
						width : TXA_WIDTH,
						height :50
					});

			var txa_payremark = FormUtil.getTAreaField({
						fieldLabel : '贷款用途',
						name : 'payremark',
						maxLength : 500,
						width : TXA_WIDTH,
						height : 50
					});

			var txa_sourceDesc = FormUtil.getTAreaField({
						fieldLabel : '还款计划及资金来源',
						name : 'sourceDesc',
						maxLength : 500,
						width : TXA_WIDTH,
						height : 50
					});
			var fset_3 = FormUtil.createLayoutFieldSet({
						title : '借款情况说明'
					}, [txa_reason, txa_payremark, txa_sourceDesc]);
			
			var layout_fields = [txt_id, txt_procId,hid_manager, fset_1,formDiyContainer, fset_2, fset_3];
			var btnTempSaveHtml = this.getButtonHml(this.btnIdObj.btnTempSave,
					'暂存申请单');
			var btnSaveHtml = this.getButtonHml(this.btnIdObj.btnSave, '提交申请单');

			var title = '借款申请单信息&nbsp;&nbsp;'
					+ btnTempSaveHtml
					+ btnSaveHtml
					+ '提示：[<span style="color:red;">带"*"的为必填项，金额默认单位为 "元"</span>]';
			var frm_cfg = {
				title : title,
				 formDiyCfg : {
				    	sysId : _this.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FROMDIY_APPLY,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id',/*对应表单中的ID Hidden 值*/
				    	container : formDiyContainer /*自定义字段存放容器*/
			    	},
				autoScroll : true,
				url : './fcApply_save.action',
				labelWidth : 115
			};
			var applyForm = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			txt_appAmount.setWidth(200);
			rad_loanMain.setWidth(515);
			var _this = this;
			applyForm.addListener('afterrender', function(panel) {
						_this.addListenersToCustButtons([{
									btnId : _this.btnIdObj.btnTempSave,
									fn : function(e, targetEle, obj) {
										_this.saveApplyData(0);
									}
								}, {
									btnId : _this.btnIdObj.btnSave,
									fn : function(e, targetEle, obj) {
										_this.saveApplyData(1);
									}
								}]);
					});

			this.applyPanel = applyForm;
		},
		/**
		 * 保存或提交申请单数据
		 */
		saveApplyData : function(submitType) {
			if (!this.validCustomer()) return;
			if (!this.validApplyForm()) return;
			var _this = this;
			EventManager.frm_save(this.applyPanel, {
						beforeMake : function(formDatas) {
							formDatas.custType = _this.custType;
							formDatas.customerId = _this.customerId;
							formDatas.submitType = submitType;
							if(_this.uuid) formDatas.uuid = _this.uuid;
						},
						sfn : function(formDatas) {
							if(formDatas["applyId"]){
								_this.applyId = formDatas["applyId"];
							}else{
								_this.applyId = null;
							}
							if(_this.uuid) _this.uuid = null;
							var apptabtreewinId = _this.params["apptabtreewinId"];
							Cmw.hideTab(apptabtreewinId,_this.tab);
							
							var tabId = null;
							var params = null;
							if (submitType == 0) {
								if (_this.custType == 0) { /* 跳转到 个人客户暂存申请单 */
									tabId = CUSTTAB_ID.tempCustApplyMgrTabId;
								} else { /* 跳转到 企业客户暂存申请单 */
									tabId = CUSTTAB_ID.tempEntCustApplyMgrTabId;
								}
								Cmw.activeTab(apptabtreewinId, tabId, params);
								if(_this.appGrid){
									_this.appGrid.reload({custType:_this.custType});
								}
							} else {  /* 跳转到 业务审批页面 */
									var code = formDatas["code"];
									var procId = _this.applyPanel.getValueByName("procId");
									 ExtUtil.confirm({title:'提示',msg:'确定提交编号为："'+code+'"的贷款申请单?',fn:function(){
									 		tabId = CUSTTAB_ID.flow_auditMainUITab.id;
											params = {customerId:_this.customerId,custType:_this.custType,applyId:_this.applyId,procId:procId};
											params["isnewInstance"] = true;
											url = CUSTTAB_ID.flow_auditMainUITab.url;
											var title =  '业务审批';
											Cmw.activeTab(apptabtreewinId, tabId, params, url, title);
									 }});
							}
						},ffn : function(formDatas){
							var id = formDatas.id;
							if(id == -1){
								ExtUtil.alert({msg : "输入的贷款申请单编号必须唯一！"});
								return;
							}
						}
					});
		},
		/**
		 * 申请表单数据验证
		 */
		validApplyForm : function() {
			var errMsg = [];
			// 贷款金额
			var appAmount_val = this.applyPanel.getValueByName("appAmount");
			if (!appAmount_val || parseFloat(appAmount_val) <= 0) {
				errMsg[errMsg.length] = "贷款金额必须大于0!";
			}
			// 贷款期限 yearLoan monthLoan dayLoan
			var limitLoan_val = this.applyPanel.getValueByName("limitLoan");
			if (!limitLoan_val) {
				errMsg[errMsg.length] = "贷款期限必须大于0!";
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
					errMsg[errMsg.length] = "贷款期限的年,月,日至少有一个必须大于0!";
				}
			}
			
			var breedVal = this.applyPanel.getValueByName("breed");
			if(!breedVal){
				errMsg[errMsg.length] = "业务品种不能为空!";
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
			var rate_val = this.applyPanel.getValueByName("rate");
			if (!rate_val || parseFloat(rate_val) <= 0) {
				errMsg[errMsg.length] = "贷款利率必须大于0!";
			}
			// 管理费收取方式
			var mgrtype_val = this.applyPanel.getValueByName("mgrtype");
			if(!mgrtype_val) mgrtype_val = 0;
			// 管理费率
			var mrate_val = this.applyPanel.getValueByName("mrate");
			if (mrate_val < 0) {
				errMsg[errMsg.length] = "管理费收取方式不能为空!";
			} else {
				if (mgrtype_val == 1 && (!mrate_val || mrate_val <= 0)) {
					errMsg[errMsg.length] = "按还息方式收取管理费；管理费率必须大于0!";
				}
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
		resize : function(adjWidth, adjHeight) {
			this.appMainPanel.setWidth(adjWidth - 0);
			this.appMainPanel.setHeight(adjHeight - 2);
			var custEl = this.customerPanel.getEl();
			var cobEl = this.coborrManPanel.getEl();
			var height = this.appMainPanel.getHeight();
			var applyPanelHeight = height - custEl.getComputedHeight()
					- cobEl.getComputedHeight();
			this.applyPanel.setHeight(applyPanelHeight);
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function() {
			if (null != this.coborrManPanel) {
				this.coborrManPanel.destroy();
				this.coborrManPanel = null;
			}
			if (null != this.customerPanel) {
				this.customerPanel.destroy();
				this.customerPanel = null;
			}
			if (null != this.customerDialog) {
				this.customerDialog.destroy();
				this.customerDialog = null;
			}
			if (null != this.entCustomerDialog) {
				this.entCustomerDialog.destroy();
				this.entCustomerDialog = null;
			}
			if (null != this.applyPanel) {
				this.applyPanel = null;
			}
			if (null != this.tab)
				this.tab = null;
			if (null != this.appMainPanel) {
				this.appMainPanel.destroy();
				this.appMainPanel = null;
			}
		}
	}
});