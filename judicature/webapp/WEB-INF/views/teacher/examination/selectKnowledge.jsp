<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>选择知识点</title>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<%@include file="/WEB-INF/views/teacher/include/validation.jsp" %>
	
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
		#content{margin:5px 5px;}
		.center-jz li{margin-bottom:15px;}
	</style>
    <script type="text/javascript">
      (function(){
    	  try{ parent.removeJbox();}catch(e){}
	      $(function(){
			 	$("#inputForm").validate({
					submitHandler: function(form){
						loading('正在提交，请稍等...');
						form.submit();
					},
					/* errorContainer: "#messageBox", */
					errorPlacement: function(error, element) {
						if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
							error.appendTo(element.parent());
						} else {
							error.insertAfter(element);
						}
					}
				});
	      });
      })();
    </script>
</head>
<body>
<div class="accordion-group" id="content">
    <div id="left" class="accordion-group">
		<div class="accordion-heading">
	    	<span class="accordion-toggle" style="list-style-type: none;">全部知识点<!-- <i class="icon-refresh pull-right"></i> --></span>
	    </div>
		<div id="leftZtree" class="ztree"></div>
	</div>
	<div id="center" style="text-align: center;">
	
		<ul class="center-jz">
			<li><button id="btnaddAll">全选选中</button></li>
			<li><button id="btnadd">确认选中</button></li>
			<li><button id="bendel">取消选中</button></li>
			<li><button id="bendelAll">全部取消</button></li>
			
			<li>1级考点(红色)</li>
			<li>2级考点(绿色)</li>
			<li>3级考点(黄色)</li>
		</ul>
		
	</div>
    <div id="right" class="accordion-group">
		<div class="accordion-heading">
	    	<span class="accordion-toggle" style="list-style-type: none;">选中的知识点<!-- <i class="icon-refresh pull-right"></i> --></span>
	    </div>
		<div id="rightZtree" class="ztree"></div>
	</div>
  </div>


<form:form id="inputForm" action="${ctx}/examination/selectKnowledge?id=${examination.id}"  method="post" class="form-horizontal">
    <input name="courseKnowledge.id" type="hidden" id="ckIds" value="${examknowledgesIds}">
   	<div class="control-group">
		<label class="control-label"></label>
		<div class="controls">
			<input  type="radio" maxlength="50" class="required" name="examMode"    ${examination.examMode eq '1' ? 'checked="checked"' :'' } value="1"  id="examTypeManual" />手动组卷
			<input  type="radio" maxlength="50" class="required" name="examMode"    ${examination.examMode eq '0' ? 'checked="checked"' :'' }  value="0"  id="examTypeAuto"  />自动组卷
		</div>
	</div>
   	<div class="form-actions" style="border-top:0px; text-align: right;">
		<input id="btnSubmit" class="btn tbtn-primary" type="button" value="下一步"/>
	</div>
