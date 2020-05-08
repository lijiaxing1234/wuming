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
button{background:url(${prc }/static/modules/Student/images/detail.png) no-repeat;width:59px;height:28px;background-size:44%;padding-left:26px;border:0 none;color:#fff;}
button:hover{color:#666;}
</style>
</head>
<body>
	<table border="0" cellspacing="0" cellpadding="0" style="border-top: 2px solid #56a0a1;">
		<tr>
			<td>
				<c:if test="${question.quesType == '1'}">
					<div class="subject" style="width: 905px;">
						<p>单选题</p>
					</div>
					<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
						<!-- 题号 -->
						<div class="problem_num">
							<div class="sign_title">
								本小题 ${question.quesPoint } 分
							</div>
						</div>
						<dl>
							<dt>${question.title }</dt>
							<c:if test="${not empty question.choice0 }">
								<dd>
									<label for="tmda_1_0" id="tmxx_1_0" style="color: black;">
										<input type="radio" topicno="1" id="tmda_1_0" name="${question.id }"	value="A" disabled> A:
										${question.choice0 }
									</label>
									
								</dd>
							</c:if>	
							<c:if test="${not empty question.choice1 }">
								<dd>
									
									<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
									<input type="radio" topicno="1" id="tmda_1_1" name="${question.id }" value="B" disabled> B:
									${question.choice1 }</label>
								</dd>
							</c:if>
							<c:if test="${not empty question.choice2 }">
								<dd>
									<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
									<input type="radio" topicno="1" id="tmda_1_1" name="${question.id }" value="C" disabled> C:
									${question.choice2 }</label>
								</dd>
							</c:if>
							<c:if test="${not empty question.choice3 }">
								<dd>
									<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
									<input type="radio" topicno="1" id="tmda_1_1" name="${question.id }" value="D" disabled> D:
									${question.choice3 }</label>
								</dd>
							</c:if>
							<c:if test="${not empty question.choice4 }">
								<dd>
									<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
									<input type="radio" topicno="1" id="tmda_1_1" name="${question.id }" value="E" disabled> E:
									${question.choice4 }</label>
								</dd>
							</c:if>
							<c:if test="${not empty question.choice5 }">
								<dd>
									<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
									<input type="radio" topicno="1" id="tmda_1_1" name="${question.id }" value="F" disabled> F:
									${question.choice5 }</label>
								</dd>
							</c:if>
							<c:if test="${not empty question.choice6 }">
								<dd>
									<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
									<input type="radio" topicno="1" id="tmda_1_1" name="${question.id }" value="G" disabled> G:
									${question.choice6 }</label>
								</dd>
							</c:if>
							<c:if test="${not empty question.choice7 }">
								<dd>
									<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
									<input type="radio" topicno="1" id="tmda_1_1" name="${question.id }" value="H" disabled> H:
									${question.choice7 }</label>
								</dd>
							</c:if>
							<c:if test="${not empty question.choice8 }">
								<dd>
									<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
									<input type="radio" topicno="1" id="tmda_1_1" name="${question.id }" value="I" disabled> I:
									${question.choice8 }</label>
								</dd>
							</c:if>
							<c:if test="${not empty question.choice9 }">
								<dd>
									<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
									<input type="radio" topicno="1" id="tmda_1_1" name="${question.id }" value="J" disabled> J:
									${question.choice9 }</label>
								</dd>
							</c:if>
							<dd>
								<button onclick="showExplain('${question.id}')">讲解</button>
							</dd>
						</dl>
					</div>
				</c:if>
				
				<c:if test="${question.quesType eq '5' }">
					<div class="subject" style="width: 905px;">
						<p>多选题</p>
					</div>
					<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
						<div class="problem_num">
							<div class="sign_title">
								本小题 ${question.quesPoint } 分
							</div>
						</div>
						<dl>
							<dt>${question.title }</dt>
							<dd>
								<c:if test="${not empty question.choice0 }">
									<dd>
										<label for="tmda_1_0" id="tmxx_1_0" style="color: black;">
											<input type="radio" topicno="1" id="tmda_1_0" name="${question.id }"	value="A" disabled> A:
											${question.choice0 }
										</label>
										
									</dd>
								</c:if>	
								<c:if test="${not empty question.choice1 }">
									<dd>
										
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="radio" topicno="1" id="tmda_1_1" name="${question.id }" value="B" disabled> B:
										${question.choice1 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty question.choice2 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="radio" topicno="1" id="tmda_1_1" name="${question.id }" value="C" disabled> C:
										${question.choice2 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty question.choice3 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="radio" topicno="1" id="tmda_1_1" name="${question.id }" value="D" disabled> D:
										${question.choice3 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty question.choice4 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="radio" topicno="1" id="tmda_1_1" name="${question.id }" value="E" disabled> E:
										${question.choice4 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty question.choice5 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="radio" topicno="1" id="tmda_1_1" name="${question.id }" value="F" disabled> F:
										${question.choice5 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty question.choice6 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="radio" topicno="1" id="tmda_1_1" name="${question.id }" value="G" disabled> G:
										${question.choice6 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty question.choice7 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="radio" topicno="1" id="tmda_1_1" name="${question.id }" value="H" disabled> H:
										${question.choice7 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty question.choice8 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="radio" topicno="1" id="tmda_1_1" name="${question.id }" value="I" disabled> I:
										${question.choice8 }</label>
									</dd>
								</c:if>
								<c:if test="${not empty question.choice9 }">
									<dd>
										<label for="tmda_1_1" id="tmxx_1_1" style="color: black;">
										<input type="radio" topicno="1" id="tmda_1_1" name="${question.id }" value="J" disabled> J:
										${question.choice9 }</label>
									</dd>
								</c:if>
							</dd>
							<dd>
								<button onclick="showExplain('${question.id}')">讲解</button>
							</dd>
						</dl>
					</div>
				</c:if>
				
				<c:if test="${question.quesType eq '2' }">
					<div class="subject" style="width: 905px;">
						<p>填空题</p>
					</div>
					<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
						<!-- 题号 -->
						<div class="problem_num">
							<div class="sign_title">
								本小题 ${question.quesPoint } 分
							</div>
						</div>
						<dl>
							<dt>${question.title }</dt>
						</dl>
						<br>
						&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="showExplain('${question.id}')">讲解</button>
					</div>
				</c:if>
				
				<c:if test="${question.quesType eq '11' }">
					<div class="subject" style="width: 905px;">
						<p>判断题</p>
					</div>
					<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
						<!-- 题号 -->
						<div class="problem_num">
							<div class="sign_title">
								${question.quesPoint } 分
							</div>
						</div>
						<dl>
							<dt>${question.title }</dt>
							<dd>
								<button onclick="showExplain('${question.id}')">讲解</button>
							</dd>
						</dl>
					</div>
				</c:if>
				
				<c:if test="${question.quesType eq '6' }">
					<div class="subject" style="width: 905px;">
						<p>概念题</p>
					</div>
					<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
						<!-- 题号 -->
						<div class="problem_num">
							<div class="sign_title">
								${question.quesPoint } 分
							</div>
						</div>
						<dl>
							<dt>${question.title }</dt>
							<dd>
								<button onclick="showExplain('${question.id}')">讲解</button>
							</dd>
						</dl>
					</div>
				</c:if>
				
				<c:if test="${question.quesType eq '4' }">
					<div class="subject" style="width: 905px;">
						<p>简答题</p>
					</div>
					<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
						<div class="problem_num">
							<div class="sign_title">
								本小题 ${question.quesPoint } 分
							</div>
						</div>
						<dl>
							<dt>${question.title }</dt>
							<dd>
								<button onclick="showExplain('${question.id}')">讲解</button>
							</dd>
						</dl>
					</div>
				</c:if>
				
				<c:if test="${question.quesType eq '3' }">
					<div class="subject" style="width: 905px;">
						<p>计算题</p>
					</div>
					<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
						<!-- 题号 -->
						<div class="problem_num">
							<div class="sign_title">
								本小题 ${question.quesPoint } 分
							</div>
						</div>
						<dl>
							<dt>${question.title }</dt>
							<dd>
								<button onclick="showExplain('${question.id}')">讲解</button>
							</dd>
						</dl>
					</div>
				</c:if>
				
				
				
				<c:if test="${question.quesType eq '7' }">
					<div class="subject" style="width: 905px;">
						<p>综合题</p>
					</div>
					<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
						<!-- 题号 -->
						<div class="problem_num">
							<div class="sign_title">
								${question.quesPoint } 分
							</div>
						</div>
						<dl>
							<dt>${question.title }</dt>
							<dd>
								<button onclick="showExplain('${question.id}')">讲解</button>
							</dd>
						</dl>
					</div>
				</c:if>
				<c:if test="${question.quesType eq '8' }">
					<div class="subject" style="width: 905px;">
						<p>制图题</p>
					</div>
					<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
						<!-- 题号 -->
						<div class="problem_num">
							<div class="sign_title">
								${question.quesPoint } 分
							</div>
						</div>
						<dl>
							<dt>${question.title }</dt>
							<dd>
								<button onclick="showExplain('${question.id}')">讲解</button>
							</dd>
						</dl>
					</div>
				</c:if>
				<c:if test="${question.quesType eq '9' }">
					<div class="subject" style="width: 905px;">
						<p>制表题</p>
					</div>
					<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
						<!-- 题号 -->
						<div class="problem_num">
							<div class="sign_title">
								${question.quesPoint } 分
							</div>
						</div>
						<dl>
							<dt>${question.title }</dt>
							<dd>
								<button onclick="showExplain('${question.id}')">讲解</button>
							</dd>
						</dl>
					</div>
				</c:if>
				<c:if test="${question.quesType eq '10' }">
					<div class="subject" style="width: 905px;">
						<p>识图题</p>
					</div>
					<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
						<!-- 题号 -->
						<div class="problem_num">
							<div class="sign_title">
								${question.quesPoint } 分
							</div>
						</div>
						<dl>
							<dt>${question.title }</dt>
							<dd>
								<button onclick="showExplain('${question.id}')">讲解</button>
							</dd>
						</dl>
					</div>
				</c:if>
				
			</td>
		</tr>
	</table>
	<input  type="button" onclick="javascript:history.back(-1)"  class="btn sbtn-primary" style="margin-left: 20px;" value="返回">
	<script type="text/javascript">
		function showExplain(questionId){
			top.$.jBox.open("iframe:${ctx}/studentExam/getQuestionExplain?questionId="+questionId, "题目详解",750,350,{
				buttons:{"确定":true}, loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
	</script>
</body>
</html>