<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>

<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<%@include file="/WEB-INF/views/include/chart.jsp" %>
	 <%-- <script src="${pageContext.request.contextPath}/echarts/js/echarts.min.js"></script> --%>
	<title>统计分析</title>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/examScore/examOnLineScore?examId=${exam.id}&examClassId=${school.id}">考试成绩列表</a></li>
	<li class="active"><a href="${ctx}/statistics/personList2?id=${exam.id}&classId=${school.id}">考试成绩统计分析</a></li>
</ul> 
	<form:form id="searchForm" modelAttribute="exam" action="${ctx}/statistics/studentList" method="post" class="breadcrumb form-search ">
		<input id="studentId" type="hidden" value="${student.id}"/>
		<input id=examClassId name="examClass.id" type="hidden" value="${school.id}"/>
		<input id=examId name="id" type="hidden" value="${exam.id}"/>
		试卷名称：${exam.title}&nbsp;&nbsp;&nbsp;
		班级：${school.title}&nbsp;&nbsp;&nbsp;
		试卷总分：${totalScore}&nbsp;&nbsp;&nbsp;
		全班最高分：${scoreMax}&nbsp;&nbsp;&nbsp;
		全班最低分：${scoreMin}&nbsp;&nbsp;&nbsp;
		全班平均分：${avgScore}&nbsp;&nbsp;&nbsp;
		<input class="btn tbtn-primary" type="button" value="返回" onclick="history.go(-1);">
	</form:form>
	<form action="">
    	 <!-- 显示Echarts图表 -->
        <div style="height:410px;min-height:100px;margin:0 auto;" id="main"></div>                        
                    
        <script type="text/javascript">
            
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            title: {    //图表标题
                text: '学生成绩分布'
            },
            tooltip: {
                trigger: 'axis', //坐标轴触发提示框，多用于柱状、折线图中
               
            },
            dataZoom: [
                 {
                     type: 'slider',    //支持鼠标滚轮缩放
                     start: 0,            //默认数据初始缩放范围为10%到90%
                     end: 100
                 },
                 {
                     type: 'inside',    //支持单独的滑动条缩放
                     start: 0,            //默认数据初始缩放范围为10%到90%
                     end: 100
                 }
            ],
            legend: {    //图表上方的类别显示               
                show:true,
                data:['人数（人）']
            },
            color:[
                   '#FF3333'    //分数曲线颜色
                   ],
             toolbox: {    //工具栏显示             
                show: true,
                feature: {                
                    saveAsImage: {}        //显示“另存为图片”工具
                }
            }, 
            xAxis:  {    //X轴           
                type : 'category',
                name:'分数',
                data : []    //先设置数据值为空，后面用Ajax获取动态数据填入
            },
            yAxis : [    //Y轴
                        {
                            //第一个（左边）Y轴，yAxisIndex为0
                             type : 'value',
                             name : '人数',
                             max: 50,
                             min: 0, 
                             axisLabel : {
                                 formatter: '{value}'    //控制输出格式
                             }
                         }
                     
            ],
            series : [    //系列（内容）列表                      
                        {
                            name:'人数（人）',
                            type:'bar',    //柱状图表示（生成分数柱状图）
                            symbol:'emptycircle',    //设置折线图中表示每个坐标点的符号；emptycircle：空心圆；emptyrect：空心矩形；circle：实心圆；emptydiamond：菱形                        
                            data:[]        //数据值通过Ajax动态获取
                        }
                                        
            ]
        };
         
         myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
         
         var tems=[];        //温度数组（存放服务器返回的所有温度值）
         var dates=[];        //时间数组
         var examId=$("#examId").val();
         var examClassId=$("#examClassId").val();
         $.ajax({    //使用JQuery内置的Ajax方法
         type : "post",        //post请求方式
         cache:false,
         async : true,        //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
         url : "${ctx}/statistics/personScoreList2",    
         data : {id:examId,examClassId:examClassId},        
         dataType : "json",        //返回数据形式为json
         success : function(result) {
        	//请求成功时执行该函数内容，result即为服务器返回的json对象
        	console.log(result);
             if (result != null && result.length > 0) {
                    for(var i=0;i<result.length;i++){       
                       tems.push(result[i].number);        
                       dates.push(result[i].scoreRange);
                    }
                    myChart.hideLoading();    //隐藏加载动画
                    
                    myChart.setOption({        //载入数据
                        xAxis: {
                            data: dates    //填入X轴数据
                        },
                        series: [    //填入系列（内容）数据
                                      {
                                    // 根据名字对应到相应的系列
                                    name: '人数',
                                    data: tems
                                }
                       ]
                    });
                    
             }
             else {
                 //返回的数据为空时显示提示信息
                 alert("图表请求数据为空，可能服务器暂未录入该学生的成绩，您可以稍后再试！");
                   myChart.hideLoading();
             }
        },
         error : function(errorMsg) {
             //请求失败时执行该函数
             alert("图表请求数据失败，可能是服务器开小差了");
             myChart.hideLoading();        
         } 
    })

    myChart.setOption(option);    //载入图表
         
    </script>
        
	</form>
</body>
</html>