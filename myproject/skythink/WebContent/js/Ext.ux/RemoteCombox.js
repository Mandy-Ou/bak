/**
 * 封装 Ext Combox 类
 * 使其支持远程数据加载
 * 使期支持千分位分隔符格式
 */
Ext.namespace("Ext.ux.form");

Ext.ux.form.RemoteComboBox = Ext.extend(Ext.form.ComboBox,{
	readType : 'json',
	mode : 'remote',
	allDispTxt : '',
	restypeId : null,	/* 资源ID引用值 如果是从基础数据取数的话，此处是资源ID引用值  */
	params : null,
	url : null,
	register : null,	/*注册器对象 /注册器对象 参考 constant.js 中的常量定义对象 REGISTER */
	//记录定义类 在 initStructure 进行初始化
	recordDef : null,
	// private
	initComponent : function() {
		this.init();
		Ext.ux.form.RemoteComboBox.superclass.initComponent.call(this);
	},
	// private
	initEvents : function() {
		this.addListeners();
		Ext.ux.form.RemoteComboBox.superclass.initEvents.call(this);
	},
	init : function(){
		if(this.register){
			this.mode = 'local';
			if(!this.restypeId){
				ExtUtil.error({msg:'名为\"'+this.name+'\" 的RemoteCombox对象必须提供 restypeId 值!'});
			}
		}
		this.store = this.getCboStore();
	},
	makeParams : function(){
		if(!this.params){
			this.params = {};
		}
		if(this.restypeId){
			this.params["restypeId"] = this.restypeId;			
		}
	},
	/**
	 * 添加初始化事件
	 */
	addListeners : function(){
		var _this = this;
		this.store.addListener('beforeload',function(_store, op){
			_this.makeParams();
			_store.baseParams = _this.params;
		});
	},
	/**
	 * 根据读取方式，返回不同的 Store 对象
	 */
	getCboStore : function(){
		var _this = this;
		this.recordDef = Ext.data.Record.create([{name : 'id'},{name : 'name'},{name : 'others'}]);
		var store = null;
		if(this.register && this.restypeId){
			var dataArr = Cmw.getComboxDataSource(this.register,this.restypeId);
			if(_this.allDispTxt){
				dataArr = Lcbo_dataSource.getAllDs(dataArr,_this.allDispTxt);		
			}
			store =	FormUtil.getSimpleStore(dataArr);
		}else{
			store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({url : this.url}),
				reader:new Ext.data.JsonReader({
					root : 'list',
					totalProperty : 'totalSize'
				},this.recordDef)
			});
			store.addListener("load",function(store,records,options ){
				if(_this.allDispTxt){
					store.insert(0,new _this.recordDef({id:'',name:_this.allDispTxt}));			
				}
			});
		}
		return store;
	},
	setValue : function(v){
		if(!v) this.setCboValue('');
		var count = this.store.getCount();
		if(this.mode == 'local'){
			 this.setCboValue(v);
			 return this;
		}
		var _this = this;
		if(v){
			if(count > 0){
				_this.setCboValue(v);
			}else{
				var callback = function(records,option,success){
					_this.setCboValue(v);
				}
				this.makeParams();
				this.store.reload({params : this.params,callback:callback});
			}
		}
	},
	setCboValue : function(v){
		var text = v;
        if(this.valueField){
            var r = this.findRecord(this.valueField, v);
            if(r){
                text = r.data[this.displayField];
            }else if(Ext.isDefined(this.valueNotFoundText)){
                text = this.valueNotFoundText;
            }
        }
        this.lastSelectionText = text;
        if(this.hiddenField){
            this.hiddenField.value = Ext.value(v, '');
        }
        Ext.form.ComboBox.superclass.setValue.call(this, text);
        this.value = v;
        return this;
	},
	/**
	 * 重新加载数据
	 * @param {} params 要加载的过滤参数
	 * @param {} callback 加载后的回调函数
	 */
	reload : function(params,callback){
		if(this.mode == 'local') return;
		Ext.apply(this.params,params);
		this.store.reload({params : this.params,callback:callback});
	}
});
//注册成xtype以便能够延迟加载   
Ext.reg('remotecbo',Ext.ux.form.RemoteComboBox);  
