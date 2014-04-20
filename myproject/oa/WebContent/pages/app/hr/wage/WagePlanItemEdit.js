/**
 * 工资方案项编辑
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
			this.parent = parentCfg.parent;
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
				cfg = {params:{},defaultVal:{wageId:_this.parentCfg.selId}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './hrPlanItem_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './hrPlanItem_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './hrPlanItem_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './hrPlanItem_next.action',cfg :cfg};
			return urls;
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var _this = exports.WinEdit;
			_this.parent.reload({wageId:_this.parentCfg.selId});
//			if(_this.parentCfg.self.refresh)_this.parentCfg.self.refresh(_this.optionType,data);	
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
			
			var hid_wageId = FormUtil.getHidField({
			    fieldLabel: '方案ID',
			    name: 'wageId',
			    "width": 125,
			    "maxLength": "20"
			});
			
			var hid_Isenabled = FormUtil.getHidField({
			    fieldLabel: '可用标志',
			    name: 'isenabled',
			    "width": 125,
			    "maxLength": "20"
			});
			
			var txt_itemId=FormUtil.getMyComboxTree({
				fieldLabel:"工资项",
				name:"itemId",
				url:'./sysTree_wItemList.action',
				isAll:true,
				allowBlank:false,
				width:150,
				height:350
			});
			
			var txt_direction=FormUtil.getLCboField({
				fieldLabel:"项目方向",
				name:"direction",
				allowBlank:false,
				width:150,
				data:[["1","加项"],["2","减项"]]
			});
			
			var txt_bamount=FormUtil.getIntegerField({
				fieldLabel:"基本金额",
				allowBlank:false,
				name:"bamount",
				unitText:"元",
				width:140
			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": "150"
			});
			var layout_fields = [
			txt_itemId, txt_direction,txt_bamount, txa_remark,hid_id,hid_Isenabled,hid_id,hid_wageId];
			var frm_cfg = {
			    title: '工资方案编辑',
			    url: './hrPlanItem_save.action'
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
