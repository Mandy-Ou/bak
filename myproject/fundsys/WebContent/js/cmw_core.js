Array.prototype.indexOf = function(val) {              
	    for (var i = 0; i < this.length; i++) {  
	        if (this[i] == val) return i;  
	    }  
	    return -1;  
	},
	/**
	 * 删除数组中指定的元素 
	 * 例如 ： 需要删除数组中的 var a = [1,2,3]; "2" a.remove("2"); 删除后的数组是： a=[1,3];
	 * 
	 */
Array.prototype.remove = function(val) {  
    var index = this.indexOf(val);  
    if (index > -1) {  
        this.splice(index, 1);  
    }  
}; 


/**
 * 扩展Ext 方法类 
 * @type 
 */
Cmw = {
	/**
	 * 通过元素名,取得该元素对象
	 * @param {} eleId
	 * @return {} 返回元素对象
	 */
	$ : function(eleId){
		return document.getElementById(eleId);
	},
	/**
	 * 通过元素名,取得该元素对象数组
	 * @param {} eleName 元素名
	 * @return {} 返回元素对象数组
	 */
	getElesByName : function(eleName){
		return document.getElementsByName(eleName);
	},
	/**
	 * 通过元素名,取得该元素对象数组
	 * @param {} eleName 元素名
	 * @return {} 返回元素对象数组
	 */
	getElementsByTagName: function(eleName){
		return document.getElementsByTagName(eleName);
	},
	
	/**
	 * 利用 Date 的 getTime 生成唯一的 UUID
	 * @return {} 返回一个时间戳
	 */
	getUuid : function(){
	 	return new Date().getTime()+'';
	},
	 /**
	  * 为指定的URL 加上一个随机数，防止浏览器缓存
	  * @param {} doUrl 要加随机数的URL
	  * @return {}	返回带随机数参数的 URL
	 */
	getUrl : function(doUrl){
		var uuid = Cmw.getUuid();
		doUrl += (doUrl.indexOf("?") == -1) ? "?t="+uuid : "&t="+uuid;
		return doUrl;
	},
	/**
	 * 导入包[动态加载JS文件]
	 * @param {} path	JS文件路径
	 * @param {} fn		function 对象
	 */
	importPackage : function(path,fn){
		var pathname = location.pathname;
		var lastIndex = pathname.lastIndexOf("/");
		if(lastIndex != pathname.length-1){
			pathname = pathname.substring(0,lastIndex+1);
		}
		path = location.protocol+'//'+location.host+pathname+path+"?time_uuid="+this.getUuid();
	  	Cmw.print("path="+path);
	  	seajs.use(path, fn);
	},
	/**
	 * 打印对象
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
	},
	/**
	 * 在指定的组件上显示 Loading 效果
	 * @param {} cmpt	要显示 Loading 效果的组件
	 * @param {} msg	Loading 时，显示的消息
	 */
	mask : function(cmpt,msg){
		var el = null;
		if(Ext.isString(cmpt)){
			el = Ext.get(cmpt);
		}else{
			el = cmpt.el;
		}
		if(!el) return;
		if(!msg) msg = 'Loading';
		el.mask(msg, 'x-mask-loading');
	},
	/**
	 * 移除指定的组件上的 Loading 效果
	 * @param {} cmpt	要移除 Loading 效果的组件
	 */
	unmask : function(cmpt){
		var el = null;
		if(Ext.isString(cmpt)){
			el = Ext.get(cmpt);
		}else{
			el = cmpt.el;
		}
		if(!el) return;
		el.unmask();
	},
	/**
	 * 根据TabTreeWinId 创建或激活指定 tabId 的 tab 对象
	 * 以JSON对象形式返回
	 * @param {} AppTabTreeWin 对象
	 * @param {} tabId tab面板对象ID
	 * @param {} params 参数对象
	 * @param String url   js 界面文件地址
	 * @param String tabTitle TabPanel 的标题
	 */
	activeTab:function(apptabtreewinId,tabId,params,url,tabTitle){
		var appTabtreeWin = Ext.getCmp(apptabtreewinId);
		if(!appTabtreeWin){
			ExtUtil.error({msg : '"'+apptabtreewinId+'" 对象为空，无法激活  "'+tabId+'" 对象!'});
			return;
		}
		if(url && !tabTitle){
			ExtUtil.error({msg : '如果加载自定义 url 的JS文件，必须为 Tab 面板指定 title 值!'});
			return;
		}
		appTabtreeWin.activeByCustTabId(tabId,params,url,tabTitle);
	},
	/**
	 * 根据TabTreeWinId 隐藏指定的 tab 对象
	 * 以JSON对象形式返回
	 * @param {} AppTabTreeWin 对象
	 * @param {} tab tab面板对象
	 */
	hideTab:function(apptabtreewinId,tab){
		var appTabtreeWin = Ext.getCmp(apptabtreewinId);
		if(!appTabtreeWin){
			ExtUtil.error({msg : '"'+apptabtreewinId+'" 对象为空，无法隐藏  "'+tab.id+'" 对象!'});
			return;
		}
		appTabtreeWin.hideTabItem(tab);
	},
	/**
	 * 根据TabTreeWinId 显示指定的 tab 对象
	 * 以JSON对象形式返回
	 * @param {} AppTabTreeWin 对象
	 * @param {} tab tab面板对象
	 */
	showTab:function(apptabtreewinId,tab,tabTitle){
		var appTabtreeWin = Ext.getCmp(apptabtreewinId);
		if(!appTabtreeWin){
			ExtUtil.error({msg : '"'+apptabtreewinId+'" 对象为空，无法显示 "'+tab.id+'" 对象!'});
			return;
		}
		appTabtreeWin.showTabItem(tab);
	},
	/**
	 * 获取复选框树选中的节点ID，并以数组返回
	 * @param tree 复选框树对象
	 * @param eqStrs 用来区分不同类型节点的字符串，多个以“，”号分隔
	 * @return ids 以数组形式返回找到的节点ID
	 */
	getCheckIds : function(tree,eqStrs){
		var ids = [];
		var nodes = tree.getChecked();
		if(!eqStrs){
			ids[0] = this.getNodesId(nodes);
		}else{
			eqStrs = eqStrs.split(",");
			for(var i=0,count=eqStrs.length; i<count; i++){
				var arr = this.getNodesId(nodes,eqStrs[i]);
				if(!arr && arr.length==0) continue;
				ids[ids.length] = arr;
			}
		}
		return ids;
	},
	/**
	 * 获取节点ID
	 * @param {} nodes 要获取的节点ID的集合
	 * @param eqStr 用来比较的字符串
	 */
	getNodesId : function(nodes,eqStr){
		var ids = [];
		if(eqStr){
			for(var i=0,count=nodes.length; i<count; i++){
				var id = nodes[i].id;
				if(!id || id.indexOf(eqStr) == -1) continue;
				ids[ids.length] = id.replace(eqStr,"");
			}
		}else{
			for(var i=0,count=nodes.length; i<count; i++){
				ids[i] = nodes[i].id;
			}
		}
		return ids;
	},
	/**
	 * 创建系统面板
	 * param self 主面板对象
	 * @param params 所需参数
	 */
	createSysPanel : function(self){
//		var self = this;
		var params = null;
		params = self.sysParams;
		var pa = {isenabled : 1};
		if(!params){
			params  = {};
		}
		Ext.apply(params,pa);
		var sysPanel = new Ext.Panel({region:'north',layout:'hbox',height:80});
		EventManager.get('./sysSystem_list.action',{params:params,sfn:function(json_data){
			self.toolBar.disable();
			if(null == json_data.totalSize || json_data.list.length == 0) return;
			var list = json_data.list;
			for(var i=list.length-1; i>=0; i--){
				var data = list[i];
				var boxHtml = "<div class='system_box'><img src='"+data.icon+"'/><br/><span>"+data.name+"</span></div>";
				var sysBox = new  Ext.BoxComponent({
					html : boxHtml,
					data : data,
					listeners:{
				      'render': function(cmpt){
				      		var ele = cmpt.el;
				      		ele.on({
				      			'click' : {
				      				fn : function(){
				      					if(sysPanel.selEle){
				      						sysPanel.selEle.removeClass('selected_sysbox');
				      					}
				      					ele.addClass('selected_sysbox');
				      					sysPanel.selEle = ele;
				      					self.toolBar.enable();
				      					if(!self["notify"]){
				      						ExtUtil.error({msg:'必须在实现类中提供  notify(data) 方法！'});
				      					}
				      					self.notify(cmpt.data);
				      				}
				      			}
				      		});
				      }
				    }
				});
				sysPanel.add(sysBox);
				sysPanel.doLayout();
			}	
		}});
		return sysPanel;
	},
	/**
	 * 获取 Grid Column render 的显示值
	 * 例： (取证件类型为1的证件。即：取身份证)
	 * 		Cmw.getRenderDispVal(REGISTER.GvlistDatas,'100002','1');
	 * 		return [1,'身份证','中华人民共和国唯一身份证标识'];
 	 * @param {} register	注册器对象 /注册器对象 参考 constant.js 中的常量定义对象 REGISTER /
	 * @param {} type 资源ID引用值 如果是从基础数据取数的话，此处是资源ID引用值 
	 * @param {} eqId 比较的基础数据 ID 值
	 * @return 返回基础数据名称
	 */
	getRenderDispVal : function(register,type,eqId){
		var dataSource = this.getDataSource(register,type);
		if(!dataSource) return null;
		for(var i=0,count=dataSource.length; i<count; i++){
			var row = dataSource[i];
			var gvlistId = row[0];
			if(eqId == gvlistId) return row;
		}
		return null;
	},
	
	/**
	 * 获取下拉框 SimpleStore 的数据源
	 * 例如：[
	 * 		  id,  name,  append
	 * 		[1001,'初中','xxxx'],	 --> 1001 为ID值, 高中 为name 显示值, 'xxxx' 为 append 附加参数值 
	 * 		[1001,'高中','yyyy'],
	 * 	]
	 * @param {} register	注册器对象 /注册器对象 参考 constant.js 中的常量定义对象 REGISTER /
	 * @param {} type 资源ID引用值 如果是从基础数据取数的话，此处是资源ID引用值 
	 * @return {} 返回 下拉框数据源数组
	 */
	getComboxDataSource : function(register,type){
		var dataSource = this.getDataSource(register,type);
		if(!dataSource) return [];
		return dataSource;
	},
	/**
	  * 获取单选按钮或复选框的数据源
	 * @param {} name name 属性值
	 * @param {} register	注册器对象 /注册器对象 参考 constant.js 中的常量定义对象 REGISTER /
	 * @param {} type 资源ID引用值 如果是从基础数据取数的话，此处是资源ID引用值 
	 * @return {} 返回 单选 或 复选框所需的格式
	 */
	getRadOrCheckDataSource : function(name,register,type){
		var dataSource = this.getDataSource(register,type);
		if(!dataSource) return [];
		/* 转换成 单选 或 复选框格式 */
		for(var i=0,count=dataSource.length; i<count; i++){
			var row = dataSource[i];
			var cfg = {boxLabel:row[1],name:name,inputValue:row[0]};
			if(row[2]){
				cfg["append"] = row[2];
			}
			dataSource[i] = cfg;
		}
		return dataSource;
	},
	/**
	 * 以二维数组的形式返回数据
	 * 例如：[
	 * 		  id,  name,  append
	 * 		[1001,'初中','xxxx'],	 --> 1001 为ID值, 高中 为name 显示值, 'xxxx' 为 append 附加参数值 
	 * 		[1001,'高中','yyyy'],
	 * 	]
	 * @param {} register	/注册器对象 参考 constant.js 中的常量定义对象 REGISTER /
	 * @param {} type
	 * @return {}
	 */
	getDataSource : function(register,type){
		switch(register){
			case REGISTER.GvlistDatas:{	/*加载基础数据的数据*/
				var dataList = GvlistDatas[type];
				break;			
			}
		}
		if(!dataList || (!dataList.list || dataList.list.length == 0)) return null;
		var locale = AppDef.getLocale();
		var dispKey = LOCALE_NAME[locale];
		if(!dispKey) dispKey = "name";
		var list = dataList.list;
		var dataSource = [];
		for(var i=list.length-1; i>=0; i--){
			var row = list[i];
			var dispname = row[dispKey];
			if(!dispname) continue;
			var id = row["id"];
			var append = (row["append"]) ?  row["append"] : '';
			dataSource[dataSource.length] = [id, dispname, append];
		}
		return dataSource;
	},
	/**
	 * 计算两个日期之间相差多小天
	 * @param {} start开始日期
	 * @param {} end 结束日期
	 */
	calculateDays : function(start,end){
		if(start==null||start.length==0||end==null||end.length==0){ 
                return 0; 
            } 
        var arr=start.split("-");  
        var starttime=new Date(arr[0],arr[1],arr[2]);  
        var starttimes=starttime.getTime(); 
         
        var arrs=end.split("-");  
        var endtime=new Date(arrs[0],arrs[1],arrs[2]);  
        var endtimes=endtime.getTime(); 
         
        var intervalTime = endtimes-starttimes;//两个日期相差的毫秒数 一天86400000毫秒 
        var Inter_Days = ((intervalTime).toFixed(2)/86400000)+1;//加1，是让同一天的两个日期返回一天 
         
        return Inter_Days; 		
	},
	/**
	 * 计算连个日期相差的月份
	 * @param {} start 开始日期
	 * @param {} end 结束日期
	 * @return {}
	 */
	calculateMonth : function(start,end){
		 var arr = start.split("-");  
		 var arrs = end.split("-");  
		var year1 = arr[0];
        var year2 = arrs[0];
        var month1 = arr[1];
        var month2 = arrs[1];
        var len = (year2 - year1) * 12 + (month2 - month1);
        return len;
	},
	/**
	 * 获取千分位值
	 * @param {} v 要转换为千分位的数据
	 * @return {}
	 */
	getThousandths : function(v){
		var minus = "";
		if(Ext.isNumber(v) && v<0){
			v = v+"";
		}
		if(Ext.isString(v) && v.indexOf("-") != -1){
			minus = "-";
			v = v.replace(minus,"");
		}
		var fmt = '0,000.00';
		v = Ext.util.Format.number(v,fmt);
		return minus+v;
	},
	/**
	 * 将金额转换成人民币大写
	 * @param {} num 要转换为大写人民币的数字
	 * @return {String} 返回大写人民币金额
	 */
	cmycurd : function (num){  //转成人民币大写金额形式
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
	  }
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
	},
	/**
	 * 图像大小调整	Cmw.resizeImage(this,85)
	 * @param {} objImage
	 * @param {} maxWidth
	 */
	resizeImage : function(objImage,maxWidth) {
		var objImg = $(objImage);
		var w = objImg.width();  
	    if(w < maxWidth){  
	        return;  
	    }else{  
	        var h = objImg.height();
	        h = parseInt(h*maxWidth/w);
	        objImg.height(h);
	        objImg.width(maxWidth);  
	    }  
	} 
}

