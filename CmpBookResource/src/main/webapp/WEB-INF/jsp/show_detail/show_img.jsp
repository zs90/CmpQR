<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery-1.12.0.min.js">
	
</script>
<title>欢迎来到机械工业出版社二维码资源库</title>
<link rel="stylesheet" type="text/css" href="css/page.css" />
<style type="text/css">
#front {
	background-color: #EAC80E;
}

#full_screen {
	width: 100%;
	height: 100%;
	overflow: auto;
}
</style>
</head>
<body>
	<div id="full_screen">
		<img id="full_img"></img>
	</div>
	<div id="main">
		<div id="front"><p style="font-size:55px; margin:0px;">${item.itemName}</p></div>
		<div id="content">
			<img id="img" src=${item.itemUrl } />
		</div>
		<div id="comment"><p style="font-size:40px; margin:0px;">亲，点击图片可以查看大图，再次点击恢复原大小。某些动图可能较大，加载较慢，请您耐心等待……${item.itemComment}</p></div>

		<div id="foot"></div>
	</div>
	<script type="text/javascript">
		$(window).load(function() {
			var w = $("#content").width();
			var h = $("#content").height();

			if (w / h > $("#img").width() / $("#img").height()) {
				$("#img").css("height", h);
			} else
				$("#img").css("width", w);

			$("#img").click(function() {
				$("#full_img").attr("src", $("#img").attr("src"));
				$("#full_img").attr("width", $("#full_screen").width());

				$("#full_screen").css("display", "block");
				$("#main").css("display", "none");
			})

			$("#full_img").click(function() {
				$("#full_screen").css("display", "none");
				$("#main").css("display", "block");
			})
		})
	</script>
</body>

</html>
