package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.AppException;
import utils.FileUtils;
import utils.PropertyUtils;

public class HttpClientUtil {
 
	private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
	
	public static void downloadFile(String path) throws AppException{
		
		log.info("downloadFile invocked! path : {}", path);
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
			log.info("fileName : {}" , fileName);
			File file = new File(fileName);
			total = getMethod.getResponseContentLength();
			if(file.exists()) {
				log.info("{} is already exist!", fileName);
				return ;
			}
			out = new FileOutputStream(fileName + "~" + total +"~.temp");
			
			int len = 0;
			byte[] b = new byte[1024];
			double finshLen = 0;
			total = getMethod.getResponseContentLength();
			while((len = resIn.read(b)) != -1) {
				out.write(b, 0, len);
				finshLen += len;
				log.debug(String.valueOf(finshLen/total));
			}
			
			out.close();
			resIn.close();
			
			long endTime = System.currentTimeMillis();
			
			usedTime = endTime - startTime;
			
			log.info(FileUtils.getFileTrasSpeed(total, endTime - startTime));
			
			FileUtils.renameFile(fileName + "~" + total +"~.temp", fileName);
			
		} catch (HttpException e) {
			throw new AppException(e.getMessage());
		} catch (IOException e) {
			throw new AppException(e.getMessage());
		} finally {
		}
		
		log.info("download success in  {} seconds", usedTime/1000);
	}
	
}