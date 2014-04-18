				/**
				 * 基础数据新增或修改页面
				 * @author liting
				 */
				define(function(require, exports) {
				exports.WinEdit = {
				parentCfg : null,
				parent : null,
				optionType : OPTION_TYPE.ADD,/* 默认为新增  */
				appFrm : null,
				appWin : null,
				selId:null,
				data:null,
				fset_roleset : null,
				setParentCfg:function(parentCfg){
					this.parentCfg=parentCfg;
					this.selId=parentCfg.selId;
					this.data=parentCfg.data;
					this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
				},
				createAppWindow : function(){
					this.appFrm = this.createForm();
					this.appWin = new Ext.ux.window.AbsEditWindow({width:350,getUrls:this.getUrls,appFrm : this.appFrm,
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
					var self = this;	
					var data=_this.data;
					var bankName =data["bankName"];
					var account=data["account"];
					var remark=data["remark"];		
					var urls = {};
					var selId=	_this.parentCfg.parent.getSelId();
					var parent = exports.WinEdit.parent;
					var cfg = {sfn:function(data){}};
					if(this.optionType == OPTION_TYPE.EDIT){ //编辑
						cfg = {params:{},defaultVal:{bankName:bankName,account:account,remark:remark,selId:selId,id:selId}};
						urls[URLCFG_KEYS.GETURLCFG] = {url : './fcFundsWater_list.action',cfg : cfg};
				}
					/*--- 上一条 URL --*/
//					urls[URLCFG_KEYS.PREURLCFG] = {url : './fcFundsWater_next.action',cfg :cfg};
					/*--- 下一条 URL --*/
//					urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fcFundsWater_prev.action',cfg :cfg};
					return urls;
				}
				,
					/**
					 * 创建Form表单
					 */
				createForm : function(){
						var self = this;
						var hid_ids = FormUtil.getHidField({name:'id'});
						var hid_selId = FormUtil.getHidField({name:'selId'});
						var txt_accountId = FormUtil.getHidField({
					    fieldLabel: '账户ID',
					    name: 'accountId',
					    "allowBlank": false
						});
						var hid_id = FormUtil.getReadTxtField({fieldLabel : '银行',width:200,name:'bankName'});
						var txt_Code = FormUtil.getReadTxtField({fieldLabel : '银行帐号',name:'account',width:200,maxLength:50});
						var txt_Edit = FormUtil.getTAreaField({fieldLabel : '备注',name:'remark',width:200,maxLength:500});
						var layout_fields  = [hid_ids,hid_selId,txt_accountId,hid_id,txt_Code,txt_Edit];
						var frm_cfg = {title : '备注修改',url:'./fcFundsWater_update.action'};
						var appform = FormUtil.createLayoutFrm(frm_cfg,layout_fields);
					return appform;
				},
				/**
				 * 当数据保存成功后，执行刷新方法
				 * [如果父页面存在，则调用父页面的刷新方法]
				 */
				refresh : function(data){
					var _this = exports.WinEdit;
					if(_this.parentCfg.self.callBack){
						_this.parentCfg.self.callBack(data);
						_this.appWin.hide();
					}
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