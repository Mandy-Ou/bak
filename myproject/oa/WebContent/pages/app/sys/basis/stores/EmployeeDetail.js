/**
 *查看员工详情
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
			this.sysId = parentCfg.sysId;
			this.optionType = parentCfg.optionType || OPTION_TYPE.DETAIL;
		},
		createAppWindow : function() {
			this.detailPnl = this.createDetailPnl();
			this.appWin = new Ext.ux.window.AbsDetailWindow({
					hiddenBtn : true,
						width : 825,
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
			var selId = parent.getSelId();
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
			var htmlArrs_1 = [
					'<tr><th col="name">姓名</th> <td col="name" >&nbsp;</td><th col="empType">员工类型</th> <td col="empType" >&nbsp;</td><th col="code">员工编号</th> <td col="code" >&nbsp;</td></tr>',
					'<tr><th col="degree">学历</th> <td col="degree" >&nbsp;</td><th col="hometown">籍贯</th> <td col="hometown" >&nbsp;</td><th col="sex">性别</th> <td col="sex" >&nbsp;</td></tr>',
					'<tr><th col="marital">婚姻</th> <td col="marital" >&nbsp;</td><th col="birthDay">出生日期</th> <td col="birthDay" >&nbsp;</td><th col="status">员工状态</th> <td col="status" >&nbsp;</td></tr>',
					'<tr><th col="phone">手机号码</th> <td col="phone" >&nbsp;</td><th col="idcard">身份证号码</th> <td col="idcard" >&nbsp;</td><th col="idadd">身份证地址</th> <td col="idadd" >&nbsp;</td></tr>',
					'<tr><th col="econtactor">紧急联系人</th> <td col="econtactor" >&nbsp;</td><th col="ctel">联络电话</th> <td col="ctel" >&nbsp;</td><th col="bank">开户银行</th> <td col="bank" >&nbsp;</td></tr>',
					'<tr><th col="account">银行卡号</th> <td col="account" >&nbsp;</td><th col="accountName">帐户名</th> <td col="accountName" >&nbsp;</td><th col="entryDate">入职日期</th> <td col="entryDate" >&nbsp;</td></tr>',
					'<tr><th col="tryDays">试用期(月)</th> <td col="tryDays" >&nbsp;</td><th col="leaveDate">离职日期</th> <td col="leaveDate" >&nbsp;</td><th col="position">行政职务</th> <td col="position" >&nbsp;</td></tr>',
					'<tr><th col="storeId">所属门店</th> <td col="storeId" >&nbsp;</td><th col="deptId">所属部门</th> <td col="indeptId" >&nbsp;</td><th col="divide">岗位分工</th> <td col="divide" >&nbsp;</td></tr>',
					'<tr><th col="postId">职位</th> <td col="postId" >&nbsp;</td><th col="postType">岗位类型</th> <td col="postType" >&nbsp;</td><th col="useType">用工类型</th> <td col="useType" >&nbsp;</td></tr>',
					'<tr><th col="socialNum">社保号</th> <td col="socialNum" >&nbsp;</td><th col="socialOrg">购买社保单位</th> <td col="socialOrg" >&nbsp;</td><th col="startDate">合同开始日期</th> <td col="startDate" >&nbsp;</td></tr>',
					'<tr><th col="endDate">合同结束日期</th> <td col="endDate" >&nbsp;</td><th col="wagePlanId">工资方案</th> <td col="wagePlanId" >&nbsp;</td><th col="wageDay">日工资标准</th> <td col="wageDay" >&nbsp;</td></tr>',
					'<tr><th col="invoceNum">OA单号</th> <td col="invoceNum" >&nbsp;</td><th col="ispart">是否兼职导购</th> <td col="ispart" >&nbsp;</td><th col="iscreate">创建登录帐号</th> <td col="iscreate" >&nbsp;</td></tr>'];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 90,
			 	title : '员工详细信息', 
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './hrEmployee_get.action',
//			    prevUrl: '#PREURLCFG#',
//			    nextUrl: '#NEXTURLCFG#',
			    params: {
			        id: _this.getParams()
			    },
			    callback: {
			        sfn: function(jsonData) {
			            /* 可在对页面进行赋值前，对数据进行转换处理 to do .. */
			            //jsonData["leaf"] = (jsonData["leaf"]=="false") ? "否" : "是";
			        	jsonData["empType"]=Render_dataSource.emp_empType(""+jsonData["empType"]);
			        	jsonData["degree"] = Render_dataSource.gvlistRender('200001',jsonData["degree"]);
			        	jsonData["hometown"] = Render_dataSource.gvlistRender('200002',jsonData["hometown"]);
			        	jsonData["sex"]=Render_dataSource.sex_hrRender(""+jsonData["sex"]);
			        	jsonData["marital"] = Render_dataSource.gvlistRender('200003',jsonData["marital"]);
			        	jsonData["status"]=Render_dataSource.emp_statusRender(""+jsonData["status"]);
			        	jsonData["position"]=Render_dataSource.format_treeOrGrid(jsonData["position"]);
			        	jsonData["storeId"]=Render_dataSource.format_treeOrGrid(jsonData["storeId"]);
			        	jsonData["indeptId"]=Render_dataSource.format_treeOrGrid(jsonData["indeptId"]);
			        	jsonData["divide"] = Render_dataSource.gvlistRender('200004',jsonData["divide"]);
			        	jsonData["postId"]=Render_dataSource.format_treeOrGrid(jsonData["postId"]);
			        	jsonData["postType"] = Render_dataSource.gvlistRender('200005',jsonData["postType"]);
			        	jsonData["useType"] = Render_dataSource.gvlistRender('200006',jsonData["useType"]);
			        	jsonData["wagePlanId"]=Render_dataSource.format_treeOrGrid(jsonData["wagePlanId"]);
			        	jsonData["wageDay"] = Render_dataSource.moneyRender(jsonData["wageDay"]);
			        	jsonData["ispart"] = Render_dataSource.emp_ispartRender(""+jsonData["ispart"]);
			        	jsonData["iscreate"] = Render_dataSource.emp_iscreateRender(""+jsonData["iscreate"]);
			        }
			    }
			}];
			var attachLoad = function(params, cmd) {
			    /* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
			    //Cmw.print(params);
			    //Cmw.print(cmd);
			}
			var detailPanel_1 = new Ext.ux.panel.DetailPanel({
			    width: 825,
			    height:380,
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
