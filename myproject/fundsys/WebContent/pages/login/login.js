Ext.onReady(function(){
	//创建LoginWindow 对象
	var loginWin = new Ext.ux.window.LoginWindow();
	loginWin.show();
	
	/**
	 * 检查客户系统环境
	 */
	(function checkSysEnv(){
		var isChorme = Ext.isChrome;
		if(isChorme) return;
		var downChormeUrl = "tools/browser/win-chorme_29.0.1547.76.exe";
		if(Ext.isWindows){
			downChormeUrl = "tools/browser/win-chorme_29.0.1547.76.exe";
		}else if(Ext.isLinux){
			downChormeUrl = "tools/browser/linux-chrome-v31.0.16.deb";
		}else if(Ext.isMac){
			downChormeUrl = "tools/browser/mac-chrome-v32.dmg";
		}
		var loginTabPanle = Ext.getCmp("login_mainTabPanel");
		loginTabPanle.setActiveTab(1);
		var downChormeEle = Ext.get("down_chorme");
		if(downChormeEle) downChormeEle.set({href:downChormeUrl});
		if(!isChorme){
			ExtUtil.confirm({title : '提示',
			msg : '尊敬的客户：<br>&nbsp;&nbsp;您好,为了使你达到最佳的使用效果，请安装“谷歌浏览器”，并用谷歌浏览器访问系统!<br/>' +
					'点击“是”，系统将自动帮你下载“谷歌浏览器”。谷歌浏览器使用帮助可在“关于本系统”里下载!',fn:function(btn){
					if(arguments && arguments[0] != 'yes') return;
					var el = downChormeEle.dom;
					if(document.all){
			            el.click();  
			        }else{
			            var evt = document.createEvent("MouseEvents");  
			            evt.initEvent("click", true, true);  
			            el.dispatchEvent(evt);  
			        }  
			}});
		}
	})();
});