</form:form>

		<script type="text/javascript">
			 (function(){
			    var leftObj,rightObj;
				var setting = { check: {enable: true},
					data:{ simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
				    view:{
						showIcon:true,
						fontCss: function(treeId,node){
							return node.font ? node.font : {};
						}
						    
					},
					callback:{ /* onClick:function(event, treeId, treeNode){
								var id = treeNode.id;
								$('#knowledgeContent').attr("src","${ctx}/knowledge/list?courseKnowledge.id="+id);
							} */
					} 
				};
				function refreshTree(){
					$.getJSON("${ctx}/knowledge/teacherKnowledgeTreeData",{ r:Math.random()},function(data){
			             for(var i=0,len=data.length;i<len;i++){
			            	 var obj=data[i];
			            	 if(obj.level){
			            		 switch(obj.level){
				            		 case "1":
				            			 obj["font"]={'font-weight':'bold','color':'#8d0c0c'};
				            			 break;
				            		 case "2":
				            			 obj["font"]={'font-weight':'bold','color':'#188681'};
				            			 break;
				            		 case "3":
				            			 obj["font"]={'font-weight':'bold','color':'#f1981a'};
				            			 break;
			            		 }
			            		 data[i]=obj;
			            	 }
			             } 
						 $.fn.zTree.init($("#leftZtree"), setting, data).expandAll(true);
						 $.fn.zTree.init($("#rightZtree"), setting, null).expandAll(true);
						/* $("#ztree > li:first > a:first").trigger("click"); */
						 leftObj=$.fn.zTree.getZTreeObj("leftZtree");
						 rightObj=$.fn.zTree.getZTreeObj("rightZtree");
						 
							
						//初始化右侧树
						var ckIds=$("#ckIds").val().split(",");
						if(ckIds.length>0){
							var nodes = leftObj.transformToArray(leftObj.getNodes());
						    for(var i=0;i<nodes.length;i++){			
								var node = nodes[i];
							    if(jQuery.inArray(node.id, ckIds) !==-1){
							    	leftObj.checkNode(node, true, false);
							    }
							}
							moveTreeNode(leftObj, rightObj);
						}
					});
				}
				$("#left .icon-refresh").click(function(){
					refreshTree();
				});
				
				refreshTree();
					
				$("#btnaddAll").click(function(){
					leftObj.checkAllNodes(true);
					moveTreeNode(leftObj, rightObj);
				});
				
				$("#btnadd").click(function(){
					moveTreeNode(leftObj, rightObj);
				});
				$("#bendel").click(function(){
					moveTreeNode(rightObj,leftObj);
				});
			    $("#bendelAll").click(function(){
			    	rightObj.checkAllNodes(true);
					moveTreeNode(rightObj, leftObj);
				});
			    
			    //保存
	    	    $("#btnSubmit").click(function(){
		    		   var nodes = rightObj.transformToArray(rightObj.getNodes());
		    		   if(nodes.length > 0){
		    			    var str=new Array();
							for(var i=0;i<nodes.length;i++){			
								var node = nodes[i];
								//if(!node.isParent){
									str.push(node.id);
								//}
							}
							if(str.length>0){
							     $("#ckIds").val(str.toString());
							}
							
		    		   }else{
		    			   alertx("请选择左侧知识点",function(){
		    				   closeTip();
		    			   });
		    			   return;
		    		   }
		    		   $("#inputForm").submit();
	    		  
	    	    });
			    
				
				function moveTreeNode(zTree1, zTree2){
					var nodes = zTree1.getCheckedNodes();	//获取选中需要移动的数据
					for(var i=0;i<nodes.length;i++){			//把选中的数据从根开始一条一条往右添加
						var node = nodes[i];
					    var strs={};			//新建一个JSON 格式数据,表示为一个节点,可以是根也可以是叶
						strs.id =node.id;
						strs.name=node.name;
						strs.pId=node.pId;
						strs.pIds= node.pIds;
						strs.font=node.font;
						zTreeDataAddNode(strs,zTree2);
						
						//使用递归移除 移除的时候从叶子开始找  和增加的时候刚好相反
						zTreeDataDelete(nodes[nodes.length-(i+1)],node,zTree1); 
						
					}
					
					//把选中状态改为未选择
				    zTree2.checkAllNodes(false);	
					zTree1.checkAllNodes(false);
					//刷新
					zTree2.refresh();
					zTree1.refresh(); 
				}
				
				//树数据移动方法
				function zTreeDataAddNode(strs,zTree2){
					var nodes = zTree2.transformToArray(zTree2.getNodes());	//获取需要添加数据的树下面所有节点数据
					
					//如果有多个数据需要遍历,找到strs 属于那个父亲节点下面的元素.然后把自己添加进去
					if(nodes.length > 0){
				
						//这个循环判断是否已经存在,true表示不存在可以添加,false存在不能添加
						var isadd=true;
						for(var j=0;j<nodes.length;j++){
							if(strs.id==nodes[j].id){
								isadd=false;
								break;
							}
						}
						//找到父亲节点
						var i=0;
						var flag =false;
						for(i ;i<nodes.length;i++){
							if(strs.pId ==nodes[i].id){
								flag = true;
								break;
							}
						}
						//同时满足两个条件就加进去,就是加到父亲节点下面去
						if(flag && isadd){
							var treeNode1=nodes[i];
							zTree2.addNodes(treeNode1,strs);
						//如果zTree2 里面找不到,也找不到父亲节点.就把自己作为一个根add进去
						}else if(isadd){
						   zTree2.addNodes(null,strs);
						}
					}else{
						//树没任何数据时,第一个被加进来的元素
						zTree2.addNodes(null,strs);
					}
				}
				
				//数据移除
				function zTreeDataDelete(node,scode,zTree1){
					if(node.isParent){	//判断是不是一个根节点,如果是一个根几点从叶子开始遍历寻找
						
						//查询当前节点的所有子节点
						var childrentNode=zTree1.getNodesByFilter(function(nodeObj){
							return true;
						},false,node);
						
						if(childrentNode.length > 0){
								//取出来
							for(var x = 0; x< childrentNode.length; x++){
								//不是根节点.并且code 相当就是需要移除的元素
								if(!(childrentNode[x].isParent) && childrentNode[x].id==scode.id){	
										//调用ztree 的移除方法,参数是一个节点json格式数据
										zTree1.removeNode(childrentNode[x]);
										
										//如果当前这个节点又是个根节点.开始递归
								}else if(childrentNode[x].isParent){	
									zTreeDataDelete(childrentNode[x],scode,zTree1);
								}
							}
						}else{
							//如果是个根,但是下面的元素没有了.就把这个根移除掉
							zTree1.removeNode(node);
						}
					}else{
						//不是就直接移除
						zTree1.removeNode(node);
					}
				}
				
			})(); 
			 
		  (function(){
				 var frameObj =$("#left, #right"),mainRight=$(window),content=$("#content"),bottom=$("#inputForm");
				function wSize(){
				    bottom.width(mainRight.width()-10).height(180);
					content.height(mainRight.height()-10-bottom.height()).width(mainRight.width()-10);
				    
				    $("#center").width("180").height(content.height()-bottom.height()).css({"float":"left","line-height":content.height()+"px"});
				    
				    var center_jz=(content.height()-$("ul.center-jz").height())/2;
				    $("ul.center-jz").css({"margin-top":center_jz+"px"});
				    
				    var w=(content.width()- $("#center").width()-4)/2,
				        h=content.height()-2;
				    frameObj.width(w).height(h).css({"float":"left"});
				    
				    $(".ztree").width(w - 10).height(h- 46);;
				}
				$(window).resize(function(){
					wSize();
				});
				wSize();
			})(); 
		</script>
</body>
</html>