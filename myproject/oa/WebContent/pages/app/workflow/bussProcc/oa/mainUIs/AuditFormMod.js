/**
 * 流程审批主页面 ---> 审批意见填写表单
 * @author 程明卫
 * @date 2012-12-26
 */
define(function(require, exports) {
	exports.moduleUI = {
		appPanel : null,
		params : null,
		callback : null,/*当审批数据保存成功后，的回调函数*/
		userRoleDialogBox : null,/*用户角色选择框*/
		dispCurrTaskLabelId:Ext.id(null,'dispCurrTaskLabelId'),/*用来显示当前处理环节的ID*/
		nextTransFieldSet : null,/*下一步流程走向  FieldSet */
		nextAIdPrefix : 'nextChoseAhref_',/* 下一环节处理人 A标记 ID 前缀*/
		nextChoseSpanIdPrefix : 'nextChoseActor_',/*Span ID 前缀*/
		nextActorListIdPrefix : 'nextActorList_',/*下一环节处理人 ID 前缀*/
		nextTransDatas : null,/* 要提交到后台的下一环节处理数据   格式： {nodeType:节点类型,nextTrans : []} */
		/**
		 * 获取业务模块
		 * @param params 由菜单点击所传过来的参数
		 * 	{tabId : tab 对象ID,apptabtreewinId : 系统程序窗口ID,sysid : 系统ID} 
		 */
		getModule : function(params){
			if(!this.appPanel){
				this.appPanel = this.creatAppForm();
			}
			this.show(params);
			return this.appPanel;
		},
		/**
		 * 设置参数
		 */
		setParams : function(params){
			this.params = params;
		},
		/**
		 * 创建审批表单面板
		 */
		creatAppForm : function(){
			var hid_taskId = FormUtil.getHidField({fieldLabel : '任务ID',name:'taskId'});
			var hid_nodeId = FormUtil.getHidField({fieldLabel : '当前节点ID',name:'nodeId'});
			var txa_approval = FormUtil.getTAreaField({
			    fieldLabel: '请填写审批结果',
			    name: 'approval',
			    hideLabel:true,
			    allowBlank:false,
			    width:600,
				height:120,
				emptyText : '请输入审批意见'
			});
			
			this.nextTransFieldSet = FormUtil.getFieldSet({ title: '请选择下一步流程走向'});
			
			 var chk_reminds = FormUtil.getCheckGroup({fieldLabel : '通知下一环节处理人',name:'reminds',width : 200,
			 items : [{boxLabel : '手机短信通知', name:'reminds',inputValue:2, checked: true},
			 		{boxLabel : '邮件通知', name:'reminds',inputValue:3, checked: true}]});
			 var rad_remindsStartor = FormUtil.getCheckGroup({fieldLabel : '通知流程发起人',name:'notifyStartor',width : 200,
			 items : [{boxLabel : '通知', name:'notifyStartor',inputValue:1, checked: true},
			 		{boxLabel : '不通知', name:'notifyStartor',inputValue:2}]});
			 		
			var dispCurrLabel = "&nbsp;&nbsp;&nbsp;[当前处理环节：<span id='"+this.dispCurrTaskLabelId+"' style='color:red;'>流程尚未开始</span>]";
			var fset_1 = FormUtil.createLayoutFieldSet({title: '审批意见填写'+dispCurrLabel},[hid_nodeId,hid_taskId,txa_approval, this.nextTransFieldSet,
			{cmns:'TWO',fields:[chk_reminds,rad_remindsStartor]}]);
			var _this = this;
			var frm_cfg = {hidden:true,url: './oaBpOaFlow_submit.action',
							labelWidth:120,buttonAlign:'center',buttons:[{text:'提交审批意见',handler:function(){
					_this.submit();	
						}}]};
			var appform = FormUtil.createLayoutFrm(frm_cfg, [fset_1]);
			return appform;
		},
		/**
		 * 提交审批意见
		 */
		submit : function(){
			var _this = this;
			if(!this.isValid()) return;
			EventManager.frm_save(this.appPanel,{beforeMake:function(formData){
				formData.sysId = _this.params.sysid;
				formData.pdid = _this.params.pdid;
				formData.applyId = _this.params.applyId;
				formData.oaprocInstanceId = _this.params.oaprocInstanceId;
				formData.bussProccCode = _this.params.bussProccCode;
				var customerParams = _this.params.customerParams;
				if(customerParams) formData.customerParams = Ext.encode(customerParams);
				var nodeType =_this.nextTransDatas.nodeType;
				formData.nodeType = nodeType;
				var forkJoinActivityId = _this.nextTransDatas.forkJoinActivityId;
				if(forkJoinActivityId){
					formData.forkJoinActivityId = forkJoinActivityId;
				}
				var nextTrans = _this.nextTransDatas.nextTrans;
				if(nextTrans){
					formData.nextTrans = Ext.encode(nextTrans);
				}
			},sfn:function(formData){
				if(formData.procId && !_this.params.procId){
					_this.params.procId = formData.procId;
				}
				_this.refreshRefPage();
				_this.appPanel.hide();
				_this.callback(_this.params);
			}});
		},
		/**
		 * 刷新引用页面
		 */
		refreshRefPage : function(){
			var idsArr = ComptIdMgr.getBussProccApplyRefreshIds();
			for(var i=0,count=idsArr.length; i<count; i++){
				var refreshId = idsArr[i];
				invokeRefresh(refreshId);
			}
			
			/**
			 * 列表视图UI刷新
			 * @param cmptId 视图面板ID
			 */
			function invokeRefresh(cmptId){
				var cmpt = Ext.getCmp(cmptId);
				if(cmpt && cmpt["refresh"]){
					cmpt.refresh();
				}
			}
		},
		/**
		 * 
		 */
		isValid : function(){
			var approval = this.appPanel.getValueByName("approval");
			if(!approval){
				ExtUtil.alert({msg:'请填写审批意见!'});
				return false;
			}
			if(null == this.nextTransDatas || this.nextTransDatas.length == 0){
				 ExtUtil.alert({msg:'请选择下一流程走向!'});
				return false;
			}
			if(this.nextTransDatas.nodeType && (this.nextTransDatas.nodeType == 'END' || this.nextTransDatas.nodeType == 'SERVICE_END')){
				return true;
			}
			var nextTrans = this.nextTransDatas.nextTrans;
			var errMsg = [];
			for(var i=0,len=nextTrans.length; i<len; i++){
				var nextTran = nextTrans[i];
				var tagNodeName = nextTran.tagNodeName;
				var actorId = nextTran.actorId;
				if(!actorId){
					errMsg[errMsg.length] =tagNodeName;
				}
			}
			if(errMsg && errMsg.length > 0){
				 ExtUtil.alert({msg:'请为'+errMsg.join(",")+'选择下一步处理人!'});
				return false;
			}
			return true;
		},
		/**
		 * 显示面板并加载数据
		 */
		show : function(params){
			this.setParams(params);
			if(this.nextTransDatas) this.nextTransDatas = null;
			if(this.appPanel && this.appPanel.rendered){
				this.loadData();
			}else{
				var _this = this;
				this.appPanel.addListener('render',function(pnl){
					_this.loadData();
				});
			}
		},
		/**
		 * 加载客户信息数据
		 */
		loadData : function() {
			if(this.params.hideForm){/*如果有必做业务，就必须隐藏审批表单*/
				this.appPanel.hide();
				return;
			}
			this.resetForm();
			var _this = this;
			var pdid = this.params.pdid;
			var procId = this.params.procId;
			var manager = this.params.manager;
			if(!procId && manager != CURRENT_USERID) return;
			var params = {pdid : pdid,procId : procId};
			EventManager.get('./oaBpOaFlow_getTask.action',{params:params,sfn:function(json_data){
				_this.setValue(json_data);
			}});
		},
		resetForm : function(){
			if(!this.appPanel.rendered) return;
			this.appPanel.reset("result");
			if(this.nextTransFieldSet.rendered){
				this.nextTransFieldSet.removeAll();
			}
			this.appPanel.hide();
		},
		/**
		 * 
		 */
		setValue : function(json_data){
			if(!json_data) return;
			var pdId = json_data.pdid;//流程定义ID
			if(!pdId) return;/*没有数据时返回*/
			var procId = json_data.procId;//流程实例ID
			var procEnd = json_data.procEnd;//流程是否结束
			if(procEnd) return;/*流程结束则不显示审批表单*/
			var currTaskInfos = json_data.currTaskInfos;
			/*--> step 1 : 取当前用户任务  <--*/
			var currNodeTask = currTaskInfos[CURRENT_USERID];
			if(!currNodeTask){/*如果当前用户没有任务，则不显示审批表单*/
				return;
			}
			
			/*--> step 2 : 显示审批表单 <--*/
			if(!this.appPanel.isVisible()) this.appPanel.show();
			
			/*--> step 3 : 更新当前任务名称 <--*/
			var nodeId = currNodeTask.nodeId+"";
			var nodeName = currNodeTask.nodeName;
			var taskId =  currNodeTask.taskId;
			if(!procId || nodeName=="Start") nodeName = "开始启动流程";
			var dispCurrTaskLabel = Ext.get(this.dispCurrTaskLabelId);
			if(dispCurrTaskLabel) dispCurrTaskLabel.update(nodeName);
			if(nodeId) this.appPanel.findFieldByName("nodeId").setValue(nodeId);
			if(taskId) this.appPanel.findFieldByName("taskId").setValue(taskId);
			
			/*--> step 4 : 为消息通知赋值 <--*/
			var currNodeCfgs = json_data.currNodeCfgs;
			var nodeCfg = currNodeCfgs ? currNodeCfgs[nodeId] : null;
			var chk_reminds = this.appPanel.findFieldByName("reminds");
			if(nodeCfg && nodeCfg.reminds){
				var reminds = nodeCfg.reminds;
				chk_reminds.setValue(reminds);
			}else{
				chk_reminds.reset();
			}
			/*--> step 5 : 加载下一步路径单选框  <--*/
			var nextTransInfo = json_data.nextTransInfos[nodeId];
			this.loadNextTransCmpts(nodeCfg,nextTransInfo);
		},
		/**
		 * 加载下一步流程走向
		 */
		loadNextTransCmpts : function(nodeCfg,nextTransInfo){
			if(!nextTransInfo){
				 ExtUtil.error({msg:'找不到下一步流转路径数据!'});
				return;
			}
			this.nextTransFieldSet.removeAll();
			var count = nextTransInfo.length;
			var checkedVal = null;
			var items = [];
			for(var i=0; i<count; i++){
				var nextTranData = nextTransInfo[i];
				var transId = nextTranData["transId"];
				var transName = nextTranData["name"];
				var nodeName = nextTranData["nodeName"];
				var acType = nextTranData["acType"];
				if(acType == 4 && count==1){
					checkedVal = transName;/*当只有一个流转方向，且参与者类型为上级领导时，选中*/
				}
				var spanId = this.nextChoseSpanIdPrefix + transId;
				var boxLabel = transName + "&nbsp;&nbsp;&nbsp;&nbsp;<span id='"+spanId+"'></span>";
				items[i] = {boxLabel : boxLabel, name:'result',inputValue:transName,spanId : spanId,nextTranData : nextTranData};
			}
			var _this = this;			
			var rad_nextTrans = FormUtil.getRadioGroup({fieldLabel: '流转路径',autoWidth:true,hideLabel:true,name : 'result', columns: 1,allowBlank : false,items : items});
			rad_nextTrans.addListener("change",function(radioGroup,checked){
				_this.radioChange(radioGroup,checked);
			});
			rad_nextTrans.addListener("render",function(radioGroup){
				if(checkedVal) radioGroup.setValue(checkedVal);
				var checkItem = radioGroup.getCheckItem();
				radioGroup.fireEvent("change",radioGroup,checkItem);
			});
			this.nextTransFieldSet.add(rad_nextTrans);
			this.nextTransFieldSet.doLayout();
		},
		/**
		 * 流转路径单选框选中时
		 */
		radioChange : function(radioGroup,checked){
			if(!checked) return;
			var spanId = checked.spanId;
			var nextTranData = checked.nextTranData;
			var nodeId = nextTranData.nodeId;
			var transId = nextTranData.transId;
			var transName = nextTranData.name;
			var activityId = nextTranData.activityId;
			var tagNodeName = nextTranData.nodeName;
			var nodeType =  nextTranData.nodeType;
			var acType = nextTranData.acType;
			var actorIds = nextTranData.actorIds;
			this.nextTransDatas = {nodeType:nodeType};
			var _this = this;
			var aId = this.nextAIdPrefix+transId;
			var aEle = Ext.get(aId);
			
			if(aEle){/*移除上次创建的元素*/
				aEle.remove();
				if(aEle) aEle = null;
			}
			
			if(nodeType == "FORK" || nodeType == "JOIN"){/*并行分支-->开始*/
				this.nextTransDatas["forkJoinActivityId"] = activityId;
				var forkTaskArr = nextTranData[nodeId];
				if(!forkTaskArr || forkTaskArr.length == 0){
				 	ExtUtil.error({msg:'找不到并行任务['+tagNodeName+'] 的分支任务数据!'});
					return;
				}
				this.showForkJoin(spanId,aId,forkTaskArr);
				return;
			} 
			
			/*普通 和会签 节点*/ 
			this.nextTransDatas.nextTrans = [{transId : transId,activityId:activityId,tagNodeName : tagNodeName,actorId : null}];
			if(nodeType && (nodeType == 'END' || nodeType == 'SERVICE_END')){/*选择结束流程*/
				var a_html = "<span id='"+aId+"' style='color:blue'>下一环节<span style='font-weight:bold;color:red;'>["+tagNodeName+"]</span></span>&nbsp;&nbsp;";
				var actorListHtml = "<span id='"+this.nextActorListIdPrefix+transId+"' style='color:#EE7621;font-weight:bold;'>[不需要选择处理人，请直接点提交审批意见按钮]</span>";
				Ext.get(spanId).update(a_html+actorListHtml);
			}else{
				if(!this.checkActor(spanId,acType,tagNodeName)) return;
				var nextActionSpanId = this.nextActorListIdPrefix+transId;
				if(acType == 4){/*上级领导 */
					this.showSuperiors(spanId,nextActionSpanId,nextTranData);
				}else{
					var a_html = "<a id='"+aId+"' href='javascript:void(0)'>下一环节<span style='font-weight:bold;color:red;'>["+tagNodeName+"]</span>处理人:</a>&nbsp;&nbsp;";
					var actorListHtml = "<span id='"+nextActionSpanId+"' style='color:#EE7621;font-weight:bold;'>[暂无处理人]</span>";
					Ext.get(spanId).update(a_html+actorListHtml,true,function(){
						 var nextStepEle = Ext.get(aId);
						 nextStepEle.acType = acType;
						 nextStepEle.actorIds = actorIds;
						 nextStepEle.nodeId = nodeId;
						 nextStepEle.tagNodeName = tagNodeName;
						 nextStepEle.nodeType = nodeType;
						 nextStepEle.transId = transId;
						 _this.showUserRoleDialogBox(nextStepEle,true);
					     nextStepEle.on('click',function(e,t,o){
					    	_this.showUserRoleDialogBox(nextStepEle,false);
					     });
					});
				}
			}
		},
		/**
		 * 上级领导
		 */
		showSuperiors : function(spanId,nextActionSpanId,nextTranData){
			var _this = this;
			var tagNodeName = nextTranData.nodeName;
			EventManager.get('./sysUser_getLeaders.action',{params:{},sfn:function(json_data){
				var leaderId = json_data.userId;
				var leader =  json_data.userName;
				_this.nextTransDatas.nextTrans[0].actorId=leaderId;/*赋值*/
				var actorListHtml = "<span><span style='color:blue;'>下一环节<span style='color:font-weight:bold;color:red;'>["+tagNodeName+"]</span>处理人</span><span id='"+nextActionSpanId+"' style='color:#EE7621;font-weight:bold;'>["+leader+"]</span></span>";
				Ext.get(spanId).update(actorListHtml);
			},ffn:function(json_data){
				_this.nextTransDatas.nextTrans[0].actorId=null;/*赋值*/
				var actorListHtml = "<span style='color:red;font-weight:bold;'>["+tagNodeName+"]节点参与者类型为:\"上级领导\"，找不到当前用户\""+CURENT_USER+"\"直属上级领导，请通知系统管理员进行相应配置!</span>";
				Ext.get(spanId).update(actorListHtml);
			}});
		},
		/**
		 * 显示 Fork Join 下一步处理人选择列表
		 * @param spanId 容器标记ID
		 * @param  forkJoinSpanId  ForkJoin Span 标记ID
		 * @param forkTaskArr 分支任务信息 Arrary
		 */
		showForkJoin : function(spanId,forkJoinSpanId,forkTaskArr){
			var nextTransHtmlArr = [];
			var aIdArr = [];
			var nextStepCfg = {};
				this.nextTransDatas.nextTrans = [];
			for(var i=0,count=forkTaskArr.length; i<count; i++){
				var forkTask = forkTaskArr[i];
				
				var transId = forkTask.transId;
				var activityId = forkTask.activityId;
				var nodeId = forkTask.nodeId;
				var tagNodeName = forkTask.nodeName;
				var acType = forkTask.acType;
				var actorIds = forkTask.actorIds;
				var nodeType = forkTask.nodeType;
				
				var aId = this.nextAIdPrefix+transId;
				aIdArr[i] = aId;
				nextStepCfg[aId] = {transId:transId,acType:acType,actorIds:actorIds,nodeType:nodeType,nodeId:nodeId,tagNodeName:tagNodeName};
				this.nextTransDatas.nextTrans[i] = {transId : transId,activityId:activityId,tagNodeName : tagNodeName,actorId : null};
				var a_html = "<a id='"+aId+"' href='javascript:void(0)'><span parentId='"+aId+"' style='font-weight:bold;color:red;'>["+tagNodeName+"]</span>处理人:</a>&nbsp;&nbsp;";
				var	actorListHtml = "<span id='"+this.nextActorListIdPrefix+transId+"' style='color:#EE7621;font-weight:bold;'>[暂无处理人]</span>";
				nextTransHtmlArr[i] = a_html + actorListHtml;
			}
			var _this = this;
			var forkJoinSpan = '<span id="'+forkJoinSpanId+'">'+nextTransHtmlArr.join("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")+'</span>';
			Ext.get(spanId).update(forkJoinSpan,true,function(){
				for(var i=0,count=aIdArr.length; i<count; i++){
					var aId = aIdArr[i];
					 var nextStepEle = Ext.get(aId);
					 var cfg = nextStepCfg[aId];
					 nextStepEle.acType = cfg.acType;
					 nextStepEle.actorIds = cfg.actorIds;
					 nextStepEle.nodeId = cfg.nodeId;
					 nextStepEle.tagNodeName = cfg.tagNodeName;
					 nextStepEle.nodeType = cfg.nodeType;
					 nextStepEle.transId = cfg.transId;
					 _this.showUserRoleDialogBox(nextStepEle,true);
				     nextStepEle.on('click',function(e,t,o){
				     	var eleId = t.id;
				     	if(!eleId){
				     		eleId = t.getAttribute('parentId');
				     	}
				     	var ele = Ext.get(eleId);
				    	_this.showUserRoleDialogBox(ele,false);
				     });
				}
			});
		},
		checkActor : function(spanId,acType,tagNodeName){
			if(!acType){
				var noActorHtml = "<span style='color:red;font-weight:bold;'>["+tagNodeName+"]节点没有配置下一步处理人，请通知系统管理员!</span>";
				Ext.get(spanId).update(noActorHtml);
				return false;
			}
			return true;
		},
		/**
		 * 显示用户角色对话框
		 * @param t 暂无处理人Span 对象
		 * @param isShowOneUser 当只有一个用户是否显示此用户为默认用户，而不用再弹用户选择对话框 (true : 如果为单用户显示,false ：不显示)
		 */
		showUserRoleDialogBox : function(t,isShowOneUser){
			var _this = this;
			var eleId = t.id;
			var acType = t.acType;
			var actorIds = t.actorIds;
			var nodeId = t.nodeId;
			var tagNodeName = t.tagNodeName;
			var nodeType = t.nodeType;
			var transId = t.transId;
			
			if(!_this.nextTransDatas){
				ExtUtil.error({msg:' nextTransDatas 为空!'});
				return;
			}
			var nextTrans = _this.nextTransDatas.nextTrans;
			if(!nextTrans){
				ExtUtil.error({msg:' nextTransDatas.nextTrans 为空!'});
				return;
			}
			
			var selval = [];
			for(var i=0,count=nextTrans.length; i<count; i++){
				var nextTran = nextTrans[i];
				if(nextTran.transId == transId){
					selval[selval.length] = nextTran.actorId;
				}
			}
			var _params = {acType : acType,actorIds:actorIds,
				procId : this.params.procId,nodeId:nodeId};
			
			var parentCfg = {parent : t,params : _params};
			if(selval && selval.length > 0){	/*设置选中值*/
				parentCfg.selval = selval.join(",");
			}
			
			var nextActorListEleId = eleId.replace(this.nextAIdPrefix,this.nextActorListIdPrefix);
			parentCfg.callback = function(selvals,seltxts){
				var nextActorListEle = Ext.get(nextActorListEleId);
				nextActorListEle.update(seltxts);
				for(var i=0,count=nextTrans.length; i<count; i++){
					var nextTran = nextTrans[i];
					if(nextTran.transId == transId){
						nextTran.actorId = selvals;
						break;
					}
				}
			};
			if(isShowOneUser){
				this.showSingleUser(parentCfg);
				return;
			}
			/*如果是会签，则为复选框 1:单选，2：复选*/
			var controlType = (nodeType && nodeType=='COUNTERSIGN') ? 2 : 1;
			if(this.userRoleDialogBox){
				this.userRoleDialogBox.controlType = controlType;
				this.userRoleDialogBox.show(parentCfg);
				return;
			}
			
			Cmw.importPackage('pages/app/dialogbox/UserRoleDialogbox',function(module) {
			 	_this.userRoleDialogBox = module.DialogBox;
			 	_this.userRoleDialogBox.controlType = controlType;
			 	_this.userRoleDialogBox.show(parentCfg);
			});
		},
		/**
		 * 当只有一个用户时，获取该用户作为默认用户显示，而不用再打开用户选择对话框
		 */
		showSingleUser : function(parentCfg){
			var params = parentCfg.params;
			EventManager.get('./sysTree_useRolelist.action',{params:params,sfn:function(json_data){
			 	if(!json_data || json_data.length == 0) return;
			 	if(json_data.length > 2) return;
			 	var gpData = json_data[0];
			 	var users = gpData.users;
			 	if(!users || users.length == 0) return;
			 	if(users.length > 2) return;
			 	var user = users[0];
			 	var id = user["id"];
			 	var name= user["name"];
			 	parentCfg.callback(id, name);
			}});
		},
		resize : function(adjWidth,adjHeight){
			this.appPanel.setWidth(adjWidth);
			this.appPanel.setHeight(adjHeight);
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function(){
			if(null != this.userRoleDialogBox){
				this.userRoleDialogBox.destroy();
				this.userRoleDialogBox = null;
			}
			
			if(null != this.appPanel){
				 this.appPanel.destroy();
				 this.appPanel = null;
			}
		}
	};
});