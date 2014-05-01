// 重新扩展的grid组建
	Ext.namespace("Ext.ux.grid");   
	Ext.ux.grid.SimpleGrid = Ext.extend(Ext.grid.GridPanel, {   
       // 表格结构   
        structure : '',  
	   //reader类型如果当为json的时候那么url不能空，当为array的时候dataObject不能为空
		readType:'json',
		// 获取数据的URL   
        url : '',   
		// 关键字段   
        keyField : '',   
		//数据对象
		dataObject:null,
		 
        //是否需要分组，默认为false，如果设置为true须再设置两个参数一个为myGroupField和myGroupTextTpl
		needGroup:false,
		//分组的字段名称
		myGroupField:'',
		//分组显示的模板，eg：{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Items" : "Item"]})
		myGroupTextTpl:'',
		//列模式的选择模式,默认为rowselectModel，如果相设置成check模式，就设置为check
		selectType:'',
        // 默认排序字段   
        defaultSortField : '',   
        // 是否需要分页工具栏，默认为true  
        needPage : true,   
        frame : false,   
		//是否带展开按钮，默认为false
        collapsible : false,   
        animCollapse : true,   
        loadMask : true,   
        viewConfig : {   
            forceFit : true  
        }, 
        
        // private   
        initComponent : function() {   
            if (this.structure != '') {   
                this.initStructure();   
            }   
            Ext.ux.grid.SimpleGrid.superclass.initComponent.call(this);   
        },   
  
        // private   
        initEvents : function() {   
            Ext.ux.grid.SimpleGrid.superclass.initEvents.call(this);   
  
           /* this.getStore().load( {   
                params : {   
                    start : 0,   
                    limit : 30   
                }   
            });   
  
            */if (this.loadMask) {   
                this.loadMask = new Ext.LoadMask(this.bwrap, Ext.apply( {   
                    store : this.store   
                }, this.loadMask));   
            }   
        },   
  
        // private   
        initStructure : function() {   
            var oCM = new Array();   //列模式数组
            var oRecord = new Array();  //容器对数组 
            // 构成grid的两个必须组件: column和record，根据structure将这两个组件创建出来
			
			//判断表格的选择模式
			if(this.selectType=='check'){
				var sm = new Ext.grid.CheckboxSelectionModel();
				oCM[oCM.length] = sm;//在列模式数组中添加checkbox模式按钮
				this.selModel = sm;//并将selModel设置为check模式
			}
			var len = this.structure.length;//得到结构的长度
            for (var i = 0;i < len; i++) {   
                var c = this.structure[i];   
                // 如果字段为hidden，则不生成filters和columnMode   
                if (c.hidden == undefined || !c.hidden) {   
                    oCM[oCM.length] = {   
                        header : c.header,   
                        dataIndex : c.name,   
                        width : c.width,   
                        // 类型为数字则右对齐   
                        align : c.type == 'numeric' ? 'right' : 'left',   
                        // 结构的渲染函数   
                        renderer : c.renderer
                    };   
                }   
                oRecord[oRecord.length] = {   
                    name : c.name,   
                    // 如果类型不是date型则全部为string型   
                    type : c.type == 'date' ? 'date' : 'string'  
                };   
            }   
            // 生成columnModel
            this.cm = new Ext.grid.ColumnModel(oCM);   
            //this.colModel = this.cm;   
            // 默认可排序   
            this.cm.defaultSortable = true;   
            // 生成表格数据容器   
            var record = Ext.data.Record.create(oRecord);   
			//判断读取类别，目前只实现了，jsonreader和arrayReader
			var reader;
			switch (this.readType){
				case 'json':
					reader = new Ext.data.JsonReader( {   
						totalProperty : "totalCount",   
						root : "result",   
						id : this.keyField  //关键字段 
					}, record);  
					this.ds = new Ext.data.GroupingStore( {   
						proxy : new Ext.data.HttpProxy( {   
							url : this.url   
						}),   
						reader : reader,   
						sortInfo : {field : this.defaultSortField,direction : 'ASC'  },   
						remoteSort : true ,
						groupField:this.myGroupField
					});   
					break;
				case 'array':
					reader = new Ext.data.ArrayReader({},record);
					this.ds = new Ext.data.GroupingStore({
						reader: reader,
						data: this.dataObject,
						sortInfo : {field : this.defaultSortField,direction : 'ASC'  },
						groupField:this.myGroupField
					});
					break;
				default:
					break;
			}
			//判断是否需要分组
			if(this.needGroup){
				this.view = new Ext.grid.GroupingView({
					forceFit:true,
					groupTextTpl:this.myGroupTextTpl
				})
			}
            this.store = this.ds;   
            // 生成分页工具栏   
           /* if (this.needPage) {   
                var pagingToolbar = new Ext.PagingToolbar( {   
                    displayInfo : true,   
                    displayMsg : '当前记录数: {0} - {1} 总记录数: {2}',   
                    emptyMsg : '没有符合条件的记录',   
                    store : this.ds 
                });   
                pagingToolbar.pageSize = 30;   
                this.bbar = pagingToolbar;   
                this.bottomToolbar = this.bbar;   
            }   */
            // 将filter加入grid   
           // this.plugins = filters;   
        }   
    });   
