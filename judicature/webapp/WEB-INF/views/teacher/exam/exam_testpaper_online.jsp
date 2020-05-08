<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>试卷管理</title>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<%@include file="/WEB-INF/views/teacher/include/date.jsp" %>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#testpaperForm").submit();
	    	return false;
	    }
		 $(document).ready(function() {
			 /* 组卷考试的修改功能 */
			 $("input[id^='Example_']").click(function(){
				 var examId=$(this).attr("id").split("_")[1]; 
				window.location.href = ("${ctx}/examination/adjustExam?id="+examId);
			 });
			 /* 查询 */
			 $("input[id^='ExamQuerySubmit_']").click(function(){
				 var examId=$(this).attr("id").split("_")[1]; 
				window.location.href = ("${ctx}/testPaper/testPaperUpExam?examId="+examId);
			 });
			 /* 发布考试 */
			 $("input[id^='ExamUpdateSubmit_']").click(function(){
				 var examId=$(this).attr("id").split("_")[1]; 
				 var examClassId=$(this).attr("id").split("_")[2]; 
				 window.location.href = ("${ctx}/testPaper/publishExam?examId="+examId+"&examClassId="+examClassId);
			 });
				/*查看成绩*/
				 $("input[id^='examSubmit_']").click(function(){
					var examId=$(this).attr("id").split("_")[2];
				 	var examClassId=$(this).attr("id").split("_")[1];
				 	var type=$(this).attr("id").split("_")[3];//Online
			 		window.location.href = '${ctx}/examScore/examOnLineScore?&examId='+examId+'&examClassId='+examClassId+'&type='+type; 
				 }); 
			 /* 结束考试 */
			 /* $("input[id^='ExamSubmit_']").click(function(){
				 var examId=$(this).attr("id").split("_")[1]; 
				 var examClassId=$(this).attr("id").split("_")[2]; 
				 window.location.href = ("${ctx}/testPaper/endExam?examId="+examId+"&examClassId="+examClassId);
			 }); */
			 /* 保存为模板 */
			 $("input[id^='ExamSaveSubmit_']").click(function(){
				 var examId=$(this).attr("id").split("_")[1]; 
				window.location.href = ("${ctx}/testPaper/saveExam?examId="+examId);
				alertx("保存成功！");
			 });
			 /* 删除 */
			 $("input[id^='ExamDelSubmit_']").click(function(){
				 var examId=$(this).attr("id").split("_")[1];
				 var examClassId=$(this).attr("id").split("_")[2];
				 return confirmx("确认要删除当前试卷吗？",function(){
					 window.location.href = ("${ctx}/testPaper/deleteTestPaper?examId="+examId+"&examClassId="+examClassId);
				});
			 });
			//判卷
			 $("input[id^='ExamIsMark_']").click(function(){
				 var examId=$(this).attr("id").split("_")[1]; 
				 var examClassId=$(this).attr("id").split("_")[2]; //online
				 var status=$(this).attr("id").split("_")[3];
				 window.location.href ='${ctx}/teacherStudentAnswer/examStudentList?examId='+examId+'&examClassId='+examClassId+'&status='+status; 
			 });
			//录入成绩
			 $("input[id^='saveSubmit_']").click(function(){
			 	var examClassId=$(this).attr("id").split("_")[1];
				var examId=$(this).attr("id").split("_")[2];
		 		window.location.href = '${ctx}/examScore/allStudent?&examId='+examId+'&examClassId='+examClassId; 
			 });
		}); 
	</script>
</head>
<body>
 <ul class="nav nav-tabs">
	<li><a href="${ctx}/testPaper/testPaperOnLineListUnSubmit">未结束的考试</a></li>
	<li class="active"><a href="${ctx}/testPaper/testPaperOnLineList">已结束的考试</a></li>
