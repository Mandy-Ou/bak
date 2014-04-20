/**
 * #DESCRIPTION#
 * @author smartplatform_auto
 * @date 2014-01-19 08:37:53
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		globalMgr : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.MyWindow({
					width:330,
					height : 260,
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
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var _this = exports.WinEdit;
			if(_this.parent.refresh)_this.parent.refresh(_this.optionType,data);	
		},
		/**
		 * 创建Form表单
		 */
		createForm : function(){
				var _self = this ;
				var ttbar = [{
						token : Btn_Cfgs.SAVE_BTN_TXT,
						text : Btn_Cfgs.SAVE_BTN_TXT,	/*-- 保存 --*/ 
						iconCls:Btn_Cfgs.SAVE_CLS,
						tooltip:Btn_Cfgs.SAVE_TIP_BTN_TXT,
						handler : function() {
							_self.saveData();	
//							EventManager.frm_reset(_self.appFrm);
						}
					},{
						text : "添加部门",
						iconCls:'page_add',
						tooltip: "添加部门",
						handler : function(){
							if(_self.globalMgr.orgtype == 2 || _self.globalMgr.orgtype =='2'){
								ExtUtil.info({msg:'警告！！！当前已选门店'});
							}else{
								_self.globalMgr.orgtype = 1;
								var orgtype = _self.globalMgr.orgtype //进行条件选择
								txt_inorgId.reload({orgtype :orgtype});		
								appgrid_1.getStore().add(new Ext.data.Record({ amount : 0.00}));
							}
						}
					},{
						text :  "添加门店",
						iconCls:'page_add',
						tooltip:"添加门店",
						handler : function(){
							if(_self.globalMgr.orgtype == 1 || _self.globalMgr.orgtype =='1'){
								ExtUtil.info({msg:'警告！！！当前已选部门'});
							}else{
								_self.globalMgr.orgtype = 2;
								var orgtype = _self.globalMgr.orgtype //进行条件选择
								txt_inorgId.reload({orgtype :orgtype});		
								appgrid_1.getStore().add(new Ext.data.Record({ amount : 0.00}));
							}
						}
					},{
						token : Btn_Cfgs.CLOSE_BTN_TXT ,
						text : Btn_Cfgs.CLOSE_BTN_TXT,  /*-- 关闭 --*/
						iconCls:Btn_Cfgs.CLOSE_CLS,
						tooltip:Btn_Cfgs.CLOSE_TIP_BTN_TXT,
						handler : function() {
							_self.globalMgr.orgtype = null;
							_self.appWin.hide();
						}
					}]
					
				var structure = [{
					header : 'id' ,name : 'id' , hidden : true},{
					header : '名称' , name :'name' , width : 150}];
					
				function ck_callback(grid , value ,selRows){
							var store = appgrid_1.getStore();
							var record = store.getAt(_self.globalMgr.addRow)//获取当前行
							record.set('id',value.split("##")[0]);
							record.set(_self.globalMgr.addField,value.split("##")[1]);
							record.set("amount",100);
					}
				//部门--从数据库中查
				var txt_inorgId = new Ext.ux.grid.AppComBoxGrid({
						gridWidth : 200,
						fieldLabel : '部门',
						name : 'inorgid',
					    structure:structure,
					    dispCmn:'name',
					    isAll:true,
					    url : './sysDepartment_list.action',
					    needPage : false,
					    isLoad:false,
					    "allowBlank": false,
					    //通过回调函数将数据填充到其他的表格中
					    callback : ck_callback
				});
				//编辑字段 ---分摊金额
				var txt_amount = FormUtil.getMoneyField({
						fielLabel : '分摊金额',
						name : 'amount',
						width : 100
				});
				
				//表格的表头
				var structure_1 = [{
						header : 'id',
						name : 'id',
						hidden : true
					},{
					    header: '费用分摊部门',
					    name: 'inorgid',
						width : 100
					},{
					    header: '分摊金额(单位：元)',
					    name: 'amount',
						width : 130
					}];
				var appgrid_1 = new Ext.ux.grid.MyEditGrid({
				    tbar : ttbar,
				    title: '部门添加',
				    editEles : {1 :txt_inorgId  , 2 : txt_amount },
				    structure: structure_1,
				    needPage: false,
				    isLoad: false,
				    keyField: 'id',
				    height : 260 ,
				    listeners : {
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
			return appgrid_1;
		},
			
		globalMgr : {
			orgtype : null,
			addField : null,
			addRow : null
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
			var _self = this;
			var _appgrid = _self.parent.globalMgr.appgrid;
			//获取所有的数据
			var row = _self.appFrm.getModRowVals();
			//进行转换
			var json = Ext.util.JSON.decode(row);
			
			//逗号 
			var splits = ",";
			//获取部门id
			var inorgId = 0;
			//部门
			var inorgid = "";
			//每个部门的费用
			var allAmount = 0 ;
			/**
			 * 费用所属组织类型，根据部门或者是门店判断
			 */
			var orgtype = _self.globalMgr.orgtype;
			//总费用
			var amount = 0;
			Cmw.print(json);
			if(json.length >0){
				for(var i = 0 ; i<json.length ; i++){
					if(i == 0 ){
						inorgid += json[i].inorgid;
						inorgId += json[i].id;
						allAmount +=json[i].amount;
					}else{
						inorgid += splits +json[i].inorgid;
						inorgId += splits +json[i].id;
						allAmount += splits+json[i].amount;
					}
					amount +=json[i].amount;
				}
			}else{
				inorgid = json.inorgid;
				inorgId = json.id;
				amount +=json.amount;
				allAmount =amount;
			}
			var params = {inorgid : inorgid ,inorgId : inorgId , amount : amount ,allAmount :allAmount, orgtype : orgtype};
			this.refresh(params);
			this.appWin.hide();
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		}
	};
});
