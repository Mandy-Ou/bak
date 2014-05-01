/**
 * #DESCRIPTION#
 * @author smartplatform_auto
 * @date 2013-01-14 01:45:28
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appGrid : null,
		appgrid :null,
		appWin : null,
		selectBtn : null,
		pleIds : null,
		gridId : null,
		custType : null,
		customerId : null,
		MortgageDialog :null,
		formId :null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.custType =  parentCfg.parent.custType;
			this.customerId =  parentCfg.parent.customerId;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
			this.formId = parentCfg.parent.formId;
		},
		
		createAppWindow : function(){
			this.appFrm = this.createForm();
			this.appGrid  = this.getAppGrid();
			this.appFrm.add(this.appGrid);
			
			this.appWin = new Ext.ux.window.AbsEditWindow({autoScroll : true,title:'抵押合同信息编辑',width:660,appFrm :this.appFrm,getUrls:this.getUrls,
			optionType : this.optionType, refresh : this.refresh,hidden :true,eventMgr:{saveData:this.saveData}
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
			var _this = this;
			this.appgrid.removeAll(true);
			this.appWin.optionType = this.optionType;
			this.appWin.show();
		},
		/**
		 * 销毁组件
		 */
		destroy : function(){
			if(!this.appWin) return;
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
			var _this = exports.WinEdit;
			var parent = exports.WinEdit.parent;
			var appWin = exports.WinEdit.appWin;
			var formId = parent.formId;
			
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{formId:formId},defaultVal:{formId:formId,custType:_this.custType,customerId:_this.customerId}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fcMortContract_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.appgrid.getSelId();
				cfg = {params : {id:selId},sfn:function(json_data){
					_this.pleIds = json_data.pleIds;
					_this.appGrid.reload({id : _this.pleIds,formId :_this.formId});
				}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './fcMortContract_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			var id = this.appFrm.getValueByName("formId");
			cfg = {params : {id:id,sfn:function(){}}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './fcMortContract_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fcMortContract_next.action',cfg :cfg};
			this.apptbar.hideButtons(Btn_Cfgs.PREVIOUS_BTN_TXT+","+Btn_Cfgs.NEXT_BTN_TXT);
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
			var _this = this;
			
			var hid_custType = FormUtil.getHidField({
			    fieldLabel: '客户类型',
			    name: 'custType',
			    "width": 135
			});
			var hid_customerId = FormUtil.getHidField({
			    fieldLabel: '客户Id',
			    name: 'customerId',
			    "width": 135
			});
			
			var hid_id = FormUtil.getHidField({
			    fieldLabel: 'id',
			    name: 'id',
			    "width":135
			});
			var hid_formId = FormUtil.getHidField({
			    fieldLabel: '贷款申请单ID',
			    name: 'formId',
			    "width":135
			});
			
			var hid_loanConId = FormUtil.getHidField({
			    fieldLabel: '借款合同ID',
			    name: 'loanConId',
			    "width":135
			});
			
			var hid_pleIds = FormUtil.getHidField({
			    fieldLabel: '质押物ID列表',
			    name: 'pleIds',
			    "width":135
			});
			
			var txt_code = FormUtil.getTxtField({
			    fieldLabel: '抵押合同编号',
			    name: 'code',
			    "width":135
			});
			
			var txt_ownerNums = FormUtil.getTxtField({
			    fieldLabel: '权属编号',
			    name: 'ownerNums',
			    "width":135
			});
			
			var txt_borrCode = FormUtil.getReadTxtField({
			    fieldLabel: '借款合同号',
			    name: 'borrCode',
			    allowBlank : false,
			    "width":135
			});
			
			var txt_assMan = FormUtil.getTxtField({
			    fieldLabel: '借款人',
			    name: 'assMan',
			    allowBlank : false,
			    "width":135
			});
			
			var txt_appAmount = FormUtil.getMoneyField({
				fieldLabel : '贷款金额',
				name : 'appAmount',
				width : 245,
				allowBlank : false,
				value : 0,
				autoBigAmount : true,
				unitText : '此处将显示大写金额',
				unitStyle : 'margin-left:4px;color:red;font-weight:bold'
			});
			
			var doub_conAmount = FormUtil.getMoneyField({
			    fieldLabel: '确认价值金额',
			    name: 'conAmount',
			    allowBlank : false,
			    "width": 135
			});
			
			var dob_rate = FormUtil.getIntegerField({
			    fieldLabel: '贷款利率',
			    name: 'rate',
			    "width":135,
			    allowBlank : false,
			    "decimalPrecision" : "2",
				unitText : '%'
			});
			
			var bdat_startDate = FormUtil.getDateField({
			    fieldLabel: '贷款起始日期',
			    name: 'startDate',
			    allowBlank : false,
			    readOnly:true,
			    "width":135
			});
			
			var bdat_endDate = FormUtil.getDateField({
			    fieldLabel: '贷款截止日期',
			    name: 'endDate',
			    allowBlank : false,
			    readOnly:true,
			    "width":135
			});
			
			var bdat_sdate = FormUtil.getDateField({
			    fieldLabel: '合同签订日期',
			    allowBlank : false,
			    name: 'sdate',
			    "width":135,
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
			
			var text_pleman = FormUtil.getTxtField({
			    fieldLabel: '抵押人',
			    name: 'pleman',
			    allowBlank : false,
			    "width": 135
			});
			
			var txt_clause = FormUtil.getTAreaField({
				maxLength  : 255,
			    fieldLabel: '合同中未涉及条款',
			    name: 'clause',
			    "width":450
			});
			
			var layout_fields = [hid_custType,hid_customerId,hid_id,hid_formId,hid_loanConId,hid_pleIds,
				{cmns: FormUtil.CMN_TWO,fields: [txt_code,txt_borrCode]},txt_appAmount,
				{cmns: FormUtil.CMN_TWO,fields: [dob_rate,doub_conAmount, text_pleman, 
				    	 txt_assMan, bdat_startDate, 
				    	bdat_endDate, bdat_sdate]
			},txt_clause];
			var frm_cfg = {
				title : '<span style="color:red">（提示：如果合同编号不输入，系统会自动生成合同编号；否则，就以用户输入的合同编号为准）</span>',
			    labelWidth: 110,
			    Height : 900,
			    url: './fcMortContract_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			
			return appform_1;
		},
		getToolBar : function(){
			var _this = this;
			var barItems = [{
				text : "选择抵押物",
				iconCls:'page_add',
				tooltip:"选择抵押物",
				handler : function(){
						var parentCfg = {
						params : {
							formId : _this.formId
						},
						callback : function(data){
							var ids =data.id;
							var conAmount =data.conAmount;
							var fields = _this.appFrm.findFieldsByName("pleIds,conAmount");
							fields[0].reset();
							fields[1].reset();
							fields[0].setValue(ids);
							fields[1].setValue(conAmount);
							_this.appGrid.reload({id:ids});
						}
					};
				if(_this.MortgageDialog){
					_this.MortgageDialog.show(parentCfg);
				}else{
						Cmw.importPackage('/pages/app/dialogbox/MortgageDialogbox',function(module) {
						 	_this.MortgageDialog = module.DialogBox;
						 	_this.MortgageDialog.show(parentCfg);
				  		});
					}
				}
			},{
			text : "删除抵押物",
			iconCls:'page_delete',
			tooltip:"删除抵押物",
			handler : function(){
				var ds = _this.appGrid.getStore(); 
				var record = _this.appGrid.getSelRows(); 
				for(var i=0; i<record.length; i++){
				        ds.remove(record[i]); //执行删除
				}
				var store = _this.appGrid.getStore(); 
				var id=[];
				for(var i=0;i<store.getCount();i++){
				    var record = store.getAt(i);
				    id[i]= record.get('id');
				}
				var fields = _this.appFrm.findFieldsByName("pleIds");
				fields[0].setValue(id.join(","));
				
		}}];
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
			return toolBar;
		},
		/**
		 * 抵押物列表
		 */
		 getAppGrid : function(){
		 	var structure = [{
			    header: 'id',
			    name: 'id',
			    hidden :true,
			    width: 125
			},{
			    header: '抵押物名称',
			    name: 'name',
			    width: 125
			},{
			    header: '单位',
			    name: 'unint',
			    hidden :true,
			    width: 125
			},{
			    header: '数量/单位',
			    name: 'quantity',
			    width: 80,
			    renderer: function(val,cellmeta,record,rowIndex,columnIndex,store){
			    	var unint = record.data['unint'];
			    	return val+unint;
			    }
			},{
			    header: '原价值(元)',
			    name: 'oldVal',
			    width: 125
			}];
			var toolBar = this.getToolBar();
			var _this = this;
			var appgrid = new Ext.ux.grid.AppGrid({
				tbar : toolBar,
				structure : structure,
				needPage: false,
				url : './fcMortgage_list.action',
				autoWidth: true,
				height:150,
				selectType : 'check' ,
				isLoad: false,
				keyField : 'id'
			});
			this.appgrid = appgrid;
		 	return appgrid;
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
			var pleIds = frmVal.pleIds;
				if(!pleIds || pleIds==""|| pleIds==null ){
					ExtUtil.alert({msg:"抵押物不能为空!"});
					return ;
				}
			EventManager.frm_save(_this.appFrm,{beforeMake : function(formData){
				},sfn:function(data){
				if(data == -1){
					ExtUtil.alert({msg:"抵押物合同编号不是唯一！"});
					return;
				}
				_this.appWin.hide();
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