/**--------------------->	2010-10-30 START CODE	<-----------------**/
/**
 * 自定义事件类,所有模块JS都继承自类
 */
Ext.util.MyObservable = function(){};
Ext.extend(Ext.util.MyObservable,Ext.util.Observable,{
	tab : null,	//当前 TabPanel 对象
	params : null,//由TabPanel 传过来的参数
	module : null,	//要加载到 TabPanel 中的模块
	idprefix : null,
	init : function(){
		this.tab = arguments[0];
		this.params = this.tab.params;
		this.getIdprefix();
		if(this["initModule"]){	//子类须重写此方法，以创建 module 对象
			this.initModule(this.tab,this.params);
			this.tab.add(this.module);
			this.tab.doLayout();
		}
	},
	/**
	 * 当TabPanel resize 时，会触发此事件
	 * @param {} pw	tab 新宽度
	 * @param {} ph	tab 新高度
	 */
	resize : function(pw,ph){
		this.module.resize(pw,ph);
	},
	/**
	 * 当TabPanel 关闭后调用 destory 事件时 ，会触发此事件
	 */
	destroy : function(){
		this.module.destroys();
	},
	/**
	 * 从TabPanel 对象中获取ID前缀[id前缀是菜单ID]
	 * @return {} 
	 */
	getIdprefix : function(){
		var id = this.tab.getId();
		if(!id) id = Cmw.getUuid();
		id = id.replace('apptab-','');
		this.idprefix = id;
		return this.idprefix;
	}
});

