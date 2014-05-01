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
		customerId : null,
		callback : null,/*回调函数*/
		prefix : Ext.id(),
		isNotHead : false,
		panel : null,
		/**
		 * 获取业务模块
		 * 
		 * @param params
		 *            由菜单点击所传过来的参数 {tabId : tab 对象ID,apptabtreewinId :
		 *            系统程序窗口ID,sysid : 系统ID}
		 */
		getModule : function(that) {
			if (!this.appPanel) {
				this.appPanel = this.htmlArrs(that);
			}
//			that.appWin.addListener('hide',function(){
//				this.destroy();
//				this.appPanel = null;
//			});
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
				selId = NodeId.substr(1);
				name = parent.getSelText();
			}else if(isNotyl==0){
				NodeId = parent.id;
				selId = NodeId.substr(1);
				name = parent.text;
			}
			if(name.indexOf("【")!=-1){
				name = name.split("【");
			}
			var htmlArrs = [];
			 htmlArrs = [
			'<div><table   width="100%" height="100%" align="center" border="1" cellspacing="0" cellpadding="0"   bgcolor="#B0E2FF"><tr ><td colspan="4" align="center">   <font size=5>'+name+'资料清单</font></td></tr>'
			];
			 var panel = new Ext.Panel({
				title : '<span style="color:red">☆ <span>'+'<span style="color:black">为必填选项</span>',
				height:530,autoWidth: true,
				header:true,
				autoScroll : true,
				renderTo: Ext.getBody(),
		        html: htmlArrs,
				hidden : true
			});
