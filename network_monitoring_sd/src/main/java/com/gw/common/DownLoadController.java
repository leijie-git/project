package com.gw.common;

import com.gw.myAnnotation.PassToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;

@RequestMapping(value = "/common/download")
@Controller
@ResponseBody
public class DownLoadController extends BaseController {

	private static final String ADDRESS = "address";
	private static final String EXTINGUISHER = "extinguisher";
	private static final String WEBCOMPONENTS = "WebComponents";
	private static final String INTERFACEOUT = "interfaceOut";
	private static final String PATROLPLAN = "patrolplan";
	private static final String NETVIDEOACTIVEX23 = "NetVideoActiveX23";
	private static final String SITEEXCELLOAD = "SiteExcelLoad";
	private static final String XLS = ".xls";
	private static final String EXE = ".exe";
	private static final String CAB = ".cab";
//	private static final String XLSX = ".xlsx";
//	private static final String DOC = ".doc";
//	private static final String DOCX = ".docx";

	/**
	 * 下载
	 * 
	 * @param response
	 * @param module
	 */
	@RequestMapping(value = "/{module}")
	public void download(HttpServletResponse response,
			@PathVariable String module) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		String filename = "";
		String fileType = "";
		if (module.equals("address")) {
			filename = ADDRESS;
			fileType = XLS;
		} 
		if (module.equals("extinguisher")) {
			filename = EXTINGUISHER;
			fileType = XLS;
		}
		if (module.equals("WebComponents")) {
			filename = WEBCOMPONENTS;
			fileType = EXE;
		}
		if (module.equals("NetVideoActiveX23")) {
			filename = NETVIDEOACTIVEX23;
			fileType = CAB;
		}
		if (module.equals("interfaceOut")) {
			filename = INTERFACEOUT;
			fileType = XLS;
		}
		if (module.equals("patrolplan")) {
			filename = PATROLPLAN;
			fileType = XLS;
		}

		if (module.equals("SiteExcelLoad")) {
			filename = SITEEXCELLOAD;
			fileType = XLS;
		}
        try {
			filename = URLEncoder.encode(filename, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ filename + fileType);
		try {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/download/" + module + fileType);
			
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			os.flush();
			os.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 下载
	 * 
	 * @param response
	 * @param module
	 */
	@PassToken
	@RequestMapping(value = "/downloadFile")
	public void downloadFile(HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		String filename = "消防安全管理";
		String fileType = ".apk";
        try {
			filename = URLEncoder.encode(filename, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ filename + fileType);
		try {
			String path = URLDecoder
					.decode(Thread.currentThread().getContextClassLoader()
							.getResource("").getPath(), "utf-8")
					+ "file/upload/" + filename + fileType;
			InputStream inputStream = new FileInputStream(new File(path));
			
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			os.flush();
			os.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
