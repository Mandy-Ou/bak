/**
 * 打印放款单
 * @author 赵世龙
 * @date 2013-12-10
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		detailPnl : null,	//详情面板
		appWin : null,
		formId : null,
		tbar : null,
		flag:false,
		sid:null,
		tempId:null,
		content:null,
		tempId:null,
		sysid : null,
		setParentCfg:function(parentCfg){
			this.sysid = parentCfg.parent.sysId;
			if(parentCfg.formId){
				this.formId = parentCfg.formId;
			}else{
				this.parent = parentCfg.parent;
			}
				if(parentCfg.tempId&&this.tempId!=parentCfg.tempId){
					this.flag=true;
					this.tempId=parentCfg.tempId;     
				}
			this.parentCfg=parentCfg;
			this.tbar=this.createBbar();
			this.sid=this.parent.getSelId();
			//--> 如果是Grid ，应该修改此处
//			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.appWin = new Ext.ux.window.MyWindow({
				hiddenBtn: true,
				getParams:this.getParams,
				width:950
			});
			this.detailPnl = this.createPnl();
			this.appWin.add(this.detailPnl);
		},
		/**
		 * 显示弹出窗口
		 * @param _parentCfg 弹出之前传入的参数
		 */
		show : function(_parentCfg){
			var _this=this;
			if(_parentCfg) this.setParentCfg(_parentCfg);
			if(!this.appWin){
				this.createAppWindow();
			}
			if(_this.flag){
				_this.upPanel(_this.detailPnl)
			}
			if(_parentCfg.ele){
				this.appWin.show(_parentCfg.ele.frame("C3DAF9", 1, { duration: 1 }));
			}else{
				this.appWin.show();
			}
			
		},
		/**
	 * 创建工具栏	
	 */
	createBbar : function(){
		var _this=this;
		this.barItems = [{
				token:"打印预览",
				text:Btn_Cfgs.PRINT_PREVIEW_BTN_TXT,
				iconCls:Btn_Cfgs.PRINT_PREVIEW_CLS,
				tooltip:Btn_Cfgs.PRINT_PREVIEW_TIP_BTN_TXT,
				handler : function(){
					_this.print(1);
				}
			},
			{type:"sp"},{ 
				token:"打印",
				text : Btn_Cfgs.PRINT_BTN_TXT,  /*-- 打印--*/
				iconCls:Btn_Cfgs.PRINT_CLS,
				tooltip:Btn_Cfgs.PRINT_TIP_BTN_TXT,
				handler : function(){
					_this.print(2);
				}
			},{
				token:"打印设计",
				text:Btn_Cfgs.PRINT_DESIGN_BTN_TXT,
				iconCls:Btn_Cfgs.PRINT_DESIGN_CLS,
				tooltip:Btn_Cfgs.PRINT_DESIGN_TIP_BTN_TXT,
				handler : function(){
					_this.print(3);
				}
			},{
				text : Btn_Cfgs.CLOSE_BTN_TXT,  /*-- 关闭 --*/
				iconCls:Btn_Cfgs.CLOSE_CLS,
				tooltip:Btn_Cfgs.CLOSE_TIP_BTN_TXT,
				handler : function(){
					_this.appWin.hide();
				}
			}];
		
		var appBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:this.barItems});
		return appBar;
	},
	print : function(val){
		var _this=this;
		if(!_this.content)ExtUtil.error({msg:'没有可打印的内容！'});
		if(val==1)
			PrintManager.preview(_this.content);
		if(val==2)
			PrintManager.print(_this.content);
		if(val==3)
			PrintManager.design(_this.content);
	},
		/**
		 * 销毁组件
		 */
		destroy : function(){
			this.appWin.close();	//关闭并销毁窗口
			this.appWin = null;		//释放当前窗口对象引用
		},
		/**
		 * 获取选中的记录参数
		 */
		getParams : function(){
			var _this = exports.WinEdit;
			var parent = _this.parent;
			var selId  = null;
			var sysId = null;
			if(parent){
				var selId = parent.getSelId();
			}else{
				selId = _this.formId;
				sysId = _this.sysid;
			}
			var params = {formId:selId,sysId:sysId};
			return params;
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
		 * 创建详情面板
		 */
		createPnl : function(){
			var _this = exports.WinEdit;
			var parent = exports.WinEdit.parent;
			 var panel = new Ext.Panel({
				title : '',
				autoWidth: true,
				tbar:_this.tbar,
				autoScroll : true,
				layout:'fit',
				renderTo: Ext.getBody()
			});
//			_this.upPanel(panel);
			return panel;
		},
		upPanel:function(panel){
			var _this = exports.WinEdit;
			var count="id,loanId,sysid";
				EventManager.get("./fcPrintTemp_getClause.action",{params:{count:count,id:_this.tempId,loanId:_this.sid,printType:2,sysid:_this.sysid},sfn : function(jsonData){
					_this.content=jsonData.content;
				 	var concatHtml = ["<div width='100%'>"+_this.content+"</div>"];
					panel.update(concatHtml);	
				}});	
		}
	};
});