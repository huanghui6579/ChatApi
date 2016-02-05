package net.ibaixin.chat.api.model.home;

import java.io.Serializable;
import java.util.Date;

/**
 * 主页的意见反馈、用户联系实体
 * @author huanghui1
 * @version 1.0.0
 * @update 2016年2月5日 下午2:11:58
 */
public class HomeComment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private long id;
	
	/**
	 * 用户姓名
	 */
	private String name;
	
	/**
	 * 反馈的主题
	 */
	private String subject;
	
	/**
	 * 用户的邮箱
	 */
	private String email;
	
	/**
	 * 反馈的内容
	 */
	private String content;
	
	/**
	 * 反馈的时间
	 */
	private Date time;
	
	/**
	 * 用户的ip地址
	 */
	private String ip;
	
	/**
	 * 用户所用的操作系统
	 */
	private String os;
	
	/**
	 * 用户的物理mac地址
	 */
	private String mac;
	
	/**
	 * 用户使用的浏览器
	 */
	private String browser;
	
	/**
	 * 用户所在地
	 */
	private String location;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "HomeComment [id=" + id + ", name=" + name + ", subject=" + subject + ", email=" + email + ", content="
				+ content + ", time=" + time + ", ip=" + ip + ", os=" + os + ", mac=" + mac + ", browser=" + browser
				+ ", location=" + location + "]";
	}
}
