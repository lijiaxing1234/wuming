package com.thinkgem.jeesite.modules.teacher.service;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.test.SpringTransactionalContextTests;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.teacher.entity.ExamDetailQuestionInfo;
import com.thinkgem.jeesite.modules.teacher.entity.Examination;

public class TestExaminationService extends SpringTransactionalContextTests {

	
	@Autowired
	ExaminationService examService;
	
	
	
	//排序组合
	@Test
	public void  testSet(){
		
		/*基本思路：求全组合，则假设原有元素n个，则最终组合结果是2^n个。原因是：
         * 用位操作方法：假设元素原本有：a,b,c三个，则1表示取该元素，0表示不取。故去a则是001，取ab则是011.
         * 所以一共三位，每个位上有两个选择0,1.所以是2^n个结果。
         * 这些结果的位图值都是0,1,2....2^n。所以可以类似全真表一样，从值0到值2^n依次输出结果：即：
         * 000,001,010,011,100,101,110,111 。对应输出组合结果为：
        空,a, b ,ab,c,ac,bc,abc.
        这个输出顺序刚好跟数字0~2^n结果递增顺序一样
        取法的二进制数其实就是从0到2^n-1的十进制数
         * ******************************************************************
         * * 
         * */
        String[] str = {"a" , "b" ,"c"};
        int n = str.length;                                  //元素个数。
        //求出位图全组合的结果个数：2^n
        int nbit = 1<<n;                                     // “<<” 表示 左移:各二进位全部左移若干位，高位丢弃，低位补0。:即求出2^n=2Bit。
        System.out.println("全组合结果个数为："+nbit);
        
        for(int i=0 ;i<nbit ; i++) {                        //结果有nbit个。输出结果从数字小到大输出：即输出0,1,2,3,....2^n。
            System.out.print("组合数值  "+i + " 对应编码为： ");
            for(int j=0; j<n ; j++) {                        //每个数二进制最多可以左移n次，即遍历完所有可能的变化新二进制数值了
                int tmp = 1<<j ;        
                if((tmp & i)!=0) {                            //& 表示与。两个位都为1时，结果才为1
                    System.out.print(str[j]);
                }
            }
            System.out.println();
        }
		
		
		
	}
	
	@Test
	public void testMapRemove(){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("a", 1);
		map.put("b", 2);
		map.put("c", 3);
		map.put("d", 4);
		Iterator<Map.Entry<String,Object>> iterator= map.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<String,Object> temp= iterator.next();
			if(temp.getKey().equals("b")){
				iterator.remove();
			}
		}
		
