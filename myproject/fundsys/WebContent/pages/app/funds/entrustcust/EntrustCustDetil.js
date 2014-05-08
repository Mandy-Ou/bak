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
		selId:null,
		detailPanlId : Ext.id(null, 'OneCustLoanDetailPanl'),
		bussKey : null,/* 钥匙KEY */
		sysId : null,
		attachMentFs:null,
		appgrid_1 : null,
		contractId : null,
		setParams : function(parentCfg) {
			this.parentCfg = parentCfg;
			this.parent = parentCfg.parent;
			this.sysId = parentCfg.parent.sysId;
			this.selId=this.parent.selId;
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
			var params = {sysId:-1,formType:ATTACHMENT_FORMTYPE.FType_43,formId:this.parent.getSelId()};
        	this.attachMentFs.clearAll();
        	this.attachMentFs.reload(params);
			this.appWin.optionType = this.optionType;
			this.appWin.show(this.parent.getEl());
			this.loadData();
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
			if (_this.parentCfg.self.refresh){
			_this.parentCfg.self.refresh(_this.optionType, data);
			}
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
					'<tr><th colspan="15" style="font-weight:bold;text-align:left;" >&nbsp;&nbsp;基本信息>>></th><tr>',
					'<tr><th col="code">编号</th> <td col="code" >&nbsp;</td><th col="name">姓名</th> <td col="name" >&nbsp;</td><th col="sex">性别</th> <td col="sex" >&nbsp;</td></tr>',
					'<tr><th col="birthday">出生日期</th> <td col="birthday" >&nbsp;</td><th col="cardNum">身份证号码</th> <td col="cardNum" >&nbsp;</td><th col="job">职务</th> <td col="job" >&nbsp;</td></tr>',
					'<tr><th col="maristal">婚姻</th> <td col="maristal" >&nbsp;</td><th col="prange">委托产品范围</th> <td col="prange" >&nbsp;</td><th col="products">委托产品</th> <td col="products" >&nbsp;</td></tr>',
					'<tr><th col="nation">民族</th> <td col="nation" >&nbsp;</td><th col="inAddress">现居住地址</th> <td col="inAddress" >&nbsp;</td><th col="homeNo">住宅号</th> <td col="homeNo" >&nbsp;</td></tr>',
					'<tr><th colspan="6" style="font-weight:bold;text-align:left;" >&nbsp;&nbsp;委托意向信息>>></th><tr>',
					'<tr><th col="ctype">委托人类型</th> <td col="ctype" >&nbsp;</td><th col="appAmount">委托金额</th> <td col="appAmount" >&nbsp;</td><th col="deadline">委托期限</th> <td col="deadline" >&nbsp;</td></tr></tr>',
					'<tr><th colspan="6" style="font-weight:bold;text-align:left;" >&nbsp;&nbsp;联系信息>>></th><tr>',
					'<tr><th col="phone">手机</th> <td col="phone" >&nbsp;</td><th col="contactTel">联系电话</th> <td col="contactTel" >&nbsp;</td><th col="contactor">联系人</th> <td col="contactor" >&nbsp;</td></tr>',
					'<tr><th col="email">电子邮件</th> <td col="email">&nbsp;</td><th col="fax">传真</th> <td col="fax" >&nbsp;</td><th col="qqmsnNum">QQ或MSN号码</th> <td col="qqmsnNum" >&nbsp;</td></tr>',
					'<tr><th col="workOrg">工作单位</th> <td col="workOrg">&nbsp;</td><th col="workAddress">单位地址</th> <td col="workAddress">&nbsp;</td><th col="hometown">籍贯</th> <td col="hometown" >&nbsp;</td></tr>'];
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
						url : './fuEntrustCust_lget.action',
						callback : {
							sfn : function(jsonData) {
								if(jsonData["maristal"]){
							jsonData["maristal"]=	Render_dataSource.gvlistRender('100003',jsonData["maristal"]);}
								if(jsonData["hometown"]){
							jsonData["hometown"]=	Render_dataSource.gvlistRender('100004',jsonData["hometown"]);}
								if(jsonData["nation"]){
								jsonData["nation"]=Render_dataSource.gvlistRender('100005',jsonData["nation"]);}
								if(jsonData["degree"]){
							jsonData["degree"]=	Render_dataSource.gvlistRender('100006',jsonData["degree"]);}
								jsonData["sex"]=Render_dataSource.entrussexRender(jsonData["sex"]);
								jsonData["ctype"]=Render_dataSource.eCusRender(jsonData["ctype"]);
								jsonData["appAmount"]=Render_dataSource.moneyRender(jsonData["appAmount"]);
								jsonData["prange"]=Render_dataSource.ecustIdRender(jsonData["prange"]);
							}
						}
					}];
			var detailPanel = new Ext.ux.panel.DetailPanel({
						width : 780,
						detailCfgs : detailCfgs_1
					});

			this.attachMentFs = this.createAttachMentFs(this);
			detailPanel.add(this.attachMentFs);
			return detailPanel;
		},

		/**
		 * 创建附件FieldSet 对象
		 * 
		 * @return {}
		 */
		createAttachMentFs : function(_this, dir, params) {
					/*
					 * ----- 参数说明： isLoad 是否实例化后马上加载数据 dir : 附件上传后存放的目录，
					 * mortgage_path 定义在 resource.properties 资源文件中 isSave :
					 * 附件上传后，是否保存到 ts_attachment 表中。 true : 保存 params :
					 * {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment
					 * 中的说明,formId:业务单ID，如果有多个用","号分隔} 当 isLoad = false 时，
					 * params 可在 reload 方法中提供 ----
					 */
					var uuid=Cmw.getUuid();
					var attachMentFs = new Ext.ux.AppAttachmentFs({
								title : '相关材料附件',
								isLoad : false,
								dir : 'mort_path',
								isSave : true,
								isNotDisenbaled : true,
								params:{sysId:-1,formType:ATTACHMENT_FORMTYPE.FType_43,formId:_this.selId}
							});
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
