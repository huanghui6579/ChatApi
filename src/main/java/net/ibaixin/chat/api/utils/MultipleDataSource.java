package net.ibaixin.chat.api.utils;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 多数据源
 * @author tiger
 * @version 1.0.0
 * @update 2015年4月18日 上午9:25:50
 */
public class MultipleDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getTargetDataSource();
	}

}
