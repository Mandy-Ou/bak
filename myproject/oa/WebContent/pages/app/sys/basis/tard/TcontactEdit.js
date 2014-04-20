/**
 * 分区联系人
 * @author smartplatform_auto
 * @date 2013-12-16 09:38:04
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
			this.appWin = new Ext.ux.window.AbsEditWindow({width:520,getUrls:this.getUrls,appFrm : this.appFrm,
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
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './oaTcontact_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './oaTcontact_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './oaTcontact_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './oaTcontact_next.action',cfg :cfg};
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
		
			var hid_id=FormUtil.getHidField({
				fielLabel:"ID",
				name:"id"
			});
			var hid_tradAreaId=FormUtil.getHidField({
				fielLabel:"商系分区id",
				name:"tradAreaId"
			});
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '姓名',
			    name: 'name',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "20"
			});
			
			var cbo_sex = FormUtil.getLCboField({
			    fieldLabel: '性别',
			    name: 'sex',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": 10,
			    "data": [["-1", "未知"], ["1", "男"], ["2", "女"]]
			});
			
			var txt_job = FormUtil.getTxtField({
			    fieldLabel: '职位',
			    name: 'job',
			    "width": 125,
			    "maxLength": "50"
			});
			
			var txt_phone = FormUtil.getTxtField({
			    fieldLabel: '手机',
			    name: 'phone',
			    "width": 125,
			    "maxLength": "20"
			});
			
			var txt_tel = FormUtil.getTxtField({
			    fieldLabel: '电话',
			    name: 'tel',
			    "width": 125,
			    "maxLength": "20"
			});
			
			var txt_email = FormUtil.getTxtField({
			    fieldLabel: 'Email',
			    name: 'email',
			    "width": 125,
			    "maxLength": "50"
			});
			
			var txt_id = FormUtil.getHidField({
			    fieldLabel: 'ID',
			    name: 'id',
			    "width": "125"
			});
			
			var txt_isenabled = FormUtil.getHidField({
			    fieldLabel: '可用标识',
			    name: 'isenabled',
			    "width": "125"
			});
			
			var txt_tradAreaId = FormUtil.getHidField({
			    fieldLabel: '商系分区ID',
			    name: 'tradAreaId'
			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 365,
			    "maxLength": 200
			});
			
			var layout_fields = [{
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_name, cbo_sex, txt_job, txt_phone, txt_tel, txt_email]
			},
			txt_id, txt_isenabled, txt_tradAreaId, txa_remark,hid_tradAreaId,hid_id];
			var frm_cfg = {
			    title: '分区联系人',
			    url: './oaTcontact_save.action'
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
