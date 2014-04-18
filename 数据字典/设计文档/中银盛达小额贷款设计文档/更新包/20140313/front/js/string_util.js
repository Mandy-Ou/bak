/**
 * JS 字符处理类扩展函数 
 */
 
 /**
 * String 替换所有指定字符串函数
 * @param {} s1	要替换的字符串
 * @param {} s2	新的字符串
 * @return {}	返回替换后的新字符串
 */
String.prototype.replaceAll = function(s1,s2){
	var r = new RegExp(s1.replace(/([\(\)\[\]\{\}\^\$\+\-\*\?\.\"\'\|\/\\])/g,"\\$1"),"ig");
	return this.replace(r,s2);
}

/**
 * 字符处理类
 * @type 
 */
var StringHandler = {
	/**
	 * 验证指定的值是否存在
	 * 如果传入的值为0,该方法将返回 true (验证通过);反之，失败 
	 * example : StringHandler.isValidVal("0") ---> return true;
	 * @param {} val 要验证的值 
	 * @return {} 返回 Boolean 值  true : 验证通过, false : 不通过
	 */
	isValidVal : function(val){
		var flag = (!val && val !==0 ) ? false : true;
		return flag;
	},
	/**
	 * StringHandler.forDight(15.989,2)
	 * 四舍五入
	 * @param {} dight 要四舍五入的数字
	 * @param {} how	保留的小数位数
	 * @return {} 
	 */
	forDight : function(dight,how){    
	   dight  =  Math.round  (dight*Math.pow(10,how))/Math.pow(10,how);    
	   return  dight;    
	},
	/**
	 * 验证指定的值是否为空，如果为空并且提供了 errMsg ，将弹出错误提示框
	 * @param {} val 要验证是否为空的值
	 * @param {} errMsg	当 val 为空时,显示的错误消息
	 * @return {} 返回 boolean 值 ,当为空时返回 false,否则，返回 true
	 */
	isNull : function(val,errMsg){
		var flag = (!val && val !==0 ) ? false : true;
		if(!flag && errMsg){
			ExtUtil.error({msg:errMsg});
		}
		return flag;
	}
}

var DateUtil = {
}
