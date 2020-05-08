
(function($){
	
	$.utils={
		 //绑定字典内容到指定的Select控件
		 BindSelect:function (tagName, url,selectId) {
		    var control = $('#' + tagName);
		    
		    //设置Select2的处理
		    if ($.isArray(url)){ 
		    	control.empty();
		    	control.append("<option value=''>请选择</option>");
		    	$.each(url,function(i,item){
		    		if(selectId&&selectId==item.id){
		    			control.append("<option value='" + item.id + "'  selected='selected'>&nbsp;" + item.name + "</option>");
		    		}else{
		    		    control.append("<option value='" + item.id + "'>" + item.name + "</option>");
		    		}
		    	});
		    }else{
		    	  //绑定Ajax的内容
			    $.getJSON(ctx+url,{r:Math.random()},function (data) {
			        control.empty();
			        control.append("<option value=''>请选择</option>");
			        $.each(data, function (i, item) {
			        	if(selectId&&selectId==item.id){
			        		control.append("<option value='" + item.id + "' selected='selected'>&nbsp;" + item.name + "</option>");
			    		}else{
			    		    control.append("<option value='" + item.id + "'>" + item.name + "</option>");
			    		}
			        });
			    });
		    }
		    
		    control.select2({
		        allowClear: true,
		        escapeMarkup: function (m) {
		            return m;
		        }
		    }).select2('val','');
		    return control;
		}
		
	};
	

})(jQuery);