		for(Map.Entry<String,Object> item:map.entrySet()){
			System.out.println(item.getKey()+"   "+item.getValue());
		}
		
	}
	@Test
	public void testList(){
		List<String> list=new ArrayList<String>(Arrays.asList("a b c d".split(" ")));
		
		Iterator<String> it=list.iterator();
	    while(it.hasNext()){
	       String key= 	it.next();
	       if(key.equals("c")){
	    	   it.remove();
	       }
	    }
		for(String s:list){
			System.out.println(s);
		}
	}
	
	
	
	
	
	@Test
	public void testExamination(){
		
	 Examination exam=examService.getExam(new Examination("4e38372c657c4d34be9378404282fc8a"));
	//自动出题
    examService.examination(exam);  
	 
	/* //手动出题
	 List<ExamKnowledgeQuestion> data=examService.findExamKnowledgeQuestionList(exam.getId());
	  for(ExamKnowledgeQuestion aa:data){
		  System.out.print(aa.getExam().getId() +"    ");
		  
		  System.out.print(aa.getCourseKnowledge().getId()+"    ");
		  System.out.print(aa.getCourseKnowledge().getTitle()+"    ");
		  System.out.println(aa.getQuestion());
		  for(VersionQuestion v: aa.getQuestions()){
			  System.out.print(v.getQuesType()+"~");
			  System.out.print(v.getQuesLevel()+"~");
			  System.out.print(v.getCount()+"~");
			  System.out.println(v.getQuesPoint()+"~");
			  
			  
		  }
	  }*/
	}
	
	
	static String[] chinaNumber = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九","十","十一","十二","十三","十四","十五","十六","十七","十八","十九","二十"};
	
	
	@Test
    public void  teatExamQuestion2Json(){
		
		
		
		Map<String, Object> jsonRootMap =Maps.newHashMap();
		
		List<Map<String, Object>> jsonQuesCateList = Lists.newArrayList();
		
		Map<ExamDetailQuestionInfo,List<VersionQuestion>> dataMaps=examService.findQuestionsByExamDetailId("79fe62abc88a416ba0b2734b195b11ef");
		Map<String,Double> quesScoreMap=examService.getQuesScore();
    	String  strQuesTitle1="{0}、{1} (每题{2}分,共{3}分)"; 
    	String  strQuesTitle2="{0}、{1} (共{2}分)"; 
    	
    	int i=1;
    	for(Map.Entry<ExamDetailQuestionInfo,List<VersionQuestion>> entry:dataMaps.entrySet()){
    		
    		Map<String, Object> jsonQuesCateMap =Maps.newHashMap();
    		
    		
    		ExamDetailQuestionInfo info=entry.getKey();
    		
    		String quesType=info.getQuesTypes();
    		
    		Double singleScore=quesScoreMap.get(quesType);
    		
    		String title=DictUtils.getDictLabel(quesType, "question_type", "");
    		
    		
    		String quesCateTitle="";
    		
    		if(singleScore !=null){
    			quesCateTitle=MessageFormat.format(strQuesTitle1, chinaNumber[i],title,info.getQuesScore(),info.getTotalScore());
    		}else{
    			quesCateTitle=MessageFormat.format(strQuesTitle2, chinaNumber[i],title,info.getTotalScore());
    		}
    		
    		System.out.println(quesCateTitle);
    		
    		jsonQuesCateMap.put("quesCateTitle", quesCateTitle);
    		
    		List<VersionQuestion>  list=entry.getValue();
    		
    		
    		
    		List<Map<String, Object>> jsonQuesList = Lists.newArrayList();
    		
    		int j=1;
    		for(VersionQuestion vq:list){
    			
    			Map<String, Object> jsonQuesMap =Maps.newHashMap();
    			
    			String quesTitle="";
    			
    			if(singleScore !=null){
    				//quesTitle=j+"、"+StringEscapeUtils.escapeHtml4(vq.getTitle());
    				quesTitle=j+"、"+StringEscapeUtils.escapeHtml4(vq.getTitle());
    			}else{
    				//quesTitle=j+"、"+StringEscapeUtils.escapeHtml4(vq.getTitle())+"("+vq.getQuesPoint()+")";
    				quesTitle=j+"、"+StringEscapeUtils.escapeHtml4(vq.getTitle())+"("+vq.getQuesPoint()+")";
    				
    				//quesTitle=j+"、"+HtmlUtils.htmlEscape(vq.getTitle())+"("+vq.getQuesPoint()+")";
    			}
    			System.out.println(quesTitle);
    			
    			jsonQuesMap.put("quesTitle",quesTitle);
    			
    			 List< Map<String, Object>> choiceList=Lists.newArrayList();
    			 for(int k=0,len=9;k<len;k++){
    				 Map<String, Object> jsonQuesChoiceMap =Maps.newHashMap();
    				 
    				 Object obj=null;
    				 try {
						obj=PropertyUtils.getProperty(vq,"choice"+k);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
    				 
					if(obj !=null){
						String choiceTitle=(char)(k+65)+"、"+obj.toString();
						
						System.out.println(choiceTitle);
						
						jsonQuesChoiceMap.put("choice", choiceTitle);
						
						choiceList.add(jsonQuesChoiceMap);
						
						continue;
					}
					break;
    			 }
    
    			 jsonQuesMap.put("choiceList",choiceList);
    			 jsonQuesList.add(jsonQuesMap);
    			 
    			j++;
    		}
    		
    		jsonQuesCateMap.put("quesList", jsonQuesList);
    		jsonQuesCateList.add(jsonQuesCateMap);
    		i++;
    	}
    	
    	
    	jsonRootMap.put("mainTitle", "主标题");
    	jsonRootMap.put("subTitle", "副标题");
    	jsonRootMap.put("quesCateList", jsonQuesCateList);
    	
    	System.out.println(JsonMapper.toJsonString(jsonRootMap));
    	
    }
	
	
	@Test
    public void  teatExamQuestionAnswer2Json(){
		
		
		
		Map<String, Object> jsonRootMap =Maps.newHashMap();
		
		List<Map<String, Object>> jsonQuesCateList = Lists.newArrayList();
		
		Map<ExamDetailQuestionInfo,List<VersionQuestion>> dataMaps=examService.findQuestionsByExamDetailId("79fe62abc88a416ba0b2734b195b11ef");
		Map<String,Double> quesScoreMap=examService.getQuesScore();
    /*	String  strQuesTitle1="{0}、{1} (每题{2}分,共{3}分)"; 
    	String  strQuesTitle2="{0}、{1} (共{2}分)"; */
    	String  strQuesTitle3="{0}、{1}"; 
    	
    	int i=1;
    	for(Map.Entry<ExamDetailQuestionInfo,List<VersionQuestion>> entry:dataMaps.entrySet()){
    		
    		Map<String, Object> jsonQuesCateMap =Maps.newHashMap();
    		
    		
    		ExamDetailQuestionInfo info=entry.getKey();
    		
    		String quesType=info.getQuesTypes();
    		
    		//Double singleScore=quesScoreMap.get(quesType);
    		
    		String title=DictUtils.getDictLabel(quesType, "question_type", "");
    		
    		
    		String quesCateTitle=MessageFormat.format(strQuesTitle3, chinaNumber[i],title);
    		
    		/*if(singleScore !=null){
    			quesCateTitle=MessageFormat.format(strQuesTitle1, chinaNumber[i],title,info.getQuesScore(),info.getTotalScore());
    		}else{
    			quesCateTitle=MessageFormat.format(strQuesTitle2, chinaNumber[i],title,info.getTotalScore());
    		}*/
    		
    		
    		
    	    System.out.println(quesCateTitle);
    		
    		jsonQuesCateMap.put("quesCateTitle", quesCateTitle);
    		
    		List<VersionQuestion>  list=entry.getValue();
    		
    		List<Map<String, Object>> jsonQuesList = Lists.newArrayList();
    		
    		int j=1;
    		for(VersionQuestion vq:list){
    			
    			Map<String, Object> jsonQuesMap =Maps.newHashMap();
    			
    			/*String quesTitle="";
    			
    			if(singleScore !=null){
    				//quesTitle=j+"、"+StringEscapeUtils.escapeHtml4(vq.getTitle());
    				quesTitle=j+"、"+StringEscapeUtils.escapeHtml4(vq.getTitle());
    			}else{
    				//quesTitle=j+"、"+StringEscapeUtils.escapeHtml4(vq.getTitle())+"("+vq.getQuesPoint()+")";
    				quesTitle=j+"、"+StringEscapeUtils.escapeHtml4(vq.getTitle())+"("+vq.getQuesPoint()+")";
    				
    				//quesTitle=j+"、"+HtmlUtils.htmlEscape(vq.getTitle())+"("+vq.getQuesPoint()+")";
    			}
    			System.out.println(quesTitle);
    			
    			jsonQuesMap.put("quesTitle",quesTitle);*/
    			
    			 //List< Map<String, Object>> choiceList=Lists.newArrayList();
    			  List<String> answerList=Lists.newArrayList();
    			 for(int k=0,len=9;k<len;k++){
    				 Map<String, Object> jsonQuesChoiceMap =Maps.newHashMap();
    				 
    				 Object obj=null;
    				 try {
						obj=PropertyUtils.getProperty(vq,"answer"+k);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
    				 
					if(obj !=null){
						//String choiceTitle=(char)(k+65)+"、"+obj.toString();
						//String choiceTitle=obj.toString();
						
						//System.out.println(obj.toString());
						answerList.add(StringEscapeUtils.escapeHtml4(obj.toString()));
						//jsonQuesChoiceMap.put("answer", choiceTitle);
						
						//choiceList.add(jsonQuesChoiceMap);
						
						continue;
					}
					break;
    			 }
                    
    			 jsonQuesMap.put("quesTitle",j+"、"+StringUtils.join(answerList,""));
    			 System.out.println(j+"、"+StringUtils.join(answerList,""));
    			 //jsonQuesMap.put("choiceList",choiceList);
    			  
    			 jsonQuesList.add(jsonQuesMap);
    			 
    			j++;
    		}
    		
    		jsonQuesCateMap.put("quesList", jsonQuesList);
    		jsonQuesCateList.add(jsonQuesCateMap);
    		i++;
    	}
    	
    	
    	jsonRootMap.put("mainTitle", "主标题");
    	jsonRootMap.put("subTitle", "副标题");
    	jsonRootMap.put("quesCateList", jsonQuesCateList);
    	
    	System.out.println(JsonMapper.toJsonString(jsonRootMap));
    	
    }
	
	
	
	
	
	
	@Test
	public void testCalc(){
		
		for(int i=1,len=100;i<len;i++){
			System.out.println(numberToChineseNumber(i+""));
		}

		
		
	}
	
	  private String numberToChineseNumber(String number) {
		  
	        String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
	        String[] s2 = { "","十", "百", "千", "万", "十万", "百万", "千万", "亿", "十亿", "百亿", "千亿" };
           
	        char[] charArr=number.toCharArray();
	        
	        StringBuilder result=new StringBuilder();
	        
	        for(int i=0,len=charArr.length;i<len;i++){
	        	 Character value=Character.valueOf(charArr[i]);
	        	 int index=Integer.valueOf(value.toString());
	        	 
	        	 if(i>0){
	        	   result.append(s2[i]);
	        	   result.append(s1[index]);
	        	 }else{
	        	   result.append(s1[index]);
	        	 }
	        	 
	        	
	        }
	        return result.toString();
	 }
	
}
