/*同心日科技公司Erp 系统 命名空间*/
Ext.namespace("skythink.cmw.crm");
/**
 * 卡片菜单管理
 */
skythink.cmw.crm.CustomerInfoEdit = function(){
	this.init(arguments[0]);
}

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.crm.CustomerInfoEdit,Ext.util.MyObservable,{
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
			getAppGrid : this.getAppGrid,
			getAppGrid2 : this.getAppGrid2,
			getToolBar : this.getToolBar,
			changeSize : this.changeSize,
			createDetailPnl : this.createDetailPnl,
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
		var _this = this;
		var selId  = null;
		var appPanel = new Ext.Panel({autoScroll:true,border : false});
		var tabs = new Ext.TabPanel({
			    activeTab: 0,
			     disabled : true
			});
		this.globalMgr.tabs = tabs;
			tabs.add(_this.createDetailPnl());
			tabs.add(_this.getAppGrid2());
			appPanel.addListener('afterrender',function(){
				var height = _this.globalMgr.detailPanel_1.getHeight();
				var sumHeight = appPanel.getHeight();
				tabs.setHeight(sumHeight-height);
				tabs.doLayout();
			});
			tabs.addListener('tabchange',function(){
				_this.globalMgr.appgrid2.addListener('afterrender',function(){
				 var id = _this.globalMgr.appgrid.getSelId();
					if(!id){
						return;
					}else{
						if(id!=selId){
							selId = id;
							_this.globalMgr.appgrid2.reload({loanInvoceId:selId});
						}else{
							return;
						}
					}
					
				});
			});
			
		appPanel.add({items:[this.getToolBar(),this.getAppGrid(),tabs]});
		return appPanel;
	},
	/**
	 * 刷新方法
	 * @param {} optionType
	 * @param {} data
	 */
	refresh:function(){},
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
	 * 组件销毁方法
	 */
	destroyCmpts : function(){
	},
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var self = this;
		var barItems = [{
			token :"同意放款", 
			text : "同意放款",
			iconCls:'page_add',
			tooltip:"同意放款",
			handler : function(){
				var SelId = self.globalMgr.appgrid.getSelId();
				var auditState = self.globalMgr.appgrid.getCmnVals("auditState").auditState;
				var data = self.globalMgr.appgrid.getCmnVals("payName,payAmount");
				var payAmount = Cmw.getThousandths(data.payAmount);
				var payName = data.payName;
				if(auditState!=2){
					ExtUtil.confirm({msg:"确定对"+payName+"放款"+payAmount+"元？",fn:function(){
						if(arguments && arguments[0] == 'yes'){
							EventManager.get('./fcLoanInvoce_agreeloan.action',
							{params:{id:SelId,auditState:2},sfn: function(jsonData){
								if(self.finishBussCallback) self.finishBussCallback();
								self.globalMgr.appgrid.reload({formId:self.globalMgr.formId,auditState:1});
								self.globalMgr.appgrid2.reload({loanInvoceId:SelId});
								self.globalMgr.detailPanel_1.reload({id:SelId});
							}});
						}
					}});
				}else if(auditState==2){
					 ExtUtil.alert({msg:"已经是放款状态！"});
					 return;
				}
			}
		},{type:"sp"},{
			token :"不同意放款", 
			text : "不同意放款",
			iconCls:'page_edit',
			tooltip:"不同意放款",
			handler : function(){
				var SelId = self.globalMgr.appgrid.getSelId();
				var auditState = self.globalMgr.appgrid.getCmnVals("auditState").auditState;
				var data = self.globalMgr.appgrid.getCmnVals("payName,payAmount");
				var payAmount = Cmw.getThousandths(data.payAmount);
				var payName = data.payName;
				if(auditState == 1){
					ExtUtil.confirm({msg:"确定对"+payName+"的"+payAmount+"元不放款？",fn:function(){
						if(arguments && arguments[0] == 'yes'){
							EventManager.get('./fcLoanInvoce_agreeloan.action',
								{params:{id:SelId,auditState:-1},sfn: function(jsonData){
									self.globalMgr.appgrid.reload({formId:self.globalMgr.formId,auditState :2});
									if(self.globalMgr.appgrid2.rendered){
										self.globalMgr.appgrid2.reload({loanInvoceId:SelId},function(){
											self.finishBussCallback(jsonData);
										});
									}else {
										if(self.globalMgr.detailPanel_1.rendered){
											self.globalMgr.detailPanel_1.reload({id:SelId},function(){
												self.finishBussCallback(jsonData);
											});
										}
									}
								
								}});
						}
					}});
				}else {
					 ExtUtil.alert({msg:"您已经已同意放款，不能进行修改数据！"});
					 return;
				}
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	createDetailPnl : function(){
			var htmlArrs = null;
			var _this = this;
			var sysId = this.globalMgr.sysId;
			var limitLoanSpan =this.globalMgr.limitLoanSpan;
			var htmlArrs_1 = [
						'<tr>' +
							'<th col="account">收款帐号</th> <td col="account" >&nbsp;</td>' +
							'<th col="code">放款单编号</th> <td col="code" >&nbsp;</td>' +
							'<th col="loancode">借款合同号</th> <td col="loancode" >&nbsp;</td>' +
						'</tr>', 
						'<tr>' +
							'<th col="auditState">审批状态</th> <td col="auditState" >&nbsp;</td>' +
							'<th col="state">放款状态</th> <td col="state">&nbsp;</td>' +
							'<th col="payName">收款人名称</th> <td col="payName" >&nbsp;</td>' +
						'</tr>',
						'<tr>' +
							'<th col="regBank">开户行</th> <td col="regBank" >&nbsp;</td>' +
							'<th col="cashier">出纳人员</th> <td col="cashier" >&nbsp;</td>' +
							'<th col="payDate">合约放款日期</th> <td col="payDate" >&nbsp;</td>' +
						'</tr>',
						'<tr>' +
							'<th col="payAmount">放款金额</th> <td col="payAmount">&nbsp;</td>' +
							'<th col="inRate">内部利率</th> <td col="inRate">&nbsp;</td>' +
							'<th col="limitLoan">贷款期限</th> <td id ='+limitLoanSpan+'>&nbsp;</td>' +
						'</tr>',
						'<tr>' +
							'<th col="mrate">管理费率</th> <td col="mrate">&nbsp;</td>' +
//							'<th col="prate">放款手续费率</th> <td col="prate">&nbsp;</td>' +
							'<th col="unAmount">未放款余额</th> <td col="unAmount" colspan = 3 >&nbsp;</td>' +
						'</tr>',
						'<tr>' +
							'<th col="ysRatMonth">预收利息月数</th> <td col="ysRatMonth">&nbsp;</td>' +
							'<th col="ysRat">预收利息金额</th> <td col="ysRat"  colspan  = 3>&nbsp;</td>' +
						'</tr>',
						'<tr>' +
							'<th col="ysMatMonth">预收管理费月数</th> <td col="ysMatMonth">&nbsp;</td>' +
							'<th col="ysMat">预收管理费金额</th> <td col="ysMat"  colspan  = 3>&nbsp;</td>' +
						'</tr>',
						FORMDIY_DETAIL_KEY,
						'<tr height="50">' +
							'<th col="remark">备注</th> <td col="remark"  colspan="5">&nbsp;</td>' +
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
			    url: './fcLoanInvoce_get.action',
			    params: {
			        id: -1
			    },
			    formDiyCfg : {sysId:sysId,formdiyCode:FORMDIY_IND.FORMDIY_LOANINVOCE,formIdName:'id'},
			    callback: {
			        sfn: function(jsonData) {
			            /* 可在对页面进行赋值前，对数据进行转换处理 to do .. */
			            if(jsonData==-1){
//			        		detailPanel_1.hide();
			        		return ;
			        	}else{
			        		detailPanel_1.show();
			        	}
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
			        	var limitLoans= (arr.length > 0) ? arr.join("") : "";
			        	if(parseFloat(jsonData["ysMat"])){
							var ysMat = jsonData["ysMat"];
			        		jsonData["ysMat"] =  Cmw.getThousandths(ysMat)+'元&nbsp;&nbsp;<span style="color:red;font-weight:bold;">(大写：'+Cmw.cmycurd(ysMat)+')</span>';
			        	}else {
			        		jsonData["ysMat"] = "";
			        	}
			        	if(parseFloat(jsonData["ysRat"])){
			        		var ysRat = jsonData["ysRat"];
			        		jsonData["ysRat"] =Cmw.getThousandths(ysRat)+'元&nbsp;&nbsp;<span style="color:red;font-weight:bold;">(大写：'+Cmw.cmycurd(ysRat)+')</span>';
			        	}else {
			        		jsonData["ysRat"] = "";
			        	}
			        	Ext.get(limitLoanSpan).update(limitLoans);
			        	jsonData["mrate"]  = jsonData["mrate"] ? jsonData["mrate"] +'%' : '0.00%';
			        	jsonData["inRate"]  = jsonData["inRate"] ? jsonData["inRate"] +'%' : '0.00%';
			        	
			        	jsonData["prate"]  = jsonData["prate"] ? jsonData["prate"] +'%' : '0.00%';
			        	switch(jsonData["auditState"]){
			        		case -1:
					            jsonData["auditState"] = '驳回';
					            break;
					        case 0:
					            jsonData["auditState"] = '待提交';
					            break;
					        case 1:
					            jsonData["auditState"] = '审批中';
					            break;
					        case 2:
					            jsonData["auditState"] = '<span style="color:green;font-weight:bold;" >审批通过</span>';
					            break;
			        	}
			        	switch(jsonData["state"]){
					        case 0:
					            jsonData["state"] = '待放款';
					            break;
					        case 1:
					            jsonData["state"] = '<span style="color:green;font-weight:bold;">已放款</span>';
					            break;
			        	}
			        	var payAmount =jsonData["payAmount"];
			        	if(payAmount!=null){
			        		jsonData["payAmount"] =  Cmw.getThousandths(payAmount)+'元&nbsp;&nbsp;<span style="color:red;font-weight:bold;">(大写：'+Cmw.cmycurd(payAmount)+')</span>';
			        	}
			        	var unAmount = jsonData["unAmount"];
			        	if(unAmount!=null){
			        		jsonData["unAmount"] =  Cmw.getThousandths(unAmount)+'元&nbsp;&nbsp;<span style="color:red;font-weight:bold;">(大写：'+Cmw.cmycurd(unAmount)+')</span>';
			        	}
			        	jsonData["realDate"]=(jsonData["realDate"]=='')? '没有通过审批' : jsonData["realDate"] ;
			        	var cashier =jsonData["cashier"];
			        	 var cas = [];
			        	 cas = cashier.split("##");
			        	 jsonData["cashier"] = '<span style="color:blue;font-weight:bold;">'+cas[1]+'</span>';
			        }
			    }
			}];
			var attachLoad = function(params, cmd) {
			    /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			    //Cmw.print(params);
			    //Cmw.print(cmd);
			}
			var detailPanel_1 = new Ext.ux.panel.DetailPanel({
				title :'放款单详情',
			    autoWidth: 780,
			    detailCfgs: detailCfgs_1,
			    attachLoad: function(params, cmd) {
			        /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			        //Cmw.print(params);
			        //Cmw.print(cmd);
			    }
			});
			_this.globalMgr.detailPanel_1 = detailPanel_1;
			return detailPanel_1;
		},
		getAppGrid : function(){
			var _this = this;
			var a=0.00;
			var wd = 100;
			var structure_1 = [{
			    header: '放款单id',
			    hidden :true,
			    name: 'id'
			},
			{
			    header: '贷款申请单ID',
			     hidden :true,
			    name: 'formId'
			},
			{
			    header: '借款合同ID',
			     hidden :true,
			    name: 'contractId'
			},
			{
			    header: '审批状态',
			    name: 'auditState',
			    renderer : function(val){
			    	switch (val) {
			        case '-1':
			            val = '驳回';
			            break;
			        case '0':
			            val = '待提交';
			            break;
			        case '1':
			            val = '待审批';
			            break;
			        case '2':
			            val = '审批通过';
			            break;
			        }
			        return val;
			    }
			},
			{
			    header: '放款单编号',
			    name: 'code'
			},
			{
			    header: '借款合同编号',
			    name: 'ccode'
			},
			{
			    header: '贷款金额',
			    name: 'appAmount',
			    renderer: function(val) {
			       	 val = Cmw.getThousandths(val);
			        return val;
			    }
			},
			{
			    header: '收款人名称',
			    name: 'payName'
			},
			{
			    header: '开户行',
			    name: 'regBank'
			},
			{
			    header: '收款帐号',
			    name: 'account'
			},
			{
			    header: '放款金额',
			    name: 'payAmount',
			    renderer: function(val) {
			       	val = Cmw.getThousandths(val);
			        return val;
			    }
			},
			{
			    header: '未放款余额',
			    name: 'unAmount',
			 	 renderer: function(val) {
			       	val = Cmw.getThousandths(val);
			        return val;
			    }
			},
			{
			    header: '放款手续费率',
			    name: 'prate',
			    hidden : true,
			    renderer: function(val) {
	       			return (val) ? val+'%' : '';
			    }
			},
			{
			    header: '出纳人员',
			    name: 'cashier'
			},
			{
			    header: '合约放款日期',
			    name: 'payDate',
			    width : 120,
			    renderer : function(val){
			    	return (val=='')? '没有通过审批' : val;
			    }
			},{
				header: '预收利息月数',
		    	width : 130,
		    	name: 'ysRatMonth',
		    	renderer : function(val,m){
		    		m.css = 'x-grid-back-ysRatMonth';;
		    		if(val){
		    			val = val+'个月'
		    			return val;
		    		}
		    	}
			},{
				header: '预收利息',
		    	width : 130,
		    	name: 'ysRat',
		    	renderer : function(val,m){
		    		m.css = 'x-grid-back-ysRat';
		    		if(val){
		    			val =val.replaceAll(",","");
		    			val = Cmw.getThousandths(val)+'元'
		    			return val;
		    		}
		    		
		    	}
			},{
				header: '预收管理费月数',
		    	width : 130,
		    	name: 'ysMatMonth',
		    	renderer : function(val,m){
		    		m.css = 'x-grid-back-ysMatMonth';
		    		if(val){
		    			val = val+'个月'
		    			return val;
		    		}
		    	}
			},{
				header: '预收管理费',
		    	width : 130,
		    	name: 'ysMat', 
		    	renderer : function(val,m){
		    		m.css = 'x-grid-back-ysMat';
		    		if(val){
		    			val =val.replaceAll(",","");
		    			val = Cmw.getThousandths(val)+'元'
		    			return val;
		    		}
		    		
		    	}
			}
			];
			var custType = this.params.custType;
			var formId = _this.globalMgr.formId;
			var custType=custType;
			var auditState =  1
			var appgrid = new Ext.ux.grid.AppGrid({
			    structure: structure_1,
			    url: './fcLoanInvoce_list.action?auditState=1',
			    params :{formId :formId,custType:custType,auditState : auditState},
			    needPage: false,
			    isLoad: true,
			    height : 120,
			    keyField: 'id'
			});
				appgrid.addListener("rowclick",function(appgrid,rowIndex,event){
		   var record = appgrid.getStore().getAt(rowIndex);
		   if(record==null){
		   	return;
		   }
			var SelId = appgrid.getSelId();
			_this.globalMgr.detailPanel_1.reload({id:SelId});
			_this.globalMgr.tabs.enable(); 
//			_this.globalMgr.detailPanel_1.show();
//			_this.globalMgr.detailPanel_1.reload({id:SelId});
		});
		_this.globalMgr.appgrid=appgrid;
			_this.globalMgr.appgrid = appgrid;
			return appgrid;
		},
		getAppGrid2: function(){
		var _this = this;
			var structure_1 = [{
		    header: '期数',
		    name: 'phases'
		},
		{
		    header: '放款单ID',
		     hidden :true,
		    name: 'loanInvoceId'
		},
		{
		    header: '应还款日期',
		    name: 'xpayDate'
		},
		{
		    header: '利息',
		    name: 'interest',
		    renderer: function(val) {
		        val = Cmw.getThousandths(val);
		        return val;
		    }
		},
		{
		    header: '本金',
		    name: 'principal',
		    renderer: function(val) {
		        val = Cmw.getThousandths(val);
		        return val;
		    }
		},
		{
		    header: '管理费',
		    name: 'mgrAmount',
		    renderer: function(val) {
		        val = Cmw.getThousandths(val);
		        return val;
		    }
		},
		{
		    header: '应付合计',
		    name: 'totalAmount',
		    renderer: function(val) {
		        val = Cmw.getThousandths(val);
		        return val;
		    }
		},
		{
		    header: '剩余本金',
		    name: 'reprincipal',
		    renderer: function(val) {
		        val = Cmw.getThousandths(val);
		        return val;
		    }
		}];
		var appgrid = new Ext.ux.grid.AppGrid({
			title:'还款计划表',
		    structure: structure_1,
		    url: './fcChildPlan_list.action',
		    needPage: true,
		    isLoad: false,
		    height : 220,
		    keyField: 'id'
		});
		_this.globalMgr.appgrid2 = appgrid;
		 return appgrid;
	},
	globalMgr:{
			 limitLoanSpan : Ext.id(null,"limitLoan"),
			sysId : this.params.sysid,
			tabs : null,
			custType : this.params.custType,
			detailPanel_1 :null,
			appgrid2 : null,
			formId :this.params.applyId,
			custType:_this.params.custType,
			appgrid : null
		}
});
