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
	<div id="content" class="row-fluid"  height=200px;">
		<div id="left" >
			<!-- <div class="accordion-heading">
		    	<a class="accordion-toggle">知识点<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
		    </div> -->
			<div id="ztree" class="ztree"></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right" style="overflow: auto;" width="100%" height="300px" >
		     	<form id="searchForm" modelAttribute="courseVesion" action="${ctx}/school/schoolversionQuestion/index" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="courseQuestionlibId" type="hidden" name="courseQuestionlibId" value="${courseQuestionlibId}"/>
				<ul class="ul-form">
					<li><label>题库：</label>
						<select name="courseQuestionlibId" id="questionlibId" class="questionlibId" style="width:350px" onChange="refreshTree();"> <!-- class="input-medium" -->
				 	     	<option value="" >请选择</option>
				 	     <c:forEach items="${list}" var="courseQuestionlib">
						   	<option value="${courseQuestionlib.id}" ${courseQuestionlibId==courseQuestionlib.id?"selected":""}>${courseQuestionlib.title} </option>
						 </c:forEach>
					 	</select>
					</li>
					<li class="clearfix"></li>
				</ul>
			</form>
			<iframe id="officeContent" src="" width="100%" height="100%"   frameborder="0" scrolling="auto"  frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
		//显示所有子节点
		var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",pIdsKey:"pIds",rootPId:'0'}},
				callback:{onClick:function(event, treeId, treeNode){
						var id = treeNode.id;
						var questionlibId =$("#questionlibId").val();
						$('#officeContent').attr("src","${ctx}/school/schoolversionQuestion/list?extId="+id+"&courseQuestionlibId="+questionlibId);
					}
				}
			};
		
		function refreshTree(){
		    var questionlibId =$("#questionlibId").val();
			$.getJSON("${ctx}/school/schoolversionQuestion/treeData?courseQuestionlibId="+questionlibId,{r:Math.random()},function(data){
				var zTree=$.fn.zTree.init($("#ztree"), setting, data);
			    var nodes = zTree.getNodes();
				if(nodes.length>0){
					var rootNode=nodes[0];  
					zTree.selectNode(rootNode);
					zTree.setting.callback.onClick(null, zTree.setting.treeId, rootNode);
				}
				zTree.expandAll(true);
			});
		  
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
		}
		refreshTree();
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>