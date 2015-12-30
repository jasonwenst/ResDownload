package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class JSCHUtils {

	public static void main(String[] args) throws Exception {
		
		JSch jsch = new JSch();
		Session session = null;
		Channel channel = null;
		
		session = jsch.getSession("root", "123.56.233.132");
		
		session.setPassword("LiqingGuo2014");//设置密码   
	    //设置第一次登陆的时候提示，可选值：(ask | yes | no)
	    session.setConfig("StrictHostKeyChecking", "no");
	    //设置登陆超时时间   
	    session.connect(30000);
		
		channel = (Channel) session.openChannel("sftp");
        channel.connect(1000);
        ChannelSftp sftp = (ChannelSftp) channel;
         
        String path = PropertyUtils.getProperty("savePath");
         
        sftp.cd("/download/java");
         
         
        
        InputStream in = sftp.get("apache-tomcat-7.0.67-windows-x64.zip");
        
        FileOutputStream out = new FileOutputStream(new File("C:\\Users\\guolq\\Desktop\\apache-tomcat-7.0.67-windows-x64-download.zip"));
        
        byte b[] = new byte[1024];
        int n;
        while ((n = in.read(b)) != -1) {
            out.write(b, 0, n);
        }
        
        in.close();
        out.close();
	}
}
