/**
 * 项目调查报告页面
 * @author 程明卫
 * @date 2013-11-23 19:54
 */
define(function(require, exports) {
	exports.viewUI = {
		params : null,
		appMainPanel : null,
		transGrid : null,
		transForm : null,
		currNodeName :  null,/*当前选中节点名称*/
		tranName : null,/*当前选中路径名称*/
		currlimitVals : null,/*当前选中路径的限制值*/
		idMgr : {
			fstUserId : Ext.id(null,'fstUserId'),
			fstRoleId : Ext.id(null,'fstRoleId'),
			currNodeSpanId : Ext.id(null,'currNodeSpanId'),
			transNameSpanId : Ext.id(null,'transNameSpanId'),
			currNodeUserSpanId : Ext.id(null,'currNodeUserSpanId'),
			transNameUserSpanId : Ext.id(null,'transNameUserSpanId')
		},
		/**
		 * 获取主面板
		 * @param tab Tab 面板对象
		 * @param params 由菜单点击所传过来的参数
		 * 	{tabId : tab 对象ID,apptabtreewinId : 系统程序窗口ID,sysid : 系统ID} 
		 */
		getMainUI : function(params){
			this.setParams(params);
			if(!this.appMainPanel){
				this.createCmpts();
			}
			if(params) this.refresh(params.datas);
			return this.appMainPanel;
		},
		/**
		 * 创建组件
		 */
		createCmpts : function(){
			this.transGrid = this.createTransGrid();
			this.appMainPanel = new Ext.Panel({title:'参与者设置',autoScroll:true,items:[this.transGrid],
			 listeners : {render : function(pnl){pnl.doLayout();}}});
		},
		createTransGrid : function(){
			var _this = this;
			var structure_1 = [
				{ header: '流转路径ID', name: 'transId', hidden : true},
				{ header: '参与者ID', name: 'actorIds', hidden : true},
				{ header: '启用条件限制', name: 'stint', hidden : true},
				{ header: '限制值', name: 'limitVals', hidden : true},
				{ header: '流程走向', name: 'tranName', width : 120},
				{ header: '参与者类型', name: 'acType',hidden : true},
				{ header: '参与者类型', name: 'acTypeName',width : 85},
				{ header: '参与者列表', name: 'actorNames', width : 180},
				{ header: '流转路径类型', name: 'tpathType',hidden : true},
				{ header: '目标任务处理方式', name: 'tagWay',hidden : true},
				{ header: '并行令牌类型', name: 'tokenType',hidden : true},
				{ header: '令牌标识', name: 'token',hidden : true}
			];
			
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    structure: structure_1,
			    needPage: false,
			    isLoad: false,
			    autoScroll : true,
			   // collapsed:true,
			    height:200,
			    keyField: 'id'
			});
			appgrid_1.addListener('rowclick',function(grid,rowIndex,e){
				_this.showTransForm();
			});
			return appgrid_1;
		},
		/**
		 * 显示表单配置面板
		 */
		showTransForm : function(){
			var _this = this;
			var selRow = this.transGrid.getSelRow();
			var data = selRow.data;
			if(!this.transForm){
				this.transForm = this.createTransForm();
				this.appMainPanel.add(this.transForm);
				this.appMainPanel.doLayout();
				if(this.transForm.rendered){
					showForm();
				}else{
					this.transForm.addListener("render",function(){showForm();});
				}
			}else{
				showForm();
			}
			function showForm(){
				_this.tranName = data.tranName;
				_this.transForm.show();
				_this.transForm.setFieldValues(data);
				var acType = data.acType;
				
				var disabled = true;
				var actorNamesCmpbox = _this.transForm.findFieldByName("actorNames");
				if(acType && (acType == "1" || acType == "2")){
					disabled = false;
					actorNamesCmpbox.params={acType : acType};
				}
				actorNamesCmpbox.setDisabled(disabled);
				_this.currlimitVals = data.limitVals;
			}
		},
		/**
		 * 创建流程路径配置表单
		 */
		createTransForm : function(){
			var _this = this;
			var hid_limitVals = FormUtil.getHidField({name:'limitVals'});
			var hid_actorIds = FormUtil.getHidField({name:'actorIds'});
			var txt_transName = FormUtil.getReadTxtField({fieldLabel : '流程走向',name:'tranName',readOnly:true,width:200});
			var lcbo_acType = FormUtil.getLCboField({fieldLabel: '参与者类型',name:"acType",data:[["0","不需要参与者"],["1","角色"],["2","用户"],["3","上一处理人"],["4","上级领导"],["5","流程发起人"]],allowBlank : false,
				listeners:{
					change : function(cbo,newVal,oldVal){
						var disabled = true;
						if(newVal == "1" || newVal == "2"){
							disabled = false;
							cbogd_companyName.params={acType : newVal};
						}
						cbogd_companyName.reset();
						cbogd_companyName.setDisabled(disabled);
					}
			}});
			
			var barItems = [{type:'label',text:'名称'},{type:'txt',name:'name'}];
			var structure = [{header: '名称',name: 'name',width:200}];
			
			var callback = function(cbogd,value){
				var vals = value.split(cbogd.SIGIN);
				var actorIds = vals[0];
				hid_actorIds.setValue(actorIds);
			};
			var cbogd_companyName = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '参与者列表',
			    name: 'actorNames',
			    barItems : barItems,
			    structure:structure,
			    dispCmn:'name',
			    isAll:true,
			    allowBlank : true,
			    autoScroll : true,
			    url : './sysRole_ruList.action',
			    needPage : true,
			    isCheck : true,
			    showLoad : true,
			    disabled : true,
			    width: 200,
			    callback : callback
			});
			
			var lcbo_tpathType = FormUtil.getLCboField({fieldLabel: '流转路径类型',name:"tpathType",allowBlank : false,value:'0',
				data:[["0","未设置"],["1","默认流转路径"],["2","退回路径"],["3","终止流程路径"]]
			});
			
			var lcbo_tagWay = FormUtil.getLCboField({fieldLabel: '目标任务处理方式',name:"tagWay",allowBlank : false,value:'0',width:250,
				data:[["0","不需要处理"],["1","目标节点有任务时,不创建新任务"]]//,["2","目标节点有任务时,等原任务完成再创建新任务"]]
			});	
			
			var rad_eventType = FormUtil.getRadioGroup({
			    fieldLabel: '并行令牌类型',
			    name: 'tokenType',
			    "width": 250,
			    "allowBlank": false,
			    "items": [{
			        "boxLabel": "无令牌",
			        "name": "tokenType",
			        "inputValue": 1,
			        checked:true
			    },
			    {
			        "boxLabel": "开始",
			        "name": "tokenType",
			        "inputValue": 2
			    },
			    {
			        "boxLabel": "汇总",
			        "name": "tokenType",
			        "inputValue": 3
			    },
			    {
			        "boxLabel": "完成",
			        "name": "tokenType",
			        "inputValue": 4
			    }],
			    listeners:{
					change : function(rdgp,checked){
						var newVal = rad_eventType.getValue();
						if(newVal && newVal == 1) txt_token.reset();
					}
			    }
			});
			
			
			var txt_token = FormUtil.getTxtField({
			 	fieldLabel: '令牌标识',
			    name: 'token',
			    "width": 200
			});
			
			var lcbo_stint = FormUtil.getLCboField({fieldLabel: '条件限制类型',name:"stint",allowBlank : false,value:'1',width:250,
				data:[["1","无条件限制"],["2","按角色限制"],["3","按用户限制"],["4","目标节点无审批人取消任务"]],
				listeners:{
						change : function(cbo,newVal,oldVal){
							var val = lcbo_stint.getValue();
							_this.showFieldSet(val);
						}
				}
			});
				
			var cbog_users = ComboxControl.getUserCboGrid({fieldLabel:'用户列表',name:'userIds',width:300,hideLabel:true,isCheck:true,callback:function(cboGrid,vals){
					_this.setLimitVals(vals);			
				}});
			var cbog_roles = ComboxControl.getRoleCboGrid({fieldLabel:'角色列表',name:'roleIds',width:300,hideLabel:true,isCheck:true,callback:function(cboGrid,vals){
					_this.setLimitVals(vals);			
				}});
		
			var titleUsers = '当'+this.getSpanHtml(this.idMgr.currNodeUserSpanId)+'环节用户属于以下范围时，才有'
						+this.getSpanHtml(this.idMgr.transNameUserSpanId)+'的权限';
			var fst_users = FormUtil.getFieldSet({id:this.idMgr.fstUserId,title:titleUsers,items:[cbog_users],hidden:true});
			
			var titleRoles = '当'+this.getSpanHtml(this.idMgr.currNodeSpanId)+'环节用户的角色属于以下范围时，才有'
						+this.getSpanHtml(this.idMgr.transNameSpanId)+'的权限';
			var fst_roles = FormUtil.getFieldSet({id:this.idMgr.fstRoleId,title:titleRoles,items:[cbog_roles],hidden:true});
			
			var buttons = this.getButtons();
			var appform = FormUtil.createFrm({title : '参与者设置'+Msg_SysTip.msg_actorGridTip,labelWidth:115,buttonAlign:'center',buttons : buttons,
			items:[hid_limitVals,hid_actorIds,txt_transName,lcbo_acType,cbogd_companyName,lcbo_tpathType,lcbo_tagWay,rad_eventType,txt_token,lcbo_stint,fst_users,fst_roles]});
			return appform;
		},
		/**
		 * 设置限制值
		 */
		setLimitVals : function(vals){
			var hid_limitVals = this.transForm.findFieldByName("limitVals");
			if(!vals){
				hid_limitVals.reset();
			}else{
				var arr = vals.split("##");
				if(null == arr || arr.length == 0){
					hid_limitVals.reset();
				}else{
					hid_limitVals.setValue(arr[0]);
				}
			}
		},
		/**
		 * 根据 条件限制类型 显示角色或用户FieldSet对象
		 */
		showFieldSet : function(stint){
			var roleShow = false;
			var userShow = false;
			var cboGdFieldName = null;
			switch(parseInt(stint)){
				case 1 :{/*无限制*/
					roleShow = false;
					userShow = false;
					break;
				}case 2 :{/*按角色限制*/
					roleShow = true;
					userShow = false;
					cboGdFieldName = "roleIds";
					break;
				}case 3 :{/*按用户限制*/
					roleShow = false;
					userShow = true;
					cboGdFieldName = "userIds";
					break;
				}
			}
			var fstRole = Ext.getCmp(this.idMgr.fstRoleId);
			var fstUser = Ext.getCmp(this.idMgr.fstUserId);
			fstRole.setVisible(roleShow);
			fstUser.setVisible(userShow);
			if(roleShow){
				Ext.get(this.idMgr.currNodeSpanId).update(this.currNodeName);
				Ext.get(this.idMgr.transNameSpanId).update(this.tranName);
			}
			if(userShow){
				Ext.get(this.idMgr.currNodeUserSpanId).update(this.currNodeName);
				Ext.get(this.idMgr.transNameUserSpanId).update(this.tranName);
			}
			this.stint = stint;
			this.limitVals = null;
			var cbog_users = this.transForm.findFieldByName('userIds');
			var cbog_roles = this.transForm.findFieldByName('roleIds');
			cbog_users.reset();
			cbog_roles.reset();
			
			if(!stint || parseInt(stint) == 1) return;
			if(!cboGdFieldName) return;
			var cboGdField = this.transForm.findFieldByName(cboGdFieldName);
			if(this.currlimitVals && cboGdField) cboGdField.setValue(this.currlimitVals);
		},
		getSpanHtml : function(spanId){
			return '<span style="color:red;">[<span id="'+spanId+'"></span>]</span>';
		},
		getButtons : function(){
			var _this = this;
			var btnSet = new Ext.Button({text:"设置",handler:function(){
				var values = _this.transForm.getValues();
				var acType = values.acType;
				if(!acType && acType != 0){
					ExtUtil.alert({msg:"请选择参与者类型!"});
					return;
				}else{
					var actorNames = values.actorNames;
					if(acType == "1" || acType == "2"){
						if(!actorNames){
							ExtUtil.alert({msg:'当参与者类型选择的是"角色"或"用户"时，参与者列表不能为空!'});
							return;
						}else{
							var actorArr =  actorNames.split("##");
							if(null != actorArr && actorArr.length>0) values.actorNames = actorArr[1];
						}
					}else{
						values.actorIds = "";
						values.actorNames = "";
					}
				}
				
				var eventType = _this.transForm.getValueByName("eventType");
				var token = _this.transForm.getValueByName("token");
				var eventTypeErr = null;
				if(eventType){
					switch(parseInt(eventType)){
						case 2 :
							if(!token) eventTypeErr = "当选择的并行令牌类型是“开始”时，必须输入令牌标识";
							break;
						case 3 :
							if(!token) eventTypeErr = "当选择的并行令牌类型是“汇总”时，必须输入令牌标识";
							break;
						case 4 :
							if(!token) eventTypeErr = "当选择的并行令牌类型是“完成”时，必须输入令牌标识";
							break;
					}
				}
				if(eventTypeErr){
					ExtUtil.alert({msg:eventTypeErr});
					return;
				}	
			
				var acType = values["acType"];
				var acTypeName = Render_dataSource.acTypeRender(acType.toString());
				values["acTypeName"] = acTypeName;
				_this.transGrid.setVals2SelRow(values);
				_this.resetForm();
			}});
			var bntCancel = new Ext.Button({text:"取消",handler:function(){_this.resetForm();}});
			return [btnSet,bntCancel];
		},
		setParams : function(params) {
			this.params = params;
			if(this.params){
				this.currNodeName = this.params.currNodeName;
			}
		},
		refresh : function(datas) {
			if(!this.appMainPanel) return;
			if (!this.appMainPanel.rendered) {
				var _this = this;
				this.appMainPanel.addListener('render', function(cmpt) {
						_this.setValues(datas);
				});
			} else {
				this.setValues(datas);
			}
		},
		/**
		 * 在这里加载数据
		 */
		setValues : function(datas){
			this.reset();
			if(!datas) return;
			var trans = datas;
			if(trans && trans.length > 0){
				for(var i=0,count=trans.length; i<count; i++){
					var data = trans[i];
					var acType = data["acType"];
					var acTypeName = data["acTypeName"];
					if(!acTypeName) acTypeName = Render_dataSource.acTypeRender(acType);
					data["acTypeName"] = acTypeName;
					this.transGrid.addRecord(data);
				}
			}
		},
		/**
		 * 获取数据
		 * @param isCheck 在获取数据是，是否需要检查
		 */
		getValues : function(){
			var transStore = this.transGrid.getStore();
			var tranCfgs = [];
			var errMsg = null;
			if(transStore.getCount() == 0){
				errMsg = "流转路径不存在!";
			}else{
				var errArr = [];
				tranCfgs = transStore.getRange(0,transStore.getCount()-1);
				for(var i=0,count=tranCfgs.length; i<count; i++){
					var record  =  tranCfgs[i];
					tranCfgs[i] = record.data;
					var acType = record.get("acType");
					var actorNames = record.get("actorNames");
					var actorIds = record.get("actorIds");
					var tranName = record.get("tranName");
					if(!acType && parseInt(acType) !== 0){
						errArr[errArr.length] = "["+tranName+"]没有设置参与者类型!";
					}else{
						if((acType == "1" || acType == "2") && !actorNames){
							errArr[errArr.length] = "["+tranName+"]参数设置有错:当参与者类型选择的是\"用户\"或\"角色\"时，必须指定参与者列表!";
						}
					}
				}
				if(errArr && errArr.length > 0) errMsg = errArr.join("<br/>");
			}
			if(errMsg){
				ExtUtil.alert({msg:"参与者设置中配置不正确:<br/>"+errMsg});
				tranCfgs = null;
			}
			return tranCfgs;
		},
		resetForm :	function (){
			this.transForm.reset();
			this.transForm.hide();
			this.transForm.findFieldByName("actorNames").setDisabled();
			this.showFieldSet(1);
		},
		/**
		 * 重置
		 */
		reset : function(){
			if(this.transGrid)this.transGrid.removeAll();
			if(this.transForm)this.resetForm();
		},
		resize : function(adjWidth,adjHeight){
			this.appMainPanel.setWidth(adjWidth);
			this.appMainPanel.setHeight(adjHeight);
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function(){
			if(null != this.appMainPanel){
				 this.appMainPanel.destroy();
				 this.appMainPanel = null;
			}
			if(null != this.transGrid){
				 this.transGrid.destroy();
				 this.transGrid = null;
			}
			if(null != this.transForm){
				 this.transForm.destroy();
				 this.transForm = null;
			}
		}
	}
});