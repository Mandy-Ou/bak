/**
 * 还款方式公式编辑
 * @author smartplatform_auto
 * @date 2014-02-26 20:52:22
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
			this.appWin = new Ext.ux.window.AbsEditWindow({width:720,getUrls:this.getUrls,appFrm : this.appFrm,
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
			var winTitle = _parentCfg.winTitle;
			this.appWin.setTitle(winTitle+"公式编辑");
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
			var _this = exports.WinEdit;
			var urls = {};
			var parent = exports.WinEdit.parent;
			var paytypeId = _this.parentCfg.paytypeId;
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{},defaultVal:{paytypeId:paytypeId}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fcPtformula_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './fcPtformula_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './fcPtformula_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fcPtformula_next.action',cfg :cfg};
			return urls;
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var _this = exports.WinEdit;
			var paytypeId = _this.parentCfg.paytypeId;
			_this.parentCfg.parent.reload({paytypeId:paytypeId});
		},
		/**
		 * 创建Form表单
		 */
		createForm : function(){
			var hid_isenabled = FormUtil.getHidField({name:'isenabled'});
			var hid_id = FormUtil.getHidField({ fieldLabel: '公式ID', name: 'id'});
			var hid_paytypeId = FormUtil.getHidField({ fieldLabel: '还款方式ID', name: 'paytypeId'});
				
			var rad_ftype = FormUtil.getRadioGroup({
			    fieldLabel: '公式类型',
			    name: 'ftype',
			    "width": 550,
			    "allowBlank": false,
			    "maxLength": 10,
			    "items": [{
			        "boxLabel": "通用公式",
			        "name": "ftype",
			        "inputValue": 1
			    },
			    {
			        "boxLabel": "第一期公式",
			        "name": "ftype",
			        "inputValue": 2
			    },
			    {
			        "boxLabel": "中间期公式",
			        "name": "ftype",
			        "inputValue": 3
			    },
			    {
			        "boxLabel": "最后一期公式",
			        "name": "ftype",
			        "inputValue": 4
			    }]
			});
			
			var txt_rateDispFormula = FormUtil.getTxtField({
			    fieldLabel: '公式说明',
			    name: 'rateDispFormula',
			    "width": 500,
			    "maxLength": "255",
			    allowBlank: false
			});
			
			var txt_rateFormula = FormUtil.getTxtField({
			    fieldLabel: '计算公式',
			    name: 'rateFormula',
			    "width": 500,
			    "maxLength": "255",
			    allowBlank: false
			});
			
			var txt_rateParams = FormUtil.getTxtField({
			    fieldLabel: '参数',
			    name: 'rateParams',
			    "width": 300,
			    "maxLength": "255",
			    allowBlank: false
			});

			var int_rateParams = FormUtil.getIntegerField({
			    fieldLabel: '优先级',
			    name: 'rnum',
			    "width": 60,
			    "maxLength": "2",
			    allowBlank: false,
			    value : 0
			});
			
			var fset_1 = FormUtil.getFieldSet({
			    title: '利息公式配置',
			    items:[txt_rateDispFormula, txt_rateFormula,
			    		{layout : 'column',items : [
			    			{columnWidth : .66,layout : 'form',items : txt_rateParams},
							{columnWidth : .33,layout : 'form',items : int_rateParams}]
						}
					 ]
			});
			
			var txt_mgrDispFormula = FormUtil.getTxtField({
			    fieldLabel: '公式说明',
			    name: 'mgrDispFormula',
			    "width": 500,
			    "maxLength": "255"
			});
			
			var txt_mgrFormula = FormUtil.getTxtField({
			    fieldLabel: '计算公式',
			    name: 'mgrFormula',
			    "width": 500,
			    "maxLength": "255",
			    allowBlank: false
			});
			
			var txt_mgrParams = FormUtil.getTxtField({
			    fieldLabel: '参数',
			    name: 'mgrParams',
			    width: 300,
			    maxLength: "255",
			    allowBlank: false
			});

			var int_mnum = FormUtil.getIntegerField({
			    fieldLabel: '优先级',
			    name: 'mnum',
			    "width": 60,
			    "maxLength": "2",
			    allowBlank: false,
			    value : 0
			});
			
			var fset_2 = FormUtil.getFieldSet({
			    title: '管理费公式配置',
			    items:[txt_mgrDispFormula, txt_mgrFormula,
			    		{layout : 'column',items : [
			    			{columnWidth : .66,layout : 'form',items : txt_mgrParams},
							{columnWidth : .33,layout : 'form',items : int_mnum}]
						}]
			});

			var txt_amoutDispFormula = FormUtil.getTxtField({
			    fieldLabel: '公式说明',
			    name: 'amoutDispFormula',
			    "width": 500,
			    "maxLength": "255"
			});
			
			var txt_amountFormula = FormUtil.getTxtField({
			    fieldLabel: '计算公式',
			    name: 'amountFormula',
			    "width": 500,
			    "maxLength": "255",
			    allowBlank: false
			});
			
			var txt_amountParams = FormUtil.getTxtField({
			    fieldLabel: '参数',
			    name: 'amountParams',
			   "width": 300,
			    "maxLength": "255",
			    allowBlank: false
			});
			
			var int_anum = FormUtil.getIntegerField({
			    fieldLabel: '优先级',
			    name: 'anum',
			    "width": 60,
			    "maxLength": "2",
			    allowBlank: false,
			    value : 0
			});

			var fset_3 = FormUtil.getFieldSet({
			    title: '本金公式配置',
			    items: [txt_amoutDispFormula, txt_amountFormula	,
			    		{layout : 'column',items : [
			    			{columnWidth : .66,layout : 'form',items : txt_amountParams},
							{columnWidth : .33,layout : 'form',items : int_anum}]
						}]
			});
			
			var txt_raDispFormula = FormUtil.getTxtField({
			    fieldLabel: '公式说明',
			    name: 'raDispFormula',
			    "width": 500,
			    "maxLength": "255"
			});
			
			var txt_raFormula = FormUtil.getTxtField({
			    fieldLabel: '计算公式',
			    name: 'raFormula',
			    "width": 500,
			    "maxLength": "255",
			    allowBlank: false
			});
			
			var txt_raParams = FormUtil.getTxtField({
			    fieldLabel: '参数',
			    name: 'raParams',
			    width: 300,
			    maxLength: "255",
			    allowBlank: false
			});

			var int_tnum = FormUtil.getIntegerField({
			    fieldLabel: '优先级',
			    name: 'tnum',
			    "width": 60,
			    "maxLength": "2",
			    allowBlank: false,
			    value : 0
			});
			
			var fset_4 = FormUtil.getFieldSet({
			    title: '本息总额公式配置',
			    items: [txt_raDispFormula, txt_raFormula,
			    		{layout : 'column',items : [
			    			{columnWidth : .66,layout : 'form',items : txt_raParams},
							{columnWidth : .33,layout : 'form',items : int_tnum}]
						}]
			});
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 500,
			    "maxLength": 200,
			    height:40
			});
			
			var layout_fields = [hid_isenabled,hid_id,hid_paytypeId,rad_ftype,fset_1,fset_2,fset_3,fset_4, txa_remark];
			var frm_cfg = {
			    title: '还款方式公式编辑',
			    labelWidth : 100,
			    url: './fcPtformula_save.action'
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
