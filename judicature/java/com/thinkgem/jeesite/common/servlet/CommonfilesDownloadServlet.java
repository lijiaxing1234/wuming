package com.thinkgem.jeesite.common.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.util.UriUtils;

import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 公共资源下载
 */
public class CommonfilesDownloadServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	static  final String BASEURL="/commonDownLoadFiles/";
	
    private static String[] IEBrowserSignals = {"MSIE", "Trident", "Edge"};

    private  boolean isMSBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        for (String signal : IEBrowserSignals) {
            if (userAgent.contains(signal))
                return true;
        }
        return false;
    }

	public void fileOutputStream(HttpServletRequest req, HttpServletResponse resp) 	throws ServletException, IOException {
		String filepath = req.getRequestURI();
		String physicalPath=req.getSession().getServletContext().getRealPath("/");
		
		
		int index = filepath.indexOf(BASEURL);
		if(index >= 0) {
			filepath = filepath.substring(index + BASEURL.length());
		}
		try {
			filepath = UriUtils.decode(filepath, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.error(String.format("解释文件路径失败，URL地址为%s", filepath), e1);
		}
		
		
		
		File file = new File(physicalPath,filepath);
		
		String fileName=req.getParameter("fileName");
		
		if(StringUtils.isNoneBlank(fileName)){
			String suffix=file.getName().substring(file.getName().lastIndexOf("."));
			boolean isMSIE = isMSBrowser(req);
			if (isMSIE) {
				fileName = URLEncoder.encode(fileName+suffix, "UTF-8");
			}else {
				fileName = new String((fileName+suffix).getBytes("UTF-8"), "ISO-8859-1");
			}
		}else{
			fileName=file.getName();
		}
		
		if(file.exists()&&file.isFile()){
			try {
				resp.reset();
			/*	resp.setHeader("Content-Type", "application/octet-stream");*/
				resp.setHeader("Content-Disposition", "attachment;filename="+fileName);
				resp.setContentType("application/octet-stream; charset=utf-8");
				
				FileCopyUtils.copy(new FileInputStream(file), resp.getOutputStream());
				return;
			} catch (FileNotFoundException e) {
				req.setAttribute("exception", new FileNotFoundException("请求的文件不存在"));
				req.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(req, resp);
			}
		}else{
			req.setAttribute("exception", new FileNotFoundException("请求的文件不存在"));
			resp.getWriter().write("您访问的资源已经被删除");
//			req.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		fileOutputStream(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		fileOutputStream(req, resp);
	}
}
