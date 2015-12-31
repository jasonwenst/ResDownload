package contollors;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import exception.AppException;
import resDownload.FileDownloadThread;
import utils.FileUtils;
import utils.PropertyUtils;

@Controller
public class FileDownloadController {

	private Log log = LogFactory.getLog(FileDownloadController.class);

	@RequestMapping(value = "download", method = RequestMethod.POST)
	public String download(@RequestParam(value = "path") String path) {
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

	@RequestMapping(value = "downloadBySFTP", method = RequestMethod.GET)
	public String downloadBySFTP(HttpServletRequest request, HttpServletResponse response, String fileName)
			throws Exception {

		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		String ip = PropertyUtils.getProperty("ip");
		String userName = PropertyUtils.getProperty("userName");
		String pwd = PropertyUtils.getProperty("pwd");
		String port = PropertyUtils.getProperty("port");

//		log.info("ip = {}, userName = {}, pwd = {}, port = {}", ip, userName, pwd, port);

		InputStream is = FileUtils.getInputStreamBySFTP(ip, userName, pwd, port, fileName);

		long fileLength = new File(
				PropertyUtils.getProperty("savePath") + System.getProperty("file.separator") + fileName).length();

		response.setContentType("application/octet-stream");
		try {
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));

			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(is);

			bos = new BufferedOutputStream(response.getOutputStream());

			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}

			bis.close();
			bos.close();
		} catch (IOException e) {
			throw new AppException(e.getMessage());
		}
		
		return "viewFiles";

	}

//	@ResponseBody
//	@RequestMapping(value = "downloadBySFTP", method = RequestMethod.GET)
//	public ResponseEntity<byte[]> downloadBySFTP(@RequestParam(value = "fileName") String fileName,
//			@RequestParam(value = "type", required = false, defaultValue = "application/vnd.ms-excel") String type) {
//
//		log.info("downloadBySFTP invocked! fileName = {}", fileName);
//
//		String ip = PropertyUtils.getProperty("ip");
//		String userName = PropertyUtils.getProperty("userName");
//		String pwd = PropertyUtils.getProperty("pwd");
//		String port = PropertyUtils.getProperty("port");
//
//		log.info("ip = {}, userName = {}, pwd = {}, port = {}", ip, userName, pwd, port);
//		HttpHeaders headers = new HttpHeaders();
//		byte[] body = null;
//		HttpStatus httpState = HttpStatus.NOT_FOUND;
//
//		InputStream is = FileUtils.getInputStreamBySFTP(ip, userName, pwd, port, fileName);
//
//		try {
//			body = new byte[is.available()];
//			is.read(body);
//			is.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		headers.add("Content-Type", type);
//		headers.add("Content-Length", "" + body.length);
//		headers.add("Content-Disposition", "attachment;filename=" + fileName);
//		httpState = HttpStatus.OK;
//
//		ResponseEntity<byte[]> entity = new ResponseEntity<>(body, headers, httpState);
//
//		log.info("downloadBySFTP compoleted!");
//
//		return entity;
//
//	}

}
