<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 
    <title>@ViewBag.Title</title>
    <link href="@Url.Content("~/extjs/resources/css/ext-all.css")" rel="stylesheet" type="text/css" />
    <script src="@Url.Content("~/extjs/ext-base.js")" type="text/javascript"></script>
    <script src="@Url.Content("~/extjs/ext-all.js")" type="text/javascript"></script>
    <script src="@Url.Content("~/extjs/ext-lang-zh_CN.js")" type="text/javascript"></script>
    <script type=”text/javascript" src="@Url.Content("~/jslib/extjs01.js")"/>
</head>
<body>
    @RenderBody()
</body>
</html>