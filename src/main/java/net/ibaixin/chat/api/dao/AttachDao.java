package net.ibaixin.chat.api.dao;

import java.sql.SQLException;

import net.ibaixin.chat.api.model.Attachment;

/**
 * 操作Attachment的持久层
 * @author tiger
 *
 */
public interface AttachDao {
	/**
	 * 添加附件
	 * @param attachment
	 */
	public void addAttach(Attachment attachment) throws SQLException;
	
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
	
	/**
	 * 根据id查询指定的附件的数量
	 * @param id
	 * @return
	 */
	public long getAttachmentCount(String id);
}
