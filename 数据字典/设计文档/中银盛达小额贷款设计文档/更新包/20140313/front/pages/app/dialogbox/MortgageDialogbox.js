/**
 * 个人客户选择弹出窗口
 * 小额贷款系统 --> 业务办理 --> 贷款申请 用到
 * @author 程明卫
 * @date 2012-12-07
 */
define(function(require, exports) {
	exports.DialogBox = {
		parentCfg : null,
		params : null,
		appWin : null,
		appGrid : null,
		isCheck : true,	/*是否显示复选框*/
		createAppWindow : function(){
			this.appGrid = this.createAppGrid();
			this.appWin = new Ext.ux.window.MyWindow({width:500,height:350,items:this.appGrid});
		},
		createAppGrid : function(){
			var _this = this;
			var structure = [{
			    header: 'id',
			    name: 'id',
			    hidden :true,
			    width: 125
			},{
			    header: '贷款申请单ID',
			    name: 'formId',
			    hidden :true,
			    width: 125
			},{
			    header: '基础数据id',
			    name: 'gtype',
			    hidden :true,
			    width: 125
			},{
			    header: '抵押物名称',
			    name: 'name',
			    width: 125
			},{
			    header: '单位',
			    name: 'unint',
			    hidden :true,
			    width: 125
			},{
			    header: '数量',
			    name: 'quantity',
			    width: 80,
			     renderer: function(val,cellmeta,record,rowIndex,columnIndex,store){
			    	var unint = record.data['unint'];
			    	return val+unint;
			    }
			},
			{
			    header: '原价值(元)',
			    name: 'oldVal',
			    width: 125,
			     renderer : Render_dataSource.moneyRender
			},
			{
			    header: '抵押价值',
			    name: 'mpVal',
			    width: 125,
			     renderer : Render_dataSource.moneyRender
			},
			{
			    header: '抵押人',
			    name: 'mman',
			    width: 125
			},
			{
			    header: '联系电话',
			    name: 'conTel',
			    width: 125
			},
			{
			    header: '抵押时间',
			    name: 'morTime',
			    width: 125
			},
			{
			    header: '抵押物所在地',
			    name: 'address',
			    width: 150
			},{
			    header: '备注',
			    name: 'remark',
			    width: 150
			}];
			var appgrid = new Ext.ux.grid.AppGrid({
			    title: '抵押物列表',
			    structure: structure,
			    tbar : _this.getToolBar(),
			    url: '	./fcMortgage_list.action',
			    needPage: true,
			    isLoad: false,
			    selectType : 'check' ,
			    keyField: 'id'
			});
//			appgrid.addListener('dblclick',function(e){
//				_this.setValue();
//			});
			return appgrid;
		},
		getToolBar : function(){
			var _this = this;
			var toolBar = null;
			var barItems = [{
				type : 'label',
				text : '抵押名称'
			},{
				type : 'search',
				name : 'name',
				onTrigger2Click : function(){
					var params = toolBar.getValues();
					if(!params) params = {};
					Ext.apply(params,_this.params);
					EventManager.query(_this.appGrid,params);
				}
			},{text:Btn_Cfgs.CONFIRM_BTN_TXT,iconCls:Btn_Cfgs.CONFIRM_CLS,handler:function(){
				_this.setValue();
			}},{
				text : Btn_Cfgs.CLOSE_BTN_TXT,
				iconCls:Btn_Cfgs.CLOSE_CLS,
				tooltip:Btn_Cfgs.CLOSE_TIP_BTN_TXT,
				handler : function(){
					_this.appWin.hide();
				}
			}];
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
			return toolBar;
		},
		/**
		 * 赋值
		 */
		setValue : function(){
			var id = this.appGrid.getSelIds();
			if(!id){
				return;
			}
			var record = this.appGrid.getSelRows();
			var mpVal = this.appGrid.getCmnArrVals("mpVal");
			var totaloldVal=0;
			for(var i = 0;i<mpVal.length;i++){
				totaloldVal += parseFloat(mpVal[i].mpVal);
			}
			var data = {id:id,record:record,conAmount:totaloldVal};
			this.appWin.hide();
			this.parentCfg.callback(data);
		},
		/**
		 * 显示弹出窗口
		 * @param _parentCfg 弹出之前传入的参数
		 */
		show : function(_parentCfg){
			if(_parentCfg) this.setParentCfg(_parentCfg);
			if(!this.appWin){
				this.createAppWindow();
				this.appGrid.reload(this.params);
			}
			if(_parentCfg.parent){
				this.appWin.show(_parentCfg.parent);
			}else{
				this.appWin.show();
				this.appGrid.show();
			}
			
		},
		setParentCfg : function(_parentCfg){
			this.parentCfg = _parentCfg;
			this.params = this.parentCfg.params;
			if(this.parentCfg.isCheck){
				this.isCheck = this.parentCfg.isCheck;
			}
		},
		/**
		 * 销毁组件
		 */
		destroy : function(){
			if(null != this.appWin){
				this.appWin.close();	//关闭并销毁窗口
				this.appWin = null;		//释放当前窗口对象引用
			}
		}
	};
});