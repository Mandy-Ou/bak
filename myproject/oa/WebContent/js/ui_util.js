/**
 * 对 Ext API 的一些扩展 
 * ExtUtil.alert({title:'',msg:'',fn:funtionObj});
 * ExtUtil.confirm({title:'',msg:'',fn:funtionObj});
 * ExtUtil.error({title:'',msg:'',fn:funtionObj});
 * ExtUtil.info({title:'',msg:'',fn:funtionObj});
 * ExtUtil.question({title:'',msg:'',fn:funtionObj});
 * ExtUtil.warn({title:'',msg:'',fn:funtionObj});
 * @type 
 */
var ExtUtil = {
	/*--------------- 创建 Ext 消息框  CODE START -----------------*/
	/**
	 * 扩展 EXT alert 方法
	 * @param {} cfg 
	 * cfg 相关属性说明 
	 * 	title : 消息标题，如果不提供将以默认的 '提示' 作为标题
	 * 	msg : alert 消息内容
	 * 	fn	: 消息框关闭后的回调函数
	 */
	alert : function(cfg){
		if(!this.checkCfg(cfg)) return;
		if(cfg.fn){	//执行回调函数 alert
			Ext.MessageBox.alert(cfg.title,cfg.msg,cfg.fn);
		}else{
			Ext.MessageBox.alert(cfg.title,cfg.msg);
		}
	},
	/**
	 * 询问提示框 扩展 Ext confirm 方法
	 * @param {} cfg
	 * title : 消息标题，如果不提供将以默认的 '提示' 作为标题
	 * 	msg : alert 消息内容
	 * 	fn	: 消息框关闭后的回调函数
	 */
	confirm : function(cfg){
		if(!this.checkCfg(cfg)) return;
		if(cfg.fn){	//执行回调函数 alert
			Ext.MessageBox.confirm(cfg.title,cfg.msg,cfg.fn);
		}else{
			Ext.MessageBox.confirm(cfg.title,cfg.msg);
		}
	},
	/**
	 *  错误提示框 扩展 Ext show 方法,此方法会弹出一个 带有 “X” 图标的窗口
	 * @param {} cfg
	 * title : 消息标题，如果不提供将以默认的 '提示' 作为标题
	 * 	msg : alert 消息内容
	 * 	fn	: 消息框关闭后的回调函数
	 */
	error : function(cfg){
		if(!this.checkCfg(cfg)) return;
		cfg.icon = Ext.MessageBox.ERROR;
		this.show(cfg);
	},
	/**
	 * 扩展 Ext show 方法,此方法会弹出一个 带有 “！” 图标的窗口
	 * @param {} cfg
	 * title : 消息标题，如果不提供将以默认的 '提示' 作为标题
	 * 	msg : alert 消息内容
	 * 	fn	: 消息框关闭后的回调函数
	 */
	info : function(cfg){
		if(!this.checkCfg(cfg)) return;
		cfg.icon = Ext.MessageBox.INFO;
		this.show(cfg);
	},
	/**
	 * 扩展 Ext show 方法,此方法会弹出一个 带有 “?” 图标的窗口
	 * @param {} cfg
	 * title : 消息标题，如果不提供将以默认的 '提示' 作为标题
	 * 	msg : alert 消息内容
	 * 	fn	: 消息框关闭后的回调函数
	 */
	question : function(cfg){
		if(!this.checkCfg(cfg)) return;
		cfg.icon = Ext.MessageBox.QUESTION;
		this.show(cfg);
	},
	/**
	 *  扩展 Ext show 方法,此方法会弹出一个 带有 “警告” 图标的窗口
	 * @param {} cfg
	 * title : 消息标题，如果不提供将以默认的 '提示' 作为标题
	 * 	msg : alert 消息内容
	 * 	fn	: 消息框关闭后的回调函数
	 */
	warn : function(cfg){
		if(!this.checkCfg(cfg)) return;
		cfg.icon = Ext.MessageBox.WARNING;
		this.show(cfg);
	},
	/**
	 * 显示加载中进度条 弹出框
	 * @param cfg 配置参数 {title : '请等待',msg:'正在生成资源文件...'}
	 * @return 返回 MessageBox 对象
	 */
	progress : function(cfg){
		//验证合法后使用进度条
		var msg = Ext.MessageBox;
		msg.show({
			title:(cfg && cfg.title) ? cfg.title : '请稍等',
			msg:(cfg && cfg.msg) ? cfg.msg : '正在加载...',
			progressText:'',
			width:300,
			progress:true,
			closable:false,
			animEl:'loading'
		});
		//控制进度条速度
		var f = function(v)
		{
		 	return function()
		 	{var i = v/11;
		 	 Ext.MessageBox.updateProgress(i,"");
		 	};
		}
		
		//使进度条加载
		for(var i=0;i<13;i++)
		{
			setTimeout(f(i),i*150);
		}
		return msg;
	},
	/**
	 * 扩展 Ext show 方法
	 * @param {} cfg 
	 * title : 消息标题，如果不提供将以默认的 '提示' 作为标题
	 * 	msg : alert 消息内容
	 * 	fn	: 消息框关闭后的回调函数
	 *  icon : 弹出框图标,目前提供【"错误","警告","提问","信息提示"】 四种
	 */
	show : function(cfg){
		cfg.buttons = Ext.MessageBox.OK;
		Ext.MessageBox.show(cfg);
	},
	/**
	 * 检查消息参数的是否合法 true ： 合法 , false : 不合法
	 * @param {} cfg 消息参数对象
	 * @return {Boolean}	返回消息参数的是否合法 true ： 合法 , false : 不合法
	 */
	checkCfg : function(cfg){
		var errMsg = null;
		if(!cfg){
			errMsg = "在调用 ExtUtil 的 alert(cfg) 方法时，没有提供  【cfg】 参数";
		} 
		if(!cfg.title) cfg.title = "系统提示";
		if(!cfg.msg){
			errMsg = "在调用 ExtUtil 的 alert(cfg) 方法时，没有提供 消息内容【cfg.msg】 参数";
		}
		if(errMsg){	//如果存在错误则提示
			window.alert(errMsg);
			return false;
		}
		return true;
	}/*--------------- 创建 Ext 消息框  CODE END -----------------*/
}

/**
 * Ext 提示框
 */
Ext.tip = function(){
    var msgCt;
    function createBox(t, s){
        return ['<div class="msg">',
                '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
                '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>', t, '</h3>', s, '</div></div></div>',
                '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
                '</div>'].join('');
    }
    return {
    	/**
    	 * 显示消息
    	 * @param title 消息提示标题
    	 * @param format 消息提示内容
    	 */
        msg : function(title, format){
            if(!msgCt){
                msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
            }
            msgCt.alignTo(document, 't-t');
            var s = String.format.apply(String, Array.prototype.slice.call(arguments, 1));
            var m = Ext.DomHelper.append(msgCt, {html:createBox(title, s)}, true);
            m.slideIn('t').pause(1).ghost("t", {remove:true});
        },

        init : function(){
            var lb = Ext.get('lib-bar');
            if(lb){
                lb.show();
            }
        }
    };
}();
Ext.onReady(Ext.tip.init, Ext.tip);