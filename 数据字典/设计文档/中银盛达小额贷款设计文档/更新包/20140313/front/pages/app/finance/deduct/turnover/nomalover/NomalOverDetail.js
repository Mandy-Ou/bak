/**
 * 正常还款金额收取流水情况窗口
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		params : null,
		parent : null,
		apptbar : null,
		mainPanel : null,
		appFrm : null,
		appWin : null,
		appgrid_1:null,
		detailPanlId : Ext.id(null, 'NomalDeductDetailPanl'),
		bussKey : null,/* 钥匙KEY */
		sysId : null,
		contractIds : null,
		uamount : null,
		total :  null,
		errArr : null,/*错误消息*/
		closeFlag : false,/*用来标识是否是关闭窗口事件*/
		vtempCode : VOUCHERTEMP_CODE.NOMALDEDUCT,/*正常扣收凭证模板编号 constant.js定义*/
		setParams : function(parentCfg) {
			this.parentCfg = parentCfg;
			// --> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.sysId = parentCfg.parent.sysId;
			this.contractIds = parentCfg.contractIds;
			this.errArr = null;
		},
		createAppWindow : function() {
			this.apptbar = this.createToolBar();
			this.mainPanel = this.createMainPanel();
			this.appWin = new Ext.ux.window.MyWindow({
						width : 850,
						height : 469,
						tbar : this.apptbar,
						items : [this.mainPanel]
					});
		},
		/**
		 * 显示弹出窗口
		 * 
		 * @param _parentCfg
		 *            弹出之前传入的参数
		 */
		show : function(_parentCfg) {
			this.closeFlag = false;
			if (_parentCfg)this.setParams(_parentCfg);
			var isFirstLoad = false;
			if (!this.appWin) {
				this.createAppWindow();
			}
			this.appWin.optionType = this.optionType;
			this.appWin.show();
			this.loadData();
		},
		/**
		 * 加载数据
		 */
		loadData : function() {
			var loanDetailPnl = Ext.getCmp(this.detailPanlId);
			var selId = this.parent.getSelId();
			loanDetailPnl.reload({id : selId});
		},
		/**
		 * 当数据保存成功后，执行刷新方法 [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data) {
			var _this = exports.WinEdit;
			if (_this.parentCfg.self.refresh)
				_this.parentCfg.self.refresh(_this.optionType, data);
		},
		createToolBar : function() {
			var _this = this;
			var barItems = [ {
						text : Btn_Cfgs.CLOSE_BTN_TXT, /*-- 关闭 --*/
						iconCls : Btn_Cfgs.CLOSE_CLS,
						tooltip : Btn_Cfgs.CLOSE_TIP_BTN_TXT,
						handler : function() {
							_this.close();
						}
					}];
			var appBar = new Ext.ux.toolbar.MyCmwToolbar({
						aligin : 'right',
						controls : barItems
					});
			return appBar;
		},
		/**
		 * 创建Form表单
		 */
		createMainPanel : function() {
			var _this = this;
			var selId = this.parent.getSelId();
			var htmlArrs_1 = [
					'<tr><th col="code" style="width:10%;">借款合同号</th> <td col="code" >&nbsp;</td><th col="custName">客户名称</th> <td colspan="3" col="custName" >&nbsp;</td><tr>',
					'<tr><th  col="payBank">还款银行</th> <td col="payBank" >&nbsp;</td><th col="payAccount">还款帐号</th> <td  col="payAccount" >&nbsp;</td><th  col="accName">还款人</th> <td col="accName" >&nbsp;</td><tr>',
					'<tr><th  col="appAmount">贷款金额</th> <td col="appAmount" >&nbsp;</td><th  col="loanLimit">贷款期限</th> <td  col="loanLimit" >&nbsp;</td><th  col="lastDate">最后还款日</th> <td col="lastDate" >&nbsp;</td><tr>',
					'<tr><th  col="state">还款状态</th> <td  col="state" >&nbsp;</td><th  col="paydPhases">已还期数</th> <td col="paydPhases">&nbsp;</td><th  col="totalPhases">总期数</th> <td col="totalPhases" >&nbsp;</td><tr>',
					'<tr>',
					'<table width="93%"  class="txr_detail_tabcls">',
					'<tr>',
						'<th width="9%"  col="principal" >应还本金</th>',
						'<td width="10%" col="principal">&nbsp;</td>',
						'<th width="8%"  col="interest">应还利息</th>',
						'<td width="21%" col="interest">&nbsp;</td>',
						'<th width="11%" col="mgrAmount">应还管理费</th>',
						'<td width="12%" col="mgrAmount">&nbsp;</td>',
						'<th width="12%" col="totalAmount">应还合计</th>',
						'<td width="13%" col="totalAmount">&nbsp;</td>',
					'</tr>',
					'<tr class="headtr">',
						'<th col="yprincipal">实收本金</th>',
						'<td col="yprincipal">&nbsp;</td>',
						'<th col="yinterest">实收利息</th>',
						'<td col="yinterest"> &nbsp;</td>',
						'<th col="ymgrAmount">实收管理费</th>',
						'<td col="ymgrAmount">&nbsp;</td>',
						'<th col="ytotalAmount">实收合计</th>',
						'<td col="ytotalAmount">&nbsp;</td>',
					'</tr>',
					'</table>',
					'</tr>'
					];
			var detailCfgs_1 = [{
				cmns : 'THREE',
				/* ONE , TWO , THREE */
				model : 'single',
				labelWidth : 95,
				title : '正常还款信息',
				isLoad:false,
				// 详情面板标题
				/* i18n : UI_Menu.appDefault, */
				// 国际化资源对象
				noautoTdWidth : false,/*自动计算每列单元格宽度，true : 不需要自动计算，false/undefined ：系统默认自动计算*/
				htmls : htmlArrs_1,
  				url: './fcNomalDeduct_overDetail.action',
  				params : {
					ids : selId
				},
				callback : {
					sfn : function(jsonData) {
						_this.callback(jsonData);
						var contractId = jsonData.contractId;
						var selId = _this.parent.getSelId();
							_this.appgrid_1.reload({contractId:contractId});	
						}
					}
				}];
			var detailPanel = new Ext.ux.panel.DetailPanel({
						id : this.detailPanlId,
						border : false,
						frame : false,
						width : true,
						isLoad:false,
						detailCfgs : detailCfgs_1
			});
			this.appgrid_1= this.createDetailPnl();
			detailPanel.add(this.appgrid_1);
			return detailPanel;
		},
		/**
		 * 当详情面板数据加载完成后触发的回调函数
		 */
		callback : function(jsonData){
			var custType = Render_dataSource.custTypeRender(jsonData.custType);
			jsonData["custName"] = jsonData["name"]+'<span style="color:red;">('+custType+')</span>';
			var appAmount = jsonData["appAmount"];
			this.total = appAmount;
			jsonData["appAmount"] = Cmw.getThousandths(appAmount)
					+ '元<br/><span style="color:red;">(大写：'
					+ Cmw.cmycurd(appAmount) + ')</span>';
			//还款状态
			switch(jsonData["state"]){
	    		case '4': jsonData["state"] = "部分收款"; break;
	    		case '6': jsonData["state"] = "结清"; break;
	    	}
			//zprincipal,zinterest,zmgrAmount,ztotalAmount
			jsonData["principal"] = jsonData["principal"] || 0.00; 
			jsonData["yprincipal"] = jsonData["yprincipal"] || 0.00; 
			
			jsonData["interest"] = jsonData["interest"] || 0.00; 
			jsonData["interest"] = jsonData["yinterest"] || 0.00; 
			
			jsonData["mgrAmount"] = jsonData["mgrAmount"] || 0.00; 
			jsonData["ymgrAmount"] = jsonData["ymgrAmount"] || 0.00; 
			jsonData["trmgrAmount"]  = jsonData["trmgrAmount"] || 0.00; 
			jsonData["totalAmount"] = jsonData["totalAmount"] || 0.00; 
			jsonData["ytotalAmount"] = jsonData["ytotalAmount"] || 0.00; 
			 
			jsonData["paydPhases"] = jsonData["paydPhases"] ? jsonData["paydPhases"]+"期" : "";
			jsonData["totalPhases"]= jsonData["totalPhases"] ? jsonData["totalPhases"]+"期" : "";
		},
		/**
		 * 创建详情面板
		 */
		createDetailPnl : function(){
			var parent = exports.WinEdit.parent;
			var selId = null;
			if(parent){
				selId = parent.getSelId();
			}else{
				ExtUtil.alert({msg:'请传入 parent 对象！'});
			}
			var structure_1 = [{
				    header: '业务标识 ',
				    name: 'bussTag',
				    renderer : Render_dataSource.AmountRecords
				},{
				    header: '实收本金 ',
				    name: 'cat',
				    renderer : function(val){return Cmw.getThousandths(val);}
				},
				{
				    header: '实收利息',
				    name: 'rat',
				     renderer : function(val){return Cmw.getThousandths(val);}
				},
				{
				    header: '实收管理费',
				    name: 'mat',
				     renderer : function(val){return Cmw.getThousandths(val);}
				},{
					header: '收款银行',
				    name: 'bankName',
				    width : 150
				},{
					header: '收款帐号',
				    name: 'account',
				     width : 150
				},
				{
				    header: '收款日期',
				    name: 'rectDate'
				}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    title: '实收流水',
			    structure: structure_1,
			    needPage:true,
			    pageSize:15,
			    autoWidth:true,
			    height:200,
			    isLoad: false,
			    keyField: 'id',
			    url: './fcAmountRecords_listRepDedut.action'
			}); 	
			return appgrid_1;
		}
,		
		/**
		 * 关闭窗口
		 */
		close : function() {
			this.closeFlag = true;
			this.appWin.hide();
		},
		/**
		 * 销毁组件
		 */
		destroy : function() {
			if(!this.appWin) return;
			this.appWin.close(); // 关闭并销毁窗口
			this.appWin = null; // 释放当前窗口对象引用
		}
	};
});
