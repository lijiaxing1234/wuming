<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<meta name="decorator" content="teacher_default"/>
<%@include file="/WEB-INF/views/include/chart.jsp" %>
<c:set var="isTrue" value="${not empty practicePercent ? false :true}" />
<c:set  var="quesLibForExamBoolen" value="${quesLibForExamNumber gt 0  ? false:true}"/>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/student/css/jq22.css">
<style type="text/css">
	.row{margin-left:0;}
	.progress_bar .pro-bar {
		background: hsl(0, 0%, 97%);
		box-shadow: 0 1px 2px hsla(0, 0%, 0%, 0.1) inset;
		height:4px;
		margin-bottom: 12px;
		margin-top: 50px;
		position: relative;
	}
	.progress_bar .progress_bar_title{
		/*color: hsl(218, 4%, 50%);*/
		color: #666;
		font-size: 15px;
		font-weight: 300;
		position: relative;
		top: -28px;
		z-index: 1;
	}
	.progress_bar .progress_number{
		float: right;
		position:relative;
	}
	.progress_bar .progress-bar-inner {
		background-color: hsl(0, 0%, 88%);
		display: block;
		width: 0;
		height: 100%;
		position: absolute;
		top: 0;
		left: 0;
		transition: width 1s linear 0s;
		animation: animate-positive 2s;
	}
	.progress_bar .progress-bar-inner:before {
		content: "";
		background-color: hsl(0, 0%, 100%);
		border-radius: 50%;
		width: 4px;
		height: 4px;
		position: absolute;
		right: 1px;
		top: 0;
		z-index: 1;
	}
	.progress_bar .progress-bar-inner:after {
		content: "";
		width: 14px;
		height: 14px;
		background-color: inherit;
		border-radius: 50%;
		position: absolute;
		right: -4px;
		top: -5px;
	}
	@-webkit-keyframes animate-positive{
		0% { width: 0%; }
	}
	@keyframes animate-positive{
		0% { width: 0%; }
	}
</style>
<script type="text/javascript">
 $(function(){
	     $("ul.right-tit:first li:lt(2)").click(function(){
	    	 
	    	     try{
				   var container = parent.$("div.main-content > div.container"),
				       container_left=container.find("div.main-left:first");
				       container_right=container.find("div.content-right:first");
				       container_right.width(parseInt(container.width())-parseInt(container_left.width())-55);
				       
		    	 }catch(e){
		    		 
		    	 }
	    	 
			  var $this=$(this),
			      url=$this.find("a:first").attr("href")
			      title=$this.find("a:first").attr("title");
			// top.$.jBox("iframe:"+url,{
		     top.$.jBox("get:"+url,{
		    	    id:"jbox-addExam"+$this.index(),
					title:title,
					width: 500,
				    height: 300,
					buttons:{}, 
				    bottomText:"",
				    loaded:function(h){
						$(".jbox-content", top.document).css({"overflow-y":"hidden"});
					 	var content_right=$("div.main-content > div.container > div.content-right", top.document);
						   content_right.css({"width":content_right.width()-5}); 
					}
			 });  
			 return false;
		 });
 });
