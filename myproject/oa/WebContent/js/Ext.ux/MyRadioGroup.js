/**
 * 封装 Ext RadioGroup 类 重写其 getValue 和 setValue 方法
 */
Ext.namespace("Ext.ux.form");

Ext.ux.form.MyRadioGroup = Ext.extend(Ext.form.RadioGroup,{
	register : null,	/*注册器对象 /注册器对象 参考 constant.js 中的常量定义对象 REGISTER */
	type : null,	/* 资源ID引用值 如果是从基础数据取数的话，此处是资源ID引用值  */
	items : [],
	initComponent : function(){
		this.init();
	},
	init : function(){
		if(this.register) this.loadItems();
	},
	/**
	 * 获取RadioGroup 选中的值方法
	 */
	getValue : function(){
		var chkVal = null;
		if(!this.items || this.items.length == 0) return null;
		if(!this.items.each){
			for(var i=0,count=this.items.length; i<count; i++){
				var item = this.items[i];
				 if(item.checked){
			   		chkVal = item.inputValue
			   		break;
			   }  
			}
		}else{
			this.items.each(function(item){
			   if(item.checked){
			   		chkVal = item.inputValue
			   		return;
			   }  
			});
		}
		return chkVal;
	},
	/**
	 * 获取选中的 Radio
	 * @return {}
	 */
	getCheckItem : function(){
	    var chkItem = null;
	    if(!this.items.each) return;
		this.items.each(function(item){
		   if(!chkItem && item.checked){
		   		chkItem = item;
		   		return ;
		   }  
		});
		return chkItem;
    },
	loadItems : function(){
		var _items = Cmw.getRadOrCheckDataSource(this.name,this.register,this.type);
		if(!_items || _items.length == 0){
			_items = [{boxLabel:'<span style="color:red;font-weight:bold;">没数据</span>',disabled:true}];
		}
		this.items = _items;
	},setAllowBlank : function(allowBlank){
		var fieldLabel = this['fieldLabel'];
		if(!allowBlank){
			if(fieldLabel && fieldLabel.indexOf(FormUtil.REQUIREDHTML) == -1){
				fieldLabel = FormUtil.REQUIREDHTML + fieldLabel;
			}
		}else{
			if(fieldLabel && fieldLabel.indexOf(FormUtil.REQUIREDHTML) != -1){
				fieldLabel = fieldLabel.replace(FormUtil.REQUIREDHTML,'');
			}
		}
		this.setFieldLabel(fieldLabel);
		this.allowBlank = allowBlank;
		this.validate();
    },
    setFieldLabel : function(text) {
	   if (this.rendered) {
         this.el.up('.x-form-item', 10, true).child('.x-form-item-label').update(text);
       } else {
          this.fieldLabel = text;
       }
  	}
});

//注册成xtype以便能够延迟加载   
Ext.reg('myradiogroup', Ext.ux.form.MyRadioGroup);  