var show={};

show.query=function(){
	show.queryweibo();
}

$(function() {
	$(".module-list-view .item").hover(function() {
		$(this).addClass("item-hover");
	}, function() {
		$(this).removeClass("item-hover");
	})
});

show.queryweibo=function()
{
	var data={};
	data.pageSize=10;
	var rand = Math.random();
	data.pageNo=1;
	data.rand = rand;	
	var myurl = basePath+"getResult";
	/**
	 * 前端分页
	 */
	$("#jg_ajax_page").whpage({
		url:myurl,
		data: data,
		showNum:5,
		type: 'java',
		pagesize: 10,
		cur: 'cur',
		action:'post',
		format: function(data,page,pagesize){
			var page = page || 1;			
			var data = data.jsonData;
			var t = {};
			var records = parseInt(data.length);
			pageCount = Math.ceil(data.length / pagesize);
			t['cur'] = page;
			t['pageCount'] = pageCount;
			t['records'] = records;
			if(data.length>0){
				$("#jg_ajax_page").removeClass("hide");
			}else{
				$("#jg_ajax_page").removeClass("hide").addClass("hide");
			}
			return t;	
		},
		insert: function(result,start,end){
			if((null==result)||(""==result)){
				return;
			}
			else{
				var data = result.jsonData;
				if(data!=[] && data!="" && data.length!=0){
					var FileHtml = "";
					var weiboBar="";
					for(var i=start;i<end;i++){
						var obj = data[i];
						FileHtml +='  <div class="item global-clearfix"><div style="width: 9%" class="col c1">'+obj.uid+'</div><div style="width: 20%;" class="col">'+obj.cmtUrl+'</div><div style="width: 10%;" class="col">'+obj.pubTime+'</div><div style="width: 5%;" class="col">'+obj.zanNum+'</div><div style="width: 5%;" class="col">'+obj.zfNum+'</div><div style="width: 5%;" class="col">'+obj.cmtNum+'</div><div style="width: 44%;" class="col">'+obj.content+'</div></div>';
					}
				}
				weiboBar='<div style="width: 9%;" class="col c1">用户名</div>	<div style="width: 20%;" class="col">微博地址</div><div style="width: 10%;" class="col">发表时间</div><div style="width: 5%;" class="col">点赞数</div><div style="width: 5%;" class="col">转发数</div><div style="width: 5%;" class="col">评论数</div><div style="width: 30%;border-right: none;" class="col">发表内容</div>';
				$("#FileHtml").html(FileHtml);
				$("#weiboBar").html(weiboBar);
			}
		}
	});
}

show.getcomment=function()
{
	var data={};
	data.pageSize=10;
	var rand = Math.random();
	data.pageNo=1;
	data.rand = rand;	
	var myurl = basePath+"getComment";
	/**
	 * 前端分页
	 */
	$("#jg_ajax_page").whpage({
		url:myurl,
		data: data,
		showNum:5,
		type: 'java',
		pagesize: 10,
		cur: 'cur',
		action:'post',
		format: function(data,page,pagesize){
			var page = page || 1;			
			var data = data.jsonData;
			console.log(data);
			var t = {};
			var records = parseInt(data.length);
			pageCount = Math.ceil(data.length / pagesize);
			t['cur'] = page;
			t['pageCount'] = pageCount;
			t['records'] = records;
			if(data.length>0){
				$("#jg_ajax_page").removeClass("hide");
			}else{
				$("#jg_ajax_page").removeClass("hide").addClass("hide");
			}
			return t;	
		},
		insert: function(result,start,end){
			if((null==result)||(""==result)){
				return;
			}
			else{
				var data = result.jsonData;
				if(data!=[] && data!="" && data.length!=0){
					var FileHtml = "";
					var weiboBar="";
					for(var i=start;i<end;i++){
						var obj = data[i];
						FileHtml +='  <div class="item global-clearfix"><div style="width: 9%" class="col c1">'+obj.uid+'</div><div style="width: 20%;" class="col">'+obj.weiboUrl+'</div><div style="width: 25%;" class="col">'+obj.wid+'</div><div style="width: 30%;" class="col">'+obj.content+'</div></div>';
					}
				}
				weiboBar='<div style="width: 9%;" class="col c1">用户名</div>	<div style="width: 20%;" class="col">微博地址</div><div style="width: 25%;" class="col">微博内容</div><div style="width: 30%;border-right: none;" class="col">评论内容</div>';
				$("#FileHtml").html(FileHtml);
				$("#weiboBar").html(weiboBar);
			}
		}
	});
}

