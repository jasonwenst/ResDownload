package contollors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import utils.HttpClientUtil;

@Controller
public class FileDownloadController {
	
	@RequestMapping("download")
	public String download(@RequestParam(value="path") String path) {
		HttpClientUtil.downloadFile(path);
		return "";
	}

}
