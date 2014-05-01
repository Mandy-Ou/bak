/**
 * 封装 Ext TextField 类
 * 使期支持右边加 label 提示
 */
Ext.namespace("Ext.ux.form");

Ext.ux.form.MyDateField = Ext.extend(Ext.form.DateField,{
	getDateField : false,
	/**
	 * 获取格式化后的日期字符串
	 * @return {}
	 */
	getValueAsStr : function(){
		var dateVal = Ext.ux.form.MyDateField.superclass.getValue.call(this);
		if(dateVal && Ext.isDate(dateVal)){
			dateVal = dateVal.format(this.format);
		}
		return dateVal;
	}
});
//注册成xtype以便能够延迟加载   
Ext.reg('mydatefield', Ext.ux.form.MyDateField);