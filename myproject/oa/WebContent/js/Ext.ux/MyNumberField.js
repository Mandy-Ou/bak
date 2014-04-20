/**
 * 封装 Ext NumberField 类
 * 使期支持右边加 label 提示
 */
Ext.namespace("Ext.ux.form");

Ext.ux.form.MyNumberField = Ext.extend(Ext.form.NumberField,{
 	unitText : '',
 	unitStyle : null,	/*单位样式*/
 	unitEl : null,
	onRender : function(ct, position) {
		  Ext.ux.form.MyNumberField.superclass.onRender.call(this, ct, position);
		  if (this.unitText != '') {
		  	var unitCfg = {
		      tag : 'div',
		      html : this.unitText
		     // style : 'color:red;font-weight:bold;'
		     };
		     if(this.unitStyle) unitCfg.style = this.unitStyle;//'color:red';
		   this.unitEl = ct.createChild(unitCfg);
		   this.unitEl.addClass('x-form-unit');
		   // 增加单位名称的同时 按单位名称大小减少文本框的长度 初步考虑了中英文混排 未考虑为负的情况
		   this.width = this.width - (this.unitText.replace(/[^\x00-\xff]/g, "xx").length * 3 + 2);
		   // 同时修改错误提示图标的位置
		   this.alignErrorIcon = function() {
		    this.errorIcon.alignTo(this.unitEl, 'tl-tr', [2, 0]);
		   };
		  }
	}
});
//注册成xtype以便能够延迟加载   
Ext.reg('mynumberfield', Ext.ux.form.MyNumberField);  
