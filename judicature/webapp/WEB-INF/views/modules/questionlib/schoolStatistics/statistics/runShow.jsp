<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数据统计</title>
 
	<meta name="decorator" content="default"/>
  <script src="${pageContext.request.contextPath}/echarts/js/echarts.min.js"></script>
<!--   <script src="/echarts/js/jquery-1.11.3.min.js"></script> -->
 <style type="text/css">
 .echartStyle
 {
 
   float:left; 
   width: 500px;height:300px; 
  
   margin-left: 3px;
   
   BACKGROUND: #ffffff;
   BORDER-RIGHT: 1px outset;
   BORDER-TOP: 1px outset; 
   BORDER-LEFT: 1px outset;
   BORDER-BOTTOM: 1px outset;
      
 }
 
 
  .echartStyle2
 {
   margin-top:20px;
   float:left; 
   width: 750px;height:500px; 
  
   margin-left: 3px;
   
   BACKGROUND: #ffffff;
   BORDER-RIGHT: 1px outset;
   BORDER-TOP: 1px outset; 
   BORDER-LEFT: 1px outset;
   BORDER-BOTTOM: 1px outset;
      
 }
</style>
   
	 
</head>
<body>
  <h1 style="text-align: center; margin-left: -200px; margin-top: 20px; margin-bottom: 20px;"> 运行统计</h1>
    
	 <div style=" width: 100%;" >
	  
		<div id="myChart1" class="echartStyle"></div>
		<div id="myChart2"  class="echartStyle"></div>
		<div id="myChart3"  class="echartStyle"></div>
		
	</div>
	
 
	<div style="clear:Both; border: 1px; width: 100%;" >
	 	<div id="myChart4" class="echartStyle2" ></div>
	    <div id="myChart5" class="echartStyle2" style="margin-left: 3px;"></div>
	</div>
	<div style=" width: 100%;" >
		<div id="myChart6" class="echartStyle"></div>
		<div class="echartStyle" style="text-align: center; line-height: 300px;">
		    注册总人数：${registeredUserTotal}
		</div>
	</div>
	
<c:set value="0" var="inSum" />
<c:set value="0" var="outSum" />
<c:set value="0" var="equSum" />

