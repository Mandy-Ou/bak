/**
 * 菜单新增或修改页面
 * @author 程明卫
 * @date 2012-07-12
 */
define(function(require, exports) {
	exports.viewUI = {
		parentCfg : null,
		parent : null,
		appMainPanel : null,
		tabPanel : null,
		uploadWin :null,
		baseFormPanel : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		ecustomerInfoId : null,
		ecustomerId : null,
		formDatas : null,
		params : null,
		tab : null,
		sysId : null,	/*系统ID*/
		dispaly: null,
		btnBack : null,
		custType : Buss_Constant.CustType_1,
		applyId : null, 
		attachMentArray : [],
		apptabtreewinId : null,
		tabIdMgr:{/* Tab Id 定义*/
			ecustinfoPnlId : Ext.id(null,'ecustinfoPnlId'),	/*企业客户详细资料面板ID*/
			legalManinfoPnlId : Ext.id(null,'legalManinfoPnlId'),/*企业法人资料面板ID*/
//			efinancePnlId : Ext.id(null,'efinancePnlId'),/*企业财务状况详情面板ID*/
			ebankPnlId : Ext.id(null,'ebankPnlId'),/*企业开户详情面板ID*/
			GuaCustomerPnlId : Ext.id(null,'GuaCustomerPnlId'),/*企业开户详情面板ID*/
			eeqstructPnlId : Ext.id(null,'eeqstructPnlId'),/*股权结构详情面板ID*/
			ebankBorrPnlId : Ext.id(null,'ebankBorrPnlId'),/*银行贷款详情面板ID*/
			eownerBorrPnlId : Ext.id(null,'eownerBorrPnlId'),/*所有者贷款情况面板ID*/
			eclasstInfoPnlId : Ext.id(null,'eclasstInfoPnlId'),/*领导班子面板ID*/
			eassureInfoPnlId : Ext.id(null,'eassureInfoPnlId'),/*企业担保面板ID*/
			otherInfoPnlId : Ext.id(null,'otherInfoPnlId')/*其他信息面板ID*/
		},
		detailIdMgr:{/* Detail 中的Id 定义*/
			efinanceDelId : Ext.id(null,'efinanceDelId'),/*企业财务状况详情面板ID*/
			ebankDelId : Ext.id(null,'ebankDelId'),/*企业开户详情面板ID*/
			GuaCustomerFrmId : Ext.id(null,'GuaCustomerFrmId'),/*第三方担保ID*/
			eeqstructDelId : Ext.id(null,'eeqstructDelId'),/*股权结构详情面板ID*/
			ebankBorrDelId : Ext.id(null,'ebankBorrDelId'),/*银行贷款详情面板ID*/
			eownerBorrDelId : Ext.id(null,'eownerBorrDelId'),/*所有者贷款情况面板ID*/
			eclasstInfoDelId : Ext.id(null,'eclasstInfoDelId'),/*领导班子面板ID*/
			eassureInfoDelId : Ext.id(null,'eassureInfoDelId'),/*企业担保面板ID*/
			otherInfoDetailId : Ext.id(null,'otherInfoDetailId')/*其他信息面板ID*/
		},
		setParams:function(params){
			this.params= params;
			this.sysId = params.sysid;
			this.dispaly = params.dispaly;
			this.optionType = params.optionType;
			this.ecustomerInfoId = this.params.ecustomerInfoId;
			this.ecustomerId =this.params.ecustomerId;
				if(!this.ecustomerInfoId){ 
					ExtUtil.alert({msg:"编辑企业客户资料时，请提供   ecustomerInfoId 值"});
					return false;
				}
			return true;
		},
		/**
		 * 显示弹出窗口
		 * @param _parentCfg 弹出之前传入的参数
		 */
		getMainUI : function(tab,params){
			var _this = this;
			this.applyId = params.applyId;
			this.sysId = params.sysid;
			if(params){
				tab.notify = function(_tab){
					_this.refresh(_tab,_tab.params);
				}
				var _this = this;
				if(!this.setParams(params)) 
					return;
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
		refresh : function(tab,params){
			if(this.tab && this.apptabtreewinId){
				Cmw.showTab(this.apptabtreewinId,this.tab)
			}
			this.sysId = params.sysid;
			var _this = this;
			this.tab = tab;
			var _this = this;
			if(params.dispaly){
				if(params.activeTab){
					this.tabPanel.activate(_this.tabIdMgr.GuaCustomerPnlId);
				}else{
					this.tabPanel.activate(_this.tabIdMgr.ecustinfoPnlId);
				}
				if(_this.btnBack)_this.btnBack.show();
			}else{
				if(!params.activeTab){
					this.tabPanel.activate(_this.tabIdMgr.ecustinfoPnlId);
				}
				if(_this.btnBack)_this.btnBack.hide();
			}
			var secondEcustInfoId = params.ecustomerInfoId;//基础id
			var secondEcustomerId = params.ecustomerId;//企业表id
			this.apptabtreewinId = params.apptabtreewinId;
//			if(secondEcustInfoId != _this.ecustomerInfoId){
				_this.detailPlnLoad(secondEcustInfoId,secondEcustomerId)
				_this.cleanDetailData();
				_this.ecustomerInfoId = params.ecustomerInfoId;
				_this.ecustomerId = params.ecustomerId;
//			}
		},
		/**
		 * 在点击表格中的数据后，需要清空表格下面中的数据方法
		 */
		cleanDetailData : function(){
			var efinanceDelId = Ext.getCmp(this.detailIdMgr.efinanceDelId);
			var ebankDelId = Ext.getCmp(this.detailIdMgr.ebankDelId);
			var eeqstructDelId = Ext.getCmp(this.detailIdMgr.eeqstructDelId);
			var GuaCustomerFrmId = Ext.getCmp(this.detailIdMgr.GuaCustomerFrmId);
			var ebankBorrDelId = Ext.getCmp(this.detailIdMgr.ebankBorrDelId);
			var eownerBorrDelId = Ext.getCmp(this.detailIdMgr.eownerBorrDelId);
			var eclasstInfoDelId = Ext.getCmp(this.detailIdMgr.eclasstInfoDelId);
			var eassureInfoDelId = Ext.getCmp(this.detailIdMgr.eassureInfoDelId);
			var otherInfoDetailId = Ext.getCmp(this.detailIdMgr.otherInfoDetailId);
			var items = [efinanceDelId,ebankDelId,eeqstructDelId,GuaCustomerFrmId,ebankBorrDelId,eownerBorrDelId,
					eclasstInfoDelId,eassureInfoDelId,otherInfoDetailId];
			this.load(items,-1);
		},
		/**
		 * 加载数据
		 */
		detailPlnLoad : function(loadId,ecustomerId){
			this.baseFormPanel.reload({ecustomerId: loadId});
			var length = this.attachMentArray.length;
			if(length>0){
				for (var index = 0; index < length; index++) {
					this.attachMentArray[index].disable();
				}
			}
			/*企业客户详细资料面板ID*/
			var ecustinfoPnlId = Ext.getCmp(this.tabIdMgr.ecustinfoPnlId);
			/*企业法人资料面板ID*/
			var legalManinfoPnlId = Ext.getCmp(this.tabIdMgr.legalManinfoPnlId);
			/*企业财务详情ID*/
//			var efinancePnlId = Ext.getCmp(this.tabIdMgr.efinancePnlId);
			/*企业开户详情*/
			var ebankPnlId = Ext.getCmp(this.tabIdMgr.ebankPnlId);
				/*第三方担保详情*/
			var GuaCustomerPnlId = Ext.getCmp(this.tabIdMgr.GuaCustomerPnlId);
			/*股权结构详情*/
			var eeqstructPnlId = Ext.getCmp(this.tabIdMgr.eeqstructPnlId);
			/*银行贷款详情*/
			var ebankBorrPnlId = Ext.getCmp(this.tabIdMgr.ebankBorrPnlId);
			/*所有者贷款详情*/
			var eownerBorrPnlId = Ext.getCmp(this.tabIdMgr.eownerBorrPnlId);
			/*领导班子详情*/
			var eclasstInfoPnlId = Ext.getCmp(this.tabIdMgr.eclasstInfoPnlId);
			/*企业担保详情*/
			var eassureInfoPnlId = Ext.getCmp(this.tabIdMgr.eassureInfoPnlId);
			/*其它信息面板ID*/
			var otherInfoPnlId = Ext.getCmp(this.tabIdMgr.otherInfoPnlId);
			var items = [ecustinfoPnlId,legalManinfoPnlId,/*efinancePnlId */,ebankPnlId,eeqstructPnlId,
				ebankBorrPnlId,eownerBorrPnlId,eclasstInfoPnlId,eassureInfoPnlId,otherInfoPnlId]
			this.load(items,ecustomerId);
		},
		load :function(items,ecustomerId){
			var Pnl = null;
			for(var i=0,j=items.length;i<j;i++){
				var Pnl = items[i];
				if(!Pnl) continue;
				var xtype = Pnl.getXType();
				if(xtype=='appgrid'){
					if(Pnl == Ext.getCmp(this.tabIdMgr.otherInfoPnlId)){
						panel.reload({customerId:this.ecustomerId,custType : this.custType});
					}else{
						Pnl.reload({ecustomerId:ecustomerId,baseId : this.params.baseId });
					}
				}else{
					Pnl.reload({id:ecustomerId});
				}
			}
			
		},
		/**
		 * 创建主应用程序面板
		 */
		createAppMainPanel : function(){
			var _this =this;
			this.appMainPanel = new Ext.Panel({border:false,autoScroll: true});
			this.appMainPanel.add(this.getToolBar());
		},
		
		openDialog : function(custType, btnId) {
			var _this = this;
			this.showBackTab(custType, btnId);
				Cmw.hideTab(this.params.apptabtreewinId,this.tab);
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
//			var  apptreeId = this.appTabId;
			var apptabtreewinId = _this.params.apptabtreewinId;
			params = {custType:custType,customerId:this.customerId};
			params["applyId"] = this.applyId;
			Cmw.hideTab(this.params.apptabtreewinId,this.tab);
			Cmw.activeTab(apptabtreewinId,tabId,params,url,title);
			
		},
		createBaseFormPanel : function(){
			var ecustomerInfoId = this.ecustomerInfoId;
			var htmlArrs_1 = [
				'<tr>' +
					'<th col="code">客户编号</th> <td col="code" >&nbsp;</td>' +
					'<th col="name">企业简称</th> <td col="name" colspan="3">&nbsp;</td>' +
				'</tr>',
				'<tr>' +
					'<th col="serialNum">客户流水号</th> <td col="serialNum" >&nbsp;</td>' +
					'<th col="tradNumber">营业执照号</th> <td col="tradNumber" >&nbsp;</td>' +
					'<th col="orgcode">组织机构代码</th> <td col="orgcode" >&nbsp;</td>' +
				'</tr>',
				'<tr>' +
					'<th col="regman">登记人</th> <td col="regman" >&nbsp;</td>' +
					'<th col="registerTime">登记日期</th> <td col="registerTime" colspan="3">&nbsp;</td>' +
				'</tr>'
			];
			
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 110,
			    /* title : '#TITLE#', */
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url : './crmCustBase_detailecustget.action',
			    params: {
			        ecustomerId: ecustomerInfoId
			    }
			}];
			var attachLoad = function(params, cmd) {
			    /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			    //Cmw.print(params);
			    //Cmw.print(cmd);
			}
			var detailPanel_1 = new Ext.ux.panel.DetailPanel({
			   	autoWidth:true,
			   	isLoad : false,
			   	border:false,
			    detailCfgs: detailCfgs_1,
			    attachLoad: function(params, cmd) {
			        /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			        //Cmw.print(params);
			        //Cmw.print(cmd);
			    }
			});
			this.baseFormPanel = detailPanel_1
			return this.baseFormPanel;
		},
		/**
		 * 创建主 TabPanel 对象
		 */
		createTabPanel : function(){
			var _this = this;
			 var bodyStyle = "";
				bodyStyle = "margin:0;padding:0;width:100%;height:100%";
			this.tabPanel = new Ext.TabPanel({//disabled :true,
				enableTabScroll : true,plugins: [Ext.plugin.DragDropTabs],border : false,
				autoScroll: true,
			items : [
				{title : '企业客户详情',itemId : this.tabIdMgr.ecustinfoPnlId,bodyStyle : bodyStyle},
				{title : '企业法人详情',itemId : this.tabIdMgr.legalManinfoPnlId,bodyStyle : bodyStyle},
//				{title : '企业财务详情',itemId : this.tabIdMgr.efinancePnlId},
				{title : '第三方担保详情',itemId : this.tabIdMgr.GuaCustomerPnlId,bodyStyle : bodyStyle},
				{title : '企业开户详情',itemId : this.tabIdMgr.ebankPnlId,bodyStyle : bodyStyle},
				{title : '股权结构详情',itemId : this.tabIdMgr.eeqstructPnlId,bodyStyle : bodyStyle},
				{title : '银行贷款详情',itemId : this.tabIdMgr.ebankBorrPnlId,bodyStyle : bodyStyle},
				{title : '所有者贷款详情',itemId : this.tabIdMgr.eownerBorrPnlId,bodyStyle : bodyStyle},
				{title : '领导班子详情',itemId : this.tabIdMgr.eclasstInfoPnlId,bodyStyle : bodyStyle},
				{title : '企业担保详情',itemId : this.tabIdMgr.eassureInfoPnlId,bodyStyle : bodyStyle},
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
					var wh = _this.getWH();
					var width = wh[0];
					var height = wh[1];
					var tabItem = _this.getTabItem(tab,itemId,width,height);
					if(!tabItem){
						var tabTitle = tab["title"];
						ExtUtil.error({msg : '"'+tabTitle+'"中没有组件可加载!'});
						return;
					}
					tab.add(tabItem);
					tab.doLayout();
				}
				_this.loadItemPanel(panel);
			},this);
		},
		
		loadItemPanel : function(panel){
			var _this = this;
			if(!panel) return;
			var itemId = panel.id;
			var xtype = panel.getXType();
			var ecustomerId = panel.ecustomerId;
			if(ecustomerId ==_this.ecustomerId){
				return;
			}else{
				if(xtype=='appgrid'){
					if(itemId == _this.tabIdMgr.otherInfoPnlId){
						panel.reload({customerId:_this.ecustomerId,custType : _this.custType});
					}else{
						panel.reload({ecustomerId:_this.ecustomerId,baseId:_this.params.baseId,custType : _this.custType});
					}
				}else{
					panel.reload({id:_this.ecustomerId});
				}
				panel.ecustomerId = _this.ecustomerId;
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
				case this.tabIdMgr.ecustinfoPnlId :{	/*企业客户详细资料*/
					tatItem = this.createEcustinfoDetailPnlId(tab,itemId,width,height);
					break;
				}case this.tabIdMgr.legalManinfoPnlId :{	/*公司法人资料*/
					tatItem = this.createLegalManinfoDetailPnlId(tab,itemId,width,height);
					break;
				}
//				case this.tabIdMgr.efinancePnlId :{	/*企业财务状况*/
//					tatItem = this.createefinanceDetailPnlId(tab,itemId,width,height);
//					break;
//				}
				case this.tabIdMgr.GuaCustomerPnlId :{	/*第三方担保详情资料*/
					tatItem = this.createeGuaCustomerPnlId(tab,itemId,width,height);
					break;
				}
				case this.tabIdMgr.ebankPnlId :{	/*企业开户详情资料*/
					tatItem = this.createebankDetailPnlId(tab,itemId,width,height);
					break;
				}case this.tabIdMgr.eeqstructPnlId :{	/*股权结构资料*/
					tatItem = this.createeeqstructDetailPnlId(tab,itemId,width,height);
					break;
				}case this.tabIdMgr.ebankBorrPnlId :{	/*银行贷款资料*/
					tatItem = this.createebankBorrDetailPnlId(tab,itemId,width,height);
					break;
				}case this.tabIdMgr.eownerBorrPnlId :{	/*所有者贷款资料*/
					tatItem = this.createeownerBorrDetailPnlId(tab,itemId,width,height);
					break;
				}case this.tabIdMgr.eclasstInfoPnlId :{	/*领导班子用资料*/
					tatItem = this.createeclasstInfoDetailPnlId(tab,itemId,width,height);
					break;
				}case this.tabIdMgr.eassureInfoPnlId :{	/*企业担保用资料*/
					tatItem = this.createeassureInfoDetailPnlId(tab,itemId,width,height);
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
			var dir = 'ecustomerinfo_path';
			/* ----- 参数说明： isLoad 是否实例化后马上加载数据 
			 * dir : 附件上传后存放的目录， mortgage_path 定义在 resource.properties 资源文件中
			 * isSave : 附件上传后，是否保存到 ts_attachment 表中。 true : 保存
			 * params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔}
			 *    当 isLoad = false 时， params 可在 reload 方法中提供
			 *  ---- */
			var cfg = {title:'相关材料附件',isLoad:true,dir : dir,isSave:true,disabled : true,isNotDisenbaled:true};
			if(!params){
				var uuid = Cmw.getUuid();
				params = {formType:-1,formId : uuid,sysId:_this.params.sysId,isNotDisenbaled:true};
			}
			cfg.params = params;
			var attachMentFs = new  Ext.ux.AppAttachmentFs(cfg);
			return attachMentFs;
		},
		/**
		 * 企业客户详细资料
		 */
		createEcustinfoDetailPnlId : function(tab,itemId,width,height){
			var ecustomerId= this.ecustomerId;
			var _this= this;
			var htmlArrs_1 = [
				'<tr>',
					'<th col="shortName">企业简称</th> <td col="shortName" >&nbsp;</td>' ,
					'<th col="kind">企业性质</th> <td col="kind" >&nbsp;</td>' ,
					'<th col="trade">所属行业</th> <td col="trade" >&nbsp;</td>' ,
				'</tr>',
				'<tr>',
					'<th col="address">企业地址</th> <td col="address" colspan="3" >&nbsp;</td>' ,
					'<th col="comeTime">成立时间</th> <td col="comeTime" >&nbsp;</td>',
				'</tr>',
				'<tr>' ,
					'<th col="contactor">联系人</th> <td col="contactor" >&nbsp;</td>' ,
					'<th col="phone">联系人手机</th> <td col="phone" >&nbsp;</td>' ,
					'<th col="contacttel">联系电话</th> <td col="contacttel" >&nbsp;</td>' ,
				'</tr>',
					'<tr><th col="fax">传真</th> <td col="fax" >&nbsp;</td>' ,
					'<th col="email">电子邮件</th> <td col="email" >&nbsp;</td>' ,
					'<th col="zipCode">邮编</th> <td col="zipCode" >&nbsp;</td>' ,
				'</tr>',
				'<tr>' ,
					'<th col="legalMan">法定代表人</th> <td col="legalMan" >&nbsp;</td>' ,
					'<th col="legalIdCard">法人身份证</th> <td col="legalIdCard" >&nbsp;</td>' ,
					'<th col="legalTel">法人联系电话</th> <td col="legalTel" >&nbsp;</td>',
				'</tr>', 
				'<tr>' ,
					'<th col="managerName">总经理</th> <td col="managerName" >&nbsp;</td>' ,
					'<th col="managerIdCard">总经理身份证</th> <td col="managerIdCard" >&nbsp;</td>' ,
					'<th col="managerTel">总经理联系电话</th> <td col="managerTel" >&nbsp;</td>' ,
				'</tr>', 
				'<tr>' ,
					'<th col="finaManager">财务经理</th> <td col="finaManager" >&nbsp;</td>' ,
					'<th col="finaIdCard">财务经理身份证</th> <td col="finaIdCard" >&nbsp;</td>',
					'<th col="finaTel">财务经理联系电话</th> <td col="finaTel" >&nbsp;</td>' ,
				'</tr>', 
				'<tr>',
					'<th col="url">网址</th> <td col="url" >&nbsp;</td>',
					'<th col="taxNumber">国税登记号</th> <td col="taxNumber" >&nbsp;</td>' ,
					'<th col="areaNumber">地税登记号</th> <td col="areaNumber" >&nbsp;</td>',
				'</tr>', 
				'<tr>',
					'<th col="licence">经营许可证</th> <td col="licence" >&nbsp;</td>',
					'<th col="licencedate">许可证颁发时间</th> <td col="licencedate" >&nbsp;</td>' ,
					'<th col="regaddress">注册地址</th> <td col="regaddress" >&nbsp;</td>',
				'</tr>', 
				'<tr>' ,
					'<th col="regcapital">注册资本(元)</th> <td col="regcapital" >&nbsp;</td>',
					'<th col="currency">注册资金币种</th> <td col="currency" >&nbsp;</td>' ,
					'<th col="incapital">实收资本(元)</th> <td col="incapital" >&nbsp;</td>' ,
				'</tr>', 
				'<tr>' ,
					'<th col="patentNumber">专利证书号码</th> <td col="patentNumber" >&nbsp;</td>',
					'<th col="rent">月租金(元)</th> <td col="rent" >&nbsp;</td>' ,
					'<th col="loanBank">贷款银行</th> <td col="loanBank" >&nbsp;</td>' ,
				'</tr>', 
				'<tr>' ,
					'<th col="loanNumber">贷款卡号</th> <td col="loanNumber" >&nbsp;</td>' ,
					'<th col="loanLog">贷款卡记录</th> <td col="loanLog" >&nbsp;</td>' ,
					'<th col="manamount">企业人数(人)</th> <td col="manamount" >&nbsp;</td>',
				'</tr>', 
				'<tr>' ,
					'<th col="empCount">在职员工人数(人)</th> <td col="empCount" >&nbsp;</td>' ,
					'<th col="insCount">社保参保人数(人)</th> <td col="insCount" >&nbsp;</td>' ,
					'<th col="registerTime">登记时间</th> <td col="registerTime" >&nbsp;</td>',
				'</tr>', 
				FORMDIY_DETAIL_KEY,
				'<tr height="50">' ,
					'<th col="remark">备注</th> <td col="remark"  colspan="5" >&nbsp;</td>' ,
				'</tr>'];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    model: 'single',
			    labelWidth: 90,
			    htmls: htmlArrs_1,
			    url: './crmEcustomer_get.action',
			    params: {
			        id: ecustomerId
			    },
			    formDiyCfg : {
				    	sysId : _this.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_ECUSTOMER_INFO,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id'/*对应表单中的ID Hidden 值*/
			    	},
			    callback: {
			        sfn: function(jsonData) {
			            jsonData["kind"]  = Render_dataSource.gvlistRender('100009',jsonData["kind"]);
			             jsonData["trade"]  = Render_dataSource.gvlistRender('100011',jsonData["trade"]);
			             jsonData["currency"]  = Render_dataSource.gvlistRender('100014',jsonData["currency"]);
			             jsonData["rent"] = Render_dataSource.moneyRender(jsonData["rent"]);
			             jsonData["incapital"] = Render_dataSource.moneyRender(jsonData["incapital"]);
			             jsonData["regcapital"] = Render_dataSource.moneyRender(jsonData["regcapital"]);
			             
			             var ia = "";
			            if(jsonData["inAddress"]!=null){
			            	var inArea = [];
			            	inArea = jsonData["address"].split("##");
			            	ia = inArea[1];
			            }
			            if(jsonData["inAddress"]==null){
			            	jsonData["inAddress"] = "";
			            }
			            jsonData["address"] = ia+jsonData["inAddress"];
			            var sysId = _this.params.sysid;
			        	var formId = jsonData.id;
			        	if(!formId){
			        		formId = -1;
			        	}
			        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_19,sysId:sysId,isNotDisenbaled:true};
			        	attachMentFs.reload(params);
			        	attachMentFs.enable(); 
			        	detailPanel_1.doLayout();
			        }
			    }
			}];
			var cfg = {autoWidth:true,autoHeight : true,detailCfgs:detailCfgs_1,id : itemId};
			var detailPanel_1 = new Ext.ux.panel.DetailPanel(cfg);
			var attachMentFs = this.createAttachMentFs(this);
			detailPanel_1.add(attachMentFs);
			return detailPanel_1;
		},
		
		/**
		 * 企业法人资料
		 */
		createLegalManinfoDetailPnlId :function(tab,itemId,width,height){
			var ecustomerId = this.ecustomerId;
			var _this = this;
			var htmlArrs_1 = [
					'<tr>' ,
						'<th col="name">客户姓名</th> <td col="name" >&nbsp;</td>' ,
						'<th col="sex">性别</th> <td col="sex" >&nbsp;</td>' ,
						'<th col="accNature">户口性质</th> <td col="accNature" >&nbsp;</td>',
					'</tr>', 
					'<tr>' ,
						'<th col="cardType">证件类型</th> <td col="cardType" >&nbsp;</td>' ,
						'<th col="cardNum">证件号码：</th> <td col="cardType">&nbsp;</td>',
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
			var detailCfgs = [{
			    cmns: 'THREE',
			    model: 'single',
			    labelWidth : 110,
			    htmls: htmlArrs_1,
			    url: './crmCustomerInfo_getlegalMan.action',
			    params: {
			        id: ecustomerId
			    },
			    formDiyCfg : {
				    	sysId : _this.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_LEGAL,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
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
			        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_20,sysId:sysId};
			        	attachMentFs.reload(params);
			        	detailPanel.doLayout();
			        	attachMentFs.enable(); 
			        }
			    }
			}];
			var cfg = {autoWidth:true,autoHeight : true,detailCfgs:detailCfgs,id : itemId,ecustomerId : ecustomerId,isNotDisenbaled:true};
			var detailPanel = new Ext.ux.panel.DetailPanel(cfg);
			var attachMentFs = this.createAttachMentFs(this);
			detailPanel.add(attachMentFs);
			return detailPanel;
		},
		
		/**
		 * 企业财务状况
		 */
		
		createefinanceDetailPnlId:function(tab,itemId,width,height){
			var self  = this;
			var rememberselId = null;
			var structure_1 = [{
			    header: '企业客户ID',
			    name: 'ecustomerId',
			    width: 125
			},
			{
			    header: '报表类型',
			    name: 'reportType',
			    width: 125,
			    renderer: function(val) {
			        switch (val) {
			        case 1:
			            val = '1';
			            break;
			
			        }
			        return val;
			    }
			},
			{
			    header: '截止月份',
			    name: 'endDate',
			    width: 125,
			    renderer: function(val) {
			        switch (val) {
			        case 0:
			            val = '0';
			            break;
			
			        }
			        return val;
			    }
			},
			{
			    header: '资产负债表',
			    name: 'hasBalance',
			    width: 125,
			    renderer: function(val) {
			        switch (val) {
			        case 1:
			            val = '1';
			            break;
			
			        }
			        return val;
			    }
			},
			{
			    header: '现金流量表',
			    name: 'hasCash',
			    width: 125,
			    renderer: function(val) {
			        switch (val) {
			        case 1:
			            val = '1';
			            break;
			
			        }
			        return val;
			    }
			},
			{
			    header: '利润表',
			    name: 'hasProfit',
			    width: 125,
			    renderer: function(val) {
			        switch (val) {
			        case 1:
			            val = '1';
			            break;
			
			        }
			        return val;
			    }
			}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				id : itemId,
			    structure: structure_1,
			    url: './crmEfinance_list.action',
			    needPage: false,
			    height : 150,
			    autoScroll : true,
			    isLoad: false,
			    keyField: 'id'
			});
			appgrid_1.addListener("afterrender",function(){
				var panel = Ext.getCmp(itemId);
				self.loadItemPanel(panel);
			});
			appgrid_1.addListener("rowclick",function(appgrid_1,rowIndex,event){
				var selId = appgrid_1.getSelId();
				if(rememberselId!=selId){
					detailPanel_1.reload({id:selId});
					rememberselId=selId;
				}else{
					return;
				}
			});
			return appgrid_1;
		},
		/**
		 * 企业第三方担保
		 */
		createeGuaCustomerPnlId:function(tab,itemId,width,height){
		var self = this;
			var rememberselId = null;
			var structure_1 = [ {
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
						header : '联系电话',
						name : 'tel'
					},{
						header : '手机',
						name : 'phone'
					},{
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
							'<th col="name">担保人名称</th> <td col="name" colspan=3>&nbsp;</td>' ,
							'<th col="contactor">担保责任人</th> <td col="contactor" >&nbsp;</td>' ,
						'</tr>', 
						'<tr>',
							'<th col="isgua">是否无限连带担保责任</th> <td col="isgua" >&nbsp;</td>' ,
							'<th col="relation">与被担保人关系</th> <td col="relation" >&nbsp;</td>',
							'<th col="cardType">证件类型</th> <td col="cardType" >&nbsp;</td>' ,
						'</tr>',
						'<tr>' ,
							'<th col="cardNum">证件号码</th> <td col="cardType">&nbsp;</td>',
							'<th col="phone">手机</th> <td col="phone" >&nbsp;</td>' ,
							'<th col="contactTel">联系电话</th> <td col="contactTel" >&nbsp;</td>' ,
						'</tr>',
						'<tr>' ,
							'<th col="inArea">现居住地区</th> <td col="inArea" colspan="5">&nbsp;</td>' ,
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
			        	if(jsonData == -1){
			        		return;
			        	}
			           jsonData["degree"] = Render_dataSource.gvlistRender('100006',jsonData["degree"]);
			           	jsonData["cardType"] = Render_dataSource.guaCardTypeRender(jsonData["cardType"].toString());
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
		 * 企业开户
		 */
		createebankDetailPnlId:function(tab,itemId,width,height){
			var self = this;
			var rememberselId = null;
			var structure_1 = [{
		    header: 'id',
		    name: 'id',
			    hidden : true
			},{
		    header: '企业客户ID',
		    name: 'ecustomerId',
		    hidden : true
		},
		{
		    header: '帐户类型',
		    name: 'accountType',
		    renderer: Render_dataSource.accountTyperenderer
		},
		{
		    header: '开户帐号',
		    name: 'account'
		},
		{
		    header: '开户银行',
		    name: 'bankOrg'
		},
		{
		    header: '开户时间',
		    name: 'orderDate',
		   	renderer: Ext.util.Format.dateRenderer('Y-m-d')
		},
		{
		    header: '月均结算量',
		    name: 'setAmount',
		     renderer : Render_dataSource.moneyRender
		},
		{
		    header: '月均存款余额',
		    name: 'balance',
		     renderer : Render_dataSource.moneyRender
		},
		{
		    header: '其他情况说明',
		    name: 'remark',
		    width: 200
		}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				id : itemId,
			    structure: structure_1,
			    url: './crmEbank_list.action',
			    height :135,
			    needPage: false,
			    isLoad: false,
			    keyField: 'id'
			});
			appgrid_1.addListener("afterrender",function(){
				var panel = Ext.getCmp(itemId);
				self.loadItemPanel(panel);
			});
			appgrid_1.addListener("rowclick",function(appgrid_1,rowIndex,event){
				var selId = appgrid_1.getSelId();
				if(rememberselId!=selId){
					detailPanel_1.reload({id:selId});
					attachMentFs.enable(); 
					rememberselId=selId;
				}else{
					return;
				}
			});
			var htmlArrs_1 = [
				'<tr>' +
					'<th col="accountType">帐户类型</th> <td col="accountType">&nbsp;</td>' +
					'<th col="account">开户帐号</th> <td col="account" >&nbsp;</td>' +
					'<th col="bankOrg">开户银行</th> <td col="bankOrg" >&nbsp;</td>' +
				'</tr>', 
				'<tr>' +
					'<th col="orderDate">开户时间</th> <td col="orderDate" >&nbsp;</td>' +
					'<th col="setAmount">月均结算量(元)</th> <td col="setAmount" >&nbsp;</td>' +
					'<th col="balance">月均存款余额(元)</th> <td col="balance" >&nbsp;</td>' +
				'</tr>' ,
				FORMDIY_DETAIL_KEY,
				'<tr height="50">' +
					'<th col="remark">备注</th> <td col="remark"  colspan="5" >&nbsp;</td>' +
				'</tr>'
				];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 90,
			    htmls: htmlArrs_1,
			    url: './crmEbank_get.action',
			    params: {
			        id: -1
			    },
			     formDiyCfg : {
				    	sysId : self.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_EBANK,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id'/*对应表单中的ID Hidden 值*/
			    	},
			    callback: {
			        sfn: function(jsonData) {
			            /* 可在对页面进行赋值前，对数据进行转换处理 to do .. */
			            jsonData["accountType"] = Render_dataSource.accountTyperDetailenderer( jsonData["accountType"]);
			            jsonData["setAmount"] = Render_dataSource.moneyRender(jsonData["setAmount"]);
			            jsonData["balance"] = Render_dataSource.moneyRender(jsonData["balance"]);
			            
			             var sysId = self.params.sysid;
			        	var formId = jsonData.id;
			        	if(!formId){
			        		formId = -1;
			        	}
			        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_22,sysId:sysId,isNotDisenbaled:true};
			        	attachMentFs.reload(params);
			        	detailPanel_1.doLayout();
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
				id : _this.detailIdMgr.ebankDelId,
			    autoWidth: true,
			    detailCfgs: detailCfgs_1,
			    attachLoad: function(params, cmd) {
			        /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			        //Cmw.print(params);
			        //Cmw.print(cmd);
			    }
			});
			var attachMentFs = this.createAttachMentFs(this);
			this.attachMentArray.push(attachMentFs);
			detailPanel_1.add(attachMentFs);
			var appgridpanel_1 = new Ext.Panel({
			    items: [appgrid_1,detailPanel_1]
			})
			return appgridpanel_1;
		},
		/**
		 * 股权结构资料
		 */
		createeeqstructDetailPnlId:function(tab,itemId,width,height){
			var self = this;
			var rememberselId = null;
			var structure_1 = [{
			    header: 'id',
			    name: 'id',
			    hidden : true
			},{
			    header: '企业客户ID',
			    name: 'ecustomerId',
			    hidden : true
			},
			{
			    header: '出资人名称',
			    name: 'name'
			},
			{
			    header: '出资方式',	
			    name: 'outType',
			   renderer: function(val) {
		       		 return Render_dataSource.gvlistRender('100013',val);
		    	}
			},
			{
			    header: '出资额',
			    name: 'inAmount',
			    renderer : Render_dataSource.wmoneyRender
			},
			{
			    header: '占比例（%）',
			    name: 'percents',
			    renderer : function(val){
			    	return (val)? val+'%':'';
			    }
			},
			{
			    header: '出资时间',
			    name: 'storderDate'
			},
			{
			    header: '其他情况说明',
			    name: 'remark',
			    width : 250
			}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				id : itemId,
			    structure: structure_1,
			    url: './crmEeqstruct_list.action',
			    height : 150,
			    needPage: false,
			    isLoad: false,
			    keyField: 'id'
			});
			appgrid_1.addListener("afterrender",function(){
				var panel = Ext.getCmp(itemId);
				self.loadItemPanel(panel);
			});
			appgrid_1.addListener("rowclick",function(appgrid_1,rowIndex,event){
				var selId = appgrid_1.getSelId();
				if(rememberselId!=selId){
					detailPanel_1.reload({id:selId});
					attachMentFs.enable(); 
					rememberselId=selId;
				}else{
					return;
				}
			});
			var htmlArrs_1 = [
					'<tr>' +
						'<th col="name">出资人名称</th> <td col="name" >&nbsp;</td>' +
						'<th col="outType">出资方式</th> <td col="outType" >&nbsp;</td>' +
						'<th col="inAmount">出资额</th> <td col="inAmount" >&nbsp;</td>' +
					'</tr>', 
					'<tr>' +
						'<th col="percents">占比例（%）</th> <td col="percents" >&nbsp;</td>' +
						'<th col="storderDate">出资时间</th> <td col="storderDate"  colspan=3 >&nbsp;</td>' +
					'</tr>',
					FORMDIY_DETAIL_KEY,
					'<tr height="50">' +
					'<th col="remark">备注</th> <td col="remark"  colspan="5" >&nbsp;</td>' +
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
			    url: './crmEeqstruct_get.action',
//			    prevUrl: '#PREURLCFG#',
//			    nextUrl: '#NEXTURLCFG#',
			    params: {
			        id: -1
			    },
			    formDiyCfg : {
				    	sysId : self.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_EEQSTRUCT,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id'/*对应表单中的ID Hidden 值*/
			    	},
			    callback: {
			        sfn: function(jsonData) {
			        	jsonData["inAmount"] = Render_dataSource.wmoneyRender(jsonData["inAmount"]);
			        	(jsonData["percents"])? jsonData["percents"] += '%' :'';
			          	jsonData["outType"]  = Render_dataSource.gvlistRender('100013',jsonData["outType"]);
			          	 var sysId = _this.params.sysid;
			        	var formId = jsonData.id;
			        	if(!formId){
			        		formId = -1;
			        	}
			        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_23,sysId:sysId,isNotDisenbaled:true};
			        	attachMentFs.reload(params);
			        	detailPanel_1.doLayout();
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
				id : _this.detailIdMgr.eeqstructDelId,
			    autoWidth: true,
			    detailCfgs: detailCfgs_1,
			    attachLoad: function(params, cmd) {
			        /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			        //Cmw.print(params);
			        //Cmw.print(cmd);
			    }
			});
			var attachMentFs = this.createAttachMentFs(this);
			this.attachMentArray.push(attachMentFs);
			detailPanel_1.add(attachMentFs);
			var appgridpanel_1 = new Ext.Panel({
			    items: [appgrid_1,detailPanel_1]
			})
			return appgridpanel_1;
		},
		/**
		 * 银行贷款资料
		 */
		createebankBorrDetailPnlId:function(tab,itemId,width,height){
			var self = this;
			var rememberselId = null;
			var structure_1 = [{
			    header: '企业客户ID',
			    name: 'ecustomerId',
			    hidden :true
			},
			{
			    header: '银行名称',
			    name: 'name'
			},
			{
			    header: '借款金额',
			    name: 'amount',
			    renderer : Render_dataSource.moneyRender
			},
			{
			    header: '借款期限',
			    name: 'limits'
			},
			{
			    header: '信贷品种',
			    name: 'creditBreed'
			},
			{
			    header: '担保方式',
			    name: 'asstype'
			},
			{
			    header: '贷款分类结果',
			    name: 'result'
			},{
			    header: '其他情况说明',
			    name: 'remark',
			    width : 250
			}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				id : itemId,
			    structure: structure_1,
			    url: './crmEbankBorr_list.action',
			    height :150,
			    needPage: false,
			    isLoad: false,
			    keyField: 'id'
			});
			appgrid_1.addListener("afterrender",function(){
				var panel = Ext.getCmp(itemId);
				self.loadItemPanel(panel);
			});
			appgrid_1.addListener("rowclick",function(appgrid_1,rowIndex,event){
				var selId = appgrid_1.getSelId();
				if(rememberselId!=selId){
					detailPanel_1.reload({id:selId});
					attachMentFs.enable(); 
					rememberselId=selId;
				}else{
					return;
				}
			});
			var htmlArrs_1 = [
				'<tr>' +
					'<th col="name">银行名称</th> <td col="name" >&nbsp;</td>' +
					'<th col="amount">借款金额(元)</th> <td col="amount" >&nbsp;</td>' +
					'<th col="limits">借款期限</th> <td col="limits" >&nbsp;</td>' +
				'</tr>', 
				'<tr>' +
					'<th col="creditBreed">信贷品种</th> <td col="creditBreed" >&nbsp;</td>' +
					'<th col="asstype">担保方式</th> <td col="asstype" >&nbsp;</td>' +
					'<th col="result">贷款分类结果</th> <td col="result" >&nbsp;</td>' +
				'</tr>' ,
				FORMDIY_DETAIL_KEY,
				'<tr height="50">' +
					'<th col="remark">备注</th> <td col="remark"  colspan="5" >&nbsp;</td>' +
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
			    url: './crmEbankBorr_get.action',
//			    prevUrl: '#PREURLCFG#',
//			    nextUrl: '#NEXTURLCFG#',
			    params: {
			        id: -1
			    },
			    formDiyCfg : {
				    	sysId : self.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_EBANKBORR,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id'/*对应表单中的ID Hidden 值*/
			    	},
			    callback: {
			        sfn: function(jsonData) {
			            /* 可在对页面进行赋值前，对数据进行转换处理 to do .. */
			            jsonData["amount"] = Render_dataSource.moneyRender(jsonData["amount"]);
			            var sysId = _this.params.sysid;
			        	var formId = jsonData.id;
			        	if(!formId){
			        		formId = -1;
			        	}
			        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_24,sysId:sysId,isNotDisenbaled:true};
			        	attachMentFs.reload(params);
			        	detailPanel_1.doLayout();
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
				id : _this.detailIdMgr.ebankBorrDelId,
			    autoWidth: true,
			    detailCfgs: detailCfgs_1,
			    attachLoad: function(params, cmd) {
			        /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			        //Cmw.print(params);
			        //Cmw.print(cmd);
			    }
			});
			var attachMentFs = this.createAttachMentFs(this);
			this.attachMentArray.push(attachMentFs);
			detailPanel_1.add(attachMentFs);
			var appgridpanel_1 = new Ext.Panel({
			    items: [appgrid_1,detailPanel_1]
			})
			return appgridpanel_1;
		},
		/**
		 * 所有者贷款资料
		 */
		createeownerBorrDetailPnlId:function(tab,itemId,width,height){
			var self = this;
			var rememberselId = null;
			var structure_1 = [{
			    header: '企业客户ID',
			    name: 'ecustomerId',
			    hidden : true
			},
			{
			    header: '所有者类型',
			    name: 'onwerType',
			    renderer:function(val){return Render_dataSource.gvlistRender('100012',val)}
			},
			{
			    header: '所有者名称',
			    name: 'onwer'
			},
			{
			    header: '银行名称',
			    name: 'name'
			},
			{
			    header: '借款金额',
			    name: 'amount',
			     renderer : Render_dataSource.moneyRender
			},
			{
			    header: '借款期限',
			    name: 'limits'
			},
			{
			    header: '信贷品种',
			    name: 'creditBreed'
			},
			{
			    header: '担保方式',
			    name: 'asstype'
			},
			{
			    header: '贷款分类结果',
			    name: 'result'
			},{
			    header: '其他情况说明',
			    name: 'remark',
			    width : 250
			}];
		
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				id : itemId,
			    structure: structure_1,
			    url: './crmEownerBorr_list.action',
			    height : 150,
			    needPage: false,
			    isLoad: false,
			    keyField: 'id'
			});
			appgrid_1.addListener("afterrender",function(){
				var panel = Ext.getCmp(itemId);
				self.loadItemPanel(panel);
			});
			appgrid_1.addListener("rowclick",function(appgrid_1,rowIndex,event){
				var selId = appgrid_1.getSelId();
				if(rememberselId!=selId){
					detailPanel_1.reload({id:selId});
					attachMentFs.enable(); 
					rememberselId=selId;
				}else{
					return;
				}
			});
			var htmlArrs_1 = [
					'<tr>' +
						'<th col="onwerType">所有者类型</th> <td col="onwerType" >&nbsp;</td>' +
						'<th col="onwer">所有者名称</th> <td col="onwer" >&nbsp;</td>' +
						'<th col="name">银行名称</th> <td col="name" >&nbsp;</td>' +
					'</tr>', 
					'<tr>' +
						'<th col="amount">借款金额</th> <td col="amount" >&nbsp;</td>' +
						'<th col="limits">借款期限</th> <td col="limits" >&nbsp;</td>' +
						'<th col="creditBreed">信贷品种</th> <td col="creditBreed" >&nbsp;</td>' +
					'</tr>',
					'<tr>' +
						'<th col="asstype">担保方式</th> <td col="asstype" >&nbsp;</td>' +
						'<th col="result">贷款分类结果</th> <td col="result"  colspan=3 >&nbsp;</td>' +
					'</tr>',
					FORMDIY_DETAIL_KEY,
					'<tr height="50">',
						'<th col="remark">备注</th> <td col="remark"  colspan="5" >&nbsp;</td>' +
					'</tr>'
						];
			
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 100,
			    /* title : '#TITLE#', */
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './crmEownerBorr_get.action',
//			    prevUrl: '#PREURLCFG#',
//			    nextUrl: '#NEXTURLCFG#',
			    params: {
			        id: -1
			    },
			    formDiyCfg : {
				    	sysId : self.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_EOWNERBORR,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id'/*对应表单中的ID Hidden 值*/
			    	},
					
			    callback: {
			        sfn: function(jsonData) {
			            /* 可在对页面进行赋值前，对数据进行转换处理 to do .. */
			           jsonData["onwerType"]  = Render_dataSource.gvlistRender('100012',jsonData["onwerType"]);
			           jsonData["amount"] = Render_dataSource.moneyRender(jsonData["amount"]);
			            var sysId = _this.params.sysid;
			        	var formId = jsonData.id;
			        	if(!formId){
			        		formId = -1;
			        	}
			        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_25,sysId:sysId,isNotDisenbaled:true};
			        	attachMentFs.reload(params);
			        	detailPanel_1.doLayout();
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
				id : _this.detailIdMgr.eownerBorrDelId,
			    autoWidth: true,
			    detailCfgs: detailCfgs_1,
			    attachLoad: function(params, cmd) {
			        /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			        //Cmw.print(params);
			        //Cmw.print(cmd);
			    }
			});
			var attachMentFs = this.createAttachMentFs(this);
			this.attachMentArray.push(attachMentFs);
			detailPanel_1.add(attachMentFs);
			var appgridpanel_1 = new Ext.Panel({
			    items: [appgrid_1,detailPanel_1]
			})
			return appgridpanel_1;
		},
		/**
		 * 领导班子
		 */
		createeclasstInfoDetailPnlId:function(tab,itemId,width,height){
			var self = this;
			var rememberselId = null;
			var structure_1 = [{
			    header: '企业客户ID',
			    name: 'ecustomerId',
			    hidden : true
			},
			{
			    header: '领导名称',
			    name: 'fugleName'
			},
			{
			    header: '性别',
			    name: 'sex',
			    width : 60,
			    renderer :Render_dataSource.sexRender
			},
			{
			    header: '出生日期',
			    name: 'birthday',
			    renderer : Ext.util.Format.dateRenderer('Y-m-d')
			},
			{
			    header: '证件类型',
			    name: 'cardType',
			    renderer: function(val) {
			       return Render_dataSource.gvlistRender('100002',val);
			    }
			},
			{
			    header: '证件号码',
			    name: 'cardNumer'
			},
			{
			    header: '籍贯',
			    name: 'hometown',
			    renderer : function(val){
			    	return Render_dataSource.gvlistRender('100004',val);
			    }
			},
			{
			    header: '民族',
			    name: 'nation',
			    renderer : function(val){
			    	return Render_dataSource.gvlistRender('100005',val);
			    }
			},
			{
			    header: '学历',
			    name: 'degree',
			     renderer : function(val){
			    	return Render_dataSource.gvlistRender('100006',val);
			    }
			},
			{
			    header: '职务',
			    name: 'job'
			},
			{
			    header: '是否董事成员',
			    name: 'isMember',
			    renderer: Render_dataSource.FormdiyRender
			},
			{
			    header: '联系电话',
			    name: 'Tel'
			},
			{
			    header: '手机',
			    name: 'phone'
			},
			{
			    header: '任职时间',
			    name: 'incomeTime',
			    renderer : Ext.util.Format.dateRenderer('Y-m-d')
			}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				id : itemId,
			    structure: structure_1,
			    url: './crmEclass_list.action',
			    needPage: false,
			    height :150,
			    isLoad: true,
			    keyField: 'id'
			});
			appgrid_1.addListener("afterrender",function(){
				var panel = Ext.getCmp(itemId);
				self.loadItemPanel(panel);
			});
			appgrid_1.addListener("rowclick",function(appgrid_1,rowIndex,event){
				var selId = appgrid_1.getSelId();
				if(rememberselId!=selId){
					detailPanel_1.reload({id:selId});
					attachMentFs.enable(); 
					rememberselId=selId;
				}else{
					return;
				}
			});
			var htmlArrs_1 = [
				'<tr>' +
					'<th col="fugleName">领导名称</th> <td col="fugleName" >&nbsp;</td>' +
					'<th col="sex">性别</th> <td col="sex" >&nbsp;</td>' +
					'<th col="birthday">出生日期</th> <td col="birthday" >&nbsp;</td>' +
				'</tr>', 
				'<tr>' +
					'<th col="cardType">证件类型</th> <td col="cardType" >&nbsp;</td>' +
					'<th col="cardNumer">证件号码</th> <td col="cardNumer" >&nbsp;</td>' +
					'<th col="hometown">籍贯</th> <td col="hometown" >&nbsp;</td>' +
				'</tr>', 
				'<tr>' +
					'<th col="nation">民族</th> <td col="nation" >&nbsp;</td>' +
					'<th col="degree">学历</th> <td col="degree" >&nbsp;</td>' +
					'<th col="job">职务</th> <td col="job" >&nbsp;</td>' +
				'</tr>', 
				'<tr>' +
					'<th col="isMember">是否董事成员</th> <td col="isMember" >&nbsp;</td>' +
					'<th col="Tel">联系电话</th> <td col="Tel" >&nbsp;</td>' +
					'<th col="phone">手机</th> <td col="phone" >&nbsp;</td>' +
				'</tr>', 
				'<tr>' +
					'<th col="incomeTime">任职时间</th> <td col="incomeTime"  colspan=5 >&nbsp;</td>' +
				'</tr>',
				FORMDIY_DETAIL_KEY,
				'<tr height="50">'+
					'<th col="remark">备注</th> <td col="remark"  colspan="5" >&nbsp;</td>' +
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
			    url: './crmEclass_get.action',
//			    prevUrl: '#PREURLCFG#',
//			    nextUrl: '#NEXTURLCFG#',
			    params: {
			        id: -1
			    },
			    formDiyCfg : {
				    	sysId : self.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_ECLASS,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id'/*对应表单中的ID Hidden 值*/
			    	},
			    callback: {
			        sfn: function(jsonData) {
			            /* 可在对页面进行赋值前，对数据进行转换处理 to do .. */
		        	  jsonData["sex"] = Render_dataSource.sexDetailRender(jsonData["sex"]);
		        	   jsonData["isMember"] = Render_dataSource.isDefaultRenders(jsonData["isMember"]);
			          jsonData["cardType"]  = Render_dataSource.gvlistRender('100002',jsonData["cardType"]);
			          jsonData["hometown"]  = Render_dataSource.gvlistRender('100004',jsonData["hometown"]);
			          jsonData["nation"]  = Render_dataSource.gvlistRender('100005',jsonData["nation"]);
			          jsonData["degree"]  = Render_dataSource.gvlistRender('100006',jsonData["degree"]);
			          var sysId = _this.params.sysid;
			        	var formId = jsonData.id;
			        	if(!formId){
			        		formId = -1;
			        	}
			        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_26,sysId:sysId,isNotDisenbaled:true};
			        	attachMentFs.reload(params);
			        	detailPanel_1.doLayout();
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
				id : _this.detailIdMgr.eclasstInfoDelId,
			    autoWidth: true,
			    detailCfgs: detailCfgs_1,
			    attachLoad: function(params, cmd) {
			        /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			        //Cmw.print(params);
			        //Cmw.print(cmd);
			    }
			});
			var attachMentFs = this.createAttachMentFs(this);
			this.attachMentArray.push(attachMentFs);
			detailPanel_1.add(attachMentFs);
			var appgridpanel_1 = new Ext.Panel({
			    items: [appgrid_1,detailPanel_1]
			})
			return appgridpanel_1;
		},
		/**
		 * 企业担保资料
		 */
		createeassureInfoDetailPnlId :function(tab,itemId,width,height){
			var self = this;
			var rememberselId = null;
			var structure_1 = [{
				    hidden:true,
				    header: '企业客户ID',
				    name: 'ecustomerId'
				},
				{
				    header: '担保对象',
				    name: 'object'
				},
				{
				    header: '担保金额',
				    name: 'amount',
				    renderer : Render_dataSource.moneyRender
				},
				{
				    header: '起始日期',
				    name: 'asstartDate'
				},
				{
				    header: '解除日期',
				    name: 'asendDate'
				},
				{
				    header: '期限',
				    name: 'term'
				},
				{
				    header: '责任比例',
				    name: 'inverse'
				},
				{
				    header: '责任余额',
				    name: 'asbalance',
				    renderer : Render_dataSource.moneyRender
				},
				{
				    header: '运营情况',
				    name: 'thing'
				}];
				var appgrid_1 = new Ext.ux.grid.AppGrid({
					id : itemId,
				    title: '企业担保表',
				    structure: structure_1,
				    url: './crmEassure_list.action',
				    needPage: false,
				    isLoad: true,
				    height : 150,
				    autoScroll : true,
				    keyField: 'id'
				});
			appgrid_1.addListener("afterrender",function(){
				var panel = Ext.getCmp(itemId);
				self.loadItemPanel(panel);
			});
			appgrid_1.addListener("rowclick",function(appgrid_1,rowIndex,event){
				var selId = appgrid_1.getSelId();
				if(rememberselId!=selId){
					detailPanel_1.reload({id:selId});
					attachMentFs.enable(); 
					rememberselId=selId;
				}else{
					return;
				}
			});
			var htmlArrs_1 = [
				'<tr>' +
					'<th col="object">担保对象</th> <td col="object" >&nbsp;</td>' +
					'<th col="amount">担保金额</th> <td col="amount" >&nbsp;</td>' +
					'<th col="asstartDate">起始日期</th> <td col="asstartDate" >&nbsp;</td>' +
				'</tr>', 
				'<tr>' +
					'<th col="asendDate">解除日期</th> <td col="asendDate" >&nbsp;</td>' +
					'<th col="term">期限（月）</th> <td col="term" >&nbsp;</td>' +
					'<th col="inverse">责任比例（%）</th> <td col="inverse" >&nbsp;</td>' +
				'</tr>', 
				'<tr>' +
					'<th col="asbalance">责任余额</th> <td col="asbalance" >&nbsp;</td>' +
					'<th col="thing">运营情况</th> <td col="thing"  colspan=3 >&nbsp;</td>' +
				'</tr>',
				FORMDIY_DETAIL_KEY,
				'<tr height="50">'+
					'<th col="remark">备注</th> <td col="remark"  colspan="5" >&nbsp;</td>' +
				'</tr>'
				];
			
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 100,
			    /* title : '#TITLE#', */
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './crmEassure_get.action',
//			    prevUrl: '#PREURLCFG#',
//			    nextUrl: '#NEXTURLCFG#',
			    params: {
			        id: -1
			    },
			    formDiyCfg : {
				    	sysId : self.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_EASSURE,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id'/*对应表单中的ID Hidden 值*/
			    	},
			    callback: {
			        sfn: function(jsonData) {
			            /* 可在对页面进行赋值前，对数据进行转换处理 to do .. */
			          jsonData['amount'] = Render_dataSource.moneyRender(jsonData['amount']);
			           jsonData['asbalance'] = Render_dataSource.moneyRender(jsonData['asbalance']);
			             var sysId = _this.params.sysid;
			        	var formId = jsonData.id;
			        	if(!formId){
			        		formId = -1;
			        	}
			        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_27,sysId:sysId,isNotDisenbaled:true};
			        	attachMentFs.reload(params);
			        	detailPanel_1.doLayout();
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
				id: _this.detailIdMgr.eassureInfoDelId,
			    autoWidth: true,
			    detailCfgs: detailCfgs_1,
			    attachLoad: function(params, cmd) {
			        /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			        //Cmw.print(params);
			        //Cmw.print(cmd);
			    }
			});
			var attachMentFs = this.createAttachMentFs(this);
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
					detailPanel_1.reload({id:selId,custType:1});
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
						FORMDIY_DETAIL_KEY,
						'<tr>' +
							'<th col="remark" height=50>资料说明</th> <td col="remark" colspan=5>&nbsp;</td>' +
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
			    url: './crmOtherInfo_get.action',
			    params: {
			        id: -1
			    },
			    formDiyCfg : {
				    	sysId : self.sysId,/*系统ID*/
				    	formdiyCode : FORMDIY_IND.FORMDIY_ECUSROMER_OTHERINFO,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
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
			        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_29,sysId:sysId,isNotDisenbaled:true};
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
			var attachMentFs = this.createAttachMentFs(this);
			this.attachMentArray.push(attachMentFs);
			detailPanel_1.add(attachMentFs);
			var appgridpanel_1 = new Ext.Panel({
			    items: [appgrid_1,detailPanel_1]
			})
			return appgridpanel_1;
		},
		/**
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