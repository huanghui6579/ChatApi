package net.ibaixin.chat.api.service;

import net.ibaixin.chat.api.model.Attachment;

public interface IAttachService {
	/**
	 * 添加附件
	 * @param attachment
	 */
	public boolean saveAttach(Attachment attachment);
	
	/**
	 * 根据ID删除附件
	 * @param id
	 * @return 是否删除成功
	 */
	public boolean deleteAttach(String id);
	
	/**
	 * 根据附件ID获取对应的附件
	 * @param id
	 * @return 附件信息
	 */
	public Attachment getAttachment(String id);
	
}
