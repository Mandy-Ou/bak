/*同心日科技公司Erp 系统 命名空间*/
Ext.namespace("skythink.cmw.workflow.bussforms");
/**
 * 提前还款计算FrmUI
 */
skythink.cmw.workflow.bussforms.PrepaymentFrmMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(skythink.cmw.workflow.bussforms.PrepaymentFrmMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		var finishBussCallback = tab.finishBussCallback;
		this.module = new Cmw.app.widget.AbsPanelView({
			tab : tab,
			params : params,/*apptabtreewinId,tabId,sysid*/
			getAppCmpt : this.getAppCmpt,
			createDetailPnl: this.createDetailPnl,
			changeSize : this.changeSize,
			destroyCmpts : this.destroyCmpts,
			globalMgr : this.globalMgr,
			refresh : this.refresh,
			finishBussCallback : finishBussCallback,/*由必做或选做业务菜单传入的回調函数，主要功能：当业务表单保存后，更新必做或选做事项为已做*/
			prefix : Ext.id()
		});
	},
	refresh : function(){
		var _this = this;
		if (!this.appPanel.rendered) {
			this.appPanel.addListener('render', function(cmpt) {
				_this.globalMgr.loadDatas(_this);
			});
		} else {
			this.globalMgr.loadDatas(_this);
		}
	},
	changeSize : function(whArr){
		var width = whArr[0];
		var height = whArr[1];
		this.appPanel.setWidth(width,true);
		this.appPanel.setHeight(height,true);
	},
	getAppCmpt :function(){
		this.appPanel = new Ext.Panel({ border : false});
		var detailPanel = this.createDetailPnl();
		this.appPanel.add(detailPanel);
		this.appPanel.doLayout();
		this.refresh();
		return this.appPanel;
	},
	/**
	 * 创建提前还款计算详情面板
	 */
	createDetailPnl : function(){
		var _this = this;
		var  freeamountTdId = this.globalMgr.idMgr.freeamountTdId;
		var  imamountTdId = this.globalMgr.idMgr.imamountTdId;
		var  totalAmountTdId = this.globalMgr.idMgr.totalAmountTdId;
		var htmlArrs_1 = [
					'<tr><th col="xpayDate">最近一期还款日</th> <td col="xpayDate" >&nbsp;</td><th col="xphases">总期数</th> <td col="xphases" >&nbsp;</td><th col="yphases">已还期数</th> <td col="yphases" >&nbsp;</td></tr>',
					'<tr><th col="yprincipals">已还本金</th> <td col="yprincipals" >&nbsp;</td><th col="reprincipal">剩余本金</th> <td col="reprincipal" >&nbsp;</td><th col="phases">当期期数</th> <td col="phases" >&nbsp;</td></tr>',
					'<tr><th col="principal">当期本金</th> <td col="principal" >&nbsp;</td><th col="interest">当期利息</th> <td col="interest" >&nbsp;</td><th col="mgrAmount">当期管理费</th> <td col="mgrAmount" >&nbsp;</td></tr>',
					'<tr><th col="overCount">逾期期数</th> <td col="overCount" >&nbsp;</td><th col="zinterests">逾期利息</th> <td col="zinterests" >&nbsp;</td><th col="zmgrAmounts">逾期管理费</th> <td col="zmgrAmounts" >&nbsp;</td></tr>',
					'<tr><th col="zpenAmounts">逾期罚息</th> <td col="zpenAmounts" >&nbsp;</td><th col="zdelAmounts">逾期滞纳金</th> <td col="zdelAmounts" >&nbsp;</td><th col="freeamount">提前还款手续费</th> <td id="'+freeamountTdId+'" col="freeamount" >&nbsp;</td></tr>',
					'<tr><th col="imamount">应退息费</th> <td id="'+imamountTdId+'" col="imamount" >&nbsp;</td><th col="totalAmount">应收合计</th> <td id="'+totalAmountTdId+'" col="totalAmount" colspan=3>&nbsp;</td></tr></tr>'];
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
		    url: './fcPrepayment_getCal.action',
		    params: {
		        id: -1
		    },
		    callback: {
		        sfn: function(jsonData) {
		        	var planId = jsonData["planId"] || "";
		        	 _this.globalMgr.applyForm.setFieldValue("payplanId",planId);
		        	if(_this.globalMgr.checkError(_this,jsonData)) return;
		        	_this.globalMgr.setAmountData(_this,jsonData);
		        	var frateField = _this.globalMgr.applyForm.findFieldByName("frate");
		        	var isretreatField =  _this.globalMgr.applyForm.findFieldByName("isretreat");
		      		_this.globalMgr.calculateFree(_this,frateField,true);
		      		_this.globalMgr.calculateFree(_this,isretreatField,false);
		      		_this.globalMgr.fmtJsonData(jsonData);
		        }
		    }
		}];
		var pcDetailPanel = new Ext.ux.panel.DetailPanel({
			title : '提前还款计算 >> ',
		    detailCfgs: detailCfgs_1,
		    border : false,
	     	isLoad : false
		});
			
		var txt_id = FormUtil.getHidField({fieldLabel: '提前还款申请单ID',name: 'id'});
		var txt_freeamount = FormUtil.getHidField({fieldLabel: '提前还款手续费',name: 'freeamount'});
		var txt_imamount = FormUtil.getHidField({fieldLabel: '退息费金额',name: 'imamount'});
		var txt_totalAmount = FormUtil.getHidField({fieldLabel: '应收合计',name: 'totalAmount'});
		var txt_payplanId = FormUtil.getHidField({fieldLabel: '还款计划ID',name: 'payplanId'});
		
		var bdat_reviewDate = FormUtil.getHidField({fieldLabel: '复核日期',name: 'reviewDate',value:CURENT_DATE()});
		var txt_reviewer = FormUtil.getHidField({fieldLabel: '财务复核人',name: 'reviewer',value:CURRENT_USERID});
		
		var bdat_adDate = FormUtil.getDateField({
			    fieldLabel: '提前还款日期',
			    name: 'predDate',
			    "width": 125,
			    "allowBlank": false,
			    listeners : {change : function(field, newVal, oldVal){
			    	if(newVal != oldVal) _this.globalMgr.isChange = true; 
			    }}
			});
			
		var dob_frate = FormUtil.getDoubleField({
			    fieldLabel: '提前还款手续费率',
			    name: 'frate',
			    "width": 125,
			    "allowBlank": false,
			    "value": "0.00",
			    "decimalPrecision": "2",
			     unitText : "%",
			    listeners : {change : function(field, newVal, oldVal){
			    	if(newVal != oldVal) _this.globalMgr.isChange = true; 
			    	//reloadDetail();
			    }}
			});
			
			var rad_isretreat = FormUtil.getRadioGroup({
			    fieldLabel: '是否退息费',
			    name: 'isretreat',
			    "width": 150,
			    "allowBlank": false,
			    "maxLength": 10,
			    "items": [{
			        "boxLabel": "不退息费",
			        "name": "isretreat",
			        "inputValue": 1
			    },
			    {
			        "boxLabel": "退息费",
			        "name": "isretreat",
			        "inputValue": 2
			    }],
			    listeners : {change : function(field, newVal, oldVal){
			    	//reloadDetail();
			    	if(newVal != oldVal) _this.globalMgr.isChange = true; 
			    }}
			});
			var layout_fields = [txt_id,txt_freeamount,txt_imamount,txt_totalAmount,
				txt_payplanId,bdat_reviewDate,txt_reviewer,
					{cmns: FormUtil.CMN_THREE,fields: 
						[bdat_adDate,dob_frate,rad_isretreat]
					}
				];
			var btnCalcute = new Ext.Button({text:'计算',handler : function(){
				reloadDetail();
			}});
			
			var btnSave = new Ext.Button({text:'保存计算结果',disabled:true,handler : function(){
				btnSave.setDisabled(true);
				if(_this.globalMgr.isChange){
					ExtUtil.alert({msg:'在保存计算结果之前，请先点击"计算"功能，进行提前还款计算!'});
					return;
				}
				EventManager.frm_save(applyForm,{sfn:function(data){
					if(_this.finishBussCallback) _this.finishBussCallback(data);
			 	}});
			}});
			var frm_cfg = {
				title : '提前还款计算参数<span style="color:red;font-weight:bold;">(提示：可通过改变提前还款日期、手续费率、是否退息费来计算提前还款额)</span>',
				autoScroll : true,
				 border : false,
				url : './fcPrepayment_saveResult.action',
				labelWidth : 115,
				buttons :[btnCalcute,btnSave]
			};
		/**
		 * 重新加载提前还款计算面板数据
		 */
		function reloadDetail(){
			var contractId =_this.globalMgr.contractId;
			var date = bdat_adDate.getValue();
	    	var xpayDate = date.format('Y-m-d');
	    	var params = {contractId:contractId,xpayDate:xpayDate};
	    	pcDetailPanel.reload(params);
	    	_this.globalMgr.isChange = false;
	    	btnSave.setDisabled(false);
		}
		var applyForm = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
		this.globalMgr.applyForm = applyForm;
		pcDetailPanel.add(applyForm);
		this.globalMgr.pcDetailPanel = pcDetailPanel;
		return pcDetailPanel;
	},
	
	globalMgr : {
		isChange : false,/*用来记录是否改变过提前还款日期、手续费率、是否退息费的其中一项*/
		prepaymentId : this.params.applyId,
		contractId : this.params.contractId,
		sysId : this.params.sysid,
		applyForm : null,
		pcDetailPanel : null,
		errArr : [],/*错误信息*/
		amountData : null,/*缓存提前还款计算相关金额*/
		idMgr:{
				/*提前还款手续费TD id*/
				freeamountTdId :Ext.id(null, 'freeamount'),
				/*应退息费 TD id*/
				imamountTdId : Ext.id(null, 'imamount'),
				/*应收合计 TD id*/
				totalAmountTdId :  Ext.id(null, 'totalAmount')
			},
		/**
		 * 加载数据
		 */
		loadDatas : function(_this){
			_this.globalMgr.applyForm.setValues('./fcPrepayment_get.action', {params : {id : _this.globalMgr.prepaymentId},sfn: function(json_data){
				_this.globalMgr.applyForm.setFieldValue("reviewDate",CURENT_DATE());
				_this.globalMgr.applyForm.setFieldValue("reviewer",CURRENT_USERID);
				var predDate = _this.globalMgr.applyForm.getValueByName("predDate");
				var adDate = json_data["adDate"];
				if(!predDate) predDate = adDate;
				_this.globalMgr.applyForm.setFieldValue("predDate",predDate);
				_this.globalMgr.pcDetailPanel.reload({contractId:_this.globalMgr.contractId,xpayDate:predDate});
			}});
		},
		/**
		 * 检查提前还款计算结查
		 */
		checkError : function(_this,jsonData){
			var planId = jsonData["planId"];
			var phases = jsonData["phases"];
			var yphases = jsonData["yphases"];
			var dat_predDate = _this.globalMgr.applyForm.findFieldByName("predDate");
			var predDate = dat_predDate.getValueAsStr();
			_this.globalMgr.errArr = [];
			var hasErr = false;
			if(!planId){
				_this.globalMgr.errArr[_this.globalMgr.errArr.length] = "经过提前还款试算后发现["+predDate+"]不能进行提前还款!";
				hasErr = true;
			}else{
				if(phases <= yphases){
					_this.globalMgr.errArr[_this.globalMgr.errArr.length] = "经过提前还款试算后发现["+predDate+"]所对应的第["+phases+"]期应还本息费已经结清，请选择一个未结清的日期来提前还款!";
					hasErr = true;
				}
			}
			if(!hasErr) _this.globalMgr.errArr = [];
			return hasErr;
		},
		/**
		 * 设置金额数据
		 */
		setAmountData : function(_this,jsonData){
			var keys = ["reprincipal","principal","interest","mgrAmount","zinterests","zmgrAmounts","zpenAmounts","zdelAmounts","freeamount","imamount","totalAmount"];
			if(!_this.globalMgr.amountData) _this.globalMgr.amountData = {};
			for(var i=0,count=keys.length; i<count; i++){
				key = keys[i];
				_this.globalMgr.amountData[key] = jsonData[key];
			}
		},
		/**
		 * 计算提前还款手续费或应退息费
		 * @param 取值字段
		 * @param  isPrefreet 是否提前还款手续费计算 true : 是, false : 退息费计算
		 */
		calculateFree : function(_this,field,isPrefreet){
			var objVal = _this.globalMgr.applyForm.getValuesByNames('payplanId,frate,predDate');
			var _params = {planId : objVal.payplanId};
			var val = field.getValue();
			var isRemoteCal = true;/*当不允许退息费时，此标记为假*/
			if(isPrefreet){/*计算提前还款手续费*/
				_params["freeType"] = 1;
				_params["frate"] = objVal.frate;
				isRemoteCal = true;
			}else{/*计算应退息费*/
				if(val && val == 2){/*允许退息费*/
					_params["freeType"] = 2;
					_params["predDate"] = objVal.predDate;
					isRemoteCal = true;
				}else{
					isRemoteCal = false;
				}
			}
			if(isRemoteCal){
				EventManager.get('./fcPrepayment_getFreeAmount.action',{params:_params,sfn:function(json_data){
					var amount = json_data.amount;
			 		if(isPrefreet){
			 			_this.globalMgr.calTotalAmount(_this,"freeamount",_this.globalMgr.idMgr.freeamountTdId,amount);
			 		}else{
			 			_this.globalMgr.calTotalAmount(_this,"imamount",_this.globalMgr.idMgr.imamountTdId,amount);
			 		}
				}});
			}else{
				_this.globalMgr.amountData["imamount"] = 0;
				_this.globalMgr.calTotalAmount(_this,"imamount",_this.imamountTdId,0);
			}
		},
		/**
		 * 
		 * @param {} fieldName
		 * @param {} labelId
		 * @param {} upAmount
		 */
		calTotalAmount:function(_this,fieldName,labelId,upAmount){
			_this.globalMgr.amountData[fieldName] = upAmount;
			var plusKeys = ["reprincipal","principal","interest","mgrAmount","zinterests","zmgrAmounts","zpenAmounts","zdelAmounts","freeamount"];
			var totalAmount = 0;
			for(var i=0,count=plusKeys.length; i<count; i++){
				var key = plusKeys[i];
				var val = _this.globalMgr.amountData[key] || 0;
				totalAmount += parseFloat(val);
			}
			
			var imamount = _this.globalMgr.amountData["imamount"] || 0;
			totalAmount -= parseFloat(imamount);
			totalAmount = StringHandler.forDight(totalAmount,2);
			_this.globalMgr.applyForm.setFieldValue("totalAmount",totalAmount);
			var labelEl = Ext.get(_this.globalMgr.idMgr.totalAmountTdId);
			if(labelEl){
				totalAmount =  Cmw.getThousandths(totalAmount)+"元&nbsp;&nbsp;<span style='font-weight:bold;color:red;'>("+Cmw.cmycurd(totalAmount)+")</span>";
				labelEl.update(totalAmount);
			}
			
			_this.globalMgr.applyForm.setFieldValue(fieldName,upAmount);
			labelEl = Ext.get(labelId);
			var upAmount = Cmw.getThousandths(upAmount)+"元";
			if(labelEl) labelEl.update(upAmount);
		},
		/**
		 * 格式化jsonData 数据
		 * @param {} _this 当前对象
		 * @param {} jsonData 提前还款详情面板数据
		 */
		fmtJsonData:function(jsonData){
			var arr = ["phases","xphases","yphases","overCount"];
			for(var i=0,count=arr.length; i<count; i++){
				var key = arr[i];
				var val = jsonData[key];
				if(val) jsonData[key] = val + "期";
			}
			jsonData["frate"] = (jsonData["frate"]) ? jsonData["frate"]+"%" : "";
			arr = ["yprincipals","reprincipal","principal","interest","mgrAmount","zinterests","zmgrAmounts","zpenAmounts","zdelAmounts","freeamount","imamount","totalAmount"];
			for(var i=0,count=arr.length; i<count; i++){
				var key = arr[i];
				var val = jsonData[key];
				if(val) jsonData[key] = Cmw.getThousandths(val)+"元";
			}	
		}
	},
	destroyCmpts :function(){
		if(null != this.appPanel){
			this.appPanel.destroy();
			this.appPanel = null;
		}
	}
});