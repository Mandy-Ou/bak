/**
 * 提成方案
 * @author smartplatform_auto
 * @date 2013-11-25 12:38:20
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
				cfg = {params:{},defaultVal:{storeId:_this.parentCfg.storeId}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './oaDeductPlan_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './oaDeductPlan_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './oaDeductPlan_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './oaDeductPlan_next.action',cfg :cfg};
			return urls;
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var _this = exports.WinEdit;
			_this.parent.reload({storeId:_this.parentCfg.storeId});
			if(_this.parentCfg.self.refresh)_this.parentCfg.self.refresh(_this.optionType,data);	
		},
		/**
		 * 创建Form表单
		 */
		createForm : function(){
		
			/*职位*/
			var barPost = [{type:'label',text:'名称'},{type:'txt',name:'name'}];
			var strPost = [
				{header: '岗位名称', name: 'name',width:100},{header: '主要职责',
				name: 'mtask',width:60}];
			var txt_postId = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '<span style="color:red">*</span>'+'职位名称',
			    name: 'postId',
			    barItems : barPost,
			    structure:strPost,
			    dispCmn:'name',
			    isAll:true,
			    url : './sysPost_list.action',
			    needPage : true,
			    isLoad : true,
			    allowBlank: false,
			    keyField : 'id',
			    width: 370
			});
			
			/*提成方案*/
			var barDeduct = [{type:'label',text:'显示公式'},{type:'txt',name:'dispExpress'}];
			var strDeduct = [
				{header: '显示公式', name: 'dispExpress',width:60},{header: '实际公式表达式',
				name: 'express',width:200}];
			var txt_deductPlan = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '<span style="color:red">*</span>'+'提成公式',
			    name: 'formulaId',
			    barItems : barDeduct,
			    structure:strDeduct,
			    dispCmn:'dispExpress',
			    isAll:true,
			    url : './sysFormula_list.action',
			    needPage : true,
			    isLoad : true,
			    keyField : 'id',
			    width: 370
			});
			
			var cbo_category = FormUtil.getLCboField({
			    fieldLabel: '业务分类',
			    'allowBlank':false,
			    name: 'category',
			    "width": 150,
			    "maxLength": 10,
			    "data": [["1", "按门店提成"], ["2", "按产品类型提成"], ["3", "按门店（销售部门）提成"]]
			});
			    
			var cbo_productType = FormUtil.getLCboField({
			    fieldLabel: '产品类型',
			    'allowBlank':false,
			    name: 'productType',
			    "width": 125,
			    "maxLength": 10,
			    "data": [["1", "常规产品"], ["2", "保两年产品"], ["3", "所有产品"]]
			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 370,
			    "maxLength": 10
			});
		
			var hid_id=FormUtil.getHidField({
				fieldLabel:"ID",
				name:"id"
			});
			
			var hid_storeId=FormUtil.getHidField({
				fieldLabel:"门店ID",
				name:"storeId"
			});
		
		var layout_fields = [{
		    cmns: FormUtil.CMN_TWO,
		    fields: [cbo_category,cbo_productType ]
		},txt_deductPlan,
		txt_postId, txa_remark,hid_storeId,hid_id];
		var frm_cfg = {
		    title: '提成方案信息编辑',
		    url: './oaDeductPlan_save.action'
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
