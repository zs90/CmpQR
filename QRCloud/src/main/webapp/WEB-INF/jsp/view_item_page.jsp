<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"  isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">  
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0"> 
<title>资源列表</title>
<script type="text/javascript" src="js/jquery-1.12.0.min.js"> </script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/jquery.uploadify.min.js"> </script>
<script type="text/javascript" src="js/zxx.drag.1.0-min.js" ></script>
<link rel="stylesheet" type="text/css" href="css/jquery.dataTables.min.css"/>
<link rel="stylesheet" type="text/css" href="css/uploadify.css" />
<link rel="stylesheet" type="text/css" href="css/button.css"/>
<link rel="stylesheet" type="text/css" href="css/page.css"/>
<style type="text/css">
#table {
	font-size:10px;
}
.pop_dialog{
	height:400px;
}

#item_table th{
	font-size: 14px;
}
#item_table td{
	font-size:13px;
}

.buttons{
	margin:30px;
}

.inspect_input_table{
	width:650px;
	height:450px;
	margin:10px auto 0;
	text-align:center;
}

#toProj{
	text-align:center;
	margin-top:20px;
}

#fucking_table{
	border-collapse: collapse;
}

#fucking_table td{
	border: solid 1px #B4B4B4;	
}

</style>
</head>
<body>
<!-- 灰色蒙版 -->
<div id="mask"></div>

<!-- 预览资源对话框 -->
<div id="inspect_dialog" class="pop_dialog" style="width:700px; height:600px">
	<div id="mybar2" class="dialog_bar" style="width:700px"></div>
	<div class="inspect_input_table">
		<table id="fucking_table">
			<tr>
				<td>
					资源名
				</td>
				<td>
					<div class="input_box1"><p id="inspect_item_name_id"> </p></div>
				</td>
				<td >
					资源介绍
				</td>
				<td>	
					<div class="input_box2"><p id="inspect_item_comment_id" style="font-size:40%"> </p></div>
				</td>
			</tr>
			<tr>
				<td rowspan="2">
					资源下载
				</td>
				<td>	
					<div class="input_box1"  style="font-size:10px"> <a id="inspect_item_url_id"></a> </div>
				</td>
				<td rowspan="2">
					资源在线预览
				</td>
				<td>	
					<div class="input_box1"  style="font-size:10px"> <a id="inspect_item_display_url_id"></a> </div>
				</td>
			</tr>
			<tr >
				<td>
					<img id="qr_image_res" width="160"/>
				</td>
				<td>
					<img id="qr_image_pre" width="160"/>
				</td>
			</tr>
			<tr>
				<td>
					资源大小
				</td>
				<td>	
					<div class="input_box1" > <p id="inspect_item_size_id"></p> </div>
				</td>
				<td>
					资源类型
				</td>
				<td>	
					<div class="input_box1" > <p id="inspect_item_type_id"></p> </div>
				</td>
			</tr>
			<tr>
				<td>
					创建时间
				</td>
				<td>	
					<div class="input_box1"> <p id="inspect_item_create_time_id"> </p> </div>
				</td>
				<td>
					更新时间
				</td>
				<td>	
					<div class="input_box1"> <p id="inspect_item_update_time_id"> </p> </div>
				</td>
			</tr>
		</table>
	</div>	
	<div class = "buttons">		
		<div id="cancel_inspect" class="button">关闭</div>
	</div>
</div>