/**
 * HashMap 类
 */
var HashMap = function() {
  this.initialize();
}
 
HashMap.hashmap_instance_id = 0;
 
HashMap.prototype = {
  hashkey_prefix: "<#HashMapHashkeyPerfix>",
  hashcode_field: "<#HashMapHashcodeField>",
 
  initialize: function() {
    this.backing_hash = {};
    this.code = 0;
    this.size = 0;
    HashMap.hashmap_instance_id += 1;
    this.instance_id = HashMap.hashmap_instance_id;
  },
 
  hashcodeField: function() {
    return this.hashcode_field + this.instance_id;
  },
  /*
   maps value to key returning previous assocciation
   */
  put: function(key, value) {
    var prev;
 
    if (key && value) {
      var hashCode;
      if (typeof(key) === "number" || typeof(key) === "string") {
        hashCode = key;
      } else {
        hashCode = key[this.hashcodeField()];
      }
      if (hashCode) {
        prev = this.backing_hash[hashCode];
      } else {
        this.code += 1;
        hashCode = this.hashkey_prefix + this.code;
        key[this.hashcodeField()] = hashCode;
      }
      this.backing_hash[hashCode] = [key, value];
      this.size++;
    }
    return prev === undefined ? undefined : prev[1];
  },
  /*
   returns value associated with given key
   */
  get: function(key) {
    var value;
    if (key) {
      var hashCode;
      if (typeof(key) === "number" || typeof(key) === "string") {
        hashCode = key;
      } else {
        hashCode = key[this.hashcodeField()];
      }
      if (hashCode) {
        value = this.backing_hash[hashCode];
      }
    }
    return value === undefined ? undefined : value[1];
  },
  /*
   deletes association by given key.
   Returns true if the assocciation existed, false otherwise
   */
  remove: function(key) {
    var success = false;
    if (key) {
      var hashCode;
      if (typeof(key) === "number" || typeof(key) === "string") {
        hashCode = key;
      } else {
        hashCode = key[this.hashcodeField()];
      }
      if (hashCode) {
        var prev = this.backing_hash[hashCode];
        this.backing_hash[hashCode] = undefined;
        delete this.backing_hash[hashCode];
        this.size--;
        if (prev !== undefined){
          key[this.hashcodeField()] = undefined; //let's clean the key object
          success = true;
        }
      }
    }
    return success;
  },
  /*
   iterate over key-value pairs passing them to provided callback
   the iteration process is interrupted when the callback returns false.
   the execution context of the callback is the value of the key-value pair
   @ returns the HashMap (so we can chain)                                                                  (
   */
  each: function(callback, args) {
    var key;
    for (key in this.backing_hash){
      if (callback.call(this.backing_hash[key][1], this.backing_hash[key][0], this.backing_hash[key][1]) === false)
        break;
    }
    return this;
  },
  toString: function() {
    return "HashMap"
  },
  /**
   * 获取 HashMap 对象的大小
   * @return {} 返回对象大小
   */
  getSize : function(){
  	return this.size;
  },
  /**
   * 以数组形式返回 HahsMap 中的所有值。
   * @return Array 以数组形式返回所有值
   */
  getValues : function(){
  	var values = [];
  	var index = 0;
  	for (key in this.backing_hash){
  		var val = this.get(key);
  		if(!val) continue;
     	values[index++] = val;
    }
    return values;
  },
  /**
   * 以数组形式返回 HahsMap 中的所有键。
   * @return Array 以数组形式返回所有键
   */
  getKeys : function(){
  	var keys = [];
  	var index = 0;
  	for (key in this.backing_hash){
     	keys[index++] = key;
    }
    return keys;
  },
  /**
   * 清空 HashMap 对象所有元素
   */
  clear : function(){
  	HashMap.hashmap_instance_id=0;
  	this.initialize();
  }
}
/**
 * 
 * var myencodedstring = Ext.util.base64.encode("Testtext"); //returns VGVzdHRleHQ
 *	var mydecodedstring = Ext.util.base64.decode("VGVzdHRleHQ="); //returns Testtext  
 * 加密工具
 * @type 
 */
