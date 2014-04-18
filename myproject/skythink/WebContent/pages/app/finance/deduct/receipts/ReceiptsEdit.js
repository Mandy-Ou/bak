/**
 * 预收息费管理收款页面 作者彭登浩
 */
define(function(require, exports) {
	exports.WinEdit = {
		parent : null,
		appWin : null,
		parentCfg : null,
		sysId : null,
		apptbar : null,
		mainPanel : null,
		appFrm : null,
		appGrid : null,
		appDetailPnl : null,
		vtempCode : VOUCHERTEMP_CODE.NOMALDEDUCT,
		/**
		 * 设置参数
		 */
		setParams : function(parentCfg){
			this.parentCfg = parentCfg;
			this.parent = parentCfg.parent;
			this.sysId = parentCfg.parent.sysId;
		},
		/**
		 * 创建主面板
		 */
		createAppWindow : function(){
			this.apptbar = this.createToolBar();
			this.mainPanel = this.createMainPanel();
			this.appWin = new Ext.ux.window.MyWindow({
							width : 850,
							height :550,
							autoScroll : true,
							tbar : this.apptbar,
							items : [this.mainPanel]
						});
		},
		/**
		 * 显示win 窗体
		 */
		show : function(_parentCfg){
			if(_parentCfg) this.setParams(_parentCfg);
			if(!this.appWin){
				this.createAppWindow();
			}
			this.appWin.show();
			this.loadData();
		},
		/**
		 * 刷新数据
		 */
		refresh : function(data){
			
		},
		/**
		 * 加载数据
		 */
		loadData : function(){
			var selId = this.parent.getSelId();
			this.appDetailPnl.reload({id:selId});
		},
		/**
		 * 创建工具栏
		 */
		createToolBar : function(){
			var _this = exports.WinEdit;
			var barItems = [{type:'chk',name:'isVoucher',checked:true,boxLabel:Finsys_Btn_Cfgs.AUTOVOUCHER_BOXLABEL},{
				text : Btn_Cfgs.DO_NOMAL_BTN_TXT, /*-- 收款 --*/
				iconCls : Btn_Cfgs.DO_NOMAL_CLS,
				tooltip : Btn_Cfgs.DO_NOMAL_TIP_BTN_TXT,
				handler : function() {
					_this.saveData();
				}
			}, {
				text : Btn_Cfgs.CLOSE_BTN_TXT, /*-- 关闭 --*/
				iconCls : Btn_Cfgs.CLOSE_CLS,
				tooltip : Btn_Cfgs.CLOSE_TIP_BTN_TXT,
				handler : function() {
					_this.close();
				}
			}];
			this.apptbar = new Ext.ux.toolbar.MyCmwToolbar({aligin : 'right',controls : barItems});
			return this.apptbar;
		},
		createMainPanel : function(){
			this.appDetailPnl = this.createDetailPnl();
			var appMainPnl = new Ext.Panel({items:[this.appDetailPnl],border : false});
			return appMainPnl;
		},	
		/**
		 * 创建详情页面
		 */
		createDetailPnl : function(){
			var _this = this;
			var selId = this.parent.getSelId();
			var htmlArrs_1 = [
				'<tr><th col="code">放款单编号</th> <td col="code" >&nbsp;</td><th col="name">客户姓名</th> <td col="name" colspan =3 >&nbsp;</td></tr>',
				'<tr><th col="ccode">借款合同号</th> <td col="ccode" >&nbsp;</td><th col="appAmount">贷款金额</th> <td col="appAmount" >&nbsp;</td><th col="loanLimit">贷款期限</th> <td col="loanLimit" >&nbsp;</td></tr>',
				'<tr><th col="payType">还款方式</th> <td col="payType" >&nbsp;</td><th col="rateType">利率类型</th> <td col="rateType" >&nbsp;</td><th col="urate">罚息利率</th> <td col="urate" >&nbsp;</td></tr>',
				'<tr><th col="payAmount">放款金额</th> <td col="payAmount" >&nbsp;</td><th col="rate">贷款利率</th> <td col="rate" >&nbsp;</td><th col="inRate">公司内部利率</th> <td col="inRate" >&nbsp;</td></tr>',
				'<tr><th col="mgrtype">管理费收取方式</th> <td col="mgrtype" >&nbsp;</td><th col="mrate">管理费率</th> <td col="mrate" >&nbsp;</td><th col="urate">罚息利率</th> <td col="urate" >&nbsp;</td></tr>',
				'<tr>'+
					'<th col="payDate">合约放款日</th> <td col="payDate">&nbsp;</td>'+
					'<th col="ysRatMonth">预收利息月数</th> <td col="ysRatMonth">&nbsp;</td>' +
					'<th col="ysRat">预收利息金额</th> <td col="ysRat" >&nbsp;</td>' +
				'</tr>',
				'<tr>' +
					'<th col="ysMatMonth">预收管理费月数</th> <td col="ysMatMonth">&nbsp;</td>' +
					'<th col="ysMat">预收管理费金额</th> <td col="ysMat"  colspan  = 3>&nbsp;</td>' +
				'</tr>'
			];
			
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 95,
			    title : '客户预收信息',
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './fcLoanInvoce_getYs.action',
			    params: {
			        id: selId
			    },
//			    formDiyCfg : {sysId:_this.sysId,formdiyCode:FORMDIY_IND.FORMDIY_LOANINVOCE,formIdName:'id'},
			    callback: {
			        sfn: function(jsonData) {
			        	jsonData["name"] = jsonData["name"] +"<span style='color:red;'>("+Render_dataSource.custTypeRender(jsonData["custType"].toString())+")</span>";
			        	jsonData["inRate"]  = jsonData["inRate"] ? jsonData["inRate"] +'%' : '0.00%';
						_this.appAmount = jsonData["appAmount"];
						jsonData["appAmount"] =  Cmw.getThousandths(_this.appAmount)+'元<br/><span style="color:red;">(大写：'+Cmw.cmycurd(_this.appAmount)+')</span>';
						jsonData["rate"]  = jsonData["rate"] ? jsonData["rate"] +'%' : '0.00%';
						jsonData["rateType"] = Render_dataSource.rateTypeRender(jsonData["rateType"]);
						jsonData["payType"]  = Render_dataSource.payTypeRender(jsonData["payType"]);
						jsonData["urate"]  =  jsonData["urate"] ? jsonData["urate"] +'%' : '0.00%';
						jsonData["mrate"]  =  jsonData["mrate"] ? jsonData["mrate"] +'%' : '0.00%';
						jsonData["mgrtype"] = Render_dataSource.mgrtype_render(jsonData["mgrtype"].toString());
						var ysRatMonth = jsonData["ysRatMonth"];
						var ysMatMonth = jsonData["ysMatMonth"];
						jsonData["ysRatMonth"] = jsonData["ysRatMonth"]? jsonData["ysRatMonth"]+"个月" : "" ;
						jsonData["ysMatMonth"] = jsonData["ysMatMonth"]?  jsonData["ysMatMonth"]+"个月" : "";
						var ysRat = jsonData["ysRat"] ;
						if(ysRat){
							jsonData["ysRat"] =  Cmw.getThousandths(ysRat)+'元<br/><span style="color:red;">(大写：'+Cmw.cmycurd(ysRat)+')</span>';
						}
						var ysMat = jsonData["ysMat"] ;
						if(ysMat){
							jsonData["ysMat"] =  Cmw.getThousandths(ysMat)+'元<span style="color:red;">(大写：'+Cmw.cmycurd(ysMat)+')</span>';
						}
						
						var payAmount = jsonData["payAmount"];
						jsonData["payAmount"] =  Cmw.getThousandths(payAmount)+'元<br/><span style="color:red;">(大写：'+Cmw.cmycurd(payAmount)+')</span>';
						_this.setFormValues(jsonData);
						if(!ysRatMonth){
							ysRatMonth = 0;
						}
						if(!ysMatMonth){
							ysMatMonth = 0;
						}
						var phases = Ext.max([ysRatMonth,ysMatMonth]);
						txt_phases.setValue(phases);
						_this.appGrid.reload({contractId:jsonData["contractId"],phases:phases});
			        }
			    }
			}];
			var detailPanel = new Ext.ux.panel.DetailPanel({
				id : this.detailPanlId,
				frame : false,
				border : false,
				isLoad : false,
				autoScroll : true,
				autoHeight : true,
			    autoWidth  : true,
			    detailCfgs: detailCfgs_1
			});
			var txt_sysId = FormUtil.getHidField({
			    fieldLabel: 'sysId',
			    name: 'sysId',
			    "width": "125"
			});
			var txt_vtempCode = FormUtil.getHidField({
			    fieldLabel: 'vtempCode',
			    name: 'vtempCode',
			    "width": "125"
			});
			var txt_phases = FormUtil.getHidField({
			    fieldLabel: '期数',
			    name: 'phases',
			    "width": "125"
			});
			var txt_id = FormUtil.getHidField({
			    fieldLabel: '借款合同Id',
			    name: 'id',
			    "width": "125"
			});
			var txt_contractId = FormUtil.getHidField({
			    fieldLabel: '借款合同Id',
			    name: 'contractId',
			    "width": "125"
			});
			
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
							isenabled:1,
							sysId : _this.sysId
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
			var layout_fields = [txt_sysId,txt_vtempCode,txt_phases,txt_id,txt_contractId,{
			    cmns: FormUtil.CMN_THREE,
			    fields: [cbog_accountId, txt_bankAccount,dat_rectDate]
			}];
			var frm_cfg = {
				title:'预收息费管理', 
				height : 100,
			    url: './fcLoanInvoce_saveYs.action'
			};
			var appform = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			this.appFrm = appform;
			this.appGrid = this.createPlanAppGrid();
			detailPanel.add(this.appGrid);
			detailPanel.add(appform);
			return detailPanel;
		},
		/**
		 * 创建需要预收的还款计划表格
		 */
		createPlanAppGrid : function(){
			var structure_1 = [{
			    header: '期数',
			    name: 'phases',
			    width: 35
			},
			{
			    header: '应还款日期',
			    name: 'xpayDate',
			    width: 80
			},
			{
			    header: '应还本金',
			    name: 'principal',
			    width: 110,
			    renderer : function(val){return Cmw.getThousandths(val);}
			},
			{
			    header: '实还本金',
			    name: 'yprincipal',
			    width: 110,
			    renderer : function(val){return Cmw.getThousandths(val);}
			},
			{
			    header: '应还利息',
			    name: 'interest',
			     width: 110,
			    renderer : function(val){return Cmw.getThousandths(val);}
			},{
			    header: '实还利息',
			    name: 'yinterest',
			     width: 110,
			    renderer : function(val){return Cmw.getThousandths(val);}
			},
			{
			    header: '应还管理费',
			    name: 'mgrAmount',
			     width: 110,
			    renderer : function(val){return Cmw.getThousandths(val);}
			},{
			    header: '实还管理费',
			    name: 'ymgrAmount',
			     width: 110,
			    renderer : function(val){return Cmw.getThousandths(val);}
			}];
		
			var continentGroupRow = [{header: '', colspan: 3, align: 'center'},
				{header: '本金', colspan: 2, align: 'center'},
				{header: '利息', colspan: 2, align: 'center'},
				{header: '管理费', colspan: 2, align: 'center'}
				];
			 var group = new Ext.ux.grid.ColumnHeaderGroup({
		        rows: [continentGroupRow]
		    });
	    
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    title: '预收息费表',
			    structure: structure_1,
			    url: './fcPlan_list.action',
			    height:150,
			    needPage: false,
			    isLoad: false,
			    plugins: group,
			    keyField: 'id'
			});
			return appgrid_1;
		},
		/**
		 * 保存数据
		 */
		saveData : function(){
			var _this= this;
			EventManager.frm_save(this.appFrm,{sfn :function(json_data){
				Ext.tip.msg(Msg_SysTip.title_appconfirm,Msg_SysTip.msg_dataSave);
				_this.close();
				_this.parent.reload();
			},ffn :function(json_data){
				Ext.alert({msg:Msg_SysTip.msg_dataFailure});
				return;
			}})
		},
		/**
		 * 为formPnl 赋值
		 */
		setFormValues : function(json_data){
			var contractId = json_data.contractId;
			var id = json_data.id;
			var text_Id = this.appFrm.findFieldByName("id");
			var text_contractId = this.appFrm.findFieldByName("contractId");
			var text_rectDate = this.appFrm.findFieldByName("rectDate");
			var text_vtempCode = this.appFrm.findFieldByName("vtempCode");
			var text_sysId = this.appFrm.findFieldByName("sysId");
			text_sysId.setValue(this.sysId);
			text_vtempCode.setValue(this.vtempCode);
			text_Id.reset(); 
			text_contractId.reset(); 
			text_rectDate.reset(); 
			text_Id.setValue(id);
			text_contractId.setValue(contractId);
			text_rectDate.setValue(json_data.payDate);
		},
		/**
		 * 关闭window  窗体
		 */
		
		close : function(){
			this.appWin.hide();
		},
		/**
		 * 销毁组件
		 */
		destroy : function(){
			this.appWin.close();	//关闭并销毁窗口
			this.appWin = null;		//释放当前窗口对象引用
		}
	}
});