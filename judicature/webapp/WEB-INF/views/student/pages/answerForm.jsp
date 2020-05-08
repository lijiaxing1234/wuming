<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" id="viewport" />
<meta name="decorator" content="student_exam" />
<title>在线答题</title>
<link rel="shortcut icon" href="${prc }/static/student/favicon.ico">
<link rel="stylesheet"	href="${prc }/static/student/2.3.1/css_default/bootstrap.css">
<link rel="stylesheet" href="${prc }/static/student/css/main.css">
<link href="${prc }/static/student/css/exam.css" rel="stylesheet"	type="text/css">
<script type="text/javascript"	src="${prc }/static/student/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${prc }/static/student/js/main.js"></script>
<!-- 倒计时插件 -->
<link rel="stylesheet" href="${prc }/static/countdown/jquery.countdown.css">
<script src="${prc }/static/countdown/jquery.countdown.js"></script>

<script src="${ctxStatic}/js/jquery.alerts.js" type="text/javascript"></script>
<link href="${ctxStatic}/js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />

<script src="${ctxStatic}/kindeditor/kindeditor-all-min.js" type="text/javascript"></script>
<style>

/*兼容FireFox*/
body {
	-moz-user-focus: ignore;
	-moz-user-select: none;
}
</style>
<script type="text/javascript">
	//防止页面后退
	history.pushState(null, null, document.URL);
	window.addEventListener('popstate', function () {
	    history.pushState(null, null, document.URL);
	});
