<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>提问管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#selectAll").click(function(){
				  if ($(this).attr("checked")){  
				        $("input[name='ids']").attr("checked", true);  
				  } else {  
				        $("input[name='ids']").attr("checked", false);  
				  } 
			});
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
		<li class="active"><a href="${ctx}/school/shoolQuestion/">提问列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="uQuestion" action="${ctx}/school/shoolQuestion/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<ul class="ul-form">
			<%-- <li><label>标题名称：</label><form:input path="subject" htmlEscape="false" maxlength="50" class="input-medium"/></li> --%>
			<%-- <li><label>用户登录名：</label><form:input path="loginName" htmlEscape="false" maxlength="60" class="input-medium"/></li>
			<li><label>用户昵称：</label><form:input path="userName" htmlEscape="false" maxlength="60" class="input-medium"/></li> --%>
			<li><label>问题：</label><form:input path="detail" htmlEscape="false" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<c:choose>
				<c:when test="${uQuestion.delFlag eq 1}">
					
				</c:when>
				<c:otherwise>
					<shiro:hasPermission name="school.web:shoolQuestion:delete">
						<li class="btns"><input id="btnDelete" class="btn btn-primary" type="button" value="批量删除" onclick="return confirmx('确认要批量删除吗？',function(){ $('#formList #delFlag').val(1); $('#formList').submit();})" /></li>
					</shiro:hasPermission>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${uQuestion.delFlag eq 2}">
					
				</c:when>
				<c:otherwise>
					<shiro:hasPermission name="school.web:shoolQuestion:auditor">
						<li class="btns"><input id="btnDelete" class="btn btn-primary" type="button" value="批量审核" onclick="return confirmx('确认要审核吗？',function(){  $('#formList #delFlag').val(2);   $('#formList').submit();})" /></li>
					</shiro:hasPermission>
				</c:otherwise>
			</c:choose>
			
			<li><label>状态：</label><form:radiobuttons onclick="$('#searchForm').submit();" path="delFlag" items="${fns:getDictList('questionlib_del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<form action="${ctx}/school/shoolQuestion/delete" method="post" id="formList">
	  <input type="hidden" name="delFlag" id="delFlag" value="1" />
	  
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<!-- <th>标题</th> -->
				<th style="text-align:center;"><input type="checkbox" id="selectAll"  value="全选"/></th>
				<th style="text-align:center;width: 50%">问题</th>
				<th style="text-align:center;width: 15%">发起人</th>
				<th style="text-align:center;width: 15%">时间</th>
				<th style="text-align:center;width: 15%">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td style="text-align:center;">
					<input type="checkbox" name="ids"   value="${item.id}" title="${fns:replaceHtml(item.detail)}" />
				</td>
				<%-- <td>${item.subject }</td> --%>
				<td style="text-align:center;"><a href="javascript:" onclick="$('#q_${item.id}').toggle()">${fns:abbr(fns:replaceHtml(item.detail),80)}</a></td>
				<td style="text-align:center;">
				    <c:if test="${ not empty item.teacher.name }">
				      ${ item.teacher.name }
				    </c:if>
				    <c:if test="${not empty item.student.name }">
				      ${item.student.name }
				    </c:if>
				
				</td>
				
				<td style="text-align:center;"> <fmt:formatDate value="${item.modifytime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
				<td style="text-align:center;">
    				<%-- <a href="${ctx}/school/shoolQuestion/form?id=${item.id}">修改</a> --%>
    				<c:if test="${item.delFlag eq '0'}">
	    				<shiro:hasPermission name="school.web:shoolQuestion:auditor">
	    					<a href="${ctx}/school/shoolQuestion/delete?ids=${item.id}&delFlag=2" 
							onclick="return confirmx('确认要审核通过吗？', this.href)">审核通过</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="school.web:shoolQuestion:delete">
	    					<a href="${ctx}/school/shoolQuestion/delete?ids=${item.id}&delFlag=1" 
							onclick="return confirmx('确认要删除吗？', this.href)">删除</a>
						</shiro:hasPermission>
					</c:if>
					<c:if test="${item.delFlag eq '1'}">
						<shiro:hasPermission name="school.web:shoolQuestion:auditor">
						    <a href="${ctx}/school/shoolQuestion/delete?ids=${item.id}&delFlag=0" 
							onclick="return confirmx('确认要恢复到未审核吗？', this.href)">恢复到未审核</a>
						</shiro:hasPermission>
					</c:if>
					<c:if test="${item.delFlag eq '2'}">
	    				<shiro:hasPermission name="school.web:shoolQuestion:auditor">
	    					<a href="${ctx}/school/shoolQuestion/delete?ids=${item.id}&delFlag=0" 
							onclick="return confirmx('确认要恢复到未审核吗？', this.href)">恢复到未审核</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="school.web:shoolQuestion:delete">
	    					<a href="${ctx}/school/shoolQuestion/delete?ids=${item.id}&delFlag=1" 
							onclick="return confirmx('确认要删除吗？', this.href)">删除</a>
						</shiro:hasPermission>
					</c:if>
				</td>
			</tr>
			<tr id="q_${item.id}" style="background:#fdfdfd;word-break:break-all;display:none;"><td colspan="6">${fns:replaceHtml(item.detail)}</td></tr>
		</c:forEach>
		</tbody>
	</table>
	</form>
	<div class="pagination">${page}</div>
</body>
</html>