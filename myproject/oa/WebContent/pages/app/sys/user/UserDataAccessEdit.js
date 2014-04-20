/**
 * 用户数据访问权限设置页面
 * @author 程明卫
 * @date 2012-12-28
 */
define(function(require, exports) {
	exports.viewUI = {
		tab : null,
		params : null,
		appMainPanel : null,
		dataAccessGrid : null,/*数据级别权限Grid*/
		/**
		 * 获取主面板
		 * @param tab Tab 面板对象
		 * @param params 由菜单点击所传过来的参数
		 * 	{tabId : tab 对象ID,apptabtreewinId : 系统程序窗口ID,sysid : 系统ID} 
		 */
		getMainUI : function(tab,params){
			this.setParams(tab,params);
			if(!this.appMainPanel){
				this.createCmpts();
				this.buildCmpts();
			}
			if(!tab.notify){
				var _this = this;
				tab.notify = function(_tab){
					_this.refresh();
				}
			}
			return this.appMainPanel;
		},
		/**
		 * 设置参数
		 */
		setParams : function(tab,params){
			this.tab = tab;
			this.params = params;
		},
		/**
		 * 创建组件
		 */
		createCmpts : function(){
			this.appMainPanel = new Ext.Panel({border:false});
			this.dataAccessGrid = this.createAppGrid();
		},
		/**
		 * 创建数据访问权限Grid
		 */
		createAppGrid : function(){
			var treeGrid = new Ext.ux.tree.TreeGrid({
		        title: '角色权限列表',
		        width: 500,
		        height: 300,
		        renderTo: Ext.getBody(),
		        enableDD: true,
		        columns:[{
		            header: '权限',
		            dataIndex: 'task',
		            width: 230
		        },{
		            header: '数据访问级别',
		            width: 100,
		            dataIndex: 'duration',
		            align: 'center',
		            sortType: 'asFloat',
		            tpl: new Ext.XTemplate('{duration:this.formatHours}', {
		                formatHours: function(v) {
		                    if(v < 1) {
		                        return Math.round(v * 60) + ' mins';
		                    } else if (Math.floor(v) !== v) {
		                        var min = v - Math.floor(v);
		                        return Math.floor(v) + 'h ' + Math.round(min * 60) + 'm';
		                    } else {
		                        return v + ' hour' + (v === 1 ? '' : 's');
		                    }
		                }
		            })
		        },{
		            header: 'Assigned To',
		            width: 150,
		            dataIndex: 'user'
		        }],
		
		        dataUrl: 'treegrid-data.json'
		    });
		    return treeGrid;
		},
		/**
		 * 组装控件
		 */
		buildCmpts : function(){
			this.appMainPanel.add(this.dataAccessGrid);
		},
		refresh : function(){
			alert('refresh');
		},
		resize : function(adjWidth,adjHeight){
			this.appMainPanel.setWidth(adjWidth);
			this.appMainPanel.setHeight(adjHeight);
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function(){
			if(null != this.appMainPanel){
				 this.appMainPanel.destroy();
				 this.appMainPanel = null;
			}
		}
	}
});