package com.thinkgem.jeesite.modules.student.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.catalina.mbeans.GlobalResourcesLifecycleListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;

public class MobileStringUtils {
	
	
	/**
     * 使用ImageReader获取图片尺寸
     * 
     * @param src
     *            源图片路径
     */
    public static Map<String, Integer> getImageSizeByImageReader(String src) {
    	Map<String, Integer> widthHeightMap = new HashMap<String, Integer>();
        File file = new File(src);
        try {
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");
            ImageReader reader = (ImageReader) readers.next();
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            reader.setInput(iis, true);
            int widht = reader.getWidth(0);
            int height = reader.getHeight(0);
            height = height/widht*200;
            widthHeightMap.put("width", 200);
            widthHeightMap.put("height", height);
            return widthHeightMap;
        } catch (IOException e) {
            e.printStackTrace();
            widthHeightMap.put("width", 200);
            return widthHeightMap;
        }
    }
	
	
	public static String getImgUrl(String str){
		if(StringUtils.isBlank(str)){
			return "";
		}else{
			String mobileImgPrefix = Global.getMobileImgPrefix();
			Document doc = Jsoup.parse(str);
			Elements elementsByTag = doc.getElementsByTag("img");
			for (int i = 0; i < elementsByTag.size(); i++) {
				Element element = elementsByTag.get(i);
				String attr = element.attr("src");
				element.attr("src", mobileImgPrefix + attr);
			}
			return doc.body().html().trim();
		}
	}
	
	public static String[] getImgUrlArr (String...str){
		List<String> strList = new ArrayList<String>();
		for (String imgStr : str) {
			String imgUrl = getImgUrl(imgStr);
			if(null != imgUrl && !"".equals(imgUrl)){
				strList.add(imgUrl);
			}
		}
		String[] array = new String[strList.size()];
		strList.toArray(array);
		return array;
	}
	
	public static String removeImgAndTable (String title) {
		if(StringUtils.isBlank(title)){
			return "";
		}
		Document doc = Jsoup.parse(title);
		doc.select("img").remove();
		doc.select("table").remove();
		return doc.body().html().trim();
	}
	
	
}
