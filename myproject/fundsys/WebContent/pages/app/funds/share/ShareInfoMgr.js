/*同心日科技公司Erp 系统 命名空间*/
Ext.namespace("skythink.cmw.workflow.bussforms");
/**
 * 
 */
skythink.cmw.workflow.bussforms.ShareInfoMgr = function(){
	this.init(arguments[0]);
}

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.workflow.bussforms.ShareInfoMgr,Ext.util.MyObservable,{
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
				getDetailPanel:this.getDetailPanel,
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
			var _this = this;
			var selId  = null;
			 EventManager.get('./fuShareInfoTran_listAll.action',{params:{formid:_this.params.applyId},sfn:function(json_data){
				_this.globalMgr.date=json_data.list;
//				 Cmw.print(_this.globalMgr.date);
			 }});
			var appPanel = new Ext.Panel({autoScroll:true,border : false,id:'panel1'});
			this.globalMgr.appgrid=this.getAppGrid();
			var heightVal=400;
			appPanel.add({items:[this.getToolBar(),this.getDetailPanel(),this.globalMgr.appgrid]});
			return appPanel;
		},
		/**
		 * 刷新方法
		 * @param {} optionType
		 * @param {} data
		 */
		refresh:function(optionType,data){},
		/**
		 * 组件大小改变方法
		 * @param {} whArr
		 */
		changeSize : function(whArr){
			var width = CLIENTWIDTH - Ext.getCmp(ACCORDPNLID);
			var height = CLIENTHEIGHT - 180;
			this.appPanel.setWidth(width);
			this.appPanel.setHeight(height);
		},
		/**
		 * 查询工具栏
		 */
		getToolBar : function(){
			var self = this;
			var barItems = [{
				text : Btn_Cfgs.CAPSAVE_LABEL_TEXT,
				iconCls:'page_edit',
				tooltip:Btn_Cfgs.CAPSAVE_LABEL_TEXT,
				token : '保存',
				handler : function(){
					var that = this;
						self.globalMgr.winEdit.show({key:that.token,self:self});
					}
			},{
				text : Btn_Cfgs.DELETE_BTN_TXT,
				iconCls:'page_delete',
				tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
				token : '删除'
			}];
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
		},
		 getAppGrid:function(){
				var structure_1 = [{
					header :'id',
					name:'id',
					hidden : true,
					 width: 120
				},
				{
				    header: '借款人姓名',
				    name: 'name'
				},{
				    header: '贷款金额',
				    name: 'appAmount',	renderer: function(val) {
			    return (val && val>0) ? Cmw.getThousandths(val)+'元' : '';}
					},
				{
				    header: '出借人名称',
				    name: 'sex',
				    width: 60
				},
				{
				    header: '收件日期',
				    name: 'phone',
				    width: 90
				},
				{
				    header: '交接日期',
				    name: 'contactTel',
				    width: 90
				},
				{
				    header: '资料保管人',
				    name: 'birthday',
				    width: 90
				},
				{
				    header: '备注',
				    name: 'remark',
				    width: 90
				}];
				var appgrid = new Ext.ux.grid.AppGrid({
				    structure: structure_1,
			  		 url : './fuShareData_list.action',
				    needPage: false,
				    height:300
				});
				return appgrid;},
		/**	
		 * 创建详情面板
		 */
		 getDetailPanel : function() {
				var _this = this;
					var hide_customerId = FormUtil.getHidField({
							fieldLabel : 'customerId',
							name : 'customerId'
						});
				var hide_contractId = FormUtil.getHidField({
					fieldLabel : 'contractId',
					name : 'contractId'
				});
				var hide_cid = FormUtil.getHidField({
					fieldLabel : 'cid',
					name : 'cid'
				});
				var txt_name = FormUtil.getTxtField({
							fieldLabel : '借款人',
							name : 'name',
							"allowBlank" : false,
							"width" : "125"
						});
				var txt_code = FormUtil.getTxtField({
							fieldLabel : '出借人',
							"allowBlank" : false,
							name : 'loanName',
							"width" : "125"
						});
				var mon_appAmount = FormUtil.getMoneyField({
							fieldLabel : '借款金额',
							name : 'loanMoney',
							"allowBlank" : false,
							"width" : 160, 
							autoBigAmount : true,
							unitText : '大写金额',
							unitStyle : 'margin-left:2px;color:red;font-weight:bold'
						});
				var rightH = "200";
				var bdat_payDate = FormUtil.getDateField({
							fieldLabel : '收件日期',
							name : 'starDate',
							"allowBlank" : false,
							"width" : 125
						});
				var bdat_endDate = FormUtil.getDateField({
				fieldLabel : '交接日期',
				name : 'endDate',
				"allowBlank" : false,
				"width" : 125
			});
				var txt_infoCustodyMan = FormUtil.getTxtField({
							fieldLabel : '资料保管人',
							name : 'infoCustodyMan',
							"allowBlank" : false,
							"width" : "125"
						});
				var txt_textarea = FormUtil.getTAreaField({
							fieldLabel : '备注',
							name : 'clause',
							"width" : "272"
						});
				/*----------- 基本合同信息设置 ---------*/
				var fset_1 = FormUtil.createLayoutFieldSet({
							title : '基本合同信息'/* ,height:800 */
						}, [
								{
									cmns : FormUtil.CMN_THREE,
									fields : [txt_name,txt_code,
											mon_appAmount, mon_appAmount,bdat_payDate,
											 bdat_endDate, txt_infoCustodyMan]
								}, txt_textarea,
								hide_customerId,hide_contractId,hide_cid]);
				var layout_fields = [fset_1];
				var frm_cfg = {
					title : '委托合同信息编辑',
					labelWidth : 110,
					autoHeight : true
					,
					url : './fuShareData_save.action'
				};
				var applyForm = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
				this.attach = this.globalMgr.createAttachMentFs(this);
				applyForm.add(this.attach);    
				_this.globalMgr.appform=applyForm;
				return applyForm;},
	/**
	 * 组件销毁方法
	 */
	destroyCmpts : function(){},
	globalMgr : {
		LoanId:null,
		appform:null,
		date:null,
		AddButton:null,
		 height : null,
	 	custType:null,
		AddBtn : null,
		Dialog:null,
		DelBtn : null,
		EditBtn : null,
		TJBtn : null,
		id : null,
		activeKey: null,
		formId:this.params.applyId,
		appgrid :null,
		appgrid2 :null,
		unAmount : null,
		sysId : this.params.sysid,
		detailPanel_1 : null
		,
		/**
		 * 创建附件FieldSet 对象
		 * 
		 * @return {}
		 */
			createAttachMentFs : function(_this) {
				/*
				 * ----- 参数说明： isLoad 是否实例化后马上加载数据 dir : 附件上传后存放的目录，
				 * mortgage_path 定义在 resource.properties 资源文件中 isSave :
				 * 附件上传后，是否保存到 ts_attachment 表中。 true : 保存 params :
				 * {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment
				 * 中的说明,formId:业务单ID，如果有多个用","号分隔} 当 isLoad = false 时，
				 * params 可在 reload 方法中提供 ----
				 */
				var uuid=Cmw.getUuid();
				var attachMentFs = new Ext.ux.AppAttachmentFs({
							title : '相关材料附件',
							isLoad : false,
							dir : 'mort_path',
							isSave : true,
							isNotDisenbaled : true,
							params:{sysId:-1,formType:ATTACHMENT_FORMTYPE.FType_42,formId:uuid}
						});
				return attachMentFs;
			},
			winEdit : {
			show : function(parentCfg){
				var _this = parentCfg.self;
				var winkey=parentCfg.key;
				var parent ={};
				_this.globalMgr.formId = _this.params.applyId;
				parent.formId = _this.globalMgr.formId;
//				parent.appgrid = _this.globalMgr.appgrid;
				parent.AddBtn  = _this.globalMgr.AddBtn;
				parent.sysId = _this.globalMgr.sysId;
				parentCfg.parent = parent;
				_this.globalMgr.activeKey = winkey;
				if(winkey=="保存"){
					var params= _this.globalMgr.date;
					alert(params);
					if(params){
					alert(_this.globalMgr.appform);
					
					var sfns=[function callback(jsonData){
						alert(jsonData);
					}];
					EventManager.frm_save(_this.globalMgr.appform,{sfn:sfns});
					Ext.Ajax.request({url: './fuShareInfoTran_save.action',params:{Edit:Ext.encode(params)}, success: function(json_data) {Cmw.print(json_data);
					Ext.MessageBox.alert("提示","数据保存成功！");}});
					}else{
					Ext.Msg.alert("提示","没有要保存的数据");
					return;
					}
				}
			}			
		}
	}
});
