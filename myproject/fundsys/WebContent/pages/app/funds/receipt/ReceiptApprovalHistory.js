/**
 * 九洲科技公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 基础数据 UI smartplatform_auto 2012-08-10 09:48:16
 */ 
cmw.skythink.ReceiptApprovalHistory = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.ReceiptApprovalHistory,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGContainerView({
			querytitle : '汇票收条审批历史',//重写主题
			tab : tab,
			params : params,
			hasTopSys : false,
			getQueryFrm : this.getQueryFrm,
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			globalMgr : this.globalMgr,
			refresh : this.refresh,
			appCmpts : {}
		});
	},
	/**
	 * 获取查询Form 表单
	 */
	getQueryFrm : function(){
		var self =this;
		var wd = 175;
		//1.票号
		var txt_rnum = FormUtil.getTxtField({fieldLabel : '票号',name:'rnum',width : wd , maxLength : 30});
		//2.出票人
		var txt_outMan = FormUtil.getTxtField({fieldLabel : '出票人',name:'outMan',width:wd,maxLength : 80});
		/**
		 * 3.计算时间 txt_outDate、txt_endDate、txt_outAndEndDate
		 */
		var txt_outDate = FormUtil.getDateField({name : 'outDate', width : 75});//出票日期
		var txt_endDate = FormUtil.getDateField({name : 'endaDate' , width : 75});//到票日期
		var txt_outAndEndDate = FormUtil.getMyCompositeField({
				itemNames : 'outDate,endDate',
				fieldLabel : '出票日期',
				name : 'time',
				sigins : null,
				width : wd,
				items :[txt_outDate, {xtype : 'displayfield',value : '至'},txt_endDate ]
			});
		
		/**
		 * 4.计算金额    txt_operational、txt_amountText、txt_amount
		 */
		var txt_operational  = FormUtil.getEqOpLCbox({name:'operational'});
		var txt_amountText = FormUtil.getTxtField({name :　'amount',width : 100});
		var txt_amount = FormUtil.getMyCompositeField({
				fieldLabel : '金额',
				sigins:null,
				name : 'amount',
				width : wd,
				items : [txt_operational,txt_amountText]
			});
		//5.收款人账户名
		var txt_rtacname = FormUtil.getTxtField({fieldLabel : '收款人账户名',name:'rtacname',width:wd,maxLength : 80});
 		//收款人账号
 		var txt_rtaccount = FormUtil.getTxtField({fieldLabel : '收款人账号',name:'rtaccount',width:wd,maxLength : 30});
		var layout_fields = [{cmns:'THREE',fields:[txt_rnum,txt_outMan,txt_outAndEndDate,txt_amount,txt_rtacname,txt_rtaccount]}]
		var queryFrm = FormUtil.createLayoutFrm(null,layout_fields);
		
		return queryFrm;
	},
	
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var self = this;
		var toolBar = null;
		var barItems = [{
			token : '查询',
			text : Btn_Cfgs.QUERY_BTN_TXT,
			iconCls:'page_query',
			tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.query(self);
			}
		},{
			token : '重置',
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls:'page_reset',
			tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
			handler : function(){
				self.queryFrm.reset();
			}
		},{/*详情*/
			token : '查看详情',
			//text : Btn_Cfgs.DETAIL_BTN_TXT,
			text : '查看详情',
			iconCls:Btn_Cfgs.DETAIL_CLS,
			tooltip:Btn_Cfgs.DETAIL_TIP_BTN_TXT,
			handler : function(){
				var id = self.appgrid.getSelId();
					if (!id)
						return;
				self.globalMgr.winEdit.dataDetail({key : this.token,self : self,_appgrid : self.globalMgr._appgrid,orgtype : 3 ,id:id});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		toolBar.hideButtons(Btn_Cfgs.EXPORT_IMPORT_BTN_TXT);
		return toolBar;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
		var self = this;
			var structure_1 = [{header: 'id', hidden :true,name: 'id'},
			{header : '可用标识',name : 'isenabled',width:60, renderer :Render_dataSource.isenabledRender},
			{header: '状态',name: 'status' ,
		    	renderer: function(val) {
		       		switch (val) {
		        		case "0":
			            	val = '待提交';
			            	break;
		            	case "1":
			            	val = '审批中';
			            	break;
		            	case "2":
			            	val = '审批通过';
			            	break;
			            case "3":
			            	val = '审批不通过';
			            	break;
			        }
		        	return val;
		}},
			{header: '票号',name: 'rnum'},
			{header: '出票人',name: 'outMan'},
			{header: '付款行',name: 'pbank'},
			{header: '出票日期',name: 'outDate'},
		    {header: '到票日期',name: 'endDate'},
			{header: '金额',name: 'amount',
		   		 renderer: function(val) {
		       		switch (val) {
		        		case 0:
		            		val = '0';
		            		break;
		       		}
		        	return val;
		    }},
			{header: '收款人账户名',name: 'rtacname'},
			{header: '收款人账号',name: 'rtaccount'},
			{header: '收款人开户行', name: 'rtbank'},
			{header: '回款收条',name: 'isbreceipt',
				renderer: function(val) {
					switch (val) {
		        		case "1":
		          	 		val = "未生成";
		          	 		break;
		          	 	case "2":
		          	 		val = "已生成";
		          	 		break;
		       		 	}
		       			return val;
		   			 }
		  },{header: '查询申请单',name: 'isappInvoce',
		    	renderer: function(val) {
		       	 	switch (val) {
		        		case "1":
		          	 		val = "未生成";
		          	 		break;
		          	 	case "2":
		          	 		val = "已生成";
		          	 		break;
		       		 	}
		       			return val;
		   			 }
		  },{header: '生成承诺书', name: 'isabook',
		   		renderer: function(val) {
		        	switch (val) {
		        		case "1":
		          	 		val = '未生成承诺书';
		          	 		break;
		          	 	case "2":
		          	 		val = '已生成承诺书';
		          	 		break;
		       		 	}
		       			return val;
		   			 }
		  },{header: '收条签收日期',name: 'recetDate'},
			{header: '出票人账号',name: 'omaccount'},
			{header: '汇票数量(单位:张)',name: 'rcount',
		   		renderer: function(val) {
		       		switch (val) {
		        		case 0:
		            	val = '0';
		            	break;
			        }
		        	return val;
		    		}
		  },{header: '收条接收人',name: 'reman'},
		    {header: '客户姓名',name: 'name'},
		    {header: '子业务流程ID',name: 'breed',hidden: true},
		    {header: '流程实例ID',name: 'procId', hidden: true}];
				
			var appgrid = new Ext.ux.grid.AppGrid({
				title : '汇票审批管理',
				tbar :self.toolBar,
			    structure: structure_1,
			    url: './fuReceipt_auditHistory.action',
			    needPage: true,
			    isLoad: true,
			    autoScroll:true,
			    keyField: 'id'
			});
			self.globalMgr._appgrid = appgrid;
		return appgrid;
	},
	refresh:function(optionType,data){
		var activeKey = this.globalMgr.activeKey;
		this.globalMgr.query(this);
		this.globalMgr.activeKey = null;
	},
	globalMgr : {
		_appgrid : null,
		activeKey : null,
		appform : null,
		winEdit : {
			/**
			 * 查看详情
			 */
			dataDetail : function(parentCfg){
				var self = parentCfg.self;
				//获取  定义的数据
				var tokenMgr = self.globalMgr.tokenMgr;
				//获取当前的按钮
				var winkey = parentCfg.key;
				//编辑时把id传过去
				if(parentCfg.id){ 
					 var id= parentCfg.id;
					 parentCfg.id = id;
				}
				self.globalMgr.activeKey = winkey;
				parentCfg.parent = self;
				//判断缓存中是否有数据
				if(self.appCmpts[winkey]){
					self.appCmpts[winkey].show(parentCfg);
				}else {
					var winModule=null;
					switch(winkey){
						case '查看详情' : {
							winModule = "ReceiptEdit";
							break;
						}
					}
					Cmw.importPackage('pages/app/funds/receipt/' + winModule,function(module) {
							self.appCmpts[winkey] = module.viewUI;
							self.appCmpts[winkey].show(parentCfg);
					});
				}
			}
		},
		/**
		 * 获得查询的参数
		 * @param {} _this
		 * @return {}
		 */
		getQparams : function(_this){
			var params = _this.queryFrm.getValues() || {};
			/*-- 附加桌面传递的参数  CODE START --*/
			if(_this.params && _this.params[DESK_MOD_TAG_QUERYPARAMS_KEY]){/*DESK_MOD_TAG_QUERYPARAMS_KEY :用来标识是由点击桌面模块中的数据跳转时所带的查询参数 KE*/
			 	var deskParams = _this.params[DESK_MOD_TAG_QUERYPARAMS_KEY];
			 	if(deskParams){
			 		Ext.applyIf(params,deskParams);
			 		 _this.params[DESK_MOD_TAG_QUERYPARAMS_KEY] = null;
			 	}
			}/*-- 附加桌面传递的参数  CODE END --*/
			return params;
		},
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params = _this.globalMgr.getQparams(_this);
			EventManager.query(_this.appgrid,params);
			}
		}
	});


