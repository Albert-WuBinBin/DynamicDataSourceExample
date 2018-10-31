package com.wbb.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.wbb.annotation.DynamicRoutingDataSource;
import com.wbb.dataSource.MultiDataSource;

@Aspect
@Component
public class HandlerDataSourceAop {

	/**
	 * @within匹配类上的注解
	 * @annotation匹配方法上的注解
	 */
	@Pointcut("@within(com.wbb.annotation.DynamicRoutingDataSource)||@annotation(com.wbb.annotation.DynamicRoutingDataSource)")
	public void pointcut() {
	}

	@Before(value = "pointcut()")
	public void beforeOpt1(JoinPoint joinPoint) {
		Object target = joinPoint.getTarget();
		Class<?> clazz = target.getClass();
		Method[] methods = clazz.getMethods();
		DynamicRoutingDataSource annotation = null;
		for (Method method : methods) {
			if (joinPoint.getSignature().getName().equals(method.getName())) {
				annotation = method.getAnnotation(DynamicRoutingDataSource.class);
				if (annotation == null) {
					annotation = joinPoint.getTarget().getClass().getAnnotation(DynamicRoutingDataSource.class);
					if (annotation == null) {
						return;
					}
				}
			}
		}
		String dataSourceName = annotation.value();
		MultiDataSource.setDataSourceKey(dataSourceName);
		System.out.println("切到" + dataSourceName + "数据库");
	}

	@After(value = "pointcut()")
	public void afterOpt() {
		MultiDataSource.toDefault();
		System.out.println("切回默认数据库");
	}
}
