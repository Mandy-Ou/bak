/**
 * 流程业务配置
 * @author 程明卫
 * @date 2013-11-23 19:54
 */
define(function(require, exports) {
	exports.viewUI = {
		params : null,
		appMainPanel : null,
		mustFrmGrid : null,
		opFrmGrid : null,
		formDialogBox : null,
		bussType : null,
		btnIdObj : {
			btnMustAddId : Ext.id(null,'btnMustAdd'),/*添加必做业务按钮ID*/
			btnMustDelId : Ext.id(null,'btnMustDel'),/*删除必做业务按钮ID*/
			btnOpAddId : Ext.id(null,'btnOpAdd'),/*添加选做业务按钮ID*/
			btnOpDelId : Ext.id(null,'btnOpDel')/*删除选做业务按钮ID*/
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
			this.mustFrmGrid = this.createMustFrmGrid();
			this.opFrmGrid = this.createOpFrmGrid();
			this.appMainPanel = new Ext.Panel({title:'<strong>流程业务事项配置</strong>',autoScroll:true,items:[this.mustFrmGrid,this.opFrmGrid],
			 listeners : {render : function(pnl){pnl.doLayout();}}});
		},
		createMustFrmGrid : function(){
			var _this = this;
			var structure_1 = [
			{header: '业务表单ID',name: 'custFormId', width: 100,hidden: true},
			{ header: '业务功能', name: 'formName', width: 100},
			{ header: '功能权限', name: 'funRights', width: 150},
			{ header: '业务顺序', name: 'orderNo', width: 60}
			];
			var orderNo = FormUtil.getIntegerField({fieldLabel : '排列顺序',name:'orderNo',value:0});
			var editEles = {3:orderNo};
			
			var btnAddHtml = this.getButtonHml(this.btnIdObj.btnMustAddId,'添加');
			var btnDelHtml = this.getButtonHml(this.btnIdObj.btnMustDelId,'删除');
			var appgrid_1 = new Ext.ux.grid.MyEditGrid({
			    title: '必做业务事项'+btnAddHtml+btnDelHtml+Msg_SysTip.msg_orderNoEdit,
			    structure: structure_1,
			    editEles : editEles,
			    needPage: false,
			    isLoad: false,
			    height : 240,
			    autoScroll : true,
			    selectType : 'check',
			    keyField: 'id'
			});
			this.addEditSortListener(appgrid_1);
			return appgrid_1;
		},
		createOpFrmGrid : function(){
			var _this = this;
			var structure_1 = [
			{ header: '业务表单ID', name: 'custFormId', width: 100, hidden: true},
			{ header: '业务功能', name: 'formName', width: 100},
			{ header: '功能权限', name: 'funRights', width: 150},
			{ header: '业务顺序', name: 'orderNo', width: 60}
			];
			
			var orderNo = FormUtil.getIntegerField({fieldLabel : '排列顺序',name:'orderNo',value:0});
			var editEles = {3:orderNo};
			var btnAddHtml = this.getButtonHml(this.btnIdObj.btnOpAddId,'添加');
			var btnDelHtml = this.getButtonHml(this.btnIdObj.btnOpDelId,'删除');
			var appgrid_1 = new Ext.ux.grid.MyEditGrid({
			    title: '选做业务事项'+btnAddHtml+btnDelHtml+Msg_SysTip.msg_orderNoEdit,
			    structure: structure_1,
			    editEles : editEles,
			    needPage: false,
			    isLoad: false,
			    height : 240,
			    autoScroll : true,
			    selectType : 'check',
			    keyField: 'id'
			});
			var _this = this;
			appgrid_1.addListener('afterrender',function(gridpanel){
				_this.addListenersToButtons();
			});
			this.addEditSortListener(appgrid_1);
			return appgrid_1;
		},
		getButtonHml : function(id,text){
			var html = "&nbsp;&nbsp;<span class='btnBussNodeCls' id='"+id+"'>"+text+"</span>&nbsp;&nbsp;";
			return html;
		},
		/**
		 * 为必做业务和选做业务添加输入业务顺序后排序的事件
		 */
		addEditSortListener : function(appgrid_1){
			appgrid_1.addListener('afteredit',function(e){
				e.record.commit();
				var store = appgrid_1.getStore();
				var count = store.getCount();
				if(count==1) return;
				var records = store.getRange(0,count-1);
				var collection = new Ext.util.MixedCollection();
				collection.addAll(records);
				collection.sort('ASC',function(record1,record2){
					var orderNo1 = record1.get("orderNo");
					var orderNo2 = record2.get("orderNo");
					return orderNo1 < orderNo2 ? -1 : 1;
				});
				store.removeAll();
				var records = collection.getRange(0,collection.getCount()-1);
				store.add(records);
			});
		},
		addListenersToButtons : function(){
			var _this = this;
			var btnMustAdd = addClass(this.btnIdObj.btnMustAddId);
			btnMustAdd.on('click',function(e,targetEle,obj){
				var parentCfg = {};
				parentCfg.parent = btnMustAdd;
				parentCfg.grid = _this.mustFrmGrid;
				clickDoDtas(parentCfg);
			},this);
			
			var btnMustDel = addClass(this.btnIdObj.btnMustDelId);
			btnMustDel.on('click',function(e,targetEle,obj){
				clickDelDatas(_this.mustFrmGrid);
			},this);
			
			var btnOpAdd = addClass(this.btnIdObj.btnOpAddId);
			btnOpAdd.on('click',function(e,targetEle,obj){
				var parentCfg = {};
				parentCfg.parent = btnOpAdd;
				parentCfg.grid = _this.opFrmGrid;
				clickDoDtas(parentCfg);
			},this);
			
			var btnOpDel = addClass(this.btnIdObj.btnOpDelId);
			btnOpDel.on('click',function(e,targetEle,obj){
				clickDelDatas(_this.opFrmGrid);
			},this);
			
			/**
			 * 点击添加时的数据处理
			 */
			function clickDoDtas(parentCfg){
				parentCfg.params = {sysId : _this.params.sysId,code:ACCORDION_CODE_TYPE_FORM_PREFIX};
				if(_this.bussType==2){
					parentCfg.params.code = ACCORDION_CODE_TYPE_SUBFORM_PREFIX;
				}
				parentCfg.callback = function(ids,txts){
					var data = {};
					var grid = parentCfg.grid;
					var idsArr = ids.split(",");
					var txtsArr = txts.split(",");
					var datas = [];
					var currIndex = -1;
					for(var i=0,count=idsArr.length;i<count;i++){
						var id = idsArr[i]+"";
						if(id.indexOf("M") != -1){
							currIndex++;
							var formName = txtsArr[i];
							var data = {custFormId:id.replace("M",""),formName:formName,funRights:[]};
							datas[currIndex] = data;
							continue;
						}
						var currData = datas[currIndex];
						currData.funRights[currData.funRights.length] = txtsArr[i];
					}
					if(null == datas || datas.length == 0) return;
					var store = grid.getStore();
					var len = store.getCount();
					if(len == 0){
						for(var i=0,count=datas.length;i<count;i++){
							var data = datas[i];
							data.funRights = data.funRights.join(",");
							grid.addRecord(data);
						}
					}else{
						for(var i=0,count=datas.length;i<count;i++){
							var data = datas[i];
							var formName = data.formName;
							var isAdd = true;
							for(var j=0; j<len; j++){
								var record = store.getAt(j);
								var _formName = record.get("formName");
								if(_formName && _formName == formName){
									isAdd = false;
									break;
								}
							}
							if(isAdd){
								data.funRights = data.funRights.join(",");
								grid.addRecord(data);
							}
						}
					}
				};
				_this.showFormDialogBox(parentCfg);
			}
			
			/**
			 * 添击删除指定Grid数据
			 */
			function clickDelDatas(grid){
				var selRows = grid.getSelRows();
				if(!selRows || selRows.length == 0){
					 ExtUtil.alert({msg:Msg_SysTip.msg_selDeleteData});
					 return;
				}
				grid.removeRecords(selRows);
			}
			
			/**
			 * 为按钮添加点击和鼠标经过样式
			 */
			function addClass(eleId){
				var btnEle = Ext.get(eleId);
				btnEle.addClassOnClick("nodeClickCls");
				btnEle.addClassOnOver("x-btn-text");
				return btnEle;
			}
			
		},
		showFormDialogBox : function(parentCfg){
			parentCfg.showClearCheck = true; //当 show 时清除上次选中的节点
			parentCfg.bussType = this.bussType;
			if(this.formDialogBox){
				this.formDialogBox.show(parentCfg);
				return;
			}
			var _this = this;
			Cmw.importPackage('pages/app/dialogbox/BussFormDialogbox',function(module) {
			 	_this.formDialogBox = module.DialogBox;
			 	_this.formDialogBox.show(parentCfg);
			});
		},
		setParams : function(params) {
			this.params = params;
			this.bussType = params.bussType
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
		setValues : function(cfgDatas){
			this.reset();
			if(!cfgDatas) return;
			var mustFormCfgs = cfgDatas.mustFormCfgs;
			if(mustFormCfgs && mustFormCfgs.length > 0){
				for(var i=0,count=mustFormCfgs.length; i<count; i++){
					var data = mustFormCfgs[i];
					this.mustFrmGrid.addRecord(data);
				}
			}
			
			var arrOp = cfgDatas.arrOp;
			if(arrOp && arrOp.length > 0){
				for(var i=0,count=arrOp.length; i<count; i++){
					var data = arrOp[i];
					this.opFrmGrid.addRecord(data);
				}
			}
		},
		/**
		 * 获取数据
		 * @param isCheck 在获取数据是，是否需要检查
		 */
		getValues : function(){
			
			var mustFormCfgs = [];
			var mustStore = this.mustFrmGrid.getStore();
			if(mustStore.getCount() > 0){
				mustFormCfgs = mustStore.getRange(0,mustStore.getCount()-1);
				for(var i=0,count=mustFormCfgs.length; i<count; i++){
					mustFormCfgs[i] = mustFormCfgs[i].data;
				}
			}
			
			/**--> step 4 获取选做事项业务表单数据 <--**/
			var opStore = this.opFrmGrid.getStore();
			var opFormCfgs = [];
			if(opStore.getCount() > 0){
				opFormCfgs = opStore.getRange(0,opStore.getCount()-1);
				for(var i=0,count=opFormCfgs.length; i<count; i++){
					opFormCfgs[i] = opFormCfgs[i].data;
				}
			}
			var bussFormDatas = {
				mustFormCfgs : mustFormCfgs,
				opFormCfgs : opFormCfgs
			};
			return bussFormDatas;
		},
		/**
		 * 重置
		 */
		reset : function(){
			this.mustFrmGrid.removeAll();
			this.opFrmGrid.removeAll();
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
			if(null != this.mustFrmGrid){
				 this.mustFrmGrid.destroy();
				 this.mustFrmGrid = null;
			}
			if(null != this.opFrmGrid){
				 this.opFrmGrid.destroy();
				 this.opFrmGrid = null;
			}
			if(null != this.formDialogBox){
				 this.formDialogBox.destroy();
				 this.formDialogBox = null;
			}
		}
	}
});