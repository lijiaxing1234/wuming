<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="teacher_default" />
<title>在线答题</title>
<link rel="shortcut icon" href="${prc }/static/student/favicon.ico">
<link rel="stylesheet"	href="${prc }/static/student/2.3.1/css_default/bootstrap.css">
<link rel="stylesheet" href="${prc }/static/student/css/main.css">
<script type="text/javascript"	src="${prc }/static/student/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${prc }/static/student/js/main.js"></script>
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/kindeditor/kindeditor-all-min.js" type="text/javascript"></script>
	<style type="text/css">
	   .form-actions{margin:0px; border-top: 0px; text-align: right;}
	   .btn-primary{background-color:#3399d7;background-image:linear-gradient(to bottom, #3399d7, #3399d7);}
		.btn-primary:hover,.btn-primary:focus,.btn-primary:active,.btn-primary.active,.btn-primary.disabled,.btn-primary[disabled] {color: #ffffff;background-color: #3399d7;*background-color: #3399d7;}
		.btn-primary:active,.btn-primary.active { background-color: #3399d7 \9;}
	</style>
<style>
/*兼容FireFox*/
body {
	-moz-user-focus: ignore;
	-moz-user-select: none;
	overflow:auto;
}
</style>
</head>
<body>
<sys:message content="${message}"></sys:message>
	<form:form id="searchForm" action="${ctx}/teacherStudentAnswer/examList" method="post"  class="breadcrumb form-search ">
		<input type="hidden" id="examId" value="${exam.id}"/>
		<input id="examDetailId" type="hidden" value="${examDetailId}">
		<input id="examClassId" type="hidden" value="${examClassId}">
		<input id="studentId" type="hidden" value="${studentId}">
		<input id="status" type="hidden" value="${status}">
		学生：${student.name}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		试卷：${exam.title}
	</form:form>
	<form>
	<table class="table table-striped table-bordered table-condensed"  style="margin-top: 20px;">
		<tr>
			<td>
				<c:if test="${not empty fQuestionList}">
					<div class="subject" style="width: 905px;" align="left">
						<p style="font-weight: bold;">二、填空题</p>
					</div>
					<c:forEach items="${fQuestionList}" var="fQuestion" varStatus="i" begin="0">
						<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;"  align="left">
							<!-- 题号 -->
							<div class="problem_num">
								<div class="sign_title" style="margin-bottom:5px;">
									第<span>${i.index+1}</span>题，本小题 ${fQuestion.quesPoint} 分<a name="fQuestion${i.index}" ></a>
								</div>
							</div>
							<dl  align="left" style="margin-bottom:5px;">
								<dt>${fQuestion.title }</dt>
							</dl>
							<c:if test="${not empty fQuestion.answer0 }">
								<div style="margin-bottom:5px;">
									<p style="float:left;font-weight:bold;">第一空填：${fQuestion.answer0}</p>
									<p style="margin-bottom:5px;"><span style="font-weight:bold;">&nbsp;&nbsp;&nbsp;&nbsp;该学生的答案：</span>${fQuestion.studentAnswer.answer0}</p>
								</div>
							</c:if>
							<c:if test="${not empty fQuestion.answer1 }">
								<p style="display:inline-block;float:left;font-weight:bold;">第二空填：${fQuestion.answer1} </p>
								<p style="margin-bottom:5px;"><span style="font-weight:bold;">&nbsp;&nbsp;&nbsp;&nbsp;该学生的答案：</span>${fQuestion.studentAnswer.answer1}</p>
							</c:if>
							<c:if test="${not empty fQuestion.answer2 }">
								<p style="display:inline-block;float:left;font-weight:bold;">第三空填：${fQuestion.answer2}</p> 
								<p style="margin-bottom:5px;"><span style="font-weight:bold;">&nbsp;&nbsp;&nbsp;&nbsp;该学生的答案：</span>${fQuestion.studentAnswer.answer2}</p>
							</c:if>
							<c:if test="${not empty fQuestion.answer3 }">
								<p style="display:inline-block;float:left;font-weight:bold;">第四空填：${fQuestion.answer3}</p>
								<p style="margin-bottom:5px;"><span style="font-weight:bold;">&nbsp;&nbsp;&nbsp;&nbsp;该学生的答案：</span>${fQuestion.studentAnswer.answer3}</p>
							</c:if>
							<c:if test="${not empty fQuestion.answer4 }">
								<p style="display:inline-block;float:left;font-weight:bold;">第五空填：${fQuestion.answer4} </p>
								<p style="margin-bottom:5px;"><span style="font-weight:bold;">&nbsp;&nbsp;&nbsp;&nbsp;该学生的答案：</span>${fQuestion.studentAnswer.answer4}</p>
							</c:if>
							<c:if test="${not empty fQuestion.answer5 }">
								<p style="display:inline-block;float:left;font-weight:bold;">第六空填：${fQuestion.answer5} </p>
								<p style="margin-bottom:5px;"><span style="font-weight:bold;">&nbsp;&nbsp;&nbsp;&nbsp;该学生的答案：</span>${fQuestion.studentAnswer.answer5}</p>
							</c:if>
							<c:if test="${not empty fQuestion.answer6 }">
								<p style="display:inline-block;float:left;font-weight:bold;">第七空填：${fQuestion.answer6}</p>
								<p style="margin-bottom:5px;"><span style="font-weight:bold;">&nbsp;&nbsp;&nbsp;&nbsp;该学生的答案：</span>${fQuestion.studentAnswer.answer6}</p>
							</c:if>
							<c:if test="${not empty fQuestion.answer7 }">
								<p style="display:inline-block;float:left;font-weight:bold;">第八空填：${fQuestion.answer7}</p>
								<p style="margin-bottom:5px;"><span style="font-weight:bold;">&nbsp;&nbsp;&nbsp;&nbsp;该学生的答案：</span>${fQuestion.studentAnswer.answer7}</p>
							</c:if>
							<c:if test="${not empty fQuestion.answer8 }">
								<p style="display:inline-block;float:left;font-weight:bold;">第九空填：${fQuestion.answer8}</p>
								<p style="margin-bottom:5px;"><span style="font-weight:bold;">&nbsp;&nbsp;&nbsp;&nbsp;该学生的答案：</span>${fQuestion.studentAnswer.answer8}</p>
							</c:if>
							<c:if test="${not empty fQuestion.answer9 }">
								<p style="display:inline-block;float:left;font-weight:bold;">第十空填：${fQuestion.answer9} </p>
								<p style="margin-bottom:5px;"><span style="font-weight:bold;">&nbsp;&nbsp;&nbsp;&nbsp;该学生的答案：</span>${fQuestion.studentAnswer.answer9}</p>
							</c:if>
								<p><span style="font-weight:bold;">该题得分：</span><input style="margin-top: 10px;" class="input-mini" type="text" id="answerSQ_${fQuestion.id}_${fQuestion.quesPoint}"  onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></p>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${not empty cQuestionList}">
					<div class="subject" style="width: 905px;" align="left">
						<p style="font-weight: bold;">三、计算题</p>
					</div>
					<c:forEach items="${cQuestionList}" var="cQuestion" varStatus="i" begin="0">
						<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
							<!-- 题号 -->
							<div class="problem_num">
								<div class="sign_title" align="left">
									第<span>${i.index+1}</span>题，本小题 ${cQuestion.quesPoint } 分<a name="cQuestion${i.index}" ></a>
								</div>
							</div>
							<dl align="left">
								<dt>${cQuestion.title }</dt>
								<dd>
									<textarea id="cQuestion${i.index }" name="calcQuestion${i.index }" rows="5" cols="40" readonly="readonly">${cQuestion.studentAnswer.answer0}</textarea>
									<script type="text/javascript">
										KindEditor.create('textarea[name="calcQuestion${i.index}"]', {
											width : '75%',
											height : '150px',
											resizeType : 1,
										    items: ['bold', 'italic', 'underline', 'strikethrough', 'removeformat', '|','subscript','superscript',
									                'shcode', 'image', 'quote', '|', 'source'],
									     uploadJson : '/fileUploadUtil/kindeditorupload',
								         fileManagerJson : '/fileUploadUtil/kindeditorfilemanager',
								         allowFileManager : true,
								         readonlyMode:true,
								         afterBlur: function(){this.sync();}
										});
									</script>
									
									该题得分：<input class="input-mini" style="margin-top: 10px;" type="text" id="answerSQ_${cQuestion.id}_${cQuestion.quesPoint}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/>
								</dd>
							</dl>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${not empty sAQuestionList}">
					<div class="subject" style="width: 905px;" align="left">
						<p style="font-weight: bold;">四、简答题</p>
					</div>
					<c:forEach items="${sAQuestionList}" var="sAQuestion" varStatus="i" begin="0">
						<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
							<!-- 题号 -->
							<div class="problem_num"  align="left">
								<div class="sign_title">
									第<span>${i.index+1}</span>题，本小题 ${sAQuestion.quesPoint} 分<a name="sAQuestion${i.index}" ></a>
								</div>
							</div>
							<dl  align="left">
								<dt>${sAQuestion.title }</dt>
								<dd>
									<textarea id="cQuestion${i.index }" name="jiandaQuestion${i.index }" rows="5" cols="40" readonly="readonly">${sAQuestion.studentAnswer.answer0}</textarea>
									<script type="text/javascript">
										KindEditor.create('textarea[name="jiandaQuestion${i.index }"]', {
											width : '75%',
											height : '150px',
											resizeType : 1,
										    items: ['bold', 'italic', 'underline', 'strikethrough', 'removeformat', '|','subscript','superscript',
									                'shcode', 'image', 'quote', '|', 'source'],
									     uploadJson : '/fileUploadUtil/kindeditorupload',
								         fileManagerJson : '/fileUploadUtil/kindeditorfilemanager',
								         allowFileManager : true,
								         readonlyMode:true,
								         afterBlur: function(){this.sync();}
										});
									</script>
									该题得分：<input class="input-mini" type="text" style="margin-top: 10px;" id="answerSQ_${sAQuestion.id}_${sAQuestion.quesPoint}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/>
								</dd>
							</dl>
						</div>
					</c:forEach>
				</c:if>
				
				<!-- 题型 1：单选题 2：填空题 3：计算题 4：简答题 5：多选题 6：概念题 7：综合题 8：制图题 9：制表题 10：识图题 11：判断题 -->
				<c:if test="${not empty gainianQuestionList}">
					<div class="subject" style="width: 905px;" align="left">
						<p style="font-weight: bold;">六、概念题</p>
					</div>
					<c:forEach items="${gainianQuestionList}" var="sAQuestion" varStatus="i" begin="0">
						<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
							<!-- 题号 -->
							<div class="problem_num" align="left">
								<div class="sign_title">
									第<span>${i.index+1}</span>题，本小题 ${sAQuestion.quesPoint} 分
								</div>
							</div>
							<dl align="left">
								<dt>${sAQuestion.title }</dt>
								<dd>
									<textarea id="cQuestion${i.index }"  name="gainian${i.index }" rows="5" cols="40" readonly="readonly">${sAQuestion.studentAnswer.answer0}</textarea>
									<script type="text/javascript">
										KindEditor.create('textarea[name="gainian${i.index}"]', {
											width : '75%',
											height : '150px',
											resizeType : 1,
										    items: ['bold', 'italic', 'underline', 'strikethrough', 'removeformat', '|','subscript','superscript',
									                'shcode', 'image', 'quote', '|', 'source'],
									     uploadJson : '/fileUploadUtil/kindeditorupload',
								         fileManagerJson : '/fileUploadUtil/kindeditorfilemanager',
								         allowFileManager : true,
								         readonlyMode:true,
								         afterBlur: function(){this.sync();}
										});
									</script>
									该题得分：<input class="input-mini" type="text" style="margin-top: 10px;" id="answerSQ_${sAQuestion.id}_${sAQuestion.quesPoint}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/>
								</dd>
							</dl>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${not empty zongheQuestionList}">
					<div class="subject" style="width: 905px;" align="left">
						<p style="font-weight: bold;">七、综合题</p>
					</div>
					<c:forEach items="${zongheQuestionList}" var="sAQuestion" varStatus="i" begin="0">
						<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
							<!-- 题号 -->
							<div class="problem_num" align="left">
								<div class="sign_title">
									第<span>${i.index+1}</span>题，本小题 ${sAQuestion.quesPoint} 分
								</div>
							</div>
							<dl align="left">
								<dt>${sAQuestion.title }</dt>
								<dd>
									<textarea id="cQuestion${i.index }" name="zonghe${i.index }" rows="5" cols="40" readonly="readonly">${sAQuestion.studentAnswer.answer0}</textarea>
									<script type="text/javascript">
										KindEditor.create('textarea[name="zonghe${i.index }"]', {
											width : '75%',
											height : '150px',
											resizeType : 1,
										    items: ['bold', 'italic', 'underline', 'strikethrough', 'removeformat', '|','subscript','superscript',
									                'shcode', 'image', 'quote', '|', 'source'],
									     uploadJson : '/fileUploadUtil/kindeditorupload',
								         fileManagerJson : '/fileUploadUtil/kindeditorfilemanager',
								         allowFileManager : true,
								         readonlyMode:true,
								         afterBlur: function(){this.sync();}
										});
									</script>
									该题得分：<input class="input-mini" type="text" style="margin-top: 10px;" id="answerSQ_${sAQuestion.id}_${sAQuestion.quesPoint}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/>
								</dd>
							</dl>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${not empty zhituQuestionList}">
					<div class="subject" style="width: 905px;" align="left">
						<p style="font-weight: bold;">八、制图题</p>
					</div>
					<c:forEach items="${zhituQuestionList}" var="sAQuestion" varStatus="i" begin="0">
						<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
							<!-- 题号 -->
							<div class="problem_num" align="left">
								<div class="sign_title">
									第<span>${i.index+1}</span>题，本小题 ${sAQuestion.quesPoint} 分<a name="sAQuestion${i.index}" ></a>
								</div>
							</div>
							<dl align="left">
								<dt>${sAQuestion.title }</dt>
								<dd>
									<textarea id="cQuestion${i.index }" name="zhitu${i.index }" rows="5" cols="40" readonly="readonly">${sAQuestion.studentAnswer.answer0}</textarea>
									<script type="text/javascript">
										KindEditor.create('textarea[name="zhitu${i.index }"]', {
											width : '75%',
											height : '150px',
											resizeType : 1,
										    items: ['bold', 'italic', 'underline', 'strikethrough', 'removeformat', '|','subscript','superscript',
									                'shcode', 'image', 'quote', '|', 'source'],
									     uploadJson : '/fileUploadUtil/kindeditorupload',
								         fileManagerJson : '/fileUploadUtil/kindeditorfilemanager',
								         allowFileManager : true,
								         readonlyMode:true,
								         afterBlur: function(){this.sync();}
										});
									</script>
								该题得分：	<input class="input-mini" type="text" style="margin-top: 10px;" id="answerSQ_${sAQuestion.id}_ ${sAQuestion.quesPoint}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/>
								</dd>
							</dl>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${not empty zhibiaoQuestionList}">
					<div class="subject" style="width: 905px;" align="left">
						<p style="font-weight: bold;">九、制表题</p>
					</div>
					<c:forEach items="${zhibiaoQuestionList}" var="sAQuestion" varStatus="i" begin="0">
						<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
							<!-- 题号 -->
							<div class="problem_num" align="left">
								<div class="sign_title">
									第<span>${i.index+1}</span>题，本小题 ${sAQuestion.quesPoint} 分<a name="sAQuestion${i.index}" ></a>
								</div>
							</div>
							<dl align="left">
								<dt>${sAQuestion.title }</dt>
								<dd>
									<textarea id="cQuestion${i.index }" name="zhibiao${i.index }" rows="5" cols="40" readonly="readonly">${sAQuestion.studentAnswer.answer0}</textarea>
									<script type="text/javascript">
										KindEditor.create('textarea[name="zhibiao${i.index }"]', {
											width : '75%',
											height : '150px',
											resizeType : 1,
										    items: ['bold', 'italic', 'underline', 'strikethrough', 'removeformat', '|','subscript','superscript',
									                'shcode', 'image', 'quote', '|', 'source'],
									     uploadJson : '/fileUploadUtil/kindeditorupload',
								         fileManagerJson : '/fileUploadUtil/kindeditorfilemanager',
								         allowFileManager : true,
								         readonlyMode:true,
								         afterBlur: function(){this.sync();}
										});
									</script>
									该题得分：<input class="input-mini" type="text" style="margin-top: 10px;" id="answerSQ_${sAQuestion.id}_${sAQuestion.quesPoint}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/>
								</dd>
							</dl>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${not empty shituQuestionList}">
					<div class="subject" style="width: 905px;" align="left">
						<p style="font-weight: bold;">十、识图题</p>
					</div>
					<c:forEach items="${shituQuestionList}" var="sAQuestion" varStatus="i" begin="0">
						<div class="problem" id="tm_1" index="1" topicid="2701"	style="width: 900px; display: block;">
							<!-- 题号 -->
							<div class="problem_num" align="left">
								<div class="sign_title">
									第<span>${i.index+1}</span>题，本小题 ${sAQuestion.quesPoint} 分<a name="sAQuestion${i.index}" ></a>
								</div>
							</div>
							<dl align="left">
								<dt>${sAQuestion.title }</dt>
								<dd>
									<textarea id="cQuestion${i.index }" name="shitu${i.index }" rows="5" cols="40" readonly="readonly">${sAQuestion.studentAnswer.answer0}</textarea>
									<script type="text/javascript">
										KindEditor.create('textarea[name="shitu${i.index }"]', {
											width : '75%',
											height : '150px',
											resizeType : 1,
										    items: ['bold', 'italic', 'underline', 'strikethrough', 'removeformat', '|','subscript','superscript',
									                'shcode', 'image', 'quote', '|', 'source'],
									     uploadJson : '/fileUploadUtil/kindeditorupload',
								         fileManagerJson : '/fileUploadUtil/kindeditorfilemanager',
								         allowFileManager : true,
								         readonlyMode:true,
								         afterBlur: function(){this.sync();}
										});
									</script>
									该题得分：<input class="input-mini" type="text" style="margin-top: 10px;" id="answerSQ_${sAQuestion.id}_${sAQuestion.quesPoint}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/>
								</dd>
							</dl>
						</div>
					</c:forEach>
				</c:if>
			</td>
		</tr>
	</table>
		<div align="center">
			<input type="button" onclick="submitAnswer()" class="btn tbtn-primary" style="margin: 10px;" value="提交成绩"/>
		</div>
	</form>
	<script type="text/javascript">
		var examId = $("#examId").val();
		var examDetailId = $("#examDetailId").val();
		var studentId=$("#studentId").val();
		var examClassId=$("#examClassId").val();
		var status=$("#status").val();
		$(document).ready(function() {
			$("input[id^='answerSQ_']").blur(function(){
				var questionId=$(this).attr("id").split("_")[1];
				var totalScore=$(this).attr("id").split("_")[2];
				var score=$(this).val();
				//alert(score);
				if(parseInt(totalScore)<parseInt(score)){
					alertx("您输入的分数大于该题总分,请重新输入！");
					$(this).val("");
				}else{
					if(score==""){
						score='0';
					}
					 $.ajax({
						url:"${ctx}/teacherStudentAnswer/updateAnswer",
						type:"post",
						cache:false,
						data:{"questionId":questionId,"score":score,"examId":examId,"studentId":studentId},
					});
				}
			});
		});
		 
		/* 提交答案 */
		function submitAnswer(){
			jConfirm("是否确定提交成绩？","阅卷结束",function(r){
				if(r){
					window.location.href = "${ctx}/teacherStudentAnswer/totalStudentScore?examDetailId="+examDetailId+"&studentId="+studentId+"&examClassId="+examClassId+"&examId="+examId+"&status="+status; 
				}
			})
		}
	</script>
</body>
</html>