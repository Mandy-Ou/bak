/**
 * @author 李听 评审会议决议
 * 
 */

define(function(require, exports) {
	exports.viewUI = {
		appMainPanel : null,
		tab : null,
		params : null,
		iframeId : null,
		/**
		 * 获取主面板
		 * @param tab
		 *            Tab 面板对象
		 * @param params
		 *            由菜单点击所传过来的参数 {tabId : tab 对象ID,apptabtreewinId :
		 *            系统程序窗口ID,sysid : 系统ID}
		 */
		getMainUI : function(tab, params) {
			/*
			 * 由必做或选做业务菜单传入的回調函数，主要功能： finishBussCallback :
			 * 当业务表单保存后，更新必做或选做事项为已做, unFinishBussCallback : 当删除业务表单后，取消已做标识
			 */
			var finishBussCallback = tab.finishBussCallback;
			this.setParams(tab, params);
			if (!this.appMainPanel) {
				this.createCmpts();
			}
			var _this = this;
			tab.notify = function(_tab) {
				_this.setParams(_tab, _tab.params);
				_this.refresh();
			};
			return this.appMainPanel;
		},
		/**
		 * 创建组件
		 */
		createCmpts : function() {
			this.iframeId = Ext.id(null, "sureyAuditIframeId");
			var htmlArr = ['<iframe id="'
					+ this.iframeId
					+ '" name="'
					+ this.iframeId
					+ '"  style="width:100%; height:100%;overflow:auto;" frameborder="0"></iframe>'// ,
			];
			var tbar = this.getToolBar();
			this.appMainPanel = new Ext.Panel({
						tbar : tbar,
						html : htmlArr.join(" ")
					});
		},
		getToolBar : function() {
			var _this = this;
			var toolBar = null;
			var barItems = [{
				token : "编辑",
				text : Btn_Cfgs.MODIFY_BTN_TXT,
				id : "ED_TL_C1_C1_ET3",
				iconCls : Btn_Cfgs.MODIFY_CLS,
				tooltip : Btn_Cfgs.MODIFY_TIP_BTN_TXT,
				handler : function() {
					toolBar.disableButtons(Btn_Cfgs.MODIFY_BTN_TXT + ","
							+ Btn_Cfgs.PRINT_BTN_TXT);
					toolBar.enableButtons(Btn_Cfgs.SAVE_BTN_TXT);
					_this.makeDatas(1, this);
				}
			}, {
				token : "保存",
				text : Btn_Cfgs.SAVE_BTN_TXT,
				iconCls : Btn_Cfgs.SAVE_CLS,
				tooltip : Btn_Cfgs.SAVE_TIP_BTN_TXT,
				handler : function() {
					toolBar.enableButtons(Btn_Cfgs.MODIFY_BTN_TXT + ","
							+ Btn_Cfgs.PRINT_BTN_TXT);
					toolBar.disableButtons(Btn_Cfgs.SAVE_BTN_TXT);
					_this.makeDatas(2);
				}
			}, {
				token : "打印",
				text : Btn_Cfgs.PRINT_BTN_TXT,
				iconCls : Btn_Cfgs.PRINT_CLS,
				tooltip : Btn_Cfgs.PRINT_TIP_BTN_TXT,
				handler : function() {
					_this.makeDatas(3);
				}
			}];
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({
						aligin : 'left',
						controls : barItems
					});
			return toolBar;
		},
		setParams : function(tab, params) {
			this.tab = tab;
			this.params = params;
		},
		refresh : function() {
			if (!this.appMainPanel) {
				return null;
			}
			if (!this.appMainPanel.rendered) {
				var _this = this;
				this.appMainPanel.addListener('render', function(cmpt) {
							_this.loadDatas();
						});
			} else {
				this.loadDatas();
			}
		},
		
		/**
		 * 处理数据
		 * 
		 * @param actionType
		 *            动作类型 [1:编辑,2:保存,3:打印]
		 */
		makeDatas : function(actionType, Object) {
			var tab = this.tab;
			var iframeEle = window.parent.frames[this.iframeId];
			if (!iframeEle)
				return;
			switch (actionType) {
				case 1 : {/* 编辑 */
					var EleName = iframeEle.document.getElementsByName("ids");
					iframeEle.document.getElementById("breedsd").disabled=false;
					this.spanHideSave("appAmounts","appAmount");
//					var breed = this.spanHideSave("breeds","breed");
					var appAmount = this.spanHideSave("appAmounts","appAmount");
					var date = this.spanHideSave("dates","date");//期限
//					 var yearLoan = this.spanSave("yearLoans","yearLoan");
//					 var monthLoan = this.spanSave("monthLoans","monthLoan");
//					 var dayLoan = this.spanSave("dayLoans","dayLoan");
//					 var situation = this.spanHideSave("situations","situation");
					 var situation = this.textHidArea("situation","situations");//企业基本情况
					 var address = this.spanHideSave("addresss","address");
					 var hostMan = this.spanHideSave("hostMans","hostMan");
			 		 var auditDate = this.spanHideSave("auditDates","auditDate");//评审会时间
					 var hostMan = this.spanHideSave("recordMans","recordMan");//记录人
					break;
				}
				case 2 : {/* 保存 */
					var iframeEle = window.parent.frames[this.iframeId];
					if (!iframeEle)
						return;
					var applyId = this.params.applyId;
					var custType = this.params.custType;
					var customerId = this.params.customerId;
					Cmw.print(this.params);
					var appAmount=	this.spanSave("appAmounts","appAmount").value;
//					var breed = this.spanSave("breeds","breed").value;
					var breed = iframeEle.$("#breedsd").val(); 
					var date = this.spanSave("dates","date").value;
					var yearLoan=date.substring(0,4);
					var monthLoan=date.substring(5,7);
					var dayLoan=date.substring(8,10);
					var appAmount = this.spanSave("appAmounts","appAmount").value;
					 var situation = this.textArea("situation","situations").value;//企业基本情况
					 var address = this.spanSave("addresss","address").value;
					 var hostMan = this.spanSave("hostMans","hostMan").value;
					 var recordMan = this.spanSave("recordMans","recordMan").value;
//					 var opinion = this.spanSave("opinions","opinion").value;//小组成员意见
					 var resultTag = this.spanSave("resultTags","resultTag").value;
					 var auditDate = this.spanSave("auditDates","auditDate").value;
					EventManager.get('./fcAppraise_save.action',{
								params : {
									breed : breed,
									appAmount : appAmount,
									yearLoan : yearLoan,
									monthLoan : monthLoan,
									dayLoan : dayLoan,
									situation : situation,
									auditDate : auditDate,
									address : address,
									custType:custType,
									customerId:customerId,
									applyId:applyId,
									hostMan : hostMan,
									recordMan : recordMan,
//									opinion : opinion,
									resultTag : resultTag
								},
								sfn : function(json_data) {
									Cmw.print(json_data);
									 ExtUtil.alert({msg:'数据保存成功'});
									 iframeEle.document.getElementById("breedsd").disabled=true;//保存成功之后将字段禁用掉
								},ffn:function(json_data){
								 	ExtUtil.alert({msg:'数据保存失败'});
								}
							})
					break;
				}case 3 : {/* 打印 */
			var iframeEle = window.parent.frames[this.iframeId];
			if (!iframeEle){
			ExtUtil.alert({msg:'没有可打印的内容！'});
			return;
			}
			var ids=iframeEle.document.getElementById("tbody").innerHTML;
				PrintManager.print(ids);
				break;
				}
			}
		},
		/**
		 * 对span元素赋值的方法:ids是span元素的id
		 * is是input元素的id
		 * 开始的时候默认input元素是隐藏的\
		 * @author 李听
		 */
		spanSave:function(ids,id){
			var iframeEle = window.parent.frames[this.iframeId];
			if (!iframeEle)
				return;
				if(ids&&id){
			var getSpan=iframeEle.document.getElementById(ids);
			var getInput= iframeEle.document.getElementById(id);
			if(getSpan&&getInput){
			getSpan.innerHTML=getInput.value;
			getInput.style.display="none"; 
			getSpan.style.display="block"; 
			}
			return getInput;
			}
		},
		/**
		 * 处理textArea的赋值
		 * 
		 */
		textArea :function(id,ids){
		var iframeEle = window.parent.frames[this.iframeId];
			if (!iframeEle)
				return;
				if(id&&ids){
			var textSpan=iframeEle.document.getElementById(id);
			var spanTx=iframeEle.document.getElementById(ids);
				if(textSpan&&spanTx){
			spanTx.innerHTML=textSpan.value;
			textSpan.style.display="none"; //隐藏
			spanTx.style.display="block"; //显示
			}
			return textSpan;
				}
		},
		/**
		 * 处理textArea的赋值
		 * 
		 */
		textHidArea :function(id,ids){
		var iframeEle = window.parent.frames[this.iframeId];
			if (!iframeEle)
				return;
				if(id&&ids){
			var textSpan=iframeEle.document.getElementById(id);
			var spanTx=iframeEle.document.getElementById(ids);
				if(textSpan&&spanTx){
					textSpan.value=spanTx.innerHTML;
					textSpan.style.display="block"; //隐藏
					spanTx.style.display="none"; //显示
			}
			return spanTx;
				}
		},
		/**
		 * 对span元素赋值的方法:ids是span元素的id
		 * is是input元素的id
		 * 开始的时候默认input元素是隐藏的
		 * @author 李听
		 */
		spanHideSave:function(ids,id){
			var iframeEle = window.parent.frames[this.iframeId];
			if (!iframeEle)
				return;
				if(ids&&id){
			var getSpan=iframeEle.document.getElementById(ids);
			var getInput= iframeEle.document.getElementById(id);
			getInput.value=getSpan.innerHTML;
			getSpan.style.display="none"; 
			getInput.style.display="block"; 
			}
		},
		/**
		 * 在这里加载数据
		 */
		loadDatas : function(printaction) {
			this.appMainPanel.doLayout();
			var projectId = this.params.applyId;
			var custType = this.params.custType;
			var sysid = this.params.sysid;
			var customerId = this.params.customerId;
			var module = "AppraiseSurvey.jsp";
			var url = "pages/app/workflow/bussProcc/formUIs/appraise/"
					+ module;
			var parentContainerId = this.appMainPanel.getId();
			var tabId = this.params.tabId;
			var pars = "?parentContainerId=" + parentContainerId
					+ "&projectId=" + projectId + "&custType=" + custType
					+ "&customerId=" + customerId + "&tabId=" + tabId+"&sysid="+sysid;
			if (printaction)
				pars += "&printaction=" + printaction;
			var iframeEle = Ext.get(this.iframeId);
			if (!iframeEle)
				return;
			Cmw.mask(this.appMainPanel, Msg_SysTip.msg_loadingUI);
			window.parent.frames[this.iframeId].location = url + pars;
		},
		resize : function(adjWidth, adjHeight) {
			this.appMainPanel.setWidth(adjWidth);
			this.appMainPanel.setHeight(adjHeight);
		},
		
		/**
		 * 组件销毁方法
		 */
		destroy : function() {
			if (null != this.appMainPanel) {
				this.appMainPanel.destroy();
				this.appMainPanel = null;
			}
			if (this.iframeId) {
				var iframeEle = Ext.get(this.iframeId);
				if (iframeEle)
					iframeEle.remove();
			}
		}
	}

});