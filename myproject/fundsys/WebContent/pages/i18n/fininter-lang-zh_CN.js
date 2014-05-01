
/**
 * 系统消息提示[提示框内容] 
 * Fs_Msg_SysTip.title_appconfirm
 * Fs_Msg_SysTip.msg_synchronousData
 * Fs_Msg_SysTip.msg_synchronousSuccess
 * Fs_Msg_SysTip.msg_cancelMapping
 */
Fs_Msg_SysTip = {
	title_appconfirm : '系统提示',
	msg_synchronousData : '确定要同步数据吗?',
	msg_chosesystem : '请从左边树上选择要测试的财务系统',
	msg_synchronousDataing : '正在同步数据...',
	msg_synchronousSuccess : '数据同步成功!',
	msg_synchronousFailure : '数据同步失败!',
	msg_cancelMapping : '确定取消账号映射吗?',
	msg_isnotlink : '删除的数据存在关联的设置,不能进行删除！',
	msg_codenotonly : '编号此编号已经存在,保存失败！',
	msg_sysidneedonly : '一个业务系统只能对应一个财务系统！',
	msg_entryIdneedonly : '一条分录只能配置一个核算项!'
}
/**
 * 财务接口按钮配置管理选项
 * Fs_Btn_Cfgs.SYNCHRONOUS_BTN_TXT
 */
var Fs_Btn_Cfgs = {
	
	/*--->财务系统连接测试<---*/
	CONNECTION_TEST_BTN_TXT : '连接测试',
	CONNECTION_TEST_BTN_CLS : 'page_add',
	CONNECTION_TEST_TIP_BTN_TXT : '连接测试',
	
	/*--->财务系统映射<---*/
	SYSTEM_MAPPING_BTN_TXT : '系统映射',
	SYSTEM_MAPPING_BTN_CLS : 'page_add',
	SYSTEM_MAPPING_TIP_BTN_TXT : '系统映射',
	/*--->取消财务系统映射<---*/
	SYSTEM_UNMAPPING_BTN_TXT : '取消映射',
	SYSTEM_UNMAPPING_BTN_CLS : 'page_delete',
	SYSTEM_UNMAPPING_TIP_BTN_TXT : '取消映射',
	
	/*--->同步数据<---*/
	SYNCHRONOUS_BTN_TXT : '同步数据',
	SYNCHRONOUS_BTN_CLS : 'page_edit',
	SYNCHRONOUS_TIP_BTN_TXT : '同步数据',
	
	/*--->业务对象关联<---*/
	BUSSOBJECT_BTN_TXT : '业务对象关联',
	BUSSOBJECT_BTN_CLS : 'page_add',
	BUSSOBJECT_TIP_BTN_TXT : '业务对象关联',
	
	
	/*--->用户账号映射<---*/
	USER_MAPPING_BTN_TXT : '用户账号映射',
	USER_MAPPING_BTN_CLS : 'page_enabled',
	USER_MAPPING_TIP_BTN_TXT : '用户账号映射',
	/*--->取消用户账号映射<---*/
	USER_UNMAPPING_BTN_TXT : '取消用户账号映射',
	USER_UNMAPPING_BTN_CLS : 'page_disabled',
	USER_UNMAPPING_TIP_BTN_TXT : '取消用户账号映射',
	
	/*--->财务自定义字段配置<---*/
	BUSSOBJECT_ADD_BTN_TXT : '添加业务对象',
	BUSSOBJECT_ADD_BTN_CLS : 'page_add',
	BUSSOBJECT_ADD_TIP_BTN_TXT : '添加业务对象',
	
	BUSSOBJECT_EDIT_BTN_TXT : '编辑业务对象',
	BUSSOBJECT_EDIT_BTN_CLS : 'page_edit',
	BUSSOBJECT_EDIT_TIP_BTN_TXT : '编辑业务对象',
	
	BUSSOBJECT_ENABLED_BTN_TXT : '起用业务对象',
	BUSSOBJECT_ENABLED_BTN_CLS : 'page_enabled',
	BUSSOBJECT_ENABLED_TIP_BTN_TXT : '起用业务对象',
	
	BUSSOBJECT_DISABLED_BTN_TXT : '禁用业务对象',
	BUSSOBJECT_DISABLED_BTN_CLS : 'page_disabled',
	BUSSOBJECT_DISABLED_TIP_BTN_TXT : '禁用业务对象',
	
	BUSSOBJECT_DEL_BTN_TXT : '删除业务对象',
	BUSSOBJECT_DEL_BTN_CLS : 'page_delete',
	BUSSOBJECT_DEL_TIP_BTN_TXT : '删除业务对象',
	
	CUSTFIELD_ADD_BTN_TXT : '添加字段',
	CUSTFIELD_ADD_BTN_CLS : 'page_add',
	CUSTFIELD_ADD_TIP_BTN_TXT : '添加字段',
	
	CUSTFIELD_EDIT_BTN_TXT : '编辑字段',
	CUSTFIELD_EDIT_BTN_CLS : 'page_edit',
	CUSTFIELD_EDIT_TIP_BTN_TXT : '编辑字段',
	
	CUSTFIELD_ENABLED_BTN_TXT : '起用字段',
	CUSTFIELD_ENABLED_BTN_CLS : 'page_enabled',
	CUSTFIELD_ENABLED_TIP_BTN_TXT : '起用字段',
	
	CUSTFIELD_DISABLED_BTN_TXT : '禁用字段',
	CUSTFIELD_DISABLED_BTN_CLS : 'page_disabled',
	CUSTFIELD_DISABLED_TIP_BTN_TXT : '禁用字段',
	
	CUSTFIELD_DEL_BTN_TXT : '删除字段',
	CUSTFIELD_DEL_BTN_CLS : 'page_delete',
	CUSTFIELD_DEL_TIP_BTN_TXT : '删除字段',
	
	/*--->凭证ENTRY配置<---*/
	VTEMP_ADD_BTN_TXT : '添加模板',
	VTEMP_ADD_BTN_CLS : 'page_add',
	VTEMP_ADD_TIP_BTN_TXT : '添加模板',
	
	VTEMP_EDIT_BTN_TXT : '编辑模板',
	VTEMP_EDIT_BTN_CLS : 'page_edit',
	VTEMP_EDIT_TIP_BTN_TXT : '编辑模板',
	
	VTEMP_ENABLED_BTN_TXT : '起用模板',
	VTEMP_ENABLED_BTN_CLS : 'page_enabled',
	VTEMP_ENABLED_TIP_BTN_TXT : '起用模板',
	
	VTEMP_DISABLED_BTN_TXT : '禁用模板',
	VTEMP_DISABLED_BTN_CLS : 'page_disabled',
	VTEMP_DISABLED_TIP_BTN_TXT : '禁用模板',
	
	VTEMP_DEL_BTN_TXT : '删除模板',
	VTEMP_DEL_BTN_CLS : 'page_delete',
	VTEMP_DEL_TIP_BTN_TXT : '删除模板',
	
	ENTRY_ADD_BTN_TXT : '添加分录',
	ENTRY_ADD_BTN_CLS : 'page_add',
	ENTRY_ADD_TIP_BTN_TXT : '添加分录',
	
	ENTRY_EDIT_BTN_TXT : '编辑分录',
	ENTRY_EDIT_BTN_CLS : 'page_edit',
	ENTRY_EDIT_TIP_BTN_TXT : '编辑分录',
	
	ENTRY_DEL_BTN_TXT : '删除分录',
	ENTRY_DEL_BTN_CLS : 'page_delete',
	ENTRY_DEL_TIP_BTN_TXT : '删除分录',
	
	
	ITEM_ADD_BTN_TXT : '添加核算项',
	ITEM_ADD_BTN_CLS : 'page_add',
	ITEM_ADD_TIP_BTN_TXT : '添加核算项',
	
	ITEM_EDIT_BTN_TXT : '编辑核算项',
	ITEM_EDIT_BTN_CLS : 'page_edit',
	ITEM_EDIT_TIP_BTN_TXT : '编辑核算项',
	
	ITEM_DEL_BTN_TXT : '删除核算项',
	ITEM_DEL_BTN_CLS : 'page_delete',
	ITEM_DEL_TIP_BTN_TXT : '删除核算项'
};


