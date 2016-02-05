package net.ibaixin.chat.api.controller.home;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.ibaixin.chat.api.model.ActionResult;
import net.ibaixin.chat.api.model.home.HomeComment;
import net.ibaixin.chat.api.service.home.ICommentService;

/**
 * 
 * @author huanghui1
 * @version 1.0.0
 * @update 2016年2月5日 下午3:32:58
 */
@Controller
@RequestMapping("/home")
public class CommentController {
	private static Logger logger = Logger.getLogger(CommentController.class);
	
	@Autowired
	private ICommentService commentService;
	
	@RequestMapping(value = "/submitComment", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<Void> submitComment(@RequestBody HomeComment homeComment) {
		ActionResult<Void> result = new ActionResult<>();
		if (commentService.addComment(homeComment)) {
			result.setResultCode(ActionResult.CODE_SUCCESS);
		} else {
			result.setResultCode(ActionResult.CODE_ERROR);
		}
		return result;
	}
}
