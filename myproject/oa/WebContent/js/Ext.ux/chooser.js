/*!
 * Ext JS Library 3.4.0
 * Copyright(c) 2006-2011 Sencha Inc.
 * licensing@sencha.com
 * http://www.sencha.com/license
 */
var ImageChooser = function(config){
	this.config = config;
	this.url = "./sysIcon_list.action";
	this.syncpicUrl = "./sysIcon_rebuild.action";
	this.saveUrl = "./sysIcon_save.action";
	this.imgDetailPanelId = Ext.id(null,'img-detail-panel');
	this.pageInfoId = Ext.id(null,'pageInfoId');
	this.imgChooserViewId = Ext.id(null,'img-chooser-view');
	this.store = null;
	this.start = 0;
	this.limit = 24;
	this.selNode = null;
	/**
	 * 图标类型 iconType [1:标准48*48大图标]
	 */
	this.iconType_1 = 1;
	/**
	 * 图标类型 iconType [2:扁平大图标]
	 */
	this.iconType_2 = 2;
	/**
	 * 图标类型 iconType [3:小图标]
	 */
	this.iconType_3 = 3;
	this.currPage = 0;/*当前页*/
	this.selId = null;/*选中图像节点*/
	this.treePanel = null;
	this.uploadWin = null;
}

