/**
 * 新建工作
 * 
 * @author chengmingwei
 * @date 2013-12-26
 */
define(function(require, exports) {
	exports.viewUI = {
		appMainPanel : null,
		tab : null,
		params : null,
		json_data : null,/*流程数据*/
		selBussTypeElId : null,/*缓存当前点击的业务分类元素ID*/
		idMgr : {
			oa_new_work_contentId : Ext.id(null,'oa_new_work_contentId_'),/*主内容面板ID*/
			oa_new_work_blankId :  Ext.id(null,'oa_new_work_blankId_'),/*空白背影图片ID*/
			oa_new_work_bussTypesId : Ext.id(null,'oa_new_work_bussTypesId_'),/*流程分类对象ID*/
			oa_new_work_bussProccesId : Ext.id(null,'oa_new_work_bussProccesId_'),/*流程表单面板ID*/
			bussTypeItemIdPrefix : 'bussTypeItem_',
			bussTypeItemSpanIdPrefix : 'bussTypeItemSpan_',
			oa_view_procc_titleIdPrefix : 'oa_view_procc_title_',
			oa_view_proccdesignerIdPrefix : 'oa_view_proccdesigner_',
			oa_view_unsubmitIdPrefix : 'oa_view_unsubmit_',
			oa_view_remarkIdPrefix : 'oa_view_remark_',
			oa_view_fun_fastNewIdPrefix : 'oa_view_fun_fastNew_',
			oa_view_fun_newIdPrefix : 'oa_view_fun_new_'
		},
		/**
		 * 设置参数
		 */
		setParams : function(tab, params) {
			this.tab = tab;
			this.params = params;
		},
		/**
		 * 主UI 界面开始点
		 */
		getMainUI : function(tab, params) {
			this.setParams(tab, params);
			if (!this.appMainPanel) {
				this.craeteMainPnl();
			}
			var _this = this;
			tab.notify = function(_tab) {
				_this.setParams(_tab, _tab.params);
				_this.refresh();
			};
			return this.appMainPanel;
		},
		/**
		 * 创建主面板
		 */
		craeteMainPnl : function() {
			var body = Ext.getBody();
			var width = body.getWidth() - 215;
			var height = body.getHeight() - 145;
			if(this.tab.rendered){
				width = this.tab.getInnerWidth();
				height = this.tab.getInnerHeight();
			}
			var html = this.getMainHtml(width,height);
			var appPanel = new Ext.Panel({
				html : html,
				width:width, 
				height:height
			});
			this.appMainPanel = appPanel;
			return this.appMainPanel;
		},
		/**
		 * 获取主架构HTML
		 */
		getMainHtml : function(width,height){
			var bussTypesDivId = this.idMgr.oa_new_work_bussTypesId;
			var bussProcessDivId = this.idMgr.oa_new_work_bussProccesId;
			//var dh = Ext.DomHelper;
			//dh.append(Ext.getBody(),{id:this.idMgr.oa_new_work_blankId,tag:'img',src:"images/public/blank.png",cls:"oa_new_work_blank"});
			var htmlArr = [
				'<div id="'+this.idMgr.oa_new_work_contentId+'" class="oa_new_work_flow_content" style="width:'+width+'px;height:'+height+'px;">',
				'<div class="oa_new_work_flow_top"><h2>新建工作</h2> </div>',
			    '<div class="oa_new_work_flow_main">',
			    '<div  id="'+bussTypesDivId+'"  class="oa_new_work_bussType">无数据...</div>',
			    '<div  id="'+bussProcessDivId+'"  class="oa_new_work_bussProcces"></div>',
			    '<div class="oa_new_work_clear_cls"></div>',
			    '</div>',
				'</div>'
			];
			return htmlArr.join(" ");
		},
		/**
		 * 刷新方法
		 */
		refresh : function() {
			this.clear();
			this.loadDatas();
		},
		clear : function(){
			if(this.json_data) this.json_data = null;
			var bussTypesEl = Ext.get(this.idMgr.oa_new_work_bussTypesId);
			if(bussTypesEl) bussTypesEl.update("");
			var bussProccesEl = Ext.get(this.idMgr.oa_new_work_bussProccesId);
			if(bussProccesEl) bussProccesEl.update("");
		},
		loadDatas : function(){
			var _this = this;
			EventManager.get('./oaProcType_query.action',{params:{actionType:'1'},sfn:function(json_data){
			 	_this.drawUIbydatas(json_data);
			}});
		},
		drawUIbydatas : function(json_data){
			var _this = this;
			var html = null;
			this.json_data = json_data;
			if(!json_data || json_data.totalSize == 0){
				html = '<h3 style="color:red;">无数据...</h3>';
			}else{
				var divIdArr = [];
				var divId = this.idMgr.bussTypeItemIdPrefix+"0";
				var spanId = this.idMgr.bussTypeItemSpanIdPrefix+"0";
				divIdArr[0] = divId;
				var htmlArr = [];
				var itemHtml = this.getBussTypeItemHtml(divId,spanId,'全部工作');
				htmlArr[0] = itemHtml;
				var list = this.json_data.list;
				for(var i=0,count=list.length; i<count; i++){
					var bussTypeData = list[i];
					var id =  bussTypeData.id;
					var name = bussTypeData.name;
					divId = this.idMgr.bussTypeItemIdPrefix+id;
					spanId = this.idMgr.bussTypeItemSpanIdPrefix+id;
					divIdArr[divIdArr.length] = divId;
					htmlArr[htmlArr.length] = this.getBussTypeItemHtml(divId,spanId,name);
				}
				html = htmlArr.join(" ");
			}
			var bussTypesEl = Ext.get(this.idMgr.oa_new_work_bussTypesId);
			if(bussTypesEl) bussTypesEl.update(html,false,function(){
				_this.bindBussTypeListeners(divIdArr);
			});
		},
		/**
		 * 绑定业务类型事件
		 */
		bindBussTypeListeners : function(divIdArr){
			if(!divIdArr || divIdArr.length == 0) return;
			var _this = this;
			for(var i=0,count = divIdArr.length; i<count; i++){
				var divId = divIdArr[i];
				var ele = Ext.get(divId);
				ele.addClassOnOver("oa_new_work_bussType_focus");
				var op = {divId : divId};
				ele.on('click',function(e,t,o){
					_this.drawProcceList(o);
				},this,op);
			}
			var allDivId = divIdArr[0];
			this.drawProcceList({divId:allDivId});
		},
		drawProcceList : function(o){
			this.upSelectCache(o);
			this.drawProccUI(o);
		},
		upSelectCache : function(op){
			var divActiveCls = "oa_new_work_bussType_active";
			var spanActiveCls = "oa_new_work_bussType_active_icon";
			var divId = op.divId;
			var spanId = null;
			var prevSpanEle = null;
			if(this.selBussTypeElId){
				var prevEle = Ext.get(this.selBussTypeElId);
				if(prevEle) prevEle.removeClass(divActiveCls);
				spanId = this.selBussTypeElId.replace(this.idMgr.bussTypeItemIdPrefix,this.idMgr.bussTypeItemSpanIdPrefix);
				prevSpanEle = Ext.get(spanId);
				if(prevSpanEle) prevSpanEle.removeClass(spanActiveCls);
			}
			this.selBussTypeElId = divId;
			var selEle = Ext.get(this.selBussTypeElId);
			if(selEle){
				selEle.addClass(divActiveCls);
			}
			spanId = this.selBussTypeElId.replace(this.idMgr.bussTypeItemIdPrefix,this.idMgr.bussTypeItemSpanIdPrefix);
			prevSpanEle = Ext.get(spanId);
			if(prevSpanEle) prevSpanEle.addClass(spanActiveCls);
		},
		drawProccUI : function(op){
			var divId = op.divId;
			var allDivId = this.idMgr.bussTypeItemIdPrefix+"0";
			var html='<h3 style="color:red;">当前分类下没有配置流程或者未设置新建工作的权限...</h3>';
			var proccDataArr = [];
			if(this.json_data && this.json_data.totalSize > 0){
				var list = this.json_data.list;
				var htmlArr = [];
				if(divId == allDivId){/*加载全部工作流*/
					for(var i=0,count=list.length; i<count; i++){
						var btData = list[i];
						var datas = btData.datas;
						if(!datas || datas.length == 0) continue;
						proccDataArr = proccDataArr.concat(datas);
					}
				}else{
					var bussTypeId = divId.replace(this.idMgr.bussTypeItemIdPrefix,'');
					for(var i=0,count=list.length; i<count; i++){
						var btData = list[i];
						var datas = btData.datas;
						if(!datas || datas.length == 0) continue;
						var id = btData.id;
						if(id == bussTypeId){
							proccDataArr = datas;
							break;
						}
					}
				}
				var _newHtml = this.getProccListHtml(proccDataArr);
				if(_newHtml) html = _newHtml;
			}
			var bussProccesEle = Ext.get(this.idMgr.oa_new_work_bussProccesId);
			var _this = this;
			if(bussProccesEle) bussProccesEle.update(html,false,function(){
				_this.bindProccListeners(proccDataArr);
			});
		},
		getProccListHtml : function(proccDataArr){
			if(!proccDataArr || proccDataArr.length == 0) return null;
			var htmlArr = ["<ul>"];
			for(var i=0,count=proccDataArr.length; i<count; i++){
				var proccData = proccDataArr[i];
				var html = this.getProccItemHtml(proccData);
				htmlArr[htmlArr.length] = html;
			}
			htmlArr[htmlArr.length] = "</ul>";
			return htmlArr.join(" ");
		},
		getProccItemHtml : function(proccData){
			var id = proccData.id;
			var name = proccData.name;
			var createTime = proccData.createTime;
			var procctitleId = this.idMgr.oa_view_procc_titleIdPrefix+id;
			var proccdesignerId = this.idMgr.oa_view_proccdesignerIdPrefix+id;
			var unsubmitId = this.idMgr.oa_view_unsubmitIdPrefix+id;
			var remarkId = this.idMgr.oa_view_remarkIdPrefix+id;
			var fastNewId = this.idMgr.oa_view_fun_fastNewIdPrefix+id;
			var newId = this.idMgr.oa_view_fun_newIdPrefix+id;
			var htmlArr = [
				'<li class="oa_new_work_bussProcces_item">',
	            '	<div class="oa_new_work_bussProcces_item_common oa_new_work_bussProcces_item_title">',
	            '    	<a id="'+procctitleId+'" href="#"><h4>'+name+'</h4></a>',
	            '        <span>创建时间('+createTime+')</span>',
	            '    </div>',
	            '    <div class="oa_new_work_bussProcces_item_common oa_new_work_bussProcces_main">',
	            '   	<div class="oa_view_fun_common oa_view_proccdesigner">',
	            '        	<a id="'+proccdesignerId+'" class="oa_view_linkText" href="javascript:void()">查看流程设计图</a>',
	            '        </div>',
	            '        <div class="oa_view_fun_common oa_view_unsubmit">',
	            '        	<a id="'+unsubmitId+'" class="oa_view_linkText" href="javascript:void()">（暂存5笔）查看</a>',
	            '        </div>',
	            '        <div class="oa_view_fun_common oa_view_remark">',
	            '        	<a id="'+remarkId+'" class="oa_view_linkText" href="#">查看流程说明</a>',
	            '        </div>',
	            '        <div class="oa_view_fun_common" style=" width:150px; float:right;">',
	            '                <a id="'+fastNewId+'" href="#" class="oa_view_fun_common_btns fastNew_css_btn_class">快速新建</a>',
	            '                <a id="'+newId+'" href="#" class="oa_view_fun_common_btns css_btn_class">新建向导</a>',
	            '        </div>',
	            '         <div class="oa_new_work_clear_cls"></div>',
	            '    </div>',
	            '     <div class="oa_new_work_clear_cls"></div>',
	            '</li>'
			];
			return htmlArr.join(" ");
		},
		getBussTypeItemHtml : function(divId,spanId,name){
			return '<div id="'+divId+'"><span id="'+spanId+'" class="oa_new_work_bussType_nomal">'+name+'</span></div>';
		},
		bindProccListeners : function(proccDataArr){
			if(!proccDataArr || proccDataArr.length == 0) return;
			var _this = this;
			for(var i=0,count=proccDataArr.length; i<count; i++){
				var proccData = proccDataArr[i];
				var id = proccData.id;
				var op = proccData;
				var procctitleId = this.idMgr.oa_view_procc_titleIdPrefix+id;
				var proccdesignerId = this.idMgr.oa_view_proccdesignerIdPrefix+id;
				var unsubmitId = this.idMgr.oa_view_unsubmitIdPrefix+id;
				var remarkId = this.idMgr.oa_view_remarkIdPrefix+id;
				var fastNewId = this.idMgr.oa_view_fun_fastNewIdPrefix+id;
				var newId = this.idMgr.oa_view_fun_newIdPrefix+id;
				var proccTitleEle = Ext.get(procctitleId);
				if(proccTitleEle){
					proccTitleEle.on('click',function(e,t,o){
						_this.openApplyUI(o);
					},this,proccData);
				}
				
				var proccdesignerEle = Ext.get(proccdesignerId);
				if(proccdesignerEle){
					proccdesignerEle.on('click',function(e,t,o){
						_this.openPdWin(o);
					},this,op);
				}
				
				var unsubmitEle = Ext.get(unsubmitId);
				if(unsubmitEle){
					unsubmitEle.on('click',function(e,t,o){
						_this.openUnsubmitWin(o);
					},this,op);
				}
				
				var remarkEle = Ext.get(remarkId);
				if(remarkEle){
					remarkEle.on('click',function(e,t,o){
						_this.openPdRemarkWin(o);
					},this,op);
				}
				
				var fastNewEle = Ext.get(fastNewId);
				if(fastNewEle){
					fastNewEle.on('click',function(e,t,o){
						_this.openApplyUI(o);
					},this,op);
				}
				
				var newEle = Ext.get(newId);
				if(newEle){
					newEle.on('click',function(e,t,o){
						_this.openNavApplyUI(o);
					},this,op);
				}
			}
		},
		/**
		 * 打开申请表单
		 * @param data 子业务流程数据
		 * @param isShowNavContent	是否需要显示条款等向导内容
		 */
		openApplyUI : function(data,isShowNavContent){
			var tabId = "oa_newWork_"+data.id;
			var tabTitle = data.name;
			var url=Flow_CustForm_Url.OaBussProccBase;
			if(!isShowNavContent){
				isShowNavContent = false;
			}
			data.isShowNavContent = isShowNavContent;
			var bussProccId = data.id;
			data.bussProccId = bussProccId;
			data.isnewInstance = true;
			Cmw.activeTabByOa(tabId,data,url,tabTitle);
		},
		/**
		 * 打开向导申请表单
		 */
		openNavApplyUI : function(data){
			this.openApplyUI(data,true);
		},
		pdViewWin : null,/*流程图查看窗口*/
		pdRemarkWin : null,/*流程说明查看窗口*/
		/**
		 * 打开流程图窗口
		 */
		openPdWin : function(data){
			var parentCfg = {params : {pdid:data.pdid,winTitle : data.name}};
			var _this = this;
			if(!this.pdViewWin){
				Cmw.importPackage('pages/app/oa/pworkes/ViewProccImgDialogbox',function(module) {
				 	_this.pdViewWin = module.DialogBox;
				 	_this.pdViewWin.show(parentCfg);
		  		});
			}else{
				this.pdViewWin.show(parentCfg);
			}
		},
		/**
		 * 打开暂存单据窗口
		 */
		openUnsubmitWin : function(data){
			alert("openUnsubmitWin...");
		},
		/**
		 * 打开流程说明窗口
		 */
		openPdRemarkWin : function(data){
			var parentCfg = {params : {bussProccId:data.id,winTitle : data.name}};
			var _this = this;
			if(!this.pdRemarkWin){
				Cmw.importPackage('pages/app/oa/pworkes/ViewProccRemarkDialogbox',function(module) {
				 	_this.pdRemarkWin = module.DialogBox;
				 	_this.pdRemarkWin.show(parentCfg);
		  		});
			}else{
				this.pdRemarkWin.show(parentCfg);
			}
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function() {
			if(this.appMainPanel) {
				this.appMainPanel.destroy();
				this.appMainPanel = null;
			}
			if(this.pdViewWin) this.pdViewWin.destroy();
			if(this.pdRemarkWin) this.pdRemarkWin.destroy();
//			var blankImg = Ext.get(this.idMgr.oa_new_work_blankId);
//			if(blankImg) blankImg.remove();
		}
	}
});