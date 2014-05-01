<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" import="com.alibaba.fastjson.JSONArray;" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	JSONArray documentDatas = (JSONArray)request.getAttribute("documentDatas");
	String swfPath = null;
	if(null != documentDatas && documentDatas.size() > 0){
		JSONObject data = documentDatas.getJSONObject(0);
		swfPath = data.getString("swfPath");
		System.out.println("swfPath="+swfPath);
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FlexPaper 应用</title>
<script type="text/javascript" src="./jquery.js"></script>
<script type="text/javascript" src="./flexpaper_flash.js"></script>
</head>
<body>
<!-- <a href="./ConvertServlet" >转换文档</a> -->
  <div style="position:absolute;left:10px;top:50px;">
            <a id="viewerPlaceHolder" style="width:660px;height:480px;display:block"></a>         
            <script type="text/javascript"> 
                var fp = new FlexPaperViewer(    
                         './FlexPaperViewer', 
                         'viewerPlaceHolder',    //对应于a 标签的id
                         { config : {
                         SwfFile : encodeURIComponent('<%=swfPath%>'),  //这句是关键: SwfFile: 指示导入的.swf的路径
                         Scale : 0.6, 
                         ZoomTransition : 'easeOut',
                         ZoomTime : 0.5,
                         ZoomInterval : 0.2,
                         FitPageOnLoad : true,
                         FitWidthOnLoad : false,
                         PrintEnabled : true,
                         FullScreenAsMaxWindow : false,
                         ProgressiveLoading : false,
                         MinZoomSize : 0.2,
                         MaxZoomSize : 5,
                         SearchMatchAll : false,
                         InitViewMode : 'Portrait',
                         ViewModeToolsVisible : true,
                         ZoomToolsVisible : true,
                         NavToolsVisible : true,
                         CursorToolsVisible : true,
                         SearchToolsVisible : true,
                         localeChain: 'zh_CN'
                         }});
            </script>
        </div>
</body>
</html>