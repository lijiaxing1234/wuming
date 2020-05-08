<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_blank"/>
	
	<title>知识点班级统计管理</title>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<%--<%@include file="/WEB-INF/views/teacher/include/validation.jsp" %>
	<%@include file="/WEB-INF/views/teacher/include/date.jsp" %> --%>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel:4}).show();
			 /*$("#listForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				//errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			}); */
		});
	</script>
</head>
<body>
		<table id="treeTable" class="table table-striped table-bordered table-condensed" style="margin-top: 20px;">
			<thead>
				<tr>
					<th>本次被测知识点</th>
					<th>掌握情况(全班答对率)</th>
					<!-- <td>答对题数</td>
					<td>全部题数</td> -->
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			 <c:forEach items="${list}" var="item" varStatus="index">
				<tr id="${item.knowId}" pId="${item.knowParentId ne parent.knowParentId ? item.knowParentId :'0' }">
					<td style="text-align: left;">${item.knowTitle }</td>
					<td> 
					   <c:choose>
					     <c:when test="${item.totalCount gt 0 }">
					        ${item.correctRate}% 
					     </c:when>
					     <c:otherwise>
					        /
					     </c:otherwise>
					   </c:choose>
					</td>
					<%-- <td>知识点Id:${ item.knowId} /答对题数:${item.correctCount }</td>
					<td>${item.totalCount}</td> --%>
					<td>
					    <a href="${ctx }/statistics/staticKnowledgeByexamIdAndClassIdInfo?knowId=${item.knowId}&examId=${examId }&classId=${classId}&backUrl=${backUrl}" style="background: url(${ctxStatic}/icon/check_1.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" title="查看"></a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	 <div class="form-actions pagination-left">
	   <!-- <input id="btnSubmit" class="btn btn-primary" type="button" onclick="history.go(-1);" value="返回"/> -->
	   <c:choose>
	       <c:when test="${ empty backUrl }">
	           <a href="${ctx}"  target="_top" class="btn tbtn-primary">返回</a>
	       </c:when>
	       <c:otherwise>
	          <a href="${backUrl}"  class="btn tbtn-primary">返回</a>
	       </c:otherwise>
	   </c:choose>
	 </div>
</body>
</html>