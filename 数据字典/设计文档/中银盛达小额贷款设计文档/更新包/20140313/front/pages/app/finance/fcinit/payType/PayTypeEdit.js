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
		appGird : null,/*公式Grid*/
		ftbar : null,/*公式工具栏*/
		ptformulaWin : null,/*公式窗口*/
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			var _this = this;
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:900,getUrls:this.getUrls,appFrm : this.appFrm,
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
			var eTdisabled = false;
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{},defaultVal:{}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fcPayType_add.action',cfg : cfg};
				eTdisabled = true;
				exports.WinEdit.appGird.removeAll();
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './fcPayType_get.action',cfg : cfg};
				exports.WinEdit.appGird.reload({paytypeId:selId});
			}
			exports.WinEdit.ftbar.setDisabled(eTdisabled);
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
			
			var txt_code = FormUtil.getTxtField({
			    fieldLabel: '编号',
			    name: 'code',
			    "width": "125",
			    allowBlank : false
			});
			
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '还款方式名称',
			    name: 'name',
			    "width": 150,
			    "maxLength": 60,
			    allowBlank : false
			});
			
			var txt_inter = FormUtil.getTxtField({
			    fieldLabel: '算法接口',
			    name: 'inter',
			    "width": 150,
			    "maxLength": 50,
			    allowBlank : false
			});
			
			var txt_pdfa = FormUtil.getTxtField({
			    fieldLabel: '公式说明',
			    name: 'pdfa',
			    "width": 680,
			    "maxLength": 250,
			    allowBlank : false
			});
			
			var txt_prfa = FormUtil.getTxtField({
			    fieldLabel: '计算公式',
			    name: 'prfa',
			    "width": 680,
			    "maxLength": 250,
			    allowBlank : false
			});
			
			var txt_rps = FormUtil.getTxtField({
			    fieldLabel: '公式参数',
			    name: 'rps',
			    "width": 680,
			    "maxLength": 250,
			    allowBlank : false
			});
			
			var fset_1 = FormUtil.getFieldSet({
			    title: '提前还款息费公式',
			    items:[txt_pdfa, txt_prfa, txt_rps]
			});
	
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 680,
			    height:80,
			    "maxLength": 200
			});
			this.appGird = this.createGrid();
			var layout_fields = [hid_isenabled,hid_id,{
			    cmns: FormUtil.CMN_THREE,
			    fields: [txt_code, txt_name, txt_inter]
			},fset_1,txa_remark,this.appGird];
			
			var frm_cfg = {
			    title: '还款方式公式编辑',
			    labelWidth : 100,
			    url: './fcPayType_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			
			return appform_1;
		},
		/**
		 * 还款方式Grid
		 */
		createGrid : function(){
			var structure_1 = [{
			    header: '公式类型',
			    name: 'ftype',
			    width: 100,
			    renderer : function(val){
			    	return Render_dataSource.ptformula_ftypeRender(val);
			    }
			},
			{
			    header: '利息公式说明',
			    name: 'rateDispFormula',
			    width: 200
			},
			{
			    header: '管理费公式说明',
			    name: 'mgrDispFormula',
			    width: 150
			},
			{
			    header: '本金公式说明',
			    name: 'amoutDispFormula',
			    width: 150
			},
			{
			    header: '本息总额公式说明',
			    name: 'raDispFormula',
			    width: 150
			},
			{
			    header: '备注',
			    name: 'remark',
			    width: 200
			}];
			this.ftbar = this.createFtbar();
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				tbar : this.ftbar,
			    structure: structure_1,
			    url: './fcPtformula_list.action',
			    needPage: false,
			    isLoad: false,
			    keyField: 'id',
			    height:150
			});
			return appgrid_1;
		},
		/**
		 * 创建还款方式Grid上的工具栏
		 */
		createFtbar : function(){
			var _this = this;
			var barItems = [{
				token : '添加公式',
				text : Btn_Cfgs.FORMULA_ADD_BTN_TXT,
				iconCls:Btn_Cfgs.FORMULA_ADD_BTN_CLS,
				handler : function(){
					_this.openWin(OPTION_TYPE.ADD);
				}},{
				token : '编辑公式',
				text : Btn_Cfgs.FORMULA_EDIT_BTN_TXT,
				iconCls:Btn_Cfgs.FORMULA_EDIT_BTN_CLS,
				handler : function(){
					_this.openWin(OPTION_TYPE.EDIT);
				}},{
				token : '删除公式',
				text : Btn_Cfgs.FORMULA_DELETE_BTN_TXT,
				iconCls:Btn_Cfgs.FORMULA_DELETE_BTN_CLS,
				handler : function(){
					var objVal = _this.appFrm.getValuesByNames('id');
					var pars = objVal.id;
					EventManager.deleteData('./fcPtformula_delete.action',{type:'grid',cmpt:_this.appGird,optionType:OPTION_TYPE.DEL,params:pars});
				}}
				];
			var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems});
			return toolBar;
		},
		/**
		 * 保存数据
		 */
		saveData : function(win){
			var _this = this;
			var cfg = {
				tb:win.apptbar,
				sfn : function(formData){
					if(win.refresh) win.refresh(formData);
					var id = formData.id;
					_this.appFrm.setFieldValue('id',id);
					_this.ftbar.enable();
				}
			};
			EventManager.frm_save(win.appFrm,cfg);
		},
		/**
		 * 打开公式配置窗口
		 */
		openWin : function(optionType){
			var _this =  this;
			var parentCfg = {optionType:optionType};
			var winkey=parentCfg.key;
			var parent = _this.appGird;
			parentCfg.parent = parent;
			var objVal = this.appFrm.getValuesByNames('id,code,name');
			var paytypeId = objVal.id;
			parentCfg.paytypeId = paytypeId;
			var winTitle = "["+objVal.code+"] &nbsp;&nbsp;"+objVal.name;
			parentCfg.winTitle = winTitle;
			if(optionType == OPTION_TYPE.EDIT){
				var id = parent.getSelId();
				if(!id) return;
			}
			if(_this.ptformulaWin){
				_this.ptformulaWin.show(parentCfg);
			}else{
				Cmw.importPackage('pages/app/finance/fcinit/payType/PtformulaEdit',function(module) {
				 	_this.ptformulaWin = module.WinEdit;
				 	_this.ptformulaWin.show(parentCfg);
	  			});
			}
		}
	};
});
