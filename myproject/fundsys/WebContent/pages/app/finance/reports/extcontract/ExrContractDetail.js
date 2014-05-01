/**
 * #DESCRIPTION#
 * @author smartplatform_auto
 * @date 2013-09-30 02:57:04
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		detailPnl : null,	//详情面板
		appWin : null,
		attachMentFs : null,
		sysId : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.sysId = parentCfg.sysId;
			this.optionType = parentCfg.optionType || OPTION_TYPE.DETAIL;
		},
		createAppWindow : function(){
			this.attachMentFs = this.createAttachMentFs();
			this.detailPnl = this.createDetailPnl();
			this.appWin = new Ext.ux.window.AbsDetailWindow({width:780,getParams:this.getParams,appDetailPanel : this.detailPnl,
			optionType : this.optionType, refresh : this.refresh
			});
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
			this.appWin.optionType = this.optionType;
			this.appWin.show();
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
			var parent = exports.WinEdit.parent;
			var selId = parent.getSelId();
			var selId = null;
			if(parent){
				selId = parent.getSelId();
			}else{
				ExtUtil.alert({msg:'请传入 parent 对象！'});
			}
			var params = {id:selId};
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
		 * 获取附件参数
		 * @param formId 业务单ID
		 */
		getAttachParams : function(formId){
			var sysId = this.sysId;
			return {sysId:sysId,formType:ATTACHMENT_FORMTYPE.FType_34,formId:formId,DisenbaledBtn:true};
		},
		/**
		 * 创建附件FieldSet 对象
		 * @return {}
		 */
		createAttachMentFs : function(){
			/* ----- 参数说明： isLoad 是否实例化后马上加载数据 
			 * dir : 附件上传后存放的目录， mortgage_path 定义在 resource.properties 资源文件中
			 * isSave : 附件上传后，是否保存到 ts_attachment 表中。 true : 保存
			 * params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔}
			 *    当 isLoad = false 时， params 可在 reload 方法中提供
			 *  ---- */
			return  new  Ext.ux.AppAttachmentFs({title:'展期相关附件上传',isLoad:false,DisenbaledBtn:true,params:{dir : 'extension_path',isSave:true}});
		},
		/**
		 * 创建详情面板
		 */
		createDetailPnl : function(){
			var _this = this;
			var parent = exports.WinEdit.parent;
			var selId = null;
			if(parent){
				selId = parent.getSelId();
			}else{
				ExtUtil.alert({msg:'请传入 parent 对象！'});
			}
			var htmlArrs = null;
			
			
			var htmlArrs_1 = [
					'<tr><th col="code">协议书编号</th> <td col="code" >&nbsp;</td><th col="custName">客户名称</th> <td col="custName">&nbsp;</td><th col="applyMan">借款人</th> <td col="applyMan" >&nbsp;</td></tr>',
					'<tr><th col="contractCode">借款合同号</th> <td col="contractCode" >&nbsp;</td><th col="appAmount">贷款金额</th> <td col="appAmount" >&nbsp;</td><th col="zprincipals">贷款余额</th> <td col="zprincipals" >&nbsp;</td></tr>',
					'<tr><th col="rate">贷款利率</th> <td col="rate" >&nbsp;</td><th col="mrate">管理费率</th> <td col="mrate" >&nbsp;</td><th col="payDate">贷款期限</th> <td col="payDate" >&nbsp;</td></tr></tr>',
					'<tr><th col="guarantor" >担保人</th> <td col="guarantor">&nbsp;</td><th col="extAmount">申请展期金额</th> <td col="extAmount" >&nbsp;</td><th col="limitLoan">展期期限</th> <td col="limitLoan" >&nbsp;</td></tr>',
					'<tr><th col="rate">展期贷款利率</th> <td col="rate" >&nbsp;</td><th col="extmrate">展期管理费率</th> <td col="extmrate" >&nbsp;</td><th col="manager">签字负责人</th> <td col="manager"  colspan=3>&nbsp;</td></tr>',
					'<tr height=50><th col="otherRemark" >其他事项</th> <td col="otherRemark" colspan=5>&nbsp;</td></tr>'
					];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 90,
			    /* title : '#TITLE#', */
			    //详情面板标题 
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './fcExtContract_getDetail.action',
			    prevUrl: './fcExtContract_prev.action',
			    nextUrl: './fcExtContract_next.action',
			    params: {
			        id: selId
			    },
			    callback: {
			        sfn: function(jsonData) {
			        if(jsonData==-1) {
			        	_this.attachMentFs.hide();
			        	return;
			        }
			        var params = _this.getAttachParams(jsonData.id);
		        	_this.attachMentFs.reload(params);
			        var contractJson = jsonData.contract.custName;
			        var custName = jsonData.contract.custName;
			        var cardType = jsonData.contract.cardType;
			        var cardNum = jsonData.contract.cardNum;
			        if(custName){
			        	jsonData["custName"] = custName ? custName+"(" +cardType+":"+cardNum+")" : "" ;
			        } 
			        jsonData["contractCode"] = jsonData.contract.contractCode ? jsonData.contract.contractCode : "";
			        if(jsonData.contract.appAmount){
			        	var appAmount = jsonData.contract.appAmount;
			        	 jsonData["appAmount"] = Cmw.getThousandths(appAmount)+"元&nbsp;&nbsp;<br/><span style='color:red;font-weight:bold;'>("+Cmw.cmycurd(appAmount)+")</span>" 
			        }else{
			        	 jsonData["appAmount"] = ""; 
			        }
			        jsonData["zprincipals"] = jsonData.contract.zprincipals ? Cmw.getThousandths(jsonData.contract.zprincipals)+"元&nbsp;&nbsp;<br/><span style='color:red;font-weight:bold;'>("+Cmw.cmycurd(jsonData.contract.zprincipals)+")</span>" : "";
			        jsonData["payDate"] =  jsonData.contract.payDate ? jsonData.contract.payDate  +"至"+ jsonData.contract.endDate : "";
			        jsonData["limitLoan"] =  jsonData.estartDate ?  jsonData.estartDate+"至" + jsonData.eendDate : "";
			        	 var extAmount = jsonData["extAmount"];
			            jsonData["extAmount"] =  Cmw.getThousandths(extAmount)+"元&nbsp;&nbsp;(展期还款方式:"+jsonData["payType"]+")<br/><span style='color:red;font-weight:bold;'>("+Cmw.cmycurd(extAmount)+")</span>";
			            var rateType = jsonData["rateType"];
			            var rateTypeName = Render_dataSource.rateTypeRender(rateType);
			            jsonData["rate"] = jsonData["rate"]+"%&nbsp;&nbsp;<span style='color:red;font-weight:bold;'>("+rateTypeName+")</span>";
			            jsonData["payType"] = Render_dataSource.payTypeRender(jsonData["payType"]);
			             jsonData["isadvance"] = Render_dataSource.isadvanceRender(jsonData["isadvance"]);
			            var extmgrtypeName = Render_dataSource.mgrtypeRender(jsonData["mgrtype"]);
			            jsonData["extmrate"] = jsonData["mrate"]+"%&nbsp;&nbsp;<span style='color:red;font-weight:bold;'>("+extmgrtypeName+")</span>";
		              	var mgrtypeName = Render_dataSource.mgrtypeRender(jsonData.contract.mgrtype);
			            jsonData["mrate"] = jsonData.contract.mrate+"%&nbsp;&nbsp;<span style='color:red;font-weight:bold;'>("+mgrtypeName+")</span>";
			        }
			    }
			}];
			var extContDetailPanel = new Ext.ux.panel.DetailPanel({
		    	title : '展期协议书详情',
				border : false,
			    isLoad : false,
			    detailCfgs: detailCfgs_1
			});
			extContDetailPanel.add(this.attachMentFs);
				return extContDetailPanel;
			}
		};
});
