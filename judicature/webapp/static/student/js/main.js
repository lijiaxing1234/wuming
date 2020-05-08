

// 引入js和css文件
function include(id, path, file){
	if (document.getElementById(id)==null){
        var files = typeof file == "string" ? [file] : file;
        for (var i = 0; i < files.length; i++){
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + path + name + "'";
            document.write("<" + tag + (i==0?" id="+id:"") + attr + link + "></" + tag + ">");
        }
	}
}

//获取URL地址参数
function getQueryString(name, url) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    if (!url || url == ""){
	    url = window.location.search;
    }else{	
    	url = url.substring(url.indexOf("?"));
    }
    r = url.substr(1).match(reg)
    if (r != null) return unescape(r[2]); return null;
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

	$(".auto-login").click(function(){
		$(this).children("i").toggleClass("act");
	})

	$(".changeColor").click(function(){
		var colorList  = ['#7a56a1','#a15667','#a1569f','#56a0a1','#56a177','#6261b0','#156186','#a99577'];
     	// $.each(div, function() {
     	    var bgColor = getColorByRandom(colorList);
     	    $('.main-right-head').css('background-color', bgColor);
     	    $('.active').css('background', bgColor);
     	    $('.click-down').css('background', bgColor + ' url(images/arrow1.png) no-repeat center center');
     	    $('select').css('border-color', bgColor);
     	    $('.table-bordered').css('border-color', bgColor);
     	    $('.table-bordered th').css('border-color', bgColor);
     	    $('.table-bordered td').css('border-color', bgColor);
     	    $('.pagination ul > .disabled > a').css('color', bgColor);
     	    $('.pagination ul > li > a').css('border-color', bgColor);
     	    $('.btn-primary').css({"background-color": bgColor,"background-image":"linear-gradient(to bottom, "+bgColor+","+bgColor+")"});
     	// })

    	function getColorByRandom(colorList){
    	    var colorIndex = Math.floor(Math.random()*colorList.length);
    	    var color = colorList[colorIndex];
    	    colorList.splice(colorIndex,1);
    	    return color;
    	}
	})

});