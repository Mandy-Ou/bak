/**
 * 客户贷款详情信息
 * 
 * @author 程明卫
 * @date 2012-12-26
 */
define(function(require, exports) {
	exports.moduleUI = {
		appPanel : null,
		params : null,
		applyId : null,
		custType : null,
		indexdiv :Ext.id(null,"indexdiv"),
		customerId : null,
		divObj : null,
		prefix : Ext.id(),
		callback : null,/*回调函数*/
		panel : null,
		formId : null,
		toolBar : null,
		appgrid : null,
		uploadGridWin : null,
		uploadWin : null,
		attparams : null,
		formId2 : null,
		isNotHead : false,
		
		/**
		 * 获取业务模块
		 * 
		 * @param params
		 *            由菜单点击所传过来的参数 {tabId : tab 对象ID,apptabtreewinId :
		 *            系统程序窗口ID,sysid : 系统ID}
		 */
		getModule : function(that) {
			var _this = this;
			_this.params = that.params;
			if(this.panel){
				_this.panel.destroy();
			}
			this.appPanel = this.htmlArrs(that);
			return this.appPanel;
		},
		htmlArrs : function(that){
			var parent = that.parent;
			var isNotyl = that.isNotyl;
			var _this = this;
			var j = 0;
			var NodeId = null;
			var selId = null;
			var name = null;
			if(isNotyl){
				NodeId = parent.getSelId();
				_this.isNotHead = true;
				selId = NodeId.substr(1);
				name = parent.getSelText();
			}else if(isNotyl==0){
				_this.formId = that.params.formId;
				NodeId = parent.id;
				selId = NodeId.substr(1);
				name = parent.text;
			}
			if(name.indexOf("【")!=-1){
				name = name.split("【");
			}
			var htmlArrs = [];
			if(!_this.divObj){
				htmlArrs.push(['<div id="'+_this.indexdiv+'">'].join(" "));
			}
			 htmlArrs.push( [
				'<table   width="100%" height="100%" align="center" border="1" cellspacing="0" cellpadding="0"   bgcolor="#B0E2FF"><tr  height="50"><td colspan="4" align="center"><input  type="text" style="display:none" name = "matResultText" id ="matResultId">   <font size=5>'+name+'资料清单</font></td></tr>'
			].join(" "));
			 var panel = new Ext.Panel({
				title : '<span style="color:red">☆ <span>'+'<span style="color:black">为必填选项</span>',
				autoWidth: true,
				height : 500,
//				bodyStyle  : 'padding: 0 5 20 0',
				boxMinHeight : 500,
				header:_this.isNotHead,
				autoScroll : true,
				layout:'fit',
				renderTo: Ext.getBody(),
		        html: htmlArrs,
				hidden : true
			});
			EventManager.get("./sysMatTemp_getTemp.action",{params:{id:selId},sfn : function(jsonData){
				var html2 = [];
				var html3 = [];
				var cookId = [];
				var coikAid = [];
				var MatParamsId = [];
				var remark = [];
				var xh = null;
 				for(var i =0;i<jsonData.length;i++ ){
 					var items  = [];
					 items = jsonData[i].items
					 if(i==0){
					 	html2.push([
						'<tr height="25" class="tr_td" >',
						'<td width="35" align="center" valign="center" bgcolor="#999999" class="tr_td" style="border-style: outset;">序号</td>',
					    '<td width="282" align="left" bgcolor="#FF9900" class="tr_td">'+jsonData[i].name+'应提供的资料</td>',
					    '<td align="center" bgcolor="#FF9900" class="tr_td"> 提交情况</td>',
					    '<td align="center" bgcolor="#FF9900" class="tr_td">备注</td>',
					 ' </tr>'
					 ].join(" "));
					 }else{
					 	html2.push([
						'<tr  height="25" class="tr_td" >',
						'<td width="35" align="left" valign="center" bgcolor="#999999" class="tr_td" style="border-style: outset;"> </td>',
					    '<td width="282" align="left" bgcolor="#FF9900" class="tr_td" colspan="3">'+jsonData[i].name+'企业客户贷款申请应提供的资料</td>',
					 	'</tr>'
					 ].join(" "));	
					 }
					
				 for(j = 0;j<items.length;j++){
				 	var spanId2 = 'SPANID2_'+items[j].id2;
				 	MatParamsId.push(items[j].id2);
				 	xh++;
				 	var allowBlank = "	";
				 	var box = 'CheckboxGroup_'+items[j].id2;
				 	if(items[j].allowBlank){
				 		allowBlank += '<div style="color:red">☆ <span style="color:black">'+xh+'</span></div>';
				 	}else{
				 		allowBlank += '<div>&nbsp;&nbsp;&nbsp;<span style="color:black">'+xh+'</span></div>';
				 	}
				 	html2.push([
				 	'<tr  height="25" class="tr_td" >',
				    '<td bgcolor="#999999" align="center" class="tr_td" style="border-style: outset;" >'+allowBlank+'</td>',
				    '<td bgcolor="#FFFFFF" align="left" class="tr_td">'+items[j].name+'</td>',
				    '<td width="230" bgcolor="#FFFFFF" class="tr_td">'
			 	 ].join(" "));
			 	
			        
			 	 if(items[j].isAttach){
			 	 	 html2.push([
			 	 	'<label><input type="checkbox" name="'+box+'" value="0" />原件&nbsp;&nbsp;  </label>',
			        '<label><input type="checkbox" name="'+box+'" value="1" />复印件</label>&nbsp;&nbsp;&nbsp;&nbsp;</span>'
			        ].join(" "));
			 	 	var id = 'IdMgr.shId_'+items[j].id2;
			 	 	var  Aid = 'AID_'+items[j].id2;
			 	 	var spanId = 'SPANID_'+items[j].id2;
			 	 	
			 	 	cookId.push(id);
			 	 	coikAid.push(Aid);
			 	 	html2.push([
						        '<a  class="hint--bottom" data-hint="上传附件"  disabled ="true" id="'+Aid+'" >',
						        '<span id="'+spanId+'">',
						        	'<img src="images/public/shangchuan.png" id="'+id+'">',
							        '</span>',
							        	'<span id="'+spanId2+'" style="color:blue;font-weight:bold;float:right;">',
							        '</span>',
						        '</a>'
			 	 	].join(" "));
			 	 }
		 	  		
			 	 html2.push(['</td>','<td width="283" align="left" bgcolor="#FFFFFF" class="tr_td">'].join(" "));
			 	 if(items[j].remark){
			 	 	var remarkid = "remark_"+items[j].id2;
			 	 	var remarkName = "remark_"+items[j].id2;
			 	 	remark.push(remarkid);
			 	 	html2.push(['<textarea class="input" name = "'+remarkName+'" id ="'+remarkid+'" rows="1"></textarea>']);
			 	 }
			 	 html2.push(['</td>',
				  '</tr>'].join(" "));
				 }
				}
				var html4 = [
					'<tr height="50"  class="tr_td">',	
				    '<td bgcolor="#999999" class="tr_td" style="border-style: outset;"align="center" cellpadding="0" cellspacing="0">注意事项 </td>',
				    '<td colspan="3" bgcolor="#FFFFFF" class="tr_td">',
				    '<span style="color: #F00;font-weight: bold;">&nbsp;&nbsp; 1.提供的材料除复印件外，同时应提供原件备验。<br />',
				   '&nbsp;&nbsp; 2.提供的材料复印件要加盖公章。</span><br /></td>',
				  '</tr>'
				  ];
				  if(!_this.divObj){
					htmlArrs.push(['</div>'].join(" "));
				  }
				  var data = {MatParamsId:MatParamsId,remark:remark};
				  if(_this.callback) _this.callback(data);
			 var concatHtml = htmlArrs.join(" ").concat(html2.join(" ")).concat(html4.join(" "));
			panel.update(concatHtml,true, function(){
				if(that.isNotyl){
					return panel.setTitle('<span style="color:red">☆ <span>'+'<span style="color:black">为必填选项</span>');
				}
				for(var i =0,cnt =cookId.length;i<cnt;i++ ){
					var cid =  cookId[i].split("_");
					var imgobj = (Ext.get(cookId[i]));
					
					imgobj.on('click',function(e,t,o){
						var ObjId = t.id;
						var cokid = ObjId.split("_");
						var spanid = Ext.get('SPANID_'+cokid[1]);
						var spanid2 = Ext.get('SPANID2_'+cokid[1]);
						var img = Ext.get('IdMgr.shId_'+cokid[1]);
						var aid = Ext.get('AID_'+cokid[1]);
						var display = true;
						_this.MyUpWin(cokid[1],display); 
					});
				}
			});
			if(_this.formId){
				EventManager.get('./sysMatResult_get.action',{params:{formId: _this.formId},sfn:function(json_data){
				if(json_data.length>0){
					var  matResultIds = [];
					var Obj = [];
					var fjsize = [];
					var spanObj = null;
					for(var i = 0;i<json_data.length;i++){
						var itemas = json_data[i];
								var size = itemas.size;
								fjsize.push(size);
								var result = itemas.result;
								var remarkvlue = itemas.remark;
								if(size){
									var ids = [] ;
									ids = size.split("##");
									var spanId  = "SPANID_"+ids[0];
									var spanId2  = "SPANID2_"+ids[0];
									var imgId = 'IdMgr.shId_'+ids[0];
									if(imgId){
										if(Ext.get(imgId)){
											Ext.get(imgId).setStyle('display','none');
										}
									}
									Obj.push(ids[0]);
									if(spanId2){
										if(Ext.get(spanId2)){
											Ext.get(spanId2).setStyle('display','block');
										}
									}
									var checkBoxName = "CheckboxGroup_"+ids[0];
									var boxs = Cmw.getElesByName(checkBoxName);
									if(result == 0){
										if(boxs[0]){
											boxs[0].checked  = true;
										}
										
									}else{
										if(boxs[0] && boxs[1]){
											boxs[0].checked  = true;
											boxs[1].checked  = true;
										}
										
									}
									if(remarkvlue && remarkvlue !=""){
										var remark = "remark_"+ids[0];
										var getVlueRemark = Ext.get(remark);
										if(getVlueRemark){
											getVlueRemark.dom.value = remarkvlue;
										}
									}
									matResultIds.push(ids[0]);
								}
							}
							
							_this.spanId2Click(Obj,fjsize);
							if(matResultIds.length>0){
								var formIds = matResultIds.join(",");
								if(Ext.get("matResultId")){
									Ext.get("matResultId").dom.value = formIds;
								}
							}
						}
				}});
			}
			}});
			_this.panel = panel;
			return  panel.show();
		},
		spanId2Click: function(Obj,fjsize){
			var _this = this;
			var spanObj = [];
			for(var i=0;i<Obj.length;i++ ){
				for(var j = 0;j<fjsize.length;j++){
					var size = fjsize[j].split("##");
					if(size[0]==Obj[i]){
						var spanid2 = Ext.get("SPANID2_"+Obj[i]);
						spanObj.push(spanid2);
						if(spanid2){
							spanid2.update(size[1]+'个附件',true,function(){});
						}
						fjsize.remove[fjsize[j]];
						break;
					}
				}
			}
			for(var i = 0;i<spanObj.length;i++){
				if(spanObj[i]){
					spanObj[i].on('click',function(e,t,o){
						var formIdObj = t.id;
						var formId2 = formIdObj.split("_");
						 _this.CreateWin(formId2[1]);
					});
				}
			}
			
		},
		CreateWin : function(formId2){
			var _this = this;
			_this.formId2 = formId2;
//			var spanid2 = Ext.get('SPANID2_'+formId2);
//			var spanid = Ext.get('SPANID_'+formId2);
			var tbar  = null;
			if(!_this.uploadGridWin){
				_this.crateGrid(formId2);
				_this.getToolBar(formId2,_this.appgrid);
				_this.newWin(_this.toolBar,_this.appgrid);
			}
			_this.uploadGridWin.show();
			_this.appgrid.reload({formId:_this.formId,formId2:formId2,formType:ATTACHMENT_FORMTYPE.MATTEMP_30});
			_this.uploadGridWin.addListener('hide',function(){
				var totalLength = _this.appgrid.getStore().totalLength;
				if(totalLength==0){
					Ext.get('SPANID_'+_this.formId2).setStyle({display:'block'});
					Ext.get('SPANID2_'+_this.formId2).setStyle({display:'none'});
				}else{
					 Ext.get('SPANID2_'+_this.formId2).update(+totalLength+'个附件');
					formId2  = null;
				}
			});
			
		},
		newWin : function(tbar,grid){
			var uploadGridWin = new Ext.Window({
				height:310,
				width:370,
				layout  : 'fit',
				tbar : tbar,
				modal  : true,
				title :'编辑附件',
				draggable : false,
				closeAction : 'hide',
				items :[grid]
			});
			this.uploadGridWin = uploadGridWin;
			return uploadGridWin;
		},
		crateGrid: function(formId2){
			var _this = this;
				var structure = [
		  			{
					    header: '附件名称',
					    width : 200,
					    name: 'fileName'
					},
					{
					    header: '上传时间',
					    name: 'createTime'
					}
					];
				var _appgrid = new Ext.ux.grid.AppGrid({
					structure : structure,
					url : './sysAttachment_list.action',
					params :{formId2 : formId2,formId:_this.formId,formType:ATTACHMENT_FORMTYPE.MATTEMP_30},
					width  : 360,
					height : 250,
					needPage : false,
					isLoad : true,
					keyField : 'id',
					autoScroll :  true
				});
				_this.appgrid = _appgrid;
			return _appgrid;
		},
		MyUpWin : function(formId2,display){
			var _this = this;
			 this.attparams = {
				sysId : _this.params.sysId,
				formType : ATTACHMENT_FORMTYPE.MATTEMP_30,
				atype:10,
				formId:_this.formId,
				formId2 : formId2,
				filePath : null,
				fileName : null,
				fileSize : null
			};
			var spanid = Ext.get('SPANID_'+formId2);
			var spanid2 = Ext.get('SPANID2_'+formId2);
			var img = Ext.get('IdMgr.shId_'+formId2);
			var aid = Ext.get('AID_'+formId2);
			if(!this.uploadWin){
				_this.CreateuploadWin();
			}
			this.uploadWin.show(img);	
			this.uploadWin.sfn = function(fileInfos){
				var fileInfo = fileInfos[0];
				_this.attparams.filePath = fileInfo.serverPath;
				_this.attparams.fileName  = fileInfo.custName;
				_this.attparams.fileSize = fileInfo.fileSize;
				EventManager.get('./sysAttachment_save.action',{params:_this.attparams,sfn:function(json_data){
					if(display){
						var spanid = Ext.get('SPANID_'+formId2);
						img.setStyle({display:'none'});
						spanid2.update('1个附件',true,function(){
							spanid2.setStyle({display:'block'});
							spanid2.on('click',function(e,t,o){
								var formIdObj = t.id;
								var formId2 = formIdObj.split("_");
								_this.CreateWin(formId2[1]);
							});
						});
					}else{
						_this.appgrid.reload({formId2: _this.formId2,formId:_this.formId,sysId : _this.params.sysId});
					}
				}});
			}
		},
		CreateuploadWin : function(){
			var _this = this;
			var uploadWin = new Ext.ux.window.MyUpWin({
				title:'上传附件',label:'请选择上传附件',width:450,dir:'MatTemp',sfn:function(fileInfos){
				var fileInfo = fileInfos[0];
				_this.attparams.filePath = fileInfo.serverPath;
				_this.attparams.fileName  = fileInfo.custName;
				_this.attparams.fileSize = fileInfo.fileSize;
			}});
			this.uploadWin = uploadWin;
			return uploadWin;
		}, 
		getToolBar: function(formId2,uploadGrid){
			var _this = this;
			var barItems = [{
				text:'上传附件',
			    iconCls:'page_add',
				tooltip:"上传附件",
			    handler : function(){
			    	_this.MyUpWin(formId2);
			}},{
				text : "删除附件",
				iconCls:'page_delete',
				tooltip:"删除附件",
				handler : function(){
					ExtUtil.confirm({msg:Msg_SysTip.msg_deleteAttachmentData,fn:function(btn){
						if(btn != 'yes') return;
						var attachmentId = uploadGrid.getSelId();
						var params = {ids:attachmentId,deleteAll:false};
						_this.deleteAttachments(params,uploadGrid,_this.formId2);
					}});
				}
			},{
				text : "下载附件",
				iconCls:'page_download',
				tooltip:"下载附件",
				handler : function(){
				var attachmentId = uploadGrid.getSelId();
				if(!attachmentId){
					ExtUtil.info({msg : "请选择要下载的数据！"});
					return ;
				}
				ExtUtil.confirm({msg:Msg_SysTip.msg_downloadAttachmentData,fn:function(btn){
					if(btn != 'yes') return;
					var params = {id:attachmentId};
					EventManager.downLoad('./sysAttachment_download.action',params);
				}});
				}
			},{
				text : "关闭",
				iconCls:'page_close',
				tooltip:"关闭",
				handler : function(){
					_this.uploadGridWin.hide();
				}
			}]
			var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems});
			_this.toolBar = toolBar;
			return toolBar;
		},
			/**
	 * 删除单个或多个附件
	 * @param {} params 参数 
	 */
	deleteAttachments : function(params,uploadGrid,formId2){
		var _this = this;
		params.isenabled=-1;
		EventManager.get("./sysAttachment_delete.action",{params:params,sfn:function(json_data){
 			uploadGrid.reload({formId2:formId2});
 		}});
	},
		/**
		 * 设置参数
		 */
		setParams : function(params) {
			this.params = params;
		},
		/**
		 * 显示面板并加载数据
		 */
		show : function(params){
			this.setParams(params);
		},
		resize : function(adjWidth, adjHeight) {
			this.appPanel.setWidth(adjWidth);
			this.appPanel.setHeight(adjHeight);
		},
		/**
		 * 获取详情面板的高度
		 */
		getHeight : function(){
			var height = 145;
			if(!this.appPanel || !this.appPanel.rendered){
				return height;
			}
			var appEl = this.appPanel.el;
			if(!appEl) return height;
			height = appEl.getComputedHeight();
			return height;
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function() {
			if (null != this.appPanel) {
				this.appPanel.destroy();
				this.appPanel = null;
			}
		}
	};
});