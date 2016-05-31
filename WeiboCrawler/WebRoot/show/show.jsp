<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=basePath%>home/css/file.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>home/css/global.css" rel="stylesheet" type="text/css" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>爬虫搜索</title>
</head>
<body>
	<div class="yao-b">
		<div class="module-toolbar">
			<div class="bar global-clearfix" style="margin-left:15px;display:inline">
				
				<div style="width:50%;float:left;height:50px">
					<p style="float:right;font-size: 19px;font-weight: bold;font-family: 宋体;">新浪微博抓取与主题发现系统</p>						
				</div>
				<div style="width:35%;float:right;height:50px">
					<a class="submit" id="CrawlerWeibo" href="javascript:;">抓取设置</a>
					<a class="submit" id="changeResult" href="javascript:;">显示切换</a>
					<a class="submit" id="saveWeibo" href="javascript:;">保存微博</a>
					<a class="submit" id="saveComment" href="javascript:;">保存评论</a>
				</div>	
			</div>
		</div>
	  	<div class="module-crumbs">
			<div class="title global-clearfix" id=filenum></div>
		</div>
		<div class="module-list-view">
			<div class="list-view-home">
				<div class="title" style="padding-right: 0px;">
					<div class="item global-clearfix" id="weiboBar">
						
					</div>
				</div>
				<div class="list" id="FileHtml"></div>
				<!--翻页-->
				<div class="page_v2" id="jg_ajax_page"></div>
				<!--翻页-->
			</div>
		</div>
	</div>
	<!-- 微博抓取 -->
	<div class="choose_class hide">
		<ul class="class_list">
	    	<li>主题词汇：&nbsp;<input type="text" id="word" placeholder="请输入要抓取的主题词汇" style="width: 300px;"/></li> 
			<li>开始页数：&nbsp;<input type="text" id="startP" placeholder="抓取的开始页数" style="width: 300px;"/></li>
			<li>结束页数：&nbsp;<input type="text" id="endP" placeholder="抓取的结束页数，最多100页" style="width: 300px;"/></li>
			<li>主题追踪：&nbsp;<input type="text" id="wTime" placeholder="主题追踪的时间设置，单位（天）" style="width: 300px;"/></li>  
			<li>微博追踪：&nbsp;<input type="text" id="time" placeholder="微博追踪的时间设置，单位（小时）" style="width: 300px;"/></li>  
	    </ul>	    
	</div>
	 
	 <!-- 微博保存 -->
	<div class="save_data hide">
		<ul class="class_list">
	    	<li><input type="radio" name="cType" value="Excel" />保存为xls格式</li> 
			<li><input type="radio" name="cType" value="Xml" />保存为xml格式</li>
			<li><input type="radio" name="cType" value="Txt" />保存为txt格式</li> 
	    </ul>	    
	</div>
	
<script type="text/javascript" src="<%=basePath%>home/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>home/js/jquery.whpage.js"></script>
<script type="text/javascript" src="<%=basePath%>home/js/show.js"></script>
<script type="text/javascript" src="<%=basePath %>home/js/pop-window.js"></script>
<script type="text/javascript" src="<%=basePath %>home/js/public.min.js"></script>
<script type="text/javascript">
	var basePath = "<%=basePath%>";
	
	$(document).ready(function() {
		show.query();
	});
</script>
</body>
</html>