/**
 * 配置流程
 * @author smartplatform_auto
 * @date 2012-12-04 01:21:25
 */
define(function(require, exports) {
	exports.WinEdit = {
		leftWidth : .67,/*左边面板宽度*/
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		accordionPnl : null,/*选项卡面板*/
		nodeCfgFrm : null,
		transMod : null,
		bussformCfgMod :  null,/*必做或选做面板*/
		uploadWin : null,	/*流程上传窗口*/
		processPanel : null, /*流程图显示面板*/
		nodeNameLabeId : Ext.id(null,'nodeNameLabe'),/*当前配置节点显示的 Span ID*/
		processDivId : Ext.id(null,'processDiv'),	/*图像ID*/
		processImageId : Ext.id(null,'processImage'),	/*图像ID*/
		btnIdObj : {
			btnSaveId : Ext.id(null,'btnSave'),	/*保存配置ID*/
			btnMustAddId : Ext.id(null,'btnMustAdd'),/*添加必做业务按钮ID*/
			btnMustDelId : Ext.id(null,'btnMustDel'),/*删除必做业务按钮ID*/
			btnOpAddId : Ext.id(null,'btnOpAdd'),/*添加选做业务按钮ID*/
			btnOpDelId : Ext.id(null,'btnOpDel')/*删除选做业务按钮ID*/
		},
		nodeEleIds : [],
		toolTip : null,	/*信息提示*/
		currNodeName : null,/*当前节点名称*/
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
			if(this.currNodeName) this.currNodeName = null;
		},
		createAppWindow : function(){
			var _this = this;
			var appPanel = this.createAppPanel();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:950,height:600,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh,maximizable : true,changeBarItems:function(barItems){ return _this.changeBarItems(barItems);},
			eventMgr : {saveData : function(win){_this.saveData(win);}},addComponents:function(){
				this.add(appPanel);
			},
			listeners : {'hide':function(win){
				_this.resetData(win);
			}}});
			_this.appWin.on('maximize',function(){
				//_this.appWin.setHeight(_this.appWin.getHeight()-50);
			})
		},
		/**
		 * 改变工具栏按钮事项
		 */
		changeBarItems : function(barItems){
			var _this = this;
			var index = -1;
			for(var i=0,count=barItems.length; i<count; i++){
				var item = barItems[i];
				if(item.text == Btn_Cfgs.SAVE_BTN_TXT){
					index = i;
					break;
				}
			}
			var btnUploadCfg = {text:Btn_Cfgs.UPLOAD_BTN_TXT,
			iconCls : Btn_Cfgs.UPLOAD_CLS,
			tooltip : Btn_Cfgs.UPLOAD_TIP_BTN_TXT,
			handler : function(){
				_this.uploadData();
			}};
			barItems.splice(index+1,0,btnUploadCfg);
			return barItems;
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
			if(this.uploadWin) this.uploadWin.destroy();
			if(this.appWin){
				this.appWin.close();	//关闭并销毁窗口
				this.appWin = null;		//释放当前窗口对象引用
			}
			if(this.transMod) this.transMod.destroy();
			if(this.bussformCfgMod) this.bussformCfgMod.destroy();
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
			this.accordionPnl = this.createAccordions();
			var rightWidth = 1 - this.leftWidth;
			var threeCmnPanel = FormUtil.getLayoutPanel([this.leftWidth,rightWidth],[this.appFrm,this.accordionPnl]);
			return threeCmnPanel;
		},
		/**
		 * 创建主表单面板
		 */
		createForm : function(){
			var hid_formId = FormUtil.getHidField({fieldLabel: '业务ID',name: 'formId'});
			
			var hid_pdid = FormUtil.getHidField({fieldLabel: '流程定义ID',name: 'pdid'});
			
			var nameFieldLabel = this.parentCfg.bussType==1 ? "品种名称" : "流程名称";
			var txt_name = FormUtil.getHidField({fieldLabel: nameFieldLabel,name: 'name'});
			
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
		 * 创建选项卡面板
		 */
		createAccordions : function(){
			var params = this.parentCfg;
			this.nodeCfgFrm = this.createNodeCfgForm();
		   var _this = this;
			var accordion = new Ext.Panel({
	            margins:'5 0 5 5',
	            height:500,
	            layout:'accordion',
	            collapseMode:'mini',
	            hidden : true,
	            items: [this.nodeCfgFrm],
	            listeners:{
	            	render : function(pnl){
	            		var bodyHeight = Ext.getBody().getHeight();
	            		var height = bodyHeight - 85;
	            		pnl.setHeight(height);
	            	}
	            }
	        });
        	this.importUI(accordion,params);
	        return accordion;
		},
		importUI : function(accordion,params){
			var _this = this;
			Cmw.importPackage('pages/app/sys/variety/TransCfgMod',function(module) {
				 	_this.transMod = module.viewUI;
				 	var mainUI =_this.transMod.getMainUI(params);
				 	accordion.add(mainUI);
				 	importBussFormCfg(params);
		  	});
		  	
		  	/**
		  	 * 导入业务配置UI模块
		  	 */
		  	function importBussFormCfg(params){
		  		Cmw.importPackage('pages/app/sys/variety/BussFormCfgMod',function(module) {
				 	_this.bussformCfgMod = module.viewUI;
				 	var mainUI =_this.bussformCfgMod.getMainUI(params);
				 	accordion.add(mainUI);
		  		});
		  	}
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
			
			var rad_transWay = FormUtil.getRadioGroup({
			    fieldLabel: '流转方式',
			    name: 'transWay',
			    "width": 200,
			    "allowBlank": false,
			    "items": [{
			        "boxLabel": "人工流转",
			        "name": "transWay",
			        "inputValue": 1,
			        checked:true
			    },
			    {
			        "boxLabel": "自动流转",
			        "name": "transWay",
			        "inputValue": 2
			    }]
			});
			
			var rad_eventType = FormUtil.getRadioGroup({
			    fieldLabel: '并行类型',
			    name: 'eventType',
			    "width": 250,
			    "allowBlank": false,
			    "items": [{
			        "boxLabel": "非并行",
			        "name": "eventType",
			        "inputValue": 1,
			        checked:true
			    },
			    {
			        "boxLabel": "分发",
			        "name": "eventType",
			        "inputValue": 2
			    },
			    {
			        "boxLabel": "汇总",
			        "name": "eventType",
			        "inputValue": 3
			    }]
			});
			
			var rad_sign = FormUtil.getRadioGroup({
			    fieldLabel: '是否允许补签',
			    name: 'sign',
			    "width": 200,
			    "allowBlank": false,
			    "items": [{
			        "boxLabel": "否",
			        "name": "sign",
			        "inputValue": 0,
			        checked:true
			    },
			    {
			        "boxLabel": "是",
			        "name": "sign",
			        "inputValue": 1
			    }]
			});
			
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
			    }],
			    listeners : {
			    	change : function(){
			    		var val = rad_counterrsign.getValue();
			    		var disabled = true;
			    		if(val == 1){
			    			disabled = false;
			    		}
			    		fst_countersign.setDisabled(disabled);
			    	}
			    }
			});
			/*-----> 会签配置 CODE STRT <-----*/
			var rad_ctype = FormUtil.getRadioGroup({
			    fieldLabel: '会签类型',
			    name: 'ctype',
			    "width": 200,
			    "allowBlank": false,
			    "items": [{
			        "boxLabel": "并行",
			        "name": "ctype",
			        "inputValue": 1,
			        checked:true
			    },
			    {
			        "boxLabel": "串行",
			        "name": "ctype",
			        "inputValue": 2
			    }]
			});
			
			var rad_pdtype = FormUtil.getRadioGroup({
			    fieldLabel: '决策方式',
			    name: 'pdtype',
			    "width": 200,
			    "allowBlank": false,
			    "items": [{
			        "boxLabel": "通过",
			        "name": "pdtype",
			        "inputValue": 1,
			        checked:true
			    },
			    {
			        "boxLabel": "拒绝",
			        "name": "pdtype",
			        "inputValue": 2
			    },
			    {
			        "boxLabel": "弃权",
			        "name": "pdtype",
			        "inputValue": 3
			    }]
			});
			
			var rad_voteType = FormUtil.getRadioGroup({
			    fieldLabel: '投票类型',
			    name: 'voteType',
			    "width": 200,
			    "allowBlank": false,
			    "items": [{
			        "boxLabel": "绝对票数",
			        "name": "voteType",
			        "inputValue": 1,
			        checked:true
			    },
			    {
			        "boxLabel": "百分比",
			        "name": "voteType",
			        "inputValue": 2
			    }]
			});
			
			var cbo_eqlogic = FormUtil.getLCboField({fieldLabel : '比较符号',name:'eqlogic',
			data:[["1","大于"],["2","大于等于"],["3","等于"],["4","小于"],["5","小于等于"]],allowBlank : false,width:70,value:"2"});
			var txt_eqval = FormUtil.getDoubleField({fieldLabel : '比较值',name:'eqval',allowBlank : false,width:30,value:"0"});
			var compt_eq = FormUtil.getMyCompositeField({fieldLabel:"票数",name:"compt_eq",allowBlank : false,itemNames:"eqlogic,eqval",sigins:null,items:[cbo_eqlogic,txt_eqval]});
			
			var cbo_transId = FormUtil.getLCboField({fieldLabel : '流转方向',name:'transId',allowBlank : false,
			data:[["--请选择--",""]],width:125});
			
			var cbo_untransId = FormUtil.getLCboField({fieldLabel : '条件不满足时流转方向',name:'untransId',allowBlank : false,
			data:[["--请选择--",""]],width:125});
			
			var cbo_transType = FormUtil.getLCboField({fieldLabel : '流转方式',name:'transType',allowBlank : false,
			data:[["1","所有参会人员给出意见后，才往下流转"],["2","只在符合会签配置条件，即可向下流转"]],width:250});
			
			var rad_acway = FormUtil.getRadioGroup({
			    fieldLabel: '参与者指定方式',
			    name: 'acway',
			    "width": 200,
			    "allowBlank": false,
			    "items": [{
			        "boxLabel": "由审批人指定",
			        "name": "acway",
			        "inputValue": 1,
			        checked:true
			    },
			    {
			        "boxLabel": "后台指定",
			        "name": "acway",
			        "inputValue": 2
			    }]
			});
			var fst_countersign = FormUtil.getFieldSet({title:"会签配置",disabled:true,
			items:[rad_ctype,rad_pdtype,rad_voteType,compt_eq,cbo_transId,cbo_untransId,cbo_transType,rad_acway]});
			/*-----> 会签配置 CODE END <-----*/
			
			/*rad_transType, */
			var layout_fields = [txt_nodeId, rad_isTimeOut, sit_timeOut, sit_btime, chk_reminds,rad_transWay,rad_eventType,rad_sign,rad_counterrsign,fst_countersign];
			var title = '<span id="'+this.nodeNameLabeId+'"></span>节点参数配置';
			var frm_cfg = {
			    title: title+Msg_SysTip.msg_formallowBlank,
			    width: 415,
			    autoScroll : true,
			    labelWidth : 100,
			    url: './sysNodeCfg_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform_1;
		},
		/**
		 * 创建流程面板
		 */
		createProcessPanel : function(){
			//var height = ;
			var _this = this;
			var processPanel = new Ext.Panel({autoScroll:true,width:800,height:500,
			title:'流程图配置<span style="color:red;">[提示：点击节点即可配置节点参数]</span>',
			html:'<div id="'+this.processDivId+'" style="width:100%;height:100%;"><image id="'+this.processImageId+'"  style="position:relative; left:0px; top:0px;"></div>',
			listeners:{
				render : function(pnl){
					var body = Ext.getBody();
					var bodyWidth = body.getWidth();
					var bodyHeight = body.getHeight();
					var height = bodyHeight - 105;
					var width = bodyWidth * _this.leftWidth - 16;
					pnl.setWidth(width);
					pnl.setHeight(height);
				}
			}});
			return processPanel;
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
			winTitle += this.parentCfg.bussType == 1 ? "[当前业务品种名称："+formDatas.name+"]" :"[当前流程名称："+formDatas.name+"]" ;
			this.appWin.setTitle(winTitle);
			this.appFrm.findFieldByName("pdid").setValue(pdid);
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
			var nodeId = cfgDatas.nodeId;
			var name = cfgDatas.name;
			this.currNodeName = name;
			Ext.get(this.nodeNameLabeId).update("<span style='color:red;'>["+name+"]</span>&nbsp;&nbsp;");
			var nodecfgDatas = {name:cfgDatas.name,nodeId:nodeId,type:cfgDatas.type};
			if(cfgDatas.isCfg){/*如果有配置记录情况下*/
				var nodecfg = cfgDatas.nodecfg;
				if(nodecfg) Ext.apply(nodecfgDatas,nodecfg);
				nodecfgDatas.nodeId = nodeId;
			}
			this.nodeCfgFrm.setVs(nodecfgDatas);
			
			var trans = cfgDatas.trans;
			this.setValue2CboTransId(trans,nodecfgDatas);
			if(this.transMod){
				this.transMod.setParams({currNodeName:this.currNodeName});
				this.transMod.refresh(trans);	
			}
			
			if(this.bussformCfgMod){
				var sysId = this.parentCfg.sysId;
				var bussType = this.parentCfg.bussType;
				var _params = {sysId:sysId,bussType:bussType};
				this.bussformCfgMod.setParams(_params);
				var mustFormCfgs = cfgDatas.mustFormCfgs;
				var arrOp = cfgDatas.arrOp;
				var formCfgDatas = {mustFormCfgs:mustFormCfgs,arrOp:arrOp};
				this.bussformCfgMod.refresh(formCfgDatas);
			}
			
			if(this.accordionPnl.hidden){
				this.accordionPnl.show();
				this.accordionPnl.doLayout();
			}
		},
		/**
		 * 为流转方向下拉框赋值
		 */
		setValue2CboTransId : function(trans,nodecfg){
			var cbo_transId = this.nodeCfgFrm.findFieldByName("transId");
			var store = cbo_transId.getStore();
			addDatas2Cbo(store);
			
			var cbo_untransId = this.nodeCfgFrm.findFieldByName("untransId");
			store = cbo_untransId.getStore();
			addDatas2Cbo(store);
			
			if(nodecfg){
				var transId = nodecfg.transId;
				var untransId = nodecfg.untransId;
				if(transId) cbo_transId.setValue(transId);
				if(untransId) cbo_untransId.setValue(untransId);
			}
			
			function addDatas2Cbo(store){
				if(store.getCount() > 0){
					store.removeAll();
				}
				var records = [];
				for(var i=0,count=trans.length; i<count; i++){
					var recId = (i+1);
					var tranCfg = trans[i];
					var transId = tranCfg.transId;
					var tranName = tranCfg.tranName;
					var defaultData = {id:transId,name:tranName};
					var record = new store.recordType(defaultData, recId);
					records[i] = record;
				}
				store.add(records);
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
		 * 保存数据
		 */
		saveData : function(){
			var _this = this;
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
			
			var counterrsign = _this.nodeCfgFrm.getValueByName("counterrsign");
			var allowBlank = (!counterrsign || parseInt(counterrsign) === 0)
			_this.nodeCfgFrm.setAllowBlanks("ctype,pdtype,voteType,eqlogic,eqval,compt_eq,transId,untransId,transType,acway",allowBlank);
			
			var transId = _this.nodeCfgFrm.getValueByName("transId");
			if((counterrsign && parseInt(counterrsign) == 1) && !transId){
				ExtUtil.alert({msg:'请选择会签配置中的"流转方向"！'});
				return;
			}
			
			/**--> step 1 获取流转路径参与者配置数据 <--**/
			var tranCfgs = _this.transMod.getValues();
			if(!tranCfgs) return;
			
			/**--> step 3 获取必做事项业务表单数据 <--**/
			var formCfgDatas = _this.bussformCfgMod.getValues();
			var mustFormCfgs = [];
			var opFormCfgs = [];
			if(formCfgDatas){
				mustFormCfgs = formCfgDatas.mustFormCfgs;
				opFormCfgs = formCfgDatas.opFormCfgs;
			}
			var cfg = {
				tb:_this.appWin.apptbar,
				beforeMake:function(formData){
					formData.tranCfgs = Ext.encode(tranCfgs);
					if(mustFormCfgs && mustFormCfgs.length>0) formData.mustFormCfgs = Ext.encode(mustFormCfgs);
					if(opFormCfgs && opFormCfgs.length>0) formData.opFormCfgs = Ext.encode(opFormCfgs);
				},
				sfn : function(formData){
					//--> 刷新流程图
					var pdid = _this.appFrm.getValueByName("pdid");
					var formId = _this.appFrm.getValueByName("formId");
					var datas = {pdid:pdid,formId:formId};
					_this.loadNodePositionInfo(datas);
					_this.accordionPnl.hide();
				}
			};
			EventManager.frm_save(_this.nodeCfgFrm,cfg);
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
		 * 
		 * 上传流程文件
		 * @param win 当前Window对象
		 */
		uploadData : function(win){
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
				//this.nodeCfgFrm.hide();
			}
			if(this.transMod) this.transMod.reset();
			if(this.bussformCfgMod) this.bussformCfgMod.reset();
			this.removeNodeInfos();
			if(this.processImageId){
				var imgEle = Ext.get(this.processImageId);
				imgEle.set({src:""});
			}
			if(this.accordionPnl) this.accordionPnl.hide();
		}
	};
});
