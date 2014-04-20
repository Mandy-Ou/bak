/**
 * 供应商信息编辑
 * @author smartplatform_auto
 * @date 2013-11-23 06:15:57
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
			this.appWin = new Ext.ux.window.AbsEditWindow({width:780,isNotSetVs:true,getUrls:this.getUrls,appFrm : this.appFrm,
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
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{},defaultVal:{}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './oaLogistics_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './oaLogistics_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './oaLogistics_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './oaLogistics_next.action',cfg :cfg};
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
			var hid_id=FormUtil.getHidField({
				fieldLavel:"ID",
				name:"id"
			});
			
			var hid_Isenabled=FormUtil.getHidField({
				fieldLavel:"可用标志",
				name:"isenabled"
			})
			var txt_code = FormUtil.getTxtField({
			    fieldLabel: '编号',
			    name: 'code',
			    "allowBlank": false,
			    "width": 125,
			    "maxLength": "20"
			});
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '物流公司名称',
			    name: 'name',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "50"
			});	
			
			var rad_logType = FormUtil.getRadioGroup({
			fieldLabel : '供应商类型',
			"allowBlank": false,
			name:'logType',
			items : [{boxLabel : '物流供应商', name:'logType',inputValue:1, checked: true},
					{boxLabel : '商品供应商', name:'logType',inputValue:2}]});
					
			var txt_indeptId = FormUtil.getMyComboxTree({
			    fieldLabel: '所属部门',
			    name: 'indeptId',
			    isAll : true,
			     width: 125,
			     url : './sysTree_deplist.action'
			});

			var txt_cman = FormUtil.getTxtField({
			    fieldLabel: '联系人',
			    name: 'cman',
			    "width": 125,
			    "maxLength": "50"
			});
			
			var txt_address = FormUtil.getTxtField({
			    fieldLabel: '地址',
			    name: 'address',
			    "width": 125,
			    "maxLength": "50"
			});
			
			var txt_ctel = FormUtil.getTxtField({
			    fieldLabel: '联系电话',
			    name: 'ctel',
			    "width": 125,
			    "maxLength": "50"
			});
			
			var txt_rway = FormUtil.getTxtField({
			    fieldLabel: '配送路线',
			    name: 'rway',
			    "width": 125,
			    "maxLength": "150"
			});
			
			var int_climit = FormUtil.getIntegerField({
			    fieldLabel: '合同期限',
			    name: 'climit',
			    "width": 125,
			    "maxLength": "150"
			});
			
			var txt_website = FormUtil.getTxtField({
			    fieldLabel: '网站',
			    name: 'website',
			    "width": 330,
			    "maxLength": "150"
			});
			var web_btn=new Ext.Button({
				text:"进入",
				name:"webBtn",
				handler:function(){
					var url=txt_website.getValue();
					if(url.indexOf("http://")==-1)url="http://"+url;
					EventManager.startDownLoad(url);
				}
			})
			var txt_website_cmp = FormUtil.getMyCompositeField({
					fieldLabel : '网站',
					sigins : null,
					itemsOne : true,
					itemNames : 'website,webBtn',
					name : 'websiteCmp',
					width : 380,
					items : [txt_website, {
								xtype : 'displayfield',
								width : 1
							}, web_btn]
			});
		
		    var txt_settleType = FormUtil.getTxtField({
			    fieldLabel: '结帐方式',
			    name: 'settleType',
			    "width": 125,
			    "maxLength": "30"
			});
			
			var txt_qtel = FormUtil.getTxtField({
			    fieldLabel: '查货电话',
			    name: 'qtel',
			    "width": 125,
			    "maxLength": "30"
			});
			
			var txt_zamount = FormUtil.getMoneyField({
			    fieldLabel: '欠款余额',
			    name: 'zamount',
			    unitText:"元",
			    "width": 125
			});
			var txt_account = FormUtil.getTxtField({
			    fieldLabel: '银行账号',
			    name: 'account',
			    "width": 125,
			    "maxLength": "30"
			});
			var txt_bankName = FormUtil.getTxtField({
			    fieldLabel: '开户银行',
			    name: 'bankName',
			    "width": 125,
			    "maxLength": "50"
			});
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 630,
			    "maxLength": 50
			});

			
	var layout_fields = [{
		    cmns: FormUtil.CMN_TWO,
		    fields: [txt_code, txt_name,txt_indeptId,rad_logType, txt_cman, txt_address, txt_ctel, txt_rway, int_climit, txt_website_cmp]
		},
		{
		    cmns: FormUtil.CMN_THREE,
		    fields: [txt_settleType, txt_qtel, txt_zamount]
		},
		{
		    cmns: FormUtil.CMN_TWO,
		    fields: [txt_account, txt_bankName]
		},
		txa_remark,hid_id,hid_Isenabled];
		var frm_cfg = {
		    title: '物流公司信息编辑',
		    labelWidth:100,
		    url: './oaLogistics_save.action'
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
