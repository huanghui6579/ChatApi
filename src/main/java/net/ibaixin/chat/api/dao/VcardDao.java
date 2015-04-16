package net.ibaixin.chat.api.dao;

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
	public void addVcard(Vcard vcard);
	
	/**
	 * 更新用户电子名片
	 * @param vcard
	 * @return
	 */
	public void updateVcard(Vcard vcard);
	
	/**
	 * 根据主键删除电子名片
	 * @param id
	 */
	public void deleteVcard(String id);
	
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
}
