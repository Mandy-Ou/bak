/**
 * 打印窗口
 * 小额贷款系统 --> 公司账户 --> 财务财号映射 用到
 * @author 程明卫
 * @date 2012-12-07
 */
define(function(require, exports) {
	exports.DialogBox = {
		parentCfg : null,
		params : null,
		tbar : null,
		appWin : null,
		iframeId :　null,
		tempId:null,
		content:null,
		mypanel:null,
		flag:false,
//		parent:null,
		sid:null,
		url : './dgPrint_doprint.action',
		printDataSourceId : 'txr_printDataSourceId_2013',
		createAppWindow : function(){
			this.mypanel=this.panel();
			this.tbar = this.getToolBar();
			this.iframeId = Ext.id(null,'iframePrint');  
			var htmlArr = ['<iframe id="'+this.iframeId+'" name="'+this.iframeId+'"  width="100%" style="width:100%; height:100% " frameborder="0"></iframe>',
			'<span id="'+this.printDataSourceId+'" style="display:none;"></span>'];
			var iframeHtml = htmlArr.join(" ");
			this.appWin = new Ext.ux.window.MyWindow({title : "单据打印",width:750,height:600,autoScroll:true,draggable:true,maximizable:true,minimizable:true,tbar:this.tbar});
		},
		getToolBar : function(){
			var _this = this;
			var toolBar = null;
			var barItems = [{
				token:"打印预览",
				text:Btn_Cfgs.PRINT_PREVIEW_BTN_TXT,
				iconCls:Btn_Cfgs.PRINT_PREVIEW_CLS,
				tooltip:Btn_Cfgs.PRINT_PREVIEW_TIP_BTN_TXT,
				handler : function(){
					toolBar.disable();
					PrintManager.preview(_this.content);
//					window.parent.frames[_this.iframeId].PrintMgr.preview();
					toolBar.enable();
				}
			},{
				token:"打印",
				text:Btn_Cfgs.PRINT_BTN_TXT,
				iconCls:Btn_Cfgs.PRINT_CLS,
				tooltip:Btn_Cfgs.PRINT_TIP_BTN_TXT,
				handler : function(){
//					window.parent.frames[_this.iframeId].PrintMgr.print();
					PrintManager.print(_this.content);
				}
			},{
				token:"打印设计",
				text:Btn_Cfgs.PRINT_DESIGN_BTN_TXT,
				iconCls:Btn_Cfgs.PRINT_DESIGN_CLS,
				tooltip:Btn_Cfgs.PRINT_DESIGN_TIP_BTN_TXT,
				handler : function(){
					PrintManager.design(_this.content);
//					window.parent.frames[_this.iframeId].PrintMgr.design();
				}
			},{
				token:"关闭",
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
		 * 显示弹出窗口
		 * @param _parentCfg 弹出之前传入的参数
		 */
		show : function(_parentCfg){
			if(_parentCfg) this.setParentCfg(_parentCfg);
			if(!this.appWin){
				this.createAppWindow();
				this.appWin.add(this.mypanel);
			}
			if(_parentCfg.parent){
				this.appWin.show(_parentCfg.parent);
			}else{
				this.appWin.show();
			}
			this.loadData(this.params,this.mypanel);
		},
		setParentCfg : function(_parentCfg){
			this.parentCfg = _parentCfg;
			this.params = this.parentCfg.params;
			if(_parentCfg.tempId)
				this.tempId=_parentCfg.tempId;
		},
		/**
		 * 加载打印数据
		 */
		loadData : function(_params,panel){
			var _this=this;
			var count="id,loanId";
			
				EventManager.get("./fcPrintTemp_getClause.action",{params:{count:count,id:this.tempId,loanId:_params.contractId,printType:_params.printType},sfn : function(jsonData){
					_this.content=jsonData.content;
				 	var concatHtml = ["<div width='100%' width='80%' style='margin-left:10%;margin-right:10%;margin-bottom:10%;'>"+_this.content+"</div>"];
					panel.update(concatHtml);
				}});	
		},
		panel : function(){
			 var panel = new Ext.Panel({
				title : '',
				autoWidth: true,
				autoScroll : true,
				layout:'fit',
				renderTo: Ext.getBody()
			});
			return panel;
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