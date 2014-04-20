/**
 * 菜单新增或修改页面
 * @author 程明卫
 * @date 2012-07-12
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.DETAIL,/* 默认为详情  */
		appFrm : null,
		appWin : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.DETAIL;
		},
		getAppWindow : function(){
			var baseId = Ext.id(null,'Gvlist');
			this.appFrm = this.createForm(baseId);
			this.appWin = new Ext.ux.window.DetailWindow({width:540,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, baseId : this.baseId, refresh : this.refresh
			});
			return this.appWin;
		},
		getUrls : function(){
			var urls = {};
			var selId = parent.getSelId();
			var cfg = null;
			/*--- 详情URL --*/
			cfg = {params : {id:selId}};
			urls['detailUrlCfg'] = {url : './sysGvlist_get.action',cfg : cfg};

			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls['preUrlCfg'] = {url : './sysGvlist_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls['nextUrlCfg'] = {url : './sysGvlist_next.action',cfg :cfg};
			return urls;
		},
		/**
		 * 创建Form表单
		 */
		createForm : function(baseId){
			/*主键ID*/
			var hid_Id = FormUtil.getHidField({fieldLabel : '主键ID',name:'id'});
			  var dataTemplate = [
		     "<table class='detail_tab' cellspacing='0'>",
			 "<tr>",
			 "<td id='"+baseId+"_lbl_code' class='alt'>编号:</td><td  id='"+baseId+"_code' class='display'>C1001</td>",
			 "<td id='"+baseId+"_lbl_name' class='alt'>名称:</td><td  id='"+baseId+"_name'  class='display'>制造业</td>",
			 "<td id='"+baseId+"_lbl_ename' class='alt'>英文名称:</td><td  id='"+baseId+"_ename'  style='width:125px;word-break:break-all'><div>ManufacturingManufacturing</div></td>",
			 "</tr>",
			 "<tr>",
		     "<td id='"+baseId+"_lbl_remark' class='alt'>备注:</td><td colspan='5' class='autobr'>www.865171.cnwww.865171.cnwww.865171.cnwww.865171.cnwww.865171.cnwww.865171.cnwww.865171.cnwww.865171.cnwww.865171.cnwww.865171.cnwww.865171.cnwww.865171.cnwww.865171.cnwww.865171.cnwww.865171.cnwww.865171.cnwww.865171.cnwww.865171.cnwww.865171.cnwww.865171.cnwww.865171.cnwww.865171.cn</td>",
		     "</tr>",
			"</table>"
		    ];
		    var html = dataTemplate.join("");
			var fldSet = new Ext.form.FieldSet({title:'基本信息',html:html,collapsed : false,collapsible : true});
		    var appform = FormUtil.createFrm({title : '菜单信息详情',frame:false,items:[hid_Id,fldSet]});
		    
			return appform;
		}
	};
});