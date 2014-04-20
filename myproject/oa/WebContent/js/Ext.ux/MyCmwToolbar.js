/**
 * 封装 Ext ToolBar 支持传入不同类型
 * 返回一个带有组件的完整 ToolBar 控件
 */
Ext.namespace("Ext.ux.toolbar");

Ext.ux.toolbar.MyCmwToolbar = Ext.extend(Ext.Toolbar,{
	rightData : null,	/*权限控制数据*/
	controls : [],	//控件配置数组
	aligin : 'right',
	fields : [],//存放表单元素数组, 私有
	buttons : [],//--> 存放按钮数组 //
	initComponent : function(){
	 	Ext.ux.toolbar.MyCmwToolbar.superclass.initComponent.call(this);
	 	this.initTBarItems();
	 	this.initListeners();
	 },
	initListeners : function(){
		var _this = this;
		this.addListener("render",function(){
			_this.saveButtons();
			_this.controlRight();
		});
	},
	controlRight : function(){
		if(!this.buttons || this.buttons.length == 0) return;
		if(!this.rightData) return;
		if(!this.rightData.currNode) return;
		var attrs = this.rightData.currNode.attributes;
		if(!attrs) return;
		if(!CURRENT_ISADMIN || CURRENT_ISADMIN == 0){/*普通用户加权限过滤*/
			var modDatas = attrs.modDatas;
			if(!modDatas || modDatas.split(",").length == 0){
				for(var i=0,count=this.buttons.length; i<count; i++){
					this.buttons[i].show();
				}
			}else{
				modDatas = modDatas.split(",");
				var len = modDatas.length;
				for(var i=0,count=this.buttons.length; i<count; i++){
					var button = this.buttons[i];
					button.disable();//.hide();
					for(var j=0; j<len; j++){
						var token = button['token'];
						if(token == modDatas[j]){
							button.enable();
							//button.show();
							break;
						}
					}
				}
			}
		}
	},
	getHeight : function(){
		var _this = this;
		var height = Ext.ux.toolbar.MyCmwToolbar.superclass.getHeight.call(_this);
		return height;
	},
	/**
	 * 保存按钮到 ts_module 表方法
	 */
	saveButtons : function(){
		if(!this.buttons || this.buttons.length == 0) return;
		if(!this.rightData) return;
		if(!this.rightData.saveRights) return;
		if(!this.rightData.currNode) return;
		var menuId = this.rightData.currNode.id;
		if(!menuId) return;
		var moduleData = {menuId : menuId};
		var modules = [];
		for(var i=0,count=this.buttons.length; i<count; i++){
			var button = this.buttons[i];
			var text = button["text"];
			var token = button["token"];
			if(!token){
				ExtUtil.error({msg:'请为 按钮\"'+text+'\" 的  \"token\" 属性提供值!'});
				return;
			}
			var iconCls = button["iconCls"] || '';
			modules[i] = {name:token,iconCls:iconCls};
		}
		moduleData["modules"] = Ext.encode(modules);
		EventManager.get('./sysModule_batchsave.action',{params:moduleData,sfn:function(json_data){
			//alert('按钮插入成功!');
		}});
	},
	/**
	 * 获取 topBar 工具栏
	 * */
	initTBarItems : function(){
		//初始化 ToolBar 控件项
		if(!this.items ) {
			var _items = [];
			this.buttons = [];
			this.fields = [];
			if(this.aligin && this.aligin == 'right') _items[0] = '->';
			if(this.controls && this.controls.length>0){
				for(var i=0; i<this.controls.length; i++){
					var control = this.getTControl(this.controls[i]);
					_items[_items.length] = control;
				}
				this.add(_items);
			}
		}
	},
	/**
	 * 根据配置参数返回顶部工具栏上不同的控件
	 * @param {} cfg
	 */
	getTControl : function(cfg){
		var control = {};
		
		if(cfg){
			var type = cfg.type;
			var Colon = null;
			if(cfg.Colon){//是否加冒号
				Colon=cfg.Colon;
			}else{
				Colon=":";
			}
			switch(type){
				case 'label':{
					cfg.text = "&nbsp;&nbsp;&nbsp;&nbsp;"+cfg.text +Colon;
					control = new Ext.Toolbar.TextItem(cfg);
					break;
				}case 'search':{
					if(!cfg.width) cfg.width = 125;
					control = new Ext.ux.SearchField(cfg);
					this.fields[this.fields.length] = control;
					break;
				}case 'txt':{
					if(!cfg.width) cfg.width = 125;
					control = new Ext.form.TextField(cfg);
					this.fields[this.fields.length] = control;
					break;
				}case 'rad':{/*单选按钮*/
					if(!cfg.width) cfg.width = 125;
					control = FormUtil.getRadioGroup(cfg);
					this.fields[this.fields.length] = control;
					break;
				}case 'chk':{
					if(!cfg.width) cfg.width = 125;
					control = new Ext.form.Checkbox(cfg);
					this.fields[this.fields.length] = control;
					break;
				}case 'date':{
					if(!cfg.format) cfg.format = 'Y-m-d';
					if(!cfg.width) cfg.width = 125;
					//cfg.readOnly = true;
					control = FormUtil.getDateField(cfg);
					this.fields[this.fields.length] = control;
					break;
				}case 'lcbo':{/*本地下拉框*/
					control = FormUtil.getLCboField(cfg);
					this.fields[this.fields.length] = control;
					break;
				}case 'combo':{
					cfg.hiddenName = cfg.name;
					cfg.triggerAction = cfg.triggerAction || "all";
					cfg.mode = cfg.mode || "local";
					cfg.valueField = cfg.valueField || "id";
					cfg.displayField = cfg.displayField || "name";
					cfg.readOnly = true;
					control = new Ext.form.ComboBox(cfg);
					this.fields[this.fields.length] = control;
					break;
				}case 'sp' :{	//分割条
					control = new Ext.Toolbar.Separator();
					break;
				}case 'br': { //工具栏的换行
					control = 'br';
					break;
				}
				default :{
					control = new Ext.Button(cfg);
					this.buttons[this.buttons.length] = control;
					break;
				}
			}
		}
		return control;
	},
	/**
	 * 获取工作栏上的输入控件字段
	 * @returns {Array}
	 */
	getFields : function(){
		return this.fields;
	},
	/**
	 * 根据元素名获取值
	 * @param name	元素名 field.name
	 * @returns	返回该元素名的值
	 */
	getValue : function(name){
		var values = this.getValues();
		return values ? values[name] : '';
	},
	/**
	 * 获取 字段值，以 JSON 对象返回
	 * 例如：{name : "张三",age:24}
	 * @returns  JSON 对象值。
	 */
	getValues : function(){
//		var vals = {};
		if(null==this.fields || this.fields.length==0) return null;
//		var count=this.fields.length;
//		for(var i=0; i<count; i++){
//			var field = this.fields[i];
//			var val = field.getValue();
//			vals[field["name"]] = val;
////			alert(field["name"]+"="+val);
//		}
		var values = FormUtil.getValues(this.fields);
		return values;
	},
	/**
	 * 为工具栏上的表单字段赋值
	 */
	setValues : function(json_data){
		if(null==this.fields || this.fields.length==0) return null;
		FormUtil.setFieldValues(this.fields, json_data);
	},
	/**
	 * 根据元素名字重置字段
	 * @param name	元素名字
	 */
	reset : function(name){
		if(null==this.fields || this.fields.length==0) return;
		var count=this.fields.length;
		for(var i=0; i<count; i++){
			var field = this.fields[i];
			if(name==field.name){
				field.reset(); 
			}
		}
	},
	/**
	 * 重置工具栏上的所有输入元素
	 */
	resets : function(){
		if(null==this.fields || this.fields.length==0) return;
		var count=this.fields.length;
		for(var i=0; i<count; i++){
			var field = this.fields[i];
			field.reset();
		}
	},
	/**
	 * 获取工具栏上的所有按钮对象
	 * @return []	以数组形式返回所有按钮对象
	 */
	getButtons : function(){
		return this.buttons;
	},
	/**
	 * 根据提供的多个按钮文本获取相应的按钮对象
	 * 例： mytoolbar.getSomeButtons("上一条,下一条");
	 *    --> 将返回文本为 "上一条" 和 "下一条"的按钮对象
	 * @param {} btnTxts
	 * @return [] 以数组形式返回指定的按钮对象
	 */
	getSomeButtons : function(btnTxts){
		if(this.buttons.length == 0) return;
		var btnTxts = btnTxts.split(",");
		var len = btnTxts.length;
		var someBtns = [];
		for(var i=0,count=this.buttons.length; i<count; i++){
			var button = this.buttons[i];
			for(var j=0; j<len; j++){
				if(button.getText() == btnTxts[j]){
					someBtns[someBtns.length] = button;
					break;
				}
			}	
		}
		return someBtns;
	},
	/**
	 * 禁用指定的按钮
	 * @param {} btnTxts	按钮文本，多个用","隔开。如："新增,修改"
	 */
	disableButtons : function(btnTxts){
		this.disOrableBtns(true,btnTxts);
	},
	/**
	 * 启用指定的按钮
	 * @param {} btnTxts	按钮文本，多个用","隔开。如："新增,修改"
	 */
	enableButtons : function(btnTxts){
		this.disOrableBtns(false,btnTxts);
	},
	/**
	 * 隐藏指定的按钮
	 * 根据提供的多个按钮文本获取相应的按钮对象
	 * 例： hideButtons("上一条,下一条");
	 *    --> 则 "上一条" 和 "下一条"的按钮将会隐藏
	 * @param {} btnTxts 要隐藏的按钮的文本。如果有多个就以“，”隔开
	 */
	hideButtons : function(btnTxts){
		this.hideOrShowBtns(false,btnTxts)		
	},
	/**
	 * 显示指定的按钮
	 * 根据提供的多个按钮文本获取相应的按钮对象
	 * 例： showButtons("上一条,下一条");
	 *    --> 则 "上一条" 和 "下一条"的按钮将会显示
	 * @param {} btnTxts 要显示的按钮的文本。如果有多个就以“，”隔开
	 */
	showButtons : function(btnTxts){
		this.hideOrShowBtns(true,btnTxts)		
	},
	/**
	 * 隐藏或显示指定的按钮
	 * @param isVisible 是否可见 true : 指定的按钮显示， false : 按钮隐藏
	 * @param {} btnTxts  要显示或隐藏的按钮的文本。如果有多个就以“，”隔开
	 */
	hideOrShowBtns : function(isVisible,btnTxts){
		if(this.buttons.length == 0) return;
		var btnTxts = btnTxts.split(",");
		var len = btnTxts.length;
		for(var i=0,count=this.buttons.length; i<count; i++){
			var button = this.buttons[i];
			for(var j=0; j<len; j++){
				if(button.getText() == btnTxts[j]){
					button.setVisible(isVisible);
					break;
				}
			}	
		}
	},
	/**
	 * 禁用或启用指定的按钮
	 * @param isenable 是否可见 false : 指定的按钮可用， true : 按钮禁用
	 * @param {} btnTxts  要显示或隐藏的按钮的文本。如果有多个就以“，”隔开
	 */
	disOrableBtns : function(isenable,btnTxts){
		if(this.buttons.length == 0) return;
		var btnTxts = btnTxts.split(",");
		var len = btnTxts.length;
		for(var i=0,count=this.buttons.length; i<count; i++){
			var button = this.buttons[i];
			for(var j=0; j<len; j++){
				if(button.getText() == btnTxts[j]){
					button.setDisabled(isenable);
					break;
				}
			}	
		}
	}
});