</ul>
<sys:message content="${message}"/>
    <form:form id="testpaperForm" modelAttribute="exam" action="${ctx}/testPaper/testPaperOnLineList" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${examList.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${examList.pageSize}"/>
		<ul class="ul-form">
			<li><label>开始时间：</label>
				  <input id="beginTime" name="beginTime" style="width: 130px;" type="text" class="input-medium Wdate"
				value="<fmt:formatDate value='${beginTime}'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
				onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}'})" readonly></input>
			</li>
			<li><label>结束时间：</label>
				<input id="endTime" name="endTime" style="width: 130px;" type="text" class="input-medium Wdate"
				value="<fmt:formatDate value='${endTime}'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
				 onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}'})"readonly></input>
			</li>
			<li><label>班级：</label>
				<form:select path="examClass.id"  class="input-small">
					<form:option value="">全部</form:option>
				   	<form:options items="${questionlib:getCourseQuestionlibByTeacherId()}" itemLabel="examClass.title" itemValue="examClass.id"  htmlEscape="false"/>
				 </form:select>
			</li>
			<li><label>试卷名称：</label>
				<input id="title" name="title" class="input-medium" value="${examTitle}" style="width: 100px;"/>
			</li>
			<li><label>组卷方式：</label>
				 <form:select path="examMode"  class="input-medium">
					<form:option value="">全部</form:option>
				   <form:options items="${fns:getDictList('examination_examMode')}" itemLabel="label" itemValue="value"  htmlEscape="false"/>
				 </form:select>
			</li>
			<%-- <li><label>试卷类型：</label>
				<form:select path="examType"  class="input-medium">
					<form:option value="">全部</form:option>
				   	<form:options items="${fns:getDictList('examination_examtype')}" itemLabel="label" itemValue="value"  htmlEscape="false"/>
				   	<form:option  value="2">组卷考试</form:option>
				   	<form:option  value="5">在线考试</form:option>
				 </form:select>
			</li> --%>
			<li>
				&nbsp;&nbsp;<input id="btnTestPaperSubmit" class="btn tbtn-primary" type="submit" value="搜索"/>
			</li>
		</ul>
	</form:form>

	<form id="examForm" method="post">
		<table  id="treeTable" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<!-- <th style="text-align: center;">出题时间</th> -->
					<th style="text-align: center;">班级</th>
					<th style="text-align: center;" hidden="true">试卷ID</th>
					<th style="text-align: center;">试卷名称</th>
					<th style="text-align: center; width: 120px; ">开始时间</th>
					<th style="text-align: center; width: 120px; ">结束时间</th>
					<th style="text-align: center; width: 120px; ">公布答案时间</th>
					<!-- <th style="text-align: center;">所属课程</th> -->
					<!-- <th style="text-align: center;">试卷性质</th> -->
					<th style="text-align: center;">组卷方式</th>
					<th style="text-align: center;">未答题人数</th>
					<th style="text-align: center;">状态</th>
					<th style="text-align: center;">操作</th>
				</tr>
				
			</thead>
			<tbody>
			  <c:forEach items="${examList.list}" var="list">
				<tr>
					<%-- <td style="text-align: center;">
						<fmt:formatDate value="${list.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td> --%>
					<td style="text-align: center;">${list.examClass.title}</td>
					<td style="text-align: center;" hidden="true">${list.id}</td>
					<td style="text-align: center;">
					<c:choose>
						<c:when test="${list.examType eq '5'}">
							<!-- 只有状态为未发布的才有发布功能   0未发布 1正在进行 2已完成 -->
								<c:if test="${list.status eq '0'}">
									${list.title}
								</c:if>
								<!-- 正在进行 -->
								<c:if test="${list.status eq '1'}">
	 								${list.title}
								</c:if>
								<!-- 已完成 -->
								<c:if test="${list.status eq '2'}">
									<c:if test="${list.isTeacher eq '1'}">
										 <a href="${ctx }/statistics/staticKnowledgeByexamIdAndClassId?examId=${list.id }&classId=${list.examClass.id}&backUrl=${currentUrl}">
						     				${list.title}
						 				</a>
									</c:if>
									<c:if test="${list.isTeacher eq null}">
										 ${list.title}
									</c:if>
								</c:if>
						</c:when>
						<c:otherwise>
							${list.title}
						</c:otherwise>
					</c:choose>
					
					  
					</td>
					<td style="text-align: center;">
						<fmt:formatDate value="${list.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td style="text-align: center;">
						<fmt:formatDate value="${list.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td style="text-align: center;">
						<fmt:formatDate value="${list.publishAnswerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<%-- <td style="text-align: center;">${list.examCourse.title}</td> --%>
					<%-- <td id="examType" style="text-align: center;">
						<c:if test="${list.examType eq '1'}">
							随堂测试
						</c:if>
						<c:if test="${list.examType eq '2'}">
							组卷考试
						</c:if>
						<c:if test="${list.examType eq '3'}">
							作业
						</c:if>
						<c:if test="${list.examType eq '4'}">
							例题 
						</c:if>
						<c:if test="${list.examType eq '5'}">
							在线考试
						</c:if>
						${fns:getDictLabels(list.examType,'examination_examtype','') }
					</td> --%>
					<td style="text-align: center;">
						 ${fns:getDictLabels(list.examMode,'examination_examMode','') }
					</td>
					<td style="text-align: center;">
						<c:if test="${list.examType eq '5'}">
							<c:if test="${list.status eq '2'}">
								${list.unSubmitPerson}
							</c:if>
						</c:if>
					</td>
					<td style="text-align: center;">
						<c:if test="${list.examType eq '5'}">
							${fns:getDictLabels(list.status,'examination_status','') }
						</c:if>
						<c:if test="${list.examType eq '2'}">
							${fns:getDictLabels(list.status,'examination_status','') }
						</c:if>
					</td>
					<td style="text-align: center;">
						<!-- 手动组卷才有存为模板功能1手动2自动 -->
						<c:if test="${list.examMode eq '1'}">
						<!-- 未存为模板的试卷才能存为模板 -->
							<c:if test="${list.istemplate eq '0'}">
								<input id="ExamSaveSubmit_${list.id}_${list.examClass.id}"  style="background: url(${ctxStatic}/icon/mb.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="存为模板" />
							</c:if>
							<!-- 在线考试才有发布考试的功能 -->
							<c:if test="${list.examType eq '5'}">
								<!-- 只有状态为未发布的才有发布功能   0未发布 1正在进行 2已完成 -->
								<c:if test="${list.status eq '0'}">
									<input id="Example_${list.id}_${list.examClass.id}"  style="background: url(${ctxStatic}/icon/xg.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="修改" />
