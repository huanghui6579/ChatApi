package net.ibaixin.chat.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.ibaixin.chat.api.model.ActionResult;
import net.ibaixin.chat.api.model.AttachDto;
import net.ibaixin.chat.api.model.Vcard;
import net.ibaixin.chat.api.model.Vcard.Gender;
import net.ibaixin.chat.api.model.VcardDto;
import net.ibaixin.chat.api.service.IVcardService;
import net.ibaixin.chat.api.service.impl.IRosterOpenfireService;
import net.ibaixin.chat.api.utils.SystemUtil;

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
					vcardDto.setMimeType(vcard.getMimeType());
					vcardDto.setHash(vcard.getHash());
					result.setData(vcardDto);
					result.setResultCode(ActionResult.CODE_SUCCESS);
				} else {
					logger.warn("------vcard 获取信息失败------" + vcard);
					result.setResultCode(ActionResult.CODE_NO_DATA);
				}
			} catch (Exception e) {
				result.setResultCode(ActionResult.CODE_ERROR_PARAM);
				e.printStackTrace();
			}
		} else {	//错误的请求参数
			result.setResultCode(ActionResult.CODE_ERROR_PARAM);
		}
		return result;
	}
	
	/**
	 * 添加电子名片信息，新建时，只添加部分字段，其余字段需更新
	 * @param jsonStr 电子名片信息的json字符串，该参数是必需的
	 * @param files 文件上传的头像数组，该参数不是必需的
	 * @param attachStr 头像的json字符串， 该参数不是必需的
	 * @return
	 * @update 2015年7月16日 上午10:29:40
	 */
	@RequestMapping(value = "/vcard/add", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<VcardDto> addVcard(@RequestParam(required = true) String jsonStr, @RequestParam(value = "avatarFile", required = false) MultipartFile[] files, @RequestParam(required = false) String attachStr, HttpServletRequest request) {
		logger.info("---------addVcard----jsonStr----" + jsonStr);
		ActionResult<VcardDto> result = new ActionResult<>();
		VcardDto vcardDto = null;
		AttachDto attachDto = null;
		if (StringUtils.isNotBlank(jsonStr)) {
			try {
				vcardDto = SystemUtil.json2obj(jsonStr, VcardDto.class);
				if (StringUtils.isNotBlank(attachStr)) {
					attachDto = SystemUtil.json2obj(attachStr, AttachDto.class);
				}
			} catch (IOException e) {
				result.setResultCode(ActionResult.CODE_ERROR);
				logger.error((e == null ? "error" : e.getMessage()), e);
			}
		} else {
			result.setResultCode(ActionResult.CODE_ERROR_PARAM);
			return result;
		}
		if (vcardDto == null) {
			result.setResultCode(ActionResult.CODE_ERROR_PARAM);
			return result;
		}
		Vcard vcard = new Vcard();
		String username = vcardDto.getUsername();
		
		//账号、用户名
		vcard.setUsername(username);
		vcard.setNickName(vcardDto.getNickName());
		vcard.setMimeType(vcardDto.getMimeType());
		vcard.setCity(vcardDto.getCity());
		vcard.setCountry(vcardDto.getCountry());
		try {
			vcard.setGender(Gender.valueOf(vcardDto.getGender()));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		vcard.setHash(vcardDto.getHash());
		vcard.setMobilePhone(vcardDto.getMobilePhone());
		vcard.setProvince(vcardDto.getProvince());
		vcard.setRealName(vcardDto.getRealName());
		vcard.setSignature(vcardDto.getSignature());
		vcard.setStreet(vcardDto.getStreet());
		vcard.setTelephone(vcardDto.getTelephone());
		
		//处理文件上传，保存文件
		if (attachDto != null) {
			attachDto.setSender(username);
			boolean success = handlerAvatarUpload(files, attachDto, request, vcard);
			logger.info("---addVcard---handlerAvatarUpload-----attachDto---" + attachDto + "----result----" + success);
		}
		
		try {
			vcard = vcardService.addVcard(vcard);
			if (vcard != null) {
				result.setResultCode(ActionResult.CODE_SUCCESS);
				
			} else {
				logger.warn("------vcard 添加失败------" + vcard);
				result.setResultCode(ActionResult.CODE_ERROR);
			}
		} catch (Exception e) {
			logger.error(e.getMessage() ,e);
			result.setResultCode(ActionResult.CODE_ERROR);
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
	public ActionResult<AttachDto> uploadAvatar(@RequestParam(value = "avatarFile", required = false) MultipartFile[] files, @RequestParam(required = true) String jsonStr, HttpServletRequest request) {
		ActionResult<AttachDto> result = new ActionResult<>();
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
			Vcard vcard = new Vcard();
			boolean success = false;
			success = handlerAvatarUpload(files, attachDto, request, vcard);
			if (success) {	//处理成功
				try {
					success = vcardService.updateAvatar(vcard);
					if (success) {
						attachDto = new AttachDto();
						attachDto.setSender(vcard.getUsername());
						attachDto.setHash(vcard.getHash());
						result.setData(attachDto);
						result.setResultCode(ActionResult.CODE_SUCCESS);
					} else {
						logger.warn("------vcard 更新失败------" + vcard);
						result.setResultCode(ActionResult.CODE_ERROR);
					}
				} catch (Exception e) {
					result.setResultCode(ActionResult.CODE_ERROR);
					logger.error(e.getMessage());
				}
			} else {	//处理失败
				logger.error("----handlerAvatarUpload---failed---jsonStr----" + jsonStr);
				result.setResultCode(ActionResult.CODE_ERROR);
			}
		}
		return result;
	}
	
	/**
	 * 处理电子名片的头像上传
	 * @param files 文件数组
	 * @param attachDto 文件信息实体
	 * @param vcard 电子名片实体
	 * @param request 
	 * @return 是否处理成功
	 * @update 2015年7月29日 上午9:56:54
	 */
	private boolean handlerAvatarUpload(MultipartFile[] files, AttachDto attachDto, HttpServletRequest request, Vcard vcard) {
		//判断文件是否为空
		if (files != null && files.length > 0) {
			try {
				String sender = attachDto.getSender();
				String hash = attachDto.getHash();
				String fileName = attachDto.getFileName();
				String mimeType = attachDto.getMimeType();
				
				//源文件的存储路径
				File avatarSaveDir = getAvatarSaveDir(request, sender, FILE_TYPE_ORIGINAL);
				//缩略图的存储路径
				File avatarThumbSaveDir = getAvatarSaveDir(request, sender, FILE_TYPE_THUMB);
				
				//原始文件的名称
				String avatarName = avatarName(sender, FILE_TYPE_ORIGINAL);
				//文件的缩略图名称
				String avatarThumbName = avatarName(sender, FILE_TYPE_THUMB);
				logger.info("---原始文件保存路径----" + avatarSaveDir);
				for (MultipartFile file : files) {
					if (!file.isEmpty()) {
						String originalFilename = file.getOriginalFilename();
						//文件的后缀
						String ext = FilenameUtils.getExtension(originalFilename);
						//保存文件到本地
						if (!originalFilename.equals(fileName)) {	//缩略图
							if (StringUtils.isNotBlank(ext)) {
								avatarThumbName = avatarThumbName + "." + ext;
							}
							saveFile(file, avatarThumbSaveDir, avatarThumbName);
						} else {
							if (StringUtils.isNotBlank(ext)) {
								avatarName = avatarName + "." + ext;
							}
							saveFile(file, avatarSaveDir, avatarName);
						}
					}
				}
				
				//添加或者保存该头像信息
				vcard.setUsername(sender);
				vcard.setAvatarPath(avatarName);
				vcard.setMimeType(mimeType);
				vcard.setHash(hash);
				return true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			return false;
		} else {
			return false;
		}
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
			try {
				Vcard vcard = vcardService.getAvatarInfo(username);
				logger.info("-----username-----" + username + "-------fileType-----" + fileType + "---vcard---" + vcard);
				if (vcard != null) {
					vcard.setUsername(username);
					File avatarFile = getAvatarSaveFile(request, vcard, fileType);
					if (avatarFile != null) {	//文件存在
						MediaType mediaType = null;
						String fileName = avatarFile.getName();
						try {
							mediaType = MediaType.parseMediaType(vcard.getMimeType());
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
						inputStreamResource = new InputStreamResource(new FileInputStream(avatarFile));
						return new ResponseEntity<InputStreamResource>(inputStreamResource, headers, HttpStatus.OK);
					} else {
						logger.warn("------no content------");
						return new ResponseEntity<InputStreamResource>(null, headers, HttpStatus.NO_CONTENT);
					}
				} else {
					logger.warn("------no content------");
					return new ResponseEntity<InputStreamResource>(null, headers, HttpStatus.NO_CONTENT);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				return new ResponseEntity<InputStreamResource>(null, headers, HttpStatus.NO_CONTENT);
			}
		} else {
			logger.warn("------no username------");
			return new ResponseEntity<InputStreamResource>(null, headers, HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * 将图片显示到页面中
	 * @param request
	 * @param response
	 * @update 2015年8月11日 下午7:11:41
	 */
	@RequestMapping(value = "/avatar/show/{username}")
	public void showImage(@PathVariable String username, int fileType, HttpServletRequest request, HttpServletResponse response) {
		String avatarName = request.getParameter("avatarName");
		if (fileType != FILE_TYPE_ORIGINAL && fileType != FILE_TYPE_THUMB) {	//默认为缩略图
			fileType = FILE_TYPE_THUMB;
		}
		try {
			Vcard vcard = null;
			if (avatarName == null) {
				//从数据库查询
				vcard = vcardService.getAvatarInfo(username);
			} else {
				vcard = new Vcard();
				vcard.setUsername(username);
				vcard.setAvatarPath(avatarName);
			}
			if (vcard != null) {
				File avatarFile = getAvatarSaveFile(request, vcard, fileType);
				if (avatarFile != null && avatarFile.exists()) {	//文件存在
					OutputStream os = response.getOutputStream();
					FileUtils.copyFile(avatarFile, os);
				} else {
					logger.warn("-----showImage---文件不存在---avatarFile--" + avatarFile + "----username----" + username + "-----的头像数据---fileType--" + fileType + "---avatarName----" + avatarName);
				}
			} else {
				//没有该好友的数据
				logger.warn("-----showImage---没有--" + username + "-----的头像数据---fileType--" + fileType + "---avatarName----" + avatarName);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 获取对应的用户所有头像的hash值和昵称等基本信息
	 * @param usernameStr 用户名字符串，多个用户用","分割
	 * @author tiger
	 * @version 1.0.0
	 * @update 2015年4月18日 下午5:54:16
	 * @return
	 */
	@RequestMapping("/vcard/userSimpleVcards")
	@ResponseBody
	public ActionResult<List<Vcard>> getUsersSimpleVcards(@RequestParam String usernameStr) {
		ActionResult<List<Vcard>> result = new ActionResult<>();
		if (StringUtils.isNotBlank(usernameStr)) {
			try {
				String[] names = usernameStr.split(",");
				if (ArrayUtils.isNotEmpty(names)) {
					List<Vcard> list = null;
					try {
						list = vcardService.getSimpleVcardByIds(names);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
					if (!CollectionUtils.isEmpty(list)) {
						result.setData(list);
						result.setResultCode(ActionResult.CODE_SUCCESS);
					} else {
						result.setResultCode(ActionResult.CODE_NO_DATA);
					}
				} else {
					result.setResultCode(ActionResult.CODE_NO_DATA);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result.setResultCode(ActionResult.CODE_ERROR_PARAM);
			}
		} else {
			logger.warn("------no username------");
			result.setResultCode(ActionResult.CODE_ERROR_PARAM);
		}
		return result;
	}
	
	/**
	 * 根据用户名获取对应好友的简单电子名片信息，值包含昵称、头像hash、性别
	 * @param username
	 * @return
	 * @update 2015年7月31日 下午7:36:44
	 */
	@RequestMapping("/rosterVcards/{username}")
	@ResponseBody
	public ActionResult<List<Vcard>> getRosterVcards(@PathVariable String username) {
		ActionResult<List<Vcard>> result = new ActionResult<>();
		if (StringUtils.isNotBlank(username)) {
			List<String> rosterNames = rosterOpenfireService.getRosters(username);
			if (!CollectionUtils.isEmpty(rosterNames)) {
				List<Vcard> list = null;
				try {
					list = vcardService.getVcardByIds(rosterNames);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
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
			logger.warn("------no username------");
			result.setResultCode(ActionResult.CODE_ERROR_PARAM);
		}
		return result;
	}
	
	/**
	 * 分页查询电子名片信息
	 * @param pageNumber 第几页，从1开始
	 * @param pageCount 每页显示多少条记录,默认为10条
	 * @return
	 * @update 2015年7月22日 下午4:14:35
	 */
	@RequestMapping("/vcard/vcards")
	@ResponseBody
	public ActionResult<List<Vcard>> getVcardList(@RequestParam int pageNumber, int pageCount, int pageable) {
		logger.info("---pageNumber--" + pageNumber + "----pageCount---" + pageCount);
		pageCount = pageCount <= 0 ? 10 : pageCount;
		pageNumber = pageNumber <= 0 ? 1 : pageNumber;
		ActionResult<List<Vcard>> result = new ActionResult<>();
		int pageOffset = (pageNumber - 1) * pageCount;
		List<Vcard> vcardList = null;
		try {
			if (pageable == 0) {	//不分页，加载全部数据
				vcardList = vcardService.getVcardListAll();
			} else {
				vcardList = vcardService.getVcardList(pageOffset, pageCount);
			}
			if (!CollectionUtils.isEmpty(vcardList)) {
				result.setResultCode(ActionResult.CODE_SUCCESS);
				result.setData(vcardList);
			} else {
				result.setResultCode(ActionResult.CODE_NO_DATA);
			}
		} catch (Exception e) {
			result.setResultCode(ActionResult.CODE_ERROR);
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value = "/modify/nick/{username}")
	@ResponseBody
	public ActionResult<Void> modifyNickname(@PathVariable String username, @RequestParam(required = true) String nickname) {
		ActionResult<Void> result = new ActionResult<>();
		if (StringUtils.isNotEmpty(nickname)) {
			try {
				boolean success = vcardService.updateNickName(nickname, username);
				if (success) {
					result.setResultCode(ActionResult.CODE_SUCCESS);
				} else {
					result.setResultCode(ActionResult.CODE_ERROR);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result.setResultCode(ActionResult.CODE_ERROR);
			}
		} else {
			logger.warn("------no nickname------");
			result.setResultCode(ActionResult.CODE_ERROR_PARAM);
		}
		return result;
	}
	
	@RequestMapping(value = "/modify/gender/{username}")
	@ResponseBody
	public ActionResult<Void> modifyGender(@PathVariable String username, @RequestParam(required = true) Integer gender) {
		ActionResult<Void> result = new ActionResult<>();
		if (gender != null) {
			try {
				
				boolean success = vcardService.updateGender(Gender.valueOf(gender.intValue()), username);
				if (success) {
					result.setResultCode(ActionResult.CODE_SUCCESS);
				} else {
					result.setResultCode(ActionResult.CODE_ERROR);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result.setResultCode(ActionResult.CODE_ERROR);
			}
		} else {
			logger.warn("------no gender------");
			result.setResultCode(ActionResult.CODE_ERROR_PARAM);
		}
		return result;
	}
	
	@RequestMapping(value = "/modify/address/{username}")
	@ResponseBody
	public ActionResult<Void> modifyAddress(@PathVariable String username, @RequestBody(required = true) Vcard vcard) {
		ActionResult<Void> result = new ActionResult<>();
		try {
			if (vcard != null) {
				vcard.setUsername(username);
				boolean success = vcardService.updateAddress(vcard);
				if (success) {
					result.setResultCode(ActionResult.CODE_SUCCESS);
				} else {
					result.setResultCode(ActionResult.CODE_ERROR);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResultCode(ActionResult.CODE_ERROR);
		}
		return result;
	}
	
	@RequestMapping(value = "/modify/signature/{username}")
	@ResponseBody
	public ActionResult<Void> modifySignature(@PathVariable String username, @RequestParam(required = true) String signature) {
		ActionResult<Void> result = new ActionResult<>();
		try {
			if (StringUtils.isNoneEmpty(signature)) {
				boolean success = vcardService.updateSignature(signature, username);
				if (success) {
					result.setResultCode(ActionResult.CODE_SUCCESS);
				} else {
					result.setResultCode(ActionResult.CODE_ERROR);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResultCode(ActionResult.CODE_ERROR);
		}
		return result;
	}
	
	/**
	 * 根据用户名数组批量删除电子名片信息，用户名的分隔符为","
	 * @param ids
	 * @return
	 * @update 2015年7月23日 上午11:26:47
	 */
	@RequestMapping(value = "/vcard/delete")
	@ResponseBody
	public ActionResult<Void> deleteVcards(@RequestParam(required = true) String ids) {
		ActionResult<Void> result = new ActionResult<>();
		if (StringUtils.isNotBlank(ids)) {
			try {
				boolean success = false;
				if (ids.indexOf(",") != -1) {	//多个用户名,则执行批量删除任务
					String[] idArray = ids.split(",");
					success = vcardService.deleteVcards(idArray);
				} else {	//只有一个用户名，则作为单条删除就行了
					success = vcardService.deleteVcard(ids);
				}
				if (success) {
					result.setResultCode(ActionResult.CODE_SUCCESS);
				} else {
					result.setResultCode(ActionResult.CODE_ERROR);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result.setResultCode(ActionResult.CODE_ERROR);
			}
		} else {
			logger.warn("------no ids------");
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
	private File getAvatarSaveDir(HttpServletRequest request, String username, int fileType) {
		if (fileType != FILE_TYPE_THUMB && fileType != FILE_TYPE_ORIGINAL) {
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
	 * @param vcard
	 * @param fileType
	 * @return
	 * @author tiger
	 * @version 1.0.0
	 * @update 2015年4月19日 下午12:43:35
	 */
	private File getAvatarSaveFile(HttpServletRequest request, Vcard vcard, int fileType) {
		if (fileType != FILE_TYPE_THUMB && fileType != FILE_TYPE_ORIGINAL) {
			return null;
		}
		String username = vcard.getUsername();
		File baseDir = getBaseDir(request);
		StringBuilder sb = new StringBuilder();
		sb.append("avatar").append(File.separator).append(username).append(File.separator);
		String fileName = null;
		String originalFileName = vcard.getAvatarPath();
		String ext = FilenameUtils.getExtension(originalFileName);
		String thumbFileName = "icon_" + username + "_thumb";
		if (StringUtils.isNoneBlank(ext)) {
			thumbFileName = thumbFileName + "." + ext;
		}
		
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
	private String avatarName(String username, int fileType) {
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
