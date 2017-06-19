<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
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

.before_select{
	display:block;
	float:right;
}
.after_select{
	display:none;
	float:right;
}
.multiple_select{
	display:none;
	float:right;
}

.pop_dialog{
	height:400px;
}

.buttons{
	margin:30px;
}

#file_upload{
	margin:0 auto;
}

#file_replace{
	margin:0 auto;
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
#item_table th{
	font-size: 14px;
}
#item_table td{
	font-size:13px;
}
.upload_status_display{
	height:450px;
}
.upload_display {
  	background-color: #FFF;
  	border-radius: 3px;
  	box-shadow: 0 1px 3px rgba(0,0,0,0.25);
  	height: 380px;
  	margin: 5px 40px 5px;
  	overflow: auto;
  	padding: 5px 10px;
  	width: 350px;
}
#upload_op{
	height:100px;
}
#start_upload{
	height:35px;
	width:240px;
	margin:30px 30px;
	float:right;
}
#select_file{
	width:410px; 
	height:30px; 
	float:left;
	margin:30px 0px;
}
.result_bar{
	background-color: #FFF;
	border-radius: 2px;
	box-shadow: 0 1px 3px rgba(0,0,0,0.25);
	height: 40px;
	line-height:40px;
	margin: 5px auto 5px;
	width: 330px;
	text-align:left;	
	word-break : break-all;
	overflow:auto;
}

.uploadify:hover .uploadify-button {
	background:#1165ae;
}


</style>
</head>
<body>
<!-- 灰色蒙版 -->
<div id="mask"></div>

<!-- 右键面板 -->
<div id="right_panel" style="height:77px">
	<div id="r_update" class="r_item">更新资源</div>
	<div id="r_inspect" class="r_item">预览资源</div>
	<div id="r_delete" class="r_item">删除资源</div>
</div>

<!-- 更新对话框 -->
<div id="update_dialog" class="pop_dialog" style="height:320px">
	<div id = "mybar1" class="dialog_bar"></div>
	<div class="input_table" style="height:180px">
		<table border="0">
			<tr>
				<td>
					资源名
				</td>
				<td>
					<div class="input_box1"><input type="text" name="update_item_name" id="update_item_name_id" class="ti"/> </div>
				</td>
			</tr>
			<tr>
				<td>
					资源介绍
				</td>
				<td>	
					<div class="input_box2"><textarea name="update_item_comment" id="update_item_comment_id" class="ta"></textarea></div>
				</td>
			</tr>
		</table>
	</div>	
	<div class = "buttons">
		<div id="confirm_update" class="button"> 确定</div>
		<div id="cancel_update" class="button"> 取消</div>		
	</div>
</div>

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

<!-- 上传链接资源对话框 -->
<div id="upload_link_dialog" class="pop_dialog">
	<div id = "mybar3" class="dialog_bar"></div>
	<div class="input_table">
		<table border="0">
			<tr>
				<td>
					链接名
				</td>
				<td>
					<div class="input_box1"><input type="text" name="upload_link_name" id="upload_link_name_id" class="ti"/> </div>
				</td>
			</tr>
			<tr>
				<td>
					链接介绍
				</td>
				<td>	
					<div class="input_box2"><textarea name="upload_link_comment" id="upload_link_comment_id" class="ta"></textarea></div>
				</td>
			</tr>
			<tr>
				<td>
					链接地址
				</td>
				<td>	
					<div class="input_box1"><textarea name="upload_link_url" id="upload_link_url_id" class="ti" placeholder="请带上http://或https://前缀"></textarea></div>
				</td>
			</tr>
			<tr>
				<td>
					链接类型
				</td>
				<td>
					<div style="height:35px; margin:10px 0 0 0; text-align:center">
					<select class="selector">
						<option value="paper">试卷</option>
						<option value="web">网站</option>
					</select>
					</div>
				</td>
			</tr>
		</table>
	</div>	
	<div class = "buttons">
		<div id="confirm_upload" class="button">确定 </div> 
		<div id="cancel_upload" class="button">取消 </div>		
	</div>
</div>

