/**
 * 流程审批主页面 ---> 业务事项菜单
 * @author 程明卫
 * @date 2012-12-26
 */
define(function(require, exports) {
	exports.moduleUI = {
		wh : [],
		auditTabPanel : null,	/*放审批表单的父面板*/
		bussMakeTabPanel : null,/*父页面的业务表单显示面板*/
		bussUiMgr : null,/*业务面板控制类*/
		menuTipCmpt : null,/*菜单提示组件*/
		bussFormDatas : null,/*业务事项菜单数据*/
		params : null,
		callback : null,/*回调函数*/
		menuLabel : null,
		idMgr : null,
		mustFst : null,/*必做业务事项  FeldSet*/
		mustMenusPanel : null,/*必做业务菜单 Panel*/
		mabeFst : null,/*选做业务事项  FeldSet*/
		mabeMenusPanel : null,/*选做业务菜单 Panel*/
		selMenusPanel : null,/*当前选中的扁平菜单面板*/
		selMenuEleId : null,/*选中的菜单元素对象ID*/
		selMenuData : null,/*选中的菜单数据*/
		bussCounterMgr : null,/*必做和选做计数器*/
		mustOrMabeTag : 0,/*标识当前是处在必做还是选做面板状态[1:必做，2：选做]*/
		/**
		 * 设置参数
		 */
		setParams : function(params) {
			this.params = params;
			this.bussFormDatas = params.bussFormDatas;
			if(this.bussFormDatas && Ext.isString(this.bussFormDatas)){
				this.bussFormDatas = Ext.decode(this.bussFormDatas);
			}
			if(!this.auditTabPanel) this.auditTabPanel = params.auditTabPanel;
			if(!this.bussMakeTabPanel) this.bussMakeTabPanel = params.bussMakeTabPanel;
			this.setCounterMgr();
		},
		/**
		 * 显示面板并加载数据
		 */
		show : function(params){
			this.refresh(params);
			//if(this.menuTipCmpt) this.menuTipCmpt.show();
		},
		initIdMgr : function(){
			this.idMgr =  {
				menuLabelId : Ext.id(null,'bussMakeMenuId'),/*菜单事项总提示ID*/
				msgTipImgId :  Ext.id(null,'msgTipImgId'),/*消息提示图片ID*/
				msgCountSpanId : Ext.id(null,'msgCountSpanId')/*必做/选做事项数量显示 Span ID*/
			};
		},
		/**
		 * 加载客户信息数据
		 */
		setCounterMgr : function() {
			var mustForms = null;
			var mabeForms = null;
			if(this.bussFormDatas){
			    mustForms = this.bussFormDatas.mustForms;
				mabeForms = this.bussFormDatas.mabeForms;
			}
			
			if(!this.bussCounterMgr) this.bussCounterMgr = {};
			if(mustForms && mustForms.length > 0){
				var mustTotalCount = mustForms.length;
				this.bussCounterMgr.mustTotalCount = mustTotalCount;
				this.bussCounterMgr.fmustCount = getFinishCount(mustForms);
			}
			if(mabeForms && mabeForms.length > 0){
				var mabeTotalCount = mabeForms.length;
				this.bussCounterMgr.mabeTotalCount = mabeTotalCount;
				this.bussCounterMgr.fmabeCount = getFinishCount(mabeForms);
			}
			
			/**
			 * 从菜单数据中获取已完成的业务菜单数量
			 * @param datas 事项菜单数组
			 */
			function getFinishCount(datas){
				var fcount = 0;
				for(var i=0,count=datas.length; i<count; i++){
					var data = datas[i];
					var finish = data.finish;
					if(finish) fcount++;
				}
				return fcount;
			}
		},
		/**
		 * 创建组件
		 */
		createCmpts : function(){
			this.createMenuLabel();
			this.createMenuTipCmpt();
			this.addMenus();
			this.updateCount();
			this.addMenuLableClick();
		},
		/**
		 * 为菜单标签添加点击事件
		 */
		addMenuLableClick : function(){
			var menuLabel = Ext.get(this.idMgr.menuLabelId);
			var _this = this;
			menuLabel.on('click',function(ele){
				if(_this.menuTipCmpt && !_this.menuTipCmpt.isVisible()) _this.menuTipCmpt.show();
			});
		},
		/**
		 * 创建TabPanel 标题上的事项显示信息
		 */
		createMenuLabel : function(){
			var auditTabTitle = '审批信息';
			menuLabelHtmlArr = [
				'<span class="buss_menu_list_menuLabel" id="'+this.idMgr.menuLabelId+'">',
				'【<img id="'+this.idMgr.msgTipImgId+'" src="./images/public/matters_tips.gif" class="buss_menu_list_menuLabel_img"/>',
				'<span id="'+this.idMgr.msgCountSpanId+'" style="display:inline;margin:-5px 0px 0px 0px;">必做：0/0项;选做：0/0项;</span>】</span>'
			];
			var menuLabelHtml = menuLabelHtmlArr.join("");
			var newTabTitle = auditTabTitle + menuLabelHtml;// menuLabelHtmlArr.join(" ");
			this.auditTabPanel.setTitle(newTabTitle);
		},
		/**
		 * 创建菜单提示组件
		 */
		createMenuTipCmpt : function(){
			this.menuTipCmpt =  new Ext.ToolTip({
		        target: this.idMgr.menuLabelId,
		        anchor: 'top',
		        autoHide: false,
        		closable: false,
        		width : 400,
		        anchorOffset: 85
		   });
		},
		/**
		 * 添加菜单
		 */
		addMenus : function(){
			if(!this.bussFormDatas) return;
			var _this = this;
			var mustFormsData = this.bussFormDatas.mustForms;
			if(mustFormsData && mustFormsData.length > 0){
				var count = mustFormsData.length;
				this.mustMenusPanel = new Ext.ux.FlatImgPanel({
					isLoad : true,
					dataArr : mustFormsData,
					eventMgr : {
						clickCallback : function(eleId,selData){
							_this.mustOrMabeTag = 1;
							_this.openUI(eleId,selData);
						}
					}
				});
				this.mustFst = new Ext.form.FieldSet({title : '必做业务('+count+'项)',anchor:'98%',style:'padding:0px;margin:0px;',items : [this.mustMenusPanel]});
				this.menuTipCmpt.add(this.mustFst);
			}
			
			var mabeFormsData = this.bussFormDatas.mabeForms;
			if(mabeFormsData && mabeFormsData.length > 0){
				var count = mabeFormsData.length;
				this.mabeMenusPanel = new Ext.ux.FlatImgPanel({isLoad : true,dataArr : mabeFormsData,
					eventMgr : {
						clickCallback : function(eleId,selData){
							_this.mustOrMabeTag = 2;
							_this.openUI(eleId,selData);
						}
					}
				});
				this.mabeFst = new Ext.form.FieldSet({title : '选做业务('+count+'项)',anchor:'98%',style:'padding:0px;margin:0px;',items : [this.mabeMenusPanel]});
				this.menuTipCmpt.add(this.mabeFst);
			}
		},
		/**
		 * 打开表单界面
		 * @param eleId 点击的菜单元素的ID
		 * @param selData 点击的菜单数据
		 */
		openUI : function(eleId,selData){
			this.selMenuEleId = eleId;
			this.selMenuData = selData;
			this.menuTipCmpt.hide();
			if(this.mustOrMabeTag == 1){/*必做*/
				if(this.mabeMenusPanel){
					this.mabeMenusPanel.unSelect();
				}
				this.selMenusPanel = this.mustMenusPanel;
			}else if(this.mustOrMabeTag == 2){/*选做*/
				if(this.mustMenusPanel){
					this.mustMenusPanel.unSelect();
				}
				this.selMenusPanel = this.mabeMenusPanel;
			}else{
				ExtUtil.error({msg:'mustOrMabeTag='+this.mustOrMabeTag+"不是预期的值，<br/>mustOrMabeTag 正确的可选值范围：[1:必做，2：选做]"});
				return;
			}
			var _uiParams = {bussTabPanel:this.bussMakeTabPanel,menuData:selData};
			Ext.apply(_uiParams,this.params);
			/*------ 删除从 params 中复制过来的不会用到的参数  -----*/
			if(_uiParams['bussFormDatas']) delete _uiParams['bussFormDatas'];
			if(_uiParams['auditTabPanel']) delete _uiParams['auditTabPanel'];
			if(_uiParams['bussMakeTabPanel']) delete _uiParams['bussMakeTabPanel'];

			if(this.bussUiMgr){
				this.bussUiMgr.show(_uiParams);
			}else{
				var _this = this;
				Cmw.importPackage('pages/app/workflow/bussProcc/mainUIs/BussMgrMod',function(module) {
				 	_this.bussUiMgr = module.moduleUI;
				 	_this.bussUiMgr.resize(_this.wh);
				 	_this.bussUiMgr.finishBussCallback = function(formData){_this.finishBussCallback(formData);};
				 	_this.bussUiMgr.unFinishBussCallback = function(formData){_this.unFinishBussCallback(formData);};
					 _this.bussUiMgr.show(_uiParams);
		  		});
			}
		},
		/**
		 * 表单业务处理完成回调函数
		 * @param formData 表单数据
		 */
		finishBussCallback : function(formData){
			var flatImgId = this.selMenuData.flatImgId;
			var flatImgEle = Ext.get(flatImgId);
			if(!flatImgEle){
				ExtUtil.error({msg:'在调用表单业务处理完成回调函数 : "finishBussCallback(formData)"时，<br/>找不到对应的菜单图标对象，无法标记事项为已完成! '});
				return;
			}
			if(this.selMenusPanel.isFinish(flatImgId)) return;
			var _this = this;
			var nodeId = this.selMenuData.nodeId;
			var menuId = this.selMenuData.id;
			var bussCode = this.params.bussProccCode;
			var formId = this.params.applyId;
			if(!bussCode){
				ExtUtil.error({msg:'必须为参数 params 的 “bussProccCode”属性提供业务流程编号值!'});
				return;
			}
			if(!formId){
				ExtUtil.error({msg:'必须为参数 params 的 “applyId”属性提供值!'});
				return;
			}
			
			var _params = {nodeId:nodeId,custFormId:menuId,bussCode:bussCode,formId:formId};
			EventManager.get('./sysFormRecords_save.action',{params:_params,sfn:function(json_data){
				_this.selMenusPanel.setFinish(flatImgId);
				_this.updateCountByFormSave(1);
			}});
		},
		/**
		 * 取消表单业务完成标识
		 */
		unFinishBussCallback : function(){
			var flatImgId = this.selMenuData.flatImgId;
			var flatImgEle = Ext.get(flatImgId);
			if(!flatImgEle){
				ExtUtil.error({msg:'在调用表单业务处理完成回调函数 : "finishBussCallback(formData)"时，<br/>找不到对应的菜单图标对象，无法标记事项为已完成! '});
				return;
			}
			if(!this.selMenusPanel.isFinish(flatImgId)) return;
			
			var _this = this;
			var nodeId = this.selMenuData.nodeId;
			var menuId = this.selMenuData.id;
			var bussCode = this.params.bussProccCode;
			var formId = this.params.applyId;
			if(!bussCode){
				ExtUtil.error({msg:'必须为参数 params 的 “bussProccCode”属性提供业务流程编号值!'});
				return;
			}
			if(!formId){
				ExtUtil.error({msg:'必须为参数 params 的 “applyId”属性提供值!'});
				return;
			}
			
			var _params = {nodeId:nodeId,custFormId:menuId,bussCode:bussCode,formId:formId};
			EventManager.get('./sysFormRecords_delete.action',{params:_params,sfn:function(json_data){
				_this.selMenusPanel.setUnFinish(flatImgId);
				_this.updateCountByFormSave(-1);
			}});
		},
		/**
		 * 刷新菜单
		 */
		refresh : function(params){
			this.setParams(params);
			/*如果没有必做或选做事项就返回*/
			if(!this.bussFormDatas || (!this.bussFormDatas["mustForms"] && !this.bussFormDatas["mabeForms"])) return;
			if(!this.menuTipCmpt){
				this.initIdMgr();
				this.createCmpts();
			}
		},
		/**
		 * 当表单数据保存后，更新已做事项数量
		 * @param num 已做或选做事项数量 
		 */
		updateCountByFormSave : function(num){
			switch(this.mustOrMabeTag){
				case 1: {/*必做*/
					var fmustCount = this.bussCounterMgr.fmustCount;
					fmustCount += num;
					if(!fmustCount || fmustCount < 0) fmustCount = 0;
					this.bussCounterMgr.fmustCount = fmustCount;
					break;
				}case 2: {/*选做*/
					var fmabeCount = this.bussCounterMgr.fmabeCount;
					fmabeCount += num;
					if(!fmabeCount || fmabeCount < 0) fmabeCount = 0;
					this.bussCounterMgr.fmabeCount = fmabeCount;
					break;
				}default : {
					ExtUtil.error({msg:'mustOrMabeTag='+this.mustOrMabeTag+"不是预期的值，<br/>mustOrMabeTag 正确的可选值范围：[1:必做，2：选做]"});
					return;
				}
			}
			var fmustCount = this.bussCounterMgr.fmustCount;
			if(fmustCount && fmustCount >= this.bussCounterMgr.mustTotalCount){
				this.bussCounterMgr.fmustCount = this.bussCounterMgr.mustTotalCount;
				var _params = this.params;
				_params.hideForm = false;
				if(this.callback) this.callback(_params);
			}
			this.setMsgCountSpan();
		},
		/**
		 * 当组件创建完后，更新必做和选做菜单事项数量
		 */
		updateCount : function(){
			this.setCounterMgr();
			this.setMsgCountSpan();
		},
		/**
		 * 更新事项数量
		 */
		setMsgCountSpan : function(){
			var msgCountEle = Ext.get(this.idMgr.msgCountSpanId);
			if(!msgCountEle) return;
			var arr = [];
			if(this.bussCounterMgr.mustTotalCount > 0){
				arr[arr.length] = "必做："+this.bussCounterMgr.fmustCount+"/"+this.bussCounterMgr.mustTotalCount+"项;";
			}
			if(this.bussCounterMgr.mabeTotalCount > 0){
				arr[arr.length] = "选做："+this.bussCounterMgr.fmabeCount+"/"+this.bussCounterMgr.mabeTotalCount+"项;";
			}
			var msgCountHtml = arr.join("&nbsp;");
			msgCountEle.update(msgCountHtml);
		},
		resize : function(adjWidth,adjHeight){
			this.wh[0] =adjWidth;
			this.wh[1] =adjHeight;
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function(){
			if(this.mustFst){
				this.mustFst.destroy();
				this.mustFst = null;
			}
			if(this.mustMenusPanel){
				this.mustMenusPanel.destroy();
				this.mustMenusPanel = null;
			}
			if(this.mabeFst){
				this.mabeFst.destroy();
				this.mabeFst = null;
			}
			if(this.mabeMenusPanel){
				this.mabeMenusPanel.destroy();
				this.mabeMenusPanel = null;
			}
			
			if(this.menuTipCmpt){
				this.menuTipCmpt.destroy();
				this.menuTipCmpt = null;
			}
			
			if(this.auditTabPanel) this.auditTabPanel = null;
			if(this.bussMakeTabPanel) this.bussMakeTabPanel = null;
			this.selMenuData = null;
			this.selMenuEleId = null;
			this.selMenusPanel = null;
			this.mustOrMabeTag = 0;
			this.bussCounterMgr = null;
		}
	};
});