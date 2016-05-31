var Login={};

Login.query=function(){
}

$(function(){
    document.onkeydown=function(event){ 
        e = event ? event :(window.event ? window.event : null); 
        if(e.keyCode==13){ 
            $("#submitLogin").click();
        } 
    } 
	$("#isAutoLogin").on('click',function(){
		$(this)[$(this).hasClass('checked')?'removeClass':'addClass']('checked');
	});
	
	$("#submitLogin").on('click',function(){
		var username = $.trim($("#username").val());
		var password = $.trim($("#password").val());
		var code = $.trim($("#code").val());
		if(username==''){
			MU.msgTips('warn','请输入账号！',1000);
			$("#username").focus();
			return;
		}
		if(password==''){
			MU.msgTips('warn','请输入密码！',1000);
			$("#password").focus();
			return;
		}
		if($("#isAutoLogin").hasClass("checked")){
			var isAutoLogin = 'checked';
		}else{
			var isAutoLogin = 'unchecked';
		}
		var myurl=basePath+"userCheck";
		var $this = $(this);
		$this.addClass('loading').text('登录中...');
		$.ajax({
		   type: "POST",
		   url:  myurl,	
		   async: true, 
		   dataType:"json",
		   data:{username:username,password:password,isAutoLogin:isAutoLogin,code:code},
		}).done(function(result){
			Login.userCheckResult(result);
		}).fail(function(){
			MU.msgTips('error','请检查网络后刷新页面重试！');
		});
	});
});


Login.userCheckResult=function(result)
{
	var indexurl=basePath+"show/show.jsp";

	if((null==result)||(""==result))
	{
		MU.msgTips('error',"数据处理异常",1000);
		return;
	}
	
	if(result.jsonData =="codewrong")
	{
		MU.msgTips('error',"验证码不正确",1000);
		$("#code").focus();
		return;
	}
	
	if(result.jsonData =="false")
	{
		MU.msgTips('error',"密码或用户名不正确",1000);
		$("#password").focus();
		return;
	}
	if(result.jsonData =="true")
	{
		window.location.href=indexurl;
	}
	else
	{
		MU.msgTips('error',"未知错误",1000);
		$("#password").focus();
		return;       
	}
}