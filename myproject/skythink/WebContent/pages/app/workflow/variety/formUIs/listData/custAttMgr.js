/*同心日科技公司Erp 系统 命名空间*/
Ext.namespace("skythink.cmw.workflow.bussforms");
/**
 * 附件材料
 */
skythink.cmw.workflow.bussforms.custAttMgr = function(){
	this.init(arguments[0]);
}
/**
 * 附件材料
 * @class skythink.cmw.workflow.bussforms.custAttMgr
 * @extends Ext.util.MyObservable
 */
/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.workflow.bussforms.custAttMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		var finishBussCallback = tab.finishBussCallback;
		this.module = new Cmw.app.widget.AbsPanelView({
			tab : tab,
			params : params,/*apptabtreewinId,tabId,sysid*/
			getAppCmpt : this.getAppCmpt,
			changeSize : this.changeSize,
			toolBar : this.getToolBar,
			globalMgr : this.globalMgr,
			destroyCmpts : this.destroyCmpts,
			prefix : Ext.id(),
			finishBussCallback : finishBussCallback/*由必做或选做业务菜单传入的回調函数，主要功能：当业务表单保存后，更新必做或选做事项为已做*/
		});
	},
	/**
	 * 获取组件方法
	 */
	getAppCmpt  : function(){
		var that = this ; 
		var height =  this.tab.getHeight();
		this.globalMgr.tabHeight = height;
		this.appPanel = new Ext.Panel({autoWidth : true,border : false,height:height});
		this.appPanel.add(that.globalMgr.getToolBat(that));
		var htmlPnl = this.globalMgr.htmlArrs(that);
		return this.appPanel;
	},
	
	
	/**
	 * 组件大小改变方法
	 * @param {} whArr
	 */
	changeSize : function(whArr){
		var width = whArr[0]-5;
		var height = whArr[1];
		this.globalMgr.height = height;
		if(this.appPanel){
			this.appPanel.setWidth(width);	
			this.appPanel.setHeight(height);
		}
	},
	/**
	 * 组件销毁方法
	 */
	destroyCmpts : function(){
		if(this.globalMgr.htmlPnl != null){
			this.globalMgr.htmlPnl.destroy();
			this.globalMgr.htmlPnl = null;
		}
		if(this.globalMgr.htmlPnlMod != null){
			this.globalMgr.htmlPnlMod.destroy();
			this.globalMgr.htmlPnlMod = null;
		}
		if(this.appPanel != null){
			this.appPanel.destroy();
			this.appPanel = null;
		}
	},
	globalMgr : {
	/**
	 * 创建工具栏
	 */
	getToolBat : function(_this){
		var barItems = [{
				type : 'label',
				text:'<span style="color:red">☆ <span>'+'<span style="color:black;font-weight:bold;" >为必填选项</span>&nbsp;&nbsp;&nbsp;'
			},{
				token :"编辑", 
				text :"编辑",
				iconCls:'page_edit',
				tooltip:"编辑",
				handler : function(){
					_this.globalMgr.htmlPnl.enable();
					toolBar.enableButtons("保存");
				}
			},{
				token :"保存", 
				text :"保存",
				iconCls:'page_save',
				tooltip:"保存",
				handler : function(){
					var matParamsIds = _this.globalMgr.Callback;
					var boxs = [];
					var saveId = [];
					var remarks = [];
					var saveRemark = [];
					var displaysave = [];
					for(var i=0;i<matParamsIds.length;i++){
						var spanId  = "SPANID2_"+matParamsIds[i];
						var span2 = Ext.get(spanId);
						if(span2){
							var display = span2.dom.style.display;
							if(display=="block"){
								displaysave.push(matParamsIds[i]);
							}
						}
						var checkBoxName = 'CheckboxGroup_'+matParamsIds[i];
						boxs = Cmw.getElesByName(checkBoxName);
						for(var j = 0;j<boxs.length;j++){
							if(boxs[j].checked){
								var cookBoxId = matParamsIds[i]+"##"+boxs[j].value ;
								saveId.push(cookBoxId);
							}
						}
						
					}
					var remarkids = _this.globalMgr.remarkIds;
						if(remarkids.length != 0){
							for(var j=0;j<remarkids.length;j++){
								var reamrkDom = Ext.get(remarkids[j]);
								var value = reamrkDom.getValue();
								var name  = reamrkDom.dom.name;
								if(value!=null && value!=""){
									var re = remarkids[j].split("_");
									saveRemark.push(re[1]+"##"+value);
								}
							}
						}
					var matResultText  = Cmw.$("matResultId");
					var ids = matResultText.value;
					if(saveId.length==0){
						if(saveRemark.length != 0){
							ExtUtil.alert({msg:"在添加备注之前请先添加附件或选择复选框后再点击保存按钮！"});
							return;
						}
						ExtUtil.alert({msg:"请添加数据后再点击保存按钮！"});
						return;
					} 
					
					var params = {results:saveId.join(","),sysId:_this.globalMgr.sysId,formId:_this.globalMgr.formId,formIds2:displaysave.join(","),remarks:saveRemark.join("☆"),ids :ids};
					EventManager.get('./sysMatResult_save.action',{params:params,sfn:function(json_data){
						ExtUtil.alert({msg:Msg_SysTip.msg_dataSave});
						var ids = json_data.saveAfterIds;
						matResultText.value=ids;
						if(_this.finishBussCallback) _this.finishBussCallback(json_data);
					},ffn:function(json_data){
						ExtUtil.alert({msg:Msg_SysTip.msg_dataFailure});
					}});
				}
			}]
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {saveRights : true,currNode : _this.params[CURR_NODE_KEY]}});
			toolBar.disableButtons("保存");
			_this.globalMgr.toolBar = toolBar;
			return toolBar;
	},
		barHeight :null,
		toolBar : null,
		apptree : null,
		htmlPnl : null,
		Callback : null,
		remarkIds : null,
		tabHeight : null,
		width : null,
		height : null,
		breed : null,
		htmlPnlMod : null,
		node : {id: 'M44',text : '企业客户应提供的材料'},
		htmlArrs : function(_this){
			_this.globalMgr.breed = _this.params.breed;
			EventManager.get('./sysMatTemp_getMat.action',
				{params:{breed : _this.globalMgr.breed,custType : _this.params.custType},
				sfn:function(jsonData){
				if(jsonData){
					if(!jsonData.id){
						_this.globalMgr.toolBar.disable();
						_this.appPanel.update("该贷款业务品种下暂无数据！",false);
						return;
					}
					_this.globalMgr.node.id = 'M'+jsonData.id;
					_this.globalMgr.node.text = jsonData.name;
					_this.parent = _this.globalMgr.node;
					_this.isNotyl = 0;
					_this.tabHeight = _this.globalMgr.tabHeight;
					_this.params.sysId = _this.globalMgr.sysId;
					_this.params.formId = _this.globalMgr.formId;
					Cmw.importPackage('pages/app/sys/mat/custAttachment',function(module) {
					 	var htmlPnlMod =  module.moduleUI;
					 	_this.globalMgr.htmlPnlMod = htmlPnlMod;
					 	_this.globalMgr.htmlPnl = htmlPnlMod.getModule(_this);
					 	var width = _this.globalMgr.htmlPnl.getWidth();
//					 	_this.globalMgr.htmlPnl.setHeight(_this.globalMgr.height);
					 	_this.appPanel.add(_this.globalMgr.htmlPnl.disable());
					 	htmlPnlMod.callback = function(data){
							_this.globalMgr.Callback=data.MatParamsId;
							_this.globalMgr.remarkIds=data.remark;
					 	}
					 	_this.appPanel.doLayout();
			  		});
					return _this.globalMgr.htmlPnl;
				}else{
					ExtUtil.error({msg:"数据加载出,请联系管理员！"});
					return;
				}
			}});
		},
		sysId : this.params.sysid,
		params : this.params,
		activeKey: null,
		formId:this.params.applyId,
		detailPanel_1 : null
	}
});

