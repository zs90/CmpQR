<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="refresh" content="3; url=${item.itemUrl}">
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
	background-image:url(img/bg.jpg);
	background-size:cover;
}
#panel{
	position:absolute;
	left:0; right:0; top:0; bottom:0;
	width:90%;
	height:30%;
	margin:auto 5%;
	background-color:#394165;
	border-bottom:15px solid #293357
}
#bar{
	height:20%;
	width:100%;
	background-color:#2b2937;
	text-align:center;
	border-top:1px solid black;
}
#content{
	height:50%;
	width:100%;
	margin:6% 0 0 0;
	text-align:center;
}
#button_area{
	height:15%;
	width:40%;
	margin:0px auto;
}
h1{
	margin:1% auto 0;
	color:#A4AAC0;
	font-size:400%;
}
p{
	color:#868da8;
	font-size:150%;
}
.but{
	border:none;
	height:90%;
	width:40%;
	font-size:180%;
	color:white;
}
</style>
</head>
<body>
<div id="panel">
<div id="bar">
	<h1>
		考  试
	</h1>
</div>
<div id="content">
	<p>
		快来测试一下，看你学习的效果如何？
	</p>
</div>
<div id="button_area">
<button type="button" id="ok" class="but" style="background-color:#e52b38; float:left">同意</button>
<button type="button" id="cancel" class="but" style="background-color:#1bbc9b; float:right">取消</button>
</div>
</div>

<script type="text/javascript">
$(document).ready(function() {
	$("#ok").click(function(){
		window.location.href = "${item.itemUrl}";
	});
})
</script>
</body>
</html>