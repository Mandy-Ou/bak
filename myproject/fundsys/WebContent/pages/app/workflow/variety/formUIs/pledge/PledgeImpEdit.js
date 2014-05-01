/**
 * #DESCRIPTION#
 * @author smartplatform_auto
 * @date 2014-03-17 05:37:01
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
			this.appWin = new Ext.ux.window.AbsEditWindow({width:520,height:180,getUrls:this.getUrls,appFrm : this.appFrm,
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
			var _this= exports.WinEdit;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{state:1},defaultVal:{id:parent.getSelId(),code:_this.parentCfg.code}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './fcPledge_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId},defaultVal:{id:parent.getSelId()}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './fcPledge_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './fcPledge_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './fcPledge_next.action',cfg :cfg};
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
		
			var txt_code = FormUtil.getTxtField({
			    fieldLabel: '质押物编号',
			    name: 'code',
			    "width": "125"
			});
			
			var bdat_carrTime = FormUtil.getDateField({
			    fieldLabel: '落实时间',
			    name: 'carrTime',
			    "width": "125"
			});
			
				/*落实人*/
			var barCarrMan = [{type:'label',text:'姓名'},{type:'txt',name:'empName'}];
			var strCarrMan = [
				{header: '姓名', name: 'empName',width:100},{header: '性别',name: 'sex',width:60,renderer: function(val) {
					return Render_dataSource.sexRender(val);
				}},{header: '手机', name: 'phone',width:100},
				{header: '部门', name: 'indeptName',width:80},
				{header: '部门ID', name: 'indeptId',width:80,hidden:true},
				{header: '联系电话', name: 'tel',width:90}];
					
			var txt_carrMan = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '落实人',
			    name: 'carrMan',
			    barItems : barCarrMan,
			    structure:strCarrMan,
			    dispCmn:'empName',
			    isAll:true,
			    url : './sysUser_list.action',
			    needPage : true,
			    isLoad : true,
			    keyField : 'userId',
			    width: 125
			});
			
			txt_carrMan.grid.on("dblclick",function(val){
				var dept=txt_carrMan.grid.getSelRow();
				txt_carrDeptName.setValue(dept.get("indeptName"));
				txt_carrDept.setValue(dept.get("indeptId"));
			})
			
			var txt_carrDeptName=FormUtil.getTxtField({
			    fieldLabel: '落实办理部门',
			    name: 'carrDeptName',
			    "width": "100"
			});
			
			var txt_carrDept=FormUtil.getHidField({
			    fieldLabel: '落实办理部门ID',
			    name: 'carrDept',
			    "width": "125"
			});
			
//			/*所属部门*/
//			var barIndept = [{type:'label',text:'名称'},{type:'txt',name:'name'}];
//			var strIndept = [
//				{header: '部门名称', name: 'name',width:100},
//				{header: '部门类型',name: 'dtype',width:60},
//				{header: '联系人', name: 'contactor',width:100}];
//					
//			var txt_carrDept = new Ext.ux.grid.AppComBoxGrid({
//			    fieldLabel: '落实办理部门',
//			    name: 'carrDept',
//			    barItems : barIndept,
//			    structure:strIndept,
//			    dispCmn:'name',
//			    isAll:true,
//			    url : './sysDepartment_list.action',
//			    allowBlank: false,
//			    needPage : true,
//			    isLoad : true,
//			    keyField : 'id',
//			    width: 125
//			});
			
			
			var hid_id = FormUtil.getHidField({
			    fieldLabel: 'ID',
			    name: 'id',
			    "width": "125"
			});
			
			var hid_state = FormUtil.getHidField({
			    fieldLabel: '状态',
			    name: 'state',
			    value:1,
			    "width": "125"
			});
			
			var layout_fields = [{
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_code, bdat_carrTime, txt_carrMan, txt_carrDeptName,hid_id,hid_state,txt_carrDept]
			}];
			var frm_cfg = {
			    title: '质押物落实办理',
			    labelWidth:100,
			    url: './fcPledge_save.action'
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
