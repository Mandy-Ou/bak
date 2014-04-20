/*
* 封装的综合的Grid控件，继承GridPanel 
* 具有分页,可加载 JSON /XML/Array 数等功能。
*  qq : 340360491
* code by cdw	
*/
Ext.namespace("Ext.ux.grid");
Ext.ux.grid.AppGrid = Ext.extend(Ext.grid.GridPanel, {
	// 储存表格结构
	structure : '',
	//要渲染的层
	mainDiv : '',
	refreshFn : null,/*刷新方法 如果要调用自定义的刷新方法，可给此属性赋值*/
	// 每页显示条数
	pageSize : PAGESIZE,
	gatherTitle : '汇总',
	gatherIdVal : -11111,/*用来标识统计列的ID值*/
	/*汇总配置信息 
	 * {
	 * 	gatherTitle : null, //汇总标题，如果不提供此属性，则默认显示为 "汇总"
	 * 	gatherOffset : null,//汇总title 所在的位置
	 *  gatherCmns : null,//汇总列名,如："totalAmount,ytotalAmount,ztotalAmount"
	 * }*/
	gatherCfg : null, 
	//当重新加载数据时，所带的参数
	params : null,
	//记录定义类 在 initStructure 进行初始化
	recordDef : null,
	// reader类型如果当为json的时候那么url不能空，当为array的时候dataObject不能为空
	readType : 'json',
	// 获取数据的URL
	url : '',
	// 数据对象
	dataObject : null,
	// 表格主键
	keyField : 'id',
	// 是否需要分组，默认为false，如果设置为true须再设置两个参数一个为myGroupField和myGroupTextTpl
	needGroup : false,
	// 分组的字段名称
	myGroupField : '',
	// 分组显示的模板，eg：{text} ({[values.rs.length]} {[values.rs.length > 1 ?
	// "Items" : "Item"]})
	myGroupTextTpl : '',
	// 列模式的选择模式,默认为rowselectModel，如果Grid带复选框就将此参数设置成"check"
	selectType : '',
	// 默认排序字段
	defaultSortField : 'ID',
	// 是否需要分页工具栏，默认为true
	needPage : true,
	// 分页样式：[ null :普通分页, 'slid' : 滑块形式分页]
	pgsty: null,
	isLoad : true,	//是否及时加载
	xtype : 'appgrid',
	frame : false,
	// 是否带展开按钮，默认为false
	collapsible : false,
	animCollapse : true,
	loadMask : true,
	autoScroll : true,
	viewConfig : {
	forceFit : false
	},
	// private
	initComponent : function() {
		if (this.structure != '') {
		this.initStructure();
		}
		Ext.ux.grid.AppGrid.superclass.initComponent.call(this);
	},
	
	// private
	initEvents : function() {
		Ext.ux.grid.AppGrid.superclass.initEvents.call(this);
		this.initListener();
		this.gatherDataListner();
		this.googleBug();
		/*
		* this.getStore().load( { params : { start : 0, limit : 30 } });
		* 
		*/
		if (this.loadMask) {
		this.loadMask = new Ext.LoadMask(this.bwrap, Ext.apply({
		store : this.store
		}, this.loadMask));
		}
		
	},
	initListener : function(){
		
	},
	/**
	 * 修复办法，谷歌浏览器中,table的单元格实际宽度=指定宽度+padding，所以只要重写gridview里的一个方法，如下
	 */
	googleBug : function(){
		Ext.override(Ext.grid.GridView,{
        getColumnStyle : function(colIndex, isHeader) {
            var colModel  = this.cm,//模型
                colConfig = colModel.config,//模型中的配置
                style     = isHeader ? '' : colConfig[colIndex].css || '',
                align     = colConfig[colIndex].align;
            if(Ext.isChrome){//是否是Google浏览器
                style += String.format("width: {0};", parseInt(this.getColumnWidth(colIndex))-4+'px');
            }else{
                style += String.format("width: {0};", this.getColumnWidth(colIndex));
            }
            if (colModel.isHidden(colIndex)) {
                style += 'display: none; ';
            }
            if (align) {
                style += String.format("text-align: {0};", align);
            }
            return style;
        }
    });
	},
	/**
	 * 汇总统计函数
	 */
	gatherDataListner : function(){
		if(!this.gatherCfg) return;
		var _this = this;
		this.store.addListener('load', function(store,records,options){
			var count = store.getCount();
			if(count == 0) return;
			var errMsg = [];
			var gatherTitle = _this.gatherCfg.gatherTitle || _this.gatherTitle;
			var gatherOffset = _this.gatherCfg.gatherOffset;
			if(!gatherOffset){
				errMsg[errMsg.length] = "必须为 [gatherOffset] 属性提供相应的值，以确定将标题 "+gatherTitle+" 放在指定的单元格位置";
			}
			var gatherCmns = _this.gatherCfg.gatherCmns;
			if(!gatherCmns || gatherCmns.length == 0){
				errMsg[errMsg.length] = "必须为 [gatherCmns] 属性提供相应的数据";
			}
			if(errMsg.length > 0){
				ExtUtil.error({msg : errMsg.join('<br/>')});
				return;
			}
			var keyField = _this.keyField;
			var gatherCmnObj = {};
			gatherCmnObj[gatherOffset] = setStyle(gatherTitle);
			gatherCmnObj[keyField] =  _this.gatherIdVal;
			for(var j=0,len=gatherCmns.length; j<len; j++){
				var gatherCmn = gatherCmns[j];
				var cmn = gatherCmn["cmn"];
				var dp = gatherCmn["dp"];
				if(!gatherCmnObj[cmn]) gatherCmnObj[cmn] = 0;
				var total = gatherCmnObj[cmn];
				for(var i=0; i<count; i++){
					var record = records[i];
					var val = record.get(cmn);
					if(dp && dp>0){
						val = parseFloat(val);
					}else{
						val = parseInt(val);
					}
					if(!Ext.isNumber(val)) continue;
					total += val;
				}
				if(dp){
					total = Ext.util.Format.round(total,dp);
				}
				if(gatherCmn["renderer"]){
					total = gatherCmn.renderer(total);
				}
				gatherCmnObj[cmn] = total;
			}
			_this.addRecord(gatherCmnObj,true);
		});
		
		function setStyle(val){
			return '<span style="color:red;font-weight:bold;">'+val+'</span>';
		}
	},
	// private
	initStructure : function() {
		var oDs = null;
		var oCM = new Array(); // 列模式数组
		var oRecord = new Array(); // 容器对数组
		oCM[oCM.length] = new Ext.grid.RowNumberer();	//添加行号
		// 构成grid的两个必须组件: column和record，根据structure将这两个组件创建出来
		// 判断表格的选择模式
		if (this.selectType == 'check') {
			var sm = new Ext.grid.CheckboxSelectionModel();
			oCM[oCM.length] = sm;// 在列模式数组中添加checkbox模式按钮
			this.selModel = sm;// 并将selModel设置为check模式
		}
		
		var len = this.structure.length;// 得到结构的长度
		for (var i = 0; i < len; i++) {
			var c = this.structure[i];
			// alert( c.hidden ? c.width: this.fieldwidth)
			// 如果字段为hidden，则不生成filters和columnMode 
			// 默认格式化日期列
			if (c.type == 'date' && !c.renderer) {
				c.renderer = this.formatDate;
			}
			var cmnCfg =  {
				header : '<div style="text-align:center">'+c.header+'</div>',
//				id : c.name,
				dataIndex : c.name,
				hidden : c.hidden || false,
				width : !c.hidden ? c.width : this.fieldwidth,
				sortable :true,		
				// 类型为数字则右对齐
				align : c.type == 'numeric' ? 'right' : 'left',
				// 结构的渲染函数
				renderer : c.renderer
			};
			oCM[oCM.length] = cmnCfg;
			oRecord[oRecord.length] = {
				name : c.name,
				// 如果类型不是date型则全部为string型
				type : c.type == 'date' ? 'date' : 'string',
				dateFormat : 'Y-m-d'
			};
		}
		
		// 生成columnModel
		this.cm = new Ext.grid.ColumnModel(oCM);
		// this.colModel = this.cm;
		// 默认可排序
		this.cm.defaultSortable = true;
//		this.cm.defaults = { sortable: true};
		this.stripeRows = true;
//		this.autoExpandColumn="remark";
		this.stateful = true;
		// 生成表格数据容器
		var record = Ext.data.Record.create(oRecord);
		// 生成表格数据容器
		this.recordDef = record;
		// 判断读取类别，目前只实现了，jsonreader和arrayReader
		var reader;
		var pageSize = this.pageSize;
		var fields = this.fields;
	
		switch (this.readType) {
		case 'json' :
			reader = new Ext.data.JsonReader({
			totalProperty : "totalSize",
			root : "list",
			id : this.keyField
			// 关键字段
			}, record);
			
			 //获取时间戳   
	       	var timstamp = (new Date()).valueOf();
			if(this.url && this.url.indexOf("?")==-1){
	       		this.url += "?"; 
			}else{
				this.url += "&"; 
			}
			this.ds = new Ext.data.GroupingStore({
			proxy : new Ext.data.HttpProxy({
			url : this.url
			}),
			reader : reader,
			groupField : this.myGroupField,
			listeners : {
				'exception' : function(thisproxy,type,action,options,response,arg) {
					EventManager.existSystem(response);
				}
			}
			});
		break;
		case 'array' :
			reader = new Ext.data.ArrayReader({}, record);
			this.ds = new Ext.data.GroupingStore({
			reader : reader,
			data : this.dataObject,
			groupField : this.myGroupField
			});
		break;
		default :
		break;
		}
		// 判断是否需要分组
		if (this.needGroup) {
		this.view = new Ext.grid.GroupingView({
			forceFit : true,
			groupTextTpl : this.myGroupTextTpl
		})
		}
		
		this.store = this.ds;
		// 生成分页工具栏
		if (this.needPage) {
			//创建滑块对象
			var _plugins= [];
			if(this.pgsty && this.pgsty == 'slid'){
				var slidPager = new Ext.ux.SlidingPager();
				Ext.apply(slidPager,{pagetip:"第 <b>{0}</b> 页，共 <b>{1}</b>页"});
				_plugins[0] = slidPager;
			}
			_plugins[_plugins.length] = new Ext.ux.ProgressBarPager();
			
			var pgCfg = {
			displayInfo : true,
			displayMsg : '当前记录数:{0} - {1} 总记录数: {2}',
			emptyMsg : '没有符合条件的记录',
			store : this.store,
			plugins: _plugins,
			doRefresh : function(){
				_this.toolRefresh();
			}
			};
			// pgsty = 'slid'
			var pagingToolbar = new Ext.PagingToolbar(pgCfg);
			pagingToolbar.pageSize = this.pageSize;
			this.bbar = pagingToolbar;
			this.bottomToolbar = this.bbar;
			var keyField = this.keyField;
		}
		if(this.isLoad){
			if(!this.params){
				this.params = {};
			}
			var start = 0;
			var limit = this.pageSize;
			if(!this.needPage){
				start = -1;
				limit = -1;
			}
			this.params["start"] = start;
			this.params["limit"] = limit;
			
			this.store.load({params : this.params});
		}
		var _this = this;
		this.store.addListener('beforeload',function(store,pars){/*解决分页会加载所有数据的问题*/
			var _params = pars.params || {};
			Ext.applyIf(_params,_this.params);
			pars.params = _params;
		});
	},
	/**
	 * 工具栏上的刷新事件
	 */
	toolRefresh : function(){
		if(this.refreshFn){
			this.refreshFn();
		}else{
			this.reload(this.params);
		}
	},
	/*
	* @功能:默认的格式化日期函数 @返回格式：2008-09-21
	*/
	formatDate : function(v) {
	return v ? v.dateFormat('Y-m-d') : '';
	},
	/**
	 * 获取选中的行对象
	 * @return Record
	 */
	getSelRow : function(msg){
		var selRow = this.getSelectionModel();
		selRow = selRow.getSelected();
		if(selRow){
			var id = selRow.id;
			if(id == this.gatherIdVal){/*如果选择统计列则清空*/
				selRow = null;
			}
		}
		if(!selRow){
			if(msg){
				Ext.MessageBox.alert("警告",msg);
			}else{
				Ext.MessageBox.alert("警告","请选择要处理的数据！");
			}
			return null;
		}
		return selRow;
	},
	/**
	 * 获取选中的那一行的指定列的值，并以 JSON 对象的形式返回
	 * @param {} flds 要获取值的列。多个以“，”隔开
	 * @return {}  JSON 对象的形式返回获取到的值,如果找不到则返回 null
	 */
	getCmnVals : function(flds){
		if(!flds) return null ;
		var selRow = this.getSelRow();
		if(null == selRow) return null;
		var jsonVal = {};
		var isArray = Ext.isArray(flds);
		if(isArray){
			for(var i=0,count = flds.length; i<count; i++){
				var fld = flds[i];
				jsonVal[fld] = selRow.get(flds[i]);	
			}
		}else{
			var flds = flds.split(",");
			if(flds && flds.length>1){
				for(var i=0,count = flds.length; i<count; i++){
					var fld = flds[i];
					jsonVal[fld] = selRow.get(flds[i]);	
				}
			}else{
				jsonVal[flds] = selRow.get(flds);			
			}
		}
		return jsonVal;
	},
	/**
	 * 获取选中的多行的指定列的值，并以 数组形式返回。数组元素为JSON 对象
	 * @param {} flds 要获取值的列。多个以“，”隔开
	 * @return {} 以数组形式返回 JSON 对象的值,如果找不到则返回 null
	 * 		例如：var data = appgrid.getCmnArrVals("id,name"); //--> [{id:1,name:'张三'},{id:2,name:'李四'}];
	 */
	getCmnArrVals : function(flds){
		if(!flds) return null;
		var selRows = this.getSelRows();
		if(null == selRows || selRows.length == 0) return null;
		var arrData = [];
		var isArray = Ext.isArray(flds);
		if(isArray){
			for(var j=0,len = selRows.length; j<len; j++){
				var selRow = selRows[j];
				var jsonVal = {};
				for(var i=0,count = flds.length; i<count; i++){
					var fld = flds[i];
					jsonVal[fld] = selRow.get(flds[i]);	
				}
				arrData[j] = jsonVal;
			}
		}else{
			for(var j=0,len = selRows.length; j<len; j++){
				var selRow = selRows[j];
				var jsonVal = {};
				jsonVal[flds] = selRow.get(flds);	
				arrData[j] = jsonVal;
			}
		}
		return arrData;
	},
	/**
	 * 获取当前Grid 中选中行指定列的值，并以新的字段作为KEY，返回一个 json 值对象
	 * @param {} fromFlds Grid 中的字段，多个以"，"分隔	
	 * @param {} toFlds	新的字段，多个以"，"分隔	
	 * @return 以 toFlds 作为 JSON key ，以 fromFlds 上的值返回一个 JSON 值对象
	 */
	getSelRowVal : function(fromFlds,toFlds){
		if(!fromFlds) return;
		if(!toFlds) return;
		if(fromFlds.length != toFlds.length){
			alert("在调用 getRowVal 方法时，所传的第一个参数和第二个参数中的列名个数必须相等！");
			return;
		}
		var selRow = this.getSelRow();
		var jsonVal = {};
		for(var i=0,count = fromFlds.length; i<count; i++){
			jsonVal[toFlds[i]] = selRow.get(fromFlds[i]);	
		}
		return jsonVal;
	},
	/**
	 * 获取选中的主键ID
	 * @return {}
	 */
	getSelId : function(msg){
		var selRow = this.getSelRow(msg);
		if(!selRow) return null;
		var selId = null;
		selId = selRow.get(this.keyField);	//selRow 就是选中的ID值
		if(!selId){
			selId = selRow.id;
			if(!selId){
				alert(this.keyField +" 不存在，无法获取其值！");
				return null;
			}
		} 
		return selId;
	},
	/**
	 * 当Grid 有复选框时获取选中的行的主键ID，
	 * 多个ID之前以逗号隔开
	 * @return {}
	 */
	getSelIds : function(){
		var selRows = this.getSelRows();
		if(!selRows) return null;
		var ids = [];
		for(var i=0; i<selRows.length; i++){
			var selRow = selRows[i]; 
			selId = selRow.get(this.keyField);
			if(!selId) selId = selRow.id;
			ids[i] = selId;
		}
		return ids.join(",");
	},
	/**
	 * 获取选中的行数组对象
	 */
	getSelRows : function(){
		var selRows =(this.selectType == "check") ? this.getSelectionModel().getSelections() : [this.getSelRow()];
		if(selRows && selRows.length > 0){
			for(var i=0,count=selRows.length; i<count; i++){
				var selRow = selRows[i];
				var id = selRow.id;
				if(id == this.gatherIdVal){/*如果统计行选中，则删除*/
					selRows.remove(selRow);
				}
			}
		}
	
		if(!selRows || selRows.length==0){
			Ext.MessageBox.alert("警告","请选择要处理的数据！");
			return null;
		}
		return selRows;
	},
	/**
	 * 清附 Store 中的所有数据
	 */
	removeAll : function(){
		var store = this.getStore();
		if(store && store.getCount()>0) store.removeAll();
	},
	/**
	 * 添加记录
	 * @param {} objVal
	 * @param Boolean isEnd 是否添加到最后，true : 插入到最后, false : 插入到 Store 的最前面 
	 */
	addRecord : function(objVal,isEnd){
		var recordDef = this.recordDef;
		var recordObj = new recordDef(objVal);
		if(objVal[this.keyField]){
			recordObj.id = objVal[this.keyField];
		}
		var store = this.getStore();
		if(!isEnd && store && store.getCount()>0){
			store.insert(0, recordObj);
		}else{
			store.add(recordObj);
		}
	},
	/**
	 * 更新选中行的数据
	 * @param {} jsonVal  要更新的 jsonVal 值对象,json 格式
	 */
	setVals2SelRow : function(jsonVal){
		var row = this.getSelRow();
		for(var key in jsonVal){
			row.set(key,jsonVal[key]);
		}
	},
	/**
	 * 加载 JSON data 数据
	 * @param {} jsonDatas 要加入到Grid 中的 json 数据
	 * @param {} append 是否追加 true : 将追加 false : 删除旧数据加入新数据
	 */
	loadJsonDatas : function(jsonDatas,append){
		var store = this.getStore();
		store.loadData(jsonDatas, append);
	},
	/**
	 * 重新加载 Grid 数据
	 * @param _params 重新加载数据时要传的参数 {}
	 * @param callback 数据加载完成后的回调函数
	 */
	reload : function(_params,callback){
		this.params = {start:0,limit:this.pageSize};
		if(!this.needPage){
			if(_params){
				Ext.apply(this.params,_params);
			}
			this.params.start = -1;
			this.params.limit = -1;
		}else{
			if(_params){
				Ext.apply(this.params,_params);
			}
		}
		this.isLoad = true;
		var option = {params : this.params};
		if(callback){
			option.callback = callback;
		}
		this.store.load(option);
	},
	/**
	 * 调整 Grid 高度
	 * @param {} ph [width,height]
	 * @param isChangeWidth 是否需要改变宽， true : 同时改变宽度， false : 不改变宽度
	 */
	resize : function(ph,isChangeWidth){
		var height =  ph[1];
		if(isChangeWidth){
			this.setWidth(ph[0]);
			height = height - 40;
		}
		if(this.needPage){
			height -= 25;
		}
		this.setHeight(height);
		//重新设置分页大小
		this.setPageSize(height);		
	},
	setPageSize :function(gridHeight){
		//this.pageSize = gridHeight/22;

//		alert("setPageSize="+this.pageSize);

	},
	/**
	 * 设置Grid高度，
	 * 此方法与 setHeight() 区别是，
	 *  setGH 内部调用的是 setHeight() ； 然后，重新计算每页分页大小
	 * @param {} gridHeight
	 */
	setGH : function(gridHeight){
		this.setHeight(gridHeight);
		//this.pageSize = gridHeight/22;
	},
	/**
	 * 设置Grid宽度，
	 * @param {} width Grid 宽度值
	 */
	setGW : function(width){
		this.setWidth(width);
	},
	/**
	 * 显示或隐藏列
	 * 例： appgrid.setHeadersVisible(true,"sex,name"); //显示 性别和性名列
	 * @param isShow [true : 隐藏指定的列,false:显示指定的列]
	 * @param cmns 要显示或隐藏的列，多个用“，”分隔
	 */
	setHeadersVisible : function(isShow,cmns){
		if(!cmns) return;
		var indexArr = [];
		var cmns = cmns.split(",");
		var cmnModel = this.getColumnModel();
		for(var i=0,count=cmns.length; i<count; i++){
			var cmn = cmns[i];
			var index = cmnModel.findColumnIndex(cmn);
			if(index == -1) continue;
			indexArr[indexArr.length] = index;
		}
		if(!indexArr || indexArr.length == 0) return;
		for(var i=0,count=indexArr.length; i<count; i++){
			var col = indexArr[i];
			cmnModel.setEditable(col,!isShow);
			cmnModel.setHidden(col,isShow);
		}
		this.getView().refresh(true);
	},
	/**
	 * 设置Grid 列头文本
	 * 例： appgrid.setHeaders({sex:'性别',name:'新姓名'}); //显示 性别和性名列
	 * @param headerCfgs 要设置的列的表头配置 : {sex:'性别',name:'新姓名'}
	 */
	setHeaders : function(headerCfgs){
		if(!headerCfgs) return;
		var cmnModel = this.getColumnModel();
		for(var cmn in headerCfgs){
			var header = headerCfgs[cmn];
			var col = cmnModel.findColumnIndex(cmn);
			if(col == -1) continue;
			cmnModel.setColumnHeader(col,header);
		}
		this.getView().refresh(true);
	}
});

//注册成xtype以便能够延迟加载   
Ext.reg('appgrid', Ext.ux.grid.AppGrid);  