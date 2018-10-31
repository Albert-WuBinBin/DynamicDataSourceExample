package com.wbb.dataSource.dynamic;

/**
 * 数据源操作类
 */
public class DataSourceContext {
	
	private static ThreadLocal<DataSourceBean> threadLocal = new InheritableThreadLocal<DataSourceBean>();

	/**
	 * 获取数据源
	 */
	public static DataSourceBean getDataSource() {
		return threadLocal.get();
	}
	/**
	 * 设置数据源
	 */
	public static void setDataSource(DataSourceBean dataSourceBean) {
		threadLocal.set(dataSourceBean);
	}
	/**
	 * 清除数据源
	 * 清除后,数据源为默认时间
	 */
	public static void toDefault() {
		threadLocal.remove();
	}
}
