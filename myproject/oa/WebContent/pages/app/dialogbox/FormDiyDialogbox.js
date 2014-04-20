/**
 * 流程业务表单选择弹出窗口
 * 业务品种管理 --> 配置流程 --> 必做业务事项/选做业务事项 Grid 用到
 * @author 程明卫
 * @date 2012-12-07
 */
define(function(require, exports) {
	exports.DialogBox = {
		parentCfg : null,
		params : null,
		appWin : null,
		appTree : null,
		appGrid : null,
		selvals : null,
		seltxts : null,
		treegridpanel :null,
		showClearCheck : false,	/*当弹出业务表单树时，是否清除上一次选中的节点。 false : 不清除； true : 清除*/
		callbackdata :null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			this.params = parentCfg.params;
			this.sysid = parentCfg.sysid;
		},
		
		createAppWindow : function(){
			this.appGrid = this.createAppGrid();
			this.appTree = this.createAppTree();
			this.treegridpanel = this.cmpt();
			this.appWin = new Ext.ux.window.MyWindow({width:570,height:390,items:[this.treegridpanel]});
		},
		/*--> 这是组装方法<--*/
		cmpt : function(){
			var gridpanel = new Ext.Panel();
			gridpanel.add(this.appGrid);
			var treepanel = new Ext.Panel();
			treepanel.add(this.appTree);
			var treegridPanel = FormUtil.getLayoutPanel([.5,.5],[treepanel,gridpanel]);
			return treegridPanel;
		},
		createAppTree : function(){
			var _this = this;
			var recode =new Array( _this.params.recode);
			var _params = {recode:recode};
			var apptree = new Ext.ux.tree.MyTree({fieldLabel : '业务表单',
				region:'west',
				width:270,
				height:350,
				isLoad : true,
				url:'./sysTree_reslist.action',
				params: _params
				});
			apptree.addListener('click',function(node){
				var id = node.id;
				_this.appGrid.reload({restypeId:id,isenabled:1});
			});
			return apptree;
		},
		
			createAppGrid : function(){
			var _this = this;
			var structure_1 = [{
			    header: 'id',
			    name: 'id',
			    hidden :true,
			    width: 80
			},
			{
			    header: '客户编号',
			    name: 'code',
			    width: 80
			},
			{
			    header: '所属资源',
			    name: 'name',
			    width: 80
			}];
			var appgrid = new Ext.ux.grid.AppGrid({
			    structure: structure_1,
			    url: './sysGvlist_list.action',
			    needPage: false,
			    width : 270,
			    height : 350,
			    isLoad: false,
			    keyField: 'id'
			});
			
			appgrid.addListener('dblclick',function(e){
				_this.setValue();
			});
			return appgrid;
		},
		hide: function(){
			this.appWin.hide();
		},
		setValue: function(){
			var data = this.appGrid.getCmnVals("id,name");
			this.parentCfg.callback(data);
			this.hide();
		},
		addButtons : function(apptree){
			var _this = this;
			var btnOk = new Ext.Button({text:Btn_Cfgs.CONFIRM_BTN_TXT,iconCls:Btn_Cfgs.CONFIRM_CLS,handler:function(){
				var flag = true;
				if(apptree.isCheck){
					_this.selvals = apptree.getCheckIds();
					_this.seltxts = apptree.getCheckTexts();
				}else{
					_this.selvals = apptree.getSelId();
					_this.seltxts = apptree.getSelText();
				}
				if(!_this.selvals){
					ExtUtil.alert({msg:Msg_SysTip.msg_noSelNode});
					return;
				}
				_this.parentCfg.callback(_this.selvals,_this.seltxts);
				_this.appWin.hide();
			}});
			apptree.tbars.add(btnOk);
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
				this.appWin.show(_parentCfg.parent.getEl());
			}else{
				this.appWin.show(_parentCfg.parent.getEl());
			}
			if(_parentCfg.showClearCheck){
				this.showClearCheck = _parentCfg.showClearCheck;
			}
			if(this.appTree.isCheck && this.showClearCheck){
				this.appTree.uncheckAll();
			}
		},
		setParentCfg : function(_parentCfg){
			this.parentCfg = _parentCfg;
			this.params = this.parentCfg.params;
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