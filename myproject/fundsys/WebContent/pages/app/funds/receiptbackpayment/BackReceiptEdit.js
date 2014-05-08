/**
 * 回款收条
 * @author 郑符明
 */
define(function(require, exports) {
	exports.viewUI = {
		appMainPanel : null,
		tab : null,
		params : null,
		codeObj : null,//接受父页面传过来的id、票号、金额、付款行、出票日期、汇票到期日、汇票数量
		iframeId :　null,
		toolBar:null,
		finishBussCallback : null,
		/**
		 * 获取主面板
		 * @param tab Tab 面板对象
		 * @param params 由菜单点击所传过来的参数
		 * 	{tabId : tab 对象ID,apptabtreewinId : 系统程序窗口ID,sysid : 系统ID} 
		 */
		getMainUI : function(tab,params){
			/*由必做或选做业务菜单传入的回調函数，主要功能：

			 * finishBussCallback : 当业务表单保存后，更新必做或选做事项为已做,
			 * unFinishBussCallback : 当删除业务表单后，取消已做标识
			 * */
			this.finishBussCallback = tab.finishBussCallback;
			this.setParams(tab, params);
			if(!this.appMainPanel){
				this.createCmpts();
				this.refresh();
			}
			var _this = this;
			tab.notify = function(_tab) {
				_this.setParams(_tab, _tab.params);
				_this.refresh();
			};
			return this.appMainPanel;
		},
		/**
		 * 创建组件
		 */
		createCmpts : function(){
			this.iframeId = Ext.id(null,"rqueryApplyId");
			var htmlArr = [
				'<iframe id="'+this.iframeId+'" name="'+this.iframeId+'"  style="width:100%; height:100%;overflow:auto;" frameborder="0" ></iframe>'//,
			];
			var tbar = this.getToolBar();
			this.toolBar=tbar;
			this.appMainPanel = new Ext.Panel({title:'收条承诺书',xtype : 'fit',height : 630,tbar:tbar,html : htmlArr.join(" ")});
//			this.appWin = new Ext.ux.window.MyWindow({title : "添加收条",width:1000,height:1700,autoScroll:true,draggable:true,maximizable:true,minimizable:true,tbar:this.tbar,html : htmlArr.join(" ")});
//			this.appWin.show();
		},
		getToolBar : function(){
			var _this = this;
			var toolBar = null;
			var barItems = [{
				token:"编辑",
				text:Btn_Cfgs.MODIFY_BTN_TXT,
				iconCls:Btn_Cfgs.MODIFY_CLS,
				tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
				handler : function(){
					toolBar.disableButtons(Btn_Cfgs.MODIFY_BTN_TXT+","+Btn_Cfgs.PRINT_BTN_TXT);
					toolBar.enableButtons(Btn_Cfgs.SAVE_BTN_TXT);
					_this.makeDatas(1);
				}
			},{
				token:"保存",
				text:Btn_Cfgs.SAVE_BTN_TXT,
				iconCls:Btn_Cfgs.SAVE_CLS,
				tooltip:Btn_Cfgs.SAVE_TIP_BTN_TXT,
				handler : function(){
					_this.makeDatas(2);
				}
			},{
				token:"打印",
				text:Btn_Cfgs.PRINT_BTN_TXT,
				iconCls:Btn_Cfgs.PRINT_CLS,
				tooltip:Btn_Cfgs.PRINT_TIP_BTN_TXT,
				handler : function(){
					_this.print();
				}
			}];
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
			return toolBar;
		},
		print : function(){
			var iframeEle = window.parent.frames[this.iframeId];
			if(!iframeEle) return;
			//PrintManager.fillDatas();
			PrintManager.preview(iframeEle.document.body.innerHTML);
		},
		setParams : function(tab, params) {
			this.tab = tab;
			this.params = params;
		},
		refresh : function() {
			if(!this.appMainPanel){
				return null;
			}
			if (!this.appMainPanel.rendered) {
				var _this = this;
				this.appMainPanel.addListener('render', function(cmpt) {
						_this.loadDatas();
				});
			} else {
				this.loadDatas();
			}
		},
		/**
		 * 处理数据
		 * @param actionType 动作类型 [1:编辑,2:保存,3:打印]
		 */
		makeDatas : function(actionType){
			var _this = this;
			var tab = this.tab;
			var iframeEle = window.parent.frames[this.iframeId];
			if(!iframeEle) return;
			switch(actionType){
				case 1 :{/*编辑*/
					var document = iframeEle.document.getElementsByTagName("input");
					document.enable();
					break;
				}case 2 :{/*保存*/
					var document = iframeEle.document;
					var beforeData = _this.dataBeforeSave(document);
					if(!beforeData)return;
					var params  = {};
					params.backReceipt =Ext.encode(beforeData[0]);
					params.backInvoce =Ext.encode(beforeData[1]);
//					params.id = document.getElementById("receiptBookId").value;
					EventManager.get("./fuBackReceipt_save.action",{params:params,
						sfn : function(json_data) {
							_this.toolBar.enableButtons(Btn_Cfgs.MODIFY_BTN_TXT+","+Btn_Cfgs.PRINT_BTN_TXT);
							_this.toolBar.disableButtons(Btn_Cfgs.SAVE_BTN_TXT);
							 ExtUtil.alert({msg:'数据保存成功'});
							 _this.refresh();
							 if(_this.finishBussCallback) _this.finishBussCallback(json_data);
						},ffn:function(json_data){
						 	ExtUtil.alert({msg:'数据保存失败'});
						}})
					break;
				}case 3 :{/*打印*/
					iframeEle.AuditProjectMgr.doPrint();
					break;
				}
			}
		},
		/**
		 * 在这里加载数据
		 */
		loadDatas : function(printaction){
			this.appMainPanel.doLayout();
			var id = this.params.applyId;
			this.codeObj = this.params.codeObj;
			var rnum = this.codeObj.rnum;//票号
			var amount = this.codeObj.amount;//金额
//			amount = Cmw.cmycurd(amount);//转换为大写
			var pbank =this.codeObj.pbank;//付款行
			var outDate = this.codeObj.outDate;//出票日期
			var endDate = this.codeObj.endDate;//汇票到期日
			var rcount = this.codeObj.rcount;//汇票数量
//			rcount = Cmw.cmycurd(rcount);
			var module = "BackReceiptEdit.jsp";
			var url = "./pages/app/funds/receiptbackpayment/"+module;
			var parentContainerId = this.appMainPanel.getId();
			var pars = "?receiptId="+id + "&rnum="+rnum + "&amount="+amount + "&pbank="
				+pbank +"&outDate="+outDate + "&endDate="+endDate + "&rcount="+rcount+"&parentContainerId="+parentContainerId;			var iframeEle = Ext.get(this.iframeId);
			if(!iframeEle) return;
			//Cmw.mask(this.appMainPanel,Msg_SysTip.msg_loadingUI);
			window.parent.frames[this.iframeId].location= url + pars;
		},
		resize : function(adjWidth,adjHeight){
			this.appMainPanel.setWidth(adjWidth);
			this.appMainPanel.setHeight(adjHeight);
		},
		/**
		 * 保存之前进行数据处理
		 */
		dataBeforeSave : function(ment){
					var datas = [];//存放所有的数据
					/**
					 * 回款收条
					 */
					var validation = ment.getElementById("validation");
					var id = ment.getElementById("id").value;
					var receiptId = this.params.applyId;//汇票收条ID
					var name = ment.getElementById("name").value; // 客户姓名
					var reman = ment.getElementById("reman").value;//收条发送人
					var rcount = ment.getElementById("rcount").value;// 汇票数量
					var rnum = ment.getElementById("rnum").value;// 票号
					var outMan = ment.getElementById("outMan").value;//出票人
					var omaccount = ment.getElementById("omaccount").value;//出票人账号
					var pbank = ment.getElementById("pbank").value;//付款行
					var outDate = ment.getElementById("outDate").value;//出票日期
					var endDate = ment.getElementById("endDate").value;//到票日期
					var amount = ment.getElementById("amount").value;//金额
					var rtacname = ment.getElementById("rtacname").value;//收款人账户名
					var rtaccount = ment.getElementById("rtaccount").value;//收款人账号
					var rtbank = ment.getElementById("rtbank").value;//收款人开户行
					var recetDate = ment.getElementById("time").value;//回款收条签收日期
					if(!Number(id))id=null;
					var backReceipt = {id:id,receiptId : receiptId,reman : reman,name : name, rcount : rcount,
					rnum : rnum,outMan : outMan,omaccount : omaccount,pbank : pbank,outDate : outDate,
					endDate : endDate,amount : amount, rtacname : rtacname,rtaccount : rtaccount,rtbank : rtbank,recetDate : recetDate}
					
					for (var back in backReceipt){
						if(back!="id" && back!="receiptId"){
							if(!backReceipt[back]){
//								alert('信息填写不完整！');
								validation.innerHTML="提示：信息填写不完整！";
								ment.getElementById(back).focus()
								return false;
							}
						}
					}
					/**
					 * 汇票回款单表
					 */
					var sdate = ment.getElementById("sdate").value; // 客户姓名
					var sname = ment.getElementById("sname").value;//收条发送人
					var samount = ment.getElementById("samount").value;// 汇票数量
					var rate = ment.getElementById("rate").value;// 票号
					var tiamount = ment.getElementById("tiamount").value;//出票人
					var bamount = ment.getElementById("bamount").value;//出票人账号
					var pamount = ment.getElementById("pamount").value;//出票人账号
					
					var backInvoce = {sdate : sdate,name : sname,amount : samount, rate : rate,
					tiamount : tiamount,bamount : bamount,pamount : pamount}

					for (var back in backInvoce){
						if(!backInvoce[back]){
							if(back=="name" || back=="amount"){
								back="s"+back;	
							}
							validation.innerHTML="提示：信息填写不完整！";
							ment.getElementById(back).focus()
							return false;
						}
					}
					
					datas[0] = [backReceipt];
					datas[1] = [backInvoce];
					return datas;
		},
		
		
		/**
		 * 组件销毁方法
		 */
		destroy : function(){
			if(null != this.appMainPanel){
				 this.appMainPanel.destroy();
				 this.appMainPanel = null;
			}
			if(this.iframeId){
				var iframeEle = Ext.get(this.iframeId);
				if(iframeEle) iframeEle.remove();
			}
		}
	}
});