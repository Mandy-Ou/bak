	/**
 * 菜单新增或修改页面
 * @author 程明卫
 * @date 2012-07-12
 */
define(function(require, exports) {
	exports.viewUI = {
		CustInfoPanel : null,
		parentCfg : null,/*tab 对象*/
		parent : null,
		appMainPanel : null,
		tabPanel : null,
		baseFormPanel : null,
		optionType : OPTION_TYPE.EDIT,/* 默认为新增  */
		addressPnlId :null,
		formDatas : null,
		sysId : null,	/*系统ID*/
		customerId : null,
		code : null,
		params : null,
		custType: Buss_Constant.CustType_0,
		consortPnlId :null,
		customerInfoId :null,
		creditCustomerId:null,
		tab : null,
		dispaly : null,
		apptabtreewinId : null,
		btnBack : null,
		applyId : null,
		changeSize : this.changeSize,
		whArr : null,
		attachMentArray : [],
		tabIdMgr:{/* Tab Id 定义*/
			custinfoPnlId : Ext.id(null,'custinfoPnlId'),	/*个人详细资料面板ID*/
			consortPnlId : Ext.id(null,'consortPnlId'),/*配偶资料面板ID*/
			guaCustomerPnlId : Ext.id(null,'guaCustomerId'),/*第三方担保人*/
			addressPnlId : Ext.id(null,'addressPnlId'),/*住宅面板ID*/
			estatePnlId : Ext.id(null,'estatePnlId'),/*房产物业面板ID*/
			workPnlId : Ext.id(null,'workPnlId'),/*职业面板ID*/
			companyPnlId : Ext.id(null,'companyPnlId'),/*旗下公司面板ID*/
			contactorPnlId : Ext.id(null,'contactor'),/*主要联系人面板ID*/
			creditInfoPnlId : Ext.id(null,'creditInfoPnlId'),/*个人信用面板ID*/
			otherInfoPnlId : Ext.id(null,'otherInfoPnlId')/*其他信息面板ID*/
		},
		detailIdMgr:{/* Detail 中的Id 定义*/
			guaCustomerDetailId : Ext.id(null,'guaCustomerDetailId'),/*第三方担保人*/
			addressDetailId : Ext.id(null,'addressDetailId'),/*住宅面板ID*/
			estateDetailId : Ext.id(null,'estateDetailId'),/*房产物业面板ID*/
			workDetailId : Ext.id(null,'workDetailId'),/*职业面板ID*/
			companyDetailId : Ext.id(null,'companyDetailId'),/*旗下公司面板ID*/
			contactorDetailId : Ext.id(null,'contactorDetailId'),/*主要联系人面板ID*/
			creditInfoDetailId : Ext.id(null,'creditInfoDetailId'),/*个人信用面板ID*/
			otherInfoDetailId : Ext.id(null,'otherInfoDetailId')/*其他信息面板ID*/
		},
		setParams:function(params){
			this.params=params;
			this.sysId = params.sysid;
			this.dispaly = params.dispaly;
			this.optionType = params.optionType;
			this.customerInfoId =this.params.customerInfoId;
			this.customerId =this.params.customerId;
				if(!this.customerInfoId){ 
					ExtUtil.alert({msg:"编辑客户资料时，请提供   customerInfoId 值"});
					return false;
				}
			return true;
		},
		/**
		 * 显示弹出窗口
		 * @param _parentCfg 弹出之前传入的参数
		 */
		getMainUI : function(tab, params){
			var _this = this;
			this.tab = tab;
			if(params){
				tab.notify =function(_tab){
					_this.refresh(params);
				}
				if(!this.setParams(params))	return;		
			}
			if(!this.appMainPanel){
				this.createAppMainPanel();
				this.createBaseFormPanel();
				this.appMainPanel.add(this.baseFormPanel);
				this.createTabPanel();
				this.appMainPanel.add(this.tabPanel);
			}
			return this.appMainPanel;
		},
		/**
		 * 刷新页面
		 */
		refresh : function(params){
			if(this.tab && this.apptabtreewinId){
				Cmw.showTab(this.apptabtreewinId,this.tab);
			}
			var _this = this;
			this.sysId = params.sysid;
			this.applyId = params.applyId;			
			this.custType = params.custType;
			if(params.dispaly){
				if(params.activeTab){
					this.tabPanel.activate(_this.tabIdMgr.guaCustomerPnlId);
				}else{
					this.tabPanel.activate(_this.tabIdMgr.custinfoPnlId);
				}
				if(_this.btnBack)_this.btnBack.show();
			}else{
				if(params.activeTab){
					this.tabPanel.activate(_this.tabIdMgr.custinfoPnlId);
				}
				if(_this.btnBack)_this.btnBack.hide();
			}
			var secondCustInfoId = params.customerInfoId;//基础id
			var secondCustomerId = params.customerId;//个人表id
//			if(secondCustInfoId != _this.customerInfoId){
				_this.detailPlnLoad(secondCustInfoId,secondCustomerId)
				_this.cleanDetailData();
				_this.customerInfoId = params.customerInfoId;
				_this.customerId = params.customerId;
//			}
		},
		/**
		 * 在点击表格中的数据后，需要清空表格下面中的数据方法
		 */
		cleanDetailData : function(){
			var guaCustomerDetailId = Ext.getCmp(this.detailIdMgr.guaCustomerDetailId);
			var addressDetailId = Ext.getCmp(this.detailIdMgr.addressDetailId);
			var estateDetailId = Ext.getCmp(this.detailIdMgr.estateDetailId);
			var workDetailId = Ext.getCmp(this.detailIdMgr.workDetailId);
			var companyDetailId = Ext.getCmp(this.detailIdMgr.companyDetailId);
			var contactorDetailId = Ext.getCmp(this.detailIdMgr.contactorDetailId);
			var creditInfoDetailId = Ext.getCmp(this.detailIdMgr.creditInfoDetailId);
			var otherInfoDetailId = Ext.getCmp(this.detailIdMgr.otherInfoDetailId);
			var items = [addressDetailId,estateDetailId,workDetailId,companyDetailId,contactorDetailId,
					creditInfoDetailId,otherInfoDetailId,guaCustomerDetailId];
			this.load(items,-1);
		},
		/**
		 * 加载数据
		 */
		detailPlnLoad : function(loadId,customerId){
			this.baseFormPanel.reload({customerId: loadId,custType:0,id:customerId});
			var length = this.attachMentArray.length;
			if(length>0){
				for (var index = 0; index < length; index++) {
					this.attachMentArray[index].disable();
				}
			}
			/*个人详细资料面板ID*/
			var custinfoPnlId = Ext.getCmp(this.tabIdMgr.custinfoPnlId);
			/*配偶资料面板ID*/
			var consortPnlId = Ext.getCmp(this.tabIdMgr.consortPnlId);
			/*住宅面板ID*/
			var addressPnlId = Ext.getCmp(this.tabIdMgr.addressPnlId);
			/*房产物业面板ID*/
			var estatePnlId = Ext.getCmp(this.tabIdMgr.estatePnlId);
			/*职业面板ID*/
			var workPnlId = Ext.getCmp(this.tabIdMgr.workPnlId);
			/*旗下公司面板ID*/
			var companyPnlId = Ext.getCmp(this.tabIdMgr.companyPnlId);
			/*主要联系人面板ID*/
			var contactorPnlId = Ext.getCmp(this.tabIdMgr.contactorPnlId);
			/*个人信用面板ID*/
			var creditInfoPnlId = Ext.getCmp(this.tabIdMgr.creditInfoPnlId);
			/*第三担保人ID*/
			var guaCustomerPnlId = Ext.getCmp(this.tabIdMgr.guaCustomerPnlId);
			/*其它信息面板ID*/
			var otherInfoPnlId = Ext.getCmp(this.tabIdMgr.otherInfoPnlId);
			var items = [custinfoPnlId,consortPnlId,addressPnlId,estatePnlId,workPnlId,companyPnlId,contactorPnlId,creditInfoPnlId,otherInfoPnlId,guaCustomerPnlId]
			this.load(items,customerId);
		},
		load :function(items,customerId){
			var _this = this;
			var Pnl = null;
			for(var i=0,j=items.length;i<j;i++){
				var Pnl = items[i];
				if(!Pnl) continue;
				var xtype = Pnl.getXType();
				if(xtype=='appgrid'){
					Pnl.reload({customerId:customerId,custType:0,baseId :_this.params.customerInfoId });
				}else{
					Pnl.reload({id:customerId,custType:0});
				}
			}
			
		},
		getTabPanelId :function(customerInfoId,customerId){
			this.baseFormPanel.reload({customerId:customerInfoId,custType:0,id:customerId});
			
			var custInfoAppForm = Ext.getCmp(this.tabIdMgr.custinfoPnlId);
			if(custInfoAppForm){
				custInfoAppForm.reload({id : customerId});
			}
			
			var consortAppForm = Ext.getCmp(this.tabIdMgr.consortPnlId);
			if(consortAppForm){
				consortAppForm.reload({id : customerId});
			}
			var creditCustomerAppFrm = Ext.getCmp(this.tabIdMgr.guaCustomerPnlId);
			if(creditCustomerAppFrm){
				creditCustomerAppFrm.reload({id : customerId});
			}
			
			var addressAppGrid = Ext.getCmp(this.tabIdMgr.addressPnlId);
			if(addressAppGrid){
				this.removeAddReload(addressAppGrid,customerId);
			}
			
			var estateAppGrid = Ext.getCmp(this.tabIdMgr.estatePnlId);
			if(estateAppGrid){
				this.removeAddReload(estateAppGrid,customerId);
			}
			
			var workAppGrid = Ext.getCmp(this.tabIdMgr.workPnlId);
			if(workAppGrid){
				this.removeAddReload(workAppGrid,customerId);
			}
			
			var companyAppGrid = Ext.getCmp(this.tabIdMgr.companyPnlId);
			if(companyAppGrid){
				this.removeAddReload(companyAppGrid,customerId);
			}
			
			var contactorAppGrid = Ext.getCmp(this.tabIdMgr.contactorPnlId);
			if(contactorAppGrid){
				this.removeAddReload(contactorAppGrid,customerId);
			}
			
			var creditInfoAppGrid = Ext.getCmp(this.tabIdMgr.creditInfoPnlId);
			if(creditInfoAppGrid){
				this.removeAddReload(creditInfoAppGrid,customerId);
			}
			var otherInfoPnlIdGrid = Ext.getCmp(this.tabIdMgr.otherInfoPnlId);
			if(otherInfoPnlIdGrid){
				this.removeAddReload(otherInfoPnlIdGrid,customerId);
			}
		},
		removeAddReload:function(appGrid,customerId){
			appGrid.removeAll();
			appGrid.reload({customerId : customerId,custType:0});
		},
		changeSize :function(whArr){
			var width = whAr[0]-50;
			var height = whAr[1]-2;
			this.appMainPanel.setWidth(width);
			this.appMainPanel.setWidth(height);
		},
		/**
		 * 创建主应用程序面板
		 */
		createAppMainPanel : function(){
			var _this =this;
			var btnId = this.btnBack;
			this.appMainPanel = new Ext.Panel({border:false,autoScroll: true});
			this.appMainPanel.add(this.getToolBar());
		},
		getToolBar : function(){
			var _this = this;
			var toolBar = null;
			var barItems = [{
				text : "返回",
				iconCls:Btn_Cfgs.PREVIOUS_CLS,
				handler : function(){	
					_this.showBackTab(_this.custType);
				}
			}];
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
			toolBar.addText("<span style='color:#416AA3; ;font-weight:bold;'>客户基本信息详情</span>");
			var buttons = toolBar.getButtons();
			this.btnBack = buttons[0]
			return toolBar;
		},
		/**
		 * 返回
		 */
		showBackTab : function(custType,btnId){
			var _this = this;
			var tabId = CUSTTAB_ID.flow_auditMainUITab.id;
			var url =  CUSTTAB_ID.flow_auditMainUITab.url;
			var title =  '业务审批';
			var apptabtreewinId = _this.params["apptabtreewinId"];
			this.apptabtreewinId =apptabtreewinId;
			params = {custType:custType,customerId:this.customerId};
			params["applyId"] = this.applyId;
			Cmw.hideTab(this.params["apptabtreewinId"],this.tab);
			Cmw.activeTab(apptabtreewinId,tabId,params,url,title);
			
			
		},
		createBaseFormPanel : function(){
			var customerId = this.customerInfoId;
			var htmlArrs = [
				'    <th><label col="name">编码：</label></th>',
				'    <td col="code">&nbsp;</td>',
				'    <th><label col="name">姓名：</label></th>',
				'    <td  col="name">&nbsp;</td>',
				'    <th><label col="serialNum">客户流水号：</label></th>',
				'    <td  col="serialNum">&nbsp;</td>',
				'  </tr>',
				'  <tr>',
				'    <th ><label col="cardType">证件类型：</label></th>',
				'    <td col="cardType">&nbsp;</td>',
				'    <th><label col="cardNum">证件号码：</label></th>',
				'    <td col="cardNum">&nbsp;</td>',
				'    <th><label col="sex">性别：</label></th>',
				'    <td col="sex">&nbsp;</td>',
				'  </tr>',
				'  <tr>',
				'    <th><label col="regman">登记人：</label></th>',
				'    <td col="regman">&nbsp;</td>',
				'    <th><label col="registerTime">登记日期：</label></th>',
				'    <td col="registerTime"  colspan="3">&nbsp;</td>',
				'  </tr>',
				'</table>'
			];
			
			var  detailCfgs = [{
				 cmns : 'TREE', 
				 model : 'single',
				 labelWidth : 80,
				 htmls : htmlArrs,
				 url : './crmCustBase_detailget.action',
				 params : {customerId:customerId},
				 callback : {sfn:function(jsonData){
				 	jsonData["sex"] = Render_dataSource.sexDetailRender(jsonData["sex"]);
				 	jsonData["cardType"] = Render_dataSource.gvlistRender('100002',jsonData["cardType"]);
				 }
			}}];
			var cfg = {autoWidth:true,detailCfgs:detailCfgs, isLoad : false};
			var panel  = new Ext.ux.panel.DetailPanel(cfg);
			this.baseFormPanel = panel;
			return this.baseFormPanel;
		},
		/**
		 * 创建主 TabPanel 对象
		 */
		createTabPanel : function(){
			var _this = this;
			 var bodyStyle = "";
				bodyStyle = "margin:0;padding:0;width:100%;height:100%";
			this.tabPanel = new Ext.TabPanel({
				 border   : false,
				enableTabScroll : true,autoScroll: true,
				plugins: [Ext.plugin.DragDropTabs],
			items : [
				{title : '个人详细资料',itemId : this.tabIdMgr.custinfoPnlId,style : bodyStyle},
				{title : '配偶资料',itemId : this.tabIdMgr.consortPnlId,bodyStyle : bodyStyle},
				{title : '第三方担保人信息',itemId :this.tabIdMgr.guaCustomerPnlId,bodyStyle : bodyStyle},
				{title : '住宅资料',itemId : this.tabIdMgr.addressPnlId,bodyStyle : bodyStyle},
				{title : '房产物业资料',itemId : this.tabIdMgr.estatePnlId,bodyStyle : bodyStyle},
				{title : '职业资料',itemId : this.tabIdMgr.workPnlId,bodyStyle : bodyStyle},
				{title : '旗下公司资料',itemId : this.tabIdMgr.companyPnlId,bodyStyle : bodyStyle},
				{title : '主要联系人资料',itemId : this.tabIdMgr.contactorPnlId,bodyStyle : bodyStyle},
				{title : '个人信用资料',itemId : this.tabIdMgr.creditInfoPnlId,bodyStyle : bodyStyle},
				{title : '其它信息',itemId : this.tabIdMgr.otherInfoPnlId,bodyStyle : bodyStyle}
			]});
			this.tabPanel.addListener("render",function(tabPanel){
				tabPanel.setActiveTab(0);
				var wh= _this.getWH();
				var width = wh[0];
				var height = wh[1];
				tabPanel.setWidth(width);
				tabPanel.setHeight(height);
			});
			this.tabPanel.addListener('tabchange', function(tabPanel,tab){
				var itemId = tab["itemId"];
				var panel = Ext.getCmp(itemId);
				if(!panel){
					var wh= _this.getWH();
					var width = wh[0];
					var height = wh[1];
					var tabItem = _this.getTabItem(tab,itemId,width,height);
					var tabTitle = tab["title"];
					if(!tabItem){
						ExtUtil.error({msg : '"'+tabTitle+'"中没有组件可加载!'});
						return;
					}
					tab.add(tabItem);
				}
				_this.loadItemPanel(panel);
				tab.doLayout();
			},this);
		},
		loadItemPanel : function(panel){
			var _this = this;
			if(!panel) return;
			var itemId = panel.id;
			var xtype = panel.getXType();
			var customerId = panel.customerId;
			if(customerId ==_this.customerId){
				return;
			}else{
				if(xtype=='appgrid'){
					panel.reload({customerId:_this.customerId,custType:0,baseId :_this.params.customerInfoId});
				}else{
					panel.reload({id:_this.customerId,custType:0});
				}
				panel.customerId = _this.customerId;
			}
		},
		getWH : function(){
			var wd = this.appMainPanel.getWidth();
			var hg =  this.appMainPanel.getHeight();
			return [wd,hg];
		},
		getTabItem : function(tab,itemId,width,height){
			var tatItem = null;
			switch(itemId){
				case this.tabIdMgr.custinfoPnlId :{	/*个人详细资料*/
					tatItem = this.createCustInfoDetailPanel(tab,itemId,width,height);
					break;
				}case this.tabIdMgr.consortPnlId :{	/*配偶资料*/
					tatItem = this.createConsortDetailPanel(tab,itemId,width,height);
					break;
				}case this.tabIdMgr.guaCustomerPnlId :{	/*第三方担保单*/
					tatItem = this.createCustomerInfoPnlId(tab,itemId,width,height);
					break;
				}case this.tabIdMgr.addressPnlId :{	/*住宅资料*/
					tatItem = this.createaddressDetailPnlId(tab,itemId,width,height);
					break;
				}case this.tabIdMgr.estatePnlId :{	/*房产物业资料*/
					tatItem = this.createestateDetailPnlId(tab,itemId,width,height);
					break;
				}case this.tabIdMgr.workPnlId :{	/*职业资料*/
					tatItem = this.createworkDetailPnlId(tab,itemId,width,height);
					break;
				}case this.tabIdMgr.companyPnlId :{	/*旗下公司资料*/
					tatItem = this.createcompanyDetailPnlId(tab,itemId,width,height);
					break;
				}case this.tabIdMgr.contactorPnlId :{	/*主要联系人资料*/
					tatItem = this.createcontactorDetailPnlId(tab,itemId,width,height);
					break;
				}case this.tabIdMgr.creditInfoPnlId :{	/*个人信用资料*/
					tatItem = this.createcreditInfoDetailPnlId(tab,itemId,width,height);
					break;
				}case this.tabIdMgr.otherInfoPnlId :{	/*个人其他资料*/
					tatItem = this.creatotherInfoPnlId(tab,itemId,width,height);
					break;
				}		
			}
			return tatItem;
		},
		/**
		 * 创建附件FieldSet 对象
		 * @return {}
		 */
		createAttachMentFs : function(_this,dir,params){
			/* ----- 参数说明： isLoad 是否实例化后马上加载数据 
			 * dir : 附件上传后存放的目录， mortgage_path 定义在 resource.properties 资源文件中
			 * isSave : 附件上传后，是否保存到 ts_attachment 表中。 true : 保存
			 * params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔}
			 *    当 isLoad = false 时， params 可在 reload 方法中提供
			 *  ---- */
			
			var cfg = {title:'相关材料附件',isLoad:true,dir : dir,isSave:true,disabled : true};
			if(!params){
				var uuid = Cmw.getUuid();
				params = {formType:-1,formId : uuid,sysId:_this.params.sysId};
			}
			cfg.params = params;
			var attachMentFs = new  Ext.ux.AppAttachmentFs(cfg);
			return attachMentFs;
		},
		/**
		 * 个人客户资料详情
		 */
		createCustInfoDetailPanel: function(tab,itemId,width,height){
			var customerId = this.customerId;
			var _this = this;
			var htmlArrs_1 = [
					'<tr>' ,
						'<th col="cendDate">证件到期日期</th> <td col="cendDate" >&nbsp;</td>' ,
						'<th col="birthday">出生日期</th> <td col="birthday" >&nbsp;</td>' ,
						'<th col="maristal">婚姻状况</th> <td col="maristal" >&nbsp;</td>' ,
					'</tr>', 
					'<tr>' ,
						'<th col="accNature">户口性质</th> <td col="accNature" >&nbsp;</td>',
						'<th col="hometown">籍贯</th> <td col="hometown" >&nbsp;</td>' ,
						'<th col="age">年龄</th> <td col="age" >&nbsp;</td>' ,
					'</tr>', 
					'<tr>' ,
						'<th col="degree">学历</th> <td col="degree" >&nbsp;</td>' ,
						'<th col="job">职务</th> <td col="job" >&nbsp;</td>' ,
						'<th col="nation">民族</th> <td col="nation" >&nbsp;</td>' ,
					'</tr>',
					'<tr>' ,
						'<th col="phone">手机</th> <td col="phone" >&nbsp;</td>' ,
						'<th col="contactTel">联系电话</th> <td col="contactTel" >&nbsp;</td>' ,
						'<th col="email">电子邮件</th> <td col="email" >&nbsp;</td>',
					'</tr>', 
					'<tr>' ,
						'<th col="contactor">联系人</th> <td col="contactor" >&nbsp;</td>' ,
						'<th col="inArea">现居住地区</th> <td col="inArea" colspan="3">&nbsp;</td>' ,
					'</tr>',
					'<tr>' ,
						'<th col="zipcode">邮政编码</th> <td col="zipcode" >&nbsp;</td>' ,
						'<th col="fax">传真</th> <td col="fax" >&nbsp;</td>',
						'<th col="qqmsnNum">QQ或MSN号码</th> <td col="qqmsnNum" >&nbsp;</td>' ,
					'</tr>', 
					'<tr>' ,
						'<th col="workOrg">工作单位</th> <td col="workOrg" >&nbsp;</td>' ,
						'<th col="workAddress">单位地址</th> <td col="workAddress" >&nbsp;</td>' ,
						'<th col="accAddress">祖籍地址</th> <td col="accAddress" >&nbsp;</td>' ,
					'</tr>',
					FORMDIY_DETAIL_KEY,
					'<tr height="50">', 
						'<th col="remark" >备注</th> <td col="remark" colspan="5">&nbsp;</td>' ,
					'<tr>' 
					];
			var detailCfgs = [{
			    cmns: 'THREE',
			    model: 'single',
			    labelWidth : 110,
			    htmls: htmlArrs_1,
			    url: './crmCustomerInfo_get.action',
			    params: {
			        id: customerId
			    },
			    formDiyCfg : {
				    	sysId : _this.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_CUSTOMER_INFO,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id'/*对应表单中的ID Hidden 值*/
			    	},
					
			    callback: {
			        sfn: function(jsonData) {
			        	if(!jsonData["cendDate"]){
			        		jsonData["cendDate"]="";
			        	}
			        	if(!jsonData["birthday"]){
			        		jsonData["birthday"]="";
			        	}
			            jsonData["accNature"] = Render_dataSource.accNatureRender(jsonData["accNature"]);
			            jsonData["degree"] = Render_dataSource.gvlistRender('100006',jsonData["degree"]);
			            jsonData["maristal"] = Render_dataSource.gvlistRender('100003',jsonData["maristal"]);
			            jsonData["hometown"] = Render_dataSource.gvlistRender('100004',jsonData["hometown"]);
			            jsonData["nation"] = Render_dataSource.gvlistRender('100005',jsonData["nation"]);
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
			            	var sysId = _this.params.sysid;
				        	var formId = jsonData.id;
				        	if(!formId){
				        		formId = -1;
				        	}
				        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_11,sysId:sysId};
				        	attachMentFs.reload(params);
				        	attachMentFs.enable(); 
				        	detailPanel.doLayout();
			        }
			    }
			}];
			
			var cfg = {width:width,height : height,detailCfgs:detailCfgs,id : itemId,customerId : customerId};
			var detailPanel = new Ext.ux.panel.DetailPanel(cfg);
			 var dir = 'customerinfo_path';
			var attachMentFs = this.createAttachMentFs(this,dir);
			detailPanel.add(attachMentFs);
			return detailPanel;
		},
		
		/**
		 * 配偶资料详情
		 */
		createConsortDetailPanel:function(tab,itemId,width,height){
			var customerId = this.customerId;
			var _this = this;
			var htmlArrs_1 = [
					'<tr>' +
						'<th col="name" col="">姓名</th> <td col="name" >&nbsp;</td>' +
						'<th col="cardType">证件类型</th> <td col="cardType" >&nbsp;</td>' +
						'<th col="cardNum">证件号码</th> <td col="cardNum" >&nbsp;</td>' +
					'</tr>', 
					'<tr>' +
						'<th col="birthday">出生日期</th> <td col="birthday" >&nbsp;</td>' +
						'<th col="age">年龄</th> <td col="age" >&nbsp;</td>' +
						'<th col="hometown">籍贯</th> <td col="hometown" >&nbsp;</td>' +
					'</tr>', 
					'<tr>' +
						'<th col="nation">民族</th> <td col="nation" >&nbsp;</td>' +
						'<th col="degree">学历</th> <td col="degree" >&nbsp;</td>' +
						'<th col="job">职务</th> <td col="job" >&nbsp;</td>'+
					'</tr>', 
						'<th col="phone">手机</th> <td col="phone" >&nbsp;</td>' +
						'<th col="contactTel">电话</th> <td col="contactTel" >&nbsp;</td>' +
						'<th col="email">电子邮件</th> <td col="email" >&nbsp;</td>' +
					'</tr>', 
						'<th col="qqmsnNum">QQ或MSN号码</th> <td col="qqmsnNum" >&nbsp;</td>' +
						'<th col="conjobunit">现工作单位</th> <td col="conjobunit" >&nbsp;</td>' +
						'<th col="workAddress">单位地址</th> <td col="workAddress" >&nbsp;</td>' +
					'</tr>', 
					'<tr>' +
						'<th col="coninterest">个人爱好</th> <td col="coninterest" colspan="5">&nbsp;</td>' +
					'</tr>',
					FORMDIY_DETAIL_KEY,
					'<tr height="50">' +
						'<th col="remark" >备注</th> <td col="remark" colspan="5">&nbsp;</td>' ,
					'</tr>'
					];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 90,
			    /* title : '#TITLE#', */
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './crmConsort_get.action',
			    params: {
			        id: customerId
			    },
			    formDiyCfg : {
				    	sysId : _this.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_CONSORT,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id'/*对应表单中的ID Hidden 值*/
			    	},
			    callback: {
			        sfn: function(jsonData) {
			             jsonData["degree"] = Render_dataSource.gvlistRender('100006',jsonData["degree"]);
			           	jsonData["cardType"] = Render_dataSource.gvlistRender('100002',jsonData["cardType"]);
			             jsonData["hometown"] = Render_dataSource.gvlistRender('100004',jsonData["hometown"]);
			            jsonData["nation"] = Render_dataSource.gvlistRender('100005',jsonData["nation"]);
			            jsonData["age"] = Render_dataSource.ageRender(jsonData["age"]);
			            var sysId = _this.params.sysid;
			        	var formId = jsonData.id;
			        	if(!formId){
			        		formId = -1;
			        	}
			        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_12,sysId:sysId};
			        	attachMentFs.reload(params);
			        	attachMentFs.enable(); 
			        	detailPanel_1.doLayout();
			        }
			    }
			}];
			var attachLoad = function(params, cmd) {
			    /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			    //Cmw.print(params);
			    //Cmw.print(cmd);
			}
			var cfg = {width:width,height : height,border:false,detailCfgs:detailCfgs_1,id : itemId,customerId : customerId};
			var detailPanel_1 = new Ext.ux.panel.DetailPanel(cfg);
			 var dir = 'customerinfo_path';
			var attachMentFs = this.createAttachMentFs(this,dir);
			detailPanel_1.add(attachMentFs);
			return detailPanel_1;
		},
		/**
		 * 住宅资料详情
		 */
		createaddressDetailPnlId:function(tab,itemId,width,height){
			var self = this;
			var rememberselId = null;
			var customerId =self.customerId;
			var wd =135;
			var structure_1 = [{
				    header: '住宅id',
				    name: 'id',
				    hidden :true
				},
				{
				    header: '个人客户ID',
				    name: 'customerId',
				    width: wd,
				    hidden :true
				},
				{
				    header: '住宅地址',
				    name: 'address',
				    width: 150
				},
				{
				    header: '默认通讯地址',
				    name: 'isDefault',
				    width: 80,
				    renderer: Render_dataSource.FormdiyRender
				},
				{
				    header: '住宅类别',
				    name: 'housType',
				    width: 80,
				  	renderer: function(val) {
			      		return Render_dataSource.gvlistRender('100007',val);
		   		 	}
				},
				{
				    header: '居住方式',
				    name: 'resideType',
				    width: 80,
				    renderer: function(val) {
			      		return Render_dataSource.gvlistRender('100008',val);
		   		 	}
				},
				{
				    header: '主要联系人',
				    name: 'contactor',
				    width: wd
				},
				{
				    header: '与本人关系',
				    name: 'relation',
				    width: wd
				},
				{
				    header: '主要联系人电话 ',
				    name: 'tel',
				    width: wd
				},
				{
				    header: '主要联系人手机',
				    name: 'phone',
				    width: wd
				},
				{
				    header: '邮编',
				    name: 'zipcode',
				    width: wd
				},
				{
				    header: '住宅居住人数',
				    name: 'manCount',
				    width: wd,
				    renderder : Render_dataSource.manCountRender
			}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				id : itemId,
			    structure: structure_1,
			    url: './crmAddress_list.action',
			    height : 135,
			     width:width,
			    needPage: false,
			    border:false,
			    isLoad: false,
			    keyField: 'id'
			});
			appgrid_1.addListener("afterrender",function(){
				var panel = Ext.getCmp(itemId);
				self.loadItemPanel(panel);
			});
			appgrid_1.addListener("rowclick",function(appgrid_1,rowIndex,event){
				var selId = appgrid_1.getSelId();
				if(rememberselId!=selId && selId){
					detailPanel_1.reload({id:selId});
					attachMentFs.enable(); 
					rememberselId=selId;
				}else{
					return;
				}
			});
			
			var htmlArrs_1 = [
					'<tr>' +
						'<th col="address">住宅地址</th> <td col="address" >&nbsp;</td>' +
						'<th col="isDefault">默认通讯地址</th> <td col="isDefault" >&nbsp;</td>' +
						'<th col="housType">住宅类别</th> <td col="housType" >&nbsp;</td>' +
					'</tr>',
					'<tr>' +
						'<th col="resideType">居住方式</th> <td col="resideType" >&nbsp;</td>' +
						'<th col="contactor">主要联系人</th> <td col="contactor" >&nbsp;</td>' +
						'<th col="relation">与本人关系</th> <td col="relation" >&nbsp;</td>' +
					'</tr>', 
					'<tr>' +
						'<th col="tel">主要联系人电话 </th> <td col="tel" >&nbsp;</td>' +
						'<th col="phone">主要联系人手机</th> <td col="phone" >&nbsp;</td>' +
						'<th col="zipcode">邮编</th> <td col="zipcode" >&nbsp;</td></tr>', 
					'</tr>' +
					'<tr>' +
						'<th col="manCount">住宅居住人数</th> <td col="manCount" colspan=5>&nbsp;</td>'+
					'</tr>' ,
					FORMDIY_DETAIL_KEY,
					'<tr height="50">' +
					'<th col="remark">备注</th> <td col="remark"  colspan=5 >&nbsp;</td>'+
					'</tr>' 
					];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 125,
			    /* title : '#TITLE#', */
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './crmAddress_get.action',
			    params: {
			        id: -1
			    },
			    formDiyCfg : {
				    	sysId : self.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_ADDRESS,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id'/*对应表单中的ID Hidden 值*/
			    	},
			    callback: {
			        sfn: function(jsonData) {
			        	jsonData["isDefault"]=Render_dataSource.isDefaultRenders(jsonData["isDefault"]);
			        	jsonData["housType"]=Render_dataSource.gvlistRender('100007',jsonData["housType"]);
			        	jsonData["resideType"]=Render_dataSource.gvlistRender('100008',jsonData["resideType"]);
			        	jsonData["manCount"]= Render_dataSource.manCountRender(jsonData["manCount"]);
			        	var sysId = self.params.sysid;
			        	var formId = jsonData.id;
			        	if(!formId){
			        		formId = -1;
			        	}
			        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_13,sysId:sysId};
			        	attachMentFs.reload(params);
			        	appgridpanel_1.doLayout();
			        }
			    }
			}];
			var attachLoad = function(params, cmd) {
			    /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			    //Cmw.print(params);
			    //Cmw.print(cmd);
			}
			var _this = this;
			var detailPanel_1 = new Ext.ux.panel.DetailPanel({
				id : _this.detailIdMgr.addressDetailId,
			    width:width,border:false,
			    height : height,
			    detailCfgs: detailCfgs_1,
			    attachLoad: function(params, cmd) {
			        /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			        //Cmw.print(params);
			        //Cmw.print(cmd);
			    }
			});
			 var dir = 'customerinfo_path';
			var attachMentFs = this.createAttachMentFs(this,dir);
			this.attachMentArray.push(attachMentFs);
			detailPanel_1.add(attachMentFs);
			var appgridpanel_1 = new Ext.Panel({width:width,height : height,
			    items: [appgrid_1,detailPanel_1]
			})
			return appgridpanel_1;
		},
		/**
		 * 房产物业信息详情
		 */
		createestateDetailPnlId :function(tab,itemId,width,height){
			var self = this;
			var wd =135;
			var rememberselId =null;
			var structure_1 = [{
			    header: '个人客户ID',
			    name: 'customerId',
			    hidden :true
			},
			{
			    header: '房产地址',
			    name: 'address',
			    width : 150
			},
			{
			    header: '购买方式',
			    name: 'buyType',
			    "width": 80,
			    renderer: Render_dataSource.buyTypeRender
			},
			{
			    header: '购买日期',
			    name: 'buyDate',
			    "width": wd
			},
			{
			    header: '建筑年份',
			    "width": wd,
			    name: 'houseYear',
			    renderer :function(val){
			    	return val+'年';
			    }
			},
			{
			    header: '建筑面积(平方米）',
			    "width": wd,
			    name: 'area'
			},
			{
			    header: '购买价格(万元)',
			    "width": wd,
			    name: 'price',
		    	renderer: Render_dataSource.moneyRender
			},
			{
			    header: '按揭银行',
			    "width": wd,
			    name: 'mortBank'
			},
			{
			    header: '贷款年限',
			    "width": wd,
			    name: 'loanYear'
			},
			{
			    header: '每月供款',
			    "width": wd,
			    name: 'contributions',
			    renderer: Render_dataSource.moneyRender
			},
			{
			    header: '贷款总额',
			    "width": wd,
			    name: 'loanAmount',
			    renderer: Render_dataSource.moneyRender
			},
			{
			    header: '尚欠余额',
			    "width": wd,
			    name: 'zAmount',
			    renderer: Render_dataSource.moneyRender
			},
			{
			    header: '已付分期',
			    "width": wd,
			    name: 'installments'
			},
			{
			    header: '总分期期数',
			    "width": wd,
			    name: 'totalInstall'
			},
			{
			    header: '居住时长',
			    "width": wd,
			    name: 'runtime'
			},
			{
			    header: '受供养人数',
			    "width": wd,
			    name: 'supCount'
			},
			{
			    header: '与谁居住',
			    "width": wd,
			    name: 'whoLived'
			}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				id : itemId,
			    structure: structure_1,
			    url: './crmEstate_list.action',
			    height : 135,
			    needPage: false,
			    border:false,
			    isLoad: false,
			    keyField: 'id'
			});
			appgrid_1.addListener("afterrender",function(){
				var panel = Ext.getCmp(itemId);
				self.loadItemPanel(panel);
			});
			appgrid_1.addListener("rowclick",function(appgrid_1,rowIndex,event){
				var selId = appgrid_1.getSelId();
				if(rememberselId!=selId && selId){
					detailPanel_1.reload({id:selId});
					attachMentFs.enable(); 
					rememberselId=selId;
				}else{
					return;
				}
			});
			var htmlArrs_1 = [
							'<tr>' ,
								'<th col="address">房产地址</th> <td col="address" >&nbsp;</td>'  ,
								'<th col="buyType">购买方式</th> <td col="buyType" >&nbsp;</td>'  ,
								'<th col="buyDate">购买日期</th> <td col="buyDate" >&nbsp;</td>'  ,
							'</tr>', 
							'<tr>'  ,
								'<th col="houseYear">建筑年份</th> <td col="houseYear" >&nbsp;</td>'  ,
								'<th col="area">建筑面积(平方米）</th> <td col="area" >&nbsp;</td>'  ,
								'<th col="price">购买价格</th> <td col="price" >&nbsp;</td>'  ,
							'</tr>', 
							'<tr>'  ,
								'<th col="mortBank">按揭银行</th> <td col="mortBank" >&nbsp;</td>' ,
								'<th col="loanYear">贷款年限</th> <td col="loanYear" >&nbsp;</td>'  ,
								'<th col="contributions">每月供款</th> <td col="contributions" >&nbsp;</td>' ,
							'</tr>', 
							'<tr>'  ,
								'<th col="loanAmount">贷款总额</th> <td col="loanAmount" >&nbsp;</td>'  ,
								'<th col="zAmount">尚欠余额</th> <td col="zAmount" >&nbsp;</td>'  ,
								'<th col="installments">已付分期</th> <td col="installments" >&nbsp;</td>'  ,
							'</tr>', 
							'<tr>'  ,
								'<th col="totalInstall">总分期期数</th> <td col="totalInstall" >&nbsp;</td>'  ,
								'<th col="runtime">居住时长</th> <td col="runtime" >&nbsp;</td>'  ,
								'<th col="supCount">受供养人数</th> <td col="supCount" >&nbsp;</td>'  ,
							'</tr>',
							'<tr>'  ,
							'<th col="whoLived">与谁居住</th> <td col="whoLived" colspan=5>&nbsp;</td>'  ,
							'</tr>',
							FORMDIY_DETAIL_KEY,
							'<tr height="50">'  ,
							'<th col="remark">备注</th> <td col="remark"  colspan=5 >&nbsp;</td>' ,
							'</tr>'
							];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			   model: 'single',
			     labelWidth: 125,
			    /* title : '#TITLE#', */
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './crmEstate_get.action',
			    params: {
			        id: -1
			    },
			    formDiyCfg : {
				    	sysId : self.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_ESTATE,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id'/*对应表单中的ID Hidden 值*/
			    	},
			    callback: {
			        sfn: function(jsonData) {
			            /* 可在对页面进行赋值前，对数据进行转换处理 to do .. */
			        	var buyType  = "";
			        	if(jsonData["buyType"]){
			        		buyType = jsonData["buyType"].toString();
			        		jsonData["buyType"] = Render_dataSource.buyTypeRender(buyType);
			        	}
			        	
			        	jsonData["houseYear"] = Render_dataSource.houseYearRender(jsonData["houseYear"]);
			        	jsonData["price"] = Render_dataSource.wmoneyRender(jsonData["price"]);
			        	jsonData["contributions"] = Render_dataSource.moneyRender(jsonData["contributions"]);
			        	jsonData["loanYear"] = Render_dataSource.houseYearRender(jsonData["loanYear"]);
			        	jsonData["loanAmount"] = Render_dataSource.wmoneyRender(jsonData["loanAmount"]);
			        	jsonData["zAmount"] = Render_dataSource.wmoneyRender(jsonData["zAmount"]);
			        	jsonData["runtime"] = Render_dataSource.houseYearRender(jsonData["runtime"]);
			        	jsonData["supCount"] = Render_dataSource.manCountRender(jsonData["supCount"]);
			        	var sysId = self.params.sysid;
			        	var formId = jsonData.id;
			        	if(!formId){
			        		formId = -1;
			        	}
			        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_14,sysId:sysId};
			        	attachMentFs.reload(params);
			        	appgridpanel_1.doLayout();
			        }
			    }
			}];
			var attachLoad = function(params, cmd) {
			    /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			    //Cmw.print(params);
			    //Cmw.print(cmd);
			}
			var _this = this;
			var detailPanel_1 = new Ext.ux.panel.DetailPanel({
				isLoad : false,
				id : _this.detailIdMgr.estateDetailId,
			    autoWidth:true,border:false,
			    autoHeight : true,
			    detailCfgs: detailCfgs_1,
			    attachLoad: function(params, cmd) {
			        /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			        //Cmw.print(params);
			        //Cmw.print(cmd);
			    }
			});
			 var dir = 'customerinfo_path';
			var attachMentFs = this.createAttachMentFs(this,dir);
			this.attachMentArray.push(attachMentFs);
			detailPanel_1.add(attachMentFs);
			var appgridpanel_1 = new Ext.Panel({
			    items: [appgrid_1,detailPanel_1]
			})
			return appgridpanel_1;
		},
		/**
		 * 职业详情
		 */
		createworkDetailPnlId:function(tab,itemId,width,height){
			var self = this;
			var rememberselId = null;
			var structure_1 = [{
			    header: '个人客户ID',
			    name: 'customerId',
			    width: 125,
			    hidden :true
			},
			{
			    header: '单位名称',
			    name: 'orgName',
			    width: 125
			},
			{
			    header: '所在部门',
			    name: 'dept',
			    width: 125
			},
			{
			    header: '职务',
			    name: 'job',
			    width: 80
			},
			{
			    header: '服务年数',
			    name: 'syears',
			    width: 60
			},
			{
			    header: '每月收入',
			    name: 'income',
			    width: 100,
			    renderder : Render_dataSource.moneyRender
			},
			{
			    header: '每月支薪日(日)',
			    name: 'payDay',
			    width: 125,
		    	renderer: Render_dataSource.payDayRender
			},
			{
			    header: '支付方式',
			    name: 'payment',
			    width: 100
			},
			{
			    header: '行业类别',
			    name: 'industry',
			    width: 125
			},
			{
			    header: '单位地址',
			    name: 'address',
			    width: 150
			},
			{
			    header: '邮编',
			    name: 'zipcode',
			    width: 100
			},
			{
			    header: '单位性质',
			    name: 'nature',
			    width: 125,
			    renderer :	function(val){ 
			    	return Render_dataSource.gvlistRender('100009',val)
			    }
			},
			{
			    header: '单位电话',
			    name: 'tel',
			    width: 135
			},
			{
			    header: '单位传真',
			    name: 'fax',
			    width: 125
			},
			{
			    header: '网址',
			    name: 'url',
			    width: 150
			}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				id : itemId,
			    structure: structure_1,
			    url: './crmWork_list.action',
			    needPage: false,
			    border:false,
			    height : 135,
			    isLoad: false,
			    keyField: 'id'
			});
			appgrid_1.addListener("afterrender",function(){
				var panel = Ext.getCmp(itemId);
				self.loadItemPanel(panel);
			});
			appgrid_1.addListener("rowclick",function(appgrid_1,rowIndex,event){
				var selId = appgrid_1.getSelId();
				if(rememberselId!=selId  && selId){
					detailPanel_1.reload({id:selId});
					attachMentFs.enable(); 
					rememberselId=selId;
				}else{
					return;
				}
			});
			var htmlArrs_1 = [
							'<tr>' +
								'<th col="orgName">单位名称</th> <td col="orgName" >&nbsp;</td>' +
								'<th col="dept">所在部门</th> <td col="dept" >&nbsp;</td>' +
								'<th col="job">职务</th> <td col="job" >&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								'<th col="syears">服务年数</th> <td col="syears" >&nbsp;</td>' +
								'<th col="income">每月收入</th> <td col="income" >&nbsp;</td>' +
								'<th col="payDay">每月支薪日</th> <td col="payDay" >&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								'<th col="payment">支付方式</th> <td col="payment" >&nbsp;</td>' +
								'<th col="industry">行业类别</th> <td col="industry" >&nbsp;</td>' +
								'<th col="address">单位地址</th> <td col="address" >&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								'<th col="zipcode">邮编</th> <td col="zipcode" >&nbsp;</td>' +
								'<th col="nature">单位性质</th> <td col="nature" >&nbsp;</td>' +
								'<th col="tel">单位电话</th> <td col="tel" >&nbsp;</td></tr>', 
							'<tr>' +
								'<th col="fax">单位传真</th> <td col="fax" >&nbsp;</td>' +
								'<th col="url">网址</th> <td col="url"  colspan=3 >&nbsp;</td>' +
							'</tr>',
							FORMDIY_DETAIL_KEY,
							'<tr height="50">'+
								'<th col="remark">备注</th> <td col="remark"  colspan=5 >&nbsp;</td>'+
							'</tr>'
							];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 90,
			    /* title : '#TITLE#', */
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './crmWork_get.action',
			    params: {
			        id: -1
			    },
			    formDiyCfg : {
				    	sysId : self.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_WORK,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id'/*对应表单中的ID Hidden 值*/
			    	},
			    callback: {
			        sfn: function(jsonData) {
			            jsonData["syears"] = Render_dataSource.houseYearRender(jsonData["syears"]);
			            jsonData["income"] = Render_dataSource.moneyRender(jsonData["income"]);
			            jsonData["payDay"] = Render_dataSource.payDayRender(jsonData["payDay"]);
			            jsonData["nature"] = Render_dataSource.gvlistRender('100009',jsonData["nature"]);
			            var sysId = self.params.sysid;
			        	var formId = jsonData.id;
			        	if(!formId){
			        		formId = -1;
			        	}
			        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_15,sysId:sysId};
			        	attachMentFs.reload(params);
			        	appgridpanel_1.doLayout();
			        }
			    }
			}];
			var attachLoad = function(params, cmd) {
			    /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			    //Cmw.print(params);
			    //Cmw.print(cmd);
			}
			var _this = this;
			var detailPanel_1 = new Ext.ux.panel.DetailPanel({
				id : _this.detailIdMgr.workDetailId,
			    autoHeight : true,border:false,
			    autoWidth:true,
			    detailCfgs: detailCfgs_1,
			    attachLoad: function(params, cmd) {
			        /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			        //Cmw.print(params);
			        //Cmw.print(cmd);
			    }
			});
			 var dir = 'customerinfo_path';
			var attachMentFs = this.createAttachMentFs(this,dir);
			this.attachMentArray.push(attachMentFs);
			detailPanel_1.add(attachMentFs);
			var appgridpanel_1 = new Ext.Panel({
			    items: [appgrid_1,detailPanel_1]
			})
			return appgridpanel_1;
		},
		/**
		 * 个人旗下/企业关联公司信息详情
		 */
		createcompanyDetailPnlId:function(tab,itemId,width,height){
			var self = this;
			var rememberselId = null
			var structure_1 = [{
			    header: '客户类型',
			    name: 'custType',
			    width : 80,
			     renderer: Render_dataSource.custTypeRender
			},
			{
			    header: '个人/企业客户ID',
			    name: 'customerId',
			    hidden :true
			},
			{
			    header: '公司名称',
			    name: 'cconame'
			},
			{
			    header: '公司性质',
			    name: 'ccokind',
			     renderer: function(val) {
				         return Render_dataSource.gvlistRender('100009',val);
			     }
			},
			{
			    header: '法人',
			    name: 'legal'
			},
			{
			    header: '工商登记号',
			    name: 'offNum'
			},
			{
			    header: '组织机构代码证',
			    width : 120,
			    name: 'orgNum'
			},
			{
			    header: '国税编号',
			    name: 'nationNum',
			    width : 120
			},
			{
			    header: '地税编号',
			    name: 'landNum',
			    width : 120
			},
			{
			    header: '成立时间',
			    name: 'regDate'
			},
			{
			    header: '注册资本',
			    name: 'regcaptial'
			},
			{
			    header: '注册币种',
			    name: 'currency',
			     renderer: function(val) {
				        return Render_dataSource.gvlistRender('100014',val);
			     }
			},
			{
			    header: '联系人',
			    name: 'linkman'
			},
			{
			    header: '联系电话',
			    name: 'ccoTel'
			},
			{
			    header: '经营场所',
			    name: 'premises'
			},
			{
			    header: '月供/月租金',
			    name: 'monthly',
			    renderder : Render_dataSource.moneyRender
			},
			{
			    header: '员工人数(人)',
			    name: 'empCount',
			    width : 80
			},
			{
			    header: '全年盈利',
			    name: 'profit'
			},
			{
			    header: '经营地址',
			    name: 'ccoaaddress'
			}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				id : itemId,
			    structure: structure_1,
			    url: './crmCustCompany_list.action',
			    needPage: false,
			    height :135,
			    border:false,
			    isLoad: false,
			    keyField: 'id',
			    autoScroll:true
			});
			
			appgrid_1.addListener("afterrender",function(){
				var panel = Ext.getCmp(itemId);
				self.loadItemPanel(panel);
			});
			appgrid_1.addListener("rowclick",function(appgrid_1,rowIndex,event){
				var selId = appgrid_1.getSelId();
				if(rememberselId!=selId && selId){
					detailPanel_1.reload({id:selId});
					attachMentFs.enable(); 
					rememberselId=selId;
				}else{
					return;
				}
			});
			var htmlArrs_1 = [
							'<tr>' +
								'<th col="cconame">公司名称</th> <td col="cconame" >&nbsp;</td>' +
								'<th col="ccokind">公司性质</th> <td col="ccokind" >&nbsp;</td>' +
								'<th col="legal">法人</th> <td col="legal" >&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								'<th col="offNum">工商登记号</th> <td col="offNum" >&nbsp;</td>' +
								'<th col="orgNum">组织机构代码证</th> <td col="orgNum" >&nbsp;</td>' +
								'<th col="nationNum">国税编号</th> <td col="nationNum" >&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								'<th col="landNum">地税编号</th> <td col="landNum" >&nbsp;</td>' +
								'<th col="regDate">成立时间</th> <td col="regDate" >&nbsp;</td>' +
								'<th col="regcaptial">注册资本</th> <td col="regcaptial" >&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								'<th col="currency">注册币种</th> <td col="currency" >&nbsp;</td>' +
								'<th col="linkman">联系人</th> <td col="linkman" >&nbsp;</td>' +
								'<th col="ccoTel">联系电话</th> <td col="ccoTel" >&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								
								'<th col="premises">经营场所</th> <td col="premises" >&nbsp;</td>' +
								'<th col="monthly">月供/月租金</th> <td col="monthly" >&nbsp;</td>' +
								'<th col="empCount">员工人数</th> <td col="empCount" >&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								'<th col="profit">全年盈利</th> <td col="profit" >&nbsp;</td>' +
								'<th col="ccoaaddress">经营地址</th> <td col="ccoaaddress">&nbsp;</td>' +
								'<th col="custType">客户类型</th> <td col="custType" >&nbsp;</td>' +
							'</tr>' ,
							FORMDIY_DETAIL_KEY,
							'<tr height="50">' +
								'<th col="remark">备注</th> <td col="remark"  colspan=5 >&nbsp;</td>'+
							'</tr>'];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			     labelWidth: 125,
			    /* title : '#TITLE#', */
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './crmCustCompany_get.action',
			    params: {
			        id: -1
			    },
			    formDiyCfg : {
				    	sysId : self.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_CUSTCOMPANY,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id'/*对应表单中的ID Hidden 值*/
			    	},
			    callback: {
			        sfn: function(jsonData) {
			            /* 可在对页面进行赋值前，对数据进行转换处理 to do .. */
			        	jsonData["ccokind"] = Render_dataSource.gvlistRender('100009',jsonData["ccokind"]);
			        	jsonData["currency"] = Render_dataSource.gvlistRender('100014',jsonData["currency"]);
			        	jsonData["custType"] = Render_dataSource.custTypeRender(jsonData["custType"]);
			        	jsonData["profit"] = Render_dataSource.moneyRender(jsonData["profit"]);
			        	jsonData["empCount"] = Render_dataSource.manCountRender(jsonData["empCount"]);
			        	var sysId = self.params.sysid;
			        	var formId = jsonData.id;
			        	if(!formId){
			        		formId = -1;
			        	}
			        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_16,sysId:sysId};
			        	attachMentFs.reload(params);
			        	appgridpanel_1.doLayout();
			        }
			    }
			}];
			var attachLoad = function(params, cmd) {
			    /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			    //Cmw.print(params);
			    //Cmw.print(cmd);
			}
			var _this = this;
			var detailPanel_1 = new Ext.ux.panel.DetailPanel({
				id : _this.detailIdMgr.companyDetailId,
			    autoHeight : true,border:false,
			    autoWidth:true,
			    detailCfgs: detailCfgs_1,
			    attachLoad: function(params, cmd) {
			        /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			        //Cmw.print(params);
			        //Cmw.print(cmd);
			    }
			});
			 var dir = 'customerinfo_path';
			var attachMentFs = this.createAttachMentFs(this,dir);
			this.attachMentArray.push(attachMentFs);
			detailPanel_1.add(attachMentFs);
			var appgridpanel_1 = new Ext.Panel({
			    items: [appgrid_1,detailPanel_1]
			})
			return appgridpanel_1;
		},
		/**
		 * 联系人资料详情
		 */
		createcontactorDetailPnlId:function(tab,itemId,width,height){
			var self =this;
			var rememberselId = null;
			var structure_1 = [{
			    header: '个人客户ID',
			    hidden :true,
			    name: 'customerId'
			},
			{
			    header: '姓名',
			    name: 'name'
			},
			{
			    header: '关系',
			    name: 'relation'
			},
			{
			    header: '出生日期',
			    name: 'birthday'
			},
			{
			    header: '年龄(岁)',
			    name: 'age'
			},
			{
			    header: '手机',
			    name: 'phone'
			},
			{
			    header: '住宅电话',
			    name: 'tel'
			},
			{
			    header: '住宅住址',
			    name: 'address'
			},
			{
			    header: '单位名称 ',
			    name: 'orgName'
			},
			{
			    header: '职务',
			    name: 'job'
			},
			{
			    header: '单位电话',
			    name: 'orgTel'
			}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				id : itemId,
			    structure: structure_1,
			    url: './crmContactor_list.action',
			    needPage: false,
			    height :135,
			    isLoad: false,
			    border:false,
			    keyField: 'id'
			});
			appgrid_1.addListener("afterrender",function(){
				var panel = Ext.getCmp(itemId);
				self.loadItemPanel(panel);
			});
			appgrid_1.addListener("rowclick",function(appgrid_1,rowIndex,event){
				var selId = appgrid_1.getSelId();
				if(rememberselId!=selId && selId){
					detailPanel_1.reload({id:selId});
					attachMentFs.enable(); 
					rememberselId=selId;
				}else{
					return;
				}
			});
			var htmlArrs_1 = [
							'<tr>' +
								'<th col="name">姓名</th> <td col="name" >&nbsp;</td>' +
								'<th col="relation">关系 </th> <td col="relation" >&nbsp;</td>' +
								'<th col="birthday">出生日期</th> <td col="birthday" >&nbsp;</td>' +
							'</tr>',
							'<tr>' +
								'<th col="phone">手机</th> <td col="phone" >&nbsp;</td>'+
								'<th col="tel">住宅电话</th> <td col="tel" >&nbsp;</td>' +
								'<th col="age">年龄</th> <td col="age" >&nbsp;</td>' +
							'</tr>',
							'<tr>' +
								'<th col="address">住址</th> <td col="address" >&nbsp;</td>' +
								'<th col="orgName">单位名称</th> <td col="orgName" >&nbsp;</td>' +
								'<th col="job">职务</th> <td col="job" >&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								'<th col="orgTel">单位电话</th> <td col="orgTel" colspan=5>&nbsp;</td>' +
							'</tr>',
							FORMDIY_DETAIL_KEY,
							'<tr height="50">' +
								'<th col="remark">备注</th> <td col="remark"  colspan=5 >&nbsp;</td>'+
							'</tr>'
							];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 90,
			    /* title : '#TITLE#', */
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './crmContactor_get.action',
			    params: {
			        id: -1
			    },
			    formDiyCfg : {
				    	sysId : self.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_CONTACTOR,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id'/*对应表单中的ID Hidden 值*/
			    	},
			    callback: {
			        sfn: function(jsonData) {
			            /* 可在对页面进行赋值前，对数据进行转换处理 to do .. */
			            jsonData["age"] = Render_dataSource.ageRender(jsonData["age"]);
			            var sysId = self.params.sysid;
			        	var formId = jsonData.id;
			        	if(!formId){
			        		formId = -1;
			        	}
			        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_17,sysId:sysId};
			        	attachMentFs.reload(params);
			        	appgridpanel_1.doLayout();
			        }
			    }
			}];
			
			var attachLoad = function(params, cmd) {
				
			    /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			    //Cmw.print(params);
			    //Cmw.print(cmd);
			}
			var _this  = this;
			var detailPanel_1 = new Ext.ux.panel.DetailPanel({
				id : _this.detailIdMgr.contactorDetailId,
			    autoHeight : true,border:false,
			    autoWidth:true,
			    detailCfgs: detailCfgs_1,
			    attachLoad: function(params, cmd) {
			        /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			        //Cmw.print(params);
			        //Cmw.print(cmd);
			    }
			});
			var dir = 'customerinfo_path';
			var attachMentFs = this.createAttachMentFs(this,dir);
			this.attachMentArray.push(attachMentFs);
			detailPanel_1.add(attachMentFs);
			var appgridpanel_1 = new Ext.Panel({
			    items: [appgrid_1,detailPanel_1]
			})
			return appgridpanel_1;
		},
		createcreditInfoDetailPnlId:function(tab,itemId,width,height){
			var self = this;
			var rememberselId = null;
			var structure_1 = [{
			    header: '个人客户ID',
			    name: 'customerId',
			    hidden :true
			},
			{
			    header: '信用类型',
			    name: 'creitType',
			    renderer :function(val){
				         return Render_dataSource.gvlistRender('100010',val);
			    }
			},
			{
			    header: '数量',
			    name: 'count'
			},
			{
			    header: '总贷款额度',
			    name: 'totalAmount',
			    renderer :Render_dataSource.moneyRender
			},
			{
			    header: '每月供款',
			    name: 'monthAmount',
			    renderer :Render_dataSource.moneyRender
			},
			{
			    header: '总贷款余额',
			    name: 'balance',
			    renderer :Render_dataSource.moneyRender
			},{
			    header: '信用描述',
			    width : 150,
			    name: 'remark'
			}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				id : itemId,
			    structure: structure_1,
			    url: './crmCreditInfo_list.action',
			    needPage: false,
			    border:false,
			    height :135,
			    isLoad: false,
			    autoScroll: true,
			    keyField: 'id'
			});
			appgrid_1.addListener("afterrender",function(){
				var panel = Ext.getCmp(itemId);
				self.loadItemPanel(panel);
			});
			appgrid_1.addListener("rowclick",function(appgrid_1,rowIndex,event){
				var selId = appgrid_1.getSelId();
				if(rememberselId!=selId && selId){
					detailPanel_1.reload({id:selId});
					attachMentFs.enable(); 
					rememberselId=selId;
				}else{
					return;
				}
			});
			
			var htmlArrs_1 = [
						'<tr>' +
							'<th col="creitType">信用类型</th> <td col="creitType" >&nbsp;</td>' +
							'<th col="count">数量</th> <td col="count" >&nbsp;</td>' +
							'<th col="totalAmount">总贷款额度</th> <td col="totalAmount" >&nbsp;</td>' +
						'</tr>', 
						'<tr>' +
							'<th col="monthAmount">每月供款</th> <td col="monthAmount" >&nbsp;</td>' +
							'<th col="balance">总贷款余额</th> <td col="balance">&nbsp;</td>' +
							'<th col="remark">信用描述</th> <td col="remark">&nbsp;</td>'+
						'</tr>',
							FORMDIY_DETAIL_KEY
						];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 90,
			    /* title : '#TITLE#', */
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './crmCreditInfo_get.action',
			    params: {
			        id: -1
			    },
			    formDiyCfg : {
				    	sysId : self.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_CREDITINFO,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id'/*对应表单中的ID Hidden 值*/
			    	},
			    callback: {
			        sfn: function(jsonData) {
			            /* 可在对页面进行赋值前，对数据进行转换处理 to do .. */
			        	jsonData["creitType"] = Render_dataSource.gvlistRender('100010',jsonData["creitType"]);
			        	jsonData["totalAmount"] = Render_dataSource.moneyRender(jsonData["totalAmount"]);
			        	jsonData["balance"] = Render_dataSource.moneyRender(jsonData["balance"]);
			        	jsonData["monthAmount"] = Render_dataSource.moneyRender(jsonData["monthAmount"]);
			        	var sysId = self.params.sysid;
			        	var formId = jsonData.id;
			        	if(!formId){
			        		formId = -1;
			        	}
			        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_18,sysId:sysId};
			        	attachMentFs.reload(params);
			        	appgridpanel_1.doLayout();
			        }
			    }
			}];
			var attachLoad = function(params, cmd) {
			    /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			    //Cmw.print(params);
			    //Cmw.print(cmd);
			}
			var _this = this;
			var detailPanel_1 = new Ext.ux.panel.DetailPanel({
				id : _this.detailIdMgr.creditInfoDetailId,
			    autoHeight : true,border:false,
			    autoWidth:true,
			    detailCfgs: detailCfgs_1,
			    attachLoad: function(params, cmd) {
			        /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			        //Cmw.print(params);
			        //Cmw.print(cmd);
			    }
			});
			var dir = 'customerinfo_path';
			var attachMentFs = this.createAttachMentFs(this,dir);
			this.attachMentArray.push(attachMentFs);
			detailPanel_1.add(attachMentFs);
			var appgridpanel_1 = new Ext.Panel({
			    items: [appgrid_1,detailPanel_1]
			})
			return appgridpanel_1;
		},
		
		
		/**
		 * 第三方担保人信息
		 */
		createCustomerInfoPnlId:function(tab,itemId,width,height){
			var self = this;
			var rememberselId = null;
			var structure_1 = [{
						header : '客户基础信息ID',
						name : 'baseId',
						hidden : true
					},{
						header : '姓名',
						name : 'name'
					},{
						header : '性别',
						name : 'sex'
						,renderer :Render_dataSource.sexRender,width : 40
					},{
						header : '出生日期',
						name : 'birthday'
					},{
						header : '联系人',
						name : 'contactor'
					}, {
						header : '联系电话',
						name : 'contactTel'
					}, {
						header : '手机',
						name : 'phone'
					}, {
						header : 'QQ或MSN号码',
						name : 'qqmsnNum'
					}, {
						header : '证件类型',
						name : 'cardType',
						renderer : function(val){
							return Render_dataSource.gvlistRender('100002',val);
						}
					}, {
						header : '证件号码',
						name : 'cardNum'
					},{
						header : '年龄',
						name : 'age'
					}, {
						header : '婚姻状况',
						name : 'maristal',
						renderer : function(val){
							return Render_dataSource.gvlistRender('100003',val);
						}
					}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				id : itemId,
			    structure: structure_1,
			    url: './crmGuaCustomer_list.action',
			    needPage: false,
			    border:false,
			    height :135,
			    isLoad: false,
			    autoScroll: true,
			    keyField: 'id'
			});
			appgrid_1.addListener("afterrender",function(){
				var panel = Ext.getCmp(itemId);
				self.loadItemPanel(panel);
			});
			appgrid_1.addListener("rowclick",function(appgrid_1,rowIndex,event){
				var selId = appgrid_1.getSelId();
				if(rememberselId!=selId && selId){
					detailPanel_1.reload({id:selId});
					attachMentFs.enable(); 
					rememberselId=selId;
				}else{
					return;
				}
			});
			
			var htmlArrs_1 = [
						'<tr>' ,
							'<th col="name">客户姓名</th> <td col="name" >&nbsp;</td>' ,
							'<th col="sex">性别</th> <td col="sex" >&nbsp;</td>' ,
							'<th col="accNature">户口性质</th> <td col="accNature" >&nbsp;</td>',
						'</tr>', 
						'<tr>',
							'<th col="isgua">是否无限连带担保责任</th> <td col="isgua" >&nbsp;</td>' ,
							'<th col="relation">与被担保人关系</th> <td col="relation" colspan = 3>&nbsp;</td>',
						'</tr>',
						'<tr>' ,
							'<th col="cardType">证件类型</th> <td col="cardType" >&nbsp;</td>' ,
							'<th col="cardNum">证件号码</th> <td col="cardType">&nbsp;</td>',
							'<th col="cendDate">证件到期日期</th> <td col="cendDate" >&nbsp;</td>' ,
						'</tr>',
						'<tr>' ,
							'<th col="birthday">出生日期</th> <td col="birthday" >&nbsp;</td>' ,
							'<th col="age">年龄</th> <td col="age" >&nbsp;</td>',
							'<th col="maristal">婚姻状况</th> <td col="maristal" >&nbsp;</td>' ,
						'</tr>',
						'<tr>' ,
							'<th col="hometown">籍贯</th> <td col="hometown" >&nbsp;</td>' ,
							'<th col="nation">民族</th> <td col="nation" >&nbsp;</td>' ,
							'<th col="degree">学历</th> <td col="degree" >&nbsp;</td>' ,
						'</tr>',
						'<tr>' ,
							'<th col="phone">手机</th> <td col="phone" >&nbsp;</td>' ,
							'<th col="contactTel">联系电话</th> <td col="contactTel" >&nbsp;</td>' ,
							'<th col="email">电子邮件</th> <td col="email" >&nbsp;</td>',
						'</tr>', 
						'<tr>' ,
							'<th col="contactor">联系人</th> <td col="contactor" >&nbsp;</td>' ,
							'<th col="inArea">现居住地区</th> <td col="inArea" colspan="3">&nbsp;</td>' ,
						'</tr>',
						'<tr>' ,
							'<th col="zipcode">邮政编码</th> <td col="zipcode" >&nbsp;</td>' ,
							'<th col="fax">传真</th> <td col="fax" >&nbsp;</td>',
							'<th col="qqmsnNum">QQ或MSN号码</th> <td col="qqmsnNum" >&nbsp;</td>' ,
						'</tr>', 
						'<tr>' ,
							'<th col="workOrg">工作单位</th> <td col="workOrg" >&nbsp;</td>' ,
							'<th col="workAddress">单位地址</th> <td col="workAddress" colspan="3">&nbsp;</td>' ,
						'</tr>',
						FORMDIY_DETAIL_KEY,
						'<tr height="50">', 
							'<th col="remark" >备注</th> <td col="remark" colspan="5">&nbsp;</td>' ,
						'<tr>' 
						];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 125,
			    /* title : '#TITLE#', */
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './crmGuaCustomer_get.action',
			    params: {
			        id: -1
			    },
			    formDiyCfg : {
				    	sysId : self.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_CREDITINFO,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id'/*对应表单中的ID Hidden 值*/
			    	},
			    callback: {
			        sfn: function(jsonData) {
			           jsonData["degree"] = Render_dataSource.gvlistRender('100006',jsonData["degree"]);
			        	jsonData["accNature"] = Render_dataSource.accNatureDetailRender(jsonData["accNature"]);
			           	jsonData["cardType"] = Render_dataSource.gvlistRender('100002',jsonData["cardType"]);
			            jsonData["hometown"] = Render_dataSource.gvlistRender('100004',jsonData["hometown"]);
			            jsonData["nation"] = Render_dataSource.gvlistRender('100005',jsonData["nation"]);
			            jsonData["maristal"] = Render_dataSource.gvlistRender('100003',jsonData["maristal"]);
			            jsonData["age"] = Render_dataSource.ageRender(jsonData["age"]);
			            jsonData["sex"] = Render_dataSource.sexDetailRender(jsonData["sex"]);
			            jsonData["isgua"] = Render_dataSource.isguaDetailRender(jsonData["isgua"].toString());
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
			            
			             var sysId = _this.params.sysid;
			        	var formId = jsonData.id;
			        	if(!formId){
			        		formId = -1;
			        	}
			        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_35,sysId:sysId};
			        	attachMentFs.reload(params);
			        	detailPanel_1.doLayout();
			        	attachMentFs.enable(); 
			        }
			    }
			}];
			var attachLoad = function(params, cmd) {
			    /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			    //Cmw.print(params);
			    //Cmw.print(cmd);
			}
			var _this = this;
			var detailPanel_1 = new Ext.ux.panel.DetailPanel({
				id : _this.detailIdMgr.guaCustomerDetailId,
				isLoad : false,
			    autoHeight : true,border:false,
			    autoWidth:true,
			    detailCfgs: detailCfgs_1,
			    attachLoad: function(params, cmd) {
			        /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			        //Cmw.print(params);
			        //Cmw.print(cmd);
			    }
			});
			var dir = 'customerinfo_path';
			var attachMentFs = this.createAttachMentFs(this,dir);
			this.attachMentArray.push(attachMentFs);
			detailPanel_1.add(attachMentFs);
			var appgridpanel_1 = new Ext.Panel({
			    items: [appgrid_1,detailPanel_1]
			})
			return appgridpanel_1;
		},
		
		
		/**
		 * 创建其他个人信息
		 */
		creatotherInfoPnlId:function(tab,itemId,width,height){
			var self = this;
			var rememberselId = null;
			var structure = [
			{header : 'id',name : 'id',hidden : true},
			{header : 'customerId',name : 'customerId',hidden : true},
			{header : 'formType',name : 'formType',hidden : true},
			{header : '资料名称',name : 'otherName',width:150},
			{header : '上传人',id:'creator',name : 'creator',width:80},
			{header : '上传时间',id:'createTime',name : 'createTime',width:80},
			{header : '修改时间',id:'modifytime',name : 'modifytime',width:80},
			{header : '资料说明',id:'remark',name : 'remark',width:300}
			];
		var appgrid_1 = new Ext.ux.grid.AppGrid({
				id : itemId,
			    structure: structure,
			    url: './crmOtherInfo_list.action',
			    needPage: false,
			    border:false,
			    height :135,
			    isLoad: false,
			    autoScroll: true,
			    keyField: 'id'
			});
			appgrid_1.addListener("afterrender",function(){
				var panel = Ext.getCmp(itemId);
				self.loadItemPanel(panel);
			});
			appgrid_1.addListener("rowclick",function(appgrid_1,rowIndex,event){
				var selId = appgrid_1.getSelId();
				if(rememberselId!=selId && selId){
					detailPanel_1.reload({id:selId,custType:0});
					attachMentFs.enable(); 
					rememberselId=selId;
				}else{
					return;
				}
			});
			
		var htmlArrs_1 = [
						'<tr>' +
							'<th col="otherName">资料名称</th> <td col="otherName" colspan=5>&nbsp;</td>' +
						'</tr>', 
						'<tr>' +
							'<th col="remark" height=50>资料说明</th> <td col="remark" colspan=5>&nbsp;</td>' +
						'</tr>',
						FORMDIY_DETAIL_KEY
						];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 90,
			    /* title : '#TITLE#', */
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './crmOtherInfo_get.action',
			    params: {
			        id: -1
			    },
			    formDiyCfg : {
				    	sysId : self.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_CUSROMER_OTHERINFO,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id'/*对应表单中的ID Hidden 值*/
			    	},
			    callback: {
			        sfn: function(jsonData) {
			            /* 可在对页面进行赋值前，对数据进行转换处理 to do .. */
			        	var sysId = self.params.sysid;
			        	var formId = jsonData.id;
			        	if(!formId){
			        		formId = -1;
			        	}
			        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_28,sysId:sysId};
			        	attachMentFs.reload(params);
			        	appgridpanel_1.doLayout();
			        }
			    }
			}];
			var attachLoad = function(params, cmd) {
			    /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			    //Cmw.print(params);
			    //Cmw.print(cmd);
			}
			var _this = this;
			var detailPanel_1 = new Ext.ux.panel.DetailPanel({
				id : _this.detailIdMgr.otherInfoDetailId,
			    autoHeight : true,border:false,
			    autoWidth:true,
			    detailCfgs: detailCfgs_1,
			    attachLoad: function(params, cmd) {
			        /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			        //Cmw.print(params);
			        //Cmw.print(cmd);
			    }
			});
			var dir = 'customerinfo_path';
			var attachMentFs = this.createAttachMentFs(this,dir);
			this.attachMentArray.push(attachMentFs);
			detailPanel_1.add(attachMentFs);
			var appgridpanel_1 = new Ext.Panel({
			    items: [appgrid_1,detailPanel_1]
			});
			return appgridpanel_1;
		},
		/**
		 * 
		 * 销毁组件
		 */
		destroy : function(){
			if(null != this.appMainPanel){
				this.appMainPanel.destroy();
				this.appMainPanel = null;
			}
		}
	};
});