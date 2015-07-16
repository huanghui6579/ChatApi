package net.ibaixin.chat.api.dao;

import java.util.List;

import net.ibaixin.chat.api.model.Vcard;

/**
 * 用户头像的持久层
 * @author huanghui1
 * @version 1.0.0
 * @update 2015年4月16日 下午2:34:04
 */
public interface VcardDao {
	/**
	 * 添加用户
	 * @param vcard
	 * @return
	 */
	public int addVcard(Vcard vcard) throws Exception;
	
	/**
	 * 更新用户电子名片
	 * @param vcard
	 * @return
	 */
	public int updateVcard(Vcard vcard) throws Exception;
	
	/**
	 * 根据主键删除电子名片
	 * @param id
	 */
	public int deleteVcard(String id) throws Exception;
	
	/**
	 * 根据主键获得对应的电子名片信息
	 * @param id
	 * @return
	 */
	public Vcard getVcard(String id) throws Exception;
	
	/**
	 * 获取用户的手机号
	 * @update 2015年4月16日 下午2:53:30
	 * @param id
	 * @return
	 */
	public String getMoblePhone(String id) throws Exception;
	
	/**
	 * 获得用户的座机号
	 * @update 2015年4月16日 下午2:53:47
	 * @param id
	 * @return
	 */
	public String getTelephone(String id) throws Exception;
	
	/**
	 * 根据主键获取该电子名片的数量，主要、用作判断该电子名片信息是否存在
	 * @update 2015年4月16日 下午3:10:14
	 * @param id
	 * @return
	 */
	public long getVcardCountById(String id) throws Exception;
	
	/**
	 * 保存或更新用户头像的存储文件名或路径
	 * @update 2015年4月17日 上午9:09:23
	 * @param avatarPath
	 */
	public int updateAvatar(Vcard vcard) throws Exception;
	
	/**
	 * 获取用户头像的存储名称或者路径
	 * @update 2015年4月17日 上午9:11:11
	 * @param username
	 * @return
	 */
	public String getAvatarPath(String username) throws Exception;
	
	/**
	 * 获取用户头像文件信息，包括存储名称和mime类型
	 * @param username
	 * @return
	 * @update 2015年7月15日 下午9:19:49
	 */
	public Vcard getAvatarInfo(String username) throws Exception;
	
	/**
	 * 保存用户昵称
	 * @update 2015年4月17日 上午9:12:20
	 * @param nickname
	 */
	public int updateNickName(Vcard vcard) throws Exception;
	
	/**
	 * 保存用户性别
	 * @update 2015年4月17日 上午9:13:15
	 * @param gender
	 */
	public int updateGender(Vcard vcard) throws Exception;
	
	/**
	 * 保存用户地址
	 * @update 2015年4月17日 上午9:26:41
	 * @param vcard
	 */
	public int updateAddress(Vcard vcard) throws Exception;
	
	/**
	 * 保存用户签名
	 * @update 2015年4月17日 上午9:27:42
	 * @param signature
	 */
	public int updateSignature(Vcard vcard) throws Exception;
	
	/**
	 * 获取该用户头像的hash值
	 * @update 2015年4月17日 下午6:27:50
	 * @param id
	 * @return
	 */
	public String getAvatarHash(String id) throws Exception;
	
	/**
	 * 根据主键的集合查询对应的头像的hash值
	 * @author tiger
	 * @version 1.0.0
	 * @update 2015年4月18日 下午6:03:05
	 * @param ids
	 * @return
	 */
	public List<Vcard> getVcards(List<String> ids) throws Exception;
	
	/**
	 * 判断是否存在该用户的电子名片信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @update 2015年7月16日 上午11:38:17
	 */
	public long countVcardById(String id) throws Exception;
}
