<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	<title>题目调整列表</title>
	 <%@include file="/WEB-INF/views/teacher/include/validation.jsp" %>
	<%@ include file="/WEB-INF/views/teacher/include/date.jsp" %>
	<style type="text/css">
	   .form-actions{margin:0px; border-top: 0px; text-align: right;}
	</style>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
	  	 $("#contentTable").treeTable({expandLevel : 2}).show();
		$("#updateSortForm").validate({
	    	onfocusin:false,
	    	onfocusout:false,
	    	onkeyup:false,
			submitHandler: function(form){
				var error=true;
				var arr=[];
				 $("input[name='sorts']").each(function(i,v){
					var tr_pId=$(this).closest("tr").attr("pId");
					if(tr_pId!==0){
						if(arr[tr_pId]){
							var list=arr[tr_pId];
							list.push(v.value);
						}else{
							var list=[];
							list.push(v.value);
							arr[tr_pId]=list;
							
						}
					}
				}); 
				$("input[name='sorts']").each(function(i,e){
					var  label = $("<label>").attr("for",e.id).addClass("error").html("只能输入[>=0]的数字");
					var tag=$("label[for="+e.id+"]");
					!!tag&&tag.remove();
					if(/^[0-9]*$/.test(jQuery.trim(e.value))<= 0){
						label.insertAfter(e);
						error=false;
					}
				});
				var isRepeat=function(arr){

					var hash = {};
					for(var i in arr) {
					  if(hash[arr[i]])
						return true;
					   hash[arr[i]] = true;
					}
					return false;
			    }
				if(error){
			       var key;
					$.each(arr,function(k,value){
						console.log(k);
						if(isRepeat(value)){
							error=false;
							key=k;
							return false;
						}
					});
					if(error){
					   form.submit();
					}else{
						var name=$("#"+key).find("td:eq(1)").text();
						top.$.jBox.tip(name+"排序重复！","error",{persistent:true,opacity:0});
					}
				}else{
					top.$.jBox.tip("输入有误请检查","error",{persistent:true,opacity:0});
				}
			}
		}); 
	}); 
		function saveButton(){ 
			var examId= "${examId}"//$("#examId").val();
			window.location.href ='${ctx}/examinationHomeWork/examHomeWorkTitle?examId='+examId; 
		}
	</script>
</head>
<body>

     <form:form id="searchForm"  action="${ctx}/examinationHomeWork/adjustExamList?examdetailId=${examdetailId }&examId=${examId}" method="post" class="breadcrumb form-search ">
		 <input type="hidden" id="examId" name="examId" value="${examId}" />
		<label>出题频率：</label>
		  <input name="beginTime"     type="text"  readonly="readonly" maxlength="20"  class="input-mini Wdate"
		         value="<fmt:formatDate value="${beginTime}" pattern="yyyy-MM-dd"/>" 
		         onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"  readonly/>
		  ~<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/>" 
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"  readonly/>
		<label>出题类型：</label>
		
	    <select name="examType">
            <option value="">--请选择--</option>
            <option value="2,5"  ${examType eq "2,5" ? "selected='selected'":"" } >组卷和在线考试出题</option>
            <option value="1,3,4"  ${examType eq "1,3,4" ? "selected='selected'":"" }>日常评测和练习出题</option>
	    </select>
	    
		<input id="btnSubmit" class="btn tbtn-primary" type="submit" value="查询"/>
		<input id="saveExam" class="btn tbtn-primary" type="button" value="返回" onclick="saveButton();"/>
	</form:form>
  	<form id="updateSortForm" method="post" action="${ctx}/examinationHomeWork/updateSort?examdetailId=${examdetailId}&examId=${examId}" >
	<table id="contentTable" class="table table-striped table-bordered table-condensed"  style="margin-top: 20px;">
		<thead>
			<tr>
				<th>编号</th>
				<th>题干</th>
				<th>出题频率</th>
				<th>排序</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		 <%-- <c:forEach items="${list}" var="item" varStatus="index">
			<tr>
				<td>${(index.index+1)}</td>
				<td>${item.question.title}</td>
				<td>
				    ${item.count}
				</td>
				 <td>
				 	<input type="hidden" name="ids" value="${item.question.id}"/>
					<input id="sorts_${item.question.id}" name="sorts" type="text" value="${item.sort}"   style="width:50px;margin:0;padding:0;text-align:center;">
				 </td>
				<td>
				    <a href="javascript:void(0);"  class="btn" id="${item.question.id}">替换</a>
				</td>
			</tr>
		</c:forEach> --%>
		 <c:forEach items="${dataMap}" var="itemKey" varStatus="index">
			<tr id="${itemKey.key}" pId="0">
				<td style="text-align: lef;">${(index.index+1)}</td>
				<td colspan="5" style="text-align: left;"> ${fns:getDictLabel(itemKey.key,'question_type','')} </td>
			</tr>
			
			<c:forEach items="${itemKey.value}" var="item" varStatus="index">
			<tr  id="${item.question.id }" pId="${itemKey.key}" >
			    <td></td>
				<td style="text-align:right;">${(index.index+1)}</td>
				<td>${item.question.title}</td>
				<td>
				    ${item.count}
				</td>
				 <td>
				 	<input type="hidden" name="ids" value="${item.question.id}"/>
					<input id="sorts_${item.question.id}" name="sorts" type="text" value="${item.sort}"   style="width:50px;margin:0;padding:0;text-align:center;">
				 </td>
				<td>
				    <a href="javascript:void(0);"  class="btn" id="${item.question.id}">替换</a>
				</td>
			</tr>
		</c:forEach>
			
		 </c:forEach>
		</tbody>
	</table>
	
	<div class="form-actions pagination-left">
		<input id="btnSubmit" class="btn tbtn-primary" type="submit" value="保存排序" />
	</div>
</form>
	<script type="text/javascript">
		(function(){
			$("#contentTable tbody tr").find("td:last-child a").click(function(){
				  var id=$(this).attr("id");
				  var examdetailId="${examdetailId}";
				  var examId=$("#examId").val();
				  top.$.jBox("iframe:${ctx}/examination/adjustExamInfo?questionId="+id+"&examdetailId="+examdetailId+"&examId="+examId,{
						title:"替换题目",
						width: 800,
					    height: 350,
						/* buttons:{"关闭":true},  */
						buttons:{}, 
					    bottomText:"",
					    loaded:function(h){
 							$(".jbox-content", top.document).css("overflow-y","hidden"); 
					    },submit:function(b,j,f){
							var  obj = j.children(0).contents();
							if(b>0){
								/* obj.find("#btnAdd").trigger("click"); */
								window.location.href ='${ctx}/examinationHomeWork/adjustExamList?examdetailId='+examdetailId+"&examId="+examId; 
								return false;
							}
						},closed:function(){
							/* $("#searchForm").submit(); */
							window.location.href ='${ctx}/examinationHomeWork/adjustExamList?examdetailId='+examdetailId+"&examId="+examId; 
						}
					});  
			});
		})();
	</script>
</body>
</html>