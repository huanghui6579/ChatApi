package net.ibaixin.chat.api.dao;

import java.util.List;
import java.util.Map;

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
	 * 根据主键批量删除电子名片
	 * @param ids
	 */
	public int deleteVcards(String[] ids) throws Exception;
	
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
	 * 分页获取电子名片列表
	 * @param param 参数集合，主要有offset:页面的索引位置，从0开始;limit:每页显示的数量
	 * @return
	 * @throws Exception
	 * @update 2015年7月22日 下午3:27:59
	 */
	public List<Vcard> getVcardList(Map<String, Integer> param) throws Exception;
	
	/**
	 * 获取电子名片列表
	 * @param param 参数集合，主要有offset:页面的索引位置，从0开始;limit:每页显示的数量
	 * @return
	 * @throws Exception
	 * @update 2015年7月22日 下午3:27:59
	 */
	public List<Vcard> getVcardListAll() throws Exception;
	
	/**
	 * 判断是否存在该用户的电子名片信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @update 2015年7月16日 上午11:38:17
	 */
	public long countVcardById(String id) throws Exception;
	
	/**
	 * 根据用户名数组获取对应的简单的电子名片信息，只包括头像的hash，昵称，性别
	 * @param ids 用户名的数组
	 * @return
	 * @update 2015年7月30日 下午3:45:21
	 */
	public List<Vcard> getSimpleVcardByIds(String[] ids) throws Exception;
	
	/**
	 * 根据用户名数组获取对应的简单的电子名片信息，只包括头像的hash，昵称，性别
	 * @param id 主键，username
	 * @return
	 * @throws Exception
	 * @update 2015年9月28日 下午3:56:36
	 */
	public Vcard getSimpleVcardById(String id) throws Exception;
}
