<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>成绩列表</title>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<%@include file="/WEB-INF/views/include/chart.jsp" %>
	<script type="text/javascript">
		  function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#typeForm").submit();
	    	return false;
	    } 
		function submitButton(){
			var type=$("#type").val();
			if(type=="no"||type==""){
				/* $("#typeForm").attr("action","${ctx}/examScore/examScoreList");
				$("#typeForm").submit(); */
				window.location.href ='${ctx}/testPaper/testPaperList'; 
			}
			if(type=="Online"){
				/* $("#typeForm").attr("action","${ctx}/examScore/examOnLineScoreList");
				$("#typeForm").submit(); */
				window.location.href ='${ctx}/testPaper/testPaperOnLineList'; 
			}
		}
		function exportDOC(){
			top.$.jBox.confirm("确认要导出该班级下的所有学生吗？","系统提示",function(v,h,f){
				 var examId=$("#examId").val();
				 var examClassId=$("#examClassId").val();
				 var examDetailId=$("#detailId").val();
			     var examDetailType=$("#detailIdType").val();
				if(v=="ok"){
					if(examDetailId==null||examDetailId==""){
						alertx("请先选择考试类型");
					}else{
						$("#examClassDetailForm").attr("action","${ctx}/examScore/export?examId="+examId+"&examClassId="+examClassId+"&examDetailId="+examDetailId+"&examDetailType="+examDetailType);
						$("#examClassDetailForm").submit();
					}
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		}
	</script>
</head>
<body>
	<%-- <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/examScore/examOnLineScore?examId=${exam.id}&examClassId=${school.id}">考试成绩列表</a></li>
		<li><a href="${ctx}/statistics/personList2?id=${exam.id}&classId=${school.id}">考试成绩统计分析</a></li>
	</ul>  --%>
	<sys:message content="${message}"/>
    <form:form id="typeForm" class="breadcrumb form-search" action="${ctx}/examScore/examOnLineScore?examId=${exam.id}&examClassId=${school.id}">
   		试卷名称：${exam.title}&nbsp;&nbsp;&nbsp;
		班级：${school.title}&nbsp;&nbsp;&nbsp;
   		<input id="pageNo" name="pageNo" type="hidden" value="${examList.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${examList.pageSize}"/>
		<input id="type" name="type" type="hidden" value="${type}"/>
		<input id="detailIdType" type="hidden" value="${examDetailType}"/> 
		<input id="detailId" type="hidden" value="${detailId}"/>
		<input id="studentId" type="hidden" value="${student.id}"/>
		<input id=examClassId type="hidden" value="${school.id}"/>
		<input id=examId type="hidden" value="${exam.id}"/>
		
		<input type="hidden" id="examId" value="${exam.id}"/>
    	<input type="hidden" id="examClassId" value="${school.id}"/>
		试卷类型：
			<c:if test="${AB eq '2'}">
				<input type="radio" maxlength="50" onclick="judgeRadioClicked();" class="required" name="examDetailType" ${examDetailType eq 'A' ? 'checked="checked"' :'' } value="A"  id="examDetailType_A" />A卷
				<input type="radio" maxlength="50" onclick="judgeRadioClicked();" class="required" name="examDetailType" ${examDetailType eq 'B' ? 'checked="checked"' :'' }  value="B"  id="examDetailType_B"  />B卷
			</c:if>
			<c:if test="${AB eq null}">
				<input type="radio" maxlength="50" onclick="judgeRadioClicked();" class="required" name="examDetailType" ${examDetailType eq 'A' ? 'checked="checked"' :'' } value="A"  id="examDetailType_A" />A卷
			</c:if>
<%-- 		 <li><label>试卷类型：</label><form:radiobuttons onclick="$('#searchForm').submit();" path="examDetailType" items="${fns:getDictList('questionlib_del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
 --%>	<c:if test="${detailId eq null}">
			 <span class="help-inline"><font color="red">请确认正确的试卷类型</font> </span>
 		</c:if>
 		<input id="btnTestPaperSubmit" class="btn tbtn-primary" type="button" onclick="submitButton();" value="返回"/>
 		<!-- <input class="btn btn-primary" type="button" value="返回" onclick="history.go(-1);">  -->
		<input id="btnExport" class="btn tbtn-primary" type="button" onclick="exportDOC();" value="成绩导出"/>
		
	</form:form>

	<%-- <sys:message content="${message}"/> --%>
	<form id="examClassDetailForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th  hidden="true" style="text-align: center;">ID</th>
					<th style="text-align: center;">姓名</th>
					<th style="text-align: center;">学号</th>
					<th style="text-align: center;">联系电话</th>
					<th style="text-align: center;">未交作业次数</th>
					<th style="text-align: center;">未随堂测次数</th>
					<th style="text-align: center;">总成绩</th>
					<c:if test="${exam.examType eq '5'}">
						<th style="text-align: center;">线上成绩</th>
					</c:if>
				</tr>
			</thead>
			<tbody>
			 <c:forEach items="${examList.list}" var="list">
				<tr>
					<td  hidden="true" style="text-align: center;">${list.studentId}</td>
					<td style="text-align: center;">${list.name}</td>
					<td style="text-align: center;">${list.stdCode}</td>
					<td style="text-align: center;">${list.stdPhone}</td>
					<td style="text-align: center;">${list.unSubmitCount}</td>
					<td style="text-align: center;">${list.unClassWork}</td>
					<td style="text-align: center;">${list.score}</td>
					<c:if test="${exam.examType eq '5'}">
						<td style="text-align: center;">
							<c:if test="${ empty list.onLineScore}">0.0</c:if>
							<c:if test="${ !empty list.onLineScore}">${list.onLineScore}</c:if>
						</td>
					</c:if>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	 </form>
	  <div class="pagination">${examList}</div>
	  <form action="">
    	 <!-- 显示Echarts图表 -->
       <div>
       		<div style="margin-left: 300px;">
		       		<font style="font-weight: bold;">试卷总分：</font>${totalScore}分
					<font style="font-weight: bold;">全班最高分：</font>${scoreMax}分
					<font style="font-weight: bold;">全班最低分：</font>${scoreMin}分
					<font style="font-weight: bold;">全班平均分：</font>${avgScore}分
       		</div>
       		 	<div style="height:410px;min-height:100px;margin:0 auto;" id="main">
       		</div>
        </div>
	</form>
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
           /*  legend: {    //图表上方的类别显示               
                show:true,
                data:['人数（人）']
            }, */
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
                    console.log(dates);
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
</body>
</html>