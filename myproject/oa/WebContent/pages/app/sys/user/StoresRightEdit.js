/**
 * 门店权限设置页面
 * @author 程明卫
 * @date 2013-04-16
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		view : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,
		appPnl : null,
		appFrm : null,
		appTbar : null,
		appGrid : null,
		appWin : null,
		dialog : null,/*门店弹出窗*/
		userId : null,
		mdbh : null,/*门店ID*/
		dataState : false,/*数据状态标识, false : 数据未发生变化, true : 数据发生变化*/
		initDataStateTag : false,/*初始数据状态标识*/
		setParentCfg:function(parentCfg){
			this.userId = null;
			this.mdbh = null;
			this.dataState = false;
			this.parentCfg=parentCfg;
			this.view = parentCfg.view;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.appPnl = this.createAppPanle();
			this.appWin = new Ext.ux.window.MyWindow({width:790,height:600,title : '门店权限设置',items:[this.appPnl]});
		},
		createAppPanle : function(){
			var _this = this;
			var htmlArrs_1 = [
					'<tr><th col="empCode">员工编号</th> <td col="empCode" >&nbsp;</td><th col="empName">员工姓名</th> <td col="empName" >&nbsp;</td><th col="sex">性别</th> <td col="sex" >&nbsp;</td></tr>',
					'<tr><th col="userName">登录账号</th> <td col="userName" >&nbsp;</td><th col="dataLevel">数据访问级别</th> <td col="dataLevel" >&nbsp;</td><th col="postName">职位</th> <td col="postName" >&nbsp;</td></tr></tr>'];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 90,
			    htmls: htmlArrs_1,
			    url: './sysUser_get.action',
			    params: {
			        userId: -1
			    },
			    callback: {
			        sfn: function(jsonData) {
			        	_this.setDataRenders(jsonData);
			        }
			    }
			}];
			
			var detailPanel = new Ext.ux.panel.DetailPanel({
			    width: 780,
			    title : '用户信息',
			    isLoad : false,
			    detailCfgs: detailCfgs_1
			});
			this.appFrm = this.createForm();
			this.appGrid = this.createAppGrid();
			detailPanel.add(this.appFrm);
			detailPanel.add(this.appGrid);
			return detailPanel;
		},
		setDataRenders : function(jsonData){
			var dataLevel = jsonData["dataLevel"];
        	jsonData["dataLevel"] = OaRender_dataSource.dataLevelRender(dataLevel+"");
        	var instoreId = jsonData["instoreId"];
        	this.mdbh = instoreId;
        	var empName = jsonData["empName"];
        	if(empName){
        		empName = empName.split("##");
        		empName = empName[1];
        		jsonData["empName"] = empName;
        	}
        	jsonData["sex"] = OaRender_dataSource.sexRender(jsonData["sex"]+"");
        	var ssLevel = jsonData["ssLevel"] || "-1";
        	this.appFrm.setFieldValue('ssLevel',ssLevel);
        	var userId = jsonData["userId"];
        	this.appGrid.reload({userId:userId});
        	this.initDataStateTag = true;
		},
		createForm : function(){
			var _this = this;
			var rad_ssLevel = FormUtil.getRadioGroup({
			    fieldLabel: '门店访问权限',
			    hideLabel : true,
			    name: 'ssLevel',
			    "width": 600,
			    "allowBlank": false,
			    "maxLength": 50,
			    "items": [{
			        "boxLabel": "无门店访问权限",
			        "name": "ssLevel",
			        "inputValue": -1
			    },
			    {
			        "boxLabel": "本门店数据",
			        "name": "ssLevel",
			        "inputValue": 1
			    },
			    {
			        "boxLabel": "多门店数据",
			        "name": "ssLevel",
			        "inputValue": 2
			    }],
			    listeners : {
			    	change : function(rdgp,checked){
			    		if(!_this.initDataStateTag) _this.dataState = true;
			    		_this.initDataStateTag = false;
			    	}
			    }
			});
			
			var layout_fields = [rad_ssLevel];
			var frm_cfg = {
			    title: '门店访问权限选择'
			};
			var appform = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform;
		},
		createAppGrid : function(){
			var structure_1 = [
			{
			    header: '门店编号',
			    name: 'code',
			    width: 85
			},
			{
			    header: '门店名称',
			    name: 'name',
			    width: 300
			},
			{
			    header: '店面区域',
			    name: 'areaName',
			    width: 85
			},
			{
			    header: '门店总监',
			    name: 'directorName',
			    width: 85
			},
			{
			    header: '区域经理',
			    name: 'mgrName',
			    width: 85
			}];
			var appTbar = this.createToolBar();
			this.appTbar = appTbar;
			var appgrid = new Ext.ux.grid.AppGrid({
			    title: '已授权可访问的门店列表',
			    tbar : appTbar,
			    structure: structure_1,
			    url: './sysStoresRight_list.action',
			    needPage: false,
			    isLoad: false,
			   	height:420,
			    selectType : "check",
			    keyField: 'id',
			    autoScroll : true
			});
			return appgrid;
		},
		createToolBar : function(){
			var _this = this;
			var barItems = [
				{
					token : "添加门店",
					text : "添加门店",
					iconCls:'page_query',
					handler : function(){
						_this.openDialogbox(this);
					}
				},{
					token : "删除门店",
					text : "删除门店",
					iconCls:'page_delete',
					handler : function(){
						_this.delData();
					}
				},{
					token : "保存门店权限",
					text : "保存门店权限",
					iconCls:'page_save',
					handler : function(){
						_this.save();
					}
				},{
					token : "关闭",
					text : "关闭",
					iconCls:'page_close',
					handler : function(){
						_this.close();
					}
				}];
			var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems});
			return toolBar;
		},
		openDialogbox : function(btn){
			var _this = this;
			var ssLevel = this.appFrm.getValueByName("ssLevel");
			var errMsg = null;
			if(ssLevel == "-1"){
				errMsg = "当前选择的是“无门店访问权限”，不能添加门店!";
			}else if(ssLevel == "1"){
				errMsg = "当前选择的是“本门店数据访问权限”，不需要添加门店!";
			} 
			
			
			if(errMsg){
				ExtUtil.alert({msg:errMsg});
				return;
			}
			var idArr = this.getMdDatas();
			var _pars = {};
			if(idArr){
				var storeIds = idArr[0];
				_pars["nostoreIds"] = storeIds;
			}
			var parentCfg = {isCheck:true,parent:btn.el,params:_pars,callback : function(ids,records){
				if(ids){
					_this.dataState = true;
				}
				var store = _this.appGrid.getStore();
				store.add(records);
			}};
			if(this.dialog){
				this.dialog.show(parentCfg);
			}else{
				Cmw.importPackage('pages/app/dialogbox/StoresDialogbox',function(module) {
				 	_this.dialog = module.DialogBox;
				 	_this.dialog.show(parentCfg);
			  	});
			}
		},
	
		delData : function(){
			var selRows = this.appGrid.getSelRows();
			if(!selRows) return;
			var store = this.appGrid.getStore();
			store.remove(selRows);
			store.commitChanges();
			this.dataState = true;
		},
		save : function(callback){
			var _this = this;
			if(!this.dataState){
				ExtUtil.alert({msg:'数据未发生任何修改，不需要保存!'});
				return;
			}else{
				var ssLevel = this.appFrm.getValueByName("ssLevel");
				if(!ssLevel){
					ExtUtil.alert({msg:'数据未发生任何修改，不需要保存!'});
					return;
				}
				var params = {ssLevel:ssLevel,userId : this.userId};
				var errMsg = null;
				switch(parseInt(ssLevel)){
					case 1 : {/*本门店数据*/
						if(!this.mdbh) errMsg = "当前用户非门店员工，不能选择“本门店数据”访问权限!";
						break;
					}case 2 : {/*多门店数据*/
						var dbDatasArr = this.getMdDatas();
						if(!dbDatasArr || dbDatasArr.length == 0){
							errMsg = "你选择的是“多本门店数据”，必须在下面的表格中添加可访问的门店!";
						}else{
							params["mdIds"] = dbDatasArr[0];
						}
						break;
					}
				}
				
				if(errMsg){
					ExtUtil.alert({msg:errMsg});
					return;
				}
				if(ssLevel == "-1"){
					ExtUtil.confirm({msg:'你选择的门店访问权限为“无门店访问权限”，<br/>在保存数据后将会删除下面表格中所有的门店，确定保存?',
					fn : function(btn){
						if(btn != 'yes') return ;
						saveData();
					}});					
				}else{
					saveData();
				}
				
				function saveData(){
					EventManager.get('./sysStoresRight_save.action',{params:params,sfn:function(json_data){
						Ext.tip.msg(Msg_SysTip.title_appconfirm, Msg_SysTip.msg_dataSucess);
						if(callback){
							callback();
						}else{
							_this.appWin.hide();
						}
						_this.view.refresh();
					},ffn:function(action){
						Ext.tip.msg(Msg_SysTip.title_appwarn, Msg_SysTip.msg_dataFailure);
					}});
					
				}
			}
		},
		getMdDatas :function (){
			var store = this.appGrid.getStore();
			if(!store || store.getCount() == 0){
				return null;
			}
			var count = store.getCount();
			var selRows =  store.getRange(0,count-1);
			var idArr = [];
			var arr = [];
			for(var j=0,len = selRows.length; j<len; j++){
				var selRow = selRows[j];
				var id = selRow.id;
				idArr[idArr.length] = id;
			}
			return [idArr.join(",")];
		},
		close : function(){
			if(this.dataState){
				var _this = this;
				ExtUtil.confirm({msg:'数据已发生过修改，是否保存数据后再关闭?',fn:function(btn){
					if(btn == 'yes'){
						_this.save(function(){
							_this.appWin.hide();
						});
					}else{
						_this.appWin.hide();
						_this.dataState = false;
					}
				}});
			}else{
				this.appWin.hide();
				this.dataState = false;
			}
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
			this.appWin.show(this.parent.getEl());
			this.userId = this.parent.getSelId();
			this.appPnl.reload({userId:this.userId});
		},
		/**
		 * 销毁组件
		 */
		destroy : function(){
			this.appWin.close();	//关闭并销毁窗口
			this.appWin = null;		//释放当前窗口对象引用
			if(this.dialog){
				this.dialog.close();
				this.dialog = null;
			}
		}
	}
});