$(function(){
	//表格高亮
	function trHighLight(id){
		$(id).live({
			mouseenter:function(){
				$(this).addClass('high-light');
			},
			mouseleave:function(){
				$(this).removeClass('high-light');
			}
		});
	}
	trHighLight('.hn_table tbody tr');
	//显示隐藏
	$('.green').click(function(){
		$('.microblog-tcc').show();
		});
	$(document).bind("click",function(e){ 
		var target = $(e.target); 
		if(target.closest(".green").length == 0){ 
			$(".microblog-tcc").hide(); 
		} 
	}) ;
	//time
	$('.hn_area_time a').click(function(){
		$(this).addClass('cur').siblings().removeClass('cur');
		})
	//login
	$("#isAutoLogin").on('click',function(){
		$(this)[$(this).hasClass('checked')?'removeClass':'addClass']('checked');
	});
	$("#submitLogin").on('click',function(){
		if($(this).hasClass('loading')){return;}
		var username = $.trim($("#username").val());
		var password = $.trim($("#password").val());
		var isAutoLogin = $("#isAutoLogin").hasClass('checked') ? '1' : '0';
		if(username==''){
			MU.msgTips('warn','请输入账号！');
			$("#username").focus();
			return;
		}
		if(password==''){
			MU.msgTips('warn','请输入密码！');
			$("#password").focus();
			return;
		}
		var $this = $(this);
		$this.addClass('loading').text('登录中...');
		$.ajax({
			type:'post',
			url:'ajax/login.html',
			dataType:'json',
			data:{username:username,password:password,isAutoLogin:isAutoLogin}
		}).done(function(data){
			if(data.success){
				MU.msgTips('success',data.message,1200,function(){
					window.location.href = 'index.html'
				});
			}else{
				MU.msgTips('error',data.message);
				$this.removeClass('loading').text('登录');
			}
		}).fail(function(){
			MU.msgTips('error','请检查网络后刷新页面重试！');
			$this.removeClass('loading').text('登录');
		})
	});
	//切换菜单
	var loca = window.location.href;
	$("div.hn_header ul.hn_nav a").each(function(){
		var str = $(this).attr('href');
		str = str.substring(str.indexOf("jsp"),str.indexOf("jsp")+18);
		if(loca.indexOf(str)>-1){
			$(this).addClass('cur').parent().siblings().find("a").removeClass('cur');
		}
	});
});