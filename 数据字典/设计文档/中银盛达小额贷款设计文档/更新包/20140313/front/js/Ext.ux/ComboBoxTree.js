/**
 * 下拉框树
 *  使用示例：
 * 	ddtCardLevel = new Ext.ux.ComboBoxTree({
		renderTo : 'ktypename',
		width : 152,
		tree : {
			xtype : 'treepanel',
			loader : new Ext.tree.TreeLoader({
				dataUrl : '../servlet/DDLTreeServlet?ddltreeid=8001'
			}),
			root : new Ext.tree.AsyncTreeNode({
				id : '-1',
				text : '知识库类型列表'
			})
		},
		// all:所有结点都可选中
		// exceptRoot：除根结点，其它结点都可选(默认)
		// folder:只有目录（非叶子和非根结点）可选
		//leaf：只有叶子结点可选
		selectNodeModel : 'exceptRoot'
	});
	ddtCardLevel.setRValue("<%=ktypeid%>","<%=ktypename%>");
 * @type String
 */
var parentTexts='',parentIds = '';
Ext.ux.ComboBoxTree = function(){
	this.treeId = Ext.id()+'-tree';
	this.maxHeight = arguments[0].maxHeight || arguments[0].height || this.maxHeight;
	this.tpl = new Ext.Template('<tpl for="."><div style="height:'+this.maxHeight+'px"><div id="'+this.treeId+'"></div></div></tpl>');
	this.store = new Ext.data.SimpleStore({fields:[],data:[[]]});
	this.selectedClass = '';
	this.mode = 'local';
	this.triggerAction = 'all';
	this.onSelect = Ext.emptyFn;
	this.editable = false;
	this.hidName="";	//存放被选中节点ID的隐藏域的ID
	this.hidtextName = "";	//存放被选中文本的隐藏域的ID
	this.isManyLevel = true;	//用来控制是否加载多级
	this.isAnyNode = false; //是否可选中任意节点，包括带有文件夹的父节点, false : 只能选择子节点, true : 可选择除根节点的任何节点
	this.selectNodeModel = arguments[0].selectNodeModel || 'exceptRoot';
	Ext.ux.ComboBoxTree.superclass.constructor.apply(this, arguments);
}
Ext.ux.ComboBoxTree.prototype.hidName = "";
Ext.extend(Ext.ux.ComboBoxTree,Ext.form.ComboBox, {
	/**
	 * 节点展开事件
	 */
	expand : function(){
		Ext.ux.ComboBoxTree.superclass.expand.call(this);
		if(!this.tree.rendered){
			this.tree.height = this.maxHeight;
			this.tree.hidName=this.hidName;
			this.tree.hidtextName = this.hidtextName;
			this.tree.isManyLevel = this.isManyLevel;
			this.tree.isAnyNode = this.isAnyNode;
			this.tree.border=false;
			this.tree.autoScroll=true;
	        if(this.tree.xtype){
				this.tree = Ext.ComponentMgr.create(this.tree, this.tree.xtype);
			}
			this.tree.render(this.treeId);
	        var combox = this;
	        this.tree.on('click',function(node){
	        	var isRoot = (node == combox.tree.getRootNode());
	        	var selModel = combox.selectNodeModel;
	        	var isLeaf = node.isLeaf();
	        	
	        	if(isRoot && selModel != 'all'){
	        		return;
	        	}else if(selModel=='folder' && isLeaf){
	        		return;
	        	}else if(selModel=='leaf' && !isLeaf){
	        		if(!this.isAnyNode){ //如果不加载除了根节点的任意父节点，则返回。此处只加载子节点
	        		return;
	        		}
	        	}
	        	parentTexts="";
	        	parentIds = "";
	        	txt_val = "";
	        	if(!this.isManyLevel)	//是否加载多级.false: 只加载被选中的级, true : 加载本级和其父级节点
	        	{
	        		parentIds = node.id;
	        		parentTexts = node.text;
	        	}
	        	else
	        	{
	        		var node1=node;
		        	while(node1.id!=combox.tree.getRootNode().id){
		        		parentIds += node1.id+",";
		        		parentTexts += node1.text+",";
		        		node1=node1.parentNode;
		        	}
	        	}
	        	combox.setValue(node);
	        	combox.collapse();
	        });	
	        var root = this.tree.getRootNode();
			if(!root.isLoaded())
				root.reload();
		}
		
    },
   /**
    * 根据节点赋值
    * @param {} node
    */     
	setValue : function(node){
		var ids=parentIds.split(",");
		var texts=parentTexts.split(",");
   		if(ids.length>1 && texts.length >1)
   		{
   			parentTexts="";
   			parentIds = "";
	    	for(var i=(texts.length -2);i>=0;i--)
	    	{
	    			parentIds += ids[i]+",";
	    			parentTexts += texts[i]+"-";
	    	}
	    	parentIds = parentIds.substring(0,parentIds.length-1);	//去掉最后 , - 等符号
	    	parentTexts = parentTexts.substring(0,parentTexts.length-1);
   		}
    	
    	if(this.hidName)	 
	     {
	        document.getElementById(this.hidName).value=parentIds;
	     }
	     if(this.hidtextName)	 
	     {
	     	  document.getElementById(this.hidtextName).value=parentTexts;
	     }
        var text = parentTexts;
        this.lastSelectionText = text;
        if(this.hiddenField){
            this.hiddenField.value = parentIds;
        }
        Ext.form.ComboBox.superclass.setValue.call(this, text);
        this.value = node.id;
    },
    /**
     * 根据节点和文本给下拉框赋值
     * @param {} id
     * @param {} text
     */
    setRValue : function(id,text){
		this.parentIds=id;
    	this.lastSelectionText = text;
    	if(this.hiddenField){
            this.hiddenField.value = id;
        }
        Ext.form.ComboBox.superclass.setValue.call(this, text);
        this.value = id;
        if(this.tree.rendered){
	    	var root = this.tree.getRootNode();
	    	
	    	this.findNode(root,id);
        }
        
    },
  /**
   * 查找节点
   * @param {} node
   * @param {} id
   */  
    findNode : function(node,id){
    	for(var i=0;i<node.childNodes.length;i++){
    		var chNode = node.childNodes[i];
    		if(chNode.id == id){
    			chNode.select();
    			this.setValue(chNode);
    		}else if(!chNode.leaf){
    			if(!chNode.isLoaded()){
    				chNode.reload();
    			}
    			this.findNode(chNode,id);
    		}
    	}
    },
    /**
     * 获取选中值
     * @return {}
     */
    getValue : function(){
    	return typeof this.value != 'undefined' ? this.value : '';
    }
    
});

Ext.ux.ComboBoxTree.prototype.getPValue =  function(){
   		var ids=parentIds.split(",");
   		parentIds="";
   		var i=0;
    	for(i=(ids.length -1);i>=0;i--)
    	{
    		if(i==0)
    		{
    			parentIds += ids[i];
    			break;
    		}
    		if(ids[i]!=null&&ids[i]!='')
    			parentIds += ids[i]+",";
    	}
    	alert(parentIds);
    	return parentIds;
    }
Ext.reg('combotree', Ext.ux.ComboBoxTree);