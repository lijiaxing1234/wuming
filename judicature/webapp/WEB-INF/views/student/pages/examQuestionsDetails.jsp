<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="student_default" />
<title>在线答题</title>
<link rel="shortcut icon" href="${prc }/static/student/favicon.ico">
<link rel="stylesheet"	href="${prc }/static/student/2.3.1/css_default/bootstrap.css">
<link rel="stylesheet" href="${prc }/static/student/css/main.css">
<link href="${prc }/static/student/css/exam.css" rel="stylesheet"	type="text/css">
<script type="text/javascript"	src="${prc }/static/student/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${prc }/static/student/js/main.js"></script>
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>

<style>

/*兼容FireFox*/
body {
	-moz-user-focus: ignore;
	-moz-user-select: none;
}
div.problem label img{ display:inline-block;}
</style>
</head>
<body>
	<sys:message content="${message}"/>
	<table border="0" cellspacing="0" cellpadding="0" style="border-top: 2px solid #1c9993; width: 100%;">
		<tr>
			<td>
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
										<label for="tmda_1_0" id="tmxx_1_0" style="color: black;">
											<input type="radio" topicno="1" id="tmda_1_0" name="${sQuestion.id }"	value="A" disabled> A:
											${sQuestion.choice0 }
										</label>
										
									</dd>
								</c:if>	
								<c:if test="${not empty sQuestion.choice1 }">
									<dd>
										
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="radio" topicno="1" id="tmda_1_1" name="${sQuestion.id }" value="B" disabled> B:
										${sQuestion.choice1 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sQuestion.choice2 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="radio" topicno="1" id="tmda_1_1" name="${sQuestion.id }" value="C" disabled> C:
										${sQuestion.choice2 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sQuestion.choice3 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="radio" topicno="1" id="tmda_1_1" name="${sQuestion.id }" value="D" disabled> D:
										${sQuestion.choice3 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sQuestion.choice4 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="radio" topicno="1" id="tmda_1_1" name="${sQuestion.id }" value="E" disabled> E:
										${sQuestion.choice4 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sQuestion.choice5 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="radio" topicno="1" id="tmda_1_1" name="${sQuestion.id }" value="F" disabled> F:
										${sQuestion.choice5 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sQuestion.choice6 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="radio" topicno="1" id="tmda_1_1" name="${sQuestion.id }" value="G" disabled> G:
										${sQuestion.choice6 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sQuestion.choice7 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="radio" topicno="1" id="tmda_1_1" name="${sQuestion.id }" value="H" disabled> H:
										${sQuestion.choice7 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sQuestion.choice8 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="radio" topicno="1" id="tmda_1_1" name="${sQuestion.id }" value="I" disabled> I:
										${sQuestion.choice8 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sQuestion.choice9 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="radio" topicno="1" id="tmda_1_1" name="${sQuestion.id }" value="J" disabled> J:
										${sQuestion.choice9 }</label>
									</dd>
								</c:if>
								<dd>
									<jsp:include page="${ctx}/studentExam/getQuestionExplain?questionId=${sQuestion.id}&examId=${examId}"></jsp:include>
								</dd>						
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
										<label for="tmda_1_0" id="tmxx_1_0" style="color: black;">
										<input type="checkbox" disabled topicno="1" id="tmda_1_0" name="${sMQuestion.id}" value="A" > A:
										${sMQuestion.choice0 }</label>
									</dd>
								</c:if>	
								<c:if test="${not empty sMQuestion.choice1 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="checkbox" disabled topicno="1" id="tmda_1_1" name="${sMQuestion.id}" value="B" > B:
										${sMQuestion.choice1 }</label>
									</dd>
								</c:if>
									<c:if test="${not empty sMQuestion.choice2 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="checkbox" disabled topicno="1" id="tmda_1_1" name="${sMQuestion.id}" value="C" > C:
										${sMQuestion.choice2 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sMQuestion.choice3 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="checkbox" disabled topicno="1" id="tmda_1_1" name="${sMQuestion.id}" value="D" > D:
										${sMQuestion.choice3 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sMQuestion.choice4 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="checkbox" disabled topicno="1" id="tmda_1_1" name="${sMQuestion.id}" value="E" > E:
										${sMQuestion.choice4 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sMQuestion.choice5 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="checkbox" disabled topicno="1" id="tmda_1_1" name="${sMQuestion.id}" value="F" > F:
										${sMQuestion.choice5 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sMQuestion.choice6 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="checkbox" disabled topicno="1" id="tmda_1_1" name="${sMQuestion.id}" value="G" > G:
										${sMQuestion.choice6 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sMQuestion.choice7 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="checkbox" disabled topicno="1" id="tmda_1_1" name="${sMQuestion.id}" value="H" > H:
										${sMQuestion.choice7 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sMQuestion.choice8 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="checkbox" disabled topicno="1" id="tmda_1_1" name="${sMQuestion.id}" value="I" > I:
										${sMQuestion.choice8 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sMQuestion.choice9 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="checkbox" disabled topicno="1" id="tmda_1_1" name="${sMQuestion.id}" value="J" > J:
										${sMQuestion.choice9 }</label>
									</dd>
								</c:if>	
								<dd>
									<jsp:include page="${ctx}/studentExam/getQuestionExplain?questionId=${sMQuestion.id}&examId=${examId}"></jsp:include>
								</dd>							
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
								<dd>
									<c:if test="${not empty fQuestion.choice0 }">
										第一空填：${fQuestion.choice0} <br>
									</c:if>
									<c:if test="${not empty fQuestion.choice1 }">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第二空填：${fQuestion.choice1} <br>
									</c:if>
									<c:if test="${not empty fQuestion.choice2 }">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第三空填：${fQuestion.choice2} <br>
									</c:if>
									<c:if test="${not empty fQuestion.choice3 }">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第四空填：${fQuestion.choice3} <br>
									</c:if>
									<c:if test="${not empty fQuestion.choice4 }">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第五空填：${fQuestion.choice4} <br>
									</c:if>
									<c:if test="${not empty fQuestion.choice5 }">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第六空填：${fQuestion.choice5} <br>
									</c:if>
									<c:if test="${not empty fQuestion.choice6 }">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第七空填：${fQuestion.choice6} <br>
									</c:if>
									<c:if test="${not empty fQuestion.choice7 }">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第八空填：${fQuestion.choice7} <br>
									</c:if>
									<c:if test="${not empty fQuestion.choice8 }">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第九空填：${fQuestion.choice8} <br>
									</c:if>
									<c:if test="${not empty fQuestion.choice9 }">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第十空填：${fQuestion.choice9} <br>
									</c:if>
								</dd>
								<dd>
									<jsp:include page="${ctx}/studentExam/getQuestionExplain?questionId=${fQuestion.id}&examId=${examId}"></jsp:include>
								</dd>
							</dl>
						</div>
					</c:forEach>
				</c:if>
				
				<c:if test="${not empty panduanQuestionList}">
					<div class="subject" style="width: 905px;">
						<p>判断题</p>
					</div>
					<c:forEach items="${panduanQuestionList}" var="pdQuestion" varStatus="i" begin="0">
						<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
							<!-- 题号 -->
							<div class="problem_num">
								<div class="sign_title">
									第<span>${i.index + 1}</span>题，本小题 ${pdQuestion.quesPoint } 分
								</div>
							</div>
							<dl>
								<dt>${pdQuestion.title }</dt>
								<dd>
									<jsp:include page="${ctx}/studentExam/getQuestionExplain?questionId=${pdQuestion.id}&examId=${examId}"></jsp:include>
								</dd>
							</dl>
						</div>
					</c:forEach>
				</c:if>
				
				<c:if test="${not empty gainianQuestionList}">
					<div class="subject" style="width: 905px;">
						<p>概念题</p>
					</div>
					<c:forEach items="${gainianQuestionList}" var="gnQuestion" varStatus="i" begin="0">
						<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
							<!-- 题号 -->
							<div class="problem_num">
								<div class="sign_title">
									第<span>${i.index + 1}</span>题，本小题 ${gnQuestion.quesPoint } 分
								</div>
							</div>
							<dl>
								<dt>${gnQuestion.title }</dt>
								<dd>
									<jsp:include page="${ctx}/studentExam/getQuestionExplain?questionId=${gnQuestion.id}&examId=${examId}"></jsp:include>
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
								<dd>
									<jsp:include page="${ctx}/studentExam/getQuestionExplain?questionId=${sAQuestion.id}&examId=${examId}"></jsp:include>
								</dd>
							</dl>
						</div>
					</c:forEach>
				</c:if>
				
				<c:if test="${not empty cQuestionList}">
					<div class="subject" style="width: 905px;">
						<p>计算题</p>
					</div>
					<c:forEach items="${cQuestionList}" var="cQuestion" varStatus="i" begin="0">
						<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
							<!-- 题号 -->
							<div class="problem_num">
								<div class="sign_title">
									第<span>${i.index + 1}</span>题，本小题 ${cQuestion.quesPoint } 分<a name="cQuestion${i.index}" ></a>
								</div>
							</div>
							<dl>
								<dt>${cQuestion.title }</dt>
								<dd>
									<jsp:include page="${ctx}/studentExam/getQuestionExplain?questionId=${cQuestion.id}&examId=${examId}"></jsp:include>
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
					<c:forEach items="${zongheQuestionList}" var="zhQuestion" varStatus="i" begin="0">
						<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
							<!-- 题号 -->
							<div class="problem_num">
								<div class="sign_title">
									第<span>${i.index + 1}</span>题，本小题 ${zhQuestion.quesPoint } 分
								</div>
							</div>
							<dl>
								<dt>${zhQuestion.title }</dt>
								<dd>
									<jsp:include page="${ctx}/studentExam/getQuestionExplain?questionId=${zhQuestion.id}&examId=${examId}"></jsp:include>
								</dd>
							</dl>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${not empty zhituQuestionList}">
					<div class="subject" style="width: 905px;">
						<p>制图题</p>
					</div>
					<c:forEach items="${zhituQuestionList}" var="ztQuestion" varStatus="i" begin="0">
						<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
							<!-- 题号 -->
							<div class="problem_num">
								<div class="sign_title">
									第<span>${i.index + 1}</span>题，本小题 ${ztQuestion.quesPoint } 分
								</div>
							</div>
							<dl>
								<dt>${ztQuestion.title }</dt>
								<dd>
									<jsp:include page="${ctx}/studentExam/getQuestionExplain?questionId=${ztQuestion.id}&examId=${examId}"></jsp:include>
								</dd>
							</dl>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${not empty zhibiaoQuestionList}">
					<div class="subject" style="width: 905px;">
						<p>制表题</p>
					</div>
					<c:forEach items="${zhibiaoQuestionList}" var="zbQuestion" varStatus="i" begin="0">
						<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
							<!-- 题号 -->
							<div class="problem_num">
								<div class="sign_title">
									第<span>${i.index + 1}</span>题，本小题 ${zbQuestion.quesPoint } 分
								</div>
							</div>
							<dl>
								<dt>${zbQuestion.title }</dt>
								<dd>
									<jsp:include page="${ctx}/studentExam/getQuestionExplain?questionId=${zbQuestion.id}&examId=${examId}"></jsp:include>
								</dd>
							</dl>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${not empty shituQuestionList}">
					<div class="subject" style="width: 905px;">
						<p>识图题</p>
					</div>
					<c:forEach items="${shituQuestionList}" var="stQuestion" varStatus="i" begin="0">
						<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
							<!-- 题号 -->
							<div class="problem_num">
								<div class="sign_title">
									第<span>${i.index + 1}</span>题，本小题 ${stQuestion.quesPoint } 分
								</div>
							</div>
							<dl>
								<dt>${stQuestion.title }</dt>
								<dd>
									<jsp:include page="${ctx}/studentExam/getQuestionExplain?questionId=${stQuestion.id}&examId=${examId}"></jsp:include>
								</dd>
							</dl>
						</div>
					</c:forEach>
				</c:if>
				
			</td>
		</tr>
	</table>
	<input id="fh" type="button" class="btn sbtn-primary" onclick="javascript:history.back(-1)"   value="返回">
</body>
</html>

