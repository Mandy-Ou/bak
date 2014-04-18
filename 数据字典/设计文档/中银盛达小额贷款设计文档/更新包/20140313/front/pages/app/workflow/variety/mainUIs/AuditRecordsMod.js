/**
 * 流程审批主页面 ---> 审批意见列表页面
 * @author 程明卫
 * @date 2013-01-03
 */
define(function(require, exports) {
	exports.moduleUI = {
		appPanel : null,
		params : null,
		parent : null,
		HEIGHT : 600,
		/**
		 * 获取业务模块
		 * @param params 由菜单点击所传过来的参数
		 * 	{tabId : tab 对象ID,apptabtreewinId : 系统程序窗口ID,sysid : 系统ID} 
		 */
		getModule : function(params){
			if(!this.appPanel){
				this.appPanel = this.creatAuditRecordPanel();
				this.show(params);
			}
			return this.appPanel;
		},
		/**
		 * 设置参数
		 */
		setParams : function(params){
			if(params.parent){
				this.parent = params.parent;
			}
			this.params = params;
		},
		/**
		 * 创建审核记录面板
		 */
		creatAuditRecordPanel : function(){
			var auditRecordsPanel =  new Ext.Panel({title : '审批记录',autoScroll:true,height:this.HEIGHT,border:false});
			return auditRecordsPanel;
		},
		/**
		 * 显示面板并加载数据
		 */
		show : function(params){
			this.setParams(params);
			if(this.appPanel.rendered){
				this.loadData();
			}else{
				var _this = this;
				this.appPanel.addListener("render",function(pnl){
					_this.loadData();
				});
			}
		},
		/**
		 * 加载客户信息数据
		 */
		loadData : function() {
			this.resize();
			var _this = this;
			var params = {};
			if(this.params.procId){
				params.procId = this.params.procId;
			}
			EventManager.get('./sysAuditRecords_list.action',{params:params,sfn:function(json_data){
				var html = "<div style='color:red;font-size:16px;'><center>流程尚未开始，无法查看审批记录!</center></div>";
				if(json_data.size > 0){
					var list = json_data.list;
					var htmlArr = [];
					for(var i=0,count=list.length; i<count; i++){
						var data = list[i];
						htmlArr[i] = _this.getRecordHtmlData(data);
					}
					html = htmlArr.join(" ");
				}
				_this.appPanel.update(html);
			}});
		},
		getRecordHtmlData : function(data){
			
			var nodeName = data.nodeName;//当前环节
			var result = data.result;//审批结果
			var auditAmountHtml = "";
			if(data.auditAmountData){
				auditAmountHtml = this.getAuditAmountHtml(data.auditAmountData);
			}
			var approval = data.approval || '没有给出审批意见';
			var userName = data.userName;//审批人
			var sex =  data.sex;//性别
			var auditManCls = "auditMan";
			if(sex && sex == 1){/*女同胞样式*/
				auditManCls = "auditWoman";
			}
			var deptName = data.deptName || "未知部门";	//部门
			var createTime = data.createTime;	//审批时间
			var htmlArr = ['<div class="bubbles"><span class="bot"></span> <span class="top"></span>',
				'<div style="font-weight:bold;">' +
				'<label style="color:#3255B2;">当前环节：</label>'+nodeName+' &nbsp;&nbsp;&nbsp;&nbsp;' +
				'<label style="color:#3255B2;">审批结果</label>：'+result+auditAmountHtml+' <br/><label style="color:#3255B2;">审批意见：</label></div>',
				'<div style="padding-left:20px;margin-top:4px;">'+approval+'</div>',
				'</div>',
				'<div class="'+auditManCls+'">审批人：<span style="color:red;">'+userName+'【'+deptName+'】</span></br>审批时间：<span style="color:red;">'+createTime+'</span></div></div>'
				];
			var html = htmlArr.join(" ");
			return html;
		},
		/**
		 * 获取审批金额建议信息
		 */
		getAuditAmountHtml : function(auditAmountData){
			var auditAmountHtmlArr = [];
			var appAmount = auditAmountData.appAmount;
			if(appAmount && appAmount>0){
				appAmount = Cmw.getThousandths(appAmount)+'元&nbsp;&nbsp;<span style="color:red;">(大写：'+Cmw.cmycurd(appAmount)+')</span>';
				auditAmountHtmlArr[auditAmountHtmlArr.length] = '贷款金额:'+appAmount;
			}
			var loanLimit = this.getLoanLimit(auditAmountData);
			if(loanLimit){
				auditAmountHtmlArr[auditAmountHtmlArr.length] = '期限:'+loanLimit;
			}
			var rate = auditAmountData.rate;
			if(rate && rate > 0){
				var rateType =  Render_dataSource.rateTypeRender(auditAmountData["rateType"]);
				rate += "% ("+rateType+")";
				auditAmountHtmlArr[auditAmountHtmlArr.length] = '利率:'+rate;
			}
			var auditAmountHtml = "";
			if(auditAmountHtmlArr && auditAmountHtmlArr.length > 0){
				 auditAmountHtml = '【<label style="color:red;">' + auditAmountHtmlArr.join("&nbsp;&nbsp;")+"</label>】";
			}
			return auditAmountHtml;
		},
		/**
		 * 获取贷款期限
		 */
		getLoanLimit : function(jsonData){
			
		 	var yearLoan = jsonData["yearLoan"];
		 	var monthLoan = jsonData["monthLoan"];
		 	var dayLoan = jsonData["dayLoan"];
		 	var arr = [];
		 	if(yearLoan && yearLoan>0){
		 		arr[arr.length] = yearLoan+'年';
		 	}
		 	if(monthLoan && monthLoan>0){
		 		arr[arr.length] = monthLoan+'个月';
		 	}
		 	if(dayLoan && dayLoan>0){
		 		arr[arr.length] = dayLoan+'天';
		 	}
	       return (arr.length > 0) ? arr.join("") : "";
		},
		resize : function(adjWidth,adjHeight){
			if(!adjHeight){
				var el = this.parent.el;
				if(el){
					adjHeight = el.getComputedHeight();
					if(adjHeight < this.HEIGHT) adjHeight = this.HEIGHT;
				}else{
					adjHeight = this.HEIGHT;
				}
			
			}
			this.appPanel.setWidth(adjWidth);
			this.appPanel.setHeight(adjHeight);
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function(){
			if(null != this.appPanel){
				 this.appPanel.destroy();
				 this.appPanel = null;
			}
		}
	};
});