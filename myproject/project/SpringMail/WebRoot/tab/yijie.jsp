<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ޱ����ĵ�</title>
<style type="text/css">
<!--
body {
	margin-left: 5px;
	margin-top: 5px;
	margin-right: 5px;
	margin-bottom: 5px;
}
.STYLE1 {font-size: 12px}
.STYLE2 {
	font-size: 12px;
	font-weight: bold;
}
.STYLE4 {font-size: 12px; color: #A4D3EE; }
.STYLE5 {color: #A4D3EE}

-->
</style>
<link href="<%=request.getContextPath() %>/css/main.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE6 {
	color: #666666;
	font-size: 12px;
}
-->
</style>

<script>
var  highlightcolor='#FFEFDB';
//�˴�clickcolorֻ����winϵͳ��ɫ������ܳɹ�,�����#xxxxxx�Ĵ���Ͳ���,��û�����Ϊʲô:(
var  clickcolor='#FFEFDB';
function  changeto(){
source=event.srcElement;
if  (source.tagName=="TR"||source.tagName=="TABLE")
return;
while(source.tagName!="TD")
source=source.parentElement;
source=source.parentElement;
cs  =  source.children;
//alert(cs.length);
if  (cs[1].style.backgroundColor!=highlightcolor&&source.id!="nc"&&cs[1].style.backgroundColor!=clickcolor)
for(i=0;i<cs.length;i++){
	cs[i].style.backgroundColor=highlightcolor;
}
}

function  changeback(){
if  (event.fromElement.contains(event.toElement)||source.contains(event.toElement)||source.id=="nc")
return
if  (event.toElement!=source&&cs[1].style.backgroundColor!=clickcolor)
//source.style.backgroundColor=originalcolor
for(i=0;i<cs.length;i++){
	cs[i].style.backgroundColor="";
}
}

function  clickto(){
source=event.srcElement;
if  (source.tagName=="TR"||source.tagName=="TABLE")
return;
while(source.tagName!="TD")
source=source.parentElement;
source=source.parentElement;
cs  =  source.children;
//alert(cs.length);
if  (cs[1].style.backgroundColor!=clickcolor&&source.id!="nc")
for(i=0;i<cs.length;i++){
	cs[i].style.backgroundColor=clickcolor;
}
else
for(i=0;i<cs.length;i++){
	cs[i].style.backgroundColor="";
}
}
</script>

		<script type="text/javascript">
        var xmlHttp;

        function createXMLHttpRequest() {

            if (window.ActiveXObject) {

                xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");

            }

            else if (window.XMLHttpRequest) {

                xmlHttp = new XMLHttpRequest();

            }

        }
 
        function checkNumber() {

           		createXMLHttpRequest();

           		var number = document.getElementById("select2").value;
       
				var url = "infoAction.do?method=selectWei&id=" + number;
	
	            xmlHttp.open("GET", url, true);
	
	            xmlHttp.onreadystatechange = checkNumber_callBack;
	
	            xmlHttp.send(null);

        }
		
		function checkNumber_callBack()
		{
		
			if (xmlHttp.readyState == 4) {

                if (xmlHttp.status == 200) {

                    var mes =xmlHttp.responseXML.getElementsByTagName("message")[0].firstChild.data;
					
					if(mes=="use"){
						
						alert("�ñ�ſ���");
						
					}else{
						
						alert("�ñ�Ų�����");
						
						bm_id.focus();
						
						return false;
					
					}
                }

            }
		
		}
		
		function clk(act,delid,checkMoreId)
	{
		if(act=="all")
		{
			for(i=0;i<delid.length;i++)
			{
				document.getElementsByName("del_id")[i].checked=true; 
		  		 //del_id[i].checked = true;
			}
		}
		if(act=="un")
		{
			for(j=0;j<delid.length;j++)
		   {
		   		document.getElementsByName("del_id")[j].checked=false; 
		    //del_id[j].checked = false;
		   }
		}
	}


	function CheckAll(delid,checkMoreId)
	{
		
		for(i=0;i<delid.length;i++)
		{
			document.getElementsByName("del_id")[i].checked=true; 
		
		   //del_id[i].checked = true;
		}
		if(checkMoreId.checked ==false)
		{
		   for(j=0;j<delid.length;j++)
		   {
		   	document.getElementsByName("del_id")[j].checked=false;
		   }
		}
	}

	
	 
	//�ж��û��Ƿ�ѡ����Ҫɾ���ļ�¼������ǣ�����ʾ���Ƿ�ɾ������������ʾ����ѡ��Ҫɾ���ļ�¼��
	function checkdel(delid,formname)
	{
	
		var flag = false;
		for(i=0;i<delid.length;i++)
		{
	  	 	if(delid[i].checked == true)
	  	    {
	   		  flag = true;
	  		  break;
	 	  	}
		}
		if(!flag)
		{
	  		alert("��ѡ��Ҫɾ���ļ�¼��");
	   		return false;
		}
		else
		{
	   		 if(confirm("ȷ��Ҫ�ƶ�����������"))
	   		 {
	    		 form.submit();
	  		 }
		}
	}
		</script>
		
</head>

<body>
<form action="infoAction.do?method=moveAll" method="post" name="form1" id="form1">
<table width="100%" border="1" cellpadding="5" cellspacing="0" bordercolor="#E3E3E3" bordercolordark="#FFFFFF" class="table" >
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="6%" align="center"><div align="left">
          <input name="chkAll" type="checkbox" id="selectAll" onclick="CheckAll(del_id,selectAll)" value="checkbox"/>
        </div></td>
        <td width="37%"><span class="STYLE1"><strong>�ռ���</strong></span></td>
        <td width="28%"><span class="STYLE1"><strong>����</strong></span></td>
        <td width="29%"><span class="STYLE1"><strong>����</strong></span></td>
        </tr>
    </table></td>
  </tr>
    <c:forEach items="${requestScope.list}" var="po">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="font" onmouseover="changeto()"  onmouseout="changeback()" >
      <tr>
        <td width="4%" align="center"><div align="left">
          <input name="del_id" id="del_id" type="checkbox" value="${po.id }"/>
        </div></td>
        <td width="2%"><img src="<%=request.getContextPath() %>/img/run.gif" width="16" height="14" /></td>
        <td width="37%"><a href="infoAction.do?method=selectMoreEmail&id=${po.id}">${po.recipients}</a></td>
        <td width="28%"><a href="infoAction.do?method=selectMoreEmail&id=${po.id}">${po.title}</a></td>
        <td width="29%"><a href="infoAction.do?method=selectMoreEmail&id=${po.id}">${po.thetime}</a></td>
        </tr>
    </table></td>
  </tr>
    </c:forEach>
  <tr>
    <td><table width="100%" border="0">
      <tr>
       <td width="70%"></td>
        <td width="30%"><span class="STYLE6">[ ��ҳ | ��һҳ | ��һҳ | βҳ ]</span></td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td bgcolor="#FFFAF0"><input name="Submit" type="submit" class="delete" value="ɾ��" style=" font-size:12px" onclick="javascript:if(checkdel(del_id,'check')){return true;}else{return false;};" />
 
     
        </td>
  </tr>
</table>
</form>
</body>
</html>