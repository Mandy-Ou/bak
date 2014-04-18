/**
 * 基础数据新增或修改页面
 * @author 彭登浩
 * @date 2012-11-20
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		fset_roleset : null,
		rname : null,
		restypeId : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			this.rname = parentCfg.rname
			this.restypeId = parentCfg.restypeId;
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
			this.appWin.show(this.parent.getEl());
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
			var self = this;
			var urls = {};
			var parent = exports.WinEdit.parent;
			var selId = _this.restypeId;
			var rname = _this.rname;
			var cfg = {sfn:function(json_data){}};
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				cfg.defaultVal = {restypeId:selId,rname:rname};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './sysGvlist_add.action',cfg : cfg};
			}else{
				cfg = {params : {id:selId},defaultVal : {restypeId:selId,rname:rname}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './sysGvlist_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './sysGvlist_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './sysGvlist_next.action',cfg :cfg};
			return urls;
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
			refresh : function(data){
			var _this = exports.WinEdit;
			if(_this.parentCfg.self.callBack){
				_this.parentCfg.self.callBack(data);
				_this.appWin.hide();
			}
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
		},
		/**
		 * 创建Form表单
		 */
			createForm : function(){
				var self = this;
				var hid_isenabled = FormUtil.getHidField({name:'isenabled'});
				/*基础数据ID*/
				var hid_id = FormUtil.getHidField({name:'id'});	
				/*资源编号*/
				var txt_Code = FormUtil.getReadTxtField({fieldLabel : '编号',name:'code',allowBlank:false,width:200,maxLength:50,hidden:true});
				/*资源ID*/
				var hid_RestypeId = FormUtil.getHidField({fieldLabel : '资源ID',name:'restypeId'});
				/*资源名称*/
				var txt_Rname = FormUtil.getReadTxtField({fieldLabel : '所属资源',name:'rname',width:200,maxLength:50});
				/*默认名称*/
				var txt_Name = FormUtil.getTxtField({fieldLabel : '默认名称',name:'name',allowBlank:false,width:200,maxLength:50});
				var layout_fields  = [hid_isenabled,hid_id,txt_Code,hid_RestypeId,txt_Rname,txt_Name];
				var frm_cfg = {title : '资源编辑',url:'./sysGvlist_save.action'};
			    var appform = FormUtil.createLayoutFrm(frm_cfg,layout_fields);
				return appform;
			}
	};
});