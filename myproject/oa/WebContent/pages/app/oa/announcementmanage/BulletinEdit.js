/**
 * 添加公告
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
		 * editUrlCfg : 修改URL
		 * preUrlCfg : 上一条URL
		 * preUrlCfg : 下一条URL
		 */
		getUrls : function(){
			
			var _this=this;
			EventManager.get(
				"./oaBulletin_add.action",
				{sfn : function(jsonData){
					_this.appFrm.findFieldByName("code").setValue(jsonData.code)
				}
			});	
		
//			var urls = {};
//			var _this=exports.WinEdit;
//			var parent = exports.WinEdit.parent;
//			var cfg = null;
//				/*--- 添加 URL --*/	
//				cfg = {params:{},defaultVal:{title:_this.title}};
//				urls[URLCFG_KEYS.ADDURLCFG] = {url : './oaBulletin_add.action',cfg : cfg};
//			var id = _this.appFrm.getValueByName("id");
//			cfg = {params : {id:id}};
//			/*--- 上一条 URL --*/
//			urls[URLCFG_KEYS.PREURLCFG] = {url : './oaBulletin_prev.action',cfg :cfg};
//			/*--- 下一条 URL --*/
//			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './oaBulletin_next.action',cfg :cfg};
//			return urls;
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
			
			var txt_code =FormUtil.getTxtField({
				fieldLabel:"编号",
				"allowBlank": false,
				width:200,
				name:"code"
			});
			
			var txt_title = FormUtil.getTxtField({
			    fieldLabel: '公告标题',
			    name: 'title',
			    "width": 650,
			    "allowBlank": false
			});
			
			var txt_color = FormUtil.getColorField({fieldLabel: '标题颜色', value: '#FFFFFF',name: 'color', msgTarget: 'qtip',width:130});
//			var title_btns = FormUtil.getMyCompositeField({fieldLabel: '公告标题',allowBlank:false,itemsOne: true ,sigins:null,itemNames : 'title,color',
//		    name: 'titleCmp',width:650,items:[txt_title,{xtype:'displayfield',width:1},txt_color]});
			
			var cbo_columnId = FormUtil.getLCboField({
			    fieldLabel: '公告类型',
			    name: 'btype',
			    "width": 130,
			    "allowBlank": false,
			    "data": [["1", "行政公告"], ["2", "人事公告"]]
			});
			
			var rad_pubLimit = FormUtil.getRadioGroup({
			    fieldLabel: '发布范围',
			    "allowBlank": false,
			    name: 'pubLimit',
			    "width": 250,
			    "maxLength": 50,
			    "items": [{
			    	"id":"quan2",
			        "boxLabel": "全部可见",
			        "checked":true,
			        "name": "pubLimit",
			        "inputValue": "1",
			        "listeners":{
			        	"check":function(){
			        		var quan=Ext.getCmp("quan2");
			   				if(quan&&quan.checked){
			   					Ext.getCmp("renyuan2").setValue("renyuan2",false);
			   					Ext.getCmp("juese2").setValue("juese2",false);
			   					Ext.getCmp("bumen2").setValue("bumen2",false);
				        		chk_publishMode.disable();
			   				}
			        	}
			        }
			    },
			    {
			    	"id":"bf2",
			        "boxLabel": "以下范围可见",
			        "name": "pubLimit",
			        "inputValue": "2",
			        "listeners":{
			        	"check":function(){
			        		var bf=Ext.getCmp("bf2");
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
			    	"id":"bumen2",
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
			        		_this.showOrHide("bumen2",budeptCmp);
			        	}
			        }
			    },
			    {
			    	"id":"renyuan2",
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
			        		_this.showOrHide("renyuan2",byuserCmp);
			        	}
			        }
			    },
			    {
			    	"id":"juese2",
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
			        		_this.showOrHide("juese2",byroleCmp);
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
						{header : '账号',name : 'userName',width:80},
						{header : '姓名',name : 'empName',width:120},
						{header : '性别',name : 'sex',width:120},
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
						{header : '角色编号',name : 'code',width:80},
						{header : '角色名称',name : 'name',width:120}
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
			
			var txt_startDate = FormUtil.getDateField({name:'startDate',width:125,"allowBlank": false});
			var txt_endDate = FormUtil.getDateField({name:'endDate',width:125});
			var comp_startDate = FormUtil.getMyCompositeField({
				itemNames : 'startDate,endDate',
				sigins : null,
				 fieldLabel: '有效期',width:510,sigins:null,
				 name:'comp_estartDate',
				 items : [txt_startDate,{xtype:'displayfield',value:'至'},txt_endDate,{xtype:'displayfield',
				 		value:'<span style="color:red">截止日期为空需要手动终止</span>',width:200}]
			});
			
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
			
			var hid_id = FormUtil.getHidField({name:'id'});
			var hid_path = FormUtil.getHidField({name:'path'});
			var txa_content = FormUtil.getMyEditorField({
				fieldLabel: '公告内容',
			    name: 'content',
			     height:400,
			    "allowBlank": true
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
			/**
			 * 默认隐藏的元素
			 */
			chk_publishMode.disable();
			budeptCmp.hide();
			byuserCmp.hide();
			byroleCmp.hide();
			var layout_fields = [hid_id,hid_path,hid_procId,hid_status,txt_title,
			{
				cmns: FormUtil.CMN_TWO,
	    		fields: [txt_code,txt_color, rad_pubLimit,cbo_columnId]
			}, chk_publishMode, budeptCmp, byuserCmp,
			byroleCmp,comp_startDate,
			{
			    cmns: FormUtil.CMN_TWO,
			    fields: [int_autoPub, int_istop]
			},txa_content];
			var filset = FormUtil.createLayoutFieldSet({
						title : ""
					}, layout_fields);
			var frm_cfg = {
			    title: '公告编辑',
				height : true,
				url : './oaBulletin_save.action',
				labelWidth : 120,
				items : [new Ext.Container({
					html : "<font size='6' color='blue' valign='top'><center>公告编辑</center></font>"
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
			var dir = 'bulletin_path';
			/* ----- 参数说明： isLoad 是否实例化后马上加载数据 
			 * dir : 附件上传后存放的目录， mortgage_path 定义在 resource.properties 资源文件中
			 * isSave : 附件上传后，是否保存到 ts_attachment 表中。 true : 保存
			 * params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔}
			 *    当 isLoad = false 时， params 可在 reload 方法中提供
			 *  ---- */
			var cfg = {title:'相关材料附件',isLoad:true,dir : dir,isSave:true,isNotDisenbaled:true};
			if(!params){
				var uuid = Cmw.getUuid();
				params = {sysId:-1,formType:ATTACHMENT_FORMTYPE.FType_5,formId : uuid,isNotDisenbaled:true};
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
		 * 保存数据
		 */
		saveData : function(callback){
			var _this=this;
			var cfg={
				beforeMake : function(formData){
					formData.parCount= Ext.encode(_this.parCount);
					formData.bydeptIds=_this.pubType.bydeptIds;
					formData.byuserIds=_this.pubType.byuserIds;
					formData.byroleIds=_this.pubType.byroleIds;
				},
				sfn:function(jsonData){
					var applyId = jsonData.id;
					var entityName = "BulletinEntity";
					callback(applyId,entityName);
					var params = {formType:ATTACHMENT_FORMTYPE.FType_5,sysId:-1,formId : jsonData.id,isNotDisenbaled:true};
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
