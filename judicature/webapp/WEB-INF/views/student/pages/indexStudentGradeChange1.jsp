<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>

<html>
<head>
	<meta name="decorator" content="student_default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<%@include file="/WEB-INF/views/include/chart.jsp" %>
	<title>统计分析</title>
	
	
</head>
<body>
	<form:form id="searchForm" modelAttribute="exam" action="${ctx}/statistics/studentList" method="post" class="breadcrumb form-search ">
		<input id="studentId" type="hidden" value="${student.id}"/>
		<input id=examClassId name="examClass.id" type="hidden" value="${exam.examClass.id}"/>
		<input id=examId name="id" type="hidden" value="${exam.id}"/>
	</form:form>
	<form action="">
		
    	 <!-- 显示Echarts图表 -->
        <div style="height:220px;min-height:100px;margin:0 auto;" id="main"></div>                        
                    
        <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            title: {    //图表标题
            	// text: '学生排名变化'
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
                data:['名次']
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
                name:'试卷名称',
                boundaryGap:false,
                data : []    //先设置数据值为空，后面用Ajax获取动态数据填入
            },
            yAxis : [    //Y轴
                        {
                            //第一个（左边）Y轴，yAxisIndex为0
                             type : 'value',
                             inverse: true,
                              max : 100,
                              min : 1, 
                             axisLabel : {
                                 formatter: '{value}'    //控制输出格式
                             }
                         }
                     
            ],
            series : [    //系列（内容）列表                      
                        {
                            name:'名次',
                            type:'line',    //折线图表示（生成分数曲线）
                            symbol:'pin',    //设置折线图中表示每个坐标点的符号；emptycircle：空心圆；emptyrect：空心矩形；circle：实心圆；emptydiamond：菱形                        
                            data:[],        //数据值通过Ajax动态获取
                            itemStyle:{
                            	normal:{
                            		color:'rgb(91, 189, 170)'
                            	}
                            }
                        }
                                        
            ]
        };
         
        myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
         var studentId=$("#studentId").val();
         var examClassId=$("#examClassId").val();
         var tems=[];        //温度数组（存放服务器返回的所有温度值）
         var dates=[];        //时间数组
         var totalCount=${countPerson};
         $.ajax({    //使用JQuery内置的Ajax方法
         type : "post",        //post请求方式
         async : true,        //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
         url : "${ctx}/studentNews/personScoreList",
         data: {studentId:studentId,examClassId:examClassId,duration:'1'},
         cache:false,
         dataType : "json",        //返回数据形式为json
         onkeyup: false,
         success : function(result) {
             //请求成功时执行该函数内容，result即为服务器返回的json对象
             if (result != null && result.length > 0) {
                    for(var i=0;i<result.length;i++){       
                       
                       var obj={
         	                    value:result[i].ranking,
         	                    symbol: 'pin',  // 数据级个性化拐点图形
         	                    symbolSize : 15,
         	                    itemStyle : {
         	                    	normal: {
         	                    		label : { 
         	                    			formatter:"第"+result[i].ranking+"名",
	           	                        show: true,
	           	                        textStyle : {
	           	                            fontSize : '14',
	           	                            fontFamily : '微软雅黑',
	           	                            fontWeight : 'bold'
	           	                        }
	           	                    }
         		 				  }
         		                }
               		    };
               		 
                       tems.push(obj);   
               		 
                       dates.push(result[i].title);
                    }
                    myChart.hideLoading();    //隐藏加载动画
                    myChart.setOption({        //载入数据
                        xAxis: {
                            data: dates    //填入X轴数据
                        },
                        yAxis : [    //Y轴
                                     {    type : 'value',
                                          axisTick:{
                                          show:false //是否显示坐标轴刻度
                                         },
                                          max: Math.ceil(totalCount/10)*10,
                                          min: 1, 
                                          splitNumber:Math.abs(Math.ceil(totalCount/10)),
                                      }
                                         
                        ],
                        series: [    //填入系列（内容）数据
                                      {
                                    // 根据名字对应到相应的系列
                                    name: '名次',
                                    data: tems
                                }
                       ]
                    });
                    
             }
             else {
               alertx("未获取到数据!");
                   myChart.hideLoading();
             }
         
        },
         error : function(errorMsg) {
           alertx("未获取到数据!");
             myChart.hideLoading();        
         }
    })
	
    myChart.clear();
    myChart.setOption(option);    //载入图表
         
    </script>
        
	</form>
</body>
</html>