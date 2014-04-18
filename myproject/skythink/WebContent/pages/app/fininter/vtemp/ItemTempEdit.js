/**
 * 核算项信息编辑窗口
 * @author smartplatform_auto
 * @date 2013-04-10 02:52:36
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		finsysId : null,
		entryId : null,
		self : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
			this.finsysId = parentCfg.finsysId;
			this.entryId = parentCfg.entryId;
			this.self = parentCfg.self;
		},
		createAppWindow : function(){
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:400,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh ,eventMgr:{saveData:this.saveData}
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
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{},defaultVal:{entryId:exports.WinEdit.entryId}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fsItemTemp_add.action',cfg : cfg};
				var cbog_fieldIds = exports.WinEdit.appFrm.findFieldByName('fieldIds');
				cbog_fieldIds.params =  {bussObjectId:-1};
				cbog_fieldIds.disable();
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId},sfn:function(json_data){
					var bussObjectId = json_data.bussObjectId;
					var cbog_fieldIds = exports.WinEdit.appFrm.findFieldByName('fieldIds');
					cbog_fieldIds.params =  {bussObjectId:bussObjectId};
					cbog_fieldIds.enable();
					cbog_fieldIds.reload();
				}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './fsItemTemp_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './fsItemTemp_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fsItemTemp_next.action',cfg :cfg};
			return urls;
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var parent = exports.WinEdit.parent;
			var selId = exports.WinEdit.entryId;
			parent.reload({entryId:selId});
		},
		/**
		 * 创建Form表单
		 */
		createForm : function(){
			var cbog_itemClass = ComboxControl.getItemClassCboGrid({
				fieldLabel:'核算项',name:'itemClassId',
				allowBlank: false,width: 250,
				params:{finsysId:this.finsysId}
			});
			
			var rcbo_bussObjectId = FormUtil.getRCboField({
			    fieldLabel: '核算业务对象',
			    name: 'bussObjectId',
			    "allowBlank": false,
			    url:'./fsFinBussObject_cbodatas.action?finsysId='+this.finsysId,
			    "width": 250
			});
			rcbo_bussObjectId.addListener('change',function(field,newVal,oldVal){
				cbog_fieldIds.enable();
				cbog_fieldIds.params={bussObjectId:newVal};
				cbog_fieldIds.reload();
			});
			
			var cbog_fieldIds = ComboxControl.getFinCustFieldsCboGrid({
			    fieldLabel: '核算自定义字段',
			    name: 'fieldIds',
			    "width": 250,
			    "allowBlank": false,
			    "maxLength": "60",
			    disabled:true,
			    params : {bussObjectId:-1}
			});
			
			var txt_entryId = FormUtil.getHidField({
			    fieldLabel: '分录模板ID',
			    name: 'entryId',
			    "width": 125,
			    "allowBlank": false
			});
			
			var txt_id = FormUtil.getHidField({
			    fieldLabel: 'ID',
			    name: 'id',
			    "width": "125"
			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 250,
			    "maxLength": 200
			});
			
			var layout_fields = [
			cbog_itemClass, rcbo_bussObjectId, cbog_fieldIds, txt_entryId, txt_id, txa_remark];
			var frm_cfg = {
			    title: '核算项信息编辑',
			    url: './fsItemTemp_save.action',
			    labelWidth:100
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform_1;
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
			var _this = exports.WinEdit;
			var cfg = {
				sfn : function(formData){
					_this.refresh(formData);
					if(_this.optionType == OPTION_TYPE.ADD){
						var delay = _this.self.itTbar.disAddBut(false);
						delay.delay(50);
					}
					win.hide();
				},ffn:function(){
					ExtUtil.alert({msg:Fs_Msg_SysTip.msg_entryIdneedonly});
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
