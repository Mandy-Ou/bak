/**
 * 追加资金申请
 * @author 彭登浩
 * @date 2014-03-31
 */
define(function(require, exports) {
	exports.viewUI = {
		name : null,
		tab : null,
		params : null,
		addApplyPnl  : null,
		customerPnl : null,
		custCommonGrid : null,
		addApplyMainPnl : null,
		customerDialog : null,
		oneCustDetailPnlId : Ext.id(null, 'oneCustDetailPnlId'),/* 个人客户详情面板ID */
		entCustDetailPnlId : Ext.id(null, 'entCustDetailPnlId'),/* 企业客户详情面板ID */
		applyId :null,
		uuid : null,/*用来临时绑定共同借款人和贷款申请单的唯一标识*/
		attachMentFs : null,
		contractId : null,/*借款合同Id*/
		breed : null,
		id:null,
		sysId:null,
		customerId : null,
		custType : null,
		custCode:null,
		btnIdObj :{
			btnChoseCust : Ext.id(null,'btnChoseCust'),
			btnTempSave : Ext.id(null,'btnTempSave'),
			btnSave : Ext.id(null,'btnSave')
		},
		/**
		 * 设置参数
		 */
		setParams: function(params){
			this.params = params;
			this.sysId = params["sysid"] ? params["sysid"] : null;
			this.customerId = params["customerId"] ? params["customerId"] : null;
			this.custType = params["custType"] ? params["custType"] : null;
			if(params.breed){
				this.breed = params.breed;
			}
		},
		
		/**
		 * 加载面板主方法
		 */
		getMainUI :function(tab, params){
			var _this = this;
			this.tab = tab;
			this.setParams(params);
			if(!this.addApplyMainPnl){
				this.createDetailPnl();
				this.creatCommonApplyGrid();
				this.createAddApplyPnl();
				this.addApplyMainPnl = new Ext.Panel({items:[this.customerPnl,this.custCommonGrid,this.addApplyPnl]});
			}
			tab.notify = function(_tab) {
				_this.setParams(_tab, _tab.params);
				_this.refresh();
			};
			var id=_this.params.applyId;
			_this.id=id;
			if(id){
				_this.getDet(id);
			}
				
			return this.addApplyMainPnl;
		},
		getDet:function(id){
			var _this=this;
			EventManager.get("./fuAmountProof_get.action",
				{params:{id:id},
				sfn : function(jsonData){
					_this.loadDatas(jsonData.custType,jsonData.contractId,jsonData.customerId);
					_this.contractId=jsonData.contractId;
					_this.addApplyPnl.enable();
					_this.addApplyPnl.setVs(jsonData);
				}});	
		},
		getButtonHml : function(id, text) {
			var html = "&nbsp;&nbsp;<span class='btnBussNodeCls' id='" + id
					+ "'>" + text + "</span>&nbsp;&nbsp;";
			return html;
		},
		/**
		 * 页面刷新
		 */
		refresh : function(jsonData){
			this.resets();
			if (!this.addApplyPnl.rendered) {
				var _this = this;
				this.addApplyPnl.addListener('render', function(cmpt) {
							_this.load(jsonData);
						});
			} else {
				this.load(jsonData);
			}
		},
		/**
		 * 加载数据
		 */
		load : function(jsonData){
			var _this = this;
			if(jsonData){
				this.addApplyPnl.enable();
				this.customerPnl.show();
				var customerId = jsonData.id;
				var custType = jsonData.custType;
				this.loadCoborrManData(customerId, custType);
				var params = {contractId:this.contractId};
				EventManager.get('./fuAmountProof_getContract.action',{params:params , sfn :  function(json_data){
					var userParams = {appManId:CURRENT_USERID,empName:CURENT_EMP,name :_this.name,breed:_this.breed };
					Ext.applyIf(json_data,userParams);
					_this.addApplyPnl.setJsonVals(json_data);
				},ffn:function(json_data){
					ExtUtil.alert({msg:"加载数据出错！"});
					return;
				}});	
			}
		},
		/**
		 * 重置表单数据
		 */
		resets : function(){
			this.uuid = null;
			/*--> step 1 隐藏个人客户和企业客户详情面板<--*/
			var oneCustDetailPnl = Ext.getCmp(this.oneCustDetailPnlId);
			if(oneCustDetailPnl) oneCustDetailPnl.hide();
			var entCustDetailPnl = Ext.getCmp(this.entCustDetailPnlId);
			if(entCustDetailPnl) entCustDetailPnl.hide();
			/*--> step 2 重置共同借款人面板数据<--*/
			this.custCommonGrid.removeAll();
			this.custCommonGrid.collapse();
			/*--> step 3  重置申请单面板数据 <--*/
			this.addApplyPnl.reset();
			this.addApplyPnl.disable();
		
		},
		/**
		 * 创建客户详情面板
		 */
		createDetailPnl : function(){
			var _this = this;
			var btnChoseCustHtml = this.getButtonHml(this.btnIdObj.btnChoseCust, '添加资金追加客户');
			this.customerPnl = new Ext.Panel({
						title : '追加客户资料' + btnChoseCustHtml,
						collapsible : true,
						collapsed : false,
						border : false
					});
			
			this.customerPnl.addListener('afterrender', function(panel) {
						_this.addListenersToCustButtons([{
							btnId : _this.btnIdObj.btnChoseCust,
							fn : function(e, targetEle, obj) {
								_this.openDialog(obj);
							}
						}]);
					});
			this.customerPnl.on('expand', function(cmpt) {
						_this.doResize();
					});
			this.customerPnl.on('collapse', function(cmpt) {
						_this.doResize();
					});
			
		},
		doResize : function(){
			var tabWidth = this.tab.getWidth();
			var tabHeight = this.tab.getHeight();
			this.resize(tabWidth, tabHeight);
		},
		resize : function(adjWidth, adjHeight) {
			this.addApplyMainPnl.setWidth(adjWidth - 0);
			this.addApplyMainPnl.setHeight(adjHeight - 2);
			var custEl = this.customerPnl.getEl();
			var cobEl = this.custCommonGrid.getEl();
			var height = this.addApplyMainPnl.getHeight();
			var applyPanelHeight = height - custEl.getComputedHeight()
					- cobEl.getComputedHeight();
			this.addApplyPnl.setHeight(applyPanelHeight);
		},
		/**
		 * 显示追加客户win
		 */
		openDialog : function(btn){
			var _this = this;
			var parentCfg = {
				parent : btn, 
				params : {},
				callback: function(contractId,record){
					_this.contractId = contractId;
					var custType  = record.json.custType;
					var customerId = record.json.customerId;
					_this.loadDatas(custType,contractId,customerId);
				}
			};
			Cmw.importPackage('/pages/app/dialogbox/AddApplyDialogbox', function(module) {
				_this.customerDialog = module.DialogBox;
				_this.customerDialog.show(parentCfg);
			});
		},
		/**
		 * 加载数据
		 */
		loadDatas : function(custType,contractId,customerId){
			if (!Ext.isNumber(custType)) {
				custType = parseInt(custType);
			}
			var activePnl = null;
			var activePnlId = null;
				switch (custType) {	
					case 0 : { /* 个人客户 */
						activePnlId = this.oneCustDetailPnlId;
						var activePnl = Ext.getCmp(activePnlId);
						if (!activePnl) {
							activePnl = this.getOneCustDetailPanel();
						}
					break;
				}
				case 1 : { /* 企业客户 */
					activePnlId = this.entCustDetailPnlId;
					var activePnl = Ext.getCmp(activePnlId);
					if (!activePnl) {
						activePnl = this.getEntCustDetailPanel();
					} 
					break;
				}
			}
			activePnl.show();
			var hidePnlId = null;
			if(activePnlId == this.oneCustDetailPnlId){
				hidePnlId = this.entCustDetailPnlId;
			}else{
				hidePnlId = this.oneCustDetailPnlId;
			}
			var hidePnl = Ext.getCmp(hidePnlId);
			if(hidePnl){
				hidePnl.hide();
			}
			this.customerPnl.add(activePnl);
			this.customerPnl.doLayout();
			activePnl.reload({custType:custType,customerId:customerId});
			return activePnl;
		},
		/**
		 *  个人客户详情面板
		 */
		getOneCustDetailPanel : function(){
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
			        	_this.name = jsonData.name;
			        	_this.custCode=jsonData.code;
			        	_this.addApplyPnl.findFieldByName("name").setValue(jsonData.name);
			        	if(!_this.id)_this.load(jsonData);
			        	var cardType = jsonData["cardType"];
			     		jsonData["cardType"] = Render_dataSource.gvlistRender('100002',cardType);
			     		
						
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
			var width = this.customerPnl.getWidth();
			var detailPanel = new Ext.ux.panel.DetailPanel({
				isLoad  : false,
				id : this.oneCustDetailPnlId,
			    width: width,
			    detailCfgs: detailCfgs_1
			});
			return detailPanel;
			
		},
		/**
		 * 企业客户详情面板
		 */
		getEntCustDetailPanel : function(){
			
			var  _this = this;
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
						_this.name = jsonData.name;
			        	_this.custCode=jsonData.code;
						_this.addApplyPnl.findFieldByName("name").setValue(jsonData.name);
						if(!_this.id)_this.load(jsonData);
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
			var width = this.customerPnl.getWidth();
			var detailPanel = new Ext.ux.panel.DetailPanel({
						isLoad  : false,
						id : this.entCustDetailPnlId,
						width : width,
						detailCfgs : detailCfgs_1,
						border : false
					});
			return detailPanel;
		
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
			this.custCommonGrid.reload({
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
				var count = _this.custCommonGrid.getStore().getCount();
				if(count){
					_this.custCommonGrid.expand();
				}
			}).delay(500);
		},
		
		/**
		 * 创建共同借款人面板
		 */
		creatCommonApplyGrid : function(){
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
			this.custCommonGrid = new Ext.ux.grid.AppGrid({
						title : "共同借款人",
						collapsible : true,
						collapsed : true,
						structure : structure_1,
						url : './crmCategory_list.action',
						needPage : false,
						isLoad : false,
						keyField : 'categoryId',
						height : 125
					});
					
			return this.custCommonGrid;
		},
		/**
		 * 创建追加资金申请单单面板
		 */
		createAddApplyPnl : function(){
			var _this = this;
			var txt_id = FormUtil.getHidField({
				fieldLabel : '资金追加申请ID',
				name : 'id',
				"width" : "125"
			});
			
			
			var txt_procId = FormUtil.getHidField({
						fieldLabel : '流程实例ID',
						name : 'procId'
					});
			
			var appManId_txt = FormUtil.getHidField({
				width : 150,
				name : 'appManId',
				fieldLabel : "申请人",
				value : CURRENT_USERID
			});
			
			var txt_contractId = FormUtil.getHidField({
				fieldLabel : '借款合同ID',
				name : 'contractId',
				"width" : "150"
			});
			var txt_breed = FormUtil.getHidField({
						fieldLabel : '子业务流程ID',
						name : 'breed'
					});
			var txt_name = FormUtil.getReadTxtField({
				fieldLabel : '客户姓名',
				name : 'name',
				 allowBlank : false,
				width : 150
			});
			var date_bdate = FormUtil.getReadTxtField({
				fieldLabel : '原借款日期',
				name : 'bdate',
				 allowBlank : false,
				width : 150
			});
			var mon_amount = FormUtil.getMoneyField({
				fieldLabel : '原借款金额',
				name : 'amount',
				allowBlank : false,
				readOnly : true,
				width : 260,
				autoBigAmount : true,
				unitText : '此处将显示大写金额',
				unitStyle : 'margin-left:4px;color:red;font-weight:bold'
			});
			var mon_bamount = FormUtil.getMoneyField({
				fieldLabel : '追加金额',
				name : 'bamount',
				 allowBlank : false,
				"width" : "260",
				autoBigAmount : true,
				unitText : '此处将显示大写金额',
				unitStyle : 'margin-left:4px;color:red;font-weight:bold',
				listeners :{
					change : function(o,newValue,oldValue ){
						var amount  = mon_amount.getValue();
						var tamount = amount+newValue;
						mon_tamount.setValue(tamount);
					}
				}
			});
			var mon_tamount = FormUtil.getMoneyField({
				fieldLabel : '合计金额',
				name : 'tamount',
				 allowBlank : false,
			  	readOnly : true,
				"width" : "260",
				autoBigAmount : true,
				unitText : '此处将显示大写金额',
				unitStyle : 'margin-left:4px;color:red;font-weight:bold'
			});
			
			var empName_txt = FormUtil.getReadTxtField({
				width : 150,
				name : 'empName',
				fieldLabel : "申请人",
				value : CURENT_EMP
			});
			
			var date_appDate = FormUtil.getDateField({
				fieldLabel : '申请日期',
				name : 'appDate',
				 allowBlank : false,
				width : 150
			});
			
			
			var txa_remark = FormUtil.getTAreaField({
					fieldLabel : '备注',
					name : 'remark',
					maxLength : 200,
					width : 500,
					height :50
				});
			this.createAttachMentFs();
			var firstPanel = new Ext.Panel({layout:'form',items:[txa_remark]});
	 		var twoPanel = new Ext.Panel({layout:'form',items:[this.attachMentFs]});
	 		
	 		var layoutCmnPanel = FormUtil.getLayoutPanel([.66,.33],[firstPanel,twoPanel]);
	 		
			var layout_fields = [txt_id,txt_contractId,txt_breed,appManId_txt,txt_procId,
			{cmns : FormUtil.CMN_TWO,fields : [txt_name,date_bdate,mon_amount,mon_bamount,mon_tamount,empName_txt,date_appDate]},
			layoutCmnPanel];
			var btnTempSaveHtml = this.getButtonHml(this.btnIdObj.btnTempSave,
					'暂存申请单');
			var btnSaveHtml = this.getButtonHml(this.btnIdObj.btnSave, '提交申请单');

			var title = '追加申请单信息&nbsp;&nbsp;'
					+ btnTempSaveHtml
					+ btnSaveHtml
					+ '提示：[<span style="color:red;">带"*"的为必填项，金额默认单位为 "元"</span>]';
			var formDiyContainer = new Ext.Container({layout:'fit'});		
			var frm_cfg = {
				title : title,
				 formDiyCfg : {
				    	sysId : _this.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FROMDIY_APPLY,//引用Code 
				    	formIdName : 'id',/*对应表单中的ID Hidden 值*/
				    	container : formDiyContainer /*自定义字段存放容器*/
			    	},
				autoScroll : true,
				disabled : true,
				url : './fuAmountProof_save.action',
				labelWidth : 115
			};
			var applyForm = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			var par=_this.params;
			applyForm.addListener('afterrender', function(panel) {
						_this.addListenersToCustButtons([{
									btnId : _this.btnIdObj.btnTempSave,
									fn : function(e, targetEle, obj) {
										_this.saveApplyData(0,par);
									}
								}, {
									btnId : _this.btnIdObj.btnSave,
									fn : function(e, targetEle, obj) {
										_this.saveApplyData(1,par);
									}
								}]);
					});
			this.addApplyPnl = applyForm;
		},
		/**
		 * 创建附件FieldSet 对象
		 * @return {}
		 */
		createAttachMentFs : function(){
			/* ----- 参数说明： isLoad 是否实例化后马上加载数据 
			 * dir : 附件上传后存放的目录， mortgage_path 定义在 resource.properties 资源文件中
			 * isSave : 附件上传后，是否保存到 ts_attachment 表中。 true : 保存
			 * params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔}
			 *    当 isLoad = false 时， params 可在 reload 方法中提供
			 *  ---- */
			var uuid = Cmw.getUuid();
			var params = this.getAttachParams(uuid);
			this.attachMentFs = new  Ext.ux.AppAttachmentFs({title:'追加资金申请相关附件上传',isLoad:false,dir : 'amountProof_path',isSave:true,params:params});
		},
		/**
		 * 获取附件参数
		 * @param formId 业务单ID
		 */
		getAttachParams : function(formId){
			var sysId = this.params.sysid;
			return {sysId:sysId,formType:ATTACHMENT_FORMTYPE.FType_38,formId:formId};
		},
		/**
		 * 保存追加资金申请单
		 *  @param	submitType	[0:暂存,1:提交]
		 */
		saveApplyData:function(submitType,params){
			var _this = this;
			var currTabId = params.tabId;
			var apptabtreewinId =params.apptabtreewinId;
			EventManager.frm_save(this.addApplyPnl, {
				beforeMake : function(formDatas) {
					formDatas.submitType = submitType;
					if(_this.uuid) formDatas.uuid = _this.uuid;
				},
				sfn : function(formDatas) {
					if(formDatas.id){
						_this.applyId = formDatas.id;
					}else{
						_this.applyId = null;
					}
					if(_this.applyId){
						var attachParams = _this.getAttachParams(_this.applyId);
						_this.attachMentFs.updateTempFormId(attachParams);
					}
					if(_this.uuid) _this.uuid = null;
					if(currTabId){
						var currTab = Ext.getCmp(currTabId);
						if(currTab) currTab.destroy();					
					}
					var tabId = null;
					var params = null;
					if (submitType == 0) {
						tabId = CUSTTAB_ID.temAmountProofTabId.id;
						Cmw.activeTab(apptabtreewinId, tabId, params);
					} else {  /* 跳转到 业务审批页面 */
						var code = _this.custCode;
						var procId = _this.addApplyPnl.getValueByName("procId");
						 ExtUtil.confirm({title:'提示',msg:'确定提交编号为："'+code+'"的资金追加申请单?',fn:function(){
						 	var params = {customerId:_this.customerId,custType:_this.custType,applyId:_this.applyId,procId:procId,bussProccCode:'B0005'};
							var tabId = CUSTTAB_ID.bussProcc_auditMainUITab.id;
							var url = CUSTTAB_ID.bussProcc_auditMainUITab.url;
							var title =  '资金追加审批';
							Cmw.activeTab(apptabtreewinId, tabId, params, url, title);
						 }});
					}
				}
			});
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
		 * 组件销毁方法
		 */
		destroy : function() {
			if(this.addApplyPnl != null){
				this.addApplyPnl.destroy();
				this.addApplyPnl = null;
			}
			
			if(this.custCommonGrid != null){
				this.custCommonGrid.destroy();
				this.custCommonGrid = null;
			}
			
			if(this.addApplyMainPnl != null){
				this.addApplyMainPnl.destroy();
				this.addApplyMainPnl = null;
			}
			
		}
	}
});