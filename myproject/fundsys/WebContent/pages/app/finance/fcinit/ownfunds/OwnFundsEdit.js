/**
 * #DESCRIPTION#
 * @author smartplatform_auto
 * @date 2013-08-14 04:22:09
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		sysId : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.sysId = parentCfg.parent.sysId;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:350,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh,eventMgr:{saveData:this.saveData}
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
			var _this = this;
			this.appWin.show(_this.parent.getEl());
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
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fcOwnFunds_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './fcOwnFunds_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './fcOwnFunds_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fcOwnFunds_next.action',cfg :cfg};
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
			var _this = this;
			var wd = 200;
			var hid_id = FormUtil.getHidField({name:'id'});
			var txt_accountId = FormUtil.getHidField({
			    fieldLabel: '账户ID',
			    name: 'accountId',
			    "width": wd,
			    "allowBlank": false
			});
			var hid_isenabled = FormUtil.getHidField({name:'isenabled'});
			var txt_code = FormUtil.getReadTxtField({
			    fieldLabel: '编号',
			    name: 'code',
			    "width": wd,
			    "allowBlank": false,
			    "maxLength": "20"
			});
			var callback = function(cboGrid,selVals){
				var grid = cboGrid.grid;
				var record = grid.getSelRow();
				var account = record.get("account");
				txt_bankAccount.setValue(account);
			}
			var cbog_accountId = ComboxControl.getAccountCboGrid({
				fieldLabel:'银行名称',name:'accountId',
				allowBlank : false,readOnly:true,
				isAll:false,width:wd,callback:callback,
				params : {sysId :_this.sysId}});
				
			var txt_bankAccount = FormUtil.getReadTxtField({
			    fieldLabel: '银行帐号',
			    name: 'account',
			    "width": wd
			});
			
			var mon_totalAmount = FormUtil.getMoneyField({
			    fieldLabel: '总金额',
			    name: 'totalAmount',
			    "width": wd,
			    "allowBlank": false,
			    "value": "0.00"
			});
			
			
			var bdat_lastDate = FormUtil.getDateField({
			    fieldLabel: '最后存入时间',
			    name: 'lastDate',
			    "width": wd,
			    "allowBlank": false
			});
			
			/*备注*/
			var txa_remark = FormUtil.getTAreaField({fieldLabel : '备注',name:'remark',width:200,maxLength:200});
			var layout_fields = [
				hid_id,txt_accountId,hid_isenabled,txt_code,cbog_accountId,txt_bankAccount,mon_totalAmount,
				bdat_lastDate,txa_remark
			];
			var frm_cfg = {
			    title: '自由资金编辑',
			    labelWidth : 100,
			    url: './fcOwnFunds_save.action'
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
			var _this = exports.WinEdit;
			var frmVal = _this.appFrm.getValues();
			var account = frmVal.account;
			EventManager.frm_save(_this.appFrm,{beforeMake : function(formData){
				},sfn:function(data){
					if(data.zh==1){
						ExtUtil.alert({msg:"银行账号"+account+"以存在，请选择其他账号"});
						return;
					}
				_this.appWin.hide();
				_this.refresh(data);
			 }});
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
			
		}
	};
});
