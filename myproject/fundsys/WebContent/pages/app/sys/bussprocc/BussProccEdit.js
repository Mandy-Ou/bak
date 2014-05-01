/**
 * 子业务流程编辑窗口
 * @author 程明卫
 * @date 2013-09-05
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		sysid : null,
		prefix : Ext.id(),
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.sysid = parentCfg.sysid;
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			var _this = this;
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:650,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh,
			eventMgr : {saveData : function(win){_this.saveData(win);}}
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
			this.appWin.show(this.parent.getEl());
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
			var sysid = exports.WinEdit.sysid;
			var cfg = null;
			var setValuesFn = function(formDatas){exports.WinEdit.setFormValues(formDatas);};
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/
				cfg = {params:{},sfn:setValuesFn,defaultVal:{sysId:sysid}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './sysBussProcc_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId},sfn:setValuesFn,defaultVal:{}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './sysBussProcc_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id},sfn:function(formDatas){exports.WinEdit.setFormValues(formDatas);},defaultVal:{}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './sysBussProcc_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './sysBussProcc_next.action',cfg :cfg};
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
		makeAccessIds : function(){
			var raduse = this.appFrm.findFieldByName("useorg");
			var txt_companyName = this.appFrm.findFieldByName("companyName");
			var val = raduse.getValue();
			if(val==0){	//所有公司
				txt_companyName.reset();
				txt_companyName.setDisabled(true);
			}else if(val==1){	//自定义公司
				txt_companyName.setDisabled(false);
			}
			
		},
		/**
		 * 创建Form表单
		 */
		createForm : function(){
			var self = this;
			var hid_isenabled = FormUtil.getHidField({name:'isenabled'});
			var hid_txtPath = FormUtil.getHidField({name:'txtPath'});
			
			var hid_id = FormUtil.getHidField({
			    fieldLabel: 'id',
			    name: 'id'
			});
			
			var hid_sysId = FormUtil.getHidField({
			    fieldLabel: '系统Id',
			    name: 'sysId'
			});

			var hid_pdid = FormUtil.getHidField({
			    fieldLabel: '流程定义Id',
			    name: 'pdid'
			});
			var txt_code = FormUtil.getReadTxtField({
			    fieldLabel: '子业务流程编号',
			    name: 'code',
			    "allowBlank": false,
			    maxLength:20,
			    width:200
			});
			
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '子业务流程名称',
			    name: 'name',
			    allowBlank: false,
			    maxLength:50,
			    width:200
			});
			
			var cbo_bussType = FormUtil.getLCboField({
			    fieldLabel: '业务性质',
			    name: 'bussType',
			    width:200,
			    allowBlank: false,
			    data: [["0","无限制"],["1","个人客户"],["2","企业客户"]]
			});
			
			var txt_menu = FormUtil.getMyComboxTree({fieldLabel : '关联功能',name:'menuId',
			url:'./sysTree_list.action',isAll:true,width:200,height:350});
			
			var rad_useorg = FormUtil.getRadioGroup({
			    fieldLabel: '适用机构',
			    name: 'useorg',
			    width: 200,
			    allowBlank: false,
			    items: [{
			        boxLabel: "所有公司",
			        name: "useorg",
			        inputValue: 0,
			        
			        checked : true
			    },
			    {
			        boxLabel: "指定公司",
			        name: "useorg",
			        inputValue: 1
			    }]
			});
			rad_useorg.addListener('change',function(group,ck){
				self.makeAccessIds();
			});
			
			var barItems = [{type:'label',text:'公司名称'},{type:'txt',name:'name'}];
			var structure = [
				{header: '隶属关系', name: 'affiliation',width:60,renderer: function(val) {
				        switch (val) {
				        case '0':
				            val = '总公司';
				            break;
				        case '1':
				            val = '分公司';
				            break;
				        }
				        return val;
				    }
				},{header: '公司名称',name: 'name',width:150}];
					
			var txt_companyName = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '指定公司名称',
			    name: 'companyName',
			    barItems : barItems,
			    structure:structure,
			    dispCmn:'name',
			    isAll:true,
			    disabled:true,
			    maxLength:200,
			    url : './sysCompany_list.action',
			    needPage : false,
			    isLoad:true,
			    width: 200
			});
			
			var txt_formUrl = FormUtil.getTxtField({fieldLabel : '关联表单地址',name:'formUrl',width:FormUtil.THREE_WIDTH,maxLength:150});
			var txt_icon = FormUtil.getMyImgChooseField({
			    fieldLabel: '流程图标',
			    width : FormUtil.THREE_WIDTH,
			    name: 'icon',
			    maxLength:150
			});
			var txa_remark = FormUtil.getTAreaField({fieldLabel : '备注',name:'remark',height:50,width:FormUtil.THREE_WIDTH,maxLength:200});
			
			var layout_fields = [
			hid_isenabled,hid_txtPath,
			hid_id, hid_sysId, hid_pdid, {
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_code, txt_name, cbo_bussType, txt_menu, rad_useorg, txt_companyName]
			},txt_formUrl,txt_icon,txa_remark];
			var frm_cfg = {
			    title: '子业务流程编辑',
			    labelWidth:100,
			    url: './sysBussProcc_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform_1;
		},
		/**
		 * 为表单元素赋值
		 */
		setFormValues : function(formDatas){
			var useorg=formDatas.useorg;
			if(!useorg || useorg == 0){
				var txt_companyName = this.appFrm.findFieldByName("companyName");
				txt_companyName.disable();
			}
			var sysid = exports.WinEdit.sysid;
			var txt_menu = this.appFrm.findFieldByName('menuId');
			txt_menu.setParams({action:1,sysid :sysid});
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
		},
		
		/**
		 * 
		 * 保存数据
		 * @param win 当前Window对象
		 */
		saveData : function(win){
			var appwin=this.appWin;
			var cfg = {
						appwin:appwin,
						tb:win.apptbar,
						beforeMake:function(formData){
						},
						sfn : function(formData){
							if(this.appwin.refresh) this.appwin.refresh(formData);
							this.appwin.hide();
						}
					};
					EventManager.frm_save(this.appFrm,cfg);
		}
		
	};
});
