package com.wbb.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
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
	public void beforeOpt(JoinPoint joinPoint) {
		/**
		 * 先查找方法上的注解，没有的话再去查找类上的注解
		 * ---------------------------------------------------------------------
		 * -- 这里使用的是接口的模式，注解在实现类上，所以不能使用如下方式获取目标方法的对象，
		 * 因为该方式获取的是该类的接口或者顶级父类的方法的对象.
		 * MethodSignature methodSignature = (MethodSignature)point.getSignature(); 
		 * Method method = methodSignature.getMethod(); 
		 * DynamicRoutingDataSource annotation = method.getAnnotation(DynamicRoutingDataSource.class);
		 * 通过上面代码是获取不到方法上的注解的，如果真要用上面代码来获取，可以修改aop代理模式，修改为cglib代理 在xml配置文件修改为
		 * <aop:aspectj-autoproxy proxy-target-class="true" /> ，
		 * proxy-target-class属性true为cglib代理，默认false为jdk动态代理 。
		 * ---------------------------------------------------------
		 * 本文使用是jdk动态代理， 这里使用反射的方式获取方法
		 */
		// 反射获取Method 方法一
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
		// 反射获取Method 方法二
		// Object[] args = joinPoint.getArgs();
		// Class<?>[] argTypes = new Class[joinPoint.getArgs().length];
		// for (int i = 0; i < args.length; i++) {
		// argTypes[i] = args[i].getClass();
		// }
		// Method method =
		// joinPoint.getTarget().getClass().getMethod(joinPoint.getSignature().getName(),
		// argTypes);
		// DynamicRoutingDataSource annotation =
		// method.getAnnotation(DynamicRoutingDataSource.class);
		// if (annotation == null) {
		// annotation =
		// joinPoint.getTarget().getClass().getAnnotation(DynamicRoutingDataSource.class);
		// if (annotation == null) {
		// return;
		// }
		// }
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
