Ext.ns("Ext.ux.form");
/**
*@类Ext.ux.form.ElasticTextArea
 *弹性textarea的大小调整内容的高度。
 *测试，发现工作精细/ ExtJS的3.2.1（ff3.6，IE7+8，铁（铬）4）
 *
 *渲染，创建一个div在身体之外。
 * div的内容得到更新的文本域KEYUP，然后textarea的被调整到div的大小
 *
 *额外的配置选项
 * height（INT）：textarea的最低高度。设置为0以禁用，默认为50
 * maxHeight（INT）：textarea的最大高度。设置为0以禁用，默认为200
 * addHeight（INT）：附加文字高度18像素
 */
Ext.ux.form.ElasticTextArea = Ext.extend(Ext.form.TextArea, {
   	height        : 50 ,
    maxHeight        : 100 ,
    addHeight        : 10,
    enableKeyEvents : true,
    lastTextHeight    : -1,
    elasticize : function() {
        var el = this.getEl();
        //clean up text area contents from html specialchars and replace newlines by <br>'s
        this.div.update( 
            el.dom.value.replace(/<br \/>&nbsp;/, '<br />')
                .replace(/<|>/g, '&lt;')
                .replace(/&/g,"&amp;")
                .replace(/\n/g, '<br />&nbsp;') 
        );
        // get the div height
        var textHeight = this.div.getHeight();
        if (textHeight == this.lastTextHeight) return;
        this.lastTextHeight = textHeight;

        //enforce text area maximum and minimum size
        if ( (textHeight > this.maxHeight ) && (this.maxHeight > 0) ){
            textHeight = this.maxHeight ;
            el.setStyle('overflow', 'auto');
        } else {
            el.setStyle('overflow', 'hidden');
        };
		
        if ( (textHeight < this.height ) && (this.height > 0) ) {
            textHeight = this.height ;
        };

        //resize the text area
        el.setHeight(textHeight + this.addHeight , true);
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
    },

    listeners: {

        // be tidy, cleanup
        destroy: function() {
            if (this.div) this.div.remove();
        },

        // create div and install key listeners
        render: function() {
            var dh=Ext.DomHelper,
                el = this.getEl(),
                styles = el.getStyles('padding','padding-top', 'padding-bottom', 'padding-left', 'padding-right', 'line-height', 'font-size', 'font-family', 'font-weight', 'font-style'),
                w = el.getWidth();

            styles.width = w +'px' ;

            this.div = dh.append(Ext.getBody() || document.body, {
                'id':this.id + '-preview-div',
                'tag' : 'div',
                'style' : 'position: absolute; top: -100000px; left: -100000px; word-wrap: break-word;'
            }, true);

            //apply the text area styles to the hidden div
            dh.applyStyles(this.div, styles);

            //recalculate the div height on each key stroke
            el.on('keyup', this.elasticize, this);

            this.elasticize()
        }
    }
});  
Ext.reg('TextArea', Ext.ux.form.ElasticTextArea);