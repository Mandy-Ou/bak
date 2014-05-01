/**
 * 汇票信息登记
 */
 Ext.namespace("skythink.cmw.workflow.bussforms");
skythink.cmw.workflow.bussforms.ReceiptMsgMgr = function(){
	this.init(arguments[0]);
}
Ext.extend(skythink.cmw.workflow.bussforms.ReceiptMsgMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		var finishBussCallback = tab.finishBussCallback;
		var unFinishBussCallback = tab.unFinishBussCallback;
		this.module = new Cmw.app.widget.AbsPanelView({
			tab : tab,
			params : params,
			getAppCmpt : this.getAppCmpt,
			getToolBar : this.getToolBar,
			changeSize : this.changeSize,
			destroyCmpts : this.destroyCmpts,
			globalMgr : this.globalMgr,
			refresh : this.refresh,
			prefix : Ext.id(),
			finishBussCallback : finishBussCallback,
			unFinishBussCallback : unFinishBussCallback
		});
	},
	refresh : function(){
	},
	/**
	 * 设置面板的大小
	 */
	changeSize : function(wh){
//		this.appPanel.setWidth(wh[0]);
//		this.appPanel.setHeight(wh[1]);
	},
	/**
	 * 创建主面板
	 * @return {}
	 */
	getAppCmpt : function(){
		var _this =this;
		var toolBar = this.getToolBar();
		var appFormPnl = this.globalMgr.createAppFormPnl(_this);
		var appDetail = this.globalMgr.createAppDetail(_this);
		var appGrid = this.globalMgr.createAppGird(_this);
		if (!this.appPanel) {
			this.appPanel = new Ext.Panel({autoScroll:true});
			Cmw.mask(this.appPanel,"正在加载数据,请稍等...");
			EventManager.get('./fuReceiptMsg_get.action',{params:{receiptId:this.globalMgr.applyId},sfn:function(json_data){
				if(json_data==-1){
					ExtUtil.alert({msg:"汇票结算单业务没有办理,不能做汇票信息登记！"});
					toolBar.disable(); 
					return;
				}
				if(json_data.isNotShowFrm == -1){
					appFormPnl.show();
					appDetail.hide();
					appGrid.hide();
					var buttons = _this.globalMgr.toolBar.getSomeButtons(Btn_Cfgs.MODIFY_BTN_TXT+","+Btn_Cfgs.SAVE_BTN_TXT);
					buttons[0].setDisabled(true);
					buttons[1].setDisabled(false);
				}else {
					appFormPnl.hide();
					appDetail.show();
					var buttons = _this.globalMgr.toolBar.getSomeButtons(Btn_Cfgs.MODIFY_BTN_TXT+","+Btn_Cfgs.SAVE_BTN_TXT);
					buttons[0].setDisabled(false);
					buttons[1].setDisabled(true);
					appDetail.reload({id:json_data.id});
					appGrid.show();
					appGrid.reload({receMsgId:json_data.id});
				}
				appFormPnl.setFieldValues(json_data);
				Cmw.unmask(_this.appPanel);
			},ffn:function(json_data){
				ExtUtil.alert({msg:"加载数据出错！"});
				return;
			}});
			this.appPanel.add(toolBar);
			this.appPanel.add(appFormPnl);
			this.appPanel.add(appDetail);
			this.appPanel.add(appGrid);
		}else {
			this.appPanel.show();
		}
		return this.appPanel;
	},
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var self = this;
		var barItems = [{
			token :"编辑", 
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.MODIFY_BTN_TXT,
			handler : function(){
				self.globalMgr.editData(self);
			}
		},{
			token :"保存", 
			text : Btn_Cfgs.SAVE_BTN_TXT,
			iconCls:'page_save',
			tooltip:Btn_Cfgs.SAVE_BTN_TXT,
			handler : function(){
				self.globalMgr.saveData(self);
			}
		}];
		
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		self.globalMgr.toolBar = toolBar;
		return toolBar;
	},
	globalMgr :{
		toolBar : null,
		params : null,
		appMainPanel :null,
		appFormPnl : null,
		appDetail : null,
		appGrid : null,	
		applyId  : this.params.applyId,
		sysId : this.params.sysId,
		appGridToolBar : null,
		
		/**
		 * 保存方法
		 * @param {} _this
		 */
		saveData : function(_this){
			var data = _this.globalMgr.appFormPnl.getValues();
			EventManager.get('./fuReceiptMsg_save.action',{params:data,sfn:function(json_data){
				_this.globalMgr.appFormPnl.hide();
				_this.globalMgr.appDetail.show();
				var buttons = _this.globalMgr.toolBar.getSomeButtons(Btn_Cfgs.MODIFY_BTN_TXT+","+Btn_Cfgs.SAVE_BTN_TXT);
				buttons[0].setDisabled(false);
				buttons[1].setDisabled(true);
				var id = json_data.id;
				if(id){
					_this.globalMgr.appFormPnl.setFieldValue("id",id);
					_this.globalMgr.appDetail.reload({id:id});
					_this.globalMgr.appGrid.show();
					_this.globalMgr.appGrid.reload({receMsgId:id});
				}
			},ffn:function(json_data){
				ExtUtil.alert({msg:"加载数据出错！"});
				return;
			}});
		},
		/***
		 * 编辑方法
		 * @param {} _this
		 * @return {}
		 */
		editData : function(_this){
			var id  =  _this.globalMgr.appFormPnl.getValueByName("id");
			if(!id){
				ExtUtil.alert({msg:"数据加载出错!"});
				return;
			}else{
				 EventManager.get('./fuReceiptMsg_detail.action',{params:{id:id,isEdit:1},sfn:function(json_data){
				 	 _this.globalMgr.appFormPnl.reset();
				 	  _this.globalMgr.appFormPnl.setFieldValues(json_data);
				 	  var buttons = _this.globalMgr.toolBar.getSomeButtons(Btn_Cfgs.MODIFY_BTN_TXT+","+Btn_Cfgs.SAVE_BTN_TXT);
						buttons[0].setDisabled(true);
						buttons[1].setDisabled(false);
				 },ffn:function(json_data){
				 	ExtUtil.alert({msg:"数据加载出错!"});
					return;
				 }});
			}
			_this.globalMgr.appFormPnl.show();
			_this.globalMgr.appDetail.hide();
		},
		/**
		 * 删除方法
		 * @param {} _this
		 * @return {}
		 */
		delDatea : function(_this){
			
		},
		/**
		 * 创建AppFromPnl程序
		 */
		createAppFormPnl : function(_this){
			
			var hide_id = new FormUtil.getHidField({name:'id'});
			
			var hide_receiptId = new FormUtil.getHidField({name:'receiptId'});
			
			var hide_settleId = new FormUtil.getHidField({name:'settleId'});
			
			var code_txtField =  FormUtil.getTxtField({name:'code',fieldLabel:'编号',"width" : 135,allowBlank : false,maxLength:30});
			
			
			
			var rnum_txtField =  FormUtil.getReadTxtField({name:'rnum',fieldLabel:'票号',"width" : 135,allowBlank : false,maxLength:30});
			
			var amount_mon =  FormUtil.getMoneyField({name:'amount',fieldLabel:'金额',"width" : 135,allowBlank : false});
			
			var rate_Doub =  FormUtil.getDoubleField({name:'rate',fieldLabel:'利息',"width" : 130,allowBlank: false,decimalPrecision:2,unitText:'%'});
			
			var tiamount_mom =  FormUtil.getMoneyField({name:'tiamount',fieldLabel:'汇票收费',"width" : 130,allowBlank : false});
			
			var name_txtField =  FormUtil.getReadTxtField({name:'name',fieldLabel:'供票人',"width" : 135,allowBlank : false,maxLength:50});
			
			var barItems = [{type:'label',text:'名称'},{type:'txt',name:'empName'}];
			var structure = [{header: '名称',name: 'empName',width:150}];
			
			
			var serviceMan_comboxGrid = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '业务人员',
			    name: 'serviceMan',
			    barItems : barItems,
			    structure:structure,
			    dispCmn:'empName',
			    isAll:false,
			    keyField:'userId',
			    allowBlank : false,
			    autoScroll : true,
			    url : './sysUser_list.action',
			    needPage : true,
			    showLoad : true,
			    width: 135
			});
			var fset1 = FormUtil.createLayoutFieldSet({
				title : '汇票信息',
				 collapsed: false
			}, [hide_id,hide_receiptId,hide_settleId,{
				cmns : FormUtil.CMN_THREE,
				fields : [
						code_txtField,rnum_txtField,amount_mon,
						rate_Doub, tiamount_mom,name_txtField,serviceMan_comboxGrid
					]
			}]);
			/*================汇票信息编辑=====================*/
			var dat_ticketDate =  FormUtil.getDateField({allowBlank : true,fieldLabel:'贴现票日期',width: 135,name:'ticketDate',allowBlank: false});
			var txt_ticketMan =   FormUtil.getTxtField({allowBlank : true,fieldLabel:'收票人',width: 135,name:'ticketMan',maxLength:30,allowBlank: false});
			var dat_fundsDate =  FormUtil.getDateField({allowBlank : true,fieldLabel:'资金到账日期',width: 135,name:'fundsDate',allowBlank: false});
			var ticketRate_Doub =  FormUtil.getDoubleField({name:'ticketRate',fieldLabel:'贴现利率',"width" : 130,allowBlank : false,decimalPrecision:2,unitText:'%'});
			var charge_mon =  FormUtil.getMoneyField({name:'charge',fieldLabel:'贴现收费',"width" : 135,allowBlank : false});
			var funds_mon =  FormUtil.getMoneyField({name:'funds',fieldLabel:'到账金额',"width" : 135,allowBlank : false});
			var adultMoney_mon =  FormUtil.getMoneyField({name:'adultMoney',fieldLabel:'提成',"width" : 135,allowBlank : false});
			var interest_mon =  FormUtil.getMoneyField({name:'interest',fieldLabel:'利润',"width" : 135,allowBlank : false});
			var remark  =  FormUtil.getTAreaField({height:50,fieldLabel:'备注',maxLength:100,"width" : 500});
			var fset2 = FormUtil.createLayoutFieldSet({
				title : '汇票贴现信息'
			}, [{
				cmns : FormUtil.CMN_THREE,
				fields : [dat_ticketDate,txt_ticketMan,dat_fundsDate,ticketRate_Doub,charge_mon,funds_mon,adultMoney_mon,interest_mon]	
			},remark]);
			
			
			var layout_fields = [hide_id, fset1,fset2];
			var frm_cfg = {
				labelWidth : 115,
				 title : '汇票信息登记', 
				 hidden : true,
				 closable  : true,
			    url: './fuReceiptMsg_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			_this.globalMgr.appFormPnl = appform_1;
			return appform_1;
			
		},
		/**
		 * 创建汇票详情面板
		 */
		createAppDetail : function(_this){
			var htmlArrs_1 = [
						'<tr>' +
							'<th col="code">编号</th> <td col="code" >&nbsp;</td>' +
							'<th col="rnum">票号</th> <td col="rnum" >&nbsp;</td>' +
							'<th col="amount">金额</th> <td col="amount" >&nbsp;</td>' +
						'</tr>', 
						'<tr>' +
							'<th col="rate">利润</th> <td col="rate" >&nbsp;</td>' +
							'<th col="tiamount">收费</th> <td col="tiamount" >&nbsp;</td>' +
							'<th col="name">供票人</th> <td col="name" >&nbsp;</td>' +
						'</tr>', 
						'<tr>' +
							'<th col="serviceMan">业务员</th> <td col="serviceMan" colspan = 5>&nbsp;</td>' +
						'<tr>'+ 
							'<th  colspan = 6><span  style="color:blue;float:left;padding-left:10px;"> <b> >>>汇票贴现信息详情</b></span></th>' +
						'</tr>', 
						'<tr>' +
							'<th col="ticketDate">贴现票日期</th> <td col="ticketDate" >&nbsp;</td>' +
							'<th col="ticketMan">收票人</th> <td col="ticketMan" >&nbsp;</td>' +
							'<th col="fundsDate">资金到账日期</th> <td col="fundsDate" >&nbsp;</td>' +
						'</tr>', 
						'<tr>' +
							'<th col="ticketRate">贴现利率</th> <td col="ticketRate" >&nbsp;</td>' +
							'<th col="charge">收费</th> <td col="charge" >&nbsp;</td>' +
							'<th col="funds">到账金额</th> <td col="funds" >&nbsp;</td>' +
						'</tr>', 
						'<tr>' +
							'<th col="adultMoney">提成</th> <td col="adultMoney" >&nbsp;</td>' +
							'<th col="interest">利率</th> <td col="interest" colspan=3>&nbsp;</td>' +
						'</tr>', 
						'<tr height = "50">' +
							'<th col="remark">备注</th> <td col="remark"  colspan=5 >&nbsp;</td>' +
						'</tr>'
						];
				var detailCfgs_1 = [{
				    cmns: 'THREE',
				    model: 'single',
				    labelWidth: 110,
				    title : '汇票信息详情', 
				    htmls: htmlArrs_1,
				    url: './fuReceiptMsg_detail.action',
				    params: {
				    	
				    },
				    callback: {
				        sfn: function(jsonData) {
				        	_this.globalMgr.callback(jsonData);
				        }
				    }
			}];
			
			var detailPanel_1 = new Ext.ux.panel.DetailPanel({
			    autoWidth : true,
			    detailCfgs: detailCfgs_1,
			    border : false,
			    isLoad : false,
			    hidden : true,
			    attachLoad: function(params, cmd) {
			    	
			    }
			});
			_this.globalMgr.appDetail = detailPanel_1;
			return detailPanel_1;
		},
		/**
		 *  处理回调函数
		 * @param {} jsonData
		 */	
		callback : function(jsonData){
			jsonData["amount"] =  Cmw.getThousandths(jsonData["amount"])+"元&nbsp;<span style='color:red;font-weight:bold;'>("+Cmw.cmycurd(jsonData["amount"])+")</span>";
			jsonData["rate"]  = jsonData["rate"]+"%";
			jsonData["tiamount"]  = jsonData["tiamount"]+"元&nbsp;<span style='color:red;font-weight:bold;'>("+Cmw.cmycurd(jsonData["tiamount"])+")</span>";
			jsonData["ticketRate"]  = jsonData["ticketRate"]+"%";
			jsonData["charge"]  = jsonData["charge"]+"元&nbsp;<span style='color:red;font-weight:bold;'>("+Cmw.cmycurd(jsonData["charge"])+")</span>";
			jsonData["funds"]  = jsonData["funds"]+"元&nbsp;<span style='color:red;font-weight:bold;'>("+Cmw.cmycurd(jsonData["funds"])+")</span>";
			jsonData["adultMoney"]  = jsonData["adultMoney"]+"元&nbsp;<span style='color:red;font-weight:bold;'>("+Cmw.cmycurd(jsonData["adultMoney"])+")</span>";
			jsonData["interest"]  = jsonData["interest"]+"元&nbsp;<span style='color:red;font-weight:bold;'>("+Cmw.cmycurd(jsonData["interest"])+")</span>";
		},
		/***
		 * 创建表格toolBar 工具栏
		 */
		appGridTbar : function(_this){
			var barItems = [{
				text : Btn_Cfgs.INSERT_BTN_TXT,
				iconCls:'page_add',
				tooltip:Btn_Cfgs.INSERT_BTN_TXT,
				handler : function(){
					_this.globalMgr.addEditAppGrid(_this);
				}
			},{
				token :"保存", 
				text : Btn_Cfgs.SAVE_BTN_TXT,
				iconCls:'page_save',
				tooltip:Btn_Cfgs.SAVE_BTN_TXT,
				handler : function(){
					_this.globalMgr.saveAppGridDatas(_this);
				}
			},{
				text : Btn_Cfgs.DELETE_BTN_TXT,
				iconCls:'page_delete',
				tooltip:Btn_Cfgs.DELETE_BTN_TXT,
				handler : function(){
					_this.globalMgr.delAppGridData(_this);
				}
			}];
			
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
			_this.globalMgr.appGridToolBar = toolBar;
			return toolBar;
		},
		/**
		 * 创建提成表
		 */
		createAppGird  : function(_this){
			var tbar = _this.globalMgr.appGridTbar(_this);
			var structure_1 = [
			{
			    header: '提成人',
			    name: 'adultMan'
			},
			{
			    header: '提成比例',
			    name: 'adultProp'
			},
			{
			    header: '提成金额',
			    name: 'adultMoney',
			    renderer: function(val) {
			        val = Cmw.getThousandths(val);
			        return val;
			    }
			},{
				header: '备注',
			    name: 'remark',
			    width : 150
			},{
				 header: 'id',
			    name: 'id',
			    hidden : true
			}];
			var adultMan_txt = FormUtil.getTxtField({fieldLabel : '提成人',name:'adultMan'});
			var adultProp_Int = FormUtil.getIntegerField({fieldLabel : '提成比例',name:'adultProp'});
			var adultMoney_mon = FormUtil.getMoneyField({fieldLabel : '提成金额',name:'adultMoney'});
			var remark = FormUtil.getTAreaField({fieldLabel : '备注',name:'remark'});
			var editEles = {0:adultMan_txt,1:adultProp_Int,2:adultMoney_mon,3:remark};
			var appgrid = new Ext.ux.grid.MyEditGrid({
				tbar:tbar,
				selectType : 'check',
				title:'汇票利润提成表',
			    structure: structure_1,
			    url: './fuReceInterest_list.action',
			    editEles : editEles,
			    hidden : true,
			    needPage: true,
			    isLoad: false,
			    height : 220,
			    keyField: 'id'
			});
			_this.globalMgr.appGrid = appgrid;
		 	return appgrid;
		},
		/**
		 * 删除表格中的数据
		 * @param {} _this
		 */
		delAppGridData : function(_this){
			var ids = _this.globalMgr.appGrid.getSelIds();
			if(!ids){
				ExtUtil.alert({msg:"请选择表格中的 数据！"});
				return;
			}else{
				EventManager.get('./fuReceInterest_delete.action',{params:{ids:ids},sfn:function(json_data){
					var receMsgId = _this.globalMgr.appFormPnl.getValueByName("id");
					_this.globalMgr.appGrid.reload({receMsgId:receMsgId});
				},ffn:function(json_data){
					
				}});
			}
		},
		/**
		 * 保存编辑表格中的数据
		 * @param {} _this
		 */
		saveAppGridDatas : function(_this){
			var data = _this.globalMgr.getBatchDatas(_this);
			var batchDatas = Ext.encode(data);
			EventManager.get('./fuReceInterest_save.action',{params:{batchDatas:batchDatas},
				sfn:function(json_data){
				ExtUtil.alert({msg:"数据保存成功！"});
				return;
			},ffn:function(json_data){
				ExtUtil.alert({msg:"加载数据出错！"});
				return;
			}});
			
		},
		/**
		 * 向表格中添加数据
		 */
		addEditAppGrid : function(_this){
			var data = {adultMan:'',adultProp:'',adultMoney:'',remark:''};
			 _this.globalMgr.appGrid.addRecord(data);
			  _this.globalMgr.appGrid.view.refresh();
		},
		/**
		 * 表格里面的数据
		 * @return {}
		 */
		getBatchDatas : function(_this){
			var store = _this.globalMgr.appGrid.getStore();
			var receMsgId = _this.globalMgr.appFormPnl.getValueByName("id");
			var arr = [];
			for(var i=0,count = store.getCount(); i<count; i++){
				var record = store.getAt(i);
				var id = record.get('id');
				var adultMan = record.get('adultMan');
				var adultProp = record.get('adultProp');
				var adultMoney = record.get('adultMoney');
				var remark = record.get('remark');
				arr[arr.length] = {id : id,receMsgId:receMsgId,adultMan:adultMan,
					adultProp:adultProp,adultMoney:adultMoney,remark:remark};
			}
			return arr;
		}
	},
	
	destroyCmpts :function(){
		
	}
});
