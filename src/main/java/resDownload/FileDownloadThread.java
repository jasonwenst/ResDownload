package resDownload;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import exception.AppException;
import utils.HttpClientUtil;

public class FileDownloadThread implements Runnable {
	
	private Log log = LogFactory.getLog(FileDownloadThread.class);
	
	private String path;
	
	
	public FileDownloadThread() {
		super();
	}

	public FileDownloadThread(String path) {
		super();
		this.path = path;
	}


	public void run(){
		try {
			HttpClientUtil.downloadFile(path);
		} catch (AppException e) {
			log.error("download failed : " + e.getMessage());
		}

	}
	
	public void setPaht(String path) {
		this.path = path;
	}

}
