/**
 * ContractTemplateEdit
 * @author smartplatform_auto
 * @date 2013-11-20 05:02:17
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		appgrid:null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		itemWin:null,
		getCenterPnl:null,
		detailPnl:null,
		tiTbar:null,
		appWin : null,
		params : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
			this.params = parentCfg.params;
			var _this=this;
		},
		/**
		 * 模板关联列表
		 */
		getAppGrid2 : function(){
			var _this = this;
			_this.tiTbar = _this.getToolBar();
			var structure_1 = [{
			    header: '功能标识码',
			    name: 'funTag'},
				{    
				header: '关联菜单',
		    	name: 'name'
				},
				{    
				header: '说明',
		    	name: 'remark'
				}];
		 	var appgrid = new Ext.ux.grid.AppGrid({
				title:'模板关联功能列表',
				tbar:_this.tiTbar,
			    structure: structure_1,
			    url: './fcFuntempCfg_list.action',
			    needPage: false,
			    region: 'center',
			    isLoad: false,
				height:300,
			    keyField: 'id'
			});
			return appgrid;
		},
		createAppWindow : function(){
			var _this=this;
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({
			width:580,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh,
			eventMgr:{saveData:function(win){_this.saveData(win);}}
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
	 * 查询工具栏
	 */
	getToolBar : function(){
		var self = this;
		var barItems = [
			{type:"sp"},{
			token : '添加',
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:this.text,optionType:OPTION_TYPE.ADD,self:self});
			}
		},{
			token : '编辑',
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:this.text,optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{
			token : '删除',
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./fcFuntempCfg_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,disabled:true});
		toolBar.hideButtons(Btn_Cfgs.EXPORT_IMPORT_BTN_TXT);
		return toolBar;
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
			var _this=this;
			var urls = {};
			var eTdisabled = false;
			var parent = exports.WinEdit.parent;
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{},defaultVal:{isenabled:1}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fcPrintTemp_add.action',cfg : cfg};
				eTdisabled = true;     
				exports.WinEdit.appgrid.removeAll();
//				exports.WinEdit.tiTbar.setDisabled(eTdisabled);
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './fcPrintTemp_get.action',cfg : cfg};
				exports.WinEdit.appgrid.reload({tempId:selId});
				exports.WinEdit.parent.reload({id:selId});
				eTdisabled = false;
			}
			exports.WinEdit.tiTbar.setDisabled(eTdisabled);
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './fcPrintTemp_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fcPrintTemp_next.action',cfg :cfg};
			return urls;
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var _this = exports.WinEdit;
			if(_this.parentCfg.self.refresh)_this.parentCfg.self.refresh(_this.optionType,data);
			var id=_this.appFrm.getValueByName("id");
			if(id)
				_this.appgrid.reload({tempId: id });
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
			var hid_isenabled = FormUtil.getHidField({
			    fieldLabel: '可用标识',
			    name: 'isenabled',
			    "width": "125"
			});
			var hid_tempPath = FormUtil.getHidField({
			    fieldLabel: '模板路径',
			    name: 'tempPath',
			    "width": "125"
			});
			var txt_code = FormUtil.getTxtField({
			    fieldLabel: '模板编号',
			    name: 'code',
			    "width": 150,
			    "allowBlank": false,
			    "maxLength": "20"
			});
			
			var cbo_custType = FormUtil.getLCboField({
			    fieldLabel: '适用客户类型',
			    name: 'custType',
			    "width": 150,
			    "allowBlank": false,
			    "maxLength": 10,
			    "data": [["-1", "无限制"], ["0", "个人客户"], ["1", "企业客户"]]
			});
			
			var cbo_tempType = FormUtil.getLCboField({
			    fieldLabel: '模板类型',
			    name: 'tempType',
			    "width": 150,
			    "allowBlank": false,
			    "maxLength": 10,
			    "data": [ ["1", "合同模板"], ["2", "通知书模板"]]
			});
			
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '模板名称',
 			    "allowBlank": false,
			    name: 'name',
			    "width": 420,
			    "maxLength": "150"
			});
			var txt_icon = FormUtil.getMyImgChooseField({
			    fieldLabel: '模板图标',
			    width : 420,
			    name: 'icon',
			    maxLength:150
			});
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '说明',
			    name: 'remark',
			    "width": 420,
			    "maxLength": "50"
			});
			
			var layout_fields = [hid_isenabled,txt_id,hid_tempPath,{
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_code,cbo_tempType, cbo_custType]
			},
			txt_name,txt_icon,txa_remark];
			var frm_cfg = {
			    url: 'fcPrintTemp_save.action',
			    labelWidth :100
			};
			this.appgrid = this.getAppGrid2();
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			appform_1.add(this.appgrid);
			return appform_1;
		},
		globalMgr : {
			width : null,
			height :null,
			detailPanel_1 : null,
			queryFrm : null,
			appgrid2 :null,
			appgrid : null,
			activeKey : null,
			appform : null,
			activeKey : null,
			/**
			 * 当前激活的按钮文本	
			 * @type 
			 */
			msg :null,
			activeKey : null,
			winEdit : {
				show : function(parentCfg){
					var _this =  parentCfg.self;
					var winModule=null;
					var winkey=parentCfg.key;
					_this.globalMgr.activeKey = winkey;
					var parent = _this.appgrid;
					var selId=null;
					if(winkey==Btn_Cfgs.MODIFY_BTN_TXT){
						selId=parent.getSelId();
						if(!selId)return;
					}
					var id = _this.appFrm.getValueByName("id"); 
					parentCfg.sysid=_this.params.sysid;
					parentCfg.tempId=id;
					parentCfg.parent=parent;
					winModule="FuntempCfgEdit";
						Cmw.importPackage('pages/app/finance/fcinit/contract/'+winModule,function(module) {
						 	module.WinEdit.show(parentCfg);
				  		});
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
		saveData : function(win){
			exports.WinEdit.tiTbar.setDisabled(false);
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
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		}
	};
});
