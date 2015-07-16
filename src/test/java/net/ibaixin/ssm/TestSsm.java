package net.ibaixin.ssm;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.ibaixin.chat.api.model.AttachDto;
import net.ibaixin.chat.api.model.Attachment;
import net.ibaixin.chat.api.model.Vcard;
import net.ibaixin.chat.api.model.Vcard.Gender;
import net.ibaixin.chat.api.service.IAttachService;
import net.ibaixin.chat.api.service.IUserService;
import net.ibaixin.chat.api.service.IVcardService;
import net.ibaixin.chat.api.service.impl.IRosterOpenfireService;
import net.ibaixin.chat.api.utils.SystemUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class TestSsm {
	private static Logger logger = Logger.getLogger(TestSsm.class);
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IAttachService attachService;
	
	@Autowired
	private IVcardService vcardService;
	
	@Autowired
	private IRosterOpenfireService rosterOpenfireService;

	private JsonGenerator jsonGenerator = null;

    private ObjectMapper objectMapper = null;
    
    
    @Before
    public void setup() {
    	objectMapper = new ObjectMapper();
    	try {
			jsonGenerator = objectMapper.getFactory().createGenerator(System.out, JsonEncoding.UTF8);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @After
    public void destory() {
        try {
			if (jsonGenerator != null) {
			    jsonGenerator.flush();
			}
			if (!jsonGenerator.isClosed()) {
			    jsonGenerator.close();
			}
			jsonGenerator = null;
			objectMapper = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	@Resource
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	@Test
	public void testJson() {
		AttachDto attachDto = new AttachDto();
		attachDto.setReceiver("bbb");
		try {
			jsonGenerator.writeObject(attachDto);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*@Test
	public void testAdd() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setUsername("李四");
		user.setPassword("123456");
		userService.add(user);
		logger.info(user.toString());
	}
	
	@Test
	public void testGetUser() {
		User user = userService.getUser("a1d7769c-8223-428f-967b-14055b1bb5d6");
		logger.info(user);
	}
	
	@Test
	public void testDeleteUser() {
		userService.delete("a1d7769c-8223-428f-967b-14055b1bb5d6");
		User user = userService.getUser("a1d7769c-8223-428f-967b-14055b1bb5d6");
		logger.info(user);
	}
	
	@Test
	public void testGetUsers() {
		List<User> users = userService.getUsers();
		logger.info(users);
	}
	
	@Test
	public void testGetUsersByPage() {
		UserDto userDto = new UserDto();
		userDto.setPageOffset(0);
		userDto.setPageSize(1);
		List<User> users = userService.getUsers(userDto);
		logger.info(users);
	}*/
	
	@Test
	public void testAddAttach() {
		Attachment attachment = new Attachment();
		
		attachment.setId(SystemUtil.encoderByMd5("aaa_bbbb_123344545"));
		attachment.setSender("aaa");
		attachment.setReceiver("bbb");
		attachment.setCreationDate(new Date());
		attachment.setFileName("abc.txt");
		attachment.setHasThumb(true);
		attachment.setMimeType("text/plain");
		attachment.setSotreName("dsafdsfgdgfdgfdhgfhg");
		
		attachService.saveAttach(attachment);
	}
	
	@Test
	public void testDeleteAttach() {
		String id = "a88f81ebe084a3523d1934bb7bea6cbb";
		
		attachService.deleteAttach(id);
	}
	
	@Test
	public void testGetAttachment() {
		Attachment attachment = attachService.getAttachment("a88f81ebe084a3523d1934bb7bea6cbb");
		logger.info(attachment);
	}
	
	@Test
	public void testAddVcard() {
		Vcard vcard = new Vcard();
		vcard.setUsername("ccc");
		vcard.setGender(Gender.WOMAN);
		try {
			vcardService.saveVcard(vcard);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetVcard() {
		Vcard vcard;
		try {
			vcard = vcardService.getVcard("aaa");
			logger.info(vcard);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeleteVcard() {
		try {
			vcardService.deleteVcard("aaa");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSaveAvatar() {
		String avatarPath = "sdsfdfggf";
		Vcard vcard = new Vcard();
		vcard.setUsername("ccc");
		vcard.setAvatarPath(avatarPath);
		vcard.setHash("gfdghfggffghghhghf");
		try {
			vcardService.saveAvatar(vcard);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAvatarPath() {
		try {
			String avatarPath = vcardService.getAvatarPath("aaa");
			logger.info("-----avatarPath-----" + avatarPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSaveNickName() {
		String nickname = "大AAA";
		try {
			vcardService.saveNickName(nickname, "aaa");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSaveGender() {
		Gender gender = Gender.MAN;
		try {
			vcardService.saveGender(gender, "aaa");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSaveAddress() {
		String country = "中国";
		String province = "广东";
		String city = "深圳";
		String street = "南山";
		Vcard vcard = new Vcard();
		vcard.setCountry(country);
		vcard.setProvince(province);
		vcard.setUsername("aaa");
		vcard.setCity(city);
		vcard.setStreet(street);
		try {
			vcardService.saveAddress(vcard);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSaveSignature() {
		String signature = "个性签名";
		try {
			vcardService.saveSignature(signature, "aaa");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetRosters() {
		
		List<String> users = rosterOpenfireService.getRosters("aaa");
		
		logger.info("--------" + users);
	}
	
	@Test
	public void testGetVcards() {
		try {
			List<String> users = rosterOpenfireService.getRosters("aaa");
			List<Vcard> vcards = vcardService.getVcardByIds(users);
			logger.info("------------------" + vcards);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
