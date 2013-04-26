package me.xiaopan.androidlibrary.net;

import java.lang.reflect.Field;

import me.xiaopan.javalibrary.net.HttpRequest;
import me.xiaopan.javalibrary.net.HttpRequestMethod;
import me.xiaopan.javalibrary.util.AnnotationUtils;
import me.xiaopan.javalibrary.util.ClassUtils;
import me.xiaopan.javalibrary.util.StringUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 访问网络工具箱
 */
public class AccessNetworkUtils {
	/**
	 * 将一个Request对象转换为HttpRequest对象
	 * @param request
	 * @param defaultHostServerAddress 默认的主机地址，挡在request对象上取不到HostAddress注解时将使用此值
	 * @return null：转换失败，出现异常，可能是没有Path注解
	 * @throws Exception 
	 */
	public static HttpRequest toHttpRequest(Request request) throws Exception{
		Class<? extends Request> requestClass = request.getClass();
		
		/*
		 * 组织HttpRequest对象
		 */
		//从请求对象中获取主机地址
		Host host = requestClass.getAnnotation(Host.class);
		if(host == null || !StringUtils.isNotNullAndEmpty(host.value())){
			throw new Exception("Request上没有Host注解");
		}
		//尝试取得Path注解的值，如果没有就就直接返回null
		Path path = requestClass.getAnnotation(Path.class);
		if(path == null || !StringUtils.isNotNullAndEmpty(path.value())){
			throw new Exception("Request上没有Path注解");
		}
		HttpRequest httpRequest = new HttpRequest(host.value() + (StringUtils.startsWithIgnoreCase(host.value(), "/")?"":"/") + path.value());
		
		//如果有Post注解就设置请求方式为POST
		if(AnnotationUtils.contain(requestClass, Post.class)){
			httpRequest.setRequestMethod(HttpRequestMethod.POST);
		}
		
		//循环处理所有字段
		String paramValue;
		String paramName;
		Object valueObject;
		for(Field field : ClassUtils.getFileds(requestClass, true, true, true)){
			try {
				/* 如果当前字段被标记为需要序列化 */
				if(AnnotationUtils.contain(field, Expose.class)){
					//初始化参数值
					field.setAccessible(true);
					valueObject = field.get(request);
					paramValue = valueObject != null?valueObject.toString():null;
					if(StringUtils.isNotNullAndEmpty(paramValue)){
						//初始化参数名
						SerializedName serializedName = field.getAnnotation(SerializedName.class);
						paramName = (serializedName != null && StringUtils.isNotNullAndEmpty(serializedName.value()))?serializedName.value():field.getName();
						
						//添加请求参数
						httpRequest.addParameter(paramName, paramValue);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return httpRequest;
	}
}