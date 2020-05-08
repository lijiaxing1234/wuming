<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
</head>
<body>
		<div id="content" class="row-fluid">
		<div id="left">
			<%-- <iframe id="cmsMenuFrame" name="cmsMenuFrame" src="${ctx}/category/treeData" style="overflow:visible;"
				scrolling="yes" frameborder="no" width="100%"></iframe> --%>
			<div id="ztree" class="ztree"></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right">
			<iframe id="cmsMainFrame" name="cmsMainFrame" src="${ctx}/resource/list" style="overflow:visible;"
				scrolling="yes" frameborder="no" width="100%"></iframe>
		</div>
	</div>
	<script type="text/javascript">
		//显示所有子节点
		var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",pIdsKey:"pIds",rootPId:'0'}},
				callback:{onClick:function(event, treeId, treeNode){
						var id = treeNode.id;
						$('#cmsMainFrame').attr("src","${ctx}/resource/list?categoryId="+id);
					}
				}
			};
		
		function refreshTree(){
			$.getJSON("${ctx}/category/treeData",{ r:Math.random() },function(data){
				$.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
			});
		    $('#cmsMainFrame').attr("src","${ctx}/resource/list");
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
		    $('#cmsMainFrame').attr("src","${ctx}/resource/list");
		}
		refreshTree();
	</script>
	<!-- </script> -->
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>