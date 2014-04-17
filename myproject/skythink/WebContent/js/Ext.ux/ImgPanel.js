/**
 * 封装 Ext.form.TriggerField 控件
 * 返回一个含有图片列表的控件 
 *  xtype : appcomboxtree
 */
Ext.namespace("Ext.ux");

Ext.ux.ImgPanel = Ext.extend(Ext.Panel,{
	width : 300,
	height:300,
	url:null,
	params : {},
	isLoad : true,
	selEle : null,
	selval : null,
	imgIsRender : false,
	style : {
		float : 'left'
	},
	autoScroll : true,
	initComponent : function(){
		Ext.ux.ImgPanel.superclass.initComponent.call(this);
		this.init();
	 },
	 initEvents : function() {  
	     Ext.ux.ImgPanel.superclass.initEvents.call(this);
	     this.initListeners();
	},
	init : function(){
		if(this.isLoad) this.loadData();
	},
	initListeners : function(){
		var self = this;
		this.addListener("show",function(){
			
		});
	},
	/**
	 * 为下拉框树赋值
	 * @param {} vals 要赋的值
	 */
	setValue : function(vals){
		Ext.ux.ImgPanel.superclass.setValue.call(this,this.seltxts);
	},
	/**
	 * 当 isAll ： true 时，将会把 ID 和	文本一起拼接返回。
	 * 其格式如下： 1001,1002##研发一部,研发二部
	 * @return {String} 返回选中的节点的ID或 (ID和文本一起返回)
	 */
	getValue : function(){
	},
	/**
	 * 重置
	 */
	reset : function(){
		this.params = {};
		if(this.selEle && Ext.isString(this.selEle)){
			this.selEle = Ext.get(this.selEle);
		}
		this.selEle.removeClass('selected_sysbox_img');
		this.selEle = null;
		this.selval = null;
	},
	/**
	 * 选中指定的元素，如果似入的元素ID为空。则选中上一次的元素对象
	 * @param {} eleId	要选中的元素ID
	 */
	select : function(eleId){
		if(eleId){
			this.selEle = Ext.get(eleId);
		}else{
			if(this.selEle && Ext.isString(this.selEle)){
				this.selEle = Ext.get(this.selEle);
			}
		}
		if(this.selEle){
			 var dom = Ext.get(this.selEle.id);
			 if(!dom) return;
			this.selEle.addClass('selected_sysbox_img');
		}
	},
	/**
	 * 设置查询参数
	 * @param {} params
	 */
	setParams : function(params){
		this.params = params;
	},
	/**
	 * 加载数据
	 */
	loadData : function(eachCallback,params){
		var items = this.items;
		var flag = false;
		if((items && items.length > 0) && eachCallback){
			for(var i=0,count=items.length; i < count; i++){
				flag = eachCallback(items.get(i));
				if(flag) break;
			}
			if(flag) return;
		}
		var _this = this;
		if(params){
			Ext.apply(this.params,params);
		}
		this.isLoad = true;
		EventManager.get(this.url,{params : this.params,sfn:function(json_data){
			if(_this.tbar) _this.getTopToolbar().disable();
			_this.removeAll();
			if(null == json_data.totalSize || json_data.list.length == 0) return;
			var list = json_data.list;
			for(var i=list.length-1; i>=0; i--){
				var imgId = Ext.id(null,'appImg_');
				var data = list[i];
				if(!_this.imgIsRender && eachCallback){
					eachCallback({id:imgId,data:data});
				}
				var boxHtml = "<div class='system_box'><img src='"+data.icon+"'/><br/><span>"+data.name+"</span></div>";
				var imgBox = new  Ext.BoxComponent({
					id:imgId,
					html : boxHtml,
					data : data,
					style : {float:'left',paddingLeft : '15px'},
					listeners:{
				      'render': function(cmpt){
				      		_this.imgIsRender = true;
				      		if(eachCallback) eachCallback(cmpt);
				      		var ele = cmpt.el;
				      		ele.on({
				      			'click' : {
				      				fn : function(){
										if(_this.selEle && Ext.isString(_this.selEle)){
											_this.selEle = Ext.get(_this.selEle);
										}
										if(_this.selEle){
				      						_this.selEle.removeClass('selected_sysbox_img');
				      					}
				      					ele.addClass('selected_sysbox_img');
				      					_this.selEle = ele;
				      					if(_this.tbar) _this.getTopToolbar().enable();
				      					_this.selval = cmpt.data;
				      				}
				      			}
				      		});
				      }
				    }
				});
				
				_this.add(imgBox);
				_this.doLayout();
			}
		}});
		this.isLoad = true;
	}
});
//注册成xtype以便能够延迟加载   
Ext.reg('imgpanel', Ext.ux.ImgPanel);

