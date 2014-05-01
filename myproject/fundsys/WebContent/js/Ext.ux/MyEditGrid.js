/*
* 封装的综合的可编辑Grid控件
* blog：[url]www.chendw.cn[/url] 
* code by cdw
*/
Ext.namespace("Ext.ux.grid");
Ext.ux.grid.MyEditGrid = Ext.extend(Ext.grid.EditorGridPanel, {
	//id : 'grid',
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
	//Grid 顶部按钮和查询字段
	tBarButtons : [],
	//可编辑元素对象，例:{1:new TextField(),2:new NumberField()}
	editEles : null,
	// 每页显示条数
	pageSize : PAGESIZE,
	//当重新加载数据时，所带的参数
	params : null,
	//要更新的行索引
	modRowIndex : -1,
	// reader类型如果当为json的时候那么url不能空，当为array的时候dataObject不能为空
	readType : 'json',
	// 获取数据的URL
	url : '',
	// 数据对象
	dataObject : null,
	// 容器对数组
	 editRecord : null, 
	//记录定义类 在 initStructure 进行初始化
	recordDef : null,
	// 表格主键
	keyField : '',
	// 绑定查询的列
	findField : null,
	// 是否需要分组，默认为false，如果设置为true须再设置两个参数一个为myGroupField和myGroupTextTpl
	needGroup : false,
	// 分组的字段名称
	myGroupField : '',
	// 分组显示的模板，eg：{text} ({[values.rs.length]} {[values.rs.length > 1 ?
	// "Items" : "Item"]})
	myGroupTextTpl : '',
	// 列模式的选择模式,默认为rowselectModel，如果相设置成check模式，就设置为check
	selectType : '',
	// 默认排序字段
	defaultSortField : 'ID',
	isLoad : true,	//是否及时加载
	// 是否需要分页工具栏，默认为true
	needPage : true,
	frame : false,
	// 是否带展开按钮，默认为false
	collapsible : false,
	animCollapse : true,
	loadMask : true,
	clicksToEdit : 1,/*默认单击就可以编辑*/
	// private
	initComponent : function() {
		if (this.structure != '') {
		this.initStructure();
		}
		Ext.ux.grid.MyEditGrid.superclass.initComponent.call(this);
		this.initBars();
		this.initProps();
	},
	/**
	 * 初始化属性
	 */
	initProps : function(){
		//this.enableColumnResize = true;
		//this.enableColumnMove = false;
	},
	// private
	initEvents : function() {
		Ext.ux.grid.MyEditGrid.superclass.initEvents.call(this);
		this.gatherDataListner();
		this.googleBug();
		/*
		* this.getStore().load( { params : { start : 0, limit : 30 } });
		* 
		*/
		if (this.loadMask) {
		// Ext.MessageBox.wait('sssssss')
		this.loadMask = new Ext.LoadMask(this.bwrap, Ext.apply({
		store : this.store
		}, this.loadMask));
		}
		//调用需添加的事件
		this.addListeners();
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
			if(!_this.gatherCfg) return;
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
				sortable :c.sortable || false,		
				// 类型为数字则右对齐
				align : c.type == 'numeric' ? 'right' : 'left',
				// 结构的渲染函数
				renderer : c.renderer
			};
					
			var editObj = this.editEles[i];
			if(editObj){
				cmnCfg.editor = editObj;
			}
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
		recordDef = Ext.data.Record.create(oRecord);
		// 生成表格数据容器
		//this.recordDef = record;
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
			}, recordDef);
			
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
			reader = new Ext.data.ArrayReader({}, recordDef);
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
	/*
	* @功能:默认的格式化日期函数 @返回格式：2008-09-21
	*/
	formatDate : function(v) {
	return v ? v.dateFormat('Y-m-d') : ''
	},
	initBars : function(){ //初始Bar Grid 中可拷贝此代码共用
		if(!this.tbars) return false;
		var _brBars = this.tbars.getBrToolBars();	//获取换行Bar
		if(!_brBars){
			this.tbar = this.tbars;
		}else{
			var bar = {
						tbar: this.tbars,
						listeners : {
							'render' : function() {
								if(_brBars && _brBars.length>0){
									for(var i=0,count=_brBars.length; i<count; i++){
										_brBars[i].render(this.tbar); // add one
									}
								}
							}
						}
					}
			this.add(bar);
		}
		return true;
	},
	/**
	 * 获取 topBar 工具栏
	 * */
	getTBarItems : function(){
		if(this.tBarButtons && this.tBarButtons.length>0){
			for(var i=0; i<this.tBarButtons.length; i++){
				this.tBarButtons[i] = this.getTControl(this.tBarButtons[i]);
			}
		}
	},
	/**
	 * 根据配置参数返回顶部工具栏上不同的控件
	 * @param {} cfg
	 */
	getTControl : function(cfg){
		var control = {};
		if(cfg){
			var type = cfg.type;
			switch(type){
				case 'label':{
					cfg.text = "&nbsp;&nbsp;"+cfg.text +":";
					control = new Ext.Toolbar.TextItem(cfg);
					break;
				}case 'search':{
					cfg.id = cfg.name;
					if(!cfg.width) cfg.width = 150;
					control = new Ext.ux.SearchField(cfg);
					break;
				}case 'date':{
					cfg.id = cfg.name;
					if(!cfg.format) cfg.format = 'Y-m-d';
					if(!cfg.width) cfg.width = 125;
					//cfg.readOnly = true;
					control = new Ext.form.DateField(cfg);
					break;
				}case 'combo':{
					cfg.id = cfg.name;
					//cfg.hiddenName = cfg.name;
					cfg.triggerAction = cfg.triggerAction || "all";
					cfg.mode = cfg.mode || "local";
					cfg.valueField = cfg.valueField || "id";
					cfg.displayField = cfg.displayField || "name";
					cfg.readOnly = true;
					control = new Ext.form.ComboBox(cfg);
					break;
				}default :{
					control = new Ext.Button(cfg);
					break;
				}
			}
		}
		return control;
	},
	/**
	 * 获取选中的行对象
	 */
	getSelRow : function(){
		var selRow = this.getSelectionModel();
		selRow = selRow.getSelected();
		if(!selRow){
			Ext.MessageBox.alert("警告","请选择要处理的数据！");
			return null;
		}
		return selRow;
	},
	/**
	 * 获取选中的主键ID
	 * @return {}
	 */
	getSelId : function(){
		var selRow = this.getSelRow();
		if(!selRow) return null;
		return selRow.get(this.keyField);
	},
	/**
	 * 当Grid 有复选框时获取选中的行的主键ID，
	 * 多个ID之前以逗号隔开
	 * @return {}
	 */
	getSelIds : function(){
		var selRows = this.getSelRows();
		if(!selRows) return null;
		var ids = "";
		for(var i=0; i<selRows.length; i++){
			var id = selRows[i].get(this.keyField);
			if(!id) continue;
			ids += id+",";
		}
		var pids = ""
		if(!ids){
			pids = "";
		}else{
			pids = ids.substring(0,ids.length-1);
		} 
		
		return !ids ? "" : ids.substring(0,ids.length-1);
	},
	/**
	 * 获取选中的行数组对象
	 */
	getSelRows : function(){
		var selRows = null;
		if(this.selectType == "check"){
			selRows = this.getSelectionModel().getSelections();
		}else{
			selRows = this.getSelRow();
			if(selRows) selRows = [selRows];
		}
		
		if(!selRows || selRows.length==0){
			Ext.MessageBox.alert("警告","请选择要处理的数据！");
			return null;
		}
		return selRows;
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
		if(!this.needPage){
			this.params = _params ? _params : {};
		}else{
			this.params = {start:0,limit:this.pageSize};
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
	 * 清附 Store 中的所有数据
	 */
	removeAll : function(){
		var store = this.getStore();
		if(store && store.getCount()>0) store.removeAll();
	},
	/**
	 * 清附 Store 中指定的一部分数据
	 * @params records 要删除的 records 数组
	 */
	removeRecords : function(records){
		var store = this.getStore();
		if(store && store.getCount()>0) store.remove(records);
	},
	/**
	 * 添加记录
	 * @param {} objVal
	 */
	addRecord : function(objVal){
		var recordObj = new recordDef(objVal);
		this.stopEditing();
		var store = this.getStore();
		if(store && store.getCount()>0){
			store.insert(0, recordObj);
		}else{
			store.add(recordObj);
		}
		var firstIndex = this.getFirstIndex();
		if(firstIndex) this.startEditing(0, firstIndex);
	},
	/**
	 * 更新指定行的数据
	 * @param {} jsonVal  要更新的 jsonVal 值对象,json 格式
	 */
	updateRowVals : function(jsonVal){
		var row = this.store.getAt(this.modRowIndex);
		for(var key in jsonVal){
			row.set(key,jsonVal[key]);
		}
	},
	/**
	 * 获取第一个可编辑元素所在的单元格索引
	 */
	getFirstIndex : function(){
		if(!this.editEles) return null;
		var arr = [];
		for(var key in this.editEles){
			arr[arr.length] = key;
		}
		arr.sort(function compare(a,b){return a-b;});
		return arr ? arr[0] : null;
	},
	/**
	 * 为 EditGrid 对象添加事件
	 */
	addListeners : function(){
		//添加编辑前事件
		this.addListener("beforeedit",function(e){
			//在编辑前获取要编辑的行索引并赋给 modRowIndex
			this.modRowIndex = e.row;
		});
		//添加编辑后事件
		this.addListener("afteredit",function(e){
			//编辑完后调用自定义计算函数 calculate 需自己实现
			if(this.calculate)this.calculate(e.record);
		});
	},
	/**
	 * 返回修改过的所有行的值
	 * @return 以JSON字符串形式返回
	 */
	getModRowVals : function(){
		var rcords = this.store.getModifiedRecords();
		if(null == rcords || rcords.length==0) return;
		var jsonStrs = "";
		if(rcords.length==1){
			var rcord = rcords[0];
			jsonStrs = Ext.util.JSON.encode(rcord.data);
		}else{
			var datas = [];
			for(var i=0,count=rcords.length; i<count; i++){
			 var rcord = rcords[i];
				//须加 if 判断, indexOf 不能少,
				//因为在调用 store 的 remove 方法删除记录时，
			 	//getModifiedRecords中的记录依然缓存着,
			    //所以须调用 indexOf 方法，看修改的记录是否在 store 中依然存在，才能提交
				if(rcord && -1 != this.store.indexOf(rcord)) {
					datas[datas.length] = Ext.util.JSON.encode(rcord.data);
				}
			}
			jsonStrs = "["+datas.join(",")+"]";
		}
		//这个不能少，获取修改完的数据后。必须提交
		for(var i=0,count=rcords.length; i<count; i++){
			var rcord = rcords[i];
			if(rcord && -1 != this.store.indexOf(rcord)) {
					rcord.commit();
			}
		}
		return jsonStrs;
	},
	/**
	 * 删除选中的记录，如果此记录在数据库表中已存在，
	 * 则删除表中的记录，然后再从 store 中移除此行。
	 * @param {} url 删除记示提交到服务器处理的地址
	 * @param {} isRefresh 是否刷新 store , 默认为 false, false : 不刷新 store, true : 刷新 store 
	 */
	delSelRows : function(url,isRefresh){
		var selRows = this.getSelRows();
		if(!selRows) return null;
		var ids = "";
		for(var i=0; i<selRows.length; i++){
			var selRow = selRows[i];
			if(!selRow) continue;
			var id = selRow.get(this.keyField);
			if(id){
				ids += id+",";
			}else{
				selRow.endEdit();
			}
			this.store.remove(selRow);
		}
		if(ids.length>0){	//删除数据库中对应的记录
			var cmp = isRefresh ? this.store : null;	//如果需要刷新就传入 store 对象,否则为空
			EventManager.gd_delData(url,ids,cmp);
		}
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
	 * 将同一个函数 绑定到 Grid中的 store 多个事件上
	 * 示例：参考 orderManage.js 中的 getOrderItemGrid 方法中 changeVals 方法【计算主要产品和总数量方法】
	 * @param {} events 事件字符串，多个以逗号分隔开	
	 * @param {} funObj 要绑定的函数对象
	 */
	addStoreListeners : function(events,funObj){
		if(!events) return;
		events = events.split(",");
		for(var i=0; i<events.length; i++){
			this.store.addListener(events[i],funObj);
		}
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
	} 
});