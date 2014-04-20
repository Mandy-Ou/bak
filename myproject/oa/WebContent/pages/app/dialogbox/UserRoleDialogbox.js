/**
 * 下一环节选择弹出窗口
 * 业务审批 --> 下一环节处理人选择时弹出
 * @author 程明卫
 * @date 2013-01-04
 */
define(function(require, exports) {
	exports.DialogBox = {
		url : './sysTree_useRolelist.action',
		parentCfg : null,
		params : null,
		appWin : null,
		controlType : 1, /* 1 : 以单选按钮形式显示, 2 : 以复选框形式显示*/
		controlName : Ext.id(null,'userRoleGroup'),
		appPanel : null,
		selvals : null,
		seltxts : null,
		ID_SIGINS : '#ID#',
		NAME_SIGINS : '#NAME#',
		createAppWindow : function(){
			this.appPanel = this.createAppPanel();
			this.appWin = new Ext.ux.window.MyWindow({width:300,height:350,items:this.appPanel});
		},
		createAppPanel : function(){
			var _this = this;
			var topBars = this.getTopBars();
			var appPanel = new Ext.Panel({width:400,height:350,tbar:topBars});
			return appPanel;
		},
		getTopBars : function(){
			var _this = this;
			var barItems = [{text:Btn_Cfgs.CONFIRM_BTN_TXT,iconCls:Btn_Cfgs.CONFIRM_CLS,handler:function(){
				var flag = true;
				_this.getValue();
				if(!_this.selvals){
					ExtUtil.alert({msg:Msg_SysTip.msg_noSelUser});
					return;
				}
				_this.parentCfg.callback(_this.selvals,_this.seltxts);
				_this.appWin.hide();
			}},{text:Btn_Cfgs.RESET_BTN_TXT,iconCls:Btn_Cfgs.RESET_CLS,handler:function(){
				_this.reset();
				_this.parentCfg.callback(_this.selvals,_this.seltxts);
			}},{text:Btn_Cfgs.CLOSE_BTN_TXT,iconCls:Btn_Cfgs.CLOSE_CLS,handler:function(){
				_this.reset();
				_this.appWin.hide();
			}}];
			var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems});
			return toolBar;
		},
		/**
		 * 显示弹出窗口
		 * @param _parentCfg 弹出之前传入的参数
		 */
		show : function(_parentCfg){
			if(_parentCfg) this.setParentCfg(_parentCfg);
			if(!this.appWin){
				this.createAppWindow();
			}
			this.selvals=null;
			this.seltxts=null;
			if(_parentCfg.parent){
				this.appWin.show(_parentCfg.parent);
			}else{
				this.appWin.show();
			}
			this.load();
		},
		setParentCfg : function(_parentCfg){
			this.parentCfg = _parentCfg;
			var selval = _parentCfg.selval;
			if(selval){
				this.selvals = selval;
			}else{
				this.reset();
			}
			this.params = this.parentCfg.params;
		},
		load : function(){
			if(this.appPanel.rendered){
				this.loadData();
			}else{
				var _this = this;
				this.appPanel.addListener('render',function(panel){
					_this.loadData();
				});
			}
		},
		loadData : function(){
			this.appPanel.removeAll();
			var _this = this;
			 EventManager.get(this.url,{params:this.params,sfn:function(json_data){
			 	_this.addCmpts(json_data);
			 }});
		},
		/**
		 * 添加组件
		 */
		addCmpts : function(json_data){
			if(null == json_data || json_data.length == 0) return;
			var controlTemp = this.getControlHtmlByType();
			for(var i=0,count=json_data.length; i<count; i++){
				var groupData = json_data[i];
				var users = groupData.users;
				var arrs = [];
				for(var j=0,len=users.length; j<len; j++){
					var user = users[j];
					arrs[j] = controlTemp.replace(this.ID_SIGINS,user.id).replaceAll(this.NAME_SIGINS,user.name);
				}
				var groupName = groupData.groupName;
				var html = arrs.join(" ");
				var fieldSet = new Ext.form.FieldSet({style:'margin-left:5px;margin-right:5px;', title : groupName,html : html});
				this.appPanel.add(fieldSet);
			}
			this.appPanel.doLayout();
			var _this = this;
			var task = new Ext.util.DelayedTask(function(){
				_this.setValue(_this.selvals);
			});
			task.delay(500);
		},
		getControlHtmlByType : function(){
			var type = "radio";
			if(!this.controlType || this.controlType == 1){/*单选框*/
				type = "radio";
			}else{/*复选框*/
				type = "checkbox";
			}
			return '<input type="'+type+'" name="'+this.controlName+'" value="'+this.ID_SIGINS+'" text="'+this.NAME_SIGINS+'"/>'+this.NAME_SIGINS+' &nbsp;&nbsp;';
		},
		setValue : function(v){
		  if(!v){
		  	this.reset();
		  	return;
		  }
		  var vals = v.split(",");
		   var objs = Cmw.getElesByName(this.controlName);
		   var selVals = [];
		   var selTxts = [];
	       for(var i=0; i<objs.length; i++) {
	       	  var obj = objs[i];
	          obj.checked = false;
	          var val = obj.value;
	          var txt = obj.getAttribute('text');
	          for(var j=0,len=vals.length; j<len; j++){
	          	if(val == vals[j]) {  
	         	  obj.checked = true;
	         	  selVals[selVals.length] = val;
	         	  selTxts[selTxts.length] = txt;
	         	  break;
	       	  	}  
	          }
	       }
	       this.selvals = selVals.join(",");
	       this.seltxts = selTxts.join(",");
		},
		getValue : function(){
			var vals = [];
			var txts = [];
			var objs = Cmw.getElesByName(this.controlName);
		    for(var i=0;i<objs.length;i++){
		    	var obj = objs[i];
		        if(!obj.checked) continue;
		        vals[vals.length] = obj.value;
		        var txt =  obj.getAttribute('text');
		        txts[txts.length] = txt;
		    }
		    if(vals.length > 0){
		    	this.selvals = vals.join(",");
		    }else{
		    	this.selvals = null;
		    }
		    
		    if(txts.length > 0){
		    	this.seltxts = txts.join(",");
		    }else{
		    	this.seltxts = null;
		    }
		    return this.selvals;
		},
		/**
		 * 重置清空（取消选中状态）
		 */
		reset : function(){
			var objs = Cmw.getElesByName(this.controlName);
			if(!objs) return;
	        for(var i=0; i<objs.length; i++) {
	       	  var obj = objs[i];
	          obj.checked = false;  
	        }
	        this.selvals = null;
	        this.seltxts = null;
		},
		/**
		 * 设置Window的标题
		 */
		setTitle : function(title){
			this.appWin.setTitle(title);
		},
		/**
		 * 销毁组件
		 */
		destroy : function(){
			if(null != this.appWin){
				this.appWin.close();	//关闭并销毁窗口
				this.appWin = null;		//释放当前窗口对象引用
			}
		}
	};
});