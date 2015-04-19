package net.ibaixin.chat.api.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author huanghui1
 * @version 1.0.0
 * @update 2015年4月16日 下午8:51:10
 */
public abstract class BaseController {
	
	/**
	 * 缩略图文件
	 */
	public static final int FILE_TYPE_THUMB = 1;
	
	/**
	 * 原始文件
	 */
	public static final int FILE_TYPE_ORIGINAL = 2;
	/**
	 * 或得存储文件的根目录
	 * @return
	 */
	protected File getBaseDir(HttpServletRequest request) {
		String fileDir = request.getServletContext().getRealPath("/") + File.separator + "upload";
		File saveDir = new File(fileDir);
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}
		return saveDir;
	}
	
	/**
	 * 保存文件到本地磁盘
	 * @param file
	 * @param saveDir 保存文件的路径
	 * @param saveFileName 保存文件的名称,以UUID命名
	 * @return true:保存到本地磁盘成功，false:保存到本地磁盘失败
	 */
	protected boolean saveFile(MultipartFile file, File saveDir, String saveFileName) {
		File saveFile = new File(saveDir, saveFileName);
		if (saveFile.exists()) {
			saveFile.delete();
		}
		try {
			file.transferTo(saveFile);
			return true;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
