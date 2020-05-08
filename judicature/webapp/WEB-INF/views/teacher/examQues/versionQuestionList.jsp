<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
    <head>
       <title>试题管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	.nav-tabs{font-weight:normal;}	
	.pagination ul > li > a{border-color:#e5e5e5;}
	.pagination ul > .controls > a{border-color:#e5e5e5;}
	.nav-tabs{font-weight:normal;}
	.pagination ul > .disabled > a{color:#666;}
	.pagination{width:auto;margin:20px 0;text-align:center;}
	
		#importTable tr td{
			height: 30px;
			vertical-align: top;
		}
		table tr td input{
			vertical-align: middle;
		}
		.table th{color:#fff;background:#64dfda;background-image:-webkit-linear-gradient(top,#64dfda,#64dfda);}
		.btn-primary{background:#1c9993;background-image:-webkit-linear-gradient(top,#1c9993,#1c9993);}
		.btn-primary:hover,.btn-primary:focus,.btn-primary:active,.btn-primary.active,.btn-primary.disabled,.btn-primary[disabled] {color: #ffffff;background-color: #1c9993;*background-color: #1c9993;}
		.btn-primary:active,.btn-primary.active { background-color: #1c9993 \9;}
		.tbtn-primary{
			background-color:#1c9993 ! important;
			background-image:-webkit-gradient(linear,0 0,0 100%,from(#1c9993),to(#1c9993)) ! important;
			background-image:-webkit-linear-gradient(top,#1c9993,#1c9993) ! important;
			background-image:linear-gradient(to bottom,#1c9993,#1c9993) ! important;
			padding:4px 14px ! important;
			color:#fff ! important;
		}
	</style>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function importQues(targe){
			window.parent.importQuestion(targe);
			return false;
		}
	</script>
    </head>
    <body>
	    <ul class="nav nav-tabs">
			<li class="active"><a href="${ctx}/examQues/list?knowledgeId=${knowledgeId}">试题列表</a></li>
			<li><a href="${ctx}/examQues/form?knowledgeId=${knowledgeId}">试题添加</a></li>
		</ul>
		
		<form:form id="searchForm" modelAttribute="versionQuestion" action="${ctx}/examQues/list?knowledgeId=${knowledgeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>题干：</label>
				<form:input path="title" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>题型：</label>
				<form:select path="quesType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('question_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li> 
			<%-- <li class="btns"><li><a  class="btn btn-primary"   href="${ctx}/examQues/importDoc?knowledgeId=${knowledgeId}" onclick="return importQues(this);">导入试题</a></li>
			<li class="btns"><li><a   class="btn tbtn-primary"   href="${ctxStatic}/template/template.rar">试题导入模板</a></li> --%>
			<li><label>难度：</label>
				<form:select path="quesLevel" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('question_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>题库：</label>
				<form:select path="questionlibId" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${quesLibs}" itemLabel="title" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn tbtn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
    <sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width:20px;">序号</th>
				<th>知识点</th>
				<th>题型</th>
				<th>难度</th>
				<th>分值</th>
				<th>试题描述</th>
				<th>出题人</th>
				<th>专业</th>
				<th>课程</th>
				<th>版本</th>
				<th>题库</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="versionQuestion" varStatus="status">
			<tr>
				<td>${status.index+1 }</td>
				<td>
					${versionQuestion.examCode}
				</td>
				<td>
					${fns:getDictLabel(versionQuestion.quesType, 'question_type', '')}
				</td>
				<td>
					${fns:getDictLabel(versionQuestion.quesLevel, 'question_level', '')}
				</td>
				<td>
					${versionQuestion.quesPoint}
				</td>
				<td>
					<a href="${ctx}/examQues/form?id=${versionQuestion.id}&knowledgeId=${knowledgeId}">
					${versionQuestion.title}
					</a>
				</td>
				<td>
					${versionQuestion.writer}
				</td>
				<td>
					${questionlib:getSpecialtyByID(questionlib:getCourseByID(questionlib:getCourseVersionByVersionId(versionQuestion.versionId).courseId).specialtyId).title}
				</td>
				<td>
					${questionlib:getCourseByID(questionlib:getCourseVersionByVersionId(versionQuestion.versionId).courseId).title}
				</td>
				<td>
					${questionlib:getCourseVersionByVersionId(versionQuestion.versionId).title}
				</td>
				<td>
					${questionlib:getCourseQuestionlibById(versionQuestion.questionlibId).title}
				</td>
				<td>
    				<a href="${ctx}/examQues/form?id=${versionQuestion.id}&knowledgeId=${knowledgeId}" style="background: url(${ctxStatic}/icon/xg.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" title="修改" ></a>
					<a href="${ctx}/examQues/delete?id=${versionQuestion.id}&knowledgeId=${knowledgeId}" onclick="return confirmx('确认要删除该试题吗？', this.href)" style="background: url(${ctxStatic}/icon/del.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" title="删除" ></a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
    </body>
</html>
