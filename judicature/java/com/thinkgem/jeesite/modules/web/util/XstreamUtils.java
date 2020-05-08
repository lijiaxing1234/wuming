package com.thinkgem.jeesite.modules.web.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;

public class XstreamUtils {
    /**
     * 
     * 将XML转换成对应的Bean
     * 
     * @param xml
     *            XML字符串
     * @param cls
     *            须要转换成的对应Bean的字节码
     * @return Bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBean(String xml, Class<T> cls) {
	XStream xstream = new XStream(new DomDriver());
	xstream.processAnnotations(cls);
	return (T) xstream.fromXML(xml);
    }

    /**
     * 将 Bean 转换成对应的XML
     * 
     * @param bean
     *            须要转换的Bean
     * @param clazz
     *            转换Bean的字节码
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String toXML(Object bean,
	    @SuppressWarnings("rawtypes") Class... clazz) {
	XStream xstream = new XStream(new Xpp3Driver(new NoNameCoder()));
	for (Class<Object> cls : clazz) {
	    xstream.processAnnotations(cls);
	}
	return xstream.toXML(bean);
    }

}
