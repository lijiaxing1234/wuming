<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>教师管理</title>
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
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/questionlib/teacher/importTeachers" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="teacherFile" name="teacherFile" type="file" style="width:330px;text-indent: 0px;line-height: 0px;"/>
			<br/>
			<br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   " />
			<a href="${ctxStatic}/excelTemplate/teacherMessage.xlsx">下载模板</a>
		</form>
	</div>
	<div id="showHideDiv" style="width: 100%;height: 100%;position: absolute; top: 0;display: none;">
		<iframe id="classCourseVersionPage" src="" style="width: 950px;min-height: 450px;position: absolute;top: 200px;left: 400px;padding: 15px 4px 4px;border: 1px solid #ccc;text-align: center;background: #fff;">
		</iframe>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/questionlib/teacher/">教师列表</a></li>
		<shiro:hasPermission name="questionlib:teacher:edit"><li><a href="${ctx}/questionlib/teacher/form">教师添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="teacher" action="${ctx}/questionlib/teacher/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>登录名：</label>
				<form:input path="loginName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>工号：</label>
				<form:input path="no" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<shiro:hasPermission name="questionlib:teacher:download">
					<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>
				</shiro:hasPermission>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width:20px;">序号</th>
				<th>登录名</th>
				<th>工号</th>
				<th>姓名</th>
				<!-- <th>邮箱</th> -->
				<th>电话</th>
				<th>手机</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="questionlib:teacher:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="teacher" varStatus="s">
			<tr>
				<td>${s.index+1+page.pageSize*(page.pageNo-1)}</td>
				<td><a href="${ctx}/questionlib/teacher/form?id=${teacher.id}">
					${teacher.loginName}
				</a></td>
				<td>
					${teacher.no}
				</td>
				<td>
					${teacher.name}
				</td>
				<%-- <td>
					${teacher.email}
				</td> --%>
				<td>
					${teacher.phone}
				</td>
				<td>
					${teacher.mobile}
				</td>
				<td>
					<fmt:formatDate value="${teacher.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${teacher.remarks}
				</td>
				<shiro:hasPermission name="questionlib:teacher:edit"><td>
    				<a href="${ctx}/questionlib/teacher/form?id=${teacher.id}">修改</a>
    				<shiro:hasPermission name="questionlib:teacher:delete">
						<a href="${ctx}/questionlib/teacher/delete?id=${teacher.id}" onclick="return confirmx('确认要删除该教师吗？', this.href)">删除</a>
					</shiro:hasPermission>
					<a href="javascript:void(0);" onclick="openClassCourseDiv('${teacher.id}')">班级课程管理</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
		$(function(){
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
		})
	
	/* 	function openClassCourseDiv(teacherId){
			$("#classCourseVersionPage").attr("src","${ctx}/questionlib/gccvtt/classCourseList?teacherId="+teacherId);
			$("#showHideDiv").show();
		} */
		
		
		
		function openClassCourseDiv(teacherId){
			/* 	$("#classStudentPage").attr("src","${ctx}/questionlib/student/findClassStudentByClassId?schoolClass.id="+classId);
			    $("#showHideDiv").show();
			 */	
				 
			    var src ="iframe:${ctx}/questionlib/gccvtt/classCourseList?teacherId="+teacherId;
			    var title ="分配课程和班级";	
				top.$.jBox(src,{
					title:title,
					width: 750,
				    height: 650,
					buttons:{}, 
				    bottomText:"",
				    loaded:function(h){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					},
				    closed:function(){
						$("#searchForm").submit();
						//window.location.href="${ctx}/questionlib/courseKnowledge/list";
					}
		     }); 
			}
	</script>
</body>
</html>