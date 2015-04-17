package net.ibaixin.chat.api.model;

import java.io.Serializable;

/**
 * 用户的电子名片
 * @author huanghui1
 *
 */
public class Vcard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public enum Gender {
		/**
		 * 性别未知
		 */
		UNKOWN(0),
		/**
		 * 男
		 */
		MAN(1),
		/**
		 * 女
		 */
		WOMAN(2);
		
		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		private Gender(final int pValue) {
			this.value = pValue;
		}
	}

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
	 * 用户头像的原始图片的下载地址
	 */
	private String avatarPath;
	
	/**
	 * 性别
	 */
	private Gender gender = Gender.UNKOWN;
	
	/**
	 * 个性签名
	 */
	private String signature;
	
	/**
	 * 文件的hash值
	 */
	private String hash;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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

	public String getAvatarPath() {
		return avatarPath;
	}

	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@Override
	public String toString() {
		return "Vcard [username=" + username + ", nickName=" + nickName
				+ ", realName=" + realName + ", country=" + country
				+ ", province=" + province + ", city=" + city + ", street="
				+ street + ", mobilePhone=" + mobilePhone + ", telephone="
				+ telephone + ", avatarPath=" + avatarPath + ", gender="
				+ gender + ", signature=" + signature + ", hash=" + hash + "]";
	}
}
