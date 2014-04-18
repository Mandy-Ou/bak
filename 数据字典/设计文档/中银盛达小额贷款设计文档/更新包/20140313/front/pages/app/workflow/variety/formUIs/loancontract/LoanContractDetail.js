/**
 * 借款合同详情
 * @author 彭登浩
 * @date 2012-07-12
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		detailPnl : null,	//详情面板
		appWin : null,
		attachMentFs : null,
		formId : null,
		sysid : null,
		idMgr : {
			flexDatasTrId : Ext.id(null,'flexDatasTrId'),
			flexDatasTdId : Ext.id(null,'flexDatasTdId')
		},
		setParentCfg:function(parentCfg){
			if(parentCfg.formId){
				this.formId = parentCfg.formId;
				this.sysid = parentCfg.sysid;
			}else{
				this.parent = parentCfg.parent;
			}
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
//			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.attachMentFs = this.createAttachMentFs();
			this.detailPnl = this.createDetailPnl();
			this.appWin = new Ext.ux.window.AbsDetailWindow({width:1000,getParams:this.getParams,appDetailPanel : this.detailPnl,hiddenBtn: true,
				refresh : this.refresh
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
			if(_parentCfg.ele){
				this.appWin.show(_parentCfg.ele.frame("C3DAF9", 1, { duration: 1 }));
			}else{
				this.appWin.show();
			}
			
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
		 * 创建附件FieldSet 对象
		 * @return {}
		 */
		createAttachMentFs : function(_this){
			/* ----- 参数说明： isLoad 是否实例化后马上加载数据 
			 * dir : 附件上传后存放的目录， mortgage_path 定义在 resource.properties 资源文件中
			 * isSave : 附件上传后，是否保存到 ts_attachment 表中。 true : 保存
			 * params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔}
			 *    当 isLoad = false 时， params 可在 reload 方法中提供
			 *  ---- */
			var attachMentFs = new  Ext.ux.AppAttachmentFs({title:'相关材料附件',isLoad:false,dir : 'mort_path',isSave:true,isNotDisenbaled:true});
			return attachMentFs;
		},
		/**
		 * 创建详情面板
		 */
		createDetailPnl : function(){
			var _this = exports.WinEdit;
			var parent = exports.WinEdit.parent;
			var htmlArrs_1 = [
				'<tr>' +
								'<th col="code">合同编号</th> <td col="code" >&nbsp;</td>' +
								'<th col="borBank">借款人银行</th> <td col="borBank" >&nbsp;</td>' +
								'<th col="borAccount">借款人帐号</th> <td col="borAccount" >&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								'<th col="payBank">还款银行</th> <td col="payBank" >&nbsp;</td>' +
								'<th col="payAccount">还款帐号</th> <td col="payAccount" >&nbsp;</td>' +
								'<th col="accName">帐户户名</th> <td col="accName" >&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								'<th col="doDate">合同签订日期</th> <td col="doDate" >&nbsp;</td>' +
								'<th col="loanLimit">贷款期限(年)</th> <td col="loanLimit" >&nbsp;</td>' +
								'<th col="payDate">合约放款日期</th> <td col="payDate" >&nbsp;</td>' +
							'<tr>'+ 
								'<th col="payDay">每期还款日</th> <td col="payDay" >&nbsp;</td>' +
								'<th col="payType">还款方式</th> <td col="payType" >&nbsp;</td>' +
								'<th col="endDate">贷款截止日期</th> <td col="endDate" >&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								'<th col="rateType">利率类型</th> <td col="rateType" >&nbsp;</td>' +
								'<th col="rate">贷款利率</th> <td col="rate" >&nbsp;</td>' +
								'<th col="urate">罚息利率</th> <td col="urate" >&nbsp;</td>' +
								//'<th col="isadvance">是否预收息</th> <td col="isadvance" >&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								'<th col="mgrtype">管理费收取方式</th> <td col="mgrtype" >&nbsp;</td>' +
								'<th col="mrate">滞纳金利率</th> <td col="frate" >&nbsp;</td>' +
								'<th col="prate">放款手续费率</th> <td col="prate" >&nbsp;</td>' +
							'</tr>', 
							'<tr>' +
								'<th col="arate">提前还款费率</th> <td col="arate" >&nbsp;</td>' +
								'<th col="appAmount">贷款金额</th> <td col="appAmount" colspan=3>&nbsp;</td>' +
							'</tr>', 
							'<tr id="'+_this.idMgr.flexDatasTrId+'" style="display:none;">' +
								'<th col="flexDatas">灵活还本信息</th> <td id="'+_this.idMgr.flexDatasTdId+'" col="flexDatas" colspan=5>&nbsp;</td>' +
							'</tr>', 
							'<tr height = "80">' +
								'<th col="clause">合同中未涉及条款</th> <td col="clause"  colspan=5 >&nbsp;</td>' +
							'</tr>'
				];
			
			var  detailCfgs = [{
				  cmns: 'THREE',
		    /* ONE , TWO , THREE */
		    model: 'single',
		    labelWidth: 110,
		    title : '借款合同详情', 
		    //详情面板标题
		    /*i18n : UI_Menu.appDefault,*/
		    //国际化资源对象
		    htmls: htmlArrs_1,
		    url: './fcLoanContract_get.action',
//		    prevUrl: '#PREURLCFG#',
//		    nextUrl: '#NEXTURLCFG#',
		    params: {
		        formId: _this.formId
		    },
		    callback: {
		        sfn: function(jsonData) {
		            /* 可在对页面进行赋值前，对数据进行转换处理 to do .. */
		            //jsonData["leaf"] = (jsonData["leaf"]=="false") ? "否" : "是";
		        	
		        	var appAmount = jsonData["appAmount"];
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
		        	jsonData["loanLimit"] = (arr.length > 0) ? arr.join("") : "";
		        	
		        	jsonData["rate"] =  jsonData["rate"]+'%';
		        	jsonData["mrate"] =  jsonData["mrate"]+'%';
		        	jsonData["prate"] =  jsonData["prate"]+'%';
		        	jsonData["arate"] =  jsonData["arate"]+'%';
		        	jsonData["urate"] =  jsonData["urate"]+'%';
		        	jsonData["frate"] =  jsonData["frate"]+'%';
		        	var payType = jsonData["payType"];
		        	var flexDatas = jsonData["flexDatas"];
		        	_this.showFlexPlanInfos(payType,flexDatas);
		        	jsonData["payType"] =  Render_dataSource.payTypeRender(payType);
		        	jsonData["rateType"] =  Render_dataSource.rateTypeRender(jsonData["rateType"]);
		        	jsonData["isadvance"] = (jsonData["isadvance"]) ? "是":"否";
		        	jsonData["mgrtype"] =  Render_dataSource.mgrtypeRender(jsonData["mgrtype"]) +'['+ jsonData["mrate"]+']';
		        	jsonData["appAmount"] =  Cmw.getThousandths(appAmount)+'元&nbsp;&nbsp;<span style="color:red;"><br><b>(大写：'+Cmw.cmycurd(appAmount)+')</b></span>';
		        	var payDay = jsonData["payDay"];
		        	var setdayType = jsonData["setdayType"];
		        	switch (setdayType){
		        		case 1 : setdayType = "实际放款日作为结算日";break;
		        		case 2 : setdayType = "公司规定的结算日";break;
		        		case 3 : setdayType = "其它结算日";break;
		        		default : setdayType = "";break;
		        	}
		        	if(payDay){
		        		jsonData["payDay"] =  setdayType+"("+payDay+")"+'号&nbsp;&nbsp';
		        	}else{
		        		jsonData["payDay"] = setdayType+"";
		        	}
		        	
		        	var sysId = _this.sysid;
		        	var formId = jsonData.id;
		        	if(!formId){
		        		formId = -1;
		        	}
		        	var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_8,sysId:sysId,isNotDisenbaled:true};
		        	_this.attachMentFs.reload(params);
		        }
				 }
			}];
			var cfg = {autoWidth:true,detailCfgs:detailCfgs,attachLoad:attachLoad};
			var detailPanel_1 = new Ext.ux.panel.DetailPanel(cfg);
			detailPanel_1.add(_this.attachMentFs);
			return detailPanel_1;
		},
		/**
		 * 显示灵活还本信息
		 */
		showFlexPlanInfos : function(payType,flexDatas){
			var flag = false;
			if(payType == "P0008"){/*灵活还本*/
				flag = true;
			}
			var flexTr = Ext.get(this.idMgr.flexDatasTrId);
			if(!flexTr) return;
			if(flag){
				flexTr.show();
				if(!flexDatas) return;
				flexDatas = Ext.decode(flexDatas);
				var arr = [];
				for(var i=0,count=flexDatas.length; i<count; i++){
					var flexData = flexDatas[i];
					var xpayDate = flexData.xpayDate;
					var principal = flexData.principal;
					if(!principal || principal==0) continue;
					var html = "["+xpayDate+":"+principal+"元]";
					arr[arr.length] = html;
				}
				var flexTd = Ext.get(this.idMgr.flexDatasTdId);
				if(!flexTd) return;
				var flexHtml = arr.join(",");
				flexTd.update(flexHtml);
			}else{
				flexTr.hide();
			}
		}
	};
});