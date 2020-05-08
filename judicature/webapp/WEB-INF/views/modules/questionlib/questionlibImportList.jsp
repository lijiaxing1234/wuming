<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>上传文档管理</title>
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
		
		
		function updateImport(){
			var checkedNum=$("input[name='ids']:checked").length;
			if(checkedNum==0){
				alertx("请至少选择一项");
				return;
			}else{
				$("#formList").attr("action", "${ctx}/questionlib/questionlibImport/delete?delFlag1=2");
				return confirmx('确认要进行此操作？',function(){$('#formList').submit();});
			}
		}
		
		function deleteLibImport(){
			var checkedNum=$("input[name='ids']:checked").length;
			if(checkedNum==0){
				alertx("请至少选择一项");
				return;
			}else{
				//$("#delFlag").val("1");
				$("#formList").attr("action", "${ctx}/questionlib/questionlibImport/delete?delFlag1=1");
				return confirmx('确认要进行此操作吗？',function(){ $('#formList').submit();});
			}
		}
		
		//下载文档
		function loadWordDoc(id){
			if(confirm('确认要下载该文档吗？')){
				 /* $.getJSON("${ctx}/questionlib/questionlibImport/loadWordDoc?id="+id,function(data){
					 
				 }); */
				 alert("id==="+id);
				 $.ajax({
			         url:"${ctx}/questionlib/questionlibImport/loadWordDoc?id="+id,  
			         data:{ r:Math.random() },
			         type:"post",  
			         dataType:"json",
			         success:function(data) { 
			        	 var message = data.message;
			        	  alert("data.message========"+data.message);
			        	  if(message==2){
				        	  var file = data.file;
				        	  alert("file===="+file);
				        	  window.location.href=file;
			        	  }else if(message==1){
			        		  alert("未发现该文档，请重新选择！");
			        	  }else{
			        		  alert("下载失败！");
			        	  }
			         },  
			         error:function(data) {  
			              alert(data.message); 
			         }
			    }); 
			}
		}
		
	
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/questionlib/questionlibImport/list">上传文档列表</a></li>
		<%-- <shiro:hasPermission name="questionlib:questionlibImport:edit"><li><a href="${ctx}/questionlib/questionlibImport/form">试题导入题库添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="questionlibImport" action="${ctx}/questionlib/questionlibImport/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>专业：</label>
				<sys:treeselect id="specialty" name="specialty.id" value="${questionlibImport.specialty.id}" labelName="specialty.title" labelValue="${questionlibImport.specialty.title}"
					title="专业" url="/questionlib/specialty/treeData" cssClass="input-small"/>
			</li>
			<li><label>文档名称：</label>
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-small"/>
			</li>
			<li><label>出题人：</label>
				<form:input path="writer" htmlEscape="false" maxlength="32" class="input-small"/>
			</li>
			<li><label>上传部门：</label>
				<sys:treeselect id="school" name="school.id" value="${questionlibImport.school.id}" labelName="school.name" labelValue="${questionlibImport.school.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="false" notAllowSelectParent="true"/>
			</li>
			<li><label>上传人：</label>
				<sys:treeselect id="user" name="user.id" value="${questionlibImport.user.id}" labelName="user.name" labelValue="${questionlibImport.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="false" notAllowSelectParent="true"/>
			</li>
			<li><label>上传人电话：</label>
				<input name="phone" type="text" value="${questionlibImport.phone}" class="input-small"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="questionlib:questionlibImport:show">
				<%-- <li class="btns"><input id="btnCheck" class="btn btn-primary" type="button" value="${delFlag1!='2'?'批量审核':'批量重审' }" onclick="return confirmx('确认要进行此操作吗？',function(){ $('#formList').submit();})" /></li> --%>
				<shiro:hasPermission name="questionlib:questionlibImport:auditor">
					<li class="btns"><input id="btnCheck" class="btn btn-primary" type="button" value="${delFlag1!='2'?'批量审核':'批量重审' }" onclick="updateImport()" /></li>
				</shiro:hasPermission>
			</shiro:hasPermission>	
				<c:if test="${delFlag1=='1' }">
				
				</c:if>
				<c:if test="${delFlag1!='1' }">
					<shiro:hasPermission name="questionlib:questionlibImport:delete">
						<li class="btns"><input id="btnDelete" class="btn btn-primary" type="button" value="批量删除" onclick="deleteLibImport()" /></li>
					</shiro:hasPermission>
				</c:if>
			
			<shiro:hasPermission name="questionlib:questionlibImport:show">
				<li><label>状态：</label><form:radiobuttons onclick="$('#searchForm').submit();" path="delFlag" items="${fns:getDictList('question_del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" /></li>
			</shiro:hasPermission>	
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<form action="${ctx}/questionlib/questionlibImport/delete" method="post" id="formList">
		 
		<c:set var="currentUser"  value="${fns:getUser() }" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align:center;"><input type="checkbox" id="selectAll" value="全选"/></th>
				<th style="width:20px;">序号</th>
				<th>题库</th>
				<th>专业</th>
				<th>文档名称</th>
				<th>出题人</th>
				<th>上传人</th>
				<th>上传部门</th>
				<th>上传者电话</th>
				<th>上传时间</th>
				<!-- <th>适用层次</th> -->
				<c:if test="${currentUser.userType ne '2'}">
				   <th>入平台状态</th>
				</c:if>
				<shiro:hasPermission name="questionlib:questionlibImport:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="questionlibImport" varStatus="status">
			<tr>
				<td style="text-align:center;">
					<input type="checkbox" name="ids" value="${questionlibImport.id}"/>
				</td>
				<td>${status.index+1+(page.pageNo-1)*page.pageSize}</td>
				<td><a href="${ctx}/questionlib/questionlibImport/form?id=${questionlibImport.id}">
					${questionlib:getCourseQuestionlibById(questionlibImport.questionlibId).title}
				</a></td>
				<td>
					${questionlib:getSpecialtyByID(questionlibImport.specialtyId).title}
				</td>
				<td>
					${questionlibImport.title}
				</td>
				<td>
					${questionlibImport.writer}
				</td>
				<td>
					${questionlibImport.user.name}
				</td>
				<td>
					${questionlib:getOfficeById(questionlibImport.office.id).name}
				</td>
				<td>
					${questionlibImport.phone}
				</td>
				<td>
					<%-- ${questionlib:getOfficeById(questionlibImport.school.id).name} --%>
						
						<fmt:formatDate value="${questionlibImport.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%-- <td>
					${fns:getDictLabel(questionlibImport.coursePhase, 'coursephase', '')}
				</td> --%>
				<c:if test="${currentUser.userType ne '2'}">
					<th>
					   
					    <c:choose>
					        <c:when test="${empty questionlibImport.quesImportApplyId }">
					                       未申请
					        </c:when>
					        <c:otherwise>
					         ${fns:getDictLabel(questionlib:getQuesImportApply(questionlibImport.id).status,"questionlib_import_apply_status","")}
					        </c:otherwise>
					    </c:choose>
					 
					</th>
			   </c:if>
				<td>
					<shiro:hasPermission name="questionlib:questionlibImport:edit">
						<c:if test="${questionlibImport.delFlag eq '2'}">
							<shiro:hasPermission name="questionlib:questionlibImport:auditor">
		    					<a href="${ctx}/questionlib/questionlibImport/delete?ids=${questionlibImport.id}&delFlag1=0" 
								onclick="return confirmx('确认要审核通过吗？', this.href)">通过审核</a>
							</shiro:hasPermission>
						</c:if>
						<c:if test="${questionlibImport.delFlag eq '1'}">
							<shiro:hasPermission name="questionlib:questionlibImport:auditor">
								<a href="${ctx}/questionlib/questionlibImport/delete?ids=${questionlibImport.id}&delFlag1=2" 
								onclick="return confirmx('确认要恢复到未审核吗？', this.href)">恢复到未审核</a>
							</shiro:hasPermission>
						</c:if>
						<c:if test="${questionlibImport.delFlag eq '0'}">
						<shiro:hasPermission name="questionlib:questionlibImport:show">
							<shiro:hasPermission name="questionlib:questionlibImport:auditor">
								<a href="${ctx}/questionlib/questionlibImport/delete?ids=${questionlibImport.id}&delFlag1=2" 
								onclick="return confirmx('确认要撤销审核吗？', this.href)">撤销审核</a>
							</shiro:hasPermission>
						</shiro:hasPermission>
						</c:if>
	    				<a href="${ctx}/questionlib/questionlibImport/form?id=${questionlibImport.id}">查看</a>
	    				<c:if test="${delFlag=='1'}">
				
						</c:if>
						<c:if test="${delFlag!='1'}">
							<shiro:hasPermission name="questionlib:questionlibImport:delete">
								<a href="${ctx}/questionlib/questionlibImport/delete?ids=${questionlibImport.id}&delFlag=1" onclick=" return confirmx2('确认要删除该文档吗？', this.href,null,"正在删除，请稍等!")">删除</a>
							</shiro:hasPermission>
						</c:if>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="questionlib:questionlibImport:download">
						<%-- <a href="${questionlibImport.filePath}" >下载文档</a> --%>
							<a href="${pageContext.request.contextPath}/commonDownLoadFiles/${questionlibImport.filePath}?fileName=${fns:urlEncode(questionlibImport.title)}" target="_blank">下载</a>
					</shiro:hasPermission>
					
					<c:if test="${currentUser.userType ne '2'}">
					<shiro:hasPermission name="questionlib:questionlibImport:intoPlatform">
					   <c:if test="${empty questionlibImport.quesImportApplyId }">
					       <a href="${ctx}/questionlib/questionlibImport/intoPlatform?quesImportId=${questionlibImport.id}" >申请入平台</a>
					   </c:if>
					</shiro:hasPermission>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</form>
	<div class="pagination">${page}</div>
</body>
</html>