Ext.util.base64 = {

    base64s : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/",
    
    encode: function(decStr){
        if (typeof btoa === 'function') {
             return btoa(decStr);            
        }
        var base64s = this.base64s;
        var bits;
        var dual;
        var i = 0;
        var encOut = "";
        while(decStr.length >= i + 3){
            bits = (decStr.charCodeAt(i++) & 0xff) <<16 | (decStr.charCodeAt(i++) & 0xff) <<8 | decStr.charCodeAt(i++) & 0xff;
            encOut += base64s.charAt((bits & 0x00fc0000) >>18) + base64s.charAt((bits & 0x0003f000) >>12) + base64s.charAt((bits & 0x00000fc0) >> 6) + base64s.charAt((bits & 0x0000003f));
        }
        if(decStr.length -i > 0 && decStr.length -i < 3){
            dual = Boolean(decStr.length -i -1);
            bits = ((decStr.charCodeAt(i++) & 0xff) <<16) |    (dual ? (decStr.charCodeAt(i) & 0xff) <<8 : 0);
            encOut += base64s.charAt((bits & 0x00fc0000) >>18) + base64s.charAt((bits & 0x0003f000) >>12) + (dual ? base64s.charAt((bits & 0x00000fc0) >>6) : '=') + '=';
        }
        return(encOut);
    },
    
    decode: function(encStr){
        if (typeof atob === 'function') {
            return atob(encStr); 
        }
        var base64s = this.base64s;        
        var bits;
        var decOut = "";
        var i = 0;
        for(; i<encStr.length; i += 4){
            bits = (base64s.indexOf(encStr.charAt(i)) & 0xff) <<18 | (base64s.indexOf(encStr.charAt(i +1)) & 0xff) <<12 | (base64s.indexOf(encStr.charAt(i +2)) & 0xff) << 6 | base64s.indexOf(encStr.charAt(i +3)) & 0xff;
            decOut += String.fromCharCode((bits & 0xff0000) >>16, (bits & 0xff00) >>8, bits & 0xff);
        }
        if(encStr.charCodeAt(i -2) == 61){
            return(decOut.substring(0, decOut.length -2));
        }
        else if(encStr.charCodeAt(i -1) == 61){
            return(decOut.substring(0, decOut.length -1));
        }
        else {
            return(decOut);
        }
    }

};
