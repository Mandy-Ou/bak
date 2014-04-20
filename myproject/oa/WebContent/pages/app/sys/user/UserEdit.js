/**
 * 菜单新增或修改页面
 * @author 程明卫
 * @date 2012-07-12
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		fset_roleset : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			var _this = this;
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:780,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh,eventMgr:{
				saveData : function(win){_this.saveData(win);}
			}
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
			this.appWin.show(this.parent.getEl().fadeIn( {easing: 'backOut'}));
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
			var self = this;
			var urls = {};
			var parent = exports.WinEdit.parent;
			var selId = parent.getSelId();
			var cfg = {sfn:function(json_data){ exports.WinEdit.loadRoles(json_data);}};
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				var indeptId = selId.substring(1);
				var indeptName = parent.getSelText();
				if(indeptName) indeptName = indeptName.replace("【","").replace("】","");
				indeptId = indeptId.substring(1); 
				/*--- 添加 URL --*/	
				cfg.defaultVal = {indeptName:indeptName,indeptId:indeptId};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './sysUser_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				cfg.params = {userId:selId};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './sysUser_get.action',cfg : cfg};
			}
			
			var userId = this.appFrm.getValueByName("userId");
			cfg = {params : {id:userId},sfn:function(json_data){ exports.WinEdit.loadRoles(json_data,userId);}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './sysUser_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './sysUser_next.action',cfg :cfg};
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
		 * 加载角色列表
		 */
		loadRoles : function(json_data,userId){
			var self = this;
			var parent = exports.WinEdit.parent;
			var selId = parent.getSelId();
			var params = {};
			if(userId){
				params["userId"] = userId;
			}else{
				if(this.optionType == OPTION_TYPE.EDIT){	//修改
					params["userId"] = selId;
				}
			}
		
			EventManager.get('./sysUrole_list.action',{params:params,sfn:function(json_data){
				self.fset_roleset.removeAll();
				if(json_data && json_data.length > 0){
					var chk_roles = FormUtil.getRadioGroup({hideLabel:true,fieldLabel : '角色设置',columns:4,width:700,name:'uroles',items : json_data});
					self.fset_roleset.add(chk_roles);
					self.fset_roleset.doLayout();
				}
			}});
			this.makeAccessIds();
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
		saveData : function(win){
			var dataLevel = this.appFrm.getValueByName("dataLevel");
			var accessIds = this.appFrm.getValueByName("accessIds");
			if(!dataLevel && dataLevel != 0){
				ExtUtil.alert({msg:'请选择 数据访问级别!'});
				return;
			}
			var errMsg = null;
			switch(parseInt(dataLevel)){
				case 1 :{/*自定义用户*/
					errMsg = '自定义用户';
					break
				}case 4 :{/*自定义部门 */
					errMsg = '自定义部门 ';
					break
				}case 6 :{/*自定义公司*/
					errMsg = '自定义公司';
					break
				}
			}
			if(!accessIds && errMsg){
				ExtUtil.alert({msg:'请在自定义列表中选择['+errMsg+']数据!'});
				return;
			}
			
			var empName = this.appFrm.getValueByName("empName");
			if(!empName){
				ExtUtil.alert({msg:'请选择员工姓名!'});
				return;
			}
			var cfg = {
				tb:win.apptbar,
				sfn : function(formData){
					win.resetData();
					if(win.refresh) win.refresh(formData);
					win.hide();
				}
			};
			EventManager.frm_save(win.appFrm,cfg);
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		},
		makeAccessIds : function(){
			var combo = this.appFrm.findFieldByName("dataLevel");
			var txt_accessIds = this.appFrm.findFieldByName("accessIds");
			if(txt_accessIds==""){
				return;
			}
			var disabled = true;
			var isCheck = true;
			var val = combo.getValue();
			var action = null;
			if(val==1){	//自定义用户
				action = 2;	
				disabled = false;
			}else if(val==4){	//自定义部门 
				action = 1;
				disabled = false;
				allowBlank = false;
			}else if(val==6){	//自定义公司
				action = 3;	
				disabled = false;
			}
			txt_accessIds.isCheck = isCheck;
			if(action){
				txt_accessIds.setParams({action:action});
			}else{
				txt_accessIds.reset();
			}
			txt_accessIds.setDisabled(disabled);
		},
		/**
		 * 创建Form表单
		 */
		createForm : function(){
			var self = this;
			/*用户ID*/
			var hid_userId = FormUtil.getHidField({name:'userId'});
			/*公司ID*/
			var hid_incompId = FormUtil.getHidField({name:'incompId'});
			/*直属领导ID*/	
		//	var hid_leader = FormUtil.getHidField({name:'leader'});
			/*职位ID*/	
			var hid_postId = FormUtil.getHidField({name:'postId'});
			/*部门ID*/	
			var hid_indeptId = FormUtil.getHidField({name:'indeptId'});
			/*所属门店ID*/	
			var hid_instoreId = FormUtil.getHidField({name:'instoreId'});
			/*可用标识*/	
			var hid_isenabled = FormUtil.getHidField({name:'isenabled'});
			/*门店访问权限*/	
			var hid_ssLevel = FormUtil.getHidField({name:'ssLevel'});
			/*员工ID*/	
//			var hid_inempId = FormUtil.getHidField({name:'inempId'});
		
			/*员工ID*/
			var cbog_empName = ComboxControl.getEmpCboGrid({fieldLabel:'员工姓名',name:'empName',allowBlank:false,callback:function(id,selValues,records){
				var record = records[0];
				var sex = record.get("sex");
				var postId = record.get("postId");
				var indeptId = record.get("indeptId");
				var storeId = record.get("storeId");
				var ssLevel = storeId ? "1" : "-1";
				var postName = record.get("postName");
				var instoreName = record.get("storeName");
				var indeptName = record.get("indeptName");
				var phone = record.get("phone");
				var ctel = record.get("ctel");
				
				rad_sex.setValue(sex);
				hid_ssLevel.setValue(ssLevel);
				hid_postId.setValue(postId);
				hid_indeptId.setValue(indeptId);
				hid_instoreId.setValue(storeId);
				
			
				txt_postName.setValue(postName);
				txt_instoreName.setValue(instoreName);
				txt_indeptName.setValue(indeptName);
				txt_phone.setValue(phone);
				txt_tel.setValue(ctel);
			}});
			/*职位*/
			var txt_postName = FormUtil.getReadTxtField({fieldLabel : '职位',name:'postName',disabled:true});
			/*所属门店*/
			var txt_instoreName =FormUtil.getReadTxtField({fieldLabel : '所属门店',name:'instoreName',disabled:true});
			/*所属部门*/
			var txt_indeptName =FormUtil.getReadTxtField({fieldLabel : '所属部门',name:'indeptName',disabled:true});
			
			var rad_sex = FormUtil.getRadioGroup({fieldLabel : '性别',name:'sex',allowBlank:false,
						items : [{boxLabel : '男', name:'sex',inputValue:1, checked: true},
						         {boxLabel : '女', name:'sex',inputValue:2}]});
			/*账号*/
			var txt_userName = FormUtil.getTxtField({fieldLabel : '账号',name:'userName',allowBlank:false,maxLength:30});
			/*密码*/
			var txt_passWord = FormUtil.getTxtField({fieldLabel : '密码',name:'passWord',value:'111',allowBlank:false,maxLength:30});
			/*拼音助记码*/
			var txt_mnemonic = FormUtil.getHidField({fieldLabel : '拼音助记码',name:'mnemonic',maxLength:100});
		
			
			/*直属领导*/
			var barItems = [{type:'label',text:'姓名'},{type:'txt',name:'empName'}];
			var structure = [
				{header: '姓名', name: 'empName',width:100},{header: '性别',name: 'sex',width:60,renderer: function(val) {
					return Render_dataSource.sexRender(val);
				}},{header: '手机', name: 'phone',width:80},{header: '联系电话', name: 'tel',width:90}];
					
			var txt_leaderName = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '直属领导',
			    name: 'leaderName',
			    barItems : barItems,
			    structure:structure,
			    dispCmn:'empName',
			    isAll:true,
			    url : './sysUser_list.action?isenabled=1',
			    needPage : true,
			    isLoad : true,
			    keyField : 'userId',
			    width: 125
			});
			
			/*手机*/
			var txt_phone = FormUtil.getTxtField({fieldLabel:'手机',name:'phone',maxLength:20});
			/*联系电话*/
			var txt_tel = FormUtil.getTxtField({fieldLabel : '联系电话',name:'tel',maxLength:20});
			/*邮箱*/
			var txt_email = FormUtil.getTxtField({fieldLabel : '邮箱',name:'email',maxLength:50});
			
			var cbo_dataLevel = FormUtil.getLCboField({fieldLabel : '数据访问级别',name:'dataLevel',allowBlank : false,
			data:[["-1","无"],["0","本人数据"],["1","自定义用户数据"],["2","本部门数据"],["4","自定义部门"],["7","无限制"]]});
			cbo_dataLevel.addListener('select',function(combo,record,index){
				txt_accessIds.reset();				
				self.makeAccessIds();
			});
			/*数据过滤ID列表*/	
			var txt_accessIds = new Ext.ux.tree.AppComboxTree({fieldLabel : '自定义列表',name:'accessIds',
			url:'./sysTree_custorglist.action',disabled:true,isCheck:true,isAll:true,width:400,height:350,maxLength:255});
		
			/*首次密码提示*/
			var rad_pwdtip = FormUtil.getRadioGroup({fieldLabel : '首次密码提示',name:'pwdtip',width:100,
						items : [{boxLabel : '是', name:'pwdtip',inputValue:0, checked: true},
						         {boxLabel : '否', name:'pwdtip',inputValue:1}]});
			/*启用密码过期*/
			var rad_pwdfail = FormUtil.getRadioGroup({fieldLabel : '启用密码过期',name:'pwdfail',width:100,
						items : [{boxLabel : '是', name:'pwdfail',inputValue:0, checked: true},
						         {boxLabel : '否', name:'pwdfail',inputValue:1}]});
			/*过期周期*/
			var txt_pwdcycle = FormUtil.getTxtField({fieldLabel : '过期周期(天)',name:'pwdcycle',value:'30'});	
			
			/*登录限制*/
			var rad_loglimit = FormUtil.getRadioGroup({fieldLabel : '登录限制',name:'loglimit',width:250,
						items : [{boxLabel : '无限制', name:'loglimit',inputValue:0, checked: true},
						         {boxLabel : '限制IP段', name:'loglimit',inputValue:1},
						          {boxLabel : '限制机器名', name:'loglimit',inputValue:2}]});
			/*限制ip段*/
			var txt_iplimit = FormUtil.getTxtField({fieldLabel : '限制ip段或机器名',name:'iplimit',width:250});
			
			var fset_secuity = FormUtil.createLayoutFieldSet({title:'安全限制'},[
				{cmns:FormUtil.CMN_THREE,fields:[rad_pwdtip,rad_pwdfail,txt_pwdcycle]},
				 rad_loglimit,txt_iplimit]);
			
			var fset_roleset = FormUtil.getFieldSet({title:'用户角色配置'});
			this.fset_roleset = fset_roleset;
			var layout_fields  = [
			                      hid_userId,hid_postId,hid_instoreId,hid_indeptId,hid_incompId,hid_isenabled,hid_ssLevel,txt_mnemonic,
				    	          {cmns:FormUtil.CMN_THREE,fields:[cbog_empName,txt_userName,txt_passWord,txt_postName,txt_instoreName,txt_indeptName,rad_sex,txt_leaderName,txt_phone,txt_tel,txt_email]},
				    	          {cmns:FormUtil.CMN_TWO,fields:[cbo_dataLevel,txt_accessIds]},
				    	        	fset_secuity,fset_roleset
				    	          ];
			var frm_cfg = {title : '用户信息编辑',url:'./sysUser_save.action',height:400,labelWidth:100};
		    var appform = FormUtil.createLayoutFrm(frm_cfg,layout_fields);
		   
			return appform;
		}
	};
});