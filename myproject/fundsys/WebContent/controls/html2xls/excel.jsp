<%@page import="com.cmw.core.kit.file.FileUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
response.setContentType("application/vnd.ms-excel;charset=utf-8"); 
String fileName = request.getParameter("fileName");
String filePath = request.getParameter("filePath");
//fileName = new String(fileName.getBytes("GB2312"),"ISO8859-1"); 
response.setHeader("Content-disposition","attachment; filename=\"" + fileName + ".xls\"");
filePath = FileUtil.getFilePath(request, filePath);
String htmlStr = FileUtil.ReadFileToStr(filePath);
FileUtil.delFile(filePath);/*删除临时HTML文件*/
//替换容易造成Excel 与HTML 显示不一致的问题
htmlStr = htmlStr.replaceAll("class=\"first_row_td\"", "").replace("class=\"last_row_tag\"", "style=\"display:none;\"");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>HTML to Excel </title>
<style type="text/css">
/**-------------------- CmwPivotGrid CSS CODE START -----------------------**/
/*主容器DIV 样式*/
.cmw_report_grid{border: solid 1px #000; overflow:visible;background-color:#F0F0F0;}
/*表头DIV 样式*/
.cmw_report_grid .report_header{overflow-x:hidden;}

 /*表头TABLE 样式*/
.report_header .report_header_table  {border-collapse:collapse; font-size:13px;}

.report_header_table th {border:1px solid black;text-align:center;padding:5px;font-weight: bold;}

/*表体DIV 样式*/
.cmw_report_grid .report_body{overflow:auto;background-color:#FFF;  height:300PX;}
 /*表体TABLE 样式*/
.report_body .report_body_table {border-collapse:collapse; border:none;  font-size:13px;}
.report_body_table td  {border:1px solid black;text-align:center;word-break:break-all;word-wrap: break-word;height:25px;}

.report_body_table .first_row_td { border-top: none;}
.report_body_table .last_row_td{ font-size:0px; line-height:0px;height:0px; padding:0px; margin:0px; border-width:0px; border:none;}
.report_body_table .cmw_td_group{ background-color:#CC99FF;text-align:left;padding-left:10px; font-weight:bold;}
/**-------------------- CmwPivotGrid CSS CODE END -----------------------**/
.ledgerDiv{
	 overFlow: auto ; 
	 width:100%; 
	 height : 550px;
}
.ledgerDiv table {  
    padding: 0px;  
    margin: 0px;  
    font-family:Arial, Tahoma, Verdana, Sans-Serif,宋体;  
    border-left:1px solid #ADD8E6;  
    border-collapse:collapse;  
}
.ledger {width:1380px;}
.ledger tr{height : 20px;}

.ledger td {
    border-top:1px solid #000;border-right:1px solid #000;border-bottom:1px solid #000;padding:0px ;font: normal 13px '宋体', '新宋体', '黑体', "Trebuchet MS", tahoma, arial, verdana, sans-serif;
    font-size:12px;
    color: #303030;
    word-break:break-all;
    word-wrap:break-word;
    white-space:normal;
}
</style>
</head>
<body>
<%=htmlStr%>
</body>
</html>