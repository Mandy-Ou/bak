/**
 * 封装 FieldSet 控件
 * 使其支持附件的显示 
 *  xtype : appattachmentfs
 */
Ext.namespace("Ext.ux");

Ext.ux.AppAttachmentFs = Ext.extend(Ext.form.FieldSet,{
	title : '附件列表',
	labelTitle : null,/*缓存旧标题*/
	frame : true,
	collapsible:true,
	anchor:'98%',
	style:'padding:0px;margin:5px;padding-top:2px;padding-left:5px;margin-bottom:5px;',
	uploadUrl : null,/*上传的URL */
	uploadWin : null,/*附件上传窗口*/
	url : './sysAttachment_items.action',
	delUrl : './sysAttachment_delete.action',/*删除的URL*/
	downLoadUrl : './sysAttachment_download.action',/*下载附件URL*/
	viewUrl : './ConvertServlet',/*文檔轉換 Servlet*/
	updateUrl : './sysAttachment_update.action',/*更新临时附件的URL*/
	params : null,//{sysId:3,formType:1,formId:'80,81'},/*参数*/
	dir : 'mortgage_path',/*附件存放目录,默认放在抵/质押物文件夹*/
	allowFiles_ext : 'allowFiles_ext',
	isLoad : true,/*是否初始化就加载附件*/
	isSave : true,/*是否保存附件*/
	events : null,/*自定义事件,供以后扩展用*/
	ids : null,/*存放所有后台传过来的附件ID值 Array 对角*/
	IdMgr : null,
	datas : null,/*存放附件数据的数组*/
	isNotDisenbaled : true,
	DisenbaledBtn : true,//禁用所有的按钮
	idArr : null,
	initComponent : function(){
		this.init();
		Ext.ux.AppAttachmentFs.superclass.initComponent.call(this);
	 },
	/**
	 * 创建组件
	 */
	init : function(){
		var _this = this;
		this.initIdMgr();
		this.labelTitle = this.title;
		var titleEleHtmls = this.getTitleElesHtml();
		this.title = this.labelTitle + titleEleHtmls;
		this.html = this.getContainerHtml();
		this.addListener('afterrender',function(fieldSet){
			_this.attachListenerToTitleImgs();
			_this.load();
		});
	},
	/**
	 * 初始化 ID管理器对象
	 */
	initIdMgr : function(){
		this.IdMgr =  {
			/*附件总数显示的Span对象ID*/
			totalCountSpanId : Ext.id(null, 'totalCount_label'),
			/*批量上传的 img 对象ID*/
			batchuploadImgId :  Ext.id(null, 'batchupload_img'),
			/*预览全部的 img 对象ID*/
			viewallImgId :  Ext.id(null, 'viewall_img'),
			/*删除全部的 img 对象ID*/
			deleteallImgId :  Ext.id(null, 'deleteall_img'),
			/*附件容器 DIV 对象ID*/
			containerDivId : Ext.id(null, 'attchments_container'),
			/*附件DIV项ID前缀*/
			attchmentItemGpPrefix : 'attchmentitem_div_',
			/*附件超链接项ID前缀*/
			attchmentItemPrefix : 'attchmentitem_href_',
			/*附件下载 img 对象前缀*/
			attchmentItemDownloadPrefix : 'attchmentitemdownload_img_',
			/*附件删除 img 对象前缀*/
			attchmentItemDeletePrefix : 'attchmentitemdelete_img_'
		};
	},
	getTitleElesHtml : function(){
		var htmlArr = [
			'<span style="margin-left:5px;margin-right:10px;">(<span id="'+this.IdMgr.totalCountSpanId+'" style="color:red;font-weight:bold;">0</span>个附件)</span>',
			'<img id="'+this.IdMgr.batchuploadImgId+'" class="fd_img_attchement" src="images/public/upload.png" title="上传附件" />'
			/*'&nbsp;&nbsp;<img id="'+this.IdMgr.viewallImgId+'" class="fd_img_attchement" src="images/public/print-preview.png" title="预览全部附件"/>',*/
		];
		if(!this.isNotDisenbaled){
			htmlArr.push(['&nbsp;&nbsp;<img id="'+this.IdMgr.deleteallImgId+'" class="fd_img_attchement" src="images/public/Trash_Delete.png"  title="删除全部附件"/>'])
		}
		return htmlArr.join("");
	},
	getContainerHtml : function(){
		var htmlArr = [
			'<div id="',
			this.IdMgr.containerDivId,
			'" class="fd_main_attchements">',
			this.getNoDataHtml(),
			'</div>'
		];
		return htmlArr.join("");
	},
	getNoDataHtml : function(){
		return '<span style="text-align:center;font-weight:18px;color:red;">暂无任何附件</span>';
	},
	/**
	 * 清除所有附件元素对象
	 */
	clearAll : function(){
		this.setTotalCount(0);
		this.datas = null;
		this.clearAttachmentEles();
	},

	/**
	 * 打开附件上传窗口
	 */
	openUploadWin : function(eleId){
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
		var cfg = this.uploadUrl ? {url:this.uploadUrl} : {};
		var params = {};
		if(this.dir) params.dir = this.dir;
		if(this.isSave) params.isSave = this.isSave;
		cfg.params = this.getUploadParams();
		cfg.hideCallback = function(){
			_this.reload();	
		};
		var uploadWin = new Ext.ux.window.SwfUploadWindow(cfg);
		return uploadWin;
	},
	/**
	 * 获取上传参数
	 * @return {}
	 */
	getUploadParams : function(){
		var params = {};
		if(this.dir) params.dir = this.dir;
		if(this.isSave) params.isSave = this.isSave;
		if(!this.params){
			ExtUtil.error({msg:"在使用 AppAttachmentFs 附件对象时，必须提供 params 参数，其格式如下：{sysId:3,formType:1,formId:'80,81'}"});
			return null;
		}
		Ext.apply(params,this.params);
		return params;
	},
	/**
	 * 加载数据
	 */
	load : function(){
		if(!this.isLoad) return;
		var _this = this;
		this.clearAll();
		EventManager.get(this.url,{params:this.params,sfn:function(json_data){
			var totalSize = json_data.totalSize;
			if(!totalSize || totalSize === 0) return;
			_this.datas = [];
			var list  = json_data.list;
			_this.datas = list;
			var attachmentsArr = [];
			var idArr = [];
			for(var i=0; i<totalSize; i++){
				var data = list[i];
				idArr[idArr.length] = data.id;
				var userId = data.userId;
				attachmentsArr[i] = _this.creatAttachment(data); 
			}
			var container = Ext.get(_this.IdMgr.containerDivId);
			if(container){
				container.update(attachmentsArr.join(""),true,function(){
					_this.attachListenerToItems(idArr);	
//					_this.disDelBtn(idArr);
				});
			}
			_this.setTotalCount(totalSize);
			if(_this.events && _this.events.loadCallback){
				_this.events.loadCallback();
			}
		}});
	},
	/**
	 * var params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_5,sysId:sysId,isNotDisenbaled:true};
    	_this.attachMentFs.reload(params);
	 * 详情面板禁用删除按钮
	 */
	disDelBtn :function(idArr){
		var _this = this;
		if(_this.isNotDisenbaled){
			if(_this.idArr){
				for(var i=0;i<_this.idArr.length;i++){
					var deleteEle = Ext.get(_this.IdMgr.attchmentItemDeletePrefix+_this.idArr[i]);
					if(deleteEle) deleteEle.hide();
				}
			}else{
				for(var i=0;i<idArr.length;i++){
					var deleteEle = Ext.get(_this.IdMgr.attchmentItemDeletePrefix+idArr[i]);
						if(deleteEle) deleteEle.hide();
				}
			}
		}
		_this.idArr = idArr;
	},
	/**
	 * 添加事件到标题上的 上传 预览全部  删除全部 Img 标记上
	 * @param {} idArr
	 */
	attachListenerToTitleImgs : function(){
		var _this = this;
			/*-- step 1 : 上传 --*/
			var aEle = Ext.get(this.IdMgr.batchuploadImgId);
			if(aEle){
//				if(this.DisenbaledBtn){
//					aEle.hide();
//				}
				aEle.on('click',function(e,t,o){
					_this.openUploadWin(t.id);
				},this);
			}
			
			/*-- step 2 : 预览全部 --*/
			var downloadEle = Ext.get(this.IdMgr.viewallImgId);
			if(downloadEle){
				downloadEle.on('click',function(e,t,o){
					_this.viewAll(t.id);
				},this);
			}
			
			/*-- step 3 : 删除全部 --*/
			var deleteEle = Ext.get(this.IdMgr.deleteallImgId);
			if(deleteEle){
//				if(this.DisenbaledBtn){
//					deleteEle.hide();
//				}
				deleteEle.on('click',function(e,t,o){
					_this.deleteAll();
				},this);
			}
	},
	/**
	 * 添加事件到附件项上
	 * @param {} idArr
	 */
	attachListenerToItems : function(idArr){
		if(null == idArr || idArr.length == 0){
			if(!this.ids) this.ids = null;
			return;
		}else{
			this.ids = idArr;
		}
		var _this = this;
		for(var i=0,count=idArr.length; i<count; i++){
			var id = idArr[i];
			/*-- step 1 : 预览 --*/
			var aEle = Ext.get(this.IdMgr.attchmentItemPrefix+id);
			if(aEle){
				aEle.on('click',function(e,t,o){
					//_this.viewAttachment(t.id);
				},this);
			}
			
			/*-- step 2 : 下载 --*/
			var downloadEle = Ext.get(this.IdMgr.attchmentItemDownloadPrefix+id);
			if(downloadEle){
				downloadEle.on('click',function(e,t,o){
					_this.downAttachment(t.id);
				},this);
			}
			
			/*-- step 3 : 删除 --*/
			var deleteEle = Ext.get(this.IdMgr.attchmentItemDeletePrefix+id);
			if(deleteEle){
//				if(this.DisenbaledBtn){
//					deleteEle.hide();
//				}
				deleteEle.on('click',function(e,t,o){
					_this.deleteAttachment(t.id);
				},this);
			}
			
		}
	},
	/**
	 * 重新加载数据
	 * @param params 加载数据时所需的参数
	 * @param callback 回调函数
	 */
	reload : function(params,callback){
		if(params){
			if(params.isNotDisenbaled){
				this.isNotDisenbaled = params.isNotDisenbaled;
			}
			if(params.DisenbaledBtn){
				this.DisenbaledBtn = params.DisenbaledBtn;
			}
			if(!this.params) this.params = {};
			Ext.apply(this.params,params);
		}
		if(callback){
			if(!this.events) this.events = {};
			this.events.loadCallback = callback;
		}
		this.isLoad = true;
		this.load();
	},
	/**
	 * 设置附件总数量
	 * @param totalCount 总数量
	 */
	setTotalCount : function(totalCount){
		var totalCountSpan = Ext.get(this.IdMgr.totalCountSpanId);
		if(totalCountSpan){
			totalCountSpan.update(totalCount+"");
		}
	},
	/**
	 * 清除所有附件内容
	 */
	clearAttachmentEles : function(){
		var container = Ext.get(this.IdMgr.containerDivId);
		if(container){
			container.update(this.getNoDataHtml());
		}
	},
	/**
	 * 预览全部附件
	 */
	viewAll : function(eleId){
	
	},
	/**
	 * 删除全部附件
	 */
	deleteAll : function(){
		var _this = this;
		if(!this.ids || this.ids.length == 0){
			ExtUtil.alert({msg:Msg_SysTip.msg_nohaveAttachmentData});
			return;
		}
		ExtUtil.confirm({msg:Msg_SysTip.msg_deleteAllAttachmentData,fn:function(btn){
			if(btn != 'yes') return;
			var params = {ids:_this.ids.join(","),formType:_this.params.formType,deleteAll:true};
			_this.deleteAttachments(params);
		}});
	},
	/**
	 * 创建附件
	 * @param {} data 附件数据
	 */
	creatAttachment : function(data){
		var id = data.id;
		var fileName = data.fileName;
		var creator = data.creator;
		var createTime = data.createTime;
		var filePath = data.filePath;
		var userId = data.userId;
		var tip = '上传人:'+creator+'上传时间:'+createTime;
		var htmlArr = [
			  '<div id="'+this.IdMgr.attchmentItemGpPrefix+id+'" class="fd_attchement" title='+tip+'>',
			  '<img class="fd_img_attchement" src="images/public/attachment.png"/>',
			  //title="预览"
			  '<a id="'+this.IdMgr.attchmentItemPrefix+id+'" href="'+this.downLoadUrl+'?id='+id+'" rel="lightbox">'+fileName+'</a>',
			  '&nbsp;&nbsp;<img id="'+this.IdMgr.attchmentItemDownloadPrefix+id+'" class="fd_img_attchement" src="images/public/disk-download.png" title="下载"/>&nbsp;' 
				];
		if(userId == CURRENT_USERID){
			htmlArr.push([
			 '<img id="'+this.IdMgr.attchmentItemDeletePrefix+id+'" class="fd_img_attchement" src="images/public/delete.png" title="删除"/>'
			].join(""));
		}	
		htmlArr.push(['</div>'].join(""));
		return htmlArr.join("");
	},
	/**
	 * 删除附件
	 * @param {} data 附件数据
	 */
	deleteAttachment : function(eleId){
		var _this = this;
		ExtUtil.confirm({msg:Msg_SysTip.msg_deleteAttachmentData,fn:function(btn){
			if(btn != 'yes') return;
			var attachmentId = _this.getAttachmentId(eleId);
			var params = {ids:attachmentId,formType:_this.params.formType,deleteAll:false};
			_this.deleteAttachments(params);
		}});
	},
	/**
	 * 删除单个或多个附件
	 * @param {} params 参数,其中 params 中必须有 deleteAll 属性   deletAll:false 代表不删除全部数据
	 */
	deleteAttachments : function(params){
		var _this = this;
		params.isenabled=-1;
		EventManager.get(this.delUrl,{params:params,sfn:function(json_data){
 			var deleteAll = params.deleteAll;
 			_this.removeCacheDatas(params.ids);
 			if(deleteAll){/*删除全部*/
 				_this.clearAll();
 			}else{
 				var totalCountSpan = Ext.get(_this.IdMgr.totalCountSpanId);
 				var val = totalCountSpan.dom.innerText;
 				if(!val) val = 0;
 				val = parseInt(val) - 1;
 				_this.setTotalCount(val);
 				var eleId = params.ids;
 				if(eleId){
 					var ele = Ext.get(_this.IdMgr.attchmentItemGpPrefix+eleId);
 					if(ele) ele.remove();
 				}
 			}
 			ExtUtil.alert({msg:Msg_SysTip.msg_dataSucess});
 		}});
	},
	removeCacheDatas : function(ids){
		if(!this.ids) return;
		if(!this.datas) return;
		var arr = ids.split(",");
		if(null == arr || arr.length == 0) return;
		var len = arr.length;
		var delArr = [];
		for(var i=0,count=this.datas.length; i<count; i++){
			var data = this.datas[i];
			var id = data.id;
			var isDel = false;
			for(var j=0; j<len; j++){
				var _id = arr[j];
				if(id == _id){
					isDel = true;
					break;
				}
			}
			if(isDel) delArr[delArr.length] = data;
		}
		if(delArr && delArr.length > 0){
			for(var i=0,count=delArr.length;i<count;i++){
				var data = delArr[i];
				this.datas.remove(data);
			}
		}
		if(this.datas && this.datas.length==0) this.datas = null;
	},
	getAttachmentId : function(eleId){
		if(!eleId){
			ExtUtil.error({msg:'在调用 getAttachmentId 方法时，参数 eleId 为空！ '});
			return null;
		}
		return eleId.substr(eleId.lastIndexOf("_")+1);
	},
	/**
	 * 根据附件ID预览指定的附件 
	 * @param {} eleId 附件元素ID
	 */
	viewAttachment : function(eleId){
		var attachmentId = this.getAttachmentId(eleId);
		var params = {ids:attachmentId};
		EventManager.downLoad(this.viewUrl,params);
	},
	/**
	 * 根据附件ID下载指定的附件
	 * @param {} eleId 附件元素ID
	 */
	downAttachment : function(eleId){
		var _this = this;
		ExtUtil.confirm({msg:Msg_SysTip.msg_downloadAttachmentData,fn:function(btn){
			if(btn != 'yes') return;
			var attachmentId = _this.getAttachmentId(eleId);
			var params = {id:attachmentId};
			EventManager.downLoad(_this.downLoadUrl,params);
		}});
	},
	/**
	 * 更新临时业务ID，临时业务ID参数格式: {formType:-1,formId : Cmw.getUuid()}
	 * @param params 要更新的新参数 新参数格式:{formId:formData.id,formType:ATTACHMENT_FORMTYPE.FType_11,sysId:_this.params.sysId}
	 * @param isLoad 更新完成后，是否重新加载附件列表 true : 重新加载, undefined或 false ： 不加载
	 */
	updateTempFormId : function(params,isLoad){
		if(!params){
			ExtUtil.error({msg:Msg_SysTip.msg_paramsisnull});
			return;
		}
		if(!params.formType){
			ExtUtil.error({msg:Msg_SysTip.msg_formTypeisnull});
			return;
		}
		if(!params.formId){
			ExtUtil.error({msg:Msg_SysTip.msg_formIdisnull});
			return;
		}
		if(!params.sysId){
			ExtUtil.error({msg:Msg_SysTip.msg_sysIdisnull});
			return;
		}
		var old_formType = this.params.formType;
		var old_formId = this.params.formId;
		//if(old_formType != -1) return;/*如果业务类型不等于-1则不用更新*/
		var formId = params.formId;
		if(formId && old_formId == formId) return;/*如果新业务单ID和旧业务单ID相同则不用更新*/
		var _params = {old_formType:old_formType,old_formId:old_formId};
		Ext.apply(_params,params);
		var _this = this;
		EventManager.get(this.updateUrl,{params:_params,sfn:function(json_data){
			if(!isLoad) return;
			Ext.apply(_this.params,params);/*替换为新的参数*/
			_this.reload();
		}});
	},
	/**
	 * 
	 * @param {} keyNames
	 * @return {}
	 */
	cleanAttachAndLoad:function(params,clean){
		if(!params){
			ExtUtil.error({msg:Msg_SysTip.msg_paramsisnull});
			return;
		}
		if(!params.formType){
			ExtUtil.error({msg:Msg_SysTip.msg_formTypeisnull});
			return;
		}
		if(!params.formId){
			ExtUtil.error({msg:Msg_SysTip.msg_formIdisnull});
			return;
		}
		if(!params.sysId){
			ExtUtil.error({msg:Msg_SysTip.msg_sysIdisnull});
			return;
		}
		Ext.apply(this.params,params);
		if(clean){
			this.clearAll();
		}
	},
	/**
	 * getAttachDatas('filePath');
	 * 获取附件中的数据，并以附件返回。如果不提供 keyNames 则返加所有附件数据
	 * @param {} keyNames 附件数据中指定列的数据,如果要返回多个，请参照以下格式：'id,fileName,filePath'
	 * @return 如果没有附件数据则返回 null ,否则返回指定KEY 的数据[注：以数组形式返回]，
	 *         例：[{id:1,filePath:'/upload/2012.xls'},{id:2,filePath:'/upload/2013.xls'}]
	 */
	getAttachDatas : function(keyNames){
		if(!this.datas) return null;
		if(!keyNames) return this.datas;
		var keys = keyNames.split(",");
		var returnDatas = [];
		for(var i=0,count=this.datas.length; i<count; i++){
			var data = this.datas[i];
			var rdata = {};
			for(var j=0,len=keys.length; j<len; j++){
				var key = keys[j];
				rdata[key] = data[key];
			}
			returnDatas[i] = rdata;
		}
		return returnDatas;
	}
});
//注册成xtype以便能够延迟加载   
Ext.reg('appattachmentfs', Ext.ux.AppAttachmentFs);