/**
 * #DESCRIPTION#
 * @author smartplatform_auto
 * @date 2013-12-25 03:38:37
 */
define(function(require, exports) {
	exports.viewUI = {
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		panel1:null,
		tPal:null,
		btns:null,
		fBtn:null,
		commPal:null,
		appMainPanel:null,
		setParams : function(tab, params) {
			this.tab = tab;
			this.params = params;
		},
		/**
		 * 主UI 界面开始点
		 */
		getMainUI : function(tab, params) {
			this.setParams(tab, params);
			if (!this.appMainPanel) {
				this.craeteMainPnl();
			}
			var _this = this;
			tab.notify = function(_tab) {
				_this.setParams(_tab, _tab.params);
				_this.refresh();
			};
			return this.appMainPanel;
		},
		/**
		 * 创建主面板
		 */
		craeteMainPnl : function() {
			var appPanel = new Ext.Panel({
				renderTo:Ext.getBody(),
				autoScroll:true
			});
			this.fBtn=this.fromBtn();
			this.btns=this.createButton();
			this.appFrm = this.createForm();
			this.panel1=this.getPanel();
			this.tPal=this.getPanel2();
			this.commPal=this.getCommPal();
			appPanel.add(this.panel1);
			appPanel.add(this.btns);
			appPanel.add(this.tPal);
			appPanel.add(this.commPal);
			this.appMainPanel = appPanel;
			return this.appMainPanel;
		},
		/**
		 * 销毁组件
		 */
		destroy : function(){
			if (this.appMainPanel) {
				this.appMainPanel.destroy();
				this.appMainPanel = null;
			}},
		getPanel:function(){
			var _this=this;
			var htmlContent="<div style='height:20px;background:#CCC'>"+"马化腾"+
			"&nbsp;&nbsp;发布于："+"2013-12-28"+"&nbsp;&nbsp;点击了"+"33"+"次</div>";
//			htmlContent+="<div style='height:20px'>"+"name"+
//			"最后编辑于"+"enddata"+"</div>"; 
			htmlContent+="<div ><h1 style='text-align:center; font-size:24px'>春运首日火车票今日开售 退票费一律涨至20%</h1>" +
					"<br/>1月16日春运首日火车票今日开售，互联网、电话订票预售期20天，" +
					"车站、代售点、售票机预售期为18天。除夕车票1月11日可买。春运期间退票将按票面价20%收退票费。" +
					"“先囤票再退票”旅客或将减少。<br/>" +
					"今年，铁路12306网站新版正式上线试运行，增加了自动查询、自动提交订单、有票提醒等功能，" +
					"这意味着12306网站实现了自动抢票，致使原本就激烈的抢票大战再次升级。在各大互联网企业大打抢票战的同时，" +
					"手机抢票软件也如雨后春笋般层出不穷。今年的抢票软件相比往年更加鱼龙混杂，用户更加真假难辨。" +
					"在360手机助手中搜索“抢票”可发现多达十余种软件，有的甚至还含有不少成人广告。有用户评论，" +
					"抢票软件中安装有恶意插件，导致流量流失、恶意扣费的现象不断。同时，" +
					"一大批山寨订票软件和冒充订票软件的手机木马软件也出现在网络中。业内人士表示，" +
					"手机木马冒充12306火车票相关应用，在用户点击运行后，会加载恶意代码，后台下载软件或偷发收费业务定制短信。" +
					"此外，如用户误用山寨版12306登录，还会泄露12306账号密码，与账号关联的个人身份信息也会被黑客窃取。" +
					"尽管在不少人看来，在一票难求的春运大潮中，抢票软件是种“刚需”，但仍引发了不少的话题和争议。大多数网民则表示，" +
					"其中孰是孰非并不是其最关心的问题，更关心的是在线购票如何才能更方便顺畅，不管是官方的还是市场化的，" +
					"旅客都希望能够使用更丰富的在线购票渠道和方式，让回家之路不再难。<br/>" +"<img src='images/public/news/news.png'/>"+
					"</div>";
			var npanel=new Ext.Panel({
				title:"我的新闻",
				height:screen.height-320,
				autoScroll:true,
				html:htmlContent
			});
			return npanel;
		},
		/**
		 * 点击按钮时展开面板
		 */
		createButton : function() {
			var _this=this;
			var button_comment = new Ext.Button({
						text : '评论',
						renderTo:document.body,
						width : 80,
						handler:function(){
							_this.commPal.expand();	
						}
				});
			var button_checkcomm = new Ext.Button({
					text : '查看评论',
					renderTo:document.body,
					width : 80,
						handler:function(){
							_this.tPal.expand();
							_this.appMainPanel.scrollTop(100);
						}			
			})
			var buttons = [button_comment,button_checkcomm];
			var btnPanel = new Ext.Panel({buttonAlign : 'center',buttons:buttons});
			return btnPanel;
		},
		/**
		 * 评论内容面板
		 */
		getPanel2:function(){
			var htmlContent=""; 	
			var npanel=new Ext.Panel({
				renderTo:Ext.getBody(),
				title:"最新5条评论",
				collapsible : true,
				collapsed :true ,
				height:300,
				html:htmlContent
			});
			return npanel;
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
		},
		
		/**
		 * 评论表单
		 */
		createForm : function(){
			var txa_publishComm = FormUtil.getTAreaField({
			    fieldLabel: '发表评论',
			    name: 'publishComm',
			    "width": "600"
			});
			
			var layout_fields = [
				txa_publishComm
				];
			var frm_cfg = {
			    url: '#SAVEURL#'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform_1;
		},
		/**
		 * form表单上的按钮
		 */
		fromBtn : function(){
			var _this=this;
			var btn_publish=new Ext.Button({
				width : 80,
				text:"发表"
			});
			
			var btn_checkComment=new Ext.Button({
				width : 80,
				text:"查看所有评论"
			});
			
			var btn_close=new Ext.Button({
				width : 80,
				text:"关闭"
			});
			var buttons = [btn_publish,btn_checkComment,btn_close];
			var btnPanel = new Ext.Panel({buttonAlign : 'center',buttons:buttons});
			return btnPanel;
		
		},
		/**
		 * 把评论表单放到可收缩面板中
		 */
		getCommPal: function(){
			var _this=this;
			_this.appFrm.add(_this.fBtn);
			var cpanel=new Ext.Panel({
				title: '发布评论',
				renderTo:Ext.getBody(),
				collapsible : true,
				collapsed :true ,
				items:[_this.appFrm]
			});
			return cpanel;
		},
		/**
		 * 为表单元素赋值
		 */
		setFormValues : function(){
			
		},
		/**
		 * 上一条
		 */
		preData : function(){
			
		},
		/**
		 * 下一条
		 */
		nextData : function(){
			
		},
		/**
		 * 保存数据
		 */
		saveData : function(){
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		}
	};
});
