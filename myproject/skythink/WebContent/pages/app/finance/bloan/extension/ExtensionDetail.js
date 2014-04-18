/**
 * 展期想起UI
 * @author 彭登浩 
 * @date 2013-11-07 03:40:57
 */
define(function(require, exports) {
	exports.WinEdit = {
		gopinionGrid : null,
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
			this.createGopinionGrid();
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
		 * 创建担保人意见Grid
		 */
		createGopinionGrid : function(){
			var structure_1 = [
			{
			    header: '担保人',
			    name: 'guarantor'
			},
			{
			    header: '法定/授权代表人',
			    name: 'legal',
			    width: 120
			},
			{
			    header: '担保人意见',
			    name: 'opinion',
			    width: 500
			},
			{
			    header: '签字日期',
			    name: 'signDate',
			    width: 80
			}];
			
			this.gopinionGrid = new Ext.ux.grid.AppGrid({
			    title: '担保人意见列表',
			    structure: structure_1,
			    height:250,
			    url: './fcGopinion_list.action',
			    needPage: false,
			    isLoad: false,
			    autoScroll : true,
			    keyField: 'id'
			});
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
					'<tr><th col="code">编号</th> <td col="code" >&nbsp;</td><th col="extAmount">展期金额</th> <td col="extAmount" >&nbsp;</td><th col="rate">展期贷款利率</th> <td col="rate" >&nbsp;</td></tr>',
					'<tr><th col="limitLoan">贷款期限</th> <td col="limitLoan" >&nbsp;</td><th col="limitExtDate">展期期限</th> <td col="limitExtDate" >&nbsp;</td><th col="isadvance">是否预收息</th> <td col="isadvance" >&nbsp;</td></tr>',
					'<tr><th col="mgrtype">管理费收取方式</th> <td col="mgrtype" >&nbsp;</td><th col="mrate">管理费率</th> <td col="mrate" >&nbsp;</td><th col="applyMan">申请人</th> <td col="applyMan" >&nbsp;</td></tr>',
					'<tr><th col="applyDate">申请日期</th> <td col="applyDate" >&nbsp;</td><th col="comeDate">收到申请书日期</th> <td col="comeDate" >&nbsp;</td><th col="manager">经办人</th> <td col="manager" >&nbsp;</td></tr>',
					'<tr><th col="paySource">还款来源</th> <td col="paySource" colspan=5>&nbsp;</td></tr>',
					'<tr><th col="extReason">展期原因</th> <td col="extReason" colspan=5>&nbsp;</td></tr>'
					];
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
						url : './fcExtension_get.action',
						prevUrl : './fcExtension_prev.action',
						nextUrl : './fcExtension_next.action',
						params : {
							id : _this.formId
						},
						callback : {
							sfn : function(jsonData) {
								if(jsonData){
									var extAmount  = jsonData["extAmount"];
									jsonData["extAmount"] =  Cmw.getThousandths(extAmount)+"元&nbsp;&nbsp;<br/><span style='color:red;font-weight:bold;'>("+Cmw.cmycurd(extAmount)+")</span>";
									jsonData["rate"] = Render_dataSource.rateTypeRender(jsonData["rateType"]) +"<span style='color : red;font-weight:bold;'>("+jsonData["rate"]+"%)</span>";
									jsonData["limitLoan"] = "<span style='color : red;font-weight:bold;'>(自"+jsonData["estartDate"] +"起  至"+ jsonData["eendDate"]+"止)</span>";;
									jsonData["limitExtDate"] =  "<span style='color : red;font-weight:bold;'>(自"+jsonData["estartDate"]+"起  至"+jsonData["estartDate"]+"止)</span>";
									jsonData["isadvance"] =  Render_dataSource.isadvanceRender(jsonData["isadvance"]);
									jsonData["mgrtype"] = Render_dataSource.mgrtype_render(jsonData["mgrtype"].toString());
									jsonData["mrate"] = jsonData["mrate"]+"%";
									
									var sysId = _this.sysid;
						        	var formId = _this.formId;
						        	if(!formId){
						        		formId = -1;
						        	}
						        	var params = {sysId:sysId,formType:ATTACHMENT_FORMTYPE.FType_31,formId:formId};
						        	_this.attachMentFs.reload(params);
						        	_this.gopinionGrid.reload({extensionId:formId});
								}
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
			detailPanel_1.add(this.gopinionGrid);
			return detailPanel_1;
		}
	};
});
