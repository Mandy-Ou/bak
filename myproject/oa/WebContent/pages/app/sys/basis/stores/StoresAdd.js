/**
 * 门店
 * @author smartplatform_auto
 * @date 2013-11-24 11:09:54
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			var _this=this;
				this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:780,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh,eventMgr:{saveData:function(win){_this.saveData(win);}}
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
			var parent = exports.WinEdit.parent;
			var _this=exports.WinEdit;
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{},defaultVal:{area:_this.parentCfg.selText}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './oaStores_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './oaStores_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './oaStores_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './oaStores_next.action',cfg :cfg};
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
				var txt_code = FormUtil.getTxtField({
			    fieldLabel: '门店编号',
			    name: 'code',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "20"
			});
			
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '店面名称',
			    name: 'name',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "50"
			});
//			
//			var txt_storeArea = FormUtil.getReadTxtField({
//			    fieldLabel: '店面区域',
//			    name: 'storeArea',
//			    "allowBlank": false,
//			    "width": "125"
//			});
			
			var txt_address = FormUtil.getTxtField({
			    fieldLabel: '店面地址',
			    name: 'address',
			    "width": 125,
			    "maxLength": "500"
			});
			
			var cbo_ptype = FormUtil.getLCboField({
			    fieldLabel: '门店属性',
			    name: 'ptype',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": 10,
			    "data": [["1", "商场门店"], ["2", "独立店"], ["", ""]]
			});
			var cbo_slevel = FormUtil.getRCboField({
			    fieldLabel: '门店等级',
			    name: 'slevel',
			    "width": 125,
			    "allowBlank": false,
			    register : REGISTER.GvlistDatas, restypeId : '100001'
			});
			var cbo_stype = FormUtil.getLCboField({
			    fieldLabel: '门店类型',
			    name: 'stype',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": 10,
			    "data": [["1", "自营店"], ["2", "直营店"], ["3", "加盟店"]]
			});
			
			var txt_dzId = ComboxControl.getEmpCboGrid({fieldLabel:'店长',name:'dzId',allowBlank:false,callback:function(id,selValues,records){
				var record = records[0];
				var ctel = record.get("ctel");
				txt_dzTel.setValue(ctel);
			}});
			    
			var txt_dzTel = FormUtil.getReadTxtField({
			    fieldLabel: '店长联系电话',
			    name: 'dzTel',
			    "width": 125,
			    "maxLength": "20"
			});
			
			var txt_directorId = ComboxControl.getEmpCboGrid({fieldLabel:'门店总监',name:'directorId',callback:function(id,selValues,records){}});
			
	    	var txt_lxrId = ComboxControl.getEmpCboGrid({fieldLabel:'联系人',name:'lxrId',callback:function(id,selValues,records){
				var record = records[0];
				var ctel = record.get("ctel");
				txt_lxrTel.setValue(ctel);
			}});
			
			var txt_lxrTel = FormUtil.getReadTxtField({
			    fieldLabel: '联系人电话',
			    name: 'lxrTel',
			    "width": 125,
			    "maxLength": "20"
			});
			
			var txt_mgrId = ComboxControl.getEmpCboGrid({fieldLabel:'区域经理',name:'mgrId',callback:function(id,selValues,records){}});
			
			var barOorg = [{type:'label',text:'名称'},{type:'txt',name:'name'}];
			var strOorg = [
				{header: '名称', name: 'name',width:100},
				{header: '客户类别',name: 'custType',width:60},
				{header: '价格等级', name: 'plevelId',width:80}];
			var txt_wlgs = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '所属部门',
			    name: 'indeptId',
			    barItems : barOorg,
			    structure:strOorg,
			    dispCmn:'name',
			    isAll:true,
			    url : './oaLogistics_list.action',
			    needPage : true,
			    isLoad : true,
			    keyField : 'id',
			    width: 125
			});
			
			var txt_areaId = FormUtil.getReadTxtField({
			    fieldLabel: '门店区域',
			    name: 'area',
			    "width": 125
			});
			
			var bdat_startDate = FormUtil.getDateField({
			    fieldLabel: '开店日期',
			    disabled :true,
			    name: 'startDate',
			    "width": 125
			});
			var bdat_endDate = FormUtil.getDateField({
			    fieldLabel: '撤店日期',
			    disabled :true,
			    name: 'endDate',
			    "width": 125
			});
			
			var barOorg = [{type:'label',text:'名称'},{type:'txt',name:'name'}];
			var strOorg = [
				{header: '名称', name: 'name',width:100},
				{header: '客户类别',name: 'custType',width:60},
				{header: '价格等级', name: 'plevelId',width:80}];
			var txt_oorgId = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '客户',
			    name: 'oorgId',
			    barItems : barOorg,
			    structure:strOorg,
			    dispCmn:'name',
			    isAll:true,
			    url : './oaBillUnit_list.action',
			    needPage : true,
			    isLoad : true,
			    keyField : 'id',
			    width: 125
			});
			
			   
			var txt_orderAmount = FormUtil.getMoneyField({
			    fieldLabel: '订货额度',
			    name: 'orderAmount',
			    unitText:"元",
			    "width": 120
			});
//			var txt_rorg = FormUtil.getTxtField({
//			    fieldLabel: '回款单位',
//			    name: 'rorgId',
//			    "width": 320
//			});
//			var Butn_rorg = new Ext.Button({text: '.....'});
//			Butn_rorg.on('click',function(){
//				new Ext.Window({
//					title:'选择回款单位',
//					renderTo:'storeAddWin',
//					items:[_this.sltHkdvGrid()],
//					height:250,
//					width:300
//				}).show();
//				
//			});
//			var txt_rorgId = FormUtil.getMyCompositeField({fieldLabel: '回款单位',sigins:null,itemNames:'dzId',
//			    name: 'Butn_rorg',width:350,items:[txt_rorg,{xtype:'displayfield',width:1},Butn_rorg]});
//			   
			var barOorg = [{type:'label',text:'名称'},{type:'txt',name:'name'}];
			var strOorg = [
				{header: '名称', name: 'name',width:100},
				{header: '客户类别',name: 'custType',width:60},
				{header: '价格等级', name: 'plevelId',width:80}];
			var txt_wlgs = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '物流公司',
			    name: 'wlgsId',
			    barItems : barOorg,
			    structure:strOorg,
			    dispCmn:'name',
			    isAll:true,
			    url : './oaLogistics_list.action',
			    needPage : true,
			    isLoad : true,
			    keyField : 'id',
			    width: 125
			});
			   
			var int_dhDays = FormUtil.getIntegerField({
			    fieldLabel: '到货天数',
			    name: 'dhDays',
			    "width": 120,
			    unitText:"天",
			    "maxLength": 10
			});
			var txt_standAmount = FormUtil.getMoneyField({
			    fieldLabel: '标准陈列额',
			    name: 'standAmount',
			    unitText:"元",
			    "width": 120
			});
			
			var barFormula = [{type:'label',text:'名称'},{type:'txt',name:'name'}];
			var strFormula = [
				{header: '显示公式', name: 'dispExpress',width:100},
				{header: '实际公式表达式',name: 'express',width:100}];
			var txt_mzFormulaId = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '免责金额公式',
			    name: 'mzFormulaId',
			    barItems : barFormula,
			    structure:strFormula,
			    dispCmn:'name',
			    isAll:true,
			    url : './sys_Formula_list.action',
			    needPage : true,
			    isLoad : true,
			    keyField : 'id',
			    width: 400
			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 620,
			    "maxLength": 50
			});
			
			var rdo_stockMaintain=FormUtil.getRadioGroup({
				fieldLabel:"库存维护",
				name:"stockMaintain",
			    "width": 120,
			    "maxLength": 50,
			    "items": [{
			        "boxLabel": "起用",
			        "name": "stockMaintain",
			        "inputValue": "1",
			        "listeners":{
			        	"check":function(){
			        		rdo_StockOwe.disable();	
			        	}
			        }
			    },
			    {
			        "boxLabel": "不起用",
			        "checked":true,
			        "name": "stockMaintain",
			        "inputValue": "2",
			        "listeners":{
			        	"check":function(){
			        		rdo_StockOwe.enable();
			        		rdo_StockOwe.setValue("2");
			        	}
			        }
			    }]
			});
			
			var rdo_StockOwe=FormUtil.getRadioGroup({
				fieldLabel:"库存允许负数",
				name:"stockOwe",
			    "width": 120,
			    "maxLength": 50,
			    "items": [{
			        "boxLabel": "允许",
			        "name": "stockOwe",
			        "inputValue": "1"
			    },
			    {
			        "boxLabel": "不允许",
			        "checked":true,
			        "name": "stockOwe",
			        "inputValue": "2"
			    }]
			});
			rdo_StockOwe.disable();	
			
			var hid_id=FormUtil.getHidField({
				fieldLabel:"ID",
				name:"id"
			});
			
			var hid_controlType=FormUtil.getHidField({
				fieldLabel:"订单限制方式",
				name:"controlType",
				value:"0"
			});
			
			var hid_indeptId=FormUtil.getHidField({
				fieldLabel:"所属部门",
				name:"indeptId",
				value:"-1"
			});
			
			var layout_fields = [{
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_code, txt_name,txt_areaId,txt_address]
			}, 
			{
			    cmns: FormUtil.CMN_THREE,
			    fields: [cbo_ptype, cbo_slevel, cbo_stype, txt_dzId, txt_dzTel, txt_directorId,
			    txt_lxrId, txt_lxrTel, txt_mgrId, bdat_startDate,bdat_endDate,txt_oorgId, txt_orderAmount,
			    txt_wlgs, txt_standAmount,int_dhDays,rdo_stockMaintain,rdo_StockOwe]
			},
			txt_mzFormulaId, txa_remark,hid_id,hid_controlType,hid_indeptId];
			var frm_cfg = {
				labelWidth:100,
			    title: '添加门店',
			    url: './oaStores_save.action'
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
		saveData : function(){
			var _this = exports.WinEdit;
			var cfg = {
				beforeMake : function(formData){
					formData.areaId=_this.parentCfg.selId;
				},
				sfn : function(formData){
					_this.appWin.hide();
					_this.parent.reload({areaId:_this.parentCfg.selId});
				}
			};
			EventManager.frm_save(_this.appFrm,cfg);
		
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		}
	};
});
