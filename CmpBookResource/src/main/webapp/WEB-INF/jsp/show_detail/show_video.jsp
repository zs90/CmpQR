<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery-1.12.0.min.js"> </script>
<title>欢迎来到机械工业出版社二维码资源库</title>
<link rel="stylesheet" type="text/css" href="css/page.css"/>
</head>
<body>
<div id="main" >
<div id="front" >
<p style="font-size:50px; margin:0px;"> ${item.itemName} </p>
</div>
<div id="content">
<video id="vi" controls="controls" preload="none" width="100%" height="100%"><source src=${item.itemUrl} /></video>
</div>
<div id="comment">	
	<p style="font-size:40px; margin:0px;">${item.itemComment} </p>
</div>
<div id="foot">
</div>
</div>
</body>
</html>
