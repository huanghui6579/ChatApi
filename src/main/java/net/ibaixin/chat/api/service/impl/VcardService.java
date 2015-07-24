package net.ibaixin.chat.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import net.ibaixin.chat.api.controller.UserController;
import net.ibaixin.chat.api.dao.VcardDao;
import net.ibaixin.chat.api.model.Vcard;
import net.ibaixin.chat.api.model.Vcard.Gender;
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
	public Vcard saveVcard(Vcard vcard) throws Exception {
		int count = 0;
		if (updateVcard(vcard)) {	//更新电子名片成功
			return vcard;
		} else {	//更新失败，添加
			count = vcardDao.addVcard(vcard);
			if (count > 0) {
				return vcard;
			} else {
				return null;
			}
		}
	}

	@Override
	public boolean updateVcard(Vcard vcard) throws Exception {
		int count = vcardDao.updateVcard(vcard);
		return count > 0;
	}

	@Override
	public boolean deleteVcard(String id) throws Exception {
		int count = vcardDao.deleteVcard(id);
		return count > 0;
	}

	@Override
	public Vcard getVcard(String id) throws Exception {
		return vcardDao.getVcard(id);
	}

	@Override
	public boolean saveAvatar(Vcard vcard) throws Exception {
		int count = 0;
		boolean success = false;
		count = vcardDao.updateAvatar(vcard);
		if (count > 0) {
			success = true;
		} else {
			count = vcardDao.addVcard(vcard);
			if (count > 0) {
				success = true;
			}
		}
		return success;
	}

	@Override
	public String getAvatarPath(String id) throws Exception {
		return vcardDao.getAvatarPath(id);
	}

	@Override
	public boolean saveNickName(String nickName, String id) throws Exception {
		Vcard vcard = new Vcard();
		vcard.setNickName(nickName);
		vcard.setUsername(id);
		int count = 0;
		boolean success = false;
		count = vcardDao.updateNickName(vcard);
		if (count > 0) {	//更新成功
			success = true;
		} else {
			count = vcardDao.addVcard(vcard);
			if (count > 0) {	//更新成功
				success = true;
			}
		}
		return success;
	}

	@Override
	public boolean saveGender(Gender gender, String id) throws Exception {
		Vcard vcard = new Vcard();
		vcard.setGender(gender);
		vcard.setUsername(id);
		int count = 0;
		boolean success = false;
		count = vcardDao.updateGender(vcard);
		if (count > 0) {
			success = true;
		} else {
			count = vcardDao.addVcard(vcard);
			if (count > 0) {
				success = true;
			}
		}
		return success;
	}

	@Override
	public boolean saveAddress(Vcard vcard) throws Exception {
		int count = 0;
		boolean success = false;
		count = vcardDao.updateAddress(vcard);
		if (count > 0) {
			success = true;
		} else {
			count = vcardDao.addVcard(vcard);
			if (count > 0) {
				success = true;
			}
		}
		return success;
	}

	@Override
	public boolean saveSignature(String signature, String id) throws Exception {
		Vcard vcard = new Vcard();
		vcard.setSignature(signature);
		vcard.setUsername(id);
		int count = 0;
		boolean success = false;
		count = vcardDao.updateSignature(vcard);
		if (count > 0) {
			success = true;
		} else {
			count = vcardDao.addVcard(vcard);
			if (count > 0) {
				success = true;
			}
		}
		return success;
	}

	@Override
	public boolean hasVcard(String id) throws Exception {
		long count = vcardDao.getVcardCountById(id);
		return count > 0;
	}

	@Override
	public String getAvatarHash(String id) throws Exception {
		return vcardDao.getAvatarHash(id);
	}

	@Override
	public List<Vcard> getVcardByIds(List<String> ids) throws Exception {
		return vcardDao.getVcards(ids);
	}

	@Override
	public Vcard addVcard(Vcard vcard) throws Exception {
		logger.info("----addVcard----" + vcard);
		if (vcard != null) {
			boolean existsVcard = existsVcard(vcard.getUsername());
			if (existsVcard) {	//该用户名已经存在
				return null;
			} else {
				int count = vcardDao.addVcard(vcard);
				if (count > 0) {
					return vcard;
				} else {
					return null;
				}
			}
		} else {
			return null;
		}
	}

	@Override
	public boolean updateAvatar(Vcard vcard) throws Exception {
		int count = vcardDao.updateAvatar(vcard);
		return count > 0;
	}

	@Override
	public boolean updateNickName(String nickName, String id) throws Exception {
		Vcard vcard = new Vcard();
		vcard.setNickName(nickName);
		vcard.setUsername(id);
		int count = vcardDao.updateNickName(vcard);
		return count > 0;
	}

	@Override
	public boolean updateGender(Gender gender, String id) throws Exception {
		Vcard vcard = new Vcard();
		vcard.setGender(gender);
		vcard.setUsername(id);
		int count = vcardDao.updateGender(vcard);
		return count > 0;
	}

	@Override
	public boolean updateAddress(Vcard vcard) throws Exception {
		if (vcard != null) {
			int count = vcardDao.updateAddress(vcard);
			return count > 0;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateSignature(String signature, String id) throws Exception {
		Vcard vcard = new Vcard();
		vcard.setSignature(signature);
		vcard.setUsername(id);
		int count = vcardDao.updateSignature(vcard);
		return count > 0;
	}

	@Override
	public Vcard getAvatarInfo(String id) throws Exception {
		return vcardDao.getAvatarInfo(id);
	}

	@Override
	public boolean existsVcard(String username) throws Exception {
		if (StringUtils.isNotEmpty(username)) {
			long count = vcardDao.countVcardById(username);
			return count > 0;
		} else {
			return false;
		}
	}

	@Override
	public List<Vcard> getVcardList(int pageOffset, int pageCount) throws Exception {
		Map<String, Integer> param = new HashMap<>();
		param.put("offset", pageOffset);
		param.put("limit", pageCount);
		return vcardDao.getVcardList(param);
	}

	@Override
	public boolean deleteVcards(String[] ids) throws Exception {
		if (!ArrayUtils.isEmpty(ids)) {
			int count = vcardDao.deleteVcards(ids);
			return count > 0;
		} else {
			return false;
		}
	}

	@Override
	public List<Vcard> getVcardListAll() throws Exception {
		return vcardDao.getVcardListAll();
	}
}
