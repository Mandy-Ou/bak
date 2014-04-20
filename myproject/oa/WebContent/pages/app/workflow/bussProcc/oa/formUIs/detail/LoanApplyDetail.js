/**
 * 借款申请单详情
 * @author 程明卫
 * @Date 2014-02-04
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		params : null,
		optionType : OPTION_TYPE.DETAIL,/* 默认为详情  */
		detailPnl : null,	//详情面板
		appWin : null,
		attachMentFs : null,/*附件对象*/
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.params = parentCfg.params;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.detailPnl = this.createDetailPnl();
			this.appWin = new Ext.ux.window.AbsDetailWindow({width:750,height:280,getParams:this.getParams,appDetailPanel : this.detailPnl,
			optionType : this.optionType, refresh : this.refresh
			});
		},
		/**
		 * 显示弹出窗口
		 * @param _parentCfg 弹出之前传入的参数
		 */
		show : function(_parentCfg){
			if(_parentCfg) this.setParentCfg(_parentCfg);
			if(!this.appWin){
				this.createAppWindow();
			}
			this.appWin.optionType = this.optionType;
			var el = (this.parent && this.parent["getEl"]) ? this.parent.getEl() : this.parent;
			this.appWin.show(el);
		},
		/**
		 * 销毁组件
		 */
		destroy : function(){
			this.appWin.close();	//关闭并销毁窗口
			this.appWin = null;		//释放当前窗口对象引用
		},
		/**
		 * 获取选中的记录参数
		 */
		getParams : function(){
			exports.WinEdit.appWin.apptbar.hideButtons(Btn_Cfgs.PREVIOUS_BTN_TXT+","+Btn_Cfgs.NEXT_BTN_TXT);
			var params = exports.WinEdit.params;
			var id = params.id;
			if(!id){
				ExtUtil.error({msg:"在调用 getParams 方法时，发现没有为 params 提供 id 值!"});
				return null;
			}
			var params = {id:id};
			return params;
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			
		},
		/**
		 * 创建详情面板
		 */
		createDetailPnl : function(){
			var _this = this;
			var htmlArrs_1 = [
					'<tr><th col="code">编号</th> <td col="code" >&nbsp;</td><th col="loanMan">借款人</th> <td col="loanMan" >&nbsp;</td><th col="amount">借款金额</th> <td col="amount" >&nbsp;</td></tr>',
					'<tr><th col="loanDate">借款日期</th> <td col="loanDate" >&nbsp;</td><th col="loanType">还款类型</th> <td col="loanType" >&nbsp;</td><th col="payDate">预计还款日期</th> <td col="payDate" >&nbsp;</td></tr></tr>',
					'<tr><th col="accountNum">借款帐号</th> <td col="accountNum" >&nbsp;</td><th col="accountName">帐户名</th> <td col="accountName" >&nbsp;</td><th col="bankName">开户行</th> <td col="bankName" >&nbsp;</td></tr></tr>',
					'<tr><th col="reason">借款事由</th> <td col="reason"  colspan=5 >&nbsp;</td></tr>'];
			
			var  detailCfgs = [{
				cmns : 'THREE',
				 model : 'single',
				 labelWidth : 90,
				 title : '借款申请单详情',
				 i18n : UI_Menu.appDefault,		//国际化资源对象
				 htmls : htmlArrs_1,
				 url : './oaLoanApply_detail.action',
				 params : {id : -1},
				 callback : {
					sfn : function(jsonData) {
						_this.renderDispData(jsonData);
					}
				 }
			}];
			var cfg = {width:750,isLoad:false,detailCfgs:detailCfgs};
			this.attachMentFs = this.createAttachMentFs(_this);
			var detailPnl = new Ext.ux.panel.DetailPanel(cfg);
			detailPnl.add(this.attachMentFs);
			return detailPnl;
		},
		renderDispData : function(jsonData) {
			var _this = this;
			var id = jsonData["id"];
			var loanMan = jsonData["loanMan"];
			var deptName = jsonData["deptName"];
			var amount =  jsonData["amount"];
			var loanDate = jsonData["loanDate"];
			var rendType = jsonData["rendType"];
			var loanType = jsonData["loanType"];
			jsonData["loanMan"] = loanMan + "&nbsp;&nbsp;<span style='color:red;font-weight:bolid;'>("+deptName+")</span>";
			jsonData["amount"] = Cmw.getThousandths(amount) +"&nbsp;"+"<span style='color:red;font-weight:bold;'>("+Cmw.cmycurd(amount)+")</span>";
			rendType = OaRender_dataSource.rendTypeRender(rendType+"");
			jsonData["loanDate"] = loanDate + "&nbsp;&nbsp;<span style='color:red;font-weight:bolid;'>("+rendType+")</span>";
			jsonData["loanType"] = OaRender_dataSource.gvlistRender('300003',loanType);
			this.reloadAttachments(id);
		},/**
		 * 创建附件FieldSet 对象
		 * 
		 * @return {}
		 */
		createAttachMentFs : function(_this) {
			/*
			 * ----- 参数说明： isLoad 是否实例化后马上加载数据 dir : 附件上传后存放的目录， mortgage_path
			 * 定义在 resource.properties 资源文件中 isSave : 附件上传后，是否保存到 ts_attachment
			 * 表中。 true : 保存 params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中
			 * ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔} 当 isLoad = false 时，
			 * params 可在 reload 方法中提供 ----
			 */
			var sysId = this.params.sysid;
			var uuid = Cmw.getUuid();
			var _params = this.getAttachParams(uuid);
			var attachMentFs = new Ext.ux.AppAttachmentFs({
						title : '相关材料附件',
						isLoad : false,
						dir : 'mort_path',
						isSave : true,
						params:_params,
						isNotDisenbaled : true
					});
			return attachMentFs;
		},
		/**
		 * 重新加载附件对象的数据
		 * @params formId 业务单ID
		 */
		reloadAttachments : function(formId){
        	if(!formId){
        		ExtUtil.error({msg:'申请单ID参数："formId" 不能为空!'});
        	}
        	var _params = this.getAttachParams(formId);
        	this.attachMentFs.reload(_params);
		},
		/**
		 * 获取附件参数
		 * @params formId 业务单ID
		 */
		getAttachParams : function(formId){
			var sysId = this.params.sysid;
			var _params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_11,sysId:sysId,isNotDisenbaled:true};
			return _params;
		}
	};
});