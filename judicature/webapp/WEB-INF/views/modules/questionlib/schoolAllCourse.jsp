<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程管理</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/questionlib/schoolCourse/getCourseListBySchoolId">已拥有课程</a></li>
	</ul>
	<sys:message content="${message}"/>
	<input id="addCourseButton" class="btn btn-primary" type="button" value="新增课程" onclick="toAddSchoolCoursePage()" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed" style="margin-top: 5px;">
		<thead>
			<tr>
				<th><!-- <input type="checkbox" id="checkAllOrZero"/> --></th>
			    <th>专业名称</th>
				<th>课程名称</th>
				<th>更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${schoolCourseList}" var="course">
				<tr>
					<td style="width: 15px;">
						<input type="checkbox" name="aa" value="${course.id}">
					</td>
					<td>
						${course.title}
					</td>
				    <td>
						${questionlib:getSpecialtyByID(course.specialtyId).title}
					</td>
					<td>
						<fmt:formatDate value="${course.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="100" style="text-align: center;">
					<input id="delCourseButton" class="btn btn-primary" type="button" value="删除选定课程" onclick="delCourseFromSchool()" />
				</td>
			</tr>
		</tbody>
		
	</table>
	<script type="text/javascript">
		function toAddSchoolCoursePage(){
			window.location.href = "${ctx}/questionlib/schoolCourse/getCourseListNotINSchool";
		}
		
		function delCourseFromSchool(){
			var checked_value = [];
			$('input[name="aa"]:checked').each(function(){
				checked_value.push($(this).val());
			});
			if(checked_value.length == 0){
				alert("请选择课程");
			}else{
				alert(checked_value);
				window.location.href = "${ctx}/questionlib/schoolCourse/deleteCourseFromSchool?courseIds=" + checked_value;
			}
		}
	</script>	
	
</body>
</html>