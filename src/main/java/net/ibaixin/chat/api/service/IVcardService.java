package net.ibaixin.chat.api.service;

import java.sql.SQLException;

import net.ibaixin.chat.api.model.Vcard;
import net.ibaixin.chat.api.model.Vcard.Gender;

/**
 * 用户电子名片的业务逻辑层
 * @author huanghui1
 * @version 1.0.0
 * @update 2015年4月16日 下午3:05:05
 */
public interface IVcardService {
	/**
	 * 添加用户
	 * @param vcard
	 * @return
	 */
	public Vcard saveVcard(Vcard vcard) throws SQLException;
	
	/**
	 * 更新用户电子名片
	 * @param vcard
	 * @return
	 */
	public boolean updateVcard(Vcard vcard) throws SQLException;
	
	/**
	 * 根据主键删除电子名片
	 * @param id
	 */
	public boolean deleteVcard(String id) throws SQLException;
	
	/**
	 * 根据主键获得对应的电子名片信息
	 * @param id
	 * @return
	 */
	public Vcard getVcard(String id) throws SQLException;
	
	/**
	 * 保存头像信息
	 * @update 2015年4月17日 下午2:22:16
	 * @param avatarPath
	 * @param username
	 */
	public boolean saveAvatar(String avatarPath, String hash, String id) throws SQLException;
	
	/**
	 * 获取头像的信息
	 * @update 2015年4月17日 下午2:23:10
	 * @param id
	 * @return
	 */
	public String getAvatarPath(String id) throws SQLException;
	
	/**
	 * 保存昵称
	 * @update 2015年4月17日 下午2:23:44
	 * @param nickName
	 * @param id
	 */
	public boolean saveNickName(String nickName, String id) throws SQLException;
	
	/**
	 * 保存性别
	 * @update 2015年4月17日 下午2:24:21
	 * @param gender
	 * @param id
	 */
	public boolean saveGender(Gender gender, String id) throws SQLException;
	
	/**
	 * 保存地址
	 * @update 2015年4月17日 下午2:24:51
	 * @param vcard
	 */
	public boolean saveAddress(Vcard vcard) throws SQLException;
	
	/**
	 * 保存个性签名
	 * @update 2015年4月17日 下午2:25:23
	 * @param signature
	 * @param id
	 */
	public boolean saveSignature(String signature, String id) throws SQLException;
	
	/**
	 * 查询该用户是否有电子名片信息
	 * @update 2015年4月17日 下午2:27:11
	 * @param id
	 * @return
	 */
	public boolean hasVcard(String id) throws SQLException;
	
	/**
	 * 获取该用户头像的hash值
	 * @update 2015年4月17日 下午6:27:50
	 * @param id
	 * @return
	 */
	public String getAvatarHash(String id) throws SQLException;
}
