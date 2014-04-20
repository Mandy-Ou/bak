/**
 * 按钮新增或修改页面
 * @author 程明卫
 * @date 2012-07-12
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
			//if(this.appWin) return this.appWin;
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:300,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh
			});
			return this.appWin;
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
		getUrls : function(){
			var urls = {};
			
			var parent = exports.WinEdit.parent;
			var selId = parent.getSelId();
			var cfg = null;
			// 0 : 新增 , 1:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				if(selId && (selId+"").indexOf('C') != -1){
					selType=1;
				}else{
					selType=2;
				}
				var menuId = parent.getSelId();
				/*--- 添加 URL --*/	
				cfg = {params:{menuId:selId},defaultVal : {menuId:selId}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './sysModule_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				cfg = {params : {id:selId},defaultVal : {}};
				urls[URLCFG_KEYS.GETURLCFG]= {url : './sysModule_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './sysModule_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG]  = {url : './sysModule_next.action',cfg :cfg};
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
			var hid_isenabled = FormUtil.getHidField({name:'isenabled'});
			/*按钮ID*/ 
			var hid_id = FormUtil.getHidField({name:'id'});
			/*按钮ID*/
			var hid_menuId = FormUtil.getHidField({name:'menuId'});
			/*所属菜单*/
			var txt_menuName = FormUtil.getReadTxtField({fieldLabel : '所属菜单',name:'menuName',width:150});
			/*按钮编码*/
			var txt_code = FormUtil.getReadTxtField({fieldLabel : '按钮编号',name:'code',allowBlank : false,width:150});
			/*按钮名称*/
			var txt_name = FormUtil.getTxtField({fieldLabel : '按钮名称',name:'name',allowBlank : false,width:150,maxLength:30});
			/*按钮样式*/
			var txt_iconCls = FormUtil.getTxtField({fieldLabel : '按钮样式',name:'iconCls',allowBlank : false,width:150,maxLength:30});
			/*功能描述*/
			var txa_remark = FormUtil.getTAreaField({fieldLabel : '功能描述',name:'remark',width:FormUtil.ONE_WIDTH,width:150,maxLength:200});
			
			var layout_fields  = [hid_isenabled,hid_menuId,hid_id,txt_menuName,txt_code,txt_name,txt_iconCls,txa_remark];
				    	          
			var frm_cfg = {title : '按钮信息编辑',url:'./sysModule_save.action',height:250};
		    var appform = FormUtil.createLayoutFrm(frm_cfg,layout_fields);
			return appform;
		}
	};
});