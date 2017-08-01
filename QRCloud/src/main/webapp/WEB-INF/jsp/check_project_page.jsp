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
	height:380px;
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

<!-- 驳回项目对话框 -->
<div id="deny_dialog" class="pop_dialog" style="height:300px">
	<div id="mybar2" class="dialog_bar"></div>
	<div class="input_table" style="height:150px">
		<table border="0">
			<tr>
				<td>
					退回理由
				</td>
				<td>	
					<div class="input_box2"><textarea name="deny_reason" id="deny_reason_id" class="ta"></textarea></div>
				</td>
			</tr>
		</table>
	</div>	
	<div class="buttons"> 
		<div id="confirm_deny" class="button">确定</div>&nbsp;&nbsp;&nbsp;&nbsp; 
		<div id="cancel_deny" class="button">取消</div> 
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
			<div id="pass_check_button" class="operation" style="float:right;display:none;">
				<img src="img/ui/Checkmark.png" width=25 height=25 style="vertical-align:middle;"></img>
				通过审核
			</div>
			<div id="deny_check_button" class="operation" style="float:right;display:none;">
				<img src="img/ui/Unchecked.png" width=25 height=25 style="vertical-align:middle;"></img>
				<span>不通过</span>
			</div>
			<div id="inspect_project_button" class="operation" style="float:right;display:none;">
				<img src="img/ui/Open.png" width=25 height=25 style="vertical-align:middle;"></img>
				查看项目
			</div>
			<div id="enter_button" class="operation" style="float:right;display:none;">
				<img src="img/ui/Open.png" width=25 height=25 style="vertical-align:middle;"></img>
				进入项目
			</div>
			<div id="check_log_button" class="operation" style="float:right;display:none;">
				<img src="img/ui/News.png" width=25 height=25 style="vertical-align:middle;"></img>
				查看日志
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
			<div id="checking" style="color:red;">
				<img src="img/ui/Bookmark.png" width=25 height=25 style="vertical-align:middle;"></img>
				待审核
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
				已申请修改
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
		
	$("#search_box").css("display", "none");
	$("#check_log_button").css("display", "block");
	$("#pv_button").css("display", "block");
	$("#enter_button").css("display","none");
	$("#inspect_project_button").css("display","block");
	
	if(checkStatusRegion == 1){			
		$("#pass_check_button").css("display","block");
		$("#deny_check_button").css("display","block");	
		$("#enter_button").css("display","block");
		$("#inspect_project_button").css("display","none");
	}
	else if(checkStatusRegion == 4 || checkStatusRegion == 2){
		$("#deny_check_button").css("display","block");
	}
	$('#upper_nav_bar').css("background-color", "#3B93FF");
	$("#log").text("");
}

function initBar(checkStatusRegion){
	$("#search_box").css("display", "block");
	
	$("#inspect_project_button").css("display","none");
	$("#enter_button").css("display","none");
	$("#pass_check_button").css("display","none");
	$("#deny_check_button").css("display","none");
	$("#check_log_button").css("display", "none");
	$("#pv_button").css("display", "none");

	$('#upper_nav_bar').css("background-color", "#F5F6F9");	
	$("#paging").css("display", "block");
	$("#log").text("");
}

