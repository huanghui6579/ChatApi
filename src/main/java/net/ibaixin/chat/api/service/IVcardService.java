package net.ibaixin.chat.api.service;

import net.ibaixin.chat.api.model.Vcard;

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
	public Vcard saveVcard(Vcard vcard);
	
	/**
	 * 更新用户电子名片
	 * @param vcard
	 * @return
	 */
	public Vcard updateVcard(Vcard vcard);
	
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
}
