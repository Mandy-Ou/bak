/**
 * 费用项
 * @author smartplatform_auto
 * @date 2013-12-16 06:48:07
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
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:350,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh,
			eventMgr:{saveData:this.saveData}
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
			var _this=exports.WinEdit;
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{},defaultVal:{tradAreaId:_this.parentCfg.tradAreaId}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './oaPitem_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './oaPitem_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './oaPitem_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './oaPitem_next.action',cfg :cfg};
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
		
			var txt_id = FormUtil.getHidField({
			    fieldLabel: 'ID',
			    name: 'id',
			    "width": "125"
			});
			
			var txt_tradAreaId = FormUtil.getHidField({
			    fieldLabel: '商系分区ID',
			    name: 'tradAreaId'
			});
			
			var txt_isenabled = FormUtil.getHidField({
			    fieldLabel: '可用标识',
			    name: 'isenabled',
			    "width": "125"
			});
			
			var int_dyear = FormUtil.getIntegerField({
			    fieldLabel: '费用年份',
			    name: 'dyear',
			    "width": 180,
			    "allowBlank": false,
			    "maxLength": 10
			});
			
			var cbo_itemId = FormUtil.getLCboField({
			    fieldLabel: '费用项',
			    name: 'itemId',
			    "width": 180,
			    "allowBlank": false,
			    "data": [["1", "水费"], ["2", "电费"]]
			});
			
			var mon_amount = FormUtil.getMoneyField({
			    fieldLabel: '费用金额',
			    name: 'amount',
			    "width": 180,
			    "allowBlank": false
			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 180,
			    "maxLength": 200
			});
			
			var layout_fields = [
			txt_id, txt_tradAreaId, txt_isenabled, int_dyear, cbo_itemId, mon_amount, txa_remark];
			var frm_cfg = {
			    title: '费用项',
			    url: './oaPitem_save.action'
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
			var f=_this.appFrm.getValues();
			var cfg = {
				tb:win.apptbar,
				sfn : function(formData){
					_this.parent.reload({tradAreaId:_this.parentCfg.tradAreaId});
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
