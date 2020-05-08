<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>试题管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
</head>
<body>
	<sys:message content="${message}"/>
 
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group">
			<!-- <div class="accordion-heading">
		    	<a class="accordion-toggle">知识点<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
		    </div> -->
			<div id="ztree" class="ztree"  ></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right" style="overflow: auto;" width="100%" height="300px" >
		    	<form id="searchForm" modelAttribute="courseVesion" action="${ctx}/questionlib/versionQuestion/index" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<ul class="ul-form">
					<li>
				  <label>课程：</label>
				    <select class="courseSelect input-small" name="courseId" id="courseId" onchange="refreshTree();" style="width:200px;">
				     <!--  <option value="">请选择</option> -->
				      <c:forEach items="${ questionlib:getCourse() }" var="course">
							<option value="${course.id}" ${courseKnowledge.courseId==course.id?"selected":""}  >${course.title} </option>
					  </c:forEach>
					  
				   	</select>
				</li>
				</ul>
				
				
				
			</form>
			<iframe id="officeContent" src="${ctx}/questionlib/versionQuestion/list?extId=&courseId=${courseId}" width="100%" height="100%"   frameborder="0" scrolling="auto"  frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
		//显示所有子节点
		var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",pIdsKey:"pIds",rootPId:'0'}},
				callback:{onClick:function(event, treeId, treeNode){
						var id = treeNode.id;
						   var courseId =$("#courseId").val();
						$('#officeContent').attr("src","${ctx}/questionlib/versionQuestion/list?extId="+id+"&courseId="+courseId);
					}
				}
			};
		
		function refreshTree(){
			
			
		    var courseId =$("#courseId").val();
		    //alert(courseId);
			$.getJSON("${ctx}/questionlib/versionQuestion/treeData?courseId="+courseId,{ r:Math.random() },function(data){
				$.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
			});
		    $('#officeContent').attr("src","${ctx}/questionlib/versionQuestion/list?courseId="+courseId);
		}
		 
		var leftWidth = 180; // 左侧窗口大小
		var htmlObj = $("html"), mainObj = $("#main");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		function wSize(){
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
			mainObj.css("width","auto");
			frameObj.height(strs[0] - 1);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
			$(".ztree").width(leftWidth - 10).height(frameObj.height() - 6);
			var questionlibId =$("#questionlibId").val();
			//alert(questionlibId);
		    $('#officeContent').attr("src","${ctx}/questionlib/versionQuestion/list?courseQuestionlibId="+questionlibId);
		}
		refreshTree();
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>