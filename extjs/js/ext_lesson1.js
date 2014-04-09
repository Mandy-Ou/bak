// JavaScript Document
/*
	
Ext.onReady(function(){
Ext.Msg.alert("title","<font color=red> heooldowrd</font>",function callback(id){
alert("你选的是"+id)	});
	});
	
	
	
	
	

	var fn=function(){
		var date=new Date();
		document.body.innerHTML=date.toLocaleString();
		}
//		window.setTimeout(fn,100);
window.setInterval(fn,100);	
	*/
	Ext.onReady(function loads(){
		Ext.get("loadtest").load({url:contentControl.html});
		
		})