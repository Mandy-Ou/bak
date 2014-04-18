/**
 * 凭证模板编辑窗口
 * @author smartplatform_auto
 * @date 2013-04-09 03:46:12
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		finsysId : null,/*业务财务系统映射ID*/
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		etTbar : null,/*分录工具栏*/
		itTbar : null,/*核算项工具栏*/
		etGrid : null,/*分录表格*/
		itGrid : null,/*核算项表格*/
		appFrm : null,
		appWin : null,
		etWin : null,/*分录窗口*/
		itWin : null,/*核算项窗口*/
		addBtn : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.finsysId = parentCfg.finsysId;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			var _this = this;
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:780,height:580,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, eventMgr:{saveData:function(win){_this.saveData(win);}},refresh : this.refresh
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
			this.appWin.close();	//关闭并销毁窗口
			this.appWin = null;		//释放当前窗口对象引用
			if(this.etWin && this.etWin.close){
				this.etWin.close();
				this.etWin = null;
			}
			if(this.itWin  && this.itWin.close){
				this.itWin.close();
				this.itWin = null;
			}
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
			var cfg = null;
			var eTdisabled = false;
			var aTdisabled = true;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/
				cfg = {params:{},defaultVal:{finsysId:exports.WinEdit.finsysId}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fsVoucherTemp_add.action',cfg : cfg};
				eTdisabled = true;
				exports.WinEdit.etGrid.removeAll();
				exports.WinEdit.itGrid.removeAll();
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './fsVoucherTemp_get.action',cfg : cfg};
				exports.WinEdit.etGrid.reload({voucherId:selId});
			}
			exports.WinEdit.etTbar.setDisabled(eTdisabled);
			exports.WinEdit.itTbar.setDisabled(aTdisabled);
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './fsVoucherTemp_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fsVoucherTemp_next.action',cfg :cfg};
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
			var txt_id = FormUtil.getHidField({
			    fieldLabel: 'ID',
			    name: 'id',
			    "width": "125"
			});
			
			var txt_finsysId = FormUtil.getHidField({
			    fieldLabel: '财务系统ID',
			    name: 'finsysId',
			    "width": "125"
			});
			
			var hid_isenabled = FormUtil.getHidField({
			    fieldLabel: '可用标识',
			    name: 'isenabled',
			    "width": "125"
			});
			
			
			var txt_code = FormUtil.getTxtField({
			    fieldLabel: '凭证模板编号',
			    name: 'code',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "30"
			});
			
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '凭证模板名称',
			    name: 'name',
			    "width": 250,
			    "allowBlank": false,
			    "maxLength": "50"
			});
			
			var rcbo_group = FormUtil.getRCboField({
			    fieldLabel: '凭证字',
			    name: 'groupId',
			    "width": 125,
			    "allowBlank": false,
			    url:'./fsVoucherGroup_cbodatas.action?finsysId='+this.finsysId,
			    "maxLength": "50"
			});
			
			
			var rcbo_currency = FormUtil.getRCboField({
			    fieldLabel: '默认币别',
			    name: 'currencyId',
			    "width": 125,
			    "allowBlank": false,
			    url:'./fsCurrency_cbodatas.action?finsysId='+this.finsysId,
			    "maxLength": "50"
			});
			
			var lcbo_entry = FormUtil.getLCboField({fieldLabel : '分录方向',name:'entry',allowBlank : false,
							data:[["1","借方多分录"],["2","贷方多分录"],["3","双方多分录"]]});
			
			
			var rad_tactics = FormUtil.getRadioGroup({fieldLabel : '批量业务策略',allowBlank : false,width:300,name:'tactics',
				items : [{boxLabel : '一笔业务生成一张凭证', name:'tactics',inputValue:1, checked: true},
						{boxLabel : '多笔业务生成一张凭证', name:'tactics',inputValue:2}]});

			
			var int_maxcount = FormUtil.getIntegerField({
			    fieldLabel: '分录最大条数',
			    name: 'maxcount',
			    "width": 125,
			    "allowBlank": false,
			    "value": "500",
			    "maxLength": 10
			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 620,
			    height:50,
			    "maxLength": 200
			});
			
			var layout_fields = [txt_id,txt_finsysId,hid_isenabled,{
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_code, txt_name]
			},{
			    cmns: FormUtil.CMN_THREE,
			    fields: [rcbo_group, rcbo_currency, lcbo_entry]
			},{
			    cmns: FormUtil.CMN_TWO_LEFT,
			    fields: [rad_tactics, int_maxcount]
			},
			txa_remark];
			var frm_cfg = {
			    title: '凭证模板基本信息编辑&nbsp;&nbsp;(提示：<span style="color:red;">添加新模板时，须先保存凭证模板基本信息才能添加分录</span>)',
			    url: './fsVoucherTemp_save.action',
			    labelWidth:100
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			this.etGrid = this.createEtGrid();
			appform_1.add(this.etGrid);
			this.itGrid = this.createItGrid();
			appform_1.add(this.itGrid);
			return appform_1;
		},
		/**
		 * 创建分录Grid
		 */
		createEtGrid : function(){
			var _this = this;
			var structure_1 = [{
			    header: '摘要说明',
			    name: 'summary',
			    width:200
			},
			{
			    header: '科目',
			    name: 'account'
			},
			{
			    header: '对方科目',
			    name: 'account2'
			},
			{
			    header: '结算方式',
			    name: 'settle',
			    width:60
			},
			{
			    header: '币别',
			    name: 'currency',
			    width:60
			},
			{
			    header: '余额方向',
			    name: 'fdc',
			    renderer : Fs_Render_dataSource.subject_dcRender
			},
			{
			    header: '金额计算公式',
			    name: 'formula'
			},
			{
			    header: '条件公式',
			    name: 'condition'
			}];
			this.etTbar = this.createEtTbar();
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    title: '分录设置列表',
			    tbar : this.etTbar,
			    structure: structure_1,
			    url: './fsEntryTemp_list.action',
			    needPage: false,
			    isLoad: false,
				height:200,
			    keyField: 'id'
			});
			appgrid_1.addListener('rowclick',function(grid,rowIndex,e){
				var selId = grid.getSelId();
				gdParams = {entryId:selId};
				_this.itGrid.reload(gdParams);
				_this.itTbar.enable(false);
				var delay = _this.disAddBut(false);
				delay.delay(50);
			});
			return appgrid_1;
		},
		/**
		 * 禁用/启用添加核算按钮
		 */
		disAddBut : function(flag){
			var _this = this;
			var delay = new Ext.util.DelayedTask(function(){//延时禁用按钮
				if(flag){
					_this.itTbar.enableButtons('添加核算项');
				}else{
					var ds = _this.itGrid.getStore(); 
					if(ds.getTotalCount()>0){
						_this.itTbar.disableButtons('添加核算项');
					}
				}
			});
			return delay;
		},
		/**
		 * 创建核算项Grid
		 */
		createItGrid : function(){
			
			var structure_1 = [{
			    header: '核算项',
			    name: 'itemClass',
			    width: 150
			},
			{
			    header: '核算业务对象',
			    name: 'bussObject',
			    width: 150
			},
			{
			    header: '核算自定义字段',
			    name: 'fieldNames',
			    width: 200
			},
			{
			    header: '备注',
			    name: 'remark',
			    width: 200
			}];
			this.itTbar = this.createItTbar();
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    title: '核算项设置列表',
			    structure: structure_1,
			    tbar : this.itTbar,
			    url: './fsItemTemp_list.action',
			    needPage: false,
			    isLoad: false,
			    height:150,
			    keyField: 'id'
			});
			return appgrid_1;
		},
		/**
		 * 创建分录Grid工具栏
		 */
		createEtTbar : function(){
			var _this = this;
			var barItems = [
				{token : '添加分录',text:Fs_Btn_Cfgs.ENTRY_ADD_BTN_TXT,iconCls:Fs_Btn_Cfgs.ENTRY_ADD_BTN_CLS,handler:function(){
					_this.openEtWin(OPTION_TYPE.ADD);
				}},
				{token : '编辑分录',text:Fs_Btn_Cfgs.ENTRY_EDIT_BTN_TXT,iconCls:Fs_Btn_Cfgs.ENTRY_EDIT_BTN_CLS,handler:function(){
					_this.openEtWin(OPTION_TYPE.EDIT);
				}},
				{token : '删除分录',text:Fs_Btn_Cfgs.ENTRY_DEL_BTN_TXT,iconCls:Fs_Btn_Cfgs.ENTRY_DEL_BTN_CLS,handler:function(){
					_this.delData('删除分录');
				}}];
			var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',disabled:true,controls:barItems});
			
			return toolBar;
		},
		/**
		 * 创建核算项Grid工具栏
		 */
		createItTbar : function(){
			var _this = this;
			var barItems = [
				{token : '添加核算项',text:'添加核算项',iconCls:Fs_Btn_Cfgs.ENTRY_ADD_BTN_CLS,handler:function(){
					_this.openItWin(OPTION_TYPE.ADD);
				}},
				{token : '编辑核算项',text:'编辑核算项',iconCls:Fs_Btn_Cfgs.ENTRY_EDIT_BTN_CLS,handler:function(){
					_this.openItWin(OPTION_TYPE.EDIT);
				}},
				{token : '删除核算项',text:'删除核算项',iconCls:Fs_Btn_Cfgs.ENTRY_DEL_BTN_CLS,handler:function(){
					_this.delData('删除核算项');
				}}];
			var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',disabled:true,controls:barItems});
			return toolBar;
		},
		/**
		 * 打开分录编辑窗口
		 * @param op 添加、修改
		 */
		openEtWin : function(op){
			var _this = this;
			if(op == OPTION_TYPE.EDIT  && !this.etGrid.getSelId()){
				return;
			}
			var voucherId = this.appFrm.getValueByName("id"); 
			var itTbar = this.itTbar;
			var parentCfg = {self:this,parent:this.etGrid,optionType:op,finsysId:this.finsysId,voucherId:voucherId};
			if(this.etWin){
				this.etWin.show(parentCfg);
			}else{
				Cmw.importPackage('pages/app/fininter/vtemp/EntryTempEdit',function(module) {
				 	_this.etWin = module.WinEdit;
				 	_this.etWin.show(parentCfg);
		  		});
			}
		},
		/**
		 * 打开核算项编辑窗口
		 * @param op 添加、修改
		 */
		openItWin : function(op){
			var _this = this;
			if(op == OPTION_TYPE.EDIT  && !this.itGrid.getSelId()){
				return;
			}
			var entryId = this.etGrid.getSelId();
			var parentCfg = {self:this,parent:this.itGrid,optionType:op,finsysId:this.finsysId,entryId:entryId};
			if(this.itWin){
				this.itWin.show(parentCfg);
			}else{
				Cmw.importPackage('pages/app/fininter/vtemp/ItemTempEdit',function(module) {
				 	_this.itWin = module.WinEdit;
				 	_this.itWin.show(parentCfg);
		  		});
			}
		},
		/**
		 * 删除分录、核算项数据
		 */
		delData : function(op){
			var _this =this;
			var tempGrid = null;
			var gdParams = null;
			var url = null;
			if(op == '删除分录')	{
				tempGrid = this.etGrid;
				var selId = this.parent.getSelId();
				gdParams = {voucherId:selId};
				url = './fsEntryTemp_delete.action';
			}else{/*删除核算项*/
				tempGrid = this.itGrid;
				var selId = this.etGrid.getSelId();
				gdParams = {entryId:selId};
				url = './fsItemTemp_delete.action';
			}
			var id = tempGrid.getSelId();
			if(!id) return;
			ExtUtil.confirm({msg:Msg_SysTip.msg_deleteData,fn:function(btn){
				if(btn != 'yes') return;
				 EventManager.get(url,{params:{ids:id,isenabled:-1},sfn:function(json_data){
					tempGrid.reload(gdParams);
					if(tempGrid == _this.itGrid){
						var delay = _this.disAddBut(true);
						delay.delay(50);
					}
					Ext.tip.msg(Msg_SysTip.title_appconfirm, Msg_SysTip.msg_dataSucess);
				 },ffn:function(json_data){
				 	Ext.tip.msg(Msg_SysTip.title_appconfirm, Msg_SysTip.msg_dataFailure);
				 }});
			}});
		},
		/**
		 * 保存数据
		 */
		saveData : function(win){
			var _this = this;
			var cfg = {
				tb:win.apptbar,
				sfn : function(formData){
					if(win.refresh) win.refresh(formData);
					var id = formData.id;
					_this.appFrm.setFieldValue('id',id);
					_this.etTbar.enable();
				}
			};
			EventManager.frm_save(win.appFrm,cfg);
		}
	};
});
