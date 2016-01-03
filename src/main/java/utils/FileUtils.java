package utils;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import entity.FileStatusBean;
import exception.AppException;

public class FileUtils {

	public static Logger log = LoggerFactory.getLogger(FileUtils.class);

	static {
		loadProperty();
	}

	private static void loadProperty() {

	}

	public static String getFlieNameFromURL(String url) {

		log.info("getFlieNameFromURL invocked, url = {}" , url);

		String fileName = "";
//		Pattern exp = Pattern.compile("^http://[\\w\\.\\-]+(?:/|(?:/[\\w\\.\\-]+)*)?$", Pattern.CASE_INSENSITIVE);

		// if(!exp.matcher(url).matches()) {
		// log.info("a terrible url : " + url);
		// return "";
		// }

		String fields[] = url.split("/");
		fileName = fields[fields.length - 1];
		
		log.info("getFlieNameFromURL compoleted! fileName = {}" , fileName);
		return fileName;
	}

	/**
	 * get the file trans speed
	 * 
	 * @param total
	 *            - file total length byte
	 * @param seconds
	 *            - total milliseconds
	 * @return speed
	 */
	public static String getFileTrasSpeed(double total, long seconds) {
		StringBuilder speed = new StringBuilder("");

		if (total == 0) {
			log.info("file total length must lager than 0!");
			return "";
		}

		speed.append("ava speed is ").append(total / seconds).append("kb/s");

		return speed.toString();
	}

	public static void renameFile(String orgName, String destName) {
		File destFile = new File(destName);
		File orgFile = new File(orgName);
		orgFile.renameTo(destFile);
	}

	public static List<FileStatusBean> getAllFiles(String path) {
		log.info("getAllFiles invocked! path : {}" , path);
		File file = new File(path);
		List<FileStatusBean> files = new ArrayList<FileStatusBean>();
		if (!file.exists()) {
			log.info("{} is not exist", path);
			return files;
		} else {
			for(File tempFile : file.listFiles()) {
				files.add(new FileStatusBean(tempFile.getName(), getTotalLength(tempFile.getName(), tempFile.length()), tempFile.length(), isFileDownloadCompoleted(tempFile.getName())));
				log.info("find file : {}" , tempFile.getName() );
			}
			return files;
		}
	}

	public static InputStream getInputStreamBySFTP(String ip, String userName, String pwd, String port, String name) 
	throws AppException{
		
		log.info("getInputStreamBySFTP invocked!");
		InputStream in = null;

		JSch jsch = new JSch();
		Session session = null;
		Channel channel = null;

		long startTime = System.currentTimeMillis();
		try {
			session = jsch.getSession(userName, ip);

			session.setPassword(pwd);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect(30000);

			channel = (Channel) session.openChannel("sftp");
			channel.connect(1000);
			ChannelSftp sftp = (ChannelSftp) channel;

			String path = PropertyUtils.getProperty("savePath");
			
			log.info("path : {}" , path);

			sftp.cd(path);

			in = sftp.get(name);

		} catch (JSchException e) {
			throw new AppException(e.getMessage());
		} catch (SftpException e1) {
			throw new AppException(e1.getMessage());
		}
		double endTime = System.currentTimeMillis();
		log.info("get inputStream in {} seconds", (endTime-startTime)/1000);
		return in;
	}

	public static boolean isWindows() {
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
			return true;
		}
		return false;
	}
	
	
	public static boolean isFileDownloadCompoleted(String fileName) {
		if(StringUtils.isEmpty(fileName)) {
			return false;
		}
		if(fileName.endsWith(".temp")) {
			return false;
		}
		return true;
	}
	
	public static long getTotalLength(String fileName, long length) {
		if(StringUtils.isEmpty(fileName)) {
			return 0;
		}
		
		if(isFileDownloadCompoleted(fileName)) {
			return length;
		}
		
		String temp = fileName.split("~")[fileName.split("~").length - 2];
		
		BigDecimal bd = new BigDecimal(temp); 
		
		return bd.longValue();
	}
	
	

	public static void main(String s[]) {
		// String url =
		// "http://mirrors.cnnic.cn/apache/tomcat/tomcat-7/v7.0.67/bin/apache-tomcat-7.0.67.tar.gz";
		// getFlieNameFromURL(url);
		// System.out.print(getFileTrasSpeed(9000000,11000));
		// renameFile("C:\\Users\\guolq\\Desktop\\org.txt",
		// "C:\\Users\\guolq\\Desktop\\dest.txt");
		
		
	}
}