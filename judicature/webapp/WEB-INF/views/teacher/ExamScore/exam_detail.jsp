<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>录入成绩</title>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	  <%@include file="/WEB-INF/views/teacher/include/validation.jsp" %>
	<script type="text/javascript">
		 function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    } 
		 $(document).ready(function() {
				$("input[id^='examSubmit_']").blur(function(){
					var studentId=$(this).attr("id").split("_")[1];
					var score=$(this).val();
					var examDetailId=$("#detailId").val();
					var totalScore=$("#totalScore").val();
					if(score==""){
						score='0';
					}
					if(parseInt(totalScore)<parseInt(score)){
						alertx("您输入的分数大于试卷总分,请重新输入！");
						$(this).val("");
					}else{
						$.ajax({
							url:"${ctx}/examScore/saveScore",
							type:"post",
							cache:false,
							data:{"score":score,"studentId":studentId,"examDetailId":examDetailId},
						});
					}
					
					
				});
				
				$.validator.setDefaults({
					submitHandler: function(form){
						
					},
					//errorContainer: "#messageBox",
					errorPlacement: function(error, element) {
						if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
							error.appendTo(element.parent().parent());
						} else if(element.parent().is("span")){
							error.appendTo(element.parent());
						}else{
							error.insertAfter(element);
						}
					}
				}); 
		 });
		 function Button(){
			 window.location.href ="${ctx}/testPaper/testPaperList"; 
		 }
	  function submitButton(){
		 	 $("#examClassDetailForm").validate();
		 	 var examId=$("#examId").val();
			 var examClassId=$("#examClassId").val();
			/*  $("#examClassDetailForm").attr("action","${ctx}/examScore/updateStatus?examId="+examId+"&examClassId="+examClassId);
			 $("#examClassDetailForm").submit(); */
			 window.location.href ='${ctx}/examScore/updateStatus?examId='+examId+'&examClassId='+examClassId; 
		 } 
		function exportDOC(){
				/* top.$.jBox.confirm("确认要导出该班级下的所有学生吗？","系统提示",function(v,h,f){
					 var examId=$("#examId").val();
					 var examClassId=$("#examClassId").val();
					var examDetailId=$("#detailId").val();
				     var examDetailType=$("#detailIdType").val();
					if(v=="ok"){
						if(examDetailId==null||examDetailId==""){
							alertx("请先选择考试类型");
						}else{
							$("#searchForm").attr("action","${ctx}/examScore/exportClassStudent?examId="+examId+"&examClassId="+examClassId+"&examDetailId="+examDetailId+"&examDetailType="+examDetailType);
							$("#searchForm").submit();
						}
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px'); */
				
			 var examId=$("#examId").val();
			 var examClassId=$("#examClassId").val();
			var examDetailId=$("#detailId").val();
		     var examDetailType=$("#detailIdType").val();
			//if(v=="ok"){
				if(examDetailId==null||examDetailId==""){
					alertx("请先选择考试类型");
				}else{
					$("#searchForm").attr("action","${ctx}/examScore/exportClassStudent?examId="+examId+"&examClassId="+examClassId+"&examDetailId="+examDetailId+"&examDetailType="+examDetailType);
					$("#searchForm").submit();
				}
			//}
				
			}
		function importDOC(){
			$.jBox($("#importBox").html(), {id:"importBoxForScore",title:"导入数据", buttons:{"关闭":true}, 
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
		}
		/* function submitImport(){
			 var examId=$("#examId").val();
			 var examClassId=$("#examClassId").val();
		     var examDetailId=$("#detailId").val();
		     var file=$("div[id='importBoxForScore']").find("#uploadFile").val();
		     var examDetailType=$("#detailIdType").val();
		     if(examDetailId==null||examDetailId==""){
		    	 alertx("请选择试卷类型！");
		     }else{
		         console.log(file);
		    	 if(!!!file){
		    		 alertx("请选择上传文件");
		    	 }else{
				    $("#importForm").attr("action","${ctx}/examScore/import?examId="+examId+"&examClassId="+examClassId+"&examDetailId="+examDetailId+"&examDetailType="+examDetailType);
					$("#importForm").submit();
		    	 }
		     }
		} */
		 function judgeRadioClicked(){  
			 var examId=$("#examId").val();
			 var examClassId=$("#examClassId").val();
		     //获得 单选选按钮name集合  
		     var radios = document.getElementsByName("examDetailType");  
		     //根据 name集合长度 遍历name集合  
		     for(var i=0;i<radios.length;i++)  
		     {   
		         //判断那个单选按钮为选中状态  
		         if(radios[i].checked)  
		         {  
		             //弹出选中单选按钮的值  
		             var examDetailType=radios[i].value;  
		             window.location.href ='${ctx}/examScore/allStudent?examId='+examId+'&examClassId='+examClassId+'&examDetailType='+examDetailType;
		         }   
		     }   
		 }  
	</script>
</head>
<body>
	<sys:message content="${message}"/>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/examScore/import?examId=${exam.id}&examClassId=${school.id}&examDetailId=${detailId}&examDetailType=${examDetailType}" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn tbtn-primary" type="submit" value="  导    入   "/>
		</form>
	</div>
    <form:form id="searchForm" class="breadcrumb form-search" action="${ctx}/examScore/allStudent">
   		<input id="pageNo" name="pageNo" type="hidden" value="${examList.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${examList.pageSize}"/>
   		<input id="detailId" name="detailId" type="hidden" value="${detailId}"/>
   		<input id="examId" name="examId" type="hidden" value="${exam.id}"/>
   		<input id="detailIdType" name="examDetailType" type="hidden" value="${examDetailType}"/> 
    	<input type="hidden" name="examClassId" id="examClassId" value="${school.id}"/>
    	<input type="hidden" name="totalScore" id="totalScore" value="${totalScore}"/>
    	 作业名称：${exam.title}&nbsp;&nbsp;&nbsp;
		班级：${school.title}&nbsp;&nbsp;&nbsp;
		试卷总分：${totalScore}&nbsp;&nbsp;&nbsp;
		试卷类型：
			<c:if test="${AB eq '2'}">
				<input type="radio" maxlength="50" onclick="judgeRadioClicked();" class="required" name="examDetailType" ${examDetailType eq 'A' ? 'checked="checked"' :'' } value="A"  id="examDetailType_A" />A卷
				<input type="radio" maxlength="50" onclick="judgeRadioClicked();" class="required" name="examDetailType" ${examDetailType eq 'B' ? 'checked="checked"' :'' }  value="B"  id="examDetailType_B"  />B卷
			</c:if>
			<c:if test="${AB eq null}">
				<input type="radio" maxlength="50" onclick="judgeRadioClicked();" class="required" name="examDetailType" ${examDetailType eq 'A' ? 'checked="checked"' :'' } value="A"  id="examDetailType_A" />A卷
			</c:if>
		<c:if test="${detailId eq null}">
			 <span class="help-inline"><font color="red">请确认正确的试卷类型</font> </span>
 		</c:if>
 		<input id="btnTestPaperSubmit" class="btn tbtn-primary" onclick="Button();" type="button" value="返回"/>
		<input id="btnImport" class="btn tbtn-primary" type="button" value="成绩导入" onclick="importDOC();"/>
		<input id="btnExport" class="btn tbtn-primary" type="button" onclick="exportDOC();" value="成绩录入模板导出"/>
	</form:form>

	<form:form id="examClassDetailForm" method="post" action="${ctx}/examScore/updateStatus?examId=${exam.id}&examClassId=${school.id}">
    	<input type="hidden" id="examId" value="${exam.id}"/>
    	<input type="hidden" name="examClassId" id="examClassId" value="${school.id}"/>
		<table id="treeTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th  hidden="true" style="text-align: center;">ID</th>
					<th style="text-align: center;">姓名</th>
					<th style="text-align: center;">学号</th>
					<th style="text-align: center;">联系电话</th>
					<th style="text-align: center;">考试成绩</th>
				</tr>
			</thead>
			<tbody>
			 <c:forEach items="${examList.list}" var="list">
				<tr>
					<td  hidden="true" style="text-align: center;">${list.studentId}</td>
					<td style="text-align: center;">${list.name}</td>
					<td style="text-align: center;">${list.stdCode}</td>
					<td style="text-align: center;">${list.stdPhone}</td>
					<td style="text-align: center;">
						<%-- <input id="examSubmit_${list.studentId}" type="text"  class="input-mini number required" value="${list.score}"/> --%>
						<input id="examSubmit_${list.studentId}" class="input-mini required" type="text" value="${list.score}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
						<!-- <input type="hidden" name=txt1 onchange="if(/\D/.test(this.value)){alert('只能输入数字');this.value='';}"> -->
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<div align="right">
			<input id="submit" type="button" value="确定" class="btn tbtn-primary" onclick="submitButton();"/><!--  onclick="submitButton();" -->
		</div>
	 </form:form>
	  <div class="pagination">${examList}</div>
</body>
</html>