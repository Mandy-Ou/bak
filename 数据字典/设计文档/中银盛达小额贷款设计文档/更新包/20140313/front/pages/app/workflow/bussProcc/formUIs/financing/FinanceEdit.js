/**
 * 金融机构编辑
 * @author 郑符明
 * @date 2014-03-05 05:36:18
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 我们使用编辑  */
		appFrm : null,
		appWin : null,
		globalMgr : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({title:'编辑金融机构',width:780,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh
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
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{},defaultVal:{}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : 'fcOutFunds_save.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id: selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : 'fcOutFunds_get.action',cfg : cfg};
			}
			var id = parent.getSelId();
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : 'fcOutFunds_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : 'fcOutFunds_next.action',cfg :cfg};
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
			var bdat_lastDate = FormUtil.getHidField({
								fieldLabel : '最后存入时间',
								name : 'lastDate'
							});
			var txt_id = FormUtil.getHidField({
				fieldLabel : 'id',
				name : 'id'
			});
			var txt_Isenabled = FormUtil.getHidField({
				fieldLabel : 'Isenabled',
				name : 'Isenabled',
				value : 0
			});
			var txt_code = FormUtil.getReadTxtField({
				fieldLabel: '编号',
			    name: 'code',
			    "width": 145,
			    "allowBlank": false,
			    "maxLength": "50"
			});
			
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '金融机构名称',
			    name: 'name',
			    "width": 145,
			    "allowBlank": false,
			    "maxLength": "50"
			});

			function ck_callback(grid,value,selRows){
					var record = selRows[0];
					txt_companyNum.setValue(record.get('account'));
					txt_accountId.setValue(record.get('id'));
					}
			structure = [{header : "id",name : 'id',hidden : true},
						{header: '公司名称',name: 'bankName',width:200},
						{header : '银行账号',name :'account',width: 200}];
			var txt_bankName = new Ext.ux.grid.AppComBoxGrid({
						gridWidth :420,
						fieldLabel : '<span style="color:red">*</span>借款银行',
						name : 'refmans',
					    structure:structure,
					    dispCmn:'bankName',
					    isAll:true,
					    url : './sysAccount_list.action',
					    needPage : false,
						"maxLength" : "50" ,
					    isLoad:true,
					    width : 135,
					   	callback : ck_callback
					});
					
			var txt_companyNum = FormUtil.getReadTxtField({
						fieldLabel : '银行账号',
						name : 'account',
						width : 135,
						"allowBlank" : false,
						"maxLength" : "20"
					});
			var txt_accountId = FormUtil.getTxtField({
								fieldLabel : '账户ID',
								name : 'accountId',
								"width" : 135,
								"allowBlank" : false,
								hidden :true
					});
			var int_xunint = FormUtil.getLCboField({
								fieldLabel : '%',
								name : 'xunint',
								"width" : 45,
								"allowBlank" : false,
								"value" : "1",
								data:[["1","%"],["2","‰"]],
								"maxLength" : 10
					});

			var dob_xrate = FormUtil.getIntegerField({
						fieldLabel : '担保费率',
						name : 'xrate',
						"width" : 85,
						"allowBlank" : false,
						"value" : "0.0000",
						"decimalPrecision" : "4"
					});
			var int_orgType = FormUtil.getIntegerField({
						fieldLabel : '机构类型',
						name : 'orgType',
						"allowBlank" : false,
						"maxLength" : 10,
						hidden : true
					});
			var txt_xratestore = FormUtil.getMyCompositeField({
						itemNames : 'xrate,xunint',
						fieldLabel : '<span style="color:red">*</span>担保费率',
						name : 'xrate',
						width : 135,
						sigins : null,
						items :[dob_xrate, int_xunint ]
					});
					
			var dob_rate = FormUtil.getIntegerField({
						fieldLabel : '贷款利率',
						name : 'rate',
						"width" : 85,
						"allowBlank" : false,
						"value" : "0.0000",
						"decimalPrecision" : "4"
					});
					
			var int_unint = FormUtil.getLCboField({
						fieldLabel : '%',
						name : 'unint',
						"width" : 45,
						"allowBlank" : false,
						"value" : "1",
						data:[["1","%"],["2","‰"]],
						"maxLength" : 10
					});
					
			var txt_ratestore = FormUtil.getMyCompositeField({
						itemNames : 'rate,unint',
						fieldLabel : '<span style="color:red">*</span>贷款利率',
						name : 'rate',
						width : 135,
						sigins : null,
						items :[dob_rate, int_unint ]
					});
			
			var int_payDay = FormUtil.getIntegerField({
			    		fieldLabel : '<span style="color:red">*</span>每月付息日',
						name : 'payDay',
						"width" : 115,
						unitText : '日',
						unitStyle : 'margin-left:2px;color:red;font-weight:bold',
						"maxLength" : 10
			});
			
			var bdat_startDate = FormUtil.getDateField({
			    fieldLabel: '贷款起始日期',
			    name: 'startDate',
			    "width": 145
			});
			
			var bdat_endDate = FormUtil.getDateField({
			    fieldLabel: '贷款截止日期',
			    name: 'endDate',
			    "width": 145
			});
			
			var txt_remark = FormUtil.getTAreaField({
				fieldLabel: '备注',
			    name: 'remark',
			    "width": 650
			});
			var layout_fields = [{
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_code,txt_name, txt_bankName,txt_companyNum,int_orgType,
			    		txt_ratestore,txt_xratestore,txt_accountId,bdat_lastDate,txt_id,txt_Isenabled]
			},{
				cmns: FormUtil.CMN_THREE,
			    fields: [int_payDay, bdat_startDate, bdat_endDate]
			
			},txt_remark];
			var frm_cfg = {
			   // title: '编辑金融机构',
				labelWidth : 85,
			    url : 'fcOutFunds_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
				return appform_1;
			},
			/**
				 * 为表单元素赋值
				 */
				setFormValues : function() {

				},
				/**
				 * 上一条
				 */
				preData : function() {

				},
				/**
				 * 下一条
				 */
				nextData : function() {

				},
				/**
				 * 保存数据
				 */
				saveData : function() {
				},
				/**
				 *  重置数据 
				 */
				resetData : function() {
				}
		};
	});
