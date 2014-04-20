/**
 * 价格扣点编辑
 * @author smartplatform_auto
 * @date 2013-12-16 05:51:53
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
			var _this = exports.WinEdit;
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{},defaultVal:{tradAreaId:_this.parentCfg.tradAreaId}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './oaDeduction_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './oaDeduction_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './oaDeduction_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './oaDeduction_next.action',cfg :cfg};
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
			var txt_tradAreaId = FormUtil.getHidField({
			    fieldLabel: '商系分区ID',
			    name: 'tradAreaId',
			    "width": 125,
			    "allowBlank": false
			});
			
			var txt_code = FormUtil.getTxtField({
			    fieldLabel: '扣点编号',
			    name: 'code',
			    "width": 300,
			    "allowBlank": false
			});
			
			var txt_id = FormUtil.getHidField({
			    fieldLabel: 'ID',
			    name: 'id',
			    "width": "125"
			});
			
			var txt_isenabled = FormUtil.getHidField({
			    fieldLabel: '可用标识',
			    name: 'isenabled'
			});
			var int_dyear = FormUtil.getIntegerField({
			    fieldLabel: '扣点年份',
			    name: 'dyear',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": 10
			});
			
			var dob_paul = FormUtil.getDoubleField({
			    fieldLabel: '销售保底',
			    name: 'paul',
			    "allowBlank": false,
			    "width": "125"
			});
			
			var txt_tded = FormUtil.getDoubleField({
			    fieldLabel: '特价扣点',
			    name: 'tded',
			    "width": 125,
			    "allowBlank": false
			});
			
			var txt_sded = FormUtil.getDoubleField({
			    fieldLabel: '正价扣点',
			    name: 'sded',
			    "width": 125,
			    "allowBlank": false
			});
			
			var txt_contractName = FormUtil.getTxtField({
			    fieldLabel: '合同名称',
			    name: 'contractName',
			    "width": 370,
			    "maxLength": "200"
			});
			
			var cpo_attchmentId = FormUtil.getMyUploadField({
				fieldLabel: '合同附件ID', 
				name: 'attachmentId', 
				"width": 370,
				disabled:true,
				sigins:null,
			    dir : 'deduction_path',
			    allowFiles:"allowFiles_ext",
			    callback:function(fileInfos){
					//附件信息
					var filePath=fileInfos[0].serverPath;
					var pathArr=filePath.split("/");
					var fileName=pathArr[pathArr.length-1];
					var	params={sysId:-1,formType:ATTACHMENT_FORMTYPE.FType_9,
						formId:_this.parentCfg.tradAreaId,atype:5,
						fileName:fileName,filePath:filePath};
					EventManager.get("./sysAttachment_save.action",{
						params:params,
						sfn : function(jsonData){
							cpo_attchmentId.setValue(jsonData.id);
						}
					});	

						
				}
		    });
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 370,
			    "maxLength": "200"
			});
			
			
			var layout_fields = [txt_code,{
			    cmns: FormUtil.CMN_TWO,
			    fields: [int_dyear, dob_paul, txt_tded, txt_sded]
			},
			txt_contractName, cpo_attchmentId, txa_remark, txt_tradAreaId, txt_id,txt_isenabled];
			var frm_cfg = {
			    title: '价格扣点编辑',
			    url: './oaDeduction_save.action'
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
			EventManager.frm_save(_this.appFrm,cfg);
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		}
	};
});
