/**
 * 委托人资料详情
 * 
 * @author 李听
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		params : null,
		parent : null,
		apptbar : null,
		mainPanel : null,
		appWin : null,
		createDetailPnl : null,
		mount : null,
		detailPanlId : Ext.id(null, 'LoanDetailPanl'),
		bussKey : null,/* 钥匙KEY */
		sysId : null,
		selId:null,
		appgrid_1 : null,
		contractId : null,
		setParams : function(parentCfg) {
			this.parentCfg = parentCfg;
			this.parent = parentCfg.parent;
			this.sysId = parentCfg.parent.sysId;
			this.selId=this.parent.getSelId();
			this.contractId = parentCfg.parent.contractId;
		},
		createAppWindow : function() {
			this.apptbar = this.createToolBar();
			this.mainPanel = this.createMainPanel();
			this.appWin = new Ext.ux.window.MyWindow({
						width : 820,
						height : 460,
						modal : true,
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
			if (_parentCfg)
				this.setParams(_parentCfg);
			if (!this.appWin) {
				this.createAppWindow();
			}
			this.appWin.optionType = this.optionType;
			this.appWin.show(/*this.parent.getEl()*/);
			this.loadData();
			this.mainPanel.reload();
		},
		/**
		 * 加载数据
		 */
		loadData : function() {
			var loanDetailPnl = this.mainPanel;
			var selId = this.parent.getSelId();
			 loanDetailPnl.reload({
			 id : selId
			 });
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
			var barItems = [{
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
			var _this=this;
			var htmlArrs_1 = [
					'<tr><th col="code">委托客户编号</th> <td col="code" >&nbsp;</td><th col="name">委托人</th> <td col="name" >&nbsp;</td><th col="appAmount">委托金额</th> <td col="appAmount" >&nbsp;</td></tr>',
					'<tr><th col="bamount">撤资金额</th> <td col="bamount" >&nbsp;</td><th col="wamount">违约金额</th> <td col="wamount" >&nbsp;</td><th col="backDate">撤资日期</th> <td col="backDate" >&nbsp;</td></tr>',
					'<tr><th col="isNotExpiration">是否期满</th> <td col="isNotExpiration" >&nbsp;</td><th col="clause">备注</th> <td col="clause" >&nbsp;</td></tr>'];
			var detailCfgs_1 = [{
						cmns : 'THREE',
						/* ONE , TWO , THREE */
						model : 'single',
						labelWidth : 90,
						/* title : '#TITLE#', */
						// 详情面板标题
						/* i18n : UI_Menu.appDefault, */
						// 国际化资源对象
						htmls : htmlArrs_1,
						url : './fuBamountApply_getDetil.action',
						params : {
							id : _this.parent.getSelId()
						},
						callback : {
							sfn : function(jsonData) {
								jsonData["bamount"]= Cmw.getThousandths(jsonData["bamount"])+'元' ;
								jsonData["wamount"]= Cmw.getThousandths(jsonData["wamount"])+'元' ;
								jsonData["appAmount"]= Cmw.getThousandths(jsonData["appAmount"])+'元' ;
									switch (jsonData["isNotExpiration"]) {
						        		case '-1' :
						        			jsonData["isNotExpiration"] = "作废";
						          	 		break;
						        		case '0':
						        			jsonData["isNotExpiration"] = "不可用";
						          	 		break;
						        		case '1':
						        			jsonData["isNotExpiration"] = "可用";
						          	 		break;
						       		 	}
								/* 可在对页面进行赋值前，对数据进行转换处理 to do .. */
								// jsonData["leaf"] =
								// (jsonData["leaf"]=="false") ? "否" : "是";
							}
						}
					}];
			var attachLoad = function(params, cmd) {
				/* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
				// Cmw.print(params);
				// Cmw.print(cmd);
			}
			var detailPanel = new Ext.ux.panel.DetailPanel({
						width : 780,
						detailCfgs : detailCfgs_1,
						attachLoad : function(params, cmd) {
							/* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
							// Cmw.print(params);
							// Cmw.print(cmd);
						}
					});

			var attachMentFs = this.createAttachMentFs(this);
			detailPanel.add(attachMentFs);
			return detailPanel;
		},

		/**
		 * 创建附件FieldSet 对象
		 * 
		 * @return {}
		 */
		createAttachMentFs : function(_this, dir, params) {	
			var dir = 'ecustomerinfo_path';
		/*
		 * ----- 参数说明： isLoad 是否实例化后马上加载数据 dir : 附件上传后存放的目录， mortgage_path
		 * 定义在 resource.properties 资源文件中 isSave : 附件上传后，是否保存到 ts_attachment
		 * 表中。 true : 保存 params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中
		 * ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔} 当 isLoad = false 时，
		 * params 可在 reload 方法中提供 ----
		 */
		var cfg = {
			title : '相关材料附件',
			isLoad : true,
			dir : dir,
			isSave : true,
			disabled : true,
			isNotDisenbaled : true
		};
		if (!params) {
			var uuid = Cmw.getUuid();
			params = {
//				formType : -1,
//				formId : uuid,
//				sysId : _this.params.sysId,
				isNotDisenbaled : true
			};
		}
		cfg.params = params;
		var attachMentFs = new Ext.ux.AppAttachmentFs(cfg);
		return attachMentFs;
	

},

		/**
		 * 关闭窗口
		 */
		close : function() {
			this.appWin.hide();
		},
		/**
		 * 销毁组件
		 */
		destroy : function() {
			this.appWin.close();
			this.appWin = null; // 释放当前窗口对象引用
		}
	};
});
