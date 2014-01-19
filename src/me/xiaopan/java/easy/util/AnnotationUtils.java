package me.xiaopan.java.easy.util;

import java.lang.annotation.Annotation;

/**
 * 注解工具箱
 */
public class AnnotationUtils {
	/**
	 * 获取一个枚举上的指定类型的注解
	 * @param enumObject 给定的枚举
	 * @param annoitaion 指定类型的注解
	 * @return
	 */
	public static final <T extends Annotation> T getAnnotationFromEnum(Enum<?> enumObject, Class<T> annoitaion){
		try {
			return (T) enumObject.getClass().getField(enumObject.name()).getAnnotation(annoitaion);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}