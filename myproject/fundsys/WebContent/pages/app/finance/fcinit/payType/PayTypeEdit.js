/**
 * 还款方式公式编辑
 * @author smartplatform_auto
 * @date 2013-01-23 07:16:22
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
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:520,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh
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
				cfg = {params:{},defaultVal:{}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fcPayType_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './fcPayType_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './fcPayType_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fcPayType_next.action',cfg :cfg};
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
		var hid_isenabled = FormUtil.getHidField({name:'isenabled'});
			var hid_id = FormUtil.getHidField({
			    fieldLabel: '还款方式ID',
			    name: 'id'
			});
			
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '还款方式名称',
			    name: 'name',
			     "width": 375,
			    "allowBlank": false,
			    "maxLength": "60"
			});
			
			var txt_code = FormUtil.getReadTxtField({
			    fieldLabel: '编号',
			    name: 'code',
			    "width": 135,
			    "maxLength": "50"
			});
			//
			var txt_inter = FormUtil.getTxtField({
			    fieldLabel: '算法接口',
			    name: 'inter',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "50"
			});
			
			var txt_rateDispFormula = FormUtil.getTxtField({
			    fieldLabel: '当期应还利息',
			    name: 'rateDispFormula',
			    "width": 375,
			    "maxLength": "255"
			});
			
			var txt_rateFormula = FormUtil.getTxtField({
			    fieldLabel: '利息计算公式',
			    name: 'rateFormula',
			    "width": 375,
			    "maxLength": "255"
			});
			
			var txt_rateParams = FormUtil.getTxtField({
			    fieldLabel: '利息参数',
			    name: 'rateParams',
			    "width": 375,
			    "maxLength": "150"
			});
			
			var txt_amoutDispFormula = FormUtil.getTxtField({
			    fieldLabel: '当期应还本金',
			    name: 'amoutDispFormula',
			    "width": 375,
			    "maxLength": "255"
			});
			
			var txt_amountFormula = FormUtil.getTxtField({
			    fieldLabel: '本金计算公式',
			    name: 'amountFormula',
			    "width": 375,
			    "maxLength": "255"
			});
			
			var txt_amountParams = FormUtil.getTxtField({
			    fieldLabel: '本金参数',
			    name: 'amountParams',
			    "width": 375,
			    "maxLength": "150"
			});
			
			var txt_raDispFormula = FormUtil.getTxtField({
			    fieldLabel: '当期还本付息',
			    name: 'raDispFormula',
			    "width": 375,
			    "maxLength": "255"
			});
			
			var txt_raFormula = FormUtil.getTxtField({
			    fieldLabel: '还本付息公式',
			    name: 'raFormula',
			    "width": 375,
			    "maxLength": "255"
			});
			
			var txt_raParams = FormUtil.getTxtField({
			    fieldLabel: '还本付息参数',
			    name: 'raParams',
			    "width": 375,
			    "maxLength": "150"
			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 375,
			    "maxLength": 200
			});
			
			var layout_fields = [hid_isenabled,hid_id,txt_name,{cmns: FormUtil.CMN_TWO,fields: [txt_code, txt_inter]},
			txt_rateDispFormula, txt_rateFormula, txt_rateParams, txt_amoutDispFormula,
			txt_amountFormula, txt_amountParams, txt_raDispFormula, txt_raFormula, txt_raParams, txa_remark];
			var frm_cfg = {
			    title: '还款方式公式编辑',
			    labelWidth : 100,
			    url: './fcPayType_save.action'
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
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		}
	};
});
