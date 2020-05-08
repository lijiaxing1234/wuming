package com.thinkgem.jeesite.common.utils.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.Range;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.StringUtils;

public class ExportWord {
	public static void main(String[] args){
		//测试方法
    }  
	
    /**
     * 读取word模板并替换变量
     * @param srcPath
     * @param prefixPath 
     * @param map
     * @return
     */
    public static XWPFDocument replaceDoc(String srcPath, Map<String,String> param, List<Map<String,String>> mapList, String prefixPath) {
    	Map<String,String> headerMap = mapList.get(0);
    	int cellSize = headerMap.size();	//表列数
		int rowSize = mapList.size();	//表行数
        try {
            // 读取word模板
            InputStream fis = new FileInputStream(srcPath);
            CustomXWPFDocument_S_9 doc = new CustomXWPFDocument_S_9(fis);
            //处理段落  
            List<XWPFParagraph> paragraphList = doc.getParagraphs();  
            System.err.println("准备出来段落");
            processParagraph(paragraphList,doc,param);
            System.err.println("段落出来完毕");
            //创建表格
            	//设置表格每列宽度
            int[] colWidths = new int[]{600, 600, 600, 600, 600, 600, 600, 1500, 1000, 1000, 1000, 1000, 600, 1500, 600, 600};
            XWPFTable table = POIWord2007Utils.createTable(doc, rowSize, cellSize, true, colWidths);
            POIWord2007Utils.setTableWidthAndHAlign(table, "8000", STJc.CENTER);
    		CTTblBorders tblBorders = POIWord2007Utils.getTableBorders(table);
    		CTBorder rBorder = tblBorders.addNewTop();
    		rBorder.setVal(STBorder.SINGLE);
    		rBorder.setSz(new BigInteger("3"));
//    		rBorder.setColor("BF9C9C");
    		CTBorder lBorder = tblBorders.addNewLeft();
    		lBorder.setVal(STBorder.SINGLE);
    		lBorder.setSz(new BigInteger("3"));
    		POIWord2007Utils.setTableBorders(table, lBorder, rBorder, lBorder, rBorder);
//            XWPFTable table = doc.createTable(rowSize, cellSize);
            List<XWPFTableRow> rows = table.getRows();  
            for (int i = 0; i < rowSize; i++) {
            	XWPFTableRow row = rows.get(i);
            	List<XWPFTableCell> cells = row.getTableCells();
				Map<String,String> map = mapList.get(i);
				for (int j = 0; j < cellSize; j++) {
					XWPFTableCell cell = cells.get(j);
					XWPFParagraph p = POIWord2007Utils.getCellFirstParagraph(cell);
					XWPFRun pRun = POIWord2007Utils.getOrAddParagraphFirstRun(p, false, false);
					String content = map.get("key"+j);
//					cell.setText(map.get("key"+j));
					List<String> list0 = Lists.newArrayList();
					List<String> list = Lists.newArrayList();
					if(StringUtils.isNoneBlank(content)){
						content =content.replace("<span lang=EN-US>", "").replace("<span   lang=EN-US>", "").replace("</span>", "").replace("<p>", "").replace("</p>", "");
					}
					
					if(StringUtils.isBlank(content)){
						continue;
					}else if(StringUtils.isNotBlank(content) && !content.contains("<img") && !content.contains("/>") && !content.contains("<table") && !content.contains("</table>")){
						POIWord2007Utils.setCellVAlign(cell, STVerticalJc.CENTER);
						POIWord2007Utils.setParagraphRunFontInfo(p, pRun, content,	"微软雅黑", "12");
						POIWord2007Utils.setParagraphTextBasicStyleInfo(p, pRun, null, false, false,
								null, null, false, false, false, null, false,
								STShd.CLEAR, "000000");
						POIWord2007Utils.setParagraphAlignInfo(p, ParagraphAlignment.CENTER, null);
					}else{
						list0 = getStringList(content, "<table", "</table>");
						for (String str : list0) {
							list.addAll(getStringList(str, "<img", ">"));
						}
						int type = 0 ;
						String imgPath = "";
						String picType  ="";
						for (String str : list) {
							
							  type = 0;
							try {
								POIWord2007Utils.setParagraphAlignInfo(p, ParagraphAlignment.LEFT, TextAlignment.CENTER);
								POIWord2007Utils.setParagraphSpacingInfo(p, true, "0", "0", "0", "0", true, "240", STLineSpacingRule.AUTO);
								pRun = POIWord2007Utils.getOrAddParagraphFirstRun(p, true, true);
								if(StringUtils.isNotBlank(str) && str.contains("<img") && str.contains(">")){
									try {
										  imgPath = str.substring(str.indexOf("src=")+5);
										 int endindex= imgPath.indexOf("'");
										 if(endindex==-1)
										 {
											 endindex= imgPath.indexOf("\"");
										 }
										 
										 if(endindex>-1)
										 {
											 imgPath =imgPath.substring(0,endindex);
										 }
										 
										
										imgPath = prefixPath.replaceAll("\\\\", "/") + imgPath;
										System.err.println("imgPath"+imgPath);
										  picType = "PICTURE_TYPE_" + imgPath.substring(imgPath.lastIndexOf('.')+1).toUpperCase();
										 type = getPicType(picType);
									} catch (Exception e) {
										 
									}
									
									if(type==0){
										POIWord2007Utils.setParagraphRunFontInfo(p, pRun, "(图片格式不正确)", "宋体", "12");
									}else{
										try {
											String blipId = p.getDocument().addPictureData(
													new FileInputStream(new File(imgPath)),
													getPicType(picType));
											doc.createPicture(blipId,
													doc.getNextPicNameNumber(getPicType(picType)), 90, 93, p);
										} catch (Exception e) {
											e.printStackTrace();
										}
										
									}
								}else if(StringUtils.isNotBlank(str) && str.contains("<table") && str.contains("</table>")){
									byte b[] = str.getBytes();  
									String blipId = p.getDocument().addPictureData(b, Document.PICTURE_TYPE_PNG);
									doc.createPicture(blipId, doc.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), 90, 93, p);
					                /*ByteArrayInputStream bais = new ByteArrayInputStream(b);    
					                POIFSFileSystem poifs = new POIFSFileSystem();    
					                DirectoryEntry directory = poifs.getRoot();  
					                DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);*/
								}else{
									POIWord2007Utils.setParagraphRunFontInfo(p, pRun, str, "宋体", "12");
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
							
						}
					}
				}
			}
            return doc;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
//    public static void replaceMap(HWPFDocument doc,Map<String,String> param){
//    	Range range = doc.getRange();  
//        //把range范围内的${reportDate}替换为当前的日期  
//	  	for (Entry<String,String> entry : param.entrySet()) {  
//		    String key = entry.getKey();  
//		    Object value = entry.getValue();  
//		        	System.out.println(key);
//		        	System.out.println(value.toString());
//		        	range.replaceText(key, value.toString());
//		        } 
//		    }  
//		}        
//	  	range.replaceText("${reportDate}", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));  
//        range.replaceText("${appleAmt}", "100.00");  
//        range.replaceText("${bananaAmt}", "200.00");  
//        range.replaceText("${totalAmt}", "300.00");  
//    }
    
    
    /**
     * 根据map集合param逐个替换模板中的标记字符串
     * @param paragraphList	段落集合
     * @param doc	模板文档
     * @param param		替换参数map集合
     */
    public static void processParagraph(List<XWPFParagraph> paragraphList, XWPFDocument doc,Map<String,String> param){
    	System.err.println("开始出理段落");
    	if(paragraphList != null && paragraphList.size() > 0){  
       	 for(XWPFParagraph paragraph:paragraphList){
       		 List<XWPFRun> runs = paragraph.getRuns();  
       		 for (XWPFRun run : runs) {  
       			 System.err.println("出理段落中");
       			 String text = run.getText(0);  
                    if(text != null){  
                   	 boolean isSetText = false;  
                        for (Entry<String,String> entry : param.entrySet()) {  
                            String key = entry.getKey();  
                            if(text.indexOf(key) != -1){  
                                isSetText = true;  
                                Object value = entry.getValue();  
                                if (value instanceof String) {//文本替换  
                                	System.out.println(key);
                                	System.out.println(text);
                                    //text = text.replaceAll(key, value.toString());  .
                                	text = value.toString();
                                } 
                            }  
                        }  
                        if(isSetText){  
                            run.setText(text,0);  
                        }  
                    }
       		 }
       	 }
       }
    } 
    
    /**
     * 设置表格格式
     */
    public void setTableStyle(){
    	
    }
    
    /**
     * 用指定两个字符串间（包含这两个字符串）的字符串分割成原字符串得到的字符串集合
     * @param str
     * @param replace1
     * @param replace2
     * @return
     */
    private static List<String> getStringList(String str,String replace1,String replace2){
    	List<String> list = Lists.newArrayList();
		int length = str.length();
		int begin = 0;
		int end = 0;
		String subString = "";
		String s = str;
		while(begin<length && StringUtils.isNotBlank(str)){
			if(s.contains(replace1) && s.contains(replace2)){
				end = begin+s.indexOf(replace1);
				list.add(str.substring(begin,end));	//-replace1.length()
				s = str.substring(end);
			}else{
				break;
			}
			subString = getSubString(s, replace1, replace2);
			if("".equals(subString)){
				break;
			}
			list.add(subString);
			begin = end + subString.length();
			s= str.substring(begin);	//replace = "(图或表格省略......)"
		}
		list.add(s);
		return list;
	}
	
    /**
     * 根据两个字符串获取，原字符串中这两个字符串间的字符串（包含这两个字符串）
     * @param str
     * @param replace1
     * @param replace2
     * @return
     */
	private static String getSubString(String str,String replace1,String replace2){
		if(str.length()>replace1.length() && str.length()>replace2.length() && str.contains(replace1) && str.contains(replace2)){
			return str.substring(str.indexOf(replace1), str.indexOf(replace2)+replace2.length());
		}
		return "";
	}
	
	public static int getPicType(String picType){
		if("PICTURE_TYPE_EMF".equals(picType)){
			return 2;
		}else if("PICTURE_TYPE_WMF".equals(picType)){
			return 3;
		}else if("PICTURE_TYPE_PICT".equals(picType)){
			return 4;
		}else if("PICTURE_TYPE_JPEG".equals(picType) || "PICTURE_TYPE_JPG".equals(picType)){
			return 5;
		}else if("PICTURE_TYPE_PNG".equals(picType)){
			return 6;
		}else if("PICTURE_TYPE_DIB".equals(picType)){
			return 7;
		}else if("PICTURE_TYPE_GIF".equals(picType)){
			return 8;
		}else if("PICTURE_TYPE_TIFF".equals(picType)){
			return 9;
		}else if("PICTURE_TYPE_EPS".equals(picType)){
			return 10;
		}else if("PICTURE_TYPE_BMP".equals(picType)){
			return 11;
		}else if("PICTURE_TYPE_WPG".equals(picType)){
			return 12;
		}
		return 0;
	}
}
