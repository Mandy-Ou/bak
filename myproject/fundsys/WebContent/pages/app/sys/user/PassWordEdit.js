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
			var self = this;
			var urls = {};
			var parent = exports.WinEdit.parent;
			var selId = parent.getSelId();
			var cfg = {sfn:function(json_data){}};
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
		},
		/**
		 * 创建Form表单
		 */
		createForm : function(){
			var self = this;
			var hid_isenabled = FormUtil.getHidField({name:'isenabled'});
			var hid_isSystem = FormUtil.getHidField({name:'isSystem'});
			/*用户ID*/
			var hid_userId = FormUtil.getHidField({name:'userId'});
			/*直属领导ID*/	
			var hid_leader = FormUtil.getHidField({name:'leader'});
			/*职位ID*/	
			var hid_postId = FormUtil.getHidField({name:'postId'});
			/*部门ID*/	
			var hid_indeptId = FormUtil.getHidField({name:'indeptId'});
			/*员工ID*/	
			var hid_inempId = FormUtil.getHidField({name:'inempId'});
		
			/*姓名*/
			var txt_empName = FormUtil.getReadTxtField({fieldLabel : '姓名',name:'empName',allowBlank:false,maxLength:30});
			/*账号*/
			var txt_userName = FormUtil.getReadTxtField({fieldLabel : '账号',name:'userName',allowBlank:false,maxLength:30});
			/*密码*/
			var txt_passWord = FormUtil.getTxtField({fieldLabel : '新密码',name:'passWord',allowBlank:false,maxLength:30});
			
			var cbo_dataLevel = FormUtil.getLCboField({fieldLabel : '数据访问级别',name:'dataLevel',allowBlank : false,hidden :true,
					data:[["-1","无"],["0","本人数据"],["1","自定义用户数据"],["2","本部门数据"],["3","本部门及子部门"],
				  			["4","自定义部门"],["5","本公司数据"],["6","自定义公司"],["7","无限制"]]});
			cbo_dataLevel.addListener('select',function(combo,record,index){
				txt_accessIds.reset();				
				self.makeAccessIds();
			});
			var layout_fields  = [
			                     hid_isenabled,hid_isSystem, hid_userId,hid_leader,hid_postId,hid_indeptId,hid_inempId,
				    	          txt_empName,txt_userName,txt_passWord,
				    	          cbo_dataLevel
				    	          ];
			var frm_cfg = {title : '密码修改',url:'./sysUser_savePassWord.action',height:140,labelWidth:100};
		    var appform = FormUtil.createLayoutFrm(frm_cfg,layout_fields);
		   
			return appform;
		}
	};
});