<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_blank"/>
	<title>课堂例题</title>
	<style type="text/css">
		.addColor td {	background-color: #BEBEBE; }
		.table tbody tr{cursor:pointer;}
		.form-search select{margin-right:5px;}
		.breadcrumb{padding:25px 0 0 0;}
		.form-search label{width:auto;margin-bottom:0;}
	</style>
</head>
<body>
    <form:form id="searchForm" modelAttribute="question" action="${ctx}/classExample/selectQuestion" method="post" class="breadcrumb form-search ">
		<input id="teacherQuestionId" name="teacherQuestionId" type="hidden" value="${teacherQuestionId}"/>
		<label>题型选择：</label>
		<form:select path="quesType">
			 <form:option value="">全部</form:option>
		    <form:options items="${fns:getDictList('question_type')}"  itemLabel="label" itemValue="value"/>
		</form:select>
		<label>难易程度：</label>
		<form:select path="quesLevel">
			 <form:option value="">全部</form:option>
		    <form:options items="${fns:getDictList('question_level')}"  itemLabel="label" itemValue="value"/>
		</form:select>
		<input id="btnSubmit" class="btn tbtn-primary" type="submit" value="查询"/>
	</form:form>
  
	<form:form id="listForm" modelAttribute="TeacherVersionQuestion" action="" method="post" class="form-horizontal">
		<table id="treeTable"  class="table  table-bordered table-condensed" style="margin-top: 20px;">
			<thead>
				<tr>
					<th></th>
					<th>编号</th>
					<th>考点</th>
					<th>题型</th>
					<th>难度</th>
					<th>分值</th>
				</tr>
			</thead>
			<tbody>
			 <c:forEach items="${list}" var="item" varStatus="index">
				<tr>
					<td><input id="comment_${item.id}_${item.courseKnowledge.id}" name="questionId" type="checkbox" value="${item.id}" onclick="selectAll();"/></td>
					<td>${index.index+1} <input type="hidden"  value="${item.id}"></td>
					<%-- <td>${item.examCode}</td> --%>
					<td>${item.courseKnowledge.title}</td>
					<td>
					    ${fns:getDictLabel(item.quesType,'question_type','')}
					</td>
					<td>${item.quesLevel}级</td>
					<td>${item.quesPoint}分
					
						 <input type="hidden"  value="${item.courseKnowledge.id}">
						<div style="display: none;">
						   <div class="control-group"> 
								<label class="control-label">知识点:</label>
								<div class="controls">
									${item.courseKnowledge.title}
								</div>
							</div>
							<div class="control-group"> 
								<label class="control-label">题型:</label>
								<div class="controls">
									${fns:getDictLabel(item.quesType,'question_type','')}
								</div>
							</div>
							<div class="control-group"> 
								<label class="control-label">难度:</label>
								<div class="controls">
									${item.quesLevel}级
								</div>
							</div>
							<div class="control-group"> 
								<label class="control-label">分值:</label>
								<div class="controls">
									${item.quesPoint}分
								</div>
								
							</div>
							<div class="control-group"> 
								<label class="control-label">题目:</label>
								<div class="controls">
									${item.title}
								</div>
							</div>
							<div class="control-group"> 
								<label class="control-label">答案:</label>
								<div class="controls">
									<c:choose>  
									   <c:when test="${item.quesType eq '11'}">   
										   <c:if test="${item.answer eq '1'}">
												正确
											</c:if>
											<c:if test="${item.answer eq '2'}">
												错误
											</c:if>
									   </c:when>  
									   <c:otherwise> 
										   ${item.answer}
									   </c:otherwise>  
									</c:choose>  
									<c:if test="${!empty item.answer1}">
										,${item.answer1}									
									</c:if>
									<c:if test="${!empty item.answer2}">
										,${item.answer2}									
									</c:if>
									<c:if test="${!empty item.answer3}">
										,${item.answer3}									
									</c:if>
									<c:if test="${!empty item.answer4}">
										,${item.answer4}									
									</c:if>
									<c:if test="${!empty item.answer5}">
										,${item.answer5}									
									</c:if>
									<c:if test="${!empty item.answer6}">
										,${item.answer6}									
									</c:if>
									<c:if test="${!empty item.answer7}">
										,${item.answer7}									
									</c:if>
									<c:if test="${!empty item.answer8}">
										,${item.answer8}									
									</c:if>
									<c:if test="${!empty item.answer9}">
										,${item.answer9}									
									</c:if>
								</div>
							</div>
							<div class="control-group"> 
								<label class="control-label">讲解:</label>
								<div class="controls">
									${item.description}
								</div>
							</div>
						</div>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	 </form:form>
	 <div id="content" style="border: 1px solid #e5e5e5;margin-top: 20px;padding: 20px 10px;">
	 
	 </div>
	 
	 
	 <script type="text/javascript">
		 function selectAll(){
		    	var obj=$("#treeTable :input[type=checkbox]");
		    	var value;
		    	var questionIds;
		    	if(obj.length){
			    	for(i=0;i<obj.length;i++){
			    		value=obj[i].checked;
			    		var questionId=obj[i].value;
			    		var knowId=$("input[id^='comment_']").attr("id").split("_")[2];
			    		if(value){
			    			if(questionIds==null){
				    			questionIds=questionId+":"+knowId;
			    			}else{
			    				questionIds=questionIds+","+questionId+":"+knowId;
			    			}
			    		}
			    	}
			    	parent.addHiddenInputValue(questionIds);
		    	}
		}
	    (function(){
	    	
	    	$("#treeTable tbody tr").click(function(){
	    		/* $(this).addClass("addColor").siblings().removeClass("addColor");
	    		var questionId = $(this).find("td:first-child input").attr("value"); */
	    		var knowId = $(this).find("td:last-child input").attr("value");
	    		var content = $(this).find("td:last-child div").html();
	    		$("#content").empty().html(content);
	    		//parent.addHiddenInputValue(questionId,knowId);
	    	});
	    	$("#treeTable tbody tr:first").trigger("click");
	    	
	    	
	    })();
	    (function(){
	    	 var frameObj = $("#content"),mainRight=$("body"),top=$("#searchForm");
				function wSize(){
					frameObj.height(mainRight.height()-top.height());
					var w=mainRight.width()/2-20;
					$("#listForm").width(w).css({"float":"left"});
					frameObj.width(w-20).css({"float":"left"});
				}
				$(window).resize(function(){
					wSize();
				});
				wSize();
	    	
	    })();
	 </script>
</body>
</html>