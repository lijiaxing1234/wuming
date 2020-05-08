<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<script type="text/javascript" src="${ctxStatic}/js/date.js"></script>
<script type="text/javascript">
$(function() {
	$("#datetime").text(getCurDate());
	$("#select_courseId").on("change",function(){
		
		var $this=$('#select_courseId option:selected'); 
		    id=$this.val(),
		    courseId=$this.attr("courseId");
	/* 	$.ajax({
		     type: 'POST',
		     url: "${ctx}/changeTeacherCourseId",
		     data: {id:id,courseId:courseId} ,
		     success: function(data){
		     } ,
		     dataType: "json"
		}); */
		top.window.location.replace('${ctx}/changeTeacherCourseId?id='+id+"&courseId="+courseId+"&currentUrl=${currentUrl}");
	});
	
});
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
.bgbg{position:absolute;z-index:-1;width:100%;height:auto;top:1px;min-width:1200px;}
.header-logo{margin:15px auto;}
.header-logo img{float:left;}
.header-logo .header-lright{float:right;line-height:65px;}
.header-logo .header-lright label{line-height:67px ! important;margin-right:10px;color:#fff;font-size:16px;}
.header-logo .header-lright select{margin-bottom:3px;}
.header-content .header-lright{float: left;position: relative;}
.header-content .header-lright i{width: 1px;height: 32px;background: #cfcfcf;position: absolute;top: 0;right: 20px;}
.header-content .header-lright select{width: 314px;height: 32px;line-height: 32px;border-color: #cfcfcf;outline: 0 none;border-radius: 0;}
.header-content .cheader-right{float: right;margin-top: 5px;}
.header-content .cheader-right .header-top-left{color: #666;}
.header-content .cheader-right .header-top-left span{color: #249994;}
.header-top-right .quit{font-size: 16px;}
.cheader-right .header-middle {float: left;margin: -3px 10px 0;margin-top: -3px;}
.cheader-right .header-middle p{display: inline-block;width: 26px;height: 26px;border:1px solid #c7c7c7;border-radius: 50%;text-align: center;line-height: 26px;position: relative;margin-left: 5px;}
.cheader-right .header-middle p a{width: 26px;height: 26px;display: inline-block;}
.cheader-right .header-middle p i{background: url(${ctxStatic}/img/message_icon.png) no-repeat;width: 14px;height: 15px;display: inline-block;position:relative;top:5px;left:-1px;}
.cheader-right .header-middle p i.h-email{background: url(${ctxStatic}/img/h_email.png) no-repeat;width: 15px;height: 11px;}
.cheader-right .header-middle p i.h-page{background: url(${ctxStatic}/img/task.png) no-repeat;height: 13px;}
.cheader-right .header-middle p em{position: absolute;top: -7px;right: -7px;width: 19px;height: 19px;background: #e34d3c;border-radius: 50%;color: #fff;text-align: center;line-height: 19px;font-size: 12px;}
</style>

<%-- <div class="header-top">
	<div class="container clearfix">
		<p class="header-top-left">欢迎您,<span>${user.name }</span>老师</p>
		<div class="header-top-right">
			<p id="datetime">2016年7月 25号 星期二 12:00</p>
			<div class="quit">
				<p><a href="${ctx }/loginOut"><i></i>退出</a></p>
			</div>
		</div>
	</div>
</div>
<img src="${ctxStatic}/modules/teacher/img/bg.png" alt="" class="bgbg">
<div class="header-logo container clearfix">
	<img src="${ctxStatic}/modules/teacher/img/logo.png" alt="">
	<div class="header-lright">
	<label for="" style="line-height:26px;">当前课程</label>
	<c:set var="courseId" value="${courseVesion.courseId}" />
    <select  style="width: 270px; display: inline-block;" id="select_courseId">
	  <c:forEach var="item" items="${questionlib:getTeacherCourseVesion()}" varStatus="i">
	     <option value="${item.courseVesion.id}" courseId="${item.course.id}"  ${courseId eq item.course.id ? " selected='selected'" : "" }    >${item.courseVesion.title}</option>
     </c:forEach>
    </select>
    </div>
</div> --%>


<div class="header-content clearfix">
	<div class="header-lright">
		<i></i>
		<c:set var="courseId" value="${courseVesion.courseId}" />
    <select  style="width: 270px; display: inline-block;" id="select_courseId">
	  <c:forEach var="item" items="${questionlib:getTeacherCourseVesion()}" varStatus="i">
	     <option value="${item.courseVesion.id}" courseId="${item.course.id}"  ${courseId eq item.course.id ? " selected='selected'" : "" }    >${item.courseVesion.title}</option>
     </c:forEach>
    </select>
    </div>
  	<div class="cheader-right">
  		<p class="header-top-left">欢迎您,<span><a href="${ctx}/updatePasswordPage" target="teacherMainFrame">${user.name }</a></span>老师</p>
  		<div class="header-middle">
  			<p><a href="javascript:alertTeacherMsg()"><i class="h-message"></i><em id="temid" class="no-read-message"><span id="TMsgSpan"></span></em></a></p>
  		</div>
		<div class="header-top-right">
			<div class="quit">
				<p><a href="${ctx }/loginOut"><i></i>退出</a></p>
			</div>
		</div>
  	</div>
</div>
<script type="text/javascript">
	function alertTeacherMsg(){
		//获取消息并弹出
		$.ajax({
			url:'${ctx}/bbs/getTMsgText',
			type:'post',
			cache:false,
			success:function(data){
				var obj = eval("("+data+")");
				alertx(obj.msg);
			}
		});
	}

	function clickthuwenhuda(){
		$("#thuwenhuda").click();
	}
	$(function(){
		clock();
	});
	
	var timeClock1=setInterval(clock,10000);
	function clock(){
		 $.ajax({
		    	url:'${ctx}/bbs/getTMsgCount',
				type:'post',
				cache:false,
				success:function(data){
					var obj = eval("("+data+")");
					if(obj.msgNo != 0){
						console.log(obj.msgNo);
						document.getElementById("TMsgSpan").innerHTML = obj.msgNo;
						$("#temid").show();						
					}else{
						$("#temid").hide();
					}
				}
		    });
	}
	
</script>




<%-- 
<div class="course-header">
	<div class="container clearfix">
		<div class="c-left">
			<img src="${ctxStatic}/modules/teacher/images/logo.png" alt="">
		</div>
		<div class="c-right">
		    <div class="cr">
		    <div class="cr-nav">
		         	欢迎您,${user.name }老师 &nbsp;&nbsp;今天是：<span id="datetime"></span>&nbsp;&nbsp;&nbsp;&nbsp;
		         	<label for="" style="line-height:26px;">当前课程</label>
					<c:set var="courseId" value="${courseVesion.courseId}" />
				    <select  style="width: 270px; display: inline-block;" id="select_courseId">
					  <c:forEach var="item" items="${questionlib:getTeacherCourseVesion()}" varStatus="i">
					     <option value="${item.courseVesion.id}" courseId="${item.course.id}"  ${courseId eq item.course.id ? " selected='selected'" : "" }    >${item.course.title}</option>
					     <option value="${item.courseVesion.id}" courseId="${item.course.id}"  ${courseId eq item.course.id ? " selected='selected'" : "" }    >${item.courseVesion.title}</option>
				     </c:forEach>
				    </select>
		    </div>
			<div class="quit">
				<p><a href="${ctx }/loginOut"><i></i>退出</a></p>
			</div>
			<span style="clear: both;"></span>
			</div>
		</div>
	</div>
</div>

 --%>
