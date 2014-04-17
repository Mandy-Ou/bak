/**
 * 处理在IE9 上的BUG: 
 *  SCRIPT438: 对象不支持“createContextualFragment”属性或方法  
 */
if ((typeof Range !== "undefined") && !Range.prototype.createContextualFragment) {  
          Range.prototype.createContextualFragment = function (html) {  
          var frag = document.createDocumentFragment(),  
          div = document.createElement("div");  
          frag.appendChild(div);  
          div.outerHTML = html;  
          return frag;  
      };  
  }
  
Ext.override(Ext.form.Field, {
	setAllowBlank : function(allowBlank){
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
   setFieldLabel: function(text) {
    if (this.rendered) {
      this.el.up('.x-form-item', 10, true).child('.x-form-item-label').update(text);
    } else {
      this.fieldLabel = text;
    }
  }
});