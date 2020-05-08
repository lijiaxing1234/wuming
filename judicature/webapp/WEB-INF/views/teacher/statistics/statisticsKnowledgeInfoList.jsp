<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_blank"/>
	<title>课堂例题</title>
	<style type="text/css">
		.addColor td {	background-color: #BEBEBE; }
		.table tbody tr{cursor:pointer;}
	</style>
</head>
<body>
	<form:form id="listForm" modelAttribute="TeacherVersionQuestion" action="" method="post" class="form-horizontal">
		<table id="treeTable" class="table  table-bordered table-condensed" style="margin-top: 20px;">
			<thead>
				<tr>
					<th width="5%">编号</th>
					<th >题干</th>
				</tr>
			</thead>
			<tbody>
			 <c:forEach items="${list}" var="item" varStatus="index">
				<tr id="${item.id }">
					<td>${index.index+1}</td>
					<td style="width: 20px;">${item.title}
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	 </form:form>
	 	
	 <div id="content" style="border: 1px solid #e5e5e5;padding: 0px 10px;height:500px; overflow-y:auto; position:relative;right: 0px; background-color:white;">
	     <div style="padding: 9px 15px;border-bottom: 1px solid #eee;">
	         <h2>做错学生</h2>
	     </div>
	     
	     <ol id="olContent" style="padding-left:20px;">
	     
	     </ol>
	     
	     <%-- <c:forEach var="item" items="${stuWrongList}" varStatus="index">
		     <ol id="${item.key}" style="display: none;">
		        <c:forEach var="stu" items="${item.value}">
		         <li>${stu["stuName"] }</li>
		        </c:forEach>
		     </ol>
	     </c:forEach> --%>
	 </div>
	 
	 
	 <script type="text/javascript">
	    (function(){
	    	var examId="${examId}",
	    	    classId="${classId}";
	       if(!!!examId||!!!classId){
	    	   
	    	   top.window.location.replace('${ctx}');
	    	   return;
	       }
	    	   
	    	   
	    	$("#treeTable tbody tr").click(function(){
	    		 $(this).addClass("addColor").siblings().removeClass("addColor");
	    		/* $("#content ol[id='"+$(this).attr("id")+"']").show().siblings().hide(); */
	    		var quesId=$(this).attr("id");
	    		 $("#olContent").empty();
	    		$.getJSON("${ctx}/statistics/findWrongStudentListByClassIdAndExamIdAndQuestionId",{classId:classId,examId:examId,quesId:quesId, r:Math.random()},function(data){
    			    var array=[];
	    			$.each(data, function(i,item){
    				   array.push("<li>"+item.stuName+"</li>");
    		       });
	    			$("#olContent").html(array.join(""));
	    		});
	    		
	    	});
	    	$("#treeTable tbody tr:first").trigger("click");

	        window.onload = window.onresize = window.onscroll = function() {
	            leftadp();
	        };
	        leftadp = function() {
	          var oBox = document.getElementById("content");
	          var iScrollTop = document.documentElement.scrollTop || document.body.scrollTop;
	          setTimeout(function() {
			       clearInterval(oBox.timer);
			       var iTop = parseInt((document.documentElement.clientHeight - oBox.offsetHeight) / 8) + iScrollTop;
		            oBox.timer = setInterval(function() {
		                var iSpeed = (iTop - oBox.offsetTop) / 8;
		                iSpeed = iSpeed > 0 ? Math.ceil(iSpeed) : Math.floor(iSpeed);
		                oBox.offsetTop == iTop ? clearInterval(oBox.timer) : (oBox.style.top = oBox.offsetTop + iSpeed + "px");
		            }, 30)
			  }, 100)
	        };
	    	
	    })();
	    (function(){
	    	 var frameObj = $("#content"),mainRight=$("body");
				function wSize(){
					//frameObj.height(mainRight.height());
					var w=mainRight.width()/3-20;
					$("#listForm").width(w*2).css({"float":"left"});
					frameObj.width(w).css({"float":"left"});
				}
				$(window).resize(function(){
					wSize();
				});
				wSize();
	    })();
	 </script>
</body>
</html>