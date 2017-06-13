<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery-1.12.0.min.js"> </script>
<title>${project.projectName}</title>
<style type="text/css">
html{
	width:100%;
	margin:0;
}
body{
	width:100%;
	margin:0; 
}
.panel{
	width:100%;
	height:170px;
	margin:10px auto 0; 
	background-color:#4b79ce;
}
.img{
	width:15%;
	background-color:white;
	float:left;
}
.title,.instruction{
	width:30%;
	height:50%;
	float:left;
	font-size:150%;
	color:white;
}
a{
	color:white;
	text-decoration:none;
}
</style>
</head>
<body>

<c:forEach var="item" items="${project.items}">
	<div class="panel">
	<div class="img"></div>
	<div class="title">
		${item.itemName}
	</div>
	<div class="instruction" style="width:20%">
		<a href="http://qr.cmpedu.com/CmpBookResource/show_resource.do?id=${item.itemId}"><b>预览</b></a>&nbsp&nbsp&nbsp
		<a href="http://qr.cmpedu.com/CmpBookResource/download_resource.do?id=${item.itemId}"><b>下载</b></a>
	</div>
	</div>
</c:forEach>
<script type="text/javascript">
$(document).ready(function() {
	$('.img').css("height", $(".img").width());
	$('.img').css("margin", ($(".panel").height() - $(".img").height()) / 2 + 'px ' + $(".panel").width() * 0.05 + 'px');
	$('.title').css("margin", $(".panel").height() * 0.25 + 'px ' + $(".panel").width() * 0.05 + 'px');
	$('.instruction').css("margin", $(".panel").height() * 0.25 + 'px ' + $(".panel").width() * 0.05 + 'px');
});
</script>
</body>
</html>