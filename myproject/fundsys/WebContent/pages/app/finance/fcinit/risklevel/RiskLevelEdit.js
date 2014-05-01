/**
 * #DESCRIPTION#
 * @author smartplatform_auto
 * @date 2012-12-23 05:25:56
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
			this.appWin = new Ext.ux.window.AbsEditWindow({width:400,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh,isNotSetVs:true
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
				
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fcRiskLevel_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
			
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './fcRiskLevel_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './fcRiskLevel_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fcRiskLevel_next.action',cfg :cfg};
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
			var field_width = 200;
			var hid_id = FormUtil.getHidField({fieldLabel: 'id', name: 'id'});
			var txt_code = FormUtil.getReadTxtField({fieldLabel: '编号',name: 'code',"width": field_width,"allowBlank": false,"maxLength": "20"});
			
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '风险等级名称',
			    name: 'name',
			    "width": field_width,
			    "allowBlank": false,
			    "maxLength": "30"
			});
			
			var int_mindays = FormUtil.getIntegerField({
			    fieldLabel: '最小逾期天数',
			    name: 'mindays',
			    "width": 60,
			    "allowBlank": false,
			    "value": "0",
			    "maxLength": 10
			});
			
			var int_maxdays = FormUtil.getIntegerField({
			    fieldLabel: '最大逾期天数',
			    name: 'maxdays',
			    "width": 60,
			    "allowBlank": false,
			    "value": "0",
			    "maxLength": 10
			});
		
			var sit_lateDaysRange = FormUtil.getMyCompositeField({fieldLabel:'逾期天数范围',width:field_width,name:'lateDaysRange',
				 allowBlank: false,sigins:null,itemNames:'mindays,maxdays',items : [int_mindays,{xtype : 'displayfield',value : '至',width : 10},
				int_maxdays,{xtype : 'displayfield',value : '天',width : 10}]});
			var txt_color = FormUtil.getColorField({fieldLabel: '预警颜色', value: '#FFFFFF',name: 'color', msgTarget: 'qtip',width:field_width});
			var txa_remark = FormUtil.getTAreaField({fieldLabel : '备注',name:'remark',width:field_width,maxLength:200});
			
			var frm_cfg = {
			    title: '风险等级信息编辑',
			    url: './fcRiskLevel_save.action',
			    labelWidth : 90,
			    items : [hid_id,txt_code, txt_name, sit_lateDaysRange,txt_color, txa_remark]
			};
			var appform_1 = FormUtil.createFrm(frm_cfg);
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
