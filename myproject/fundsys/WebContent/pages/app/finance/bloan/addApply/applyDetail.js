/**
 * 菜单新增或修改页面
 * 
 * @author 程明卫
 * @date 2012-07-12
 */
define(function(require, exports) {
	exports.viewUI = {
		appMainPanel : null, /* 主面板 */
		customerPanel : null, /* 客户基本资料面板 */
		coborrManPanel : null, /* 共同借款人面板 */
		applyPanel : null,/* 借款申请单面板 */
		tab : null,
		apptabtreewinId : null,
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
		appTabId : null,
		dispaly : null,
		olddispaly : null,
		AuditMainUI : null,
		btnBack : null,
		remberId : null,
		btnIdObj : {
			btnBack : Ext.id(null, 'btnBack'),/* 选择个人客户 */
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
			this.display = params.dispaly ; 
			this.setParams(tab, params);
			var _this = this;
			if (!this.appMainPanel) {
				this.createCustomerPanel();
				this.createCoborrManPanel();
				this.createApplyPanel();
				this.appMainPanel = new Ext.Panel({
							autoScroll: true,
							items : [this.customerPanel, this.coborrManPanel,
									this.applyPanel]
						});
				this.appMainPanel.addListener('afterrender', function(cmpt) {
							_this.doResize();
						});
				this.appMainPanel.on('collapse',function(cmpt){
							_this.doResize();
						});
			}
			tab.notify = function(_tab) {
				_this.setParams(_tab, _tab.params);
				_this.refresh(_this.params);
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
			this.appTabId = params.apptabtreewinId;
			this.params = params;
			this.sysId = params["sysid"] ? params["sysid"] : null;
			this.customerId = params["customerId"] ? params["customerId"] : null;
			this.custType = params["custType"] ? params["custType"] : null;
			if(!this.custType){
				this.custType = params.custType;
			}
			if(params["applyId"]){
				this.applyId = params["applyId"];
			}
			
		},
		refresh : function(params) {
			var _this = this;
			this.btnBack.disable(); 
			if(this.tab && this.apptabtreewinId){
//				Cmw.showTab(this.apptabtreewinId,this.tab);
			}
			_this.custType = params.custType;
			if(_this.customerPanel){
				if(params.dispaly){
					if(_this.btnBack)
					_this.btnBack.show();
					_this.callbackExpand();
				}else{
					if(_this.btnBack)_this.btnBack.hide();
				}
			}
			if (!this.applyPanel.rendered) {
				this.applyPanel.addListener('render', function(cmpt) {
							_this.loadDatas();
						});
			} else if(this.remberId != params["applyId"]){
				_this.loadDatas();
				this.remberId = params["applyId"];
			}
		},
		createCustomerPanel : function() {
			var btnId = this.btnIdObj.btnBack;
			var btnBack = null;
				btnBack = this.getButtonHml(
					btnId, '返回');
			this.customerPanel = new Ext.Panel({
//						collapsible : true,
						collapsed : false,
						border : false
					});
			this.customerPanel.add(this.getToolBar());
			var _this = this;
			
			this.customerPanel.on('expand', function(cmpt) {
						_this.doResize();
					});
			this.customerPanel.on('collapse', function(cmpt) {
						_this.doResize();
					});
		},
		
		getToolBar : function(){
			var _this = this;
			var toolBar = null;
			var barItems = [{
				text : "返回",
				iconCls:Btn_Cfgs.PREVIOUS_CLS,
				tooltip: "返回",
				handler : function(){
					_this.showBackTab(_this.custType);
				}
			}];
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
			toolBar.addText("<span style='color:#416AA3; ;font-weight:bold;'>申请人资料详情</span>");
			var buttons = toolBar.getButtons();
			this.btnBack = buttons[0]
			return toolBar;
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
			this.showBackTab(custType, btnId);
		},
		/**
		 * 返回
		 */
		showBackTab : function(custType,btnId){
			var _this = this;
			var tabId = CUSTTAB_ID.flow_auditMainUITab.id;
			var url =  CUSTTAB_ID.flow_auditMainUITab.url;
			var title =  '业务审批';
			var  apptreeId = this.appTabId;
			var apptabtreewinId = _this.params["apptabtreewinId"];
			this.apptabtreewinId = apptabtreewinId;
			params = {custType:custType,customerId:this.customerId,id:this.params.id};
			params["applyId"] = this.applyId;
//			this.AuditMainUI = Ext.getCmp(tabId);
			Cmw.hideTab(apptabtreewinId,this.tab);
			Cmw.activeTab(apptabtreewinId,tabId,params,url,title);
			
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
//			this.applyPanel.reset();
//			this.applyPanel.disable();
		},
		/**
		 * 加载数据
		 */
		loadDatas : function() {
//			this.resets();
			if(!this.applyId) return;
//			var id = this.applyPanel.getValueByName("id");
			// 已经加载过数据，就不需要重复加载
			if (this.remberId == this.applyId) return;
			this.loadCustomerData(this.customerId, this.custType);
			this.loadCoborrManData(this.customerId, this.custType);
			var _this = this;
			this.applyPanel.reload({id : _this.applyId});
		},
		/**
		 * 加载客户信息数据
		 */
		loadCustomerData : function(customerId, custType) {
			this.sysId = this.params.sysid;
			var isSameCustType = true;
			if(this.custType && this.custType != custType){
				isSameCustType = false;
			}
			
			this.custType = custType;
			this.customerId = customerId;
			var activePanel = this.showCustPanle(custType);
			activePanel.reload({customerId : customerId,custType : custType});
			this.applyPanel.enable();/*当有客户数据时，启用申请表单面板*/
			this.loadRateDatas();
			
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
			this.getDet(this.applyId,activePnl);
			return activePnl;
		},
		getDet:function(id,activePnl){
			var _this=this;
			EventManager.get("./fuAmountProof_detail.action",
				{params:{id:id},
				sfn : function(jsonData){
					activePnl.reload({custType:jsonData.custType,customerId:jsonData.customerId});
//					_this.loadDatas(jsonData.custType,jsonData.contractId,jsonData.customerId);
//					_this.contractId=jsonData.contractId;
//					_this.addApplyPnl.enable();
//					_this.addApplyPnl.setVs(jsonData);
				}});	
		},
		/**
		 * 获取个人客户详情面板
		 */
		getOneCustDetailPanel : function() {
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
			     		
			     		var custLevel = jsonData["custLevel"];
			     		if(!custLevel && custLevel != 0){
			     		  jsonData["custLevel"] = '暂未设置';
			     		}else{
			     		  jsonData["custLevel"] = Render_dataSource.custLevelRender(''+jsonData["custLevel"]+'');
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
			            jsonData["inArea"] = ia+jsonData["inAddress"];
			            jsonData["sex"] = Render_dataSource.sexRender(''+jsonData["sex"]+'');
			            var maristal = jsonData["maristal"];
			     		jsonData["maristal"] =  Render_dataSource.gvlistRender('100003',maristal);
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
			var htmlArrs_1 = [
					'<tr><th col="code">客户编号</th> <td col="code" >&nbsp;</td><th col="name">企业名称</th> <td col="name"  colspan=3 >&nbsp;</td></tr>',
					'<tr><th col="serialNum">流水号</th> <td col="serialNum" >&nbsp;</td><th col="custLevel">客户级别</th> <td col="custLevel" >&nbsp;</td><th col="kind">企业性质</th> <td col="kind" >&nbsp;</td></tr>',
					'<tr><th col="regcapital">注册资本</th> <td col="regcapital" >&nbsp;</td><th col="regaddress">注册地址</th> <td col="regaddress"  colspan=3 >&nbsp;</td></tr>',
					'<tr><th col="contactor">联系人</th> <td col="contactor" >&nbsp;</td><th col="phone">联系人手机</th> <td col="phone" >&nbsp;</td><th col="contacttel">联系电话</th> <td col="contacttel" >&nbsp;</td></tr>',
					'<tr><th col="tradNumber">工商登记号</th> <td col="tradNumber" >&nbsp;</td><th col="address">企业地址</th> <td col="address"  colspan=3 >&nbsp;</td></tr>'];
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
						var kind = jsonData["kind"];
						jsonData["kind"] = Render_dataSource.gvlistRender('100009', kind);
						var custLevel = jsonData["custLevel"];
			     		if(!custLevel && custLevel != 0){
			     		  jsonData["custLevel"] = '暂未设置';
			     		}else{
			     		  jsonData["custLevel"] = Render_dataSource.custLevelRender(jsonData["custLevel"].toString(10));
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
			            
						var regcapital = jsonData["regcapital"];
						if(regcapital){
							var currency = jsonData["currency"];
							currency = !currency ? "人民币" : Render_dataSource.gvlistRender('100014', currency);
							jsonData["regcapital"] =  Cmw.getThousandths(regcapital)+'元&nbsp;&nbsp;<span style="color:red;">(大写：'+Cmw.cmycurd(regcapital)+')</span>';
						}
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
			var title = '共同借款人资料详情' /*+ btnChoseCoManHtml + btnDelCoManHtml*/;
			
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
					}, {
						header : '现居住地址',
						name : 'inArea',
						width : 150
					}, {
						header : '现居住详细地址',
						name : 'inAddress',
						width : 1,
						hidden : true
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
							category : 1
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
				_this.btnBack.enable();
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
			var html = null;
			if(!this.params.dispaly){
				html = "&nbsp;&nbsp;<span  class='btnBussNodeCls' style='display:none' id='" + id
				+ "'>" + text + "</span>&nbsp;&nbsp;";
			}else{
				html = "&nbsp;&nbsp;<span  class='btnBussNodeCls' id='" + id
				+ "'>" + text + "</span>&nbsp;&nbsp;";
			}
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
			var htmlArrs_1 = [
					'<tr><th col="bdate">原借款日期</th> <td col="bdate" >&nbsp;</td><th col="amount">原借款金额</th> <td col="amount" >&nbsp;</td><th col="bamount">追加金额</th> <td col="bamount" >&nbsp;</td></tr>',
					'<tr><th col="tamount">合计金额</th> <td col="tamount" >&nbsp;</td><th col="appManId">申请人</th> <td col="appManName" >&nbsp;</td><th col="appDate">申请日期</th> <td col="appDate" >&nbsp;</td></tr></tr>'];
			var detailCfgs_1 = [{
				title : '追加申请单信息',
			    cmns: 'THREE',
			    model: 'single',
			    labelWidth: 90,
			    htmls: htmlArrs_1,
			    url: './fuAmountProof_detail.action',
			    isLoad : false,
			    params: {
			        id:_this.applyId
			    },
		    	formDiyCfg : {sysId:_this.sysId,formdiyCode : FORMDIY_IND.FROMDIY_APPLY,formIdName:'id'},
			    callback: {
			        sfn: function(jsonData) {
			        	jsonData["amount"]=Render_dataSource.moneyRender(jsonData["amount"]);
						jsonData["bamount"]=Render_dataSource.moneyRender(jsonData["bamount"]);
						jsonData["tamount"]=Render_dataSource.moneyRender(jsonData["tamount"]);
			        }
			    }
			}];
			var detailPanel_1 = new Ext.ux.panel.DetailPanel({
			    autoWidth : true,
			    detailCfgs: detailCfgs_1,
			    autoScroll: true,
			    isLoad : false
			});

			this.applyPanel = detailPanel_1;
		},
		/**
		 * 获取贷款期限
		 */
		getLoanLimit : function(jsonData){
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
	       return (arr.length > 0) ? arr.join("") : "";
		},
		/**
		 * 保存或提交申请单数据
		 */
		saveApplyData : function(submitType) {},
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