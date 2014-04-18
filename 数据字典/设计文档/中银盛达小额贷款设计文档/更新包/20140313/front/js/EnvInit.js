/*============= ------- 与系统初始化相关的业务 START ----- =============*/
Ext.onReady(function(){
	/***----------- 获取初始化的系统参数数据  CODE START  -----------***/
	(function(){
		Ext.Ajax.request({ //获取编号
			url : './sysSysparams_getInitDatas.action',
			success : function(response){
				if(EventManager.existSystem(response)) return;
				var dataObj = Ext.decode(response.responseText); //将后台JSON字符串转换为JSON对象
				SYSPARAMS_DATA = dataObj;// SYSPARAMS_DATA 定义在constant.js 文件中
				Cmw.print(SYSPARAMS_DATA);
			},failure : function(response){
				if(EventManager.existSystem(response)) return;
				Ext.Msg.alert("失败","获取数据失败！");
			}
		});
	})();/***----------- 获取初始化的系统参数数据  CODE END  -----------***/
});/*============= ------- 与系统初始化相关的业务  END ----- =============*/