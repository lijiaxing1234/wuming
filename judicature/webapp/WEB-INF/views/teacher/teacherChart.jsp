<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<meta name="decorator" content="teacher_default"/>
<%@include file="/WEB-INF/views/include/chart.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="main" style="width: 643px;height:230px;margin:0 auto;"></div>
	<script type="text/javascript">
		// 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
		
        // 指定图表的配置项和数据
        $.ajax({
			url:'${ctx}/bbs/getTeacherChartsData',
			type:'post',
			cache:false,
			success:function(data){
				var obj = eval("("+data+")")
				myChart.setOption({
					title: {
			        },
			        tooltip: {},
			        legend: {
			            data:obj.legendArray
			        },
			        xAxis: {
			            data: obj.arrayExam,
			            name:'月份',
			            nameLocation:'end'
			        },
			        yAxis: {
						nameLocation:'end'
			        },
			        series: [{
			            name: '随堂练习',
			            type: 'line',
			            data: obj.arrayQuiz2,
			            smooth:true,
			            itemStyle:{
			            	normal:{
			            		color:'rgb(117, 247, 239)'
			            	}
			            },
			            lineStyle:{
			            	normal:{
			            		color:'rgb(117, 247, 239)'
			            	}
			            },
			            areaStyle:{
			            	normal:{
			            		color:'rgb(117, 247, 239)'
			            	}
			            }
			        },{
			        	name:'在线考试',
			        	type:'line',
			        	data:obj.arrayExam2,
			        	smooth:true,
			        	itemStyle:{
			            	normal:{
			            		color:'rgb(223, 235, 108)'
			            	}
			            },
			            lineStyle:{
			            	normal:{
			            		color:'rgb(223, 235, 108)'
			            	}
			            },
			            areaStyle:{
			            	normal:{
			            		color:'rgb(223, 235, 108)'
			            	}
			            }
			        },{
			        	name:'课后作业',
			        	type:'line',
			        	data:obj.arrayHomeWork2,
			        	smooth:true,
			        	itemStyle:{
			            	normal:{
			            		color:'rgb(209, 206, 215)'
			            	}
			            },
			            lineStyle:{
			            	normal:{
			            		color:'rgb(209, 206, 215)'
			            	}
			            },
			            areaStyle:{
			            	normal:{
			            		color:'rgb(209, 206, 215)'
			            	}
			            }
			        },{
			        	name:'课堂例题',
			        	type:'line',
			        	data:obj.arrayExample2,
			        	smooth:true,
			        	itemStyle:{
			            	normal:{
			            		color:'rgb(110, 210, 175)'
			            	}
			            },
			            lineStyle:{
			            	normal:{
			            		color:'rgb(110, 210, 175)'
			            	}
			            },
			            areaStyle:{
			            	normal:{
			            		color:'rgb(110, 210, 175)'
			            	}
			            }
			        }]
				});
			}
		});
	</script>
	
</body>
</html>