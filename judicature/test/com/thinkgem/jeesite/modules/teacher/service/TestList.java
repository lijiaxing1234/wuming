package com.thinkgem.jeesite.modules.teacher.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestList {
	public static void main(String[] args) {
		
		/*List<Persion> list = new ArrayList<Persion>();
		Persion p1 = new Persion();
		p1.setAge("20");
		p1.setId(1);
		p1.setMobile("1213");
		p1.setName("张三");
		Persion p2 = new Persion();
		p2.setId(2);
		p2.setAge("18");
		p2.setMobile("1213");
		p2.setName("张三");
		list.add(p1);
		List<Persion> list2 = new ArrayList<Persion>();
		list2.add(p2);
		int i = 0;
		for (Persion persion : list2) {
			if(list.contains(persion)){
				i++;
			}
		}
		System.out.println(i);*/
		
		
	}
	@Test
	public void testOffice2pdf(){
		BufferedReader br=null;
		BufferedWriter bw=null;
		OutputStream out=null;
		
		try{
			 String wordName="F:\\试题模板\\填空题.doc";
			 
			 String exePath ="F:\\新媒科创\\中邮\\code\\word转PDF\\打包\\word2pdf.exe" ;
			 //第一个是txt文件名，第二个是word文件名，
			 String[] cmds = {"cmd.exe","/C",exePath,wordName};//quesJson,targetQuestionFile.toString()+File.separator+wordName
			 Process  process=Runtime.getRuntime().exec(cmds);
			 
			InputStreamReader reader= new InputStreamReader(process.getInputStream());
			br=new BufferedReader(reader);
			String temp=null;
			while((temp=br.readLine())!=null){
				System.out.println(temp);
			}
			br.close();
		
		}catch(Exception ex){
			ex.printStackTrace();
		}finally {
			try{
				
				if(br!=null){
					br.close();
				}
				if(bw!=null){
					bw.close();
				}
				if(out!=null){
					out.close();
				}
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}

		
		
	}
}
