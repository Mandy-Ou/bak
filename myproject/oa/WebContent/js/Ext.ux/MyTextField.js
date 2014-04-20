/**
 * 封装 Ext TextField 类
 * 使期支持右边加 label 提示
 */
Ext.namespace("Ext.ux.form");
  
Ext.ux.form.MyTextField = Ext.extend(Ext.form.TextField,{
	fieldWidth : null,
 	unitText : '',
 	unitStyle : null,	/*单位样式*/
 	vtype : null,//email age mobile 等验证  详细信息请看Ext.form.MyVTypes.js 
 	unitEl : null,
 	regex : null,
	onRender : function(ct, position) {
		  Ext.ux.form.MyTextField.superclass.onRender.call(this, ct, position);
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
		   if(this.fieldWidth && this.fieldWidth >0){
		   		 this.width = this.fieldWidth;
		   }else{
		   		this.width = this.width - (this.unitText.replace(/[^\x00-\xff]/g, "xx").length * 6 + 2);
		   }
		   // 同时修改错误提示图标的位置
		   this.alignErrorIcon = function() {
		    this.errorIcon.alignTo(this.unitEl, 'tl-tr', [2, 0]);
		   };
		  }
	},
	/**
	 * 计算字符串长度，1个中文=2个字符长度
	 * @param {} val
	 * @return {}
	 */
	fucCheckLength : function(val){
		var i, sum;sum = 0;
		 for (i = 0; i < val.length; i++) {
		  if ((val.charCodeAt(i) >= 0) && (val.charCodeAt(i) <= 255)){
		  	sum = sum + 1;
		  }else{
		   	sum = sum + 2;
		  }
		 }
		 return sum;
	},
	/**
	 * 重写验证涵数 使其支持中文长度验证
	 * @param {} value
	 * @return {Boolean}
	 */  
    validateValue : function(value){  
      if(this.allowBlank == false){ //不允许为空  
      	if(value == null || value == ''){  
            this.markInvalid(String.format(this.blankText,value));  
            return false;  
         }  
       }
       //email age mobile 等验证  详细信息请看Ext.form.MyVTypes.js 
       if(value){
       	if(this.vtype){
            var vt = Ext.form.VTypes;
            if(!vt[this.vtype](value, this)){
                this.markInvalid(this.vtypeText || vt[this.vtype +'Text']);
                return false;
            }
        }
       }
       
       var maxLen = this.maxLength;  
       var maxLenText = this.maxLengthText;  
       if(maxLenText.indexOf('{0}') != -1){  
          if(maxLen != null && maxLen != 'undefined' && maxLen > 0){   
             var regex = /[^\x00-\xff]/g;  //中文正则  
          	 var len ;  
             if('string' == Ext.type(value)){  
                  //将中文替换成2位字符  
                 // len = value.replace(regex,'**').length; 
             	 len = this.fucCheckLength(value);
              }else{  
                  len = value.length;  
              }
              
             var label = this.fieldLabel;  
             if(label != null && label != 'undefined'){  
                   //去掉fieldLabel中生成的不必要字符  
                  if(label.indexOf('</') != -1 ){  
                     label = label.substring(label.lastIndexOf('>')+1, label.length);  
                  }  
                  if(len > maxLen){  
                       //验证未通过,并设置提示信息  
                      this.markInvalid(String.format(label+'长度不能大于'+maxLen+'位!(中文占2位)'));  
                      return false;  
                   }  
                   return true;  
               }  
           }  
        }else{  
              var len = value.length;  
              if(len > maxLen){  
                   this.markInvalid(String.format(maxLenText ,value));  
                   return false;  
              }  
            return true;  
        }  
    }
});
