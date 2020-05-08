<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">
 (function(){
	  try{
	    top.$.jBox.closeTip();
		parent.$.jBox.closeTip();
	    parent.alertx("这位同学已收到您的提醒,谢谢！",function(){
	 	  parent.$.jBox.close(true);
		});
	 }catch(e){
	 }
 })();
</script>
