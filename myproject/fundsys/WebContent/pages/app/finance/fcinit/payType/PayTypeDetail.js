/**
 * 还款方式公式详情
 * @author smartplatform_auto
 * @date 2013-01-23 07:21:09
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		detailPnl : null,	//详情面板
		appWin : null,
		WIDTH : 600,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.DETAIL;
		},
		createAppWindow : function(){
			this.detailPnl = this.createDetailPnl();
			this.appWin = new Ext.ux.window.AbsDetailWindow({width:this.WIDTH,getParams:this.getParams,appDetailPanel : this.detailPnl,
			optionType : this.optionType, refresh : this.refresh
			});
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
			this.appWin.optionType = this.optionType;
			this.appWin.show();
		},
		/**
		 * 销毁组件
		 */
		destroy : function(){
			this.appWin.close();	//关闭并销毁窗口
			this.appWin = null;		//释放当前窗口对象引用
		},
		/**
		 * 获取选中的记录参数
		 */
		getParams : function(){
			var parent = exports.WinEdit.parent;
			var selId = parent.getSelId();
			var selId = null;
			if(parent){
				selId = parent.getSelId();
			}else{
				ExtUtil.alert({msg:'请传入 parent 对象！'});
			}
			var params = {id:selId};
			return params;
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var _this = exports.WinEdit;
			if(_this.parentCfg.self.refresh)_this.parentCfg.self.refresh(_this.optionType,data);	
		},
		/**
		 * 创建详情面板
		 */
		createDetailPnl : function(){
			var parent = exports.WinEdit.parent;
			var selId = null;
			if(parent){
				selId = parent.getSelId();
			}else{
				ExtUtil.alert({msg:'请传入 parent 对象！'});
			}
			var htmlArrs = null;
			
			var htmlArrs_1 = [
					'<tr><th col="name">还款方式名称</th> <td col="name"  colspan=3 >&nbsp;</td></tr>',
					'<tr><th col="code">编号</th> <td col="code" >&nbsp;</td><th col="name">算法接口</th> <td col="inter">&nbsp;</td></tr>',
					'<tr><th col="rateDispFormula">当期应还利息</th> <td col="rateDispFormula"  colspan=3 >&nbsp;</td></tr>',
					'<tr><th col="rateFormula">利息计算公式</th> <td col="rateFormula"  colspan=3 >&nbsp;</td></tr>',
					'<tr><th col="rateParams">利息参数</th> <td col="rateParams"  colspan=3 >&nbsp;</td></tr>',
					'<tr><th col="amoutDispFormula">当期应还本金</th> <td col="amoutDispFormula"  colspan=3 >&nbsp;</td></tr>',
					'<tr><th col="amountFormula">本金计算公式</th> <td col="amountFormula"  colspan=3 >&nbsp;</td></tr>',
					'<tr><th col="amountParams">本金参数</th> <td col="amountParams"  colspan=3 >&nbsp;</td></tr>',
					'<tr><th col="raDispFormula">当期还本付息</th> <td col="raDispFormula"  colspan=3 >&nbsp;</td></tr>',
					'<tr><th col="raFormula">还本付息公式</th> <td col="raFormula"  colspan=3 >&nbsp;</td></tr>',
					'<tr><th col="raParams">还本付息参数</th> <td col="raParams"  colspan=3 >&nbsp;</td></tr>',
					'<tr><th col="remark">备注</th> <td col="remark"  colspan=3 >&nbsp;</td></tr>'];
			var detailCfgs_1 = [{
			    cmns: 'TWO',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 90,
			    /* title : '还款方式公式', */
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './fcPayType_get.action',
			    prevUrl: './fcPayType_prev.action',
			    nextUrl: './fcPayType_next.action',
			    params: {
			        id: selId
			    },
			    callback: {
			        sfn: function(jsonData) {
			            /* 可在对页面进行赋值前，对数据进行转换处理 to do .. */
			            //jsonData["leaf"] = (jsonData["leaf"]=="false") ? "否" : "是";
			        }
			    }
			}];
			var detailPanel_1 = new Ext.ux.panel.DetailPanel({
			    width: this.WIDTH,
			    detailCfgs: detailCfgs_1
			});
			
			return detailPanel_1;
		}
	};
});
