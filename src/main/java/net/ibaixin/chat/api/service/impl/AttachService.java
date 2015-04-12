package net.ibaixin.chat.api.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ibaixin.chat.api.dao.AttachDao;
import net.ibaixin.chat.api.model.Attachment;
import net.ibaixin.chat.api.service.IAttachService;

/**
 * 附件的service层
 * @author tiger
 *
 */
@Service("attachService")
public class AttachService implements IAttachService {
	private static final Logger logger = Logger.getLogger(AttachService.class);
	
	@Autowired
	private AttachDao attachDao;

	@Override
	public boolean addAttach(Attachment attachment) {
		boolean flag = false;
		try {
			attachDao.addAttach(attachment);
			logger.info("-------添加-------" + attachment.getId() + "-------成功");
			flag = true;
		} catch (Exception e) {
			long count = attachDao.getAttachmentCount(attachment.getId());
			flag = count > 0;
			if (flag) {
				logger.info("-------该文件已存在-------");
			} else {
				logger.error("-------添加-------" + attachment.getId() + "-------失败");
			}
//			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean deleteAttach(String id) {
		try {
			attachDao.deleteAttach(id);
			logger.info("-------删除-------" + id + "-------成功");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("-------删除-------" + id + "-------失败");
		}
		return false;
	}

	@Override
	public Attachment getAttachment(String id) {
		Attachment attachment = attachDao.getAttachment(id);
		logger.info("-------获得-------" + attachment);
		return attachment;
	}

}
