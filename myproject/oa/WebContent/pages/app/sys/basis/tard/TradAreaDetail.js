/**
 * 放款单的记录
 * @author smartplatform_auto
 * @date 2013-11-23 03:33:21
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增 */
		detailPnl : null, // 详情面板
		appWin : null,
		sysId : null,
		payName : null,
		setParams : function(parentCfg) {
			this.parentCfg = parentCfg;
			this.parent = parentCfg.parent;
			this.sysId = this.parent.getSelId();
			this.optionType = parentCfg.optionType || OPTION_TYPE.DETAIL;
		},
		createAppWindow : function() {
			this.detailPnl = this.createDetailPnl();
			this.appWin = new Ext.ux.window.AbsDetailWindow({
					hiddenBtn : true,
						width : 600,
						getParams : this.getParams,
						appDetailPanel : this.detailPnl,
						optionType : this.optionType,
						refresh : this.refresh
					});
		},
		
		/**
		 * 显示弹出窗口
		 * 
		 * @param _parentCfg
		 *            弹出之前传入的参数
		 */
		show : function(_parentCfg) {
			if (_parentCfg)
				this.setParams(_parentCfg);
			if (!this.appWin) {
				this.createAppWindow();
			}
			this.appWin.optionType = this.optionType;
			this.appWin.show();
		},
		/**
		 * 销毁组件
		 */
		destroy : function() {
			if (!this.appWin)
				return;
			this.appWin.close(); // 关闭并销毁窗口
			this.appWin = null; // 释放当前窗口对象引用
		},
		/**
		 * 获取选中的记录参数
		 */
		getParams : function() {
			var parent = exports.WinEdit.parent;
			var selId = null;
			if (parent) {
				selId = parent.getSelId();
			} else {
				ExtUtil.alert({
							msg : '请传入 parent 对象！'
						});
			}
			var params = {id : selId};
			return params;
		},
		/**
		 * 当数据保存成功后，执行刷新方法 [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data) {
			var _this = exports.WinEdit;
			if (_this.parentCfg.self.refresh)
				_this.parentCfg.self.refresh(_this.optionType, data);
		},
		/**
		 * 创建详情面板
		 */
		createDetailPnl : function() {
			var _this=this;
			var htmlArrs_1 = ['<tr><th col="tdedb">特价扣点</th> ' +
					'<td col="tdedb" >&nbsp;</td>' +
					'<th col="sdedb">正价扣点</th> ' +
					'<td col="sdedb" >&nbsp;</td></tr><tr>' +
					'<th col="contOrg">合同单位</th> ' +
					'<td col="contOrg" >&nbsp;</td>',
					'<th col="plevelId">价格等级</th>' +
					'<td col="plevelId" >&nbsp;</td></tr><tr>' +
					'<th col="tradId">所属门店</th> ' +
					'<td col="tradId" >&nbsp;</td>' +
					'<th col="name">分区名称</th> ' +
					'<td col="name" >&nbsp;</td></tr><tr>', 
					'<th col="code">分区编号</th> ' +
					'<td col="code" >&nbsp;</td>' +
					'<th col="creatorName">创建人</th> ' +
					'<td col="creatorName" >&nbsp;</td></tr><tr>' +
					'<th col="createTime">创建日期</th> ' +
					'<td col="createTime" >&nbsp;</td>' +
					'<th col="Modifier">修改人姓名</th> ' +
					'<td col="Modifier" >&nbsp;</td></tr><tr>', 
					'<th col="ModifyTime">修改日期</th> ' +
					'<td col="ModifyTime" >&nbsp;</td>' +
					'<th col="deptid">创建部门</th> ' +
					'<td col="deptid" >&nbsp;</td></tr><tr>' +
					'</tr></tr>'];
			var detailCfgs_1 = [{
			    cmns: 'TWO',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 90,
			    /* title : '#TITLE#', */
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './oaTradArea_get.action',
			    prevUrl: './oaTradArea_prev.action',
			    nextUrl: './oaTradArea_next.action',
			    params: {
			        id: _this.sysId
			    },
			    callback: {
			        sfn: function(jsonData) {
			        	jsonData["tradId"]=Render_dataSource.gvlistRender(100002,jsonData["tradId"]);
			        	jsonData["plevelId"]=Render_dataSource.gvlistRender(100003,jsonData["plevelId"]);
			            /* 可在对页面进行赋值前，对数据进行转换处理 to do .. */
			            //jsonData["leaf"] = (jsonData["leaf"]=="false") ? "否" : "是";
			        }
			    }
			}];
			var attachLoad = function(params, cmd) {
			    /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			    //Cmw.print(params);
			    //Cmw.print(cmd);
			}
			var detailPanel_1 = new Ext.ux.panel.DetailPanel({
			    width: 600,
			    height:200,
			    detailCfgs: detailCfgs_1,
			    attachLoad: function(params, cmd) {
			        /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			        //Cmw.print(params);
			        //Cmw.print(cmd);
			    }
			});
			return detailPanel_1;
		}
	};
});
