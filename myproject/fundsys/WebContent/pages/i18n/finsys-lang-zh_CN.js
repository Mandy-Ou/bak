/**
 *	小额贷款产品国际化文件 
 * 	2013-07-25	程明卫
 * 	V2.0
 * **/
 
/**
 * 小额贷款系统
 * 系统消息提示[提示框内容] 
 */
Finsys_Msg_SysTip = {
}
/**
 * 小额贷款系统按钮配置管理选项
 * Finsys_Btn_Cfgs.AUTOVOUCHER_BOXLABEL
 */
var Finsys_Btn_Cfgs = {
	/*--->财务系统连接测试<---*/
	AUTOVOUCHER_BOXLABEL : '自动生成财务凭证'
};


//**************** -- 本地下拉框或单选框或复选框数据源国际化 -- ******************//
var Finsys_Lcbo_dataSource = {
	/**
	 * 获取一个包含有 "--请选择--" 的下拉框数据源
	 * 例：Lcbo_dataSource.getAllDs(Lcbo_dataSource.rate_types_datas);
	 * @param {} dsArr	下拉框数据源
	 * @param {} allLbl	为空元素项显示名。例：“请选择”
	 */
	getAllDs : function(dsArr,allLbl){
		var dispLblArr = ["",this.allDispTxt];
		if(allLbl) dispLblArr[1] = allLbl;
		//dsArr.unshift(dispLblArr);
		var newDsArr = [dispLblArr].concat(dsArr);
		return newDsArr;
	}
}; 

//**************** -- Grid render 数据源国际化 -- ******************//
/**
 * 
 * @type 
 */
var Finsys_Render_dataSource = {
};
//**************** -- 业务界面UI国际化 -- ******************//
