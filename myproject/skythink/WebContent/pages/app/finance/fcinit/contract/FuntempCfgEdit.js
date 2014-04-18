/**
 * fcFuntempCfg
 * @author smartplatform_auto
 * @date 2013-11-21 12:02:14
 */
 
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		sysid:null,
		tempId:null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.tempId=parentCfg.tempId;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
			this.sysid=parentCfg.sysid;
		},
		createAppWindow : function(){
			var _this=this;
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:350,getUrls:this.getUrls,appFrm : this.appFrm,
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
			var sysid = exports.WinEdit.sysid;
			var cfg = null;
			var _this=exports.WinEdit;
			var setValuesFn = function(formDatas){exports.WinEdit.setFormValues(formDatas);};
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/
				cfg = {params:{},sfn:setValuesFn,defaultVal:{sysId:sysid,tempId:_this.tempId}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fcFuntempCfg_add.action',cfg : cfg};
//				exports.WinEdit.parent.removeAll();
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId},sysid:sysid,sfn:setValuesFn,defaultVal:{}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './fcFuntempCfg_get.action',cfg : cfg};
//				exports.WinEdit.parent.reload({tempId:selId});
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id},sfn:function(formDatas){exports.WinEdit.setFormValues(formDatas);}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './fcFuntempCfg_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fcFuntempCfg_next.action',cfg :cfg};
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
			var _this=this;
			var tempId=_this.parentCfg.tempId;
			var hid_bussType = FormUtil.getHidField({name:'bussType',value:1});
			var hid_tempId=FormUtil.getHidField({name:"tempId"});
			var hid_id = FormUtil.getHidField({
			    fieldLabel: 'id',
			    name: 'id'
			});
			
			var hid_sysId = FormUtil.getHidField({
			    fieldLabel: '系统Id',
			    name: 'sysId'
			});
			var txt_funTag = FormUtil.getTxtField({
		    fieldLabel: '功能标识码',
		    name: 'funTag',
		    "width": 200,
		    "maxLength": "50"
			});
			var txt_name = FormUtil.getMyComboxTree({fieldLabel : '关联功能',name:'menuId',
				url:'./sysTree_list.action',isAll:true,width:200,height:350});
			var txa_remark = FormUtil.getTAreaField({
				    fieldLabel: '备注',
				    name: 'remark',
				    "width": 200,
				    "maxLength": "50"
				});
			
			var layout_fields = [hid_id,hid_sysId,hid_tempId,hid_bussType,
			 txt_name,txt_funTag, txa_remark];
			var frm_cfg = {
			    title: '模板关联功能编辑',
			    url: './fcFuntempCfg_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
				return appform_1;
					},
		/**
		 * 为表单元素赋值
		 */
		setFormValues : function(formDatas){
			var sysid = exports.WinEdit.sysid;
			var txt_menu = this.appFrm.findFieldByName('menuId');
			txt_menu.setParams({action:1,sysid :sysid});
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
			var _this = this;
			var f=_this.appFrm.getValues();
			var cfg = {
				tb:win.apptbar,
				sfn : function(formData){
					_this.parent.reload({tempId:_this.parentCfg.tempId});
					win.hide();
				}
			};
			if(!f.menuId&&!f.funTag){
				Ext.Msg.alert("提示","关联功能、功能标识码必须有一项不为空");
				return;
			}else{
				EventManager.frm_save(win.appFrm,cfg);
			}
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		}
	};
});
