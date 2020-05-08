package com.thinkgem.jeesite.modules.teacher.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import sun.security.krb5.internal.LastReq;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.test.SpringTransactionalContextTests;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.service.CourseKnowledgeService;


/**
 * 测试 知识点导入
 * @author Administrator
 *
 */
public class TestCourseKnowlegeImport  extends SpringTransactionalContextTests{

	@Autowired
	CourseKnowledgeService courseKnowledgeService;
  
  @Test
  public void testKnowlegeImport() throws IOException{
	  
	    File file=new File("D:/import_Knowledge_Temp.xlsx");
	    String newExcelFileName=file.getName();
	    
	    
	    FileInputStream fin=new FileInputStream(file); 
		String fileExtend = newExcelFileName.substring(newExcelFileName.lastIndexOf('.')).toLowerCase();
		Workbook workbook = null;//创建工作薄  
		Sheet sheet = null;//得到工作表  
		Row row = null;//对应excel的行  
		Cell cell = null;//对应excel的列  
		if(".xls".equals(fileExtend)){
			workbook=new HSSFWorkbook(fin);//创建工作薄  
			sheet=workbook.getSheetAt(0);//得到工作表  
			row=(HSSFRow)row;//对应excel的行  
			cell=(HSSFCell)cell;//对应excel的列  
		}else{
			try {
				workbook=new XSSFWorkbook(fin);//创建工作薄  
			
			} catch (Exception e) {
				//model.addAttribute("message",  "不能读取文档，请确认文档是否可用!");
				// return "modules/questionlib/tips";
				
				throw new RuntimeException("不能读取文档，请确认文档是否可用!");
			}
		
			sheet=workbook.getSheetAt(0);//得到工作表  
			row=(XSSFRow)row;//对应excel的行  
			cell=(XSSFCell)cell;//对应excel的列  
		}
		
		int sheetMergerCount=sheet.getNumMergedRegions();
		for(int i = 0 ; i < sheetMergerCount ; i++ ){
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			
			System.out.println(firstColumn);
			System.out.println(lastColumn);
			
			System.out.println(firstRow);
			System.out.println(lastRow);
		}
		
		
		System.out.println(sheetMergerCount);
		
		
		
		
		
		/*CourseKnowledge root = courseKnowledgeService.get("1");
	    int totalRow=sheet.getLastRowNum(); //得到excel的总记录条数 
	    
	    List<CourseKnowledge> allList=Lists.newArrayList();
	    
	    for(int i=2;i<=totalRow;i++){  
	    	
	    	
		     row=sheet.getRow(i);
		     int cancel = 0;
		     if(null==row){
		    	 continue;
		     }else{
		    	 
		    	 
		    	// System.err.println("第"+i+"行列数："+);
			     //System.err.println("第"+i+"行："+row);
		    	 
		    	 List<CourseKnowledge> firstNodeList=Lists.newArrayList();
		    	 List<CourseKnowledge> secondNodeList=Lists.newArrayList();
		    	 List<CourseKnowledge> thirdNodeList=Lists.newArrayList();
		    	 
			     for (int j = 0,jLen=row.getPhysicalNumberOfCells(); j < jLen; j++) {
			    	 
			    	 cell=row.getCell(j);
			    	 if(null!=cell){
			    		//if(isMergedRegion(sheet,i,j)){
			    			
			    			System.out.println(getMergedRegionValue(sheet,i,j));
			    			
			    		//}
			    		 
			    		 String record = getCellValue(cell);
			    		 if(StringUtils.isBlank(record)){
			    			 cancel++;
			    			 
			    			// map.put("key"+j, "");
			    		 }else{
			    			 
			    			// map.put("key"+j, record);
			    		 }
			    	 }else{
			    		 cancel++;
			    		// map.put("key"+j, "");
			    		 continue;
			    	 }
				}
			     
			    if(nodeList.size()>0){
			    	allList.addAll(nodeList);
			    }
		     }
		     if(cancel<6){
		    	// mapList.add(map);
		     }
	    }*/
	  
	  
	  
  }
	
  
  
  /**
	 * 获取合并单元格的值
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public String getMergedRegionValue(Sheet sheet ,int row , int column){
		int sheetMergeCount = sheet.getNumMergedRegions();
		
		for(int i = 0 ; i < sheetMergeCount ; i++){
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			
			if(row >= firstRow && row <= lastRow){
				
				if(column >= firstColumn && column <= lastColumn){
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					
					return getCellValue(fCell) ;
				}
			}
		}
		
		return null ;
	}
	
	/**
	 * 判断指定的单元格是否是合并单元格
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean isMergedRegion(Sheet sheet , int row , int column){
		int sheetMergeCount = sheet.getNumMergedRegions();
		
		for(int i = 0 ; i < sheetMergeCount ; i++ ){
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			
			if(row >= firstRow && row <= lastRow){
				if(column >= firstColumn && column <= lastColumn){
					
					return true ;
				}
			}
		}
		
		return false ;
	}
  

	//根据cell的类型获取cell值
	private static String getCellValue(Cell cell){   
	      String value = null;   
	      //简单的查检列类型   
	      switch(cell.getCellType()){   
	          case Cell.CELL_TYPE_STRING://字符串   
	              value = cell.getRichStringCellValue().getString();   
	              break;   
	          case Cell.CELL_TYPE_NUMERIC://数字   
	              long dd = (long)cell.getNumericCellValue();   
	              value = dd+"";   
	              break;   
	          case Cell.CELL_TYPE_BLANK:   
	              value = "";   
	              break;      
	          case Cell.CELL_TYPE_FORMULA:   
	              value = String.valueOf(cell.getCellFormula());   
	              break;   
	          case Cell.CELL_TYPE_BOOLEAN://boolean型值   
	              value = String.valueOf(cell.getBooleanCellValue());   
	              break;   
	          case Cell.CELL_TYPE_ERROR:   
	              value = String.valueOf(cell.getErrorCellValue());   
	              break;   
	          default:   
	              break;   
	      }   
	      return value;   
	} 
}
