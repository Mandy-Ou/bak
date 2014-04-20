/*同心日科技公司Erp 系统 命名空间*/
Ext.namespace("skythink.cmw.workflow.bussforms");
/**
 * 质押合同
 */
skythink.cmw.workflow.bussforms.PleContractMgr = function(){
	this.init(arguments[0]);
}

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.workflow.bussforms.PleContractMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		/*由必做或选做业务菜单传入的回調函数，主要功能：
		 * finishBussCallback : 当业务表单保存后，更新必做或选做事项为已做,
		 * unFinishBussCallback : 当删除业务表单后，取消已做标识
		 * */
		var finishBussCallback = tab.finishBussCallback;
		var unFinishBussCallback = tab.unFinishBussCallback;
		this.module = new Cmw.app.widget.AbsPanelView({
			tab : tab,
			params : params,/*apptabtreewinId,tabId,sysid*/
			getAppCmpt : this.getAppCmpt,
			getToolBar : this.getToolBar,
			createForm : this.createForm,
			getAppGrid : this.getAppGrid,
			createDetailPnl : this.createDetailPnl,
			changeSize : this.changeSize,
			destroyCmpts : this.destroyCmpts,
			globalMgr : this.globalMgr,
			refresh : this.refresh,
			prefix : Ext.id(),
			finishBussCallback : finishBussCallback,
			unFinishBussCallback : unFinishBussCallback
		});
	},
	/**
	 * 获取组件方法
	 */
	getAppCmpt : function(){
		var appPanel = new Ext.Panel({autoScroll:true});
		appPanel.add({items:[this.getToolBar(),this.getAppGrid(),this.createDetailPnl()]});
		return appPanel;
	},
	/**
	 * 刷新方法
	 * @param {} optionType
	 * @param {} data
	 */
	refresh:function(optionType,data){
		var _this = this;
		var activeKey = _this.globalMgr.activeKey;
		_this.globalMgr.appgrid.reload({formId:_this.globalMgr.formId}	,function(){
			switch(optionType){
				case OPTION_TYPE.ADD:
				case OPTION_TYPE.EDIT:{
					if(data) _this.finishBussCallback(data);
					break;
				}case OPTION_TYPE.DEL:{
					if(_this.globalMgr.appgrid.getStore().getCount() > 0) return;
					 _this.unFinishBussCallback(data);
					break;
				}
			}
		});
		if(optionType == OPTION_TYPE.DEL){
			_this.globalMgr.detailPanel_1.hide();
		}
		_this.globalMgr.activeKey = null;
	},
	/**
	 * 组件大小改变方法
	 * @param {} whArr
	 */
	changeSize : function(whArr){
	},
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var self = this;
		var barItems = [{
			text :Btn_Cfgs.PLECONTRACT_ADD_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.PLECONTRACT_ADD_TIP_BTN_TXT,
			handler : function(){
				EventManager.get('./fcLoanContract_get.action',{params:{ formId :  self.globalMgr.formId},sfn :function(data){
					if(data==-1){
						ExtUtil.alert({msg:"没有添加借款合同不能进行添加质押合同！"});
						return;
					}
					self.globalMgr.winEdit.show({key:"添加质押合同",self:self});
				}});
				
			}
		},{type:"sp"},{
			text : Btn_Cfgs.PLECONTRACT_EDIT_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.PLECONTRACT_EDIE_TIP_BTN_TXT,
			handler : function(){
				var selId = self.globalMgr.appgrid.getSelId();
				if(selId!=null){
					self.globalMgr.winEdit.show({key:"编辑质押合同",optionType:OPTION_TYPE.EDIT,self:self});
				}
				
			}
		},{type:"sp"},{
			text : Btn_Cfgs.PLECONTRACT_DEL_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.PLECONTRACT_DEL_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./fcPleContract_delete.action',{type:'grid',cmpt:self.globalMgr.appgrid,optionType:OPTION_TYPE.DEL,self:self});
			}
		},{
			text : Btn_Cfgs.PLECONTRACT_SC_BTN_TXT,
			iconCls:'page_query',
			tooltip:Btn_Cfgs.PLECONTRACT_SC_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.query(self);
			}
		},
			{
			text : Btn_Cfgs.PLECONTRACT_DATEIL_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.PLECONTRACT_DATEIL_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"打印质押合同",self:self});
			}
		},{
			text : Btn_Cfgs.PLECONTRACT_DOWNLOAD_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.PLECONTRACT_DOWNLOAD_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"质押合同模板下载",optionType:OPTION_TYPE.EDIT,self:self});
			}
		}];
		
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
		toolBar.hideButtons(Btn_Cfgs.PLECONTRACT_SC_BTN_TXT+','+Btn_Cfgs.PLECONTRACT_DATEIL_BTN_TXT+','+Btn_Cfgs.PLECONTRACT_DOWNLOAD_BTN_TXT);
		var buttons = toolBar.getButtons();
