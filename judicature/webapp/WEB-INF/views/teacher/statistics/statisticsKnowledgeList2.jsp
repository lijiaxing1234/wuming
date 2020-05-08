<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_blank"/>
	
	<title>知识点班级统计管理</title>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<%@include file="/WEB-INF/views/include/chart.jsp" %>
	<%--<%@include file="/WEB-INF/views/teacher/include/validation.jsp" %>
	<%@include file="/WEB-INF/views/teacher/include/date.jsp" %> --%>
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
			margin-top: -24px;
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
			$("#treeTable").treeTable({expandLevel:4}).show();
			 /*$("#listForm").validate({
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
			}); */
		});
	</script>
</head>
<body>

	<div class="clearfix">
		<div id="main1" style="width: 190px;height:219px;float:left;"></div>
		<script type="text/javascript">
				var myChart = echarts.init(document.getElementById('main1'));

				myChart.setOption({
					title: {
			            text: '平均分',
			            bottom: 4,
			            left:'center'
			        },
					tooltip: {
				        trigger: 'item',
				        formatter: "{a} <br/>{b}: {c} ({d}%)"
				    },
				    legend: {
				        orient: 'vertical',
				        x: 'left',
				        data:['平均分','']
				    },
				    series: [
				        {
				            type:'pie',
				            radius: ['50%', '70%'],
				            avoidLabelOverlap: false,
				            label: {
				                normal: {
				                    show: false,
				                    position: 'center'
				                },
				                emphasis: {
				                    show: true,
				                    textStyle: {
				                        fontSize: '30',
				                        fontWeight: 'bold'
				                    }
				                }
				            },
				            labelLine: {
				                normal: {
				                    show: false
				                }
				            },
				            data:[{
				                    value: ${avgScore},
				                    name:'平均分',
				                    itemStyle:{
				                        normal:{
				                            color:'rgb(132, 229, 212)'
				                        }
				                    }
				                },
				                {
				                    value:100-${avgScore}, 
				                    name:'',
				                    itemStyle:{
				                        normal:{
				                            color:'rgb(218, 236, 235)'
				                        }
				                    }
				                    
				                }
				            ]
				        }
				    ]
				});
			</script>
			
			<div id="main2" style="width: 190px;height:219px;float:left;"></div>
			<script type="text/javascript">
					var myChart2 = echarts.init(document.getElementById('main2'));
	
					myChart2.setOption({
						title: {
				            text: '最高',
				            bottom: 4,
				            left:'center'
				        },
						tooltip: {
					        trigger: 'item',
					        formatter: "{a} <br/>{b}: {c} ({d}%)"
					    },
					    legend: {
					        orient: 'vertical',
					        x: 'left',
					        data:['最高分','']
					    },
					    series: [
					        {
					            type:'pie',
					            radius: ['50%', '70%'],
					            avoidLabelOverlap: false,
					            label: {
					                normal: {
					                    show: false,
					                    position: 'center'
					                },
					                emphasis: {
					                    show: true,
					                    textStyle: {
					                        fontSize: '30',
					                        fontWeight: 'bold'
					                    }
					                }
					            },
					            labelLine: {
					                normal: {
					                    show: false
					                }
					            },
					            data:[{
					                    value: ${scoreMax},
					                    name:'最高分',
					                    itemStyle:{
					                        normal:{
					                            color:'rgb(132, 229, 212)'
					                        }
					                    }
					                },
					                {
					                    value:100-${scoreMax}, 
					                    name:'',
					                    itemStyle:{
					                        normal:{
					                            color:'rgb(218, 236, 235)'
					                        }
					                    }
					                    
					                }
					            ]
					        }
					    ]
					});
				</script>
				
				<div id="main3" style="width: 190px;height:219px;float:left;"></div>
				<script type="text/javascript">
						var myChart3 = echarts.init(document.getElementById('main3'));
		
						myChart3.setOption({
							title: {
					            text: '最低',
					            bottom: 4,
					            left:'center'
					        },
							tooltip: {
						        trigger: 'item',
						        formatter: "{a} <br/>{b}: {c} ({d}%)"
						    },
						    legend: {
						        orient: 'vertical',
						        x: 'left',
						        data:['最低分','']
						    },
						    series: [
						        {
						            type:'pie',
						            radius: ['50%', '70%'],
						            avoidLabelOverlap: false,
						            label: {
						                normal: {
						                    show: false,
						                    position: 'center'
						                },
						                emphasis: {
						                    show: true,
						                    textStyle: {
						                        fontSize: '30',
						                        fontWeight: 'bold'
						                    }
						                }
						            },
						            labelLine: {
						                normal: {
						                    show: false
						                }
						            },
						            data:[{
						                    value: ${scoreMin},
						                    name:'最低分',
						                    itemStyle:{
						                        normal:{
						                            color:'rgb(132, 229, 212)'
						                        }
						                    }
						                },
						                {
						                    value:100-${scoreMin}, 
						                    name:'',
						                    itemStyle:{
						                        normal:{
						                            color:'rgb(218, 236, 235)'
						                        }
						                    }
						                    
						                }
						            ]
						        }
						    ]
						});
					</script>
					<input type="hidden" id="examId" value="${examId }">
					<input type="hidden" id="examClassId" value="${examClassId}">
				</div>	
					
					<div id="main5" style="width: 800px;height:279px;"></div>
					<script type="text/javascript">
						var myChart5 = echarts.init(document.getElementById('main5'));
						var examId=$("#examId").val();
				        var examClassId=$("#examClassId").val();
						$.ajax({
							url : "${ctx}/statistics/personScoreList3",
					        data : {examId:examId,examClassId:examClassId},
							type:'post',
							cache:false,
							success:function(data){
								var obj = eval("("+data+")");
								console.log(obj.arr1);
								myChart5.setOption({
									title: {
						                text: '成绩分布',
						                textStyle:{
						                	fontSize:12
						                }
						            },
						            tooltip: {},
						            legend: {
						                data:['成绩分布']
						            },
						            xAxis: {
						            	type : 'category',
						                data: obj.arr1,
						                axisLabel:{
						                	interval:0
						                }
						            },
						            yAxis: {
						            	splitNumber:1
						            },
						            series: [{
						                name: '成绩分布',
						                type: 'bar',
						                data: obj.arr2,
						                itemStyle:{
						                	normal:{
						                		color:'rgb(245,222,30)'
						                	}
						                }
						            }]
								});
							}
						});
						
					</script>
					
					
					
				
		<div class="demo" style="width: 100%;height:auto;margin:10px auto 0;">
			<div class="container">
				<div class="row">
					<div class="col-md-offset-3 col-md-6" style="width:80%;margin:0 auto;">
						<c:forEach items="${list}" var="item" varStatus="index">
							<div class="progress_bar">
								<div class="pro-bar">
									<small class="progress_bar_title">
										<%-- <a href="${ctx }/statistics/staticKnowledgeByexamIdAndClassIdInfo?knowId=${item.knowId}&examId=${examId }&classId=${classId}&backUrl=${backUrl}" style="background: url(${ctxStatic}/icon/check_1.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" title="查看"></a> --%>
										<a href="${ctx }/statistics/staticKnowledgeByexamIdAndClassIdInfo?knowId=${item.knowId}&examId=${examId }&classId=${classId}&backUrl=${backUrl}"  title="查看">
											${item.knowTitle }
										</a>
										<span  class="progress_number">%</span>
									</small>
									<span class="progress-bar-inner" style="background-color: #f4e101; width:${item.correctRate}%;" data-value="${item.correctRate}" data-percentage-value="${item.correctRate}"></span>
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

	 <div class="form-actions pagination-left">
	   <!-- <input id="btnSubmit" class="btn btn-primary" type="button" onclick="history.go(-1);" value="返回"/> -->
	   <c:choose>
	       <c:when test="${ empty backUrl }">
	           <a href="${ctx}"  target="_top" class="btn tbtn-primary">返回</a>
	       </c:when>
	       <c:otherwise>
	          <a href="${backUrl}"  class="btn tbtn-primary">返回</a>
	       </c:otherwise>
	   </c:choose>
	 </div>
		 
</body>
</html>