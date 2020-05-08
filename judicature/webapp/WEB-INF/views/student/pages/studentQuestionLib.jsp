<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>
<html>
<head>
	<title>我的题库</title>
	<meta name="decorator" content="student_default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
	<script type="text/javascript">
			 
	</script>
</head>
<body>
	<sys:message content="${message}"/>
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group">
			<div class="accordion-heading">
		    	<a class="accordion-toggle">知识点<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
		    </div>
			<div id="ztree" class="ztree"></div>
		</div>
		<div id="openClose" class="close" style="background:#efefef">&nbsp;</div>
		<div id="right">
			<iframe id="officeContent" src="${ctx}/studentNews/knowledgePointsList?id=&parentIds=" width="100%" height="100%" frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
		refreshTree();
		//显示所有子节点
		var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",pIdsKey:"pIds",rootPId:'0'}},
				callback:{onClick:function(event, treeId, treeNode){
						var id = treeNode.id;
						var pIds = treeNode.pIds;
						var courseVesionId =$("#courseVesionSelect").val();
						$('#officeContent').attr("src","${ctx}/studentNews/knowledgePointsList?id="+id+"&parentIds="+id);
					}
				}
			};
		
		function refreshTree(){
		    var courseVesionId =$("#courseVesionSelect").val();
			$.getJSON("${ctx}/studentNews/treeData",{ r:Math.random() },function(data){
				var tree = $.fn.zTree.init($("#ztree"), setting, data);
				var nodes = tree.getNodes();
				if(nodes.length>0){
					var rootNode=nodes[0];  
		            expandLevel(tree,rootNode,1);  
				}
				//.expandAll(true);
				
				
			});
			
			$('#officeContent').attr("src","${ctx}/studentNews/knowledgePointsList");
		}
		 
		var leftWidth = 180; // 左侧窗口大小
		var htmlObj = $("html"), mainObj = $("#main");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		function wSize(){
			var strs = getWindowSize().toString().split(",");
			//htmlObj.css({"overflow-x":"hidden", "overflow-y":"auto"});
			mainObj.css("width","auto");
			frameObj.height(strs[0] - 5-$("div.course-header:first").height()-$("div.course-footer:first").height() + 112);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
			$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
			var courseVesionId =$("#courseVesionSelect").val();
			$('#officeContent').attr("src","${ctx}/studentNews/knowledgePointsList");
		}
		
		/* function expandLevel(treeObj,node,level){  
            var childrenNodes = node.children;  
            for(var i=0;i<childrenNodes.length;i++)  
            {  
                treeObj.expandNode(childrenNodes[i], true, false, false);  
                level=level-1;  
                if(level>0)  
                {  
                    expandLevel(treeObj,childrenNodes[i],level);  
                }  
            }  
        }   */
		
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>