<%-- 									<input id="ExamUpdateSubmit_${list.id}_${list.examClass.id}" class="btn tbtn-primary" type="button" value="发布" /> --%>		
									<input id="ExamDelSubmit_${list.id}_${list.examClass.id}"  style="background: url(${ctxStatic}/icon/del.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="删除" />
								</c:if>
								<!-- 已完成 -->
								<c:if test="${list.status eq '2'}">
									<c:choose>
										<c:when test="${list.isTeacher eq '1'}">
											<a href="${ctx }/statistics/staticKnowledgeByexamIdAndClassId?examId=${list.id }&classId=${list.examClass.id}&backUrl=${currentUrl}"  style="background: url(${ctxStatic}/icon/tji.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  title="统计"></a>
											<input id="examSubmit_${list.examClass.id}_${list.id}_Online"  style="background: url(${ctxStatic}/icon/check.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="查看成绩"/>
										</c:when>
										<c:otherwise>
											<input id="ExamIsMark_${list.id}_${list.examClass.id}_online"  style="background: url(${ctxStatic}/icon/jq.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="判卷" />
										</c:otherwise>
									</c:choose>
									<input id="ExamQuerySubmit_${list.id}_${list.examClass.id}_online"  style="background: url(${ctxStatic}/icon/check_1.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="查看试卷" />
								</c:if>
							</c:if>
							<!-- 组卷考试 -->
							<%-- <c:if test="${list.examType eq '2'}">
								<c:if test="${list.status eq '0'}">
									<input id="Example_${list.id}_${list.examClass.id}" class="btn btn-primary" type="button" value="修改" />
									<input id="ExamDelSubmit_${list.id}_${list.examClass.id}" class="btn btn-primary" type="button" value="删除" />
								</c:if>
								<c:if test="${list.status eq '2'}">
									<c:choose>
										<c:when test="${list.isTeacher eq '1'}">
											<a href="${ctx }/statistics/staticKnowledgeByexamIdAndClassId?examId=${list.id }&classId=${list.examClass.id}&backUrl=${currentUrl}" class="btn btn-primary">统计</a>
											<input id="examSubmit_${list.examClass.id}_${list.id}_no" class="btn btn-primary" type="button" value="查看成绩"/>
										</c:when>
										<c:otherwise>
											<input id="saveSubmit_${list.examClass.id}_${list.id}_no" class="btn btn-primary" type="button" value="录入成绩" />
										</c:otherwise>
									</c:choose>
									<input id="ExamQuerySubmit_${list.id}_${list.examClass.id}" class="btn btn-primary" type="button" value="查看试卷" />
								</c:if>
							</c:if> --%>
						</c:if>
						<!-- 自动 -->
						<c:if test="${list.examMode eq '0'}">
							<!-- 在线考试才有发布考试的功能 -->
							<c:if test="${list.examType eq '5'}">
								<!-- 只有状态为未发布的才有发布功能   0未发布 1正在进行 2已完成 -->
								<c:if test="${list.status eq '0'}">
									<input id="Example_${list.id}_${list.examClass.id}" style="background: url(${ctxStatic}/icon/xg.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" type="button" title="修改" />
