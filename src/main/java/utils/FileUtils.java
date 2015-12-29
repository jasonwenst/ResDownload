package utils;

import java.io.File;
import java.io.IOException;
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
		
//		if(!exp.matcher(url).matches()) {
//			log.info("a terrible url : " + url);
//			return "";
//		}
		
		String fields[] = url.split("/");
		fileName = fields[fields.length - 1];
		
		log.info("getFlieNameFromURL compoleted! fileName = " + fileName);
		return fileName;
	}
	
	/**
	 * get the file trans speed
	 * @param total - file total length byte
	 * @param seconds - total milliseconds
	 * @return speed
	 */
	public static String getFileTrasSpeed(double total, long seconds) {
		StringBuilder speed = new StringBuilder("");
		
		if(total == 0) {
			log.info("file total length must lager than 0!");
			return "";
		}
		
		speed.append("ava speed is ").append(total/seconds).append("kb/s");
		
		return speed.toString();
	}
	
	
	public static void renameFile(String orgName, String destName) {
		File destFile = new File(destName);
		File orgFile = new File(orgName);
		orgFile.renameTo(destFile);
	}
	
	public static void main (String s[]) {
//		String url = "http://mirrors.cnnic.cn/apache/tomcat/tomcat-7/v7.0.67/bin/apache-tomcat-7.0.67.tar.gz";
//		getFlieNameFromURL(url);
//		System.out.print(getFileTrasSpeed(9000000,11000));
		renameFile("C:\\Users\\guolq\\Desktop\\org.txt", "C:\\Users\\guolq\\Desktop\\dest.txt");
	}
}