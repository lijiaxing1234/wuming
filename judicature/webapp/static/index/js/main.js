$(function(){

	$('.e-sele').click(function(){
		$(this).addClass('act');
		$(this).siblings('.bea-opt').slideToggle();
		$(this).children('input').css({'borderColor':'#dcdcdc'})
		$(this).children('em').css({'borderColor':'#dcdcdc'})
		$(this).parent().parent().siblings('li').children('.bea-sele').children('.bea-opt').slideUp();
		$(this).parent().parent().siblings('li').children('.bea-sele').children('.e-sele').removeClass('act');
		$(this).parent().parent().siblings('li').children('.bea-sele').children('.e-sele').children('em').css({'borderColor':'#dcdcdc'});
		$(this).parent().parent().siblings('li').children('.bea-sele').children('.e-sele').children('input').css({'borderColor':'#dcdcdc'});
		$(this).siblings('.bea-opt').addClass('open');

	});
	$('.bea-opt li').click(function(){
		$(this).addClass('on').siblings().removeClass('on');
		var txt=$(this).html();
		// alert(txt);
		$(this).parent().siblings('.e-sele').children('input').val(txt);
		$(this).parent().slideUp();
		$(this).parent().siblings('.e-sele').children('input').css({'borderColor':'#dcdcdc'});
		$(this).parent().siblings('.e-sele').children('em').css({'borderColor':'#dcdcdc'});
		$(this).parent().siblings('.e-sele').removeClass('act');
                    $('.bea-opt li').css({'display':'block'})
	});

	$(".right-tit li").eq(1).css("background","#ef8079");
	$(".right-tit li").eq(2).css("background","#5acfc7");
	$(".right-tit li").eq(3).css("background","#4fe2f6");
	$(".right-tit li").eq(4).css("background","#fdc345");

	$(".auto-login").click(function(){
		$(this).children("i").toggleClass("act");
	})

});