<script type="text/javascript">

    var editId="";
    var ewidth ="500px";
    var eheight ="400px";
    var resultStr="";
    var resultEdit="";
    // 基于准备好的dom，初始化echarts实例
    var myChart1 = echarts.init(document.getElementById('myChart1'));
    var myChart2 = echarts.init(document.getElementById('myChart2'));
    var myChart3 = echarts.init(document.getElementById('myChart3'));
    var myChart6 = echarts.init(document.getElementById('myChart6'));
    
    var   myChart1Data=[
                        <c:forEach var="item" items="${inStorehouse}" varStatus="index">
		           		    {value:${item.equipmentNumber},name:'${item.equipmentType }'}
		           		  	<c:if test="${not index.last}">,</c:if>
		           		  <c:set value="${inSum + item.equipmentNumber}" var="inSum" />
	          			</c:forEach>
                        ],
    	  myChart2Data=[
                      <c:forEach var="item" items="${outStorehouse}" varStatus="index">
	           		    {value:${item.equipmentNumber},name:'${item.equipmentType }'}
	           		  	<c:if test="${not index.last}">,</c:if>
	           		 	<c:set value="${outSum + item.equipmentNumber }" var="outSum" />
        				</c:forEach>
                     ],
          myChart3Data=[
                        <c:forEach var="item" items="${equipments}" varStatus="index">
		           		    {value:${item.equipmentNumber},name:'${item.equipmentType }'}
		           		  	<c:if test="${not index.last}">,</c:if>
		           			 <c:set value="${equSum + item.equipmentNumber}" var="equSum" />
	          			</c:forEach>
                       ];

    // 指定图表的配置项和数据
  var  option = {
        title : {
            text: '入库总量(总数:${inSum})',
            x:'center'
        },
        tooltip : {
	       	 show:false,
	     	showContent:false,
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: [
                   <c:forEach var="item" items="${inStorehouse}" varStatus="index">
                     '${item.equipmentType }'<c:if test="${not index.last}">,</c:if>
           			</c:forEach>
                  ]
        },
        series : [
            {
                name: '终端类型',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:myChart1Data,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    },
                    normal:{ 
                        label:{ 
                          show: true, 
                          formatter: '{b}\n\n 数量:{c} \n\n 占比:{d}%' 
                        }, 
                        labelLine :{show:true,	
                        	position:'outer',
                        	length:55
                        } 
                    }
                }
            }
        ]
    };
    
  var  option2 = {
	        title : {
	            text: '出库总量(总数:${outSum})',
	            x:'center'
	        },
	        tooltip : {
	        	 show:false,
	        	showContent:false,
	            trigger: 'item',
	            formatter: "{a} <br/>{b} : {c} ({d}%)"
	        },
	        legend: {
	            orient: 'vertical',
	            left: 'left',
	            data: [
						<c:forEach var="item" items="${outStorehouse}" varStatus="index">
						'${item.equipmentType }'<c:if test="${not index.last}">,</c:if>
						</c:forEach>
					 ]
	        },
	        series : [
	                {
	                name: '终端类型',
	                type: 'pie',
	                radius : '55%',
	                center: ['50%', '60%'],
	                data:myChart2Data,
	                itemStyle: {
	                    emphasis: {
	                        shadowBlur: 10,
	                        shadowOffsetX: 0,
	                        shadowColor: 'rgba(0, 0, 0, 0.5)'
	                    },
	                    normal:{ 
	                        label:{ 
	                          show: true, 
	                          formatter: '{b}\n\n 数量:{c} \n\n 占比:{d}%' 
	                        }, 
	                        labelLine :{show:true,
	                        	position:'outer',
	                        	length:55	
	                        } 
	                    }
	                }
	            }
	        ]
	    };
	    
  var  option3 = {
	        title : {
	            text: '在网设备(总数:${equSum})',
	            x:'center'
	        },
	        tooltip : {
	       	    show:false,
	        	showContent:false,
	            trigger: 'item',
	            formatter: "{a} <br/>{b} : {c} ({d}%)"
	        },
	        legend: {
	            orient: 'vertical',
	            left: 'left',
	            data: [
						<c:forEach var="item" items="${equipments}" varStatus="index">
						'${item.equipmentType }'<c:if test="${not index.last}">,</c:if>
						</c:forEach>
	                  ]
	        },
	        series : [
	            {
	                name: '终端类型',
	                type: 'pie',
	                radius : '55%',
	                center: ['50%', '60%'],
	                data:myChart3Data,
	                itemStyle: {
	                    emphasis: {
	                        shadowBlur: 10,
	                        shadowOffsetX: 0,
	                        shadowColor: 'rgba(0, 0, 0, 0.5)'
	                    },
	                    normal:{ 
	                        label:{ 
	                          show: true,
	                          formatter: '{b}\n\n 数量:{c} \n\n 占比:{d}%' 
	                        }, 
	                        labelLine :{
	                        	show:true,
	                        	position:'outer',
	                        	length:55
	                        } 
	                    }
	                }
	            }
	        ]
	    };
 
  var  option6 = {
	        title : {
	            text: '昨日(注册/活跃)用户',
	            x:'center'
	        },
	        tooltip : {
	            trigger: 'item',
	            formatter: "{a} <br/>{b} : {c} ({d}%)"
	        },
	        legend: {
	            orient: 'vertical',
	            left: 'left',
	            data: ['昨日注册用户','昨日活跃用户']
	        },
	        series : [
	            {
	                name: '用户类型',
	                type: 'pie',
	                radius : '55%',
	                center: ['50%', '60%'],
	                data:[
		           		  {value:${onlineUsers.userRegisterNumber},name:'昨日注册用户'},
		           		  {value:${onlineUsers.userOnlineNumber},name:'昨日活跃用户'},
	                ],
	                itemStyle: {
	                    emphasis: {
	                        shadowBlur: 10,
	                        shadowOffsetX: 0,
	                        shadowColor: 'rgba(0, 0, 0, 0.5)'
	                    }
	                }
	            }
	        ]
	    };
	    
  myChart1.setOption(option);
  myChart2.setOption(option2);
  myChart3.setOption(option3);
  myChart6.setOption(option6);
  
  
  
  option4 = {
		    title : {
		        text: '异常监控',
		        subtext: ''
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['异常'  ]
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            dataView : {show: true, readOnly: false},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            data : [  '手机重复绑定','设备重复绑定','多次注册']
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'异常数量',
		            type:'bar',
		            data:['${deviceException.userNumber }','${deviceException.terminalNumber}','${deviceException.registerNumber}'],
		            markPoint : {
		                data : [
		                    {type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }
		        } 
		         
		    ]
		};


  var myChart4 = echarts.init(document.getElementById('myChart4'));
  myChart4.setOption(option4);
  
  option5 = {
		    title: {
		        text: '昨日出入库情况',
		        subtext: ''
		    },
		    tooltip: {
		        trigger: 'axis',
		        axisPointer: {
		            type: 'shadow'
		        }
		    },
		    legend: {
		        data: [
						<c:forEach var="item" items="${yesterdayColumnar}" varStatus="index">
						'${item.equipmentType }'<c:if test="${not index.last}">,</c:if>
						</c:forEach> 
		              ]
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis: {
		        type: 'value',
		        boundaryGap: [0, 0.01]
		    },
		    yAxis: {
		        type: 'category',
		        data: [ '昨日出库量','昨日入库量']
		    },
		    series: [
		             <c:forEach var="item" items="${yesterdayColumnar}" varStatus="index">
	           		    {name:'${item.equipmentType }',type:'bar',data:[${item.number},${item.equipmentNumber}]}
	           		  	<c:if test="${not index.last}">,</c:if>
       				 </c:forEach>
		    ]
		};
  
  var myChart5 = echarts.init(document.getElementById('myChart5'));
  myChart5.setOption(option5);
</script>
</body>
</html>