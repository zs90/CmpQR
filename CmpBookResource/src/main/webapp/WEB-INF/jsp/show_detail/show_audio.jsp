<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery-1.12.0.min.js"> </script>
<title>欢迎来到机械工业出版社二维码资源库</title>
<style type="text/css">
html, body {
	height: 100%;
	margin: 0;
}

#head {
	background-color: #E42B38;
	height: 210px;
	width: 100%;
	color: white;
	line-height:210px;
	text-align:center;
	overflow:auto;
	white-space:nowrap;
}

#content {
	background-color: #2b2937;
	height: 1140px;
	border-top: 1px solid #2b2937;
}

#control {
	background-color: #413f4c;
	height: 220px;
	border-top: 1px solid #413f4c;
}

#interaction {
	background-color: #23212c;
	height: 180px;
}

#content_bar {
	margin: 7% auto;
	width: 80%;
	height: 40px;
}

#play_pivot {
	position: absolute;
	width: 90px;
	height: 35px;
	left: 0px;
	top: -10px;
	background-image: url(img/pivot.png);
	background-size: cover;
	z-index: 3;
}

#past_bar {
	position:absolute;
	top:0px;
	left:0px;
	height: 100%;
	width: 0px;
	background-color: #E42B38;
	margin: 0px;
	z-index: 2;
}

#buffer_bar {
	position:absolute;
	top:0px;
	left:0px;
	height: 100%;
	width: 0px;
	background: #E6E6E6;
	margin: 0px;
	z-index: 1;
}

#bg_img {
	width: 60%;
	margin: 5% auto;
	height: 590px;
	background-image: url(img/cd.png);
	background-size: cover;
}

#play_button {
	display: block;
	height: 190px;
	margin: 15px auto;
	width: 190px;
	background-size:190px 190px;
	background-image: url(img/play_music.png);
}

#timer {
	width: 50%;
	height: 180px;
	margin: 0px auto;
	text-align: center;
	position: relative;
}

#ptime {
	font-size: 400%;
	color: white;
	margin: auto auto;
	position: absolute;
	left: 0;
	right: 0;
	top: 0;
	bottom: 0;
	height: 50%;
	width: 90%;
}

#bar_container{
	height: 20px; 
	width: 100%; 
	margin: auto 0; 
	border: 7px solid #403f4c; 
	background-color: #060815;
	position:relative;
	top:0px;
}
</style>

</head>
<body>
	<div id="head">
		<p style="font-size:55px; margin:0px;">${item.itemName}</p>
	</div>
	<div id="content">
		<div id="bg_img"></div>
		<div id="timer">
			<p id="ptime">00:00/00:00</p>
		</div>
		<div id="content_bar">
			<div id="bar_container">
				<div id="past_bar"></div>
				<div id="buffer_bar"></div>
				<div id="play_pivot"></div>
			</div>		
		</div>

		<audio id="my_audio" preload="meta"> <source
			src=${item.itemUrl } /></audio>
	</div>

	<div id="control">
		<a href="javascript:;" id="play_button"></a>
	</div>
	<div id="interaction"></div>

<script type="text/javascript">
var play_timer, buffer_timer, audio;
var is_playing = false;
var rot = 0;
var move_length = $("#content_bar").width() - $("#play_pivot").width();

jQuery.fn.rotate = function(degrees) {
    $(this).css({'-webkit-transform' : 'rotate('+ degrees +'deg)',
                 '-moz-transform' : 'rotate('+ degrees +'deg)',
                 '-ms-transform' : 'rotate('+ degrees +'deg)',
                 'transform' : 'rotate('+ degrees +'deg)'});
    return $(this);
};

function formatTime(time){
        var past = parseInt(time);
        var min = parseInt(past / 60);
        var sec = past % 60;
		if(min < 10)
			min = '0' + min;
		if(sec < 10)
			sec = '0' + sec;
			
        return min + ":" + sec;
}

function play_buffer(){
	var i = 0;
	while(i < audio.buffered.length){
		$("#buffer_bar").css("width", audio.buffered.end(i) / audio.duration * $("#content_bar").width());
		if(audio.duration - audio.buffered.end(i) < 0.1){
			window.clearInterval(buffer_timer);
		}
		i = i + 1;
	}
}

function move_pivot(){
		$("#play_pivot").css("left", audio.currentTime / audio.duration * move_length);
		$("#past_bar").css("width", audio.currentTime / audio.duration * move_length);
		$("#bg_img").rotate(rot);
		rot = (rot + 1) % 360;

		$("#ptime").text(formatTime(audio.currentTime) + '/' + formatTime(audio.duration));
		if(audio.ended == true){
				window.clearInterval(play_timer);
				is_playing = false;
				$("#play_button").css("background-image", "url(img/play_music.png)");
		}
}

$(document).ready(function() {
        audio = document.getElementById("my_audio");

        $("#play_button").click(function(){
            if(is_playing == false){
                audio.play();
                is_playing = true;
                $("#play_button").css("background-image", "url(img/pause_music.png)");
                play_timer = window.setInterval(move_pivot,50);
				buffer_timer = window.setInterval(play_buffer, 50);
        	}
        	else{
                audio.pause();
                is_playing = false;
                $("#play_button").css("background-image", "url(img/play_music.png)");
                window.clearInterval(play_timer);
        	}
		});
		
		$("#play_pivot").bind('touchmove', function(e){
				var bar = document.getElementById("content_bar");
		        e.preventDefault();
		        var touch =  e.originalEvent.touches[0] || e.originalEvent.changedTouches[0];		
		        $("#play_pivot").css("left", touch.pageX - bar.getBoundingClientRect().left);
		        $("#past_bar").css("width", touch.pageX - bar.getBoundingClientRect().left);		        
		});
		
		$("#play_pivot").bind('touchstart', function(){
				if(is_playing == true)
					window.clearInterval(play_timer);
		});
		
		$("#play_pivot").bind('touchend', function(){			
			audio.currentTime = parseInt($("#play_pivot").css("left")) / move_length * audio.duration;
			play_timer = window.setInterval(move_pivot, 50);

			if(is_playing == false){
				if(buffer_timer == null)
					buffer_timer = window.setInterval(play_buffer, 50);
                audio.play();	
                is_playing = true;
                $("#play_button").css("background-image", "url(img/pause_music.png)");				
			}
		});
})
</script>

</body>
</html>
