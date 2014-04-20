/**
 * #DESCRIPTION#
 * 
 * @author smartplatform_auto
 * @date 2014-01-08 10:56:19
 */
define(function(require, exports) {
			exports.WinEdit = {
				parentCfg : null,
				parent : null,
				optionType : OPTION_TYPE.ADD,/* 默认为新增 */
				appFrm : null,
				appWin : null,
				tbar : null,
				setParentCfg : function(parentCfg) {
					this.parentCfg = parentCfg;
					// --> 如果是Grid ，应该修改此处
					this.parent = parentCfg.parent;
					this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
				},
				createAppWindow : function() {
					this.tbar = this.createTbar();
					this.appFrm = this.createForm();
					this.appWin = new Ext.ux.window.MyWindow({
							tbar : this.tbar,
							width : 400,
							height : 200,
							items :[this.appFrm]
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
//					alert(_parentCfg._self);
					Cmw.print(_parentCfg._appgrid);
						this.setParentCfg(_parentCfg._appgrid);
					if (!this.appWin) {
						this.createAppWindow();
					}
					this.appWin.optionType = this.optionType;
					this.appWin.show(/*this.parent.getEl()*/);//动画效果
				},
				/**
				 * 销毁组件
				 */
				destroy : function() {
					if (!this.appWin)
						return;
					this.appWin.close(); // 关闭并销毁窗口
					this.appWin = null; // 释放当前窗口对象引用
				},
			/**
			 * 创建工具栏
			 */
			createTbar : function(){
				var _self = this;
					var ttbar = [{
						text : Btn_Cfgs.SAVE_BTN_TXT,	/*-- 保存 --*/ 
						iconCls:Btn_Cfgs.SAVE_CLS,
						tooltip:Btn_Cfgs.SAVE_TIP_BTN_TXT,
						handler : function() {
							_self.saveData();	
						}
					}, {
						text : Btn_Cfgs.RESET_BTN_TXT,  /*-- 重置 --*/
						iconCls:Btn_Cfgs.RESET_CLS,
						tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
						handler : function() {
							EventManager.frm_reset(_self.appFrm);
						}
					},{
						text : Btn_Cfgs.CLOSE_BTN_TXT,  /*-- 关闭 --*/
						iconCls:Btn_Cfgs.CLOSE_CLS,
						tooltip:Btn_Cfgs.CLOSE_TIP_BTN_TXT,
						handler : function() {
							this.appWin.hide();
						}
					}]
			
					var toolBar = new Ext.ux.toolbar.MyCmwToolbar({
						aligin : 'right',
						controls : ttbar
					});
					return toolBar;
				},
				
				/**
				 * 当数据保存成功后，执行刷新方法 [如果父页面存在，则调用父页面的刷新方法]
				 */
				refresh : function(data) {
					var _this = exports.WinEdit;
//					if (_this.parentCfg._self.refresh)_this.parentCfg._self.refresh(_this.optionType, data);
				},
				/**
				 * 创建Form表单
				 */
				createForm : function() {
					var appFrm=this;
					var structure = [
						{header: '职位选择',name: 'name',width:200}];
					var txt_jobId = FormUtil.getHidField({
							name : 'jobId'						
					});
					var txt_post = new Ext.ux.grid.AppComBoxGrid({
							gridWidth :200,
						    fieldLabel: '请选择职位',
						    name: 'name',
//						    barItems : barItems,
						    structure:structure,
						    dispCmn:'name',
						    isAll:true,
						    url : './sysPost_list.action',
						    needPage : false,
						    isLoad:true,
						    width: 150/*,
						    value : appFrm.parent.name*/
						});
					// 费用标准s
					var txt_name = FormUtil.getTxtField({
							fieldLabel : '费用标准',
							name : 'samount',
							"value" : "1",
							width : 130
						});
			
					var txt_costStander = FormUtil.getMyCompositeField({
							fieldLabel : '费用标准',
							name : 'samount',
							width : 150,
							items : [txt_name, {xtype : 'displayfield',value : '元'}],
							allowBlank : false
						});
						
					var layout_fields = [txt_jobId,txt_post, txt_costStander];
					var frm_cfg = {
							title : '添加职位编辑',
							isLoad:true
					};		
					var appform_1 = FormUtil.createLayoutFrm(frm_cfg,layout_fields);
					return appform_1;
				},

				
				/**
				 * 上一条
				 */
				preData : function() {},
				/**
				 * 下一条
				 */
				nextData : function() {},
				/**
				 * 保存数据
				 */
				saveData : function() {
					//获取当前输入的数据，进行赋值
					//this.parentCfg._appgrid=params;
					/**
					 * 当前的弹出窗口相当于grid表格
					 */
					var _appgrid = this.parentCfg;
					/**
					 * 获取职位名称，得到的是一个field对象，再获取对象的name的属性值,包含名称和iD
					 */
					var name_combox = this.appFrm.findFieldByName("name").getValue();
					if(name_combox==''){
						alert("请选择数据");
						return false;
					}
					var post_json = name_combox.split("##");
					/**
					 * 获取id
					 */
					var jobId = post_json[0];
					/**
					 * 获取名称
					 */
					var name = post_json[1];
					/**
					 * 获取费用标准
					 */
					var samount =this.appFrm.getValueByName("samount");
					/**
					 * 返回json对象
					 */
					var params = {jobId : jobId,name:name,samount : samount};
					var data =[];
					var record;
					data.push(params);
					if(data.length>0){
						for(var i = 0; i<data.length;i++){
							record = new Ext.data.Record(data[i]);		
						}
					}
					/**
					 * 获取表格的数据，进行判断,如果名称相同，弹出提示框，否则进行添加
					 */
					
					var store = _appgrid.getStore(params);
					var count = store.getCount();
					if(count==0) {
						store.insert(count,record);
					}else{
						var numIndex=store.findBy(function(store){
							return store.get('name')==name;
						});
						if(numIndex != -1){
							ExtUtil.info({msg:"当前已有数据"});
						}else {
							store.insert(count,record);		
						}	
					}
					this.refresh(params);
					this.appWin.hide();
				},
				/**
				 * 重置数据
				 */
				resetData : function() {}
			};
		});
