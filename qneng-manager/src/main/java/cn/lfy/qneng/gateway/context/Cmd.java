package cn.lfy.qneng.gateway.context;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Cmd {

	/**
	 * 请求消息包ID
	 * @return
	 */
	int in();
	/**
	 * 请求回复消息包ID
	 * @return
	 */
	int out() default 0;
	/**
	 * 是否异步
	 * @return
	 */
	boolean async() default false;
}
