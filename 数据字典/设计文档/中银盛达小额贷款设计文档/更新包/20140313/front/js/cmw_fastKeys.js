 window.onload=function(){
 /****************************
 * 作者：q821424508@sina.com *
 * 时间：2012-08-20 *
 * version：2.0 *
 * *
 ****************************/
 document.getElementsByTagName("body")[0].onkeydown =function(){

 // 获取事件对象
 	var flag = checkFirefox();
 	if(flag) return;
		var event = window.event || arguments[0];

		if (event.keyCode == 8) {// 判断按键为backSpace键

			// 获取按键按下时光标做指向的element
			var elem = event.srcElement || event.currentTarget;

			// 判断是否需要阻止按下键盘的事件默认传递
			var name = elem.nodeName;

			if (name != 'INPUT' && name != 'TEXTAREA') {
				return _stopIt(event);
			}
			var type_e = elem.type.toUpperCase();
			if (name == 'INPUT'
					&& (type_e != 'TEXT' && type_e != 'TEXTAREA'
							&& type_e != 'PASSWORD' && type_e != 'FILE')) {
				return _stopIt(event);
			}
			if (name == 'INPUT'
					&& (elem.readOnly == true || elem.disabled == true)) {
				return _stopIt(event);
			}
		}
	}
}
function _stopIt(e) {
	if (e.returnValue) {
		e.returnValue = false;
	}
	if (e.preventDefault) {
		e.preventDefault();
	}

	return false;
 }
 
 function checkFirefox(){
	userAgent=window.navigator.userAgent.toLowerCase();
	if(userAgent.indexOf("firefox")>=1){
		return true;
	}else{
		return false;
	}
 }
/**
 * 快捷键事件管理类 
 * @type 
 */
FKeyMgr = {
	keyObj : null,
	groupKeys : {ctrl : true,shift : true,alt : true},	//组合键常量定义 
	keyDefines : {
		setCtrl_Key : function(obj){
			var keyCfg =  FKeyMgr.getKeys(obj);
			keyCfg.fn = function(){
				var args = obj.handler.arguments;
				if(!args){
					obj.handler.call(obj);
				}else{
					obj.handler.apply(obj,args);
				}
			};
			keyObj = new Ext.KeyMap(Ext.getBody(),keyCfg);
		}
	},
	/**
	 * 获取 obj 对象要设置的快捷键
	 * @param {} obj 要设置快捷键的Object 对象
	 */
	getKeys : function(obj){
		var _key = obj.key;
		if(!_key){
			Ext.Msg.show({
				title: "错误信息",
				msg:"在调用FKeyMgr.getKeys方法时，\n" +
					"发现没有为要设置快捷键的对象设置 key 属性！",
				buttons:Ext.MessageBox.OK,
				icon: Ext.MessageBox.ERROR
			});
			return;
		}
		_key = _key.toUpperCase();
		var keys = _key.split("+");
		var obj = {key : keys[keys.length-1]};	
		//设置组合键配置
		for(var i=0,count=keys.length-1; i < count; i++){
			var key = keys[i];
			if(FKeyMgr.groupKeys.hasOwnProperty(key)){
				obj[key] = FKeyMgr.groupKeys[key];
			}
		}
		return obj;
	},
	/**
	 * 为指定的对象设置快捷键
	 * @param {} obj	要绑定快捷键的对象
	 */
	setkey : function(obj){
		if(!obj){
			Ext.Msg.show({
				title: "错误信息",
				msg:"要关联快捷键的对象不存在！",
				buttons:Ext.MessageBox.OK,
				icon: Ext.MessageBox.ERROR
			});
			return;
		}
		var text = obj.key;
		FKeyMgr.keyDefines.setCtrl_Key(obj);
	} 
}