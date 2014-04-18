/**
 * fcFuntempCfg
 * @author smartplatform_auto
 * @date 2013-11-21 12:02:14
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : {},
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		sid:null,
		appTree:null,
		tempId:null,
		setParentCfg:function(parentCfg){
			this.tempId=parentCfg.tempId;
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.appTree=this.getAppTree();
			this.appFrm=this.createForm();
			var _this=exports.WinEdit;
			this.appWin = new Ext.ux.window.AbsEditWindow({width:(CLIENTWIDTH+520)/2,height : 600,appFrm:this.appFrm,getUrls:this.getUrls,
			optionType : this.optionType, refresh : this.refresh,title:"配置模板",refresh : this.refresh,
						eventMgr:{saveData:function(win){_this.saveData(win);}}

			});
			this.appWin.add(this.getCenterPnl());
		},
		getCenterPnl : function(){
			var _this = this;
			var centerPnl = new Ext.Panel({
				layout:'border',width : CLIENTWIDTH-130,height :535,border : false,
				items :[
						_this.getPanel(),_this.getPanel2()
						]
			});
			return centerPnl;
		},
		/**
		 * 创建Form表单
		 */
		createForm : function(){
			var hid_id = FormUtil.getHidField({name:'id'});
			var hid_txtPath = FormUtil.getHidField({name:'txtPath'});
			var txa_content = FormUtil.getMyEditorField({
			    fieldLabel: '',
			    name: 'content',
			     height:550,
			    "allowBlank": true
			});
			
			var layout_fields = [hid_id,hid_txtPath,txa_content];
			var frm_cfg = {
			    labelWidth:5,
			    url: './fcPrintTemp_saveClause.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform_1;
		},
		/**
		 * 左部数据源面板
		 */
		getPanel:function(){
			var _this=this;
			var p=new Ext.Panel({
				tbar:_this.getToolBar(),
				title : '数据源列表',
			     width : 200,
			     region: 'west',
			    height : 600
			});
			p.add(_this.appTree);
			return p;
		},
		/**
		 * 右部编辑器面板
		 */
		getPanel2:function(){
			var _this=this;
			var p2=new Ext.Panel({
			     width : (CLIENTWIDTH+150)/2,
			     region: 'center',
			    height : 450,
			    items:[_this.appFrm]
			});
			return p2;
		},
		/**
		 * 打开添加和编辑窗口
		 */
		openView:function(parentCfg){
			var _this=this;
			parentCfg.tempId=_this.tempId;
			var parent={};
			var selId=_this.sid;
			if(parentCfg.key==Btn_Cfgs.MODIFY_BTN_TXT){
				if(!selId){
					ExtUtil.alert({msg:"请选择要编辑的数据源！"});
					return;
				}
				if(selId.indexOf('D') == -1){
					ExtUtil.alert({msg:"您选择的不是数据源，请选择上一层节点！"});
					return ;
				}
				parent.selId=selId.substring(1);
			}
				parent.appTree=_this.appTree;
				parentCfg.parent=parent;
			if(!parentCfg.tempId)return;
				Cmw.importPackage('pages/app/finance/fcinit/contract/TdsCfgEdit',function(module) {
					 	module.WinEdit.show(parentCfg);
			  		});
			
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
					self.openView({key:this.text,optionType:OPTION_TYPE.ADD,self:self});
				}
			},{
				token : '编辑',
				text : Btn_Cfgs.MODIFY_BTN_TXT,
				iconCls:'page_edit',
				tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
				handler : function(){
					self.openView({key:this.text,optionType:OPTION_TYPE.EDIT,self:self});
				}
			},{
				token : '删除',
				text : Btn_Cfgs.DELETE_BTN_TXT,
				iconCls:'page_delete',
				tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
				handler : function(){
					EventManager.deleteData('./fcTdsCfg_delete.action',{type:'tree',cmpt:self.appTree,self:self});
				}
			}];
			var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
			toolBar.hideButtons(Btn_Cfgs.EXPORT_IMPORT_BTN_TXT);
			return toolBar;
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
					var self=this;
					var params={};
					params.tempId=self.tempId;
					this.appTree.reload(params);
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
				var _this = exports.WinEdit;
				var parent = exports.WinEdit.parent;
				var cfg = null;
				// 1 : 新增 , 2:修改
				if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
					/*--- 添加 URL --*/	
					var selId = parent.getSelId();
					cfg = {params:{id:selId,count:"id"},defaultVal:{id:selId}};
					urls[URLCFG_KEYS.ADDURLCFG] = {url : './fcPrintTemp_getClause.action',cfg : cfg};
				}else{
					/*--- 修改URL --*/
					var selId = parent.getSelId();
					cfg = {params : {id:selId}};
					urls[URLCFG_KEYS.GETURLCFG] = {url : './fcFuntempCfg_get.action',cfg : cfg};
				}
				var id = this.appFrm.getValueByName("id");
				cfg = {params : {id:id}};
				/*--- 上一条 URL --*/
				urls[URLCFG_KEYS.PREURLCFG] = {url : './fcFuntempCfg_prev.action',cfg :cfg};
				/*--- 下一条 URL --*/
				urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fcFuntempCfg_next.action',cfg :cfg};
				return urls;
			},
		/**
		 *  获取 TreePanel 对象
		 */
		getAppTree : function(){
			var self = this;
			var _apptree = new Ext.ux.tree.MyTree({
			url:'./sysTree_tdsCfgList.action',
			isLoad : true,
			height:580,
			width:200,
			enableDD:false,
			autoScroll :true,
			params:{tempId:self.tempId}
			});
			_apptree.expandAll(); 
			_apptree.addListener('click',function(node){
				var id = node.id;
				self.sid=id;
				var departId = -1;
				if(id.indexOf('D') != -1){
					departId = id.substring(1);
				}
			});
			return _apptree;
		},
			/**
			 * 当数据保存成功后，执行刷新方法
			 * [如果父页面存在，则调用父页面的刷新方法]
			 */
			refresh : function(data){
				var _this = exports.WinEdit;
				if(_this.parentCfg.self.refresh)_this.parentCfg.self.refresh(_this.optionType,data);	
				this.appTree.reload({tempId:_this.tempId});
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
//				exports.WinEdit.tiTbar.setDisabled(false);
				var _this = this;
				var cfg = {
//					tb:win.apptbar,
					sfn : function(formData){
						win.hide();
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
