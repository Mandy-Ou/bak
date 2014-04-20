/**
 * 借款申请单
 * 
 * @author liting
 */
define(function(require, exports) {
	exports.viewUI = {
		appMainPanel : null,
		appForm : null,
		tab : null,
		params : null,
		attachMentFs:null,
		createAttachMentFs : null,
		appCmpts : {},
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
			this.appForm = this.createFrmPnl();
			var appPanel = new Ext.Panel({autoHeight: true});
			appPanel.add(this.appForm);
			this.attachMentFs=this.createAttachMentFs(this);
			this.appForm.add(this.attachMentFs);
			this.appMainPanel = appPanel;
			return this.appMainPanel;
		},
		/**
		 * form表单
		 */
		createFrmPnl : function() {
			var _self = this;
			var wd = 240;
			// 流程实例id
			var txt_procId = FormUtil.getHidField({
						name : 'procId',
						value : -1
					});
			var txt_code = FormUtil.getReadTxtField({
						fieldLabel : '还款单编号',
						name : 'code',
						"width" : wd,
						"allowBlank" : false,
						"maxLength" : "20"
					});

			var bdat_modifyTime = FormUtil.getDateField({
						fieldLabel : '申请日期',
						name : 'modifyTime',
						"width" : wd
					});

			var txt_loanMan = FormUtil.getReadTxtField({
						fieldLabel : '还款人',
						name : 'loanMan',
						"width" : wd,
						"allowBlank" : false
					});

			var txt_deptId = FormUtil.getHidField({
						fieldLabel : '部门Id',
						name : 'deptId',
						"width" : wd
					});
			
			var txt_deptName = FormUtil.getReadTxtField({
				fieldLabel : '部门',
				name : 'deptName',
				"width" : wd
			});

			var txt_loanApplyId= FormUtil.getTxtField({
						fieldLabel : '借款单编号',
						name : 'loanApplyId',
						"width" : 180,
						"allowBlank" : false
					});

			var bdat_payDate = FormUtil.getDateField({
						fieldLabel : '借款日期',
						name : 'payDate',
						"width" : wd,
						"allowBlank" : false
					});

			var mon_amount = FormUtil.getMoneyField({
						fieldLabel : '借款总金额',
						name : 'totalAmount',
						"width" : wd,
						listeners : {
							change : function(e, newValue, oldValue) {
								var newValue = Cmw.cmycurd(newValue);
								mon_amdou.setValue(newValue);
							}
						}
					});
			_self.globalMgr.thisamount = mon_amount;
			
			var mon_amdou = FormUtil.getReadTxtField({
						fieldLabel : '借款金额大写',
						name : 'damountd',
						"width" : wd
					});
			_self.globalMgr.thisamounts = mon_amdou;
			
			var txt_amount = FormUtil.getMoneyField({
						fieldLabel : '还款总金额',
						name : 'thisamount',
						width : wd,
						allowBlank : false,
						value : 0,
						listeners : {
							change : function(e, newValue, oldValue) {
								var newValue = Cmw.cmycurd(newValue);
								mon_adou.setValue(newValue);
							}
						}
					});
			_self.globalMgr.aomunt = txt_amount;
			var mon_adou = FormUtil.getReadTxtField({
						fieldLabel : '还款金额大写',
						name : 'thisamounts',
						"width" : wd
					});
			_self.globalMgr.amonuts = mon_adou;
			var bdat_applyDate = FormUtil.getDateField({
						fieldLabel : '还款日期',
						name : 'loanDate',
						"width" : wd,
						"allowBlank" : false
					});
			var rad_loanType = FormUtil.getRadioGroup({
						fieldLabel : '还款类型',
						name : 'loanType',
						"width" : wd,
						"allowBlank" : false,
						"items" : [{
									"boxLabel" : "现金",
									"name" : "loanType",
									"inputValue" : "1"
								}, {
									"boxLabel" : "转账",
									"name" : "loanType",
									"inputValue" : "2"
								}, {
									"boxLabel" : "支票",
									"name" : "loanType",
									"inputValue" : "3"
								}]
					});

			var txa_reason = FormUtil.getTAreaField({
						fieldLabel : '还款说明',
						name : 'reason',
						allowBlank : false ,
						maxLength :200 ,
						"width" : 800
					});

			var txa_remark = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
						"width" : 800
					});

			//借款单中的按钮
			var loanManButton = [{
						token :  Btn_Cfgs.INSERT_BTN_TXT,// "添加借款单"
						text :  Btn_Cfgs.INSERT_BTN_TXT,
						iconCls : 'page_add',
						tooltip : Btn_Cfgs.INSERT_TIP_BTN_TXT,
						key : Btn_Cfgs.INSERT_FASTKEY,
						handler : function() {
							//判断表格中的数据条数是否为0，大于0的话就可以计算借款总金额了
							_self.globalMgr.winEdit.show({key : this.token,_self : _self,_appgrid :_self.globalMgr._appgrid});

						}
					},/*{
						token : Btn_Cfgs.MODIFY_BTN_TXT,//
						text : Btn_Cfgs.MODIFY_BTN_TXT,
						iconCls : 'page_edit',
						tooltip : Btn_Cfgs.MODIFY_TIP_BTN_TXT,
						key : Btn_Cfgs.MODIFY_FASTKEY,
						handler : function() {
							_self.globalMgr.winEdit.show({key : this.token,_self : _self,_appgrid :_self.globalMgr._appgrid});
						}
						
					},*/{
						token : Btn_Cfgs.DELETE_BTN_TXT,//
						text : Btn_Cfgs.DELETE_BTN_TXT,
						iconCls : 'page_delete',
						tooltip : Btn_Cfgs.DELETE_TIP_BTN_TXT,
						key : Btn_Cfgs.DELETE_FASTKEY,
						handler : function() {
							var srow=_self.globalMgr._appgrid.getSelRow();
							var ds=_self.globalMgr._appgrid.getStore();
							ds.remove(srow);
						}
						
					}];
			//工具栏(有工具栏)
			var loanManTbar = new Ext.ux.toolbar.MyCmwToolbar({
						aligin : 'left',
						controls : loanManButton
				});
			//表格中的header，标题
			var loanManInfo = [{
						name : 'id',//这是借款单的id，还款单有借款单的外键
						hidden : true
					}, {
						header : '借款单编号',
						name : 'code',
						width : 150
					}, {
						header : '原借款余额',
						name : 'amount',
						width : 100
					},{
						header : '已还款金额',
						name : 'yamount',
						width : 100
					},{
						header : '未还款余额',
						name : 'noamount'
					},{
						header : '本次还款金额',
						name : 'repayment'
					},{
						header : '借款日期',
						name : 'loanDate',
						width : 100
					}]
			//借款单表格
			var _appgrid = new Ext.ux.grid.AppGrid({
//					url : './oaCitemsCfg_list.action',
					tbar : loanManTbar,
					structure : loanManInfo,
					height : 150,
					labelWidth : 400,
					isLoad : false,
					needPage : false
				});
			_self.globalMgr._appgrid=_appgrid;
			//借款单的fieldset
			var loanManfieldset =new Ext.form.FieldSet({
					checkboxToggle: false,
					title : '借款单选择',
					width : 920,
					items : [_appgrid]
			})

			var layout_fields = [{                                                                   
							cmns : FormUtil.CMN_TWO,                                                     
							fields : [txt_code, bdat_modifyTime, txt_loanMan, txt_deptName,txt_procId/*,txt_loanApplyId, bdat_payDate*/]                  
						},loanManfieldset,{
							cmns : FormUtil.CMN_TWO,         
							fields : [ mon_amount, mon_amdou,txt_amount, mon_adou, bdat_applyDate, rad_loanType]
						},txa_reason, txa_remark,txt_deptId];                                                      	
			
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			var htmls = "<font size='11' color='blue' valign='top'><center>还款单</center></font>";
			var filset = FormUtil.createLayoutFieldSet({
						title : "还款审批单"
					}, layout_fields);
			var frm_cfg = {
						autoHeight: true,
						url : './oaPayApply_save.action',
						labelWidth : 115,
						items : [new Ext.Container({
							html : '<div style = "height : 40px;"><div style = "left : 650px;color : blue ; position : fixed ;font-size : 30px;z-index : 99">还款单</div></div>'
						}), filset]
					};
			var applyForm = FormUtil.createFrm(frm_cfg);
			this.appForm = applyForm;
			_self.globalMgr.appform = applyForm;
			// _this.attachMentFs.reload(params);
			return applyForm
		},
		/**
		 * 刷新方法
		 */
		refresh : function() {
					var _this=this;
					var gid = _this.globalMgr._appgrid;
					var store = gid.getStore();//获取整个表格
					var count = store.getCount();//表格的行数
					if(count > 0 ){
							//计算金额。定义变量在外面，进行循环叠加
							var amount = 0;
							var thisamount = 0;
						for(var i = 0 ;i<count ; i++){
							var record = store.getAt(i);//每一行
							//本次还款金额
							amount +=Number(record.get('repayment'));//每一行中的amount字段的值
							_this.globalMgr.aomunt.setValue(amount);
							_this.globalMgr.amonuts.setValue(Cmw.cmycurd(amount));
							//原金额
							 thisamount += Number(record.get('amount'));
							_this.globalMgr.thisamount.setValue(thisamount);
							_this.globalMgr.thisamounts.setValue(Cmw.cmycurd(thisamount));
						}
					}else{
					EventManager.add("./oaPayApply_add.action", _this.appForm,{sfn : function(formdate) {
						_this.appForm.setFieldValues(formdate);
				}
			});}
		},
		/**
		 * 创建附件FieldSet 对象
		 * 
		 * @return {}
		 */
		createAttachMentFs : function(_this,params) {
			/* ----- 参数说明： isLoad 是否实例化后马上加载数据 
			 * dir : 附件上传后存放的目录， mortgage_path 定义在 resource.properties 资源文件中
			 * isSave : 附件上传后，是否保存到 ts_attachment 表中。 true : 保存
			 * params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔}
			 *    当 isLoad = false 时， params 可在 reload 方法中提供
			 *  ---- */
			var cfg = {title:'相关材料附件',isLoad:true,isSave:true,isNotDisenbaled:true};
			if(!params){
				var uuid = Cmw.getUuid();
				params = {formType:ATTACHMENT_FORMTYPE.FType_15,sysId:-1,formId : uuid,isNotDisenbaled:true};
			}
			cfg.params = params;
			var attachMentFs = new  Ext.ux.AppAttachmentFs(cfg);
			return attachMentFs;
		},
		/**
		 * 保存方法
		 */
		saveData:function(callback){
			var _this=this;
			var cfg={
				/**
				 * 验证表单数据合法性
				 */
				valid:function(baseForm){
					var store =  _this.globalMgr._appgrid.getStore(); 
					var count = store.getCount();
					if(count<=0){
						Ext.Msg.alert("提示","请选择借款单");
						return false;
					}
				},beforeMake : function(beforeData){
					var store =  _this.globalMgr._appgrid.getStore(); 
					var count = store.getCount();
					var datas = [];
					if(count>0){
						for(var i = 0 ;i<count;i++){
							var record = store.getAt(i);
							//借款单id
							var id = record.get("id");
							//已还款金额
							var yamount = record.get('yamount');
							//本次还款金额
							var repayment =record.get("repayment");
//									//获取借款单的总金额
//									var amount = record.get('amount');
							var params = {id : id, yamount :yamount , repayment :repayment/* , amount : amount*/};
							datas.push(params);
							}
							beforeData.store = Ext.encode(datas);
						}
					},sfn : function(formData) {
						var applyId = formData.id;
						var entityName = "PayApplyEntity";
						callback(applyId,entityName);
						var params = {formType:ATTACHMENT_FORMTYPE.FType_15,sysId:-1,formId : formData.id,isNotDisenbaled:true};
						_this.attachMentFs.updateTempFormId(params,true);
							}
				}
			EventManager.frm_save(_this.appForm,cfg);		
		},
		//点击添加，弹出借款单表格窗口
		globalMgr : {
				_appgrid : null,
				appform : null,
				activeKey : null,	
				//已还款金额
				aomunt : null,
				//已还款金额大写
				amonuts : null,
				//原金额
				thisamount : null,
				//原金额大写
				thisamounts : null,
				tokenMgr : {
					INSERT_BTN_TXT : '添加',
					MODIFY_BTN_TXT : '编辑',
					DELETE_BTN_TXT : '删除'
				},
				winEdit : {
					show : function(parentCfg) {
						var _this = parentCfg._self;
						//获取  定义的数据
						var tokenMgr = _this.globalMgr.tokenMgr;
						//获取用户点击的按钮
						var winkey = parentCfg.key;
						_this.globalMgr.activeKey = winkey;
						var parent = _this;
						parentCfg.parent = parent;
						//判断缓存中是否有数据
						if(_this.appCmpts[winkey]){
							_this.appCmpts[winkey].show(parentCfg);
						}else {
							var winModule=null;
							//根据用户点击的按钮进行选择
							switch(winkey){
								case tokenMgr.INSERT_BTN_TXT :{
									    winModule = "PayApplyEdit";
										break;
									}
								case tokenMgr.MODIFY_BTN_TXT :{
										break;
									}	
							}
						Cmw.importPackage('pages/app/workflow/bussProcc/oa/formUIs/' + winModule,function(module) {
							_this.appCmpts[winkey] = module.WinEdit;
							_this.appCmpts[winkey].show(parentCfg);
						});
					}
				}
			}	
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function() {
			if (this.appMainPanel) {
				this.appMainPanel.destroy();
				this.appMainPanel = null;
			}
		}
	}
});