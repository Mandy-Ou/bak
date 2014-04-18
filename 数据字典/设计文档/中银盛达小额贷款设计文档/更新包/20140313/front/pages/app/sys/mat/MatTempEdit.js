/**
 * #DESCRIPTION#
 * @author smartplatform_auto
 * @date 2012-12-29 03:36:33
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		params : null,
		setParentCfg:function(parentCfg){
			this.params = parentCfg.params;
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:400,getUrls:this.getUrls,appFrm : this.appFrm,
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
				cfg = {params:{},defaultVal:{sysId :_this.params.sysId }};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './sysMatTemp_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				var id = selId.substr(1);
				cfg = {params : {id:id}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './sysMatTemp_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './sysMatTemp_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './sysMatTemp_next.action',cfg :cfg};
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
		    
			var hid_id = FormUtil.getHidField({
			    fieldLabel: 'id',
			    name: 'id',
			    "width": "150"
			    	
			  });
			  var hid_isenabled = FormUtil.getHidField({name:'isenabled'});
			var txt_sysId = FormUtil.getHidField({
				    fieldLabel: '系统Id',
				    name: 'sysId',
				    "width": 200,
				    "allowBlank": false
				});
				
				var txt_name = FormUtil.getTxtField({
				    fieldLabel: '模板名称',
				    name: 'name',
				    "width": 200,
				    "allowBlank": false,
				    "maxLength": "100"
				});
				
				var cbo_custType = FormUtil.getLCboField({
				    fieldLabel: '客户类型',
				    name: 'custType',
				    width:200,
				    allowBlank: false,
				    data: [["0","个人客户"],["1","企业客户"]]
				});
				
				var txt_breedId = new Ext.ux.form.AppComboxImg({
				    fieldLabel: '业务品种',
				    name: 'breedId',
				    "width": 200,
				     allowBlank: false,
				    valueField : 'id',
					displayField : 'name',
				    url : './sysVariety_cbolist.action',
				    callback : function(seldata){
				    	var sysId  = seldata.sysId;
				    	if(!sysId) sysId="";
				    	txt_sysId.setValue(sysId);
				    },
				   params : {sysId : _this.params.sysId}
				});
				
				var txa_remark = FormUtil.getTAreaField({
				    fieldLabel: '备注',
				    name: 'remark',
				    "width": 200,
				    "maxLength": 200
				});
				
				var layout_fields = [
				hid_id,hid_isenabled,txt_sysId, txt_name, cbo_custType,txt_breedId, txa_remark];
				var frm_cfg = {
				    title: '模版信息编辑',
				    url: './sysMatTemp_save.action'
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
