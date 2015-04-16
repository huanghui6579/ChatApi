package net.ibaixin.chat.api.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.ibaixin.chat.api.model.ActionResult;
import net.ibaixin.chat.api.model.User;
import net.ibaixin.chat.api.model.Vcard;
import net.ibaixin.chat.api.model.VcardDto;
import net.ibaixin.chat.api.service.IUserService;
import net.ibaixin.chat.api.service.IVcardService;
import net.ibaixin.chat.api.utils.SystemUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	
	private static Logger logger = Logger.getLogger(UserController.class); 
	
	private IUserService userService;
	
	@Autowired
	private IVcardService vcardService;
	
	@Autowired
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/hello")
	public String hello(Model model) {
		model.addAttribute("hello", "你好");
		return "user/showUser";
	}
	
	@ResponseBody
	@RequestMapping(value = "/users")
	public List<User> show(Model model) {
		List<User> users = userService.getUsers();
		logger.info(users);
		return users;
	}
	
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
			Vcard vcard = vcardService.getVcard(username);
			if (vcard != null) {
				VcardDto vcardDto = new VcardDto();
				vcardDto.setCity(vcard.getCity());
				vcardDto.setCountry(vcard.getCountry());
				vcardDto.setGender(vcard.getGender().ordinal());
				vcardDto.setMobilePhone(vcard.getMobilePhone());
				vcardDto.setNickName(vcard.getNickName());
				vcardDto.setProvince(vcard.getProvince());
				vcardDto.setRealName(vcard.getRealName());
				vcardDto.setStreet(vcard.getStreet());
				vcardDto.setSignature(vcard.getSignature());
				result.setData(vcardDto);
			} else {
				result.setResultCode(ActionResult.CODE_NO_DATA);
			}
		} else {	//错误的请求参数
			result.setResultCode(ActionResult.CODE_ERROR_PARAM);
		}
		return result;
	}
	
	/**
	 * 更新用户的电子名片信息，如果没有名片，则创建
	 * @update 2015年4月16日 下午8:39:20
	 * @param request
	 * @param username
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping(value = {"/vcard/{username}"}, method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<Void> updateVcard(@PathVariable String username, @RequestParam(required = true) String jsonStr, @RequestParam(required = true) int fileType, HttpServletRequest request) {
		ActionResult<Void> result = new ActionResult<>();
		if (StringUtils.isNotBlank(jsonStr)) {
			try {
				VcardDto vcardDto = SystemUtil.json2obj(jsonStr, VcardDto.class);
				if (vcardDto != null) {
					File avatarFile = new File(getAvatarSaveDir(request, username, fileType), avatarName(username, fileType));
					Vcard vcard = new Vcard();
					vcard.setUsername(username);
//					vcard.setAvatarPath(avatarPath);
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			
		}
		return result;
	}
	
	/**
	 * 根据用户名以及是否是缩略图来获得对应的文件存储路径
	 * @update 2015年4月16日 下午8:54:55
	 * @param username
	 * @param fileType
	 * @return
	 */
	public File getAvatarSaveDir(HttpServletRequest request, String username, int fileType) {
		if (fileType != 1 || fileType != 2) {
			return null;
		}
		File baseDir = getBaseDir(request);
		StringBuilder sb = new StringBuilder();
		sb.append("avatar").append(File.separator).append(username).append(File.separator);
		if (fileType == 1) {	//1:缩略图:/upload/avatar/username/thumb/...
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
