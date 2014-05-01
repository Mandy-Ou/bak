Ext.namespace("skythink.cmw.workflow.bussforms");
/**
 * 抵押物登记 UI
 * @author 彭登浩
 * @date 2013-01-09
 * */ 
skythink.cmw.workflow.bussforms.MortgageMgr = function(){
	this.init(arguments[0]);
};

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.workflow.bussforms.MortgageMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		/*由必做或选做业务菜单传入的回調函数，主要功能：
		 * finishBussCallback : 当业务表单保存后，更新必做或选做事项为已做,
		 * unFinishBussCallback : 当删除业务表单后，取消已做标识
		 * */
		var finishBussCallback = tab.finishBussCallback;
		var unFinishBussCallback = tab.unFinishBussCallback;
		this.module = new Cmw.app.widget.AbsTreePanelView({
			tab : tab,
			params : params,
			hasTopSys : false,
			getToolBar : this.getToolBar,
			getAppPanel : this.getAppPanel,
			getAppGrid : this.getAppGrid,
			getAppTree : this.getAppTree,
			getMgDetail : this.getMgDetail,
			globalMgr : this.globalMgr,
			notify : this.notify,
			refresh : this.refresh,
			TREE_WIDTH : 150,
			formId:null,
			attachMentFs : null,/*附件对象*/	
			finishBussCallback : finishBussCallback,
			unFinishBussCallback : unFinishBussCallback
		});
	},
	notify : function(data){
		var gvlistId = data.id;
		this.globalMgr.createMgDetailByGvlistId(this,gvlistId);
		this.globalMgr.toolBar.enable();
		this.globalMgr.formId = this.params.applyId;
		this.globalMgr.appgrid.show();
		this.globalMgr.appgrid.reload({gtype:this.globalMgr.gvlistid,formId:this.globalMgr.formId,state:"2,1"});
	},
	getToolBar : function(){
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({hidden:true});
		return toolBar;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
	  	var structure = [{
			    header: 'id',
			    name: 'id',
			    hidden :true,
			    width: 125
			},{
			    header: '贷款申请单ID',
			    name: 'formId',
			    hidden :true,
			    width: 125
			},{
			    header: '基础数据id',
			    name: 'gtype',
			    hidden :true,
			    width: 125
			},{
			    header: '状态',
			    name: 'state',
			    renderer:Render_dataSource.mortState,
			    width: 125
			},{
			    header: '抵押物名称',
			    name: 'name',
			    width: 125
			},
			{
			    header: '单位',
			    name: 'unint',
			    hidden :true,
			    width: 125
			},{
			    header: '数量',
			    name: 'quantity',
			    width: 80,
			    renderer: function(val,cellmeta,record,rowIndex,columnIndex,store){
			    	var unint = record.data['unint'];
			    	return val+unint;
			    }
			},
			{
			    header: '原价值(元)',
			    name: 'oldVal',
			    width: 125,
			     renderer : Render_dataSource.moneyRender
			},
			{
			    header: '抵押价值',
			    name: 'mpVal',
			    width: 125,
			     renderer : Render_dataSource.moneyRender
			},
			{
			    header: '抵押人',
			    name: 'mman',
			    width: 125
			},
			{
			    header: '联系电话',
			    name: 'conTel',
			    width: 125
			},
			{
			    header: '抵押时间',
			    name: 'morTime',
			    width: 125
			},
			{
			    header: '抵押物所在地',
			    name: 'address',
			    width: 150
			},{
			    header: '落实人',
			    renderer:Render_dataSource.treeOrGrid,
			    name: 'carrMan',
			    width: 150
			},{
			    header: '落实时间',
			    name: 'carrTime',
			    width: 150
			},{
			    header: '解押人',
			    renderer:Render_dataSource.treeOrGrid,
			    name: 'charMan',
			    width: 150
			},{
			    header: '解押时间',
			    name: 'charTime',
			    width: 150
			},{
			    header: '备注',
			    name: 'remark',
			    width: 300
			}];
			
		var appgrid = new Ext.ux.grid.AppGrid({
			structure : structure,
			url : './fcMortgage_list.action',
			needPage : false,
			autOWidth : true,
			hidden :true,
			height:120,
			isLoad : false,
			keyField : 'id'
		});
		var _this = this;
		appgrid.addListener("rowclick",function(appgrid,rowIndex,event){
			var selId = appgrid.getSelId();
			_this.globalMgr.selId = selId;
			_this.globalMgr.hideshow(selId);
			_this.globalMgr.detailpanel.reload({id:selId})
		});
		return appgrid;
	},
	
	/**
	 * 抵押物详情
	 * @return {}
	 */
	getMgDetail : function(){
		var sysId = this.params.sysid;
		var gvlistId = this.globalMgr.gvlistid;
		var htmlArrs = [
			'<tr>',
			    '<th><label col="name">抵押物名称：</label></th>',
			    	'<td col="name">&nbsp;</td>',
			    '<th><label col="quantity">数量：</label></th>',
			    	'<td  col="quantity">&nbsp;</td>',
			   	'<th><label col="mman">抵押人：</label></th>',
			   		'<td col="mman">&nbsp;</td>',
			'</tr>',
			'<tr>',
			 	'<th ><label col="oldVal" >原价值（元）：</label></th>',
			    	'<td  col="oldVal">&nbsp;</td>',
			    '<th><label col="mapVal" >抵押价值：</label></th>',
			    	'<td  col="mpVal" colspan="3">&nbsp;</td>',
			'</tr>',
			'<tr>',
			 	'<th><label col="conTel">联系电话：</label></th>',
			    	'<td  col="conTel">&nbsp;</td>',
			     '<th><label col="morTime">抵押时间：</label></th>',
			    	'<td col="morTime" colspan=3>&nbsp;</td>',
			'</tr>',
			'<tr>',
			    '<th><label col="address">抵押物所在地：</label></th>',
			    	'<td col="address" colspan = 5>&nbsp;</td>',
			'</tr>',
			FORMDIY_DETAIL_KEY,
			'</tr>',
			     '<th><label col="remark" height:40>备注:</label></th>',
			    	'<td col="remark" colspan="5">&nbsp;</td>',
			'</tr>'
			];
		var _this = this;
		var  detailCfgs = [{
			 cmns : 'THREE', 
			 model : 'single',
			labelWidth : 80,
			 title : '抵押物详情',
			 htmls : htmlArrs,
			 url : './fcMortgage_get.action',
			 prevUrl : './fcMortgage_prev.action',
			 nextUrl : './fcMortgage_next.action',
			 params : {id:-1},
			 formDiyCfg : {sysId:sysId,formdiyCode:gvlistId,formIdName:'id'},
			 callback: {
		        sfn: function(jsonData) {
		        	var unint =jsonData["unint"];
		        		jsonData["quantity"] = jsonData["quantity"]+unint;
		        	var oldVal =jsonData["oldVal"];
		        	if(oldVal){
		        		jsonData["oldVal"] =  Cmw.getThousandths(oldVal)+'元&nbsp;&nbsp;<br/><span style="color:red;font-weight:bold;">(大写：'+Cmw.cmycurd(oldVal)+')</span>';
		        	}
		        	var mapVal = jsonData["mpVal"];
		        	if(mapVal){
		        		jsonData["mpVal"] =  Cmw.getThousandths(mapVal)+'元&nbsp;&nbsp;<br/><span style="color:red;font-weight:bold;">(大写：'+Cmw.cmycurd(mapVal)+')</span>';
		        	}
		        	var sysId = _this.params.sysid;
		        	var formId = jsonData.id;
		        	if(!formId){
		        		formId = -1;
		        	}
		        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_4,sysId:sysId,isNotDisenbaled:true};
		        	_this.attachMentFs.reload(params);
		        }
			  }
		}];
		var cfg = {autoWidth:true,detailCfgs:detailCfgs,hidden : true,isLoad:false};
		var detailpanel = new Ext.ux.panel.DetailPanel(cfg);
		this.attachMentFs = this.globalMgr.createAttachMentFs(this);
		detailpanel.add(this.attachMentFs);
