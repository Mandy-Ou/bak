/**
 * 展期客户选择弹出窗口
 * 小额贷款系统 --> 业务办理 --> 展期申请 用到
 * @author 程明卫
 * @date 2013-09-08
 */
define(function(require, exports) {
	exports.DialogBox = {
		parentCfg : null,
		params : null,
		appWin : null,
		appGrid : null,
		createAppWindow : function(){
			this.appGrid = this.createAppGrid();
			this.appWin = new Ext.ux.window.MyWindow({width:800,height:350,items:this.appGrid});
		},
		createAppGrid : function(){
			var _this = this;
			var structure_1 = [{
				header :'id',
				name:'id',
				hidden : true,
				 width: 120
			},
			{header :'entrustContractId',
				hidden : true,
				name:'entrustContractId',
				 width: 120
			},
			{
			    header: '委托合同编号',
			    name: 'code',
			    width: 120
			},
			{
			    header: '委托人姓名',
			    name: 'name'
			},
			{
			    header: '委托金额',
			    name: 'appAmount',
			    width: 90,
				renderer: function(val) {
			    return (val && val>0) ? Cmw.getThousandths(val): '';}
			},
			{
			    header: '期限',
			    name: 'yearLoan',
			    width: 90,
					renderer: function(r,t,x) {
						if(x.get('monthLoan')==0){
							return r+'&nbsp;年'+'&nbsp;&nbsp;';
						}else{
							return r+'&nbsp;年'+'&nbsp;&nbsp;'+x.get('monthLoan')+'&nbsp;月';
						}
				    	
				    }
			},
			{
			    header: '期限',
			    name: 'monthLoan',
			    width: 90,hidden:true
			},
			{	
			    header: '利率',
			    name: 'rate',
			    width: 90,
			    renderer : function(value , cellmeta,record){
			    	return value + Render_dataSource.rateUnit_datas(record.get('unint'));
			    }
			},	
			{
				header : '利率单位',
				name : 'unint',
				hidden : true
			},
			{
			    header: '委托生效日期',
			    name: 'payDate',
			    width: 90
			},
			{
			    header: '委托失效日期',
			    name: 'endDate',
			    width: 90
			},	
			{
			    header: '每月收益金额',
			    name: 'iamount',
			    width: 90,
				renderer: function(val) {
			    return (val && val>0) ? Cmw.getThousandths(val): '';}
			},
			{
				header: '合同签订日期',
			    name: 'doDate',
			    width: 90,hidden:true
				
			}
			];
			
			var appgrid = new Ext.ux.grid.AppGrid({
			    title: '委托合同列表',
			    structure: structure_1,
			    url : './fuBamountApply_listBam.action',
			    needPage: false,
			    isLoad: false,
			    tbar : this.getToolBar(),
			    keyField: 'contractId'
			});
			appgrid.addListener('dblclick',function(e){
				_this.setValue();
			});
			return appgrid;
		},
		getToolBar : function(){
			var _this = this;
			var toolBar = null;
			var barItems = [/*{
				type : 'label',
				text : '委托人类型'
			},{
				type : 'lcbo',
				name : 'ctype',
				data:Lcbo_dataSource.getAllDs(Lcbo_dataSource.custType_datas),
				width:100
			},*/{
				type : 'label',
				text : '姓名'
			},{
				type : 'txt',
				name : 'name'
			},{
				text : Btn_Cfgs.QUERY_BTN_TXT,
				iconCls:'page_query',
				tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
				handler : function(){
					var params = toolBar.getValues();
					if(!params) params = {};
					Ext.apply(params,_this.params);
					EventManager.query(_this.appGrid,params);
				}
			},{
				text : Btn_Cfgs.RESET_BTN_TXT,
				iconCls:'page_reset',
				tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
				handler : function(){
					toolBar.resets();
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
			var id = this.appGrid.getSelId();
			if(!id){
				return;
			}
			var record = this.appGrid.getSelRow();
			this.parentCfg.callback(id,record);
			this.appWin.hide();
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
			if(_parentCfg.parent){
				this.appWin.show(_parentCfg.parent);
			}else{
				this.appWin.show();
			}
			this.appGrid.reload(this.params);
		},
		setParentCfg : function(_parentCfg){
			this.parentCfg = _parentCfg;
			this.params = this.parentCfg.params;
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