<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery-1.12.0.min.js"> </script>
<link rel="stylesheet" type="text/css" href="css/button.css"/>
<title>二维码批量导出</title>
</head>
<body>
<%@page import="java.util.List"%>
<div style="width:1000px; margin:0 auto;">
<div>
	<div id="download_zip" class="button">
	<img src='img/ui/Download.png' height="23" style="vertical-align:middle"></img>
		下载二维码
	</div>	
</div>
<table border="1" width="700" style="border-collapse: collapse">
<%
        List<List<String>> list = (List<List<String>>)request.getAttribute("qr_table");
        //String[] colNames = new String[]{"资源名","资源介绍","更新时间","资源原始地址","资源预览地址二维码"};
        String[] colNames = new String[]{"资源名","修改时间","下载地址","下载地址二维码","展示地址","展示地址二维码"};
        out.println("<tr>");
        for(int i = 0; i < 6; ++i){
                out.println("<td>");
                out.print(colNames[i]);
                out.println("</td>");
        }
        out.println("</tr>");
        for(int i = 0; i < list.size(); ++i){
                out.println("<tr>");
                out.println("<td>");
                out.print(list.get(i).get(0));
                out.println("</td>");
                out.println("<td>");
                out.print(list.get(i).get(2));
                out.println("</td>");
                out.println("<td>");
		if(!list.get(i).get(3).equals("")){
                	out.print("http://qr.cmpedu.com/CmpBookResource<br/>/download_resource.do?id=" + list.get(i).get(5));
                }
		out.println("</td>");
                out.println("<td style=\"width:140px\">");
                out.print("<img width=\"140\" src=\"" + list.get(i).get(3) + "\"/ >");
                out.println("</td>");

                out.println("<td>");
                if(!list.get(i).get(4).equals("")){
                        out.print("http://qr.cmpedu.com/CmpBookResource<br/>/show_resource.do?id=" + list.get(i).get(5));
                }
                out.println("</td>");

                out.println("<td style=\"width:140px\">");
                out.print("<img width=\"140\" src=\"" + list.get(i).get(4)+ "\"/ >");
                out.println("</td>");
                out.println("</tr>");
        }
%>
</table>
</div>
<script type="text/javascript">
$(document).ready(function() {
	$('#download_zip').click(function(){
		window.location="QRDownloadZip.do?project_id=<%out.print((String)request.getAttribute("pid"));%>";
	});	
});
</script>
</body>
