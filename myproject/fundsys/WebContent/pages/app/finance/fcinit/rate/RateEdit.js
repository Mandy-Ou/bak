/**
 * 利率
 * @author smartplatform_auto
 * @date 2012-12-06 07:15:53
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
			this.appWin = new Ext.ux.window.AbsEditWindow({width:550,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh,eventMgr:{
				saveData : this.saveData
			}
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
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fcRate_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './fcRate_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './fcRate_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fcRate_next.action',cfg :cfg};
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
			var hid_id = FormUtil.getHidField({
			    fieldLabel: '利率id',
			    name: 'id',
			    "width": 135
			});
			
			var int_code = FormUtil.getReadTxtField({
			    fieldLabel: '编号',
			    name: 'code',
			    "width": 135
			});
			//id,types,limits,bdate,,
			var cbo_type = FormUtil.getLCboField({
			    fieldLabel: '利率类型',
			    name: 'types',
			    width: 135,
			    allowBlank: false,
			    data: Lcbo_dataSource.rate_types_datas
			});
			
			var int_limit = FormUtil.getLCboField({
			    fieldLabel: '利率期限',
			    name: 'limits',
			    "width": 135,
			    "allowBlank": false,
			    data:Lcbo_dataSource.rate_limits_datas
			});
			
			var dob_val = FormUtil.getDoubleField({
			    fieldLabel: '利率值',
			    name: 'val',
			    "allowBlank": false,
			    "width": 125,
			    unitText : '%'
			});
			
			var bdat_bdate = FormUtil.getDateField({
			    fieldLabel: '生效日期',
			    name: 'bdate',
			    "width": 125,
			    allowBlank: false
			});
			
//			var comp_payDate = FormUtil.getMyCompositeField({
//				itemNames : 'bdate',
//				sigins : null,
//				 fieldLabel: '利率生效日期',width:400,sigins:null,
//				 name:'comp_bdate',
//				 items : [bdat_bdate,{xtype:'displayfield',value:'<span style="color:red;">提示：如果"生效日期"为空，则该利率永远有效</span>'}]
//			});
//			var rad_isFormula = FormUtil.getRadioGroup({
//			    fieldLabel: '是否启用公式',
//			    name: 'isFormula',
//			    "width": 135,
//			    "allowBlank": false,
//			    "items": [{
//			        "boxLabel": "否",
//			        "name": "isFormula",
//			        checked : true,
//			        inputValue: 0
//			    },
//			    {
//			        "boxLabel": "是",
//			        "name": "isFormula",
//			        inputValue: 1
//			    }]
//			});
//			
//			var txt_formulaId = FormUtil.getTxtField({
//			    fieldLabel: '公式ID',
//			    name: 'formulaId',
//			    "width": 135
//			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 375,
			    "maxLength": 200
			});
			
			var layout_fields = [hid_isenabled,{
			    cmns: FormUtil.CMN_TWO,
			    fields: [int_code, cbo_type, int_limit, dob_val,bdat_bdate/*,rad_isFormula, txt_formulaId*/]
			},hid_id,txa_remark];
			var frm_cfg = {
			    title: '利率设置',
			    labelWidth:85,
			    url: './fcRate_save.action'
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
		saveData : function(self){
			var appFrm = self.appFrm;
			var params = appFrm.getValuesByNames('id,types,limits,bdate');
			var types = params.types;
			var limits = params.limits;
			var bdate =appFrm.findFieldByName('bdate').getValueAsStr();
			if(limits == '4' && (types != '5' && types != '6')){
				ExtUtil.alert({msg:'只有"放款手续费率" 和 "提前还款手续费率" 的利率期限才能设置为[无]'});
				return;
			}
						
			EventManager.get('./fcRate_valid.action',{params:params,sfn:function(json_data){
			 	if(!valid(json_data)) return;
			 	var cfg = {
					tb:self.apptbar,
					sfn : function(formData){
						self.resetData();
						if(self.refresh) self.refresh(formData);
						self.hide();
					}
				};
				EventManager.frm_save(self.appFrm,cfg);
			},ffn : function(json_data){
				if(!valid(json_data)) return;
			}});
			
		 function valid(json_data){
			var success = json_data.success;
			if(success) return true;
		 		var msg = [];
		 		switch(parseInt(types)){
		 			case 1 :
		 				msg[msg.length] = '贷款';
		 				break;
		 			case 2 :
		 				msg[msg.length] = '管理费';
		 				break;
		 			case 3 :
			 			msg[msg.length] = '罚息费';
			 			break;
		 			case 4 :
		 				msg[msg.length] = '滞纳金';
		 				break;
		 			case 5 :
			 			msg[msg.length] = '放款手续费';
			 			break;
			 		case 6 :
			 			msg[msg.length] = '提前还款手续费';
			 			break;
		 			default : 
		 				msg[msg.length] = '无';
		 		}
		 		
		 		switch(parseInt(limits)){
		 			case 1 :
		 				msg[msg.length] = '月利率';
		 				break;
		 			case 2 :
		 				msg[msg.length] = '日利率';
		 				break;
		 			case 3 :
			 			msg[msg.length] = '年利率';
			 			break;
		 			default : 
		 				msg[msg.length] = '率';
		 		}
		 		
		 		msg = '在生效日期['+bdate + ']已经存在'+msg.join("")+"的记录，请不要重复保存!";
		 		ExtUtil.alert({msg:msg});
		 		return false;
			}
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		}
	};
});
