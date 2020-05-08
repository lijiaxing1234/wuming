<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学校管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/modules/questionlib/js/questionlibSelect.js"></script>
	<script type="text/javascript">
	
	 function chengeCourse(ids,names){
		 $.utils.BindSelect("courseId",[]);
		 $.utils.BindSelect("courseVesionSelect",[]);
		 $.utils.BindSelect("courseId", "/questionlib/common/getCourseBySpecialtyId?specialtyId="+ ids[0]);
	 }
	 
	 $(function(){
		  $("#courseId").off("change").on("change",function(e){
			 var selectId=e.val;
			 $.utils.BindSelect("courseVesionSelect", "/questionlib/common/getCourseVersionByCouresId?courseId="+selectId);
		 }); 
		 
	 });
			 
	  function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
       	return false;
      }
	 
	  function exportClick(element){
		  $("#searchForm").attr("action",element.href).submit();
		  return false;
	  }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">试题统计</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="staticDTO" action="${ctx}/questionlib/statistics/question" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>专业： </label>
				<sys:treeselect id="specialty" ontextchange="true" name="specialty.id" value="${staticDTO.specialtyId}" labelName="specialty.name" labelValue="${ questionlib:getSpecialtyByID(staticDTO.specialtyId).title}"
					title="专业" url="/questionlib/specialty/treeData" cssClass="required"/>
			</li>
			<li><label>课程：</label>
				<select class="courseSelect" name="courseId" id="courseId" style="width:150px;" >
				   <option  value="">请选择</option>
				   <c:forEach items="${ empty staticDTO.specialtyId ? null: (questionlib:getCourseBySpecialtyId(staticDTO.specialtyId))}" var="course">
						<option value="${course.id}" ${courseId==course.id?"selected":""}  >${course.title} </option>
				   </c:forEach>
			   </select>
			</li>
			<li><label>版本：</label>
			
			  <select   name="versionId" id="courseVesionSelect"  class="input-medium" >
			 	 <option value="">请选择</option>
			 	 <c:forEach items="${empty courseId ? null:(questionlib:getCourseVersionByCourseId(courseId))}" var="courseVesion">
				   		<option value="${courseVesion.id}" ${versionId==courseVesion.id?"selected":""}  >${courseVesion.title} </option>
				 	</c:forEach>
			  </select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询"   onclick="$('#searchForm').attr('action','${ctx}/questionlib/statistics/question').submit();" /></li>
			<shiro:hasPermission name="questionlib:statistics:question:export">
			  <li class="btns"><a href="${ctx }/questionlib/statistics/questionExport"  onclick="return exportClick(this);"   class="btn btn-primary" >导出</a></li>
			</shiro:hasPermission>
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
				<c:forEach var="item" items="${fns:getDictList('question_type')}">
				   <th>${item.label }</th>
				</c:forEach>
				
				<!-- <th>多选题</th>
				<th>填空题</th>
				<th>计算题</th>
				<th>简答题</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="question" varStatus="s">
			<tr>
				<td>${s.index+1+page.pageSize*(page.pageNo-1)}</td>
				<td>
					${question.specialtyName}
				</td>
				<td>
					${question.courseName}
				</td>
				<td>
					${question.versionName}
				</td>
				
				<c:forEach var="item" items="${fns:getDictList('question_type')}">
				   <td> 
				   
				       ${questionlib:getStaticDTOValue(item.value,question)}
				   </td>
				</c:forEach>
				
				<%-- <td>
					${question.count1}
				</td>
				<td>
					${question.count2}
				</td>
				<td>
					${question.count3}
				</td>
				<td>
					${question.count4}
				</td>
				<td>
					${question.count5}
				</td>
					<td>
					${question.count6}
				</td>
				<td>
					${question.count7}
				</td>
				<td>
					${question.count8}
				</td>
				<td>
					${question.count9}
				</td>
				<td>
					${question.count10}
				</td>
				<td>
					${question.count11}
				</td> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
<!-- 	<script>
	
	chengeCourse() ;
	
	firstLoadVersion();
	</script> -->
</body>
</html>