package net.ibaixin.chat.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.ibaixin.chat.api.model.ActionResult;
import net.ibaixin.chat.api.model.AttachDto;
import net.ibaixin.chat.api.model.Vcard;
import net.ibaixin.chat.api.model.VcardDto;
import net.ibaixin.chat.api.service.IVcardService;
import net.ibaixin.chat.api.service.impl.IRosterOpenfireService;
import net.ibaixin.chat.api.utils.SystemUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	
	private static Logger logger = Logger.getLogger(UserController.class); 
	
	@Autowired
	private IVcardService vcardService;
	
	@Autowired
	private IRosterOpenfireService rosterOpenfireService;
	
	/**
	 * 根据用户名获取对应的电子名片信息
	 * @update 2015年4月16日 下午8:22:27
	 * @param request
	 * @param username
	 * @return
	 */
	@RequestMapping("/vcard/{username}")
	@ResponseBody
	public ActionResult<VcardDto> getUserVcard(@PathVariable String username) {
		ActionResult<VcardDto> result = new ActionResult<>();
		if (StringUtils.isNotBlank(username)) {
			try {
				Vcard vcard = vcardService.getVcard(username);
				if (vcard != null) {
					VcardDto vcardDto = new VcardDto();
					vcardDto.setUsername(username);
					vcardDto.setCity(vcard.getCity());
					vcardDto.setCountry(vcard.getCountry());
					vcardDto.setGender(vcard.getGender().ordinal());
					vcardDto.setMobilePhone(vcard.getMobilePhone());
					vcardDto.setNickName(vcard.getNickName());
					vcardDto.setProvince(vcard.getProvince());
					vcardDto.setRealName(vcard.getRealName());
					vcardDto.setStreet(vcard.getStreet());
					vcardDto.setSignature(vcard.getSignature());
					vcardDto.setAvatarPath(vcard.getAvatarPath());
					vcardDto.setHash(vcard.getHash());
					result.setData(vcardDto);
					result.setResultCode(ActionResult.CODE_SUCCESS);
				} else {
					result.setResultCode(ActionResult.CODE_NO_DATA);
				}
			} catch (SQLException e) {
				result.setResultCode(ActionResult.CODE_ERROR_PARAM);
				e.printStackTrace();
			}
		} else {	//错误的请求参数
			result.setResultCode(ActionResult.CODE_ERROR_PARAM);
		}
		return result;
	}
	
	/**
	 * 上传用户头像
	 * @param files
	 * @param jsonStr
	 * @param request
	 * @return
	 * @author tiger
	 * @version 1.0.0
	 * @update 2015年4月19日 下午12:18:18
	 */
	@RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<Void> uploadAvatar(@RequestParam(value = "avatarFile", required = false) MultipartFile[] files, @RequestParam(required = true) String jsonStr, HttpServletRequest request) {
		ActionResult<Void> result = new ActionResult<>();
		AttachDto attachDto = null;
		if (StringUtils.isNotBlank(jsonStr)) {
			try {
				attachDto = SystemUtil.json2obj(jsonStr, AttachDto.class);
			} catch (IOException e) {
				result.setResultCode(ActionResult.CODE_ERROR);
				logger.error((e == null ? "error" : e.getMessage()));
			}
		} else {
			result.setResultCode(ActionResult.CODE_ERROR_PARAM);
			return result;
		}
		if (attachDto == null) {
			result.setResultCode(ActionResult.CODE_ERROR_PARAM);
			return result;
		}
		//判断文件是否为空
		if (files != null && files.length > 0) {
			String sender = attachDto.getSender();
			String hash = attachDto.getHash();
			String fileName = attachDto.getFileName();
			
			//源文件的存储路径
			File avatarSaveDir = getAvatarSaveDir(request, sender, FILE_TYPE_ORIGINAL);
			//缩略图的存储路径
			File avatarThumbSaveDir = getAvatarSaveDir(request, sender, FILE_TYPE_THUMB);
			
			//原始文件的名称
			String avatarName = avatarName(sender, FILE_TYPE_ORIGINAL);
			//文件的缩略图名称
			String avatarThumbName = avatarName(sender, FILE_TYPE_THUMB);
			
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					String originalFilename = file.getOriginalFilename();
					//保存文件到本地
					if (!originalFilename.equals(fileName)) {	//缩略图
						saveFile(file, avatarThumbSaveDir, avatarThumbName);
					} else {
						saveFile(file, avatarSaveDir, avatarName);
					}
				}
			}
			
			//添加或者保存该头像信息
			Vcard vcard = new Vcard();
			vcard.setUsername(sender);
			vcard.setAvatarPath(avatarName);
			vcard.setHash(hash);
			
			try {
				boolean success = vcardService.saveAvatar(avatarName, hash, sender);
				if (success) {
					result.setResultCode(ActionResult.CODE_SUCCESS);
				} else {
					result.setResultCode(ActionResult.CODE_ERROR);
				}
			} catch (SQLException e) {
				result.setResultCode(ActionResult.CODE_ERROR);
				logger.error(e.getMessage());
			}
		}
		return result;
	}
	
	/**
	 * 下载用户的头像
	 * @param username
	 * @param fileType 要下载的文类型，有两种类型，1：表示缩略图，2：表示原始文件
	 * @param request
	 * @return
	 * @author tiger
	 * @version 1.0.0
	 * @update 2015年4月19日 下午12:25:34
	 */
	@RequestMapping(value = "/avatar/{username}")
	public ResponseEntity<InputStreamResource> downloadAvatar(@PathVariable String username, @RequestParam(required = true) int fileType, HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		if (StringUtils.isNotBlank(username)) {
			File avatarFile = getAvatarSaveFile(request, username, fileType);
			if (avatarFile != null) {	//文件存在
				MediaType mediaType = null;
				ServletContext context = request.getServletContext();
				String fileName = avatarFile.getName();
				try {
					mediaType = MediaType.parseMediaType(context.getMimeType(fileName));
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				if (mediaType == null) {
					mediaType = MediaType.APPLICATION_OCTET_STREAM;
				}
				String encodeFilename = null;
				try {
					encodeFilename = URLEncoder.encode(fileName, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.error(e.getMessage());
					encodeFilename = fileName;
				}
				headers.setContentType(mediaType);
				headers.setContentLength(avatarFile.length());
				StringBuilder sb = new StringBuilder();
				sb.append("attachment;filename=")
					.append(encodeFilename)
					.append(";filename*=UTF-8''")
					.append(encodeFilename);
				headers.add(HttpHeaders.CONTENT_DISPOSITION, sb.toString());
				InputStreamResource inputStreamResource = null;
				try {
					inputStreamResource = new InputStreamResource(new FileInputStream(avatarFile));
					return new ResponseEntity<InputStreamResource>(inputStreamResource, headers, HttpStatus.OK);
				} catch (FileNotFoundException e) {
					logger.error(e.getMessage());
				}
				return new ResponseEntity<InputStreamResource>(inputStreamResource, headers, HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<InputStreamResource>(null, headers, HttpStatus.NO_CONTENT);
			}
		} else {
			return new ResponseEntity<InputStreamResource>(null, headers, HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * 获取对应的用户的好友的所有头像的hash值
	 * @author tiger
	 * @version 1.0.0
	 * @update 2015年4月18日 下午5:54:16
	 * @param username
	 * @return
	 */
	@RequestMapping("/rosterVcards/{username}")
	@ResponseBody
	public ActionResult<List<Vcard>> getRosterVcards(@PathVariable String username) {
		ActionResult<List<Vcard>> result = new ActionResult<>();
		if (StringUtils.isNotBlank(username)) {
			List<String> rosterNames = rosterOpenfireService.getRosters(username);
			if (!CollectionUtils.isEmpty(rosterNames)) {
				List<Vcard> list = vcardService.getVcardByIds(rosterNames);
				if (!CollectionUtils.isEmpty(list)) {
					result.setData(list);
					result.setResultCode(ActionResult.CODE_SUCCESS);
				} else {
					result.setResultCode(ActionResult.CODE_NO_DATA);
				}
			} else {
				result.setResultCode(ActionResult.CODE_NO_DATA);
			}
		} else {
			result.setResultCode(ActionResult.CODE_ERROR_PARAM);
		}
		return result;
	}
	
	/**
	 * 根据用户名以及是否是缩略图来获得对应的文件存储路径，不包含文件名
	 * @update 2015年4月16日 下午8:54:55
	 * @param username
	 * @param fileType
	 * @return
	 */
	public File getAvatarSaveDir(HttpServletRequest request, String username, int fileType) {
		if (fileType != FILE_TYPE_THUMB || fileType != FILE_TYPE_ORIGINAL) {
			return null;
		}
		File baseDir = getBaseDir(request);
		StringBuilder sb = new StringBuilder();
		sb.append("avatar").append(File.separator).append(username).append(File.separator);
		if (fileType == FILE_TYPE_THUMB) {	//1:缩略图:/upload/avatar/username/thumb/...
			sb.append("thumb").append(File.separator);
		}
		//2原始图片
		File avatarDir = new File(baseDir, sb.toString());
		if (!avatarDir.exists()) {
			avatarDir.mkdirs();
		}
		return avatarDir;
	}
	
	/**
	 * 根据用户名以及是否是缩略图来获得对应的文件全路径，包含文件名
	 * @param request
	 * @param username
	 * @param fileType
	 * @return
	 * @author tiger
	 * @version 1.0.0
	 * @update 2015年4月19日 下午12:43:35
	 */
	public File getAvatarSaveFile(HttpServletRequest request, String username, int fileType) {
		if (fileType != FILE_TYPE_THUMB || fileType != FILE_TYPE_ORIGINAL) {
			return null;
		}
		File baseDir = getBaseDir(request);
		StringBuilder sb = new StringBuilder();
		sb.append("avatar").append(File.separator).append(username).append(File.separator);
		String fileName = null;
		
		String thumbFileName = "icon_" + username + "_thumb";
		String originalFileName = "icon_" + username;
		
		boolean isThumb = false;
		if (fileType == FILE_TYPE_THUMB) {	//1:缩略图:/upload/avatar/username/thumb/...
			sb.append("thumb").append(File.separator);
			fileName = thumbFileName;
			isThumb = true;
		} else {
			fileName = originalFileName;
		}
		//2原始图片
		File avatarDir = new File(baseDir, sb.toString());
		if (!avatarDir.exists()) {
			avatarDir.mkdirs();
		}
		File saveFile = new File(avatarDir, fileName);
		if (isThumb) {	//是缩略图,如果没有缩略图，就直接下载原文件
			if (!saveFile.exists()) {	//缩略图不存在
				saveFile = new File(avatarDir, originalFileName);
				if (!saveFile.exists()) {	//原文件也不存在
					return null;
				}
			}
		} else {
			if (!saveFile.exists()) {
				return null;
			}
		}
			
		return saveFile;
	}
	
	/**
	 * 根据用户名和文件的类型来获取对应的文件
	 * @update 2015年4月16日 下午9:02:49
	 * @param username 用户名
	 * @param fileType 1表示缩略图，2表示原始文件
	 * @return
	 */
	public String avatarName(String username, int fileType) {
		String fileName = null;
		if (fileType == 1) {	//缩略图
			fileName = "icon_" + username + "_thumb";
		} else if (fileType == 2) {
			fileName = "icon_" + username;
		} else {
			fileName = null;
		}
		return fileName;
	}
	
}
