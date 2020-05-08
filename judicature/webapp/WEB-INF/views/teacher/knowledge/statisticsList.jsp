<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_blank"/>
	
	<title>知识点管理</title>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %> 
	<%@include file="/WEB-INF/views/teacher/include/validation.jsp" %>
	<%@include file="/WEB-INF/views/teacher/include/date.jsp" %>
	<%@include file="/WEB-INF/views/include/chart.jsp" %>
	
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/student/css/jq22.css">
	<style type="text/css">
		.row{margin-left:0;}
		.progress_bar .pro-bar {
			background: hsl(0, 0%, 97%);
			box-shadow: 0 1px 2px hsla(0, 0%, 0%, 0.1) inset;
			height:4px;
			margin-bottom: 12px;
			margin-top: 65px;
			position: relative;
		}
		.progress_bar .progress_bar_title{
			/*color: hsl(218, 4%, 50%);*/
			color: #666;
			font-size: 15px;
			font-weight: 300;
			position: relative;
			top: -28px;
			z-index: 1;
		}
		.progress_bar .progress_number{
			float: right;
			position:relative;
			
		}
		.progress_bar .progress-bar-inner {
			background-color: hsl(0, 0%, 88%);
			display: block;
			width: 0;
			height: 100%;
			position: absolute;
			top: 0;
			left: 0;
			transition: width 1s linear 0s;
			animation: animate-positive 2s;
		}
		.progress_bar .progress-bar-inner:before {
			content: "";
			background-color: hsl(0, 0%, 100%);
			border-radius: 50%;
			width: 4px;
			height: 4px;
			position: absolute;
			right: 1px;
			top: 0;
			z-index: 1;
		}
		.progress_bar .progress-bar-inner:after {
			content: "";
			width: 14px;
			height: 14px;
			background-color: inherit;
			border-radius: 50%;
			position: absolute;
			right: -4px;
			top: -5px;
		}
		@-webkit-keyframes animate-positive{
			0% { width: 0%; }
		}
		@keyframes animate-positive{
			0% { width: 0%; }
		}
	</style>
	
	<script type="text/javascript">
		$(document).ready(function() {
			 $("#treeTable").treeTable({expandLevel:30}).show(); 
			
			$("#listForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				//errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
			//设置关闭所有子节点
			$("select[id^='ck_']").change(function(){
				var id=$(this).attr("id").split("_")[1],
				    thisVlue=$(this).find("option:selected").val();
				if(thisVlue==4){
					$("select[id^='ck_']").each(function(key,value){
						var pIds=$(this).attr("pIds");
						if(pIds.indexOf(","+id+",")!==-1){
							$(this).val(4);
						}
					});
				}
			});
		});
	</script>
</head>
<body>

	<c:if test="${not empty message}">
	<script type="text/javascript">top.$.jBox.closeTip();</script>
		<%-- 消息类型：info、success、warning、error、loading --%>
		<div id="messageBox" class="alert alert-${message.type}">
			<button data-dismiss="alert" class="close">×</button>
		    	${message.message}
		</div> 
	</c:if>
	
	<div class="demo" >
			<div class="container">
				<div class="row">
					<div class="col-md-offset-3 col-md-6" style="width:80%;margin:0 auto;">
						<h3>知识点通过率</h3>
						<c:forEach items="${list}" var="teacherKnowledge">
							<div class="progress_bar">
								<div class="pro-bar">
									<small class="progress_bar_title">
										${teacherKnowledge.knowledgeTitle }
										<span  class="progress_number">%</span>
									</small>
									<span class="progress-bar-inner" style="background-color: #f4e101; width: ${teacherKnowledge.passingRate }%;" data-value="${teacherKnowledge.passingRate }" data-percentage-value="${teacherKnowledge.passingRate }"></span>
								</div>
							</div>
						</c:forEach>
		
					</div>
				</div>
			</div>
		</div>
		
		<script type="text/javascript">
		    $(function(){
		        var progress = $(".progress-bar-inner");
		        progress.each(function (i)
		        {
		            var data = $(this).attr('data-value');
		            $(this).prev().find("span").html(data+"%");
		        });
		    });
		</script>
	
	
	<!-- <div id="main" style="width: 600px;height:400px;"></div>
	<script type="text/javascript">
		var myChart = echarts.init(document.getElementById('main'));
		var myOption = {
			title: {
                text: '知识点通过率',
                textStyle:{
                	fontSize:12
                }
            },
            tooltip: {},
            legend: {
                data:['通过率']
            },
            xAxis: {
            	axisLabel:{
            		show:false
            	},
            	splitLine:{
            		show:false
            	},
            	data: [],
                axisLabel: {
                    interval:0,//横轴信息全部显示
                    rotate: 45
               }
            },
            yAxis: {
            	max:100
            },
            series: [{
                name: '通过率',
                type: 'bar',
                data: [],
                itemStyle:{
                	normal:{
                		color:'rgb(245, 222, 30)'
                	}
                }
            }]
		};
			myOption.xAxis.data = ${arr1};
			myOption.series[0].data = ${arr2};
			myChart.setOption(myOption);
		</script> -->
		
	  <%-- <form:form id="searchForm" modelAttribute="teacherKnowledge" action="${ctx}/knowledge/statisticsList" method="post" class="breadcrumb form-search form-horizontal">
		<form:hidden path="courseKnowledge.id"/>
		<input id="pageNo" name="pageNo" type="hidden" value="${examList.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${examList.pageSize}"/>
		班级：&nbsp;
		   <form:select path="classId">
		         <form:option value="">全部</form:option>
		         <form:options items="${questionlib:getCourseQuestionlibByTeacherId()}" itemLabel="examClass.title" itemValue="examClass.id"  htmlEscape="false"/>
		   </form:select>
		 通过率小于：
		 <form:select path="passingRate" class="input-small">
		     <form:option value="100">100%</form:option>
		     <form:option value="80">80%</form:option>
		     <form:option value="60">60%</form:option>
		     <form:option value="40">40%</form:option>
		 </form:select>
		 
		 <br/>
		 <div style="margin-top:5px;">
			日期范围：<input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				value="<fmt:formatDate value="${beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			--
			  <input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>&nbsp;&nbsp;
				<input id="btnExamSubmit" class="btn tbtn-primary" type="submit" value="查看"/>
		  </div>
		
	</form:form>
	
	<form:form id="listForm" modelAttribute="teacherKnowledge" action="${ctx}/knowledge/save" method="post" class="form-horizontal">
		<form:hidden path="courseKnowledge.id"/>
		<table id="treeTable" class="table table-striped table-bordered table-condensed" style="margin-top: 20px;">
			<thead>
				<tr>
					<!-- <th>编号</th> -->
					<th>考点(知识点)</th>
					<!-- <th>建议级别</th>
					<th>课时</th> -->
					<th>自定义级别</th>
					<th>通过率</th>
				</tr>
			</thead>
			<tbody>
			 <c:forEach items="${list}" var="item" varStatus="index">
				<tr id="${item.courseKnowledge.id}" pId="${ item.courseKnowledge.parent.id ne '1' ? item.courseKnowledge.parent.id : '0' }">
					<td>${index.index+1}</td>
					<td style="text-align: left;">${item.courseKnowledge.title }</td>
				 
					 
					<td style="text-align:center;">
					    <input name="ckIdArr" value="${item.courseKnowledge.id}" type="hidden">
					       <c:forEach  items="${fns:getDictList('questionlib_courseKnowledge_level')}" var="dictItem" >
					               ${item.courseKnowledge.level eq dictItem.value ? dictItem.label:""}   
					       </c:forEach>
					</td>
					<td> ${item.passingRate}% </td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	     
	 </form:form> --%>
</body>
</html>