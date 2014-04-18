/**
 * 公司新增或修改页面
 * @author 彭登浩
 * @date 2012-07-12
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
			this.appWin = new Ext.ux.window.AbsEditWindow({width:760,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, eventMgr : {saveData:this.saveData}
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
			var _this = exports.WinEdit;
			var parent = exports.WinEdit.parent;
			var selId = parent.getSelId();
			var cfg = null;//配置参数
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				if(selId && selId.indexOf('C') != -1 /*|| selId.indexOf('root')!= -1*/){
					selType=1;
				}
				else{
					selType=0;
				}
				/*--- 添加 URL --*/	
				cfg = {params:{"poid":selId},defaultVal:{affiliation:selType},sfn:function(){
				}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './sysCompany_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './sysCompany_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");// 得到id
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './sysCompany_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './sysCompany_next.action',cfg :cfg};
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
			var appform = _this.appFrm;
			 EventManager.frm_save(appform,{sfn:function(jsonObj){
			 	_this.appWin.hide();
				_this.refresh(jsonObj);
			}});
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		},
		/**
		 * 创建Form表单
		 */
		createForm : function(){
			var hid_isenabled = FormUtil.getHidField({name:'isenabled'});
			/*菜单ID*/
			var hid_id = FormUtil.getHidField({name:'id'});
			/*上级单位类型*/
			var hid_potype = FormUtil.getHidField({fieldLabel : '上级单位类型',name:'potype',width:140});
			/*上级单位id*/
			var hid_poid= FormUtil.getHidField({fieldLabel : '上级单位id',name:'poid'});
			/*公司编号*/
			var txt_code= FormUtil.getReadTxtField({fieldLabel : ' 公司编号',name:'code',width:140,allowBlank : false});
			/*公司名字*/
			var txt_name = FormUtil.getTxtField({fieldLabel : '公司名称',name:'name',width:290,allowBlank : false,maxLength:150});
			/*隶属关系*/
			var txt_affiliation = FormUtil.getRadioGroup({fieldLabel : '隶属关系',allowBlank : false,name:'affiliation',width:150,
						items : [{boxLabel : '总公司', name:'affiliation',inputValue:0},
						         {boxLabel : '分公司', name:'affiliation',inputValue:1}]});
			var txt_mnemonic = FormUtil.getTxtField({fieldLabel : '拼音助记码',name:'mnemonic',width:140,maxLength:100});
			/*组织机构码*/
			var txt_orgcode = FormUtil.getTxtField({fieldLabel : '组织机构码',name:'orgcode',width:140,maxLength:20});
			/*营业执照号*/
			var txt_offnum= FormUtil.getTxtField({fieldLabel : '营业执照号',name:'offnum',width:140,maxLength:20}); 
			/*公司成立时间*/
			var txt_builddate = FormUtil.getDateField({fieldLabel : '创建日期',name:'builddate',width:140/*,isdeaulft:true*/});
			/*公司法人*/
			var txt_legal = FormUtil.getTxtField({fieldLabel : '公司法人',name:'legal',width:140,maxLength:50 });
			/*联系人*/
			var txt_contactor = FormUtil.getTxtField({fieldLabel : '联系人',name:'contactor',width:140,maxLength:20});
			/*联系电话*/
			var txt_tel = FormUtil.getTxtField({fieldLabel : '联系电话',name:'tel',width:140,maxLength:20,vtype:'telephone' });
			/*公司邮编*/
			var txt_zipcode = FormUtil.getTxtField({fieldLabel : '公司邮编',name:'zipcode',width:140,maxLength:20,vtype:'postcode'});
			/*公司邮箱*/
			var txt_email = FormUtil.getTxtField({fieldLabel : '公司邮箱',name:'email',width:140,maxLength:50,vtype:'email'});
			/*公司地址*/
			var txt_address = FormUtil.getTxtField({fieldLabel : '公司地址',name:'address',width:620,maxLength:200});
			/*公司网址*/
			var txt_url = FormUtil.getTxtField({fieldLabel : '公司网址',name:'url',width:290,maxLength:150,vtype:'url'});
			/*公司简介*/
			var txa_remark = FormUtil.getTAreaField({fieldLabel : '公司简介',name:'remark',width:620,maxLength:200,height:50});
			var layout_fields  = [
									hid_isenabled,hid_id,hid_potype,hid_poid,
				    	          {cmns:'TWO',fields:[txt_code,txt_name]},
				    	          {cmns:'THREE',fields:[txt_mnemonic,txt_affiliation,txt_orgcode,txt_offnum,txt_builddate,txt_legal,txt_contactor,txt_tel,txt_zipcode]},
				    	          {cmns:'TWO',fields:[txt_email,txt_url]},
				    	          txt_address,txa_remark
				    	          ];
			var frm_cfg = {title : '公司信息编辑',url : Cmw.getUrl('./sysCompany_save.action'),height:260,labelWidth:83};
		    var appform = FormUtil.createLayoutFrm(frm_cfg,layout_fields);
			return appform;
		}
	};
});