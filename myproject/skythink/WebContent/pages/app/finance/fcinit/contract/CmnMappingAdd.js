	/**
	 * #DESCRIPTION#
	 * @author smartplatform_auto
	 * @date 2013-12-03 11:58:15
	 */
	define(function(require, exports) {
		exports.WinEdit = {
			parentCfg : null,
			parent : null,
			optionType : OPTION_TYPE.ADD,/* 默认为新增  */
			appFrm : null,
			tdsId:null,
			appWin : null,
			setParentCfg:function(parentCfg){
				this.parentCfg=parentCfg;
				//--> 如果是Grid ，应该修改此处
				this.tdsId=parentCfg.tdsId;
				this.parent = parentCfg.parent;
				this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
			},
			createAppWindow : function(){
				this.appFrm = this.createForm();
				this.appWin = new Ext.ux.window.AbsEditWindow({width:520,getUrls:this.getUrls,appFrm : this.appFrm,
				eventMgr:{saveData:this.saveData},optionType : this.optionType, refresh : this.refresh
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
				var _this=exports.WinEdit;
				var parent = exports.WinEdit.parent;
				var cfg = null;
				// 1 : 新增 , 2:修改
				if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
					/*--- 添加 URL --*/	
					cfg = {params:{},defaultVal:{tdsId:_this.tdsId}};
					urls[URLCFG_KEYS.ADDURLCFG] = {url : './fcCmnMapping_add.action',cfg : cfg};
				}else{
					/*--- 修改URL --*/
					var selId = parent.getSelId();
					cfg = {params : {id:selId}};
					urls[URLCFG_KEYS.GETURLCFG] = {url : './fcCmnMapping_get.action',cfg : cfg};
				}
				var id = this.appFrm.getValueByName("id");
				cfg = {params : {id:id}};
				/*--- 上一条 URL --*/
				urls[URLCFG_KEYS.PREURLCFG] = {url : './fcCmnMapping_prev.action',cfg :cfg};
				/*--- 下一条 URL --*/
				urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fcCmnMapping_next.action',cfg :cfg};
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
				var hid_id= FormUtil.getHidField({name:"id"});
				var hid_tdsId= FormUtil.getHidField({name:"tdsId"});
				var txt_name = FormUtil.getTxtField({
				    fieldLabel: '数据列名',
				    name: 'name',
				    "allowBlank": false,
				    "width": "125"
				});
				
				var int_cellIndex = FormUtil.getIntegerField({
				    fieldLabel: 'HTML列索引',
				    name: 'cellIndex',
				    "allowBlank": false,
				    "width": "125"
				});
				
				var cbo_dataType = FormUtil.getLCboField({
				    fieldLabel: '数据类型',
				    name: 'dataType',
				    "allowBlank": false,
				    "width": 125,
				    "maxLength": 50,
				    "data": [["0", "无"], ["1", "字符串"], ["2", "金额"], ["3", "日期"], ["4", "整数"], ["5", "浮点数"]]
				});
				
				var txt_fmt = FormUtil.getTxtField({
				    fieldLabel: '日期格式',
				    name: 'fmt',
				    "width": "125"
				});
				
				var txt_fun = FormUtil.getTxtField({
				    fieldLabel: '渲染函数',
				    name: 'fun',
				    "width": "125"
				});
				
				var txt_mapingCmns = FormUtil.getTxtField({
				    fieldLabel: '映射列',
				    name: 'mapingCmns',
				    "width": "125"
				});
				
				var txa_remark = FormUtil.getTAreaField({
				    fieldLabel: '备注',
				    name: 'remark',
				    "width": 380,
				    "maxLength": 200
				});
				
				var layout_fields = [hid_id,hid_tdsId,{
				    cmns: FormUtil.CMN_TWO,
				    fields: [txt_name, int_cellIndex, cbo_dataType, txt_fmt, txt_fun, txt_mapingCmns]
				},
				txa_remark];
				var frm_cfg = {
				    title: '数据源列表',
				    url: './fcCmnMapping_save.action'
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
				var cfg = {
					sfn : function(formData){
						_this.parentCfg.appGrid.reload({tdsId:_this.parentCfg.tdsId});
						_this.appWin.hide();
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
