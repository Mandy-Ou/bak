/**
 * 回款收条详情
 * 
 * @author 郑符明
 */
define(function(require, exports) {
	exports.viewUI = {
		parent : null,
		parentCfg : null,
		params : null,
		tbar : null,
		appWin : null,
		iframeId :　null,
		tempId:null,
		content:null,
		mypanel:null,
		globalMgr : null,
		orgtype : null,
		createAppWindow : function(){
			this.tbar = this.getToolBar();
			this.iframeId = Ext.id(null,'receiptBookDetail');  
//			var htmlArr = ['<iframe id="'+this.iframeId+'" name="'+this.iframeId+'"  style="width:100%; height:100% " frameborder="0" src="'+this.globalMgr.url+this.globalMgr.params+'"></iframe>'];
			var htmlArr = ['<iframe id="'+this.iframeId+'" name="'+this.iframeId+'"  style="width:100%; height:100% " frameborder="0" src="pages/app/funds/receiptbackpayment/BackReceiptEdit.jsp'+this.globalMgr.params+'"></iframe>'];
			var iframeHtml = htmlArr.join(" ");
			this.appWin = new Ext.ux.window.MyWindow({title : "回款收条详情",width:900,height:600,autoScroll:true,draggable:true,maximizable:true,minimizable:true,tbar:this.tbar,html :iframeHtml});
			this.appWin.on('aftershow',function(){
				Cmw.mask(this.appWin,Msg_SysTip.msg_loadingUI);
			});
		},
		getToolBar : function(){
			var _this = this;
			var toolBar = null;
			var barItems = [{
				token:"保存",
				text:Btn_Cfgs.SAVE_BTN_TXT,
				iconCls:Btn_Cfgs.SAVE_CLS,
				tooltip:Btn_Cfgs.SAVE_TIP_BTN_TXT,
				handler : function(){
					_this.makeDatas(1);
				}
			},{
				text : Btn_Cfgs.PRINT_BTN_TXT,  /*-- 打印--*/
				iconCls:Btn_Cfgs.PRINT_CLS,
				tooltip:Btn_Cfgs.PRINT_TIP_BTN_TXT,
				handler : function(){
					_this.print();
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
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems});
			return toolBar;
		},
		print : function(){
			var iframeEle = window.parent.frames[this.iframeId];
			if(!iframeEle) return;
			//PrintManager.fillDatas();
			PrintManager.preview(iframeEle.document.body.innerHTML);
		},
		/**
		 * 显示弹出窗口
		 * @param _parentCfg 弹出之前传入的参数
		 */
		show : function(_parentCfg){
			var _this = this;
			if(_parentCfg) this.setParentCfg(_parentCfg);
			var id = this.globalMgr.id;
			var orgtype = this.globalMgr.orgtype;
			this.globalMgr.params = "?id=" +id+"&orgtype="+ orgtype;
			if( !this.appWin){
				this.createAppWindow();
			}else{
				this.appWin.doLayout();
				var parentContainerId = this.appWin.getId();
				var url = this.globalMgr.url +this.globalMgr.params+"&parentContainerId="+parentContainerId;
				var iframeEle = Ext.get(this.iframeId);
				if(!iframeEle) return;
				Cmw.mask(this.appWin,Msg_SysTip.msg_loadingUI);
				window.parent.frames[this.iframeId].location= url;
			}
			this.appWin.show();
		},
		/*loadData : function(orgtype,pat){
			var id = pat;
			var iframeEle = Ext.get(this.iframeId);
			if(iframeEle){
//				var url = iframeEle.dom.src;
				var url = this.globalMgr.url;
				var parentContainerId = this.appWin.getId();
				if(null != orgtype){
					url += "?id=" + id+"&orgtype="+orgtype+"&parentContainerId="+parentContainerId;
					window.parent.frames[this.iframeId].location = url;
				}
			}
		},*/
		setParentCfg : function(_parentCfg){
			this.parentCfg = _parentCfg;
			this.parent = this.parentCfg.parent;
			var self = this.parentCfg.self;
			this.params = this.parentCfg.params;
			this.globalMgr.id = this.parentCfg.id;
			this.globalMgr.orgtype = this.parentCfg.orgtype;
			if(_parentCfg.tempId)
				this.tempId=_parentCfg.tempId;
		},
		/**
		 * 通过刷新将数据填充到表单中，进行编辑
		 */
		refresh : function() {
			if (!this.appWin) {
				return ;
			}
			if (!this.appWin.rendered) {
				var _this = this;
				this.appWin.addListener('render', function(cmpt) {
						this.loadData();
					});
			} else {
					this.loadData();
			}
		},
		/**
		 * 加载数据
		 */
		makeDatas : function(){
			var _this = this;
			var iframeEle = window.parent.frames[this.iframeId];
			if(!iframeEle)
				return ;
			/*添加*/
				var params = {};
				var document = iframeEle.document;
				var id = this.globalMgr.id;
				var reman = document.getElementById("reman").value;
				var name = document.getElementById("name").value;
				var rcount =document.getElementById("rcount").value;
				var rnum = document.getElementById("rnum").value;
				var outMan =document.getElementById("outMan").value;
				var omaccount = document.getElementById("omaccount").value;
				var pbank = document.getElementById("pbank").value;
				var outDate = document.getElementById("outDate").value;
				var endDate = document.getElementById("endDate").value;
				var amount = Number(document.getElementById("amount").value);
				var rtacname = document.getElementById("rtacname").value;
				var rtaccount = document.getElementById("rtaccount").value;
				var rtbank = document.getElementById("rtbank").value;
				var recetDate = document.getElementById("recetDate").value;
				
				var param = {id : id,reman : reman ,name : name,rcount : rcount,
					rnum : rnum ,outMan : outMan,omaccount : omaccount,pbank  : pbank,
					outDate : outDate ,endDate  : endDate,amount   : amount ,
					rtacname  : rtacname ,rtaccount : rtaccount,rtbank : rtbank ,
					recetDate : recetDate};
	//			var form = iframeEle.document.forms[0];
				EventManager.get('./fuReceipt_save.action',{
						params : param,  
						sfn : function(json_data) {
							_this.appWin.close();
							if(_this.parentCfg.self.refresh)_this.parentCfg.self.refresh(json_data);
						},ffn:function(json_data){
						 	ExtUtil.alert({msg:'数据保存失败'});
						}
				})
			},
		
		globalMgr : {
			params : null,
			url : 'pages/app/funds/receiptbackpayment/BackReceiptEdit.jsp',
			id : null,
			orgtype: null
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