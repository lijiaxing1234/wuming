<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>
<c:if test="${not empty studentAnswer }">
		<c:choose>
			<c:when test="${studentAnswer.questionType == 2 }">
				<p>
					我的答案为：
						<c:if test="${not empty studentAnswer.answer0 }">
							${studentAnswer.answer0 }	
						</c:if>
						<c:if test="${not empty studentAnswer.answer1 }">
							,${studentAnswer.answer1 }	
						</c:if>
						<c:if test="${not empty studentAnswer.answer2 }">
							,${studentAnswer.answer2 }	
						</c:if>
						<c:if test="${not empty studentAnswer.answer3 }">
							,${studentAnswer.answer3 }	
						</c:if>
						<c:if test="${not empty studentAnswer.answer4 }">
							,${studentAnswer.answer4 }	
						</c:if>
						<c:if test="${not empty studentAnswer.answer5 }">
							,${studentAnswer.answer5 }	
						</c:if>
						<c:if test="${not empty studentAnswer.answer6 }">
							,${studentAnswer.answer6 }	
						</c:if>
						<c:if test="${not empty studentAnswer.answer7 }">
							,${studentAnswer.answer7 }	
						</c:if>
						<c:if test="${not empty studentAnswer.answer8 }">
							,${studentAnswer.answer8 }	
						</c:if>
						<c:if test="${not empty studentAnswer.answer9 }">
							,${studentAnswer.answer9 }	
						</c:if>
				</p>
			</c:when>
			<c:when test="${studentAnswer.questionType != 2 }">
				<p>
					我的答案为：
						${studentAnswer.answer0 }
						${studentAnswer.answer1 }
						${studentAnswer.answer2 }
						${studentAnswer.answer3 }
						${studentAnswer.answer4 }
						${studentAnswer.answer5 }
						${studentAnswer.answer6 }
						${studentAnswer.answer7 }
						${studentAnswer.answer8 }
						${studentAnswer.answer9 }
				</p>
			</c:when>
		</c:choose>
		
	</c:if>
	<c:if test="${empty studentAnswer }">
		<p>我的答案为：无</p>
	</c:if>
	
	<c:if test="${not empty  question.answer0}">
		<c:choose>
			<c:when test="${question.quesType == 2}">
				<p>
					题目的正确答案为：
					<c:if test="${not empty question.answer0 and question.answer0 != '&nbsp;' }">
						${question.answer0 }
					</c:if>
					<c:if test="${not empty question.answer1 and question.answer1 != '&nbsp;' }">
						,${question.answer1 }
					</c:if>
					<c:if test="${not empty question.answer2 and question.answer2 != '&nbsp;' }">
						,${question.answer2 }
					</c:if>
					<c:if test="${not empty question.answer3 and question.answer3 != '&nbsp;' }">
						,${question.answer3 }
					</c:if>
					<c:if test="${not empty question.answer4 and question.answer4 != '&nbsp;' }">
						,${question.answer4 }
					</c:if>
					<c:if test="${not empty question.answer5 and question.answer5 != '&nbsp;' }">
						,${question.answer5 }
					</c:if>
					<c:if test="${not empty question.answer6 and question.answer6 != '&nbsp;' }">
						,${question.answer6 }
					</c:if>
					<c:if test="${not empty question.answer7 and question.answer7 != '&nbsp;' }">
						,${question.answer7 }
					</c:if>
					<c:if test="${not empty question.answer8 and question.answer8 != '&nbsp;' }">
						,${question.answer8 }
					</c:if>
					<c:if test="${not empty question.answer9 and question.answer9 != '&nbsp;' }">
						,${question.answer9 }
					</c:if>
				</p>
			</c:when>
			<c:when test="${question.quesType != 2}">
				<p>
					题目的正确答案为：
					${question.answer0 }
					${question.answer1 }
					${question.answer2 }
					${question.answer3 }
					${question.answer4 }
					${question.answer5 }
					${question.answer6 }
					${question.answer7 }
					${question.answer8 }
					${question.answer9 }
				</p>
			</c:when>
		</c:choose>
	</c:if>
	
	<c:if test="${empty  question.answer0}">
		<p>题目的正确答案为：无</p>
	</c:if>
	
	<c:if test="${not empty question.description }">
		<p>
			题目详解：${question.description }
		</p>
	</c:if>
	<c:if test="${empty question.description }">
		<p>题目详解：无</p>
	</c:if>