/**
 * 提前还款详情
 * @author zsl
 * @date 2012-07-12
 */
define(function(require, exports) {
	exports.viewUI = {
		params : null,
		appDetailPnl: null,
		applyId : null,
		appMainPanel : null,
		apptabtreewinId : null,
		tab:null,
		ToolBar:null,
		/**
		 * 设置参数
		 */
		setParams : function(params){
			this.params = params;
			this.ToolBar=this.getToolBar();
		},
		/**
		 * 创建UI
		 */
		getMainUI : function(tab, params){
			this.setParams(params);		
			var _this = this;
			this.tab = tab;
			if(!this.appMainPanel){
				this.createAppMainPanel();
			}
			if(params){
				tab.notify = function(_tab){
				_this.refresh(_tab,params);
				}
			}
			this.createAttachMentFs();
			return this.appMainPanel;
		},
		
		/**
		 * 主面板
		 */
		createAppMainPanel : function(){
			this.createAppDetailPanel();
			this.appMainPanel = new Ext.Panel({
			});
			this.customerPanel = new Ext.Panel({
						collapsed : false,
						border : false
					});
			this.appMainPanel.add(this.getToolBar());
			
			this.appMainPanel.add(this.appDetailPnl);
			return this.appMainPanel; 
		},
		/**
		 * 返回
		 */
		getToolBar : function(){
			var _this = this;
			var toolBar = null;
			var barItems = [{
				text : "返回",
				iconCls:Btn_Cfgs.PREVIOUS_CLS,
				tooltip: "返回",
				handler : function(){
					_this.showBackTab();
				}
			}];
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
			toolBar.addText("<span style='color:#416AA3; ;font-weight:bold;'>提前还款详情</span>");
			var buttons = toolBar.getButtons();
			this.btnBack = buttons[0]
			return toolBar;
		},
		showBackTab : function(btnId){
			var _this = this;
			var tabId = CUSTTAB_ID.AuditPrepaymentMgrTabId;
//			var tabId = CUSTTAB_ID.AuditPrepaymentMgrTab.id;
//			var url =  CUSTTAB_ID.AuditPrepaymentMgrTab.url;
			var title =  '提前还款业务审批';
//			var  apptreeId = this.appTabId;
			var apptabtreewinId = _this.params["apptabtreewinId"];
//			this.apptabtreewinId = apptabtreewinId;
			params = {id:this.params.id};
			params["applyId"] = this.applyId;
//			this.AuditMainUI = Ext.getCmp(tabId);
			Cmw.hideTab(apptabtreewinId,this.tab);
			Cmw.activeTab(apptabtreewinId,tabId,params,url,title);
			
		},
		/**
		 * 创建详情面板
		 */
		createAppDetailPanel : function(){
			var _this = this;
			var htmlArrs_1 = [
					'<tr><th col="loanCode">借款合同</th> <td col="loanCode" >&nbsp;</td><th col="name">客户名称</th> <td col="name" >&nbsp;</td><th col="ptype">提前还款类别</th> <td col="ptype" >&nbsp;</td></tr>',
					'<tr><th col="payBank">还款银行</th> <td col="payBank" >&nbsp;</td><th col="payAccount">还款帐号</th> <td col="payAccount" >&nbsp;</td><th col="accName">还款人</th> <td col="accName" >&nbsp;</td></tr>',
					'<tr><th col="adDate">预计提前还款日</th> <td col="adDate" >&nbsp;</td><th col="predDate">实际提前还款日</th> <td col="predDate" >&nbsp;</td><th col="frate">提前还款手续费率</th> <td col="frate" >&nbsp;</td></tr>',
					'<tr><th col="isretreat">是否退息费</th> <td col="isretreat" >&nbsp;</td><th col="xphases">总期数</th> <td col="xphases" >&nbsp;</td><th col="yphases">已还期数</th> <td col="yphases" >&nbsp;</td></tr>',
					'<tr><th col="phases">当期期数</th> <td col="phases" >&nbsp;</td><th col="reprincipal">应收剩余本金</th> <td col="reprincipal" >&nbsp;</td><th col="zinterest">应收利息</th> <td col="zinterest" >&nbsp;</td></tr>',
					'<tr><th col="zmgrAmount">应收管理费</th> <td col="zmgrAmount" >&nbsp;</td><th col="zpenAmounts">应收罚息</th> <td col="zpenAmounts" >&nbsp;</td><th col="zdelAmounts">应收滞纳金</th> <td col="zdelAmounts" >&nbsp;</td></tr>',
					'<tr><th col="freeAmount">应收手续费</th> <td  col="freeAmount">&nbsp;</td><th col="imamount">应退息费</th> <td  col="imamount" >&nbsp;</td><th col="totalAmount">应收合计</th> <td col="totalAmount" >&nbsp;</td></tr>'
						];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 115,
			    /* title : '#TITLE#', */
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './fcPrepayment_getfinancePreMent.action',
			    params: {
			        id:_this.applyId
			    },
			    callback: {
			        sfn: function(jsonData) {
			        	_this.calculateFree(_this,jsonData);
			        	 _this.fmtJsonData(jsonData);
			        	 var sysId = _this.params.sysid;
				        	var formId = jsonData.id;
				        	if(!formId){
				        		formId = -1;
				        	}
			        	 var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_32,sysId:sysId};
			        	 attachMentFs.reload(params);
			        },
			       ffn : function(json_data) {
			       		ExtUtil.alert({msg:"数据加载失败！"});
			       		return;
			       }
			    }
			    }];
			    var pcDetailPanel = new Ext.ux.panel.DetailPanel({
				    detailCfgs: detailCfgs_1,
				    border : false,
			     	isLoad : false
				});
				var dir='prepayment_path';
				var attachMentFs = this.createAttachMentFs(this,dir);
				pcDetailPanel.add(attachMentFs);
				this.appDetailPnl = pcDetailPanel;
		},
		/**
		 * 设置值
		 */
		calculateFree: function(_this,jsonData){
			var interes = jsonData["interest"] ||  0.00;
			var zinterest = jsonData["zinterests"] || 0.00;
			 var interest = parseFloat(interes)+parseFloat(zinterest);//(应收利息=当期应收利息+逾期应收利息)
			 jsonData["interest"] = Cmw.getThousandths(interest)+"元";
			 var mgrAmount = jsonData["mgrAmount"] || 0.00;
			 var zmgrAmounts = jsonData["zmgrAmounts"] || 0.00;
			 var mgrAmount = parseFloat(mgrAmount)+parseFloat(zmgrAmounts);//(应收管理费=当期应收管理费+逾期应收管理费)
			  jsonData["mgrAmount"] = Cmw.getThousandths( mgrAmount)+"元";
			  var reprincipal = jsonData["reprincipal"] || 0.00;
			  var zpenAmounts = jsonData["zpenAmounts"] || 0.00;
			  var zdelAmounts = jsonData["zdelAmounts"] || 0.00;
			  var freeAmount = jsonData["freeAmount"] || 0.00;
			  var imamount = jsonData["imamount"] || 0.00;
			  var totalAmount = jsonData["totalAmount"]; 
			var xstatus = jsonData["xstatus"];
			xstatus = Render_dataSource.statusRender(xstatus+"");
			jsonData["totalAmount"] = Cmw.getThousandths(totalAmount)+"元<span style='color:red;font-weight:bold'>("+xstatus+")</span>";
		}
		,/**
		 * 格式化jsonData 数据
		 * @param {} _this 当前对象
		 * @param {} jsonData 提前还款详情面板数据
		 */
		fmtJsonData:function(jsonData){
			var arr = ["phases","xphases","yphases"];
			for(var i=0,count=arr.length; i<count; i++){
				var key = arr[i];
				var val = jsonData[key];
				if(val) jsonData[key] = val + "期";
			}
			jsonData["frate"] = (jsonData["frate"]) ? jsonData["frate"]+"%" : "";
			arr = ["yprincipals","reprincipal","principal","zinterests","zmgrAmounts","zpenAmounts","zdelAmounts","freeAmount","imamount"];
			for(var i=0,count=arr.length; i<count; i++){
				var key = arr[i];
				var val = jsonData[key] || 0.00;
				jsonData[key] = Cmw.getThousandths(val)+"元";
			}
			jsonData["ptype"]=Render_dataSource.prepayment_ptypeRender(jsonData["ptype"].toString());
			jsonData["isretreat"]=Render_dataSource.prepayment_isretreatRender(jsonData["isretreat"].toString());
	},
	/**
	 * 刷新
	 */
	refresh : function(_tab,params){
		_this=this;
		apptabtreewinId=params["apptabtreewinId"];
		if(_this.applyId!=params["applyId"]){
			if(_this.tab && _this.apptabtreewinId){
					Cmw.showTab(apptabtreewinId,_this.tab)
				}
		_this.applyId= params["applyId"];
		_this.appDetailPnl.reload({id :_this.applyId});
					
		}
	},
	createAttachMentFs : function(_this,dir){
			/* ----- 参数说明： isLoad 是否实例化后马上加载数据 
			 * dir : 附件上传后存放的目录， mortgage_path 定义在 resource.properties 资源文件中
			 * isSave : 附件上传后，是否保存到 ts_attachment 表中。 true : 保存
			 * params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔}
			 *    当 isLoad = false 时， params 可在 reload 方法中提供
			 *  ---- */
			var cfg = {title:'相关材料附件',isLoad:true,dir : dir,isSave:true,DisenbaledBtn:true};
//				var uuid = Cmw.getUuid();
//				var params = {formType:-1,formId : uuid};
//				cfg.params = params;
				var attachMentFs = new  Ext.ux.AppAttachmentFs(cfg);
				return attachMentFs;
		},
	
	/**
		 * 
		 * 销毁组件
		 */
		destroy : function(){
//			if(null != this.appMainPanel){
//				this.appMainPanel.destroy();
//				this.appMainPanel = null;
//			}
//			if(null!=this.ToolBar){
//				this.ToolBar.destroy();
//				this.ToolBar=null;
//			}
//			if(null!=this.appDetailPnl){
//				this.appDetailPnl.destroy();
//				this.appDetailPnl=null;
//			}
		}
	};
});