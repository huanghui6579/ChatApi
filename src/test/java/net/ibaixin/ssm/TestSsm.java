package net.ibaixin.ssm;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import net.ibaixin.chat.api.model.AttachDto;
import net.ibaixin.chat.api.model.Attachment;
import net.ibaixin.chat.api.service.IAttachService;
import net.ibaixin.chat.api.service.IUserService;
import net.ibaixin.chat.api.utils.SystemUtil;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class TestSsm {
	private static Logger logger = Logger.getLogger(TestSsm.class);
	
	private IUserService userService;
	
	@Autowired
	private IAttachService attachService;
	
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
}
