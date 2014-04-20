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
		gvlistid : null,
		gvlistname : null,
		formId : null,
		sysId : null,/*系统ID*/
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		
		setParams:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.gvlistid = parentCfg.gvlistid;
			this.formId = parentCfg.formId;
			this.sysId = parentCfg.sysId;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({autoScroll : true,closeAction:'close',width:600,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh,hidden : true,isNotSetVs : true
			});
		},
		/**
		 * 显示弹出窗口
		 * @param _parentCfg 弹出之前传入的参数
		 */
		show : function(_parentCfg){
			if(_parentCfg) this.setParams(_parentCfg);
//			if(!this.appWin){
				this.createAppWindow();
//			}
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
			var gvlistid = exports.WinEdit.gvlistid;
			var formId = exports.WinEdit.formId;
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{},defaultVal:{gtype:gvlistid,formId:formId},sfn:function(){
					if(json_data==-1){
						ExtUtil.alert({msg:"请添加借款合同！"});
						return;
					}
					_this.appWin.hide();
				}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fcMortgage_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url :'./fcMortgage_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './fcMortgage_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fcMortgage_next.action',cfg :cfg};
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
				var txt_id = FormUtil.getHidField({
			    fieldLabel: '物品ID',
			    name: 'id',
			    "width": 125,
			    "maxLength": "30"
			});
				var txt_formId = FormUtil.getHidField({
			    fieldLabel: '贷款申请单ID',
			    name: 'formId',
			    "width": 125,
			    "maxLength": "30"
			});
				var txt_gtype = FormUtil.getHidField({
			    fieldLabel: '抵押物类型',
			    name: 'gtype',
			    "width": 125,
			    "maxLength": "30"
			});
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '抵押物名称',
			    name: 'name',
			    "width": 125,
			    "maxLength": "30"
			});
			
			var int_quantity = FormUtil.getIntegerField({
			    fieldLabel: '数量',
			    name: 'quantity',
			    "width": 125,
			    "maxLength": 10
			});
			    
			var txt_unint = FormUtil.getTxtField({
			    fieldLabel: '单位',
			    name: 'unint',
			    "width": 30,
			    "maxLength": "20"
			});
			
			var quan_unint = FormUtil.getMyCompositeField({fieldLabel: '数量',sigins:null,itemNames:'quantity,unint', "allowBlank": false,
			    name: 'quan_unint',width:300,items:[int_quantity,{xtype:'displayfield',width:1},txt_unint,{xtype:'displayfield',value:'单位'}]});
			
			var txt_oldVal = FormUtil.getMoneyField({
			    fieldLabel: '原价值(元)',
			    name: 'oldVal',
			    "width": 125,
			    "allowBlank": false
			});
			
			var txt_mpVal = FormUtil.getMoneyField({
			    fieldLabel: '抵押价值',
			    name: 'mpVal',
			    "width": 125,
			    "allowBlank": false
			});
			
			var txt_mman = FormUtil.getTxtField({
			    fieldLabel: '抵押人',
			    name: 'mman',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "30"
			});
			
			var txt_conTel = FormUtil.getTxtField({
			    fieldLabel: '联系电话',
			    name: 'conTel',
			    "width": 125,
			    "maxLength": "20"
			});
			
			var bdat_morTime = FormUtil.getDateField({
			    fieldLabel: '抵押时间',
			    name: 'morTime',
			     "allowBlank": false,
			    "width": 125
			});
			
			var txt_address = FormUtil.getTxtField({
			    fieldLabel: '抵押物所在地',
			    name: 'address',
			    "width": 450,
			    "maxLength": "150"
			});
			
			var txt_conTel2 = FormUtil.getTxtField({
			    fieldLabel: '联系电话2',
			    name: 'conTel2',
			    "width": 125,
			    "maxLength": "20"
			});
			
			var bdat_morTime2 = FormUtil.getDateField({
			    fieldLabel: '抵押时间2',
			    name: 'morTime',
			     "allowBlank": false,
			    "width": 125
			});
			var items = FormUtil.getTwoLayout([txt_conTel2,bdat_morTime2]);
			var formDiyContainer = new Ext.Container({layout:'fit'});
			var txa_remark = FormUtil.getTAreaField({fieldLabel : '备注',name:'remark',width:450});
			var layout_fields = [txt_id,txt_formId,txt_gtype,{
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_name,quan_unint, txt_oldVal, txt_mpVal, txt_mman, txt_conTel, bdat_morTime]
			},txt_address,formDiyContainer,txa_remark];
			var frm_cfg = {
				autoHeight:true,
			    url: './fcMortgage_save.action',
			    formDiyCfg : {
			    	sysId : _this.sysId,/*系统ID*/
			    	formdiyCode : _this.gvlistid,/*引用Code --> 对应 ts_Formdiy 中的业务引用键：recode*/
			    	formIdName : 'id',/*对应表单中的ID Hidden 值*/
			    	container : formDiyContainer /*自定义字段存放容器*/
			    }
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
