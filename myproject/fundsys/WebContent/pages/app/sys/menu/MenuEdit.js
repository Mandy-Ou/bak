/**
 * 菜单新增或修改页面
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
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:540,height:390,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType,eventMgr : {saveData:this.saveData}
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
			var urls = {};
			var parent = exports.WinEdit.parent;
			var selId = parent.getSelId();
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				if(selId && (selId+"").indexOf('C') != -1){
					selType=1;
				}else{
					selType=2;
				}
				/*--- 添加 URL --*/	
				cfg = {params:{"pid":selId}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './sysMenu_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				cfg = {params : {menuId:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './sysMenu_get.action',cfg : cfg};
			}
			var menuId = this.appFrm.getValueByName("menuId");
			cfg = {params : {menuId:menuId}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './sysMenu_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './sysMenu_next.action',cfg :cfg};
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
			var hid_isenabled = FormUtil.getHidField({name:'isenabled'});
			/*菜单ID*/
			var hid_menuID = FormUtil.getHidField({name:'menuId'});
			/*卡片ID*/	
			var hid_accordionId = FormUtil.getHidField({name:'accordionId'});
			/*菜单类型*/
			var hid_type = FormUtil.getHidField({fieldLabel : '所属父菜单',name:'type'});
			/*父菜单ID*/ 
			var hid_pid = FormUtil.getHidField({name:'pid'});
			/*父菜单*/
			var txt_parentName = FormUtil.getReadTxtField({fieldLabel : '父菜单',name:'parentName'});
			/*系统大图标*/
//			var txt_biconCls = FormUtil.getTxtField({fieldLabel : '系统大图标',name:'biconCls',width:FormUtil.TWO_WIDTH,maxLength:200});
			var txt_biconCls = FormUtil.getMyImgChooseField({
			    fieldLabel: '系统图标',
			    width : FormUtil.TWO_WIDTH,
			    name: 'biconCls',
			    showIconTypes : [2,3],
			    maxLength:200
			});
			/*菜单编码*/
			var txt_code = FormUtil.getReadTxtField({fieldLabel : '菜单编码',name:'code',allowBlank : false,maxLength:20});
			/*菜单名称*/
			var txt_name = FormUtil.getTxtField({fieldLabel : '菜单名称',name:'name',allowBlank : false,maxLength:50});
			/*菜单样式*/
			var txt_iconCls = FormUtil.getMyImgChooseField({//FormUtil.getTxtField({fieldLabel : '菜单样式',name:'iconCls',maxLength:50});
			    fieldLabel: '菜单样式',
			    name: 'iconCls',
			    showIconTypes : [1],
			    maxLength:50
			});
			/*UI加载方式*/
			var rad_type = FormUtil.getRadioGroup({fieldLabel : 'UI加载方式',name:'loadType',allowBlank : false,
			items : [{boxLabel : 'localXHR', name:'loadType',inputValue:1, checked: true},{boxLabel : 'seaJs', name:'loadType',inputValue:2}]});
			/*排列顺序*/
			var int_orderNo = FormUtil.getIntegerField({fieldLabel : '排列顺序',name:'orderNo',allowBlank : false,value:0});
			/*url*/
			var txt_jsArray = FormUtil.getTxtField({fieldLabel : 'URL',name:'jsArray',width:FormUtil.TWO_WIDTH,maxLength:200});
			/*附加参数*/
			var txt_params = FormUtil.getTxtField({fieldLabel : '附加参数',name:'params',width:FormUtil.TWO_WIDTH,maxLength:200}); 
			/*tabId*/
			var txt_tabId = FormUtil.getTxtField({fieldLabel : '自定义TabId',name:'tabId',maxLength:50});
			/*缓存UI*/
			var rad_cached = FormUtil.getRadioGroup({fieldLabel : '缓存UI',allowBlank : false,name:'cached',width:100,
						items : [{boxLabel : '是', name:'cached',inputValue:1},
						         {boxLabel : '否', name:'cached',inputValue:0, checked: true}]});
			
			/*是否叶子*/
			var rad_leaf = FormUtil.getRadioGroup({fieldLabel : '是否叶子',allowBlank : false,name:'leaf',width:100,
						items : [{boxLabel : '是', name:'leaf',inputValue:'true', checked: true},
						         {boxLabel : '否', name:'leaf',inputValue:'false'}]});
			/*是否系统菜单*/
			var rad_isSystem = FormUtil.getRadioGroup({fieldLabel : '是否系统菜单',allowBlank : false,name:'isSystem',width:100,
						items : [{boxLabel : '是', name:'isSystem',inputValue:1},
						         {boxLabel : '否', name:'isSystem',inputValue:0,checked:true}]});
			/*备注*/
			var txa_remark = FormUtil.getTAreaField({fieldLabel : '备注',name:'remark',width:FormUtil.TWO_WIDTH,maxLength:200});
			
			var layout_fields  = [
			                      hid_isenabled,hid_menuID,hid_accordionId,hid_type,hid_pid,
				    	          {cmns:'TWO',fields:[txt_code,txt_parentName,txt_name,rad_leaf,txt_iconCls,rad_isSystem]},
				    	          txt_biconCls,
				    	          {cmns:'TWO',fields:[rad_type,int_orderNo]},
				    	          txt_jsArray,txt_params, {cmns:'TWO',fields:[rad_cached,txt_tabId]},txa_remark
				    	          ];
			var frm_cfg = {title : '菜单信息编辑',url:Cmw.getUrl('./sysMenu_save.action'),height:330,labelWidth:83};
		    var appform = FormUtil.createLayoutFrm(frm_cfg,layout_fields);
		   
			return appform;
		}
	};
});