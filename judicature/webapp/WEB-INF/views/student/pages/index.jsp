<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>
<%@ page import="java.util.*"%>
<html>
<head>
<meta name="decorator" content="student_default"/>
<%@include file="/WEB-INF/views/include/chart.jsp" %>
<title>首页</title>
</head>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/student/css/jq22.css">

<script language="javascript"> 
    var checkSubmitFlg = false; 
    function checkSubmit() { 
      if (checkSubmitFlg == true) { 
         return false; 
      } 
      checkSubmitFlg = true; 
      return true; 
   } 
   document.ondblclick = function docondblclick() { 
    window.event.returnValue = false; 
   } 
   document.onclick = function doconclick() { 
       if (checkSubmitFlg) { 
         window.event.returnValue = false; 
       } 
   } 
   
</script>

<body>
<style>
.stu-button button{background:#fff;border-radius:5px;margin-left:10px;padding:3px 10px;border:1px solid #ccc;color:#666;}
.stu-button button.current{background:#f7b652;border-radius:5px;border:0 none;color:#fff;}
.stu-tit{margin-left:40px;margin-top:20px;}
.stu-tit h2{font-size:18px;color:#666;margin-bottom:5px;line-height:20px;}
.stu-tit p{line-height:20px;color:#333;font-size:16px;}
.stu-tit p i{width:10px;height:10px;border-radius:100%;display:inline-block;background:#1c9993;margin-right:10px;}
.talk-icon{width:32px;height:32px;background:url(${ctxStatic}/img/talk_icon.png) no-repeat center;background-size:100%;margin: 15px 10px 0 0;display:inline-block;}
.talk-border{background:url(${ctxStatic}/img/br.jpg) no-repeat;width:270px;height:68px;}
</style>

<style type="text/css">
	.row{margin-left:0;}
	.progress_bar .pro-bar {
		background: hsl(0, 0%, 97%);
		box-shadow: 0 1px 2px hsla(0, 0%, 0%, 0.1) inset;
		height:4px;
		margin-bottom: 12px;
		margin-top: 55px;
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
		position: relative;
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
	<%
		Date date = new Date();
		request.setAttribute("date",date);
	%>
	<sys:message content="${message}"/>
	<script type="text/javascript">
		var message = '${message}';
		if(message == "修改密码成功"){
			window.location.href = "${ctx}/loginOut";
		}
	</script>
	<div class="main-right">
		<div class="content-right-con">
    			<div class="jsyy">
					<ul class="jsyy-list clearfix">
						<li>
							<a href="javascript:clickexam();"><img src="${ctxStatic}/img/sj.png" alt=""></a>
							<div class="list-text">
								<h2><a href="javascript:clickexam();">${examCountVo.examCount}份</a></h2>
								<p>即将开始的考试</p>
							</div>
						</li>
						<li>
							<a href="javascript:clickHomeWork();"><img src="${ctxStatic}/img/khzy.png" alt=""></a>
							<div class="list-text">
								<%-- <h2><a href="${ctx}/studentExam/homeWorkList?examType=3">${examCountVo.homeworkCount}份</a></h2> --%>
								<h2><a href="javascript:clickHomeWork();">${examCountVo.homeworkCount}份</a></h2>
								<p>待完成的作业</p>
							</div>
						</li>
						<li>
							<a href="javascript:clickquiz();"><img src="${ctxStatic}/img/stlx.png" alt=""></a>
							<div class="list-text">
								<h2><a href="javascript:clickquiz();">${quizCount}份</a></h2>
								<p>待完成的随堂练习</p>
							</div>
						</li>
						<li>
							<a href="javascript:clickexample();"><img src="${ctxStatic}/img/zxks.png" alt=""></a>
							<div class="list-text">
								<h2><a href="javascript:clickexample();">${exampleQuestionsCount}份</a></h2>
								<p>新发布的例题</p>
							</div>
						</li>
					</ul>
				</div>
				<div class="content-stu-sy clearfix">
					<div class="con-stu-left" style="height:302px;">
						<div class="stu-button" style="padding:10px 10px 0;color:#999;font-size:16px;">
							<p style="display:inline-block;margin-left:25px;font-size:18px;color:#666;">排名</p>
							<button id="button0" class='current' onclick="getOneMonthData('0')">全部</button>
							<button id="button1" onclick="getOneMonthData('1')">近一月</button>
							<button id="button2" onclick="getOneMonthData('2')">近两月</button>
							<button id="button3" onclick="getOneMonthData('3')">近三月</button>
						</div>
						<iframe id="studentRankingFrame" name="studentRankingFrame" src="${ctx}/studentNews/indexGradeChange" style="overflow: hidden;margin:0 auto;display:block;" scrolling="yes" frameborder="no" width="100%" height="263px;"></iframe>
						<script type="text/javascript">
							function getOneMonthData(duration){
								if('0' == duration){
									location.reload();
								}
								if('1' == duration){
									$('#button1').attr("class","current");
									$('#button0').removeClass("current");
									$('#button2').removeClass("current");
									$('#button3').removeClass("current");
								}
								if('2' == duration){
									$('#button2').attr("class","current");
									$('#button0').removeClass("current");
									$('#button1').removeClass("current");
									$('#button3').removeClass("current");
								}
								if('3' == duration){
									$('#button3').attr("class","current");
									$('#button0').removeClass("current");
									$('#button2').removeClass("current");
									$('#button1').removeClass("current");
								}
									$("#studentRankingFrame").attr("src","${ctx}/studentNews/indexGradeChange"+duration);
							}
						</script>
					</div>
					
					<div class="con-stu-right" style="height:302px;">
						<div class="demo">
							<div class="container" style="width:auto ! important;">
								<div class="row">
									<div class="col-md-offset-3 col-md-6" style="margin:0 auto;width:80%;">
										<h3 style="font-size:18px;color:#333;line-height:20px;margin-top:20px;">知识点通过率</h3>
										<c:forEach items="${reList}" var="teacherKnowledge" begin="1" end="4">
											<div class="progress_bar">
												<div class="pro-bar">
													<small class="progress_bar_title">
														<i style="width:135px;display:inline-block;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">${teacherKnowledge.examCode }</i>
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
				</div>
				<div class="content-stu clearfix">
					<div class="con-stu-left" style="height:274px;">
						<!-- 题库中试题总数和可供练习的数量对比 -->
						<div id="questionsCount" style="width: 225px;height: 254px;margin:10px auto;"></div>
						<script type="text/javascript">
						var questionsCountChart = echarts.init(document.getElementById('questionsCount'));
						$.ajax({
							url : '${ctx}/studentNews/getExercisesCount',
							type : 'post',
							cache : false,
							success : function(data){
								var obj = eval("("+data+")")
								var currentRate = obj.data[0].value/obj.data[1].value*100;
								currentRate = currentRate.toFixed(2);
								
								var errotRate = (obj.data[1].value - obj.data[0].value)/obj.data[1].value*100;
								errotRate = errotRate.toFixed(2);
								
								questionsCountChart.setOption({
									title: {
							            text: '题目正确率',
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
								        data:['正确率','错误率']
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
								                    value:currentRate,
								                    name:'正确率',
								                    itemStyle:{
								                        normal:{
								                            color:'rgb(132, 229, 212)'
								                        }
								                    }
								                },
								                {
								                    value:errotRate, 
								                    name:'错误率',
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
						
						</script>
						<%-- <img src="${ctxStatic}/img/st3.jpg" alt=""> --%>
					</div>
					<div class="con-stu-middle" style="height:274px;">
						<h2 style="padding: 0 75px;"><a href="javascript:clickhuwenhuda();" style="color:#666;">互问互答<i>More...</i></a></h2>
						<div style="width: 80%;margin:0 auto;">
							<table style="margin: 8px auto 0;">
							<c:forEach items="${uQuestionList }" var="questionAndAnswer">
								<c:if test="${not empty questionAndAnswer.questionDetail}">
									<tr style="height:95px;">
										<td class="talk-icon"></td>
										<td class="talk-border">
											<div style="margin:-22px 0 5px 20px;"><span style="color:#4dbba8;">${questionAndAnswer.questionGiver }</span><span style="float:right;margin-right:20px;color:#666;font-size:12px;"><fmt:formatDate pattern="HH:mm:ss" value="${questionAndAnswer.questionCreateTime }"/></span>
											</div>
											<div style="margin-left:20px;"><span style="color:#666;">questionStr</span></div>
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</table>
						</div>
					</div>
					<div class="con-stu-right" style="height:274px;">
						<div class="stu-tit">
							<h2>课程进度</h2>
							<p><i></i>已学习知识点:<span id="finishCourseSchedule"></span></p>
							<p><i></i>课程总知识点:<span id="allCourseSchedule"></span></p>
						</div>
						<div id="mainhour" style="width: 205px;height:185px;margin:0 auto;"></div>
						<script type="text/javascript">
							var myChartHour = echarts.init(document.getElementById('mainhour'));
							$.ajax({
								url:'${ctx}/studentNews/courseSchedule',
								type:'post',
								cache:false,
								success:function(data){
									var obj = eval("("+data+")")
									$('#finishCourseSchedule').html(obj.data[0].value);
									$('#allCourseSchedule').html(obj.data[1].value);
									
									myChartHour.setOption({
										/* title:{
									        text:'课程进度'
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
					
					
						<!-- <div id="mainhour" style="width: 209px;height:256px;float: right;"></div>
						<script type="text/javascript">
							var myChartHour = echarts.init(document.getElementById('mainhour'));
							$.ajax({
								url:'${ctx}/studentNews/courseSchedule',
								type:'post',
								cache:false,
								success:function(data){
									var obj = eval("("+data+")")
									myChartHour.setOption({
										title: {
								            text: '课程进度',
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
									        data:['已进行','未进行']
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
									            data:obj.data
									        }
									    ]
									});
								}
							});
						</script> -->
					
					
						<%-- <img src="${ctxStatic}/img/st5.jpg" alt=""> --%>
					</div>
				</div>
    		</div>
		</div>
		
		<script type="text/javascript">
		function clickexam(){
			window.parent.document.getElementById("left_exam").click();
		}
		
		function clickHomeWork(){
			window.parent.document.getElementById("left_homework").click();
		}
		
		function clickquiz(){
			window.parent.document.getElementById("left_quiz").click();
		}
		
		function clickhuwenhuda(){
			window.parent.document.getElementById("aaaa").click();
		}
		
		function clickexample(){
			window.parent.document.getElementById("left_example").click();
		}
		</script>
		
	</div>
</body>
</html>