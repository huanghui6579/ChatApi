package net.ibaixin.chat.api.service.home;

import net.ibaixin.chat.api.model.home.HomeComment;

/**
 * 用户意见反馈的服务
 * @author huanghui1
 * @version 1.0.0
 * @update 2016年2月5日 下午2:18:57
 */
public interface ICommentService {
	/**
	 * 提交反馈
	 * @param comment
	 * @update 2016年2月5日 下午2:19:47
	 */
	public boolean addComment(HomeComment comment);
}
