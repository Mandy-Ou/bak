/**
 * 流程审批主页面 ---> 业务办理主界面
 * @author 程明卫
 * @date 2012-12-26
 */
define(function(require, exports) {
	exports.moduleUI = {
		appPanel : null,
		processDivId : null,
		processImageId : null,
		processDivIdPrefix : 'detailProcessDiv',
		processImgIdPrefix : 'detailProcessImg',
		processNodeIdPrefix : 'detailProcessNode',
		processNodeIds : null,
		highLightDatas : {}, /*缓存当前高亮节点的审批人信息数据*/
		toolTip : null,/*节点提示对象*/
		params : null,
		wh : [],
		/**
		 * 获取业务模块
		 * @param params 由菜单点击所传过来的参数
		 * 	{tabId : tab 对象ID,apptabtreewinId : 系统程序窗口ID,sysid : 系统ID} 
		 */
		getModule : function(params){
			if(!this.appPanel){
				this.processDivId = Ext.id(null,this.processDivIdPrefix);
				this.processImageId = Ext.id(null,this.processImgIdPrefix);
				var html = '<div id="'+this.processDivId+'" style="width:100%;height:100%;"><image id="'+this.processImageId+'"  style="position:relative; left:0px; top:0px;"></div>';
				this.appPanel = new Ext.Panel({autoScroll:true,html:html,border:false});
				this.show(params);
			}
			return this.appPanel;
		},
		/**
		 * 设置参数
		 */
		setParams : function(params){
			this.params = params;
		},
		/**
		 * 显示面板并加载数据
		 */
		show : function(params){
			this.setParams(params);
			if(this.appPanel.rendered){
				this.loadData();
			}else{
				var _this = this;
				this.appPanel.addListener("render",function(pnl){
					var width = CLIENTWIDTH - 225;
					var height = CLIENTHEIGHT - 190;
					_this.loadData();
					pnl.setWidth(width);
					pnl.setHeight(height);
				});
			}
		},
		/**
		 * 
		 */
		loadData : function(){
//			Cmw.print("ProcessImgMode.loadData invoke ... ");
			var pdid = this.params.pdid;
			var url = "./sysBussNode_processImage.action?pdid="+pdid;
			var processImageId = this.processImageId;
		
			var procId = this.params.procId;
			if(!pdid) return;
			var _this = this;
			var params = {pdid : pdid, procId:procId };
			EventManager.get('./sysBussNode_getExePostion.action',{params:params,sfn:function(json_data){
				_this.removeNodeInfos();
				var imgEle = Ext.get(processImageId);
				imgEle.set({src:url});
				_this.appPanel.setWidth(_this.wh[0]);
				_this.appPanel.setHeight(_this.wh[1]);
				var datas = json_data.datas;
				if(null == datas || datas.length == 0) return;
				_this.processNodeIds = [];
				_this.highLightDatas = {};
				var htmlArr = [];
				for(var i=0,count=datas.length; i<count; i++){
					var data = datas[i];
					var nodeHtml = _this.getNodeHtml(data);
					if(nodeHtml) htmlArr[htmlArr.length] = nodeHtml;
				}
				Cmw.$(_this.processDivId).innerHTML += htmlArr.join("");
				_this.addToolTipToNodes();
			}});
		},
		/**
		 * 为当前的待办节点添加处理人信息
		 */
		addToolTipToNodes : function(){
			var _this = this;
			if(!_this.toolTip)_this.toolTip = _this.createNodeTip();
			if(!_this.highLightDatas) return;
			var task = new Ext.util.DelayedTask(function(){
				for(var hightLightNodeId in _this.highLightDatas){
					var ele = Ext.get(hightLightNodeId);
					ele.on('mouseover',function(e,targetEle,obj){
						var id = targetEle.id;
						var taskInfos = _this.highLightDatas[id];
						if(taskInfos && taskInfos.length > 0){
							var htmlArr = [];
							for(var i=0,count=taskInfos.length; i<count; i++){
								var taskInfo = taskInfos[i];
								var userName = taskInfo.userName;
								var deptName = taskInfo.deptName;
								if(!userName) continue;
								if(!deptName) deptName = "未知部门";
								var html = "<span style='margin-left:25px;color:red'>"+userName+"【"+deptName+"】</span>";
								htmlArr[i] = html;
							}
							_this.toolTip.showBy(id);
							var tipContentId = _this.toolTip.tipContentId;
							var htmlCodes = "<span style='font-weight:bold;color:blue;'>当前环节审批人信息：</span><br/>"+htmlArr.join("<br/>");
							Ext.get(tipContentId).update(htmlCodes);
						}
					},this);
				}
			});
			task.delay(200);
		},
		removeNodeInfos : function(){
			if(!this.processNodeIds || this.processNodeIds.length == 0) return;
			for(var i=0,count=this.processNodeIds.length; i<count; i++){
				var eleId = this.processNodeIds[i];
				var ele = Ext.get(eleId);
				if(!ele) continue;
				ele.remove();
			}
			if(this.toolTip) this.toolTip = null;
			this.highLightDatas = null;
		},
		getNodeHtml : function(data){
			var x = data.x-2;
			var y = data.y-2;
			var width = data.width;
			var height = data.height;
			var color = 'red';
			var divId = Ext.id(null,this.processNodeIdPrefix);
			this.processNodeIds[this.processNodeIds.length] = divId;
			this.highLightDatas[divId] = data.taskInfos;
			return '<div id="'+divId+'" style="position:absolute;border:2px solid '+color+';left:'+x+'px;top:'+y+'px;width:'+width+'px;height:'+height+'px;"></div>';
		},
		/**
		 * 创建节点信息提示框
		 */
		createNodeTip : function(){
			var tipContentId = Ext.id(null,'nodeContentTip');
			var toolTip = new Ext.ToolTip({
		        //title: '当前节点信息',
		        width:200,
		        tipContentId : tipContentId,
		        html: '<div id="'+tipContentId+'">暂无节点信息</div>',
		        trackMouse:true,
		        dismissDelay: 15000
		    });
		    return toolTip;
		},
		resize : function(adjWidth,adjHeight){
			if(this.appPanel){
				this.appPanel.setWidth(adjWidth);
				this.appPanel.setHeight(adjHeight-10);
			}
			this.wh[0] = adjWidth;
			this.wh[1] = adjHeight;
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function(){
			if(null != this.appPanel){
				 this.appPanel.destroy();
				 this.appPanel = null;
			}
		}
	};
});