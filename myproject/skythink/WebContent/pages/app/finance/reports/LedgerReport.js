Ext.namespace('skythink.cmw.report');
/**
 * 客户贷款台账明细资料报表
 */
skythink.cmw.report.LedgerReport = function(){
	this.init(arguments[0]);
}
	
Ext.extend(skythink.cmw.report.LedgerReport,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsPanelView({
			tab : tab,
			params : params,
			getToolBar : this.getToolBar,
			getAppCmpt : this.getAppCmpt,
			globalMgr : this.globalMgr,
			changeSize : this.changeSize,
			destroyCmpts : this.destroyCmpts,
			refresh : this.refresh
		});
	},
	/**
	 * 组装面板
	 */
	getAppCmpt : function(){
		var _this = this;
		var reportPanelId = this.globalMgr.reportPanelId;
		var reportPanel = new Ext.Panel({
			tbar : this.toolBar,
			html:"<div id='"+reportPanelId+"' class='ledgerDiv'></div>"
		});
		return reportPanel;
	},
	/**
	 * 设置面板得的大小
	 */
	changeSize : function(whArr){
		var h = whArr[1];
		if(h>0) h-=8;
		this.appPanel.setHeight(h);
	},
	/**
	 * 创建工具栏
	 */
	getToolBar : function(){
		var _this = this;
		var barItems = [{/*重置*/
				token : '选择客户',
				text : Btn_Cfgs.LEDGERREPORT_SELECT_BTN_TXT,
				iconCls:'page_select',
				tooltip:Btn_Cfgs.LEDGERREPORT_SELECT_TIP_TXT,
				handler : function(){
					_this.globalMgr.showCustomerDialog(this,_this);
				}
			},{/*导出*/
				token : '导出',
				text : Btn_Cfgs.EXPORT_BTN_TXT,
				iconCls:Btn_Cfgs.EXPORT_CLS,
				tooltip:Btn_Cfgs.EXPORT_TIP_BTN_TXT,
				handler : function(){
				_this.globalMgr.doExport(_this);
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 * 刷新
	 */
	refresh : function(){
		
	},
	
	globalMgr : {
		reportPanelId :  Ext.id(null,"reportPanel"),//客户贷款账台明细资料Div Id
		planDivId : Ext.id(null,"planDivId"),//还款计划表Div Id
		extDivId : Ext.id(null,"extId"), // 贷款展期借款Div id
		amountRecordsDiv : Ext.id(null,"amountRecordsDiv"),//实收金额div id
		customerDialog : null,
		/**
		 * 显示数据
		 */
		showCustomerDialog : function(el,_this){
			var parentCfg = {parent:el,callback:function(id){
				_this.globalMgr.loanContactLoad(_this,id);
			}};
			if(_this.globalMgr.customerDialog){
				_this.globalMgr.customerDialog.show(parentCfg);
			}else{
				Cmw.importPackage('pages/app/dialogbox/LedgerDialogBox',function(module) {
				 	_this.globalMgr.customerDialog = module.DialogBox;
				 	_this.globalMgr.customerDialog.show(parentCfg);
		  		});
			}
		},
		/**
		 * 计算利息
		 * @param {} _this
		 * @param {} data
		 * @return {}
		 */
		rateCalculate : function(_this,data){
			var yrate = "";
			var mrate = "";
			var drate = "";
			var rateCalculateType = "";
			var payDay = "";
			if(data.payDay){
				payDay = data.payDay+"日";
			}
			if(data){
				var rateType = data.rateType;
				if(rateType == 1 ){
					rateCalculateType = "按月";
					mrate = (data.rate).toFixed(6) +"%"
					yrate = (data.rate*12).toFixed(6)+"%";
					drate = (data.rate/30).toFixed(6)+"%";
				}else if(rateType == 1){
					rateCalculateType = "按日";
					mrate = (data.rate*30).toFixed(6) +"%"
					yrate = (data.rate*360).toFixed(6)+"%";
					drate = (data.rate).toFixed(6)+"%";
				}else {
					rateCalculateType = "按年";
					mrate = (data.rate/12).toFixed(6) +"%"
					yrate = (data.rate).toFixed(6)+"%";
					drate = (data.rate/360).toFixed(6)+"%";
				}
			}
			var rateArray = [yrate,mrate,drate,rateCalculateType];
			return rateArray;
		},
		/**
		 * 创建table面板
		 * @param {} contractId
		 */
		createTableCmpt : function(_this,data){
			var rateArray = _this.globalMgr.rateCalculate(_this,data);
			var contractId = data.contractId;
			var htmlArray = [
				'<table cellpadding="0" cellspacing="0" class="ledger"  >',
			  '<tr>',
			    '<td colspan="16" bgcolor="#FF9900"  align="center" height ="30" style="fontSize:17px;"><strong>客户贷款台账明细资料</strong></td>',
			  '</tr>',
			  '<tr>',
			    '<td width="50" height="25" bgcolor="#EFF0F2">客户名称</td>',
			    '<td width="121">'+data.custName+'</td>',
			    '<td width="101" bgcolor="#EFF0F2">客户开户银行</td>',
			    '<td width="152">'+data.borBank+'</td>',
			    '<td width="50" bgcolor="#EFF0F2">银行帐号</td>',
			    '<td colspan="2">'+data.borAccount+'</td>',
			    '<td width="80" bgcolor="#EFF0F2">项目介绍人</td>',
			    '<td colspan="2" width="82">'+data.referrals+'</td>',
			    '<td width="60" bgcolor="#EFF0F2">项目经办人</td>',
			    '<td colspan="2">'+data.managerName+'</td>',
			    '<td bgcolor="#EFF0F2">台账编号</td>',
			    '<td colspan="2">'+data.applyCode+'</td>',
			  '</tr>',
			  '<tr>',
			    '<td bgcolor="#EFF0F2">贷款金额</td>',
			    '<td>'+Cmw.getThousandths(data.appAmount)+'</td>',
			    '<td bgcolor="#EFF0F2">贷款合同编号</td>',
			    '<td>'+data.contractCode+'</td>',
			    '<td bgcolor="#EFF0F2">贷款发放日</td>',
			    '<td width="70">'+data.payDate+'</td>',
			    '<td width="100" bgcolor="#EFF0F2">贷款期限(月)</td>',
			    '<td>'+data.loanLimit+'</td>',
			    '<td width="100" bgcolor="#EFF0F2">贷款归还到期日</td>',
			    '<td width="70">'+data.endDate+'</td>',
			    '<td bgcolor="#EFF0F2">借款年利率</td>',
			    '<td width="90">'+rateArray[0]+'</td>',
			    '<td width="66" bgcolor="#EFF0F2">月利率</td>',
			    '<td width="90">'+rateArray[1]+'</td>',
			    '<td width="55" bgcolor="#EFF0F2">日利率</td>',
			    '<td width="90">'+rateArray[2]+'</td>',
			  '</tr>',
			  '<tr height = "40">',
			    '<td colspan="6" rowspan="3" >',
			    	'<div  id="'+_this.globalMgr.planDivId+'" style="width : 100%;height : 100%;overFlow: auto;padding : 0 "></div>',
    			'</td>',
			    '<td bgcolor="#EFF0F2">结息方式</td>',
			    '<td bgcolor="#EFF0F2">利息计算</td>',
			    '<td rowspan="2" bgcolor="#EFF0F2" >借款合同还款条款</td>',
			    '<td colspan="7" rowspan="2">'+data.clause+'</td>',
			  '</tr>',
			  '<tr>',
  					'<td>'+data.payDay+'</td>',
  					'<td>'+rateArray[3]+'</td>',
				'</tr>',
			  '<tr>',
					'<td colspan="10">',
						'<div  id="'+_this.globalMgr.extDivId+'" style="width : 100%;height : 100%;overFlow: auto; padding : 0"></div>',
					'</td>',
			   '</tr>',
			   '<tr>',
					'<td colspan="16">',
						'<div  id="'+_this.globalMgr.amountRecordsDiv+'" style="width : 100%;height : 100%;overFlow: auto;padding : 0 "></div>',
					'</td>',
			   '</tr>',
			'</table>'
			].join("");
			Ext.get(_this.globalMgr.reportPanelId).update(htmlArray,true,function(){
				_this.globalMgr.planLoad(_this,contractId);
				_this.globalMgr.extContractLoad(_this,contractId);
				_this.globalMgr.amountRecordsLoad(_this,contractId);
			});
		},
		/**
		 * 加载借款合同数据
		 * @param {} _this
		 */
		loanContactLoad : function(_this,id){
			Cmw.mask(_this.appPanel,"正在飞速加载数据请稍等...");
			EventManager.get('./fcLoanContract_listContracts.action',{params : {contractId:id},sfn:function(json_data){
				if(null == json_data.totalSize || json_data.list.length == 0) {
					ExtUtil.alert({msg:"没有数据可加载！"});
					return;
				}
				var list = json_data.list;
				var data = list[0];
				_this.globalMgr.createTableCmpt(_this,data);
			},ffn : function(json_data){
				ExtUtil.alert({msg : "数据加载加载失败！"});
				return;
			}});
		},
		/**
		 * 加载还款计划表数据
		 * @param {} _this
		 * contractId : 借款合同id
		 */
		planLoad : function(_this,contractId){
			EventManager.get('./fcChildPlan_list.action',{params : {loanInvoceId:contractId},sfn:function(json_data){
				var planDiv = Ext.get(_this.globalMgr.planDivId);
				var tableHtml = [
					'<table style="width : 100%;padding : 0;border :0px;">',
					  '<tr>',
					    '<td bgcolor="#EFF0F2">期数</td>',
					    '<td bgcolor="#EFF0F2">还款日</td>',
					    '<td bgcolor="#EFF0F2">归还贷款金额</td>',
					    '<td bgcolor="#EFF0F2">贷款余额</td>',
					    '<td bgcolor="#EFF0F2">月度应收利息</td>',
					    '<td bgcolor="#EFF0F2">管理费</td>',
					  '</tr>'
				];
				if(null == json_data.totalSize || json_data.list.length == 0) {
					for(var i=0; i<14; i++){
						tableHtml.push([
									'<tr>',
								 	'<td>&nbsp;</td>',
								 	'<td>&nbsp;</td>',
								 	'<td>&nbsp;</td>',
								 	'<td>&nbsp;</td>',
								 	'<td>&nbsp;</td>',
								 	'<td>&nbsp;</td>',
								 	'</tr>'
								].join(" "));
					}
				}else{
					var list = json_data.list;
					for(var i=0; i<json_data.totalSize; i++){
						data = list[i];
						tableHtml.push([
								 '<tr>',
								 '<td>'+data.phases+'</td>',
								 '<td>'+data.xpayDate+'</td>',
								 '<td>'+Cmw.getThousandths(data.principal)+'</td>',
								 '<td>'+Cmw.getThousandths(data.reprincipal)+'</td>',
								 '<td>'+Cmw.getThousandths(data.interest)+'</td>',
								 '<td>'+Cmw.getThousandths(data.mgrAmount)+'</td>',
								 '</tr>'
								].join(" "));
					}
				}
				tableHtml.push(['</table>'].join(""));
				planDiv.update(tableHtml.join(""));
			},ffn : function(json_data){
				ExtUtil.alert({msg : "数据加载加载失败！"});
				return;
			}});
		},
		/**
		 * 计算利息总额
		 * @param {} json_data
		 */
		rateTotal : function(planData){
			var rateTotal = 0.00;
			for(var i=0; i<planData.length; i++){
				var interest = planData[i].interest;
				rateTotal = parseFloat(interest)+parseFloat(rateTotal);
			}
			return Cmw.getThousandths(rateTotal);
		},
		/**
		 * 加载展期协议数据
		 * @param {} _this
		 * contractId : 借款合同id
		 */
		extContractLoad : function(_this,contractId){
			var tableHtml = [];
			var count = 0;
			EventManager.get('./fcExtContract_ledgerlist.action',{params:{contractId:contractId},sfn:function(jsonData){
				if(null == jsonData.totalSize || jsonData.list.length == 0) {
					var extDiv = Ext.get(_this.globalMgr.extDivId);
					//ExtUtil.alert({msg:"该客户没有展期协议！"});
					extDiv.update("该客户没有展期协议！");
					Cmw.unmask(_this.appPanel);
					return;
				}
				var list = jsonData.list;
				for(var i=0 ,size = jsonData.totalSize; size>i; size--){
					var dataSize = size;
					count = count+1;
					var data = list[size-1];
					var estartDate = new Date(data.estartDate).getDay()-1+ "日";
					var otherRemark = "";
					var remark = data.otherRemark;
					if(remark) otherRemark = remark;
					var signDate = data.signDate;
					var eendDate = data.eendDate;
					var estartDate = data.estartDate+"起 <br/>至"+eendDate;
					var jxDate = new Date(data.estartDate).getDate()+'日';
					var code = data.code;
					var rateArray = _this.globalMgr.rateCalculate(_this,data);
					if(data.extPlanList){
						var extPlanList = data.extPlanList;
						var rateTotal = _this.globalMgr.rateTotal(extPlanList);
						tableHtml.push([
									'<table style="width : 100%;padding : 0;border :0px;">',
									'<tr>',
										 '<td colspan="10" bgcolor="#EFF0F2"><div align="center">借款展期借款补充协议'+count+'</div></td>',
									'</tr>',
									'<tr>',
									 	'<td bgcolor="#EFF0F2">展期还款日期</td>',
									    '<td bgcolor="#EFF0F2">月利率变更</td>',
									    '<td bgcolor="#EFF0F2">日利率变更</td>',
									    '<td bgcolor="#EFF0F2">结息方式</td>',
									    '<td bgcolor="#EFF0F2">利息总和</td>',
									    '<td bgcolor="#EFF0F2">补充协议生效日</td>',
									    '<td bgcolor="#EFF0F2">时间贷款期限</td>',
									    '<td bgcolor="#EFF0F2">停止计息日</td>',
									    '<td colspan="2" bgcolor="#EFF0F2">补充协议文号及编号</td>',
									'</tr>',
									'<tr>',
									    '<td>'+data.estartDate+'</td>',
									    '<td>'+rateArray[1]+'</td>',
									    '<td>'+rateArray[2]+'</td>',
									    '<td>'+jxDate+'</td>',
									    '<td>'+rateTotal+'</td>',
									    '<td>'+data.signDate+'</td>',
									    '<td>'+estartDate+'</td>',
									     '<td>'+eendDate+'</td>',
									    '<td colspan="2" rowspan="2">'+code+'</td>',
									  '</tr>',
									  '<tr>',
									     	'<td bgcolor="#EFF0F2">期数</td>',
										    '<td bgcolor="#EFF0F2">还款日</td>',
										    '<td bgcolor="#EFF0F2">归还贷款金额</td>',
										    '<td bgcolor="#EFF0F2">贷款余额</td>',
										    '<td bgcolor="#EFF0F2">月度应收利息</td>',
										    '<td bgcolor="#EFF0F2">管理费</td>',
										    '<td>&nbsp;</td>',
										     '<td>&nbsp;</td>',
									  '</tr>',
									  _this.globalMgr.addRow(extPlanList)+
									  '<tr width = "20">',
									    '<td rowspan="2" bgcolor="#EFF0F2">补充协议其他事项</td>',
									    '<td colspan="9" rowspan="2">'+otherRemark+'</td>',
									  '</tr>'
										].join(" "));
											var extDiv = Ext.get(_this.globalMgr.extDivId);
											tableHtml.push(['</table>'].join(""));
											extDiv.update(tableHtml.join(""));
											Cmw.unmask(_this.appPanel);
					}
					}
				}
			});
		},
		/**
		 * 添加展期计划表格
		 * @param {} data
		 */
		addRow : function(planData){
			var dataArray = [];
			if(planData.length>0){
				for(var i=planData.length; i>0; i--){
					data = planData[i-1];
					var xpayDate = data.xpayDate;
					xpayDate = new Date(xpayDate).format('Y-m-d');
					dataArray.push([
						 '<tr>',
							 '<td>'+data.phases+'</td>',
							 '<td>'+xpayDate+'</td>',
							 '<td>'+Cmw.getThousandths(data.principal)+'</td>',
							 '<td>'+Cmw.getThousandths(data.reprincipal)+'</td>',
							 '<td>'+Cmw.getThousandths(data.interest)+'</td>',
							 '<td>'+Cmw.getThousandths(data.mgrAmount)+'</td>',
							 '<td>&nbsp;</td>',
							 '<td>&nbsp;</td>',
							  '<td>&nbsp;</td>',
							 '<td>&nbsp;</td>',
						 '</tr>'
					].join(""));
				}
			}else{
				dataArray.push([
					'<tr>',
							 '<td>&nbsp;</td>',
							 '<td>&nbsp;</td>',
							 '<td>&nbsp;</td>',
							 '<td>&nbsp;</td>',
							 '<td>&nbsp;</td>',
							 '<td>&nbsp;</td>',
							 '<td>&nbsp;</td>',
							 '<td>&nbsp;</td>',
							  '<td>&nbsp;</td>',
							 '<td>&nbsp;</td>',
						 '</tr>'
				].join(""));
			}
			
			return dataArray.join("");
		},
		/**
		 * 加载实收金额表中的数据
		 * @param {} _this
		 * @param {} contractId
		 */
		amountRecordsLoad:function(_this,contractId){
			EventManager.get('./fcAmountRecords_getLedger.action',{params:{contractId:contractId},sfn:function(json_data){
				var amountRecordsDiv = Ext.get(_this.globalMgr.amountRecordsDiv);
				var tableHtml = [
					'<table style="width : 100%;padding : 0;border :0px; ">',
					'<tr>',
						'<td colspan="7" align="center" bgcolor="#EFF0F2">借款利息收息及利息计算</td>',
						'<td colspan="3" align="center" bgcolor="#EFF0F2">罚息及提前还款补偿收取记录</td>',
					'</tr>',
					  '<tr>',
					    '<td bgcolor="#EFF0F2">收取利息时间</td>',
					    '<td bgcolor="#EFF0F2">已收利息额</td>',
					    '<td bgcolor="#EFF0F2">利息初始日</td>',
					    '<td bgcolor="#EFF0F2">利息结束日</td>',
					    '<td bgcolor="#EFF0F2">占用资金日(按日计算)</td>',
					    '<td bgcolor="#EFF0F2">占用资金日(按月计算)</td>',
					    '<td bgcolor="#EFF0F2">应收利息</td>',
				        '<td bgcolor="#EFF0F2">收款时间</td>',
				        '<td bgcolor="#EFF0F2">罚息收入</td>',
				        '<td bgcolor="#EFF0F2">提前还款补偿费</td>',
					  '</tr>'
				];
				var payDate = "";
				if(json_data.payDate){
					payDate = new Date(json_data.payDate).dateFormat('Y-m-d');
				}
				var endDate = new Date(json_data.endDate).dateFormat('Y-m-d');
				var predDate = new Date(json_data.predDate).dateFormat('Y-m-d');
				var pat = json_data.pat;
				var yfreeamount = json_data.yfreeamount;
				var predDate = "";
				if(json_data.predDate){
					predDate = new Date(json_data.predDate).dateFormat('Y-m-d');
				}
				var Inter_Days = Cmw.calculateDays(payDate,endDate)+'日';
				var Inter_Moths = Cmw.calculateMonth(payDate,endDate)+'月';
					tableHtml.push([
								 '<tr>',
								 '<td>'+payDate+'</td>',
								 '<td>'+Cmw.getThousandths(json_data.rat)+'</td>',
								 '<td>'+payDate+'</td>',
								 '<td>'+endDate+'</td>',
								 '<td>'+Inter_Days+'</td>',
								 '<td>'+Inter_Moths+'</td>',
								 '<td>'+Cmw.getThousandths(json_data.interest)+'</td>',
								 '<td>'+predDate+'</td>',
								 '<td>'+Cmw.getThousandths(pat)+'</td>',
								 '<td>'+Cmw.getThousandths(yfreeamount)+'</td>',
								 '</tr>'
								].join(" "));
					tableHtml.push(['</table>'].join(""));
					amountRecordsDiv.update(tableHtml.join(""));
			}});
		},
		/**
		 * 导出Excel
		 * @param {} _this
		 */
		doExport : function(_this){
			var title = _this.tab.title;
			var reportHtml = $('#'+_this.globalMgr.reportPanelId).html();
			var url = './sysHtmlFile_save.action';
			 EventManager.get(url,{params:{reportHtml:reportHtml},sfn:function(json_data){
			 	var filePath = json_data.msg;
			 	title = encodeURIComponent(title);
			 	EventManager.downLoad('./controls/html2xls/excel.jsp',{fileName:title,filePath:filePath});
			 }});
		}
	},
	/**
	 * 销毁组件
	 */
	destroyCmpts : function(){
		
	}
})