<!-- 主页面 -->
<div id="main_page">
	<!-- 上导航栏 -->
	<div id="upper_nav_bar">
			<div id="cmp_brand">
				<img src="img/cmp.jpg" width=70 height=70></img>
			</div>
			<div id="search_box" class="before_select">
				<img src="img/ui/Search.png" width=30 height=30 style="vertical-align:middle;"></img>
				<input type="text" id="search_text_field" style="vertical-align:middle;">
			</div>
			<div id="inspect_button" class="operation" style="display:none; float:right" >
				<img src="img/ui/About.png" width=25 height=25 style="vertical-align:middle;"></img>
				预览资源
			</div>	
			<div id="pv_button" class="operation" style="display:none; float:right" >
				<img src="img/ui/Document.png" width=25 height=25 style="vertical-align:middle;"></img>
				访问统计
			</div>		
			<div class="operation cancel_all_button after_select multiple_select" style="float:left" >取消选择</div>
	</div>
	<!-- 左导航栏 -->
	<div id="left_nav_bar">
		<div class="left_sub_bar">
			<img src="img/ui/Bookmark.png" width=25 height=25 style="vertical-align:middle;"></img>
			欢迎！<%= (String)request.getSession(false).getAttribute("username") %>
		</div>
		<div class="left_sub_bar" style="overflow:auto; white-space:nowrap; ">
			<img src="img/ui/Folder.png" width=25 height=25 style="vertical-align:middle;"></img>
			<b>
				<%		
				String current_project = request.getParameter("project_name");
				out.print(current_project);
				%>
			</b>
		</div>
		<div class="left_sub_bar dynamic">	
			<div id="return_button" onclick="window.location.href='${from}'">
				<img src="img/ui/Home.png" width=25 height=25 style="vertical-align:middle;"></img>
				返回项目
			</div>
		</div>
		<div class="left_sub_bar dynamic">
			<div id="return_button" onclick="window.location.href='/QRCloud/logout.do'">
				<img src="img/ui/External.png" width=25 height=25 style="vertical-align:middle;"></img>
				注销
			</div>
		</div>	
	</div>
	<!-- 表格区域 -->
	<div id="content_area">
		<div id='table' style="height:90%;">	
			<table id="item_table" class="display" width="100%">
			</table>
		</div>
	</div>
</div>

<script type="text/javascript">
//每次选中项目后，对应按钮的动态显示
function check_status(table){
	if(table.rows(".selected").data().length == 1){
		$("#search_box").css("display", "none");
		$("#inspect_button").css("display", "block");
		$("#pv_button").css("display", "block");
		$('#upper_nav_bar').css("background-color", "#3B93FF");
	}
	else if(table.rows(".selected").data().length == 0){
		$("#search_box").css("display", "block");
		$("#inspect_button").css("display","none");	
		$("#pv_button").css("display", "none");
		$('#upper_nav_bar').css("background-color", "#F5F6F9");
	}
}

//检查选中目标资源类型
function typeExist(target, types){
	for(var i = 0; i < types.length; i++){
		if(types[i] == target){
			return i;
		}	
	}
	return -1;
}

