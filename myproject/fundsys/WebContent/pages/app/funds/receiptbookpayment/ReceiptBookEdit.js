/**
 * 汇票承诺书
 * @author 郑符明
 */
define(function(require, exports) {
	exports.viewUI = {
		appMainPanel : null,
		tab : null,
		params : null,
		codeObj : null,//接受父页面传过来的id、票号、金额、付款行、出票日期、汇票到期日、汇票数量
		iframeId : null,
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
			this.iframeId = Ext.id(null,"receiptBookEditId");
			var htmlArr = [
				'<iframe id="'+this.iframeId+'" name="'+this.iframeId+'"  style="width:100%; height:100%;overflow:auto;" frameborder="0" ></iframe>'//,
			];
			var tbar = this.getToolBar();
			this.appMainPanel = new Ext.Panel({title:'收条承诺书',xtype : 'fit',height : 630,tbar:tbar,html : htmlArr.join(" ")});
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
					toolBar.enableButtons(Btn_Cfgs.MODIFY_BTN_TXT+","+Btn_Cfgs.PRINT_BTN_TXT);
					toolBar.disableButtons(Btn_Cfgs.SAVE_BTN_TXT);
					_this.makeDatas(2);
				}
			},{
				text : Btn_Cfgs.PRINT_BTN_TXT,  /*-- 打印--*/
				iconCls:Btn_Cfgs.PRINT_CLS,
				tooltip:Btn_Cfgs.PRINT_TIP_BTN_TXT,
				handler : function(){
					_this.print();
				}
			}];
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
			return toolBar;
		},
		/**
		 * 打印
		 */
		print : function(){
			var iframeEle = window.parent.frames[this.iframeId];
			if(!iframeEle) return;
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
					break;
				}case 2 :{/*保存*/
					var document = iframeEle.document;
					var beforeData = _this.dataBeforeSave(document);
					if(!beforeData)return;
					var params  = {};
					params.receiptBook =Ext.encode( beforeData[0]);
					params.attachmentList = Ext.encode(beforeData[1]);
					params.settlement =Ext.encode(beforeData[2]);
					var id = document.getElementById("id");
					if(!id)return ;
					params.id = id.value;
					params.receiptId = document.getElementById("receiptId").value;
					EventManager.get("./fuReceiptBook_save.action",{params:params,
						sfn : function(json_data) {
							 ExtUtil.alert({msg:'数据保存成功'});
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
			var module = "ReceiptBookEdit.jsp";
			var url = "./pages/app/funds/receiptbookpayment/"+module;
			var parentContainerId = this.appMainPanel.getId();
			var pars = "?receiptId="+id + "&rnum="+rnum + "&amount="+amount + "&pbank="
				+pbank +"&outDate="+outDate + "&endDate="+endDate + "&rcount="+rcount+"&parentContainerId="+parentContainerId;
			var iframeEle = Ext.get(this.iframeId);
			if(!iframeEle) return;
			Cmw.mask(this.appMainPanel,Msg_SysTip.msg_loadingUI);
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
					 * 承诺书的数据
					 */
					//var id = ment.getElementById("receiptBookEditId").value;//
					var tamount = ment.getElementById("amountVal").value;//总金额
					var tcount  = ment.getElementById("tcount").value;//汇票总张数
					var name = ment.getElementById("name").value;//收款人姓名
					var cardNum = ment.getElementById("cardNum").value;//身份证号
					var tel = ment.getElementById("tel").value;//手机号码
					var rtacname = ment.getElementById("rtacname").value;//银行全称
					var rtaccount = ment.getElementById("rtaccount").value;//银行账号
					var rtbank = ment.getElementById("rtbank").value;//开户银行
					var tdate = ment.getElementById("time").value;//转让时间
					//承诺书 的json数据
					var receiptBook = {tcount : tcount,tamount : tamount,name : name,cardNum : cardNum, tel : tel,
					rtacname : rtacname,rtaccount : rtaccount,rtbank : rtbank,tdate : tdate}
					/**
					 * 以下是附件表的数据
					 */
					var attachment = ment.getElementById("attachment");
					var data = [];
					var rl= attachment.rows;  
					for (i=1;i<rl.length;i++) {  
						var name=rl[i].cells[0].children[0].value;  //材料名称
						var status=rl[i].cells[1].children[0].children[0].value;  //是否提供 这里多了一个children[0] ： 原因：我们要取的是option的值，select的子节点才是option，select是第一个children
						var remark=rl[i].cells[2].children[0].value;  //备注
						var params = {name : name ,status : status ,remark :remark};
						//附件表的json数组
						data[i]=params;
						}  
					/**
					 * 以下是汇票结算单表的数据
					 */
					var sdate = ment.getElementById("sdate").value;//日期时间
					var qamonut = ment.getElementById("qamonut").value;//查询费
					var auditMan = ment.getElementById("auditMan").value;//审核签字
					var name = ment.getElementById("name").value;//客户姓名
					var tiamount = ment.getElementById("tiamount").value;//贴息
					var leaderMan = ment.getElementById("leaderMan").value;//领导签字
					var amount = ment.getElementById("amount").value;//金额
					var ramount = ment.getElementById("ramount").value;//实际付款金额
					var finMan = ment.getElementById("finMan").value;//财务签字
					var rate = ment.getElementById("rate").value;//贴息利率
					/**
					 * 汇票结算单表的json数据
					 */
					var settlement = {sdate : sdate,qamonut : qamonut,auditMan : auditMan,name:name,
						tiamount : tiamount,leaderMan : leaderMan,amount : amount,ramount : ramount,finMan : finMan,rate : rate}
					
					var validation = ment.getElementById("validation");
					for (var back in settlement){
						if(!settlement[back]){
							validation.innerHTML="提示：信息填写不完整！";
							ment.getElementById(back).focus()
							return false;
						}
					}
				datas[0] = [receiptBook];
				datas[1] = [data];
				datas[2] = [settlement];
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