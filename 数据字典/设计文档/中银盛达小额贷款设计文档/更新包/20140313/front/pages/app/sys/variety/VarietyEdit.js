/**
 * 业务品种编辑窗口
 * @author smartplatform_auto
 * @date 2012-12-02 09:12:40
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
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/
				cfg = {params:{},defaultVal:{sysId:sysid}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './sysVariety_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId},sfn:function(formDatas){exports.WinEdit.setFormValues(formDatas);},defaultVal:{}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './sysVariety_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id},sfn:function(formDatas){exports.WinEdit.setFormValues(formDatas);},defaultVal:{}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './sysVariety_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './sysVariety_next.action',cfg :cfg};
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
		makeAccessIds1:function(){
			var radisLoan = this.appFrm.findFieldByName("isLoan");
			var txt_sitar = this.appFrm.findFieldByName("amountRange");
			var val = radisLoan.getValue();
			var disabled = true;
			if(val==0){	//不限制
				txt_sitar.reset();
				txt_sitar.setDisabled(true);
			}else if(val==1){	//自定义
				txt_sitar.setDisabled(false);
			}
		},
		
		makeAccessIds2:function(){
			var radisLimit = this.appFrm.findFieldByName("isLimit");
			var txt_sitlimit = this.appFrm.findFieldByName("limitRange");
			var val = radisLimit.getValue();
			var disabled = true;
			if(val==0){	//不限制
				radisLimit.reset();
				txt_sitlimit.reset();
				disabled = true;
			}else if(val==1){	//自定义
				disabled = false;
			}
			txt_sitlimit.setDisabled(disabled);
		},
		
		/**
		 * 创建Form表单
		 */
		createForm : function(){
			var self = this;
			var hid_isenabled = FormUtil.getHidField({name:'isenabled'});
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
			    fieldLabel: '品种编号',
			    name: 'code',
			    "allowBlank": false,
			    maxLength:20,
			    width:200
			});
			
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '品种名称',
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
			
			var rad_isCredit = FormUtil.getRadioGroup({
			    fieldLabel: '是否授信品种',
			    id:'bussType'+this.prefix,
			    name: 'isCredit',
			     width:200,
			    allowBlank: false,
			    items: [{
			        boxLabel: "否",
			        name: "isCredit",
			        inputValue: 0,
			        checked : true
			    },
			    {
			        boxLabel: "是",
			        name: "isCredit",
			        inputValue: 1
			    }]
			});
			
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
			
			var rad_isLoan = FormUtil.getRadioGroup({
			    fieldLabel: '限制贷款金额',
			    name: 'isLoan',
			    width:200,
			    allowBlank: false,
			    items: [{
			        boxLabel: "不限制",
			        name: "isLoan",
			        inputValue: 0,
			        checked : true
			    },
			    {
			        boxLabel: "限制",
			        name: "isLoan",
			        inputValue: 1
			    }]
			});
		
			rad_isLoan.addListener('change',function(group,ck){
				self.makeAccessIds1();
			});
			
			var mon_amountRange = FormUtil.getMoneyField({
			    name: 'amountRange1',
			    width:70
			});
			var mon_amountRange2 = FormUtil.getMoneyField({
			    name: 'amountRange2',
			    width:80
			});
			var sit_amountRange = FormUtil.getMyCompositeField({fieldLabel:'金额范围',width:200,name:'amountRange',disabled:true,items : [mon_amountRange,
				{xtype : 'displayfield',value : '至',width : 10},mon_amountRange2,{xtype : 'displayfield',value : '万元',width : 25}]});
			
			var rad_isLimit = FormUtil.getRadioGroup({
			    fieldLabel: '限制贷款期限',
			    name: 'isLimit',
			     width:200,
			    allowBlank: false,
			    items: [{
			        boxLabel: "不限制",
			        name: "isLimit",
			        inputValue: 0,
			        checked : true
			    },
			    {
			        boxLabel: "限制",
			        name: "isLimit",
			        inputValue: 1
			    }]
			});
			
			rad_isLimit.addListener('change',function(group,ck){
				self.makeAccessIds2();
			});
			
			var int_limitRange = FormUtil.getIntegerField({
			    fieldLabel: '期限范围',
			    name: 'limitRange1',
			    width:60
			});
			
			var int_limitRange2 = FormUtil.getIntegerField({
			    fieldLabel: '期限范围2',
			    name: 'limitRange2',
			    width:60
			});
			
			var cbo_unints = FormUtil.getLCboField({
			    fieldLabel: '单位',
			    name: 'unints',
			    width:60,
			    data: [["M","月"], ["D","天"],["Y","年"]]
			});
			
			var sit_limitRange = FormUtil.getMyCompositeField({fieldLabel:'期限范围',name:'limitRange',disabled:true,width:200,items : [int_limitRange,
				{xtype : 'displayfield',value : '至',width : 10},int_limitRange2,cbo_unints]});
			
			var txt_icon = FormUtil.getMyImgChooseField({
			    fieldLabel: '显示图标',
			    width : FormUtil.THREE_WIDTH,
			    name: 'icon',
			    showIconTypes : [1,2,3],
			    maxLength:200
			});
			
			
			var txa_remark = FormUtil.getTAreaField({fieldLabel : '品种介绍',name:'remark',height:50,width:FormUtil.THREE_WIDTH,maxLength:200});
			
			var layout_fields = [
			hid_isenabled,
			hid_id, hid_sysId, hid_pdid, {
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_code, txt_name, cbo_bussType, rad_isCredit, rad_useorg, txt_companyName, rad_isLoan, sit_amountRange, rad_isLimit, sit_limitRange]
			},txt_icon,txa_remark];
			var frm_cfg = {
			    title: '业务品种编辑',
			    labelWidth:85,
			    url: './sysVariety_save.action'
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
			var txt_amountRange = this.appFrm.findFieldByName("amountRange");
			var amountRange = txt_amountRange.getValue();
			var txt_limitRange = this.appFrm.findFieldByName("limitRange");
			var limitRange = txt_limitRange.getValue();
			if(amountRange){/*禁用，以便让表单的 isValid 方法验证通过*/
				txt_amountRange.disable();
			}
			if(limitRange){
				txt_limitRange.disable();
			}
			var appwin=this.appWin;
			var cfg = {
						appwin:appwin,
						tb:win.apptbar,
						beforeMake:function(formData){
							if(limitRange){
								formData.limitRange = limitRange;
								txt_limitRange.enable();/*当有值时恢复为可编辑状态*/
							}
							if(amountRange){
								formData.amountRange = amountRange;
								txt_amountRange.enable();
							}
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
