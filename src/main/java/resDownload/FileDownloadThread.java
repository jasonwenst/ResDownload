package resDownload;

import utils.HttpClientUtil;

public class FileDownloadThread implements Runnable {
	
	private String path;
	
	
	public FileDownloadThread() {
		super();
	}

	public FileDownloadThread(String path) {
		super();
		this.path = path;
	}


	public void run() {
		HttpClientUtil.downloadFile(path);

	}
	
	public void setPaht(String path) {
		this.path = path;
	}

}
