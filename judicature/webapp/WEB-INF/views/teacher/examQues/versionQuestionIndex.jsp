<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<title>试题管理</title>
<meta name="decorator" content="teacher_default"/>
<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
	
	<script type="text/javascript">
	function importQuestion(target){
		 $.jBox("iframe:"+target,{
			title:"导入试题",
			width: 700,
		    height: 500,
			buttons:{}, 
		    bottomText:"",
		    loaded:function(h){
				$(".jbox-content",document).css("overflow-y","hidden");
			},closed:function(){
				refreshTree();
			}
		});  
		return false;
	} 
</script>
</head>
<body style="clear:both;">
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group">
			<div class="accordion-heading">
		    	<a class="accordion-toggle">知识点<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
		    </div>
			<div id="ztree" class="ztree"></div>
		</div>
		<div id="right">
			<iframe id="officeContent" src="" width="100%" height="100%"   frameborder="0" scrolling="auto"  frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
		//显示所有子节点
		var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",pIdsKey:"pIds",rootPId:'0'}},
				callback:{onClick:function(event, treeId, treeNode){
						var id = treeNode.id;
						$('#officeContent').attr("src","${ctx}/examQues/list?knowledgeId="+id);
					}
				}
			};
		
		function refreshTree(){
			$.getJSON("${ctx}/common/courseKnowledgeByVersionTreeData",{ r:Math.random()},function(data){
				var treeObj=$.fn.zTree.init($("#ztree"), setting, data);
				selectFirst(treeObj,1);
				//treeObj.expandAll(true);
				//$("#ztree  li:first  a:first").trigger("click");
			});
		    /* $('#officeContent').attr("src","${ctx}/examQues/list"); */

		}
		refreshTree();
		
		 (function(){
			 var frameObj = $("#left, #right, #right iframe"),mainRight=$(window);
			function wSize(){
				mainRight.css({"overflow-y":"hiden"});
				
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