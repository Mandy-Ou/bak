/**
 * 部门新增或修改页面
 * @author 彭登浩
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
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;// 默认是添加的
		},
		createAppWindow : function(){
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:540,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType ,eventMgr : {saveData:this.saveData}
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
			this.appWin.show(this.parent.getEl().fadeIn());
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
			var selId = parent.getSelId();
			var cfg = null;
			var indeptId = null;
			var _this =  exports.WinEdit;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				var subpoid=selId.substring(1);
				var deptName=parent.getSelText();
				cfg = {params:{"indeptId":subpoid},defaultVal : {indeptId:subpoid,deptName:deptName}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './sysPost_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var curNode = parent.getCurNode();
				var parentNode = curNode.parentNode;
				indeptId = parentNode.id.substring(1);
				cfg = {params : {id:selId},defaultVal : {}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './sysPost_get.action',cfg : cfg};
				
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './sysPost_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './sysPost_next.action',cfg :cfg};
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
			var _this = exports.WinEdit;
			var appform = _this.appFrm;
			 EventManager.frm_save(appform,{sfn:function(jsonObj){
				_this.refresh(jsonObj);
				_this.appWin.hide();
			}});
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
			var _this =this;
			var hid_isenabled = FormUtil.getHidField({name:'isenabled'});
			/*职位ID*/
			var hid_id = FormUtil.getHidField({name:'id'});
			/*所属部门ID*/
			var hid_indeptId = FormUtil.getHidField({fieldLabel : '所属部门Id',name:'indeptId'});
			/*岗位编号*/
			var txt_code= FormUtil.getReadTxtField({fieldLabel : '岗位编号',name:'code',width:130,allowBlank : false});
			/*岗位名称*/
			var txt_name = FormUtil.getTxtField({fieldLabel : '岗位名称',name:'name',width:130,allowBlank : false,maxLength:100});
			/*所属部门*/
			var txt_deptName = FormUtil.getReadTxtField({fieldLabel : '所属部门',name:'deptName',width:130,allowBlank : false});
			/*拼音助记码*/
			var txt_mnemonic = FormUtil.getTxtField({fieldLabel : '拼音助记码',name:'mnemonic',width:130,maxLength:100});
			/*部门负责人*/
			var txt_mtask = FormUtil.getTAreaField({fieldLabel : '主要职责',name:'mtask',width:390,maxLength:200});
			
			/*备注*/
			var txa_remark = FormUtil.getTAreaField({fieldLabel : '备注',name:'remark',width:390,maxLength:200});
			var layout_fields  = [
								hid_isenabled,hid_id,hid_indeptId,
				    	          {cmns:'TWO',fields:[txt_code,txt_name]},
				    	          {cmns:'TWO',fields:[txt_deptName,txt_mnemonic]},
				    	          txt_mtask,txa_remark
				    	          ];
			var frm_cfg = {title : '职位信息编辑',url : Cmw.getUrl('./sysPost_save.action')/*,url:'./sysPost_save.action'*/,height:270,labelWidth:83};
		    var appform = FormUtil.createLayoutFrm(frm_cfg,layout_fields);
		   
			return appform;
		}
	};
});