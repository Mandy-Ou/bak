/**
 * 多表头，纵表头 报表Grid控件
 * 依赖 Jquery 文件(1.7x+ version)
 * @author 程明卫
 * @date 2013-08-08 
 * @version 1.0
 * @param {} _cfg
 * 示例：
 * var cfg = {};	
 * var reportGrid = new CmwPivotGrid(cfg);
 * 	  reportGrid.load({queryMonth:'2013-08'});
 */
var CmwPivotGrid = function(_cfg){
	/**
	 * 配置参数cfg
	 * cfg : {
	 * 			parentId : String [父容器ID , 必须]
	 * 			headTabHtml : String [表头Table HTML字符串, 必须]
	 * 			width : int [宽度，非必须。如果没提供，则取父容器的宽度]
	 * 			height : int [高度，非必须。如果没提供，则取父容器的高度]
	 * 			offset : int [单元格偏移量。非必须。如果主表体与表头没有发生表格错位，则不需要提供]
	 * 			cellWidthArr : [表体表格单元格宽度配置数组，必须。该参数是用来控抽表体单元格宽度。以便与表头单元格对齐]
	 * 		 }
	 */
	this.cfg = _cfg;
	/**
	 * 样式常量管理器对象
	 * 样式定义 see : main.css
	 */
	this.classMgr = {
		/**
		 * 主容器DIV 样式
		 */
		cmw_report_grid : 'cmw_report_grid',
		/**
		 * 表头DIV 样式
		 */
		report_header : 'report_header',
		/**
		 * 表体DIV 样式
		 */
		report_body : 'report_body',
		/**
		 * 表头TABLE 样式
		 */
		report_header_table : 'report_header_table',
		/**
		 * 表体TABLE 样式
		 */
		report_body_table : 'report_body_table',
		/**
		 * 表体首行单元格 样式
		 */
		first_row_td : 'first_row_td',
		/**
		 * 表体最后一行单元格 样式
		 */
		last_row_td : 'last_row_td'
	};
	/**
	 * CmwPivotGrid 容器DIV对象
	 */
	this.cmwMainDiv = null;
	/**
	 * CmwPivotGrid 表头DIV对象
	 */
	this.cmwHeadDiv = null;
	/**
	 * CmwPivotGrid 表体DIV对象
	 */
	this.cmwBodyDiv = null;
	/**
	 * 表头Table对象
	 */
	this.cmwHeadTab = null;
	/**
	 * 表体Table对象 
	 */
	this.cmwBodyTab = null;
	this.gridWidth = 0;
	this.gridHeight = 0;
	/**
	 * 表头是否减掉了滚动条宽度  false : 否 , true : 是
	 */
	this.isMinusScrollWidth = false;
	this.init();
}

