/**
 * 修改密码页面
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
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			var _this = this;
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:300,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh,
			eventMgr : {
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
			this.appWin.show();
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
			if(self.optionType == OPTION_TYPE.SYSMODPWD){
				self.apptbar.hideButtons("上一条,下一条");
			}
			
			var urls = {};
			var parent = exports.WinEdit.parent;
			var selId = (exports.WinEdit.optionType == OPTION_TYPE.SYSMODPWD) ? parent.userId : parent.getSelId();
			var cfg = {sfn:function(json_data){}};
			function doEmpName(json_data){
				var empName = json_data["empName"];
				if(empName && empName.indexOf("##") != -1){
					empName = empName.split("##")[1];
				}
				self.appFrm.setFieldValue("empName",empName);
			}
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				var postId = selId.substring(1);
				var postName = parent.getSelText();
				var indeptId = parent.getCurNode().parentNode.id;
				indeptId = indeptId.substring(1); 
				/*--- 添加 URL --*/	
				cfg.defaultVal = {postId:postId,postName:postName,indeptId:indeptId};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './sysUser_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				cfg.params = {userId:selId};
				cfg.sfn = doEmpName;
				urls[URLCFG_KEYS.GETURLCFG] = {url : './sysUser_get.action',cfg : cfg};
			}
			
			var userId = this.appFrm.getValueByName("userId");
			cfg = {params : {id:userId},sfn:doEmpName};
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
			var optionType = this.optionType;
			var cfg = {
				tb:win.apptbar,
				sfn : function(formData){
					win.resetData();
					if(optionType == OPTION_TYPE.SYSMODPWD){
						ExtUtil.confirm({msg:'密码修改成功，只有重新登录系统后，<br/>新的密码才会生效，是否需要重新登录?',fn:function(btn){
							if(btn != 'yes') return;
							 EventManager.exist();
						}});
					}else{
						if(win.refresh) win.refresh(formData);
					}
					
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
		/**
		 * 创建Form表单
		 */
		createForm : function(){
			var self = this;
			/*用户ID*/
			var hid_userId = FormUtil.getHidField({name:'userId'});
		
			/*姓名*/
			var txt_empName = FormUtil.getReadTxtField({fieldLabel : '姓名',name:'empName',allowBlank:false,maxLength:30});
			/*账号*/
			var txt_userName = FormUtil.getReadTxtField({fieldLabel : '账号',name:'userName',allowBlank:false,maxLength:30});
			/*原密码*/
			var txt_oldPassWord = FormUtil.getTxtField({fieldLabel : '旧密码',name:'oldPassWord',allowBlank:false,maxLength:30});
			
			/*密码*/
			var txt_newPassWord = FormUtil.getTxtField({fieldLabel : '新密码',name:'newPassWord',allowBlank:false,maxLength:30});
			var layout_fields  = [hid_userId,txt_empName,txt_userName,txt_oldPassWord,txt_newPassWord];
			var frm_cfg = {title : '密码修改',url:'./sysUser_modfiyPwd.action',height:140,labelWidth:100};
		    var appform = FormUtil.createLayoutFrm(frm_cfg,layout_fields);
		   
			return appform;
		}
	};
});