//		self.globalMgr.getBtn(buttons,self);
		return toolBar;
	},
	/**
	 * 表格信息
	 * @return {}
	 */
	 getAppGrid:function(){
		var structure_1 = [{
		    header: '贷款申请单ID',
		    hidden : true,
		    name: 'formId'
		},
		{
		    header: '借款合同ID',
		    hidden : true,
		    name: 'loanConId'
		},
		{
		    header: '质押物ID列表',
		    hidden : true,
		    name: 'pleIds'
		},
		{header : '可用标识',name : 'isenabled',width:60,renderer : function(val){
				switch (val) {
				case "0":
					val = "禁用";
					break;
				case "1":
					val = "可用";
					break;
				default:
					val = "暂未设置";
					break;
				}
				return val;
			}},
		{
		    header: '质押合同编号',
		    name: 'code'
		},
		{
		    header: '借款合同号',
		    name: 'borrCode'
		},
		{
		    header: '借款人',
		    name: 'assMan'
		},
		{
		    header: '贷款金额',
		    name: 'appAmount',
	     	renderer: Render_dataSource.moneyRender
		},
		{
		    header: '贷款利率',
		    name: 'rate',
		    renderer:function(val){
		    	if(val!=null){
		    		val +='%';
		    	}
		    	return val;
		    }
		},
		{
		    header: '贷款起始日期',
		    name: 'startDate'
		},
		{
		    header: '贷款截止日期',
		    name: 'endDate'
		},
		{
		    header: '合同签订日期',
		    name: 'sdate'
		},
		{
		    header: '确认价值金额',
		    name: 'conAmount',
		     renderer: Render_dataSource.moneyRender
		},
		{
		    header: '合同中未涉及条款',
		    name: 'clause'
		}];
		var appgrid = new Ext.ux.grid.AppGrid({
		    structure: structure_1,
		    url: './fcPleContract_list.action',
		     needPage: false,
		    isLoad: false,
		    height : 150,
		    keyField: 'id',
		    listeners : {
			   	render : function(grid){
			   		 _this.globalMgr.query(_this);
			   	}
		    }
		});
		
		var _this = this;
    	appgrid.addListener('rowclick',function(appgrid,rowIndex,event){
    		var attachMentFs = _this.globalMgr.createAttachMentFs(_this);
			var selId = appgrid.getSelId();
			_this.globalMgr.selId = selId;
			_this.globalMgr.detailPanel_1.show();
			_this.globalMgr.detailPanel_1.reload({id:selId})
		});
    	
		this.globalMgr.appgrid=appgrid;
		return appgrid;
	 },
	 	/**
	 * 质押物详情
	 * @return {}
	 */
	createDetailPnl : function(){
			var appgrid=this.globalMgr.appgrid;
			 var sysId = this.globalMgr.sysId;
			var gvlistId = this.globalMgr.gvlistid;
			var htmlArrs = [
				'<tr>',
				    '<th><span>*</span><label col="code">质押合同编号：</label></th>',
				    '<td  col="code">&nbsp;</td>',
				    '<th><span>*</span><label col="borrCode">借款合同号：</label></th>',
				    '<td col="borrCode">&nbsp;</td>',
				    '<th><span>*</span><label col="assMan">借款人：</label></th>',
				    '<td col="assMan">&nbsp;</td>',
				'</tr>',
				'<tr>',
					'<th><label col="rate">贷款利率(%)：</label></th>',
				    '<td  col="rate" >&nbsp;</td>',
					'<th><span>*</span><label col="appAmount">贷款金额：</label></th>',
				    '<td  col="appAmount" colspan="3">&nbsp;</td>',
				    
				   
				'</tr>',
				'<tr>',
				 '<th><label col="startDate">贷款起始日期：</laebl></th>',
				    '<td  col="startDate">&nbsp;</td>',
				    '<th><label col="endDate">贷款截止日期：</label></th>',
				    '<td col="endDate">&nbsp;</td>',
				    '<th><label col="sdate">合同签订日期：</label></th>',
				    '<td col="sdate">&nbsp;</td>',
				'</tr>',
				'<tr>',
					'<th><label col="conAmount">确认价值金额：</laebl></th>',
				    '<td  col="conAmount">&nbsp;</td>',
				    '<th><label col="pleName">质押物：</laebl></th>',
				    '<td  col="pleName"  colspan="3">&nbsp;</td>',
				'</tr>',
//				FORMDIY_DETAIL_KEY,
				 '<tr>',
				    '<th><label col="clause">备注:</label></th>',
				    '<td col="clause" colspan="5">&nbsp;</td>',
				'</tr>'   
				];
			
			var _this = this;
			var  detailCfgs = [{
				 cmns : 'TREE', 
				 model : 'single',
				 labelWidth : 110,
				 title : '质押合同物详情',
				 htmls : htmlArrs,
				 url : './fcPleContract_get.action',
				 params : {id:-1},
//				 formDiyCfg : {sysId:sysId,formdiyCode:gvlistId,formIdName:'id'},
				 callback:{
				 	sfn:function(jsonData){
			        	var appAmount =jsonData["appAmount"];
			        	if(appAmount){
			        		jsonData["appAmount"] =  Render_dataSource.moneyRender(appAmount)+'&nbsp;&nbsp;<span style="color:red;font-weight:bold;">(大写：'+Cmw.cmycurd(appAmount)+')</span>';
			        	}
			        	var conAmount = jsonData["conAmount"];
			        	if(conAmount){
				        	jsonData["conAmount"] =  Render_dataSource.moneyRender(conAmount)+'&nbsp;&nbsp;<br/><span style="color:red;font-weight:bold;">(大写：'+Cmw.cmycurd(conAmount)+')</span>';
			        	}
			        	var formId = jsonData.id;
			        	if(!formId){
			        		formId = -1;
			        	}
			        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_10,sysId:sysId,isNotDisenbaled:true};
			        	_this.attachMentFs.reload(params);
				 	}
				 }
			}];
			var cfg = {autoWidth:true,detailCfgs:detailCfgs,hidden : true};
			var detailpanel = new Ext.ux.panel.DetailPanel(cfg);
			this.attachMentFs = this.globalMgr.createAttachMentFs(this);
			detailpanel.add(this.attachMentFs);
			this.globalMgr.detailPanel_1 = detailpanel;
			return detailpanel;
	},
	
	/**
	 * 组件销毁方法
	 */
	destroyCmpts : function(){
		
	},
	globalMgr : {
		query : function(_this){
			var params ={formId:_this.globalMgr.formId};
			EventManager.query(_this.globalMgr.appgrid,params);
		},
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
			var attachMentFs = new  Ext.ux.AppAttachmentFs({title:'质押合同附件列表',isLoad:false,dir : 'mort_path',isSave:true,isNotDisenbaled:true});
			return attachMentFs;
		},
	
		/**
		 * 删除合同
		 */
		 btuclick :function(self){
		 	if(arguments && arguments[0] == 'yes'){
				EventManager.get('./fcPleContract_delete.action',{params:{ formId : self.globalMgr.formId}},{optionType:OPTION_TYPE.DEL,self:self});
				self.globalMgr.hide();
//				self.globalMgr.EditBtn.disable();
//				self.globalMgr.DelBtn.disable();
			} else{
				return;
			}
		 },
		 /**
		  * 获取getToolBar 上的Btn 
		  */
		  getBtn : function(buttons,self){
		  	for(var i=0,count=buttons.length; i<count; i++){
			var btnCfg = buttons[i];
			if(btnCfg.text==Btn_Cfgs.PLECONTRACT_EDIT_BTN_TXT){
				self.globalMgr.EditBtn=btnCfg;
				self.globalMgr.EditBtn.disable();
			}
			if(btnCfg.text== Btn_Cfgs.PLECONTRACT_DEL_BTN_TXT){
				self.globalMgr.DelBtn=btnCfg;
				self.globalMgr.EditBtn.disable();
			}
		}
		  },
		/**
		 * 显示面板详情
		 * @return {}
		 */
		show: function(){
			this.globalMgr.detailPanel_1.show();
		},
		hide : function(){
			this.globalMgr.detailPanel_1.hide();
		},
		DelBtn : null,
		EditBtn : null,
		id : null,
		activeKey: null,
		custType : this.params.custType,
		customerId :  this.params.customerId,
		formId:this.params.applyId,
		appgrid :null,
		sysId : this.params.sysid,
		detailPanel_1 : null,
			winEdit : {
			show : function(parentCfg){
				var _this = parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent =null;
				parent={
					formId:_this.globalMgr.formId,appgrid:_this.globalMgr.appgrid,
					custType:_this.globalMgr.custType,
					customerId : _this.globalMgr.customerId
					};
				parentCfg.parent = parent;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{ 
					var winModule=null;
					if(winkey=="添加质押合同" || winkey=="编辑质押合同"){
						winModule="PleContractEdit";
					}
					Cmw.importPackage('pages/app/workflow/variety/formUIs/plecontract/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		}
	}
});

