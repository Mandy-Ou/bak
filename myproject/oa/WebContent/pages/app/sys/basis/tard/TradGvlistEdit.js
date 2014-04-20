/**
 * 门店分类编辑
 * @author smartplatform_auto
 * @date 2013-11-22 08:20:48
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
			this.parent = parentCfg.tree;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:300,getUrls:this.getUrls,appFrm : this.appFrm,
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
			var _this = exports.WinEdit;
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{},defaultVal:{restypeId:100002}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './sysGvlist_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				if(!selId){
					ExtUtil.alert({msg:"没有选中数据节点！"});
					return;
				}
				selId = selId.substring(1);
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './sysGvlist_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './sysGvlist_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './sysGvlist_next.action',cfg :cfg};
			return urls;
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var _this = exports.WinEdit;
			_this.parent.reload();
			if(_this.parentCfg.self.refresh)_this.parentCfg.self.refresh(_this.optionType,data);	
		},
		/**
		 * 创建Form表单
		 */
		createForm : function(){
			var hid_id = FormUtil.getHidField({
			    fieldLabel: 'ID',
			    name: 'id',
			    "width": 125,
			    "maxLength": "20"
			});
			
			var hid_restypeId = FormUtil.getHidField({
			    fieldLabel: '资源ID',
			    name: 'restypeId',
			    "width": 125,
			    "maxLength": "20"
			});
			
			var hid_Isenabled = FormUtil.getHidField({
			    fieldLabel: '可用标志',
			    name: 'isenabled',
			    "width": 125,
			    "maxLength": "20"
			});
			
			var txt_code = FormUtil.getTxtField({
			    fieldLabel: '编号',
			    name: 'code',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "50"
			});
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '名称',
			    name: 'name',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "50"
			});
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": "125"
			});
			var layout_fields = [
			txt_code, txt_name, txa_remark,hid_id,hid_Isenabled,hid_restypeId];
			var frm_cfg = {
			    title: '商系编辑',
			    url: './sysGvlist_saveIs.action'
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
