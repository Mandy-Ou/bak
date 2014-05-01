/**
 * 罚息、滞纳金最低收费标准设置
 * @author smartplatform_auto
 * @date 2013-03-01 10:17:36
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
			this.appWin = new Ext.ux.window.AbsEditWindow({width:300,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh,eventMgr : {saveData:this.saveData}
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
				cfg = {params:{},defaultVal:{}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fcMinAmount_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './fcMinAmount_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './fcMinAmount_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fcMinAmount_next.action',cfg :cfg};
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
			var hid_isenabled = FormUtil.getHidField({name:'isenabled'});
			var txt_id = FormUtil.getHidField({
			    fieldLabel: 'ID',
			    name: 'id',
			    "width": "125"
			});
			
			var bdat_opdate = FormUtil.getDateField({
			    fieldLabel: '生效日期',
			    name: 'opdate',
			    "width": 125,
			    "allowBlank": false
			});
			
			var mon_mpamount = FormUtil.getMoneyField({
			    fieldLabel: '罚息',
			    name: 'mpamount',
			    "width": 90,
			    "allowBlank": false,
			    "value": "0",
			    unitText : '元'
			});
			
			var mon_moamount = FormUtil.getMoneyField({
			    fieldLabel: '滞纳金',
			    name: 'moamount',
			    "width": 90,
			    "allowBlank": false,
			    "value": "0",
			    unitText : '元'
			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '说明',
			    name: 'remark',
			    "width": 125,
			    "maxLength": 200
			});
			
			var layout_fields = [
			hid_isenabled,txt_id, bdat_opdate, mon_mpamount, mon_moamount, txa_remark];
			var frm_cfg = {
			    title: '罚息、滞纳金最低收费标准设置',
			    url: './fcMinAmount_save.action'
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
			var frm = win.appFrm;
			var id = frm.findFieldByName("id").getValue();
			var errMsg = [];
			var opdate = frm.findFieldByName("opdate").getValue();
			if(!opdate){
				errMsg[errMsg.length] = "生效日期不能为空!";
			}
			 
			var mpamount = frm.findFieldByName("mpamount").getValue();
			var moamount = frm.findFieldByName("moamount").getValue();
			if(mpamount<=0){
				errMsg[errMsg.length] = "罚息必须大于0!";
			}
			if(moamount<=0){
				errMsg[errMsg.length] = "滞纳金必须大于0!";
			}
			if(errMsg && errMsg.length > 0){
				ExtUtil.alert({msg:errMsg.join("<br/>")});
				return;
			}
			
			var params = {id:id,opdate:opdate};
			EventManager.get('./fcMinAmount_validOpdate.action',{params:params,sfn:function(json_data){
				var msg = json_data.msg;
				if(!json_data.success){
					ExtUtil.alert({msg:msg});
					return;
				}
				var cfg = {
					tb:win.apptbar,
					sfn : function(formData){
						win.resetData();
						if(win.refresh) win.refresh(formData);
						win.hide();
					}
				};
				EventManager.frm_save(win.appFrm,cfg);
			},ffn:function(data){
				var msg = data.msg;
				if(msg) ExtUtil.alert({msg:msg});
			}});
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		}
	};
});
