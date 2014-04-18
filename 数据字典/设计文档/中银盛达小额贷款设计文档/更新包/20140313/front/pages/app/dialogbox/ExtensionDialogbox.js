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
			    header: '客户编号',
			    name: 'custCode'
			},
			{
			    header: '客户类型',
			    name: 'custType',
			    renderer: function(val) {
			     return	Render_dataSource.custTypeRender(val);
			    }
			},
			{
			    header: '客户名称',
			    name: 'custName'
			},
			{
			    header: '借款合同号',
			    name: 'contractCode'
			},
			{
			    header: '保证合同号',
			    name: 'guacontractCode'
			},
			{
			    header: '证件类型',
			    name: 'cardType'
			},
			{
			    header: '证件号码',
			    name: 'cardNum'
			},
			{
			    header: '业务品种',
			    name: 'breedName'
			},
			{
			    header: '还款方式',
			    name: 'payTypeName',
			    width:115
			},
			{
			    header: '还款方式CODE',
			    name: 'payType',
			    hidden: true
			},
			{
			    header: '贷款金额',
			    name: 'appAmount',
			    renderer: function(val) {
			     return Cmw.getThousandths(val)+"元";
			    }
			},
			{
			    header: '贷款期限(起)',
			    name: 'payDate',
			    hidden: true
			},
			{
			    header: '贷款期限',
			    name: 'endDate',
			    renderer: function(val,metadata ,record) {
			     var payDate = record.get("payDate");
			     return payDate+"&nbsp;至&nbsp;"+val;
			    }
			},
			{
			    header: '利率类型',
			    name: 'rateType',
			    width: 100,
			    hidden: true
			},
			{
			    header: '贷款利率',
			    name: 'rate',
			    width: 100,
			    renderer: function(val,metadata ,record) {
			     if(!val) val = "0";
			     var rateType = record.get("rateType");
			     rateType = Render_dataSource.rateTypeRender(rateType);
			     return val+"%&nbsp;&nbsp;<span style='color:red;'>("+rateType+")</span>";
			    }
			},
			{
			    header: '管理费率',
			    name: 'mrate',
			    renderer: function(val,metadata ,record) {
				     if(!val) val = "0";
				     var mgrtype = record.get("mgrtype");
				     var mgrtypeName = Render_dataSource.mgrtypeRender(mgrtype);
			         val = val+"%&nbsp;&nbsp;<span style='color:red;'>("+mgrtypeName+")</span>";
				     return val;
			    }
			},
			// 民汇内部利率
			{header: '内部利率类型',  name: 'inRateType',width: 100,
			 renderer: function(val,m) {
			 	m.css='x-grid-back-red'; 
		       return Render_dataSource.rateTypeRender(val);
		    }},
		    {header: '公司内部利率',  name: 'inRate' ,width: 90,
			 	renderer : function(val,m){
		    		m.css='x-grid-back-red'; 
		    		if(val || val==0) val += '%';
		    		return val ;
		    	}
		    },
			
			{
			    header: '贷款余额',
			    name: 'zprincipals',
			    renderer: function(val) {
			     return Cmw.getThousandths(val)+"元";
			    }
			},
			{
			    header: '是否预收息',
			    name: 'isadvance',
			    renderer: function(val) {
			     return Render_dataSource.isadvanceRender(val);
			    }
			},{
			    header: '每期固定还本额',
			    name: 'phAmount',
			    hidden: true
			},{
			    header: '管理费收取方式',
			    name: 'mgrtype',
			    hidden: true
			},{
			    header: '提前还款手续费率',
			    name: 'arate'
			}];
			
			var appgrid = new Ext.ux.grid.AppGrid({
			    title: '符合展期条件的客户',
			    structure: structure_1,
			    url: './fcExtension_listContracts.action',
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
			var barItems = [{
				type : 'label',
				text : '客户类型'
			},{
				type : 'lcbo',
				name : 'custType',
				data:Lcbo_dataSource.getAllDs(Lcbo_dataSource.custType_datas),
				width:100
			},{
				type : 'label',
				text : '客户名称'
			},{
				type : 'txt',
				name : 'custName'
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