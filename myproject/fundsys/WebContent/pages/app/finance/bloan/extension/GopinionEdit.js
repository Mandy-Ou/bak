/**
 * 担保人意见编辑窗口
 * @author smartplatform_auto
 * @date 2013-09-08 06:22:36
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		params : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
			this.params = parentCfg.params;
		},
		createAppWindow : function(){
			var _this = this;
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:780,getUrls:function(){return _this.getUrls();},appFrm : this.appFrm,
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
			var parent = exports.WinEdit.parent;
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				var extensionId = this.params.extensionId;
				cfg = {params:{},defaultVal:{extensionId:extensionId}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fcGopinion_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './fcGopinion_get.action',cfg : cfg};
				this.appWin.apptbar.hideButtons(Btn_Cfgs.PREVIOUS_BTN_TXT +","+ Btn_Cfgs.NEXT_BTN_TXT);
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './fcGopinion_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fcGopinion_next.action',cfg :cfg};
			return urls;
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var _this = exports.WinEdit;
			if(_this.parentCfg.parent)_this.parentCfg.parent.reload({extensionId:_this.params.extensionId});	
		},
		/**
		 * 创建Form表单
		 */
		createForm : function(){
		
			var txt_id = FormUtil.getHidField({
			    fieldLabel: 'ID',
			    name: 'id',
			    "width": "125"
			});
			
			var txt_isenabled = FormUtil.getHidField({
			    fieldLabel: '可用标识',
			    name: 'isenabled',
			    "width": "125"
			});
			
			var txt_extensionId = FormUtil.getHidField({
			    fieldLabel: '展期申请单ID',
			    name: 'extensionId',
			    "width": 125,
			    "allowBlank": false
			});
			
			var txt_guarantor = FormUtil.getTxtField({
			    fieldLabel: '担保人',
			    name: 'guarantor',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "30"
			});
			
			var txt_legal = FormUtil.getTxtField({
			    fieldLabel: '法定/授权代表人',
			    name: 'legal',
			    "width": 125,
			    "maxLength": "30"
			});
			
			var bdat_signDate = FormUtil.getDateField({
			    fieldLabel: '签字日期',
			    name: 'signDate',
			    "width": 125,
			    "allowBlank": false
			});
			
			var txa_opinion = FormUtil.getTAreaField({
			    fieldLabel: '担保人意见',
			    name: 'opinion',
			    "width": 600,
			    "allowBlank": false,
			    "maxLength": "200"
			});
			
			var layout_fields = [
			txt_id, txt_isenabled, txt_extensionId, {
			    cmns: FormUtil.CMN_THREE,
			    fields: [txt_guarantor, txt_legal, bdat_signDate]
			},
			txa_opinion];
			var frm_cfg = {
			    title: '担保人意见编辑',
			    labelWidth : 100,
			    url: './fcGopinion_save.action'
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
