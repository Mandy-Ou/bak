/**
 * 合同详情
 * @author 李听
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
			this.detailPnl.reload();
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
			var htmlArrs_1 = [
					'<tr><th colspan="15" style="font-weight:bold;text-align:left;" >&nbsp;&nbsp;委托人信息>>></th><tr>',    
					'<tr><th col="code">委托合同编号</th> <td col="code" >&nbsp;</td><th col="name">姓名</th> <td col="name">&nbsp;</td><th col="sex">性别</th> <td col="sex">&nbsp;</td></tr>',
					'<tr><th col="birthday">出生日期</th> <td col="birthday" >&nbsp;</td><th col="cardNum">身份证号码</th> <td col="cardNum" >&nbsp;</td><th col="appAmount">委托金额</th> <td col="appAmount" >&nbsp;</td></tr>',
					'<tr><th col="products">委托产品</th> <td col="products" >&nbsp;</td><th col="maristal">婚姻状况</th> <td col="maristal" >&nbsp;</td><th col="job">职务</th> <td col="job" >&nbsp;</td></tr>'
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
						url : './fuEntrustCust_get.action',
						params : {
							id : _this.formId
						},
						callback : {
							sfn : function(jsonData) {
								jsonData["appAmount"]=Cmw.getThousandths(jsonData["appAmount"])+'元'; 
								 var tdote=new Date(jsonData["birthday"]);//转换为时间格式（返回值类型是时间）
							      jsonData["birthday"]=Ext.util.Format.date(tdote,'Y-m-d');//进行时间的格式化
							      switch(jsonData["appAmount"]){
							      case '0':
							    	  return jsonData["sex"]='男'
							      break;
							      case '1':
							    	  return jsonData["sex"]='女'
							      break;
							      }
								/*
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
							*/}
						}
					}];
			var detailPanel_1 = new Ext.ux.panel.DetailPanel({
						autoWidth : true,
						detailCfgs : detailCfgs_1
					});
			detailPanel_1.add(this.attachMentFs);
			return detailPanel_1;
		}
	};
});
