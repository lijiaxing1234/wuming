(function(){
	
	if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
		alertx('未登录或登录超时。请重新登录，谢谢！');
		top.location = "${ctx}";
	}
	
	 function LoginModeInit() {
	    var _RenderMain = $("#RenderMain");
	    $(document).on("click", ".JS-login-main", function() {
	        _RenderMain.html(template("T:LoginTemplateMain", {})),
	        $("body").addClass("overflow-h");
	        var _userName = $("#index_login_username"),
	        _password = $("#index_login_password");
	        $17.isBlank($17.getQuery("userId")) ? "" == _userName.val() && _userName.focus() : (_userName.val($17.getQuery("userId")), _password.focus());
	    }),
	    "login" == $17.getQuery("ref") && $(".JS-login-main").trigger("click"),
	    $(document).on("submit", ".JS-formSubmit",function() {
	        var _userName = $("#index_login_username"),
	            _password = $("#index_login_password");
	        return "" == _userName.val() ? (ErrorInfoAlert(_userName, "请输入登录账号"), !1) : "" == _password.val() ? (ErrorInfoAlert(_password, "请输入密码"), !1) : void 0;
	    }),
	    $(document).on("click", ".JS-rememberMe-btn",function() {
	        var $self = $(this),
	        $checked = $self.find("input");
	        $checked.prop("checked") ? ($checked.prop("checked", !1).val("off"), $self.removeClass("active")) : ($checked.prop("checked", !0).val("on"), $self.addClass("active"));
	    }),
	    $(document).on("click", ".JS-forgetPassword-btn",function() {
	    	  var _userName = $("#index_login_username");
	    	  "" == _userName.val() ? alertx("请输入登录名!"):(
			  top.$.jBox("iframe:"+ctx+"/pass/form?type=3&loginName="+_userName.val(),{
					title:"找回密码",
					width:650,
				    height: 350,
					buttons:{}, 
				    bottomText:"",
				    loaded:function(h){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					},
				    closed:function(){
				    	
					}
		      })); 
	    }),
	    $(document).on("click",".JS-showPassword-btn",function(){
	    	 $(this).toggle(function () {
	    		 var _icon = $("#index_login_icon"),
	             _password = $("#index_login_password");
	    		  _password.remove();
		    	  $('<input type="text" id="index_login_password" name="password" class="JS-inputEvent" tabIndex="2" placeholder="输入密码" value="'+_password.val()+'"/> ').insertAfter(_icon);
		    	  $(this).text("隐藏密码");
			 },function () {
				 var _icon = $("#index_login_icon"),
	             _password = $("#index_login_password");
				 _password.remove();
				 $('<input type="password" id="index_login_password" name="password" class="JS-inputEvent" tabIndex="2" placeholder="输入密码" value="'+_password.val()+'"/> ').insertAfter(_icon);
				 $(this).text("显示密码");
			 });
	    	 $(this).trigger('click');
	    });
	 }
	
	function clearMain(id) {
        id.empty(),
        $("body").removeClass("overflow-h");
	}
	function indexSwitch() {
	    var _winHeight = $(window).height();
	    $(".JS-indexSwitch-main li").height(600 >= _winHeight ? 600 : _winHeight);
	}
	
	function ErrorInfoAlert(id, val) {
	    id.focus(),
	    id.parent().addClass("error"),
	    id.siblings(".errorTips").text(val);
	}
	
	$(".JS-indexSwitch-main").flexslider({
	    animation: "slide",
	    slideshow: !0,
	    slideshowSpeed: 5e3,
	    directionNav: !1,
	    animationLoop: !0,
	    manualControls: ".JS-indexSwitch-mode li",
	    touch: !0
	 }),
	indexSwitch(),
	LoginModeInit(),
	$(window).resize(function() {
        indexSwitch();
    }),
	$(document).on("click", ".JS-clear-btn",function() {
        clearMain($("#RenderMain"));
    }), 
    $(document).on("keyup", ".JS-inputEvent",function() {
        var $self = $(this);
        "" != $self.val() && $self.parent().removeClass("error");
    });
})();