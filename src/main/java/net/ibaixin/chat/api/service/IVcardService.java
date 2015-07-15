package net.ibaixin.chat.api.service;

import java.sql.SQLException;
import java.util.List;

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
	 * 保存用户，若用户不存在则添加
	 * @param vcard
	 * @return
	 */
	public Vcard saveVcard(Vcard vcard) throws SQLException;
	
	/**
	 * 添加电子名片信息
	 * @param hash
	 * @return 返回添加后的电子名片信息
	 * @throws SQLException
	 */
	public Vcard addVcard(Vcard vcard) throws SQLException;
	
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
	 * 保存头像信息，若不存在电子名片信息则添加
	 * @param vcard 电子名片实体，主要数据有头像文件的相关信息
	 * @return
	 * @throws SQLException
	 */
	public boolean saveAvatar(Vcard vcard) throws SQLException;
	
	/**
	 * 修改用户头像
	 * @param vcard 电子名片实体，主要数据有头像文件的相关信息
	 * @return
	 * @throws SQLException
	 */
	public boolean updateAvatar(Vcard vcard) throws SQLException;
	
	/**
	 * 获取头像的名称
	 * @update 2015年4月17日 下午2:23:10
	 * @param id
	 * @return
	 */
	public String getAvatarPath(String id) throws SQLException;
	
	/**
	 * 获取头像的信息，包括头像文件的名称和文件mime类型
	 * @param id
	 * @return
	 * @throws SQLException
	 * @update 2015年7月15日 下午9:16:49
	 */
	public Vcard getAvatarInfo(String id) throws SQLException;
	
	/**
	 * 保存昵称，若电子名片信息不存在，则添加
	 * @update 2015年4月17日 下午2:23:44
	 * @param nickName
	 * @param id
	 */
	public boolean saveNickName(String nickName, String id) throws SQLException;
	
	/**
	 * 修改昵称
	 * @param nickName 用户昵称
	 * @param id 用户名（主键）
	 * @return
	 * @throws SQLException
	 */
	public boolean updateNickName(String nickName, String id) throws SQLException;
	
	/**
	 * 保存性别
	 * @update 2015年4月17日 下午2:24:21
	 * @param gender
	 * @param id
	 */
	public boolean saveGender(Gender gender, String id) throws SQLException;
	
	/**
	 * 修改性别信息
	 * @param gender 性别
	 * @param id 用户名（主键）
	 * @return
	 * @throws SQLException
	 */
	public boolean updateGender(Gender gender, String id) throws SQLException;
	
	/**
	 * 保存地址，若不存在电子名片信息则添加
	 * @update 2015年4月17日 下午2:24:51
	 * @param vcard
	 */
	public boolean saveAddress(Vcard vcard) throws SQLException;
	
	/**
	 * 修改地址信息
	 * @param vcard
	 * @return
	 * @throws SQLException
	 */
	public boolean updateAddress(Vcard vcard) throws SQLException;
	
	/**
	 * 保存个性签名
	 * @update 2015年4月17日 下午2:25:23
	 * @param signature
	 * @param id
	 */
	public boolean saveSignature(String signature, String id) throws SQLException;
	
	/**
	 * 修改个性签名
	 * @param signature 个性签名
	 * @param id 用户名（主键）
	 * @return
	 * @throws SQLException
	 */
	public boolean updateSignature(String signature, String id) throws SQLException;
	
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
	
	/**
	 * 根据主键的集合查询对应的电子名片基本信息
	 * @author tiger
	 * @version 1.0.0
	 * @update 2015年4月18日 下午6:03:05
	 * @param ids
	 * @return
	 */
	public List<Vcard> getVcardByIds(List<String> ids);
}
