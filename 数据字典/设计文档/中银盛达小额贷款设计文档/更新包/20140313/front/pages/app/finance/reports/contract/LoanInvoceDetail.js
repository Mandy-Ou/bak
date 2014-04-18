/**
 * 还款方式公式详情
 * @author smartplatform_auto
 * @date 2013-01-23 07:21:09
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		detailPnl : null,	//详情面板
		appWin : null,
		WIDTH : 800,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.DETAIL;
		},
		createAppWindow : function(){
			this.detailPnl = this.createDetailPnl();
			this.appWin = new Ext.ux.window.AbsDetailWindow({width:this.WIDTH,getParams:this.getParams,appDetailPanel : this.detailPnl,
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
		 * 创建详情面板
		 */
		createDetailPnl : function(){
			var parent = exports.WinEdit.parent;
			var selId = null;
			if(parent){
				selId = parent.getSelId();
			}else{
				ExtUtil.alert({msg:'请传入 parent 对象！'});
			}
			var htmlArrs = null;
			var _this = this;
			var htmlArrs_1 = [
						'<tr>' +
							'<th col="account">收款帐号</th> <td col="account" >&nbsp;</td>' +
							'<th col="code">放款单编号</th> <td col="code" >&nbsp;</td>' +
							'<th col="loancode">借款合同号</th> <td col="loancode" >&nbsp;</td>' +
							
						'</tr>', 
						'<tr>' +
							'<th col="auditState">审批状态</th> <td col="auditState" >&nbsp;</td>' +
							'<th col="state">放款状态</th> <td col="state">&nbsp;</td>' +
							'<th col="payName">收款人名称</th> <td col="payName" >&nbsp;</td>' +
						'</tr>', 
						'<tr>' +
							'<th col="regBank">开户行</th> <td col="regBank" >&nbsp;</td>' +
							'<th col="cashier">出纳人员</th> <td col="cashier" >&nbsp;</td>' +
							'<th col="realDate">实际放款日期</th> <td col="realDate" >&nbsp;</td>' +
						'</tr>',
						'<tr>' +
							'<th col="prate">放款手续费率</th> <td col="prate">&nbsp;</td>' +
							'<th col="unAmount">未放款余额</th> <td col="unAmount"  colspan="3">&nbsp;</td>' +
						'</tr>',
						'<tr>' +
							'<th col="payAmount">放款金额</th> <td col="payAmount"  colspan="5">&nbsp;</td>' +
						'</tr>',
						'<tr height="50">' +
							'<th col="remark">备注</th> <td col="remark"  colspan="5">&nbsp;</td>' +
						'</tr>'
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
			    url: './fcLoanInvoce_get.action',
				 prevUrl : './fcLoanInvoce_prev.action',
				 nextUrl : './fcLoanInvoce_next.action',
			    params: {
			        id: -1
			    },
			    callback: {
			        sfn: function(jsonData) {
			            /* 可在对页面进行赋值前，对数据进行转换处理 to do .. */
			        	jsonData["prate"]  = jsonData["prate"] ? jsonData["prate"] +'%' : '';
			        	switch(jsonData["auditState"]){
			        		case -1:
					            jsonData["auditState"] = '驳回';
					            break;
					        case 0:
					            jsonData["auditState"] = '待提交';
					            break;
					        case 1:
					            jsonData["auditState"] = '待审批';
					            break;
					        case 2:
					            jsonData["auditState"] = '<span style="color:green;font-weight:bold;">审批通过</span>';
					            break;
			        	}
			        	switch(jsonData["state"]){
					        case 0:
					            jsonData["state"] = '待放款';
					            break;
					        case 1:
					            jsonData["state"] = '<span style="color:green;font-weight:bold;">已放款</span>';
					            break;
			        	}
			        	var payAmount =jsonData["payAmount"];
			        	if(payAmount!=null){
			        		jsonData["payAmount"] =  Cmw.getThousandths(payAmount)+'元&nbsp;&nbsp;<span style="color:red;font-weight:bold;">(大写：'+Cmw.cmycurd(payAmount)+')</span>';
			        	}
			        	var unAmount =jsonData["unAmount"];
			        	if(unAmount!=null){
			        		jsonData["unAmount"] =  Cmw.getThousandths(unAmount)+'元&nbsp;&nbsp;<span style="color:red;font-weight:bold;">(大写：'+Cmw.cmycurd(unAmount)+')</span>';
			        	}
			        	jsonData["realDate"]=(jsonData["realDate"]=='')? '没有通过审批' : jsonData["realDate"] ;
			        	var cashier =jsonData["cashier"];
			        	 var cas = [];
			        	 cas = cashier.split("##");
			        	 jsonData["cashier"] ='<span style="color:blue;font-weight:bold;">'+cas[1]+'</span>';
			        }
			    }
			}];
			var attachLoad = function(params, cmd) {
			}
			var detailPanel_1 = new Ext.ux.panel.DetailPanel({
				title :'放款单详情',
			    autoWidth:  _this.WIDTH,
			    detailCfgs: detailCfgs_1,
			    attachLoad: function(params, cmd) {
			    	
			    }
			});
			
			return detailPanel_1;
		}
	};
});
