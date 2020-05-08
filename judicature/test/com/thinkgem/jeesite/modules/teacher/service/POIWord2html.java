package com.thinkgem.jeesite.modules.teacher.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class POIWord2html {

  static String dirPath="C:/Users/Administrator/Desktop/22221111";
  static String wordHtml="newjisuanti.htm";
  public static void  main(String[] args){
	  
	//  System.out.println(fileReader(new File(dirPath,wordHtml),"GB2312"));
	  
	  String source=fileReader(new File(dirPath,wordHtml),"GB2312");
	  
      Document doc = Jsoup.parse(source);
      
      Elements trs = doc.select("table").select("tr");
      for(int i = 0;i<trs.size();i++){
          Elements tds = trs.get(i).select("td");
          for(int j = 0;j<tds.size();j++){
        	  Element el= tds.get(j);
        	  Elements ps= el.select("p").select("span");
        	 
        	  
        	 /* String ext=el.select("p").select("span").text();
        	  if(el.select(""))
              System.out.println(ext);*/
          }
      }
	  
  }
  
  
/*  public static String resolveString(String source){
	  
	  
	  
  }*/

  
  
  
  public static  String  fileReader( File file,String charset){
	 // File file=new File(dirPath,wordHtml);
	    
	    StringBuilder str=new StringBuilder(100);
		BufferedReader br = null;
		//String charset="UTF-8";
		//String charset="gb2312";
		try {
			
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file),charset);
			
			br = new BufferedReader(isr);
			
			String temp = null;
			
			while ((temp = br.readLine()) != null) {
			
				str.append(temp);
			}
			
			//System.out.println(str.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
          
			try {
				if(br !=null){
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	 return str.toString();
  }
}