$('#CrawlerWeibo').on('click',function(){
	Prompt.add();
	Prompt.init({
		title : "请设置微博抓取的主题词",
		shade : true,
		opacity : 20,
		width : 550,
		height : 350,
		html : $('.choose_class').html(),
		ConfirmFun : a1,
        CancelFun : a2
		});
		function a1(){
			var myurl=basePath+"getCrawler";
			var indexurl=basePath+"show/show.jsp";
			var data={};
			var keyword=$("#prompt_body").contents().find("#word").val();
			var startP=$("#prompt_body").contents().find("#startP").val();
			var endP=$("#prompt_body").contents().find("#endP").val();
			var wTime=$("#prompt_body").contents().find("#wTime").val();
			var time=$("#prompt_body").contents().find("#time").val();
			$.ajax({
			   type: "POST",
			   url:  myurl,	
			   async: true, 
			   dataType:"json",
			   data:{keyWord:keyword,start:startP,end:endP,time:time,wTime:wTime},
			}).done(function(result){
				alert("微博爬取成功!\r\n"+result.jsonData);
				show.CrawComment();
			}).fail(function(){
				MU.msgTips('error','请检查网络后刷新页面重试！');
			});
		}
		function a2(){
	}
});

show.CrawComment=function()
{
	var data={};
	var myurl = basePath+"getCmtCrawler";
	$.ajax({
		   type: "POST",
		   url:  myurl,	
		   async: true, 
		   dataType:"json",
		   data:data,
		}).done(function(result){
			alert("评论爬取成功!\r\n"+result.jsonData);
			window.location.href=window.location.href;
		}).fail(function(){
			MU.msgTips('error','请检查网络后刷新页面重试！');
		});
}

$('#saveWeibo').on('click',function(){
	Prompt.add();
	Prompt.init({
		title : "请设置微博的保存格式",
		shade : true,
		opacity : 20,
		width : 550,
		height : 300,
		html : $('.save_data').html(),
		ConfirmFun : a1,
        CancelFun : a2
		});
		function a1(){
			
			var type=$("input[name='cType']:checked").val();
			var myurl=basePath+'servlet/Save'+type+'Servlet';
			$.ajax({
			   type: "POST",
			   url:  myurl,	
			   async: true, 
			   dataType:"json",
			   data:{dir:'weibo'},
			}).done(function(result){
				alert("请确定下载");
				window.location.href=basePath+'servlet/FileDownServlet?dir=weibo&filename='+result.jsonData;
			}).fail(function(){
				MU.msgTips('error','请检查网络后刷新页面重试！');
			});
		}
		function a2(){
	}
});

$('#saveComment').on('click',function(){
	Prompt.add();
	Prompt.init({
		title : "请设置评论的保存格式",
		shade : true,
		opacity : 20,
		width : 550,
		height : 300,
		html : $('.save_data').html(),
		ConfirmFun : a1,
        CancelFun : a2
		});
		function a1(){
			
			var type=$("input[name='cType']:checked").val();
			var myurl=basePath+'servlet/Save'+type+'Servlet';
			$.ajax({
			   type: "POST",
			   url:  myurl,	
			   async: true, 
			   dataType:"json",
			   data:{dir:'comment'},
			}).done(function(result){
				alert("请确定下载");
				window.location.href=basePath+'servlet/FileDownServlet?dir=comment&filename='+result.jsonData;
			}).fail(function(){
				MU.msgTips('error','请检查网络后刷新页面重试！');
			});
		}
		function a2(){
	}
});

$('#changeResult').on('click',function(){
	
	if(false==$('#changeResult').hasClass('checked')){
		$('#changeResult').addClass('checked');
		show.getcomment();
		
	}else if(true==$('#changeResult').hasClass('checked')){
		$('#changeResult').removeClass('checked');
		show.queryweibo();
	}
});