/*同心日科技公司Erp 系统 命名空间*/
Ext.namespace("skythink.cmw.workflow.bussforms");
/**
 * 财务提前还款FrmUI
 */
skythink.cmw.workflow.bussforms.FinancePrepayMentMgr = function(){
	this.init(arguments[0]);
}
Ext.extend(skythink.cmw.workflow.bussforms.FinancePrepayMentMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		var finishBussCallback = tab.finishBussCallback;
		this.module = new Cmw.app.widget.AbsPanelView({
			tab : tab,
			params : params,/*apptabtreewinId,tabId,sysid*/
			getAppCmpt : this.getAppCmpt,
			createDetailPnl: this.createDetailPnl,
			changeSize : this.changeSize,
			destroyCmpts : this.destroyCmpts,
			globalMgr : this.globalMgr,
			refresh : this.refresh,
			finishBussCallback : finishBussCallback,/*由必做或选做业务菜单传入的回調函数，主要功能：当业务表单保存后，更新必做或选做事项为已做*/
			prefix : Ext.id()
		});
	},
	/**
	 * 面板渲染后加载数据 ： 之前进行了收款的，则要禁用收款按钮，否则要启用收款按钮
	 */
	refresh : function(){
		var _this = this;
		if (!this.appPanel.rendered) {
			this.appPanel.addListener('render', function(cmpt) {
				_this.globalMgr.loadDatas(_this);
			});
		} else {
			this.globalMgr.loadDatas(_this);
		}
	},
	/**
	 * 设置面板的大小
	 * @param {} whArr
	 */
	changeSize : function(whArr){
		var width = whArr[0];
		var height = whArr[1];
		this.appPanel.setWidth(width,true);
		this.appPanel.setHeight(height,true);
	},
	/**
	 * 创建财务主面板
	 */
	getAppCmpt : function(){
		this.appPanel = new Ext.Panel({border : false});
		var financePrepayMentDateilPnl  = this.createDetailPnl();
		this.appPanel.add(financePrepayMentDateilPnl);
		this.appPanel.doLayout();
		this.refresh();
		return this.appPanel;
	},
	/**
	 * 创建财务提前还款详情面板
	 */
	createDetailPnl : function(){
		var _this = this;
		var htmlArrs_1 = [
					'<tr><th col="loanCode">借款合同</th> <td col="loanCode" >&nbsp;</td><th col="name">客户名称</th> <td col="name" >&nbsp;</td><th col="ptype">提前还款类别</th> <td col="ptype" >&nbsp;</td></tr>',
					'<tr><th col="payBank">还款银行</th> <td col="payBank" >&nbsp;</td><th col="payAccount">还款帐号</th> <td col="payAccount" >&nbsp;</td><th col="accName">还款人</th> <td col="accName" >&nbsp;</td></tr>',
					'<tr><th col="adDate">预计提前还款日</th> <td col="adDate" >&nbsp;</td><th col="predDate">实际提前还款日</th> <td col="predDate" >&nbsp;</td><th col="frate">提前还款手续费率</th> <td col="frate" >&nbsp;</td></tr>',
					'<tr><th col="isretreat">是否退息费</th> <td col="isretreat" >&nbsp;</td><th col="xphases">总期数</th> <td col="xphases" >&nbsp;</td><th col="yphases">已还期数</th> <td col="yphases" >&nbsp;</td></tr>',
					'<tr><th col="phases">当期期数</th> <td col="phases" >&nbsp;</td><th col="reprincipal">应收剩余本金</th> <td col="reprincipal" >&nbsp;</td><th col="zinterest">应收利息</th> <td col="zinterest" >&nbsp;</td></tr>',
					'<tr><th col="zmgrAmount">应收管理费</th> <td col="zmgrAmount" >&nbsp;</td><th col="zpenAmounts">应收罚息</th> <td col="zpenAmounts" >&nbsp;</td><th col="zdelAmounts">应收滞纳金</th> <td col="zdelAmounts" >&nbsp;</td></tr>',
					'<tr><th col="freeAmount">应收手续费</th> <td  col="freeAmount">&nbsp;</td><th col="imamount">应退息费</th> <td  col="imamount" >&nbsp;</td><th col="totalAmount">应收合计</th> <td col="totalAmount" >&nbsp;</td></tr>'
					];
		var detailCfgs_1 = [{
		    cmns: 'THREE',
		    /* ONE , TWO , THREE */
		    model: 'single',
		    labelWidth: 115,
		    /* title : '#TITLE#', */
		    //详情面板标题
		    /*i18n : UI_Menu.appDefault,*/
		    //国际化资源对象
		    htmls: htmlArrs_1,
		    url: './fcPrepayment_getfinancePreMent.action',
		    params: {
		        id: -1
		    },
		    callback: {
		        sfn: function(jsonData) {
		        	var accountId = jsonData["accountId"] || "";
		        	cbog_accountId.setValue(accountId);
		        	var bankAccount = jsonData["bankAccount"] || "";
		        	txt_bankAccount.setValue(bankAccount);
		        	var predDate = jsonData["predDate"] || "";
		        	var xstatus =  jsonData["xstatus"];
		        	 _this.globalMgr.applyForm.setFieldValue("rectDate",predDate);
		        	 if(xstatus && parseInt(xstatus)==2){/*结清时禁用收款按钮*/
		        	 	pcDetailPanel.setTitle("提前还款收款 >> "+'<span style="color:red;">已经进行了收款！</span>');
		        		 btnSave.setDisabled(true);
		        	 }
		        	var planId = jsonData["planId"] || "";
		        	var id = _this.globalMgr.applyId || "";
		        	var freeAmount = jsonData["freeAmount"] || "";
		        	var imamount = jsonData["imamount"] || ""; 
		        	jsonData["isretreat"] = Render_dataSource.prepayment_isretreatRender(jsonData["isretreat"].toString());
		        	jsonData["ptype"] = Render_dataSource.prepayment_ptypeRender(jsonData["ptype"].toString());
		        	 _this.globalMgr.applyForm.setFieldValue("payplanId",planId);
		        	 _this.globalMgr.applyForm.setFieldValue("id",_this.globalMgr.applyId);
		        	 _this.globalMgr.applyForm.setFieldValue("freeAmount",freeAmount);
		        	 _this.globalMgr.applyForm.setFieldValue("imamount",imamount);
		        	 _this.globalMgr.setAmountData(_this,jsonData);
		        	 _this.globalMgr.calculateFree(_this,jsonData);
		        	 _this.globalMgr.fmtJsonData(jsonData);
		        }
		    }
		    }];
	    var pcDetailPanel = new Ext.ux.panel.DetailPanel({
			title : '提前还款收款 >> ',
		    detailCfgs: detailCfgs_1,
		    border : false,
	     	isLoad : false
		});
		this.globalMgr.pcDetailPanel = pcDetailPanel;
		var txt_vtempCode = FormUtil.getHidField({fieldLabel: '提前还款财务凭证编号',name: 'vtempCode',value:VOUCHERTEMP_CODE.PREPAYMENTCODE});
		var txt_sysId = FormUtil.getHidField({fieldLabel: '系统ID',name: 'sysId',value:_this.globalMgr.sysId});
		var txt_id = FormUtil.getHidField({fieldLabel: '提前还款申请单ID',name: 'id'});
		var txt_payplanId = FormUtil.getHidField({fieldLabel: '还款计划ID',name: 'payplanId'});
		var txt_freeamount = FormUtil.getHidField({fieldLabel: '提前还款手续费',name: 'freeAmount'});
		var txt_imamount = FormUtil.getHidField({fieldLabel: '退息费金额',name: 'imamount'});
		var txt_interest = FormUtil.getHidField({fieldLabel: '应收利息',name: 'interest'});
		var txt_mgrAmount = FormUtil.getHidField({fieldLabel: '应收管理费',name: 'mgrAmount'});
		var txt_totalAmount = FormUtil.getHidField({fieldLabel: '应收合计',name: 'totalAmount'});
		
		var callback = function(cboGrid, selVals) {
				var grid = cboGrid.grid;
				var record = grid.getSelRow();
				var account = record.get("account");
				txt_bankAccount.setValue(account);
			}
		var cbog_accountId = ComboxControl.getAccountCboGrid({
				fieldLabel : '收款银行',
				name : 'accountId',
				allowBlank : false,
				readOnly : true,
				isAll : false,
				width : 130,
				callback : callback,
				params : {
					isIncome : 1,
					sysId : _this.globalMgr.sysId
				}
		});

		var txt_bankAccount = FormUtil.getReadTxtField({
			fieldLabel : '银行帐号',
			name : 'bankAccount',
			"width" : 160
		});

		var dat_rectDate = FormUtil.getDateField({
			fieldLabel : '收款日期',
			name : 'rectDate',
			"width" : 125,
			"allowBlank" : false,
			editable : false,
			"maxLength" : 50
		});
		
		
		var check_isVoucher = FormUtil.getCheckGroup({
			    name: 'isVoucher',
			    "width": 150,
			    "maxLength": "10",
			    items : [
			    	{boxLabel: Finsys_Btn_Cfgs.AUTOVOUCHER_BOXLABEL, name: 'isVoucher',inputValue:1,checked:true}
				]
			});
			
		
		var btnSave = new Ext.Button({text : '收款',handler : function(){
					var isVoucher = check_isVoucher.getValue();
					EventManager.frm_save(applyForm,{beforeMake:function(data){
						data["isVoucher"] = isVoucher;
					},sfn:function(data){
						if(_this.finishBussCallback) _this.finishBussCallback(data);
						var amountLogIds = data["amountLogIds"];
						btnSave.disable();
			 		},ffn:function(data){
			 			Cmw.print(data);
			 			btnSave.enable();
			 		}});
				}
			});
			
			var btnPanel = this.globalMgr.getBtnPanel([check_isVoucher,btnSave]);
		var layout_fields = [txt_vtempCode,txt_sysId,txt_id,txt_payplanId,txt_freeamount,txt_imamount,txt_totalAmount,txt_interest,txt_mgrAmount,
					{cmns: FormUtil.CMN_THREE,fields: 
						[cbog_accountId,txt_bankAccount,dat_rectDate]
					},btnPanel
				];
				
		var frm_cfg = {
				autoScroll : true,
				border : false,
				url : './fcPrepayment_financeSave.action',
				labelWidth : 115
			};
		var applyForm = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
		pcDetailPanel.add(applyForm);
		this.globalMgr.applyForm = applyForm;
		return pcDetailPanel;
	},
	/**
	 * 类的全`局变量
	 * @type 
	 */
	globalMgr : {
		sysId : this.params.sysid,
		pcDetailPanel : null,
		applyForm : null,
		applyId : this.params.applyId,
		amountData : null,/*缓存提前还款计算相关金额*/
		/**
		 * 返回btn面板
		 * @param {} _this
		 */
		 getBtnPanel : function(buttons){
		 	var panel = new Ext.Panel({ buttonAlign : 'center',buttons : buttons,autoScroll: true});
			return panel;
		 },
		/**
		 * 加载数据
		 * @param {} _this
		 */
		loadDatas : function(_this){
			EventManager.get('./fcPrepayment_getfinancePreMent.action',{params:{id:_this.globalMgr.applyId},sfn:function(jsonData){
				_this.globalMgr.pcDetailPanel.reload({json_data : jsonData},true);
			},ffn:function(jsonData){
				
			}});
		},
		/**
		 * 设置金额数据
		 */
		setAmountData : function(_this,jsonData){
			var keys = ["reprincipal","principal","interest","mgrAmount","zinterests","zmgrAmounts","zpenAmounts","zdelAmounts","freeamount","imamount","totalAmount"];
			if(!_this.globalMgr.amountData) _this.globalMgr.amountData = {};
			for(var i=0,count=keys.length; i<count; i++){
				key = keys[i];
				_this.globalMgr.amountData[key] = jsonData[key];
			}
		},
		/**
		 * 计算金额 //(应收利息=当期应收利息+逾期应收利息)、 (应收管理费=当期应收管理费+逾期应收管理费)
		 * @param {} _this
		 */
		calculateFree: function(_this,jsonData){
			Cmw.print(jsonData);
			var interes = jsonData["interest"] ||  0.00;
			var zinterest = jsonData["zinterests"] || 0.00;
			 var interest = parseFloat(interes)+parseFloat(zinterest);//(应收利息=当期应收利息+逾期应收利息)
			  _this.globalMgr.applyForm.setFieldValue("interest",interest);
			 jsonData["interest"] = Cmw.getThousandths(interest)+"元";
			 var mgrAmount = jsonData["mgrAmount"] || 0.00;
			 var zmgrAmounts = jsonData["zmgrAmounts"] || 0.00;
			 var mgrAmount = parseFloat(mgrAmount)+parseFloat(zmgrAmounts);//(应收管理费=当期应收管理费+逾期应收管理费)
			  _this.globalMgr.applyForm.setFieldValue("mgrAmount",mgrAmount);
			  jsonData["mgrAmount"] = Cmw.getThousandths( mgrAmount)+"元";
			  var reprincipal = jsonData["reprincipal"] || 0.00;
			  var zpenAmounts = jsonData["zpenAmounts"] || 0.00;
			  var zdelAmounts = jsonData["zdelAmounts"] || 0.00;
			  var freeAmount = jsonData["freeAmount"] || 0.00;
			  var imamount = jsonData["imamount"] || 0.00;
//			 var totalAmount = parseFloat(reprincipal)+zinterest+zmgrAmounts+
//			 							parseFloat(zpenAmounts)+parseFloat(zdelAmounts)+
//			 							parseFloat(jsonData["freeAmount"])-parseFloat(jsonData["imamount"]);
			  var totalAmount = jsonData["totalAmount"]; 
		 	 _this.globalMgr.applyForm.setFieldValue("totalAmount",totalAmount);
			var xstatus = jsonData["xstatus"];
			xstatus = Render_dataSource.statusRender(xstatus+"");
			jsonData["totalAmount"] = Cmw.getThousandths(totalAmount)+"元<span style='color:red;font-weight:bold'>("+xstatus+")</span>";
			},
		/**
		 * 格式化jsonData 数据
		 * @param {} _this 当前对象
		 * @param {} jsonData 提前还款详情面板数据
		 */
		fmtJsonData:function(jsonData){
			var arr = ["phases","xphases","yphases"];
			for(var i=0,count=arr.length; i<count; i++){
				var key = arr[i];
				var val = jsonData[key];
				if(val) jsonData[key] = val + "期";
			}
			jsonData["frate"] = (jsonData["frate"]) ? jsonData["frate"]+"%" : "";
			arr = ["yprincipals","reprincipal","principal","zinterests","zmgrAmounts","zpenAmounts","zdelAmounts","freeAmount","imamount"];
			for(var i=0,count=arr.length; i<count; i++){
				var key = arr[i];
				var val = jsonData[key] || 0.00;
				jsonData[key] = Cmw.getThousandths(val)+"元";
			}

		}
	},
	/**
	 * 销毁对象和面板
	 */
	destroyCmpts : function(){
		if(this.appPanel != null){
			this.appPanel.destroy();
			this.appPanel = null;
		}
	}
});