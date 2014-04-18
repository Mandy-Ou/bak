/**
 * #DESCRIPTION#
 * @author smartplatform_auto
 * @date 2012-12-30 05:04:42
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		params : null,
		appGrid : null,
		subjectId : null,
		setParentCfg:function(parentCfg){
			this.appGrid = parentCfg.appGrid;
			if(this.appGrid){
				this.subjectId = parentCfg.appGrid.getSelId();
			}
			this.params = parentCfg.params;
			this.parentCfg=parentCfg;
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
			var _this = exports.WinEdit;
			var urls = {};
			var parent = exports.WinEdit.parent;
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{},defaultVal:{subjectId:_this.subjectId}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './sysMatParams_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};	   
				urls[URLCFG_KEYS.GETURLCFG] = {url : './sysMatParams_get.action',cfg : cfg};
			}
			var id = _this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './sysMatParams_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './sysMatParams_next.action',cfg :cfg};
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
			var txt_id= FormUtil.getHidField({
			    fieldLabel: '一级标题ID',
			    name: 'id',
			    "width": 125
			});
			var hid_isenabled = FormUtil.getHidField({name:'isenabled'});
			var txt_subjectId = FormUtil.getHidField({
			    fieldLabel: '资料标题ID',
			    name: 'subjectId',
			    "width": 125,
			    "allowBlank": false
			});
			
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '资料项名称',
			    name: 'name',
			    "width": 370,
			    "allowBlank": false,
			    "maxLength": "200"
			});
			
			var rad_allowBlank = FormUtil.getRadioGroup({
			    fieldLabel: '是否必填项',
			    name: 'allowBlank',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": 10,
			    "items": [{
			        "boxLabel": "非必填",
			        "name": "allowBlank",
			        "inputValue": 0
			    },
			    {
			        "boxLabel": "必填",
			        "name": "allowBlank",
			        "inputValue": 1
			    }]
			});

			var rad_isAttach = FormUtil.getRadioGroup({
			    fieldLabel: '是否支持附件',
			    name: 'isAttach',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": 10,
			    "items": [{
			        "boxLabel": "否",
			        "name": "isAttach",
			        "inputValue": 0
			    },
			    {
			        "boxLabel": "是",
			        "name": "isAttach",
			        "inputValue": 1
			    }]
			});
			
			var rad_isRemark = FormUtil.getRadioGroup({
			    fieldLabel: '是否需要备注',
			    name: 'isRemark',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": 10,
			    "items": [{
			        "boxLabel": "否",
			        "name": "isRemark",
			        "inputValue": 0
			    },
			    {
			        "boxLabel": "是",
			        "name": "isRemark",
			        "inputValue": 1
			    }]
			});
			
			var int_orderNo = FormUtil.getIntegerField({
			    fieldLabel: '排序',
			    name: 'orderNo',
			    "width": 125,
			    "allowBlank": false,
			    "value": "0",
			    "maxLength": 10
			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 370,
			    "maxLength":30
			});
			
			var layout_fields = [txt_id,txt_subjectId,hid_isenabled,txt_name,{
			    cmns: FormUtil.CMN_TWO,
			    fields: [  rad_allowBlank, rad_isAttach, rad_isRemark, int_orderNo]
			}, txa_remark];
			var frm_cfg = {
				labelWidth : 90,
			    title: '资料项信息编辑',
			    url: './sysMatParams_save.action'
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
