/**
 * 封装 Ext.form.TriggerField 控件
 * 返回一个含有下拉树的控件 
 *  xtype : appcomboxtree
 */
Ext.namespace("Ext.ux.tree");

Ext.ux.tree.AppComboxTree = Ext.extend(Ext.form.TriggerField,{
	menu : null,
	tree : null,
	hidenField : null,
	treeHeight:200,
	url:null,
	hideBtnOk : false,
	params : {},
	isLoad : true,
	isCheck : false,
	hideOnClick : false,	//点击菜单项是不隐藏
	isChange : false,
	selvals : '',
	seltxts : '',
	treeMinWidth : 200,
	SIGIN : '##',	//ID和文本分隔符
	isAll : false,	//是否将选中节点的 ID和文本一起拼接后返回。 true : 是[返回ID和文本。例如：1001,1002##研发一部,研发二部], false : 只返回ID
	initComponent : function(){
		Ext.ux.tree.AppComboxTree.superclass.initComponent.call(this);
		this.menu = this.createMenu();
	 },
	 initEvents : function() {  
	     Ext.ux.tree.AppComboxTree.superclass.initEvents.call(this);
	},
	initListeners : function(){
		var self = this;
		 this.addListener("afterrender",function(selfcmpt){
		 	var ownerCt = selfcmpt.ownerCt;
			if(ownerCt) ownerCt.add(self.hidenField);
		});
	},
	onTriggerClick : function(){
		if(this.disabled) return;
		if(!this.menu){
			this.menu = this.createMenu();
		}
		this.menu.show(this.el,"tl-bl?");
		this.reload();
	},
	/**
	 * 为下拉框树赋值
	 * @param {} vals 要赋的值
	 */
	setValue : function(vals){
		if(vals && this.disabled){
			this.enable();
		}
		if(vals && vals.indexOf(this.SIGIN) != -1){
			var val_txtArr = vals.split(this.SIGIN);
			if(val_txtArr && val_txtArr.length>1){
				this.selvals = val_txtArr[0];
				this.seltxts = val_txtArr[1];
			}
		}else{
			if(!vals) vals = "";
			this.selvals = vals;
			this.seltxts = vals;
		}
		Ext.ux.tree.AppComboxTree.superclass.setValue.call(this,this.seltxts);
	},
	/**
	 * 当 isAll ： true 时，将会把 ID 和	文本一起拼接返回。
	 * 其格式如下： 1001,1002##研发一部,研发二部
	 * @return {String} 返回选中的节点的ID或 (ID和文本一起返回)
	 */
	getValue : function(){
		if(!this.selvals) return "";
		return (this.isAll) ? this.selvals+this.SIGIN+this.seltxts : this.selvals;
	},
	/**
	 * 重置
	 */
	reset : function(){
		Ext.ux.tree.AppComboxTree.superclass.reset.call(this);
		this.isChange = false;
		this.selvals = null;
		this.seltxts = null;
		this.params = {};
	},
	/**
	 * 返回选中的文本
	 * 选中多个的情况下，以“，”拼接并返回
	 * @return {}
	 */
	getText : function(){
		return this.seltxts ? this.seltxts : "";
	},
	createMenu : function(){
		var tree = this.createTree();
		this.tree = tree;
		var menu  = new Ext.menu.Menu({items:[tree]});
		return menu;
	},
	createTree : function(){
		var width = (!this.width || this.width < this.treeMinWidth) ? this.treeMinWidth : this.width;
		var cfg = {url:this.url,params:this.params,isLoad:this.isLoad,
		width:width,height:this.treeHeight,isCheck:this.isCheck};
		var apptree = new Ext.ux.tree.MyTree(cfg);
		var self = this;
		apptree.addListener('render',function(tree){
			self.addButtons(apptree);
			self.reload();
		});
		apptree.addListener('dblclick',function(node,e){
			if(!node) return;
			self.doConfirm();
		});
		return apptree;
	},
	/**
	 * 点击确定按钮赋值
	 * @param {} apptree
	 */
	doConfirm : function(apptree){
		var self = this;
		if(!apptree) apptree = this.tree;
		if(self.isCheck){
			self.selvals = apptree.getCheckIds();
			self.seltxts = apptree.getCheckTexts();
		}else{
			self.selvals = apptree.getSelId();
			self.seltxts = apptree.getSelText();
		}
		var val = null;
		//if(self.isAll){
		val = self.selvals+self.SIGIN+self.seltxts;
		//}
		self.setValue(val);
		self.menu.hide();
	},
	addButtons : function(apptree){
		var self = this;
		var btnOk = new Ext.Button({text:Btn_Cfgs.CONFIRM_BTN_TXT,iconCls:Btn_Cfgs.CONFIRM_CLS,handler:function(){
			self.doConfirm(apptree);
//			if(self.isCheck){
//				self.selvals = apptree.getCheckIds();
//				self.seltxts = apptree.getCheckTexts();
//			}else{
//				self.selvals = apptree.getSelId();
//				self.seltxts = apptree.getSelText();
//			}
//			var val = null;
//			//if(self.isAll){
//			val = self.selvals+self.SIGIN+self.seltxts;
//			//}
//			self.setValue(val);
//			self.menu.hide();
		}});
		apptree.tbars.addButton(btnOk);
		if(this.hideBtnOk){
			var items = apptree.tbars.items.items;
			for(var i=0,cnt = items.length;i<cnt;i++){
				var btn = items[i];
				var text = btn.getText();
				if(text == Btn_Cfgs.CONFIRM_BTN_TXT){
					btn.hide();
					break;
				}
			}
		}
	},
	
	/**
	 * 获取现居住地区中的树节点
	 */
	comboxTree:function(comBox){
		var _this = this;
		var tree = comBox.tree;
		var tbars = null;
		tree.addListener('click',function(node,e){
			var selId = node.id;
			var Text = [];
			var joinId = [];
			var isNotRegion = selId.indexOf("R");
			if(isNotRegion==-1){
				return;
			}else{
				var Rtext = node.text;
				var subText = null;
				var ismr = Rtext.indexOf("【");
				if(ismr!=-1){
					var subText = Rtext.split("【");
					Text.push(subText[0]);
				}else{
					Text.push(Rtext);
				}
				joinId.push(selId.substr(1));
				var Depth = node.getDepth();
				var Depthj = Depth-1;
				var pnode = null;
				while(--Depth){
					if(Depth==Depthj){
						pnode = node.parentNode;
					}else{
						pnode = pnode.parentNode;
					}
					var pt = pnode.text;
					var pn = pt.indexOf("【");
					if(pn != -1){
						var subText = pt.split("【");
						Text.push(subText[0]);
					}else{
						Text.push(pt);
					}
					joinId.push(pnode.id.substr(1));
				}
			}
			var Textpx = Text.reverse().toString().replaceAll(",","");
			joinNode = joinId.reverse().toString();
			
			var val = null;
			if(_this.isAll){
				val = joinNode+_this.SIGIN +Textpx;
			}
			comBox.menu.hide();
			comBox.reset();
			_this.setValue(val);
			
		});
	},
	/**
	 * 设置查询参数
	 * @param {} params
	 */
	setParams : function(params){
		this.params = params;
		this.isChange = true;
	},
	reload : function(){
		var self = this;
		if(!this.tree.rendered) return;
		if(this.isChange){
			this.tree.reload(this.params,function(){
				if(self.selvals) self.tree.checkNodeByIds(self.selvals);
			});
		}else{
			if(this.selvals) this.tree.checkNodeByIds(this.selvals);
		}
		this.isChange = false;
		this.selvals = null;
		this.seltxts = null;
	}
});
//注册成xtype以便能够延迟加载   
Ext.reg('appcomboxtree', Ext.ux.tree.AppComboxTree);  