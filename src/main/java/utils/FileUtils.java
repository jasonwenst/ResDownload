package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class FileUtils {

	public static Log log = LogFactory.getLog(FileUtils.class);

	static {
		loadProperty();
	}

	private static void loadProperty() {

	}

	public static String getFlieNameFromURL(String url) {

		log.info("getFlieNameFromURL invocked");

		String fileName = "";
		Pattern exp = Pattern.compile("^http://[\\w\\.\\-]+(?:/|(?:/[\\w\\.\\-]+)*)?$", Pattern.CASE_INSENSITIVE);

		// if(!exp.matcher(url).matches()) {
		// log.info("a terrible url : " + url);
		// return "";
		// }

		String fields[] = url.split("/");
		fileName = fields[fields.length - 1];

		log.info("getFlieNameFromURL compoleted! fileName = " + fileName);
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

	public static List<String> getAllFiles(String path) {
		log.info("getAllFiles invocked! path : " + path);
		File file = new File(path);
		List<String> files = new ArrayList<String>();
		if (!file.exists()) {
			return files;
		} else {
			files = Arrays.asList(file.list());
			log.info("files :" + file.list().toString());
			return files;
		}
	}

	public static InputStream getInputStreamBySFTP(String ip, String userName, String pwd, String port, String name) {
		InputStream in = null;

		JSch jsch = new JSch();
		Session session = null;
		Channel channel = null;

		long startTime = System.currentTimeMillis();
		try {
			session = jsch.getSession(userName, ip);

			session.setPassword(pwd);// 设置密码
			// 设置第一次登陆的时候提示，可选值：(ask | yes | no)
			session.setConfig("StrictHostKeyChecking", "no");
			// 设置登陆超时时间
			session.connect(30000);

			channel = (Channel) session.openChannel("sftp");
			channel.connect(1000);
			ChannelSftp sftp = (ChannelSftp) channel;

			String path = PropertyUtils.getProperty(isWindows() ? "savePathWindows" : "savePathLinux");
			
			log.info("path : " + path);

			sftp.cd(path);

			try {
				in = sftp.get(name);
			} catch (SftpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SftpException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		double endTime = System.currentTimeMillis();
		log.info("get inputStream in " + (endTime-startTime)/1000 + "seconds");
		return in;
	}

	public static boolean isWindows() {
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
			return true;
		}
		return false;
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