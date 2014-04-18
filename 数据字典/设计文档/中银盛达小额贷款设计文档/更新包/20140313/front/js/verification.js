/**
* JS 文件校验类
**/
var V={
	ipnums : function(){
	  var evt = window.event || e;
	  var obj = evt.target||evt.srcElement;
      //得到第一个字符是否为负号  
      var t = obj.value.charAt(0);    
      //先把非数字的都替换掉，除了数字和.   
      obj.value = obj.value.replace(/[^\d\.]/g,'');     
       //必须保证第一个为数字而不是.     
       obj.value = obj.value.replace(/^\./g,'');     
       //保证只有出现一个.而没有多个.     
       obj.value = obj.value.replace(/\.{2,}/g,'.');     
       //保证.只出现一次，而不能出现两次以上     
       obj.value = obj.value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');  
       //如果第一位是负号，则允许添加  
       if(t == '-'){  
         obj.value = '-'+obj.value;  
       }  
	}
}