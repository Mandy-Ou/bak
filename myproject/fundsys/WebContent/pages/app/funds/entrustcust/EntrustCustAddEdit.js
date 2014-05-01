/**
 * 基础数据新增或修改页面
 * 
 * @author liting
 */
define(function(require, exports) {
			exports.WinEdit = {
				attach : null,
				parentCfg : null,
				parent : null,
				optionType : OPTION_TYPE.ADD,/* 默认为新增 */
				appFrm : null,
				appWin : null,
				selId : null,
				sysId:null,
				fset_roleset : null,
				setParentCfg : function(parentCfg,params) {
					this.parentCfg = parentCfg.parent;
					this.selId=this.parentCfg.selId;
					this.params = params;
					this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;//默认是新增的
				},
				createAppWindow : function() {
					this.appFrm = this.createForm();
					this.appWin = new Ext.ux.window.AbsEditWindow({
								width : 850,
								getUrls : this.getUrls,
								appFrm : this.appFrm,
								optionType : this.optionType,
								refresh : this.refresh,
								eventMgr:{saveData:this.saveData}
							});
				},
				/**
				 * 显示弹出窗口
				 * 
				 * @param _parentCfg
				 *            弹出之前传入的参数
				 */
				show : function(_parentCfg) {
					if (_parentCfg)
						this.setParentCfg(_parentCfg);
					if (!this.appWin) {
						this.createAppWindow();
					}
					this.appWin.optionType = this.optionType;
					this.appWin.show();
				},
				/**
				 * 销毁组件
				 */
				destroy : function() {
						this.appWin.close();	//关闭并销毁窗口
						this.appWin = null;		//释放当前窗口对象引用
				},
				/**
				 * 获取各种URL配置 addUrlCfg : 新增 URL editUrlCfg : 修改URL preUrlCfg :
				 * 上一条URL preUrlCfg : 下一条URL
				 */
				getUrls : function() {
					var _this = exports.WinEdit;
					var self = this;
					var urls = {};
					var selId = exports.WinEdit.parentCfg.selId;//返回父类中的appgrid对象
					var id = self.appFrm.findFieldByName("id").id; //--> {id:1,name:'张三'}
					if (this.optionType == OPTION_TYPE.ADD) { // --> 新增
						/*--- 添加 URL --*/
						var cfg = {
							params : {},
							defaultVal : {}
						};
						urls[URLCFG_KEYS.ADDURLCFG] = {
							url : './fuEntrustCust_add.action',
							cfg : cfg
						};
					} else if (this.optionType == OPTION_TYPE.EDIT) {
						/*--- 修改URL --*/
						var cfg = {
							params : {
								id : selId
							},
							defaultVal : {
								id : selId
							},
							sfn : function(Json_data) {
							var birthday=self.appFrm.findFieldByName("birthday");
							var data=new Date(Json_data["birthday"]);
							birthday.setValue(data);
							var _ctype=	self.appFrm.findFieldByName("ctype");
							_ctype.setValue(Json_data["ctype"]);
							var _deadline=self.appFrm.getValueByName("cpt_deadline").deadline;
							_deadline.setValue(Json_data["deadline"]);
							}
						};
						urls[URLCFG_KEYS.GETURLCFG] = {
							url : './fuEntrustCust_get.action',
							cfg : cfg
						};
					}
					var id = this.appFrm.getValueByName("id");
					cfg = {params : {id:id}};
					/*--- 上一条 URL --*/
					 urls[URLCFG_KEYS.PREURLCFG] = {url :
					 './fcFundsWater_next.action',cfg :cfg};
					/*--- 下一条 URL --*/
					 urls[URLCFG_KEYS.NEXTURLCFG] = {url :
					 './fcFundsWater_prev.action',cfg :cfg};
					return urls;
				},
				/**
				 * 创建Form表单
				 */
				createForm : function() {
					var self = this;
					var hide_id=FormUtil.getHidField({name:'id',fieldLabel : 'id'});//隐藏字段id
					var hide_isenidbase=FormUtil.getHidField({name:'isenabled',	fieldLabel : 'isenabled'});//隐藏字段isenabled
					var hide_state=	FormUtil.getHidField({name:'status'});//隐藏字段status
					var hide_breed=	FormUtil.getHidField({name:'breed'});//隐藏字段breed
					var hide_procId=FormUtil.getHidField({name:'procId'});//隐藏字段procid
					var txt_code = FormUtil.getTxtField({
								fieldLabel : '编号',
								name : 'code',
								"allowBlank" : false,
								"width" : "125"
							});

					var txt_name = FormUtil.getTxtField({
								fieldLabel : '姓名',
								name : 'name',
								"allowBlank" : false,
								"width" : "125"
							});

					var rad_sex = FormUtil.getRadioGroup({
								fieldLabel : '性别',
								name : 'sex',
								"allowBlank" : false,
								"width" : "125",
								"maxLength" : 50,
								"items" : [{
											"boxLabel" : '男',
											"name" : "sex",
											"inputValue" : 0
										}, {
											"boxLabel" : '女',
											"name" : "sex",
											"inputValue" : 1
										}]
							});
					var bdat_birthday = FormUtil.getDateField({
								fieldLabel : '出生日期',
								"allowBlank" : false,
								name : 'birthday',
								"width" : 125
							});

					var txt_cardNum = FormUtil.getTxtField({
								fieldLabel : '身份证号码',
								name : 'cardNum',
								"allowBlank" : false,
								"width" : "125"
							});

					var txt_job = FormUtil.getTxtField({
								fieldLabel : '职务',
								name : 'job',
								"width" : "125"
							});

					var cbo_maristal = FormUtil.getRCboField({
								fieldLabel : '婚姻',
								name : 'maristal',
								"width" : 125,
								"maxLength" : 50,
								register : REGISTER.GvlistDatas,
								restypeId : '100003'
								,"url" : "./sysGvlist_cbodatas.action?maristal=100003"
							});

					var cbo_hometown = FormUtil.getRCboField({
								fieldLabel : '籍贯',
								name : 'hometown',
								"width" : 125,
								"maxLength" : 50,register : REGISTER.GvlistDatas,
								restypeId : '100004'
								,"url" : "./sysGvlist_cbodatas.action?hometown=100004"
							});

					var cbo_nation = FormUtil.getRCboField({
								fieldLabel : '民族',
								name : 'nation',
								"width" : 125,
								"maxLength" : 50,register : REGISTER.GvlistDatas,
								restypeId : '100005'
								,"url" : "./sysGvlist_cbodatas.action?nation=100005"
							});
					var txt_inAddress = FormUtil.getTxtField({
								fieldLabel : '现居住地址',
								name : 'inAddress',
								"width" : "125"
							});

					var txt_homeNo = FormUtil.getTxtField({
								fieldLabel : '住宅号',
								name : 'homeNo',
								"width" : "125"
							});

					var rad_ctype = FormUtil.getRadioGroup({
   							 fieldLabel : '委托人类型',
								name : 'ctype',
								"width" : '150',
								"allowBlank" : false,
								"maxLength" : 50,
								"items" : [{
											"boxLabel" : "内部委托",
											"name" : "ctype",
											"inputValue" : "1"
										}, {
											"boxLabel" : "外部委托",
											"name" : "ctype",
											"inputValue" : "2"
										}]
							});

					var txt_appAmount = FormUtil.getMoneyField({
								fieldLabel : '委托金额',
								"allowBlank" : false,
								name : 'appAmount',
								unitText:"元",
								"width" : "120"
							});

					var cbo_eqopAmount =FormUtil.getLCboField({
						 fieldLabel : '期限类型',
						 name : 'dlimit',
						 "width" : 55,
					 	"allowBlank" : false,
						 "maxLength" : 50,
						data : [["1", "年"], ["2", "月"], ["3", "日"]]
						 });
							var txt_appAmountdd = FormUtil.getTxtField({
							    fieldLabel: '委托期限',
							    name:'deadline',
						    	"allowBlank" : false,
							    width:70
							});
							var txt_deadlines = FormUtil.getMyCompositeField({
								 fieldLabel: '委托期限'
								 ,width:150,
								 name:'cpt_deadline',
								 sigins : null,
								 itemNames : 'deadline,dlimit',
							 	 "allowBlank" : false,
								 items : [txt_appAmountdd,cbo_eqopAmount]
							});
							
					var txt_prange = FormUtil.getRadioGroup({
						fieldLabel : '委托产品范围',
						name : 'prange',
						"allowBlank" : false,
						"width" : '150',
						"maxLength" : 50,
						"items" : [{
									"boxLabel" : "所有产品",
									"name" : "prange",
									"inputValue" : "1"
								}, {
									"boxLabel" : "指定产品",
									"name" : "prange",
									"inputValue" : "2"
								}],
						listeners : {
							'change' : ChangePrange
						}
					});
			// 判断选中的产品的范围
			function ChangePrange() {
				var PraVals = txt_prange.getValue();
				var PraVal=Ext.getCmp(_id);
				if (PraVals && PraVals == 1) {
				PraVal.setValue("");
				txt_products.setRawValue("");
				txt_products.setDisabled(true);
				}else{
				txt_products.setDisabled(false);
				}
				}
			var _id=Ext.id();
			var txt_products = new Ext.ux.form.AppComboxImg({// FormUtil.getTxtField({
				fieldLabel : '委托产品',
				name : 'products',
				"width" : 200,
				id:_id,
				valueField : 'id',
				displayField : 'name',
				url : './fuEntrustCust_getName.action?id=' +self.sysId,
//				params : {
//					sysId : this.params.sysid
//				},
				allDispTxt : Lcbo_dataSource.allDispTxt
				,validate : function(){
					var valid = true;
					var val = this.getValue();
//					valid = val ? true : false;
					return valid;
				}
			});
					var loanManfieldset = FormUtil.createLayoutFieldSet({
						title : '委托意向信息'
					},[{
						cmns : FormUtil.CMN_THREE,
						fields : [rad_ctype, txt_appAmount,txt_deadlines]
					},{
						cmns : FormUtil.CMN_TWO,
						fields : [txt_prange,txt_products]
					}]);	
					var txt_phone = FormUtil.getTxtField({
								fieldLabel : '手机',
								name : 'phone',
								"width" : "125"
							});

					var txt_contactTel = FormUtil.getTxtField({
								fieldLabel : '联系电话',
								name : 'contactTel',
								"width" : "125"
							});

					var txt_contactor = FormUtil.getTxtField({
								fieldLabel : '联系人',
								name : 'contactor',
								"width" : "125"
							});

					var txt_email = FormUtil.getTxtField({
								fieldLabel : '电子邮件',
								name : 'email',
								"width" : "125"
							});

					var txt_fax = FormUtil.getTxtField({
								fieldLabel : '传真',
								name : 'fax',
								"width" : "125"
							});

					var txt_qqmsnNum = FormUtil.getTxtField({
								fieldLabel : 'QQ或MSN号码',
								name : 'qqmsnNum',
								"width" : "125"
							});

					var txt_workOrg = FormUtil.getTxtField({
								fieldLabel : '工作单位',
								name : 'workOrg',
								"width" : "125"
							});

					var txt_workAddress = FormUtil.getTxtField({
								fieldLabel : '单位地址',
								name : 'workAddress',
								"width" : "125"
							});
					var txt_remined = FormUtil.getTAreaField({
								fieldLabel : '备注',
								name : 'remark',
								maxLength : '120',
								"width" : "670"
							});
					var fset_2 = [{
						cmns : FormUtil.CMN_THREE,
						fields : [txt_phone, txt_contactTel, txt_contactor,
								txt_email, txt_fax, txt_qqmsnNum, txt_workOrg,
								txt_workAddress]
					}];

				var eCust = FormUtil.createLayoutFieldSet({
						title : '联系信息',
						labelWidth : 110,
						TWO_WIDTH : 400
					},fset_2);	
					
					var layout_fields = [{
						cmns : FormUtil.CMN_THREE,
						fields : [txt_code, txt_name, rad_sex, bdat_birthday,
								txt_cardNum, txt_job, cbo_maristal,
								cbo_hometown, cbo_nation, txt_inAddress,
								txt_homeNo]
					},loanManfieldset,eCust,txt_remined,hide_id,hide_isenidbase,hide_state,hide_breed,hide_procId];
				var frm_cfg = {
						title : "基本信息",
						url : './fuEntrustCust_save.action',
						labelWidth : 115
				};
				var applyForm = FormUtil.createLayoutFrm(frm_cfg,layout_fields);
				this.attach = this.createAttachMentFs(this);
				applyForm.add(this.attach);
				return applyForm;
			},
			/**
			 * 当数据保存成功后，执行刷新方法 [如果父页面存在，则调用父页面的刷新方法]
			 */
			refresh : function(data) {
				var _this = exports.WinEdit;
				if(_this.parentCfg.refresh){
				_this.parentCfg.refresh(_this.optionType,data);	
				}
				
			},
			/**
			 * 为表单元素赋值
			 */
			setFormValues : function() {

			},
			/**
			 * 创建附件FieldSet 对象
			 * 
			 * @return {}
			 */
				createAttachMentFs : function(_this) {
					/*
					 * ----- 参数说明： isLoad 是否实例化后马上加载数据 dir : 附件上传后存放的目录，
					 * mortgage_path 定义在 resource.properties 资源文件中 isSave :
					 * 附件上传后，是否保存到 ts_attachment 表中。 true : 保存 params :
					 * {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment
					 * 中的说明,formId:业务单ID，如果有多个用","号分隔} 当 isLoad = false 时，
					 * params 可在 reload 方法中提供 ----
					 */
					var uuid=Cmw.getUuid();
					var attachMentFs = new Ext.ux.AppAttachmentFs({
								title : '相关材料附件',
								isLoad : false,
								dir : 'mort_path',
								isSave : true,
								isNotDisenbaled : true,
								params:{sysId:-1,formType:ATTACHMENT_FORMTYPE.FType_39,formId:uuid}
							});
					return attachMentFs;
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
				var _this = exports.WinEdit;
				var cfg={
					sfn:function(jsonData){
						var applyId = jsonData.id;
						var params = {formType:ATTACHMENT_FORMTYPE.FType_37,sysId:-1,formId : applyId,isNotDisenbaled:true};
//						_this.attachMentFs.updateTempFormId(params,true);
						_this.appWin.hide();
					}
				};
				EventManager.frm_save(_this.appFrm,cfg);
			 	
		},
			/**
			 * 重置数据
			 */
			resetData : function() {

			}
			};
		});