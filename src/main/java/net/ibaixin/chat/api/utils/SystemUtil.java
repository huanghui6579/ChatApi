package net.ibaixin.chat.api.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class SystemUtil {
	private SystemUtil() {}
	
	private static JsonFactory factory;
	private static ObjectMapper mapper;
	
	static {
		getFactory();
		getMapper();
	}
	
	public static JsonFactory getFactory() {
		if(factory == null) {
			factory = new JsonFactory();
		}
		return factory;
	}
	
	public static ObjectMapper getMapper() {
		if(mapper == null) {
			mapper = new ObjectMapper();
		}
		return mapper;
	}
	
	public static <T> String obj2json(T obj) {
		StringWriter out = new StringWriter();
		JsonGenerator generator = null;
		try {
			generator = factory.createGenerator(out);
			mapper.writeValue(generator, obj);
			return out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(generator != null) {
				try {
					generator.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static <T> T json2obj(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
		T t = mapper.readValue(json, clazz);
		return t;
	}
	
	/**
	 * 将字符创进行MD5加密
	 * @author tiger
	 * @update 2015年3月8日 下午6:44:03
	 * @param str 待加密的字符串
	 * @return 加密后的字符串
	 */
	public static String encoderByMd5(String str) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(str.getBytes("UTF-8"));
			byte[] hash = digest.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append(0).append(Integer.toHexString((0xFF & hash[i])));
				} else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}
			return hexString.toString(); 
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
