/**
 * 封装 Ext TwinTriggerField 类
 * 使其支持点击后弹出公式窗口
 */
Ext.namespace("Ext.ux.form");

Ext.ux.form.MyFormulaField = Ext.extend(Ext.ux.SearchField,{
	tbar : null,
	formulaWin : null,
	formulaFrm : null,
	cboUrl : null,
	gridUrl : './fsFinCustField_list.action',/*字段Grid URL*/
	gridParams : null,/*字段Grid参数*/
	fieldsGrid : null,/*字段Grid对象*/
	isLoad : true,/*Grid创建后是否马上加载数据*/
	winWidth:620,
	fieldSource : null,/*字段来源 , 取值：FinCustField 从财务系统字段表取字段数据*/
	fieldDatas : null,/*已经使用的字段 {字段ID："{字段名称}"}*/
	selVal : null,/*当前控件值*/
	change : false,/*标识公式是否发生过更改，true:已更改,false:未更改*/
	isError : false,/*公式检查是否有错*/
	isCheck : false,
	onTrigger2Click : function(){
		if(!this.formulaWin){
			this.formulaWin = this.createFormulaWin();
		}
		var el = this.el;
		this.formulaWin.show(el);
	},
	createFormulaWin : function(){
		var _this = this;
		this.tbar = this.createTbar();
		this.formulaFrm = this.createFormulaFrm();
		var win = new Ext.ux.window.MyWindow({
			title : '公式编辑器',
			tbar : this.tbar,
			width: this.winWidth,
			items : [this.formulaFrm],
			listeners : {
				show : function(win){
					if(_this.selVal) _this.setValue(_this.selVal);
				}
			}
		});
		return win;
	},
	/**
	 * 创建工具栏
	 * @return {}
	 */
	createTbar : function(){
		var _this = this;
		var barItems = [
						{token : '保存',text:Btn_Cfgs.SAVE_BTN_TXT,iconCls:Btn_Cfgs.SAVE_CLS,handler:function(){
							_this.save();
						}},
						{token : '清空',text:Btn_Cfgs.CLEAR_BTN_TXT,iconCls:Btn_Cfgs.CLEAR_CLS,handler:function(){
							_this.clear();
						}},
						{token : '删除',text:Btn_Cfgs.DELETE_BTN_TXT,iconCls:Btn_Cfgs.DELETE_CLS,handler:function(){
							_this.del();
						}},
						{type:'sp'},
						{token : '检查公式',text:Btn_Cfgs.CHECKFM_BTN_TXT,iconCls:Btn_Cfgs.CHECKFM_CLS,handler:function(){
							_this.checkFormula();
						}},
						{token : '关闭',text:Btn_Cfgs.CLOSE_BTN_TXT,iconCls:Btn_Cfgs.CLOSE_CLS,handler:function(){
							_this.formulaWin.hide();
						}}/*,
						{token : '导入公式',text:Fs_Btn_Cfgs.VTEMP_EDIT_BTN_TXT,iconCls:Fs_Btn_Cfgs.VTEMP_EDIT_BTN_CLS,handler:function(){
							self.globalMgr.winEdit.show({key:"编辑模板",optionType:OPTION_TYPE.EDIT,self:self});
						}}*/
						];
		var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems});
		return toolBar;
	},
	createFormulaFrm : function(){
		var _this = this;
		var txaFormula = new Ext.form.TextArea({name:'dispExpress',hideLabel:true,width:550,height:100,listeners:{
				keyup:function(field,e){
					_this.change = true;
					_this.isCheck = false;
				}
			}});
		var hidId = new Ext.form.Hidden({name:'id',fieldLabel:'ID'});
		var hidCode = new Ext.form.Hidden({name:'code',fieldLabel:'code'});
		var hidExpress = new Ext.form.Hidden({name:'express'});
		var hidFieldIds = new Ext.form.Hidden({name:'fieldIds'});
		var fst_operator = this.createFieldSet('运算符',['+','=','!=','-','>','>=','*','<','<=','/','(',')','&&','||','?',':']);
		var fst_nums = this.createFieldSet('数字',['1','2','3','4','5','6','7','8','9','0','.']);
		var fst_fields = this.createCfdFieldSet();
		var layout_fields = [
			txaFormula,hidCode,hidId,hidExpress,hidFieldIds, {
			    cmns: FormUtil.CMN_THREE,
			    fields: [fst_operator,fst_nums,fst_fields]
			}];
		var fieldSet = FormUtil.createLayoutFieldSet({title:'公式信息编辑'},layout_fields);
		var appForm = FormUtil.createLayoutFrm({url:'./sysFormula_save.action'},[fieldSet]);
		return appForm;
	},
	save : function(){
		var dispExpress = this.formulaFrm.getValueByName('dispExpress');
		if(!dispExpress){
			ExtUtil.alert({msg:'公式内容不能为空!'});
			return;
		}
		if(!this.change){
			ExtUtil.alert({msg:'公式未发生变化，不需要保存!'});
			return;
		}
		var _this = this;
		if(this.isCheck && this.isError){
			ExtUtil.alert({msg:'检查到公式有错，不能保存!'});
			return;
		}
		if(this.isCheck){/*公式已检查就直接保存*/
			saveData();
		}else{/*执行公式检查*/
			this.checkFormula(saveData);
		}
		
		function saveData(){
			EventManager.frm_save(_this.formulaFrm,{sfn:function(jsonData){
				var id = jsonData.id;
				_this.formulaFrm.setFieldValue('id',id);
				_this.setValue(id);
				_this.formulaWin.hide();
			}});
		}
	},
	/**
	 * 检查公式
	 */
	checkFormula : function(callback){
		var dispExpress = this.formulaFrm.getValueByName('dispExpress');
		if(!dispExpress){
			ExtUtil.alert({msg:'没有公式可供检查'});
			return false;
		}
		var _this = this;
		if(!this.fieldDatas){
			this.parseFoumula(false);
			this.checkFormula2server(null,callback);
		}else{
			this.parseFoumula(true);
			ParamsWinFactory.show(this.fieldDatas);
			ParamsWinFactory.callback = function(datas){
				_this.checkFormula2server(datas,callback);
			};
		}
		
	},
	/**
	 * 开始服务端公式验证
	 * @param params 公式所需要的参数值
	 */
	checkFormula2server : function(params,callback){
		var express = this.formulaFrm.getValueByName('express');
		if(params) params = Ext.encode(params); 
		var _this = this;
		EventManager.get('./sysFormula_check.action',{params:{express:express,fmparams:params},sfn:function(json_data){
			_this.isCheck = true;
			_this.isError = false;
			Ext.tip.msg(Msg_SysTip.title_appconfirm,Msg_SysTip.msg_checkFormulaOk);
			if(callback) callback();
		},ffn:function(action){
			_this.isCheck = true;
			_this.isError = true;
			var errMsg = Msg_SysTip.msg_checkFormulaNo;
			if(action || action.msg){
				errMsg = Ext.isString(action) ? action : action.msg;
				ExtUtil.alert({msg:errMsg});
			} 
		}});
	},
	parseFoumula : function(isParams){
		var dispExpress = this.formulaFrm.getValueByName('dispExpress');
		var hidExpress = this.formulaFrm.findFieldByName('express');
		var hidFieldIds = this.formulaFrm.findFieldByName('fieldIds');
		var fieldIds = '';
		var express = '';
		if(!isParams){
			express = dispExpress;
		}else{
			var fieldDatas = this.fieldDatas;
			var fidArr = [];
			for(var key in fieldDatas){
				var metea = fieldDatas[key];
				var name = metea.name;
				if(!name) continue;
				var field = metea.field;
				fidArr[fidArr.length] = key;
				dispExpress = dispExpress.replaceAll('{'+name+'}',field);
			}
			fieldIds = fidArr.join(",");
			express = dispExpress;
		}
		hidFieldIds.setValue(fieldIds);
		hidExpress.setValue(express);
	},
	/**
	 * 删除公式
	 */
	del : function(){
		var _this = this;
		ExtUtil.confirm({msg:Msg_SysTip.msg_delFmAnswer,fn:function(btn){
			if(btn != 'yes') return;
			if(!_this.selVal){
				_this.reset();
			}else{
				var params = {ids:_this.selVal};
				EventManager.get('./sysFormula_delete.action',{params:params,sfn:function(json_data){
				 	_this.reset();
				}});
			}
		}});
	},
	/**
	 * 清空数据
	 */
	clear : function(){
		if(this.formulaFrm && this.formulaFrm.rendered) this.formulaFrm.reset();
		if(this.fieldDatas) this.fieldDatas=null;
		if(this.change) this.change = false;
		if(this.isCheck) this.isCheck = false;
		if(this.isError) this.isError = false;
	},
	/**
	 * 创建自定义字段 FieldSet 对象
	 */
	createCfdFieldSet:function(){
		var items = [];
		var cboBussObj = this.createBussObjCbo();
		if(cboBussObj) items[items.length] = cboBussObj;
		var fieldsGrid = this.createFieldGrid();
		this.fieldsGrid = fieldsGrid;
		items[items.length] = fieldsGrid;
		var fieldSet = FormUtil.createLayoutFieldSet({title:'公式字段列表'},items);
		return fieldSet;
	},
	/**
	 * 创建业务对象下拉框
	 */
	createBussObjCbo : function(){
		if(!this.cboUrl) return null;
		this.isLoad = false;
		var _this = this;
		var bussObjCbo = FormUtil.getRCboField({hideLabel:true,width:180,url:this.cboUrl});
		bussObjCbo.addListener('select',function(combo,record,index){
			var id=record.id;
			var params = {bussObjectId:id}
			if(_this.gridParams) Ext.applyIf(params,_this.gridParams);
			_this.fieldsGrid.reload(params);
		});
		return bussObjCbo;
	},
	/**
	 * 创建自定义字段Grid
	 */
	createFieldGrid : function(){
		 var structure = [
						{header: '字段说明', name: 'name',width: 150},
						{header: '字段名称', name: 'field',width: 55},
						{header: '表列名',name: 'cmn',hidden:true},
						{header: '数据类型',name: 'dataType',hidden:true}
						];
		var appgrid = new Ext.ux.grid.AppGrid({
			//title : '公式字段列表',
			structure : structure,
			url : this.gridUrl,
			keyField : 'id',
			needPage : false,
			isLoad: this.isLoad,
			width:180,
			height:240
		});
		if(this.gridParams) appgrid.params = this.gridParams;
		var _this = this;
		appgrid.addListener('rowclick',function(grid,rowIndex,e){
			_this.addClick2Field(grid,rowIndex);
		});
		return appgrid;
	},
	/**
	 * 为字段Grid 添加单击事件
	 */
	addClick2Field : function(grid,rowIndex){
		var store = grid.getStore();
		var record = store.getAt(rowIndex);
		if(!this.fieldDatas) this.fieldDatas = {};
		var id = record.id;
		var name = record.get('name');
		var field = record.get('field');
		var dataType = record.get('dataType');
		this.fieldDatas[id] = {name:name,field:field,dataType:dataType};
		name = "{"+name+"}";
		this.setTxaDispExpressValue(name);
		this.change = true;
		this.isCheck = false;
	},
	/**
	 * 创建公式项按钮
	 * @param {} datas 公式数据项数组
	 * @param {} cmns	每一排的列数
	 * @return {}
	 */
	createFieldSet : function(title,datas,cmns){
		if(!cmns) cmns = 3;
		var idArr = [];
		var htmlArr = [];
		var idPrefix = Ext.id(null,'formulaItem')+'_';
		for(var i=0,count=datas.length; i<count; i++){
			var num = i+1;
			var eleId =  idPrefix + num;
			idArr[idArr.length] = eleId;
			var val = datas[i];
			htmlArr[htmlArr.length] = '<input id="'+eleId+'" style="width:30px;padding:5px;" type="button" value="'+val+'" />';
			if(num%cmns == 0) htmlArr[htmlArr.length] = '<br/>';
		}
		var eleIds = idArr.join(",");
		var html = "&nbsp;&nbsp;"+htmlArr.join("&nbsp;&nbsp;");
		var fst = FormUtil.createFieldSet({title :title,eleIds:eleIds,html:html,anchor:'80%'});
		var _this = this;
		fst.addListener('afterrender',function(_fst){
			_this.addClick2eles(_fst);
		});
		return fst;
	},
	addClick2eles : function(fst){
		var _eleIds = fst.eleIds;
		if(!_eleIds) return;
		var _this = this;
		var eleIdArr = _eleIds.split(",");
		for(var i=0,count=eleIdArr.length; i<count; i++){
			var eleId = eleIdArr[i];
			var eleObj = Ext.get(eleId);
			if(!eleObj) continue;
			eleObj.on('click',function(e,t,o){
				var val = t.value;
				if(!val) val = t.innerText;
				_this.setTxaDispExpressValue(val);
				_this.change = true;
				_this.isCheck = false;
			},this);
		}
	},
	/**
	 * 为公式编辑器赋值
	 * @param {} val 要赋的值
	 */
	setTxaDispExpressValue:function(val){
		if(!val) return;
		var txa_dispExpress =  this.formulaFrm.findFieldByName("dispExpress");
		var dispExpress = txa_dispExpress.getValue();
		dispExpress += val;
		txa_dispExpress.setValue(dispExpress);
	},
	setValue : function(v){
		this.clear();
		if(!v && v !== 0) return;
		var isAsync = true;
		if(this.formulaFrm && this.formulaFrm.rendered){
			var id = this.formulaFrm.getValueByName('id');
			if(v == id) isAsync = false;
		}
		if(!isAsync) return;
		var _this = this;
		var params = {id:v};
		if(this.fieldSource){
			params["fieldSource"] = this.fieldSource;
		}
		
		 EventManager.get('./sysFormula_get.action',{params:params,sfn:function(json_data){
		 	_this.reset();
		 	var fieldDatas = json_data.fieldDatas;
		 	_this.fieldDatas = fieldDatas;
		 	_this.selVal = json_data["id"];
		 	var dispExpress = json_data["dispExpress"];
		 	if(dispExpress){
		 		Ext.ux.form.MyFormulaField.superclass.setValue.call(_this,dispExpress);
		 	}
		 	if(_this.formulaFrm && _this.formulaFrm.rendered){
		 		_this.formulaFrm.setVs(json_data);
		 	}
		 }});
	},
	/**
	 * 获取公式ID值
	 * @return {}
	 */
	getValue : function(){
		return this.selVal;
	},
	reset : function(){
		Ext.ux.form.MyFormulaField.superclass.setValue.call(this,"");
		if(this.selVal) this.selVal=null;
		this.clear();
	}
});

