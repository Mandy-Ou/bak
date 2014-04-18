/**
 * 模板数据源配置表
 * @author smartplatform_auto
 * @date 2013-11-21 06:42:26
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : {},
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		appGrid:null,
		tdsId:null,
		toolBar:null,
		tempId:null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.tempId=parentCfg.tempId;
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.toolBar=this.getToolBar();
			this.appGrid=this.getAppGrid();
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:(CLIENTWIDTH+300)/2,getUrls:this.getUrls,appFrm : this.appFrm,
			eventMgr:{saveData:this.saveData},
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
			}else{
				var _this=this;
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
			var _this = exports.WinEdit;
			var urls = {};
			var parent = exports.WinEdit.parent;
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{},defaultVal:{tempId : _this.tempId}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fcTdsCfg_add.action',cfg : cfg};
				_this.appGrid.removeAll();
			}else{
				/*--- 修改URL --*/
				var selId = parent.selId;
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './fcTdsCfg_get.action',cfg : cfg};
				_this.appGrid.reload({tdsId:selId});
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './fcTdsCfg_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fcTdsCfg_next.action',cfg :cfg};
			return urls;
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var _this = exports.WinEdit;
			if(_this.parentCfg.self.refresh)_this.parentCfg.self.refresh(_this.optionType,data);	
			_this.appGrid.reload({tdsId:_this.parent.selId});
		},
		/**
		 * 创建Form表单
		 */
		createForm : function(){
			var _this=this;
			var hid_id = FormUtil.getHidField({name:'id'});
			var hid_tempId = FormUtil.getHidField({name:'tempId'});
			var txt_dstag = FormUtil.getTxtField({
			    fieldLabel: '数据源标识',
			    name: 'dstag',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "50"
			});
			
			var rad_loadType = FormUtil.getRadioGroup({
			    fieldLabel: '加载方式',
			    name: 'loadType',
			    "width": 150,
			    "allowBlank": false,
			    "maxLength": 10,
			    "items": [{
			        "boxLabel": "sql",
			        "name": "loadType",
			        "inputValue": "1"
			    },
			    {
			        "boxLabel": "hql",
			        "name": "loadType",
			        "inputValue": "2"
			    },
			    {
			        "boxLabel": "方法 ",
			        "name": "loadType",
			        "inputValue": "3"
			    }]
			});
			
			var cbo_dispType = FormUtil.getLCboField({
			    fieldLabel: '数据展示方式',
			    name: 'dispType',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": 10,
			    "data": [["1", "单条显示"], ["2", "多条显示"]]
			});
			
			var int_orderNo = FormUtil.getIntegerField({
			    fieldLabel: '数据加载顺序',
			    name: 'orderNo',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": 10
			});
			
			var txt_urlparams = FormUtil.getTxtField({
			    fieldLabel: '链接参数',
			    name: 'urlparams',
			    "width": 150,
			    "allowBlank": true,
			    "maxLength": "50"
			});
			
			var txt_relydsId = FormUtil.getMyComboxTree({
			    fieldLabel: '依赖数据源',
			    name: 'relydsId',
			    "width": 125,
				isAll : true,
			    url : './sysTree_tdsCfgList.action',
			    params : {tempId:_this.tempId,action:"2"}
			    });
			
			var txt_relyCmns = FormUtil.getTxtField({
			    fieldLabel: '依赖数据源列',
			    name: 'relyCmns',
			    "width": 150,
			    "allowBlank": true,
			    "maxLength": "150"
			});
			
			var txa_dataCode = FormUtil.getTAreaField({
			    fieldLabel: '数据代码',
			    name: 'dataCode',
			    "width": 630,
			    "allowBlank": false,
			    "maxLength": "2000"
			});
			
			var txa_htmlCode = FormUtil.getTAreaField({
			    fieldLabel: 'html代码',
			    name: 'htmlCode',
			    "width": 630,
			    height:80,
			    "maxLength": "500"
			});
			
			var layout_fields = [hid_id,hid_tempId,{
			    cmns: FormUtil.CMN_THREE,
			    fields: [txt_dstag, rad_loadType, cbo_dispType]
			},
			{
			    cmns: FormUtil.CMN_TWO,
			    fields: [int_orderNo, txt_urlparams]
			},
			{
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_relydsId, txt_relyCmns]
			},	
			txa_dataCode,txa_htmlCode];
			var frm_cfg = {
			    title: '数据源配置',
			    labelWidth:100,
			    url: './fcTdsCfg_saveOrMapping.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			appform_1.add(_this.appGrid);		
			return appform_1;
			},
		/**
		 * 可编辑表格
		 */
			getAppGrid : function(){
				var _this=this;
				var structure_1 = [{
				    header: '数据列名',
				    name: 'name',
				    width: 125
				},
				{
				    header: 'HTML列索引',
				    name: 'cellIndex',
				    width: 125
				},
				{
				    header: '数据类型',
				    name: 'dataType',
				    width: 125,
				    renderer:function(val){
				    	switch(val){
				    		case '0':
				    			val= "无";
				    			break;
				    		case '1':
				    			val="字符串"
				    			break;
				    		case '2':
				    			val= "金额";
				    			break;
				    		case '3':
				    			val="日期"
				    			break;
				    		case '4':
				    			val= "整数";
				    			break;
				    		case '5':
				    			val="浮点数"
				    			break;	
				    	}
				    		return val;
				    }
				},{
				    header: '日期格式',
				    name: 'fmt',
				    width: 125
				},
				{
				    header: '渲染函数',
				    name: 'fun',
				    width: 125
				},{
				    header: '映射列',
				    name: 'mapingCmns',
				    width: 125
				},{
				    header: '备注',
				    name: 'remark',
				    width: 125
				}, { 
					header: 'ID', 
					name: 'id', 
					hideable : true, 
					hidden : true
				},{ 
					header: '数据源ID', 
					name: 'tdsId', 
					hideable : true, 
					hidden : true},{
				   header: 'isenabled',
				   hideable : true, 
				    name: 'isenabled'
				}];
					
				var txt_name = FormUtil.getTxtField({
				    fieldLabel: '数据列名',
				    name: 'name',
				    "allowBlank": false,
				    "width": "125"
				});
				
				var int_cellIndex = FormUtil.getIntegerField({
				    fieldLabel: 'HTML列索引',
				    name: 'cellIndex',
				    "allowBlank": false,
				    "width": "125"
				});
				
				var cbo_dataType = FormUtil.getLCboField({
				    fieldLabel: '数据类型',
				    name: 'dataType',
				    "allowBlank": false,
				    "width": 125,
				    "maxLength": 50,
				  "data": [["0", "无"], ["1", "字符串"], ["2", "金额"], ["3", "日期"], ["4", "整数"], ["5", "浮点数"]]
				});
				
				var txt_fmt = FormUtil.getTxtField({
				    fieldLabel: '日期格式',
				    name: 'fmt',
				    "width": "125"
				});
				
				var txt_fun = FormUtil.getTxtField({
				    fieldLabel: '渲染函数',
				    name: 'fun',
				    "width": "125"
				});
				
				var txt_mapingCmns = FormUtil.getTxtField({
				    fieldLabel: '映射列',
				    name: 'mapingCmns',
				    "width": "125"
				});
				
				var txa_remark = FormUtil.getTAreaField({
				    fieldLabel: '备注',
				    name: 'remark',
				    "width": 380,
				    "maxLength": 200
				});
			var appgrid_1 = new Ext.ux.grid.MyEditGrid({
			    title: '数据源列配置',
			    structure: structure_1,
				 selectType :'check',
			    url: './fcCmnMapping_list.action',
			    needPage: false,
			    tbar:_this.toolBar,
			    isLoad: false,
			    height:280,
			    editEles:{0:txt_name,1:int_cellIndex,2:cbo_dataType,3:txt_fmt,4:txt_fun,5:txt_mapingCmns,6:txa_remark},
			    keyField: 'id'
			});
			return appgrid_1;
		},
	/**
	 * 工具栏
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
				self.openView({key:this.text,optionType:OPTION_TYPE.ADD,self:self});
			}
		},{
			token : '删除',
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				
				EventManager.deleteData('./fcCmnMapping_delete.action',{type:'grid',cmpt:self.appGrid,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
		return toolBar;
	},
	/**	
	 * 打开添加和编辑窗口
	 */
	openView:function(parentCfg){
		var _this=this;
		parentCfg.tempId=_this.tempId;
		parentCfg.tdsId=_this.parent.selId;
		parentCfg.appGrid=_this.appGrid;
			Cmw.importPackage('pages/app/finance/fcinit/contract/CmnMappingAdd',function(module) {
				 	module.WinEdit.show(parentCfg);
		  		});
		
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
		getBatchDatas : function(){
			var _this=this;
			var store = _this.appGrid.getStore();
			var arr = [];
			for(var i=0,count = store.getCount(); i<count; i++){
				var record = store.getAt(i);
				var id = record.id;
				var name = record.get('name');
				var cellIndex = record.get('cellIndex');
				var dataType = record.get('dataType');
				var tdsId = record.get('tdsId');
				var fmt = record.get('fmt');
				var fun = record.get('fun');
				var mapingCmns = record.get('mapingCmns');
				var remark = record.get('remark');
				arr[arr.length] = {id : id,tdsId:tdsId,cellIndex : cellIndex, name : name,dataType:dataType,fmt:fmt,fun:fun,mapingCmns:mapingCmns,remark:remark};
			}
			return arr;
		},
		/**
		 * 保存数据
		 */
		saveData : function(){
			var _this = exports.WinEdit;
			var cfg = {
				beforeMake : function(formData){
					var batchDatas = _this.getBatchDatas();
					formData.batchDatas= Ext.encode(batchDatas);
				},
				sfn : function(formData){
					_this.parent.appTree.reload({tempId:_this.tempId});
					_this.appWin.hide();
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
