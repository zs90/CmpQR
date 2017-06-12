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
   	   width:100%;
       margin:0;
   }
   body{
       height:100%;
       width:100%;
       margin:0; 
   }
   #item_name{
   	   margin:auto auto;
   	   font-size:400%;
   	   color:white;
   	   height:50%;
   	   width:50%;
   	   left:0; right:0; top:0; bottom:0;
   	   position:absolute;
   }
   #main{
   		margin:auto;
   		text-align:center; 
   		width:100%; 
   		height:100%;
   		background-color:#434250;
   }
   #front{
   		margin:0px; 
   		height:9%; 
   		width:100%; 
   		background-color:#EAC80E;
   		border:1px solid #1bbc9b;
   		border-bottom:15px solid #2b2937;
   		position:relative;
   }
   #content{
   		margin:50px 0px 0px;
   		height:77%;
   		width:100%;
   		border-style:solid none solid none;
   		border-width:thin 0px thin 0px;
   		border-color:black black black black;
		overflow: auto; 
		-webkit-overflow-scrolling: touch;
	}
   #foot{
   		margin:0px;
   		height:10%
   }
   #item_comment{
   		color:white;
   		font-size:250%;
   		margin:0px 0px 0px 0px;
   		text-align:justify;
   }
 </style>
</head>
<body style="margin:0">
<div id="mask"></div>
<div id="full_screen">
<img id="full_img"></img>
</div>
<div id="main" >
<div id="front" >
	<p id="item_name">
		${item.itemName}
	</p>
</div>
<div id="content">
<embed id="pdf" src=${item.itemUrl} width="100%">
</div>
<div id="foot">
<div id="section_1" style="float:left; width:40%; height:95%; border:solid thin #8e94aa; border-left:none; border-bottom:none"></div>
<div id="section_2" style="float:left; width:30%; height:95%; border:solid thin #8e94aa; border-left:none; border-bottom:none"></div>
<div id="section_3" style="float:left; width:29.5%; height:95%; border:solid thin #8e94aa; border-left:none; border-bottom:none; border-right:none"></div>
</div>
</div>
<script type="text/javascript">

</script>
</body>
<script type="text/javascript">
$(window).load(function() {
	//$("#pdf").css("height", "80000px") ;
	//$("#pdf").css("width", $("#content").width()) ;
})
</script>
</html>
