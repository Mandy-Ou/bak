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
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:350,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, eventMgr:{saveData:this.saveData}
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
			var self = this;
			var urls = {};
			var parent = exports.WinEdit.parent;
			var selId = parent.getSelId();
			var subid = selId.substring(1);
			var cfg = {sfn:function(json_data){}};
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg.defaultVal = {};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './sysCountry_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var name = exports.WinEdit.parentCfg.self.apptree.getSelText();
				cfg = {params : {id:subid},defaultVal : {}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './sysCountry_get.action',cfg : cfg};
			}
			
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './sysCountry_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './sysCountry_next.action',cfg :cfg};
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
			 	_this.appWin.hide();
				_this.refresh(jsonObj);
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
			var self = this;
			var hid_isenabled = FormUtil.getHidField({name:'isenabled'});
			/*基础数据ID*/
			var hid_id = FormUtil.getHidField({name:'id'});
			
			/*国家编号*/
			var txt_Code = FormUtil.getReadTxtField({fieldLabel : '编号',name:'code',allowBlank:false,width:200,maxLength:20});
			/*国家名称*/
			var txt_name = FormUtil.getTxtField({fieldLabel : '国家名称',name:'name',width:200,maxLength:25,allowBlank:false});
			/*英文名称*/
			var txt_Jname = FormUtil.getTxtField({fieldLabel : '英文名称',name:'ename',width:200,maxLength:50});
			/*繁体中文名称*/
			var txt_Twname = FormUtil.getTxtField({fieldLabel : '繁体中文名称',name:'twname',width:200,maxLength:50});
			/*法文名称*/
			var txt_Fname = FormUtil.getTxtField({fieldLabel : '法文名称',name:'fname',width:200,maxLength:50});
			/*韩文名称*/
			var txt_Koname = FormUtil.getTxtField({fieldLabel : '韩文名称',name:'koname',width:200,maxLength:50});
			/*备注*/
			var txa_remark = FormUtil.getTAreaField({fieldLabel : '备注',name:'remark',width:200,maxLength:200});
			
			var layout_fields  = [hid_isenabled,hid_id,txt_Code,txt_name,txt_Jname,txt_Twname,txt_Fname,txt_Koname,txa_remark];
			
			var frm_cfg = {title : '资源编辑',url:'./sysCountry_save.action'};
		    var appform = FormUtil.createLayoutFrm(frm_cfg,layout_fields);
		   
			return appform;
		}
	};
});