<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>
<script type="text/javascript" src="${ctxStatic}/js/date.js"></script>
<script type="text/javascript">
	$(function() {
		$("#datetime").text(getCurDate());
		function getMsgNo(){
			$.ajax({
				url:'${ctx}/studentNews/getStudentMsgCount',
				type:'post',
				cache:false,
				success:function(data){
					var obj = eval("("+data+")");
					if(0 == obj.msgNo){
						$("#emid").hide();
					}else{
						document.getElementById("MsgSpan").innerHTML = obj.msgNo;
						$("#emid").show();
					}
				}
			});
		};
		getMsgNo();
	});
	function changeCourse(){
		window.location.href = "${ctx}/changeCourse";
	}
	
	var timeClock=setInterval(clock,10000);
	function clock(){
	    $.ajax({
	    	url:'${ctx}/studentNews/getStudentMsgCount',
			type:'post',
			cache:false,
			success:function(data){
				var obj = eval("("+data+")");
				if(obj.statusCode==301){
					clearInterval(timeClock);
					alertx(obj.message,function(){
						location.href="${ctx}";
					});
				}else{
					if(obj.msgNo != 0){
					  	document.getElementById("MsgSpan").innerHTML = obj.msgNo;
					  	$("#emid").show();						
					}
				}
			}
	    });
	}
	
	function getMsg(){
		$.ajax({
	    	url:'${ctx}/studentNews/getStudentMsg',
			type:'post',
			cache:false,
			success:function(data){
				var obj = eval("("+data+")");
				document.getElementById("MsgSpan").innerHTML = 0;
				alertx(obj.msg);
			}
	    });
	}
	
	function clickaaaa(){
		$("#aaaa").click()
	}
</script>


