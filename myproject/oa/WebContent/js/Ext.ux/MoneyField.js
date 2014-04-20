/**
 * 封装 Ext TextField 类
 * 使期支持千分位分隔符格式
 */
Ext.namespace("Ext.ux.form");

Ext.ux.form.MoneyField = Ext.extend(Ext.ux.form.MyTextField,{
 	unitText : '',
 	singin : ',',	/*--- 千分位分隔符，默认为“,” ---*/
 	decimalPrecision : 2,   //小数点位数 
 	allowNegative : false,  //是否可以输入负数   
    allowDecimals : true,   //是否可以输入小数点 
    autoBigAmount : false,	/*是否自动转换成大写人民币*/
 	initComponent : function(){
		Ext.ux.form.MoneyField.superclass.initComponent.call(this);
	 },
	 initEvents : function() {  
	     Ext.ux.form.MoneyField.superclass.initEvents.call(this);
	     this.initListeners();
	},
	initListeners : function(){
		var _this = this;
		this.addListener("change",function(field, newValue, oldValue){
			_this.setValue(newValue);
		});
	},
	/**
	 * 用千分位分隔符进行赋值
	 * @param {} v 要赋的值
	 */
	setValue : function(v){
		v = this.getNumVal(v);
		if(this.allowDecimals){
			var decimalPrecisions = [];
			if(this.decimalPrecision == 0) this.decimalPrecision = 2;
			for(var i=0; i<this.decimalPrecision; i++){
				decimalPrecisions[i] = '0';
			}
		}
		var fmt = '0'+this.singin+'000';
		fmt = (decimalPrecisions.length < 0) ? fmt : fmt+'.'+decimalPrecisions.join("");//带小数点后两位
		v = Ext.util.Format.number(v,fmt);
		this.convertBigAmount(v);
		Ext.ux.form.MoneyField.superclass.setValue.call(this, v);
	},
	/**
	 * 大写人民币金额转换
	 */
	convertBigAmount : function(val){
		if(!this.autoBigAmount) return;
		if(val && Ext.isString(val)){
			val = val.replaceAll(this.singin,"");
		}
		if(!val || val == 0){
			val = this.unitText;
		}else{
			val = Cmw.cmycurd(val);
		}
		if(this.unitEl){
			this.unitEl.dom.innerHTML = val;
		}
	},
	/**
	 * 获取值
	 * @return 返回TriggerField值
	 */
	getValue : function(){
		var val = Ext.ux.form.MoneyField.superclass.getValue.call(this);
		if(val){
			val = (val+"").replaceAll(this.singin,"");
			val = parseFloat(val);
		}
		return val;
	},
	/** 将指定的值转换成数字格式，如果其中有非数字则替换掉
	* @return 返回数字值
	**/
	getNumVal:function(val){
		if(!val) val = "0";
	 	//得到第一个字符是否为负号  
		if(Ext.isNumber(val)){
			val = val+"";
		}
  		var t = val.charAt(0);    
		//先把非数字的都替换掉，除了数字和.
		val = val.replace(/[^\d.]/g,"");
		//必须保证第一个为数字而不是.
		val = val.replace(/^\./g,"");
		//保证只有出现一个.而没有多个.
		val = val.replace(/\.{2,}/g,".");
		//保证.只出现一次，而不能出现两次以上
		val = val.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		 //如果第一位是负号，则允许添加  
        if(t == '-'){  
        	val = '-'+val;  
        }
        if(!this.allowNegative) val.replace('-',"");
		return val;
	},
	/**
	 * 只读设置
	 * @param {} readOnly
	 */
	setReadOnly : function(readOnly ){
		if(readOnly){
			this.addClass('x-readonly-field');
		}else{
			this.removeClass('x-readonly-field');
		}
		Ext.ux.form.MoneyField.superclass.setReadOnly.call(this,readOnly);
	}
});

//注册成xtype以便能够延迟加载   
Ext.reg('moneyfield', Ext.ux.form.MoneyField);  