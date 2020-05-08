<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程版本管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		$("#btnExport").click(function(){
			 return confirmx("确认要导出吗？",function(){
					$("#searchForm").attr("action","${ctx }/questionlib/courseVesion/export");
					$("#searchForm").submit(); 
				});
			});
		$("#btnSubmit").click(function(){
					$("#searchForm").attr("action","${ctx}/questionlib/courseVesion/");
					$("#searchForm").submit(); 
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function chengeCourse() {
			var specialtyId=$("#specialtyId").val();
			$("#courseId").empty();
		     //调用Ajax获取
		     if(specialtyId==null || specialtyId==""){
		    	 $("#courseId").append("<option value='' selected>请选择</option>");
		     }else{
				  $.getJSON("${ctx}/questionlib/common/getCourseJSONBySpecialtyId?specialtyId="+$("#specialtyId").val(),{ r:Math.random() },function(data){
					  $("#courseId").append("<option value='' selected>全部</option>");
						for (var i=0; i<data.length; i++){
							//$("#courseId").append("<option value='"+data[i].id+"'>"+data[i].title+"</option>"); 
							  if (data[i].id == '${courseId}') {
								  $("#courseId").append("<option selected value='"+data[i].id+"'>"+data[i].title+"</option>");
								  $(".select2-chosen").html(data[i].title);
							  } else {
								  $("#courseId").append("<option value='"+data[i].id+"'>"+data[i].title+"</option>");
							  }
								 
						}
				  });
		     }
		}
			 
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/questionlib/courseVesion/list">课程版本列表</a></li>
		<shiro:hasPermission name="questionlib:courseVesion:edit"><li><a href="${ctx}/questionlib/courseVesion/form">课程版本添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="courseVesion" action="${ctx}/questionlib/courseVesion/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<ul class="ul-form">
			<li><label>版本名称：</label>
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			
			 <li><label>专业：</label>
				<sys:treeselect id="specialty" ontextchange="true" name="specialtyId" value="${specialtyId}" labelName="courseVesion.specialty.name" labelValue="${questionlib:getSpecialtyByID(specialtyId).title}"
					title="专业" url="/questionlib/specialty/treeData" cssClass="required" notAllowSelectParent="true" />
				
			 </li>
			
			 <li>
			  <label>课程：</label>
			   <select class="input-xlarge" name="courseId" id="courseId"  >
			   </select>
			</li>
			
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/></li>
			<shiro:hasPermission name="questionlib:courseVesion:export">
			 <li class="btns">
				 <input id="btnExport" class="btn btn-primary" type="button" value="版本导出"/>
			 <%-- <a class="btn btn-primary"  href="${ctx }/questionlib/courseVesion/export">版本导出</a> --%>
			 </li>
			</shiro:hasPermission>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	
	<script type="text/javascript">

chengeCourse();

</script>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width:20px;">序号</th>
			    <th>专业名称</th>
			    <th>课程名称</th>
				<th>版本名称</th>
				<shiro:hasPermission name="questionlib:courseVesion:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="courseVesion" varStatus="status">
			<tr>
				<td>${status.index+1+(page.pageNo-1)*page.pageSize }</td>	
			    <td>
					${questionlib:getSpecialtyByID(questionlib:getCourseByID(courseVesion.courseId).specialtyId).title}
				</td>
			    <td>
					${questionlib:getCourseByID(courseVesion.courseId).title}
				</td>
				<td><a href="${ctx}/questionlib/courseVesion/form?id=${courseVesion.id}">
					${courseVesion.title}
				</a></td>
				<shiro:hasPermission name="questionlib:courseVesion:edit"><td>
    				<a href="${ctx}/questionlib/courseVesion/form?id=${courseVesion.id}">修改</a>
    				<shiro:hasPermission name="questionlib:courseVesion:delete">
					<a href="${ctx}/questionlib/courseVesion/delete?id=${courseVesion.id}" onclick="return confirmx('确认要删除该课程版本吗？', this.href)">删除</a>
				</shiro:hasPermission>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>


</html>