package net.ibaixin.chat.api.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping()
public class IndexController {
	private static Logger logger = Logger.getLogger(IndexController.class);
	
	@RequestMapping(value = {"/", ""})
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadFile(@RequestParam("uploadFile") MultipartFile file, String username, HttpServletRequest request) {
		logger.info("-------------" + username + "--------------");
		//判断文件是否为空
		if (!file.isEmpty()) {
			try {
				String fileDir = request.getServletContext().getRealPath("") + File.separator + "upload" + File.separator;
				File saveDir = new File(fileDir);
				if (!saveDir.exists()) {
					saveDir.mkdirs();
				}
				File saveFile = new File(saveDir, file.getOriginalFilename());
				file.transferTo(saveFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "redirect:/";
	}
}
