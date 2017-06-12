<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${project.projectName}</title>
</head>
<body>

<div style="width:200px; margin:0 auto">
<p>请点击右键另存为下载</p>
<h1>${project.projectName}</h1>
<c:forEach var="item" items="${project.items}">
	<a href=${item.itemUrl}>
	${item.itemName}
	</a>
	<br/>
</c:forEach>
</div>
</body>
</html>