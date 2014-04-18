/**
 * 封装 Ext FormPanel 
 * 返回一个有扩展功能的 AppForm 
 *  xtype : appform
 */
Ext.namespace("Ext.ux.form");

Ext.ux.form.AppForm = Ext.extend(Ext.FormPanel,{
	frame : true,
	labelAlign : 'right',
	labelWidth : 80,
	border : false,
	buttonAlign : 'center',
	formDiyCfg : null,/*加载自定义字段所用*/
	initComponent : function(){
		this.init();
		Ext.ux.form.AppForm.superclass.initComponent.call(this);
	 },
	/**
	 * 初始化方法
	 * */
	 init : function(){
	 	this.listeners = {
	 		render : this.loadFormDiyFields
	 	};
	},
	/**
	 * 为表单元素赋值	
	 * 例: appForm.setFormValues('./sysMenu_list.action',	// --> 服务器请求数据时的 URL 地址 //
	 *     { params:{id:'1'},	// --> 服务器请求数据时，后台业务需要的参数值 //
	 *       sfn : function(jsonObj){xxcode ...}, // --> 处理向服务器请求数据成功后要执行的函数 "jsonObj" 服务器返回的 json 对象数据 //
	 * 	     ffn : function(errMsg){xxcode...},	// --> 处理向服务器请求数据失败后要执行的函数 "errMsg" 服务器返回的错误信息参数 //
	 * 	     defaultVal : {name : '企业客户管理',parentName : '客户管理'} // --> 为表单要赋的默认值 json 对象 [可选参数] //
	 *     }
	 * );
	 * @param url 要请求数据的 url 
	 * @param cfg 表单附加的参数对象 
	 */
	setValues : function(url,cfg){
		var self = this;
		var action = {};
		var result = (cfg && cfg["params"]);
		if (result) {
			action.params = cfg.params;
		}
		//--> 当服务器成功返回数据后的回调函数，在回调函数中为表单赋值
		action.sfn = function(json_data){
			self.reset();
			var frm = self.getForm();
			self.setVs(json_data);
			if(cfg && cfg.defaultVal) self.setVs(cfg.defaultVal);
			if(cfg && cfg.sfn) cfg.sfn(json_data); 
			FormDiyManager.setFormDiyFieldValues(self);// 这里是加载自定义表单的数据
		};
		//如果配有失败后处理函数，当数据处理失败后。将把错误信息反馈给错误处理函数
		if(cfg && cfg.ffn){	
			action.ffn = function(msg){ if(cfg && cfg.ffn)cfg.ffn(msg); };
		}
		if(url) EventManager.get(url,action);
	},
	/**
	 * 专门供 setValues 方法调用 与 setFieldValues方法区别是，此方法少了  if(!json_data.hasOwnProperty(name)) continue; 判断
	 * @param {} json_data
	 */
	setVs : function(json_data){
			var form = this.getForm();
			var fields = form.items;
			for(var i=0,count=fields.getCount(); i<count; i++){
				var field = fields.itemAt(i);
				var name = field.getName();
				if(!name) continue;
				var xtype = field.getXType();
				var val = json_data[name];
				if(xtype){
					if((xtype == 'mycompositefield' || xtype == 'myuploadfield') && field.itemNames){
						var nameArr = field.itemNames.split(",");
						val = {};
						for(var j=0,len = nameArr.length; j<len; j++){
							var itemName = nameArr[j];
							val[itemName] = json_data[itemName];
						}
					}
				}
				if(!val && val != "0"){
					field.reset();
				}else{
					field.setValue(val);
				}
				field.clearInvalid();//清楚tip 提示
			}
	},
	/**
	 * 为表单中所有 Field 对象赋值
	 * @param {} json_data	要赋值的 JSON 对象数据 
	 */
	setFieldValues : function(json_data){
		var form = this.getForm();
		var fields = form.items;
		for(var i=0,count=fields.getCount(); i<count; i++){
			var field = fields.itemAt(i);
			var name = field.getName();
			if(!name) continue;
			if(!json_data.hasOwnProperty(name)){
				field.reset();
				continue;
			}
			var xtype = field.getXType();
			var val = json_data[name];
			this.setVal2Item(field,val,json_data);
//			if(xtype){
//				if((xtype == 'mycompositefield' || xtype == 'myuploadfield') && field.itemNames){
//					var nameArr = field.itemNames.split(",");
//					val = {};
//					for(var j=0,len = nameArr.length; j<len; j++){
//						var itemName = nameArr[j];
//						val[itemName] = json_data[itemName];
//					}
//				}
//			}
//			field.setValue(val);
		}
	},
	/**
	 * 为表单字段赋值
	 * @param {} field
	 * @param {} val
	 */
	setVal2Item : function(field,val,json_data){
		var xtype = field.getXType();
		if(xtype){
			if((xtype == 'mycompositefield' || xtype == 'myuploadfield') && field.itemNames){
				var nameArr = field.itemNames.split(",");
				val = {};
				for(var j=0,len = nameArr.length; j<len; j++){
					var itemName = nameArr[j];
					val[itemName] = json_data[itemName];
				}
			}
		}
		field.setValue(val);
	},
	/**
	 * 根据json_data 的key 为表单元素赋值
	 * @param {} json_data	要赋值的 json 数据(注：json_data 中的 key 必须是表单字段中的 name 值，才能赋值成功)
	 */
	setJsonVals : function(json_data){
		var form = this.getForm();
		var fields = form.items;
		for(var key in json_data){
			var field = null;
			for(var i=0,count=fields.getCount(); i<count; i++){
				var _field = fields.itemAt(i);
				var name = _field.getName();
				if(!name) continue;
				if(key == name){
					field = _field;
					break;
				}
			}
			
			if(field){
				var val = json_data[key];
				this.setVal2Item(field, val,json_data);
			}
		}
	},
	/**
	 * 设置表单元素值
	 * @param name 表单元素项的名称
	 * @param value	要赋给元素项的值
	 */
	setFieldValue : function(name,value){
		var field = this.findFieldByName(name);
		if(!field) return;
		field.setValue(value);
	},
	/**
	 * 获取表单元素的所有值。
	 * 此方法调用的是元素的 getValue() 方法来取值。
	 * @return {} 以 json 对象返回表单所有元素
	 */
	getValues : function(){
		var form = this.getForm();
		
		var fields = form.items;
		var values = {};
		for(var i=0,count=fields.getCount(); i<count; i++){
			var field = fields.itemAt(i);
			var name = field.getName();
			var val = field.getValue();
			if(!name) continue;
			var xtype = field.getXType();
			if(xtype){
				if(xtype == 'mydatefield' || xtype == 'superDateField'){
					val = field.getValueAsStr();
				}else if(xtype == 'mycompositefield' || xtype == 'myuploadfield'){
					val = field.getValue();
					var sigins = field.sigins;
					if(!sigins && field.items){//如果此属性没有值，则以 json 对象形式返回
						Ext.apply(values,val);
						continue;
					}
					
				}
			}
			values[name] = val;
		}
		return values;
	},
	/**
	 * 重置表单
	 * @param names 不需要重置的 Field 名称，多个字段用","隔开,例如："name,age,sex"
	 */
	reset : function(names){
		var val_fields = null;
		var values = [];
		if(names){
			val_fields = this.findFieldsByName(names);
			for(var i=0,count=val_fields.length; i<count; i++){
				values[values.length] = val_fields[i].getValue();
			}
		}
		this.getForm().reset();
		if(val_fields && val_fields.length > 0){
			for(var i=0,count=val_fields.length; i<count; i++){
				val_fields[i].setValue(values[i] || '');
			}
		}
	},
	/**
	 * 根据表单多个元素名获取该值，并以 json 对象返回
	 * 例如：
	 *    var objVal = this.appFrm.getValuesByNames('cat,rat,mat');
	 *    	则 objVal 值是以下格式 : 
	 * @param {} names
	 */
	getValuesByNames : function(names){
		var fields = this.findFieldsByName(names);
		if(null == fields || fields.length == 0) return null;
		var objVal = {};
		for(var i=0,count=fields.length; i<count; i++){
			var field = fields[i];
			var name = field['name'];
			var val = field.getValue() || '';
			objVal[name] = val;
		}
		return objVal;
	},
	/**
	 * 根据表单元素名获取对应值
	 * @param {} name	元素名称
	 * @return {String}	元素值
	 */
	getValueByName : function(name){
		var field = this.findFieldByName(name);
		if(!field) return "";
		var val = field.getValue();
		var val = (!val && val !==0 ) ? "" : val; 
		return val;
	},
	/**
	 * 根据字段的 name 值，查找 Field 对象
	 * @param name  要查找 Field 名称;例如："name"
	 * @returns 返回 Field 对象
	 */
	findFieldByName : function(name){
		var fields = this.findFieldsByName(name);
		if(!fields || fields.length == 0) return null;
		return fields[0];
	},
	/**
	 * 根据字段的 name 值，查找相应的字段对象数组。以数组形式返回
	 * @param names  要查找 Field 名称，多个字段用","隔开,例如："name,age,sex"
	 * @returns 返回 Field 对象数组
	 */
	findFieldsByName : function(names){
		if(!names || names.length == 0) return null;
		var formFields = [];
		names = names.split(",");
		var len = names.length;
		var form = this.getForm();
		var fields = form.items;
		for(var i=0,count=fields.getCount(); i<count; i++){
			var field = fields.itemAt(i);
			for(var j=0; j<len; j++){
				if(names[j] == field["name"]){
					formFields[formFields.length] = field;
					break;
				}
			}
		}
		return formFields;
	},
	isValid : function(){
		return this.getForm().isValid();
	},
	/**
	 * 得到form面板中的宽度和高度
	 * @param {} formPanel
	 */
	getResize : function(){
		var resize = [];
		resize[0] = this.getWidth();
		resize[1] = this.getHeight();
		return resize;
	},
	/**
	 * 加载个性化表单字段数据
	 * @param formPanel 表单对象
	 */
	loadFormDiyFields : function(formPanel){
		FormDiyManager.addFormFieldCfg(formPanel);
	},
	/**
	 * 设置字段必填属性
	 * @param 字段元素名,多个用","连接
	 */
	setAllowBlanks : function(names,allowBlank){
		var fields = this.findFieldsByName(names);
		for(var i=0,count=fields.length; i<count; i++){
			var field = fields[i];
			if(field["setAllowBlank"]){
				field.setAllowBlank(allowBlank);
			}
		}
	}
});

//注册成xtype以便能够延迟加载   
Ext.reg('appform',Ext.ux.form.AppForm);  