//		detailpanel.doLayout();
		return detailpanel;
	},
	/**
	 *  获取 按钮 Grid 对象
	 */
	getAppPanel : function(){
		var width = CLIENTWIDTH - Ext.getCmp(ACCORDPNLID);
		var height = CLIENTHEIGHT - 180;
		var panel = new Ext.Panel({border:false,width:width,height : height,autoScroll:true});
		var toolBar = this.globalMgr.getToolBar(this);
		this.globalMgr.appgrid = this.getAppGrid();
		panel.add(toolBar);
		panel.add(this.globalMgr.appgrid);
		panel.doLayout();
		return  panel;
	},
	/**
	 * 当新增、修改、删除、起用、禁用 
	 * 数据保存成功后会执行此方法刷新父页面
	 * @param optionType 操作类型 参考 constant.js 文件中的 "OPTION_TYPE" 常量值
	 * 	
	 * @param {} data 
	 * 	1).如果是 新增、修改表单数据保存成功的话. data 参数代表的是表单数据json对象
	 *  2).如果是 删除、起用、禁用 数据处理成功的话.data 参数是 ids 值。例如:{ids:'1001,1002,1003'}
	 */
	refresh:function(optionType,data){
		var _this = this;
		var activeKey = this.globalMgr.activeKey;
		var formId = this.globalMgr.formId;
		if(activeKey =="添加"||activeKey == "编辑"){
			this.globalMgr.detailpanel.reload();
		}
		if(optionType==OPTION_TYPE.DEL){
			this.globalMgr.detailpanel.hide();
		}
		var _this = this;
		this.globalMgr.appgrid.reload({gtype:this.globalMgr.gvlistid,formId:formId,state:"2,1"},function(){
			switch(optionType){
				case OPTION_TYPE.ADD:
				case OPTION_TYPE.EDIT:{
					if(data) _this.finishBussCallback(data);
					break;
				}case OPTION_TYPE.DEL:{
					if(_this.globalMgr.appgrid.getStore().getCount() > 0) return;
					EventManager.get('./fcMortgage_afterDelDt.action',{params:{formId:formId},sfn:function(json_data){
						if(null == json_data.totalSize || json_data.list.length == 0)  _this.unFinishBussCallback(data);;
					}});
					break;
				}
			}
		});
		this.globalMgr.activeKey = null;
	},
	/**
	 *  获取 TreePanel 对象
	 */
	getAppTree : function(){
		var self = this;
		return this.globalMgr.createSysPanel(self);
	},
	/**
	 * 当点击顶部的 系统图标 时，会触发 notify 方法。
	 * @param {} data 选中的 系统图标数据。
	 * 			{id : '',name:'',code:'',url:''} 等。
	 * 			具体值请看 SystemEntity 的 getFields() 方法
	 */
	
	globalMgr : {
		/**
		 * 创建附件FieldSet 对象
		 * @return {}
		 */
		createAttachMentFs : function(_this){
			/* ----- 参数说明： isLoad 是否实例化后马上加载数据 
			 * dir : 附件上传后存放的目录， mortgage_path 定义在 resource.properties 资源文件中
			 * isSave : 附件上传后，是否保存到 ts_attachment 表中。 true : 保存
			 * params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔}
			 *    当 isLoad = false 时， params 可在 reload 方法中提供
			 *  ---- */
			var attachMentFs = new  Ext.ux.AppAttachmentFs({title:'抵押物附件列表',isLoad:false,dir : 'mortgage_path',isSave:true,isNotDisenbaled:true});
			return attachMentFs;
		},
		/**
		 * 表格单击事件
		 * @param {} selId
		 * @return {}
		 */
		hideshow: function(selId){
			var _this = this;
			if(selId==null){
				_this.detailpanel.hide();
			}else{
				_this.detailpanel.show();
			}
			_this.detailpanel.doLayout();
			return _this.detailpanel;
		},
		/**
		 * 根据用户点击的抵/质押物基础数据创建详情面板
		 * @param {} _this
		 * @param {} gvlistId
		 */
		createMgDetailByGvlistId : function(_this,gvlistId){
			var oldgvlistId = _this.globalMgr.gvlistid;
			if(oldgvlistId && oldgvlistId == gvlistId) return;
			_this.globalMgr.gvlistid = gvlistId;
			var old_detailpanel = _this.globalMgr.detailpanel;
			if(old_detailpanel){
				_this.apppanel.remove(old_detailpanel);
			}
			var detailPanel = _this.getMgDetail();
			_this.globalMgr.detailpanel = detailPanel;
			_this.apppanel.add(_this.globalMgr.detailpanel);
			_this.apppanel.doLayout();
		},
	/**
	 *  创建系统面板
	 * param self 主面板对象
	 */
	createSysPanel : function(self){
		var sysPanel = new Ext.Panel({region:'west',title:'抵押物列表',border:false,autoScroll:true,width:125,height : 550});
		var _params={recode:100016};
		EventManager.get('./sysGvlist_list.action',{params:_params,sfn:function(json_data){
			toolBar.disable();
			if(null == json_data.totalSize || json_data.list.length == 0) return;
			var list = json_data.list;
			for(var j = 0 ,i=list.length-1; j<=i; j++){
				var data = list[j];
				var img = data.biconCls;
				if(img==null || img==""){
					img = 'images/big_icons/48x48/apps/achilles.png';
					var boxHtml = "<div class='system_box'><img src='"+img+"'/><br/><span>"+data.name+"</span></div>";
				}else{
					var boxHtml = "<div class='system_box'><img src='"+img+"'/><br/><span>"+data.name+"</span></div>";
				}
				var sysBox = new  Ext.BoxComponent({
					html : boxHtml,
					data : data,
					listeners:{
				      'render': function(cmpt){
				      		var ele = cmpt.el;
				      		ele.on({
				      			'click' : {
				      				fn : function(){
				      					toolBar.enable();
				      					toolBar.controlRight();
				      					if(sysPanel.selEle){
				      						sysPanel.selEle.removeClass('selected_sysbox');
				      					}
				      					ele.addClass('selected_sysbox');
				      					sysPanel.selEle = ele;
				      					if(!self["notify"]){
				      						ExtUtil.error({msg:'必须在实现类中提供  notify(data) 方法！'});
				      					}
				      					self.formId=cmpt.data.formId;
				      					self.notify(cmpt.data);
				      				}
				      			}
				      		});
				      }
				    }
				});
				sysPanel.add(sysBox);
				sysPanel.doLayout();
			}	
		}});
		return sysPanel;
	},
	
	/**
	 * 查询工具栏
	 */
	getToolBar : function(self){
		var barItems = [{
			token :"释放确认", 
			text : Btn_Cfgs.RELEASE_BTN_TXT,
			iconCls:Btn_Cfgs.RELEASE_CLS,
			tooltip:Btn_Cfgs.RELEASE_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"释放确认",self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {saveRights : true,currNode : self.params[CURR_NODE_KEY]}});
		self.globalMgr.toolBar = toolBar;
		/**
		 * 获取button 配置信息
		 * @return {}
		 */
		var buttons = toolBar.getButtons();
		for(var i=0,count=buttons.length; i<count; i++){
			var btnCfg = buttons[i];//.enable();
			if(btnCfg.text="释放确认"){
				self.globalMgr.EditBtn=btnCfg;
			}
		}
		return toolBar;
	},
	/**
	 * 当前激活的按钮文本	
	 * @type 
	 */
	AddBtn: null,
	EditBtn: null,
	DelBtn: null,
	activeKey : null,
	sysId : null,
	gvlistid : null,
	appgrid : null,
	formdiyId : null,
	detailpanel : null,
	_paneltwo : null,
	selId : null,
	toolBar : null,
	formId:null,
	winEdit : {
			show : function(parentCfg){
				var _this = parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent = _this.globalMgr.appgrid;
				parentCfg.parent = parent;
				parentCfg.formId =  _this.globalMgr.formId;
				parentCfg.gvlistid = _this.globalMgr.gvlistid;
				parentCfg.sysId =_this.params.sysid;
				parentCfg.AddBtn= _this.globalMgr.AddBtn
				winModule="MortgageReleEdit";
				var selId=parent.getSelId();
				if(!selId)return;
				if(parent.getSelRow().get("state")=="2"){
					ExtUtil.alert({msg:'该抵押物已释放！'});
					return;
				}
				if(parent.getSelRow().get("state")!="1"){
					ExtUtil.alert({msg:'该抵押物未落实，不能释放！'});
					return;
				}
					Cmw.importPackage('pages/app/workflow/variety/formUIs/release/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
//			}
		}
	}
});

