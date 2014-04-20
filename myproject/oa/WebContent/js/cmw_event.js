 /**
  * 事件管理对象 
  * 重置表单元素 
  * EventManager.frm_reset(frm,[id,name]);
  * 获取服务器数据
  * EventManager.get(url,action)
  * --------------------------------
  * 保存表单数据，并刷新指定的对象
  * EventManager.frm_save(frm,{type:'tree',cmpt:treeObj})
  * -----------------------------------------------------
  * 删除数据,并刷新指定对象
  * EventManager.deleteData('sys_enabled.action',{type:'tree',cmpt:treeObj});
  * ----------------------------------------------------------------------
  * 起用数据,并刷新指定对象
  * EventManager.enabledData('sys_enabled.action',{type:'tree',cmpt:treeObj});
  * ----------------------------------------------------------------------
  * 禁用数据,并刷新指定对象
  * EventManager.disabledData('sys_enabled.action',{type:'tree',cmpt:treeObj});
  * @type 
  */
var EventManager = {
	enabled_val : 1,//起用
	disabled_val : 0,//禁用
	delete_val : -1,//删除
	/**
	 * 查询数据
	 * @param grid Grid 对象
	 * @param eleIds	要查询的元素ID字符串，<br/>多个以逗号分隔，
	 *              例如："custname,sex,state#1" ，如果要传默认值,请用“#”分隔。如 "state#1"
	 * @param isIds 如果 isIds:true 则 eleIds 是一个元素ID字符串列表。否则，就是一个 JSON 参数对象
	 */
	query : function(grid,eleIds,isIds){
		var params = isIds ? this.parseParams(eleIds) : eleIds;
		if(!grid) throw new Error("在调用 EventManager.query 时,没有提供  Grid 对象!");
		if(!grid.needPage){
			if(!params) params = {};
			params.start = -1;
			params.limit = -1;
		}
		var xtype = grid.getXType();
		if(xtype=='appgrid'){
		 	grid.reload(params);
		}else{
			grid.getStore().reload({params : params});
		}
	},
	/** 
	 * Grid 添加数据
	 * @param url 服务器路径
	 * @param frm Form 对象
	 * @param defaultVal 默认从页面传入的值
	 * @param sfn 成功后的函数
	 * @param ffn 失败后的函数
	 */
	add : function(url,frm,defaultVal,sfn,ffn){
		if(url){	//当传入URL时，新增则加编号
			url = Cmw.getUrl(url);
			Ext.Ajax.request({ //获取编号
							url : url,
							success : function(response){
								if(EventManager.existSystem(response)) return;
								var codeObj = Ext.decode(response.responseText); //将后台JSON字符串转换为JSON对象
								var frmObj = frm.getForm();
								frmObj.reset();
								var values = {};
								var xtype = frm.getXType();
								if(xtype=='appform'){
									if(defaultVal) Ext.apply(codeObj,defaultVal);
									if(codeObj) frm.setFieldValues(codeObj);
									values = frm.getValues();
								}else{
									frmObj.setValues(codeObj);
									if(defaultVal) frmObj.setValues(defaultVal);
									values = frmObj.getValues();
								}
								if(sfn) {
									sfn(values);
								}
							},failure : function(response){
								if(EventManager.existSystem(response)) return;
								Ext.Msg.alert("失败","获取数据失败！");
								var errObj = (response && response.responseText) ? Ext.decode(response.responseText) : {};
								if(ffn) ffn(errObj);
							}
			});
		}
	
	},

	/**
	 * 通过指定的 URL 获取服务器数据 EventManager.get(url,{params:{id:'1',name : '张三'},sfn:function(json_data){}})
	 * @param url 服务器URL地址
	 * @param action 动作参数对象 
	 */
	get : function(url,action){
		if(!action){
			ExtUtil.error({title:Msg_SysTip.title_apperr,msg:Msg_SysTip.msg_nohaveAction});
			return;
		}
		url = Cmw.getUrl(url);
		Ext.Ajax.request({
			url : url,
			params : action.params || {},
			success : function(response){
				var isExist = EventManager.existSystem(response);
				if(isExist) return;
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
			failure : function(_action){
				var isExist = EventManager.existSystem(_action);
				if(isExist) return;
				var errMsg = '操作失败，请报告管理员！';
				if(_action && _action.result && _action.result.msg){
					errMsg = _action.result.msg;
				}
				Ext.Msg.alert('系统提示',errMsg);
				//失败后执行 ffn 方法
				if(action.ffn) action.ffn(action.result);
			}
		});
	},
	/**  
	 * Form 表单 保存数据
	 * @param frm 表单ID
	 * @param cfg 要加载的对象, 
	 * 	cfg 参数使用方式:
	 *  1. cfg = {type:'win',cmpt:winObj}	// 表示传入的是 Window 对象
	 * 	2. cfg = {type:'grid',cmpt:gridObj}	// 表示传入的是 Grid 对象
	 * // 表示传入的是 Tree 对象,tb：工具栏对象，
	 *  3. cfg = {type:'tree',cmpt:treeObj,tb:toolBarObject,beforeMake:function(formData){},sfn:function(){formData},ffn:function(formData){}}	
	 */
	frm_save : function(frm,cfg){
		var curForm = (frm && frm.getXTypes()) ? frm.getForm() : Ext.getCmp(frm).getForm();
		if(!curForm.isValid() || (cfg.valid && !cfg.valid(frm))) return;
		var tb = cfg.tb;
		if(tb) tb.disable();
		var frmEl = frm.el;
		frmEl.mask('Loading', 'x-mask-loading');
		var url = curForm.url;
		url = Cmw.getUrl(url);
		var params = (frm.getXType()=="appform") ? frm.getValues() : curForm.getValues();
		if(cfg.beforeMake) cfg.beforeMake(params);	//保存数据前，表单数据处理
		Ext.Ajax.request({
			url : url,
			params : params || {},
			success : function(response){
				var responseText = response.responseText;
				var action = Ext.decode(responseText);
				var success = action.success;
				if(success){
					var formDatas = execute(Msg_SysTip.title_appconfirm,curForm,action,true);
					FormDiyManager.saveCustFieldVals(frm,formDatas);
				}else{
					execute(Msg_SysTip.title_appwarn,curForm,action,false);
				}
				if(EventManager.existSystem(response)) return;
			},failure : function(action){
				if(EventManager.existSystem(action)) return;
				execute(Msg_SysTip.title_appwarn,curForm,action,false);
			}
		});
		
		function execute(title,form,action,isSusscess){
			if(action.msg) Ext.tip.msg(title,action.msg);
			frmEl.unmask();
			if(tb) tb.enable();
			EventManager.refresh(cfg);
			var formDatas = form.getValues();
			if(action.id){
			 formDatas.id = action.id;
			}
			if(action.data){
			  if(Ext.isString(action.data)) action.data = Ext.decode(action.data);
			  Ext.apply(formDatas,action.data);
			}else{
				 Ext.apply(formDatas,action);
			}
			if(isSusscess && cfg.sfn){
				cfg.sfn(formDatas);
			}
			if(!isSusscess && cfg.ffn){
				cfg.ffn(formDatas);
			}
			return formDatas;
		}
	},
	/**
	 * 刷新组件，重新加载数据
	 * @param {} cfg 要刷新的对象 例：{type:'tree',cmpt:apptree}
	 */
	refresh : function(cfg){
		if(!cfg.type || !cfg.cmpt) return;
		try{
			console.log('cfg：',cfg.type+','+cfg.cmpt);
			if(cfg.type == "win"){
				cfg.cmpt.hide();
			}else if(cfg.type == "tree"){
				cfg.cmpt.reload();//reload
				cfg.cmpt.expandAll();
			}else{
				cfg.cmpt.reload();
			}
		}catch(ex){
			Ext.tip.msg(Msg_SysTip.title_apperr, Msg_SysTip.msg_nohaveReloadObj);
			//ExtUtil.error({title:Msg_SysTip.title_apperr,msg:Msg_SysTip.msg_nohaveReloadObj});
		}
	},
	/**
	 * 重置表单数据，如果有传 fields 数组，将不会重置这些 fields 值
	 * @param  frmPnl 表单面板对象
	 * @param fields 要保留值的输入元素对象
	 */
	frm_reset : function(frmPnl,fields){
		var cacheVals = [];
		if(fields){
			for(var i=0,count=fields.length; i<count; i++){
				cacheVals[i] = fields[i].getValue();
			}
		}
		frmPnl.getForm().reset();
		if(null != cacheVals && cacheVals.length>0){
			for(var i=0,count=cacheVals.length; i<count; i++){
				fields[i].setValue(cacheVals[i] ? cacheVals[i] : "");
			}
		}
	},
	/**
	 * 根据指定的元素ID和 baseId,返回 json 参数对象	
	 * @param {} eleIds 元素ID
	 */
	parseParams : function(eleIds){
		if(!eleIds || eleIds.length==0) throw new Error("在调用 EventManager.parseParams 时,没有提供要取值的元素ID!");
		eleIds = eleIds.split(",");
		var params = {};
		for(var i=0; i<eleIds.length; i++){
			var eleId = eleIds[i];
			var val = null;
			if(eleId.indexOf("#")!=-1){
				eleId = eleId.split("#");
			 	val = eleId[1];
			 	eleId = eleId[0];
			}else{
				val = Ext.get(eleId).getValue();
			} 
			if(val && val != '请在此输入搜索关键字' && val != ""){
				params[eleId] = val;
			}else{
				params[eleId] = null;
			}
		}
		params.start = 0;
		params.limit = PAGESIZE;
//		for(var key in params){
//			alert(key+":"+params[key]);
//		}
		return params;
	},
	/**
	 * 删除指定的记录
	 * @param url	URL
	 * @param cfg	数据删除成功后，要处理的对象。例如：刷新动作
	 */
	deleteData : function(url,cfg){
		this.setEnabled(url, this.delete_val, cfg);
	},
	/**
	 * 起用指定的记录
	 * @param url	URL
	 * @param cfg	数据起用成功后，要处理的对象。例如：刷新动作
	 */
	enabledData : function(url,cfg){
		var cmpt = cfg.cmpt;
		this.setEnabled(url, this.enabled_val, cfg);
	},
	/**
	 * 禁用指定的记录
	 * @param url	URL
	 * @param cfg	数据禁用成功后，要处理的对象。例如：刷新动作
	 */
	disabledData : function(url,cfg){
		this.setEnabled(url, this.disabled_val, cfg);
	},
	/**
	 * 数据验证
	 * @param {} url	url 地址
	 * @param {} isenabled	数据标识
	 * @param {} cfg	参数配置
	 * @return {Boolean}	
	 */
	enabledValid : function(url,isenabled,cfg){
		var type = cfg.type;
		var cmpt = cfg.cmpt;
		ids = cmpt.getSelId();
		if(!ids){
			ExtUtil.info({msg:'请选择要处理的数据！'});
		  return false;
		}
		if(!url){
			ExtUtil.info({msg:'没有指定服务器 URL ！'});
			return false;
		}
		var errMsg = null;
		if(type==CMPT_TYPE.TREE){
			var nodeTxt = cmpt.getSelText();
			if(isenabled == this.enabled_val && nodeTxt.indexOf(Msg_AppCaption.disabled_text) == -1){
				errMsg = Msg_SysTip.msg_enabledstate;
			}else if(isenabled == this.disabled_val && nodeTxt.indexOf(Msg_AppCaption.disabled_text) != -1){
				errMsg = Msg_SysTip.msg_disabledstate;
			}
		}else if(type==CMPT_TYPE.GRID){
			var data = cmpt.getCmnVals("isenabled");
			if(!data || !data.isenabled) return true;
			if(isenabled == this.enabled_val && data.isenabled == this.enabled_val){
				errMsg = Msg_SysTip.msg_record_enabledstate;
			}else if(isenabled == this.disabled_val && data.isenabled == this.disabled_val){
				errMsg = Msg_SysTip.msg_record_disabledstate;
			}
		}
		if(errMsg){
			 ExtUtil.alert({msg:errMsg});
			return false;
		}
		return true;
	},
	/**
	 * 通过指定的 URL 获取服务器数据 EventManager.get(url,{sfn:function(json_data){}})
	 * @param url 服务器URL地址
	 * @param isenabled	删除:-1，起用：1，禁用：0
	 * @param cfg	要重新加载的组件配置 例如：{type:'tree',cmpt:apptree}
	 */
	setEnabled : function(url,isenabled,cfg){
		if(!this.enabledValid(url,isenabled,cfg)) return;
		var cmpt = cfg.cmpt;
		var ids = cmpt.getSelId();
		url = Cmw.getUrl(url); 
		url += "&ids="+ids+"&isenabled="+isenabled;
		var msg = Msg_SysTip.msg_deleteData;
		if(isenabled == this.enabled_val){
			msg = Msg_SysTip.msg_enabledData;
		}else if(isenabled == this.disabled_val){
			msg = Msg_SysTip.msg_disabledData;
		}
		ExtUtil.confirm({title:Msg_SysTip.title_appconfirm,msg:msg,fn:function(btn){
			if(!btn || btn != 'yes') return;
			Ext.Ajax.request({ 
				url : url,
				success : function(response){
					if(EventManager.existSystem(response)) return;
					if(cfg.self){
						if(!cfg.self.refresh){
							ExtUtil.error({msg:'传入 self 参数时，必须在 self 对象中加入 refresh(optionType,data) 方法！'});
						}else{
							cfg.self.refresh(cfg.optionType,{ids:ids});
						}
					}else{
						EventManager.refresh(cfg);					
					}
					Ext.tip.msg(Msg_SysTip.title_appconfirm, Msg_SysTip.msg_dataSucess);
//					ExtUtil.alert({title:Msg_SysTip.title_appconfirm,msg:Msg_SysTip.msg_dataSucess,fn:function(){
//						EventManager.refresh(cmpt);
//					}});
				},failure : function(response){
					if(EventManager.existSystem(response)) return;
//					ExtUtil.error({title:Msg_SysTip.title_appconfirm,msg:Msg_SysTip.msg_dataFailure});
					Ext.tip.msg(Msg_SysTip.title_appconfirm, Msg_SysTip.msg_dataFailure);
				}
			});
		}});
	},
	EXPORT_HREF_ID : 'EXPORT_HREF_ID',
	/**
	 * 导出 Excel 报表
	 * @param token 菜单ID
	 * @param params 页面查询参数
	 * @param url 导出的服务器地址，如不提供则默认为：./servlet/XlsReportServlet
	 */
	doExport : function(token,params,url){
		if(!token){
			ExtUtil.error({msg:'必须传入 token 参数'});
			return;
		}
		if(!url) url = "./servlet/XlsReportServlet";
		var exportUrl = this.getExportUrl(token,params,url);
		this.startDownLoad(exportUrl);
	},
	/**
	 * 获取导出的 URL 
	 * @param {} token 导出模板标识（默认菜单ID）
	 * @param {} params 导出的参数
	 * @param {} url	导出的服务器地址
	 * @return {}	将参数追加到URL地址后，并返回此地址
	 */
	getExportUrl : function(token,params,url){
		params["reportTemplat_token"] = token;
		var _paras = Ext.urlEncode(params);
		var exportUrl = url+"?"+_paras;
		return exportUrl;
	},
	/**
	 * 下载
	 * @param {} url URL地址
	 * @param {} params 参数 如果没有参数，可为空
	 */
	downLoad : function(url,params){
		if(!url){
			ExtUtil.error({msg : '下载时，必须提供 URL 参数!'});
			return;
		}
		if(params){
			var _paras = Ext.urlEncode(params);
			url += "?"+_paras;
		}
		this.startDownLoad(url);
	},
	/**
	 * 开始下载
	 * @param {} url 下载的URL
	 */
	startDownLoad : function(url){
		var _this = this;
		var ele = Ext.get(this.EXPORT_HREF_ID);
		if(!ele){
			var body = Ext.getBody();
			body.createChild({id:this.EXPORT_HREF_ID,tag:'A',href:'#',target:"_blank",style:"display:none;",html:'专门用来导出或下载报表的A标记'});
			ele = Ext.get(this.EXPORT_HREF_ID);
			if(!ele){
				var task = Ext.util.DelayedTask(function(){
					_this.linkClick(_this.EXPORT_HREF_ID,url);
				});
				task.delay(300);
			}else{
				_this.linkClick(_this.EXPORT_HREF_ID,url);
			}
		}else{
			_this.linkClick(_this.EXPORT_HREF_ID,url);
		}
	},
	/**
	 * 触发超链接单击事件
	 * @addDate 2012-11-09
	 * @param {} eleId	超链接ID
	 * @param url 超链接地址
	 */
	linkClick : function(eleId,url){
		var ele = Ext.isString(eleId) ? Cmw.$(eleId) : eleId;
		ele.href= url;
		if(document.all){  
            ele.click();  
        }else{  
            var evt = document.createEvent("MouseEvents");  
            evt.initEvent("click", true, true);  
            ele.dispatchEvent(evt);  
        }  
	},
	/**
	 * 退出系统
	 * 	
	 */
	existSystem : function(response){
		if(!response) return;
		var responseText = response.responseText;
		if(!responseText) return;
		var json = Ext.decode(responseText);
		var success = json.success;
		var msg = json.msg;
		if(!success && (msg && msg == 'user.session.timeout.break')){
 			var msg = Msg_SysTip.msg_user_exist;
 			msg = msg.replace('CURENT_EMP',CURENT_EMP);
			ExtUtil.alert({title:Msg_SysTip.title_appconfirm,msg:msg,fn:function(){
				 EventManager.exist();
			}});
			return true;
		}
		return false;
	},
	/**
	 * 退出
	 */
	exist : function(){
	 	window.location.href = "./logout.action?TXR_SWITCHINGSYSTEM=TXR_SWITCHINGSYSTEM";
	},
	/**
	 * 点击桌面模块单条数据
	 * @param tabId TabPanel 对象ID
	 * @param paramsStr 字符串JSON参数
	 */
	gotoModule : function(tabId,paramsStr){
		var params = Ext.decode(paramsStr);
		var apptabtreewinId = params['apptabtreewinId'];
		if(tabId == CUSTTAB_ID.flow_auditMainUITab.id){/*如果是跳流程审批面板*/
			var url =  CUSTTAB_ID.flow_auditMainUITab.url;
			var title =  '业务审批';
			Cmw.activeTab(apptabtreewinId,tabId,params,url,title);
		}else{
			Cmw.activeTab(apptabtreewinId,tabId,params);
		}
	},
	/**
	 * 供 AppTabTreeWin 使用
	 * @type 
	 */
	WinMgr : {
		cacheArr : [],
		/**
	 	 * 绑定调整 Window 的 z-index 的事件函数(专供 AppTabTreeWin 使用)
		 * @return {}
		 */
		attachWinZindexListeners : function(){
			return {
				beforeshow : function(win){
			  		EventManager.maxWinZindex(win);
				},
				beforehide : function(win){
			  		EventManager.restoreWinZindx(win);
				},
				activate: function(win){
					EventManager.WinMgr.activateWin(win);
				},
				deactivate : function(win){
					EventManager.WinMgr.deactivateWin(win);
				},
				close : function(win){
					EventManager.WinMgr.closeWin(win);
				}
			};
		},
		activateWin : function(win){
			var winId = win.id;
			var cacheArr = EventManager.WinMgr.cacheArr;
			if(cacheArr && cacheArr.length > 0){
				var exist = false;
				for(var i=0,count=cacheArr.length; i<count; i++){
					var cacheData = cacheArr[i];
					if(cacheData.parentWin && cacheData.parentWin == winId){
						cacheData.activate = true;
						if(cacheData.subWin){
							var subWinId = cacheData.subWin;
							var subWindow = Ext.getCmp(subWinId);
							if(subWindow) subWindow.show();
						}
						exist = true;
						break;
					}
				}
				if(!exist) cacheArr[cacheArr.length] = {parentWin : winId, activate : true, subWin : null};
			}else{
				cacheArr[cacheArr.length] = {parentWin : winId, activate : true, subWin : null};
			}
		},
		deactivateWin : function(win){
			var winId = win.id;
			var cacheArr = EventManager.WinMgr.cacheArr;
			if(!cacheArr || cacheArr.length == 0) return;
			for(var i=0,count=cacheArr.length; i<count; i++){
				var cacheData = cacheArr[i];
				if(cacheData.parentWin && cacheData.parentWin == winId){
					cacheData.activate = false;
					if(cacheData.subWin){
						var subWinId = cacheData.subWin;
						var subWindow = Ext.getCmp(subWinId);
						if(subWindow) subWindow.hide();
					}
					break;
				}
			}
		},
		closeWin : function(win){
			var winId = win.id;
			var cacheArr = EventManager.WinMgr.cacheArr;
			if(!cacheArr || cacheArr.length == 0) return;
			for(var i=0,count=cacheArr.length; i<count; i++){
				var cacheData = cacheArr[i];
				if(cacheData.parentWin && cacheData.parentWin == winId){
					if(cacheData.subWin){
						var subWinId = cacheData.subWin;
						var subWindow = Ext.getCmp(subWinId);
						if(subWindow) subWindow.close();
					}
					cacheArr.splice(i,1);
					break;
				}
			}
		},
		addAcvitateSubWin :  function(win){
			var winId = win.id;
			var cacheArr = EventManager.WinMgr.cacheArr;
			if(!cacheArr || cacheArr.length == 0) return;
			for(var i=0,count=cacheArr.length; i<count; i++){
				var cacheData = cacheArr[i];
				if(cacheData.activate){
					cacheData.subWin = winId;
					break;
				}
			}
		},
		removeAcvitateSubWin : function(win){
			var winId = win.id;
			var cacheArr = EventManager.WinMgr.cacheArr;
			if(!cacheArr || cacheArr.length == 0) return;
			for(var i=0,count=cacheArr.length; i<count; i++){
				var cacheData = cacheArr[i];
				if(cacheData.activate && cacheData.subWin == winId){
					cacheData.subWin = null;
					break;
				}
			}
		}
	},

	/**
	 * 绑定调整 Window 的 z-index 的事件函数(供 MyWindow,AbsEditWindow,AbsDetailWindow使用)
	 * @return {}
	 */
	attachWinZindexListeners : function(){
		return {
			beforeshow : function(win){
		  		EventManager.maxWinZindex(win);
		  		EventManager.WinMgr.addAcvitateSubWin(win);
			},
			beforehide : function(win){
		  		EventManager.restoreWinZindx(win);
		  		EventManager.WinMgr.removeAcvitateSubWin(win);
			},
			beforeclose : function(win){
				EventManager.restoreWinZindx(win);
		  		EventManager.WinMgr.removeAcvitateSubWin(win);
			}
		};
	},
	maxWinZindex : function(win){
		var zseed = Ext.WindowMgr.zseed;
  		if(zseed <= maxZseed){/* maxZseed 定义在 constant.js 中*/
  			maxZseed++;
  			Ext.WindowMgr.zseed = maxZseed;
  		}
	},
	restoreWinZindx : function(win){
		var zseed = Ext.WindowMgr.zseed;
  		if(zseed > minZseed){/* minZseed 定义在 constant.js 中*/
  			Ext.WindowMgr.zseed = minZseed;
  		}
	}
}

/**
 * 贷款资金收付业务锁管理器
 * @type 
 */
var LockManager = {
	/**
	 * 钥匙常量 
	 * @type 
	 */
	keys : {
		/**
		 * 个人放款锁钥匙
		 * @type String	LockManager.keys.LoanInvoceKey
		 */
		LoanInvoceKey : 'LoanInvoceKey',
		/**
		 * 企业放款锁钥匙
		 * @type String	LockManager.keys.LoanInvoceKey
		 */
		EloanInvoceKey : 'EloanInvoceKey',
		/**
		 * 正常收款锁钥匙
		 * @type String		LockManager.keys.NomalPayKey
		 */
		NomalPayKey : 'NomalPayKey',
		/**
		 * 逾期收款锁钥匙
		 * @type String		LockManager.keys.OverduePayKey
		 */
		OverduePayKey : 'OverduePayKey',
		/**
		 * 提前收款锁钥匙
		 * @type String		LockManager.keys.AdvancePayKey
		 */
		AdvancePayKey : 'AdvancePayKey',
		/**
		 * 收取放款手续费锁钥匙
		 * @type String		LockManager.keys.FreePayKey
		 */
		FreePayKey : 'FreePayKey'
	},
	activeKey : null,
	/**
	 * 申请锁住 LockManager.applyLock(LockManager.keys.LoanInvoceKey);
	 * @param {} key 锁钥匙
	 */
	applyLock : function(key){
		this.activeKey = key;
	},
	/**
	 * 
	 * 解锁(钥匙为调用 applyLock 时指定的锁钥匙)
	 */
	releaseLock : function(){
		var _this = this;
		EventManager.get('./sysLock_releaseLock.action',{params:{key:this.activeKey},sfn:function(json_data){
		 	_this.activeKey = null;
		}});
	},
	/**
	 * 验证是否有锁住选中的客户 (钥匙为调用 applyLock 时指定的锁钥匙)
	 * @param grid 要取客户数据的Grid
	 * @param cmn 客户的列名
	 * @param callback 回调函数,当没有锁住时会调用此方法触相指定的业务
	 */
	isLock : function(grid,cmn,callback){
		var selRows = grid.getSelRows();
		if(null == selRows || selRows.length == 0) return;
		var customerArr = [];
		for(var i=0,count=selRows.length; i<count; i++){
			var selRow = selRows[i];
			var customer = selRow.get(cmn);
			if(!customer) continue;
			customerArr[customerArr.length] = customer;
		}
		var customers = customerArr.join(",");
		EventManager.get('./sysLock_isLock.action',{params:{key:this.activeKey,customers:customers},sfn:function(json_data){
		 	if(callback) callback();
		 },ffn : function(json_data){
		 	var lockCustomers = json_data.lockCustomers;
		 	if(!lockCustomers || lockCustomers.length == 0) return;
		 	var errArr = [];
		 	for(var i=0,count=lockCustomers.length; i<count; i++){
		 		var lockCustomer = lockCustomers[i];
		 		var userName = lockCustomer.userName;
		 		var customers = lockCustomer.customers;
		 		errArr[i] = "用户\""+userName+"\" 锁定了以下客户：<br/>["+customers+"],<br/>请等待其操作完成后再处理业务!";
		 	}
		 	var errMsg = errArr.join("<br/>");
		 	ExtUtil.alert({msg : errMsg});
		 }});
	}
};
