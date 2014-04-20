/**
 * #DESCRIPTION#
 * @author smartplatform_auto
 * @date 2014-01-19 03:06:37
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		selId  : null,
		row : null,
		refresh : null,
		appCmpts : {},
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.selId = parentCfg.selId;
			this.row = parentCfg.row;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			var _this = this;
			this.appFrm = this.createForm();
			setValueForEdit : this.setValueForEdit();
			this.appWin = new Ext.ux.window.MyWindow({
				width : 800,
				height : 300 ,
				items : [this.appFrm]
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
			if(!this.appWin) return;
			this.appWin.close();	//关闭并销毁窗口
			this.appWin = null;		//释放当前窗口对象引用
		},
		/**
		 * 编辑时调用的方法
		 */
		setValueForEdit :function(){
			var _self = this;
			var id = _self.selId;
			var setValuesFn = function(json_data){exports.WinEdit.setFormValues(json_data);};
			if(null != id){
				EventManager.get('./oaWorkLog_get.action',{params:{id:id},sfn:setValuesFn});
			}
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(optionType,data){
					var _self=this;
					
					if(null != data){
						_self.appFrm.updateRowVals(data);
					}
			},
		/**
		 * 创建Form表单
		 */
		createForm : function(){
			var _self =this;
			var ttbar = [{
						token : Btn_Cfgs.SAVE_BTN_TXT,
						text : Btn_Cfgs.SAVE_BTN_TXT,	/*-- 保存 --*/ 
						iconCls:Btn_Cfgs.SAVE_CLS,
						tooltip:Btn_Cfgs.SAVE_TIP_BTN_TXT,
						handler :function(){
							_self.saveData();
					}
				}, {
						token : "添加",
						text : Btn_Cfgs.INSERT_BTN_TXT,
						iconCls:'page_add',
						tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
						handler : function(){
							appgrid_1.getStore().add(new Ext.data.Record({ yamount : 0.00}));
						}
					},{
						token : Btn_Cfgs.CLOSE_BTN_TXT ,
						text : Btn_Cfgs.CLOSE_BTN_TXT,  /*-- 关闭 --*/
						iconCls:Btn_Cfgs.CLOSE_CLS,
						tooltip:Btn_Cfgs.CLOSE_TIP_BTN_TXT,
						handler : function() {
							_self.appWin.hide();
						}
					}]
			var txt_id = FormUtil.getHidField({
						fieldLabel : 'id',
						name : 'id'			
			});
			var structure = [{
					header : 'id' ,name : 'id' , hidden : true},{
					header : '费用项编号' , name :'code' , width : 180},{
					header: '费用项名称',name: 'name',width:150}];
					
					
			function ck_callback(grid,value,selRows){
					var store = appgrid_1.getStore();
					var record = store.getAt(_self.globalMgr.addRow)//获取当前行
					var fieldid = value.split("##")[0];//获取id
					var fieldvalue = value.split("##")[1];//获取名称
					/*var fieldname = */record.set(_self.globalMgr.addField,fieldvalue);//对当前行的某个字段进行赋值
					/*var fieldnameid = */record.set("id",fieldid);//对当前行的某个字段进行赋值
			}
			//1.费用项
			var txt_citemId = new Ext.ux.grid.AppComBoxGrid({
						gridWidth :350,
						name : 'citemId',
					    "width": 125,
					    structure:structure,
					    dispCmn:'name',
					    isAll:true,
					    url : './oaCitemsCfg_list.action',
					    needPage : false,
					    isLoad:true,
					    "allowBlank": false,
					    //通过回调函数将数据填充到其他的表格中
					    callback : ck_callback
			});
			_self.globalMgr.citemid = txt_citemId ;
			
			//2.对费用所属组织类型的key一个隐藏
			var txt_orgtypeId = FormUtil.getHidField({
					id : 'affairTypeId',
					name : 'affairTypeId'
			});
			//对费用所属组织类型的value一个隐藏
			_self.globalMgr.orgtypeId = txt_orgtypeId;
			var txt_overtimeName = FormUtil.getHidField({
					id : 'affairTypeName',
					name : 'affairTypeName'
			});
			//3.事务类型
			var txt_affairType = FormUtil.getLCboField({
						fieldLabel : 'affairType',
						name : 'affairType',
						data : [["1","出差"],["2","其他"]],
						listeners:{
               			 'select':function(combo, record){
               			 	Ext.getCmp('affairTypeId').text = combo.getValue();//获取的key
            				Ext.getCmp('affairTypeName').text = combo.getRawValue();//获取的value
               			 	}
               			 }
			});
			_self.globalMgr.affairType = txt_affairType;
			//4.起始时间
			var txt_startDate = FormUtil.getDateField({
						fieldLabel : 'startDate',
						name : 'startDate'
			});
			//5.结束时间
			var txt_endDate = FormUtil.getDateField({
						fieldLabel : 'endDate',
						name : 'endDate'/*,
						listeners : {
							change:function(){								
								var start = Ext.util.Format.date(Ext.getCmp('startDate').getValue(), 'Y-m-d''Y-m-d H:i:s');//格式化日期控件值
								var end = Ext.util.Format.date(Ext.getCmp('endDate').getValue(), 'Y-m-d' 'Y-m-d H:i:s');//格式化日期控件值
								if(start != "" && end != ""){
								var startday = start.split("-")[2];
								var endday = end.split("-")[2];
								var resultday = endday - startday ;
								//开始时间小时 == 结束时间小时
								if(resultday == 0){
									ExtUtil.info({msg:"请假开始时间和结束时间不能一样"});
								}else if(resultday < 0){
									ExtUtil.info({msg:"出错啦！结束日期不能小于开始日期"});
									}	
								}
							}
						}*/
						
			});
			
			//6.费用所属部门			
			var txt_inorgId = FormUtil.getTxtField({
						fieldLabel : 'inorgid',
						name : 'inorgid'
			});
			_self.globalMgr.inorgId = txt_inorgId;
			//7.费用金额
			var txt_amount = FormUtil.getReadTxtField({
						fieldLabel : 'amount',
						name : 'amount'
			});
			_self.globalMgr.amount = txt_amount;
			//8.原由
			var txt_reason = FormUtil.getTAreaField({
						fieldLabel : 'reason',
						name : 'reason',
						width : 100
			});
			
			var structure_1 = [{
				id : 'id',//费用项的id
				name : 'id',
				hidden : true
			},{
			    header: '费用项',
			    name: 'citemId',
			    allowBlank : false
			},{
			    header: '事务类型',
//			    id : 'affairType',
			    name: 'affairType',
			    allowBlank : false,
			    renderer : function(val){
			    	switch (val) {
							case 1 :
								val = "出差";
								break;
							case 2 :
								val = "其他";
								break;
						}
						return val;
			    }
			},{
			    header: '起始日期',
			    name: 'startDate',
			    allowBlank : false,
			    //页面的显示格式
				renderer:Ext.util.Format.dateRenderer('y-m-d')
			},{
			    header: '截止日期',
			    name: 'endDate',
			    allowBlank : false,
			    //页面的显示格式
				renderer:Ext.util.Format.dateRenderer('y-m-d')
			},{
				header : '费用所属部门',
				name : 'inorgid',
			    allowBlank : false
			},{
			    header: '费用金额',
			    name: 'amount',
			    allowBlank : false
			    /*,
			    renderer: function(val) {
			        switch (val) {
			        case 0.00:
			            val = '0.00';
			            break;
			        }
			        return val;
			    }*/
			},{
			    header: '事由',
			    name: 'reason'
			},{
			    header: '每个部门分摊的金额',
			    name: 'allAmount',
			    hidden :true
			},{
			    header: '每个部门的id',
			    name: 'inorgId',
			    hidden :true
			},{
			    header: '事务类型的id',
			    name: 'affairTypeId',
			    hidden :true
			},{
				header : '费用报销状态',
				name : 'xstatus',
				valie : '1',
				hidden : true
			},{
				header : '费用所属组织类型',
				name : 'orgtype',
				hidden : true
			},{
				header : 'Isenabled',
				name : 'Isenabled',
				value : '-1',
				hidden : true
			}];
			var appgrid_1 = new Ext.ux.grid.MyEditGrid({
				tbar : ttbar,
			    title: '工作日志',
			    height :450 ,
			    editEles : {1 : txt_citemId , 2 : txt_affairType ,3 : txt_startDate ,4: txt_endDate ,
			    			5 :txt_inorgId , 6 : txt_amount,7:txt_reason  },
			    structure: structure_1,
			    needPage: false,
			    isLoad: false,
			    keyField: 'id',
			    listeners :{
						'beforeedit':function(e){
							/**
							 * 获取点击的当前行索引
							 */
							var row = e.row;
							/**
							 * 获取要赋值的列名
							 */
							var field = e.field;
							/*alert(row);
							alert(field);*/
							_self.globalMgr.addRow = row ;
							_self.globalMgr.addField = field;							
						}
					} 
			});
				appgrid_1.on('validateedit', function(e) {
       				 if(e.field == 'affairType'){
           		 		e.value = Ext.getCmp('affairTypeName').text;
           		 		Ext.getCmp('affairTypeName').text = null;
           		 		var store = appgrid_1.getStore();
						var record = store.getAt(_self.globalMgr.addRow)//获取当前行
           		 		record.set('affairTypeId',Ext.getCmp('affairTypeId').text);
           		 		 }
					});	
				//点击所属部门的时候 弹出窗口
				appgrid_1.on('beforeedit' ,function(e){
						if(e.field == 'inorgid'){
							_self.globalMgr.winEdit.show({key:Btn_Cfgs.INSERT_BTN_TXT,_self:_self,appgrid_1 :_self.globalMgr.appgrid});
						}
				});
			_self.globalMgr.appgrid = appgrid_1;
			return appgrid_1;
		},
		
		globalMgr : {
			affairType : null,
			citemid : null,
			inorgId : null,
			amount : null ,
			appgrid : null ,
			addRow : null ,
			addField : null ,
			orgtypeId : null , 
			activeKey : {
					INSERT_BTN_TXT : '添加',
					MODIFY_BTN_TXT : '编辑'
			},	
			
			winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg._self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				parentCfg.parent = _this;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					   var winModule = null ;
					   if(winkey == Btn_Cfgs.MODIFY_BTN_TXT){
							var selId = _this.appgrid.getSelId();
							if(winkey == Btn_Cfgs.MODIFY_BTN_TXT && !selId ){
								ExtUtil.alert({msg:"请选择表格中的数据！！！"}); return ;
							}
						}
							winModule="ApportioneEdit";
					Cmw.importPackage('pages/app/oa/todo/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});}
				}
			}
		},
		/**
		 * 为表单元素赋值
		 */
		setFormValues : function(json_data){
			var _self = this;
			var worklog=json_data;
			if(null != worklog){
				_self.globalMgr.citemid = worklog.citemId;
				_self.globalMgr.affairType = worklog.affairType;
				_self.globalMgr.appgrid.addRecord(worklog);
				var store = _self.globalMgr.appgrid.getStore();
				var count = store.getCount();
				var record = store.getAt(count-1);
				record.set('affairTypeId',_self.globalMgr.affairType);
				record.set('allAmount',worklog.amount);
			}
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
			var _this=exports.WinEdit;
			var params  = {store:""};
			if(_this.parent.getXType() =='appgrid'){
					params.store =Ext.encode( _this.dataBeforeSave()[0]);
					params.store2 = Ext.encode(_this.dataBeforeSave()[1]);
					if(null != this.selId){
						params.id = this.selId;
					}
			}
			EventManager.get("./oaWorkLog_save.action",{params:params,sfn :function(json_data){
//				_this.appForm.reset();
				EventManager.query(_this.appgrid,null);
				FormUtil.disabledFrm(appform);	
			},ffn :function(json_data){
				
			}});
		},
		/**
		 * 保存表格数据之前进行数据处理
		 */
		dataBeforeSave : function(){
			var _self = this;
			//获取表格中的数据，进行处理
			var store = _self.globalMgr.appgrid.getStore();
			var count = store.getCount();
			var datas = [];
			var data  = null;
			if(count > 0){
			for(var i=0; i<count; i++){
					var record = store.getAt(i);
					var citemId =  record.get('id')//费用项id
//					var affairTypeId = record.get('affairType');//事务类型名称
					var affairType = record.get('affairTypeId');//事务类型id
					var startDate = record.get('startDate');//工作日志起始时间
					var endDate = record.get('endDate');//工作日志截止时间  有的可能在后台显示包含“T”，加上format格式化即可
					var amount = record.get('amount');//费用总金额
					var reason = record.get('reason');//原由
					var allAmount = record.get('allAmount');//每个部门（门店）的金额
					var inorgId = Number(record.get('inorgId'));//获取每个部门的id
					var xstatus = record.get('xstatus');//费用报销状态
					var yamount = record.get('yamount');//已报销金额
					var orgtype = record.get('orgtype');//费用所属组织类型
					var Isenabled = record.get('Isenabled');
					/**
					 * 工作日志表中的信息
					 */
					var params = { Isenabled : Isenabled , citemId : citemId,affairType :affairType,startDate : startDate,
						endDate : endDate,amount : amount,reason : reason , xstatus :xstatus ,yamount : yamount ,orgtype : orgtype};
			 		/**
			 		 * 费用分摊表中的信息 ： 每个部门分摊的金额，每个部门的id
			 		 */
					var apportion = { amount : allAmount ,inorgId : inorgId }
					datas[0]=[params];
					datas[1]=[apportion];
					}
			}
			return datas; 
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		}
	};
});
