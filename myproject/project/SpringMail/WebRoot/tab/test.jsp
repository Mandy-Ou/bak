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
<html:link page="/paper/add.jsp">���</html:link>
<form id="list" action="" name="list" method="POST">
<table border="1">
<tr>
<th>ѡ��</th>
<th>����</th>
<th>�Ա�</th>
<th>��˾��ַ</th>
<th>��ͥסַ</th>
<th>���˵绰</th>
<th>��˾�绰</th>
<th>��������</th>
<th>��Ƭ���</th>
<th>����</th>
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
<html:link action="/cardContactAction.do?method=selectContact" paramId="contactid" paramName="card" paramProperty="contactid">�鿴|</html:link>
<html:link action="/cardContactAction.do?method=deleteContact" paramId="contactid" paramName="card" paramProperty="contactid">ɾ��|</html:link>
<a href="javascript:del(<bean:write name="card" property="contactid"/>)">ɾ��</a>
</td>
</tr>
</logic:iterate>
</table>
<table>
<tr>
<td><input type="button" onClick="clk('all');" value="ȫѡ"></td>
<td><input type="button" onClick="clk('un');" value="��ѡ"></td>
<td><input type="button" value="����ɾ��" onClick="clk('del');"></td>
<td>
<input type="button" value="�����ƶ���" onClick="clk('move');">
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
alert("��û��ѡ���κμ�¼");
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
alert("��û��ѡ���κμ�¼");
}
else{
var truthBeTold = window.confirm("ȷ��ɾ����Щ��¼��");
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
var truthBeTold = window.confirm("ȷ��ɾ���˰칫��Ʒ�����");
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