</script>
<style type="text/css">
 .stu-tit{margin-left:40px;margin-top:20px;}
 .stu-tit h2{font-size:18px;color:#666;margin-bottom:5px;line-height:20px;}
 .stu-tit p{line-height:20px;color:#333;font-size:16px;}
 .stu-tit p i{width:10px;height:10px;border-radius:100%;display:inline-block;background:#1c9993;margin-right:10px;}
 .div-index{ width: 100%; margin: 0 auto;}
 .div-index .right-tit {margin: 35px 0;}
 .div-index .right-tit li{float: left;width: 15%;height: 140px;background: #4aa7e0;font-size: 16px;color: #fff;text-align: center;margin-left: 3%;position: relative;}
 .div-index .right-tit li:nth-child(2){background: #ef8079;}
 .div-index .right-tit li:nth-child(3){background: #5acfc7;}
 .div-index .right-tit li:nth-child(4){background: #4fe2f6;}
 .div-index .right-tit li:last-child{background: #fdc345;}
 .div-index .right-tit li a{width: 150px;height: 123px;display: inline-block;position: absolute;top: 0;left: 0;}
 .div-index .right-tit li img{margin: 20px auto 12px;}
 .message-alert .message-con{padding-bottom:10px;}
 
 
 
.notice{width: 99%;background: #fffcf9;line-height: 30px;color: #333;border:1px solid #fda72e;border-radius: 3px;padding-bottom: 5px;margin-bottom:10px;}
.notice p i{width: 27px;height: 27px;display: inline-block;background: url(${ctxStatic}/modules/teacher/img/notice.png) no-repeat;margin: 0 10px 0 20px;position: relative;top: 7px;}
.notice p a{color: #ff6f44;font-size: 14px;} 
.notice p button{line-height:40px;margin-right:5px;}
.jsyy .jsyy-list{width: 99%;padding: 30px 0;border:1px solid #e5e5e5;list-style:none;}
.jsyy .jsyy-list li{float: left;text-align: center;margin-left: 7%;cursor:pointer;}
.jsyy .jsyy-list li p{margin-top: 20px;}
.jsyy .jsyy-list li p a{color: #333;}
.jsyy .jsyy-list li:first-child {margin-left:3%;}
.jsyy h2,
.xxts h2{font-size: 18px;color: #333;line-height: 55px;}
.xxts-list{width: 99%;padding: 25px 0;border:1px solid #e5e5e5;}
.xxts-list p{line-height: 30px;font-size:16px;padding-left:20px;}
.xxts-list p i{width: 19px;height: 19px;display: inline-block;background: url(${ctxStatic}/modules/teacher/img/notice_1.png) no-repeat;position: relative;top: 4px;margin-right: 15px;} 
.shiwu .xxts-list{padding:10px 0;}
.shiwu .xxts-list p{text-indent:20px;line-height:40px;font-size:16px;color:#666;}
/* .shiwu .xxts-list p span{display:block;} */
.xxts .xx-p{width:97%;}

.m-right-con .mright-con-left{float: left;width: 73%;margin-top: 20px;}
.m-right-con .mright-con-left .zjct{width: 100%;height: 277px;border:1px solid #eaeaea;margin-bottom: 20px;}
.m-right-con .mright-con-left .zjct img{width: 100%;height: 100%;}
.m-right-con .mright-con-left .m-progress{width: 100%;height: 219px;border:1px solid #eaeaea;}
.m-right-con .mright-con-left .m-progress img{width: 100%;height: 100%;}
.m-right-con .mright-con-right{float: left;margin-top: 20px;width: 25%;margin-left:10px;}
.m-right-con .mright-con-right .kcjd{border:1px solid #eaeaea;width: 100%;height: 277px;margin-bottom: 20px;}
.m-right-con .mright-con-right .kcjd img{width: 100%;height: 100%;}
.m-right-con .mright-con-right .zsd{border:1px solid #eaeaea;width: 100%;height: 219px;}
.m-right-con .mright-con-right .zsd img{width: 100%;height: 100%;}
/* @media screen  and (min-width:1799px) {
 	.jsyy .jsyy-list li{margin-left: 9%;}
} 
@media screen and (min-width:1249px) {
	.container{width: 99%;}
	 .jsyy .jsyy-list li{margin-left: 9%;}
} 
@media screen and (min-width: 1200px) {
	.main-right .notice p i{margin: 0 5px;}
}
@media screen and (min-width:1149px) {
	.jsyy .jsyy-list li{margin-left: 8%;}
}
@media screen and (min-width:1119px) {
	.jsyy .jsyy-list li{margin-left: 10%;}
	.notice,.jsyy .jsyy-list,.xxts-list,.xxts .xx-p{width:99%;}
} 
@media screen and (min-width:1048px) {
	.jsyy .jsyy-list li{margin-left: 5.5%;}
	.notice,.jsyy .jsyy-list,.xxts-list,.xxts .xx-p{width:98%;}
} 
@media screen and (min-width:960px) {
	.jsyy .jsyy-list li{margin-left: 3%;}
	.notice,.jsyy .jsyy-list,.xxts-list,.xxts .xx-p{width:66%;}
}  */

@media screen and and (max-width:1175px) {
	.jsyy .jsyy-list li{margin-left: 3%;}
}



.stu-button button{background:#fff;border-radius:5px;margin-left:10px;padding:3px 10px;border:1px solid #ccc;color:#666;}
.stu-button button.current{background:#f7b652;border-radius:5px;border:0 none;color:#fff;}
.stu-tit{margin-left:40px;margin-top:20px;}
.stu-tit h2{font-size:18px;color:#666;margin-bottom:5px;line-height:20px;}
.stu-tit p{line-height:20px;color:#333;font-size:16px;}
.stu-tit p span{margin-left:10px;}
.stu-tit p i{width:10px;height:10px;border-radius:100%;display:inline-block;background:#1c9993;margin-right:10px;}

</style>
<script type="text/javascript">

function getMsg(){
	 
	$.ajax({
		url:'${ctx}/bbs/getTMsgText',
		type:'post',
		cache:false,
		success:function(data){
			var obj = eval("("+data+")");
			 
			//document.getElementById("MsgSpan").innerHTML = 0;
			$("#xiaoxi").html("<i></i>"+obj.msg);
			//alertx(obj.msg);
		}
    });
}

 


</script>

<title>首页${quesLibForExamBoolen }</title>
</head>
<body>
<sys:message content="${message}"/>
<script type="text/javascript">
	var message = '${message}';
	if(message == "修改密码成功"){
		window.location.href = "${ctx}/loginOut";
	}
</script>
<div class="notice" id="messageFirst">
		<p>
		 <i></i>首次生成试卷需要设置练习题百分比，请点击<a  href="${ctx}/knowledge/index" target="teacherMainFrame"  onclick="try{parent.$('.navlist > li > a:eq(1)').trigger('click');}catch(e){}">设置</a>去设置百分比，若不设置则默认练习题百分比为100.0%
		 <button  class="close" style="margin-right: 20px;">×</button></p>
		
		 
	<%-- <div  style="padding-top:3px;">
		<font size="3px;">首次生成试卷需要设置练习题百分比，请点击<a  href="${ctx}/knowledge/index" target="teacherMainFrame"  onclick="parent.$('#content-menu li:eq(1)').trigger('click')">设置</a>去设置百分比，若不设置则默认练习题百分比为100.0%</font>
	    <button  class="close">×</button>
	</div> --%>
</div>

<div class="notice" id="msStr"  >
		 
		 <p  id="xiaoxi">
		 <i></i>
		     
	    </p>
 
</div>
<div class="jsyy">
	<!-- <h2>教师应用</h2> -->
  <ul class="right-tit jsyy-list clearfix">
	<li>
	    <a href="${ctx }/examination/examPager?examType=2" title="组卷考试"></a>
		<img src="${ctxStatic}/img/zj.png" alt="">
	</li>
	<li>
		<a href="${ctx }/examination/examPager?examType=5" title="在线考试"></a>
		<img src="${ctxStatic}/img/zx.png" alt="">
	</li>
	<li>
		<a href="${ctx }/examinationHomeWork/selectKnowledge" title="课后作业">
		<img src="${ctxStatic}/img/kh.png" alt=""></a>
	</li>
	<li>
		<a href="${ctx }/exerciseExamination/selectKnowledge" title="随堂练习">
		<img src="${ctxStatic}/img/st.png" alt=""></a>
	</li>
	<li>
		<a href="${ctx }/classExample/selectKnowledge" title="课堂例题">
		<img src="${ctxStatic}/img/kt.png" alt=""></a>
	</li>
</ul>
</div>
<div class="m-right-con clearfix">
	<div class="mright-con-left">
		<div class="zjct">
			<div class="stu-button" style="padding:10px 10px 5px;color:#999;font-size:16px;">
				<p style="display:inline-block;margin-left:25px;font-size:18px;color:#666;">出卷量</p>
				<button id="tbom0" class="current" onclick="getTeacherChart('0')">全部</button>
				<button id="tbom1" onclick="getTeacherChart('1')">近一月</button>
				<button id="tbom2" onclick="getTeacherChart('2')">近两月</button>
				<button id="tbom3" onclick="getTeacherChart('3')">近三月</button>
			</div>
			<iframe id="teacherMakeTest" name="teacherMakeTest" src="${ctx}/bbs/getTeacherCharts" style="overflow: hidden;margin:0 auto;display:block;" scrolling="yes" frameborder="no" width="643px" height="230px;"></iframe>
			<script type="text/javascript">
				function getTeacherChart(duration){
					if('0' == duration){
						location.reload();
					}
					if('1' == duration){
						$('#tbom1').attr("class","current");
						$('#tbom0').removeClass("current");
						$('#tbom2').removeClass("current");
						$('#tbom3').removeClass("current");
					}
					if('2' == duration){
						$('#tbom2').attr("class","current");
						$('#tbom0').removeClass("current");
						$('#tbom1').removeClass("current");
						$('#tbom3').removeClass("current");
					}
					if('3' == duration){
						$('#tbom3').attr("class","current");
						$('#tbom0').removeClass("current");
						$('#tbom2').removeClass("current");
						$('#tbom1').removeClass("current");
					}
					
					$("#teacherMakeTest").attr("src","${ctx}/bbs/getTeacherCharts?duration="+duration);
				}
			</script>
		</div>
		<div class="m-progress">
			<div style="width:665px;margin:0 auto;">
			<div id="main1" style="width: 190px;height:219px;float:left;"></div>
			<script type="text/javascript">
				var myChart = echarts.init(document.getElementById('main1'));
				$.ajax({
					url:'${ctx}/bbs/getTeacherMain1',
					type:'post',
					cache:false,
					success:function(data){
						var obj = eval("("+data+")")
						myChart.setOption({
							title: {
					            text: '正在进行的考试',
					            bottom: 4,
					            left:'center'
					        },
							tooltip: {
						        trigger: 'item',
						        formatter: "{a} <br/>{b}: {c} ({d}%)"
						    },
						    legend: {
						        orient: 'vertical',
						        x: 'left',
						        data:['进行中','其他']
						    },
						    series: [
						        {
						            type:'pie',
						            radius: ['50%', '70%'],
						            avoidLabelOverlap: false,
						            label: {
						                normal: {
						                    show: false,
						                    position: 'center'
						                },
						                emphasis: {
						                    show: true,
						                    textStyle: {
						                        fontSize: '30',
						                        fontWeight: 'bold'
						                    }
						                }
						            },
						            labelLine: {
						                normal: {
						                    show: false
						                }
						            },
						            data:[{
						                    value:obj.data[0].value,
						                    name:'进行中',
						                    itemStyle:{
						                        normal:{
						                            color:'rgb(132, 229, 212)'
						                        }
						                    }
						                },
						                {
						                    value:obj.data[1].value, 
						                    name:'其他',
						                    itemStyle:{
						                        normal:{
						                            color:'rgb(218, 236, 235)'
						                        }
						                    }
						                    
						                }
						            ]
						        }
						    ]
						});
					}
				});
				
				myChart.on('click',function(params){
					if(params.name == '进行中'){
						window.parent.document.getElementById("examManage").click();
						window.parent.document.getElementById("onLineExam").click();
					}
				});
			</script>
			
			<div id="main2" style="width: 190px;height:219px;float: left;margin:0 7%;"></div>
			<script type="text/javascript">
				var myChart2 = echarts.init(document.getElementById('main2'));
				$.ajax({
					url:'${ctx}/bbs/getTeacherMain2',
					type:'post',
					cache:false,
					success:function(data2){
						var obj2 = eval("("+data2+")")
						myChart2.setOption({
							title: {
					            text: '作业批改情况',
					            bottom: 4,
					            left:'center'
					        },
							tooltip: {
						        trigger: 'item',
						        formatter: "{a} <br/>{b}: {c} ({d}%)"
						    },
						    legend: {
						        orient: 'vertical',
						        x: 'left',
						        data:['已批改','未批改']
						    },
						    series: [
						        {
						            type:'pie',
						            radius: ['50%', '70%'],
						            avoidLabelOverlap: false,
						            label: {
						                normal: {
						                    show: false,
						                    position: 'center'
						                },
						                emphasis: {
						                    show: true,
						                    textStyle: {
						                        fontSize: '30',
						                        fontWeight: 'bold'
						                    }
						                }
						            },
						            labelLine: {
						                normal: {
						                    show: false
						                }
						            },
						            data:[{
						                    value:obj2.data[0].value,
						                    name:'已批改',
						                    itemStyle:{
						                        normal:{
						                            color:'rgb(149, 203, 210)'
						                        }
						                    }
						                },
						                {
						                    value:obj2.data[1].value, 
						                    name:'未批改',
						                    itemStyle:{
						                        normal:{
						                            color:'rgb(218, 236, 235)'
						                        }
						                    }
						                    
						                }
						            ]
						        }
						    ]
						});
					}
				});
				
				myChart2.on('click',function(params){
					if(params.name == '未批改'){
						window.parent.document.getElementById("homework").click();
					}
				});
			</script>
			
			<div id="main3" style="width: 190px;height:219px;float: left;"></div>
			<script type="text/javascript">
				var myChart3 = echarts.init(document.getElementById('main3'));
				$.ajax({
					url:'${ctx}/bbs/getTeacherMain3',
					type:'post',
					cache:false,
					success:function(data){
						var obj = eval("("+data+")")
						myChart3.setOption({
							title: {
					            text: '考试成绩录入',
					            bottom: 4,
					            left:'center'
					        },
							tooltip: {
						        trigger: 'item',
						        formatter: "{a} <br/>{b}: {c} ({d}%)"
						    },
						    legend: {
						        orient: 'vertical',
						        x: 'left',
						        data:['录入','未录入']
						    },
						    series: [
						        {
						            type:'pie',
						            radius: ['50%', '70%'],
						            avoidLabelOverlap: false,
						            label: {
						                normal: {
						                    show: false,
						                    position: 'center'
						                },
						                emphasis: {
						                    show: true,
						                    textStyle: {
						                        fontSize: '30',
						                        fontWeight: 'bold'
						                    }
						                }
						            },
						            labelLine: {
						                normal: {
						                    show: false
						                }
						            },
						            data:[{
						                    value:obj.data[0].value,
						                    name:'录入',
						                    itemStyle:{
						                        normal:{
						                            color:'rgb(35, 139, 110)'
						                        }
						                    }
						                },
						                {
						                    value:obj.data[1].value, 
						                    name:'未录入',
						                    itemStyle:{
						                        normal:{
						                            color:'rgb(218, 236, 235)'
						                        }
						                    }
						                    
						                }
						            ]
						        }
						    ]
						});
					}
				});
				
				myChart3.on('click',function(params){
					if(params.name == '未录入'){
						window.parent.document.getElementById("examManage").click();
						window.parent.document.getElementById("onLineExam").click();
					}
				});
			</script>
			
			<%-- <img src="${ctxStatic}/img/t2.jpg" alt=""> --%>
			</div>
		</div>
	</div>
	<div class="mright-con-right">
		<div class="kcjd">
			<div class="stu-tit">
				<h2>课程进度</h2>
				<p><i></i>已学习知识点<span id="finishCourseSchedule"></span></p>
				<p><i style="background:#84e5d4;"></i>课程总知识点<span id="allCourseSchedule"></span></p>
			</div>
		
			<div id="mainCourse" style="width: 190px;height:219px;margin:10px auto 0;"></div>


			<script type="text/javascript">
				var myChartCourse = echarts.init(document.getElementById('mainCourse'));
				$.ajax({
					url:'${ctx}/bbs/courseSchedule',
					type:'post',
					cache:false,
					success:function(data){
						var obj = eval("("+data+")");
						$('#finishCourseSchedule').html(  obj.data[0].value);
						$('#allCourseSchedule').html( obj.data[1].value);

						
						myChartCourse.setOption({
							/* title:{
						       // text:'课程进度'
						    }, */
							tooltip : {
						        formatter: "{a} <br/>{b} : {c}%"
						    },
						    toolbox: {
						        feature: {
						            restore: {},
						            saveAsImage: {}
						        }
						    },
						    series: [
						        {
						            name: '已学习知识点',
						            type: 'gauge',
						            detail: {formatter:'{value}%'},
						            data: [{value: 75, name: '完成率'}],
						            startAngle:180,
						            endAngle:0,
						            splitNumber:5,
						            data : {
						                value:(obj.data[0].value/obj.data[1].value*100).toFixed(2)
						            },
						            axisLine:{
						                lineStyle:{
						                    color:[[0.2, 'rgb(132, 229, 212)'], [0.8, 'rgb(132, 229, 212)'], [1, 'rgb(132, 229, 212)']],
						                    width:10
						                }
						            },
						            splitLine:{
						                length:30
						            }
						        }
						    ]
						});
					}
				});
			</script>
		
		
		</div>
		<div class="demo" style="width: 100%;height:218px;margin:10px auto 0;border:1px solid #e2e2e2;">
			<div class="container">
				<div class="row">
					<div class="col-md-offset-3 col-md-6" style="width:80%;margin:0 auto;">
						<h3 style="font-size:18px;color:#333;line-height:20px;margin-top:20px;">知识点通过率</h3>
						<c:forEach items="${sourcelist}" var="teacherKnowledge" begin="1" end="3">
							<div class="progress_bar">
								<div class="pro-bar">
									<small class="progress_bar_title">
										${teacherKnowledge.knowledgeTitle }
										<span  class="progress_number">%</span>
									</small>
									<span class="progress-bar-inner" style="background-color: #f4e101; width: ${teacherKnowledge.passingRate }%;" data-value="${teacherKnowledge.passingRate }" data-percentage-value="${teacherKnowledge.passingRate }"></span>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		
		<script type="text/javascript">
		    $(function(){
		        var progress = $(".progress-bar-inner");
		        progress.each(function (i)
		        {
		            var data = $(this).attr('data-value');
		            $(this).prev().find("span").html(data+"%");
		        });
		    });
		</script>
		
		
</div>

<script type="text/javascript" src="${ctxStatic}/jquery-plugin/jquery.cookies.2.2.0.min.js"></script>
<script type="text/javascript">
 (function(){
	 $("#messageFirst").find(".close:button").click(function(){
		 var flag="none";
		 var flag2="";
		 $.cookie('messageFirst_${teacher.id}',flag, {expires : 3650 });
		 $("#messageFirst").css({"display":flag});
		 $("#msStr").css({"display":flag2});
		 
	 });
	 if (typeof ($.cookie('messageFirst_${teacher.id}')) != 'undefined') {
	  $("#messageFirst").css({"display":$.cookie('messageFirst_${teacher.id}')});
	 
	  
	 }
	 var temp= $("#messageFirst").is(":hidden");//是否隐藏 
	 
	  if($("#messageFirst").is(":hidden")) 
	  { 
		  $("#msStr").css({"display":""});
	   
	  }else
		  {
		  $("#msStr").css({"display":"none"});
		  }
 })();
 
 
 getMsg();
</script>

</body>
</html>