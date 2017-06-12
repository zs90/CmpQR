<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎来到云资源二维码管理平台</title>
<script type="text/javascript" src="js/jquery-1.12.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/button.css"/>
<style type="text/css">
html,body{ 
	margin:0px; 
	height:100%; 
	width:100%;
	font-family:\5FAE\8F6F\96C5\9ED1;
} 
#main{
	margin:0px; 
	height:100%; 
	width:100%;
}
#upper{
	width:100%;
	height:70px;
}

#middle{
	width:100%;
	height:550px;
	background-color:#E0F2FF;
	background-image:url(img/cloud.jpg);
	background-size:cover;
}

#foot{
	width:100%;
	margin:20px auto 0;
	padding:5px 0 5px 0;
	text-align:center;
	font-size:small;
	background-color:#F5F6F9;
}

#login_panel{
	float:right;
	width:400px;
	height:350px;
	background-color:white;
	text-align:center;
	margin:90px 8% 0px 0px;
}

#user_input{
	width:250px;
	height:40px;
	border-style:solid;
	border-color:#EAEAEA #EAEAEA #EAEAEA #2AB9FF;	
	border-left-width: 5px;
	padding:0 0 0 5px;
	display:block;
	margin:40px auto 10px;
}

#pw_input{
	width:250px;
	height:40px;
	border-style:solid;
	border-color:#EAEAEA #EAEAEA #EAEAEA #DC5026;
	border-left-width: 5px;
	padding:0 0 0 5px;
	display:block;
	margin:40px auto 10px;
}

#input_button{
	margin:40px auto 0;
	width:180px;
	height:36px;
}

#login_panel p{
	font-weight:bold;
	font-size:large;
}

#platform_function{
	width:100%;
	height:330px;
}

.func_item{
	float:left;
	width:20%;
	height:80%;
	margin:50px 2.3% 30px 2.6%;
	text-align:center;
}

#platform_function img{
	width:40%;
}

#platform_function p{
	color:#3B93FF;
}

.nav_item{
	float:right; 
	width:100px; 
	height:40px; 
	line-height:40px; 
	color:#60666b;
}

.nav_item:hover{
	cursor:pointer;
	color:#3091F2;
}

</style>

</head>

<body>
<div id="main">
	<div id="upper">
		<img src="img/logo.png" height=40 style="float:left; margin:15px 5% 0;"></img>
		<div id="intro" class="nav_item" style="margin:15px 6% 0 0;">功能介绍</div>
		<div id="main_page" class="nav_item" style="margin:15px 2% 0 0;">首页</div>
	</div>
	<div id="middle">
		<div id="login_panel">
			<div style="border-bottom:1px solid grey">	
			<p style="color:#666666">欢迎登录 </p>
			</div>		
			<input type="text" name="username" id="user_input" placeholder="请输入用户名"/> 											
			<input type="password" name="password" id="pw_input" placeholder="请输入密码"/> 		
			<div class="button" id="input_button">登录 </div>
		</div>	
	</div>
	<div id="platform_function">
		<div id="lock" class="func_item">
			<img src="img/ui/Lock.png" ></img>
			<p>
				<b>
				安全防护技术
				</b>
			</p>
			<p>
				防盗链，防恶意刷流量
			</p>
		</div>
		<div id="cloud" class="func_item">
			<img src="img/ui/Cloud.png"></img>
			<p>
				<b>基于Amazon Web Services</b>
			</p>
			<p>
				稳定，安全，高速
			</p>
		</div>
		<div id="file" class="func_item">
			<img src="img/ui/File.png"></img>
			<p>
				<b>支持多种格式在线浏览</b>
			</p>
			<p>
				png gif bmp jpg mp3 mp4 pdf
			</p>
		</div>
		<div id="rating" class="func_item">
			<img src="img/ui/Rating.png"></img>
			<p>
				<b>其他特色</b>
			</p>
			<p>
				二维码自动生成，活码管理
			</p>
			<p>
				读者浏览行为统计
			</p>
			<p>
				统一审核机制，确保内容安全
			</p>
		</div>
	</div>
	<div id="foot" style="font-family:宋体; color:#74787c">
		<p>版权所有：机械工业出版社  Copyright (C) 2015-2016. All Rights Reserved</p>
	</div>	
</div>		
<script type="text/javascript">
	$(window).keydown(function(event){
		if(event.keyCode == 13){
			doLogin();
		}
	});
	
	function doLogin(){
		$.ajax({
			url:"Login.do",
			data:{"username":$('#user_input').val(), "password":$('#pw_input').val()},
			type:'post',
			success:function(data){
				if(data == '1')
					alert("用户名或密码错误");
				else
					window.location.href=data;
			}
		});			
	}
	
	$('#input_button').click(function(){
		doLogin();
	})

	$(document).ready(function(){
  		$("#intro").click(function(){
    			$("html,body").animate({scrollTop:$("#platform_function").offset().top}, "normal");
  		});
	});
</script>
</body>

</html>
