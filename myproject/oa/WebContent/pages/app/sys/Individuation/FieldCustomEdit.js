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
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.sysid = parentCfg.sysid;
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:520,getUrls:this.getUrls,appFrm : this.appFrm,
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
			var self = this;
			var urls = {};
			var parent = exports.WinEdit.parent;
			var cfg = {sfn:function(json_data){}};
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/
				var formdiyid = parent.getSelId();
				var subid = formdiyid.substring(1);
				cfg.defaultVal = {formdiyId:subid};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './sysFieldCustom_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var NodeId = parent.getSelId();
				cfg = {params : {id:NodeId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './sysFieldCustom_get.action',cfg : cfg};
			}
			
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './sysFieldCustom_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './sysFieldCustom_next.action',cfg :cfg};
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
			/*自定义字段ID*/
			var hid_id = FormUtil.getHidField({fieldLabel : '自定义字段表id',name:'id'});
			/*表单id*/
			var hid_formdiyId = FormUtil.getHidField({fieldLabel : '表单diy Id',name:'formdiyId',allowBlank:false});
			/*字段属性名*/
			var txt_name = FormUtil.getTxtField({fieldLabel : '字段属性名',name:'name',allowBlank:false,maxLength:30});
			/*默认显示名称*/
			var txt_dispName = FormUtil.getTxtField({fieldLabel : '默认显示名称',name:'dispName',allowBlank:false,maxLength:50});
			/*控件类型*/
			var cbo_controlType = FormUtil.getLCboField({fieldLabel : '控件类型',name:'controlType',allowBlank:false,
												data:[["0","文本框"],["1","整数输入框"],["2","小数点输入框"],["3","金额输入框"],
														["4","日期输入框"],["5","本地下拉框"],["6","远程下拉框"],["7","多行文本框"],
														["8","单选框"],["9","复选框"]]
												});
			/*数据源*/
			var txt_datasource = FormUtil.getTxtField({fieldLabel : '数据源',name:'datasource',width:370,maxLength:200});
			/*是否必填*/
			var rad_isRequired = FormUtil.getRadioGroup({fieldLabel : '是否必填',name:'isRequired',
												items : [{boxLabel : '是', name:'isRequired',inputValue:1},
														{boxLabel : '否', name:'isRequired',inputValue:0, checked: true}]});
			/*最大长度*/	
			var txt_maxlength = FormUtil.getIntegerField({fieldLabel : '最大长度',name:'maxlength',allowBlank:false});
			/*所在行*/	
			var txt_row = FormUtil.getIntegerField({fieldLabel : '所在行',name:'row',allowBlank:false});
			/*所在列*/	
			var txt_col = FormUtil.getIntegerField({fieldLabel : '所在列',name:'col',allowBlank:false});
			/*宽*/	
			var txt_width = FormUtil.getIntegerField({fieldLabel : '宽',name:'width',allowBlank:false});
			/*默认值*/	
			var txt_dval = FormUtil.getTxtField({fieldLabel : '默认值',name:'dval',maxLength:50});
			/*高*/
			var txt_hieght = FormUtil.getIntegerField({fieldLabel : '高',name:'height'});
			/*英文名称*/
			var txt_ename = FormUtil.getTxtField({fieldLabel : '英文名称',name:'ename',maxLength:50});
			/*日本名称*/
			var txt_jname = FormUtil.getTxtField({fieldLabel : '日本名称',name:'jname',maxLength:50});
			/*繁体中文名称*/
			var txt_twname = FormUtil.getTxtField({fieldLabel : '英文名称',name:'twname',maxLength:50});
			/*法文名称*/
			var txt_fname = FormUtil.getTxtField({fieldLabel : '法文名称',name:'fname',maxLength:50});
			/*韩文名称*/
			var txt_koname = FormUtil.getTxtField({fieldLabel : '韩文名称',name:'koname',maxLength:50});
			/*备注*/
			
			var fset_gjh = FormUtil.createLayoutFieldSet({title:'国际化语言设置'},[
				{cmns:FormUtil.CMN_TWO,fields:[txt_ename,txt_jname,txt_twname,txt_fname,txt_koname]}]);
			var txa_remark = FormUtil.getTAreaField({fieldLabel : '备注',name:'remark',width:380,maxLength:200});
			
			var layout_fields  = [hid_isenabled,hid_id,hid_formdiyId,
									{cmns:'TWO',fields:[txt_name,txt_dispName,cbo_controlType,rad_isRequired,txt_maxlength,txt_row,txt_col,txt_width,txt_dval,txt_hieght]},
									txt_datasource,fset_gjh,txa_remark];
			
			var frm_cfg = {title : '添加个性表单化</br><span style="color:red">当控件类型是本地下拉框，单选框，复选框时候，数据源的格式：高中，大专，本科</span>',url:'./sysFieldCustom_save.action',autoHeight:true,labelWidth:100};
		    var appform = FormUtil.createLayoutFrm(frm_cfg,layout_fields);
			return appform;
		}
	};
});