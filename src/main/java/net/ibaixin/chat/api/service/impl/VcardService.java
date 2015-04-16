package net.ibaixin.chat.api.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ibaixin.chat.api.dao.VcardDao;
import net.ibaixin.chat.api.model.Vcard;
import net.ibaixin.chat.api.service.IVcardService;

/**
 * 用户电子名片的业务逻辑层实现层
 * @author huanghui1
 * @version 1.0.0
 * @update 2015年4月16日 下午3:06:05
 */
@Service("vcardService")
public class VcardService implements IVcardService {
	private static Logger logger = Logger.getLogger(VcardService.class);
	
	@Autowired
	private VcardDao vcardDao;

	@Override
	public Vcard saveVcard(Vcard vcard) {
		long count = vcardDao.getVcardCountById(vcard.getUsername());
		if (count > 0) {	//已经存在该电子名片信息,则更新
			updateVcard(vcard);
		} else {	//添加
			vcardDao.addVcard(vcard);
			logger.info("----添加了----" + vcard);
		}
		return vcard;
	}

	@Override
	public Vcard updateVcard(Vcard vcard) {
		logger.info("----更新了----" + vcard);
		vcardDao.updateVcard(vcard);
		return vcard;
	}

	@Override
	public void deleteVcard(String id) {
		logger.info("----删除了----" + id);
		vcardDao.deleteVcard(id);
	}

	@Override
	public Vcard getVcard(String id) {
		return vcardDao.getVcard(id);
	}
}