<style type="text/css">
  .c-right{overflow: hidden; }
  .cr{height: 124px; overflow: hidden; /* min-width: 930px; */}
  .cr-nav{ height: 124px; line-height:124px; font-size:18px; color: #fff; position: relative; left: 25px; width: 90%;float: left;}
  .cr-nav label{font-size: inherit;}
 /*  .c-right .quit{ margin-top: 0px; line-height: 124px;} */
.header-top{height: 45px;line-height: 45px;background: #f7f7f7;border-bottom: 1px solid #e0e0e0;}
.header-top-left{float: left;}
.header-top-left span{color: #1283c8;font-weight: bold;margin: 0 5px;}
.header-top-right{float: right;}
.header-top-right p,
.header-top-right .quit{float: left;}
.header-top-right .quit p i{background: url(${ctxStatic}/modules/teacher/img/quit_icon.png) no-repeat center;width: 15px;height: 14px;display: inline-block;margin: 0 5px 0 10px;position: relative;top: 1px;}
.header-top-right .quit p a{color: #666;}
.bgbg{position:absolute;z-index:-1;width:100%;height:auto;top:1px;}
.header-logo{margin:15px auto;}
.header-logo img{float:left;}
.header-logo .header-lright{float:right;line-height:65px;}
.header-logo .header-lright label{line-height:67px ! important;margin-right:10px;color:#fff;font-size:16px;}
.header-logo .header-lright select{margin-bottom:3px;}

.content-top-right{padding:10px 20px;}
.header-top-right .quit{font-size: 16px;}
.header-top-right .header-middle {float: left;margin: -3px 10px 0;margin-top: -3px;}
.header-top-right .header-middle p{display: inline-block;width: 26px;height: 26px;border:1px solid #c7c7c7;border-radius: 50%;text-align: center;line-height: 26px;position: relative;margin-left: 5px;}
.header-top-right .header-middle p a{width: 26px;height: 26px;display: inline-block;}
.header-top-right .header-middle p i{background: url(${ctxStatic}/img/message_icon.png) no-repeat;width: 14px;height: 15px;display: inline-block;position:relative;top:5px;left:-1px;}
.header-top-right .header-middle p i.h-email{background: url(${ctxStatic}/img/h_email.png) no-repeat;width: 15px;height: 11px;position:relative;top:7px;}
.header-top-right .header-middle p i.h-page{background: url(${ctxStatic}/img/task.png) no-repeat;height: 13px;}
.header-top-right .header-middle p em{position: absolute;top: -7px;right: -7px;width: 19px;height: 19px;background: #e34d3c;border-radius: 50%;color: #fff;text-align: center;line-height: 19px;font-size: 12px;}
</style>


<%-- <div class="header-top">
	<div class="container clearfix">
		<p class="header-top-left">欢迎您,<span>${user.name }</span>同学 </p>
				&nbsp;&nbsp;&nbsp;&nbsp;当前课程：
		  		<c:forEach var="courseVersion" items="${student:getCourseVersionListByStudentId()}" varStatus="i">
					<c:if test="${versionId eq courseVersion.id }"><a title="${courseVersion.course.title}" style="height:90px;vertical-align:top; display:inline-block;width:72px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;" href="${ctx}/changeCourse">${courseVersion.course.title}</a></c:if>
				</c:forEach>
				&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="changeCourse()">切换课程</button>	&nbsp;&nbsp;&nbsp;&nbsp;
		        <a href="javascript:getMsg();">消息:&nbsp;&nbsp;<span id="MsgSpan" style="color: red;"></span></a>
		<div class="header-top-right">
			<p id="datetime">2016年7月 25号 星期二 12:00</p>
			<div class="quit">
				<p><a href="${ctx }/loginOut"><i></i>退出</a></p>
			</div>
		</div>
	</div>
</div> --%>
<div class="content-top-right">
				当前课程：
		  		<c:forEach var="courseVersion" items="${student:getCourseVersionListByStudentId()}" varStatus="i">
					<c:if test="${versionId eq courseVersion.id }"><a title="${courseVersion.title }" style="height:23px;vertical-align:top; display:inline-block;width:256px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;margin-top: 4px;" href="${ctx}/changeCourse">${courseVersion.title }</a></c:if>
				</c:forEach>
				&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="changeCourse()" style="background:#1c9993;padding:4px 6px;border-radius:5px;">切换课程</button>	&nbsp;&nbsp;&nbsp;&nbsp;
				<div class="header-top-right">
					<p class="header-top-left">欢迎您,<span><a href="${ctx}/updatePasswordPage" target="studentMainFrame">${user.name }</a></span>同学</p>
         			 <div class="header-middle">
  						<p><a href="javascript:getMsg();"><i class="h-message"></i><em id="emid" class=""><span id="MsgSpan"></span></em></a></p>
  						<p><a href="javascript:clickaaaa();" ><i class="h-email"></i></a></p>
  					</div>
					<div class="quit">
						<p><a href="${ctx }/loginOut"><i></i>退出</a></p>
					</div>
				</div>
  			</div>
<%-- <img src="${ctxStatic}/modules/teacher/img/bg.png" alt="" class="bgbg"> --%>
<%-- <div class="header-logo container clearfix">
	<img src="${ctxStatic}/modules/teacher/img/logo.png" alt="">
	<div class="header-lright">
		    <label for="" style="line-height:26px;">当前课程</label>
			<c:forEach var="courseVersion" items="${student:getCourseVersionListByStudentId()}" varStatus="i">
					<c:if test="${versionId eq courseVersion.id }"><a title="${courseVersion.course.title}" style="height:90px;vertical-align:top; display:inline-block;width:72px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;" href="${ctx}/changeCourse">${courseVersion.course.title}</a></c:if>
			</c:forEach>
			&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="changeCourse()">切换课程</button>
	
    </div>
</div> --%>






<%-- <style>
  .cr-header{clear: both; height: 124px; line-height: 124px; position: relative; left: 20px; font-size: 18px;color: #fff; }
  .cr-header a{ }
</style>
<div class="course-header">
	<div class="container clearfix">
		<div class="c-left">
			<img src="${ctxStatic}/modules/teacher/images/logo.png" alt="">
		</div>

		<div class="c-right">
		  <div class="cr-header">
		  	欢迎您,${user.name }同学 今天是：<span id="datetime"></span> &nbsp;&nbsp;&nbsp;&nbsp;当前课程：
		  		<c:forEach var="courseVersion" items="${student:getCourseVersionListByStudentId()}" varStatus="i">
					<c:if test="${versionId eq courseVersion.id }"><a title="${courseVersion.course.title}" style="height:90px;vertical-align:top; display:inline-block;width:72px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;" href="${ctx}/changeCourse">${courseVersion.course.title}</a></c:if>
				</c:forEach>
				&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="changeCourse()">切换课程</button>
				<a style="float: right; margin-right: 20px;" href="${ctx }/loginOut"><i></i>退出</a>
				<a style="float: right;margin-right: 30px;" href="javascript:getMsg();">消息:&nbsp;&nbsp;<span id="MsgSpan" style="color: red;"></span></a>
		  </div>
		</div>
	</div>
</div> --%>