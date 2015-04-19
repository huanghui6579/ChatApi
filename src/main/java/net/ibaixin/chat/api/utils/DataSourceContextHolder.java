package net.ibaixin.chat.api.utils;

import org.springframework.util.Assert;

public class DataSourceContextHolder {
	private static final ThreadLocal<String> contextHolder = 
		new InheritableThreadLocal<String>();
	
	public static void setTargetDataSource(String targetDataSource){
		Assert.notNull(targetDataSource, "Target data source cannot be null");
		contextHolder.set(targetDataSource);
	}
	
	public static String getTargetDataSource(){
		return contextHolder.get();
	}
	
	public static void resetDefaultDataSource(){
		contextHolder.remove();
	}
	
	public enum DataSourceEnum {
		openfireDataSource,
		apiDataSource;
	}
}