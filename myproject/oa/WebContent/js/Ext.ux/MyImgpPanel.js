Ext.ns("Ext.ux");

Ext.ux.MyImgPanel = Ext.extend(Ext.Panel,{
	IdMgr : null,
	isLoad :true,
	frame:false, 
    border:false,
    bodyStyle : 'background-color:#E5E3DF;',  
    initComponent : function(){
		this.init();
		Ext.ux.MyImgPanel.superclass.initComponent.call(this);
	 },
	 init : function(){
	 	var _this = this;
	 	this.initIdMgr();
	 	this.html  = this.panelHtml();
	 	this.addListener('afterrender',function(panel){
			_this.load();
		});
	 },
	 initIdMgr :function(){
	 	this.IdMgr = {
	 		picview : Ext.id(null,'picview'),
	 		/*向上移动Btn id*/
	 		up : Ext.id(null,'up'),
	 		/*向下移动图片id*/
	 		down : Ext.id(null,'down'),
	 		/*还原图片id*/
	 		zoom : Ext.id(null,'zoom'),
	 		/*向左移动图片Id*/
	 		left : Ext.id(null,'left'),
	 		/*向右移动图片id*/
	 		right : Ext.id(null,'right'),
	 		/*放大图片id*/
	 		zoomIn : Ext.id(null,'zoomIn'),
	 		/*缩小图片id*/
	 		zoomOut : Ext.id(null,'zoomOut'),
	 		/* 被移动图片id*/
	 		viewArea : Ext.id(null,'viewArea'),
	 		/*图片容器ID*/
	 		container : Ext.id(null,'Container')
	 	};
	 },
	 panelHtml : function(){
	 	var htmlArr = [
	 		'<div id="'+this.IdMgr.picview+'" class="picview">',
			this.getNoDataHtml(),
			'</div>'
	 	].join(" ");
	 	return htmlArr;
	 },
	 getNoDataHtml : function(){
	 	return '<span style="text-align:center;font-weight:18px;color:red;">没有加载图片</span>';
	 },
	 load : function(){
	 	if(!this.isLoad) return;
		var _this = this;
		this.upDateHtml();
	 },
	 upDateHtml : function(){
	 	var _this = this;
	 	var picview = Ext.get(this.IdMgr.picview);
	 	if(picview){
	 		var updateHtml = this.updateHtml();
	 		this.containUpdateHtml(updateHtml)
	 	}
	 },
	 containUpdateHtml : function(updateHtml){
	 	var _this = this;
	 	var picview = Ext.get(this.IdMgr.picview);
	 	
	 	picview.update(updateHtml,true,function(){
	 			 var image = Ext.get(_this.IdMgr.viewArea); 
	 			 if(image != null){  
		         image.on({  
		            'mousedown':{fn:function(){this.setStyle('cursor','url(images/public/closedhand.cur),default;');},scope:image},  
		            'mouseup':{fn:function(){this.setStyle('cursor','url(images/public/openhand.cur),move;');},scope:image},  
		            'dblclick':{fn:function(){  
		                _this.zoom(image,true,1.2);  
		            }
		            }});
		             image.resize = {  
			            imgWidth:image.getWidth(),  
			            imgHeight:image.getHeight()  
			        };  
		            new Ext.dd.DD(image, 'pic');
		            
		            
		            Ext.get(_this.IdMgr.up).on('click',function(){_this.imageMove('up',image);});       //向上移动  
			        Ext.get(_this.IdMgr.down).on('click',function(){_this.imageMove('down',image);});   //向下移动  
			        Ext.get(_this.IdMgr.left).on('click',function(){_this.imageMove('left',image);});   //左移  
			        Ext.get(_this.IdMgr.right).on('click',function(){_this.imageMove('right',image);}); //右移动  
			        Ext.get(_this.IdMgr.zoomIn).on('click',function(){_this.zoom(image,true,1.5);});        //放大  
			        Ext.get(_this.IdMgr.zoomOut).on('click',function(){_this.zoom(image,false,1.5);});      //缩小  
			        Ext.get(_this.IdMgr.zoom).on('click',function(){_this.restore(image);});            //还原  
	 			 }
	 		})
	 },
	 /**
	  * 缩放图片
	  * @param {} image 图片对象他
	  * @param {} flag true放大,false缩小
	  * @param {} num 缩放倍数
	  */
	 zoom : function(image,flag,num){
	 	var width = image.getWidth();  
	    var height = image.getHeight();  
	    var nwidth = flag ? (width * num) : (width / num);  
	    var nheight = flag ? (height * num) : (height / num);  
	    var left = flag ? -((nwidth - width) / 2):((width - nwidth) / 2);  
	    var top =  flag ? -((nheight - height) / 2):((height - nheight) / 2);   
	    image.animate(  
	        {  
	            height: {to: nheight, from: height},  
	            width: {to: nwidth, from: width},  
	            left: {by:left},  
	            top: {by:top}  
	        },  
	        null,        
	        null,       
	        'backBoth',  
	        'motion'  
	    ); 
	 },
	 /**
	  * 移动图片
	  * @param {} direction  方向
	  * @param {} el 移动图片的对象
	  */
	  imageMove:function(direction, el) {  
    	el.move(direction, 50, true);  
	  }, 
	  /**
	   * 还原图片
	   * @param {} image 被还原的图片对象
	   */
	  restore : function(image){
	     var size = image.resize;  
		    function center(image,callback){  
		        image.center();  
		        callback(el);  
		    }  
		    image.fadeOut({callback:function(){  
		        image.setSize(size.imgWidth, size.imgHeight, {callback:function(){  
		            center(image,function(ee){
		                ee.fadeIn();  
		            });  
		        }});  
		    }  
		    });  
	  },
  	/**
  	 * 更新的 html
  	 * @return {}
  	 */
	 updateHtml : function(){
	 	var updateHtmlArr = [
	 		 '<div id="contral">',
		    '<ul>',
		      '<li></li>',
		      '<li><img src="images/public/up.gif" id ="'+this.IdMgr.up+'" title="上移"></li>',
		      '<li></li>',
		      '<li><img src="images/public/left.gif" id ="'+this.IdMgr.left+'" title="左移"></li>',
		      '<li><img src="images/public/zoom.gif" id ="'+this.IdMgr.zoom+'" title="还原"></li>',
		      '<li><img src="images/public/right.gif" id ="'+this.IdMgr.right+'" title="右移"></li>',
		      '<li></li>',
		      '<li><img src="images/public/down.gif" id ="'+this.IdMgr.down+'" title="下移"></li>',
		      '<li></li>',
		      '<li></li>',
		      '<li><img src="images/public/zoom_in.gif" id ="'+this.IdMgr.zoomIn+'"title="放大"></li>',
		      '<li></li>',
		      '<li></li>',
		      '<li><img src="images/public/zoom_out.gif" id ="'+this.IdMgr.zoomOut+'"title="缩小"></li>',
		      '<li></li>',
		    '</ul>',
		    ' </div>',
		  '<div id="'+this.IdMgr.container+'"><img src="images/public/Sunset.jpg" alt="Moving" name="viewArea" id ="'+this.IdMgr.viewArea+'" /></div>'
	 	].join(" ");
	 	return updateHtmlArr;
	 }
});
Ext.reg("imgPanel",Ext.ux.MyImgPanel);