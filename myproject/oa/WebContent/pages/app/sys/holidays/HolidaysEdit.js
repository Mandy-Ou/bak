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
			this.appWin = new Ext.ux.window.AbsEditWindow({width:280,getUrls:this.getUrls,appFrm : this.appFrm,
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
			var urls = {};
			var parent = exports.WinEdit.parent;
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{},defaultVal:{}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './sysHolidays_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var rowid=parent.getSelectionModel().getSelected();//获取一行的所有值
				var id= rowid.get("id");
				var sdate = rowid.get("sdate");
				var edate = rowid.get("edate");
				cfg = {params : {id:id,sdate:sdate,edate:edate}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './sysHolidays_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './sysHolidays_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './sysHolidays_next.action',cfg :cfg};
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
			var hid_Id =  FormUtil.getHidField({fieldLabel : '节假日id',name:'id'});
			/*节假日名称*/
			var txt_Name = FormUtil.getTxtField({fieldLabel : '节假日名称',name:'name',allowBlank:false,maxLength:100});
			/*起始日期*/
			var txt_sdate = FormUtil.getDateField({fieldLabel : '起始日期',name:'sdate',allowBlank:false});
			/*结束日期*/
			var txt_edate = FormUtil.getDateField({fieldLabel : '结束日期',name:'edate',allowBlank:false});
			
			var txa_remark = FormUtil.getTAreaField({fieldLabel : '备注',name:'remark',width:FormUtil.ONE_WIDTH,maxLength:200});
			
			var layout_fields  = [hid_isenabled,hid_Id,txt_Name,txt_sdate,txt_edate,txa_remark];
			var frm_cfg = {title : '节假日编辑',url:'./sysHolidays_save.action',height:210,labelWidth:83};
		   	var appform = FormUtil.createLayoutFrm(frm_cfg,layout_fields);
			return appform;
		}
	};
});



