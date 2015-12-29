package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import utils.FileUtils;
import utils.PropertyUtils;

public class HttpClientUtil {
 
	private static final String URL = "http://mirrors.cnnic.cn/apache/tomcat/tomcat-7/v7.0.67/bin/apache-tomcat-7.0.67.tar.gz";
	private static Log log = LogFactory.getLog(HttpClientUtil.class);
	
	public static void downloadFile(String path) {
		
		InputStream in = HttpClientUtil.class.getResourceAsStream("properties.properties");
		FileOutputStream out = null;
		
		HttpClient client = new HttpClient();
		GetMethod getMethod = new GetMethod();
		getMethod.setPath(path);
		double total;
		long usedTime;
		try {
			long startTime = System.currentTimeMillis();
			client.executeMethod(getMethod);
			InputStream resIn = getMethod.getResponseBodyAsStream();
			String fileName = PropertyUtils.getProperty("savePath")+System.getProperty("file.separator")+FileUtils.getFlieNameFromURL(path);
			File file = new File(fileName);
			if(file.exists()) {
				log.info(fileName + " is already exist!");
				return ;
			}
			out = new FileOutputStream(fileName+".temp");
			
			int len = 0;
			byte[] b = new byte[1024];
			double finshLen = 0;
			total = getMethod.getResponseContentLength();
			while((len = resIn.read(b)) != -1) {
				out.write(b, 0, len);
				finshLen += len;
				log.info(finshLen/total);
			}
			
			out.close();
			resIn.close();
			
			long endTime = System.currentTimeMillis();
			
			usedTime = endTime - startTime;
			
			log.info(FileUtils.getFileTrasSpeed(total, endTime - startTime));
			
			FileUtils.renameFile(fileName+".temp", fileName);
			
		} catch (HttpException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
		}
		
		log.info("download success in " + usedTime/1000 + "seconds");
	}
	
}