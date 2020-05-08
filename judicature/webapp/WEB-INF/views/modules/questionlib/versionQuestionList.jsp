<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
    <head>
       <title>试题管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		#importTable tr td{
			height: 30px;
			vertical-align: top;
		}
		table tr td input{
			vertical-align: middle;
		}
	</style>
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
		
		//导入试题
		function importQuestions(){
			window.location.href="${ctx}/questionlib/versionQuestion/importDoc?extId=${extId}&courseQuestionlibId=${courseQuestionlibId}&courseVersionId=${courseVersionId}";
		}
		
		//下载导入模板
		function loadTemplate(){
 
			window.location.href="${ctxStatic}/template/template.rar";	//试题导入编写模板与说明
 
		}
		
		//打开导出试题页面
		function openEmportQuestions(){
			$("#emportForm").show();
		}
		//关闭导出试题页面
		function closeEmportQuestions(){
			$("#emportForm").hide();
		}
		
		//导出试题
		function emportQuestions(){
			var courseQuestionlibId = $("#courseId").val();
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
		        	  alert(data.message);
		        	  var docName = data.docName;
		        	  //alert("docName"+docName);
		        	  window.location.href="${ctxStatic}/template/loadFile/"+docName;
		        	  $("#emportForm").hide();
		         },  
		         error:function(data) {  
		              alert("导出失败!"); 
		         }
		    }); 
		}
		
		function updateQuestion(){
			 var checkedNum = $(" input[name='ids']:checked").length;
			  if(checkedNum==0){
				 alertx("请至少选择一项");
				 return;
			   }else{
				  // $("#delFlag").val("0");
				   $("#formList").attr("action", "${ctx}/questionlib/versionQuestion/delete?extId=${extId}&courseQuestionlibId=${courseQuestionlibId}&delFlag1=0");
					return confirmx('确认要进行此操作吗？',function(){ $('#formList').submit();});
			   }
		}
		function deleteLibQuestions(){
			 var checkedNum = $(" input[name='ids']:checked").length;
			  if(checkedNum==0){
				 alertx("请至少选择一项");
				 return;
			   }else{
				/* 	$("#delFlag").val("1"); */
					/* var ids = $("input[name='ids']").attr("checked", true).val();
					alert(ids); */
					 
					$("#formList").attr("action", "${ctx}/questionlib/versionQuestion/delete?extId=${extId}&courseQuestionlibId=${courseQuestionlibId}&delFlag1=1");
					return confirmx('确认要进行此操作吗？',function(){ $('#formList').submit();});
			   }
		}
		
		(function(){
		    $(function(){
		    	$("#importButton").on("click",function(){
		    		 
		    	 	//alert(courseId);
		    		openJbox("iframe:${ctx}/questionlib/versionQuestion/importQuestionWordForm?courseId=${courseId}","试题导入");
		    		  
		    		return false;
		    	});
		    	
		    	$("#emportButton").on("click",function(){
		    		var delFlag = $("input[type='radio']").attr("checked","checked").val();
		    		//alert(delFlag);
		    		var courseQuestionlibId = $("#questionlibId").val();
		    		openJbox("iframe:${ctx}/questionlib/versionQuestion/emportQuestionWordForm?courseQuestionlibId="+courseQuestionlibId+"&delFlag="+delFlag,"试题导出");
		    		return false;
		    	});
		    	
		    	function openJbox(src,title){
		    		 top.$.jBox(src,{
							title:title,
							width: 750,
						    height: 350,
							buttons:{}, 
						    bottomText:"",
						    persistent:true,
						    loaded:function(h){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							},
						    closed:function(){
								$("#searchForm").submit();
								//window.location.href="${ctx}/questionlib/courseKnowledge/list";
							}
				     }); 
		    	}
		        	
		    });
		})();
	</script>
    </head>
    <body>
	    <ul class="nav nav-tabs">
			<li class="active"><a href="${ctx}/questionlib/versionQuestion/list?extId=${extId}&courseId=${courseId}">试题列表</a></li>
			<shiro:hasPermission name="questionlib:versionQuestion:edit"><li><a href="${ctx}/questionlib/versionQuestion/form?extId=${extId}&courseId=${courseId}">试题添加</a></li></shiro:hasPermission>
			<shiro:hasPermission name="questionlib:versionQuestion:download">
			    <li style="margin-left: 20px;" ><input id="importButton" class="btn btn-primary" type="button" value="导入试题"/></li>
			<!-- 	 <li style="margin-left: 20px;"><input id="emportButton" class="btn btn-primary" type="button" value="导出试题"/></li> -->
	<!-- 			 <li class="btns"><input class="btn btn-primary" type="button" value="导入试题" onclick="importQuestions();"/></li>
				 <li style="margin-left: 20px;" ><input class="btn btn-primary" type="button" value="导出试题" onclick="openEmportQuestions();"/></li> -->
				 <li style="margin-left: 20px;" ><input class="btn btn-primary" type="button" value="下载试题导入文档模板" onclick="loadTemplate();"/></li>
			</shiro:hasPermission>
		</ul>
		<form:form id="searchForm" modelAttribute="versionQuestion" action="${ctx}/questionlib/versionQuestion/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="questionlibId" type="hidden" name="courseQuestionlibId" value="${courseQuestionlibId}"/>
		<input id="extId" type="hidden" name="extId" value="${extId}"/>
		<ul class="ul-form">
			<li><label>知识点：</label>
				<form:input path="examCode" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>题型：</label>
				<form:select path="quesType" class="input-medium">
					<option></option>
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('question_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		<%-- 	<li><label>难度：</label>
				<form:select path="quesLevel" class="input-medium">
					<option></option>
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('question_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li> --%>
			
 			<%--  <li><label>入库日期：</label>
				<input name="createDate" type="text"   maxlength="15" class="input-medium Wdate required"  
					value="<fmt:formatDate value="${versionQuestion.createDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li> --%>
			
			<%-- <li class="btns"><shiro:hasPermission name="questionlib:versionQuestion:edit"><li><a href="${ctx}/questionlib/versionQuestion/importDoc?extId=${extId}&courseQuestionlibId=${courseQuestionlibId}&courseVersionId=${courseVersionId}">导入试题页面</a></li></shiro:hasPermission>
		</li> --%>
		<li><label>状态： </label>
			<form:radiobuttons onclick="$('#pageNo').val(0); $('#searchForm').submit();" path="delFlag" items="${fns:getDictList('question_del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" /></li>
			<%-- <li class="btns"><input id="btnCheck" class="btn btn-primary required" type="button" value="${qdelFlag!='2'?'批量审核':'批量重审' }" onclick="return confirmx('确认要进行此操作吗？',function(){ $('#formList').submit();})" /></li> --%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			
			<shiro:hasPermission name="questionlib:versionQuestion:auditor">
				<li class="btns" style="display: ${delFlag=='2'?'':'none'}"><input id="btnCheck" class="btn btn-primary required" type="button" value="批量审核" onclick="updateQuestion()" /></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="questionlib:versionQuestion:delete">
				<li class="btns" style="display: ${delFlag=='1'?'none':''}"><input id="btnDelete" class="btn btn-primary" type="button" value="批量删除" onclick="deleteLibQuestions()" /></li>
			</shiro:hasPermission>
			
			
			<li class="clearfix"></li>
		</ul>
	</form:form>
    <sys:message content="${message}"/>
    <form id="formList" action="${ctx}/questionlib/versionQuestion/delete?extId=${extId}&courseQuestionlibId=${courseQuestionlibId}" method="post">
    	<input id="delFlag" type="hidden" name="delFlag" value="${delFlag}" />
    	<%-- <input id="delFlag1" type="hidden" name="delFlag1" value="${delFlag}" /> --%>
	<table id="contentTable" class="table table-striped table-bordered table-condensed"> 
		<thead>
			<tr>
				<th style="text-align:center; width:10px;"><input type="checkbox" id="selectAll"  value="全选"/></th>
				<th style="width:20px;">序号</th>
				<th>序列</th>
				<th>知识点</th>
				<th>题型</th>
				<!-- <th>难度</th> -->
				<th>分值</th>
				<th>试题描述</th>
				<!-- <th>出题人</th> -->
				<th>修改时间</th>
				<!-- <th>专业</th>
				<th>课程</th>
				<th>版本</th>
				<th>题库</th> -->
				<shiro:hasPermission name="questionlib:versionQuestion:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="versionQuestion" varStatus="status">
			<tr>
				<td style="text-align:center;">
					<input type="checkbox" name="ids" value="${versionQuestion.id}"/>
				</td>
				<td>${status.index+1+(page.pageNo-1)*page.pageSize }</td>
				<td>
					${versionQuestion.sort}
				</td>
				<td>
					${versionQuestion.examCode}
				</td>
				<td>
					${fns:getDictLabel(versionQuestion.quesType, 'question_type', '')}
				</td>
				<%-- <td>
					${fns:getDictLabel(versionQuestion.quesLevel, 'question_level', '')}
				</td> --%>
				<td>
					${versionQuestion.quesPoint}
				</td>
				<td>
				   <a href="${ctx}/questionlib/versionQuestion/form?id=${versionQuestion.id}&extId=${extId}&courseQuestionlibId=${courseQuestionlibId}">
					${versionQuestion.title}
					</a>
				</td>
				<%-- <td>
					${versionQuestion.writer}
				</td> --%>
				<td>
					<fmt:formatDate value="${versionQuestion.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				
				<%-- <td>
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
				</td> --%>
				<td>
					<c:if test="${versionQuestion.delFlag eq '2'}">
						<shiro:hasPermission name="questionlib:versionQuestion:auditor">
	    					<a href="${ctx}/questionlib/versionQuestion/delete?ids=${versionQuestion.id}&delFlag1=0&courseQuestionlibId=${courseQuestionlibId}&extId=${extId}"
							onclick="return confirmx('确认要审核通过吗？', this.href)">通过审核</a>
						</shiro:hasPermission>
					</c:if>
				<%-- 	<c:if test="${versionQuestion.delFlag eq '1'}">
						<a href="${ctx}/questionlib/versionQuestion/delete?ids=${versionQuestion.id}&delFlag=6&courseQuestionlibId=${courseQuestionlibId}&extId=${extId}" 
						onclick="return confirmx('确认要撤销审核吗？', this.href)">撤销审核</a>
					</c:if> --%>
					<c:if test="${versionQuestion.delFlag eq '0'}">
					<shiro:hasPermission name="questionlib:versionQuestion:auditor">
						<a href="${ctx}/questionlib/versionQuestion/delete?ids=${versionQuestion.id}&delFlag1=2&courseQuestionlibId=${courseQuestionlibId}&extId=${extId}" 
						onclick="return confirmx('确认撤销审吗？', this.href)">撤销重审</a>
					</shiro:hasPermission>
					</c:if>
    				<a href="${ctx}/questionlib/versionQuestion/form?id=${versionQuestion.id}&operation=view&extId=${extId}&courseQuestionlibId=${courseQuestionlibId}">查看</a>
    				<shiro:hasPermission name="questionlib:versionQuestion:edit"><a href="${ctx}/questionlib/versionQuestion/form?id=${versionQuestion.id}&extId=${extId}&courseQuestionlibId=${courseQuestionlibId}">修改</a>
						<shiro:hasPermission name="questionlib:versionQuestion:delete">
							<a id="deleteQuestion" href="${ctx}/questionlib/versionQuestion/delete?id=${versionQuestion.id}&extId=${extId}&courseQuestionlibId=${courseQuestionlibId}&delFlag1=1" onclick="return confirmx('确认要删除该试题吗？', this.href)">删除</a>
						</shiro:hasPermission>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</form>
	<div class="pagination">${page}</div>
	
	<!-- 试题导出界面 -->
	<div id="emportForm" style="width: 100%;height: 100%;position: absolute; top: 0;display: none;">
		<div style="width: 850px;min-height: 450px;position: absolute;top: 150px;left: 300px;padding: 15px 4px 4px;border: 1px solid #ccc;text-align: center;background: #fff;">
		<form id="saveEmportDocMessage" action="" method="post" >
			<table style="margin: 0 auto;" id="importTable">
				<tr>
					<th colspan="2" class="control-label"><h3>试题导出</h3></th>
				</tr>
				<tr>
					<td colspan="2" style="height: 10px;"><!-- examCode	
					knowledgeCode	quesType	quesLevel	index	questionCode	
					quesPoint	title	choice0	choice1	choice2	Choice3	answer0	
					description	writer	checker --></td>
				</tr>
				<tr>
					<th class="control-label">知识点：</th>
					<td style="height: 40px;">
						<sys:treeselect id="courseKnowledge" name="courseKnowledge.id" value="${versionQuestion.courseKnowledge.id}" labelName="courseKnowledge.title" labelValue="${versionQuestion.examCode}"
						title="知识点" url="/questionlib/versionQuestion/treeData?courseQuestionlibId=${courseQuestionlibId}" cssClass="required" checked="false" notAllowSelectParent="false" notAllowSelectRoot="false"/>
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
				</td>
				</tr>
				<tr>
					<td></td>
					<td style="height: 40px;align:center;">
					<input id="saveImportMessage" class="btn btn-primary" type="button" value="开始导出" onclick="emportQuestions();"/>
					<input id="closeImportQuestion" class="btn btn-primary" type="button" value="关闭" onclick="closeEmportQuestions();"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	</div>
    </body>
</html>
