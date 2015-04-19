package net.ibaixin.chat.api.aop;

import net.ibaixin.chat.api.utils.DataSourceContextHolder;
import net.ibaixin.chat.api.utils.DataSourceContextHolder.DataSourceEnum;

import org.aspectj.lang.JoinPoint;

/**
 * 数据源的切面
 * @author tiger
 * @version 2015年4月18日 上午9:53:34
 */
public class DataSourceOperation {
	
	public void doBefore(JoinPoint point) {
		org.aspectj.lang.Signature signature = point.getSignature();
		if (signature.getDeclaringTypeName().endsWith("OpenfireService")) {	//openfire数据源
			DataSourceContextHolder.setTargetDataSource(DataSourceEnum.openfireDataSource.toString());
		} else {	//api的数据源
			DataSourceContextHolder.setTargetDataSource(DataSourceEnum.apiDataSource.toString());
		}
	}
}
