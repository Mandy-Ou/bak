/**
 * 表单工具类
 * 
 * 未加入帮助文档的方法：
 * 	FormUtil.getCompositeField(cfg); 
 * 	FormUtil.getMoneyField(cfg);
 * 	FormUtil.getDoubleField(cfg);
 *  FormUtil.getRCboField(cfg);
 *  
 * @type 
 */
var FormUtil = {
	WIDTH : 125,
	ONE_WIDTH : 125,	/*---> 一行布局 宽度*/
	TWO_WIDTH : 380,	/*---> 两行布局 宽度*/ 
	THREE_WIDTH : 500,	/*---> 三行布局 宽度*/
	CMN_THREE : 'THREE',/*三列布局*/
	CMN_TWO : 'TWO',/*二列布局，第二列布局比重大*/
	CMN_TWO_LEFT : 'TWO_LEFT',/*二列布局，第一列布局比重大*/
	REMARK_HEIGHT : 80,
	REQUIREDHTML: '<span style="color:red">*</span>',
	STORE_FIELDS : ["id","name"],
	/**
	 * 根据配置参数获取一个 Hidden 对象
	 * @param {} cfg	Hidden 配置参数
	 * @return {}	返回一个 Hidden 对象
	 */
	getHidField : function(cfg){
		return new Ext.form.Hidden(cfg);
	},
	/**
	 * 根据配置参数获取一个 只读 TextField 对象
	 * @param {} cfg	TextField 配置参数
	 * @return {}	返回一个 TextField 对象
	 */
	getReadTxtField : function(cfg){
		if(!cfg.width) cfg.width = this.WIDTH;
		cfg.readOnly = true;
		cfg.fieldClass = 'x-readonly-field';
		return new Ext.ux.form.MyTextField(cfg);
	},
	
	/**
	 * 根据配置参数获取一个 TextField 对象
	 * @param {} cfg	TextField 配置参数
	 * @return {}	返回一个 TextField 对象
	 */
	getTxtField : function(cfg){
		if(!cfg.width) cfg.width = this.WIDTH;
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		return new Ext.ux.form.MyTextField(cfg);
//		return new Ext.form.TextField(cfg);
	},
	/**
	 * 根据配置参数获取一个图片选择控件
	 * @param {} cfg	TextField 配置参数
	 * @return {}	返回一个 TextField 对象
	 */
	getMyImgChooseField : function(cfg){
		if(!cfg.width) cfg.width = this.WIDTH;
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		return new Ext.ux.form.MyImgChooseField(cfg);
		//return new Ext.form.TextField(cfg);
	},
	/**
	 * 根据配置参数获取一个 IntegerField 对象
	 * @param {} cfg	IntegerField 配置参数
	 * @return {}	返回一个 IntegerField 对象
	 */
	getIntegerField : function(cfg){
		if(!cfg.width) cfg.width = this.WIDTH;
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		cfg.decimalPrecision = 0;
		cfg.fieldClass = 'x-form-num-field-right';
		return new Ext.ux.form.MyNumberField(cfg);
	},
	/**
	 * 根据配置参数获取一个 DoubleField 对象
	 * @param {} cfg	DoubleField 配置参数
	 * @return {}	返回一个 DoubleField 对象
	 */
	getDoubleField : function(cfg){
		if(!cfg.width) cfg.width = this.WIDTH;
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		if(cfg.hasOwnProperty("decimalPrecision"))cfg.decimalPrecision = 2;
		var clsArr = ['x-form-num-field-right'];
		if(cfg.hasOwnProperty("readOnly") && cfg.readOnly) clsArr[clsArr.length]= 'x-readonly-field';
		cfg.fieldClass = clsArr.join(" ");
		return new Ext.ux.form.MyNumberField(cfg);
	},
	/**
	 * 根据配置参数获取一个 MoneyField 对象
	 * @param {} cfg	MoneyField 配置参数
	 * @return {}	返回一个 MoneyField 对象
	 */
	getMoneyField : function(cfg){
		if(!cfg.width) cfg.width = this.WIDTH;
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		if(cfg.hasOwnProperty("decimalPrecision"))cfg.decimalPrecision = 2;
		if(cfg.hasOwnProperty("readOnly") && cfg.readOnly) cfg.fieldClass = 'x-readonly-field';
		cfg.fieldClass = 'x-form-num-field-right';
		return new Ext.ux.form.MoneyField(cfg);
	},
	/**
	 * 根据配置参数获取一个 HtmlEditorField (Ext自身的富文本编辑器) 对象
	 * @param {} cfg	MyEditorField 配置参数
	 * @return {}	返回一个 HtmlEditorField 对象
	 */
	getEditorField : function(cfg){
		if(!cfg.width) cfg.width = this.WIDTH;
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		return new Ext.ux.form.MyHtmlEditorField(cfg);
	},
	/**
	 * 根据配置参数获取一个 MyEditorField (百度富文本编辑器) 对象
	 * @param {} cfg	MyEditorField 配置参数
	 * @return {}	返回一个 MyEditorField 对象
	 */
	getMyEditorField : function(cfg){
		if(!cfg.width) cfg.width = this.WIDTH;
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		return new Ext.ux.form.MyEditorField(cfg);
	},
	/**
	 * 根据配置参数获取一个 MyCompositeField 对象
	 * 与 CompositeField 区别是: 
	 *  MyCompositeField 可以将其内部的Field值以逗号拼接返回，
	 *  且赋值时，用逗号分隔后给内部Field 逐个进行赋值
	 * @param {} cfg	MyCompositeField 配置参数
	 * @return {}	返回一个 MyCompositeField 对象
	 */
	getMyCompositeField : function(cfg){
		if(!cfg.width) cfg.width = this.WIDTH;
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		return new Ext.ux.form.MyCompositeField(cfg);
	},
	/**
	 * 根据配置参数获取一个 CompositeField 对象
	 * @param {} cfg	CompositeField 配置参数
	 * @return {}	返回一个 CompositeField 对象
	 */
	getCompositeField : function(cfg){
		if(!cfg.width) cfg.width = this.WIDTH;
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		return new Ext.form.CompositeField(cfg);
	},
	/**
	 * 根据配置参数获取一个 MyUploadField 对象
	 * @param {} cfg	MyUploadField 配置参数
	 * @return {}	返回一个 MyUploadField 对象
	 */
	getMyComboxTree : function(cfg){
		if(!cfg.width) cfg.width = this.WIDTH;
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		return new Ext.ux.tree.AppComboxTree(cfg);
	},
	/**
	 * 根据配置参数获取一个 AppComboxImg  对象
	 * @param {} cfg	AppComboxImg 配置参数
	 * @return {}	返回一个 AppComboxImg 对象
	 */
	getAppComboxImg : function(cfg){
		if(!cfg.width) cfg.width = this.WIDTH;
//		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		return new Ext.ux.form.AppComboxImg(cfg);
	},
	/**
	 * 根据配置参数获取一个 MyUploadField 对象
	 * @param {} cfg	MyUploadField 配置参数
	 * @return {}	返回一个 MyUploadField 对象
	 */
	getMyUploadField : function(cfg){
		if(!cfg.width) cfg.width = this.WIDTH;
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		return new Ext.ux.form.MyUploadField(cfg);
	},
	/**
	 * 根据配置参数获取一个 DateField 对象
	 * @param {} cfg	DateField 配置参数
	 * @return {}	返回一个 DateField 对象
	 */
	getDateField : function(cfg){
		if(!cfg.width) cfg.width = this.WIDTH;
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		if(!cfg.format)cfg.format = 'Y-m-d';
		if(cfg.isdeaulft){
		 cfg.value = CURENT_DATE();
		}
		if(cfg.format == 'Y' || cfg.format == 'm' || cfg.format == 'Y-m'){
			return new Ext.form.SuperDateField(cfg);
		}
		return new Ext.ux.form.MyDateField(cfg);
		//return new Ext.form.DateField(cfg);
	},
	/**
	 * 据配置参数获取一个 RadioGroup 对象
	 * @param {} cfg
	 */
	getRadioGroup : function(cfg){
		if(!cfg.width) cfg.width = this.WIDTH;
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		return new Ext.ux.form.MyRadioGroup(cfg);
	},
	/**
	 * 据配置参数获取一个 CheckboxGroup 对象
	 * @param {} cfg
	 */
	getCheckGroup : function(cfg){
		if(!cfg.width) cfg.width = this.WIDTH;
		if(cfg.name && cfg.items){
			for(var i=0,count=cfg.items.length; i<count; i++){
				var item = cfg.items[i];
				if(item.name) continue;
				item.name = cfg.name;
			}
		}
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		return new Ext.ux.form.MyCheckBoxGroup(cfg);
	},
	/**
	 * 获取本地加载数据的下拉框
	 * @param {} cfg
	 * @return {}
	 */
	getLCboField : function(cfg){
		if(!cfg.width) cfg.width = this.WIDTH;
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		if(!cfg.data){	
			ExtUtil.error({title:'创建下拉框错误',msg:'在创建本地下拉框时，必须提供 data 属性和数据！'});
			return;
		};
		var _store = FormUtil.getSimpleStore(cfg.data);
		cfg = Ext.applyIf(
			cfg,{store : _store,emptyText:'请选择',mode : 'local',triggerAction : 'all',hiddenName:cfg.name,editable: false,
		valueField : FormUtil.STORE_FIELDS[0],displayField : FormUtil.STORE_FIELDS[1]});
		return new Ext.form.ComboBox(cfg);
	},
	/**
	 * 获取数据的下拉框
	 * @param {} cfg
	 * @return {}
	 */
	getRCboField : function(cfg){
		if(!cfg.width) cfg.width = this.WIDTH;
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		cfg = Ext.applyIf(cfg,{emptyText:'请选择',triggerAction : 'all',hiddenName:cfg.name,valueField : FormUtil.STORE_FIELDS[0],displayField : FormUtil.STORE_FIELDS[1]});
		return new Ext.ux.form.RemoteComboBox(cfg);
	},
	/**
	 * 获取颜色的下拉框
	 * @param {} cfg
	 * @return {}
	 */
	getColorField : function(cfg){
		if(!cfg.width) cfg.width = this.WIDTH;
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		cfg = Ext.applyIf(cfg,{emptyText:'请选择',triggerAction : 'all',hiddenName:cfg.name,valueField : FormUtil.STORE_FIELDS[0],displayField : FormUtil.STORE_FIELDS[1],editable: false});
		return new Ext.ux.ColorField(cfg);
	},
	/**
	 * 根据配置参数获取一个 TextArea 对象
	 * @param {} cfg	TextArea 配置参数
	 * @return {}	返回一个 TextArea 对象
	 */
	getTAreaField : function(cfg){
		if(!cfg.height) cfg.height = this.REMARK_HEIGHT;
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		return new Ext.ux.form.ElasticTextArea (cfg);
	},
	/**
	 * 根据配置参数获取一个 FieldSet 对象
	 * @param {} cfg	FieldSet 配置参数
	 * @return {}	返回一个 FieldSet 对象
	 */
	getFieldSet : function(cfg){
		if(!cfg) cfg = {};
		Ext.applyIf(cfg,{collapsible:true,anchor:'98%',style:'padding:0px;margin:0px;padding-top:2px;padding-left:5px;margin-bottom:5px;'});
		return new Ext.form.FieldSet(cfg);
	},
	/**
	 * 禁用表单元素
	 * @param {} frm
	 */
	disabledFrm : function(frm){
		var _items = frm.items;
		if(_items){
			for(var i=0,count=_items.getCount(); i<count; i++){
				var field = _items.get(i);
				if(!field) continue;
				field.disable();
			}
		}
		_items = frm.buttons;
		if(_items){
			for(var i=0,count=_items.length; i<count; i++){
				var button = _items[i];
				if(!button) continue;
				button.disable();
			}
		}
	},
	
	/**
	 * 启用表单元素
	 * @param {} frm
	 */
	enableFrm : function(frm){
		var _items = frm.items;
		if(_items){
			for(var i=0,count=_items.getCount(); i<count; i++){
				var field = _items.get(i);
				if(!field) continue;
				field.enable();
				
			}
		}
		_items = frm.buttons;
		if(_items){
			for(var i=0,count=_items.length; i<count; i++){
				var button = _items[i];
				if(!button) continue;
				button.enable();
			}
		}
	},
	/**
	 * 创建Form表单
	 * @param cfg 表单配置参数
	 */
	createFrm : function(cfg){
//		cfg = Ext.apply({frame:true,labelAlign : 'right',labelWidth : 80,
//			border : false,buttonAlign : 'center'},cfg);
		return new Ext.ux.form.AppForm(cfg);
	},
	/**
	 * 创建一个布局好的Form表单
	 * @param cfg 表单配置参数
	 * @param fldGroups 待布局的字段数组
	 */
	createLayoutFrm : function(cfg,fldGroups){
		if(null == fldGroups || fldGroups.length == 0){
			ExtUtil.error({msg:'在调用 FormUtil.createLayoutFrm(cfg,fldGroups) 方法时，没有提供 fldGroups 参数！'});
			return new Ext.ux.form.AppForm(cfg);
		}
		var items = this.getLayoutItems(fldGroups);
		if(!cfg) cfg = {};
		Ext.apply(cfg,{items:items});
		return new Ext.ux.form.AppForm(cfg);
	},
	/**
	 * 创建一个FieldSet对象
	 * @param cfg FieldSet 配置参数
	 */
	createFieldSet : function(cfg){
		if(!cfg) cfg = {};
		Ext.applyIf(cfg,{collapsible:true,anchor:'98%',style:'padding:0px;margin:0px;padding-top:2px;padding-left:5px;margin-bottom:5px;'});
		return new Ext.form.FieldSet(cfg);
	},
	/**
	 * 创建一个布局好的FieldSet对象
	 * @param cfg FieldSet 配置参数
	 * @param fldGroups 待布局的字段数组
	 */
	createLayoutFieldSet : function(cfg,fldGroups){
		if(null == fldGroups || fldGroups.length == 0){
			ExtUtil.error({msg:'在调用 FormUtil.createLayoutFieldSet(cfg,fldGroups) 方法时，没有提供 fldGroups 参数！'});
			return new Ext.form.FieldSet(cfg);
		}
		var items = this.getLayoutItems(fldGroups);
		if(!cfg) cfg = {};
		Ext.apply(cfg,{collapsible:true,anchor:'98%',style:'padding:0px;margin:0px;padding-top:2px;padding-left:5px;margin-bottom:5px;',items:items});
		return new Ext.form.FieldSet(cfg);
	},
	/**
	 * 获取布局 Items 数组对象
	 */
	getLayoutItems : function(fldGroups){
		var isThreeCmn = this.checkThreeCmns(fldGroups);
		var items = [];
		if(isThreeCmn){	//存在三行布局的情况
			for(var i=0,count=fldGroups.length; i<count; i++){
				var layoutGp = fldGroups[i];
				//--> 一行布局情况  <--//
				if(!layoutGp.cmns){
					items[items.length] = layoutGp;
					continue;
				}
				var cmns = layoutGp.cmns;
				var fields = layoutGp.fields;
				var layoutFlds = null;
				//--> 三行布局情况  <--//
				if(cmns == this.CMN_THREE){
					layoutFlds = this.getThreeLayout(fields);
				}else if(cmns == this.CMN_TWO){
					layoutFlds = this.getTwoLayout(fields,{first:.35,second:.65});
				}else if(cmns == this.CMN_TWO_LEFT){
					layoutFlds = this.getTwoLayout(fields,{first:.65,second:.35});
				}
				if(layoutGp.title){	/*-- 加标题 --*/
					Ext.apply(layoutFlds,{title:layoutGp.title});
				}
				//--> 两行布局情况  <--//
				items[items.length] = layoutFlds;
			}
		}else{//两行或一行布局的情况
			for(var i=0,count=fldGroups.length; i<count; i++){
				var layoutGp = fldGroups[i];
				//--> 一行布局情况  <--//
				if(!layoutGp.cmns){
					items[items.length] = layoutGp;
					continue;
				}
				var cmns = layoutGp.cmns;
				var fields = layoutGp.fields;
				var layoutFlds = null;
				if(cmns == this.CMN_TWO){
					layoutFlds = this.getTwoLayout(fields);
				}
				if(layoutGp.title){	/*-- 加标题 --*/
					Ext.apply(layoutFlds,{title:layoutGp.title,padding:4});
				}
				//--> 两行布局情况  <--//
				items[items.length] = layoutFlds;
			}	
		}
		return items;
	},
	/**
	 * 获取两列布局
	 * @param {} fields 要布局的字段
	 * @param {} cmnCfg	列宽配置
	 */
	getTwoLayout : function(fields,cmnCfg){
		if(!cmnCfg) cmnCfg={first:.5,second:.5};
		var count=fields.length;
		var ycount = count % 2;
		var xcount = count-ycount;
		var cmn1 = [];
		var cmn2 = [];
		for(var i=0; i<xcount; i++){
			var firstField = fields[i];
			var secondField = fields[++i];
			if(cmnCfg.first>cmnCfg.second){
				Ext.apply(firstField,{width:FormUtil.TWO_WIDTH});
			}else if(cmnCfg.first<cmnCfg.second){
				Ext.apply(secondField,{width:FormUtil.TWO_WIDTH});
			}
			cmn1[cmn1.length] = firstField;
			cmn2[cmn2.length] = secondField;
		}
		if(ycount>0){
			cmn1[cmn1.length] = fields[xcount];
		}
		var layout = {layout : 'column',
			items : [{columnWidth : cmnCfg.first,layout : 'form',items : cmn1},
				{columnWidth : cmnCfg.second,layout : 'form',items : cmn2}]
			};
		return layout;
	},
	/**
	 * 获取三列布局的对象
	 * @field 要进行布局的字段
	 * @return {}	返回三列布局JSON对象
	 */
	getThreeLayout:function(fields){
		var count=fields.length;
		var ycount = count % 3;
		var xcount = count-ycount;
		var cmn1 = [];
		var cmn2 = [];
		var cmn3 = [];
		for(var i=0; i<xcount; i++){
			cmn1[cmn1.length] = fields[i];
			cmn2[cmn2.length] = fields[++i];
			cmn3[cmn3.length] = fields[++i];
		}
		if(ycount>0){
			cmn1[cmn1.length] = fields[xcount];
			if(ycount==2){
			  cmn2[cmn2.length] = fields[++xcount];
			}
		}
		var layout = {layout : 'column',border:false,
			items : [{columnWidth : .33,layout : 'form',items : cmn1},
				{columnWidth : .33,layout : 'form',items : cmn2	},
				{columnWidth : .33,layout : 'form',items : cmn3}]
			};
		return layout;
	},
	/**
	 * 检查是否是三列布局
	 * @param fldGroups 要检查的字段数组
	 * @return 返回 boolean 值。 true : 三列布局，false : 不是三列布局
	 */
	checkThreeCmns:function(fldGroups){
		for(var i=0,count=fldGroups.length; i<count; i++){
			var layoutGp = fldGroups[i];
			if(layoutGp.cmns && layoutGp.cmns == this.CMN_THREE) return true;
		}
		return false;
	},
	/**
	 * 获取一个column 布局面板对象
	 * 例如：要获取一个三列布局的面板
	 * 	var firstPanel = new Ext.Panel({title:'第一列面板'});
	 *  var twoPanel = new Ext.Panel({title:'第二列面板'});
	 *  var threePanel = new Ext.Panel({title:'第三列面板'});
	 *   var threeCmnPanel = FormUtil.getLayoutPanel([.33,.33,.33],[firstPanel,twoPanel,threePanel]);
	 *   
	 * @param {} cwArr 布局宽度数组
	 * @param {} items 元素项数组
	 */
	getLayoutPanel : function(cwArr,items){
		if(!cwArr || cwArr.length==0) return;
		if(!items || items.length==0) return;
		var _items = [];
		for(var i=0,count=cwArr.length; i<count; i++){
			_items[i] = {columnWidth:cwArr[i],layout : 'form',items : items[i]};
		}
		var layout = {layout : 'column',items : _items};
		return new Ext.Panel(layout);
	},
	/**
	 * 根据指定的 URL 获取下拉框 Store 对象
	 * @param {} url URL
	 * @return {} 返回下拉框的 Store 对象
	 */
	getComboStore : function(url){
		return new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({url : url}),
					reader:new Ext.data.JsonReader(
					{
						root : 'list',
						totalProperty : 'totalSize'
					},[{name : 'id',mapping : 'id'},{name : 'name',mapping : 'name'}])
				});
	},
	/**
	 * 以指定的二维数组创建 SimpleStore对象
	 * @param {} arrVal 二维数组值
	 */
	getSimpleStore : function(_data){
		var store = new Ext.data.SimpleStore({
			fields : FormUtil.STORE_FIELDS,
			data : _data
		});
		return store;
	},
	/**
	 * 给指定的Ext 元素赋值
	 * @param {} eleIds 元素ID字符串，多个以“，”逗号分隔
	 * @param {} arrVal 数组值，用来赋给指定的元素
	 */
	setValues : function(eleIds,arrVal){
		eleIds = eleIds.split(",");
		for(var i=0,count=eleIds.length; i<count; i++){
			var val = arrVal[i];
			if(val){
				var obj = Ext.getCmp(eleIds[i]);
				obj.reset();
				obj.setValue(val);
			}
		}
	},
	/**
	 * 获取比较符号下拉框
	 * 例：FormUtil.getEqOpLCbox(cfg);
	 * 如果没传 cfg 参数。
	 *  则默认为: {
	 *	    fieldLabel: '比较符号',
	 *	    name : 'eqop',
	 *	    width : 50,
	 *	    readOnly : true,
	 *	    fieldClass : 'x-readonly-field',
	 *	    data: datas
	 *	}
	 */
	getEqOpLCbox : function(cfg){
		if(!cfg) cfg = {};
		Ext.applyIf(cfg,{
		    fieldLabel: '比较符号',
		    name : 'eqop',
		    width : 70,
		    data: Lcbo_dataSource.eqOp_datas,
		    value : '='
		});
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		var cbo_eqOp = this.getLCboField(cfg);
		return cbo_eqOp;
	},
	/**
	 * 获取日期范围组控件
	 * FormUtil.getDateFieldGroup({fieldLabel:'制单日期',name1:'startDate1',name2:'endDate1'});
	 * @param {} cfg {fieldLabel：必须,name1:可选||"startDate",name2:可选||"endDate",width:可选||200}
	 */
	getDateFieldGroup : function(cfg){
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = this.REQUIREDHTML + cfg.fieldLabel;
		var name1 = cfg.name1 || 'startDate';
		var name2 = cfg.name2 || 'endDate';
		var width = cfg.width || 225;
		var separater = cfg.separater || '<span style="font-weight:bold;">--</span>';
		var txt_startDate = FormUtil.getDateField({name:name1,width:90/*,isdeaulft:true*/});
		var txt_endDate = FormUtil.getDateField({name:name2,width:90/*,value : dt*/});
		var comp_dateGroup = FormUtil.getMyCompositeField({
			itemNames : name1+','+name2,
			sigins : null,
			 fieldLabel: cfg.fieldLabel,width:width,sigins:null,
			 name:Ext.id(null,'comp_'),
			 items : [txt_startDate,{xtype:'displayfield',value:separater},txt_endDate]
		});
		return comp_dateGroup;
	},
	/**
	 * 获取表单字段数组的值，并以JSON 对象的形式返回
	 * @param {} fields	要获取值的表单字段数组
	 * @return {}	返回 JSON对象值
	 */
	getValues : function(fields){
		var values = {};
		for(var i=0,count=fields.length; i<count; i++){
			var field = fields[i];
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
					if(!sigins && field.itemNames){//如果此属性没有值，则以 json 对象形式返回
						Ext.apply(values,val);
						continue;
					}
					
				}
			}
			values[name] = val;
		}
		return values;
	}
}