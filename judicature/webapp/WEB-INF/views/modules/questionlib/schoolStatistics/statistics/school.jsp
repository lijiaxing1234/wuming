<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学校管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		  function chengeCourse() {

				$("#courseId").empty();
			     //调用Ajax获取
				  $.getJSON("${ctx}/questionlib/common/getCourseJSONBySpecialtyId?specialtyId="+$("#specialtyId").val(),{ r:Math.random() },function(data){
					 
						for (var i=0; i<data.length; i++){
							if (i==0) {
								$("#courseId").append("<option value='"+data[i].id+"'>"+data[i].title+"</option>"); 
							} else {
								$("#courseId").append("<option value='"+data[i].id+"'>"+data[i].title+"</option>"); 
							}
							
						}
				  });
			}
		  
		  function loadVersion() {
			  
		     //调用Ajax获取版本信息
		        $("#courseVesionSelect").empty();
		     
			  $.getJSON("${ctx}/questionlib/courseKnowledge/getCourseQuestionByCouresId?id="+$("#courseId").val(),{ r:Math.random() },function(data){
					 for (var i=0; i<data.length; i++){
						  if(i==0){
						 	 $("#courseVesionSelect").append("<option value=\""+data[i].id+"\" \"selected\">"+data[i].value+"</option>"); 
						  }else{
							  $("#courseVesionSelect").append("<option value=\""+data[i].id+"\">"+data[i].value+"</option>"); 
						  }
						 
						}
				});
			  
			 
		} 
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">学校统计</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="school" action="${ctx}/questionlib/statistics/school" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		<li>
			<label class="control-label">学校名称：</label>
			
				<form:input path="schoolName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
			
		</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width:20px;">序号</th>
				<th>学校名称</th>
				<th>教师数量</th>
				<th>学生数量</th>
				<th>题库数量</th>
				<th>试卷数量</th>
				
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="school" varStatus="s">
			<tr>
				<td>${s.index+1+page.pageSize*(page.pageNo-1)}</td>
				<td>
					${school.schoolName}
				</td>
				<td>
					${school.teacherCount}
				</td>
				<td>
					${school.studentCount}
				</td>
				<td>
					${school.questionlibCount}
				</td>
				<td>
					${school.examCount}
				</td>
		
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>