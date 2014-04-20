/**
 * 自定义的panel组建，添加了这个一个url属性js扩展函数
 */
Ext.namespace("Ext.ux.panel"); 
Ext.ux.panel.DetailPanel = Ext.extend(Ext.Panel, {
	apptbar : null,//工具栏
	detailPanels : [],	//存放详情面板的数组
	isLoad : true,/*是否立马加载数据*/
	detailHtml : null,
	panel : null,
	updateHtml : null,//把要更行的html 缓存起来！
	/**
	 * detailCfg 放的是 jsonObject 对象,如下所示：
	 * 
	 * { url : './sysMenu_list.action',	//从后台取数据的 url 
	 * 	 keyField : 'menuId',	//后台对应的主键ID
	 * 	 params : '',	//传入后对的过滤参数 jsonObject 对象
	 *   cmns : 'THREE',	// 布局列数 'THREE' : 一行排三个, 'TWO' : 一行排两个 , 'ONE' 或不提供此属性 ： 一行排一个 
	 * 	//数据展示模式，model 为 'single' 时只是显示单条详情数据，
	 * 	//为 'list' 时，以表格列表形时显示多条数据。默认为 "single" 
	 * 	model : 'single/list',
	 * 	labelWidth : 90, label 的宽度。默认为 90 像素
	 *  listWidths : [100,80,120,300] //当 model 为 list 时，为表头每列提供的宽度
	 * 	// callback 服务器端数据返回后的回调函数对象 jsonObject . 其包含： sfn 和 ffn 两个属性 
	 *   callback : {sfn : function(data){xx},ffn : function(data)} } ,	
	 *   title : '菜单详情',	//详情面板的标题
	 * 	 htmls : []		//详情模板 HTML 代码片断
	 * }
	 * 
	 * @type 
	 */
	detailCfgs : [],	//详情数据配置
	baseidprefix : null,
	idPrefixDef : {
		panelIdprefix : 'DETAILPANEL_',		//详情面板ID前缀
		contentDivIdPrefix : 'CONTENTDIV_'	//放表格内容在DIV ID前缀
	},
	globalCfgMgr : {},//全局配置管理
	activeBtnTxt : null,	//当前激活的按钮文本
	initComponent : function() {
		Ext.ux.panel.DetailPanel.superclass.initComponent.call(this);
		this.initialize();
    },  
	initEvents : function() {  
       	Ext.ux.panel.DetailPanel.superclass.initEvents.call(this);
       	this.initlisteners();
	},
	/**
	 * 组件初始化方法
	 */
	initialize : function(){
		this.baseidprefix = Ext.id();
		this.detailPanels = [];
		this.globalCfgMgr = {};
		this.buildCmpts();	//开始组装以上创建的所有组件
	},
	/**
	 * 初始化事件
	 */
	initlisteners : function(){
		var self = this;
	},
	/**
	 * 组装组件
	 */
	buildCmpts : function(){
		if(!this.checkBaseCfgs()) return;
		this.detailPanels = this.createDetailCmpts();
		if(!this.detailPanels || this.detailPanels.length == 0) return;
		this.add(this.detailPanels);
	},
	/**
	 * 检查 DetailPanel 基本属性配置是否存在
	 * @return {Boolean}
	 */
	checkBaseCfgs : function(){
		if(!this.detailCfgs || this.detailCfgs.length == 0){
			ExtUtil.error({title : "创建组件错误",msg : "创建DetailPanel 对象时，须提供  detailCfgs 属性配置！"});
			return false;
		}
		return true;
	},
	/**
	 * 更新面板
	 * @return {}
	 */
	upDateHtml :function(detailPlnTwo){
		var self =this;
		var xtype = detailPlnTwo.getXType();
		if(!xtype || xtype!="detailPanel") ExtUtil.error({title : "更新传参错误",msg:"传入的面板必须是详情面板，否则不能进行更行操作！！！"});
		if(this.detailHtml){
			var detailPlnTwoHtml = detailPlnTwo.detailCfgs[0].htmls;
			if(detailPlnTwoHtml){
				if(!this.updateHtml){
					var htmlAdd = [];
					if(Ext.isArray(this.detailCfgs[0].htmls)){
						htmlAdd[0] = this.detailCfgs[0].htmls.join(" ");
					}else{
						htmlAdd[0] = this.detailCfgs[0].htmls;
					}
					htmlAdd[1] = detailPlnTwoHtml.join(" ");
					htmlAdd = htmlAdd.join(" ");
					this.updateHtml = htmlAdd ;
					this.detailCfgs[0].htmls = this.updateHtml;
					var html = this.updateTempHtml(this.detailCfgs[0]);
					if(this.panel){
							this.panel.update(html,true,function(){
								self.initEles(self.panel,self.detailCfgs[0],true);
								var cmptId = self.panel.getId();
								if(self.isLoad) self.loadData(self.globalCfgMgr[cmptId]);
								self.panel.doLayout();
						});
					}
				}
			}
		}
	},
	/**
	 * 更新解析 HTML 模板
	 * @param {} detailCfg	详情数据配置
	 * @return {}
	 */
	updateTempHtml : function(detailCfg){
		var htmlArr = ["<div id='"+this.idPrefixDef.contentDivIdPrefix+detailCfg.id+"' class='txr_detail_fixcmncls'><table class='txr_detail_tabcls'>"];
		htmlArr[1] = this.getHeadTr(detailCfg);
		this.setFormDiyHtml(detailCfg);
		htmlArr[2] =  detailCfg.htmls;
		this.detailHtml = htmlArr[2];
		htmlArr[3] = "</table></div>";
		var htmlStr = htmlArr.join(" ");
		return htmlStr;
	},
	/**
	 * 创建详情组件
	 */
	createDetailCmpts : function(){
		var panels = [];
		for(var i=0,count=this.detailCfgs.length; i<count; i++){
			var detailCfg = this.detailCfgs[i];
			var detailCmpId = this.baseidprefix+"_"+(i+1);
			detailCfg.id = detailCmpId;
			panels[i] = this.createDetailCmpt(detailCfg);
		}
		return panels;
	},
	
	/**
	 * 创建详情面板
	 */
	createDetailCmpt : function(detailCfg){
		var html = this.parseTempHtml(detailCfg);
		this.detailHtml = html;
		var cfg = {frame:false,id:this.idPrefixDef.panelIdprefix+detailCfg.id ,html:html};		
		if(detailCfg.title){
			cfg.title = detailCfg.title;
		}
		var self = this;
		var panel = new Ext.Panel(cfg);
		panel.addListener('afterrender',function(cmpt){
			self.initEles(cmpt,detailCfg);
			var cmptId = cmpt.getId();
			if(self.isLoad) self.loadData(self.globalCfgMgr[cmptId]);
		});
		this.panel = panel;
		return panel;
	},
	initEles : function(cmpt,detailCfg,isNotUpdate){
		 var cmptId = cmpt.getId();
		 var contentDivId = this.idPrefixDef.contentDivIdPrefix+(cmptId.substring(cmptId.indexOf("_")+1));
		 var contentDiv;
	 	 contentDiv = Ext.get(contentDivId);
	 	 if(isNotUpdate){
	 	 	Cmw.print(contentDiv);
	 	 }
		 var eles = contentDiv.query("*[col]");
		 var labelEles = [];
		 var valEles = [];
		 for(var i=0,count=eles.length; i<count; i++){
			var ele  = eles[i];
			var tagName = ele.tagName;
			if(tagName == "TH" || tagName == "LABEL"){
				labelEles[labelEles.length] = ele;
			}else{
				valEles[labelEles.length] = ele;
			}
		 }
		 this.internationalizationLabels(labelEles,detailCfg);
		 this.globalCfgMgr[cmptId] = {panelId : cmptId,contentDivId : contentDivId, labelEles : labelEles, valEles : valEles,detailCfg:detailCfg};
	},
	/**
	 * 上一条数据
	 */
	prevData : function(btnTxt){
		this.activeBtnTxt = (btnTxt) ? btnTxt : URLCFG_KEYS.PREURLCFG;
		this.loadDatas(null);
	},
	/**
	 * 下一条数据
	 */
	nextData : function(btnTxt){
		this.activeBtnTxt = (btnTxt) ? btnTxt : URLCFG_KEYS.NEXTURLCFG;
		this.loadDatas(null);
	},
	/**
	 * 打印
	 * @param printType 打印方式 (1或null):打印预览，2:直接打印，3：打印设计
	 */
	print : function(printType){
		if(!this.globalCfgMgr){
			ExtUtil.error({msg:'没有可打印的内容！'});
			return;
		}
		var htmlArr = [];
		for(var key in this.globalCfgMgr){
			var cfgMgr = this.globalCfgMgr[key];
			var contentDiv = Ext.getDom(cfgMgr.contentDivId);
			if(!contentDiv) continue;
			var html = contentDiv.innerHTML;
			htmlArr[htmlArr.length] = html;
		}
		var htmlStr = htmlArr.join("");
		if(!printType || printType == 1){	//打印预览
			PrintManager.preview(htmlStr);
		}else if(printType == 2){	//直接打印
			PrintManager.print(htmlStr);
		}else if(printType == 3){	//打印设计
			PrintManager.design(htmlStr);
		}
		
	},
	/**
	 * 重新加载数据
	 * @param {} params	加载数据时所需的参数
	 * @param {} isLocal 是否加载本地JSON数据， 默认为 : false ；[false : 加载远程数据,true:加载本地传过来的 json 对象数据] 
	 */
	reload : function(params,isLocal){
		this.activeBtnTxt = null;	//重新加载数据，将 activeBtnTxt 标识重置
		this.loadDatas(params,isLocal);
		
	},
	/**
	 * 加载所有详情数组
	 */
	loadDatas : function(params,isLocal){
		if(!this.globalCfgMgr) return;
		this.isLoad = true;
		for(var key in  this.globalCfgMgr){
			var cfg = this.globalCfgMgr[key];
			this.parseParams(params, cfg);
			this.loadData(cfg,isLocal)
		}
	},
	/**
	 * 解析参数，将新的参数放入到 cfg 中
	 * @param {} params 要放置的新参数
	 * @param {} cfg
	 */
	parseParams : function(params,cfg){
		if(!params) return;
		var cache_params = cfg.detailCfg.params;
		if(!cache_params){
			cache_params = params;
		}else{
			Ext.apply(cache_params, params);
		}
		cfg.detailCfg.params = cache_params;
	},
	/**
	 * 加载数据
	 * @param {} cfg
	 */
	loadData : function(cfg,isLocal){
		var detailCfg = cfg.detailCfg;
		var url = this.getUrlByActiveBtnTxt(detailCfg);
		if(!url) return;
		var _cfg = {panelId:cfg.panelId,contentDivId:cfg.contentDivId,valEles : cfg.valEles};
		if(detailCfg.formDiyCfg){
			_cfg.formDiyCfg = detailCfg.formDiyCfg;
		}
		if(detailCfg.params){
			_cfg.params = detailCfg.params;
		}
		if(detailCfg.callback){
			_cfg.callback = detailCfg.callback;
		}
		if(isLocal) url = null;	/*如果加载本地JSON数据，则清空 URL*/
		this.setValues(this.activeBtnTxt,url,_cfg);
	},
	
	getUrlByActiveBtnTxt : function(detailCfg){
		var url = null;
		if(!this.activeBtnTxt){
			url = detailCfg.url;
		}else if(this.activeBtnTxt == URLCFG_KEYS.PREURLCFG){	//上一条
			url = detailCfg.prevUrl;
			if(!url){
				ExtUtil.error({msg:'在 \"'+detailCfg.title+'\"没有提供上一条的 URL属性 \"prevUrl\"，无法获取数据！'});
			}
		}else if(this.activeBtnTxt == URLCFG_KEYS.NEXTURLCFG){	//下一条
			url = detailCfg.nextUrl;
			if(!url){
				ExtUtil.error({msg:'在 \"'+detailCfg.title+'\"没有提供下一条的 URL属性 \"nextUrl\"，无法获取数据！'});
			}
		}
		return url;
	},
	/**
	 * 标签国际化处理
	 * @param {} labelEles  要国际华的标签元素
	 * @param {} detailCfg	详情配置对象。 detailCfg.i18n 国际化对象
	 */
	internationalizationLabels : function(labelEles,detailCfg){
		if(!labelEles || labelEles.length == 0) return;
		if(!detailCfg.i18n) return;
		for(var i=0,count=labelEles.length; i<count; i++){
			var labelEle = labelEles[i];
			var colName = labelEle.getAttribute("col");
			if(!colName) continue;
			if(!detailCfg.i18n[colName])continue;
			labelEle.innerHTML = detailCfg.i18n[colName]; 
		}
	},
	/**
	 * 解析 HTML 模板
	 * @param {} detailCfg	详情数据配置
	 * @return {}
	 */
	parseTempHtml : function(detailCfg){
		var htmlArr = ["<div id='"+this.idPrefixDef.contentDivIdPrefix+detailCfg.id+"' class='txr_detail_fixcmncls'><table class='txr_detail_tabcls'>"];
		htmlArr[1] = this.getHeadTr(detailCfg);
		this.setFormDiyHtml(detailCfg);
		htmlArr[2] =  detailCfg.htmls.join(" ");
		htmlArr[3] = "</table></div>";
		var htmlStr = htmlArr.join(" ");
		return htmlStr;
	},
	/**
	 * 设置自定义字段详情值
	 * @param {} detailCfg
	 */
	setFormDiyHtml : function(detailCfg){
		var formDiyCfg = detailCfg.formDiyCfg;
		if(!formDiyCfg) return;
		var maxCount = this.getMaxCount(detailCfg.cmns);
		var htmls = detailCfg.htmls;
		//sysId,formdiyCode,formIdName,dataProps
		var sysId = formDiyCfg.sysId;
		var formdiyCode = formDiyCfg.formdiyCode;
		var params = {sysId:sysId,formdiyCode:formdiyCode,maxCount:maxCount};
		var htmlsCfg = FormDiyManager.getDetailHtmlArr(params,htmls);
		detailCfg.htmls = htmlsCfg.htms;
		formDiyCfg.dataProps = htmlsCfg.dataProps;
		formDiyCfg.formdiyId = htmlsCfg.formdiyId;
	},
	getMaxCount : function(cmns){
		var maxcount = 3;
		switch(cmns){
			case 'ONE' : 
				maxcount = 1;
				break;
			case 'TWO' : 
				maxcount = 2;
				break;
			case 'THREE' : 
				maxcount = 3;
				break;
			default : 
			 maxcount = 3;
		}
		return maxcount;
	},
	/**
	 * 获取表头固定行
	 * @param {} detailCfg
	 */
	getHeadTr : function(detailCfg){
		/*noautoTdWidth : 自动计算每列单元格宽度，true : 不需要自动计算，false/undefined ：系统默认自动计算*/
		if(detailCfg.noautoTdWidth) return "";
		var headTrHtml = null;
		var cmns = null;
		var width = null;
		if(!detailCfg.model || detailCfg.model == 'single'){	//单条详情模式
			cmns = detailCfg.cmns || 'ONE';
			width = detailCfg.labelWidth || 90;
			headTrHtml = this.getHeadByCmns(cmns,width);
		}else{	// model : list 列表模式
			if(!detailCfg.listWidths){
				var errMsg = "须为  \""+detailCfg.title+"\" 提供  listWidths 属性值，该值是数组，主要用来为列表的每列提供宽度！";
				ExtUtil.error({title : "创建 title 为 \""+detailCfg.title+"\"详情面板发生错误",
				msg : errMsg});
				headTrHtml = "<div>"+errMsg+"</div>";
			}else{
				headTrHtml = this.getHeadByCmns(null,detailCfg.listWidths);
			}
		}
		return headTrHtml;
	},
	getHeadByCmns : function(cmns,widths){
		var headTrHtmls = ["<tr class='headtr'>"];
		if(!cmns){	//列表模式处理
			for(var i=0,count = widths.length; i<count; i++){
				headTrHtmls[headTrHtmls.length] = "<td class='txr_detail_fixcmncls' style='width:"+widths[i]+"px'><span>xxxx</span></td>";
			}
		}else{	//单条详情数据表头固定行
			var count = 0;
			if(cmns == 'ONE'){
				count = 2;
			}else if(cmns == 'TWO'){
				count = 4;
			}else if(cmns == 'THREE'){
				count = 6;
			}
			var valTdWidth = this.getValTdWidth(count,widths);
			var offset = 0;
			if(valTdWidth>0){	//当前详情面板对象提供了 width 值时，已知放内容的单元格宽度
				for(var i=0; i<count; i++){
					offset++;
					if(offset%2==0){// 数据内容 单元格
						headTrHtmls[headTrHtmls.length] = "<td class='txr_detail_fixcmncls' style='width:"+valTdWidth+"px'><span></span></td>";
					}else{	// label 单元格
						headTrHtmls[headTrHtmls.length] = "<td class='txr_detail_fixcmncls' style='width:"+widths+"px'><span></span></td>";
					}
				}	
			}else{
				for(var i=0; i<count; i++){
					offset++;
					if(offset%2==0){// 数据内容 单元格
						headTrHtmls[headTrHtmls.length] = "<td class='txr_detail_fixcmncls'><span></span></td>";
					}else{	// label 单元格
						headTrHtmls[headTrHtmls.length] = "<td class='txr_detail_fixcmncls' style='width:"+widths+"px'><span></span></td>";
					}
				}	
			}
		}
		headTrHtmls[headTrHtmls.length] = "</tr>";
		return headTrHtmls.join(" ");
	},
	getValTdWidth : function(cmnCount,labelWidth){
		if(!this.width) return 0;
		var _cmns = cmnCount/2;
		var _width = this.width - (_cmns * labelWidth);
		if(_width>0) return _width/_cmns;
		return 0;
	},
	/**
	 * 为表单元素赋值	
	 * 例: appForm.setFormValues('./sysMenu_list.action',	// --> 服务器请求数据时的 URL 地址 //
	 *     { params:{id:'1'},	// --> 服务器请求数据时，后台业务需要的参数值 //
	 *       sfn : function(jsonObj){xxcode ...}, // --> 处理向服务器请求数据成功后要执行的函数 "jsonObj" 服务器返回的 json 对象数据 //
	 * 	     ffn : function(errMsg){xxcode...},	// --> 处理向服务器请求数据失败后要执行的函数 "errMsg" 服务器返回的错误信息参数 //
	 * 	     defaultVal : {name : '企业客户管理',parentName : '客户管理'} // --> 为表单要赋的默认值 json 对象 [可选参数] //
	 *     }
	 * );
	 * @param url 要请求数据的 url 
	 * @param cfg 表单附加的参数对象 
	 */
	setValues : function(key,url,cfg){
		var self = this;
		if(self.apptbar) self.apptbar.disable();
		var contentDiv = Ext.get(cfg.contentDivId);
		contentDiv.mask('Loading', 'x-mask-loading');
		var action = {};
		var result = (cfg && cfg["params"]);
		if (result) {
			action.params = cfg.params;
		}
		//--> 当服务器成功返回数据后的回调函数，在回调函数中为表单赋值
		action.sfn = function(json_data){
			contentDiv.unmask();
			if(self.apptbar){
				self.apptbar.enable();
				if(key == URLCFG_KEYS.PREURLCFG){ //点上一条，如果下一条按钮禁用就启用
					var btns = self.apptbar.getSomeButtons(Btn_Cfgs.NEXT_BTN_TXT);
					if(btns && btns[0].disabled) self.apptbar.enableButtons(Btn_Cfgs.NEXT_BTN_TXT);
				}else if(key == URLCFG_KEYS.NEXTURLCFG){//下一条
					var btns = self.apptbar.getSomeButtons(Btn_Cfgs.PREVIOUS_BTN_TXT);
					if(btns && btns[0].disabled) self.apptbar.enableButtons(Btn_Cfgs.PREVIOUS_BTN_TXT);
				}
				if(json_data[CURSOR_MGR.CURSOR_KEY]){	//如果上一条是第一条或下一条是最后一条处理
					var cursorVal = json_data[CURSOR_MGR.CURSOR_KEY];
					if(CURSOR_MGR.CURSOR_FIRST == cursorVal){	//第一条处理
						self.apptbar.disableButtons(Btn_Cfgs.PREVIOUS_BTN_TXT);
					}else if(CURSOR_MGR.CURSOR_LAST == cursorVal){	//最后一条处理
						self.apptbar.disableButtons(Btn_Cfgs.NEXT_BTN_TXT);
					}
				}else{
					setAllDatas();
				}
			}else{
				setAllDatas();
			}
			self.attachLoad(cfg.params,key);
			
			function setAllDatas(){
				var formDiyCfg = cfg.formDiyCfg;
				if(formDiyCfg){
					FormDiyManager.copyFormDiyData2Detail(formDiyCfg,json_data,function(datas){
						json_data = datas;
						setDatas();
					});
				}else{
					setDatas();
				}
			}
			
			function setDatas(){
				if(cfg.callback && cfg.callback.sfn){
					var makeJsonData = cfg.callback.sfn(json_data);
					if(makeJsonData) json_data = makeJsonData;
				}
				//数据处理
				var valEles = cfg.valEles;
				self.setDatas(valEles,json_data);
				self.overWriteIdVal(cfg,json_data);
			}
		};
		//如果配有失败后处理函数，当数据处理失败后。将把错误信息反馈给错误处理函数
		action.ffn = function(msg){ 
			if(self.apptbar) self.apptbar.enable();
			if(cfg.callback && cfg.callback.ffn)cfg.callback.ffn(msg);
		};
		if(url){
			 EventManager.get(url,action);
		}else{
			if(cfg.params["json_data"]){	//本地JSON数据加载
				action.sfn(cfg.params["json_data"]);
			}
		}
	},
	/**
	 * 当初始或上一条，下一条获取数据时，
	 * 触发第三方组件加载数据的事件
	 * @param {} params 数据加载时的过滤参数	
	 * @param {} cmd	按钮点击命令，
	 * 		如上一条:URLCFG_KEYS.PREURLCFG，下一条URLCFG_KEYS.NEXTURLCFG
	 */
	attachLoad:function(params,cmd){
	},
	/**
	 * 重写 globalCfgMgr.detailCfg.params 中的ID值，使其能支持上一条，下一条数据的查找
	 * @param {} cfg	参数配置对象
	 * @param {} json_data	服务端返回的数据
	 */
	overWriteIdVal : function(cfg,json_data){
		var _params = cfg.params;
		for(var key in _params){
			if(!json_data[key]) continue;
			_params[key] = json_data[key];
		}
		var panelId = cfg.panelId;
		this.globalCfgMgr[panelId].detailCfg.params = _params;
	},
	/**
	 * 为所有详情元素赋值
	 * @param {} eles	要赋值的元素数组
	 * @param {} data	数据
	 */
	setDatas : function(eles, data){
		if(null == eles || eles.length == 0) return;
		for(var i=0,count=eles.length; i<count; i++){
			var ele = eles[i];
			if(!ele) continue;
			var colName = ele.getAttribute("col");
			if(!colName) continue;
			var val = (!data[colName]) ? "" : data[colName];
			if(ele.tagName == "IMAGE"){
				if(!val) val = "";
				ele.setAttribute("src",val);
			}else if(ele.tagName == "A"){
				if(!val) val = "";
				ele.setAttribute("href",val);
			}else if(ele.tagName == "INPUT"){
				if(!val) val = "";
				ele.setAttribute("value",val);
			}else{
				if(!val) val = "&nbsp;";
				ele.innerHTML = val;
			}
		}
	}
})
	
Ext.reg('detailPanel', Ext.ux.panel.DetailPanel);