<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="student_default"/>
<title>bbs论坛</title>
<%@include file="/WEB-INF/views/student/include/validation.jsp" %>
<script type="text/javascript">
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
<style>
.control-group{border-bottom:0 none;}
.control-group .bg-wt{width:auto;color:#000;border-left:5px solid #1c9993;padding-left:5px;font-size:20px;}
.control-group h2{padding:10px;line-height:25px;border:1px solid #56a0a1;margin-top:10px;word-break:break-all;text-indent:24px;}
.table-tr tr th{font-weight:normal;background:#fff;background-image:-webkit-linear-gradient(top,#fff,#fff);color:#000;}
</style>
</head>
<body>
	<sys:message content="${message}"/>
    <form:form id="searchForm" action="${ctx}/studentNews/answerlist?question.id=${ques.id}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<div class="control-group" >
		    <p class="bg-wt">问题详情：</p>
           	<h2 style="width:96%;">${fns:replaceHtml(ques.detail)}
           	<span style="float: right;">
		     	<fmt:formatDate value="${ques.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		   	</span>
		   	<div style="clear:both;"></div>
           	</h2>
		</div>
		
		<input type="button"  id="addAnswerBtn" class="btn  sbtn-primary" value="我来回答"  />
		<a href="${ctx}/studentNews/questionList" class="btn  sbtn-primary">返回问题列表</a>
	</form:form>
	

	<div class="control-group" style="margin-left:50px;">
	   <p class="bg-wt">回答列表：</p>
	</div>
	<c:forEach items="${page.list}" var="item">
	<table  class="table table-striped table-bordered table-condensed table-tr" style="margin-left:50px;word-break:break-all;">
			<tr>
			 	<th style="width: 20%;word-break:break-all;">
			      <c:if test="${not empty item.teacher.name}">
			        ${item.teacher.name}
			      </c:if>
			      <c:if test="${not empty item.student.name }">
			         ${item.student.name }
			      </c:if>
				</th>
				<th style="text-align: left;word-break:break-all;" onclick="$('#a_${item.id}').toggle()">
				   <a style="cursor: pointer;">${fns:abbr(fns:replaceHtml(item.detail),80)}</a>
				   <span style="float: right;">
				     <fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				   </span>
				</th>
			</tr>
			<tr id="a_${item.id}" style="background:#fdfdfd;display:none;"><td colspan="2" style=" text-align: left;word-break:break-all;">${fns:replaceHtml(item.detail)}</td></tr>
	</table>
	</c:forEach>
	<div class="pagination">${page}</div>
	
	<div class="modal hide fade in" id="addAnswer" style="width: 400px; margin-left: -100px; top: 20%;"  >
		<form:form action="${ctx}/studentNews/saveAnswerForm"  method="post" class="form-horizontal" id="addAnswerForm">
		    <input type="hidden"  name="quesId" value="${ques.id }">
		    <div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h2>我来回答(新回答在审核通过后才会在列表显示)</h2>
			</div>
			<div class="modal-body" style="overflow-x: hidden;  overflow: hidden;  height:237px;">
				<div id="messageBoxModel" class="alert hide">
				</div>
			  	<div class="control-group">
						<%-- <form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/> --%>
						<textarea id="content" name="content" rows="3" cols="200" style="width: 335px !important;height: 200px !important;" class="input-xlarge required" minlength="4" maxlength="200" placeholder="请至少写4个字" style="margin: 0px; height: 181px; width: 339px;"  maxlength="200"></textarea>
						<span class="help-inline"><font color="red">*最多添加200字</font></span>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn sbtn-primary" data-dismiss="modal">关闭</button>
				<input type="submit"  class="btn sbtn-primary"  value="提交"/>
			</div>
		</form:form>	   
	</div>
<script type="text/javascript">
  (function(){
	  $("#addAnswerBtn").click(function(){
		    $("#messageBoxModel").empty();
		    $("#addAnswerForm")[0].reset();
			$("#addAnswer").modal({
				show : true,
				backdrop : 'static'
			});
			/* .on('hide.bs.modal',function(){
				window.location.replace('${ctx}/bs/message/systemPushLog?pageNo=1');
				return false;
			}) */      	  	  
	  });
	  
	  $("#addAnswerForm").validate({
			submitHandler: function(form){
				loading('正在提交，请稍等...');
				form.submit();
			},
			errorContainer: "#messageBoxModel",
			errorPlacement: function(error, element) {
				/* $("#messageBoxModel").text("输入有误，请先更正。"); */
				/* if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				} */
				error.appendTo($("#messageBoxModel"));
			}
		});
	  
  })();
</script>
</body>
</html>