/**
 * 封装 Ext.Panel 控件
 * 返回一个含有扁平图片的面板 
 *  xtype : flatimgpanel
 */
Ext.ux.FlatImgPanel = Ext.extend(Ext.Panel,{
	//width : 300,
	url:null,
	params : {},
	dataArr : null,
	eventMgr : {
		clickCallback : null /*菜单点击回调函数*/
	},
	bodyCssClass : 'flat_content',
	/*json {菜单图标 li ID ：对应的菜单数据,....}
	 * 对应的菜单数据json 格式说明:
	 * 	菜单ID,ts_NodeCfg ID,是否已做,菜单名称,菜单URL,参数,小图标,大图标,按钮权限,图像IMG ID
	 *	id,nodeId,finish,text,jsArray,params,icon,bicon,modDatas,flatImgId
	 * */
	cachMenuDatas : null,
	selEle : null,/*选中的菜单元素*/
	selData : null,
	isLoad : false,
	idMgr : null,/*ID管理器*/
	liTagIdPrefix : 'float_liId_',/*li ID标记前缀*/
	imgTagIdPrefix : 'float_li_imgId_',/*Img ID标记前缀*/
	classMgr : {
		cls_flat_content : 'flat_content',
		cls_flat_img : 'flat_img',
		cls_select_bussform_ele : 'select_bussform_ele',
		cls_block_buss_finish : 'block_buss_finish',
		cls_flat_icon_text : 'flat_icon_text',
		cls_flat_buss_clear : 'flat_buss_clear'
	},
	autoScroll : true,
	initComponent : function(){
		Ext.ux.FlatImgPanel.superclass.initComponent.call(this);
		this.init();
	 },
	 initEvents : function() {  
	     Ext.ux.FlatImgPanel.superclass.initEvents.call(this);
	     this.initListeners();
	},
	init : function(){
		this.initIdMgr();
		if(this.isLoad){
			var _this = this;
			if(this.rendered){
				this.loadData();
			}else{
				this.addListener('afterrender',function(){
					_this.loadData();
				})
			}
		}
	},
	initIdMgr : function(){
		this.idMgr = {
			flatContentId : Ext.id(null,'flat_contentId'),/*扁平图标DIV容器ID*/
			ulbussformId : Ext.id(null,'ulbussformId')/*扁平图标ul ID*/
		};
	},
	initListeners : function(){
		var self = this;
		this.addListener("show",function(){
			
		});
	},
	loadData : function(){
		if(this.dataArr){
			this.createImgItems(this.dataArr);
		}else{
			if(this.url){
				ExtUtil.alert({msg:'加载扁平大图标时，必要提供 "url" 参数'});
				return;
			}
			var _this = this;
			EventManager.get(this.url,{params : this.params,sfn:function(json_data){
				if(null == json_data.totalSize || json_data.list.length == 0) return;
				var list = json_data.list;
				_this.createImgItems(list);
			}});
		}
	},
	/**
	 * 根据数组数据添加扁平图标组件
	 * @param {} dataArr
	 */
	createImgItems : function(dataArr){
		this.removeAlls();
		if(!dataArr || dataArr.length == 0) return;
		var htmlArr = [
			'<ul id="'+this.idMgr.ulbussformId+'">  '
		];
		this.cachMenuDatas = {};
		for(var i=0,count=dataArr.length; i<count; i++){
			var itemData = dataArr[i];
			var liEleId = Ext.id(null,this.liTagIdPrefix);
			var imgEleId = Ext.id(null,this.imgTagIdPrefix);
			var tempHtml = this.getImgHtmlTemp(itemData, liEleId, imgEleId);
			htmlArr[htmlArr.length] = tempHtml;
			itemData["flatImgId"] = liEleId; 
			this.cachMenuDatas[liEleId] = itemData;
		}
		htmlArr[htmlArr.length] = '</ul>';
		var htmlDatas = htmlArr.join("");
		var _this = this;
		this.update(htmlDatas,false,function(){
			_this.bindImgItemListeners();
		});
	},
	/**
	 * 为 Img Li 绑定事件
	 */
	bindImgItemListeners : function(){
		if(!this.cachMenuDatas){
			ExtUtil.alert({msg:'参数 "cachMenuDatas" 为空，无法绑定事件，错误发生在 "bindImgItemListeners()" 方法'});
			return;
		}
		var _this = this;
		for(var liId in this.cachMenuDatas){
			var itemData = this.cachMenuDatas[liId];
			var flatImgId = itemData["flatImgId"];
			var finish = itemData["finish"]; /*是否已做完事项*/
			var liEle = Ext.get(liId);
			var flatImgEle = Ext.get(flatImgId);
			if(finish){
				this.setFinish(liId);
			}
			liEle.on('click',function(e,t,o){
				_this.menuClick(t);
			}); 
		}
	},
	menuClick : function(htmlElement){
		var selCls = this.classMgr.cls_select_bussform_ele;
		if(this.selEle) this.selEle.removeClass(selCls);
		var currEle = Ext.get(htmlElement);
		var eleId = currEle.id;
		var flag = false;
		do{
			if(eleId.indexOf(this.liTagIdPrefix) == -1){
				currEle = currEle.parent();
				eleId = currEle.id;
			}else{
				flag = true;
				break;
			}
		}while(!flag);
	 	currEle.addClass(selCls);
		this.selEle = currEle;
		this.selData = this.cachMenuDatas[eleId];
		if(this.eventMgr.clickCallback) this.eventMgr.clickCallback(eleId,this.selData);
	},
	/**
	 * 取消菜单选中
	 */
	unSelect : function(){
		if(!this.selEle) return;
		var selCls = this.classMgr.cls_select_bussform_ele;
		this.selEle.removeClass(selCls);
		this.selEle = null;
		this.selData = null;
	},
	/**
	 * 将菜单标记为已完成
	 * @param {} flatImgEle	要标记为已完成的菜单图标对象
	 */
	setFinish : function(liId){
		var liEle = Ext.get(liId);
		if(liEle.hasClass(this.classMgr.cls_block_buss_finish)) return;
		liEle.addClass(this.classMgr.cls_block_buss_finish);
		var dom = liEle.dom;
		var title = dom.title;
		dom.title = title + "(已做)";
	},
	/**
	 * 判断指定的菜单是否为已完成。
	 *  
	 * @param {} liId 要检查的菜单元素ID
	 * @return true : 该业务菜单已做, false : 未做
	 */
	isFinish : function(liId){
		var liEle = Ext.get(liId);
		if(!liEle) return false;
		return (liEle.hasClass(this.classMgr.cls_block_buss_finish));
	},
	/**
	 * 将已完成的菜单标记为未完成
	 * @param {} flatImgEle	要标记为已完成的菜单图标对象
	 */
	setUnFinish : function(liId){
		var liEle = Ext.get(liId);
		if(!liEle.hasClass(this.classMgr.cls_block_buss_finish)) return;
		liEle.removeClass(this.classMgr.cls_block_buss_finish);
		var dom = liEle.dom;
		var title = dom.title;
		if(title){
			title = title.replace("(已做)","");
		}
		dom.title = title;
	},
	/**
	 * 获取单个扁平化图标HTML 模板数据
	 * @param {} itemData
	 * @param {} liEleId
	 * @param {} imgEleId
	 * @return {}
	 */
	getImgHtmlTemp : function(itemData,liEleId,imgEleId){
		var text = itemData["text"];
		var bicon = itemData["bicon"];
		var arr = [
		'<li id="'+liEleId+'" title="'+text+'">',
	      '<div id="'+imgEleId+'" class="'+this.classMgr.cls_flat_img+'">',
	       '<img src="'+bicon+'" style="border:0px;width:64px;height:64px;">',
	     ' </div>',
	      '<a class="'+this.classMgr.cls_flat_icon_text+'" href="javascript: void(0)"><span>'+text+'</span></a>',
		'</li>'
		];
		return arr.join(" ");
	},
	removeAlls : function(){
		this.removeAll();	
	}
});
//注册成xtype以便能够延迟加载   
Ext.reg('flatimgpanel', Ext.ux.FlatImgPanel);