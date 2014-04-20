/**
 * 封装 Ext CompositeField 类
 * 重写期 getValue 和 setValue 方法
 */
Ext.namespace("Ext.ux.form");
/**
 * 
 * @class Ext.ux.form.MyCompositeField
 * @extends Ext.form.CompositeField
 */
Ext.ux.form.MyCompositeField = Ext.extend(Ext.form.CompositeField,{
	itemNames : '',	/*元素名字字符串，例：itemNames : 'yearLoan,monthLoan,dayLoan'*/
	sigins : ',', /*默认用逗号分隔 ,如果此值为 null ,则 getValue 方法将以 json 对象返回值*/
	setValue : function(v){
		this.reset();
		var _this = this;
		if(this.itemNames && !this.sigins){
			if(this.itemNames && this.itemNames.indexOf(",")!=-1){
				var nameArr = this.itemNames.split(",");
				count = nameArr.length;
				this.items.each(function(item){
					if(_this.isFilterItem(item)) return;
					var name = item["name"];
					if(!name || name == "undefined") return;
					for(var i=0; i<count; i++){
						if(name == nameArr[i]){
							var val = v[name];
							item.setValue(val);
							break;
						}
					}
				});
			}
		}else{
			if(!v || v == 'undefined' ) return;
			var indexOfsigins = v.indexOf(this.sigins);
			var vals = v.split(this.sigins);
			var index = 0;
			this.items.each(function(item){
				if(_this.isFilterItem(item)) return;
				item.setValue(vals[index]);
				index++;
			});
		}
	 	Ext.ux.form.MyCompositeField.superclass.setValue.call(this, v);
	},
	getValue : function(){
		var vals = null;
		if(this.sigins){
			vals = this.getValueBySigins();
		}else{
			
			vals = this.getValueByJson();
		}
		return vals;
	},
	/**
	 * 将获取的值以JSON 对象形式返回
	 * @return {}
	 */
	getValueByJson : function(){
		var vals = {};
		this.items.each(function(item){
			var itemName = item["name"];
			if(!itemName || itemName == "undefined") return;
			if(!item["getValue"]) return;
			var xtype = item.getXType();	
			var v = (xtype=='mydatefield') ? item.getValueAsStr() : item.getValue();
			if(v && v == 'NaN') v = "";
			vals[itemName] = v;
		});
		
		return vals;
	},
	/**
	 * 将获取的值用 singins 所设定的符号连接后以字符串形式返回
	 * @return {String}
	 */
	getValueBySigins : function(){
		var vals = [];
		this.items.each(function(item){
			if(!item["name"]) return;
			if(!item["getValue"]) return;
			var v = item.getValue();
			vals[vals.length] =(v || v==0) ? v : "";
		});
		if(null == vals || vals.length == 0) return "";
		var flag = false;
		for(var i=0,count=vals.length; i<count; i++){
			if(vals[i]){
				flag = true;
				break;
			}
		}
		if(!flag) return "";
		vals = (vals && vals.length == 1) ? vals[0] : vals.join(this.sigins);
		return vals;
	},
	reset : function(){
		var _this = this;
		this.items.each(function(item){
			if(!item["reset"]) return;
			if(_this.isFilterItem(item)) return;
			item.reset();
		});
	},
	/**
	 * 是否是需要过滹的元素项
	 * @param {} item
	 * @return {Boolean}
	 */
	isFilterItem : function(item){
		var xtype = item.getXType();
		var name = item["name"];
		if((!name || name=='undefined') || (xtype && xtype=='displayfield ')) return true;
		return false;
	},
	/**
	 * 验证字段值
	 */
	validate : function(){
		var valid = true;
		this.items.each(function(item){
			if(item.hasOwnProperty("allowBlank") && !item["allowBlank"]){
				var val = item["getValue"] ? item.getValue() : null;
				if(valid  && (val==null || val == 'undefined')){
					valid = false;
					return;
				}
			}
		});
		return valid;
	}
});

//注册成xtype以便能够延迟加载   
Ext.reg('mycompositefield', Ext.ux.form.MyCompositeField);  