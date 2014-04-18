/**
 * 公司帐户
 * @author smartplatform_auto
 * @date 2012-12-08 06:31:07
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
			this.appWin = new Ext.ux.window.AbsEditWindow({width:350,getUrls:this.getUrls,appFrm : this.appFrm,
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
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fcAccount_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './fcAccount_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './fcAccount_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fcAccount_next.action',cfg :cfg};
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
					var wd =200;
				var hid_isenabled = FormUtil.getHidField({name:'isenabled'});
				var txt_id = FormUtil.getHidField({
				    fieldLabel: 'id',
				    name: 'id',
				    "width": wd
				});
				
				var txt_refId = FormUtil.getHidField({
				    fieldLabel: '财务账号ID',
				    name: 'refId'
				});
				
				var txt_isenabled = FormUtil.getHidField({
				    fieldLabel: 'isenabled',
				    name: 'isenabled'
				});
				
				
				var txt_code = FormUtil.getTxtField({
				    fieldLabel: '科目编号',
				    name: 'code',
				    "width": wd,
				    "allowBlank": false
				});
				
				
				var txt_bankName = FormUtil.getTxtField({
				    fieldLabel: '银行名称',
				    name: 'bankName',
				    "width": wd,
				    "allowBlank": false
				});
				
				var txt_account = FormUtil.getTxtField({
				    fieldLabel: '银行帐号',
				    name: 'account',
				    "width": wd,
				    "allowBlank": false
				});
				
				var cbo_atype = FormUtil.getLCboField({
				    fieldLabel: '账户类型',
				    name: 'atype',
				    "width":  wd,
				    "allowBlank": false,
				    "data": Lcbo_dataSource.account_atype_datas
				});
				
			var remark  = FormUtil.getTAreaField({fieldLabel : '备注',name:'remark',width:wd});
				
				var layout_fields = [
				hid_isenabled,txt_id, txt_refId, txt_isenabled, txt_code, txt_bankName, txt_account, cbo_atype,remark];
				var frm_cfg = {
				    title: '公司帐户',
				    url: './fcAccount_save.action'
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
