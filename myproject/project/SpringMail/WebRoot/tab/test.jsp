<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
+ request.getServerName() + ":" + request.getServerPort()
+ path + "/";
%>

<html>
<html:html>
<html:link page="/paper/add.jsp">添加</html:link>
<form id="list" action="" name="list" method="POST">
<table border="1">
<tr>
<th>选择</th>
<th>姓名</th>
<th>性别</th>
<th>公司地址</th>
<th>家庭住址</th>
<th>个人电话</th>
<th>公司电话</th>
<th>个人邮箱</th>
<th>名片类别</th>
<th>操作</th>
</tr>
<logic:iterate id="card" name="cards">
<tr>
<td><input type="checkbox" name="isList" value='<bean:write name="card" property="contactid"/>'/> </td>
<td>&nbsp;<bean:write name="card" property="name"/></td>
<td>&nbsp;<bean:write name="card" property="sex"/></td>
<td>&nbsp;<bean:write name="card" property="officeAddress"/></td>
<td>&nbsp;<bean:write name="card" property="homeAddress"/></td>
<td>&nbsp;<bean:write name="card" property="mobile"/></td>
<td>&nbsp;<bean:write name="card" property="officePhone"/></td>
<td>&nbsp;<bean:write name="card" property="email1"/></td>
<td>&nbsp;<bean:write name="card" property="typeid"/></td>
<td>
<html:link action="/cardContactAction.do?method=selectContact" paramId="contactid" paramName="card" paramProperty="contactid">查看|</html:link>
<html:link action="/cardContactAction.do?method=deleteContact" paramId="contactid" paramName="card" paramProperty="contactid">删除|</html:link>
<a href="javascript:del(<bean:write name="card" property="contactid"/>)">删除</a>
</td>
</tr>
</logic:iterate>
</table>
<table>
<tr>
<td><input type="button" onClick="clk('all');" value="全选"></td>
<td><input type="button" onClick="clk('un');" value="反选"></td>
<td><input type="button" value="批量删除" onClick="clk('del');"></td>
<td>
<input type="button" value="批量移动到" onClick="clk('move');">
<html:select name="card" property="typeid">
<html:options collection="cardTypeList" labelProperty="typeName" property="typeId"/>
</html:select>
</td>
</tr>
</table>
</form>
</html:html>
</html>
<script language="javascript" type="">
function clk(act)
{
var v = "";
var o;
var a = document.forms["list"];
for(var i=0; i<a.length; i++){
o = a[i];
if(act=="all"){
o.checked = true;
}
else if(act=="un"){
if(o.checked){
o.checked = false;
}
else{
o.checked = true;
}
}
else{
var n = a[i].value;
if(n>0 && a[i].checked){
v = v + a[i].value + "_";
}
}
}
if(act=="move"){
v = v.substring(0,v.length-1);
if(v.length==0){
alert("您没有选择任何纪录");
}
else{
var bb = document.forms["list"].action="<%=basePath%>/cardContactAction.do?method=moveAll";
alert(bb);
document.forms["list"].action="<%=basePath%>/cardContactAction.do?method=moveAll";
document.forms["list"].submit();
}
}
if(act=="del"){
v = v.substring(0,v.length-1);
if(v.length==0){
alert("您没有选择任何纪录");
}
else{
var truthBeTold = window.confirm("确定删除这些纪录吗");
if (truthBeTold==false)
{
event.returnValue=false;
return false;
}
else{
var aa = document.forms["list"].action="<%=basePath%>/cardContactAction.do?method=deleteAll";
alert(aa);
document.forms["list"].action="<%=basePath%>/cardContactAction.do?method=deleteAll";
document.forms["list"].submit();
}
}
}
}
function del(oid)
{
var truthBeTold = window.confirm("确定删除此办公用品类别吗？");
if (truthBeTold==false)
{
event.returnValue=false;
return false;
}
var aa = document.forms["list"].action="<%=basePath%>/cardContactAction.do?method=deleteContact&cardid="+oid;
document.forms["list"].action="<%=basePath%>/cardContactAction.do?method=deleteContact&cardid="+oid;
alert(aa);
document.forms["list"].submit();
}
</script>