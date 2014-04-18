/**
 * 配置流程
 * @author smartplatform_auto
 * @date 2012-12-04 01:21:25
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		nodeCfgFrm : null,
		transGrid : null,
		mustFrmGrid : null,
		opFrmGrid : null,
		uploadWin : null,	/*流程上传窗口*/
		formDialogBox : null,/*业务表单选择窗口*/ 
		processPanel : null, /*流程图显示面板*/
		nodeNameLabeId : Ext.id(null,'nodeNameLabe'),/*当前配置节点显示的 Span ID*/
		processDivId : Ext.id(null,'processDiv'),	/*图像ID*/
		processImageId : Ext.id(null,'processImage'),	/*图像ID*/
		btnIdObj : {
			btnSaveId : Ext.id(null,'btnSave'),	/*保存配置ID*/
			btnStintId : Ext.id(null,'btnStint'),	/*启用条件限制ID*/
			btnMustAddId : Ext.id(null,'btnMustAdd'),/*添加必做业务按钮ID*/
			btnMustDelId : Ext.id(null,'btnMustDel'),/*删除必做业务按钮ID*/
			btnOpAddId : Ext.id(null,'btnOpAdd'),/*添加选做业务按钮ID*/
			btnOpDelId : Ext.id(null,'btnOpDel')/*删除选做业务按钮ID*/
		},
		nodeEleIds : [],
		toolTip : null,	/*信息提示*/
		currNodeName : null,/*当前节点名称*/
		transRowIndex : null,/*当前选中的流程走向行记录索引*/
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
			if(this.currNodeName) this.currNodeName = null;
			if(this.transRowIndex) this.transRowIndex = null;
		},
		createAppWindow : function(){
			var _this = this;
			var appPanel = this.createAppPanel();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:950,height:600,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh,maximizable : true,changeBarItems:this.changeBarItems,
			eventMgr : {saveData : function(win){_this.saveData(win);}},addComponents:function(){
				this.add(appPanel);
			},
			listeners : {'hide':function(win){
				_this.resetData(win);
			}}
			});
		},
		/**
		 * 改变工具栏按钮事项
		 */
		changeBarItems : function(barItems){
			for(var i=0,count=barItems.length; i<count; i++){
				var item = barItems[i];
				if(item.text == Btn_Cfgs.SAVE_BTN_TXT){
					item.text = Btn_Cfgs.UPLOAD_BTN_TXT;
					item.iconCls = Btn_Cfgs.UPLOAD_CLS;
					item.tooltip = Btn_Cfgs.UPLOAD_TIP_BTN_TXT;
				}
			}
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
			this.appWin.maximize();
		},
		/**
		 * 销毁组件
		 */
		destroy : function(){
			if(this.formDialogBox) this.formDialogBox.destroy();
			if(this.uploadWin) this.uploadWin.destroy();
			if(this.appWin){
				this.appWin.close();	//关闭并销毁窗口
				this.appWin = null;		//释放当前窗口对象引用
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
			var selId = parent.getSelId();
			var bussType = exports.WinEdit.parentCfg.bussType;
			/*--- 添加 URL --*/ 
			cfg = {params: {formId:selId,inType:bussType},sfn:function(formDatas){exports.WinEdit.setFormValues(formDatas);},defaultVal:{}};
			urls[URLCFG_KEYS.ADDURLCFG] = {url : './sysBussNode_get.action',cfg : cfg};
			/*--- 修改URL --*/
			urls[URLCFG_KEYS.GETURLCFG] = {url : './sysBussNode_get.action',cfg : cfg};
			
			
			var id = this.appFrm.getValueByName("formId");
			cfg = {params : {id:id,inType:bussType}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './sysBussNode_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './sysBussNode_next.action',cfg :cfg};
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
		 * 创建应用布局面板
		 */
		createAppPanel : function(){
			this.appFrm = this.createForm();
			this.nodeCfgFrm = this.createNodeCfgForm();
			var threeCmnPanel = FormUtil.getLayoutPanel([.67,.33],[this.appFrm,this.nodeCfgFrm]);
			return threeCmnPanel;
		},
		/**
		 * 创建主表单面板
		 */
		createForm : function(){
			var hid_inType = FormUtil.getHidField({fieldLabel: '所属分类',name: 'inType',value:'2'});
			var hid_formId = FormUtil.getHidField({fieldLabel: '业务ID',name: 'formId'});
			
			var hid_pdid = FormUtil.getHidField({fieldLabel: '流程定义ID',name: 'pdid'});
			
			var txt_name = FormUtil.getHidField({fieldLabel: "子业务流程名称",name: 'name'});
			
			var txt_filePath = FormUtil.getHidField({fieldLabel: '流程文件路径', name: 'filePath'});
			
			this.processPanel = this.createProcessPanel();

			var layout_fields = [hid_formId, hid_pdid, txt_name, txt_filePath,this.processPanel];
			var frm_cfg = {
			    url: './sysBussNode_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform_1;
		},
		/**
		 * 创建节点配置Form面板
		 */
		createNodeCfgForm : function(){
			var txt_nodeId = FormUtil.getHidField({
			    fieldLabel: '节点ID',
			    name: 'nodeId',
			    "width": "125"
			});
			
			/*
			var rad_transType = FormUtil.getRadioGroup({
			    fieldLabel: '流转方式',
			    name: 'transType',
			    width: 200,
			    "allowBlank": false,
			    "items": [{
			        "boxLabel": "人工流转",
			        "name": "transType",
			        "inputValue": 0,
			        checked:true
			    },
			    {
			        "boxLabel": "系统自动流转",
			        "name": "transType",
			        "inputValue": 1
			    }]
			});
			*/
			
			var rad_counterrsign = FormUtil.getRadioGroup({
			    fieldLabel: '是否会签',
			    name: 'counterrsign',
			    "width": 200,
			    "allowBlank": false,
			    "items": [{
			        "boxLabel": "否",
			        "name": "counterrsign",
			        "inputValue": 0,
			        checked:true
			    },
			    {
			        "boxLabel": "是",
			        "name": "counterrsign",
			        "inputValue": 1
			    }]
			});
			
			var rad_isTimeOut = FormUtil.getRadioGroup({
			    fieldLabel: '超时设置',
			    name: 'isTimeOut',
			    "width": 200,
			    "allowBlank": false,
			    "items": [{
			        "boxLabel": "否",
			        "name": "isTimeOut",
			        "inputValue": 0,
			        checked:true
			    },
			    {
			        "boxLabel": "是",
			        "name": "isTimeOut",
			        "inputValue": 1
			    }]
			});
			rad_isTimeOut.addListener('change',function(chkgp,checked){
				var flag = true;
				var val = chkgp.getValue();
				if(val == 1){
					flag = false;
				}
				sit_timeOut.setDisabled(flag);	
				sit_btime.setDisabled(flag);
				if(flag){
					sit_timeOut.reset();
					sit_btime.reset();
				}
			});
			
			var int_timeOut = FormUtil.getIntegerField({
			    fieldLabel: '超时时长',
			    name: 'timeOut1',
			    "width": 60
			});
			
			var cbo_timeUnit = FormUtil.getLCboField({
			    fieldLabel: '超时单位',
			    name: 'timeUnit1',
			    "width": 100,
			    data: [["M","月"],["W","周"],["D","天"],["H","小时"],["N","分钟"]]
			});
		
			var sit_timeOut = FormUtil.getMyCompositeField({
			fieldLabel:'超时时长',width:200,name:'timeOut',disabled:true,
			items : [int_timeOut,cbo_timeUnit]});
			
			var int_btime = FormUtil.getIntegerField({
			    name: 'btime1',
			    "width": 60
			});
			
			var cbo_bunit = FormUtil.getLCboField({
			    name: 'bunit1',
			    "width": 100,
			    "data": [["D","天"],["H","小时"],["N","分钟]"]]
			});
			var sit_btime = FormUtil.getMyCompositeField({
			fieldLabel:'超时提前提醒时间',width:200,name:'btime',disabled:true,
			items : [int_btime,cbo_bunit]});
		
			var chk_reminds = FormUtil.getCheckGroup({
			    fieldLabel: '提醒设置',
			    name: 'reminds',
			    "width": 200,
			    "items": [{
			        "boxLabel": "系统提醒",
			        "name": "reminds",
			        "inputValue": 1,
			        checked : true
			    },
			    {
			        "boxLabel": "短信提醒",
			        "name": "reminds",
			        "inputValue": 2
			    },
			    {
			        "boxLabel": "邮件提醒",
			        "name": "reminds",
			        "inputValue": 3
			    }]
			});
			
			this.transGrid = this.createTransGrid();
			this.mustFrmGrid = this.createMustFormGrid();
			this.opFrmGrid = this.createOpFormGrid();
			var fset_bussSet = FormUtil.createLayoutFieldSet({title:'流程业务配置',autoScroll:true},[
			 this.mustFrmGrid,this.opFrmGrid]);
				 /*rad_transType, */
			var layout_fields = [txt_nodeId, rad_counterrsign, rad_isTimeOut, sit_timeOut, sit_btime, chk_reminds,this.transGrid,fset_bussSet];
			var btnHtml = this.getButtonHml(this.btnIdObj.btnSaveId,'保存配置');
			var title = '<span id="'+this.nodeNameLabeId+'"></span>节点参数配置';
			var frm_cfg = {
			    title: title+btnHtml+Msg_SysTip.msg_formallowBlank,
			    width: 415,
			    autoScroll : true,
			    hidden : true,
			    labelWidth : 100,
			    url: './sysNodeCfg_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform_1;
		},
		createTransGrid : function(){
			var _this = this;
			var lcbo_acType = FormUtil.getLCboField({data:[["0","不需要参与者"],["1","角色"],["2","用户"],["3","上一处理人"],["4","上级领导"],["5","流程发起人"]],allowBlank : false,
				listeners:{
					change : function(cbo,newVal,oldVal){
						cbo.record.set('acType',newVal);
					}
				}});
			
			var barItems = [{type:'label',text:'名称'},{type:'txt',name:'name'}];
			var structure = [{header: '名称',name: 'name',width:150}];
			
			var showValidFn = function(cbogd){
				var record = cbogd.record;
				var acType = record.get("acType");
				if(!acType){
					ExtUtil.alert({msg:'在选择参与者之前，必须先选择参与者类型！'});
					return false;
				}
				cbogd.params={acType : acType};
				return true;
			}
			
			var callback = function(cbogd,value){
				var record = cbogd.record;
				var vals = value.split(cbogd.SIGIN);
				record.set("actorIds",vals[0]);
				record.set("actorNames",vals[1]);
			};
			var cbogd_companyName = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '参与者列表',
			    name: 'actorNames',
			    barItems : barItems,
			    structure:structure,
			    dispCmn:'name',
			    isAll:true,
			    allowBlank : true,
			    autoScroll : true,
			    url : './sysRole_ruList.action',
			    needPage : true,
			    isCheck : true,
			    showLoad : true,
			    width: 200,
			    showValidFn : showValidFn,
			    callback : callback
			});
		
			var editEles = {6:lcbo_acType,7:cbogd_companyName};
			var structure_1 = [
				{ header: '流转路径ID', name: 'transId', hidden : true},
				{ header: '参与者ID', name: 'actorIds', hidden : true},
				{ header: '启用条件限制', name: 'stint', hidden : true},
				{ header: '限制值', name: 'limitVals', hidden : true},
				{ header: '流程走向', name: 'tranName', width : 80,renderer : function(val, metaData, record){
					var stint = record.get('stint');
					if(!stint || stint=='1') return val;
					return "<span style='color:red;'>"+val+"</span>";
				}},
				{ header: '参与者类型ID', name: 'acType',hidden : true},
				{ header: '参与者类型', name: 'acTypeName',width : 80,
				  renderer : function(val){
				     	if(!val) return "";
				     	switch (val){
				     		case '0' :{
				     			val = "不需要参与者";
				     			break;
				     		}case '1' :{
				     			val = "角色";
				     			break;
				     		}case '2' :{
				     			val = "用户";
				     			break;
				     		}case '3' :{
				     			val = "上一处理人";
				     			break;
				     		}case '4' :{
				     			val = "上级领导";
				     			break;
				     		}case '5' :{
				     			val = "流程发起人";
				     			break;
				     		}
				     	}
				     	return val;
				     }
				},
				{ header: '参与者列表', name: 'actorNames', width : 180}
			];
			
			var btnHtml = this.getButtonHml(this.btnIdObj.btnStintId,'配置条件');
			
			var appgrid_1 = new Ext.ux.grid.MyEditGrid({
			    title: '参与者设置'+btnHtml+Msg_SysTip.msg_actorGridTip,
			    structure: structure_1,
			    editEles : editEles,
			    needPage: false,
			    isLoad: false,
			    autoScroll : true,
			    height:100,
			    clicksToEdit : 2,
			    keyField: 'id'
			});
			appgrid_1.addListener('rowclick',function(grid,rowIndex,e){
				_this.transRowIndex = rowIndex;
			});
			
			appgrid_1.addListener('beforeedit',function(e){
				var field  = e.field;
				if(field == 'actorNames'){
					cbogd_companyName.record = e.record;
					cbogd_companyName.row = e.row;
				}else if(field == 'acTypeName'){
					lcbo_acType.record = e.record;
					lcbo_acType.row = e.row;
				} 
			});
			appgrid_1.addListener('afteredit',function(e){
				var field  = e.field;
				if(field != 'acType') return;
				var value = e.value;
				var record = e.record;
				record.set("actorIds","");
				record.set("actorNames","");
			});
			
			return appgrid_1;
		},
		/**
		 * 必做业务事项Grid
		 */
		createMustFormGrid : function(){
			var structure_1 = [
			{header: '业务表单ID',name: 'custFormId', width: 100,hidden: true},
			{ header: '业务功能', name: 'formName', width: 100},
			{ header: '功能权限', name: 'funRights', width: 150},
			{ header: '业务顺序', name: 'orderNo', width: 60}
			];
			var orderNo = FormUtil.getIntegerField({fieldLabel : '排列顺序',name:'orderNo',value:0});
			var editEles = {3:orderNo};
			
			var btnAddHtml = this.getButtonHml(this.btnIdObj.btnMustAddId,'添加');
			var btnDelHtml = this.getButtonHml(this.btnIdObj.btnMustDelId,'删除');
			var appgrid_1 = new Ext.ux.grid.MyEditGrid({
			    title: '必做业务事项'+btnAddHtml+btnDelHtml+Msg_SysTip.msg_orderNoEdit,
			    structure: structure_1,
			    editEles : editEles,
			    needPage: false,
			    isLoad: false,
			    height : 120,
			    autoScroll : true,
			    selectType : 'check',
			    keyField: 'id'
			});
			this.addEditSortListener(appgrid_1);
			return appgrid_1;
		},
		/**
		 * 选做业务事项Grid
		 */
		createOpFormGrid : function(){
			var structure_1 = [
			{ header: '业务表单ID', name: 'custFormId', width: 100, hidden: true},
			{ header: '业务功能', name: 'formName', width: 100},
			{ header: '功能权限', name: 'funRights', width: 150},
			{ header: '业务顺序', name: 'orderNo', width: 60}
			];
			
			var orderNo = FormUtil.getIntegerField({fieldLabel : '排列顺序',name:'orderNo',value:0});
			var editEles = {3:orderNo};
			var btnAddHtml = this.getButtonHml(this.btnIdObj.btnOpAddId,'添加');
			var btnDelHtml = this.getButtonHml(this.btnIdObj.btnOpDelId,'删除');
			var appgrid_1 = new Ext.ux.grid.MyEditGrid({
			    title: '选做业务事项'+btnAddHtml+btnDelHtml+Msg_SysTip.msg_orderNoEdit,
			    structure: structure_1,
			    editEles : editEles,
			    needPage: false,
			    isLoad: false,
			    height : 150,
			    autoScroll : true,
			    selectType : 'check',
			    keyField: 'id'
			});
			var _this = this;
			appgrid_1.addListener('afterrender',function(gridpanel){
				_this.addListenersToButtons();
			});
			this.addEditSortListener(appgrid_1);
			return appgrid_1;
		},
		/**
		 * 创建流程面板
		 */
		createProcessPanel : function(){
			var processPanel = new Ext.Panel({autoScroll:true,
			title:'流程图配置<span style="color:red;">[提示：点击节点即可配置节点参数]</span>',
			html:'<div id="'+this.processDivId+'"><image id="'+this.processImageId+'"  style="position:relative; left:0px; top:0px;"></div>'});
			return processPanel;
		},
		/**
		 * 为必做业务和选做业务添加输入业务顺序后排序的事件
		 */
		addEditSortListener : function(appgrid_1){
			appgrid_1.addListener('afteredit',function(e){
				e.record.commit();
				var store = appgrid_1.getStore();
				var count = store.getCount();
				if(count==1) return;
				var records = store.getRange(0,count-1);
				var collection = new Ext.util.MixedCollection();
				collection.addAll(records);
				collection.sort('ASC',function(record1,record2){
					var orderNo1 = record1.get("orderNo");
					var orderNo2 = record2.get("orderNo");
					return orderNo1 < orderNo2 ? -1 : 1;
				});
				store.removeAll();
				var records = collection.getRange(0,collection.getCount()-1);
				store.add(records);
			});
		},
		/**
		 * 为表单元素赋值
		 */
		setFormValues : function(formDatas){
			this.removeNodeInfos();
			this.resetData();
			var pdid = formDatas.pdid;
			if(!pdid) pdid="";
			var winTitle = "配置流程&nbsp;&nbsp;&nbsp;";
			winTitle += "[当前子业务流程名称："+formDatas.name+"]";
			this.appWin.setTitle(winTitle);
			this.appFrm.setFieldValues(formDatas);
			var url = "./sysBussNode_processImage.action?pdid="+pdid;
			var processImageId = exports.WinEdit.processImageId;
			var imgEle = Ext.get(processImageId);
			imgEle.set({src:url});
			this.loadNodePositionInfo(formDatas);
		},
		/**
		 * 为 nodeNameLabeId
		 */
		setNodeCfgFormValues : function(cfgDatas){
			var name = cfgDatas.name;
			this.currNodeName = name;
			Ext.get(this.nodeNameLabeId).update("<span style='color:red;'>["+name+"]</span>&nbsp;&nbsp;");
			this.nodeCfgFrm.setFieldValues(cfgDatas);
			var nodecfgVals = cfgDatas.nodecfg;
			if(nodecfgVals){
				this.nodeCfgFrm.setFieldValues(nodecfgVals);
			}
			
			this.transGrid.removeAll();
			var trans = cfgDatas.trans;
			if(trans && trans.length > 0){
				for(var i=0,count=trans.length; i<count; i++){
					var data = trans[i];
					this.transGrid.addRecord(data);
				}
			}
			
			this.mustFrmGrid.removeAll();
			var mustFormCfgs = cfgDatas.mustFormCfgs;
			if(mustFormCfgs && mustFormCfgs.length > 0){
				for(var i=0,count=mustFormCfgs.length; i<count; i++){
					var data = mustFormCfgs[i];
					this.mustFrmGrid.addRecord(data);
				}
			}
			
			this.opFrmGrid.removeAll();
			var arrOp = cfgDatas.arrOp;
			if(arrOp && arrOp.length > 0){
				for(var i=0,count=arrOp.length; i<count; i++){
					var data = arrOp[i];
					this.opFrmGrid.addRecord(data);
				}
			}
			
			if(this.nodeCfgFrm.hidden){
				this.nodeCfgFrm.show();
				this.nodeCfgFrm.doLayout();
			}
		},
		/**
		 * 移除所有高亮节点数据
		 */
		removeNodeInfos : function(){
			if(!this.nodeEleIds || this.nodeEleIds.length == 0) return;
			for(var i=0,count=this.nodeEleIds.length; i<count; i++){
				var nodeId = this.nodeEleIds[i];
				Ext.get(nodeId).remove();
			}
			this.nodeEleIds = [];
		},
		/**
		 * 绑定事件
		 */
		addListenersToNodes : function(){
			if(!this.nodeEleIds || this.nodeEleIds.length == 0) return;
			var _this = this;
			if(!this.toolTip){
				this.toolTip = this.createNodeTip();
			}
			for(var i=0,count=this.nodeEleIds.length; i<count; i++){
				var nodeArr = this.nodeEleIds[i];
				var nodeId = nodeArr[0];
				var data = nodeArr[1];
				var ele = Ext.get(nodeId);
				ele.data = data;
				ele.addClassOnClick("nodeClickCls");
				ele.addClassOnOver("nodeOverCls");
				ele.on('click',function(e,targetEle,obj){
					if(_this.optionType != OPTION_TYPE.ADD) return;
					var ele = Ext.get(targetEle);
					Cmw.print(ele.data);
					_this.setNodeCfgFormValues(ele.data);
				},this);
				ele.on('mouseover',function(e,targetEle,obj){
					var id = targetEle.id;
					Ext.get(targetEle);
					_this.toolTip.showBy(id);
				},this);
			}
		},
		/**
		 * 为节点配置表单中的按钮绑定事件
		 */
		addListenersToButtons : function(){
			var _this = this;
			var btnSave = addClass(this.btnIdObj.btnSaveId);
			btnSave.on('click',function(e,targetEle,obj){
				clickSaveData();
			},this);
			
			var btnStint = addClass(this.btnIdObj.btnStintId);
			btnStint.on('click',function(e,targetEle,obj){
				openStintWin(btnStint);
			},this);
			
			
			var btnMustAdd = addClass(this.btnIdObj.btnMustAddId);
			btnMustAdd.on('click',function(e,targetEle,obj){
				var parentCfg = {};
				parentCfg.parent = btnMustAdd;
				parentCfg.grid = _this.mustFrmGrid;
				clickDoDtas(parentCfg);
			},this);
			
			var btnMustDel = addClass(this.btnIdObj.btnMustDelId);
			btnMustDel.on('click',function(e,targetEle,obj){
				clickDelDatas(_this.mustFrmGrid);
			},this);
			
			var btnOpAdd = addClass(this.btnIdObj.btnOpAddId);
			btnOpAdd.on('click',function(e,targetEle,obj){
				var parentCfg = {};
				parentCfg.parent = btnOpAdd;
				parentCfg.grid = _this.opFrmGrid;
				clickDoDtas(parentCfg);
			},this);
			
			var btnOpDel = addClass(this.btnIdObj.btnOpDelId);
			btnOpDel.on('click',function(e,targetEle,obj){
				clickDelDatas(_this.opFrmGrid);
			},this);
			
			/**
			 * 点击 "保存配置" 后保存数据
			 */
			function clickSaveData(){
				/**--> step 1 验证超时设置参数 <--**/
				var isTimeOut = _this.nodeCfgFrm.getValueByName("isTimeOut");
				if(isTimeOut && parseInt(isTimeOut) == 1){
					 var timeOut = _this.nodeCfgFrm.getValueByName("timeOut");
					 var btime = _this.nodeCfgFrm.getValueByName("btime");
					 if(!timeOut || !btime){
					 	ExtUtil.alert({msg:'“超时设置” 设为了“是”时，必须输入“超时时长”和“超时提前提醒时间”'});
					 	return;
					 }
					 var timeOutArr = timeOut.split(",");
					 if(timeOutArr.length==1){
					 	var timeOutVal = timeOutArr[0];
					 	var isUnit = timeOutVal.indexOf("M") ==1 || timeOutVal.indexOf("W") ==1 ||
					 	timeOutVal.indexOf("D") ==1 || timeOutVal.indexOf("H") ==1 || timeOutVal.indexOf("N") ==1 ;
					 	if(!isUnit){
					 		ExtUtil.alert({msg:'请选择“超时时长”的单位！'});
					 		return;
					 	}
					 }else{
					 	if(!timeOutArr[0] || !timeOutArr[1]){
					 		ExtUtil.alert({msg:'请输入“超时时长”并 选择时间单位！'});
					 		return;
					 	}
					 }
					 
					  var btimeArr = btime.split(",");
					 if(btimeArr.length==1){
					 	var  btimeVal = btimeArr[0];
					 	var isUnit = btimeVal.indexOf("D") ==1 ||  btimeVal.indexOf("H") ==1 ||  btimeVal.indexOf("N") ==1 ;
					 	if(!isUnit){
					 		ExtUtil.alert({msg:'请选择“超时提前提醒时间”的单位！'});
					 		return;
					 	}
					 }else{
					 	if(!btimeArr[0] || !btimeArr[1]){
					 		ExtUtil.alert({msg:'请输入“超时提前提醒时间”并 选择时间单位！'});
					 		return;
					 	}
					 }
				}
				
				/**--> step 2 验证和获取参与者设置 <--**/
				var transStore = _this.transGrid.getStore();
				var actorNull = false;
				var tranNames = [];
				var count=transStore.getCount();
				for(var i=0; i<count; i++){
					var record = transStore.getAt(i);
					var actorIds = record.get("actorIds");
					if(!actorIds){ 
						actorNull = true;
						tranNames[tranNames.length] = record.get("tranName");
					}
				}
				if(actorNull){
					 ExtUtil.confirm({title:'提示',msg:'没有为“'+tranNames.join(",")+'”流程走向设置参与者，确定保存数据？',fn:function(){
			 	  		if(arguments && arguments[0] != 'yes') return;
			 	  		saveDatas(transStore);
				 	}});
				}else{
					saveDatas(transStore);
				}
			
			}
			
			function saveDatas(transStore){
				var tranCfgs = [];
				if(transStore.getCount() > 0){
					tranCfgs = transStore.getRange(0,transStore.getCount()-1);
					for(var i=0,count=tranCfgs.length; i<count; i++){
						tranCfgs[i] = tranCfgs[i].data;
					}
				}
				
				/**--> step 3 获取必做事项业务表单数据 <--**/
				var mustStore = _this.mustFrmGrid.getStore();
				var mustFormCfgs = [];
				if(mustStore.getCount() > 0){
					mustFormCfgs = mustStore.getRange(0,mustStore.getCount()-1);
					for(var i=0,count=mustFormCfgs.length; i<count; i++){
						mustFormCfgs[i] = mustFormCfgs[i].data;
					}
				}
				
				/**--> step 4 获取选做事项业务表单数据 <--**/
				var opStore = _this.opFrmGrid.getStore();
				var opFormCfgs = [];
				if(opStore.getCount() > 0){
					opFormCfgs = opStore.getRange(0,opStore.getCount()-1);
					for(var i=0,count=opFormCfgs.length; i<count; i++){
						opFormCfgs[i] = opFormCfgs[i].data;
					}
				}
				
				var cfg = {
					tb:_this.appWin.apptbar,
					beforeMake:function(formData){
						formData.tranCfgs = Ext.encode(tranCfgs);
						formData.mustFormCfgs = Ext.encode(mustFormCfgs);
						formData.opFormCfgs = Ext.encode(opFormCfgs);
					},
					sfn : function(formData){
						//--> 刷新流程图
						var pdid = _this.appFrm.getValueByName("pdid");
						var formId = _this.appFrm.getValueByName("formId");
						var datas = {pdid:pdid,formId:formId};
						_this.loadNodePositionInfo(datas);
						_this.nodeCfgFrm.hide();
					}
				};
				EventManager.frm_save(_this.nodeCfgFrm,cfg);
			}
			
			var stitWin = null;
			/**
			 * 打开启用条件限制窗口
			 */
			function openStintWin(ele){
				var gridView = _this.transGrid.getView();
				var store =  _this.transGrid.getStore();
				var count = store.getCount();
				if(count==0 || _this.transRowIndex === null){
					ExtUtil.alert({msg:'请从下面表格中选择要启用条件的流程走向记录!'});
					return;
				}
				var row = store.getAt(_this.transRowIndex);
				var tranName = row.get("tranName");
				var stint = row.get("stint");
				var limitVals = row.get("limitVals");
				var parentCfg = {parent:_this.transGrid,stint:stint,limitVals:limitVals,tranName:tranName,currNodeName:_this.currNodeName,callback:function(formDatas){
					row = store.getAt(_this.transRowIndex);
					tranName = row.get("tranName");
					var stint = formDatas["stint"];
					var limitVals = formDatas["limitVals"];
					row.set('stint',stint);
					row.set('limitVals',limitVals);
					if(stint && stint!=1){
						tranName = "<span style='color:red;'>"+tranName+"</span>";
					}
					var cell = gridView.getCell(_this.transRowIndex,5);
					row.commit();
					row.set('tranName',tranName);
					gridView.refresh();
					return true;
				}};
				if(stitWin){
					stitWin.show(parentCfg);
				}else{
					Cmw.importPackage('/pages/app/sys/variety/TransStintEdit',function(module) {
					 	stitWin = module.WinEdit;
					 	stitWin.show(parentCfg);
			  		});
				}
			}
			
			/**
			 * 添击删除指定Grid数据
			 */
			function clickDelDatas(grid){
				var selRows = grid.getSelRows();
				if(!selRows || selRows.length == 0){
					 ExtUtil.alert({msg:Msg_SysTip.msg_selDeleteData});
					 return;
				}
				grid.removeRecords(selRows);
			}
			/**
			 * 点击添加时的数据处理
			 */
			function clickDoDtas(parentCfg){
				parentCfg.params = {sysId : _this.parentCfg.sysId,code:ACCORDION_CODE_TYPE_SUBFORM_PREFIX};
				parentCfg.callback = function(ids,txts){
					var data = {};
					var grid = parentCfg.grid;
					var idsArr = ids.split(",");
					var txtsArr = txts.split(",");
					var datas = [];
					var currIndex = -1;
					for(var i=0,count=idsArr.length;i<count;i++){
						var id = idsArr[i]+"";
						if(id.indexOf("M") != -1){
							currIndex++;
							var formName = txtsArr[i];
							var data = {custFormId:id.replace("M",""),formName:formName,funRights:[]};
							datas[currIndex] = data;
							continue;
						}
						var currData = datas[currIndex];
						currData.funRights[currData.funRights.length] = txtsArr[i];
					}
					if(null == datas || datas.length == 0) return;
					var store = grid.getStore();
					var len = store.getCount();
					if(len == 0){
						for(var i=0,count=datas.length;i<count;i++){
							var data = datas[i];
							data.funRights = data.funRights.join(",");
							grid.addRecord(data);
						}
					}else{
						for(var i=0,count=datas.length;i<count;i++){
							var data = datas[i];
							var formName = data.formName;
							var isAdd = true;
							for(var j=0; j<len; j++){
								var record = store.getAt(j);
								var _formName = record.get("formName");
								if(_formName && _formName == formName){
									isAdd = false;
									break;
								}
							}
							if(isAdd){
								data.funRights = data.funRights.join(",");
								grid.addRecord(data);
							}
						}
					}
				};
				_this.showFormDialogBox(parentCfg);
			}
			
			/**
			 * 为按钮添加点击和鼠标经过样式
			 */
			function addClass(eleId){
				var btnEle = Ext.get(eleId);
				btnEle.addClassOnClick("nodeClickCls");
				btnEle.addClassOnOver("x-btn-text");
				return btnEle;
			}
		},
		showFormDialogBox : function(parentCfg){
			parentCfg.showClearCheck = true; //当 show 时清除上次选中的节点
			if(this.formDialogBox){
				this.formDialogBox.show(parentCfg);
				return;
			}
			var _this = this;
			Cmw.importPackage('pages/app/dialogbox/BussFormDialogbox',function(module) {
			 	_this.formDialogBox = module.DialogBox;
			 	_this.formDialogBox.show(parentCfg);
			});
		},
		loadNodePositionInfo : function(formDatas){
			var _this = this;
			var inType = formDatas.inType;
			if(!inType) inType = this.parentCfg.bussType;
			var pdid = formDatas.pdid;
			if(!pdid) return;
			var params = {pdid : pdid, formId:formDatas.formId, inType:inType };
			EventManager.get('./sysBussNode_getPostion.action',{params:params,sfn:function(json_data){
				var datas = json_data.datas;
				if(null == datas || datas.length == 0) return;
				var htmlArr = [];
				for(var i=0,count=datas.length; i<count; i++){
					var data = datas[i];
					var nodeHtml = _this.getNodeHtml(data);
					if(nodeHtml) htmlArr[htmlArr.length] = nodeHtml;
				}
				Cmw.$(_this.processDivId).innerHTML += htmlArr.join("");
				_this.addListenersToNodes();
			}});
		},
		getNodeHtml : function(data){
			var x = data.x-2;
			var y = data.y-2;
			var width = data.width;
			var height = data.height;
			var nodeType = data.type;
			if(nodeType == "endEvent") return null;
			var color = (data.isCfg) ? 'blue' : 'red';
			var divId = Ext.id(null,"nodeInfo");
			this.nodeEleIds[this.nodeEleIds.length] = [divId,data];
			return '<div id="'+divId+'" style="position:absolute;border:2px solid '+color+';left:'+x+'px;top:'+y+'px;width:'+width+'px;height:'+height+'px;"></div>';
		},
		getButtonHml : function(id,text){
			var html = "&nbsp;&nbsp;<span class='btnBussNodeCls' id='"+id+"'>"+text+"</span>&nbsp;&nbsp;";
			return html;
		},
		/**
		 * 创建节点信息提示框
		 */
		createNodeTip : function(){
			var tipContentId = Ext.id(null,'tooltip_node');
			var toolTip = new Ext.ToolTip({
		       // target: 'track-tip',
		        title: 'Mouse Track',
		        width:200,
		         tipContentId : tipContentId,
		        html: '<div id='+tipContentId+'>This tip will follow the mouse while it is over the element</div>',
		        trackMouse:true,
		        dismissDelay: 15000
		    });
		    return toolTip;
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
		 * 
		 * 保存数据
		 * @param win 当前Window对象
		 */
		saveData : function(win){
			var _this = this;
			var pdid = this.appFrm.findFieldByName("pdid").getValue();
			if(pdid){
				 ExtUtil.confirm({title:'提示',msg:'业务品种已经配置了流程，重新上传会覆盖当前已配置的流程，确定上传新流程？',fn:function(){
				 	  if(arguments && arguments[0] == 'yes') _this.uploadProcess();
				 }});
			}else{
				_this.uploadProcess();
			}
		},
		/**
		 * 上传流程
		 */
		uploadProcess : function(){
			if(!this.uploadWin){
				var _this = this;
				this.uploadWin = new Ext.ux.window.MyUpWin({title:'上传流程文件',label:'请选择流程文件',width:450,dir:'process_path',
				sfn:function(fileInfos){
					_this.submitData(fileInfos);
				}});
			}
			this.uploadWin.show();
		},
		/**
		 * 提交表单数据
		 */
		submitData : function(fileInfos){
			var _this = this;
			var filePath = null;
			var size = 0;
			var custName = null;
			if(fileInfos){
				var fileInfo = fileInfos[0];
				filePath = fileInfo.serverPath;
				custName = fileInfo.custName;
				size = fileInfo.size;
			}
			this.appFrm.findFieldByName('filePath').setValue(filePath);
			var cfg = {
						tb:_this.appWin.apptbar,
						beforeMake:function(formData){
							formData.inType = _this.parentCfg.bussType;
							formData.custName = custName;
							formData.size = size; 
						},
						sfn : function(formData){
							if(_this.refresh) _this.refresh(formData);
							_this.setFormValues(formData);
						}
					};
			EventManager.frm_save(this.appFrm,cfg);
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
			if(this.nodeCfgFrm){
				this.nodeCfgFrm.reset();
				this.nodeCfgFrm.hide();
			}
			if(this.transGrid) this.transGrid.removeAll();
			if(this.mustFrmGrid) this.mustFrmGrid.removeAll();
			if(this.opFrmGrid) this.opFrmGrid.removeAll();
			this.removeNodeInfos();
			if(this.processImageId){
				var imgEle = Ext.get(this.processImageId);
				imgEle.set({src:""});
			}
		}
	};
});