//			that.appWin.addListener('hide',function(){
//				this.destroy();
//				this.panel = null;
//			});
			EventManager.get("./sysMatTemp_getTemp.action",{params:{id:selId},sfn : function(jsonData){
				var html2 = [];
				var html3 = [];
				var cookId = [];
				var coikAid = [];
				var xh = null;
 				for(var i =0;i<jsonData.length;i++ ){
 					var items  = [];
					 items = jsonData[i].items
					 if(i==0){
					 	html2.push([
						'<tr  class="tr_td" style="font-weight:bold;">',
						'<td width="35" align="center" valign="center" bgcolor="#999999" class="tr_td" style="border-style: outset;">序号</td>',
					    '<td width="282" align="left" bgcolor="#FF9900" class="tr_td">'+jsonData[i].name+'应提供的资料</td>',
					    '<td align="center" bgcolor="#FF9900" class="tr_td"> 提交情况</td>',
					    '<td align="center" bgcolor="#FF9900" class="tr_td">备注</td>',
					 ' </tr>'
					 ].join(" "));
					 }else{
					 	html2.push([
						'<tr   class="tr_td" style="font-weight:bold;">',
						'<td width="35" align="left" valign="center" bgcolor="#999999" class="tr_td" style="border-style: outset;"> </td>',
					    '<td width="282" align="left" bgcolor="#FF9900" class="tr_td" colspan="3">'+jsonData[i].name+'企业客户贷款申请应提供的资料</td>',
					 ' </tr>'
					 ].join(" "));	
					 }
					
				 for(j = 0;j<items.length;j++){
				 	xh++;
				 	var allowBlank = "	";
				 	var box1 = 'IdMgr.CheckboxGroup1_'+items[j].id2;
				 	var box2 = 'IdMgr.CheckboxGroup2_'+items[j].id2;
				 	if(items[j].allowBlank){
				 		allowBlank += '<div style="color:red">☆ <span style="color:black">'+xh+'</span></div>';
				 	}else{
				 		allowBlank += '<div>&nbsp;&nbsp;&nbsp;<span style="color:black">'+xh+'</span></div>';
				 	}
				 	html2.push([
				 	'<tr  class="tr_td" >',
				    '<td bgcolor="#999999" align="center" class="tr_td" style="border-style: outset;" >'+allowBlank+'</td>',
				    '<td bgcolor="#FFFFFF" align="left" class="tr_td">'+items[j].name+'</td>',
				    '<td width="280" bgcolor="#FFFFFF" class="tr_td">'
				    
			 	 ].join(" "));
			 	 html2.push([
			 	 	'<label><input type="checkbox" name="result" value="0" id="'+box1+'"/>原件&nbsp;&nbsp;  </label>',
			        '<label><input type="checkbox" name="result" value="1" id="'+box2+'"/>复印件</label>&nbsp;&nbsp;&nbsp;上传附件'
			        ].join(" "));
			 	 if(items[j].isAttach){
			 	 	var box1 = 'IdMgr.CheckboxGroup1_'+items[j].id2;
			 	 	var id = 'IdMgr.shId_'+items[j].id2;
			 	 	var  Aid = 'AID_'+items[j].id2;
			 	 	var spanId = 'SPANID_'+items[j].id2;
			 	 	cookId.push(id);
			 	 	coikAid.push(Aid);
			 	 	
			 	 	html2.push([
					        '<a  class="hint--bottom" data-hint="上传附件" disabled ="true" id="'+Aid+'" > <span id="'+spanId+'"><img src="images/public/shangchuan.png" id="'+id+'"></span></a>'
			 	 	].join(" "));
			 	 }
			 	 html2.push(['</td>','<td width="283" align="left" bgcolor="#FFFFFF" class="tr_td">'].join(" "));
			 	 if(items[j].remark){
			 	 	html2.push(['<textarea class="input" rows="1"></textarea>']);
			 	 }
			 	 html2.push(['</td>',
				  '</tr>'].join(" "));
				 }
				}
				var html4 = [
					'<tr height="50"  class="tr_td">',	
				   ' <td bgcolor="#999999" class="tr_td" style="border-style: outset;"align="center">注意事项 </td>',
				    '<td colspan="3" bgcolor="#FFFFFF" class="tr_td">',
				     ' <span style="color: #F00;font-weight: bold;">&nbsp;&nbsp; 1.提供的材料除复印件外，同时应提供原件备验。<br />',
				   '&nbsp;&nbsp; 2.提供的材料复印件要加盖公章。</span><br /></td>',
				  '</tr></div>'
				  ];
				  
			 var concatHtml = htmlArrs.join(" ").concat(html2.join(" ")).concat(html4.join(" "));
			 panel.show();
			panel.update(concatHtml,true, function(){
				if(that.isNotyl){
					return panel;
				}
				for(var i =0,cnt =cookId.length;i<cnt;i++ ){
					var cid =  cookId[i].split("_");
					var imgobj = (Ext.get(cookId[i]));
					EventManager.get('./sysAttachment_list.action',{params :{formId2 : cid[1]},sfn:function(json_data){
						
					}});
					imgobj.on('click',function(e,t,o){
						var ObjId = t.id;
						var uploadWin = new Ext.ux.window.MyUpWin({
						title:'上传附件',label:'请选择上传附件',width:450,dir:'MatTemp',sfn:function(fileInfos){
							var fileInfo = fileInfos[0];
							filePath = fileInfo.serverPath;
							var fileName  = fileInfo.custName;
							var fileSize = fileInfo.fileSize;
							var cokid = ObjId.split("_");
							var spanid = Ext.get('SPANID_'+cokid[1]);
							var img = Ext.get('IdMgr.shId_'+cokid[1]);
							var aid = Ext.get('AID_'+cokid[1]);
							var params = {sysId : that.params.sysId,formType : ATTACHMENT_FORMTYPE.MATTEMP_30,
								filePath:filePath,fileName:fileName,fileSize:fileSize,atype:10,formId:-2,
								formId2 : cokid[1]
							};
							EventManager.get('./sysAttachment_save.action',{params:params,sfn:function(json_data){
								
							}});
							
							spanid.update('<a style="color:blue;text-decoration: none;font-weight:bold;" id = "SPANID_'+cokid[1]+'"  href="javascript:void(0);">(1)个附件</a>',true,function(){
								spanid.on('click',function(e,t,o){
									var formIdObj = t.id;
									var formId2 = formIdObj.split("_");
									var cfg =  {url:''};
									var params = {dir:'MatTemp',isSave: true};
									cfg.params = params
									cfg.hideCallback = function(){
									};
									var  uploadGrid = _this.crateGrid(formId2[1]);
									var tbar = _this.getToolBar(formId2[1],uploadGrid);
									var uploadWin = new Ext.Window({
										height:310,
										width:510,
										modal  : true,
										items :[tbar,uploadGrid]
									});
									uploadWin.show();
									uploadWin.addListener('hide',function(){
										var totalLength = uploadGrid.getStore().totalLength;
										var spanid = Ext.get('SPANID_'+formId2[1]);
										spanid.update('<a style="color:blue;text-decoration: none;font-weight:bold;" id = "SPANID_'+cokid[1]+'"  href="javascript:void(0);">'+totalLength+'个附件</a>');
									});
								});
							});
							
						}
					});
					uploadWin.show(Ext.get(ObjId));
					uploadWin.addListener('hide',function(){
						uploadWin.destroy();
					});
					});
				}
			})
			}});
			return panel;
		},
		crateGrid: function(formId2){
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
					tbar : this.toolBar,
					structure : structure,
					url : './sysAttachment_list.action',
					params :{formId2 : formId2},
					width  : 500,
					height : 250,
					needPage : false,
					isLoad : true,
					keyField : 'id',
					autoScroll :  true
				});
				return _appgrid;
		},
		getToolBar: function(formId2,uploadGrid){
			var _this = this;
			var barItems = [{
				text:'上传附件',
			    iconCls:'page_add',
				tooltip:"上传附件",
			    handler : function(){
			    	var uploadWin = new Ext.ux.window.MyUpWin({
						title:'上传附件',label:'请选择上传附件',width:450,dir:'MatTemp',sfn:function(fileInfos){
							var fileInfo = fileInfos[0];
							filePath = fileInfo.serverPath;
							var fileName  = fileInfo.custName;
							var fileSize = fileInfo.fileSize;
							var spanid = Ext.get('SPANID_'+formId2);
							var img = Ext.get('IdMgr.shId_'+formId2);
							var aid = Ext.get('AID_'+formId2);
							var params = {sysId : that.params.sysId,formType : ATTACHMENT_FORMTYPE.MATTEMP_30,
								filePath:filePath,fileName:fileName,fileSize:fileSize,atype:10,formId:-2,
								formId2 : formId2
							};
							EventManager.get('./sysAttachment_save.action',{params:params,sfn:function(json_data){
								uploadGrid.reload({formId2: formId2});
							}});
						}});
						uploadWin.show();	
						uploadWin.addListener('hide',function(){
						uploadWin.destroy();
						uploadWin = null;
					});
			}},{
				text : "删除附件",
				iconCls:'page_delete',
				tooltip:"删除附件",
				handler : function(){
					ExtUtil.confirm({msg:Msg_SysTip.msg_deleteAttachmentData,fn:function(btn){
						if(btn != 'yes') return;
						var attachmentId = uploadGrid.getSelId();
						var params = {ids:attachmentId,deleteAll:false};
						_this.deleteAttachments(params,uploadGrid,formId2);
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
			}]
			var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
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
			var width = CLIENTWIDTH - Ext.getCmp(ACCORDPNLID);
			var height = CLIENTHEIGHT - 180;
			this.appPanel.setWidth(width);
			this.appPanel.setHeight(height);
			
//			this.appPanel.setWidth(adjWidth);
//			this.appPanel.setHeight(adjHeight);
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