ImageChooser.prototype = {
    lookup : {},
	show : function(el, callback){
		if(!this.win){
			this.initTemplates();
			this.getStore();
			this.winBar = this.getToolBar();
			this.treePanel = this.getTreePnl(this.config);	
			var winCfg = this.createCfg();	
		    this.win = new Ext.ux.window.MyWindow(winCfg);
		}
	    this.win.show(el);
		this.callback = callback;
		this.animateTarget = el;
		if(this.win.rendered)this.reset();
	},
	createCfg : function(){
		var cfg = {
		    	title: this.config.title,
		    	id: 'img-chooser-dlg',
		    	layout: 'border',
				minWidth:  this.config.width,
				minHeight:  this.config.height,
				modal: true,
				closeAction: 'hide',
				border: false,
				items:[{
					region: 'west',
					split: true,	
					width: 250,
					minWidth: 180,
					maxWidth: 250,
					items : [this.treePanel,{
					id: this.imgDetailPanelId,
					title : "图片详情",
					region: 'center',
					width:  this.config.width/2-10,
					height:  280
					}]
				},{
					id: 'img-chooser-view',//this.imgChooserViewId,
					region: 'center',
					autoScroll: true,
					items: this.view,
                    tbar:this.winBar
				}]
			};
			Ext.apply(cfg, this.config);
			return cfg;
	},
	getTreePnl : function(){
		var _this = this;
		var tbar = this.createTreeBar();
		var treePanel = new Ext.tree.TreePanel({
			title : '图片目录',
			region: 'north',
			width:	this.config.width/2-10,
			height:  this.config.height-290,
			useArrows: true,
		    autoScroll: true,
		    animate: true,
		    tbar : tbar,
		    root: new Ext.tree.AsyncTreeNode({
            expanded: true,
            text : '图片目录',
	            children: [{
	            	id : this.iconType_3,
	                text: '小图标',
	                leaf: true
	            }, {
	            	id : this.iconType_1,
	                text: '标准48*48大图标',
	                leaf: true
	            }, {
	            	id : this.iconType_2,
	                text: '扁平大图标',
	                leaf: true
	            }]
	        }),
	        rootVisible: false
		});
		treePanel.addListener('click',function(node, e){
			_this.reset();
			_this.selNode = node;
			_this.reload();
		});
		return treePanel;
	},
	reset : function(){
		this.start = 0;
		this.limit = 24;
		this.currPage = 0;
		this.winBar.resets();
		var detailEl = Ext.getCmp(this.imgDetailPanelId).body;
		detailEl.update('');
		this.selId = null;
	},
	createTreeBar : function(){
		var _this = this;
		var tbar = new Ext.Toolbar({
			items : [{
					text : Btn_Cfgs.EXPAND_BTN_TXT, /*-- 展开 --*/
					iconCls:Btn_Cfgs.EXPAND_CLS,
					tooltip:Btn_Cfgs.EXPAND_TIP_BTN_TXT,
					handler : function(){
						_this.treePanel.root.expandAll();
					}
				},{
					text : Btn_Cfgs.COLLAPSE_BTN_TXT, /*-- 收起 --*/
					iconCls:Btn_Cfgs.COLLAPSE_CLS,
					tooltip:Btn_Cfgs.COLLAPSE_TIP_BTN_TXT,
					handler : function(){
						_this.treePanel.root.collapseAll();
					}
				},{
					text : Btn_Cfgs.REFRESH_BTN_TXT, /*-- 刷新 --*/
					iconCls:Btn_Cfgs.REFRESH_CLS,
					tooltip:Btn_Cfgs.REFRESH_TIP_BTN_TXT,
					handler : function(){
						_this.treePanel.root.reload();
					}
				},{
					text : Btn_Cfgs.SYNCHRONOUSPIC_BTN_TXT, /*-- 同步图片 --*/
					iconCls:Btn_Cfgs.SYNCHRONOUSPIC_CLS,
					tooltip:Btn_Cfgs.SYNCHRONOUSPIC_TIP_BTN_TXT,
					handler : function(){
					  EventManager.get(_this.syncpicUrl,{params:{},sfn:function(json_data){
					  	_this.reload();
					  	Ext.tip.msg(Msg_SysTip.title_appconfirm, Msg_SysTip.msg_synchronouspic);
					  }});
					}
				}]
		});
		return tbar;
	},
	getToolBar : function(){
		var _this = this;
		var barItems = [{type:'label',text:'图片名称'},
			{
				type : 'txt',
				name : 'fileName'
			},{
				text : Btn_Cfgs.QUERY_BTN_TXT,
				iconCls:'page_query',
				tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
				handler : function(){
					_this.start = 0;
					_this.limit = 24;
					_this.currPage = 0;
					_this.reload();
				}
			},{
				text : Btn_Cfgs.CONFIRM_BTN_TXT,/*确定*/
				iconCls:Btn_Cfgs.CONFIRM_CLS,
				tooltip:Btn_Cfgs.CONFIRM_TIP_BTN_TXT,
				handler : function(){
					_this.doCallback();
				}
			},{
				text : "上一页",
				iconCls:'page_prev',
				tooltip:"上一页",
				handler : function(){
					_this.gotoPage(-1);
				}
			},{
				text : "下一页",
				iconCls:'page_nexty',
				tooltip:"下一页",
				handler : function(){
					_this.gotoPage(1);
				}
			},{
				text : "上传文件",
				iconCls:'page_upload',
				tooltip:"上传文件",
				handler : function(){
					_this.openUploadWin();
				}
			},{
				text : "删除",
				iconCls:'page_delete',
				tooltip:"删除",
				handler : function(){
					_this.del();	
				}
			},{type:'label',id:this.pageInfoId,text:'<span>当前[1至24]张|共:[100]张</span>',Colon:"&nbsp;"}];
		return  new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
	},
	/**
	 * 删除图片
	 */
	del : function(){
		 EventManager.deleteData('./sysIcon_delete.action',{cmpt:this,optionType:OPTION_TYPE.DEL,self:this});
	},
	
	/**
	 * 打开附件上传窗口
	 */
	openUploadWin : function(eleId){
		if(!this.selNode){
			ExtUtil.alert({msg:'请从左边选择图片所存放的目!'});
			return;
		}
		if(!this.uploadWin){
			this.uploadWin = this.createUploadWin();
		}
		
		this.uploadWin.params = this.getUploadParams();
		this.uploadWin.show();
	},
	/**
	 * 创建附件上传窗口
	 */
	createUploadWin : function(){
		var _this = this;
		var params = this.getUploadParams();
		params.sfn = function(fileInfos){
			if(!fileInfos || fileInfos.length == 0) return;
			_this.saveIcon(fileInfos[0]);
		};
		var uploadWin = new Ext.ux.window.MyUpWin(params);
		return uploadWin;
	},
	saveIcon : function(fileInfo){
		var _this = this;
		var iconType = this.selNode.id;
		var fileName = fileInfo.custName;
		var filePath = fileInfo.serverPath;
		var fileSize = fileInfo.size;
		var data = {iconType:iconType,fileName:fileName,filePath:filePath,fileSize:fileSize};
		EventManager.get(this.saveUrl,{params:data,sfn:function(json_data){
			_this.reload();
		}});
	},
	/**
	 * 获取上传参数
	 * @return {}
	 */
	getUploadParams : function(){
		var params = {};
		var dir = null;
		var iconType = this.selNode.id;
		switch(iconType){
			case this.iconType_1 :
				dir = "bigImg_path";
			break;
			case this.iconType_2 :
				dir = "flatImg_path";
			break;
			case this.iconType_3 :
				dir = "smallImg_path";
			break;
			default :
			 ExtUtil.alert({msg:"["+iconType+"]是无法解析的目录类型!"});
		};
		params.dir = dir;
		params.allowFiles = "allowFiles_pic_ext";
		params.isSave = false;
		return params;
	},
	getSelId : function(){
		if(!this.selId){
			ExtUtil.alert({msg:'请选择图片!'});
			return null;
		}
		return this.selId;	
	},
	refresh : function(){
		this.reset();
		this.reload();
	},
	/**
	 * 重新加载数据
	 */
	reload : function(_params){
		var params = {};
		if(_params) Ext.apply(params, _params);
		var iconType = this.iconType_3;/*默认加载小图标*/
		if(this.selNode){
		 	iconType = this.selNode.id;
		}
		var barVals = this.winBar.getValues();
		if(barVals) Ext.apply(params, barVals);
		params["iconType"] = iconType;
		params["start"] = this.start;
		params["limit"] = this.limit;
		var _this = this;
		var maskCmpt = Ext.getCmp('img-chooser-view');//this.imgChooserViewId);
		Cmw.mask(maskCmpt,'图片加载中,请稍等...');
		this.winBar.disable();
		this.store.load({params : params,callback:function(){
			_this.calculatePage();
			Cmw.unmask(maskCmpt);
		}});
	},
	calculatePage : function(){
		this.winBar.enable();
		var totalCount = this.store.getTotalCount();
		var currCount = this.store.getCount();
		var pageInfo = 	'当前[0至0]张|共:[0]张';
		if(!currCount || currCount === 0){
			this.winBar.disOrableBtns(true,"上一页,下一页");
		}else{
			var disPrev = (!this.currPage || this.currPage==0);
			var disNext = (currCount < this.limit);
			var currTotalCount = this.currPage * this.limit;
			if(currTotalCount && currTotalCount >= totalCount){
				disNext = true;
				disPrev = false;
			}
			this.winBar.disOrableBtns(disPrev,"上一页");
			this.winBar.disOrableBtns(disNext,"下一页");
			pageInfo = 	'当前['+(currTotalCount+1)+'至'+(currTotalCount+currCount)+']张|共:['+totalCount+']张';
		}
		Ext.getCmp(this.pageInfoId).update("<span style='color:blue;font-weight:bold;'>"+pageInfo+"</span>");
	},
	/**
	 * 翻页
	 */
	gotoPage : function(num){
		this.currPage+=num;
		if(this.currPage<0) this.currPage = 0;
		var _start = this.currPage * this.limit;
		this.start = _start;
		this.reload();
	},
	getStore : function(){
	 	var url = this.url;
	 	var reader = new Ext.data.JsonReader({
			totalProperty : "totalSize",
			root : "list",
			id : "id",
		    fields: [
		    "id",
		    {name:'name',mapping:'fileName'},
		    {name : 'url',mapping:'filePath'},
		    {name:'size', mapping:'fileSize', type: 'float'},
		    {name:'iconType', mapping:'iconType', type: 'int'},
		    {name:'lastmod', mapping:'lastmod'}
		    ]
			});
	 	this.store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({url : url}),
			  	reader : reader,
			    listeners: {
			    	'load': {fn:function(){ this.view.select(0);}, scope:this, single:true}
			    }
		});
		
		var formatSize = function(data){
	        if(data.size < 1024) {
	            return data.size + " bytes";
	        } else {
	            return (Math.round(((data.size*10) / 1024))/10) + " KB";
	        }
	    };
		var formatData = function(data){
	    	data.shortName = data.name.ellipse(15);
	    	data.sizeString = formatSize(data);
	    	//data.dateString = new Date(data.lastmod).format('Y-m-d');
	    	this.lookup[data.id] = data;
	    	return data;
	    };

	    this.view = new Ext.DataView({
				tpl: this.thumbTemplate,
				singleSelect: true,
				overClass:'x-view-over',
				itemSelector: 'div.thumb-wrap',
				emptyText : '<div style="padding:10px;">无图像匹配指定过滤器</div>',
				store: this.store,
				listeners: {
					'selectionchange': {fn:this.showDetails, scope:this, buffer:100},
					'dblclick'       : {fn:this.doCallback, scope:this},
					'loadexception'  : {fn:this.onLoadException, scope:this},
					'beforeselect'   : {fn:function(view){
				        return view.store.getRange().length > 0;
				    }}
				},
				prepareData: formatData.createDelegate(this)
			});
	 },
	
	/**
	 * 初始化模板
	 */
	initTemplates : function(){
		this.thumbTemplate = new Ext.XTemplate(
			'<tpl for=".">',
				'<div class="thumb-wrap" id="{id}">',
				'<div class="thumb"><img src="{url}" title="{name}" onload="Cmw.resizeImage(this,60)"></div>',
				'<span>{shortName}</span></div>',
			'</tpl>',
			'<div class="x-clear"></div>'
		);
		this.thumbTemplate.compile();
		/**
		 * 图片详情模板
		 */
		this.detailsTemplate = new Ext.XTemplate(
					'<div class="imgdetails-info"><img src="{url}"  onload="Cmw.resizeImage(this,120)">',
					'<b>图片名称:</b>{name}',
					'<b>图片大小:{sizeString}</b>',
					'<b>最后修改时间:{lastmod}</b>',
					'</div>'
		);
		this.detailsTemplate.compile();
	},
	/**
	 * 显示详情模板
	 */
	showDetails : function(){
	    var selNode = this.view.getSelectedNodes();
	    var detailEl = Ext.getCmp(this.imgDetailPanelId).body;
		if(selNode && selNode.length > 0){
			selNode = selNode[0];
			this.selId = selNode.id;
		    var data = this.lookup[selNode.id];
            detailEl.hide();
            this.detailsTemplate.overwrite(detailEl, data);
            detailEl.slideIn('l', {stopFx:true,duration:.2});
		}else{
		    detailEl.update('');
		    this.selId = null;
		}
	},
	doCallback : function(){
		if(!this.getSelId()) return;
        var selNode = this.view.getSelectedNodes()[0];
		var callback = this.callback;
		var lookup = this.lookup;
		this.win.hide(this.animateTarget, function(){
            if(selNode && callback){
				var data = lookup[selNode.id];
				callback(data);
			}
		});
    },
	onLoadException : function(v,o){
	    this.view.getEl().update('<div style="padding:10px;">加载图片出错...</div>');
	}
};

String.prototype.ellipse = function(maxLength){
    if(this.length > maxLength){
        return this.substr(0, maxLength-3) + '...';
    }
    return this;
};

/**
 * 单体选择模式
 * ImgChooserLazySingleton.getInstace();
 */
var ImgChooserLazySingleton = function(){
 	var cfg = {
			title : "图片管理",
			width:950, 
			height:450
		};
	var  chooser = new ImageChooser(cfg);
    function init(){
        return chooser;
    }
    return {getInstace: init};
}();