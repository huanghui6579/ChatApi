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
	public int addVcard(Vcard vcard);
	
	/**
	 * 更新用户电子名片
	 * @param vcard
	 * @return
	 */
	public int updateVcard(Vcard vcard);
	
	/**
	 * 根据主键删除电子名片
	 * @param id
	 */
	public int deleteVcard(String id);
	
	/**
	 * 根据主键获得对应的电子名片信息
	 * @param id
	 * @return
	 */
	public Vcard getVcard(String id);
	
	/**
	 * 获取用户的手机号
	 * @update 2015年4月16日 下午2:53:30
	 * @param id
	 * @return
	 */
	public String getMoblePhone(String id);
	
	/**
	 * 获得用户的座机号
	 * @update 2015年4月16日 下午2:53:47
	 * @param id
	 * @return
	 */
	public String getTelephone(String id);
	
	/**
	 * 根据主键获取该电子名片的数量，主要、用作判断该电子名片信息是否存在
	 * @update 2015年4月16日 下午3:10:14
	 * @param id
	 * @return
	 */
	public long getVcardCountById(String id);
	
	/**
	 * 保存或更新用户头像的存储文件名或路径
	 * @update 2015年4月17日 上午9:09:23
	 * @param avatarPath
	 */
	public int updateAvatar(Vcard vcard);
	
	/**
	 * 获取用户头像的存储名称或者路径
	 * @update 2015年4月17日 上午9:11:11
	 * @param username
	 * @return
	 */
	public String getAvatarPath(String username);
	
	/**
	 * 保存用户昵称
	 * @update 2015年4月17日 上午9:12:20
	 * @param nickname
	 */
	public int updateNickName(Vcard vcard);
	
	/**
	 * 保存用户性别
	 * @update 2015年4月17日 上午9:13:15
	 * @param gender
	 */
	public int updateGender(Vcard vcard);
	
	/**
	 * 保存用户地址
	 * @update 2015年4月17日 上午9:26:41
	 * @param vcard
	 */
	public int updateAddress(Vcard vcard);
	
	/**
	 * 保存用户签名
	 * @update 2015年4月17日 上午9:27:42
	 * @param signature
	 */
	public int updateSignature(Vcard vcard);
	
	/**
	 * 获取该用户头像的hash值
	 * @update 2015年4月17日 下午6:27:50
	 * @param id
	 * @return
	 */
	public String getAvatarHash(String id);
	
	/**
	 * 根据主键的集合查询对应的头像的hash值
	 * @author tiger
	 * @version 1.0.0
	 * @update 2015年4月18日 下午6:03:05
	 * @param ids
	 * @return
	 */
	public List<Vcard> getVcards(List<String> ids);
}
