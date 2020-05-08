<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
<title>课堂例题</title>
<link rel="shortcut icon" href="${prc }/static/student/favicon.ico">
<link rel="stylesheet"	href="${prc }/static/student/2.3.1/css_default/bootstrap.css">
<link rel="stylesheet" href="${prc }/static/student/css/main.css">
<link href="${prc }/static/student/css/exam2.css" rel="stylesheet"	type="text/css">
<script type="text/javascript"	src="${prc }/static/student/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${prc }/static/student/js/main.js"></script>
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>

<style>
 html{overflow: visible;}
/*兼容FireFox*/
body {
	-moz-user-focus: ignore;
	-moz-user-select: none;
}
	.btn-primary{background-color:#1c9993;background-image:linear-gradient(to bottom, #1c9993, #1c9993);}
	.btn-primary:hover,.btn-primary:focus,.btn-primary:active,.btn-primary.active,.btn-primary.disabled,.btn-primary[disabled] {color: #ffffff;background-color: #1c9993;*background-color: #1c9993;}
	.btn-primary:active,.btn-primary.active { background-color: #1c9993 \9;}
</style>
</head>
<body>
	<div style="padding: 10px;10px;10px;30px;">
		<input id="btnExamSubmit" class="btn tbtn-primary" type="button" value="返回" onclick="history.go(-1)"/>
	</div>
	<input type="hidden" id="examId" value="${examId }"/>
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
								答案：${sQuestion.answer0 }<br>
								讲解：${sQuestion.description }
									<%-- <button onclick="showExplain('${sQuestion.id}')">讲解</button> --%>
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
										<input type="checkbox" topicno="1" id="tmda_1_0" name="${sMQuestion.id}" value="A" disabled> A:
										${sMQuestion.choice0 }</label>
									</dd>
								</c:if>	
								<c:if test="${not empty sMQuestion.choice1 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="checkbox" topicno="1" id="tmda_1_1" name="${sMQuestion.id}" value="B" disabled> B:
										${sMQuestion.choice1 }</label>
									</dd>
								</c:if>
									<c:if test="${not empty sMQuestion.choice2 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="checkbox" topicno="1" id="tmda_1_1" name="${sMQuestion.id}" value="C" disabled> C:
										${sMQuestion.choice2 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sMQuestion.choice3 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="checkbox" topicno="1" id="tmda_1_1" name="${sMQuestion.id}" value="D" disabled> D:
										${sMQuestion.choice3 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sMQuestion.choice4 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="checkbox" topicno="1" id="tmda_1_1" name="${sMQuestion.id}" value="E" disabled> E:
										${sMQuestion.choice4 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sMQuestion.choice5 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="checkbox" topicno="1" id="tmda_1_1" name="${sMQuestion.id}" value="F" disabled> F:
										${sMQuestion.choice5 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sMQuestion.choice6 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="checkbox" topicno="1" id="tmda_1_1" name="${sMQuestion.id}" value="G" disabled> G:
										${sMQuestion.choice6 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sMQuestion.choice7 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="checkbox" topicno="1" id="tmda_1_1" name="${sMQuestion.id}" value="H" disabled> H:
										${sMQuestion.choice7 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sMQuestion.choice8 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="checkbox" topicno="1" id="tmda_1_1" name="${sMQuestion.id}" value="I" disabled> I:
										${sMQuestion.choice8 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty sMQuestion.choice9 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="checkbox" topicno="1" id="tmda_1_1" name="${sMQuestion.id}" value="J" disabled> J:
										${sMQuestion.choice9 }</label>
									</dd>
								</c:if>	
								<dd>
								答案：${sMQuestion.answer0 }<br>
								讲解：${sMQuestion.description }
								<%-- 	<button onclick="showExplain('${sMQuestion.id}')">讲解</button> --%>
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
							
							<c:if test="${not empty fQuestion.choice0 }">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第一空填：${fQuestion.choice0}<br>
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
							<br>
							<dd>
								答案：<br/>
							<c:if test="${not empty fQuestion.answer0 }">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第一空填：${fQuestion.answer0} <br>
							</c:if>
							<c:if test="${not empty fQuestion.answer1 }">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第二空填：${fQuestion.answer1} <br>
							</c:if>
							<c:if test="${not empty fQuestion.answer2 }">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第三空填：${fQuestion.answer2} <br>
							</c:if>
							<c:if test="${not empty fQuestion.answer3 }">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第四空填：${fQuestion.answer3} <br>
							</c:if>
							<c:if test="${not empty fQuestion.answer4 }">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第五空填：${fQuestion.answer4} <br>
							</c:if>
							<c:if test="${not empty fQuestion.answer5 }">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第六空填：${fQuestion.answer5} <br>
							</c:if>
							<c:if test="${not empty fQuestion.answer6 }">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第七空填：${fQuestion.answer6} <br>
							</c:if>
							<c:if test="${not empty fQuestion.answer7 }">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第八空填：${fQuestion.answer7} <br>
							</c:if>
							<c:if test="${not empty fQuestion.answer8 }">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第九空填：${fQuestion.answer8} <br>
							</c:if>
							<c:if test="${not empty fQuestion.answer9 }">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第十空填：${fQuestion.answer9} <br>
							</c:if>
								讲解：${fQuestion.description }
								<%-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="showExplain('${sQuestion.id}')">讲解</button> --%>
							</dd>
							</dl>
						</div>
					</c:forEach>
				</c:if>
					<c:if test="${not empty panduanQuestionList}">
					<div class="subject" style="width: 905px;">
						<p>判断题</p>
					</div>
					<c:forEach items="${panduanQuestionList}" var="sAQuestion" varStatus="i" begin="0">
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
								答案：
								<c:if test="${sAQuestion.answer0 eq '2'}">错误</c:if>
								<c:if test="${sAQuestion.answer0 eq '1'}">正确</c:if>
								<br>
								讲解：${sAQuestion.description }
									<%-- <button onclick="showExplain('${sAQuestion.id}')">讲解</button> --%>
								</dd>
							</dl>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${not empty gainianQuestionList}">
					<div class="subject" style="width: 905px;">
						<p>概念题</p>
					</div>
					<c:forEach items="${gainianQuestionList}" var="sAQuestion" varStatus="i" begin="0">
						<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
							<!-- 题号 -->
							<div class="problem_num">
								<div class="sign_title">
									第<span>${i.index + 1}</span>题，本小题 ${sAQuestion.quesPoint } 分
								</div>
							</div>
							<dl>
								<dt>${sAQuestion.title }</dt>
								<dd>
								答案：${sAQuestion.answer0 }<br>
								讲解：${sAQuestion.description }
								<%-- 	<button onclick="showExplain('${sAQuestion.id}')">讲解</button> --%>
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
								答案：${sAQuestion.answer0 }<br>
								讲解：${sAQuestion.description }
									<%-- <button onclick="showExplain('${sAQuestion.id}')">讲解</button> --%>
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
								答案：${cQuestion.answer0 }<br>
								讲解：${cQuestion.description }
									<%-- <button onclick="showExplain('${fQuestion.id}')">讲解</button> --%>
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
					<c:forEach items="${zongheQuestionList}" var="sAQuestion" varStatus="i" begin="0">
						<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
							<!-- 题号 -->
							<div class="problem_num">
								<div class="sign_title">
									第<span>${i.index + 1}</span>题，本小题 ${sAQuestion.quesPoint } 分
								</div>
							</div>
							<dl>
								<dt>${sAQuestion.title }</dt>
								<dd>
								答案：${sAQuestion.answer0 }<br>
								讲解：${sAQuestion.description }
									<%-- <button onclick="showExplain('${sAQuestion.id}')">讲解</button> --%>
								</dd>
							</dl>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${not empty zhituQuestionList}">
					<div class="subject" style="width: 905px;">
						<p>制图题</p>
					</div>
					<c:forEach items="${zhituQuestionList}" var="sAQuestion" varStatus="i" begin="0">
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
								答案：${sAQuestion.answer0 }<br>
								讲解：${sAQuestion.description }
									<%-- <button onclick="showExplain('${sAQuestion.id}')">讲解</button> --%>
								</dd>
							</dl>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${not empty zhibiaoQuestionList}">
					<div class="subject" style="width: 905px;">
						<p>制表题</p>
					</div>
					<c:forEach items="${zhibiaoQuestionList}" var="sAQuestion" varStatus="i" begin="0">
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
								答案：${sAQuestion.answer0 }<br>
								讲解：${sAQuestion.description }
								<%-- 	<button onclick="showExplain('${sAQuestion.id}')">讲解</button> --%>
								</dd>
							</dl>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${not empty shituQuestionList}">
					<div class="subject" style="width: 905px;">
						<p>识图题</p>
					</div>
					<c:forEach items="${shituQuestionList}" var="sAQuestion" varStatus="i" begin="0">
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
								答案：${sAQuestion.answer0 }<br>
								讲解：${sAQuestion.description }
									<%-- <button onclick="showExplain('${sAQuestion.id}')">讲解</button> --%>
								</dd>
							</dl>
						</div>
					</c:forEach>
				</c:if>
			
			</td>
		</tr>
	</table>
	
	<!-- <script type="text/javascript">
		var examId = $("#examId").val();
		function showExplain(questionId){
			top.$.jBox.open(
				"iframe:${ctx}/classExample/getQuestionExplain?questionId="+questionId+"&&examId="+examId, 
				"题目详解",
				750,
				350,
				{
				buttons:{"确定":true}, loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
	</script> -->
</body>
</html>