//**************** -- 本地下拉框或单选框或复选框数据源国际化 -- ******************//
var Fs_Lcbo_dataSource = {
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
var Fs_Render_dataSource = {
   /**
     * 用户映射状态(用户映射)
     * Fs_Render_dataSource.usermapping_statusRender(val)
    */
    usermapping_statusRender : function(val) {
        switch (val) {
	        case '0':
	            val = '未映射';
	            break;
	        case '1':
	            val = '已映射';
	            break;
	        default:
				val = "";
        }
        return val;
    },
    /**
     * 数据类型(财务自定义VTEMP配置)
     * Fs_Render_dataSource.finCustField_dataTypeRender
    */
    finCustField_dataTypeRender : function(val) {
        switch (val) {
	        case '1':
	            val = '字符串';
	            break;
            case '2':
	             val = '日期';
	             break;
            case '3':
	            val = '金额';
	            break;
            case '4':
	            val = '整数';
	            break;
	        default:
				val = "";
        }
        return val;
    },
    /**
     * 是否明细科目(科目配置)
     * Fs_Render_dataSource.subject_detailRender
    */
    subject_detailRender:function(val){
		switch (val) {
			case "0":
				val = "否";
				break;
			case "1":
				val = "是";
				break;
			}
		return val;
	},
	 /**
     * 借贷方向(科目配置)
     * Fs_Render_dataSource.subject_dcRender
    */
	subject_dcRender:function(val){
		switch (val) {
			case "1":
				val = "借方";
				break;
			case "0":
				val = "贷方";
				break;
			}
		return val;
	},
	/**
     * 科目类型(科目配置)
     * Fs_Render_dataSource.subject_atypeRender
    */
	subject_atypeRender:function(val){
		switch (val) {
			case "0":
				val = "未设置";
				break;
			case "1":
				val = "现金类";
				break;
			case "2":
				val = "现金类";
				break;
			}
		return val;
	},
	/**
     * 分录方向(凭证模板配置)
     * Fs_Render_dataSource.vt_entryRender
    */
	vt_entryRender:function(val){
		switch (val) {
			case "1":
				val = "借方多分录";
				break;
			case "2":
				val = "贷方多分录";
				break;
			case "3":
				val = "双方多分录";
				break;
			}
		return val;
	},
	/**
     * 批量业务策略(凭证模板配置)
     * Fs_Render_dataSource.vt_tacticsRender
    */
	vt_tacticsRender:function(val){
		switch (val) {
			case "1":
				val = "一笔业务一张凭证";
				break;
			case "2":
				val = "多笔业务一张凭证";
				break;
			}
		return val;
	}
};
//**************** -- 业务界面UI国际化 -- ******************//
