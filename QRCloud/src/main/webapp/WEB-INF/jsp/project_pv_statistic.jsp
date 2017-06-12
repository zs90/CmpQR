<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"  isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>访问统计</title>
<script type="text/javascript" src="js/jquery-1.12.0.min.js"></script>
<script type="text/javascript" src="js/jquery.jqplot.js"></script>
<script type="text/javascript" src="js/plugins/jqplot.barRenderer.js"></script>
<script type="text/javascript" src="js/plugins/jqplot.categoryAxisRenderer.js"></script>
<script type="text/javascript" src="js/plugins/jqplot.pointLabels.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery.jqplot.css" />
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="css/page.css"/>
</head>
<body>
<div id="upper_nav_bar" style='text-align:center;'><p style="font-size:25px; margin:0px; padding:15px 0 0 0;">丛书:${name}</p></div>
<div style="width:600px; height:50px; margin:0 auto 0; padding:15px 0 0 0">
起始日期: <input type="text" id="datepicker1">
结束日期: <input type="text" id="datepicker2">
<button id="submit">提交</button>
</div>
<div id="chart1" style="width:1200px; height:400px; margin:0 auto 0;">
</div>
</body>
<script type="text/javascript">
$(document).ready(function(){ 
	$( "#datepicker1" ).datepicker();
	$( "#datepicker2" ).datepicker();
    $( "#datepicker1" ).datepicker("option", "dateFormat", "yy-mm-dd");
    $( "#datepicker2" ).datepicker("option", "dateFormat", "yy-mm-dd");
	var plot1 = null;
	    
    $("#submit").click(function(){
		if($( "#datepicker1" ).val() > $( "#datepicker2" ).val()){
			alert('日期错误');
			return;
		}
		
    	$.ajax({
    		url:'GetProjectPVDetail.do?startDate=' + $( "#datepicker1" ).val() + '&' + 'endDate=' + $( "#datepicker2" ).val(),
    		async:false,
    		type:'get',
    		dataType:'json',
    		success:function(data){
    			//console.log(data);
    			var s1 = [];
    			var ticks = [];
    			for(var i = 0; i < data.length; i++){
    				s1[i] = data[i]["count"];
    				ticks[i] = data[i]["province"];
    				 				
    			    if (plot1) {
    			        plot1.destroy();
    			    }
    			    
    			    $.jqplot.config.enablePlugins = true;
    			     
    			    plot1 = $.jqplot('chart1', [s1], {
    			        // Only animate if we're not using excanvas (not in IE 7 or IE 8)..
    			        animate: !$.jqplot.use_excanvas,
    			        title:'PV图',
    			        seriesDefaults:{
    			            renderer:$.jqplot.BarRenderer,
    			            pointLabels: { show: true }
    			        },
    			        axes: {
    			            xaxis: {
    			                renderer: $.jqplot.CategoryAxisRenderer,
    			                ticks: ticks,
    			                label:'省市'
    			            },
    			        	yaxis: {
    			        		label:'访问次数'
    			        	}
    			        },
    			        highlighter: { show: false }
    			    });
    			}
    		}			
    	});	
    });
});
</script>
</html>