</script>
<style type="text/css">
body{background:#fff ! important;}
.exam .header{height:167px;border:1px solid #e5e5e5;margin:20px auto;}
.btn-sub{background:#39abb6;width:114px;height:35px;color:#fff;text-align:center;line-height:35px;cursor:pointer;border-radius:3px;margin-top:10px;}
.paper{min-height:800px;border:1px solid #e5e5e5;margin-left:10px;}
.answer_title{background:#39aab6;height:50px;line-height:50px;color:#fff;text-align:center;font-size:16px;}
.timedownclass{color:#39aab6;}
.user{height:90px;padding-top:30px;border-left:1px solid #e5e5e5;border-right:1px solid #e5e5e5;}
.answer{border:1px solid #e5e5e5;border-top:0 none;}
.answer_body{margin:0 auto;padding:10px 0;}
.answer_body h3{font-size:16px;}
.answer_body table{border-left:0 none;margin:0 auto;}
.h-text p{line-height:30px;}
.answer_body table td{display:inline-block;margin-right:5px;border:1px solid #d1d1d1;width:27px;height:29px;line-height:32px;border-radius:3px;margin-bottom:5px;}
.answer_body table td:last-child{margin-right:0;}
</style>
</head>
<body>
	<input id="durationTime" type="hidden" value="${exam.durationTime }"/>
	<input id="examDetailId" type="hidden" value="${examDetailId}">
	<div class="exam container">
		<div class="header">
			<table width="1200px" border="0" cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<td>
							<div class="header_title clearfix" style="width: 745px; padding-left: 30px;padding-top:25px;">
								<img src="${ctxStatic}/img/exam.png" alt="" style="float:left;margin-right:20px;" >
								<div style="float:left;margin-top:20px;" class="h-text"> 
									<p>${exam.title }</p> 
									<p id="msg" style="font-size:20px;color:#333;">本套试卷 <c:if test="${not empty exam.totalScore }">共<span style="color:#ff7044;">${exam.totalScore }</span>分，</c:if>共<span style="color:#ff7044;">${examQuestionCount}</span>道试题。</p>
									<p class="time" id="ztesttime" style="color:#333;margin-left:0;text-indent:0;padding-top:0;">考试时间<span style="color:#ff7044;">${exam.residueTime }</span>分钟</p>
								</div>
								
				
							</div>
						</td>
						<%-- <td style="width: 350px;">
							<div class="time" id="ztesttime" style="">考试时间${exam.residueTime }分钟</div>
						</td> --%>
						<td style="width: 120px; padding-right: 30px">
							<div class="btn-sub right" style="background-position: -114px -359px;" onclick="submitAnswer()">
								提交答卷
								<!-- <input type="button" onclick="submitAnswer()" class="btn" style="cursor: pointer; background-position: 0px -360px;"> -->
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<form id="questionForm"  method="post">
			<input type="hidden" id="examId" name="examId" value="${exam.id }"/>
			<input type="hidden" id="returnUrl" name="returnUrl" value="${returnUrl }">
			<input type="hidden" id="examType" name="examType" value="${examType }">
			
			
			<c:set var="styleBackgound"   value="style='background-color:#8FC31F'"/>
			<c:set var="checked"   value="checked='checked'"/>
			
			
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="/* border-top: 2px solid #56a0a1; */">
				<tbody>
					<tr>
						<td valign="top" style="background: #fff; width: 227px; border-top: 1px solid #e5e5e5;">
							<div class="user">
									<div class="timedown">
										<h1 id="timedown1"></h1>
										<input id="beginBtn1" type="hidden"/>
										<input id="stopBtn1" type="hidden"/>
										<input id="resetBtn1" type="hidden"/>
									</div>
									<script>
										 $(function(){
											var durationTime = $("#durationTime").val();
											$("#timedown1").countdown({
												time_length: durationTime, //输入的秒数
								                begin_btn_id: 'beginBtn1',
								                stop_btn_id: 'stopBtn1',
								                reset_btn_id: 'resetBtn1',
								                time_ding_dang: [0,0],//时间触发事件述责 当时间在10的时候出发一个时间在
								                bian_se_time: 30,//变色时间点设置
								                arrival_time: 600,//时间到达10的时候触发函数
								                se_code: "red",//变色支持16进制颜色如＃ff0000
								                onFlagTime:function(){//当到设定时间时候触发事件time_ding_dang
								                	mustSubmitAnswer();
								                },
								                onArrivalTime:function(){
								                	jAlert("您还有10分钟的答题时间！");
								                }
											});
										 });
									</script>
									
								<div class="username">
									<p style="font-size:14px;">考生：${student.name }</p>
								</div>
							</div>
							
					
							<div class="answer">
								<div class="answer_title">答题卡</div>
								<div class="answer_body" id="answerContent"	style="min-height: 200px; overflow-x: hidden; overflow-y: auto;">
									<div id="navContent">
										<c:if test="${not empty sQuestionList}">
											<h3>单选题	共${sQuestionPoint }分</h3>
											<table border="0" cellspacing="0" cellpadding="0">
												<tbody>
													<c:forEach items="${sQuestionList}" var="sQuestion" varStatus="i" begin="0" step="1">
														<tr>
															<c:forEach items="${sQuestionList}" var="sQuestion" varStatus="j" begin="${(i.count-1)*5 }" step="1" end="${5*i.count -1 }">
																<td id="${sQuestion.id }"   ${not empty student:getClassByProperty(sQuestion.studentAnswer,"answer") ? styleBackgound:"" }  >
																	<a href="#sQuestion${j.index }" style="cursor: pointer;display:inline-block;width:27px;height:29px;" >${j.index + 1}</a>
																</td>
															</c:forEach>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>
										<c:if test="${not empty sMQuestionList}">
											<h3>多选题	共${sMQuestionPoint }分</h3>
											<table border="0" cellspacing="0" cellpadding="0">
												<tbody>
													<c:forEach items="${sMQuestionList}" var="sMQuestion" varStatus="i" begin="0">
														<tr>
															<c:forEach items="${sMQuestionList}" var="sMQuestion" varStatus="j" begin="${(i.count-1)*5  }" step="1" end="${5*i.count - 1 }">
																<td id="${sMQuestion.id }"  ${not empty student:getClassByProperty(sMQuestion.studentAnswer,"answer") ? styleBackgound:"" }  >
																	<a href="#sMQuestion${j.index }" style="cursor: pointer;display:inline-block;width:27px;height:29px;" >${j.index + 1}</a>
																</td>
															</c:forEach>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>
										<c:if test="${not empty fQuestionList}">
											<h3>填空题	共${fQuestionPoint }分</h3>
											<table border="0" cellspacing="0" cellpadding="0">
												<tbody>
														<c:forEach items="${fQuestionList}" var="fQuestion" varStatus="i" begin="0">
															<tr>
																<c:forEach items="${fQuestionList}" var="fQuestion" varStatus="j" begin="${(i.count-1)*5 }" step="1" end="${5*i.count - 1 }">
																	
																	<td id="${fQuestion.id }"> 
																		 <c:if test="${not empty student:getClassByProperty(fQuestion.studentAnswer,'answer')}">
																	 		 <c:choose>
																		 		<c:when test="${fQuestion.count eq fn:length(student:getClassByPropertys(fQuestion.studentAnswer,'answer')) }">
																		 			style="background-color:#8FC31F"
																		 		</c:when>
																		 		<c:when test="${fQuestion.count ne fn:length(student:getClassByPropertys(fQuestion.studentAnswer,'answer')) }">
																		 			style="background-color:#FFF100"
																		 		</c:when>
																			 </c:choose>
																		 </c:if>
																	 	<a href="#fQuestion${j.index }" style="cursor: pointer;display:inline-block;width:27px;height:29px;" >${j.index + 1}</a>
																	</td>
																	 
																</c:forEach>
															</tr>
														</c:forEach>
												</tbody>
											</table>
										</c:if>
										<c:if test="${not empty panduanQuestionList}">
											<h3>判断题	共${panduanQuestionPoint }分</h3>
											<table border="0" cellspacing="0" cellpadding="0">
												<tbody>
													<c:forEach items="${panduanQuestionList}" var="sMQuestion" varStatus="i" begin="0">
														<tr>
															<c:forEach items="${panduanQuestionList}" var="sMQuestion" varStatus="j" begin="${(i.count-1)*5  }" step="1" end="${5*i.count - 1 }">
																	<td id="${sMQuestion.id }"  ${not empty student:getClassByProperty(sMQuestion.studentAnswer,"answer") ? styleBackgound:"" } >
																		<a href="#panduanQuestion${j.index }" style="cursor: pointer;display:inline-block;width:27px;height:29px;" >
																			${j.index + 1}
																		</a>
																	</td>
															</c:forEach>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>
										<c:if test="${not empty gainianQuestionList}">
											<h3>概念题	共${gainianQuestionPoint }分</h3>
											<table border="0" cellspacing="0" cellpadding="0">
												<tbody>
													<c:forEach items="${gainianQuestionList}" var="sMQuestion" varStatus="i" begin="0">
														<tr>
															<c:forEach items="${gainianQuestionList}" var="sMQuestion" varStatus="j" begin="${(i.count-1)*5  }" step="1" end="${5*i.count - 1 }">
																<td id="${sMQuestion.id }">
																	<a href="#gainianQuestion${j.index }" style="cursor: pointer;display:inline-block;width:27px;height:29px;" >
																		${j.index + 1}
																	</a>
																</td>
															</c:forEach>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>
										<c:if test="${not empty sAQuestionList}">
											<h3>简答题	共${sAQuestionPoint }分</h3>
											<table border="0" cellspacing="0" cellpadding="0">
												<tbody>
													<c:forEach items="${sAQuestionList}" var="sAQuestion" varStatus="i" begin="0">
														<tr>
															<c:forEach items="${sAQuestionList}" var="sAQuestion" varStatus="j" begin="${(i.count-1)*5  }" step="1" end="${5*i.count - 1 }">
																<td id="${sAQuestion.id }">
																	<a href="#sAQuestion${j.index }" style="cursor: pointer;display:inline-block;width:27px;height:29px;" >
																		${j.index + 1}
																	</a>
																</td>
															</c:forEach>
														</tr>
													</c:forEach>
												</tbody>
											</table>					
										</c:if>
										
										<c:if test="${not empty cQuestionList}">
											<h3>计算题	共${cQuestionPoint }分</h3>
											<table border="0" cellspacing="0" cellpadding="0">
												<tbody>
													<c:forEach items="${cQuestionList}" var="cQuestion" varStatus="i" begin="0">
														<tr>
															<c:forEach items="${cQuestionList}" var="cQuestion" varStatus="j" begin="${(i.count-1)*5  }" step="1" end="${5*i.count - 1 }">
																<td id="${cQuestion.id }">
																	<a href="#cQuestion${j.index }" style="cursor: pointer;display:inline-block;width:27px;height:29px;" >
																		${j.index + 1}
																	</a>
																</td>
															</c:forEach>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>
										
										<c:if test="${not empty zongheQuestionList}">
											<h3>综合题	共${zongheQuestionPoint }分</h3>
											<table border="0" cellspacing="0" cellpadding="0">
												<tbody>
													<c:forEach items="${zongheQuestionList}" var="sMQuestion" varStatus="i" begin="0">
														<tr>
															<c:forEach items="${zongheQuestionList}" var="sMQuestion" varStatus="j" begin="${(i.count-1)*5  }" step="1" end="${5*i.count - 1 }">
																<td id="${sMQuestion.id }">
																	<a href="#zongheQuestion${j.index }" style="cursor: pointer;display:inline-block;width:27px;height:29px;" >
																		${j.index + 1}
																	</a>
																</td>
															</c:forEach>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>
										<c:if test="${not empty zhituQuestionList}">
											<h3>制图题	共${zhituQuestionPoint }分</h3>
											<table border="0" cellspacing="0" cellpadding="0">
												<tbody>
													<c:forEach items="${zhituQuestionList}" var="sMQuestion" varStatus="i" begin="0">
														<tr>
															<c:forEach items="${zhituQuestionList}" var="sMQuestion" varStatus="j" begin="${(i.count-1)*5  }" step="1" end="${5*i.count - 1 }">
																<td id="${sMQuestion.id }">
																	<a href="#zhituQuestion${j.index }" style="cursor: pointer;display:inline-block;width:27px;height:29px;" >
																		${j.index + 1}
																	</a>
																</td>
															</c:forEach>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>
										<c:if test="${not empty zhibiaoQuestionList}">
											<h3>制表题	共${zhibiaoQuestionPoint }分</h3>
											<table border="0" cellspacing="0" cellpadding="0">
												<tbody>
													<c:forEach items="${zhibiaoQuestionList}" var="sMQuestion" varStatus="i" begin="0">
														<tr>
															<c:forEach items="${zhibiaoQuestionList}" var="sMQuestion" varStatus="j" begin="${(i.count-1)*5  }" step="1" end="${5*i.count - 1 }">
																
																	<td id="${sMQuestion.id }">
																		<a href="#zhibiaoQuestion${j.index }" style="cursor: pointer;display:inline-block;width:27px;height:29px;" >
																			${j.index + 1}
																		</a>
																	</td>
															</c:forEach>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>
										<c:if test="${not empty shituQuestionList}">
											<h3>识图题	共${shituQuestionPoint }分</h3>
											<table border="0" cellspacing="0" cellpadding="0">
												<tbody>
													<c:forEach items="${shituQuestionList}" var="sMQuestion" varStatus="i" begin="0">
														<tr>
															<c:forEach items="${shituQuestionList}" var="sMQuestion" varStatus="j" begin="${(i.count-1)*5  }" step="1" end="${5*i.count - 1 }">
																
																	<td id="${sMQuestion.id }">
																		<a href="#shituQuestion${j.index }" style="cursor: pointer;display:inline-block;width:27px;height:29px;" >
																			${j.index + 1}
																		</a>
																	</td>
															</c:forEach>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>
										
									</div>
									<br>
									&nbsp;&nbsp;<span>说明：</span> <br>
									<div class="tushi" style="float: right;"></div>
									<div style="clear:both;"></div>
								</div>
							</div>
						</td>
						<td valign="top">
							<div class="paper"  style="overflow: hidden;height:auto;">
								<div class="paper_title"></div>
								<%-- <div class="tip">
									<table width="95%" border="0" cellspacing="0" cellpadding="0">
										<tbody>
											<tr>
												<td class="tip_left"></td>
												<td class="tip_bg"><div id="msg">本套试卷 <c:if test="${not empty exam.totalScore }">共${exam.totalScore }分，</c:if>共${examQuestionCount}道试题。</div></td>
												<td class="tip_right">&nbsp;</td>
											</tr>
										</tbody>
									</table>
								</div> --%>
								<%-- <div class="cal_fonts" style="width: 960px;">
									<div class="cal">
										<a id="showcabutton" style="cursor: pointer;"><img
											src="${prc }/static/student/images/exam/icon_calculator.gif" width="94" height="17"
											border="none"></a>
									</div>
									<div id="timelimitDiv" class="timelimit" style="display: none;"></div>
									<br clear="all">
								</div> --%>
								
								
								<div id="topicContent" style="height: auto ! important;  overflow-y: hidden; overflow-x: hidden; width: 951px;">
									<!-- 题型 1：单选题 2：填空题 3：计算题 4：简答题 5：多选题 6：概念题 7：综合题 8：制图题 9：制表题 10：识图题 11：判断题 -->
									<!-- 单选题 -->
									<div class="basetype" id="basetype_1" style="display: block;">
										<c:if test="${not empty sQuestionList}">
											<div class="subject" style="width: 905px;">
												<p>单选题</p>
											</div>
											<c:forEach items="${sQuestionList}" var="sQuestion" varStatus="i" begin="0">
												<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
													<!-- 题号 -->
													<div class="problem_num">
														<div class="sign_title">
															第<span>${i.index + 1}</span>题，本小题 ${sQuestion.quesPoint } 分<a name="sQuestion${i.index}" ></a>
														</div>
													</div>
													<dl>
														<dt>${sQuestion.title }</dt>
														<!-- 选项 -->
														<c:if test="${not empty sQuestion.choice0 }">
															<dd>
																<input ${ student:getClassByProperty(sQuestion.studentAnswer,"answer0") eq "A" ? checked:"" }      type="radio"       topicno="1" id="stmda_1_0${sQuestion.id }" name="${sQuestion.id }"  value="A" onclick="answerSQ('${sQuestion.id}','A','1')"> A:
																<label for="stmda_1_0${sQuestion.id }" id="stmxx_1_0${sQuestion.id }" style="color: black;">${sQuestion.choice0 }</label>
															</dd>
														</c:if>	
														<c:if test="${not empty sQuestion.choice1 }">
															<dd>
																<input ${ student:getClassByProperty(sQuestion.studentAnswer,"answer0") eq "B" ? checked:"" }    type="radio" topicno="1" id="stmda_1_1${sQuestion.id }" name="${sQuestion.id }" value="B" onclick="answerSQ('${sQuestion.id}','B','1')"> B:
																<label for="stmda_1_1${sQuestion.id }" id="stmxx_1_1${sQuestion.id }" style="color: black;">${sQuestion.choice1 }</label>
															</dd>
														</c:if>
														<c:if test="${not empty sQuestion.choice2 }">
															<dd>
																<input  ${ student:getClassByProperty(sQuestion.studentAnswer,"answer0") eq "C" ? checked:"" }  type="radio" topicno="1" id="stmda_1_2${sQuestion.id }" name="${sQuestion.id }" value="C" onclick="answerSQ('${sQuestion.id}','C','1')"> C:
																<label for="stmda_1_2${sQuestion.id }" id="stmxx_1_2${sQuestion.id }" style="color: black;">${sQuestion.choice2 }</label>
															</dd>
														</c:if>
														<c:if test="${not empty sQuestion.choice3 }">
															<dd>
																<input  ${ student:getClassByProperty(sQuestion.studentAnswer,"answer0") eq "D" ? checked:"" }   type="radio" topicno="1" id="stmda_1_3${sQuestion.id }" name="${sQuestion.id }" value="D" onclick="answerSQ('${sQuestion.id}','D','1')"> D:
																<label for="stmda_1_3${sQuestion.id }" id="stmxx_1_3${sQuestion.id }" style="color: black;">${sQuestion.choice3 }</label>
															</dd>
														</c:if>
														<c:if test="${not empty sQuestion.choice4 }">
															<dd>
																<input ${ student:getClassByProperty(sQuestion.studentAnswer,"answer0") eq "E" ? checked:"" }   type="radio" topicno="1" id="stmda_1_4${sQuestion.id }" name="${sQuestion.id }" value="E" onclick="answerSQ('${sQuestion.id}','D','1')"> E:
																<label for="stmda_1_4${sQuestion.id }" id="stmxx_1_4${sQuestion.id }" style="color: black;">${sQuestion.choice4 }</label>
															</dd>
														</c:if>
														<c:if test="${not empty sQuestion.choice5 }">
															<dd>
																<input ${ student:getClassByProperty(sQuestion.studentAnswer,"answer0") eq "F" ? checked:"" }  type="radio" topicno="1" id="stmda_1_5${sQuestion.id }" name="${sQuestion.id }" value="F" onclick="answerSQ('${sQuestion.id}','F','1')"> F:
																<label for="stmda_1_5${sQuestion.id }" id="stmxx_1_5${sQuestion.id }" style="color: black;">${sQuestion.choice5 }</label>
															</dd>
														</c:if>
														<c:if test="${not empty sQuestion.choice6 }">
															<dd>
																<input ${ student:getClassByProperty(sQuestion.studentAnswer,"answer0") eq "G" ? checked:"" }  type="radio" topicno="1" id="stmda_1_6${sQuestion.id }" name="${sQuestion.id }" value="G" onclick="answerSQ('${sQuestion.id}','G','1')"> G:
																<label for="stmda_1_6${sQuestion.id }" id="stmxx_1_6${sQuestion.id }" style="color: black;">${sQuestion.choice6 }</label>
															</dd>
														</c:if>
														<c:if test="${not empty sQuestion.choice7 }">
															<dd>
																<input ${ student:getClassByProperty(sQuestion.studentAnswer,"answer0") eq "H" ? checked:"" }   type="radio" topicno="1" id="stmda_1_7${sQuestion.id }" name="${sQuestion.id }" value="H" onclick="answerSQ('${sQuestion.id}','H','1')"> H:
																<label for="stmda_1_7${sQuestion.id }" id="stmxx_1_7${sQuestion.id }" style="color: black;">${sQuestion.choice7 }</label>
															</dd>
														</c:if>
														<c:if test="${not empty sQuestion.choice8 }">
															<dd>
																<input  ${ student:getClassByProperty(sQuestion.studentAnswer,"answer0") eq "I" ? checked:"" }  type="radio" topicno="1" id="stmda_1_8${sQuestion.id }" name="${sQuestion.id }" value="I" onclick="answerSQ('${sQuestion.id}','I','1')"> I:
																<label for="stmda_1_8${sQuestion.id }" id="stmxx_1_8${sQuestion.id }" style="color: black;">${sQuestion.choice8 }</label>
															</dd>
														</c:if>
														<c:if test="${not empty sQuestion.choice9 }">
															<dd>
																<input ${ student:getClassByProperty(sQuestion.studentAnswer,"answer0") eq "J" ? checked:"" }  type="radio" topicno="1" id="stmda_1_9${sQuestion.id }" name="${sQuestion.id }" value="J" onclick="answerSQ('${sQuestion.id}','J','1')"> J:
																<label for="stmda_1_9${sQuestion.id }" id="stmxx_1_9${sQuestion.id }" style="color: black;">${sQuestion.choice9 }</label>
															</dd>
														</c:if>								
													</dl>
												</div>
											</c:forEach>
										</c:if>
										<c:if test="${not empty sMQuestionList}">
											<div class="subject" style="width: 905px;">
												<p>多选题</p>
											</div>
											<c:forEach items="${sMQuestionList}" var="sMQuestion" varStatus="i" begin="0">
												<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
													<!-- 题号 -->
													<div class="problem_num">
														<div class="sign_title">
															第<span>${i.index + 1}</span>题，本小题 ${sMQuestion.quesPoint } 分<a name="sMQuestion${i.index}" ></a>
														</div>
													</div>
													<dl>
														<dt>${sMQuestion.title }</dt>
														<!-- 选项 -->
														<c:if test="${not empty sMQuestion.choice0 }">
															<dd>
																<input type="checkbox" ${ student:getClassByProperty(sMQuestion.studentAnswer,"answer0") eq "A" ? checked:"" }   topicno="1" id="tmda_1_0${sMQuestion.id}" name="${sMQuestion.id}" value="A" onchange="answerSQM('${sMQuestion.id}', this,'5')"> A:
																<label for="tmda_1_0${sMQuestion.id}" id="tmxx_1_0${sMQuestion.id}" style="color: black;">${sMQuestion.choice0 }</label>
															</dd>
														</c:if>	
														<c:if test="${not empty sMQuestion.choice1 }">
															<dd>
																<input ${ student:getClassByProperty(sMQuestion.studentAnswer,"answer1") eq "B" ? checked:"" }   type="checkbox" topicno="1" id="tmda_1_1${sMQuestion.id}" name="${sMQuestion.id}" value="B" onchange="answerSQM('${sMQuestion.id}', this,'5')"> B:
																<label for="tmda_1_1${sMQuestion.id}" id="tmxx_1_1${sMQuestion.id}" style="color: black;">${sMQuestion.choice1 }</label>
															</dd>
														</c:if>
														<c:if test="${not empty sMQuestion.choice2 }">
															<dd>
																<input ${ student:getClassByProperty(sMQuestion.studentAnswer,"answer2") eq "C" ? checked:"" }  type="checkbox" topicno="1" id="tmda_1_2${sMQuestion.id}" name="${sMQuestion.id}" value="C" onchange="answerSQM('${sMQuestion.id}', this,'5')"> C:
																<label for="tmda_1_2${sMQuestion.id}" id="tmxx_1_2${sMQuestion.id}" style="color: black;">${sMQuestion.choice2 }</label>
															</dd>
														</c:if>
														<c:if test="${not empty sMQuestion.choice3 }">
															<dd>
																<input ${ student:getClassByProperty(sMQuestion.studentAnswer,"answer3") eq "D" ? checked:"" }  type="checkbox" topicno="1" id="tmda_1_3${sMQuestion.id}" name="${sMQuestion.id}" value="D" onchange="answerSQM('${sMQuestion.id}', this,'5')"> D:
																<label for="tmda_1_3${sMQuestion.id}" id="tmxx_1_3${sMQuestion.id}" style="color: black;">${sMQuestion.choice3 }</label>
															</dd>
														</c:if>
														<c:if test="${not empty sMQuestion.choice4 }">
															<dd>
																<input ${ student:getClassByProperty(sMQuestion.studentAnswer,"answer4") eq "E" ? checked:"" } type="checkbox" topicno="1" id="tmda_1_4${sMQuestion.id}" name="${sMQuestion.id}" value="E" onchange="answerSQM('${sMQuestion.id}', this,'5')"> E:
																<label for="tmda_1_4${sMQuestion.id}" id="tmxx_1_4${sMQuestion.id}" style="color: black;">${sMQuestion.choice4 }</label>
															</dd>
														</c:if>
														<c:if test="${not empty sMQuestion.choice5 }">
															<dd>
																<input ${ student:getClassByProperty(sMQuestion.studentAnswer,"answer5") eq "F" ? checked:"" } type="checkbox" topicno="1" id="tmda_1_5${sMQuestion.id}" name="${sMQuestion.id}" value="F" onchange="answerSQM('${sMQuestion.id}', this,'5')"> F:
																<label for="tmda_1_5${sMQuestion.id}" id="tmxx_1_5${sMQuestion.id}" style="color: black;">${sMQuestion.choice5 }</label>
															</dd>
														</c:if>
														<c:if test="${not empty sMQuestion.choice6 }">
															<dd>
																<input ${ student:getClassByProperty(sMQuestion.studentAnswer,"answer6") eq "G" ? checked:"" } type="checkbox" topicno="1" id="tmda_1_6${sMQuestion.id}" name="${sMQuestion.id}" value="G" onchange="answerSQM('${sMQuestion.id}', this,'5')"> G:
																<label for="tmda_1_6${sMQuestion.id}" id="tmxx_1_6${sMQuestion.id}" style="color: black;">${sMQuestion.choice6 }</label>
															</dd>
														</c:if>
														<c:if test="${not empty sMQuestion.choice7 }">
															<dd>
																<input ${ student:getClassByProperty(sMQuestion.studentAnswer,"answer7") eq "H" ? checked:"" } type="checkbox" topicno="1" id="tmda_1_7${sMQuestion.id}" name="${sMQuestion.id}" value="H" onchange="answerSQM('${sMQuestion.id}', this,'5')"> H:
																<label for="tmda_1_7${sMQuestion.id}" id="tmxx_1_7${sMQuestion.id}" style="color: black;">${sMQuestion.choice7 }</label>
															</dd>
														</c:if>
														<c:if test="${not empty sMQuestion.choice8 }">
															<dd>
																<input ${ student:getClassByProperty(sMQuestion.studentAnswer,"answer8") eq "I" ? checked:"" } type="checkbox" topicno="1" id="tmda_1_8${sMQuestion.id}" name="${sMQuestion.id}" value="I" onchange="answerSQM('${sMQuestion.id}', this,'5')"> I:
																<label for="tmda_1_8${sMQuestion.id}" id="tmxx_1_8${sMQuestion.id}" style="color: black;">${sMQuestion.choice8 }</label>
															</dd>
														</c:if>
														<c:if test="${not empty sMQuestion.choice9 }">
															<dd>
																<input ${ student:getClassByProperty(sMQuestion.studentAnswer,"answer9") eq "J" ? checked:"" } type="checkbox" topicno="1" id="tmda_1_9${sMQuestion.id}" name="${sMQuestion.id}" value="J" onchange="answerSQM('${sMQuestion.id}', this,'5')"> J:
																<label for="tmda_1_9${sMQuestion.id}" id="tmxx_1_9${sMQuestion.id}" style="color: black;">${sMQuestion.choice9 }</label>
															</dd>
														</c:if>								
													</dl>
												</div>
											</c:forEach>
										</c:if>
										<c:if test="${not empty fQuestionList}">
											<div class="subject" style="width: 905px;">
												<p>填空题</p>
											</div>
											<c:forEach items="${fQuestionList}" var="fQuestion" varStatus="i" begin="0">
												<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
													<!-- 题号 -->
													<div class="problem_num">
														<div class="sign_title">
															第<span>${i.index + 1}</span>题，本小题 ${fQuestion.quesPoint } 分<a name="fQuestion${i.index}" ></a>
														</div>
													</div>
													<dl>
														<dt>${fQuestion.title }</dt>
													</dl>
													<c:if test="${not empty fQuestion.answer0 }">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第一空填：<input type="text" name="${fQuestion.id}" value="${student:getClassByProperty(fQuestion.studentAnswer,'answer0') }" onblur="answerFQ('${fQuestion.id}',this,'2','0','${fQuestion.count}')"><br>
													</c:if>
													<c:if test="${not empty fQuestion.answer1 }">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第二空填：<input type="text" name="${fQuestion.id}" value="${student:getClassByProperty(fQuestion.studentAnswer,'answer1') }" onblur="answerFQ('${fQuestion.id}',this,'2','1','${fQuestion.count}')"><br>
													</c:if>
													<c:if test="${not empty fQuestion.answer2 }">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第三空填：<input type="text" name="${fQuestion.id}" value="${student:getClassByProperty(fQuestion.studentAnswer,'answer2') }" onblur="answerFQ('${fQuestion.id}',this,'2','2','${fQuestion.count}')"><br>
													</c:if>
													<c:if test="${not empty fQuestion.answer3 }">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第四空填：<input type="text" name="${fQuestion.id}" value="${student:getClassByProperty(fQuestion.studentAnswer,'answer3') }" onblur="answerFQ('${fQuestion.id}',this,'2','3','${fQuestion.count}')"><br>
													</c:if>
													<c:if test="${not empty fQuestion.answer4 }">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第五空填：<input type="text" name="${fQuestion.id}" value="${student:getClassByProperty(fQuestion.studentAnswer,'answer4') }" onblur="answerFQ('${fQuestion.id}',this,'2','4','${fQuestion.count}')"><br>
													</c:if>
													<c:if test="${not empty fQuestion.answer5 }">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第六空填：<input type="text" name="${fQuestion.id}" value="${student:getClassByProperty(fQuestion.studentAnswer,'answer5') }" onblur="answerFQ('${fQuestion.id}',this,'2','5','${fQuestion.count}')"><br>
													</c:if>
													<c:if test="${not empty fQuestion.answer6 }">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第七空填：<input type="text" name="${fQuestion.id}" value="${student:getClassByProperty(fQuestion.studentAnswer,'answer6') }" onblur="answerFQ('${fQuestion.id}',this,'2','6','${fQuestion.count}')"><br>
													</c:if>
													<c:if test="${not empty fQuestion.answer7 }">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第八空填：<input type="text" name="${fQuestion.id}" value="${student:getClassByProperty(fQuestion.studentAnswer,'answer7') }" onblur="answerFQ('${fQuestion.id}',this,'2','7','${fQuestion.count}')"><br>
													</c:if>
													<c:if test="${not empty fQuestion.answer8 }">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第九空填：<input type="text" name="${fQuestion.id}" value="${student:getClassByProperty(fQuestion.studentAnswer,'answer8') }" onblur="answerFQ('${fQuestion.id}',this,'2','8','${fQuestion.count}')"><br>
													</c:if>
													<c:if test="${not empty fQuestion.answer9 }">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第十空填：<input type="text" name="${fQuestion.id}" value="${student:getClassByProperty(fQuestion.studentAnswer,'answer9') }" onblur="answerFQ('${fQuestion.id}',this,'2','9','${fQuestion.count}')"><br>
													</c:if>
												</div>
											</c:forEach>
										</c:if>
										
										<c:if test="${not empty panduanQuestionList}">
											<div class="subject" style="width: 905px;">
												<p>判断题</p>
											</div>
											<c:forEach items="${panduanQuestionList }" var="panduanQuestion" varStatus="i" begin="0">
												<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
													<!-- 题号 -->
													<div class="problem_num">
														<div class="sign_title">
															第<span>${i.index + 1}</span>题，本小题 ${panduanQuestion.quesPoint } 分<a name="panduanQuestion${i.index}" ></a>
														</div>
													</div>
													<dl>
														<dt>${panduanQuestion.title }</dt>
														
														<dd>
															正确：<input type="radio"   ${ student:getClassByProperty(panduanQuestion.studentAnswer,"answer0") eq "1" ? checked:"" } value="1"   name="panduan${i.index + 1}" onclick="answerSQ('${panduanQuestion.id}','1','11')">
															错误：<input type="radio"   ${ student:getClassByProperty(panduanQuestion.studentAnswer,"answer0") eq "2" ? checked:"" } value="2"   name="panduan${i.index + 1}" onclick="answerSQ('${panduanQuestion.id}','0','11')">
														</dd>
													</dl>
												</div>
											</c:forEach>
										</c:if>
										
										<c:if test="${not empty gainianQuestionList}">
											<div class="subject" style="width: 905px;">
												<p>概念题</p>
											</div>
											<c:forEach items="${gainianQuestionList}" var="cQuestion" varStatus="i" begin="0">
												<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
													<!-- 题号 -->
													<div class="problem_num">
														<div class="sign_title">
															第<span>${i.index + 1}</span>题，本小题 ${cQuestion.quesPoint } 分<a name="gainianQuestion${i.index}" ></a>
														</div>
													</div>
													<dl>
														<dt>${cQuestion.title }</dt>
														<dd style="margin-top: 60px;">
															<textarea id= 'gainianTextArea${i.index}' name="gainianTextArea${i.index}" rows="5" cols="40">${student:getStudentAnswer(cQuestion.studentAnswer,"answer0")}</textarea>
															
														   <script type="text/javascript">
														    
														  	 $(function(){
																	KindEditor.create('textarea[name="gainianTextArea${i.index}"]', {
																		width : '75%',
																		height : '450px',
																		resizeType : 1,
																	    items: ['bold', 'italic', 'underline', 'strikethrough', 'removeformat', '|','subscript','superscript',
																                'shcode', 'image', 'quote', '|'],
																     uploadJson : '/fileUploadUtil/kindeditorupload',
															         fileManagerJson : '/fileUploadUtil/kindeditorfilemanager',
															         extraFileUploadParams:{},
															         allowFileManager : false,
															         allowImageRemote:true,
															         useContextmenu:true,
															         afterBlur: function(){this.sync();},
															         afterChange:function(){
															        	 var data=this.html();
															        	 if(data){
															        		 answerSQ('${cQuestion.id}', data ,'6')
															        	 }else{
															        		 $("#${cQuestion.id}").css("backgroundColor","#fff");
															        	 }
															         }
																	});
															   });
															</script>
															
															<%--
															<sys:ckeditor replace="gainianTextArea${i.index}" uploadPath="/upload/answerFile" height="200" />
															<script type="text/javascript">
																CKEDITOR.instances["gainianTextArea${i.index}"].on("instanceReady", function () {  
															        this.document.on("keyup", function (){
															        	var data = CKEDITOR.instances['gainianTextArea${i.index}'].getData();
															        	answerSQ('${cQuestion.id}', data ,'6')
															        });  
															    });  
															</script> --%>
														</dd>
													</dl>
												</div>
											</c:forEach>
										</c:if>
										
										<c:if test="${not empty sAQuestionList}">
											<div class="subject" style="width: 905px;">
												<p>简答题</p>
											</div>
											<c:forEach items="${sAQuestionList}" var="sAQuestion" varStatus="i" begin="0">
												<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
													<!-- 题号 -->
													<div class="problem_num">
														<div class="sign_title">
															第<span>${i.index + 1}</span>题，本小题 ${sAQuestion.quesPoint } 分<a name="sAQuestion${i.index}" ></a>
														</div>
													</div>
													<dl>
														<dt>${sAQuestion.title }</dt>
														<dd style="margin-top: 60px;">
															<textarea id= 'jiandaTextArea${i.index }' name="jiandaTextArea${i.index}" rows="5" cols="40" >${student:getStudentAnswer(sAQuestion.studentAnswer,"answer0")}</textarea>
															<script type="text/javascript">
															 $(function(){
																KindEditor.create('textarea[name="jiandaTextArea${i.index}"]', {
																	width : '75%',
																	height : '450px',
																	resizeType : 1,
																    items: ['bold', 'italic', 'underline', 'strikethrough', 'removeformat', '|','subscript','superscript',
															                'shcode', 'image', 'quote', '|'],
															     uploadJson : '/fileUploadUtil/kindeditorupload',
														         fileManagerJson : '/fileUploadUtil/kindeditorfilemanager',
														         extraFileUploadParams:{},
														         allowFileManager : false,
														         allowImageRemote:true,
														         useContextmenu:true,
														         afterBlur: function(){this.sync();},
														         afterChange:function(){
														        	 var data=this.html();
														        	 if(data){
														        		 answerSQ('${sAQuestion.id}', data ,'4')
														        	 }else{
														        		 $("#${sAQuestion.id}").css("backgroundColor","#fff");
														        	 }
														         }
																});
															  });
															</script>
															
															<%--<sys:ckeditor replace="jiandaTextArea${i.index}" uploadPath="/upload/answerFile" height="200" />
															<script type="text/javascript">
																CKEDITOR.instances["jiandaTextArea${i.index}"].on("instanceReady", function () {  
															        this.document.on("keyup", function (){
															        	var data = CKEDITOR.instances['jiandaTextArea${i.index}'].getData();
															        	answerSQ('${sAQuestion.id}', data ,'4')
															        });  
															    });  
															</script> --%>
														</dd>
													</dl>
												</div>
											</c:forEach>
										</c:if>
										
										<c:if test="${not empty cQuestionList}">
											<div class="subject" style="width: 905px;">
												<p>计算题</p>
											</div>
											<c:forEach items="${cQuestionList }" var="cQuestion" varStatus="i">
												<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
													<!-- 题号 -->
													<div class="problem_num">
														<div class="sign_title">
															第<span>${i.index + 1}</span>题，本小题 ${cQuestion.quesPoint } 分<a name="cQuestion${i.index}" ></a>
														</div>
													</div>
													<dl>
														<dt>${cQuestion.title }</dt>
														<dd style="margin-top: 60px;">
															<textarea id= 'jisuanTextArea${i.index }' name="jisuanTextArea${i.index}" rows="5" cols="40">${student:getStudentAnswer(cQuestion.studentAnswer,"answer0")}</textarea>
															<script type="text/javascript">
															 $(function(){
																KindEditor.create('textarea[name="jisuanTextArea${i.index}"]', {
																	width : '75%',
																	height : '450px',
																	resizeType : 1,
																    items: ['bold', 'italic', 'underline', 'strikethrough', 'removeformat', '|','subscript','superscript',
															                'shcode', 'image', 'quote', '|'],
															     uploadJson : '/fileUploadUtil/kindeditorupload',
														         fileManagerJson : '/fileUploadUtil/kindeditorfilemanager',
														         extraFileUploadParams:{},
														         allowFileManager : false,
														         allowImageRemote:true,
														         useContextmenu:true,
														         afterBlur: function(){this.sync();},
														         afterChange:function(){
														        	 var data=this.html();
														        	 if(data){
														        		 answerSQ('${cQuestion.id}', data ,'3')
														        	 }else{
														        		 $("#${cQuestion.id}").css("backgroundColor","#fff");
														        	 }
														         }
																});
																});
															</script>
															<%--<sys:ckeditor replace="jisuanTextArea${i.index}" uploadPath="/upload/answerFile" height="200" />
															<script type="text/javascript">
																CKEDITOR.instances["jisuanTextArea${i.index}"].on("instanceReady", function () {  
															        this.document.on("keyup", function (){
															        	var data = CKEDITOR.instances['jisuanTextArea${i.index}'].getData();
															        	answerSQ('${cQuestion.id}', data ,'4')
															        });  
															    });  
															</script> --%>
														</dd>
													</dl>
												</div>
											</c:forEach>
										</c:if>
										
										
										<!-- 题型 1：单选题 2：填空题 3：计算题 4：简答题 5：多选题 6：概念题 7：综合题 8：制图题 9：制表题 10：识图题 11：判断题 -->
										
										<c:if test="${not empty zongheQuestionList}">
											<div class="subject" style="width: 905px;">
												<p>综合题</p>
											</div>
											<c:forEach items="${zongheQuestionList}" var="cQuestion" varStatus="i" begin="0">
												<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
													<!-- 题号 -->
													<div class="problem_num">
														<div class="sign_title">
															第<span>${i.index + 1}</span>题，本小题 ${cQuestion.quesPoint } 分<a name="zongheQuestion${i.index}" ></a>
														</div>
													</div>
													<dl>
														<dt>${cQuestion.title }</dt>
														<dd style="margin-top: 60px;">
															<textarea id= 'zongheTextArea${i.index}' name="zongheTextArea${i.index}" rows="5" cols="40">${student:getStudentAnswer(cQuestion.studentAnswer,"answer0")}</textarea>
															  <script type="text/javascript">
															  $(function(){
																KindEditor.create('textarea[name="zongheTextArea${i.index}"]', {
																	width : '75%',
																	height : '450px',
																	resizeType : 1,
																    items: ['bold', 'italic', 'underline', 'strikethrough', 'removeformat', '|','subscript','superscript',
															                'shcode', 'image', 'quote', '|'],
															     uploadJson : '/fileUploadUtil/kindeditorupload',
														         fileManagerJson : '/fileUploadUtil/kindeditorfilemanager',
														         extraFileUploadParams:{},
														         allowFileManager : false,
														         allowImageRemote:true,
														         useContextmenu:true,
														         afterBlur: function(){this.sync();},
														         afterChange:function(){
														        	 var data=this.html();
														        	 if(data){
														        		 answerSQ('${cQuestion.id}', data ,'7')
														        	 }else{
														        		 $("#${cQuestion.id}").css("backgroundColor","#fff");
														        	 }
														         }
																});
																});
															</script>
															
															<%--
															<sys:ckeditor replace="zongheTextArea${i.index}" uploadPath="/upload/answerFile" height="200" />
															<script type="text/javascript">
																CKEDITOR.instances["zongheTextArea${i.index}"].on("instanceReady", function () {  
															        this.document.on("keyup", function (){
															        	var data = CKEDITOR.instances['zongheTextArea${i.index}'].getData();
															        	answerSQ('${cQuestion.id}', data ,'7')
															        });  
															    });  
															</script> --%>
														</dd>
													</dl>
												</div>
											</c:forEach>
										</c:if>
										<c:if test="${not empty zhituQuestionList}">
											<div class="subject" style="width: 905px;">
												<p>制图题</p>
											</div>
											<c:forEach items="${zhituQuestionList}" var="cQuestion" varStatus="i" begin="0">
												<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
													<!-- 题号 -->
													<div class="problem_num">
														<div class="sign_title">
															第<span>${i.index + 1}</span>题，本小题 ${cQuestion.quesPoint } 分<a name="zhituQuestion${i.index}" ></a>
														</div>
													</div>
													<dl>
														<dt>${cQuestion.title }</dt>
														<dd style="margin-top: 60px;">
															<textarea id= 'zhituTextArea${i.index}' name="zhituTextArea${i.index}" rows="5" cols="40">${student:getStudentAnswer(cQuestion.studentAnswer,"answer0")}</textarea>
															<script type="text/javascript">
															 $(function(){
																KindEditor.create('textarea[name="zhituTextArea${i.index}"]', {
																	width : '75%',
																	height : '450px',
																	resizeType : 1,
																    items: ['bold', 'italic', 'underline', 'strikethrough', 'removeformat', '|','subscript','superscript',
															                'shcode', 'image', 'quote', '|'],
															     uploadJson : '/fileUploadUtil/kindeditorupload',
														         fileManagerJson : '/fileUploadUtil/kindeditorfilemanager',
														         extraFileUploadParams:{},
														         allowFileManager : false,
														         allowImageRemote:true,
														         useContextmenu:true,
														         afterBlur: function(){this.sync();},
														         afterChange:function(){
														        	 var data=this.html();
														        	 if(data){
														        		 answerSQ('${cQuestion.id}', data ,'8')
														        	 }else{
														        		 $("#${cQuestion.id}").css("backgroundColor","#fff");
														        	 }
														         }
																});
																});
															</script>
															
															<%--
															<sys:ckeditor replace="zhituTextArea${i.index}" uploadPath="/upload/answerFile" height="200" />
															<script type="text/javascript">
																CKEDITOR.instances["zhituTextArea${i.index}"].on("instanceReady", function () {  
															        this.document.on("keyup", function (){
															        	var data = CKEDITOR.instances['zhituTextArea${i.index}'].getData();
															        	answerSQ('${cQuestion.id}', data ,'8')
															        });  
															    });  
															</script> --%> 
														</dd>
													</dl>
												</div>
											</c:forEach>
										</c:if>
										<c:if test="${not empty zhibiaoQuestionList}">
											<div class="subject" style="width: 905px;">
												<p>制表题</p>
											</div>
											<c:forEach items="${zhibiaoQuestionList}" var="cQuestion" varStatus="i" begin="0">
												<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
													<!-- 题号 -->
													<div class="problem_num">
														<div class="sign_title">
															第<span>${i.index + 1}</span>题，本小题 ${cQuestion.quesPoint } 分<a name="zhibiaoQuestion${i.index}" ></a>
														</div>
													</div>
													<dl>
														<dt>${cQuestion.title }</dt>
														<dd style="margin-top: 60px;">
															<textarea id= 'zhibiaoTextArea${i.index}' name="zhibiaoQuestion${i.index}" rows="5" cols="40">${student:getStudentAnswer(cQuestion.studentAnswer,"answer0")}</textarea>
															<script type="text/javascript">
															 $(function(){
																KindEditor.create('textarea[name="zhibiaoQuestion${i.index}"]', {
																	width : '75%',
																	height : '450px',
																	resizeType : 1,
																    items: ['bold', 'italic', 'underline', 'strikethrough', 'removeformat', '|','subscript','superscript',
															                'shcode', 'image', 'quote', '|'],
															     uploadJson : '/fileUploadUtil/kindeditorupload',
														         fileManagerJson : '/fileUploadUtil/kindeditorfilemanager',
														         extraFileUploadParams:{},
														         allowFileManager : false,
														         allowImageRemote:true,
														         useContextmenu:true,
														         afterBlur: function(){this.sync();},
														         afterChange:function(){
														        	 var data=this.html();
														        	 if(data){
															        	 answerSQ('${cQuestion.id}', data ,'9')
														        	 }else{
														        		 $("#${cQuestion.id}").css("backgroundColor","#fff");
														        	 }
														         }
																});
																});
															</script>
															<%-- 
															<sys:ckeditor replace="zhibiaoTextArea${i.index}" uploadPath="/upload/answerFile" height="200" />
															<script type="text/javascript">
																CKEDITOR.instances["zhibiaoTextArea${i.index}"].on("instanceReady", function () {  
															        this.document.on("keyup", function (){
															        	var data = CKEDITOR.instances['zhibiaoTextArea${i.index}'].getData();
															        	answerSQ('${cQuestion.id}', data ,'9')
															        });  
															    });  
															</script>--%>
														</dd>
													</dl>
												</div>
											</c:forEach>
										</c:if>
										<c:if test="${not empty shituQuestionList}">
											<div class="subject" style="width: 905px;">
												<p>识图题</p>
											</div>
											<c:forEach items="${shituQuestionList}" var="cQuestion" varStatus="i" begin="0">
												<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
													<!-- 题号 -->
													<div class="problem_num">
														<div class="sign_title">
															第<span>${i.index + 1}</span>题，本小题 ${cQuestion.quesPoint } 分<a name="shituQuestion${i.index}" ></a>
														</div>
													</div>
													<dl>
														<dt>${cQuestion.title }</dt>
														<dd style="margin-top: 60px;">
															<textarea id= 'shituTextArea${i.index}' name="shituTextArea${i.index}" rows="5" cols="40">${student:getStudentAnswer(cQuestion.studentAnswer,"answer0")}</textarea>
															<script type="text/javascript">
															 $(function(){
																KindEditor.create('textarea[name="shituTextArea${i.index}"]', {
																	width : '75%',
																	height : '450px',
																	resizeType : 1,
																    items: ['bold', 'italic', 'underline', 'strikethrough', 'removeformat', '|','subscript','superscript',
															                'shcode', 'image', 'quote', '|'],
															     uploadJson : '/fileUploadUtil/kindeditorupload',
														         fileManagerJson : '/fileUploadUtil/kindeditorfilemanager',
														         extraFileUploadParams:{},
														         allowFileManager : false,
														         allowImageRemote:true,
														         useContextmenu:true,
														         afterBlur: function(){this.sync();},
														         afterChange:function(){
														        	 var data=this.html();
														        	 if(data){
														        		 answerSQ('${cQuestion.id}', data ,'10')
														        	 }else{
														        		 $("#${cQuestion.id}").css("backgroundColor","#fff");
														        	 }
														         }
																});
																});
															</script>
															<%-- 
															<sys:ckeditor replace="shituTextArea${i.index}" uploadPath="/upload/answerFile" height="200" />
															<script type="text/javascript">
																CKEDITOR.instances["shituTextArea${i.index}"].on("instanceReady", function () {  
															        this.document.on("keyup", function (){
															        	var data = CKEDITOR.instances['shituTextArea${i.index}'].getData();
															        	answerSQ('${cQuestion.id}', data ,'10')
															        });  
															    });  
															</script>--%>
														</dd>
													</dl>
												</div>
											</c:forEach>
										</c:if>
										
										<div class="btn_bottom" style="width: 950px; margin: 30px 0 50px;">
											<div class="btn"  onclick="submitAnswer()" style="background-position: -114px 0; width:218px;height:36px;text-align:center;line-height:36px;border-radius:3px;color:#fff;cursor:pointer;background:#39abb6;text-shadow:none;">
												提交答卷
												<!-- <input type="button" onclick="submitAnswer()" class="btn btn_submit" style="cursor: pointer; background-position: 0px -360px;"> -->
											</div>
										</div>
								</div>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<div></div>
		</form>
	</div>
	<script type="text/javascript">
	
	

	var examId = $("#examId").val();
	var examDetailId = $("#examDetailId").val();

	/* 题目只有一个答案的时候  单选题 计算题 简答题 */
	function answerSQ(questionId,answer,questionType){
		if(Object.prototype.toString.call(answer) === "[object String]"){
		}else{
			answer = answer.value;
		}
		

		if(answer !== null && answer !== undefined && answer !== ''){
			
			
			$.ajax({
				url:"${ctx}/studentAnswer/answerQuestion",
				type:"post",
				cache:false,
				data:{questionId:questionId,answer0:answer,examId:examId,questionType:questionType},
				success:function(data){
					if(data === 'false'){
						mustSubmitAnswer();
					}else{
						$("#"+questionId).css("backgroundColor","#8FC31F");
					}
					
					if(data === 'isAnswer' ){
						jAlert("您已在其他终端完成此次考试，请勿重复提交");
						setTimeout(function(){
							window.location.href = "${ctx}${studentPath}";
						},5000);
					}
				}
			})
		}
	}
	
		
		/* 多选题  */
		function answerSQM(questionId,obj,questionType){
			
			var id_array = new Array();
			var checkBoxName = $(obj).attr("name");
			$('input[name="'+checkBoxName+'"]:checked').each(function(){
				id_array.push($(this).val());	//向数组中添加元素
			});
			var idstr = id_array.join(','); //将数组元素连接起来拼成字符串
			$.ajax({
				url:"${ctx}/studentAnswer/answerQuestion",
				type:"post",
				cache:false,
				data:{questionId:questionId,answer0:idstr,examId:examId,questionType:questionType},
				success:function(data){
					if(data === 'false'){
						mustSubmitAnswer();
					}else{
						if(id_array.length == 0){
							$("#"+questionId).css("backgroundColor","#fff");						
						}else{
							$("#"+questionId).css("backgroundColor","#8FC31F");
						}
					}
					
					if(data === 'isAnswer' ){
						jAlert("您已在其他终端完成此次考试，请勿重复提交");
						setTimeout(function(){
							window.location.href = "${ctx}${studentPath}";
						},5000);
					}
				}
			});
		}
		
		/* 填空题 */
		function answerFQ(questionId,obj,questionType,orderNo,count){
			var obj = document.getElementsByName(questionId);
			var valueCount = 0;
			for(var i = 0; i < obj.length; i++){
				if(obj[i].value !== null && obj[i].value !== undefined && obj[i].value !== '' && $.trim(obj[i].value) !== ''){
					valueCount++;
				}
			}
			
			var val_array = new Array();
			var inputName = $(obj).attr('name');
			$('input[name="'+inputName+'"]').each(function(){
				
				if($(this).val().length == 0){
					val_array.push(" ");
				}else{
					val_array.push($(this).val());
				}
			});
			
			var valstr = val_array.join(',');
			$.ajax({
				url:"${ctx}/studentAnswer/answerQuestion",
				type:"post",
				cache:false,
				data:{questionId:questionId,answer0:valstr,examId:examId,questionType:questionType,orderNo:orderNo},
				success:function(data){
					if(data === 'false'){
						mustSubmitAnswer();
					}else{
						if(valueCount != 0 && valueCount != count){
							$("#"+questionId).css("backgroundColor","#FFF100");
						}else if(valueCount == count){
							$("#"+questionId).css("backgroundColor","#8FC31F");
						}else{
							$("#"+questionId).css("backgroundColor","#ffffff");
						}
					}
					
					if(data === 'isAnswer' ){
						jAlert("您已在其他终端完成此次考试，请勿重复提交");
						setTimeout(function(){
							window.location.href = "${ctx}${studentPath}";
						},5000);
					}
				}
			});
		}
		
		/* 提交答案 */
		var returnUrl = $("#returnUrl").val();
		var examType = $("#examType").val();
		function mustSubmitAnswer(){
			jAlert("由于到达考试结束时间或老师已收卷,5秒后自动提交试卷");
			setTimeout(function(){
				window.location.href = "${ctx}/studentAnswer/submitAnswer?examId="+examId+"&&examDetailId="+examDetailId+"&returnUrl="+returnUrl+"&examType="+examType;
			},5000);
		}
		
		
		function submitAnswer(){
			jConfirm("是否确定提交试卷？","提交试卷",function(r){
				if(r){
					window.location.href = "${ctx}/studentAnswer/submitAnswer?examId="+examId+"&&examDetailId="+examDetailId+"&returnUrl="+returnUrl+"&examType="+examType;
				}
			})
		}
	 
		/* (function(){
			 var divHeader =$("div.header"),
			     $win=$(window),
			     divPT=$("div.paper_title"),
			     divTip=$("div.tip"),
			     divCf=$("div.cal_fonts");
			function wSize(){
			    $("#topicContent").height($win.height()-divHeader.height()-divPT.height()-divTip.height()-divCf.height());
			
			    $("div.paper").height($("div.user").height()+$("div.answer").height());
			}
			
			$(window).resize(function(){
				wSize();
			}); 
			
			wSize();
		})();*/
	</script>
</body>
</html>