<!-- 替换链接资源对话框 -->
<div id="replace_link_dialog" class="pop_dialog">
	<div id = "mybar4" class="dialog_bar"></div>
	<div class="input_table">
		<table border="0">
			<tr>
				<td>
					链接名
				</td>
				<td>
					<div class="input_box1"><input type="text" name="replace_link_name" id="replace_link_name_id" class="ti" readonly/> </div>
				</td>
			</tr>
			<tr>
				<td>
					链接介绍
				</td>
				<td>	
					<div class="input_box2"><textarea name="replace_link_comment" id="replace_link_comment_id" class="ta" readonly></textarea></div>
				</td>
			</tr>
			<tr>
				<td>
					链接地址
				</td>
				<td>	
					<div class="input_box1"><textarea name="replace_link_url" id="replace_link_url_id" class="ti" placeholder="请带上http://或https://前缀"></textarea></div>
				</td>
			</tr>
		</table>
	</div>	
	<div class = "buttons">
		<div id="confirm_replace" class="button"> 确定</div>
		<div id="cancel_replace" class="button"> 取消	</div>
	</div>
</div>

<!-- 上传文件对话框 -->
<div id="upload_dialog" class="pop_dialog" style="width:900px; height:600px">
	<div id = "mybar5" class="dialog_bar" style="width:860px; display:inline-block"
	></div><div id="close_upload" style="width:40px; margin:0px auto; height:40px; display:inline-block; background-image:url('img/ui/Close.png'); background-size:cover;"
	></div>
	<div class="upload_status_display">
		<div style="float:left; text-align:center">	
			<p style="color:#1D7FE2">上传进度</p>	
			<div id="some_file_queue" class="upload_display">  </div>
		</div>
		<div style="float:right; text-align:center">
			<p style="color:#1D7FE2">上传结果</p>
			<div id="file_upload_result" class="upload_display"> </div>
		</div>
	</div>
	<div id="upload_op">
		<div id="select_file">
			<div id="file_upload"></div>
		</div>
		<div id="start_upload">
			<img src="img/ui/Cloud.png" height="35" style="vertical-align:middle"></img
			><div id="start_upload_button" class="button">开始上传</div>
		</div> 
	</div>
</div>

<!-- 批量添加链接对话框 -->
<div id="upload_multi_link_dialog" class="pop_dialog" style="width:900px; height:600px">
	<div id = "mybar6" class="dialog_bar" style="width:900px; display:inline-block"
	></div>
	<div class="upload_status_display">
		<div style="float:left; text-align:center">	
			<p style="color:#1D7FE2">待添加链接</p>	
			<textarea id="multi_link_url_id" placeholder="每个项目占一行 每行三列 列间空格分隔  格式为：项目名 地址名 类型  eg:test http://www.baidu.com web"  class="upload_display" style="border:0px">
			</textarea>
		</div>
		<div style="float:right; text-align:center">
			<p style="color:#1D7FE2">添加结果</p>
			<div id="link_upload_result" class="upload_display"> </div>
		</div>
	</div>
	<div style="width:220px; height:50px; margin:30px auto 0px;">
		<div id="confirm_multi_link_upload" class="button"> 确定</div>
		<div id="cancel_multi_link_upload" class="button"> 取消</div>
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
			<div id="upload_button" class="operation button before_select">
				<img src="img/ui/Plus.png" width=25 height=25 style="vertical-align:middle;"></img>
				添加文件
			</div>
			<div id="upload_link_button" class="operation button before_select" style="width:130px">
				<img src="img/ui/Plus.png" width=25 height=25 style="vertical-align:middle;"></img>
				添加单个链接
			</div>
			<div id="upload_multi_link_button" class="operation button before_select" style="width:130px">
				<img src="img/ui/Plus.png" width=25 height=25 style="vertical-align:middle;"></img>
				批量添加链接
			</div>
			<div id="delete_button" class="operation delete after_select" >
				<img src="img/ui/Delete.png" width=25 height=25 style="vertical-align:middle;"></img>
				删除资源
			</div>
			<div id="replace_link_button" class="operation after_select">
				<img src="img/ui/Edit.png" width=25 height=25 style="vertical-align:middle;"></img>
				更新链接
			</div>
			<div id="update_button" class="operation after_select" >
				<img src="img/ui/Edit.png" width=25 height=25 style="vertical-align:middle;"></img>
				更新资源描述
			</div>
			<div id="inspect_button" class="operation after_select" >
				<img src="img/ui/About.png" width=25 height=25 style="vertical-align:middle;"></img>
				预览资源
			</div>			
			<div class="operation cancel_all_button after_select multiple_select" style="float:left" >取消选择</div>
			<div class="selected_sum after_select multiple_select" style="color:white; float:left; width:120px; margin:15px 20px; line-height:30px;"></div>
			<div class="operation delete multiple_select" >
				<img src="img/ui/Delete.png" width=25 height=25 style="vertical-align:middle;"></img>
				批量删除资源
			</div>
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
			<div id="return_button" onclick="window.location.href='/QRCloud/project_page.do'">
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
		<div id="select_all_button" class="operation button" style="margin:10px 20px 0; display:inline-block">全选</div>
		<div id="file_replace1" style="float:right; display:inline-block">
			<div id="file_replace" >				
			</div>				
		</div>
		<div id="rep_queue" style="float:right"></div>
		<div id='table'>	
			<table id="item_table" class="display" width="100%">
			</table>
		</div>
	</div>
