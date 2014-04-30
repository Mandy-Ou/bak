PrintManager.lodopexePath = "../../controls/lodop/";
PrintManager.printcssPath = "./printCfg.css";
/*
 * 打印事件类
 */
var PrintMgr = {
	/**
	 * 打印预览
	 */
	preview : function(){
		PrintManager.preview(this.getPrintHtml());
	},
	/**
	 * 打印
	 */
	print : function(){
		PrintManager.print(this.getPrintHtml());
	},
	/**
	 * 打印设计
	 */
	design : function(){
		PrintManager.design(this.getPrintHtml());
	},
	/**
	 * 填充数据
	 */
	fillDatas : function(){
		var printDataSourceId = this.getParameter("printDataSourceId");
		var printDs = parent.document.getElementById(printDataSourceId).innerText;
		this.print(printDs);
		var dataArr = Ext.decode(printDs);
		if(null == dataArr || dataArr.length == 0) return;
		for(var i=0,count=dataArr.length; i<count; i++){
			var data = dataArr[i];
			if(!data) continue;
			var cmnMappings = data.cmnMappings;
			this.fmtVal(cmnMappings,data);
			for(var key in data){
				var val = data[key];
				this.setValue(key,val);
			}
		}
	},
	/**
	 * 格式化数据
	 * @param {} cmnMappings
	 * @param {} data
	 */
	fmtVal : function(cmnMappings,data){
		if(null == cmnMappings || cmnMappings.length == 0) return;
		for(var i=0,count=cmnMappings.length; i<count; i++){
			var mapping = cmnMappings[i];
			var name = mapping["name"];
			var dataType = mapping["dataType"] || "0";
			var fun = mapping["fun"];
			var mapinngCmns = mapping["mapingCmns"];
			var fmt = mapping["fmt"];
			if(data.hasOwnProperty(name)){
				var val = data[name];
				val = this.getValByDataType(val,dataType,fmt);
				data[name] = val;
			}else{
				data[name] = "";
			}
			var cmnsArgs = (mapinngCmns) ? mapinngCmns : name;
			if(fun){
				//val = eval('('+fun+'("'+cmnsArgs+'",'+data+'))');
				eval("val = "+fun+"(data,cmnsArgs)");
				data[name] = val;
			}
		}
	},
	/**
	 * 根据数据类型返回指定格式的数据
	 * @param {} val
	 * @param {} dataType 数据类型
	 * @param {} fmt
	 */
	getValByDataType : function(val, dataType, fmt){
		if(!val) return val;
		switch(parseInt(dataType)){
			case 2 :{/*金额*/
				val = RenderMgr.getThousandths(val);
				break;
			}case 3 :{/*日期*/
				if(!fmt) fmt = "Y-m-d";
				if(Ext.isDate(val)){
					val = val.format(fmt);
				}else{
					try{
						var newDateTime = Date.parse(val);
						var date = new Date();
						date.setTime(newDateTime);
						val = date.Format(fmt);
					}catch(err){
						alert("将["+val+"]转换成日期对象发生错误："+err.description );
					}
				}
				break;
			}case 4 :{/*整数*/
				val = Ext.isNumber(val) ? val : parseInt(val);
				break;
			}case 5 :{/*浮点数*/
				val = Ext.isNumber(val) ? val : parseFloat(val);
				break;
			}
		}
		return val;
	},
	setValue : function(eleId,val){
		var ele = document.getElementById(eleId);
		if(!ele) return;
		var isInput = (ele.tagName && ele.tagName == "INPUT");
		if(isInput){
			//ele.value = val;
			ele.setAttribute("value",val);
		}else{
			ele.innerHTML = val;
		}
	},
	/**
	 * 获取要打印的HTML
	 * @return {}
	 */
	getPrintHtml : function(){
		var html = document.body.innerHTML;
		return html;
	},
	/**
	 * 获取浏览器请求参数
	 * @param {} qs
	 * @return {}
	 */
	getParameter : function(qs){  
        var s = location.href;  
        s = s.replace("?","?&").split("&");//这样可以保证第一个参数也能分出来  
        var re = "";  
        for(i=1;i<s.length;i++){
        	if(s[i].indexOf(qs+"=")==0) re = s[i].replace(qs+"=","");//取代前面的参数名，只剩下参数值  
        }
        return re;  
    },
	/**
	 * 打印输出
	 * @param {} obj
	 */
    print : function(obj){
    	if(!window.console) return;
		if(Ext.isString(obj)){
			console.log(obj);
		}else{
			for(var key in obj){
				console.log(key+"="+obj[key]);
			}		
		}
    }
} 

Ext.onReady(function(){
	var printDataSourceId = PrintMgr.getParameter("printDataSourceId");
	if(printDataSourceId){
		PrintMgr.fillDatas();
	}
});

/**
 * 数据渲染对象
 * @type 
 */
