<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true"  description="隐藏域名称（ID）"%>
<%@ attribute name="url" type="java.lang.String" required="true" description="树结构数据地址"%>
<%@ attribute name="valueUrl" type="java.lang.String" required="true" description="树结构数据地址"%>
<%@ attribute name="cssClass" type="java.lang.String" required="false" description="css样式"%>
  <span>
	<input name="${name}" id="${id}" type="hidden"  class=" ${cssClass}"/>
	<a id="${id}Button" href="javascript:" class="btn">添加专业</a>
	<span class="help-inline"><font color="red">*</font></span>
  </span>
   
<ol id="${id}SelectList"></ol>
			  
 <script type="text/javascript">
	var ${id}Select = [];
	function ${id}SelectAddOrDel(id,title){
		var isExtents = false, index = 0;
		for (var i=0; i< ${id}Select.length; i++){
			if (${id}Select[i][0]==id){
				isExtents = true;
				index = i;
			}
		}
		if(isExtents){
			${id}Select.splice(index,1);
		}else{
			${id}Select.push([id,title]);
		}
		//${id}SelectRefresh();
	}
	function ${id}SelectRefresh(){
		var ${id}DataRelation=$("#${id}"),
			${id}SelectList=$("#${id}SelectList");
		
		${id}DataRelation.val("");
		${id}SelectList.children().remove();
		
		for (var i=0; i<${id}Select.length; i++){
			${id}SelectList.append("<li>"+${id}Select[i][1]+"&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"${id}SelectAddOrDel('"+${id}Select[i][0]+"','"+${id}Select[i][1]+"');${id}SelectRefresh();\">×</a></li>");
			${id}DataRelation.val(${id}DataRelation.val()+${id}Select[i][0]+",");
		} 
	}
	<c:if test="${not empty valueUrl}"> 
	
 	$.getJSON("${valueUrl}",function(data){
		for (var i=0; i<data.length; i++){
			${id}Select.push([data[i]["id"],data[i]["title"]]);
		}
		${id}SelectRefresh();
	}); 
	</c:if>
	
	$("#${id}Button").click(function(){
	     var selectIds="";
		 for(var i=0; i<${id}Select.length; i++) {
			 selectIds+=${id}Select[i][0];
			 if(i !== ${id}Select.length-1){
				 selectIds+=",";
			 }
		 }
		top.$.jBox.open("iframe:${ctx}/tag/treeselect?url="+encodeURIComponent("${url}")+"&module=false&checked=true&extId=&isAll=false", "添加专业", 300, 420,	{
			ajaxData:{
				 selectIds:selectIds
			},
			buttons:{"确定":"ok","关闭":true},
			submit:function(v, h, f){
				if (v=="ok"){
					var tree = h.find("iframe")[0].contentWindow.tree;
					var ids = [], names = [], nodes = [];
					//if ("${checked}" == "true"){
						nodes = tree.getCheckedNodes(true);
					/* }else{
						nodes = tree.getSelectedNodes();
					} */
					${id}Select = [];
					for(var i=0; i<nodes.length; i++) {
						
						if (nodes[i].isParent){
							continue; // 如果为复选框选择，则过滤掉父节点
						}
						/* if (nodes[i].level == 0){
							top.$.jBox.tip("不能选择根节点（"+nodes[i].name+"）请重新选择。");
							return false;
						} */
						/* if (nodes[i].isParent){
							top.$.jBox.tip("不能选择父节点（"+nodes[i].name+"）请重新选择。");
							return false;
						} */
						/*if (nodes[i].module == ""){
							top.$.jBox.tip("不能选择公共模型（"+nodes[i].name+"）请重新选择。");
							return false;
						}else if (nodes[i].module != "${module}"){
							top.$.jBox.tip("不能选择当前栏目以外的栏目模型，请重新选择。");
							return false;
						}*/
						/* ids.push(nodes[i].id);
						names.push(nodes[i].name); */
						${id}SelectAddOrDel(nodes[i].id,nodes[i].name);
						${id}SelectRefresh();
						
					}
					/* $("#${id}Id").val(ids.join(",").replace(/u_/ig,""));
					$("#${id}Name").val(names.join(","));
					
					if ("${ontextchange}" == "true") {
						chengeCourse();
					} */
				}
			}, loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
				${id}SelectRefresh();
			}
			
			/* buttons:{"确定":true},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			},submit:function(b,d,f){
				if(b){
					${id}SelectRefresh();
				}
			},closed:function(){
				${id}SelectRefresh();
			}*/
		}); 
	});
</script>