function redrawTable(table, myUrl, postData){
	var data_len = 0;
	$.ajax({
		url: myUrl,
		async:false,
		data:postData,
		type:'post',
		dataType:'json',
		success:function(data){
			dataSet = [];
			fucking_id = [];
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
	var checkStatusRegion = 1;
	var currentPage = 1;
	var actions = ["未审核", "提交审核", "已通过审核", "审核未通过", "提交修改"];
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
	});

	//审核未通过事件和同意修改事件
	$('#deny_check_button').click(function(){
			$("#mask").fadeTo(500, 0.6);
			$('#deny_dialog').fadeIn(500);
		
			$('#deny_reason_id').val("");

			var oBox = document.getElementById("deny_dialog");
			var oBar = document.getElementById("mybar2");
			startDrag(oBar, oBox);
	});
	
	$('#confirm_deny').click(function(){	
		var deny_data = {"checkStatus":"3",
				"projectId":$(table.row('.selected').node()).attr("rid"),
				"checkInfo":$('#deny_reason_id').val()
		};
		$.ajax({
			url:"changeCheckStatus.do",
			async:false,
			data:deny_data,
			type:'post',
			dataType:'text',
			success:function(data){
				table.row('.selected').remove().draw();
				resetNo(table);
				alert("已标记为审核未通过！");
			}			
		});
						
		//销毁对话框
		$("#deny_dialog").fadeOut(500);
		$("#mask").fadeOut(500);
	});	
	
	$('#cancel_deny').click(function(){
		$("#deny_dialog").fadeOut(500);
		$("#mask").fadeOut(500);
	});	
	
	//通过审核事件
	$('#pass_check_button').click(function(){
		var pass_data = {"checkStatus":"2",
				"projectId":$(table.row('.selected').node()).attr("rid"),
				"checkInfo":""
		};
		$.ajax({
			url:"changeCheckStatus.do",
			async:false,
			data: pass_data,
			type:'post',
			dataType:'text',
			success:function(data){
				alert("审核已通过！");
				table.row('.selected').remove().draw();
				resetNo(table);				
			}			
		});		
	});	
	
	//进入待审核区域
	$('#checking').click(function(){
		checkStatusRegion = 1;
		
		initBar(checkStatusRegion);
		isSearchMode = false;
		
		$("#deny_check_button").find("span").text("不通过");
		$(this).css("color", "red");
		$('#passed').css("color", "black");
		$('#unpassed').css("color", "black");
		$('#modified').css("color", "black");
		
		currentPage = 1;
		$('#cp').text(currentPage);
		redrawTable(table, "GetProjectList.do", {"checkStatus":checkStatusRegion, "page":currentPage, "pageLen":10});
	});	
	
	//进入审核通过区域
	$('#passed').click(function(){
		checkStatusRegion = 2;
		initBar(checkStatusRegion);
		isSearchMode = false;
		
		$("#deny_check_button").find("span").text("强制下架");
		
		$(this).css("color", "red");
		$('#checking').css("color", "black");
		$('#unpassed').css("color", "black");
		$('#modified').css("color", "black");
		
		currentPage = 1;
		$('#cp').text(currentPage);
		redrawTable(table, "GetProjectList.do", {"checkStatus":checkStatusRegion, "page":currentPage, "pageLen":10});
	});	
	
	//进入审核未通过区域
	$('#unpassed').click(function(){
		checkStatusRegion = 3;
		initBar(checkStatusRegion);
		isSearchMode = false;
		
		$(this).css("color", "red");
		$('#checking').css("color", "black");
		$('#passed').css("color", "black");
		$('#modified').css("color", "black");
		
		
		currentPage = 1;
		$('#cp').text(currentPage);
		redrawTable(table, "GetProjectList.do", {"checkStatus":checkStatusRegion, "page":currentPage, "pageLen":10});
	});	
	
	//进入已提交修改区域
	$('#modified').click(function(){
		checkStatusRegion = 4;
		initBar(checkStatusRegion);
		isSearchMode = false;
		
		$("#deny_check_button").find("span").text("退回修改");
		
		$(this).css("color", "red");
		$('#checking').css("color", "black");
		$('#passed').css("color", "black");
		$('#unpassed').css("color", "black");
		
		currentPage = 1;
		$('#cp').text(currentPage);
		redrawTable(table, "GetProjectList.do", {"checkStatus":checkStatusRegion, "page":currentPage, "pageLen":10});
	});
	
	//进入项目预览事件
	$('#inspect_project_button').click(function(){
		var url = "view_item.do";
		var form = $('<form action="' + url + '" method="post"'+ 'type="hidden"' + '>' +
		  '<input type="text" name="project_id" value="' + $(table.row('.selected').node()).attr("rid") + '" />' +
		  '<input type="text" name="project_name" value="' + table.row('.selected').data()[1] + '" />' +
		  '</form>');
		$('body').append(form);
		form.submit();			
	});
	
	//进入项目进行审核事件
	$('#enter_button').click(function(){
		var url = "check_item.do";
		var form = $('<form action="' + url + '" method="post"'+ 'type="hidden"' + '>' +
		  '<input type="text" name="project_id" value="' + $(table.row('.selected').node()).attr("rid") + '" />' +
		  '<input type="text" name="project_name" value="' + table.row('.selected').data()[1] + '" />' +
		  '</form>');
		$('body').append(form);
		form.submit();		
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