var RenderMgr = {
	  /**
     * 贷款期限渲染器
     * @param {} jsonData  记录对象 须包含 "yearLoan","monthLoan","dayLoan" 属性
     * @return {} 返回格式化后的年月日
     */
    loanLimitRender : function(jsonData){
    	var yearLoan = jsonData["yearLoan"];
	 	var monthLoan = jsonData["monthLoan"];
	 	var dayLoan = jsonData["dayLoan"];
	 	var arr = [];
	 	if(yearLoan && yearLoan>0){
	 		arr[arr.length] = yearLoan+'年';
	 	}
	 	if(monthLoan && monthLoan>0){
	 		arr[arr.length] = monthLoan+'个月';
	 	}
	 	if(dayLoan && dayLoan>0){
	 		arr[arr.length] = dayLoan+'天';
	 	}
       return (arr.length > 0) ? arr.join("") : "";
    },
      /**
     * 贷款期限渲染器
     * @param {} jsonData  记录对象 须包含 "yearLoan","monthLoan","dayLoan" 属性
     * @return {} 返回格式化后的年月日
     */
    rateTypeRender : function(jsonData){
    	//rateType,rate
    	var rate = jsonData["rate"];
	 	var rateType = jsonData["rateType"];
	 	if(!rateType){
	 		alert("利率类型：rateType 不能为空!");
	 	}
	 	rateType = parseInt(rateType);
	 	switch(rateType){
	 		case 1 :{/*月利率*/
	 			rateType = "月利率";
	 			break;
	 		}case 2 :{/*日利率*/
	 			rateType = "日利率";
	 			break;
	 		}case 3 :{/*年利率*/
	 			rateType = "年利率";
	 			break;
	 		}
	 	}
	 	rate = "("+rateType+")"+rate;
       return (rate) ? rate : "";
    },
	/**
	 * 将指定JSON数据中指定的列的值转换为大写
	 * @param {} cmns	要转换为大写的列名
	 * @param {} data	要取值的 JSON 数据
	 * @return 返回转换为大写金额的值
	 */
	convertBigAmount : function(data,cmns){
		if(!cmns){
			alert("在调用 RenderMgr.convertBigAmount 方法时，必须同为参数 \"cmns\"提供值!");
			return;
		}
		var val = data[cmns];
		if(val){
			val = val.toString();
			val = val.replace(",","");
			val = this.cmycurd(val);
		}
		return val;	
	},
	/**
	 * 获取千分位值
	 * @param {} v 要转换为千分位的数据
	 * @return {}
	 */
	getThousandths : function(v){
		if(!v) return v;
		if(!Ext.isString(v)) v = v.toString();
		re=/(\d{1,3})(?=(\d{3})+(?:$|\.))/g;
		v=v.replace(re,"$1,");
		return v;
	},
	/**
	 * 转大写金额
	 * @param {} num
	 * @return {String}
	 */
	cmycurd : function(num){
	  var str1 = '零壹贰叁肆伍陆柒捌玖';  //0-9所对应的汉字
	  var str2 = '万仟佰拾亿仟佰拾万仟佰拾元角分'; //数字位所对应的汉字
	  var str3;    //从原num值中取出的值
	  var str4;    //数字的字符串形式
	  var str5 = '';  //人民币大写金额形式
	  var i;    //循环变量
	  var j;    //num的值乘以100的字符串长度
	  var ch1;    //数字的汉语读法
	  var ch2;    //数字位的汉字读法
	  var nzero = 0;  //用来计算连续的零值是几个
	  var negative = null;
	  if(num){
	  	var t = (num+"").charAt(0); 
	  	if(t === '-')  negative = '负';
	  }else{
	  	num = 0;
	  }
	  
	  if(!Ext.isNumber(num)) num = parseFloat(num);
	  num = Math.abs(num).toFixed(2);  //将num取绝对值并四舍五入取2位小数
	  str4 = (num * 100).toFixed(0).toString();  //将num乘100并转换成字符串形式
	  j = str4.length;      //找出最高位
	  if (j > 15){return '溢出';}
	  str2 = str2.substr(15-j);    //取出对应位数的str2的值。如：200.55,j为5所以str2=佰拾元角分
	  
	  //循环取出每一位需要转换的值
	  for(i=0;i<j;i++){
	    str3 = str4.substr(i,1);   //取出需转换的某一位的值
	   if (i != (j-3) && i != (j-7) && i != (j-11) && i != (j-15)){    //当所取位数不为元、万、亿、万亿上的数字时
		   if (str3 == '0'){
		     ch1 = '';
		     ch2 = '';
		  	 nzero = nzero + 1;
		   }else{
		     if(str3 != '0' && nzero != 0){
		       ch1 = '零' + str1.substr(str3*1,1);
		          ch2 = str2.substr(i,1);
		          nzero = 0;
			  }else{
			    ch1 = str1.substr(str3*1,1);
			          ch2 = str2.substr(i,1);
			          nzero = 0;
			  }
		   }
	 	} else{ //该位是万亿，亿，万，元位等关键位
	      if (str3 != '0' && nzero != 0){
	        ch1 = "零" + str1.substr(str3*1,1);
	        ch2 = str2.substr(i,1);
	        nzero = 0;
	      }else{
		     if (str3 != '0' && nzero == 0){
		          ch1 = str1.substr(str3*1,1);
		          ch2 = str2.substr(i,1);
		          nzero = 0;
		  	}else{
			    if (str3 == '0' && nzero >= 3){
			            ch1 = '';
			            ch2 = '';
			            nzero = nzero + 1;
			       }else{
				      if (j >= 11){
			              ch1 = '';
			              nzero = nzero + 1;
					  }else{
					     ch1 = '';
					     ch2 = str2.substr(i,1);
					     nzero = nzero + 1;
				   	  }
	          }
	  }
	   }
	 }
	   if (i == (j-11) || i == (j-3)){  //如果该位是亿位或元位，则必须写上
	        ch2 = str2.substr(i,1);
	    }
	    str5 = str5 + ch1 + ch2;
	    
	    if (i == j-1 && str3 == '0' ){   //最后一位（分）为0时，加上“整”
	      str5 = str5 + '整';
	    }
	  }
	  if(num == 0){
	    str5 = '零元整';
	  }else{
	  	if(negative) str5 = negative+str5;
	  }
	  return str5;
	}
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