//注册成xtype以便能够延迟加载   
Ext.reg('myformulafield', Ext.ux.form.MyFormulaField);  

/**
 * 参数输入窗口对象
 * @type 
 */
var ParamsWinFactory = {
	paramsWin : null,
	paramsFrm : null,
	callback : null,/*当参数窗口点击确定按钮后，调用此函数*/
	createWin : function(){
		var hidTest = FormUtil.getHidField({name:Ext.id(null,'hidTest')});
		var appForm = FormUtil.createFrm({labelWidth:100,items:[hidTest]});
		this.paramsFrm = appForm;
		var buttons = this.createButtons();
		var win = new Ext.ux.window.MyWindow({
			title : '公式测试参数录入',
			width: 300,
			height:200,
			items : [this.paramsFrm],
			buttonAlign : 'center',
			buttons : buttons
		});
		this.paramsWin = win;
	},
	show : function(metea){
		if(!this.paramsWin) this.createWin();
		this.paramsWin.show();
		this.addFields(metea);
	},
	createButtons : function(){
		var _this = this;
		var buttons = [{text:Btn_Cfgs.CONFIRM_BTN_TXT,handler:function(){
			if(!_this.callback){
				ExtUtil.alert({msg:'请为 ParamsWinFactory 提供 callback 回调函数!'});
				return;
			}
			if(!_this.paramsFrm.isValid()) return;
			var formData = _this.paramsFrm.getValues();
			_this.callback(formData);
			_this.paramsFrm.removeAll();
			_this.paramsWin.hide();
		}},{text:Btn_Cfgs.CLOSE_BTN_TXT,handler:function(){
			_this.paramsFrm.removeAll();
			_this.paramsWin.hide();
		}}];
		return buttons;
	},
	addFields : function(metea){
		this.paramsFrm.removeAll();
		if(!metea) return;
		var fields = [];
		for(var key in metea){
			var fieldInfo = metea[key];
			if(!fieldInfo || !fieldInfo.name) continue;
			var name = fieldInfo.name;
			var field = fieldInfo.field;
			var cfg = {fieldLabel : name,name:field,allowBlank : false};
			var dataType = fieldInfo.dataType || 1;
			var fieldObj = null;
			switch(parseInt(dataType)){
				case 1 :/*字符串口 --> 文本框*/
					fieldObj = FormUtil.getTxtField(cfg);
					break;
				case 2 :/*日期 --> 日期控件*/
					fieldObj = FormUtil.getDateField(cfg);
					break;
				case 3 :/*金额 --> 金额框*/
					fieldObj = FormUtil.getMoneyField(cfg);
					break;
				case 4 :/*整数 --> 整数文本框*/
					fieldObj = FormUtil.getIntegerField(cfg);
					break;
			}
			if(!fieldObj) continue;
			fields[fields.length] = fieldObj;
		}
		this.paramsFrm.add(fields);
		this.paramsFrm.doLayout();
	}
};
