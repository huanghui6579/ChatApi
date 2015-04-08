package net.ibaixin.chat.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	/*@ResponseBody
	@RequestMapping(value = "/download/{fileName}", produces = MediaType.TEXT_XML_VALUE)
	public FileSystemResource downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		String filePath = request.getServletContext().getRealPath("") + File.separator + "upload" + File.separator + "coolpad.xml";
		File file = new File(filePath);
		FileSystemResource fileResource = new FileSystemResource(file);
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "coolpad.xml");
	    try {
			responseHeaders.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileResource.contentLength()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	    responseHeaders.add(HttpHeaders.CONTENT_TYPE, "text/xml");
		return fileResource;
	}*/
	
	/*@RequestMapping("/download")
	@ResponseBody
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
		ServletContext context = request.getServletContext();
		String filePath = context.getRealPath("") + File.separator + "upload" + File.separator + "coolpad.xml";
		File downloadFile = new File(filePath);
		FileInputStream fis = null;
		OutputStream os = null;
		try {
			fis = new FileInputStream(downloadFile);
			response.setContentLength((int) downloadFile.length());
			response.setContentType(context.getMimeType(filePath) + "; charset=utf-8");
			String filename = "中文名.xml";
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + new String(filename.getBytes("utf-8"), "ISO8859-1"));
			
			os = response.getOutputStream();
			
			IOUtils.copy(fis, os);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}*/
	
	@RequestMapping("/download")
	@ResponseBody
	public ResponseEntity<InputStreamResource> downloadFile(HttpServletRequest request) throws IOException {
		ServletContext context = request.getServletContext();
		String filePath = context.getRealPath("") + File.separator + "upload" + File.separator + "coolpad.xml";
		File downloadFile = new File(filePath);
		
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = null;
		
		try {
			mediaType = MediaType.parseMediaType(context.getMimeType(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (mediaType == null) {
			mediaType = MediaType.APPLICATION_OCTET_STREAM;
		}
		String filename = "中文名.xml";
		String encodeFilename = URLEncoder.encode(filename,"UTF-8");
		headers.setContentType(mediaType);
		headers.setContentLength(downloadFile.length());
		StringBuilder sb = new StringBuilder();
		sb.append("attachment;filename=")
			.append(encodeFilename)
			.append(";filename*=UTF-8''")
			.append(encodeFilename);
		headers.add(HttpHeaders.CONTENT_DISPOSITION, sb.toString());
		InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(downloadFile));
		return new ResponseEntity<InputStreamResource>(inputStreamResource, headers, HttpStatus.OK);
	}
	
/*	@RequestMapping("/download")
	public ResponseEntity<byte[]> downloadFile(HttpServletRequest request) throws IOException {
		ServletContext context = request.getServletContext();
		String filePath = context.getRealPath("") + File.separator + "upload" + File.separator + "coolpad.xml";
		File downloadFile = new File(filePath);
		
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = null;
		
		try {
			mediaType = MediaType.parseMediaType(context.getMimeType(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (mediaType == null) {
			mediaType = MediaType.APPLICATION_OCTET_STREAM;
		}
		String filename = "中文名.xml";
		headers.setContentType(mediaType);
		headers.setContentLength(downloadFile.length());
		headers.setContentDispositionFormData("attachment", filename);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(downloadFile), headers, HttpStatus.OK);
	}
*/}
