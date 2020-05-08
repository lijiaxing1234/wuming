<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>试卷管理</title>
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
		<li class="active"><a href="#">试卷查询</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="exam" action="${ctx}/questionlib/exam/examPlatform" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>专业：</label>
				<sys:treeselect id="specialty" ontextchange="true" name="specialty.id" value="${course.specialty.id}" labelName="specialty.name" labelValue="${course.specialty.title}"
					title="专业" url="/questionlib/specialty/treeData" cssClass="required"/>
			</li>
			<li><label>课程：</label>
				<form:select path="courseId" id="courseId" style="width:150px;" onchange="loadVersion(this);"></form:select>
			</li>
			<li><label>版本：</label>
				<select name="versionId" id="courseVesionSelect"  class="input-medium" >
					<c:forEach items="${courseVesionList}" var="courseVesion">
					   	<option value="${courseVesion.id}"  ${courseVesionId==courseVesion.id?"selected":""}>${courseVesion.title} </option>
					</c:forEach>
			  </select>
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
				<th>专业</th>
				<th>课程</th>
				<th>版本</th>
				<th>学校</th>
				<th>试卷名称</th>
				<th>考试时间</th>
			
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="exam" varStatus="s">
			<tr>
				<td>${s.index+1+page.pageSize*(page.pageNo-1)}</td>
				<td>
					${exam.specialtyName}
				</td>
				<td>
					${exam.courseName}
				</td>
				<td>
					${exam.versionName}
				</td>
				<td>
					${exam.schoolName}
				</td>
				<td>
					${exam.name}
				</td>
				<td>
					<fmt:formatDate value="${exam.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>