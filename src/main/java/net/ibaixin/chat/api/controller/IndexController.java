package net.ibaixin.chat.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.ibaixin.chat.api.model.AttachDto;
import net.ibaixin.chat.api.utils.SystemUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
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
	@ResponseBody
	public void uploadFile(@RequestParam(value = "uploadFile", required = false) MultipartFile[] files, @RequestParam(required = true) String jsonStr, HttpServletRequest request) {
		AttachDto attachDto = null;
		if (StringUtils.isNoneBlank(jsonStr)) {
			attachDto = SystemUtil.json2obj(jsonStr, AttachDto.class);
			logger.info("--------------" + attachDto);
		} else {
			return;
		}
		if (attachDto == null) {
			return;
		}
		//判断文件是否为空
		if (files != null && files.length > 0) {
			File baseDir = getBaseDir(request);
			try {
				
				//缩略图的名称
				String thumbName = attachDto.getThumbName();
				String fileName = attachDto.getFileName();
				String sender = attachDto.getSender();
				String receiver = attachDto.getReceiver();
				if (StringUtils.isNoneBlank(thumbName)) {	//有缩略图
					long time = System.currentTimeMillis();
					for (MultipartFile file : files) {
						if (!file.isEmpty()) {
							String originalFilename = file.getOriginalFilename();
							boolean isThumb = false;
							String saveName = null;
							if (!originalFilename.equals(fileName)) {	//缩略图
								isThumb = true;
								saveName = UUID.randomUUID().toString() + "_thumb";
							} else {
								saveName = UUID.randomUUID().toString();
							}
							File saveDir = getSaveDir(baseDir, sender, receiver, time, isThumb);
							String mimeType = file.getContentType();
							saveFile(file, saveDir, saveName);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 或得存储文件的根目录
	 * @return
	 */
	private File getBaseDir(HttpServletRequest request) {
		String fileDir = request.getServletContext().getRealPath("/") + File.separator + "upload";
		File saveDir = new File(fileDir);
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}
		return saveDir;
	}
	
	/**
	 * 根据发送这和接受者来动态生成文件的存储目录,生成的目录为:upload/2015/04/12/sender/receiver/...,缩略图的为:upload/sender/receiver/thumb/...
	 * @param baseDir
	 * @param sender
	 * @param receiver
	 * @param time
	 * @param isThumb
	 * @return
	 */
	private File getSaveDir(File baseDir, String sender, String receiver, long time, boolean isThumb) {
		StringBuilder sb = new StringBuilder();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		sb.append(year).append(File.separator).append(valueOnInt(month)).append(File.separator).append(valueOnInt(day)).append(File.separator);
		
		sb.append(sender).append(File.separator).append(receiver);
		if (isThumb) {
			sb.append(File.separator).append("thumb");
		}
		
		File saveFile = new File(baseDir, sb.toString());
		if (!saveFile.exists()) {
			saveFile.mkdirs();
		}
		return saveFile;
	}
	
	/**
	 * 将int值装换成两位数的字符串,如1----->01
	 * @param value
	 * @return
	 */
	private String valueOnInt(int value) {
		StringBuilder sb = new StringBuilder(String.valueOf(value));
		if (Math.abs(value) < 10) {	//个位数
			sb.insert(0, 0);
		}
		return sb.toString();
	}
	
	/**
	 * 保存文件到本地磁盘
	 * @param file
	 * @param saveDir 保存文件的路径
	 * @param saveFileName 保存文件的名称,以UUID命名
	 */
	private void saveFile(MultipartFile file, File saveDir, String saveFileName) {
		File saveFile = new File(saveDir, saveFileName);
		try {
			file.transferTo(saveFile);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
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
