<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<title>项目列表</title>
<script type="text/javascript" src="js/jquery-1.12.0.min.js"> </script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/zxx.drag.1.0-min.js" ></script>
<link rel="stylesheet" type="text/css" href="css/button.css"/>
<link rel="stylesheet" type="text/css" href="css/jquery.dataTables.min.css"/>
<link rel="stylesheet" type="text/css" href="css/page.css"/>

<style type="text/css">
#table {  
	font-size:14px;
}

#project_table th{
	font-size: 14px;
}
#project_table td{
	font-size:12px;
}

.check_input_table{
	width:650px;
	height:480px;
	margin:10px auto 0;
	text-align:center;
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

<!-- 右键面板 -->
<div id="right_panel">
	<div id="r_create" class="r_item">创建项目</div>
	<div id="r_check" class="r_item">预览项目</div>
	<div id="r_inspect" class="r_item">进入项目</div>
	<div id="r_update" class="r_item">更新项目</div>
	<div id="r_delete" class="r_item">删除项目</div>
	<div id="r_batch_down" class="r_item">批量导码</div>
</div>

<!-- 创建项目对话框 -->
<div id="create_dialog" class="pop_dialog" style="height:600px">
	<div id="mybar1" class="dialog_bar"></div>
	<div class="input_table" style = "height:480px;">
		<table border="0">
			<tr>
				<td>
					项目名
				</td>
				<td>	
					<div class="input_box1"><input type="text" name="create_project_name" id="create_pjname_id" class="ti" placeholder="请输入项目名"/></div>
				</td>
			</tr>
			<tr>
				<td>
					项目介绍
				</td>
				<td>
					<div class="input_box2"><textarea name="create_project_comment" id="create_pjcomm_id" class="ta" placeholder="请输入项目简介"></textarea></div>
				</td>
			</tr>
			<tr>
				<td>
					策划编辑
				</td>
				<td>
					<div class="input_box1"><input type="text" name="create_editor_name" id="create_editor_id" class="ti"/></div> 
				</td>
			</tr>
			<tr>
				<td>
					选题号
				</td>
				<td>
					<div class="input_box1"><input type="text" name="create_candidate_num_name" id="create_candidate_num_id" class="ti"/></div> 
				</td>
			</tr>
			<tr>
				<td>
					版次
				</td>
				<td>
					<div class="input_box1"><input type="text" name="create_edition_name" id="create_edition_id" class="ti"/></div> 
				</td>
			</tr>
			<tr>
				<td>
					作者
				</td>
				<td>
					<div class="input_box1"><input type="text" name="create_writer_name" id="create_writer_id" class="ti"/></div> 
				</td>
			</tr>
			<tr>
				<td>
					印刷册数
				</td>
				<td>
					<div class="input_box1"><input type="text" name="create_pub_num" id="create_pub_num_id" class="ti" placeholder="请输入印刷册数"/></div>
				</td>	
			</tr>
			<tr>
				<td>
					用户每日访问限制
				</td>
				<td>
					<div class="input_box1"><input type="text" name="create_access_threshold" id="create_access_threshold_id" class="ti" placeholder="请输入每日访问限制"/></div>
				</td>	
			</tr>
		</table>
	</div>
	<div class="buttons">
		<div id="confirm_create" class="button">确定</div>&nbsp;&nbsp;&nbsp;&nbsp; 
		<div id="cancel_create" class="button">取消</div>
	</div>
</div>
<!-- 更新项目对话框 -->
<div id="update_dialog" class="pop_dialog" style="height:600px">
	<div id="mybar2" class="dialog_bar"></div>
	<div class="input_table" style="height:480px">
		<table border="0">
			<tr>
				<td>
					项目名
				</td>
				<td>
					<div class="input_box1"><input type="text" name="update_project_name" id="update_pjname_id" class="ti"/></div> 
				</td>
			</tr>
			<tr>
				<td>
					项目介绍
				</td>
				<td>	
					<div class="input_box2"><textarea name="update_project_comment" id="update_pjcomm_id" class="ta"></textarea></div>
				</td>
			</tr>
			<tr>
				<td>
					策划编辑
				</td>
				<td>
					<div class="input_box1"><input type="text" name="update_editor_name" id="update_editor_id" class="ti"/></div> 
				</td>
			</tr>
			<tr>
				<td>
					选题号
				</td>
				<td>
					<div class="input_box1"><input type="text" name="update_candidate_num_name" id="update_candidate_num_id" class="ti"/></div> 
				</td>
			</tr>
			<tr>
				<td>
					版次
				</td>
				<td>
					<div class="input_box1"><input type="text" name="update_edition_name" id="update_edition_id" class="ti"/></div> 
				</td>
			</tr>
			<tr>
				<td>
					作者
				</td>
				<td>
					<div class="input_box1"><input type="text" name="update_writer_name" id="update_writer_id" class="ti"/></div> 
				</td>
			</tr>
			<tr>
				<td>
					印刷册数
				</td>
				<td>
					<div class="input_box1"><input type="text" name="update_pub_num" id="update_pub_num_id" class="ti"/></div> 
				</td>
			</tr>
			<tr>
				<td>
					每日访问限制
				</td>
				<td>
					<div class="input_box1"><input type="text" name="update_access_threshold" id="update_access_threshold_id" class="ti"/></div> 
				</td>
			</tr>
		</table>
	</div>	
	<div class="buttons"> 
		<div id="confirm_update" class="button">确定</div>&nbsp;&nbsp;&nbsp;&nbsp; 
		<div id="cancel_update" class="button">取消</div> 
	</div>
</div>
<!-- 预览项目对话框 -->
<div id="check_dialog" class="pop_dialog" style="width:700px; height:600px">
	<div id="mybar3" class="dialog_bar" style="width:700px">
	</div>
	<div class="check_input_table">
		<table id="fucking_table">
			<tr>
				<td>
					项目名
				</td>
				<td>
					<div class="input_box1"><p id="check_project_name_id"> </p></div>
				</td>
				<td >
					项目介绍
				</td>
				<td>	
					<div class="input_box2"><p id="check_project_comment_id" > </p></div>
				</td>
			</tr>
			<tr>
				<td rowspan="2">
					项目下载
				</td>
				<td>	
					<div class="input_box1"  style="font-size:10px"> <a id="check_project_url_id"></a> </div>
				</td>
				<td rowspan="2">
					项目在线预览
				</td>
				<td>	
					<div class="input_box1"  style="font-size:10px"> <a id="check_project_display_url_id"></a> </div>
				</td>
			</tr>
			<tr >
				<td>
					<img id="qr_down" width="160"/>
				</td>
				<td>
					<img id="qr_show" width="160"/>
				</td>
			</tr>
			<tr>
				<td>
					策划编辑
				</td>
				<td>
					<div class="input_box1"><p id="check_editor_id"> </p></div>
				</td>
				<td>
					选题号
				</td>
				<td>	
					<div class="input_box1"><p id="check_candidate_num_id" > </p></div>
				</td>
			</tr>
			<tr>
				<td>
					版次
				</td>
				<td>
					<div class="input_box1"><p id="check_edition_id" > </p></div>
				</td>
				<td>
					作者
				</td>
				<td>
					<div class="input_box1"><p id="check_writer_id" > </p></div>
				</td>
			</tr>
		</table>
	</div>	
	<div class = "buttons">		
		<div id="cancel_check" class="button">关闭</div>
	</div>
</div>
<!-- 主页面 -->
<div id="main_page">
	<!-- 上边栏 -->
	<div id="upper_nav_bar">
			<div id="cmp_brand">
				<img src="img/cmp.jpg" width=70 height=70></img>
			</div>
			<div id="search_box" style="float:left;display:block;">
				<img src="img/ui/Search.png" width=30 height=30 style="vertical-align:middle;"></img>
				<input type="text" id="search_text_field" style="vertical-align:middle;">
				<div id="search" class="button" style="vertical-align:middle;">搜索</div>
				<div id="reset" class="button" style="vertical-align:middle;">重置</div>
			</div>
			<div id="create_button" class="operation button" style="float:right;display:block;">
				<img src="img/ui/Plus.png" width=25 height=25 style="vertical-align:middle;"></img>
				创建新项目
			</div>
			
			<div id="delete_button" class="operation" style="float:right;display:none;">
				<img src="img/ui/Delete.png" width=25 height=25 style="vertical-align:middle;"></img>
				删除项目
			</div>
			<div id="update_button" class="operation" style="float:right;display:none;">
				<img src="img/ui/Edit.png" width=25 height=25 style="vertical-align:middle;">
				更新项目
			</div>
			<div id="enter_button" class="operation" style="float:right;display:none;">
				<img src="img/ui/Open.png" width=25 height=25 style="vertical-align:middle;"></img>
				进入项目
			</div>
			<div id="inspect_button" class="operation" style="float:right;display:none;">
				<img src="img/ui/About.png" width=25 height=25 style="vertical-align:middle;"></img>
				预览项目
			</div>
			<div id="batch_download_qr_button" class="operation" style="float:right;display:none;">
				<img src="img/ui/Briefcase.png" width=25 height=25 style="vertical-align:middle;"></img>
				批量导码
			</div>
			<div id="query_check_button" class="operation" style="float:right;display:none;">
				<img src="img/ui/HandCursor.png" width=25 height=25 style="vertical-align:middle;"></img>
				提交审核
			</div>
			<div id="uncheck_button" class="operation" style="float:right;display:none;">
				<img src="img/ui/Restart.png" width=25 height=25 style="vertical-align:middle;"></img>
				退回修改
			</div>
			<div id="preview_project_button" class="operation" style="float:right;display:none;">
				<img src="img/ui/Open.png" width=25 height=25 style="vertical-align:middle;"></img>
				进入项目
			</div>
			<div id="check_log_button" class="operation" style="float:right;display:none;">
				<img src="img/ui/News.png" width=25 height=25 style="vertical-align:middle;"></img>
				查看日志
			</div>
			<div id="modify_button" class="operation" style="float:right;display:none;">
				<img src="img/ui/Support.png" width=25 height=25 style="vertical-align:middle;"></img>
				申请修改
			</div>
			<div id="pv_button" class="operation" style="display:none; float:right" >
				<img src="img/ui/Document.png" width=25 height=25 style="vertical-align:middle;"></img>
				访问统计
			</div>
	</div>
	<!-- 左边栏 -->
	<div id="left_nav_bar">
		<div class="left_sub_bar">
			<img src="img/ui/Bookmark.png" width=25 height=25 style="vertical-align:middle;"></img>
			欢迎！<%= (String)request.getSession(false).getAttribute("username") %>
		</div>

		<div class="left_sub_bar dynamic">
			<div id="unchecked" style="color:red;">
				<img src="img/ui/Bookmark.png" width=25 height=25 style="vertical-align:middle;"></img>
				未审核
			</div>
		</div>
		<div class="left_sub_bar dynamic">
			<div id="checking">
				<img src="img/ui/Bookmark.png" width=25 height=25 style="vertical-align:middle;"></img>
				已提交审核
			</div>
		</div>
		<div class="left_sub_bar dynamic">
			<div id="passed">
				<img src="img/ui/Bookmark.png" width=25 height=25 style="vertical-align:middle;"></img>
				已通过审核
			</div>
		</div>
		<div class="left_sub_bar dynamic">
			<div id="unpassed">
				<img src="img/ui/Bookmark.png" width=25 height=25 style="vertical-align:middle;"></img>
				未通过审核
			</div>
		</div>		
		<div class="left_sub_bar dynamic">
			<div id="modified">
				<img src="img/ui/Bookmark.png" width=25 height=25 style="vertical-align:middle;"></img>
				已提交修改
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
		<div id='table'>
			<table id="project_table" class="display" width="100%">
			</table>
		</div>
		<div id="paging" style="margin:10px auto; width:600px">
			<div class="button" id="pre">前一页</div>
			<div id="cp" style="display:inline">1</div>
			<div class="button" id="next">后一页</div>
			&nbsp;&nbsp;<div id="jump" style="display:inline">跳到第<input style="width:30px;border-radius:15px;" id="to_page"></input>页</div>&nbsp;&nbsp;
			<div class="button" id="to">跳转</div>
			共<div id="sum_page_len" style="display:inline"></div>页 <div id="sum_project_len" style="display:inline"></div>项
		</div>
		<div id="log" style="width:100%; height:300px; border-top:solid 1px #B4B4B4; overflow:auto">
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

function updateBar(checkStatusRegion, table){
	if(table.rows(".selected").data().length == 0)
		return;
	
	if(checkStatusRegion == 0){	
		$("#create_button").css("display","none");
		$("#update_button").css("display","block");
		$("#delete_button").css("display", "block");
		$("#enter_button").css("display", "block");
		$("#inspect_button").css("display", "block");
		$("#batch_download_qr_button").css("display", "none");
		$("#query_check_button").css("display", "block");
		$("#uncheck_button").css("display", "none");	
		$("#preview_project_button").css("display", "none");
		$("#check_log_button").css("display", "block");
		$("#modify_button").css("display", "none");
		$("#pv_button").css("display", "none");
	}
	else if(checkStatusRegion == 1 || checkStatusRegion == 4){
		$("#create_button").css("display","none");
		$("#update_button").css("display","none");
		$("#delete_button").css("display", "none");
		$("#enter_button").css("display", "none");
		$("#inspect_button").css("display", "block");
		if(checkStatusRegion == 1)
			$("#batch_download_qr_button").css("display", "block");
		if(checkStatusRegion == 4)
			$("#batch_download_qr_button").css("display", "none");
		$("#query_check_button").css("display", "none");
		$("#uncheck_button").css("display", "none");	
		$("#preview_project_button").css("display", "block");
		$("#check_log_button").css("display", "block");
		$("#modify_button").css("display", "none");
		$("#pv_button").css("display", "none");
	}
	else if(checkStatusRegion == 2){
		$("#create_button").css("display","none");
		$("#update_button").css("display","none");
		$("#delete_button").css("display", "none");
		$("#enter_button").css("display", "none");
		$("#inspect_button").css("display", "block");
		$("#batch_download_qr_button").css("display", "block");
		$("#query_check_button").css("display", "none");
		$("#uncheck_button").css("display", "none");
		$("#preview_project_button").css("display", "block");
		$("#check_log_button").css("display", "block");
		$("#modify_button").css("display", "block");
		$("#pv_button").css("display", "block");
	}	
	else if(checkStatusRegion == 3){
		$("#create_button").css("display","none");
		$("#update_button").css("display","none");
		$("#delete_button").css("display", "none");
		$("#enter_button").css("display", "none");
		$("#inspect_button").css("display", "block");
		$("#batch_download_qr_button").css("display", "none");
		$("#query_check_button").css("display", "none");
		$("#uncheck_button").css("display", "block");	
		$("#preview_project_button").css("display", "block");
		$("#check_log_button").css("display", "block");
		$("#modify_button").css("display", "none");
		$("#pv_button").css("display", "none");
	}	
	$("#log").text("");
	$("#search_box").css("display", "none");
	$('#upper_nav_bar').css("background-color", "#3B93FF");
}

function initBar(checkStatusRegion){
	$("#search_box").css("display", "block");
	$('#upper_nav_bar').css("background-color", "#F5F6F9");
	
	$("#inspect_project_button").css("display","none");
	if(checkStatusRegion == 0){	
		$("#create_button").css("display","block");
	}
	else
		$("#create_button").css("display","none");
	
	$("#update_button").css("display","none");
	$("#delete_button").css("display", "none");
	$("#enter_button").css("display", "none");
	$("#inspect_button").css("display", "none");
	$("#batch_download_qr_button").css("display", "none");
	$("#query_check_button").css("display", "none");
	$("#uncheck_button").css("display", "none");
	$("#preview_project_button").css("display", "none");
	$("#check_log_button").css("display", "none");
	$("#modify_button").css("display", "none");
	$("#paging").css("display", "block");
	$("#pv_button").css("display", "none");
	
	$("#log").text("");
}

function redrawTable(table, myUrl, postData){
	var data_len = 0;
	$.ajax({
		url: myUrl,
		async:false,
		type:'post',
		data:postData,
		dataType:'json',
		success:function(data){
			dataSet = [];
			fucking_id = []
			data_len = data.length;
			var i = 0;
			for(; i< data.length - 1; i++){
				dataSet[i] = new Array();
				dataSet[i][0] = (parseInt(data[i][0]) + 1).toString();
				dataSet[i][1] = data[i][1];
				dataSet[i][2] = data[i][2];
				dataSet[i][3] = data[i][3];
				dataSet[i][4] = data[i][4];
				dataSet[i][5] = data[i][5];
				dataSet[i][6] = data[i][6];
				dataSet[i][7] = data[i][7];
				fucking_id[i] = data[i][8];
			}
			$('#sum_page_len').text(Math.ceil(data[i] / 10));
			$('#sum_project_len').text(data[i]);
		}			
	});	
	if(data_len == 1){
		table.clear().draw();
		return 0;
	}
	
	table.clear().draw();
	table.rows.add(dataSet);
	table.columns.adjust().draw();
	for(var i = 0; i < table.rows().data().length; i++){
		var row = table.row(i).node();
		$(row).attr("rid", fucking_id[i]);
	}
}

$(document).ready(function() {	
	$("#left_nav_bar").height($(window).height() - $('#upper_nav_bar').height());
	$("#content_area").height($(window).height() - $('#upper_nav_bar').height());
	$("#content_area").width($(window).width() - $("#left_nav_bar").width() - 2);
	$("#mask").height($(window).height());
	
	var dataSet = new Array();
	var fucking_id = [];
	var checkStatusRegion = 0;
	var currentPage = 1;
	var actions = ["未审核", "提交审核", "通过审核", "审核未通过", "提交修改"];
	var isSearchMode = false;

	//表格绘制
	var table = $('#project_table').DataTable( {
			data: dataSet,
			columns: [
			    { title: "编号" },
			    { title: "项目名称" , width:"200px"},
			    { title: "项目介绍" , width:"150px"},
			    { title: "创建时间"},
			    { title: "修改时间"},
			    { title: "所有者"},
			    { title: "印刷册数"},
			    { title: "单个用户每日访问限制"}
			],
			paging:false,
			dom:'t'
	    });

	redrawTable(table, "GetProjectList.do", {"checkStatus":checkStatusRegion, "page":currentPage, "pageLen":10});
	
	//表格点击事件
	$('#project_table tbody').on('click', 'tr', function(){
		if($(this).hasClass('selected')){
			$(this).removeClass('selected');			
			initBar(checkStatusRegion);		
		}
		else{
			table.$('tr.selected').removeClass('selected');
			$(this).addClass('selected');
			updateBar(checkStatusRegion, table);
		}
		
		$("#right_panel").css("display", "none");
	});
	
	$('#project_table tbody').on("contextmenu", "tr", function(e){
			if(checkStatusRegion != 0)
				return;
			
			if($(this).hasClass('selected')){
				$(this).removeClass('selected');
				initBar(checkStatusRegion);	
				$("#right_panel").css("display", "none");
			}
			else{
				table.$('tr.selected').removeClass('selected');
				$(this).addClass('selected');
				updateBar(checkStatusRegion, table);
				$("#right_panel").css("top", e.pageY);
				$("#right_panel").css("left", e.pageX);
				$("#right_panel").css("display", "block");
			}			
		    return false;
	});
	
	$('#r_create').click(function(){
		$( "#create_button" ).trigger( "click" );
		$("#right_panel").css("display", "none");
	});
	$('#r_check').click(function(){
		$( "#inspect_button" ).trigger( "click" );
		$("#right_panel").css("display", "none");
	});
	$('#r_inspect').click(function(){
		$( "#enter_button" ).trigger( "click" );
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
	$('#r_batch_down').click(function(){
		$( "#batch_download_qr_button" ).trigger( "click" );
		$("#right_panel").css("display", "none");
	});
			
	//创建项目事件
	$('#create_button').click(function(){
		$("#mask").fadeTo(500, 0.6);
		$('#create_dialog').fadeIn(500);
		
		$('#create_pjname_id').val("");
		$('#create_pjcomm_id').val("");
		$('#create_pub_num_id').val("0");
		$('#create_access_threshold_id').val("50");
		$('#create_editor_id').val("");
		$('#create_edition_id').val("1");
		$('#create_writer_id').val("");
		$('#create_candidate_num_id').val("0");
		
		var oBox = document.getElementById("create_dialog");
        var oBar = document.getElementById("mybar1");
        startDrag(oBar, oBox);
	});
	
	//创建响应事件
	$('#confirm_create').click(function(){	
		//表单验证
		if($('#create_pjname_id').val() == ""){
			alert("项目名不可为空");
			return;
		}
		
		var reg = new RegExp("^[0-9]+$");
		if(!reg.test($('#create_pub_num_id').val()) || 
		   !reg.test($('#create_access_threshold_id').val()) ||
		   !reg.test($('#create_edition_id').val()) ||
		   !reg.test($('#create_candidate_num_id').val())
		   ){
			alert("请输入有效数字！");
			return;
		}
		
		//ajax提交创建
		//准备数据
		var d = {"projectName":$('#create_pjname_id').val(), 
				"projectComment":$('#create_pjcomm_id').val().replace(/[\r\n]/g,""),
				"pubNum":$('#create_pub_num_id').val(),
				"accessThreshold":$('#create_access_threshold_id').val(),
				"writer":$('#create_writer_id').val(),
				"editor":$('#create_editor_id').val(),
				"edition":$('#create_edition_id').val(),
				"candidateNum":$('#create_candidate_num_id').val()
				};
		
		$.ajax({
			url:"AddProject.do",
			async:false,
			type:'post',
			data:d,
			dataType:'text',
			success:function(data){
				//提示创建结果
				if(data == "error")
					alert("创建失败！换个名字试试！");
				else{
					alert("项目创建成功！");
					data = eval("(" + data + ")");
					var new_row = table.row.add([table.rows().data().length + 1, 
					                $('#create_pjname_id').val(),
				               		$('#create_pjcomm_id').val(), 
				               		data[1],
				               		data[2],
				               		data[3],
				               		$('#create_pub_num_id').val(),
				               		$('#create_access_threshold_id').val()
									]).draw().node();
					$(new_row).attr("rid", data[0]);
				}		
			}			
		});
		
		//无论是否成功，都要销毁对话框
		$("#create_dialog").fadeOut(500);
		$("#mask").fadeOut(500);
	});	
	
	//点击取消，也销毁对话框
	$('#cancel_create').click(function(){
		$("#create_dialog").fadeOut(500);
		$("#mask").fadeOut(500);
	});	
	//删除事件
	$('#delete_button').click(function(){
			var assure = confirm("您确信要删除么？");
			if (assure == true){
				//ajax提交删除
				var d = {"project_id": $(table.row('.selected').node()).attr("rid")};
				$.ajax({
					url:"DeleteProject.do",
					async:false,
					type:'post',
					dataType:'text',
					data:d,
					success:function(data){
						if(data == "1"){
							alert("删除项目成功！");	
							table.row('.selected').remove().draw();
							resetNo(table);
						}
						else if(data == "2"){
							alert("项目还有资源，不可删除！");
						}
					}			
				});		
			}
	});
	//更新项目事件
	$('#update_button').click(function(){
			$("#mask").fadeTo(500, 0.6);
			$('#update_dialog').fadeIn(500);
		
			$('#update_pjname_id').val(table.row('.selected').data()[1]);
			$('#update_pjcomm_id').val(table.row('.selected').data()[2]);
			$('#update_pub_num_id').val(table.row('.selected').data()[6]);
			$('#update_access_threshold_id').val(table.row('.selected').data()[7]);
			
			$.ajax({
				url:"CheckProject.do",
				data:{"pid":$(table.row('.selected').node()).attr("rid")},
				async:false,
				type:'post',
				dataType:'json',
				success:function(data){
						$('#update_editor_id').val(data[3]);
						$('#update_edition_id').val(data[4]);
						$('#update_writer_id').val(data[2]);
						$('#update_candidate_num_id').val(data[5]);
				}			
			});
			
			var oBox = document.getElementById("update_dialog");
			var oBar = document.getElementById("mybar2");
			startDrag(oBar, oBox);
	});
	
	$('#confirm_update').click(function(){	
		/*
		if($('#update_pjname_id').val() == "" || $('#update_pub_num_id').val() == ""){
			alert("项目名和印刷册数不可为空！");
			return;
		}
		*/
		//表单验证
		if($('#update_pjname_id').val() == ""){
			alert("项目名不可为空");
			return;
		}
		
		var reg = new RegExp("^[0-9]+$");
		if(!reg.test($('#update_pub_num_id').val()) || 
		   !reg.test($('#update_access_threshold_id').val()) ||
		   !reg.test($('#update_edition_id').val()) ||
		   !reg.test($('#update_candidate_num_id').val())
		   ){
			alert("请输入有效数字！");
			return;
		}
		
		//ajax提交创建
		var d = {"projectName":$('#update_pjname_id').val(), 
				"projectComment":$('#update_pjcomm_id').val().replace(/[\r\n]/g,""),
				"projectId": $(table.row('.selected').node()).attr("rid"),
				"pubNum":$('#update_pub_num_id').val(),
				"accessThreshold":$('#update_access_threshold_id').val(),
				"writer":$('#update_writer_id').val(),
				"editor":$('#update_editor_id').val(),
				"edition":$('#update_edition_id').val(),
				"candidateNum":$('#update_candidate_num_id').val()
				};
		$.ajax({
			url:"UpdateProject.do",
			async:false,
			type:'post',
			data:d,
			dataType:'text',
			success:function(data){	
				if(data == "2"){
					alert("项目名重复，请修改！");
				}
				else{
					alert("项目更新成功！");
					table.row('.selected').data([table.row('.selected').data()[0], 
					                   $('#update_pjname_id').val(), 
					                   $('#update_pjcomm_id').val(), 
					                   table.row('.selected').data()[3],
					                   data,
					                   table.row('.selected').data()[5],
					                   $('#update_pub_num_id').val(),
					                   $('#update_access_threshold_id').val()
					                   ]
					).draw();								
				}
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
	
	//进入项目进行编辑事件
	$('#enter_button').click(function(){
		var url = "item_page.do";
		var form = $('<form action="' + url + '" method="post"'+ 'type="hidden"' + '>' +
		  '<input type="text" name="project_id" value="' + $(table.row('.selected').node()).attr("rid") + '" />' +
		  '<input type="text" name="project_name" value="' + table.row('.selected').data()[1] + '" />' +
		  '</form>');
		$('body').append(form);
		form.submit();		
	});
	
	//进入项目预览事件
	$('#preview_project_button').click(function(){
		var url = "view_item.do";
		var form = $('<form action="' + url + '" method="post"'+ 'type="hidden"' + '>' +
		  '<input type="text" name="project_id" value="' + $(table.row('.selected').node()).attr("rid") + '" />' +
		  '<input type="text" name="project_name" value="' + table.row('.selected').data()[1] + '" />' +
		  '</form>');
		$('body').append(form);
		form.submit();			
	});
	
	//预览项目事件
	$('#inspect_button').click(function(){	
		$("#mask").fadeTo(500, 0.6);
		$('#check_dialog').fadeIn(500);
	
		$('#check_project_name_id').text( table.row('.selected').data()[1]);
		$('#check_project_comment_id').text( table.row('.selected').data()[2]);
		
		var oBox = document.getElementById("check_dialog");
           		var oBar = document.getElementById("mybar3");
           		startDrag(oBar, oBox);

		$.ajax({
			url:"CheckProject.do",
			data:{"pid":$(table.row('.selected').node()).attr("rid")},
			async:false,
			type:'post',
			dataType:'json',
			success:function(data){
					$("#qr_show").attr("src", data[0]);
					$("#qr_down").attr("src", data[1]);
					$('#check_project_url_id').html("http://qr.cmpedu.com/CmpBookResource/<br/>down_project.do?pid=" + $(table.row('.selected').node()).attr("rid"));
					$('#check_project_display_url_id').html("http://qr.cmpedu.com/CmpBookResource/<br/>show_project.do?pid=" + $(table.row('.selected').node()).attr("rid"));
					$('#check_editor_id').text(data[3]);
					$('#check_edition_id').text(data[4]);
					$('#check_writer_id').text(data[2]);
					$('#check_candidate_num_id').text(data[5]);
			}			
		});
	});
	
	$('#cancel_check').click(function(){
		$("#check_dialog").fadeOut(500);
		$("#mask").fadeOut(500);
	});
	
	//批量导出项目事件
	$('#batch_download_qr_button').click(function(){
		var url = "QRDownloadZip.do";
		var form = $('<form action="' + url + '" method="post"'+ 'type="hidden"' + '>' +
		  '<input type="text" name="project_id" value="' + $(table.row('.selected').node()).attr("rid") + '" />' +
		  '</form>');
		$('body').append(form);
		form.submit();	
	});	
	
	//进入未审核界面
	$('#unchecked').click(function(){
		checkStatusRegion = 0;
		
		$(this).css("color", "red");
		$('#checking').css("color", "black");
		$('#passed').css("color", "black");
		$('#unpassed').css("color", "black");
		$('#modified').css("color", "black");
		
		initBar(checkStatusRegion);	
		isSearchMode = false;
			
		currentPage = 1;
		$('#cp').text(currentPage);
		redrawTable(table, "GetProjectList.do", {"checkStatus":checkStatusRegion, "page":currentPage, "pageLen":10});
	});	
	
	//进入正在审核界面
	$('#checking').click(function(){
		checkStatusRegion = 1;
		
		$(this).css("color", "red");
		$('#unchecked').css("color", "black");
		$('#passed').css("color", "black");
		$('#unpassed').css("color", "black");
		$('#modified').css("color", "black");
		
		initBar(checkStatusRegion);	
		isSearchMode = false;
		
		currentPage = 1;
		$('#cp').text(currentPage);
		redrawTable(table, "GetProjectList.do", {"checkStatus":checkStatusRegion, "page":currentPage, "pageLen":10});
	});	
	
	//进入已通过审核界面
	$('#passed').click(function(){
		checkStatusRegion = 2;
		
		$(this).css("color", "red");
		$('#checking').css("color", "black");
		$('#unchecked').css("color", "black");
		$('#unpassed').css("color", "black");
		$('#modified').css("color", "black");
		
		initBar(checkStatusRegion);	
		isSearchMode = false;
				
		currentPage = 1;
		$('#cp').text(currentPage);
		redrawTable(table, "GetProjectList.do", {"checkStatus":checkStatusRegion, "page":currentPage, "pageLen":10});
	});	
	
	//进入未通过界面
	$('#unpassed').click(function(){
		checkStatusRegion = 3;
		
		$(this).css("color", "red");
		$('#checking').css("color", "black");
		$('#passed').css("color", "black");
		$('#unchecked').css("color", "black");
		$('#modified').css("color", "black");
		
		initBar(checkStatusRegion);	
		isSearchMode = false;
				
		currentPage = 1;
		$('#cp').text(currentPage);
		redrawTable(table, "GetProjectList.do", {"checkStatus":checkStatusRegion, "page":currentPage, "pageLen":10});
	});	
	
	//进入提交修改
	$('#modified').click(function(){
		checkStatusRegion = 4;
		
		$(this).css("color", "red");
		$('#checking').css("color", "black");
		$('#passed').css("color", "black");
		$('#unchecked').css("color", "black");
		$('#unpassed').css("color", "black");
		
		initBar(checkStatusRegion);	
		isSearchMode = false;
				
		currentPage = 1;
		$('#cp').text(currentPage);
		redrawTable(table, "GetProjectList.do", {"checkStatus":checkStatusRegion, "page":currentPage, "pageLen":10});
	});
	
	//响应提交审核事件
	$('#query_check_button').click(function(){
		var assure = confirm("您确信要提交审核么？提交后将无法更改！若要更改，请联系管理员");
		if (assure == false)
			return;
		
		var d = {
				"checkStatus":"1",
				"projectId":$(table.row('.selected').node()).attr("rid")
		};
		$.ajax({
			url:"changeCheckStatus.do" ,
			async:false,
			data:d,
			type:'post',
			dataType:'text',
			success:function(data){
				table.row('.selected').remove().draw();
				resetNo(table);
			}			
		});		
	});	
	
	//响应转为待审核状态事件
	$('#uncheck_button').click(function(){
		var assure = confirm("您确信将该资源退回待审核状态么？");
		if (assure == false)
			return;
		
		var d = {
				"checkStatus":"0",
				"projectId":$(table.row('.selected').node()).attr("rid")
		};
		$.ajax({
			url:"changeCheckStatus.do" ,
			async:false,
			data:d,
			type:'post',
			dataType:'text',
			success:function(data){
				table.row('.selected').remove().draw();
				resetNo(table);
			}			
		});		
	});	
	
	//响应提交修改事件
	$('#modify_button').click(function(){
		var assure = confirm("您确信对该资源提交修改？提交后将不可撤回，且不提供资源公开访问");
		if (assure == false)
			return;
		
		var d = {
				"checkStatus":"4",
				"projectId":$(table.row('.selected').node()).attr("rid")
		};
		$.ajax({
			url:"changeCheckStatus.do" ,
			async:false,
			data:d,
			type:'post',
			dataType:'text',
			success:function(data){
				table.row('.selected').remove().draw();
				resetNo(table);
			}			
		});		
	});			
	
	//下一页事件
	$('#next').on( 'click', function () {
	   currentPage += 1;
	   if(isSearchMode == false){
		   var ret = redrawTable(table, "GetProjectList.do", {"checkStatus":checkStatusRegion, "page":currentPage, "pageLen":10});
		   if(ret == 0){
			   alert("已经是最后一页了");
			   currentPage -= 1;
			   redrawTable(table, "GetProjectList.do", {"checkStatus":checkStatusRegion, "page":currentPage, "pageLen":10});
		   }
	   }
	   else{
		   var ret = redrawTable(table, "searchProject.do",{"checkStatus":checkStatusRegion,"page":currentPage, "pageLen":10, "projectName":$('#search_text_field').val()});
		   if(ret == 0){
			   alert("已经是最后一页了");
			   currentPage -= 1;
			   redrawTable(table, "searchProject.do",{"checkStatus":checkStatusRegion,"page":currentPage, "pageLen":10, "projectName":$('#search_text_field').val()});
		   }		   
	   }
	   $('#cp').text(currentPage);
	   initBar(checkStatusRegion);	
	   $("#log").text("");
	} );
	 
	//上一页事件
	$('#pre').on( 'click', function () {
		if(currentPage == 1)
			return;
		currentPage -= 1;
		if(isSearchMode == false)
			redrawTable(table, "GetProjectList.do", {"checkStatus":checkStatusRegion, "page":currentPage, "pageLen":10});
		else
			redrawTable(table, "searchProject.do",{"checkStatus":checkStatusRegion,"page":currentPage, "pageLen":10, "projectName":$('#search_text_field').val()});
		$('#cp').text(currentPage);
		initBar(checkStatusRegion);	
		$("#log").text("");
	} );
	
	//页面跳转事件
	$('#to').on( 'click', function () {
		var reg = new RegExp("^[0-9]+$");
		if(!reg.test($('#to_page').val())){
			alert("请输入有效页码");
			return;
		}
		
		var des_page = parseInt($('#to_page').val());

		var ret = 0;
		
		if(isSearchMode == false){
			ret = redrawTable(table, "GetProjectList.do", {"checkStatus":checkStatusRegion, "page":des_page, "pageLen":10});
			if(ret == 0){
				alert("您的选择超出了最大页码！");
				redrawTable(table, "GetProjectList.do", {"checkStatus":checkStatusRegion, "page":currentPage, "pageLen":10});
			}
			else{
				currentPage = des_page;
				$('#cp').text(currentPage);
				initBar(checkStatusRegion);	
				$("#log").text("");
			}
		}
		else{
			ret = redrawTable(table, "searchProject.do",{"checkStatus":checkStatusRegion,"page":des_page, "pageLen":10, "projectName":$('#search_text_field').val()});
			if(ret == 0){
				alert("您的选择超出了最大页码！");
				redrawTable(table, "searchProject.do", {"checkStatus":checkStatusRegion, "page":currentPage, "pageLen":10, "projectName":$('#search_text_field').val()});
			}
			else{
				currentPage = des_page;
				$('#cp').text(currentPage);
				initBar(checkStatusRegion);	
				$("#log").text("");
			}			
		}
	});
	
	//重置事件
	$('#reset').on( 'click', function(){
		$('#checking').trigger("click");
	});
	
	//搜索事件
	$('#search').on( 'click', function () {
		if($('#search_text_field').val() == ""){
			alert("搜索不能为空");
			return;
		}
		
		isSearchMode = true;
		currentPage = 1;
		$('#cp').text(currentPage);
		redrawTable(table, "searchProject.do",{"checkStatus":checkStatusRegion,
												"page":currentPage, 
												"pageLen":10,
												"projectName":$('#search_text_field').val()}
					);
		$("#log").text("");
	} );
		
	//查看日志事件
	$('#check_log_button').click(function(){
		$.ajax({
			url:"getCheckLog.do",
			data:{"projectId":$(table.row('.selected').node()).attr("rid")},
			async:false,
			type:'post',
			dataType:'json',
			success:function(data){
				$("#log").text("");
				
				for(var i = 0; i < data.length; i++){
					$("#log").append("<p style='margin:10px'>" 
							          + "时间：" + data[i][0] + "&nbsp&nbsp"
							          + "操作人：" + data[i][1] + "&nbsp&nbsp"
							          + "动作：" + actions[data[i][2]] + "&nbsp&nbsp"
							          + "备注：" + data[i][3] + "&nbsp&nbsp"
							          + "</p>");
				}
			}			
		});				
	});
	
	$('#pv_button').click(function(){		
		window.location.href = 'GetProjectPV.do?id=' + $(table.row('.selected').node()).attr("rid") + '&' + 'name=' + table.row('.selected').data()[1];	
	});
});

$(window).resize(function() {
	$("#left_nav_bar").height($(window).height() - $('#upper_nav_bar').height());
	$("#content_area").height($(window).height() - $('#upper_nav_bar').height());
	$("#content_area").width($(window).width() - $('#left_nav_bar').width() - 2);
	$("#mask").height($(window).height());
});
</script>
</body>

</html>