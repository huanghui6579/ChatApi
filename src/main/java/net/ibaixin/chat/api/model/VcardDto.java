package net.ibaixin.chat.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 电子名片的web映射类
 * @author huanghui1
 * @version 1.0.0
 * @update 2015年4月16日 下午8:16:30
 */
@JsonInclude(Include.NON_NULL)
public class VcardDto {
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 用户的真实名称
	 */
	private String realName;
	
	/**
	 * 用户所在的国家
	 */
	private String country;
	
	/**
	 * 用户的省份
	 */
	private String province;
	
	/**
	 * 用户所在的城市
	 */
	private String city;
	
	/**
	 * 用户所在的街道地址，详细地址
	 */
	private String street;
	
	/**
	 * 手机号码，多个手机号码用";"来隔开
	 */
	private String mobilePhone;
	
	/**
	 * 用户的座机号码
	 */
	private String telephone;
	
	/**
	 * 性别
	 */
	private int gender;
	
	/**
	 * 个性签名
	 */
	private String signature;
	
	/**
	 * 头像的mimeType
	 */
	private String mimeType;
	
	/**
	 * 文件的hash值
	 */
	private String hash;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	@Override
	public String toString() {
		return "VcardDto [username=" + username + ", nickName=" + nickName + ", realName=" + realName + ", country="
				+ country + ", province=" + province + ", city=" + city + ", street=" + street + ", mobilePhone="
				+ mobilePhone + ", telephone=" + telephone + ", gender=" + gender + ", signature=" + signature
				+ ", mimeType=" + mimeType + ", hash=" + hash + "]";
	}
}
