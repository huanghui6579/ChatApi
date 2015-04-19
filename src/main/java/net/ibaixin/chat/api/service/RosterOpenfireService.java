package net.ibaixin.chat.api.service;

import java.util.ArrayList;
import java.util.List;

import net.ibaixin.chat.api.dao.RosterOpenfireDao;
import net.ibaixin.chat.api.service.impl.IRosterOpenfireService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 
 * @author tiger
 * @version 2015年4月18日 上午11:25:09
 */
@Service("rosterOpenfireService")
public class RosterOpenfireService implements IRosterOpenfireService {
	@Autowired
	private RosterOpenfireDao rosterOpenfireService;

	@Override
	public List<String> getRosters(String username) {
		List<String> users = rosterOpenfireService.getRosters(username);
		if (!CollectionUtils.isEmpty(users)) {
			List<String> list = new ArrayList<>();
			for (String name : users) {
				int index = name.lastIndexOf("@");
				if (index != -1) {
					name = name.substring(0, index);
					list.add(name);
				}
			}
			return list;
		}
		return null;
	}
}
