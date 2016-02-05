package net.ibaixin.chat.api.service.home.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ibaixin.chat.api.dao.home.CommentDao;
import net.ibaixin.chat.api.model.home.HomeComment;
import net.ibaixin.chat.api.service.home.ICommentService;

/**
 * 用户反馈服务的具体实现
 * @author huanghui1
 * @version 1.0.0
 * @update 2016年2月5日 下午2:20:56
 */
@Service("commentService")
public class CommentService implements ICommentService {
	private static final Logger logger = Logger.getLogger(CommentService.class);
	
	@Autowired
	private CommentDao commentDao;

	@Override
	public boolean addComment(HomeComment comment) {
		boolean falg = false;
		try {
			commentDao.addComment(comment);
			falg = true;
			logger.debug("---CommentService----addComment----success---" + comment);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return falg;
	}

}
