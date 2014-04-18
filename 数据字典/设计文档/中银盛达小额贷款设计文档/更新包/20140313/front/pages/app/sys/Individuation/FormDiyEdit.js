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
		sysid : null,
		formdiyDialog : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.sysid = parentCfg.sysid;
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:370,getUrls:this.getUrls,appFrm : this.appFrm,isNotSetVs : true,
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
			var sysid = exports.WinEdit.sysid;
			var cfg = {sfn:function(json_data){}};
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg.defaultVal = {sysid:sysid};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './sysFormdiy_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var NodeId = parent.getSelId();
				var selId = NodeId.substring(1);
				cfg = {params : {id:selId},defaultVal : {}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './sysFormdiy_get.action',cfg : cfg};
			}
			
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './sysFormdiy_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './sysFormdiy_next.action',cfg :cfg};
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
			var self = this;
			var hid_isenabled = FormUtil.getHidField({name:'isenabled'});
			/*基础数据ID*/
			var hid_id = FormUtil.getHidField({fieldLabel : '基础数据id',name:'id'});
			/*系统id*/
			var hid_sysid = FormUtil.getHidField({fieldLabel : '系统id',name:'sysid'});
			/*是否来自基础数据*/
			var Rad_isRestype = FormUtil.getRadioGroup({fieldLabel : '是否来自基础数据',name:'isRestype',allowBlank:false,
				items :[{boxLabel : '是', name:'isRestype',inputValue:1},{boxLabel : '否', name:'isRestype',inputValue:0}]});
			Rad_isRestype.addListener('change',function(){
				var isNotrest =  Rad_isRestype.getValue();
				if(isNotrest){
					Butn.enable();
				}else{
					Butn.setDisabled(true);
				}
			});	
			/*业务引用键*/
			var txt_Recode = FormUtil.getTxtField({fieldLabel : '业务引用键',name:'recode',width:130,allowBlank:false,maxLength:50});
			/*buuton*/
			var Butn = new Ext.Button({text: '基础数据',name:'btn'});
			Butn.disable();	
			var rec_btn = FormUtil.getMyCompositeField({fieldLabel: '业务引用键',allowBlank:false,itemsOne: true ,sigins:null,itemNames : 'recode,btn',
			    name: 'recodeCmp',width:200,items:[txt_Recode,{xtype:'displayfield',width:1},Butn]});
			    
			/*表单名称*/
			var txt_Name = FormUtil.getTxtField({fieldLabel : '表单名称',name:'name',width:200,allowBlank:false,maxLength:50});
			/*关联功能列表*/
			var txt_Funcs = FormUtil.getTxtField({fieldLabel : '关联功能列表',name:'funcs',width:200,maxLength:200});
			/*关联功能引用键*/
			var txt_Frecode = FormUtil.getTxtField({fieldLabel : '关联功能引用键',name:'frecode',width:200,maxLength:200});
			/*最大列数*/
			var txt_MaxCmncount = FormUtil.getIntegerField({fieldLabel : '最大列数',name:'maxCmncount',
				width:200,allowBlank:false,emptyText :'最大列数必须大于0'
				});
			txt_MaxCmncount.addListener('change',function(){
				var max = txt_MaxCmncount.getValue(); 
				if(max<=0) txt_MaxCmncount.reset(); 
			});
			var txa_remark = FormUtil.getTAreaField({fieldLabel : '备注',name:'remark',width:200});
			
			var layout_fields  = [hid_isenabled,hid_id,hid_sysid,Rad_isRestype,rec_btn,txt_Name,txt_Funcs,txt_Frecode,txt_MaxCmncount,txa_remark];
			
			var frm_cfg = {title : '添加个性表单化',url:'./sysFormdiy_save.action',height:300,labelWidth:110};
			Butn.addListener("click",function(){
					var _this = this;
					var appFrm = _this.appFrm;
					var parentCfg = {
					parent :Butn,
					params : {
						recode :["100016","100017"]
					},
					callback : function(data){
						var id = data.id;
						var name = data.name;
						txt_Recode.reset();
						txt_Name.reset();
						txt_Recode.setValue(id);
						txt_Name.setValue(name);
					}
				};
				if(_this.formdiyDialog){
					_this.formdiyDialog.show(parentCfg);
				}else{
					Cmw.importPackage('/pages/app/dialogbox/FormDiyDialogbox', function(module) {
						_this.formdiyDialog = module.DialogBox;
						_this.formdiyDialog.show(parentCfg);
					});
				}
			});
		    var appform = FormUtil.createLayoutFrm(frm_cfg,layout_fields);
			return appform;
		}
	};
});