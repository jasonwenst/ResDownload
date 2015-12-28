package resDownload;

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
	
	public static void main(String s[]) {

		InputStream in = HttpClientUtil.class.getResourceAsStream("properties.properties");
		FileOutputStream out = null;
		
		HttpClient client = new HttpClient();
		GetMethod getMethod = new GetMethod();
		getMethod.setPath(URL);
		
		try {
			
			client.executeMethod(getMethod);
			InputStream resIn = getMethod.getResponseBodyAsStream();
			out = new FileOutputStream(PropertyUtils.getProperty("savePath")+System.getProperty("file.separator")+FileUtils.getFlieNameFromURL(URL));
			
			int len = 0;
			byte[] b = new byte[1024];
			double finshLen = 0;
			double total = getMethod.getResponseContentLength();
			while((len = resIn.read(b)) != -1) {
				out.write(b, 0, len);
				finshLen += len;
				log.info(finshLen/total);
			}
			
			out.close();
			in.close();
			
		} catch (HttpException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
		}
		
		log.info("download success!");
		
	}
}
