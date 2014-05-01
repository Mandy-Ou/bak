/**
 * 分录信息编辑窗口
 * @author smartplatform_auto
 * @date 2013-04-10 11:46:11
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		finsysId : null,
		voucherId : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
			this.finsysId = parentCfg.finsysId;
			this.voucherId = parentCfg.voucherId;
		},
		createAppWindow : function(){
			var _this = this;
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:575,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, eventMgr:{saveData:function(win){_this.saveData(win);}},refresh : this.refresh
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
			this.appWin.show();
		},
		/**
		 * 销毁组件
		 */
		destroy : function(){
			this.appWin.close();	//关闭并销毁窗口
			this.appWin = null;		//释放当前窗口对象引用
		},
		/**
		 * 获取各种URL配置
		 * addUrlCfg : 新增 URL
		 * editUrlCfg : 修改URL
		 * preUrlCfg : 上一条URL
		 * preUrlCfg : 下一条URL
		 */
		getUrls : function(){
			var urls = {};
			var parent = exports.WinEdit.parent;
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{},defaultVal:{voucherId:exports.WinEdit.voucherId}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fsEntryTemp_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId},sfn:function(json_data){
					var conditionId = json_data["conditionId"];
					var appFrm = exports.WinEdit.appFrm;
					var rad_isCondition = appFrm.findFieldByName('isCondition');
					var txt_conditionId = appFrm.findFieldByName('conditionId');
					var flag = true;
					var isCondition = 2;
					if(conditionId){
						flag = false;
						isCondition = 1;
					}
					rad_isCondition.setValue(isCondition);
					txt_conditionId.setDisabled(flag);
				}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './fsEntryTemp_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './fsEntryTemp_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fsEntryTemp_next.action',cfg :cfg};
			return urls;
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var _this = exports.WinEdit;
			if(_this.parentCfg.self.refresh)_this.parentCfg.self.refresh(_this.optionType,data);	
		},
		/**
		 * 创建Form表单
		 */
		createForm : function(){
		
			var txt_summary = FormUtil.getTxtField({
			    fieldLabel: '摘要说明',
			    name: 'summary',
			    "width": 425,
			    "maxLength": "150",
			    allowBlank : false
			});
			
			var rcbo_settleId = FormUtil.getRCboField({
			    fieldLabel: '结算方式',
			    name: 'settleId',
			    "width": 150,
			    "maxLength": "50",
			    "url": './fsSettle_cbodatas.action?finsysId='+this.finsysId,
			    allowBlank : false
			});
			
			var rad_fdc = FormUtil.getRadioGroup({
			    fieldLabel: '余额方向',
			    name: 'fdc',
			    "width": 150,
			    allowBlank : false,
			    "items": [{
			        "boxLabel": "贷方",
			        "name": "fdc",
			        "inputValue": 0
			    },
			    {
			        "boxLabel": "借方",
			        "name": "fdc",
			        "inputValue": 1
			    }]
			});
			
			var rcbo_currencyId = FormUtil.getRCboField({
			    fieldLabel: '币别',
			    name: 'currencyId',
			    "width": 150,
			    "maxLength": "50",
			    "url": './fsCurrency_cbodatas.action?finsysId='+this.finsysId,
			    allowBlank : false
			});
			
			 var cbog_accountId =  ComboxControl.getSubjectCboGrid({
			 	 fieldLabel: '科目',
			    name: 'accountId',
			    "width": 150,
			    "maxLength": "30",
			    allowBlank : false,
			    params:{finsysId:this.finsysId}
			 });
			 
			var cbog_accountId2 =  ComboxControl.getSubjectCboGrid({
			 	fieldLabel: '对方科目',
			    name: 'accountId2',
			    "width": 150,
			    "maxLength": "30",
			    params:{finsysId:this.finsysId}
			});
			    
			var txt_formulaId = new Ext.ux.form.MyFormulaField({
			    fieldLabel: '金额计算公式',
			    name: 'formulaId',
			    "width": 150,
			    allowBlank : false,
			    fieldSource : 'FinCustField',/*表示从 ts_FinCustField 表取字段*/
			    cboUrl : './fsFinBussObject_cbodatas.action?finsysId='+this.finsysId
			});
			
			var rad_isCondition = FormUtil.getRadioGroup({
			    fieldLabel: '是否启用条件',
			    name: 'isCondition',
			    "width": 150,
			    "allowBlank": false,
			    "maxLength": 50,
			    "items": [{
			        "boxLabel": "是",
			        "name": "isCondition",
			        "inputValue": 1
			    },
			    {
			        "boxLabel": "否",
			        "name": "isCondition",
			        "inputValue": 2,
			        checked:true
			    }],
			     listeners : {change:function(rdgp,checked){
			     	var isCond = rad_isCondition.getValue();
			     	var flag = true;
			     	if(isCond == 1){
			     		flag = false;
			     	}
			     	txt_conditionId.setDisabled(flag);
			     }}
			});
			
			var txt_conditionId = new Ext.ux.form.MyFormulaField({//FormUtil.getTxtField({
			    fieldLabel: '条件公式',
			    name: 'conditionId',
			    "width": 150,
			    allowBlank : false,
			    fieldSource : 'FinCustField',/*表示从 ts_FinCustField 表取字段*/
			    cboUrl : './fsFinBussObject_cbodatas.action?finsysId='+this.finsysId
			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 425,
			    "maxLength": 200
			});
			
			var txt_id = FormUtil.getHidField({
			    fieldLabel: 'ID',
			    name: 'id',
			    "width": "150"
			});
			
			var txt_isenabled = FormUtil.getHidField({
			    fieldLabel: '可用标识',
			    name: 'isenabled',
			    "width": "150"
			});
			
			var txt_isItemClass = FormUtil.getHidField({
			    fieldLabel: '是否挂核算项',
			    name: 'isItemClass',
			    "width": 150
			});
			
			var txt_voucherId = FormUtil.getHidField({
			    fieldLabel: '凭证模板ID',
			    name: 'voucherId',
			    "width": 150
			});
			
			var layout_fields = [
			txt_summary, {
			    cmns: FormUtil.CMN_TWO,
			    fields: [cbog_accountId,cbog_accountId2, rad_fdc, txt_formulaId,rcbo_settleId, rcbo_currencyId, rad_isCondition, txt_conditionId]
			},
			txa_remark, txt_id, txt_isenabled, txt_isItemClass, txt_voucherId];
			var frm_cfg = {
			    title: '分录信息编辑',
			    url: './fsEntryTemp_save.action',
			    labelWidth:100
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform_1;
		},
		/**
		 * 为表单元素赋值
		 */
		setFormValues : function(){
			
		},
		/**
		 * 上一条
		 */
		preData : function(){
			
		},
		/**
		 * 下一条
		 */
		nextData : function(){
			
		},
		/**
		 * 保存数据
		 */
		saveData : function(win){
			var _this = this;
			var cfg = {
				tb:win.apptbar,
				sfn : function(formData){
					_this.parent.reload({voucherId:_this.voucherId});
					win.hide();
				}
			};
			EventManager.frm_save(win.appFrm,cfg);
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		}
	};
});