CmwPivotGrid.prototype = {
	/**
	 * 初始化方法
	 */
	init : function(){
		this.buildCmpts();
	},
	/**
	 * 构造相应对象方法
	 */
	buildCmpts : function(){
		var parentId = this.cfg.parentId;
		var parent = $("#"+parentId);
		this.cmwMainDiv = $("<div></div>").addClass(this.classMgr.cmw_report_grid).appendTo(parent);
		this.cmwHeadDiv = $("<div></div>").addClass(this.classMgr.report_header).appendTo(this.cmwMainDiv);
		this.cmwBodyDiv = $("<div></div>").addClass(this.classMgr.report_body).appendTo(this.cmwMainDiv);
		this.createHeader();
		this.createBody();
		this.setSize(parent);
		this.addEvents();
	},
	/**
	 * 创建表头
	 */
	createHeader : function(){
		var headTabHtml = this.cfg.headTabHtml;
		this.cmwHeadTab = $(headTabHtml).addClass(this.classMgr.report_header_table).appendTo(this.cmwHeadDiv);
	},
	/**
	 * 创建表体
	 */
	createBody : function(){
		var width = this.cmwHeadTab.width();
		if(!width){
			alert("必须为表头Table对象设置一个固定宽度");
			return;
		}
		this.cmwBodyTab= $("<table></table>").addClass(this.classMgr.report_body_table).width(width).appendTo(this.cmwBodyDiv);
		this.fixBodyTab();
		var offset = this.cfg.offset;
		if(offset) this.setCellOffset(offset);
	},
	/**
	 * 设置单元格宽度并固定表格
	 * @param isAppend
	 */
	fixBodyTab : function(isAppend){
		if(!this.cmwBodyTab) return;
		var arr = this.cfg.cellWidthArr;
		if(!arr){
			alert("必须提供  \"cellWidthArr\" 属性");
			return;
		}
		var trHtmls = ['<tr class="last_row_tag">'];/* last_row_tag 在 main.css 中不存在，其主要是在导出Excel时，用来替换为 style="display:none;"的标记 */
		for(var i=0,count=arr.length; i<count; i++){
			var cellWidth = arr[i];
			trHtmls[trHtmls.length] = '<td class="last_row_td" style="width:'+cellWidth+'px;">'+i+'</td>';
		}
		trHtmls[trHtmls.length] = '</tr>';
		trHtmls = trHtmls.join(" ");
		if(isAppend) this.cmwBodyTab.append(trHtmls);
		return trHtmls;
	},
	/**
	 * 设置报表的大小
	 */
	setSize : function(parent){
		var width = this.cfg.width;
		var height = this.cfg.height;
		if(!width){
			width = parent.width() || this.cmwHeadTab.width();
			if(!width){
				alert("请为 CmwPivotGrid 的 cfg 提供 width值! ");			
			}
		}
		if(!height){
			height = parent.height() || this.cmwHeadTab.height();
		}
		this.resize(width,height);
	},
	/**
	 * 调整整个 CmwPivotGrid 报表Grid 的宽，高
	 */
	resize : function(width,height){
		this.gridWidth = width;
		this.gridHeight = height;
		if(width){
			this.cmwMainDiv.width(width);
			this.cmwHeadDiv.width(width);
			this.cmwBodyDiv.width(width);
		}
		if(height){
			this.cmwMainDiv.height(height);
			var headHeight = this.cmwHeadDiv.height();
			var bodyHeight = height - headHeight;
			this.cmwBodyDiv.height(bodyHeight);
		}
	},
	/**
	 * 调整单元格偏移
	 * @param {} offset 偏移量
	 */
	setCellOffset : function(offset){
		if(!offset) return;
		var firstCell = $(this.cmwBodyTab).children().children("tr:last").children("td:first");
		var width = firstCell.width();
		width -= offset;
		if(width) firstCell.width(width);
	},
	/**
	 * 添加相关事件
	 */
	addEvents : function(){
		var _this = this;
		this.cmwBodyDiv.bind('scroll',function scroll(){
			var left = this.scrollLeft;
			_this.cmwHeadDiv.scrollLeft(left);
		});
	},
	/**
	 * 调整宽度
	 * @param {} width 宽度值
	 */
	changeWidth : function(width){
		alert("");
	},
	/**
	 * 调整高度
	 * @param {} height 高度值
	 */
	changeHeight : function(height){
	
	},
	/**
	 * 当有垂直滚动条时，表头的宽度 = 初始宽度 - 滚动条宽度
	 */
	minusHeadScrollWidth : function(){
		var report_tbody = $(this.cmwBodyDiv)[0];
		var clientWidth = report_tbody.clientWidth;
		var offsetWidth = report_tbody.offsetWidth;
		var borderLeftWidth = report_tbody.style.borderLeftWidth || 0;
		var borderRightWidth = report_tbody.style.borderRightWidth || 0;
		if(clientWidth < offsetWidth && !this.isMinusScrollWidth){
			var div_theader = $(this.cmwHeadDiv)[0];
			var theader_offsetWidth = div_theader.offsetWidth;
			var scrollBarWidth = offsetWidth - borderLeftWidth - borderRightWidth - clientWidth;
			var noscrollWidth = theader_offsetWidth-scrollBarWidth; 
			$(this.cmwHeadDiv).width(noscrollWidth);
			this.isMinusScrollWidth = true;
		}else{/*如果没有垂直滚动条，则宽度相同*/
			var headWidth = $(this.cmwHeadDiv).width();
			var bodyWidth = $(this.cmwBodyDiv).width();
			if(headWidth < bodyWidth) $(this.cmwHeadDiv).width(bodyWidth);
		}
	},
	/**
	 * 加载数据
	 * @param {} params 数据参数
	 * @param parentContainer 父容器(当加载数据时，显示进度加载效果)
	 * @param msg 加载时，显示的文字
	 */
	load : function(params,parentContainer,msg){
		var _this = this;
		if(parentContainer){
			if(!msg){
				msg = parentContainer.title;
			}
			msg = "正在飞速加载<span style='color:red;font-weight:bold;'>"+msg+"</span>数据...";
			Cmw.mask(parentContainer,msg);
		}
		var url = this.cfg.url;
		$.ajax({
		   type: "POST",
		   url: url,
		   data: params,
		   dataType : 'json',
		   success: function(json_data){
			   	var isSuccess = json_data.success;
			   	if(!isSuccess){
			   		_this.alert(json_data.msg);
			   		if(parentContainer) Cmw.unmask(parentContainer);
			   		return;
			   	}
			   	_this.addRows(json_data.msg);
			   	if(parentContainer) Cmw.unmask(parentContainer);
		   },
		   error : function(json_data){
		   		_this.alert(json_data.msg);
		   	if(parentContainer) Cmw.unmask(parentContainer);
		   }
		});
	},
	alert : function(msg){
		 if(ExtUtil.alert){
	   	 	ExtUtil.alert({msg:msg});
	   	 }else{
	   	 	alert(json_data.msg);
	   	 }
	},
	/**
	 * 添加HTML数据行
	 * @param {} htmlDatas HTML 数据行字符串
	 */
	addRows : function(htmlDatas){
		var html = htmlDatas+this.fixBodyTab(false);
		$(this.cmwBodyTab).html(html);
		if(this.cfg.offset) this.setCellOffset(this.cfg.offset);
		this.minusHeadScrollWidth();
		this.setFirstNoTopBorder();
	},
	/**
	 * 设置第一行顶部单元格无边框（避免边框重叠）
	 */
	setFirstNoTopBorder : function(){
		var _cells = $(this.cmwBodyTab).children().children("tr:first").children().addClass(this.classMgr.first_row_td);
	},
	/**
	 * 获取表头Table对象
	 */
	getHeadTab : function(){
		return this.cmwHeadTab;
	},
	/**
	 * 获取表体Table对象
	 */
	getBodyTab : function(){
		return this.cmwBodyTab;
	},
	/**
	 * 获得报表Grid 的HTML 代码
	 * @return {}
	 */
	getReportHtml : function(){
		var parentId = this.cfg.parentId;
		var parent = $("#"+parentId);
//		return this.cmwHeadDiv.html();
		return parent.html();
	}
}
