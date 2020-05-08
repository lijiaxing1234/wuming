<link href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css" rel="stylesheet" type="text/css"/>
<script src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
<script type="text/javascript">
function expandLevel(treeObj,node,level){  
    var childrenNodes = node.children; 
    if(childrenNodes){
	    for(var i=0;i<childrenNodes.length;i++){  
	        treeObj.expandNode(childrenNodes[i], true, false, false);  
	        level=level-1;  
	        if(level>0){  
	            expandLevel(treeObj,childrenNodes[i],level);  
	        }  
	    }  
    }
} 

function selectFirst(treeObj,level,selectId){
	var nodes = treeObj.getNodes();
	if(nodes.length>0){
	   var firstNode;
	   if(selectId)
		  firstNode=treeObj.getNodeByParam("id",selectId, null);
	   else
		   firstNode=nodes[0];  
	   if(level){
         expandLevel(treeObj,firstNode,level);
	   }else{
		 expandLevel(treeObj,firstNode,1);
	   }
	   treeObj.selectNode(firstNode);  
	   treeObj.setting.callback.onClick(null, treeObj.setting.treeId, firstNode);
	}
}
</script>