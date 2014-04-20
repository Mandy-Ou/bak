/**
 * 添加新闻
 * @author smartplatform_auto
 * @date 2013-12-25 08:36:38
 */
define(function(require, exports) {
	exports.viewUI = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appMainPanel : null,
		title:null,
		attachMentFs:null,
		btn:null,
		bydeptDialog:null,
		byuserDialog:null,
		byroleDialog:null,
		params : null,
		pubType:{},
		parCount:{},
		setParams : function(tab, params) {
			this.tab = tab;
			this.params = params;
		},
		/**
		 * 主UI 界面开始点
		 */
		getMainUI : function(tab, params) {
			this.setParams(tab, params);
			if (!this.appMainPanel) {
				this.craeteMainPnl();
			}
			var _this = this;
			tab.notify = function(_tab) {
				_this.setParams(_tab, _tab.params);
				_this.refresh();
			};
			_this.getUrls();
			return this.appMainPanel;
		},
		/**
		 * 创建主面板
		 */
		craeteMainPnl : function() {
			var appPanel = new Ext.Panel({
				height:screen.height-300,
				autoScroll:true
			});
			this.appFrm = this.createForm();
			this.attachMentFs=this.createAttachMentFs(this);
			this.appFrm.add(this.attachMentFs);
			appPanel.add(this.appFrm);
			this.appMainPanel = appPanel;
			return this.appMainPanel;
		},
		/**
		 * 销毁组件
		 */
		destroy : function(){
			if (this.appMainPanel) {
				this.appMainPanel.destroy();
				this.appMainPanel = null;
			}
		},
		/**
		 * 获取各种URL配置
		 * addUrlCfg : 新增 URL
		 */
		getUrls : function(){
			var _this=this;
			EventManager.get(
				"./oaNews_add.action",
				{sfn : function(jsonData){
					_this.appFrm.findFieldByName("code").setValue(jsonData.code)
				}
			});	
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
		},
		/**
		 * 创建Form表单
		 */
		createForm : function(){
			var _this=this;
			var hid_id = FormUtil.getHidField({
			    fieldLabel: 'ID',
			    name: 'id',
			    "width": 120
			});
			
			var hid_procId = FormUtil.getHidField({
			    fieldLabel: '流程实例ID',
			    name: 'procId',
			    value:"-1",
			    "width": 120
			});
			
			var hid_status = FormUtil.getHidField({
			    fieldLabel: '状态',
			    name: 'status',
			    value:"1",
			    "width": 120
			});
			
			var hid_click = FormUtil.getHidField({
			    fieldLabel: '点击次数',
			    name: 'clickCount',
			    value:"0",
			    "width": 120
			});
			
			var txt_code =FormUtil.getTxtField({
				fieldLabel:"编号",
				"allowBlank": false,
				width:200,
				name:"code"
			});
			
			var cbo_columnId = FormUtil.getLCboField({
			    fieldLabel: '新闻栏目',
			    name: 'columnId',
			    "width": 120,
			    "allowBlank": false,
			    "data": [["1", "选择新闻栏目"], ["2", "图片新闻"], ["3", "视频新闻"]]
			});
			
			var txt_title = FormUtil.getTxtField({
			    fieldLabel: '新闻标题',
			    name: 'title',
			    "width": 650,
			    "allowBlank": false
			});
			
			var txt_color = FormUtil.getColorField({fieldLabel: '标题颜色', value: '#FFFFFF',name: 'color', msgTarget: 'qtip',width:120});
//			var title_btns = FormUtil.getMyCompositeField({fieldLabel: '新闻标题',allowBlank:false,itemsOne: true ,sigins:null,itemNames : 'title,color',
//		    name: 'titleCmp',width:650,items:[txt_title,{xtype:'displayfield',width:1},txt_color]});
			
			var rad_pubLimit = FormUtil.getRadioGroup({
			    fieldLabel: '发布范围',
			    "allowBlank": false,
			    name: 'pubLimit',
			    "width": 250,
			    "maxLength": 50,
			    "items": [{
			    	"id":"quan",
			        "boxLabel": "全部可见",
			        "checked":true,
			        "name": "pubLimit",
			        "inputValue": "1",
			        "listeners":{
			        	"check":function(){
			        		var quan=Ext.getCmp("quan");
			   				if(quan&&quan.checked){
			   					Ext.getCmp("renyuan").setValue("renyuan",false);
			   					Ext.getCmp("juese").setValue("juese",false);
			   					Ext.getCmp("bumen").setValue("bumen",false);
				        		chk_publishMode.disable();
			   				}
			        	}
			        }
			    },
			    {
			    	"id":"bf",
			        "boxLabel": "以下范围可见",
			        "name": "pubLimit",
			        "inputValue": "2",
			        "listeners":{
			        	"check":function(){
			        		var bf=Ext.getCmp("bf");
			   				if(bf&&bf.checked){
				        		chk_publishMode.enable();
			   				}
			        	}
			        }
			    }]
			});

			_this.parCount.byrole=2;
			_this.parCount.byuser=2;
			_this.parCount.bydept=2;
			var chk_publishMode = FormUtil.getCheckGroup({
			    fieldLabel: '发布方式',
			    name: 'publishMode',
			    "width": 250,
			    "maxLength": 50,
			    "items": [{
			    	"id":"bumen",
			        "boxLabel": "按部门",
			        "name": "bydept",
			        "inputValue": "1",
			        "listeners":{
			        	"check":function(){
			        		if(this.getValue()){
			        			_this.parCount.bydept=1;
			        		}else{
			        			_this.parCount.bydept=2;
			        		}
			        		_this.showOrHide("bumen",budeptCmp);
			        	}
			        }
			    },
			    {
			    	"id":"renyuan",
			        "boxLabel": "按人员",
			        "name": "byuser",
			        "inputValue": "1",
			        "listeners":{
			        	"check":function(){
			        		if(this.getValue()){
			        			_this.parCount.byuser=1;
			        		}else{
			        			_this.parCount.byuser=2;
			        		}
			        		_this.showOrHide("renyuan",byuserCmp);
			        	}
			        }
			    },
			    {
			    	"id":"juese",
			        "boxLabel": "按角色",
			        "name": "byrole",
			        "inputValue": "1",
			        "listeners":{
			        	"check":function(){
			        		if(this.getValue()){
			        			_this.parCount.byrole=1;
			        		}else{
			        			_this.parCount.byrole=2;
			        		}
			        		_this.showOrHide("juese",byroleCmp);
			        	}
			        }
			    }]
			});
			
			var txt_bydept = FormUtil.getTxtField({
			    fieldLabel: '按部门发布',
			    name: 'bydeptNum',
			    "width": 500,
			    "maxLength": 100
			});
			var btn_addDepart=new Ext.Button({
				text:"添加",
				name : 'adddepart',
				handler:function(){
					var structure=[
						{header : '',name : 'id',hidden : true},
						{header : '名称',name : 'name',width:80},
						{header : '编号',name : 'code',width:120},
						{header : '部门负责人',name : 'mperson',width:120}
						];
					var url="./sysDepartment_list.action";
					var params={
						structure:structure,
						url:url,
						self:txt_bydept
					};
					 _this.selectWin(params);
				}
			});
			var btn_delDepart=new Ext.Button({
				text:"清空",
				name : 'deldepart',
				handler:function(){
					txt_bydept.setValue("");
					_this.pubType.bydeptIds=null;
				}
			});
			var budeptCmp = FormUtil.getMyCompositeField({fieldLabel: '按部门发布',
			itemsOne: true ,sigins:null,itemNames : 'bydeptNum,adddepart,deldepart',
		    name: 'bydeptCmp',width:590,items:[txt_bydept,{xtype:'displayfield',width:1},
		    btn_addDepart,{xtype:'displayfield',width:1},btn_delDepart]});
			
			var txt_byuser = FormUtil.getTxtField({
			    fieldLabel: '按人员发布',
			    name: 'byuserNum',
			    "width": 500,
			    "maxLength": 100
			});
			var btn_addbyuser=new Ext.Button({
				text:"添加",
				name : 'addbyuser',
				handler:function(){
					var structure=[
						{header : '',name : 'userId',hidden : true},
						{header : '姓名',name : 'empName',width:120},
						{header : '账号',name : 'userName',width:80},
						{header : '邮箱',name : 'email',width:120}
						];
					var url="./sysUser_list.action";
					var params={
						structure:structure,
						url:url,
						self:txt_byuser
					};
					 _this.selectWin(params);
				}
			});
			var btn_delbyuser=new Ext.Button({
				text:"清空",
				name : 'delbyuser',
				handler:function(){
					txt_byuser.setValue("");
					_this.pubType.byuserIds=null;
				}
			});
			var byuserCmp = FormUtil.getMyCompositeField({fieldLabel: '按人员发布',
			itemsOne: true ,sigins:null,itemNames : 'byuserNum,addbyuser,delbyuser',
		    name: 'byuserCmp',width:590,items:[txt_byuser,{xtype:'displayfield',width:1},
		    btn_addbyuser,{xtype:'displayfield',width:1},btn_delbyuser]});
		    
			var txt_byrole = FormUtil.getTxtField({
			    fieldLabel: '按角色发布',
			    name: 'byroleNum',
			    "width": 500,
			    "maxLength": 100
			});
			var btn_addbyrole=new Ext.Button({
				text:"添加",
				name : 'addbyrole',
				handler:function(){
					var structure=[
						{header : '',name : 'id',hidden : true},
						{header : '角色名称',name : 'name',width:120},
						{header : '角色编号',name : 'code',width:80}
						];
					var url="./sysRole_list.action";
					var params={
						structure:structure,
						url:url,
						self:txt_byrole
					};
					 _this.selectWin(params);
				}
			});
			var btn_delbyrole=new Ext.Button({
				text:"清空",
				name : 'delbyrole',
				handler:function(){
					txt_byrole.setValue("");
					_this.pubType.byroleIds=null;
				}
			});
			var byroleCmp = FormUtil.getMyCompositeField({fieldLabel: '按角色发布',
			itemsOne: true ,sigins:null,itemNames : 'byrole,addbyrole,delbyrole',
		    name: 'byroleCmp',width:590,items:[txt_byrole,{xtype:'displayfield',width:1},
		    btn_addbyrole,{xtype:'displayfield',width:1},btn_delbyrole]});
			
			var bdat_pubDate = FormUtil.getDateField({
			    fieldLabel: '发布日期',
			    name: 'pubDate',
			    "width": 200,
			    "allowBlank": false
			});
			
			var int_ctype = FormUtil.getLCboField({
			    fieldLabel: '评论权限',
			    name: 'ctype',
			    "width": 120,
			    "allowBlank": false,
			    "data": [["1", "实名评论"], ["2", "匿名评论"], ["3", "禁止评论"]]
			});
	
//			var chk_remind = FormUtil.getCheckGroup({
//			    fieldLabel: '提醒',
//			    name: 'remind',
//			    "width": 250,
//			    "maxLength": 50,
//			    "items": [{
//			        "boxLabel": "发送事务提醒消息",
//			        "name": "remind",
//			        "inputValue": "1"
//			    }]
//			});
			
			
			var int_autoPub = FormUtil.getRadioGroup({
			    fieldLabel: '审批通过自动发布',
			    name: 'autoPub',
			    "width": 170,
			    "maxLength": 10,
			    "items": [{
			        "boxLabel": "是",
			        "checked":"true",
			        "name": "autoPub",
			        "inputValue": "1"
			    },
			    {
			        "boxLabel": "否",
			        "name": "autoPub",
			        "inputValue": "2"
			    }]
			});
			
			var int_istop = FormUtil.getRadioGroup({
			    fieldLabel: '是否置顶',
			    name: 'istop',
			    "width": 170,
			    "maxLength": 10,
			    "items": [{
			        "boxLabel": "是",
			        "checked":"true",
			        "name": "istop",
			        "inputValue": "1"
			    },
			    {
			        "boxLabel": "否",
			        "name": "istop",
			        "inputValue": "2"
			    }]
			});
			
			var txt_summary = FormUtil.getTxtField({
			    fieldLabel: '内容简介',
			    name: 'summary',
			    "width": 550,
			    "allowBlank": false,
			    unitText:"(最多输入30个字)",
			    "maxLength": "150"
			});
			var hid_id = FormUtil.getHidField({name:'id'});
			var hid_path = FormUtil.getHidField({name:'path'});
			var txa_content = FormUtil.getMyEditorField({
				fieldLabel: '新闻内容',
			    name: 'content',
			     height:400,
			    "allowBlank": true
			});
			/**
			 * 默认隐藏的元素
			 */
			chk_publishMode.disable();
			budeptCmp.hide();
			byuserCmp.hide();
			byroleCmp.hide();
			var layout_fields = [hid_id,hid_path,txt_title,
			{
				cmns: FormUtil.CMN_TWO,
	    		fields: [txt_code,txt_color, rad_pubLimit,cbo_columnId]
			}, chk_publishMode, budeptCmp, byuserCmp,
			byroleCmp,
			{
			    cmns: FormUtil.CMN_TWO,
			    fields: [ bdat_pubDate, int_ctype, int_istop,int_autoPub]
			}, txt_summary,txa_content,hid_click,hid_id,hid_procId,hid_status];
			var filset = FormUtil.createLayoutFieldSet({
						title : ""
					}, layout_fields);
			var frm_cfg = {
			    title: '新闻编辑',
				url : './oaNews_save.action',
				labelWidth : 120,
				items : [new Ext.Container({
					html : "<font size='6' color='blue' valign='top'><center>新闻编辑</center></font>"
				}), filset]
			}
			var applyForm = FormUtil.createFrm(frm_cfg);
			return applyForm;
		},
		/**
		 * 创建附件FieldSet 对象
		 * @return {}
		 */
		createAttachMentFs : function(_this,params){
			var dir = 'news_path';
			/* ----- 参数说明： isLoad 是否实例化后马上加载数据 
			 * dir : 附件上传后存放的目录， mortgage_path 定义在 resource.properties 资源文件中
			 * isSave : 附件上传后，是否保存到 ts_attachment 表中。 true : 保存
			 * params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔}
			 *    当 isLoad = false 时， params 可在 reload 方法中提供
			 *  ---- */
			var cfg = {title:'相关材料附件',isLoad:true,dir : dir,isSave:true,isNotDisenbaled:true};
			if(!params){
				var uuid = Cmw.getUuid();
				params = {formType:ATTACHMENT_FORMTYPE.FType_6,sysId:-1,formId : uuid,isNotDisenbaled:true};
			}
			cfg.params = params;
			var attachMentFs = new  Ext.ux.AppAttachmentFs(cfg);
			return attachMentFs;
		},
		showOrHide : function(eid,cmp){
			var ele=Ext.getCmp(eid);
			if(ele&&ele.checked){
				cmp.show();
			}else{
				cmp.hide();
			}
		},
		/**
		 * 保存方法
		 */
		saveData:function(callback){
			var _this=this;
			var cfg={
				beforeMake : function(formData){
					formData.parCount= Ext.encode(_this.parCount);
					formData.bydeptIds=_this.pubType.bydeptIds;
					formData.byuserIds=_this.pubType.byuserIds;
					formData.byroleIds=_this.pubType.byroleIds;
				},
				sfn:function(formData){
					var applyId = formData.id;
					var entityName = "NewsEntity";
					callback(applyId,entityName);
					var params = {formType:ATTACHMENT_FORMTYPE.FType_6,sysId:-1,formId : formData.id,isNotDisenbaled:true};
					_this.attachMentFs.updateTempFormId(params,true);
				}
			}
			EventManager.frm_save(_this.appFrm,cfg);		
		},
		/**
		 * 打开选择发布对象窗口
		 */
		selectWin:function(params){
			var _this=this;
			var parentCfg = {
				params : params,
				isCheck : true,
				callback : function(ids,objs) {
					_this.setFrmValue(ids,objs,params.self);
				}
			};
			var key=params.self.name;
			if(key=="bydeptNum"){
				if(_this.bydeptDialog){
						_this.bydeptDialog.show(parentCfg);	
				}
				Cmw.importPackage('pages/app/dialogbox/BydeptDialogbox', function(module) {
					_this.bydeptDialog = module.DialogBox;
					_this.bydeptDialog.show(parentCfg);
				});	
			}else if(key=="byuserNum"){
				if(_this.byuserDialog){
						_this.byuserDialog.show(parentCfg);	
				}
				Cmw.importPackage('pages/app/dialogbox/ByuserDialogbox', function(module) {
					_this.byuserDialog = module.DialogBox;
					_this.byuserDialog.show(parentCfg);
				});	
			}else{
				if(_this.byroleDialog){
						_this.byroleDialog.show(parentCfg);	
				}
				Cmw.importPackage('pages/app/dialogbox/ByroleDialogbox', function(module) {
					_this.byroleDialog = module.DialogBox;
					_this.byroleDialog.show(parentCfg);
				});	
			}
		},
		/**
		 * 给发布人文本框设置值
		 * ids:选中的所有id(以逗号隔开)
		 * objs：选中的所有对象
		 * self：文本框对象
		 */
		setFrmValue:function(ids,objs,self){
			var value="";
			for(var i=0;i<objs.length;i++){
				value+=objs[i].data.name?objs[i].data.name+",":objs[i].data.userName+",";	
			}
			self.setValue(value);
			if(self.name=="bydeptNum")
				this.pubType.bydeptIds=ids;
			if(self.name=="byuserNum")
				this.pubType.byuserIds=ids;
			if(self.name=="byroleNum")
				this.pubType.byroleIds=ids;
		}
		
	};
});
