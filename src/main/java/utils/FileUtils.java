package utils;

import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtils {

	public static Log log =  LogFactory.getLog(FileUtils.class);
	
	static {
		loadProperty();
	}
	
	private static void loadProperty() {
		
	}
	
	public static String getFlieNameFromURL(String url) {
		
		log.info("getFlieNameFromURL invocked");
		
		String fileName = "";
		Pattern exp=Pattern.compile("^http://[\\w\\.\\-]+(?:/|(?:/[\\w\\.\\-]+)*)?$", Pattern.CASE_INSENSITIVE);
		
		if(!exp.matcher(url).matches()) {
			log.info("a terrible url : " + url);
			return "";
		}
		
		String fields[] = url.split("/");
		fileName = fields[fields.length - 1];
		
		log.info("getFlieNameFromURL compoleted! fileName = " + fileName);
		return fileName;
	}
	
	
	public static void main (String s[]) {
		String url = "http://mirrors.cnnic.cn/apache/tomcat/tomcat-7/v7.0.67/bin/apache-tomcat-7.0.67.tar.gz";
		getFlieNameFromURL(url);
	}
}
