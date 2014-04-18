function i2c(n){
 if(typeof(n) == "number"){
  if(-1 == n.toString().indexOf(".")){
   return seti2c(n);
  }else{
   var i,istr,f,fstr,a,rstr;
   a = n.toString().split(".");
   i = a[0];
   f = a[1];
   istr = seti2c(i);
   fstr = setf2c(f);
   rstr = istr+fstr;
   rstr = rstr.replace(/^元/,"");
   return rstr;
  }
 }else{
  return "---";
 }
}

function seti2c(n){
 var ns = n.toString();
 var tempstr = "";
 for(var i=1;i<ns.length+1;i++){
  switch(i){
   case 1:
    var t = ns.substr(ns.length-i,1);
    if(t!="0"){
     tempstr = t+"元"+tempstr;
    }else{
     tempstr = "元"+tempstr;
    }
    break;
   case 2:
    var t = ns.substr(ns.length-i,1);
    if(t!="0"){
     tempstr = t+"拾"+tempstr;
    }else{
     tempstr = "0"+tempstr;
    }
    break;
   case 3:
    var t = ns.substr(ns.length-i,1);
    if(t!="0"){
     tempstr = t+"佰"+tempstr;
    }else{
     tempstr = "0"+tempstr;
    }
    break;
   case 4:
    var t = ns.substr(ns.length-i,1);
    if(t!="0"){
     tempstr = t+"仟"+tempstr;
    }else{
     tempstr = "0"+tempstr;
    }
    break;
   case 5:
    var t = ns.substr(ns.length-i,1);
    if(t!="0"){
     tempstr = t+"万"+tempstr;
    }else{
     tempstr = "万"+tempstr;
    }
    break;
   case 6:
    var t = ns.substr(ns.length-i,1);
    if(t!="0"){
     tempstr = t+"拾"+tempstr;
    }else{
     tempstr = "0"+tempstr;
    }
    break;
   case 7:
    var t = ns.substr(ns.length-i,1);
    if(t!="0"){
     tempstr = t+"佰"+tempstr;
    }else{
     tempstr = "0"+tempstr;
    }
    break;
   case 8:
    var t = ns.substr(ns.length-i,1);
    if(t!="0"){
     tempstr = t+"仟"+tempstr;
    }else{
     tempstr = "0"+tempstr;
    }
    break;
   case 9:
    var t = ns.substr(ns.length-i,1);
    if(t!="0"){
     tempstr = t+"亿"+tempstr;
    }else{
     tempstr = "亿"+tempstr;
    }
    break;
   case 10:
    var t = ns.substr(ns.length-i,1);
    if(t!="0"){
     tempstr = t+"拾"+tempstr;
    }else{
     tempstr = "0"+tempstr;
    }
    break;
   case 11:
    var t = ns.substr(ns.length-i,1);
    if(t!="0"){
     tempstr = t+"佰"+tempstr;
    }else{
     tempstr = "0"+tempstr;
    }
    break;
   case 12:
    var t = ns.substr(ns.length-i,1);
    if(t!="0"){
     tempstr = t+"仟"+tempstr;
    }else{
     tempstr = "0"+tempstr;
    }
    break;
   default:break;
  }
 }
 return g2b(tempstr);
}

function setf2c(n){
 var ns = n.toString();
 var tempstr = "";
 for(var i=0;i<ns.length;i++){
  switch(i){
   case 0:
    var t = ns.substr(i,1);
    if(t!="0"){
     tempstr += t+"角";
    }else{
     tempstr += "0";
    }
    break;
   case 1:
    var t = ns.substr(i,1);
    if(t!="0"){
     tempstr += t+"分";
    }
    break;
   default:break;
  }
 }
 return g2b(tempstr);
}

function g2b(s){
 rs = s.replace(/0+/g,"0");
 rs = rs.replace(/0(元|万|亿)/g,"$1");
 rs = rs.replace("亿万","亿");
 rs = rs.replace(/0/g,"零");
 rs = rs.replace(/1/g,"壹");
 rs = rs.replace(/2/g,"贰");
 rs = rs.replace(/3/g,"叁");
 rs = rs.replace(/4/g,"肆");
 rs = rs.replace(/5/g,"伍");
 rs = rs.replace(/6/g,"陆");
 rs = rs.replace(/7/g,"柒");
 rs = rs.replace(/8/g,"捌");
 rs = rs.replace(/9/g,"玖");
 return rs;
}
  
  
  
//--填充表格

function piliskys(numb){

	if(isNaN(numb)) {
	  alert("不是一个有效的数字，请重新输入！");
	}
	
	var money1 = new Number(numb);
	
	if(money1> 1000000000000000000) {
	  alert("您输入的数字太大，重新输入！");
	  return;
	}
 
	  
	
	var monee = Math.round(money1*100).toString(10)
	  
	var bigmoney = update2B(monee);
	 
	
	var i,j;
	j=0;
	var leng = bigmoney.length;
	var monval="";
	for( i=0;i<leng;i++)
	{
	  fileto_mon(bigmoney.charAt(i), leng-i-1);
	}
 

}

function update2B(s){ 


 rs = s.replace(/0/g,"零");
 rs = rs.replace(/1/g,"壹");
 rs = rs.replace(/2/g,"贰");
 rs = rs.replace(/3/g,"叁");
 rs = rs.replace(/4/g,"肆");
 rs = rs.replace(/5/g,"伍");
 rs = rs.replace(/6/g,"陆");
 rs = rs.replace(/7/g,"柒");
 rs = rs.replace(/8/g,"捌");
 rs = rs.replace(/9/g,"玖"); 
 
 
 return rs;
}

  

function fileto_mon(bignum, a){
  
  if(a>10){ 
     a=a - 8;
     return(fileto_mon(bignum,a));
  }
	switch(a){
	case 0 : document.all('span0').innerText=bignum; break;
	case 1 : document.all('span1').innerText=bignum; break;
	case 2 : document.all('span2').innerText=bignum; break;
	case 3 : document.all('span3').innerText=bignum; break;
	case 4 : document.all('span4').innerText=bignum; break;
	case 5 : document.all('span5').innerText=bignum; break;
	case 6 : document.all('span6').innerText=bignum; break;
	case 7 : document.all('span7').innerText=bignum; break;
	case 8 : document.all('span8').innerText=bignum; break;
	case 9 : document.all('span9').innerText=bignum; break;
	case 10 : document.all('span10').innerText=bignum; break;
   }
} 
  
  

