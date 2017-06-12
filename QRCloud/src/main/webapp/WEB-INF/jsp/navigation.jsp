<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false" %>
<div id="nav">
<div style="float:left">
<p style="margin:10px 0 0 0;">
欢迎！
<%= (String)request.getSession(false).getAttribute("username") %>
</p>
</div>
<div style="float:right;">
<button type="button" class="button" onclick="window.location.href='/QRManager/logout.do'">注销</button>

</div>
</div>