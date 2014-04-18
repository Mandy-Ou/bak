/**
 * 灵活还款添加弹出窗口
 * 小额贷款系统 --> 借款合同登记 -->灵活还款 用到
 * @author 程明卫  
 * @date 2014-03-12
 */
define(function(require, exports) {
	exports.DialogBox = {
		parentCfg : null,
		params : null,
		appWin : null,
		appGrid : null,
		isCheck : false,	/*是否显示复选框*/
		selRowIndex : null,/*选中的行索引*/
		idMgr : {
			span_unAmountId : Ext.id(null,'span_unAmountId')/*未分配余额汇总提示*/
		},
		createAppWindow : function(){
			this.appGrid = this.createAppGrid();
			this.appWin = new Ext.ux.window.MyWindow({width:500,height:350,items:this.appGrid});
		},
		createAppGrid : function(){
			var _this = this;
			var structure_1 = [{
			    header: '期数',
			    name: 'phases',
			    width:60
			},
			{
			    header: '还款日期',
			    name: 'xpayDate'
			},
			{
			    header: '本金',
			    name: 'principal'
			}];
			var mon_principal = FormUtil.getMoneyField({
					fieldLabel : '还本金额',
					name : 'principal',
					width : 100,
					value : 0
			});
			var appgrid = new Ext.ux.grid.MyEditGrid({
				title : '灵活还款还本列表(<span style="color:red;">提示：表格中本金列可以编辑</span>)&nbsp;<span id="'+this.idMgr.span_unAmountId+'"></span>',
				//selectType : 'check',
				structure : structure_1,
				editEles : {2:mon_principal},
				height : 150,
				url : './fcFlexPlan_list.action',
				keyField : 'id',
				needPage : false,
				isLoad : false,
				tbar : this.getToolBar(),
				listeners : {
					afteredit : function(e){
						_this.selRowIndex = e.row;
						e.record.commit();
						_this.gather();
					},
					cellclick : function(grid,rowIndex,cmnIndex,e){
						_this.selRowIndex = rowIndex;
					} 
				}
			});
			return appgrid;
		},
		getToolBar : function(){
			var _this = this;
			var toolBar = null;
			var barItems = [{text:Btn_Cfgs.SAVE_BTN_TXT,iconCls:Btn_Cfgs.SAVE_CLS,handler:function(){
				_this.setValue();
			}},{text:Btn_Cfgs.POSTPONE_BTN_TXT,iconCls:Btn_Cfgs.POSTPONE_CLS,handler:function(){
				_this.setPostpone();
			}},{
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
		 * 赋值
		 */
		setValue : function(){
			var appAmount = this.params.appAmount;
			var batchDatas = this.getBatchDatas();
			var totalAmount = batchDatas.totalAmount;
			if(totalAmount<appAmount){
				var zamount = appAmount - totalAmount;
				zamount = Cmw.getThousandths(zamount) + "元";
				ExtUtil.alert({msg:'本次灵活还本还剩['+zamount+']余额未分配。请分配完后，再保存!'});
				return;
			}
			var dataArr = batchDatas.dataArr;
			this.parentCfg.callback(dataArr);
			this.appWin.hide();
		},
		/**
		 * 获取Grid数据
		 */
		getBatchDatas : function(){
			var dataArr = [];
			var totalAmount = 0;
			var store = this.appGrid.getStore();
			for(var i=0,count=store.getCount(); i<count; i++){
				var record = store.getAt(i);
				var phases = record.get("phases");
				var xpayDate = record.get("xpayDate");
				var principal = record.get("principal");
				if(!principal){
					principal = 0;
				}else{
					principal = parseFloat(principal);
				}
				totalAmount += principal;
				dataArr[dataArr.length] = {phases:phases,xpayDate:xpayDate,principal:principal};
			}
			return {dataArr:dataArr,totalAmount:totalAmount};
		},
		/**
		 * 顺延
		 */
		setPostpone : function(){
			if(!this.selRowIndex){
				ExtUtil.alert({msg:'请在表格中本金一栏中输入要进行顺延的还本的金额!'});
				return;
			}
			var store = this.appGrid.getStore();
			var precord = store.getAt(this.selRowIndex);
			var xpayDate = precord.get("xpayDate");
			var principal = precord.get("principal");
			if(!principal || parseFloat(principal) <= 0){
				ExtUtil.alert({msg:'"'+xpayDate+'"那一期的还款本金不能小于O!'});
				return;
			}
			var count = store.getCount();
			if(this.selRowIndex < (count-1)){
				var i = this.selRowIndex+1;
				principal = parseFloat(principal);
				var _appAmount = parseFloat(this.params.appAmount);
				_appAmount -= principal;
				for(; i<count; i++){
					var record = store.getAt(i);
					_appAmount = _appAmount - principal;
					var currAmount = 0;
					if(_appAmount>=0){
						currAmount = principal;
					}
					record.set("principal",currAmount);
				}
				if(_appAmount > 0){
					var lastRecord = store.getAt(count-1);
					var _principal = lastRecord.get("principal");
					_principal = parseFloat(_principal) + _appAmount;
				}
			}
			this.gather();
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
			if(_parentCfg.parent){
				this.appWin.show(_parentCfg.parent);
			}else{
				this.appWin.show();
			}
			var  appAmount = this.params.appAmount;
			this.setUnamount(appAmount);
			appAmount = Cmw.getThousandths(appAmount) + "元";
			var title = "灵活还款，本金信息编辑 [贷款本金:"+appAmount+"]";
			this.appWin.setTitle(title);
			var batchFlexDatas = this.parentCfg.batchFlexDatas;
			if(batchFlexDatas){
				var batchFlexDatas = Ext.decode(batchFlexDatas);
				this.appGrid.addRecords(batchFlexDatas);
			}else{
				this.appGrid.reload(this.params);
			}
		},
		/**
		 * 汇总计算余额
		 */
		gather : function(){
			var totalAmount = 0;
			var store = this.appGrid.getStore();
			for(var i=0,count=store.getCount(); i<count; i++){
				var record = store.getAt(i);
				var principal = record.get("principal");
				if(!principal){
					principal = 0;
				}else{
					principal = parseFloat(principal);
				}
				totalAmount += principal;
			}
			var appAmount = this.params.appAmount;
			totalAmount = parseFloat(appAmount) - totalAmount;
			if(totalAmount < 0) totalAmount = 0;
			this.setUnamount(totalAmount);
		},
		setUnamount : function(amount){
			var eleUnamount = Ext.get(this.idMgr.span_unAmountId);
			if(!eleUnamount) return;
			amount = Cmw.getThousandths(amount) + "元";
			eleUnamount.update("未分配余额："+amount);
		},
		setParentCfg : function(_parentCfg){
			this.selRowIndex = null;
			this.parentCfg = _parentCfg;
			this.params = this.parentCfg.params;
			if(this.parentCfg.isCheck){
				this.isCheck = this.parentCfg.isCheck;
			}
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