<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎来到下载界面</title>
<style type="text/css">
html{
	height:100%;
	margin:0;
}
body{
	height:100%;
	margin:0; 
	background-image:url(img/bg.jpg);
	background-size:cover;
}
#upper{
	height:10%;
	width:45%;
	background-color:#19bc9b;
	margin:40% auto 10%;
	text-align:center;
	border-top:1px solid #19bc9b;	
}
#middle{
	width:50%;
	height:10%;
	margin: 0 auto;
}
#down{
	width:150px;
	height:150px;
	display:block;
	background-image:url(img/download.jpg);
	background-size:cover;
	margin:5% auto 5%;
}
#bottom{
	width:70%;
	height:15%;
	margin: 5% auto;
	background-color:#19bc9b;
	text-align:center;
	border-top:1px solid #19bc9b;	
}
   p{
   	   margin:7% auto;
   	   font-size:300%;
   	   color:white;
   	   height:100%;
   	   width:100%;
   }
</style>
</head>
<body>
<div id="upper">
<p>点击下方按钮下载</p>
</div>
<div id="middle">
<a id="down" href=${item.itemUrl}></a>
</div>
<div id="bottom">
<p>${item.itemName}</p>
</div>
</body>
</html>