$(document).ready(function() {		
	$("#left_nav_bar").height($(window).height() - $('#upper_nav_bar').height());
	$("#content_area").height($(window).height() - $('#upper_nav_bar').height());
	$("#content_area").width($(window).width() - $("#left_nav_bar").width() - 2);
	$("#mask").height($(window).height());
	
	var dataSet = new Array();
	var fucking_id = [];
	var comments = [];
	var all_types = ["gif","png","jpg","mp4","mp3","bmp","pdf"];
	var link_types = ["paper","web"];

	//表格数据加载
	$.ajax({
		url:"GetItemList.do",
		async:false,
		type:'get',
		dataType:'json',
		success:function(data){
			for(var i = 0; i< data.length; i++){
				dataSet[i] = new Array();
				for(var j = 0; j < 7; ++j){
					dataSet[i][j] = data[i][j];
				}
				dataSet[i][6] = (dataSet[i][6] / 1048576.0).toFixed(3);				
				fucking_id[i] = data[i][7];
				comments[i] = data[i][8];
				dataSet[i][7] = data[i][9];		
				dataSet[i][8] = data[i][10] == 1?"<span style='color:red;font-weight:bold;'>yes</span>":"no";
				dataSet[i][0] = (parseInt(dataSet[i][0]) + 1).toString();
			}
		}			
	});	

	var table = $('#item_table').DataTable( {
		data: dataSet,
		columns: [
			{ title: "编号"},
			{ title: "名称", width:"200px"},
			{ title: "创建时间", width:"130px" },
			{ title: "更新时间", width:"130px" },
			{ title: "所有者" },
			{ title: "类型" },
			{ title: "大小(MB)" },
			{ title: "访问量"},
			{ title: "最近修改"}
		],
		paging:true,
		dom:'rtip'
	});
	//为表格添加部分元数据
	for(var i = 0; i < table.rows().data().length; i++){
		var row = table.row(i).node();
		$(row).attr("rid", fucking_id[i]);
		$(row).attr("comment", comments[i]);
		$(row).attr("item_name", dataSet[i][1]);
	}
	//表格中项目选中事件
	$('#item_table tbody').on('click', 'tr', function(){
		if($(this).hasClass('selected')){
			$(this).removeClass('selected');
		}
		else{
			table.$('tr.selected').removeClass('selected');
			$(this).addClass('selected');
		}
		check_status(table);
	});

	$('#search_text_field').keyup(function(){
	      table.search($(this).val()).draw() ;
	});

	//预览资源事件
	$('#inspect_button').click(function(){	
		$("#mask").fadeTo(500, 0.6);
		$('#inspect_dialog').fadeIn(500);
	
		$('#inspect_item_name_id').text(table.row('.selected').data()[1]);
		$('#inspect_item_comment_id').text($(table.row('.selected').node()).attr("comment"));
		
		var oBox = document.getElementById("inspect_dialog");
     	var oBar = document.getElementById("mybar2");
     	startDrag(oBar, oBox);
		
		$.ajax({
			url:"GetItemQR.do",
			data:{"item_id": $(table.row('.selected').node()).attr("rid")},
			async:false,
			type:'post',
			dataType:'json',
			success:function(data){			
				$("#qr_image_res").removeAttr("src");
				$("#qr_image_pre").removeAttr("src");
				$("#inspect_item_display_url_id").html("");					
				$("#inspect_item_url_id").html("");	
				
				//object that can be downloaded.
				if(typeExist(table.row('.selected').data()[5], link_types) == -1){
					$("#qr_image_res").attr("src", data[1]);
					var link = 'http://qr.cmpedu.com/CmpBookResource/download_resource.do?id=' + $(table.row('.selected').node()).attr("rid");	
					$("#inspect_item_url_id").attr("href", "http://qr.cmpedu.com/CmpBookResource/download_resource.do?id=" + $(table.row('.selected').node()).attr("rid"));
					$("#inspect_item_url_id").html("http://qr.cmpedu.com/CmpBookResource/<br/>download_resource.do?id=" + $(table.row('.selected').node()).attr("rid"));	
				}
				
				//object that can be displayed
				if(typeExist(table.row('.selected').data()[5],link_types) != -1 || typeExist(table.row('.selected').data()[5],all_types) != -1){
					$("#qr_image_pre").attr("src", data[0]);
					var link = 'http://qr.cmpedu.com/CmpBookResource/show_resource.do?id=' + $(table.row('.selected').node()).attr("rid");
					$("#inspect_item_display_url_id").attr("href", link);
					$("#inspect_item_display_url_id").html('http://qr.cmpedu.com/CmpBookResource/<br/>show_resource.do?id=' + $(table.row('.selected').node()).attr("rid"));
				}
						
				$("#inspect_item_create_time_id").text(table.row('.selected').data()[2]);
				$("#inspect_item_update_time_id").text(table.row('.selected').data()[3]);
				$("#inspect_item_type_id").text(table.row('.selected').data()[5]);
				$("#inspect_item_size_id").text(table.row('.selected').data()[6] + "MB");
			}			
		});			
	});
		
	$('#cancel_inspect').click(function(){
		$("#inspect_dialog").fadeOut(500);
		$("#mask").fadeOut(500);
	});	
	
	$(".cancel_all_button").click(function(){
		table.rows('.selected').nodes().to$().removeClass('selected');
		check_status(table);
	});
	
	$('#pv_button').click(function(){		
		window.location.href = 'GetItemPV.do?id=' + $(table.row('.selected').node()).attr("rid") + '&' + 'name=' + table.row('.selected').data()[1];
	});
});

//窗口大小变动事件
$(window).resize(function() {
	$("#left_nav_bar").height($(window).height() - $('#upper_nav_bar').height());
	$("#content_area").height($(window).height() - $('#upper_nav_bar').height());
	$("#content_area").width($(window).width() - $('#left_nav_bar').width() - 2);
	$("#mask").height($(window).height());
});
</script>
</body>
</html>