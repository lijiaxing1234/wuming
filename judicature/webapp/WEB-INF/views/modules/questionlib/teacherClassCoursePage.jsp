<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>老师分配班级课程</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<div id="giveCCVTTBox" style="width: 100%;height: 100%;position: absolute; top: 0;display: none;">
		<div style="width: 520px;min-height: 250px;position: absolute;top: 80px;left:15%;padding: 15px 4px 4px;border: 1px solid #ccc;text-align: center;background: #fff;">
			<fieldset>
			    <legend>第一步</legend>
				<span style="display: block;float: left;margin-right: 5px;margin-top: 4px;">请选择课程</span>
				<select id="courseSelect" style="float:left;width: 120px; margin-bottom: 8px;" onchange="changeVersionOption()">
				</select>
				<span style="display: block;float: left;margin-right: 5px;margin-top: 4px; margin-left: 8px;">请选择课程版本</span>
				<select id="versionSelect" style="float:left;width: 120px; margin-bottom: 8px;" onchange="showClassTable()">
				  <option value="">请选择课程版本</option>
				</select>
			</fieldset>
			<hr/>
			<fieldset>
			    <legend>第二步</legend>
				   <span style="display: block;float: left;margin-right: 5px;margin-top: 4px; margin-left: 8px;">请选择专业</span>
			      <select id="specialtyTitle" name="specialtyTitle" style="float:left;width: 120px; margin-bottom: 8px;" onchange="showClassTable()">
		             <option value="">请选择专业</option>
		             <c:forEach  var="specTitle" items="${specTitles}">
		                <option value="${specTitle.specialtyTitle}"   ${specTitle.specialtyTitle eq student.schoolClass.specialtyTitle ? 'selected="selected"' : '' } >${specTitle.specialtyTitle}</option>
		             </c:forEach>
			      </select>
		    </fieldset>
			<table id="classTable" class="table table-striped table-bordered table-condensed">
			</table>
			<button onclick="yesDivBox()">确定</button>
			<button onclick="closeDivBox()">取消</button>
		</div>
	</div>
	
	<sys:message content="${message}"/>
	<input  style="margin-left: 10px; "  id="giveCCVTT" class="btn btn-primary" type="button" value="分配班级课程" onclick="giveCCVTT()" />
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<input type="hidden" id="teacherId" value="${teacherId }"/>
		<thead>
			<tr>
			  	<th>教师姓名</th>
				<th>课程名称</th>
				<th>班级名称</th>
				<shiro:hasPermission name="questionlib:gccvtt:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${ccListVoList}" var="vo">
			<tr>
			    <td>
					${vo.teacherName }
				</td>
				<td>
					${vo.courseName }
				</td>
				<td>
					${vo.className }
				</td><td>
				<shiro:hasPermission name="questionlib:teacher:deleteClassCourse">
					<a href="${ctx}/questionlib/gccvtt/deleteClassCourse?voId=${vo.id}&&teacherId=${vo.teacherId}" onclick="return confirmx('确认要删除该数据吗？', this.href)">删除该分配方案</a>
				</shiro:hasPermission></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
<!-- 	<div ><input  style="margin-left: 350px; "  id="closeBtnId" class="btn btn-primary" type="button" value="关闭" onclick="closeDiv()" /> </div> -->
	
	<script type="text/javascript">
		var teacherId = $("#teacherId").val();
		function giveCCVTT(){
			$("#courseSelect").html("");
			$.ajax({
				url:"${ctx}/questionlib/gccvtt/getAllCourse",
				data:{ r:Math.random()},
				type:"post",
				dataType:'html',
				success:function(data){
					$("#giveCCVTTBox").show();
					$("#courseSelect").append(data).select2("val",'');
				}
			});
		}
		
		function closeDivBox(){
			$("#giveCCVTTBox").hide();
			$("#classTable").html("");	
			$("#specialtyTitle").select2("val",'');
			
		}
		
		
		function yesDivBox(){
			var courseId = $("#courseSelect option:selected").val();
			var classIds = [];
			$('input[name="classId"]:checked').each(function(){
				classIds.push($(this).val());
			});
			var versionId = $("#versionSelect option:selected").val();
			
			if(courseId.length == 0){
				alertx("请选择课程");
				return;
			}
			if(versionId.length == 0){
				alertx("请选择版本");
				return;
			}
			if(classIds.length == 0){
				alertx("请选择班级");
				return;
			}
			
			window.location.href = "${ctx}/questionlib/gccvtt/giveCCTT?courseId="+courseId+"&&classIds="+classIds+"&&teacherId="+teacherId+"&&versionId="+versionId;
		}
		
		function changeVersion(){
			var checked_value = [];
			$('input[name="aa"]:checked').each(function(){
				checked_value.push($(this).val());
			});
			
			
			$.ajax({
				url:"${ctx}/questionlib/gccvtt/getAllVersionByCourseId?courseId="+checked_value,
				type:"post",
				data:{ r:Math.random()},
				success:function(data){
					$("#versionTable").append(data).select2("val",'');
				}
			})
		}
		
		function changeVersionOption(){
			$("#versionSelect").html("");
			//根据课程id获取版本
			var courseId = $("#courseSelect").val();
			
			$.ajax({
				url:"${ctx}/questionlib/sCourseVersion/getVersionsByCourseIdAndSchoolId",
				type:"post",
				 data:{ r:Math.random()},
				data:{courseId:courseId},
				dataType:"html",
				success:function (data){
					$("#versionSelect").append(data).select2("val",'');;
				}
			})
		}
		
		function showClassTable(){
			//select的onchange事件
			$("#classTable").html("");			
		    var spectitles=$("#specialtyTitle").select2("val");
			var courseId = $("#courseSelect").val();
			var versionId = $("#versionSelect").val();
			
			if(courseId&&versionId){
				var schoolClassIds =[]; 
				$('input[name="test"]:checked').each(function(){ 
					schoolClassIds.push($(this).val()); 
				}); 
				
				 
				$.ajax({
					url:"${ctx}/questionlib/gccvtt/getNotGiveCourseClass",
					type:"post",
					data:{courseId:courseId,teacherId:teacherId,versionId:versionId,spectitles:spectitles,r:Math.random()},
					dataType:'html',
					success:function(data){
						$("#classTable").append(data);
					}
				})
			}else{
				alertx("请先选择课程和版本后重试.");
			}
		}
		
		function closeDiv(){
			//$('#showHideDiv', window.parent.document).hide();
			
			   
 		    try{
 		    	top.$.jBox.closeTip();
 		    	top.$.jBox.close(true);
 		    }catch(e){
 		    	
 		    }
		}
		
	</script>
</body>
</html>