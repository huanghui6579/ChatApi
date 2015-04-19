package net.ibaixin.chat.api.service.impl;

import java.sql.SQLException;
import java.util.List;

import net.ibaixin.chat.api.dao.VcardDao;
import net.ibaixin.chat.api.model.Vcard;
import net.ibaixin.chat.api.model.Vcard.Gender;
import net.ibaixin.chat.api.service.IVcardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户电子名片的业务逻辑层实现层
 * @author huanghui1
 * @version 1.0.0
 * @update 2015年4月16日 下午3:06:05
 */
@Service("vcardService")
public class VcardService implements IVcardService {
	
	@Autowired
	private VcardDao vcardDao;

	@Override
	public Vcard saveVcard(Vcard vcard) throws SQLException {
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
	public boolean updateVcard(Vcard vcard) throws SQLException {
		int count = vcardDao.updateVcard(vcard);
		return count > 0;
	}

	@Override
	public boolean deleteVcard(String id) throws SQLException {
		int count = vcardDao.deleteVcard(id);
		return count > 0;
	}

	@Override
	public Vcard getVcard(String id) throws SQLException {
		return vcardDao.getVcard(id);
	}

	@Override
	public boolean saveAvatar(String avatarPath, String hash, String id) throws SQLException {
		Vcard vcard = new Vcard();
		vcard.setAvatarPath(avatarPath);
		vcard.setUsername(id);
		vcard.setHash(hash);
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
	public String getAvatarPath(String id) throws SQLException {
		return vcardDao.getAvatarPath(id);
	}

	@Override
	public boolean saveNickName(String nickName, String id) throws SQLException {
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
	public boolean saveGender(Gender gender, String id) throws SQLException {
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
	public boolean saveAddress(Vcard vcard) throws SQLException {
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
	public boolean saveSignature(String signature, String id) throws SQLException {
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
	public boolean hasVcard(String id) throws SQLException {
		long count = vcardDao.getVcardCountById(id);
		return count > 0;
	}

	@Override
	public String getAvatarHash(String id) throws SQLException {
		return vcardDao.getAvatarHash(id);
	}

	@Override
	public List<Vcard> getVcardByIds(List<String> ids) {
		return vcardDao.getVcards(ids);
	}
}
