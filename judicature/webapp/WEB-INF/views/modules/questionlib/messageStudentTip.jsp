<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">
 (function(){
	  try{
	    top.$.jBox.closeTip();
		parent.$.jBox.closeTip();
	    parent.alertx("保存成功",function(){
	 	  parent.$.jBox.close(true);
		});
	 }catch(e){
	 }
 })();
</script>
