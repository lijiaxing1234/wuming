<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="teacher_default"/>
<title>教学资源</title>
<%@include file="/WEB-INF/views/include/treeview.jsp" %>
<style type="text/css">
	.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
</style>
</head>
<body>
		<div id="left" class="accordion-group">
			<div class="accordion-heading">
		    	<span class="accordion-toggle" style="list-style-type: none;">知识点<i class="icon-refresh pull-right"></i></span>
		    </div>
			<div id="ztree" class="ztree"></div>
		</div>
		<div id="right">
			<iframe id="knowledgeContent" src="" width="100%" height="100%" frameborder="0"></iframe>
		</div>
		<script type="text/javascript">
			(function(){
				var setting = { data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'},view:{showIcon:false}},
					callback:{ onClick:function(event, treeId, treeNode){
							var id = treeNode.id;
							$('#knowledgeContent').attr("src","${ctx}/sources/list?knoId="+id);
						}
					}
				};
				function refreshTree(){
					$.getJSON("${ctx}/common/courseKnowledgeByVersionTreeData",{ r:Math.random() },function(data){
						
						var treeObj=$.fn.zTree.init($("#ztree"), setting, data);
						//treeObj.expandAll(true);
						selectFirst(treeObj,1);
						//$("#ztree > li:first  > a:first").trigger("click");
/* 						$("#ztree > li:first  > ul:first > li:first > a:first").trigger("click"); */
					});
					 
				}
				$("#left .icon-refresh").click(function(){
					refreshTree();
				});
				refreshTree();
			})();
			 (function(){
				 var frameObj = $("#left, #right, #right iframe"),mainRight=$(window);
				function wSize(){
					frameObj.height(mainRight.height()- 5);
				    $("#left").width("180").css({"float":"left"});
					var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
					$("#right").width(mainRight.width()- leftWidth -5).css({"float":"left"});
					$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46); 
				}
				$(window).resize(function(){
					wSize();
				});
				wSize();
			})(); 
		</script>
</body>
</html>