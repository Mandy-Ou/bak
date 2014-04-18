/**
 * 部门新增或修改页面
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
			var parent = exports.WinEdit.parent;
			var selId = parent.getSelId();
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				if(selId.indexOf('C')!=-1){
					var potype=1
				}else{
					var potype=2;
				}
				var subpoid=selId.substring(1);
				cfg = {params:{"poid":subpoid},defaultVal : {poid:subpoid,potype:potype}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './sysDepartment_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './sysDepartment_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './sysDepartment_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './sysDepartment_next.action',cfg :cfg};
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
				_this.refresh(jsonObj);
				_this.appWin.hide();
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
			var txt_potype = FormUtil.getHidField({fieldLabel : '上级单位类型',name:'potype',width:140});
			/*上级单位ID*/
			var txt_poid= FormUtil.getHidField({fieldLabel : '上级单位ID',name:'poid',allowBlank : false,width:140});
			/*部门编号*/
			var txt_code= FormUtil.getReadTxtField({fieldLabel : '部门编号',name:'code',width:140,allowBlank : false});
			/*部门名字*/
			var txt_name = FormUtil.getTxtField({fieldLabel : '部门名称',name:'name',width:290,allowBlank : false,maxLength:100});
			/*拼音助记码*/
			var txt_mnemonic = FormUtil.getTxtField({fieldLabel : '拼音助记码',name:'mnemonic',width:140,maxLength:100});
			/*部门成立时间*/
			var txt_builddate = FormUtil.getDateField({fieldLabel : '创建日期',name:'builddate',width:140/*,isdeaulft:true*/});
						
			/*部门负责人*/
			var txt_mperson = FormUtil.getTxtField({fieldLabel : '部门负责人',name:'mperson',width:140,maxLength:50});
			/*联系人*/
			var txt_contactor = FormUtil.getTxtField({fieldLabel : '联系人',name:'contactor',width:140,maxLength:20});
			/*联系电话*/
			var txt_tel = FormUtil.getTxtField({fieldLabel : '联系电话',name:'tel',width:140,maxLength:20,vtype:'telephone'});
			/*部门邮编*/
			var txt_zipcode = FormUtil.getTxtField({fieldLabel : '部门邮编',name:'zipcode',width:140,maxLength:20,vtype:'postcode'});
			/*部门邮箱*/
			var txt_email = FormUtil.getTxtField({fieldLabel : '部门邮箱',name:'email',width:140,maxLength:50,vtype:'email'});
			/*部门地址*/
			var txt_address = FormUtil.getTxtField({fieldLabel : '部门地址',name:'address',width:620,maxLength:200});
			/*部门网址*/
			var txt_url = FormUtil.getTxtField({fieldLabel : '部门网址',name:'url',width:290,maxLength:150,vtype:'url'});
			
			/*公司简介*/
			var txa_remark = FormUtil.getTAreaField({fieldLabel : '部门简介',name:'remark',width:620,maxLength:200});
			var layout_fields  = [
									hid_isenabled,hid_id,txt_potype,txt_poid,
				    	          {cmns:'TWO',fields:[txt_code,txt_name]},
				    	          {cmns:'THREE',fields:[txt_mnemonic,txt_mperson,txt_builddate,txt_contactor,txt_tel,txt_zipcode]},
				    	          {cmns:'TWO',fields:[txt_email,txt_url]},
				    	          txt_address,txa_remark
				    	          ];
			var frm_cfg = {title : '部门信息编辑',url : Cmw.getUrl('./sysDepartment_save.action'),height:250,labelWidth:83};
		    var appform = FormUtil.createLayoutFrm(frm_cfg,layout_fields);
		   
			return appform;
		}
	};
});