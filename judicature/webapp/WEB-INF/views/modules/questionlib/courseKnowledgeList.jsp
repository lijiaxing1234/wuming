<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>知识点管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			refresh();
			//alert($("#extId").val());
			//alert($("#courseVesionId").val());
		});
		function refresh(){
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].id);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].parentId+',') == -1){
					if ((','+rootIds.join(',')+',').indexOf(','+data[i].parentId+',') == -1){
						rootIds.push(data[i].parentId);
					}
				}
			}
			for (var i=0; i<rootIds.length; i++){
				addRow("#treeTableList", tpl, data, rootIds[i], true);
			}
			$("#treeTable").treeTable({expandLevel : 5});
		}
		
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
						blank123:0}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
		
		//打开知识点导入窗口
		function showImportPage(){
			$("#importForm").show();
		}
		//关闭知识点导入窗口
		function closeImportPage(){
			$("#importForm").hide();
		}
		
		function chengeCourse() {
			$("#courseId").empty();
			$("#courseSelect").empty();
			//alert($("#specialtyId").val());
		     //调用Ajax获取
			  $.getJSON("${ctx}/questionlib/common/getCourseJSONBySpecialtyId?specialtyId="+$("#specialtyId").val(),{ r:Math.random() },function(data){
					for (var i=0; i<data.length; i++){
						$("#courseSelect").append("<option value='"+data[i].id+"'>"+data[i].title+"</option>");
					}
			  });
		}
	  function loadVersion() {
	     //调用Ajax获取版本信息
	      $("#vesionSelect").empty();
		  $.getJSON("${ctx}/questionlib/courseKnowledge/getCourseQuestionByCouresId?id="+$("#courseSelect").val(),{ r:Math.random() },function(data){
				for (var i=0; i<data.length; i++){
					 $("#vesionSelect").append("<option value=\""+data[i].id+"\">"+data[i].value+"</option>"); 
				}
		  });
	} 
	  
	  
	//下载导入模板
		function loadTemplate(){
			window.location.href="${ctxStatic}/template/import_Knowledge_Temp.xlsx";	//知识点导入模板示例
		}
		
		//导入知识点界面
		(function(){
		    $(function(){
		    	$("#btnImport").on("click",function(){
		    		openJbox("iframe:${ctx}/questionlib/courseKnowledge/courseKnowledgeImportForm","知识点导入");
		    		return false;
		    	});
		    	
		    	function openJbox(src,title){
		    		 top.$.jBox(src,{
							title:title,
							width: 550,
						    height: 350,
							buttons:{}, 
						    bottomText:"",
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
		
		
		function add()
		{
			 
			 var courseId =$("#courseId").val();
			 // alert(courseId);
			 window.location.href ="${ctx}/questionlib/courseKnowledge/form?courseId="+courseId;
		}
		function find()
		{
			$("#searchForm").submit();
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/questionlib/courseKnowledge/list?extId=${extId}&courseId=${courseId}">知识点列表</a></li>
		<shiro:hasPermission name="questionlib:courseKnowledge:view"><li><a   href="javascript:add();">知识点添加</a></li></shiro:hasPermission>
		 
		
		<%-- <shiro:hasPermission name="questionlib:courseKnowledge:download">
	      	<li ><input id="btnImport" class="btn btn-primary" type="button" value="导入知识点"/></li>
 
			<li style="margin-left: 20px;"><input class="btn btn-primary" type="button" value="下载知识点导入文档模板" onclick="loadTemplate();"/></li>
		</shiro:hasPermission> --%>
		
	</ul>
	<form:form id="searchForm" modelAttribute="courseKnowledge" action="${ctx}/questionlib/courseKnowledge/list" method="post" class="breadcrumb form-search">
		<input type="hidden" name="operation" value="search"/>
		<input id="extId" type="hidden" name="extId" value="${extId==null?'1':extId}"/>
		<input id="courseVesionId" type="hidden" name="courseVesionId" value="${courseVesionId}"/>
		<ul class="ul-form">
		
				<li>
				  <label>课程：</label>
				    <select class="courseSelect input-small" name="courseId" id="courseId" onchange="find();" style="width:200px;">
				     <!--  <option value="">请选择</option> -->
				      <c:forEach items="${ questionlib:getCourse() }" var="course">
							<option value="${course.id}" ${courseKnowledge.courseId==course.id?"selected":""}  >${course.title} </option>
					  </c:forEach>
					  
				   	</select>
				</li>
			<%-- <li><label>知识点：</label>
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li> --%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<!-- <li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入知识点" onclick="showImportPage();"/></li> -->
			 
		</ul>
	</form:form>
	<%-- <form action="${ctx}/questionlib/courseKnowledge/importExcel?extId=${extId}&courseVesionId=${courseVesionId}" method="post" enctype="multipart/form-data">
           <input type="file" name="excelFile"/>
           <input type="submit" value="导入excel文档" class="btn btn-primary control-label">
	</form> --%>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    
				<th>知识点</th>
			<!-- 	<th>建议级别</th> -->
			
			<th>知识点编号</th>
				<th>更新时间</th>
				<shiro:hasPermission name="questionlib:courseKnowledge:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<div style="height:100px; ">&nbsp;</div> 
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			 
			<td><a href="${ctx}/questionlib/courseKnowledge/form?id={{row.id}}&extId=${extId}&courseVesionId=${courseVesionId}">
				{{row.title}}
			</a></td>
 
			<td>
				{{row.knowledgeCode}}
			</td>

			<td>
				{{row.updateDate}}
			</td>
			<shiro:hasPermission name="questionlib:courseKnowledge:edit"><td>
   				<a href="${ctx}/questionlib/courseKnowledge/form?id={{row.id}}&extId=${extId}&courseVesionId=${courseVesionId}">修改</a>
				<shiro:hasPermission name="questionlib:courseKnowledge:delete">
					<a href="${ctx}/questionlib/courseKnowledge/delete?id={{row.id}}&extId=${extId}&courseVesionId=${courseVesionId}" onclick="return confirmx('确认要删除该知识点及所有子知识点吗？', this.href)">删除</a>
				</shiro:hasPermission>
			<!--	<a href="${ctx}/questionlib/courseKnowledge/form?parent.id={{row.id}}&extId=${extId}&courseVesionId=${courseVesionId}">添加下级知识点</a> -->
			</td>
             </shiro:hasPermission>
		</tr>
	</script>
	
	<!-- 试题导入界面 -->
	<div id="importForm" style="width: 100%;height: 100%;position: absolute; top: 0;display: none;">
		<div style="width: 850px;min-height: 450px;position: absolute;top: 150px;left: 300px;padding: 15px 4px 4px;border: 1px solid #ccc;text-align: center;background: #fff;">
		<form id="saveImportDocMessage" action="${ctx}/questionlib/courseKnowledge/importExcel" method="post" enctype="multipart/form-data">
			<table style="margin: 0 auto;" id="importTable">
				<tr>
					<th colspan="2" class="control-label"><h3>知识点导入</h3></th>
				</tr>
				<tr>
					<td colspan="2" style="height: 10px;"> </td>
				</tr>
				<tr>
					<th class="control-label">专业：</th>
					<td style="height: 40px;"><sys:treeselect id="specialtyId" ontextchange="true" name="specialty.id" value="${course.specialty.id}" labelName="course.specialty.name" labelValue="${course.specialty.title}"
					title="专业" url="/questionlib/specialty/treeData" cssClass="input-medium" notAllowSelectParent="true" />
				</td>
				</tr>
				<tr>
					<th class="control-label">课程：</th>
					<td style="height: 40px;"><select class="courseSelect" name="courseId" id="courseSelect" style="width:220px;" onchange="loadVersion();">
			   	</select></td>
				</tr>
				<tr>
					<th class="control-label">版本：</th>
					<td style="height: 40px;"><select name="courseVesionId" id="vesionSelect" class="courseVesionSelect" style="width:220px"> 
			  </select></td>
				</tr>
				<tr>
					<th class="control-label">上传excel文件：</th>
					<td style="height: 40px;"><input name="excelFile" type="file" class="input-medium"  style="width:210px;"/></td>
				</tr>
				<tr>
				<td></td>
					<td style="height: 40px;align:center;"><input id="saveImportMessage" class="btn btn-primary" type="submit" value="开始导入"/>
					<input id="closeImportQuestion" class="btn btn-primary" type="button" value="关闭" onclick="closeImportPage();"/></td>
				</tr>
			</table>
		</form>
	</div>
	</div>
</body>
</html>