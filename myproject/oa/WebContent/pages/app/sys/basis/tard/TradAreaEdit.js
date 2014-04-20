/**
 * 门店分区编辑
 * @author smartplatform_auto
 * @date 2013-11-22 09:02:15
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		grid1:null,
		grid2:null,
		grid3:null,
		grid4:null,
		TradAreaId:null,
		tbar_deduction:null,
		tbar_pitem:null,
		tcontactTbar:null,
		appWin : null,
		deductionWin : null,/*价格扣点Win*/
		pitemWin : null,/*费用项Win*/
		tcontactWin : null,/*分区联系人Win*/
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.tbar_deduction = this.getTbar();
			this.tbar_pitem = this.getPitemTbar();
			this.tcontactTbar = this.getTcontactTbar();
			this.grid1=this.getGrid();
			this.grid2=this.getGrid2();
			this.grid3=this.getGrid3();
			this.grid4=this.getGrid4();
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:570,appFrm : this.appFrm,getUrls:this.getUrls,
			eventMgr:{saveData:this.saveData},optionType : this.optionType, refresh : this.refresh
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
			if(!this.appWin) return;
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
			var _this=exports.WinEdit;
			var cfg = null;
			var tdesbled=false;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{},defaultVal:{isenabled:1,tradId:_this.parentCfg.selId}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './oaTradArea_add.action',cfg : cfg};
				_this.grid1.removeAll();
				_this.grid2.removeAll();
				_this.grid3.removeAll();
				_this.grid4.removeAll();
				tdesbled=true;
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './oaTradArea_get.action',cfg : cfg};
				_this.TradAreaId=selId;
				_this.grid1.reload({tradAreaId:selId});
				_this.grid2.reload({tradAreaId:selId});
				_this.grid3.reload({tradAreaId:selId});
				_this.grid4.reload({formId:selId,formType:ATTACHMENT_FORMTYPE.FType_9});
				tdesbled=false;
			}
			_this.tbar_pitem.setDisabled(tdesbled);
			_this.tbar_deduction.setDisabled(tdesbled);
			_this.tcontactTbar.setDisabled(tdesbled);
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './oaTradArea_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './oaTradArea_next.action',cfg :cfg};
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
			var hid_id = FormUtil.getHidField({
			    fieldLabel: 'ID',
			    name: 'id',
			    "width": 125,
			    "maxLength": "20"
			});
			
			var hid_Isenabled = FormUtil.getHidField({
			    fieldLabel: '可用标志',
			    name: 'isenabled',
			    "width": 125,
			    "maxLength": "20"
			});
			
			var txt_code = FormUtil.getTxtField({
			    fieldLabel: '分区编号',
			    name: 'code',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "20"
			});
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '分区名称',
			    name: 'name',
			    "allowBlank": false,
			    "maxLength": "50",
			    "width": 125
			    
			});
			var txt_tradId = FormUtil.getRCboField({
			    fieldLabel: '所属分类',
			    name: 'tradId',
			    isAll:true,
			    width:125,
			    disabled:true,
				register : REGISTER.GvlistDatas, restypeId : '100002',
			    "allowBlank": false
			});
			var txt_plevelId = FormUtil.getRCboField({
			    fieldLabel: '价格等级',
			    name: 'plevelId',
			    isAll:true,
			    "allowBlank": false,
			    register : REGISTER.GvlistDatas, restypeId : '100003',
			    "width": 125
			});
			var txt_contOrg = FormUtil.getTxtField({
			    fieldLabel: '合同单位',
			    name: 'contOrg',
			    "width": 390,
			    "allowBlank": false,
			    "maxLength": "150"
			});
			var txt_tded = FormUtil.getDoubleField({
			    fieldLabel: '特价扣点',
			    name: 'tded',
			    "allowBlank": false,
			    unitText:"%",
			    "width": 100
			});
			var txt_sded = FormUtil.getDoubleField({
			    fieldLabel: '正价扣点',
			    name: 'sded',
			    unitText:"%",
			    "width": 100
			});
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 390,
			    "maxLength": 50
			});
			var layout_fields = [{
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_code, txt_name, txt_tradId, txt_plevelId]
			},
			txt_contOrg, {
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_tded, txt_sded]
			},
			txa_remark,hid_id,hid_Isenabled];
			var frm_cfg = {
			    title: '商系分区编辑',
			    url: './oaTradArea_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			var _this=this;
			appform_1.add(_this.getTabPn());
			return appform_1;
		},
	getTabPn:  function(){
		var _this=this;
		var tabs=new Ext.TabPanel({
		    activeTab: 0,
		    height:200,
		    items:[_this.grid1,_this.grid2,_this.grid3,_this.grid4],
		    listeners:{
		    	"render":function(){
		    		tabs.activate(1);
		    		tabs.activate(2);
		    		tabs.activate(0);
		    	}
		    }
		})
		return tabs;
	},
	getGrid:function(){
		var structure_1 = [{
			    header: '扣点年份',
			    name: 'dyear'
			},
			{
			    header: '正价扣点',
			    renderer:Render_dataSource.percent_add_render,
			    name: 'sded'
			},
			{
			    header: '特价扣点',
			    renderer:Render_dataSource.percent_add_render,
			    name: 'tded'
			},
			{
			    header: '合同名称',
			    name: 'contractName'
			},
			{
			    header: '合同',
			    name: 'attachmentName'
			},
			{
			    header: '销售保底',
			    renderer:Render_dataSource.percent_add_render,
			    name: 'paul'
			}];
			
			var _this=this;
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    title: '商系价格扣点',
			    tbar : _this.tbar_deduction,
			    structure: structure_1,
			    url: './oaDeduction_list.action',
			    needPage: false,
			    isLoad: false,
			    keyField: 'id'
			});
			return appgrid_1;
	},
	getTbar : function(){
		var _this = this;
		  var barItems = [{
			text : '添加扣点',
			iconCls:'page_add',
			tooltip:'添加扣点',
			handler : function(){
			 _this.openDeductionWin(OPTION_TYPE.ADD);
			}
			},{
			text : '编辑扣点',
			iconCls:'page_edit',
			tooltip:'编辑扣点',
			handler : function(){
				 _this.openDeductionWin(OPTION_TYPE.EDIT);
			}
			},{
			token : "删除扣点",
			text : "删除扣点",
			iconCls:'page_delete',
			tooltip:"删除扣点",
			handler : function(){
				EventManager.deleteData('./oaDeduction_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		var tbar_deduction = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,disabled:true});
		return tbar_deduction;
	},
	/**
	 * 打开扣点Win
	 */
	openDeductionWin : function(optionType){
		var cfg = {optionType:optionType};
		cfg.tradAreaId=this.TradAreaId;
		cfg.parent=this.grid1;
		if(this.deductionWin){
			this.deductionWin.show(cfg);
		}else{
			Cmw.importPackage('pages/app/sys/basis/tard/DeductionEdit',function(module) {
				module.WinEdit.show(cfg);
			});
		}
	},
	getGrid2:function(){
		var structure_1 = [{
			    header: '年度',
			    name: 'dyear'
			},
			{
			    header: '费用项目',
			    name: 'itemId'
			},
			{
			    header: '费用标准',
			    name: 'amount'
			},
			{
			    header: '备注',
			    name: 'remark'
			}];
		
			var _this=this;
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    title: '商系费用项',
			    structure: structure_1,
			    tbar : _this.tbar_pitem,
			    url: './oaPitem_list.action',
			    needPage: false,
			    isLoad: false,
			    keyField: 'id'
			});
			return appgrid_1;
	},
	getPitemTbar : function(){
		var _this = this;
		  var barItems = [{
			text : '添加费用',
			iconCls:'page_add',
			tooltip:'添加费用',
			handler : function(){
			 _this.openPitemWin(OPTION_TYPE.ADD);
			}
			},{
			text : '编辑费用',
			iconCls:'page_edit',
			tooltip:'编辑费用',
			handler : function(){
				 _this.openPitemWin(OPTION_TYPE.EDIT);
			}
			},{
			token : "删除费用",
			text : "删除费用",
			iconCls:'page_delete',
			tooltip:"删除费用",
			handler : function(){
				EventManager.deleteData('./oaDeduction_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		var tbar_pitem = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,disabled:true});
		return tbar_pitem;
	},
	/**
	 * 打开费用项Win
	 */
	openPitemWin : function(optionType){
		var cfg = {optionType:optionType};
		cfg.tradAreaId=this.TradAreaId;
		cfg.parent=this.grid2;
		if(this.pitemWin){
			this.pitemWin.show(cfg);
		}else{
			Cmw.importPackage('pages/app/sys/basis/tard/PitemEdit',function(module) {
				module.WinEdit.show(cfg);
			});
		}
	},
	getGrid3 : function(){
		var structure_1 = [{
		    header: '姓名',
		    name: 'name'
		},
		{
		    header: '性别',
		    name: 'sex'
		},
		{
		    header: '职位',
		    name: 'job'
		},
		{
		    header: '手机',
		    name: 'phone'
		},
		{
		    header: '电话',
		    name: 'tel'
		},
		{
		    header: 'Email',
		    name: 'email'
		},
		{
		    header: '备注',
		    name: 'remark'
		},
		{
		    header: '商系分区ID',
		    name: 'tradAreaId',
		    width: 100,
		    hidden: true
		}];
		
		var _this=this;
		var appgrid_1 = new Ext.ux.grid.AppGrid({
		    title: '分区联系人',
		    structure: structure_1,
		    tbar : _this.tcontactTbar,
		    url: './oaTcontact_list.action',
		    needPage: false,
		    isLoad: false,
		    keyField: 'id'
		});
		return appgrid_1;
	},
	getTcontactTbar : function(){
		var _this = this;
		  var barItems = [{
			text : '添加联系人',
			iconCls:'page_add',
			tooltip:'添加联系人',
			handler : function(){
			 _this.openTcontactWin(OPTION_TYPE.ADD);
			}
			},{
			text : '编辑联系人',
			iconCls:'page_edit',
			tooltip:'编辑联系人',
			handler : function(){
				 _this.openTcontactWin(OPTION_TYPE.EDIT);
			}
			},{
			token : "删除联系人",
			text : "删除联系人",
			iconCls:'page_delete',
			tooltip:"删除联系人",
			handler : function(){
				EventManager.deleteData('./oaTcontact_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		var tbar_tcontact = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,disabled:true});
		return tbar_tcontact;
	},
	/**
	 * 打开费用项Win
	 */
	openTcontactWin : function(optionType){
		var cfg = {optionType:optionType};
		cfg.tradAreaId=this.TradAreaId;
		cfg.parent=this.grid3;
		if(this.tcontactWin){
			this.tcontactWin.show(cfg);
		}else{
			Cmw.importPackage('pages/app/sys/basis/tard/TcontactEdit',function(module) {
				module.WinEdit.show(cfg);
			});
		}
	},
	getGrid4:function(){
		var structure_1 = [{
			    header: '附件编号',
			    name: 'id'
			},
			{
			    header: '附件名称',
			    name: 'fileName'
			},
			{
			    header: '附件路径',
			    name: 'filePath'
			},
			{
			    header: '上传日期',
			    name: 'createTime'
			},
			{
			    header: '上传人',
			    name: 'creator'
			},
			{
			    header: '备注',
			    name: 'remark'
			}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    title: '附件列表',
			    structure: structure_1,
			    url: './sysAttachment_list.action',
			    needPage: false,
			    isLoad: false,
			    keyField: 'id'
			});
			return appgrid_1;
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
			var cfg = {
				sfn : function(formData){
					_this.TradAreaId=formData.id;
					_this.parentCfg.parent.reload({tradId:_this.parentCfg.selId});
					_this.tbar_deduction.enable();
					_this.tbar_pitem.enable();
					_this.tcontactTbar.enable();
					}
			};
			EventManager.frm_save(_this.appFrm,cfg);
		},
	/**
	 *  重置数据 
	 */
	resetData : function(){
	}
	};
});