</div>

<script type="text/javascript">
//对表格编号进行刷新
function resetNo(mtable){
	var i = 1;
	mtable.rows().every( function ( rowIdx, tableLoop, rowLoop ) {
    		this.data()[0] = i++;
		this.invalidate();
    	} );
	mtable.draw();
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

//每次选中项目后，对应按钮的动态显示
function check_status(table){
	if(table.rows(".selected").data().length == 1){
		$(".before_select").css("display", "none");	
		$(".multiple_select").css("display","none");
		$(".after_select").css("display","block");
		
		$("#file_replace").css("display","block");		
		$("#file_replace").css("float","right");
		$("#file_replace").css("margin","10px");
		
		$('.selected_sum').text("已选择了" + table.rows(".selected").data().length + "项");
		$('#upper_nav_bar').css("background-color", "#3B93FF");
	}
	else if(table.rows(".selected").data().length == 0){
		$(".after_select").css("display","none");	
		$(".multiple_select").css("display","none");
		$(".before_select").css("display", "block");
		
		$("#file_replace").css("display","none");
		$('#upper_nav_bar').css("background-color", "#F5F6F9");
	}
	else if(table.rows(".selected").data().length > 1){
		$(".before_select").css("display", "none");
		$(".after_select").css("display","none");	
		$(".multiple_select").css("display","block");	
		
		$("#file_replace").css("display","none");
		
		$('.selected_sum').text("已选择了" + table.rows(".selected").data().length + "项");
		$('#upper_nav_bar').css("background-color", "#3B93FF");
	}
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
	var ctrl_down = false;
	var shift_down = false;

	//按键响应事件
	$(document).keydown(function(e){
		if(e.which == "17" && ctrl_down == false){
			ctrl_down = true;
			return false;
		}
		else if(e.which == "16" && shift_down == false){
			shift_down = true;
			return false;
		}
	});
	
	$(document).keyup(function(e){
		if(e.which == "17" && ctrl_down == true){
			ctrl_down = false;
			return false;
		}
		else if(e.which == "16" && shift_down == true){
			shift_down = false;
			return false;
		}
	});
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
			{ title: "访问量"}
		],
		paging:true,
		dom:'lrtip'
	});
	//为表格添加部分元数据
	for(var i = 0; i < table.rows().data().length; i++){
		var row = table.row(i).node();
		$(row).attr("rid", fucking_id[i]);
		$(row).attr("comment", comments[i]);
		$(row).attr("item_name", dataSet[i][1]);
	}
	//表格中项目选中事件
	var last_item, shift_start, shift_end;	
	$('#item_table tbody').on('click', 'tr', function(){
		if(ctrl_down == false && shift_down == false){
			if($(this).hasClass('selected')){
				$(this).removeClass('selected');
			}
			else{
				table.$('tr.selected').removeClass('selected');
				$(this).addClass('selected');
			}
			last_item = table.row(this).index();
		}
		else if(ctrl_down == true){
			$(this).toggleClass('selected');
			last_item = table.row(this).index();
		}
		else if(shift_down == true){
			if(table.rows('.selected').data().length == 0)
				last_item = 0;

			table.rows('.selected').nodes().to$().removeClass("selected");

			current_item = table.row(this).index();
			
			if(current_item >= last_item){
				shift_start = last_item;
				shift_end = current_item;
			}
			else{
				shift_start = current_item;
				shift_end = last_item;
			}
			for(var i = shift_start; i <= shift_end; i++){
				tmp = table.row(i).node();
				$(tmp).addClass('selected');
			}
		}
		$("#right_panel").css("display", "none");
		check_status(table);
	});
	
	$('#item_table tbody').on("contextmenu", "tr", function(e){
		if($(this).hasClass('selected')){
			$(this).removeClass('selected');
			$("#right_panel").css("display", "none");
		}
		else{
			table.$('tr.selected').removeClass('selected');
			$(this).addClass('selected');
			$("#right_panel").css("top", e.pageY);
			$("#right_panel").css("left", e.pageX);
			$("#right_panel").css("display", "block");
		}		
		check_status(table);
	    return false;
	});

	$('#r_inspect').click(function(){
		$( "#inspect_button" ).trigger( "click" );
		$("#right_panel").css("display", "none");
	});
	$('#r_delete').click(function(){
		$( "#delete_button" ).trigger( "click" );
		$("#right_panel").css("display", "none");
	});
	$('#r_update').click(function(){
		$( "#update_button" ).trigger( "click" );
		$("#right_panel").css("display", "none");
	});
	
	$('#search_text_field').keyup(function(){
	      table.search($(this).val()).draw() ;
	})
	//上传事件
	$('#upload_button').click(function(){
		$("#mask").fadeTo(500, 0.6);
		$('#upload_dialog').fadeIn(500);
		var oBox = document.getElementById("upload_dialog");
		var oBar = document.getElementById("mybar5");
		startDrag(oBar, oBox);
		$(".result_bar").remove();
	});
	
	$('#start_upload_button').click(function(){
		$('#file_upload').uploadify('upload','*');
	});
	
	$('#close_upload').click(function(){
		$("#upload_dialog").fadeOut(500);
		$("#mask").fadeOut(500);
	});	
	
	$("#file_upload").uploadify({
		'buttonClass':'button',
		'auto':false,
		'queueID':'some_file_queue',
		"height":30,
		"swf":"swf/uploadify.swf",
		"uploader": 'upload_item.do',
		'width': 140,
		'buttonText':'选择文件资源',
		'onSelect' : function(file){
			var reg = "([\\s\\S]+)\\.([a-zA-Z0-9]+)";
			var group = file.name.match(reg);
			if(table.rows('*[item_name=' + group[1] + ']').nodes().length > 0){
				$("#file_upload").uploadify('cancel', file.id);
				alert(file.name + ":该资源名重复，请重命名文件名后再上传！");
				return;
			}		
        },
		'onUploadSuccess' : function(file, data, response) {   
			if(data == "error"){
        		$("#file_upload_result").append("<div class='result_bar'><img src='img/ui/Cancel.png' height=80% style='vertical-align:middle'></img>" + file.name + ' 服务端处理失败</div>');
				return;
			}			
			var dd = eval('(' + data + ')');			
			$("#file_upload_result").append("<div class='result_bar'><img src='img/ui/Checked.png' height=80% style='vertical-align:middle'></img>" + file.name + ' 上传成功</div>');
			var tmp_name = file.name.match("([\\s\\S]+)\\.([a-zA-Z0-9]+)")[1];
			var new_row = table.row.add([table.rows().data().length + 1,
				               tmp_name,
				               dd[1],
				               dd[2],
				               dd[3],
				               dd[4],
				               (dd[5]/1048576.0).toFixed(3),
				               dd[6]
								]).draw().node();
			$(new_row).attr("rid", dd[0]);
			$(new_row).attr("comment", $("#create_item_comment_id").val());
			$(new_row).attr("item_name", tmp_name);
			return;			
        },
		'onQueueComplete':function(queueData){
			alert("批量上传完成");
			return;
		},
		'onUploadError' : function(file, errorCode, errorMsg, errorString) {
        	$("#file_upload_result").append("<div class='result_bar'><img src='img/ui/Cancel.png' height=80% style='vertical-align:middle'></img>" + file.name + ' ' + errorString + '</div>');
        }
	});	
	
	//资源替换事件	
	$("#file_replace").uploadify({
		'buttonClass':'button',
		"height":30,
		'queueID':'rep_queue',
		"swf":"swf/uploadify.swf",
		"uploader": 'replace_item.do',
		'width': 160,
		'buttonText':'替换普通类型文件',
		'onUploadStart' : function(file){			
			if(typeExist(table.row('.selected').data()[5], link_types) != -1){
				$("#file_replace").uploadify('cancel');
				alert("您只能替换非链接类型的资源！");
				return;
			}
			
			var reg = "([\\s\\S]+)\\.([a-zA-Z0-9]+)";
			var group = file.name.match(reg);

			var check = table.rows('*[item_name=' + group[1] + ']').nodes();
			if(check.length > 0 && check.to$().eq(0).hasClass('selected') == false){
				$("#file_replace").uploadify('cancel', file.id);
				alert(file.name + ":该资源名重复，请重命名文件名后再上传！");
				return;
			}
			/*
			var reg = "([\\s\\S]+)\\.([a-zA-Z0-9]+)";
			var file_type = file.name.match(reg)[2];
			if(file_type != table.row('.selected').data()[5]){
				$("#file_replace").uploadify('cancel');
				alert("请确认您所替换的文件是同一类型！");
				return;
			}
			*/
            $("#file_replace").uploadify("settings", "formData", 
            		{ 
            		  'item_id': table.rows('.selected').nodes().to$().attr("rid")
            		});
        },
        'onUploadSuccess' : function(file, data, response) {       				
				if(data == "error"){
					alert('替换失败');
					return;
				}
				alert("文件上传更新成功");
				var json = eval('(' + data + ')');
				table.row('.selected').data([table.row('.selected').data()[0], 
						   json[1], 
						   table.row('.selected').data()[2], 
						   json[0],
						   table.row('.selected').data()[4], 
						   json[2],        		          
						   (json[3]/ 1048576.0).toFixed(3),
						   table.row('.selected').data()[7]
				]).draw();
				$(table.row(".selected").node()).attr("item_name", json[1]);			
		},
	});
	$("#file_replace").css("display","none");
	$("#file_replace").html('<img src="img/ui/Refresh.png" width=25 height=25 style="vertical-align:bottom;"></img>' + $("#file_replace").html());
	$("#file_replace").css("width","190");
	
	//资源介绍更新事件
	$('#update_button').click(function(){	
		$("#mask").fadeTo(500, 0.6);
		$('#update_dialog').fadeIn(500);
	
		$('#update_item_name_id').val(table.row('.selected').data()[1]);
		$('#update_item_comment_id').val($(table.row('.selected').node()).attr("comment"));
		var oBox = document.getElementById("update_dialog");
		var oBar = document.getElementById("mybar1");
		startDrag(oBar, oBox);
	});
	
	$('#confirm_update').click(function(){	
		if($('#update_item_name_id').val() == ""){
			alert("资源名不能为空！");
			return;
		}
		
		var check = table.rows('*[item_name=' +  $('#update_item_name_id').val() + ']').nodes();
		if(check.length > 0 && check.to$().eq(0).hasClass('selected') == false){
			alert("资源名重复，请确认后再修改！");
			return;

		}
		//ajax提交创建
		var d = {"itemName":$('#update_item_name_id').val(), 
				"itemComment":$('#update_item_comment_id').val().replace(/[\r\n]/g,""),
				"itemId": $(table.row('.selected').node()).attr("rid")
				};
		
		$.ajax({
			url:"UpdateItem.do",
			async:false,
			type:'post',
			data:d,
			dataType:'json',
			success:function(data){	
				alert("资源介绍更新成功！");
				table.row('.selected').data([table.row('.selected').data()[0], 
				                   $('#update_item_name_id').val(), 
				                   table.row('.selected').data()[2],
				                   data[0],
				                   table.row('.selected').data()[4],
				                   table.row('.selected').data()[5],
				                   table.row('.selected').data()[6],
				                   table.row('.selected').data()[7]
				                   ]
				).draw();	
				$(table.row('.selected').node()).attr('comment', $('#update_item_comment_id').val().replace(/[\r\n]/g,""));
				$(table.row('.selected').node()).attr('item_name', $('#update_item_name_id').val());
			}			
		});
						
		//销毁对话框
		$("#update_dialog").fadeOut(500);
		$("#mask").fadeOut(500);
	});
	
	$('#cancel_update').click(function(){
		$("#update_dialog").fadeOut(500);
		$("#mask").fadeOut(500);
	});		
	
	//资源删除事件
	$('.delete').click(function(){
		var del_list = ""; 
		table.rows('tr.selected').every(function (rowIdx, tableLoop, rowLoop){
			del_list += this.data()[1] + "\n " ;
		});
		if(del_list==""){
			alert("请选择要删除的项目");
			return;
		}
		var batch_del_confirm = confirm("您真要的要批量删除下列文件吗:\n" + del_list);
		if(batch_del_confirm == true){
			var del_info = "";
			var cannot_del = [];
			table.rows('tr.selected').every(function (rowIdx, tableLoop, rowLoop){
				
				var d = {"item_id": $(this.node()).attr("rid")};
				var temp_this = this;
				
				$.ajax({
					url:"DeleteItem.do",
					async:false,
					type:'post',
					data:d,
					success:function(result_data){
						if(result_data == "1"){
							del_info += temp_this.data()[1] + "删除成功\n";
						}
						else{
							del_info += temp_this.data()[1] + "删除失败\n";
							cannot_del.push(temp_this);
						}
					}			
				});
						
			});
			for(var j = 0; j < cannot_del.length; j++){
				cannot_del.removeClass('selected');
			}
			table.rows('tr.selected').remove().draw();
			alert("删除结果如下:\n" + del_info);
			resetNo(table);
		}
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
	
	//上传链接资源事件
	$('#upload_link_button').click(function(){
		$("#mask").fadeTo(500, 0.6);
		$('#upload_link_dialog').fadeIn(500);
		
		$('#upload_link_name_id').val("");
		$('#upload_link_comment_id').val("");
		$('#upload_link_url_id').val("");
		
		var oBox = document.getElementById("upload_link_dialog");
		var oBar = document.getElementById("mybar3");
		startDrag(oBar, oBox);
	});
	
	$('#cancel_upload').click(function(){
		$("#upload_link_dialog").fadeOut(500);
		$("#mask").fadeOut(500);
	});	
	
	$('#confirm_upload').click(function(){		
		if($('#upload_link_name_id').val() == "" || $('#upload_link_url_id').val() == ""){
			alert("链接名和链接地址不可为空!");
			return;
		}

		if(table.rows('*[item_name=' + $('#upload_link_name_id').val() + ']').nodes().length > 0){
			alert("资源名重复，请重命名链接名后再上传！");
			return;
		}	
		
		//ajax提交创建
		var d = {"itemName":$('#upload_link_name_id').val(), 
				"itemComment":$('#upload_link_comment_id').val().replace(/[\r\n]/g,""),
				"resourceUrl":$('#upload_link_url_id').val(),
				"objectType":$(".selector").val()
		};
		$.ajax({
			url:"UploadLink.do",
			async:false,
			type:'post',
			data:d,
			dataType:'json',
			success:function(data){
				//提示创建结果
				if(data == "error"){
					alert("链接创建失败！");
					return;
				}

				alert("链接创建成功！");
				var new_row = table.row.add([table.rows().data().length + 1, 
				               $('#upload_link_name_id').val(),
			               		data[1],
			               		data[2],
			               		data[3],
			               		data[4],
			               		data[5],
			               		data[6]
						]).draw().node();
				$(new_row).attr("rid", data[0]);
				$(new_row).attr("comment", $("#upload_link_comment_id").val());
				$(new_row).attr("item_name",  $('#upload_link_name_id').val());
			}			
		});
		
		//无论是否成功，都要销毁对话框
		$("#upload_link_dialog").fadeOut(500);
		$("#mask").fadeOut(500);
	});	
	
	//批量添加链接事件
	$('#upload_multi_link_button').click(function(){
		$("#mask").fadeTo(500, 0.6);
		$('#upload_multi_link_dialog').fadeIn(500);
		var oBox = document.getElementById("upload_multi_link_dialog");
		var oBar = document.getElementById("mybar6");
		$('#multi_link_url_id').val("");
		startDrag(oBar, oBox);
		$(".result_bar").remove();
	});
	
	$('#cancel_multi_link_upload').click(function(){
		$("#upload_multi_link_dialog").fadeOut(500);
		$("#mask").fadeOut(500);
	});	
	
	$('#confirm_multi_link_upload').click(function(){		
		var link_lines = $('#multi_link_url_id').val().split(/[\r\n]+/g);
		var link_items;
		
		for(var i = 0; i < link_lines.length; i++){
			link_items = link_lines[i].trim().split(/\s+/);
			
			if(link_items.length != 3){
				$("#link_upload_result").append("<div class='result_bar'><img src='img/ui/Cancel.png' height=80% style='vertical-align:middle'></img>" + link_items[0]+ ' 格式错误' + '</div>');
				continue;
			}
			
			if(table.rows('*[item_name=' + link_items[0] + ']').nodes().length > 0){
				$("#link_upload_result").append("<div class='result_bar'><img src='img/ui/Cancel.png' height=80% style='vertical-align:middle'></img>" + link_items[0]+ ' 名称重复' + '</div>');
				continue;
			}	
			
			//ajax提交创建
			var d = {"itemName":link_items[0],
					"itemComment":"",
					"resourceUrl":link_items[1],
					"objectType":link_items[2]
			};
			$.ajax({
				url:"UploadLink.do",
				async:false,
				type:'post',
				data:d,
				dataType:'json',
				success:function(data){
					//提示创建结果
					if(data == "error"){
						$("#link_upload_result").append("<div class='result_bar'><img src='img/ui/Cancel.png' height=80% style='vertical-align:middle'></img>" + link_items[0]+ ' 创建失败' + '</div>');
						return;
					}

					$("#link_upload_result").append("<div class='result_bar'><img src='img/ui/Checked.png' height=80% style='vertical-align:middle'></img>" + link_items[0] + ' 添加成功</div>');
					var new_row = table.row.add([table.rows().data().length + 1, 
					                link_items[0],
				               		data[1],
				               		data[2],
				               		data[3],
				               		data[4],
				               		data[5],
				               		data[6]
							]).draw().node();
					$(new_row).attr("rid", data[0]);
					$(new_row).attr("comment", "");
					$(new_row).attr("item_name",  link_items[0]);
				}			
			});
		}
		
	});	
	//替换链接事件
	$('#replace_link_button').click(function(){		
		if(typeExist(table.row('.selected').data()[5], link_types) == -1){
			alert("请先选中链接资源");
			return;
		}

		$("#mask").fadeTo(500, 0.6);
		$('#replace_link_dialog').fadeIn(500);
		
		$('#replace_link_url_id').val("");
		$('#replace_link_comment_id').val($(table.row('.selected').node()).attr("comment"));
		$('#replace_link_name_id').val(table.row('.selected').data()[1]);
		$(table.row('.selected').node()).attr("comment", $("#upload_link_comment_id").val());

		var oBox = document.getElementById("replace_link_dialog");
		var oBar = document.getElementById("mybar4");
		startDrag(oBar, oBox);
	});
	
	$('#cancel_replace').click(function(){
		$("#replace_link_dialog").fadeOut(500);
		$("#mask").fadeOut(500);
	});	
	
	$('#confirm_replace').click(function(){		
		if($('#replace_link_url_id').val() == ""){
			alert("链接地址不可为空!");
			return;
		}
	
		//ajax提交创建
		var d = {
				"itemId":$(table.row('.selected').node()).attr("rid"),
				"resourceUrl":$('#replace_link_url_id').val()
				};
		$.ajax({
			url:"ReplaceLink.do",
			async:false,
			type:'post',
			data:d,
			dataType:'json',
			success:function(data){	
				//提示创建结果
				alert("链接替换成功");
				table.row('.selected').data([table.row('.selected').data()[0], 
				                   table.row('.selected').data()[1],
				                   table.row('.selected').data()[2],
				                   data[0],
				                   table.row('.selected').data()[4],
				                   table.row('.selected').data()[5],
				                   table.row('.selected').data()[6],
				                   table.row('.selected').data()[7]
				                   ]
				).draw();
			}			
		});
		
		//无论是否成功，都要销毁对话框
		$("#replace_link_dialog").fadeOut(500);
		$("#mask").fadeOut(500);
	});	

	//全选和取消全选事件
	$('#select_all_button').click(function(){
/*		$("tr[role=row][class=odd],tr[role=row][class=even]").each(function(){
			$(this).attr("class", $(this).attr("class") + " selected");
		});*/
		table.rows().nodes().to$().addClass('selected');
		check_status(table);
	/*	$("tr[role=row][class='odd selected'],tr[role=row][class='even selected']").each(function(){
			//console.log($(this).attr("class"));
			$(this).attr("class", $(this).attr("class").split(" ")[0]);
		});	*/
	});
	$(".cancel_all_button").click(function(){
		table.rows('.selected').nodes().to$().removeClass('selected');
		$("#right_panel").css("display", "none");
		check_status(table);
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