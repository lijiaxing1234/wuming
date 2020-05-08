
//获取字典标签
function getDictLabel(data, value, defaultValue){
	for (var i=0; i<data.length; i++){
		var row = data[i];
		if (row.value == value){
			return row.label;
		}
	}
	return defaultValue;
}

// 打开一个窗体
function windowOpen(url, name, width, height){
	var top=parseInt((window.screen.height-height)/2,10),left=parseInt((window.screen.width-width)/2,10),
		options="location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes,"+
		"resizable=yes,scrollbars=yes,"+"width="+width+",height="+height+",top="+top+",left="+left;
	window.open(url ,name , options);
}

// 恢复提示框显示
function resetTip(){
	top.$.jBox.tip.mess = null;
}

// 关闭提示框
function closeTip(){
	top.$.jBox.closeTip();
}

//显示提示框
function showTip(mess, type, timeout, lazytime){
	resetTip();
	setTimeout(function(){
		top.$.jBox.tip(mess, (type == undefined || type == '' ? 'info' : type), {opacity:0, 
			timeout:  timeout == undefined ? 2000 : timeout});
	}, lazytime == undefined ? 500 : lazytime);
}

// 显示加载框
function loading(mess){
	if (mess == undefined || mess == ""){
		mess = "正在提交，请稍等...";
	}
	resetTip();
	top.$.jBox.tip(mess,'loading',{opacity:0});
}

// 关闭提示框
function closeLoading(){
	// 恢复提示框显示
	resetTip();
	// 关闭提示框
	closeTip();
}

// 警告对话框
function alertx(mess, closed){
	top.$.jBox.info(mess, '提示', {closed:function(){
		if (typeof closed == 'function') {
			closed();
		}
	}});
	top.$('.jbox-body .jbox-icon').css('top','55px');
}

// 确认对话框
function confirmx(mess, href, closed){
	top.$.jBox.confirm(mess,'系统提示',function(v,h,f){
		if(v=='ok'){
			if (typeof href == 'function') {
				href();
			}else{
				resetTip(); //loading();
				location = href;
			}
		}
	},{buttonsFocus:1, closed:function(){
		if (typeof closed == 'function') {
			closed();
		}
	}});
	top.$('.jbox-body .jbox-icon').css('top','55px');
	return false;
}


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
});

