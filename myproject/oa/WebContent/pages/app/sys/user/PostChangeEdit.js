/**
 * 岗位调整页面
 * @author 程明卫
 * @date 2013-05-06
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		appTree : null,
		baseTitle : '用户职位调整',
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			var _this = this;
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:340,getUrls:this.getUrls,appFrm : this.appFrm,
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
			self.apptbar.hideButtons("上一条,下一条");
			
			var urls = {};
			var parent = exports.WinEdit.parent;
		 	var dataObj = parent.getCmnVals("empName,postName");
		 	var empName = dataObj["empName"];
		 	var postName = dataObj["postName"];
		 	var title = exports.WinEdit.baseTitle+"[当前用户:<span style='color:red;'>"+empName+"</span>，原职位：<span style='color:red;'>"+postName+"</span>]";
		 	this.appFrm.setTitle(title);
			var cfg = {};
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './sysUser_add.action',cfg : cfg};
			}
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
		 * 保存数据
		 */
		saveData : function(win){
			var curNode = this.appTree.getCurNode();
			if(!curNode || (curNode.id.indexOf('P') == -1)){
				ExtUtil.alert({msg:Msg_SysTip.msg_noSelNewPost});
				return;
			}
			var userId = this.parent.getSelId();
			
			var postId = curNode.id;
			var indeptId = null;
			var incompId = null;
			var parentNode = curNode.parentNode;
			while(true){
				var id = parentNode.id;
				if(!id) continue;
				if(!indeptId && id.indexOf('D') != -1){
					indeptId = id;
				}else if(!incompId && id.indexOf('C') != -1){
					incompId = id;
					break;
				}
				parentNode = parentNode.parentNode;
			}
			postId = postId.substr(1);
			if(indeptId) indeptId = indeptId.substr(1);
			if(incompId) incompId = incompId.substr(1);
			
			var formData = {userId : userId,postId : postId, indeptId : indeptId,incompId : incompId};
			this.appFrm.setVs(formData);
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
		 * 创建Form表单
		 */
		createForm : function(){
			var self = this;
			/*用户ID*/
			var hid_userId = FormUtil.getHidField({name:'userId'});
			/*岗位ID*/
			var hid_postId = FormUtil.getHidField({name:'postId'});
			/*部门ID*/
			var hid_indeptId = FormUtil.getHidField({name:'indeptId'});
			/*公司ID*/
			var hid_incompId = FormUtil.getHidField({name:'incompId'});
			this.appTree = this.createAppTree();
			var layout_fields  = [hid_userId,hid_postId,hid_indeptId,hid_incompId,this.appTree];
			var frm_cfg = {title : this.baseTitle,url:'./sysUser_changePost.action',height:500,labelWidth:100,autoScroll :true,frame:false};
		    var appform = FormUtil.createLayoutFrm(frm_cfg,layout_fields);
			return appform;
		},
		/**
		 *  获取 TreePanel 对象
		 */
		createAppTree : function(){
			var self = this;
			var _apptree = new Ext.ux.tree.MyTree({url:'./sysTree_orglist.action',isLoad : true,width:325,enableDD:false,autoScroll :true});
			return _apptree
		}
	};
});