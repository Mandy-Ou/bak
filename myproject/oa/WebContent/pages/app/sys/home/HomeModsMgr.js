/**
 * 系统首页桌面模块管理
 * 
 * @author 程明卫
 * @date 2013-03-08
 */
define(function(require, exports) {
	exports.viewUI = {
		tab : null,
		params : null,
		HEIGHT : 500,
		col_one_id : 'COL_ONE_',
		col_two_id : 'COL_TWO_',
		col_three_id : 'COL_THREE_',
		DIV_ID :'DIV_CONTENT_',
		show : function(tab,params){
			this.tab = tab;
			this.setParams(params);
			var portalPnl = this.createPortal();
			tab.add(portalPnl);
		},
		setParams : function(params){
			this.params = params;
			var sysId = this.params.sysid;
			this.col_one_id += sysId;
			this.col_two_id += sysId;
			this.col_three_id += sysId;
		},
		createPortal : function(){
			var tools = this.createTools();
			var portalPnl = new Ext.Panel({
				 layout:'border',
				 border : false,
				 layoutConfig:{animate:true},
				 items : [{
		            xtype:'portal',
		            region: 'center',
		            items : [{
		            	id:this.col_one_id,
		                columnWidth:.5,
		                style:'padding:0px 5px 5px 5px'
            		},{
            			id:this.col_two_id,
		                columnWidth:.5,
		                style:'padding:0px 5px 5px 5px'
            		}]
	            }]
				});
				var _this = this;
				portalPnl.addListener('render',function(pnl){
					var el = _this.tab.getEl();
					var width = el.getComputedWidth();
					var height = el.getComputedHeight();
					if(!height || height < _this.HEIGHT) height = _this.HEIGHT;
					portalPnl.setHeight(height+50);
					_this.loadModules();
				});
			return portalPnl;
		},
		/**
		 * 创建工具栏
		 */
		createTools : function(){
			var _this = this;
			var tools = [{
		        id:'refresh',
		        handler: function(e, target, panel){
		        	var data = panel.data;
		        	_this.loadContent(data);
		        }
		    },{
		        id:'close',
		        handler: function(e, target, panel){
		            panel.ownerCt.remove(panel, true);
		        }
		    }];
		    return tools;
		},
		/**
		 * 加载桌面模块
		 */
		loadModules : function(){
			var _this = this;
			var col_one_cmpt = Ext.getCmp(this.col_one_id);
			var col_two_cmpt = Ext.getCmp(this.col_two_id);
			EventManager.get('./sysUserMod_getDatas.action',{params:{},sfn:function(json_data){
			  var totalSize = json_data.totalSize;
			  if(!totalSize || totalSize == 0) return;
			  var list = json_data.list;
			  for(var i=0,count=list.length; i<count; i++){
			  	var data = list[i];
			  	var module = _this.createModule(data);
			  	if((i+1)%2 == 0){
			  		col_two_cmpt.add(module);
			  	}else{
			  		col_one_cmpt.add(module);
			  	}
			  }
			  _this.tab.doLayout();
			}});
		},
		/**
		 * 创建模块对象并返回
		 */
		createModule : function(data){
			var module = null;
			var tools = this.createTools();
			var code = data.code;
			var title = data.title;
			var cls = data.cls;
			var dispType = data.dispType;
			var url = data.url;
			var urlparams = data.urlparams;
			var dataTemp = data.dataTemp;
			var divId = this.DIV_ID+code;
			var cfg = { layout:'fit',border : false,style:'margin-top:5px',data:data,tools: tools,html:'<div id="'+divId+'" class="contentDiv"></div>',
					listeners:{
						render : function(pnl){Cmw.mask(pnl,Msg_SysTip.msg_loadingDatas)}
					}
				};
			if(code){
				cfg.id = code;
			}
			if(title){
				cfg.title = title;
			}
			this.loadContent(data);
			
			module = new Ext.ux.Portlet(cfg);
			return module;
		},
		/**
		 * 获取桌面模块数据
		 */
		loadContent : function(data){
			//loadType#I,dataCode,dispcmns
			var _this = this;
			var errMsg = [];
			var code = data.code;
			var loadType = data.loadType;
			var dataCode = data.dataCode;
			var dispcmns = data.dispcmns;
			var dataTemp = data.dataTemp;
			var url = data.url;
			var urlparams = data.urlparams;
			var ismore = data.ismore;
			var moreUrl = data.moreUrl;
			var msgCount = data.msgCount;
			if(!loadType){
				errMsg[errMsg.length] = '加载方式参数 "loadType" 为空!';
			}
			if(!dataCode){
				errMsg[errMsg.length] = '数据代码参数 "dataCode" 为空!';
			}
			if((loadType && (loadType == 1 || loadType == 2)) && !dispcmns){
				errMsg[errMsg.length] = '显示列名参数 "dispcmns" 为空!';
			}
			if(!dataTemp){
				errMsg[errMsg.length] = '数据模板参数 "dataTemp" 为空!';
			}
			if(!url){
				errMsg[errMsg.length] = '链接地址参数 "url" 为空!';
			}
			
			if(errMsg && errMsg.length > 0){
				ExtUtil.alert({msg:errMsg.join('<br/>')});
				return;
			}
			var cmpt = Ext.getCmp(code);
			if(cmpt){
				Cmw.mask(cmpt,Msg_SysTip.msg_loadingDatas);
			}
			var params = {loadType:loadType, dataCode:dataCode, dispcmns:dispcmns,msgCount:msgCount};
			EventManager.get('./sysUserMod_getContents.action',{params:params,sfn:function(json_data){
				var html = "<span style='color:red;text-align:center;'>暂无数据...</span>";
				var divEle = Ext.get(_this.DIV_ID+code);
				if(!json_data || json_data.totalSize == 0){
					if(divEle) divEle.update(html);
					var cmpt = Ext.getCmp(code);
					if(cmpt) Cmw.unmask(cmpt);
					 return;
				}
				var totalSize = json_data.totalSize;
				var list = json_data.list;
				var arr = [];
				for(var i=0; i<totalSize; i++){
					var row = list[i];
					arr[i] = _this.parseTempCode(row,url,urlparams,dataTemp);
				}
				if(arr && arr.length > 0){
					var codesArr = [
						'<table class="newsList" cellpadding="0" cellspacing="0" rules="rows"><tbody>',
						arr.join(""),
						'</tbody></table>'
					];
					if(ismore == 1){/*加载更多*/
						codesArr[codesArr.length] = _this.getMoreCode(moreUrl);
					}
					html = codesArr.join("");
				}
				if(divEle) divEle.update(html);
				var cmpt = Ext.getCmp(code);
				if(cmpt) Cmw.unmask(cmpt);
			}});
		},
		parseTempCode : function(data,url,urlparams,dataTemp){
			var apptabtreewinId = this.params["apptabtreewinId"];
			//Cmw.activeTab(apptabtreewinId,url,params);
			var content = dataTemp;
			for(var key in data){
				var val = data[key] || "&nbsp;";
				var cmn = '{'+key+'#MONEY}';
				if(content.indexOf(cmn) != -1){
					val = Cmw.getThousandths(val);
					content = content.replace(cmn, val);
				}else{
					content = content.replace('{'+key+'}', val);
				}
			}
			var _params = this.params;
			if(urlparams){
				var arr = urlparams.split(',');
				if(arr && arr.length>0){
					var queryparams = {};
					for(var i=0,count=arr.length; i<count; i++){
						var key = arr[i];
						var val = '';
						if(key.indexOf('#') != -1){
							var kvs = key.split('#');
							key = kvs[0];
							val = kvs[1];
						}else{
							val = data[key];
						}
						queryparams[key] = val;
					}
					_params[DESK_MOD_TAG_QUERYPARAMS_KEY] = queryparams;
				}
			}
			var goToFun = this.getGotoUrl(url,_params);
			content = content.replace("{GO_TO}",goToFun);
			return content;
		},
		/**
		 * 获取单条数据点击时的URL链接
		 */
		getGotoUrl : function(url,_params){
			if(url == CUSTTAB_ID.flow_auditMainUITab.id){/*如果是跳流程审批面板*/
				if(!_params["applyId"]) _params["applyId"] = _params["id"];
				if(!_params["procId"]) _params["procId"] = null;
				var apptabtreewinId = _params["apptabtreewinId"];
				var params = this.params;
				params =_params[DESK_MOD_TAG_QUERYPARAMS_KEY];
				params["sysId"] = _params["sysid"];
				params["apptabtreewinId"] = apptabtreewinId;
				params["isnewInstance"] = true;
				_params = params; 
			}
			var paramsStr = Ext.encode(_params);
			var goToFun = "EventManager.gotoModule('"+url+"','"+paramsStr+"');";
			return goToFun;
		},
		/**
		 * 获取更多Code
		 */
		getMoreCode : function(moreUrl){
			var _params = this.params;
			if(_params[DESK_MOD_TAG_QUERYPARAMS_KEY]){
				_params[DESK_MOD_TAG_QUERYPARAMS_KEY] = null;
			}
			var paramsStr =  Ext.encode(_params);
			var html = '<div class="moreDiv"><span><a href="#" onclick=EventManager.gotoModule(\''+moreUrl+'\',\''+paramsStr+'\')>更多...</a></span></div>';
			return html;
		},
		/**
		 * 根据模块编号添加模块
		 * @param modCode 模块编号
		 */
		addModule : function(modCode){
		
		}
	}
});
	