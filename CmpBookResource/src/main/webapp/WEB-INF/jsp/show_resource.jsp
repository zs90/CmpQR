<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery-1.12.0.min.js"> </script>
<title>${item.itemName}</title>
<style type="text/css">
   html{
   	   height:100%;
       margin:0;
   }
   body{
       height:100%;
       margin:0; 
   }
   p{
   	   margin:0 auto;
   	   font-size:600%;
   	   height:100%;
   	   width:100%;
   }
   #main{
   		margin:auto;
   		text-align:center; 
   		width:100%; 
   		height:100%;
   		background-color:#555555;
   }
   #front{
   		margin:0px; 
   		height:10%; 
   		width:100%; 
   		background-color:#4DB39E;
   		border:1px solid #4DB39E;
   }
   #content{
   		margin:0px;
   		height:35%;
   		width:100%
   }
   #comment{
   		margin:0px;
   		height:55%;
   }
</style>
</head>
<body style="margin:0">
<div id="main" >
<div id="front" >
	<p>
		${item.itemName}
	</p>
</div>
<div id="content">
<c:choose>
	<c:when test="${item.itemType == 'mp4'}">
	<video id="vi" controls="controls" preload="none" width="100%" height="100%"><source src=${item.itemUrl} /></video>
	</c:when>
	
	<c:when test="${item.itemType == 'jpg' || item.itemType == 'gif' || item.itemType == 'png' || item.itemType == ''}">
	<img id="img1" src=${item.itemUrl} /> 
	</c:when>
	
	<c:when test="${item.itemType == 'mp3'}">
	<audio controls="controls" preload="none" style="height:200px; width:600px"> <source src=${item.itemUrl} /></audio>
	</c:when>		
	
	<c:when test="${item.itemType == 'paper'}">
	<a href=${item.itemUrl}> 点我开始做题啦 </a>
	</c:when>
	
	<c:when test="${item.itemType == 'web'}">
	<a href=${item.itemUrl}> 点我开始吧</a>
	</c:when>
</c:choose>
</div>
<div id="comment">
	
</div>
</div>

<script type="text/javascript">
$(document).ready(function() {
	$("#img1").css("width", $(window).width()/2) ;
})
</script>
</body>
</html>