<%-- 									<input id="ExamUpdateSubmit_${list.id}_${list.examClass.id}" class="btn btn-primary" type="button" value="发布" />
 --%>									<input id="ExamDelSubmit_${list.id}_${list.examClass.id}" style="background: url(${ctxStatic}/icon/del.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" type="button" title="删除" />
								</c:if>
								<!-- 已完成 -->
								<c:if test="${list.status eq '2'}">
									<c:choose>
										<c:when test="${list.isTeacher eq '1'}">
											<a href="${ctx }/statistics/staticKnowledgeByexamIdAndClassId?examId=${list.id }&classId=${list.examClass.id}&backUrl=${currentUrl}"  style="background: url(${ctxStatic}/icon/tji.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" title="统计"></a>
											<input id="examSubmit_${list.examClass.id}_${list.id}_Online" style="background: url(${ctxStatic}/icon/check.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="查看成绩"/>
										</c:when>
										<c:otherwise>
											<input id="ExamIsMark_${list.id}_${list.examClass.id}_online"  style="background: url(${ctxStatic}/icon/jq.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="判卷" />
										</c:otherwise>
									</c:choose>								
									<input id="ExamQuerySubmit_${list.id}_${list.examClass.id}_online" style="background: url(${ctxStatic}/icon/check_1.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" type="button" title="查看试卷" />
								</c:if>
							</c:if>
							<!-- 组卷考试 -->
							<%-- <c:if test="${list.examType eq '2'}">
								<c:if test="${list.status eq '0'}">
									<input id="Example_${list.id}_${list.examClass.id}" class="btn btn-primary" type="button" value="修改"" />
									<input id="ExamDelSubmit_${list.id}_${list.examClass.id}" class="btn btn-primary" type="button" value="删除" />
								</c:if>
								<c:if test="${list.status eq '2'}">
									<c:choose>
										<c:when test="${list.isTeacher eq '1'}">
											<a href="${ctx }/statistics/staticKnowledgeByexamIdAndClassId?examId=${list.id }&classId=${list.examClass.id}&backUrl=${currentUrl}" class="btn btn-primary">统计</a>
											<input id="examSubmit_${list.examClass.id}_${list.id}_no" class="btn btn-primary" type="button" value="查看成绩"/>
										</c:when>
										<c:otherwise>
											<input id="saveSubmit_${list.examClass.id}_${list.id}_no" class="btn btn-primary" type="button" value="录入成绩" />
										</c:otherwise>
									</c:choose>
									<input id="ExamQuerySubmit_${list.id}_${list.examClass.id}" class="btn btn-primary" type="button" value="查看试卷" />
								</c:if>
							</c:if> --%>
						</c:if>
					</td>
				</tr>
			</c:forEach> 
			</tbody>
		</table>
	 </form>
	 <div class="pagination">${examList}</div>
	 
</body>
</html>