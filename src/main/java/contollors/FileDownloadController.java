package contollors;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import resDownload.FileDownloadThread;
import utils.FileUtils;
import utils.PropertyUtils;

@Controller
public class FileDownloadController {
	
	private Log log = LogFactory.getLog(FileDownloadController.class);
	
	@RequestMapping(value = "download", method = RequestMethod.POST)
	public String download(@RequestParam(value="path") String path) {
		log.info("download invocked!");
		
		FileDownloadThread thread = new FileDownloadThread(path);
		new Thread(thread).start();
		
		log.info("download compoleted!");
		return "redirect:viewFiles";
	}
	
	@RequestMapping(value = "download", method = RequestMethod.GET)
	public String download() {
		log.info("download invocked!");
		
		log.info("download compoleted!");
		return "index";
	}
	
	@RequestMapping(value = "viewFiles", method = RequestMethod.GET)
	public String viewFiles() {
		
		log.info("viewFiles invocked!");
		
		
		log.info("viewFiles compoleted!");
		return "viewFiles";
		
	}
	
	@ResponseBody
	@RequestMapping(value = "downloadBySFTP", method = RequestMethod.GET)
	public ResponseEntity<byte[]> downloadBySFTP( 
			@RequestParam(value = "fileName") String fileName,
            @RequestParam(value = "type", required = false, defaultValue = "application/vnd.ms-excel") String type) {
		
		String ip = PropertyUtils.getProperty("ip");
		String userName = PropertyUtils.getProperty("userName");
		String pwd = PropertyUtils.getProperty("pwd");
		String port = PropertyUtils.getProperty("port");
		
		
		HttpHeaders headers = new HttpHeaders();
        byte[] body = null;
        HttpStatus httpState = HttpStatus.NOT_FOUND;
 
        InputStream is = FileUtils.getInputStreamBySFTP(ip, userName, pwd, port, fileName);
        
        try {
			body = new byte[is.available()];
	        is.read(body);
	        is.close();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        headers.add("Content-Type", type);
        headers.add("Content-Length", "" + body.length);
        headers.add("Content-Disposition", "attachment;filename="
                + fileName);
        httpState = HttpStatus.OK;
 
 
        ResponseEntity<byte[]> entity = new ResponseEntity<>(body, headers,
                httpState);
 
        return entity;
		
	}

}
