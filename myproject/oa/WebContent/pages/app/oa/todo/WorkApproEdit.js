/**
 * #DESCRIPTION#
 * @author smartplatform_auto
 * @date 2014-01-23 04:51:59
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({
					width:800,
					height : 400,
					getUrls:this.getUrls,
					appFrm : this.appFrm,
					optionType : this.optionType,
					refresh : this.refresh
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
		 * 获取各种URL配置
		 * addUrlCfg : 新增 URL
		 * editUrlCfg : 修改URL
		 * preUrlCfg : 上一条URL
		 * preUrlCfg : 下一条URL
		 */
		getUrls : function(){
			var urls = {};
			var parent = exports.WinEdit.parent;
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{},defaultVal:{}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : '#ADDURLCFG#',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : '#GETURLCFG#',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : '#PREURLCFG#',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : '#NEXTURLCFG#',cfg :cfg};
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
		 * 创建Form表单
		 */
		createForm : function(){
		
			var structure_1 = [{
				id : 'id',//费用项的id
				name : 'id',
				hidden : true
			},{
			    header: '费用项',
			    name: 'citemId'
			},{
			    header: '事务类型',
//			    id : 'affairType',
			    name: 'affairType'
			},{
			    header: '起始日期',
			    name: 'startDate',
			    //页面的显示格式
				renderer:Ext.util.Format.dateRenderer('y-m-d')
			},{
			    header: '截止日期',
			    name: 'endDate',
			    //页面的显示格式
				renderer:Ext.util.Format.dateRenderer('y-m-d')
			},{
				header : '费用所属部门',
				name : 'inorgid'
			},{
			    header: '费用金额',
			    name: 'amount'/*,
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
			
			var appgridpanel_1 = new Ext.Panel({
			    items: [appgrid_1]
			})
						
				return appgridpanel_1;
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
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		}
	};
});
