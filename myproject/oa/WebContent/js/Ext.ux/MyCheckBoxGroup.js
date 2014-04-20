/**
 * 封装 Ext CheckBoxGroup 类 重写其 getValue 和 setValue 方法
 */
Ext.namespace("Ext.ux.form");

Ext.ux.form.MyCheckBoxGroup = Ext.extend(Ext.form.CheckboxGroup,{
	joinType : ',',
	/**
	 * 获取CheckBoxGroup 值方法
	 */
	getValue : function(){
		var v= [];
		if(!this.items) return null;
		if(!this.items.each){
			for(var i=0,count=this.items.length; i<count; i++){
				var item = this.items[i];
				 if(item.checked){
			   		var val = item.getRawValue();
					v.push(val);
			   }  
			}
		}else{
			this.items.each(function(item){
				if(item.getValue()){
					var val = item.getRawValue();
					v.push(val);
				}
			});
		}
		return this.getValueByJoinType(v);
	},
	getValueByJoinType : function(v){
		if(!this.joinType) return v;
		return v.join(this.joinType);
	},
	/**
	 * 根据参数值显示或隐藏复选框的元素项
	 *  例如： chk_etype.hideOrShowItems("1,2",true,true);
	 *     将会把 复选框对象 chk_etype 中值为 1 和 2 的元素项隐藏起来，并且重置 1 和 2 元素项的值
	 * @param {} vals 参数值 多个值用“，”隔开。
	 * @param isHide 是否隐藏 [true:隐藏,false:显示]
	 * @param isReset 是否重置[true:重置,false:不重置]
	 */
	hideOrShowItems : function(vals,isHide,isReset){
		if(!vals) return;
		var v = (vals.split) ? vals.split(this.joinType) : [vals];
		var len=v.length;
		if(this.items.each){
			this.items.each(function(item){
				var value = item.getRawValue();
				for(var i=0; i<len; i++){
					if(value != v[i]) continue;
					if(isReset) item.reset();
					item.setVisible(!isHide);
					break;
				}
			});
		}else{
			for(var j=0,count=len; j<count; j++){
				var item = this.items[j];
				var value = item.getRawValue();
				for(var i=0; i<len; i++){
					if(value != v[i]) continue;
					if(isReset) item.reset();
					item.setVisible(!isHide);
					break;
				}
			}
		}
	},
	/**
	 * 为复选框赋值
	 * @param {} vals
	 */
	setValue : function(vals){
		this.reset();
		if(!vals) return;
		var v = (vals.split) ? vals.split(this.joinType) : [vals];
		var len=v.length;
		if(this.items.each){
			this.items.each(function(item){
				item.setValue(false);
				var value = item.getRawValue();
				for(var i=0; i<len; i++){
					if(value != v[i]) continue;
					item.setValue(true);
					break;
				}
			});
		}else{
			for(var j=0,count=len; j<count; j++){
				var item = this.items[j];
				var value = item.getRawValue();
				for(var i=0; i<len; i++){
					if(value != v[i]) continue;
					item.setValue(true);
					break;
				}
			}
		}
	}
});

//注册成xtype以便能够延迟加载   
Ext.reg('mycheckboxgroup', Ext.ux.form.MyCheckBoxGroup);  