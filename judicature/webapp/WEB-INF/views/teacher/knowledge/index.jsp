<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="teacher_default"/>
<title>首页</title>
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
							$('#knowledgeContent').attr("src","${ctx}/knowledge/list?courseKnowledge.id="+id);
						}
					}
				};
				function refreshTree(){
					$.getJSON("${ctx}/common/courseKnowledgeByVersionTreeData",{ r:Math.random()},function(data){
						
						var tree=$.fn.zTree.init($("#ztree"), setting, data);
						//$("#ztree > li:first > a:first").trigger("click");
						//tree.expandAll(true);
						var nodes = tree.getNodes();
						if(nodes.length>0){
							var rootNode=tree.getNodes()[0];  
				            expandLevel(tree,nodes[0],0);  
				            tree.selectNode(rootNode);  
				            tree.setting.callback.onClick(null, tree.setting.treeId, rootNode);//调用事件 
						}
					});
					 
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
				$("#left .icon-refresh").click(function(){
					refreshTree();
				});
				refreshTree();
			})();
			 (function(){
				 var frameObj = $("#left, #right, #right iframe"),mainRight= $(window);//parent.$("div.content-right");
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