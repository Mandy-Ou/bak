/**
 * 业务财务系统映射 编辑
 * @author smartplatform_auto
 * @date 2013-03-29 12:07:55
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			var _this = this;
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:380,getUrls:function(){return _this.getUrls();},appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh,eventMgr : {saveData:function(){return _this.saveData();}}
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
			var _this = this;
			var urls = {};
			var parent = exports.WinEdit.parent;
			var sysIds = this.getSysIds();
			
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{},defaultVal:{},sfn:function(){
					var cbo_sysId = _this.appFrm.findFieldByName('sysId');
					cbo_sysId.reload({sysIds:sysIds});
				}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fsBussFinCfg_add.action',cfg : cfg};
			}
			return urls;
		},
		getSysIds : function(){
			var sysIds = null;
			var parent = exports.WinEdit.parent;
			var store = parent.getStore();
			if(store && store.getCount()>0){
				sysIds = [];
				for(var i=0,count=store.getCount(); i<count; i++){
					var record = store.getAt(i);
					var sysId = record.get('sysId');
					if(sysId) sysIds[sysIds.length] = sysId;
				}
			}
			return (sysIds && sysIds.length>0) ? sysIds.join() : null;
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
			var sysIds = this.getSysIds();
			var rcbo_sysId = FormUtil.getRCboField({
			    fieldLabel: '业务系统',
			    name: 'sysId',
			    "width": 200,
			    "allowBlank": false,
			    url : './fsBussFinCfg_cbodatas.action?restypeId=1',
			    params : {sysIds:sysIds}
			});
			
			var rcbo_finsysId = FormUtil.getRCboField({
			    fieldLabel: '财务系统',
			    name: 'finsysId',
			    "width": 200,
			    "allowBlank": false,
			    url : './fsBussFinCfg_cbodatas.action?restypeId=0'
			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备济',
			    name: 'remark',
			    "width": 200,
			    "maxLength": 150
			});
			
			var layout_fields = [
			txt_id, rcbo_sysId, rcbo_finsysId, txa_remark];
			var frm_cfg = {
			    title: '业务财务系统映射信息编辑',
			    url: './fsBussFinCfg_save.action'
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
		saveData : function(){
			var _this = this
			EventManager.frm_save(_this.appFrm,{sfn:function(data){
				_this.refresh(data);
				_this.appWin.hide();
			 },ffn:function(data){
			 	ExtUtil.alert({msg:Fs_Msg_SysTip.msg_sysidneedonly});
			 }});
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		}
	};
});
