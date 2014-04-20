/**
 * 角色复制页面
 * @author 程明卫
 * @date 2012-11-10
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
			this.appWin = new Ext.ux.window.AbsEditWindow({width:425,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh,changeBarItems:this.changeBarItems
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
				/*--- 添加 URL --*/	
				cfg = {params:{roleId:selId}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './sysRole_add.action',cfg : cfg};
			}
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
		changeBarItems : function(barItems){
			for(var i=0,count=barItems.length; i<count; i++){
				var item = barItems[i];
				if(item["text"] && item["text"] == Btn_Cfgs.SAVE_BTN_TXT){
					item.text = Btn_Cfgs.COPY_BTN_TXT;
					item.iconCls = Btn_Cfgs.ROLE_COPY_BTN_CLS;
					item.tooltip = Btn_Cfgs.COPY_TIP_BTN_TXT;
					item.key = Btn_Cfgs.COPY_FASTKEY;
				}
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
			
			/*角色ID*/
			var hid_id = FormUtil.getHidField({name:'oldid'});
			/*角色编码*/
			var txt_oldcode = FormUtil.getReadTxtField({fieldLabel : '角色编号',name:'oldcode',width:200,maxLength:20});
			/*角色名称*/
			var txt_oldname = FormUtil.getReadTxtField({fieldLabel : '角色名称',name:'oldname',width:200,maxLength:30});
			/*备注*/
			var txa_oldremark = FormUtil.getTAreaField({fieldLabel : '角色描述',name:'oldremark',readOnly:true,width:200,maxLength:200});
		
			/*角色编码*/
			var txt_code = FormUtil.getReadTxtField({fieldLabel : '角色编号',name:'code',allowBlank : false,width:200,maxLength:20});
			/*角色名称*/
			var txt_name = FormUtil.getTxtField({fieldLabel : '角色名称',name:'name',allowBlank : false,width:200,maxLength:30});
			/*备注*/
			var txa_remark = FormUtil.getTAreaField({fieldLabel : '角色描述',name:'remark',width:200,maxLength:200});
			
			var oldFieldSet = {
	            xtype:'fieldset',
	            title: '原角色信息',
	            autoHeight:true,
	            defaults: {width: 210},
	            collapsed: false,
	            items :[txt_oldcode,txt_oldname,txa_oldremark]
            };
            
            var newFieldSet = {
	            xtype:'fieldset',
	            title: '复制后的新角色信息',
	            autoHeight:true,
	            defaults: {width: 210},
	            collapsed: false,
	            items :[txt_code,txt_name,txa_remark]
            };
			var layout_fields  = [hid_id,oldFieldSet,newFieldSet];
			var title = '角色信息复制&nbsp;&nbsp;<span style="color:red;">(提示：角色复制后，原角色的权限将复制到新角色上)</span>';
			var frm_cfg = {title : title,url:'./sysRole_copy.action',height:400,labelWidth:100,items:layout_fields};
		    var appform = FormUtil.createFrm(frm_cfg);
		   
			return appform;
		}
	};
});