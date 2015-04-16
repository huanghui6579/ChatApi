package net.ibaixin.chat.api.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author huanghui1
 * @version 1.0.0
 * @update 2015年4月16日 下午8:51:10
 */
public abstract class BaseController {
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
}
