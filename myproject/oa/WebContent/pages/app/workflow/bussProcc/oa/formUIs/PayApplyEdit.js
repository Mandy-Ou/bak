/**
 * #DESCRIPTION#
 * @author smartplatform_auto
 * @date 2014-01-11 09:28:04
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		tbar : null ,
		setParentCfg:function(parentCfg){
			this.parentCfg = parentCfg;
			// --> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			//有工具栏
			this.tbar = this.createTbar();
			//有form Panel
			this.appFrm = this.createForm();
			//工具栏和form Panel 组成弹出窗口
			this.appWin = new Ext.ux.window.MyWindow({
				tbar : this.tbar,
				width:550,
				height : 200,
				items : [this.appFrm]
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
			if(!this.appWin) return;
			this.appWin.close();	//关闭并销毁窗口
			this.appWin = null;		//释放当前窗口对象引用
		},
		
		createTbar : function(){
				var _self = this;
					var ttbar = [{
						text : Btn_Cfgs.SAVE_BTN_TXT,	/*-- 保存 --*/ 
						iconCls:Btn_Cfgs.SAVE_CLS,
						tooltip:Btn_Cfgs.SAVE_TIP_BTN_TXT,
						handler : function() {
							_self.saveData();	
							EventManager.frm_reset(_self.appFrm);
						}
					}, {
						text : Btn_Cfgs.RESET_BTN_TXT,  /*-- 重置 --*/
						iconCls:Btn_Cfgs.RESET_CLS,
						tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
						handler : function() {
							EventManager.frm_reset(_self.appFrm);
						}
					},{
						text : Btn_Cfgs.CLOSE_BTN_TXT,  /*-- 关闭 --*/
						iconCls:Btn_Cfgs.CLOSE_CLS,
						tooltip:Btn_Cfgs.CLOSE_TIP_BTN_TXT,
						handler : function() {
							_self.appWin.hide();
						}
					}]
			
					var toolBar = new Ext.ux.toolbar.MyCmwToolbar({
						aligin : 'right',
						controls : ttbar
					});
					return toolBar;
				},

		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var _this = exports.WinEdit;
			if(_this.parent.refresh)_this.parent.refresh(_this.optionType, data);	
		},
		/**
		 * 创建Form表单
		 */
		createForm : function(){
			var appFrm=this;
			var txt_id = FormUtil.getHidField({
				    fieldLabel: 'id',
				    name: 'id',//借款单的id
				    "width": 150,
				    "allowBlank": false
			});

			var structure = [{
					header: '借款单选择',name: 'code',width:200},{
					header : '借款日期',name : 'loanDate',width : 200},{
					header : '借款金额',name : 'amount',width : 80},{
					header : '已还款余额' , name : 'yamount',width :80
				}];
				
			function ck_callback(grid , value ,selRows){
				var record = selRows[0];
				//原借款金额
				txt_amount.setValue(record.get('amount'));
				//已还款金额
				txt_yamount.setValue(record.get('yamount'));
				//未还款金额
				txt_noamount.setValue(record.get('amount')-record.get('yamount'));
				//借款时间
				txt_loanDate.setValue(record.get('loanDate'));
			}
			var txt_code =new Ext.ux.grid.AppComBoxGrid({
					gridWidth :800,
			   		fieldLabel: '借款单编号',
				    name: 'loanApplyId',
				    structure:structure,
				    dispCmn:'code',
				    isAll:true,
				    url : './oaLoanApply_list.action',
				    needPage : false,
				    isLoad:true,
				    "width": 150,
				    "allowBlank": false,
				    //通过回调函数将数据填充到其他的表格中
				    callback : ck_callback
				    
				});	
			
			var txt_amount = FormUtil.getReadTxtField({
				    fieldLabel: '原借款金额',
				    name: 'amount',
				    "width": 150
				});
			var txt_yamount = FormUtil.getReadTxtField({
				    fieldLabel: '已还款金额',
				    name: 'yamount',
				    "width": 150
				});

			var txt_noamount = FormUtil.getReadTxtField({
				    fieldLabel: '未还款余额',
				    name: 'noamount',
				    "width": 150,
				    "allowBlank": false
				});

			var bdat_repayment = FormUtil.getTxtField({
				    fieldLabel: '本次还款金额',
				    name: 'repayment',
				    "width": 150,
				    "allowBlank": false
				});
				
			var txt_loanDate = FormUtil.getReadTxtField({
				    fieldLabel: '借款日期',
				    name: 'loanDate',
				    "width": 200,
				    "allowBlank": false
				});
				
			//用一个隐藏字段 将金额计算之后返回去，然后再赋值
			var txt_grossAmount = FormUtil.getHidField({
					name : 'grossAmount'				
			});
			
			var layout_fields = [{
				    cmns: FormUtil.CMN_TWO,
				    fields: [ txt_code , txt_amount, txt_yamount, txt_noamount, bdat_repayment, txt_loanDate,txt_id,txt_grossAmount]
				}];
			var frm_cfg = {
				    title: '借款单编辑',
				    labelWidth : 100 
				};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);	
			return appform_1;
				},
		/**
		 * 为表单元素赋值
		 */
		setFormValues : function(){
			
		},
		/**
		 * 上一条
		 */
		preData : function(){
			
		},
		/**
		 * 下一条
		 */
		nextData : function(){
			
		},
		/**
		 * 保存数据
		 */
		saveData : function(){
//			var _self = exports.WinEdit;
			var _self = this;
			var _appgrid = _self.parent.globalMgr._appgrid;
			var name_combox = this.appFrm.findFieldByName("loanApplyId").getValue();
			if(name_combox==''){
				Ext.Msg.alert("提示","请选择数据");
				return false;
			}
			var txt_amount = this.appFrm.findFieldByName("repayment").getValue();
			if(!txt_amount){
				Ext.Msg.alert("提示","还款金额不能为空");
				return false;
			}
			//将借款单编号和id进行分割
			var post_json = name_combox.split("##");
			//借款单id
			var id = post_json[0];
			//借款单编号
			var code = post_json[1];
			//原借款金额
			var amount = this.appFrm.getValueByName("amount");
			//还款金额
			var yamount =this.appFrm.getValueByName("yamount");
			//未还款金额
			var noamount = this.appFrm.getValueByName('noamount');
			//本次还款金额
			var repayment = this.appFrm.getValueByName('repayment');
			//借款日期
			var loanDate = this.appFrm.getValueByName('loanDate');
			/**
			 * 返回json对象
			 */
			var params = {id : id, code : code, amount : amount , yamount : yamount , noamount : noamount ,repayment : repayment ,loanDate : loanDate};
			var data =[];
			var record;
			data.push(params);
			if(data.length>0){
				for(var i = 0; i<data.length;i++){
					record = new Ext.data.Record(data[i]);		
				}
			}
			/**
			 * 获取表格的数据，进行判断,如果名称相同，弹出提示框，否则进行添加
			 */
			var store = _appgrid.getStore(params);
			var num =record.get(code);
			var count = store.getCount();
			if(count==0) {
				store.insert(count,record);
			}else{
				var numIndex=store.findBy(function(rec){
					return rec.get('code')==code;
				});
				if(numIndex != -1){
					ExtUtil.info({msg:"当前已有数据"});
				}else {
					store.insert(count,record);		
				}	
			}
			this.refresh(params);
			this.appWin.hide();
	
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		}
	}
});