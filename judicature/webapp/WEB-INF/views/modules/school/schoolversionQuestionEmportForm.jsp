<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>题库管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	 
	
	
		$(document).ready(function() {
			chengeCourse();
			//$("#title").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
					
					top.$.jBox.close(true);
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		
		function downLoad()
		{
			$("#inputForm").submit();
			$("#inputForm").hide();
			loading('正在生成文档，请稍等...');
			
			setTimeout("top.$.jBox.close(true)",10000);
		     
		}
		
		//导出试题
		function emportQuestions(){
			var courseQuestionlibId = $("#questionlibId").val();
			var extId = $("#extId").val();
			var delFlag = $(".delFlagSelect").attr("checked","checked").val();
			//alert(delFlag);
			$.ajax({
		         url:"${ctx}/questionlib/versionQuestion/emportQuestionWord?courseQuestionlibId="+courseQuestionlibId+"&extId="+extId+"&delFlag="+delFlag,  
		         type:"post",  
		         cache:false,
		         data:$("#saveEmportDocMessage").serialize(),  
		         dataType:"json",
		         success:function(data) { 
		        	 // alert(data.message);
		        	  var docName = data.docName;
		        	   alert("docName"+docName);
		        	  window.location.href="${ctxStatic}/template/loadFile/"+docName;
		        	 // $("#emportForm").hide();
		         },  
		         error:function(data) {  
		              alert("导出失败!"); 
		         }
		    }); 
		}
	</script>
 <style type="text/css">
 	.form-horizontal{ margin-top: 40px;}
 </style>
</head>
<body>
    <sys:message content="${message}"/>
	<form id="inputForm" action="${ctx}/school/schoolversionQuestion/emportQuestionWord" method="post"  target="_parent">
		<input type="hidden" id="courseQuestionlibId" name="courseQuestionlibId" value="${courseQuestionlibId }">
			<table style="margin: 0 auto;" id="importTable">
				<tr>
					<td colspan="2" style="height: 10px;"></td>
				</tr>
				<tr>
					<th class="control-label">知识点：</th>
					<td style="height: 40px;">
						<sys:treeselect id="courseKnowledge" name="courseKnowledge.id" value="${versionQuestion.courseKnowledge.id}" labelName="courseKnowledge.title" labelValue="${versionQuestion.examCode}"
						title="知识点" url="/questionlib/versionQuestion/treeData?courseQuestionlibId=${courseQuestionlibId}" cssClass="required" checked="false" notAllowSelectParent="false" notAllowSelectRoot="false"/>
						<br/><font color="red">注：默认为空，导出所有知识点关联的试题，也可选择某个知识点下的试题</font>
					</td>
				</tr>
				<tr>
					<th class="control-label">题型：</th>
					<td style="height: 40px;">
						<select name="quesType" class="input-xlarge " id="questionType">
							<option value="" label=""/>
							<c:forEach items="${fns:getDictList('question_type')}" var="dict" varStatus="status">
								<option value="${ dict.value}">${dict.label }</option>
							</c:forEach>
							<%-- <form:options items="${fns:getDictList('question_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						 --%></select>
						<br/><font color="red">注：默认为空，导出所有题型关联的试题，也可选择导出某一种题型的试题</font>
					</td>
				</tr>
				<tr>
					<th class="control-label">难度：</th>
					<td style="height: 40px;">
						<select name="quesLevel" class="input-xlarge ">
							<option value="" label=""/>
							<c:forEach items="${fns:getDictList('question_level')}" var="dict" varStatus="status">
								<option value="${ dict.value}">${dict.label }</option>
<%-- 								<options items="${fns:getDictList('question_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
							</c:forEach>
						</select>
						<br/><font color="red">注：默认为空，导出所有难度关联的试题，也可选择导出某一难度的试题</font>
				</td>
				</tr>
				<tr>
					<td></td>
					<td style="height: 40px;align:center;">
				     <input id="btnSubmit" class="btn btn-primary" type="button" value="开始导出" onclick="downLoad();"/> 
					  
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="top.$.jBox.close(true);"/>
					</td>
				</tr>
			</table>
		</form>
</body>
</html>