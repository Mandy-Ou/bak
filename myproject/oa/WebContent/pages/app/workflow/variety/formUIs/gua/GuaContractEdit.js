/**
 * #DESCRIPTION#
 * @author smartplatform_auto
 * @date 2013-01-06 07:00:32
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		params : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		getSelIds : null,
		appgrid :null,
		sysId : null,
		gvlistid : null,
		guarantorIds : null,
		custType : null,
		customerId : null,
		guarantorIds : null,
		setParams:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.custType =  parentCfg.parent.custType;
			this.customerId =  parentCfg.parent.customerId;
			this.gvlistid = parentCfg.gvlistid;
			this.sysId = parentCfg.sysId;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			var _this  = this;
			this.appFrm = this.createForm();
			this.appgrid = this.getAppGrid();
			this.appFrm.add(this.appgrid);
			this.appWin = new Ext.ux.window.AbsEditWindow({ title: '保证合同信息编辑'+'<span style="color:red">>>>填写的保证合同编号必须唯一</span>',width:855,appFrm : this.appFrm,getUrls:this.getUrls,
			optionType : this.optionType, refresh : this.refresh,hidden : true,eventMgr:{saveData:this.saveData}
			});
			
		},
		/**
		 * 显示弹出窗口
		 * @param _parentCfg 弹出之前传入的参数
		 */
		show : function(_parentCfg){
			var _this = this;
			if(_parentCfg) this.setParams(_parentCfg);
			this.createAppWindow();
			this.appgrid.removeAll(true);
			this.appWin.optionType = this.optionType;
			this.appWin.show();
		},
		/**
		 * 销毁组件
		 */
		destroy : function(){
			if(this.appFrm != null){
				this.appFrm.destroy();
				this.appFrm = null;
			}
			if(this.appgrid!=null){
				this.appgrid.destroy();
				this.appgrid = null;
			}
			if(this.appWin != null){
				this.appWin.close();	//关闭并销毁窗口
				this.appWin = null;		//释放当前窗口对象引用
			}
			
			
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
			var _this = exports.WinEdit;
			var parent = exports.WinEdit.parent;
			var appWin = exports.WinEdit.appWin;
			var detailPanel = parent.detailPanel_1; 
			var grid = _this.appgrid;
			var formId = parent.formId;
			var cfg = null;
			var json_data =null;
			var appGrid = _this.appgrid;
			// 1 : 新增 , 2:修改
//			var _this = this;
			if(_this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{formId:formId},defaultVal:{formId:formId},sfn:function(jsonData){
					_this.guarantorIds = null;
					if(json_data==-1){
						ExtUtil.alert({msg:"请添加借款合同！"});
						return;
					}
					_this.appWin.hide();
				}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fcGuaContract_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
			cfg = {params : {formId:formId},defaultVal:{formId:formId},sfn:function(jsonData){
				var guarantorIds = jsonData.guarantorIds;
				appGrid.reload({id : guarantorIds});
			}};
				urls[URLCFG_KEYS.GETURLCFG] = {url :'./fcGuaContract_get.action',cfg : cfg};
			}
			var id = _this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './fcGuaContract_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fcGuaContract_next.action',cfg :cfg};
			var _this = this;
			_this.apptbar.hideButtons(Btn_Cfgs.PREVIOUS_BTN_TXT+","+Btn_Cfgs.NEXT_BTN_TXT);
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
		 * getToolBar 
		 */
		getToolBar : function(){
			var _this = this;
			var barItems = [{
				text : "添加担保人",
				iconCls:'page_add',
				tooltip:"添加担保人",
				handler : function(){
					var parentCfg = {
						parent:{appWin : _this.appFrm},
						params : {
							detailPanel_1 : parent.detailPanel_1
						},
						callback : function(data){
							var guarantorIds =data.guarantorIds;
							var record =data.record;
							var fields = _this.appFrm.findFieldsByName("guarantorIds");
							fields[0].reset();
							fields[0].setValue(guarantorIds);
							_this.appgrid.reload({id:guarantorIds});
						}
					};
				if(_this.GuaContractDialog){
					_this.GuaContractDialog.show(parentCfg);
				}else{
						Cmw.importPackage('/pages/app/dialogbox/GuaContractDialog',function(module) {
						 	_this.GuaContractDialog = module.DialogBox;
						 	_this.GuaContractDialog.show(parentCfg);
				  		});
					}
				}
			},{type:"sp"},{
				text : "删除担保人",
				iconCls:'page_delete',
				tooltip:"删除担保人",
				handler : function(){
					var ds = _this.appgrid.getStore(); 
					var record = _this.appgrid.getSelRows(); 
					for(var i=0; i<record.length; i++){
					        ds.remove(record[i]); //执行删除
					}
					var store = _this.appgrid.getStore(); 
					var id=[];
					for(var i=0;i<store.getCount();i++){
					    var record = store.getAt(i);
					    id[i]= record.get('id');
					}
					var fields = _this.appFrm.findFieldsByName("guarantorIds");
						fields[0].reset();
						fields[0].setValue(id.join(","));
					}
			}];
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
			return toolBar;
		},
		/**
		 *创建表格
		 */
		getAppGrid :function(){
			var _this = this;
			var structure_1 = [{
				    header: 'id',
				    hidden : true,
				    name: 'id'
				},{
				    header: '客户名称',
				    name: 'name'
				},
				{
				    header: '性别',
				    name: 'sex',
				    renderer :function(val){
				    	return ((val ? '':val)==1)? "女":"男";
				    }
				},
				{
				    header: '证件类型',
				    name: 'cardType',
				    renderer : function(val){
			    	return Render_dataSource.gvlistRender('100002',val);
			    }
				},
				{
				    header: '证件号码',
				    name: 'cardNum'
				},
				{
				    header: '出生日期',
				    name: 'birthday'
				},
				{
				    header: '婚姻状况',
				    name: 'maristal',
				    renderer : function(val){
				    	return Render_dataSource.gvlistRender('100003',val);
				    }
				},
				{
				    header: '手机',
				    name: 'phone'
				},
				{
				    header: '联系电话',
				    name: 'contactTel'
				},
				{
				    header: '联系人',
				    name: 'contactor'
				}
				];
				var appgrid = new Ext.ux.grid.AppGrid({
					tbar : _this.getToolBar(),
				    structure: structure_1,
				    url: './crmCustomerInfo_list.action',
				    needPage: false,
				    selectType : "check",
				    isLoad: false,
				    height : 200,
				    keyField: 'id'
				});
				_this.appgrid = appgrid;
				return appgrid;
		},
		
		/**
		 * 创建Form表单
		 */
		createForm : function(){
				var _this = this;
				var hid_Id = FormUtil.getHidField({
				    fieldLabel: 'ID',
				    name: 'id',
				    "width": 135
				});
				
				var hid_formId = FormUtil.getHidField({
				    fieldLabel: '贷款申请单ID',
				    name: 'formId',
				    "width": 135,
				    "allowBlank": false
				});
				
				var hid_loanConId = FormUtil.getHidField({
				    fieldLabel: '借款合同ID',
				    name: 'loanConId',
				    "width": 135,
				    "allowBlank": false
				});
				
				var txt_borrCode = FormUtil.getTxtField({
				    fieldLabel: '借款合同号',
				    name: 'borrCode',
				    "width": 135,
				    "allowBlank": false,
				    "maxLength": "30"
				});
				
				var txt_assMan = FormUtil.getReadTxtField({
				    fieldLabel: '被担保人',
				    name: 'assMan',
				    "width": 135,
				    "allowBlank": false,
				    "maxLength": 50
				});
				
				var dob_rate = FormUtil.getDoubleField({
					fieldLabel : '贷款利率',
					name : 'rate',
					"width" : 135,
					readOnly:true,
					"allowBlank" : false,
					"value" : 0,
					style:"dob_rate-align:right" ,
					unitText : '%',
					"decimalPrecision" : 2
				});
				var txt_appAmount = FormUtil.getMoneyField({
					fieldLabel : '贷款金额',
					name : 'appAmount',
					width : 235,
					allowBlank : false,
					value : 0,
					readOnly:true,
					autoBigAmount : true,
					unitText : '此处将显示大写金额',
					unitStyle : 'margin-left:4px;color:red;font-weight:bold'
				});
			    
				var bdat_startDate = FormUtil.getDateField({
				    fieldLabel: '贷款起始日期',
				    name: 'startDate',
				    readOnly:true,
				    "width": 135,
				    "allowBlank": false
				});
				
				var bdat_endDate = FormUtil.getDateField({
				    fieldLabel: '贷款截止日期',
				    name: 'endDate',
				    "width": 135,
				    readOnly:true,
				    "allowBlank": false
				});
				
				var txt_code = FormUtil.getReadTxtField({
				    fieldLabel: '保证合同编号',
				    name: 'code',
				    "width": 135,
				    "maxLength": "20"
				});
				
				var bdat_sdate = FormUtil.getDateField({
				    fieldLabel: '合同签订日期',
				    name: 'sdate',
				    "width": 135,
				    "allowBlank": false,
				    listeners : {
				    	select : function(bdat,newValue){
				    		var startDate = bdat_startDate.getValue();
				    			startDate = Date.parse(startDate);
				    			newValue = Date.parse(newValue);
				    			if(newValue < startDate){
				    				bdat.reset(); 
				    				ExtUtil.alert({msg:"选择的合同签订日期不能小于贷款起始日期！"});
				    				return;
				    			}
				    		}
				    	}
				});
				
				var txt_guarantorIds = FormUtil.getHidField({
				    fieldLabel: '保证人列表',
				    name: 'guarantorIds',
				    "width": 135,
				    "maxLength": 150
				});
				
				var txt_clause = FormUtil.getTAreaField({
				    fieldLabel: '合同中未涉及条款',
				    maxLength  : 255,
				    name: 'clause',
			     	width: 540,
			     	height:50,
				    "maxLength": "255"
				});
				var formDiyContainer = new Ext.Container({layout:'fit'});
				var txt_remark = FormUtil.getTAreaField({
				    fieldLabel: '备注',
				    name: 'remark',
			     	width: 540,
			     	height:50,
				    "maxLength": "200"
				});
				var layout_fields = [hid_Id,hid_formId, hid_loanConId,
					{cmns: FormUtil.CMN_TWO,fields: [ txt_borrCode ,txt_code,txt_appAmount, dob_rate,txt_assMan , bdat_startDate, bdat_endDate,  bdat_sdate]},
					txt_clause,formDiyContainer,txt_remark,txt_guarantorIds ];
				var frm_cfg = {
			     	labelWidth: 90,
				    height : 430,
				    url: './fcGuaContract_save.action',
				    params :{custType : _this.custType,customerId : _this.customerId},	
				    formDiyCfg : {
				    	sysId : _this.sysId,/*系统ID*/
				    	formdiyCode : _this.gvlistid,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
				    	formIdName : 'id',/*对应表单中的ID Hidden 值*/
				    	container : formDiyContainer /*自定义字段存放容器*/
			    	}
				};
				var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
				txt_appAmount.setWidth(200);
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
		saveData : function(){
			var _this = exports.WinEdit;
			var frmVal = _this.appFrm.getValues();
			var guarantorIds = frmVal.guarantorIds;
			if(!guarantorIds || guarantorIds=="" || guarantorIds==null){
				ExtUtil.alert({msg:"请添加保证人！"});
				return ;
			}
			EventManager.frm_save(_this.appFrm,{beforeMake : function(formData){
					formData.custType = _this.custType;
					formData.customerId = _this.customerId;
				},sfn:function(data){
					if(data == -1){
						ExtUtil.alert({msg:"保证合同编号必须唯一！"});
						return;
					}
				_this.refresh(data);
				_this.destroy();
			 }});
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		}
	};
});
