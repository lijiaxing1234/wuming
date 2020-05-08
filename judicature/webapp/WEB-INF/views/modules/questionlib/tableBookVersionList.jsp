<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>书籍检查管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#checkBoxAll").click(function() {
				if ($(this).attr("checked") == "checked") {
					$("input[type='checkbox']").each(function() {
						if ($(this).attr("disabled") != "disabled") {
							$(this).attr("checked",true);
						}
					});
				} else {
					$("input[type='checkbox']").each(function () {
						if ($(this).attr("disabled") != "disabled") 
							$(this).attr("checked",false);
					}); 
				}
				
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		var allarr = [];
		//click获取ckeckbox值
		function addvalue(obj, verId) {
			var checked = $(obj).attr("checked");
			if (checked) {
				if (allarr.length > 0) {
					for ( var i = 0; i < allarr.length; i ++) {
						if (allarr[i] == verId) {
							return;
						}
					}
					allarr.push(verId);
				} else {
					allarr.push(verId);
				}
			} else {
				for ( var i = 0; i < allarr.length; i ++) {
					if (allarr[i] == verId) {
						allarr.splice(i, 1);
					}
				}
			}
		}
		
		function submitAll() {
			if ($("#checkBoxAll").attr("checked") == 'checked') {
				var aa;
				$("input[name='checkbox']:checked:checked").each(function(){
					aa +=',' + $(this).attr('bookId');
				});
				$.ajax({
					type : "POST",
					url : "${ctx}/questionlib/tableBookVersion/appList?verIds=" + aa,
					cache : false,
					dataType : "text",
					success : function (data) {
						
						location.reload();
						$("input[type='checkbox']").each(function () {
							if ($(this).attr("disabled") != "disabled") 
								$(this).attr("checked",false);
						}); 
					}
				});
			} else {
				var bookInfors = allarr.join(",");
				$.ajax({
					type : "POST",
					url : "${ctx}/questionlib/tableBookVersion/appList?verIds=" + bookInfors,
					cache : false,
					dataType : "text",
					success : function (data) {
						
						location.reload();
						$("input[type='checkbox']").each(function () {
							if ($(this).attr("disabled") != "disabled") 
								$(this).attr("checked",false);
						}); 
					}
				});
			}
			
			
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/questionlib/tableBookVersion/">书籍检查列表</a></li>
		
	</ul>
	<form:form id="searchForm" modelAttribute="tableBookVersion" action="${ctx}/questionlib/tableBookVersion/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>电子书名称：</label>
				<form:input path="bookName" htmlEscape="false" maxlength="1000" class="input-medium"/>
			</li>
			<li><label>作者：</label>
				<form:input path="bookAuthors" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>isbn：</label>
				<form:input path="isbn" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="checkState" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('check_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>打开：</label>
				<form:select path="isOpen" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('check_result')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>封面：</label>
				<form:select path="isCover" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('check_result')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>版权页：</label>
				<form:select path="isCopyright" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('check_result')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>目录：</label>
				<form:select path="isCatalog" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('check_result')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>图片：</label>
				<form:select path="isImage" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('check_result')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>排版：</label>
				<form:select path="isLayout" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('check_result')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>乱码：</label>
				<form:select path="isCode" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('check_result')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>我的：</label>
				<form:checkbox path="isMy" value="1"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns">
			<input id="btnSubmit" class="btn btn-primary"
				type="button" value="批量提交检查" onclick="submitAll()" />
				</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input id="checkBoxAll" type="checkbox" onclick="checkAll()"/></th>
				<th>电子书名称</th>
				<th>作者</th>
				<th>isbn</th>
				<th>状态</th>
				<shiro:hasPermission name="questionlib:tableBookVersion:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tableBookVersion">
			<tr>
				<td><input name="checkbox" id="checkbox" type="checkBox" bookId="${tableBookVersion.id}" onclick="addvalue(this,'${tableBookVersion.id}')"/></td>
				<td><a href="${ctx}/questionlib/tableBookVersion/form?id=${tableBookVersion.id}">
					${tableBookVersion.bookName}
				</a></td>
				<td>
					${tableBookVersion.bookAuthors}
				</td>
				<td>
					${tableBookVersion.isbn}
				</td>
				<td>
					${fns:getDictLabel(tableBookVersion.checkState, 'check_state', '')}
				</td>
				<shiro:hasPermission name="questionlib:tableBookVersion:edit"><td>
    				<a href="${ctx}/questionlib/tableBookVersion/form?id=${tableBookVersion.id}">修改</a>
					<a href="${ctx}/questionlib/tableBookVersion/delete?id=${tableBookVersion.id}" onclick="return confirmx('确认要删除该书籍检查吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>