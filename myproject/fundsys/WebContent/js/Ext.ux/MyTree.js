/**
 * 封装 Ext Tree 只需传入更少参数即可构造成一棵树
 */
Ext.namespace("Ext.ux.tree");
/**
 * 继承Ext Tree 
 * @class Ext.ux.tree.MyTree
 * @extends Ext.tree.TreePanel
 */
Ext.ux.tree.MyTree = Ext.extend(Ext.tree.TreePanel,{
	idprefix : '',	//ID前缀
	url : '',	//树连接的URL  须提供
	rootText : '', //根节点显示的文本
	tbars : null, /*工具栏Bars private */
	barItems : null, //工具栏子项
	expType : 0, // expType 的值为 0 : 展开根节点下一级节点 , 1 : 展开 tree 的所有节点
	curNode : null, //当前点击节点
	regridCfg : null, //与树关联的Grid 参数设置，用来当点击节点时重新加载Grid,例如：{url:'./doEmpGetList.action',params:{id:"1001"},ds : ds}
	autoScroll : true,
	enableDD : true,
	ddScroll :false,
	bodyStyle :'overflow-y:hidden;overflow-x:hidden;',
	containerScroll : true,
	rootVisible : true,
	border : false,
	isLoader : false,
	isCheck : false,	/*---> 是否有复选框 <---*/
	params : null,
	checkIdsMap : new HashMap(),	//存放所有选中的ID的HashMap 对象
	checkState : false,	//标识CheckBox 是否有发生改变。 false : 未发生改变，true:发生改变
	// private
	initComponent : function() {
		var self = this;
		if(!this.idprefix) this.idprefix = Cmw.getUuid();
		//this.id = "tree_"+this.idprefix;
		this.loader =  new Ext.tree.TreeLoader({
			dataUrl : this.url,
			listeners : {loadexception:function(this_load, node, response){
				EventManager.existSystem(response);
			},
			load : function(this_load, node, response){
				EventManager.existSystem(response);
			}}
		});
		this.makeParams();
 		
		Ext.ux.tree.MyTree.superclass.initComponent.call(this);
		this.tbars = this.initTBar();
		this.add(this.tbars);
		this.initRoot();
	},
	initRoot : function(){
		var _idprefix = this.idprefix;
		var text = this.rootText;
		var _rootVisible=true;
		if(!this.rootText){	//如果没有为根节点提供 text ，则隐藏根节点
			text = "根节点";
			_rootVisible = false;
		}
		this.rootVisible = _rootVisible; 
	   //菜单根节点
		var treeRoot = new Ext.tree.AsyncTreeNode({
					text : text,
					draggable : false,
					id : 'root_'+_idprefix
		});
		this.setRootNode(treeRoot);
	},
	// private
	initEvents : function() {
		var self = this;
		 Ext.ux.tree.MyTree.superclass.initEvents.call(this);
		 switch(this.expType){
		 	case 0 : // 展开根节点
		 	 if(this.root) this.root.expand();
		 	 break;
		 	case 1 : //展开所有节点
		 	 this.expandAll();
		 	 break;
		 }
		 this.on("click",function(node,e){
				self.curNode = node;
				self.reLoadGD();
		 });
		if(this.isCheck){	//支持复选框
			this.on("checkchange",function(node,checked){
				self.toggleCheckParents(node,checked);
				self.toggleCheck(node,checked);
				self.checkState = true;	//标识CheckBox 有发生改变。
		 	});
		}
	},
	initTBar : function(){
		var self = this;
		var tbar = new Ext.Toolbar({
			items : [{
					text : Btn_Cfgs.EXPAND_BTN_TXT, /*-- 展开 --*/
					iconCls:Btn_Cfgs.EXPAND_CLS,
					tooltip:Btn_Cfgs.EXPAND_TIP_BTN_TXT,
					handler : function(){
						self.expandAll();
					}
				},{
					text : Btn_Cfgs.COLLAPSE_BTN_TXT, /*-- 收起 --*/
					iconCls:Btn_Cfgs.COLLAPSE_CLS,
					tooltip:Btn_Cfgs.COLLAPSE_TIP_BTN_TXT,
					handler : function(){
						self.collapseAll();
					}
				},{
					text : Btn_Cfgs.REFRESH_BTN_TXT, /*-- 刷新 --*/
					iconCls:Btn_Cfgs.REFRESH_CLS,
					tooltip:Btn_Cfgs.REFRESH_TIP_BTN_TXT,
					handler : function(){
						self.reload();
					}
				}]
		});
		return tbar;
	},
	/**
	 * 全选所有节点
	 */
	checkAll : function(){
		if(this.isCheck){
			this.toggleCheck(this.root,true);
		}
	},
	/**
	 * 反选所有节点
	 */
	uncheckAll : function(){
		if(this.isCheck){
			this.toggleCheck(this.root,false);
		}
	},
	/**
	 * 全选/反选指定节点下的所有子节点
	 * @param node 要选中或反选的父节点
	 * @param checked 是否选中 true : 选中, false:不选中
	 */
	toggleCheck : function(parentNode,checked){
		var checkbox = parentNode.getUI().checkbox;
		if(checkbox){
			var chked = checkbox.checked;
		 	if(chked != checked){
		 		 parentNode.getUI().checkbox.checked = checked;
		 	}
		 	this.doCacheCheckIds(parentNode,checked);
		}
			
		var self = this;
		var hasChilds = parentNode.hasChildNodes();
		if(hasChilds){
			if(!parentNode.isExpanded()) parentNode.expand();
			var childs = parentNode.childNodes ;
			Ext.each(childs,function(node){
				self.toggleCheck(node,checked);
			},this);
		}
	},
	/**
	 * 选中/反选父节点
	 * @param {} currNode 当前节点
	 * @param {} checked	是否选中 true : 选中,false:不选中
	 */
	toggleCheckParents : function(currNode,checked){
		var parentNode = currNode.parentNode;
		while(parentNode && (parentNode.id).indexOf('root_') == -1 ){
			if(!parentNode.getUI().checkbox){
				parentNode = parentNode.parentNode;
				 continue;
			}
		    if(checked){
		        parentNode.getUI().checkbox.checked = checked;
		        this.doCacheCheckIds(parentNode,checked);
		    }else{
		        if(!checked && !this.ischeckedMoreNodes(parentNode)){
					parentNode.getUI().checkbox.checked = checked;
					this.doCacheCheckIds(parentNode,checked);
				} 
		    }
		    parentNode = parentNode.parentNode;
		}
	},
	/**
	 * 处理缓存中选中的ID。
	 * 如果节点选中，则往缓存中添加选中节点ID。如果是取消选中，则移除此节点ID
	 * @param {} node	要处理的节点
	 * @param {} checked	选中/取消选中 true : 往缓存中添加节点ID； false : 移除节点ID
	 */
	doCacheCheckIds : function(node,checked){
		var id = node.id;
		var text = node.text;
		if(checked){
			this.checkIdsMap.put(id,text);
		}else{
			this.checkIdsMap.remove(id);
		}
	},
	/**
	 * 根据参数 isReturnStr 指定的类型 
	 *  返回所有选中节点的ID列表。
	 * @param {} isReturnStr 参数值说明：null或false 将以字符串拼接ID返回； true : 将直接返回数组
	 */
	getCheckIds:function(isReturnStr){
		var values = this.checkIdsMap.getKeys();
		if(null == values || values.length == 0) return null;
		return (!isReturnStr) ? values.join(",") : values;
	},
	/**
	 * 根据参数 isReturnStr 指定的类型 
	 *  返回所有选中节点的名称列表。
	 * @param {} isReturnStr 参数值说明：null或false 将以字符串拼接 text 返回； true : 将直接返回数组
	 */
	getCheckTexts:function(isReturnStr){
		var values = this.checkIdsMap.getValues();
		if(null == values || values.length == 0) return null;
		return (!isReturnStr) ? values.join(",") : values;
	},
	/**
	 * 判断父节点是否存在一个以上选中的子节点
	 * @param {} parentNode
	 * @return {}
	 */
	ischeckedMoreNodes : function(parentNode){
		var checkedCount = 0;
		var childs = parentNode.childNodes;
		Ext.each(childs,function(node){
			var checked = node.getUI().checkbox.checked;
			if(checked) checkedCount++;
		},this);
		return checkedCount;
	},
	/**
	 * 根据传入的ID集合字符串，选中指定节点
	 * @param {} ids	要选中的节点ID字符串集合,","号分隔
	 */
	checkNodeByIds : function(ids){
		//--> 先把所有节点的选中状态改为取消选中 <--//
		this.toggleCheck(this.root,false);
		
		if(!ids) return;
		var idsArr = ids.split(",");
		var len = idsArr.length;
		var self = this;
		this.checkState = false;
		
		//--->以下传入根节点并根据IDS进行选中节<---//
		(function toggleChecks(parentNode){
			var id = parentNode.id;
		 	var isChecked = false;
		 	for(var i=0; i<len; i++){
		 		if(id == idsArr[i]){
		 			isChecked = true;
		 			break;
		 		}
		 	}
		 	if(isChecked){
		 		if(parentNode.getUI().checkbox) parentNode.getUI().checkbox.checked = true;
		 		self.doCacheCheckIds(parentNode,true);
		 	}
		 
		 	var hasChilds = parentNode.hasChildNodes();
			if(hasChilds){
				if(!parentNode.isExpanded()) parentNode.expand();
				var childs = parentNode.childNodes ;
				Ext.each(childs,function(node){
					toggleChecks(node);
				},this);
			}
		})(this.root);
	},
	/**
	 * Tree 数据加载时的参数处理
	 */
	makeParams : function(){
		var self = this;
		this.loader.on('beforeload',function(loader,node){
			if(!self.isLoad){
				this.baseParams.isLoad = 'false'; //通过这个传递参数，这样就可以点一个节点出来它的子节点来实现异步加载
			}else{
				this.baseParams.isLoad = 'true';
			}
			if(self.params){
				Ext.apply(this.baseParams,self.params);
			}
	  	},this.loader);
	},
	/**
	 * 重新加载数据
	 * @param params 过滤参数
	 * @param callback 重新加载数据后的回调函数
	 */
	reload : function(params,callback){
		if(params) this.params = params;
		this.isLoad = true;
		if(callback){
			this.root.reload(callback,this);
		}else{
			this.root.reload();
		}
		this.curNode = null;
		if(this.isCheck){
			this.checkIdsMap.clear();
			this.checkState = false;
		}
	},
	/**
	 * 获取选中节点的ID,如果没有选中将返回 null
	 * @return 返回选中节点ID
	 */
	getSelId : function(){
		return (this.curNode) ? this.curNode.id : null;
	},
	/**
	 * 获取选中节点的文本,如果没有选中将返回 null
	 * @returns 返回选中节点文本	
	 */
	getSelText : function(){
		return (this.curNode) ? this.curNode.text : null;
	},
	/**
	 * 获取选中节点的指定属性名称的值
	 * @param {} attrName 要取值的属性名称{String}
	 * @return {} 返回属性名称的值	{String}
	 */
	getAttrVal : function(attrName){
		return (this.curNode) ? this.curNode.attributes[attrName] : null;
	},
	/**
	 * 获取当前点击的节点对象
	 * @return {TreeNode} 返回选中的树节点 
	 */
	getCurNode : function(){ 
		return this.curNode;
	},
	/**
	 * 在当前选中节点下添加子节点
	 * @param {} cfg	子节点配置信息
	 */
	addChild2CurNode : function(cfg){
		var cNode = new Ext.tree.TreeNode(cfg);
		this.curNode.appendChild(cNode);
		this.curNode.expand();
	},
	/**
	 * 用来加载Grid的相关配置参数
	 * @param {} cfg {url:'./doEmpGetList.action',params:{id:"1001"},ds : ds}
	 */
	setRegridCfg : function(cfg){
		this.regridCfg = cfg;
	},
	/**
	 * 重新加载Grid控件中的数据
	 */
	reLoadGD : function(){//重新加载Grid
		if(!this.regridCfg) return;
		var url = this.regridCfg.url;
		var params = this.regridCfg.params;
		var ds = this.regridCfg.ds;
		if(!url || !ds) return;
	//如果点击的是子节点就重新加载Grid 数据
	 	if(this.curNode.leaf) Ext.Ajax.request({ 
					url : url,
					params: params || {},
					success : function(response){
						var data = Ext.decode(response.responseText); //将后台JSON字符串转换为JSON对象
						ds.loadData(data);
					},failure : function(response){
						Ext.Msg.alert("失败","获取数据失败！");
					}
		});
	},
	/*---------------- 以下为自定义的一些方法 CODE START  ----------------------*/
	getRootId : function(){
		var node = this.getRootNode();
		return node ? node.id : 0; 
	}
	/*---------------- 以下为自定义的一些方法 CODE END  ----------------------*/
});

//注册成xtype以便能够延迟加载   
Ext.reg('mytree', Ext.ux.tree.MyTree);  