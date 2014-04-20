/**
 * #DESCRIPTION#
 * @author smartplatform_auto
 * @date 2012-12-29 03:36:33
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		params : null,
		setParentCfg:function(parentCfg){
			this.params = parentCfg.params;
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:400,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh,changeBarItems:this.changeBarItems
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
			var _this = exports.WinEdit;
			var urls = {};
			var parent = exports.WinEdit.parent;
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.EDIT){ //--> 新增
//				/*--- 添加 URL --*/	
				var _this = exports.WinEdit;
				var selId = parent.getSelId();
				var id = selId.substr(1);
				cfg = {params : {id:id},defaultVal:{sysId:_this.params.sysId},sfn:function(json_data){
					_this.setOldTempValues(json_data);
				}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './sysMatTemp_get.action',cfg : cfg};
			}
			this.apptbar.hideButtons(Btn_Cfgs.PREVIOUS_BTN_TXT +","+ Btn_Cfgs.NEXT_BTN_TXT);
			return urls;
		},
		setOldTempValues : function(json_data){
		    this.appFrm.reset();
		    var txt_id = this.appFrm.findFieldByName("id");
			txt_id.setValue(json_data["id"]);
			var txt_oldName = this.appFrm.findFieldByName("oldName");
			txt_oldName.setValue(json_data["name"]);
		    var txt_oldbreedName = this.appFrm.findFieldByName("oldbreedName");
		    txt_oldbreedName.setValue(json_data["oldbreedName"]);
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var _this = exports.WinEdit;
			if(_this.parentCfg.self.refresh)_this.parentCfg.self.refresh(_this.optionType,data);	
		},
		changeBarItems : function(barItems){
			for(var i=0,count=barItems.length; i<count; i++){
				var item = barItems[i];
				if(item["text"] && item["text"] == Btn_Cfgs.SAVE_BTN_TXT){
					item.text = Btn_Cfgs.COPY_BTN_TXT;
					item.iconCls = Btn_Cfgs.COPY_CLS;
					item.tooltip = Btn_Cfgs.COPY_TIP_BTN_TXT;
					item.key = Btn_Cfgs.COPY_FASTKEY;
				}
			}
		},
		/**
		 * 创建Form表单
		 */
		createForm : function(){
		    var _this = this;
			var hid_id = FormUtil.getHidField({
			    fieldLabel: 'id',
			    name: 'id',
			    "width": "150"
			  });
			var txt_sysId = FormUtil.getHidField({
				    fieldLabel: '系统Id',
				    name: 'sysId',
				    "width": 200,
				    "allowBlank": false
				});
				var txt_oldName = FormUtil.getReadTxtField({
				    fieldLabel: '模板名称',
				    name: 'oldName',
				    "width": 200,
				    "allowBlank": false,
				    "maxLength": "100"
				});
				
				var txt_oldbreedName = FormUtil.getReadTxtField({
				    fieldLabel: '业务品种',
				    name: 'oldbreedName',
				    "width": 200,
				    "allowBlank": false
				});	
				
				var txt_name = FormUtil.getTxtField({
				    fieldLabel: '模板名称',
				    name: 'name',
				    "width": 200,
				    "allowBlank": false,
				    "maxLength": "100"
				});
				var txt_breedId = new Ext.ux.form.AppComboxImg({
				    fieldLabel: '业务品种',
				    name: 'breedId',
				    "width": 200,
				    "allowBlank": false,
				    valueField : 'id',
					displayField : 'name',
				    url : './sysVariety_cbolist.action',
				    callback : function(seldata){
				    	var sysId  = seldata.sysId;
				    	if(!sysId) sysId="";
				    	txt_sysId.setValue(sysId);
				    },
				    params :{sysId : _this.params.sysId}
				});	
				var txt_remark = FormUtil.getTAreaField({
				    fieldLabel: '备注',
				    name: 'remark',
				    "width": 200,
				    "maxLength": 200
				});
				
				
				var fset_secuity = {title:'原模板信息', xtype:'fieldset', autoHeight:true, defaults: {width: 200},collapsed: false,items:[hid_id, txt_oldName,txt_oldbreedName]};
				var fset_secuity1 = {title:'新模板信息',xtype:'fieldset', autoHeight:true, defaults: {width: 200},collapsed: false,items:[txt_sysId,txt_name, txt_breedId,txt_remark]};
				var layout_fields = [fset_secuity,fset_secuity1];
				var frm_cfg = {
				    title: '模版信息编辑',
				    url: './sysMatTemp_copy.action'
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
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		}
	};
});
