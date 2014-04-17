/**
 *  params 参数【projectId,customerId,custType】
 *   ./fcAuditproject_query.action
 *  ./fcAuditproject_insert.action
 *  ./fcAuditproject_update.action
 *  ./fcAuditproject_delete.action
 *
**/
var AuditProjectMgr = {
	basePath : null,
	baseParams : {},
	cache : {},
	oldCache : {},/*上一次的缓存数据，用于取消时用*/
	editTabId : null,	/*缓存正在编辑的TAB ID*/
	uuid : null,
	selRow : null,	/*当前选中的行*/
	updateUrl : './fcAuditproject_update.action',
	addUrl : './fcAuditproject_insert.action',
	queryUrl : './fcAuditproject_query.action',
	delUrl : './fcAuditproject_delete.action',
	tabUuidCache : {},
	finishBussCallback : null,
	tabCfgs : null,/*表数组对象*/
	setTabCfgs:function(_tabCfgs){
		if(Ext.isString(_tabCfgs)) _tabCfgs = Ext.decode(_tabCfgs);
		this.tabCfgs = _tabCfgs;
		this.init();
	},
	/**
	 * 初始化
	 * 将工具栏按钮隐藏
	 */
	init : function(){
		if(!this.tabCfgs || this.tabCfgs.length == 0) return;
		var _this = this;
		for(var i=0,count=this.tabCfgs.length; i<count; i++){
			var tabCfg = this.tabCfgs[i];
			var tabId = tabCfg.tabId;
			var toolEle = this.$("TL_"+tabId);
			if(toolEle) toolEle.style.display = "none";
			var btnEditEle = this.$("ED_TL_"+tabId);
			if(btnEditEle){
				btnEditEle.disabled = "";
				Ext.get(btnEditEle).on('click',function(e,t,o){
					_this.edit(t);
				});
			}
			var btnCancelEle = this.$("SV_TL_"+tabId);
			if(btnCancelEle){
				btnCancelEle.disabled = "disabled";
				btnCancelEle.innerHTML = "取消";
				Ext.get(btnCancelEle).on('click',function(e,t,o){
					_this.cancel(t);
				});
			}
		}
	},
	/**
	 * 隐藏所有工具栏按钮
	 */
	hideTools : function(){
		if(!this.tabCfgs || this.tabCfgs.length == 0) return;
		for(var i=0,count=this.tabCfgs.length; i<count; i++){
			var tabCfg = this.tabCfgs[i];
			var tabId = tabCfg.tabId;
			var toolEle = this.$("TL_"+tabId);
			if(toolEle) toolEle.style.display = "none";
			var btnEditEle = this.$("ED_TL_"+tabId);
			if(btnEditEle) btnEditEle.disabled = "";
			var btnCancelEle = this.$("SV_TL_"+tabId);
			if(btnCancelEle) btnCancelEle.disabled = "disabled";
		}
	},
	/**
	 * 编辑
	 */
	doEdit : function(){
		if(!this.tabCfgs || this.tabCfgs.length == 0) return;
		for(var i=0,count=this.tabCfgs.length; i<count; i++){
			var tabCfg = this.tabCfgs[i];
			var tabId = tabCfg.tabId;
			if(!this.$(tabId)){
				alert("tabId=["+tabId+"] 对象不存在，请检查!");
				return;
			}
			var toolEle = this.$("TL_"+tabId);
			if(toolEle) toolEle.style.display = "";
			var btnEditEle = this.$("ED_TL_"+tabId);
			if(btnEditEle) btnEditEle.disabled = "";
			var btnCancelEle = this.$("SV_TL_"+tabId);
			if(btnCancelEle) btnCancelEle.disabled = "disabled";
		}
	},
	/**
	 * 保存数据
	 */
	doSave : function(_finishBussCallback){
		this.finishBussCallback = _finishBussCallback;
		var flag = false;
		for(var prop in this.cache){
			flag = true;
		}
		if(!flag){
			alert("没有任何数据要保存！");	
			return;
		}
		
		for(var prop in this.cache){
			var tabId = prop;
			var btnId = 'SV_TL_'+tabId;
			if(!this.cache.hasOwnProperty(tabId)) continue;
			var _cache = this.cache[tabId];
			if(!_cache){
				delete this.cache[tabId];
				continue;
			}
			this.save(btnId,_cache);
		}
	},
	/**
	 * 打印
	 */
	doPrint : function(){
		if(!this.tabCfgs || this.tabCfgs.length == 0) return;
		for(var i=0,count=this.tabCfgs.length; i<count; i++){
			var tabCfg = this.tabCfgs[i];
			var tabId = tabCfg.tabId;
			if(!this.$(tabId)){
				alert("tabId=["+tabId+"] 对象不存在，请检查!");
				return;
			}
			var toolEle = this.$("TL_"+tabId);
			if(toolEle) toolEle.style.display = "none";
		}
		PrintManager.lodopexePath = this.basePath+"/controls/lodop/";
		PrintManager.printcssPath = this.basePath+"/pages/app/workflow/variety/formUIs/survey/auditProject.css";
		var html = document.body.innerHTML;
		PrintManager.preview(html);
	},
	/**
	 *  修改
	 *  params self 当前点击对象
	 *  params otherTabIds 其它要一起修改的 Tab ID,多个用 "," 号隔开
	 **/
	edit : function(self,otherTabIds){
//		if(!this.checkEidtTab()){
//			return;
//		}
		var btnId = self.id;
		var tabId = this.getTabId(btnId,'ED_TL_');
		var flag = this.makeFields(tabId);
		if(!flag) return;
		self.disabled = "disabled";
		var btnSaveId = btnId.replace('ED_TL_','SV_TL_');
 		var btnSave = this.$(btnSaveId);
		btnSave.disabled = "";
		this.setUuid(tabId);		
	},
	/**
	 * 取消
	 */
	cancel : function(self){
		var id = self.id;
		self.disabled = "disabled";
		var btnEditId = id.replace('SV_TL_', 'ED_TL_');
 		var btnEdit = this.$(btnEditId);
 		btnEdit.disabled = "";
 		var tabId = this.getTabId(id,"SV_TL_");
 		var oldData = this.oldCache[tabId];
 		if(!oldData) return;
 		for(var key in oldData){
 			var tdEle = this.$(key);
 			if(!tdEle) continue;
 			var val = oldData[key];
 			tdEle.innerText = val;
 		}
 		if(this.cache.hasOwnProperty(tabId)) delete this.cache[tabId];
 		if(this.oldCache.hasOwnProperty(tabId)) delete this.oldCache[tabId];
	},
	/**
	 *  设置UUID值
	 *   @return true : 没有未保存， false : 有未保存
	 **/
	setUuid : function(tabId){
		/*--> 如果是可编辑表格和导入表格则从 table 上取UUID <--*/
		if(-1 != tabId.indexOf('ET') || -1 != tabId.indexOf('IT')){
			var tabObj = this.$(tabId);
			var uuid = tabObj.getAttribute("uuid");
			if(uuid) this.tabUuidCache[tabId] = uuid;
		}else{ /*-->新增的从选中行上取UUID<--*/
			if(this.selRow) this.uuid = this.selRow.uuid;
		}	
	},
	/**
	 *  检查是否有编辑后未保存的表格
	 *   @return true : 没有未保存， false : 有未保存
	 **/
	checkEidtTab : function(){
		var flag = true;
		if(!this.editTabId){
			flag = true;
		}else{
			var idArr = this.editTabId.split('_');
			var errMsg = [];
			var id = null;
			for(var i=0,count=idArr.length; i<count; i++){
				if(null == id){
					id = idArr[i];
				}else{
					id += "_"+idArr[i];
				}
				
				if(i==count-1) break;
				
				var ele = this.$(id);
				if(ele) errMsg[errMsg.length] = ele.innerText;
			}
			if(errMsg.length > 0){
				alert("请先保存：\n"+errMsg.join("\n--")+"\n的数据！");	
				flag = false;	
			}else{
				flag = true;	
			}
		}
		return flag;
	},

	/**
	 *  保存
	 *  params btnId 按钮ID
	 *  @param _cache 缓存的表单元素对象
	 **/
	save : function(btnId,_cache){
		var flag = false;
		var tabId = this.getTabId(btnId,'SV_TL_');
		var isSave = false;
		for(var prop in _cache){
 			var ele = _cache[prop];
			var val = ele.value;
			if(val && this.trim(val).length>0){
				isSave = true;
				break;
			}
		}
		
		if(!isSave){
			var cancelEleId = btnId.replace( 'ED_TL_','SV_TL_');
			this.cancel(this.$(cancelEleId));
			var finish = this.isFinish();
			if(finish){
				this.hideTools();
				alert("没有任何数据要保存！");
			}
			return;
		}
		var _this = this;
		function callback(data){
			//--> 给单元格赋值 <--//
			for(var prop in _cache){
				var ele = _cache[prop];
				var val = ele.value;
				_this.$(prop).innerHTML = val;
		 	}
			
			//--> 放到 Ajax callback 中去执行 <--//
			self.disabled = "disabled";
			var btnEditId = btnId.replace('SV_','ED_');
			var btnEdit = _this.$(btnEditId);
			btnEdit.disabled = "";
			
			if(data._id){
				_this.$(tabId).setAttribute("uuid",data._id);
			}
			
			//-->  重置 <--//
			//_this.editTabId = null;
			delete _this.cache[tabId];
			//Ext.Msg.alert('系统提示',"data.tab="+data.tab+",data._id="+data._id);
			var finish = _this.isFinish();
			if(finish){
				_this.hideTools();
				if(_this.finishBussCallback) _this.finishBussCallback(data);
				alert("保存成功！");
			}
		}
		var uuid = this.$(tabId).getAttribute("uuid");
		var url = uuid ? this.updateUrl : this.addUrl;
		this.saveDatas(url,{},tabId,_cache,uuid,callback);
		
	},
	/**
	 * 判断要保存的数据是否已全部处理完成
	 * @return {}
	 */
	isFinish : function(){
		var finish = true;
		for(var prop in this.cache){
			finish = false;
		}
		return finish;
	},
	/**
	 *  保存数据并更新单元格值
	 *  @param url 服务器地址
	 *  @param params	参数
	 *  @param tabId 当前保存的表ID
	 *  @param _cache 当前缓存的表单元素对象
	 *  @param uuid
	 *  @param callback 回调函数
	 **/
	saveDatas : function(url,params,tabId,_cache,uuid,callback){
		var data = {};
		Ext.apply(data,this.baseParams);
		for(var prop in _cache){
			var ele = _cache[prop];
			var val = ele.value;
			data[prop] = val;
		}
		if(uuid) data["_id"] = uuid;
		if(!params) params = {};
		params.datas = Ext.encode(data);
		params.tab = tabId;
		//--->  data 保存到数据库中 Ajax <----//
		var action = {url : url,params : params,sfn:callback};
		this.request(action);
	},
	/**
	 *  新增
	 *  params self 当前点击对象
	 **/
	add : function(self){
		alert("add,id="+self.id);
	},
	/**
	 *  删除
	 *  params self 当前点击对象
	 **/
	del : function(self){
		alert("del,id="+self.id);
	},
	makeFields : function(tabId){
		
		var tab = this.$(tabId);
		var cfg = tab.getAttribute("cfg");
		if(!cfg){
			alert("在编辑数据时，发现没有为【"+tabId+"】添加 \"cfg\" 属性，无法生成可编辑控件！");
			return false;
		}
		cfg = Ext.decode(cfg);
		if(!cfg.edcmns){
			alert("在编辑数据时，发现ID为【"+tabId+"】的 \"cfg\" 属性没有 \"edcmns\"属性，无法生成可编辑控件！");
			return false;
		}
		var prefix = "";
		if(!cfg.type || cfg.type == 0){
			prefix = tabId;
		}
		this.makeFieldByType(prefix,cfg.edcmns);
		return true;
	},
	makeFieldByType : function(prefix,edcmns){
		var data = {};
		var oldData = {};
		for(var i=0,count=edcmns.length; i<count; i++){
			var cfg = edcmns[i];
				var tdId = prefix+"_"+cfg.id;
				var tdObj = this.$(tdId);
				if(!tdObj){
					alert("找不到ID为\""+tdId+"\"的单元格对象!");
					break;	
				}
				var val = tdObj.innerText;
				if(!val && (val !== 0 || val !== '0')) val='';
				var _cfg = {val : val};
				tdObj.innerHTML = '';
				var height = tdObj.offsetHeight;
				var width = tdObj.offsetWidth;
				if(height>0){_cfg.height = height;}
				if(width>0){_cfg.width = width;}	
				var type = cfg.type;
				var inputObj = this.getFiledByType(type,_cfg);
				tdObj.appendChild(inputObj);
				data[tdId] = inputObj;
				oldData[tdId] = val;
		}
		var tabId = prefix;
		this.cache[prefix] = data;
		this.oldCache[prefix] = oldData;
	},
	getFiledByType : function(type,cfg){
		if(!type) type=1;
		var ele = null;
		switch(type){
			case 1 :	/* 文本框 */
			ele= this.createTxt(cfg);
			break;
			case 2 : /* 多行文本框 */
			ele= this.createTA(cfg);
			break;
			case 3 : /* 数字框 */
			ele= this.createNTxt(cfg);
			break;
			default:
			 ele= this.createTxt(cfg);
		}
		
		return ele;
	},
	/**
	 * 根据配置创建文本框对象
	 * @params cfg 文本框配置 
	 * @return 返回元素对象
	**/
	createTxt : function(cfg){
		if(!cfg.input) cfg.input = "input";
		if(!cfg.type) cfg.type = "text";		
		var ele = this.createFormEle(cfg);
		ele.style.height=20;
		return ele;
	},
	/**
	 * 根据配置创建文本框对象
	 * @params cfg 文本框配置 
	 * @return 返回元素对象
	**/
	createNTxt : function(cfg){
		if(!cfg.input) cfg.input = "input";
		if(!cfg.type) cfg.type = "text";
		var ele = this.createFormEle(cfg);
		ele.style.height=20;
		ele.onkeyup=V.ipnums;
		return ele;
	},
	/**
	 * 根据配置创建多行文本框对象
	 * @params cfg 文本框配置 
	 * @return 返回元素对象
	**/
	createTA : function(cfg){
		if(!cfg.input) cfg.input = "TEXTAREA";
		
		return this.createFormEle(cfg);
	},
	createFormEle:function(cfg){
		var ele = document.createElement(cfg.input);
		if(cfg.width) ele.style.width = cfg.width-5;	
		if(cfg.height) ele.style.height = cfg.height-5;
		if(cfg.val) ele.value=cfg.val;
		return ele;
	},
	/**
	 * 根据元素ID返回元素对象
	 * @params eleId 元素ID
	 * @return 返回元素对象
	**/
	$ : function(eleId){
		return document.getElementById(eleId);
	},
	/**
	 * 向服务器发送Ajax request 数据
	 * @params action
	 *  var action = {url : 'xx.action',params : {name:'xxx'},ffn:function(data){xxx code;},sfn:function(data){xxx code}}
	**/
	request : function(action){
		var url = action.url;
		var params =  action.params || {};
			Ext.Ajax.request({
			url : url,
			params : params,
			success : function(response){
				var respMsg = response.responseText;
				if(!respMsg) return;
				respMsg = Ext.decode(respMsg);
				if(respMsg.success == false){	//失败后执行 ffn 方法
					if(action.ffn) action.ffn(respMsg);
				}else{
					
					//操作成功后执行 sfn 方法
					if(action.sfn) action.sfn(respMsg);
				}
			},
			failure : function(action){
				var errMsg = '操作失败，请报告管理员！';
				if(!action || !action.result || !action.result.msg){
					Ext.Msg.alert('系统提示',errMsg);
					return;
				}
				//失败后执行 ffn 方法
				if(action.ffn) action.ffn(action.result);		
			}
		});	
	},
	getTabId : function(otherId,repStr){
		return otherId.replace(repStr,'');
	},
	/**
	 * 去空格
	 * @params str 要去空格的字符串
	 * @return 返回去掉空格的字符串
	**/
	trim : function(str){
		return str.replace(/(^\s*)|(\s*$)/g, ""); ;
	},
	/**
	 *  加载数据
	 *  params self 当前点击对象
	 **/
	loadDatas : function(tabCfgs){
		if(!tabCfgs || tabCfgs.length == 0){
			alert("在调用 AuditProjectMgr.loadDatas(tabCfgs)时，没有传入 \"tabCfgs\" 参数，无法加载数据！");	
			return;
		}
		var self = this;
		for(var i=0,count=tabCfgs.length; i<count; i++){
			var tabCfg = tabCfgs[i];
			var tabId = tabCfg.tabId;
			var params = {tab: tabId};
			var data = this.baseParams;
			params.datas = Ext.encode(data);
			function callback(result){
				var totalcount = result.totalcount;
				//-->没有数据就不解析<--//
				if(!totalcount || totalcount == 0) return;
				self.parseDatas(result);
			};
			var action = {url : this.queryUrl,params : params,sfn:callback};
			this.request(action);
		}
	},
	parseDatas : function(result){
		var totalcount = result.totalcount;
		var tabId = result.tab;
		var datas = result.datas;
		if(!datas) return;
		datas = Ext.decode(datas);
		if(!datas || datas.length == 0) return;
		/*--> 如果是可编辑表格和导入表格则从 table 上取UUID <--*/
		if(-1 != tabId.indexOf('ET') || -1 != tabId.indexOf('IT')){
			var tabObj = this.$(tabId);
			if(!datas[0]) return;
			var uuid = datas[0]._id;
			if(uuid) tabObj.setAttribute("uuid",uuid);
			for(var i=0,count=datas.length; i<count; i++){
				var data = datas[i];
				delete data["_id"];
				for(var prop in data){
					var ele = this.$(prop);	
					if(!ele) continue;
					var val = data[prop];
					if(!val || this.trim(val) == '') val = "";
					ele.innerText = val;
				}
			}
			
		}else{ /*-->新增的从选中行上取UUID<--*/
			alert("新增行数据加载在 parseDatas 暂未实现");
		}	
	}
}