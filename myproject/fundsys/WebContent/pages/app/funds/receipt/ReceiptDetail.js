/**
 * 汇票收条详情
 * @author 郑符明
 */
define(function(require, exports) {
	exports.WinEdit = {
//		gopinionGrid : null,
		attachMentFs : null,
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增 */
		detailPnl : null, // 详情面板
		appWin : null,
		setParentCfg : function(parentCfg) {
			if(parentCfg.formId){
				this.formId = parentCfg.formId;
				this.sysid = parentCfg.sysid;
			}else{
				this.parent = parentCfg.parent;
			}
			// --> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.DETAIL;
		},
		createAppWindow : function() {
//			this.createGopinionGrid();
			this.createAttachMentFs();
			this.detailPnl = this.createDetailPnl();
			this.appWin = new Ext.ux.window.AbsDetailWindow({
						width : 900,
						getParams : this.getParams,
						appDetailPanel : this.detailPnl,
						optionType : this.optionType,
						refresh : this.refresh,
						hiddenBtn: true
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
				this.setParentCfg(_parentCfg);
			if (!this.appWin) {
				this.createAppWindow();
			}
			if(_parentCfg.ele){
				this.appWin.show(_parentCfg.ele.frame("C3DAF9", 1, { duration: 1 }));
			}else{
				this.appWin.show();
			}
		},
		/**
		 * 销毁组件
		 */
		destroy : function() {
			if (!this.appWin)
				return;
			this.appWin.close(); // 关闭并销毁窗口
			this.appWin = null; // 释放当前窗口对象引用
		},
		/**
		 * 获取选中的记录参数
		 */
		getParams : function() {
			var _this = exports.WinEdit;
			var parent = _this.parent;
			var selId  = null;
			var sysId = null;
			if(parent){
				var selId = parent.getSelId();
			}else{
				selId = _this.formId;
				sysId = _this.sysid;
			}
			var params = {formId:selId,sysId:sysId};
			return params;
		},
		/**
		 * 当数据保存成功后，执行刷新方法 [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data) {
			var _this = exports.WinEdit;
			if (_this.parentCfg.self.refresh)
				_this.parentCfg.self.refresh(_this.optionType, data);
		},
		
		/**
		 * 创建附件FieldSet 对象
		 * @return {}
		 */
		createAttachMentFs : function(){
			/* ----- 参数说明： isLoad 是否实例化后马上加载数据 
			 * dir : 附件上传后存放的目录， mortgage_path 定义在 resource.properties 资源文件中
			 * isSave : 附件上传后，是否保存到 ts_attachment 表中。 true : 保存
			 * params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔}
			 *    当 isLoad = false 时， params 可在 reload 方法中提供
			 *  ---- */
			this.attachMentFs = new  Ext.ux.AppAttachmentFs({title:'展期相关附件上传',isLoad:false,dir : 'extension_path',isSave:true});
		},
		/**
		 * 创建详情面板
		 */
		createDetailPnl : function() {
			var _this = exports.WinEdit;
			var parent = exports.WinEdit.parent;
			var htmlArrs = null;

			var htmlArrs_1 = [
					'<tr><th col="rnum">票号</th> <td col="rnum" >&nbsp;</td><th col="name">客户姓名</th> <td col="name" >&nbsp;</td><th col="outMan">出票人</th> <td col="outMan" >&nbsp;</td></tr>',
					'<tr><th col="outDate">出票日期</th> <td col="outDate" >&nbsp;</td><th col="endDate">到票日期</th> <td col="endDate" >&nbsp;</td><th col="amount">金额</th> <td col="amount" >&nbsp;</td></tr>',
					'<tr><th col="rtacname">收款人账户名</th> <td col="rtacname" >&nbsp;</td><th col="rtaccount">收款人账号</th> <td col="rtaccount" >&nbsp;</td><th col="rtbank">收款人开户行</th> <td col="rtbank" >&nbsp;</td></tr>',
					'<tr><th col="omaccount">出票人账号</th> <td col="omaccount" >&nbsp;</td><th col="reman">收条接收人</th> <td col="reman" >&nbsp;</td><th col="amount">金额</th> <td col="amount" >&nbsp;</td></tr>',
					'<tr><th col="pbank">付款行</th> <td col="pbank" >&nbsp;</td><th col="rcount">汇票数量(单位:张)</th> <td col="rcount" >&nbsp;</td><th col="recetDate">收条签收日期</th> <td col="recetDate" >&nbsp;</td></tr>'];
			var detailCfgs_1 = [{
						cmns : 'THREE',
						model : 'single',
						labelWidth : 90,
						htmls : htmlArrs_1,
						url : './fuReceipt_get.action',
						params : {
							id : _this.formId
						},
						callback : {
							sfn : function(jsonData) {
									var sysId = _this.sysid;
						        	var formId = _this.formId;
						        	if(!formId){
						        		formId = -1;
						        	}
						        	var params = {sysId:sysId,formType:ATTACHMENT_FORMTYPE.FType_31,formId:formId};
						        	_this.attachMentFs.reload(params);
								}
							}
						}];
			var attachLoad = function(params, cmd) {
				/* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
				// Cmw.print(params);
				// Cmw.print(cmd);
			}
			var detailPanel_1 = new Ext.ux.panel.DetailPanel({
						autoWidth : true,
						detailCfgs : detailCfgs_1,
						attachLoad : function(params, cmd) {
							/* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
							// Cmw.print(params);
							// Cmw.print(cmd);
						}
					});
			detailPanel_1.add(this.attachMentFs);
			return detailPanel_1;
		}
	};
});
