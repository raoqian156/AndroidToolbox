package me.xiaopan.androidlibrary.net;

import java.lang.reflect.Field;
import java.util.Map;

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
	 * 将一个请求对象转换为HttpRequest对象
	 * @param requestObject 请求对象
	 * @return 
	 * @throws Exception 没有Path或者没有Host注解
	 */
	public static HttpRequest toHttpRequest(Object requestObject) throws Exception{
		Class<?> requestClass = requestObject.getClass();
		
		//尝试从请求对象中获取主机地址
		Host host = requestClass.getAnnotation(Host.class);
		if(host == null || !StringUtils.isNotNullAndEmpty(host.value())){
			throw new Exception(requestClass.getName()+"上没有Host注解");
		}
		//尝试从请求对象中获取路径地址
		Path path = requestClass.getAnnotation(Path.class);
		if(path == null || !StringUtils.isNotNullAndEmpty(path.value())){
			throw new Exception(requestClass.getName()+"上没有Path注解");
		}
		HttpRequest httpRequest = new HttpRequest(host.value() + (StringUtils.startsWithIgnoreCase(host.value(), "/")?"":"/") + path.value());
		
		//如果有Post注解就设置请求方式为POST
		if(AnnotationUtils.contain(requestClass, Post.class)){
			httpRequest.setRequestMethod(HttpRequestMethod.POST);
		}
		
		//如果有ConnectTimeout注解就设置连接超时时间
		ConnectTimeout connectTimeout = requestClass.getAnnotation(ConnectTimeout.class);
		if(connectTimeout != null){
			httpRequest.setConnectTimeout(connectTimeout.value());
		}
		
		//如果有ReadTimeout注解就设置读取超时时间
		ReadTimeout readTimeout = requestClass.getAnnotation(ReadTimeout.class);
		if(readTimeout != null){
			httpRequest.setReadTimeout(readTimeout.value());
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
					valueObject = field.get(requestObject);
					if(valueObject instanceof Map){
						@SuppressWarnings("unchecked")
						Map<Object, Object> map = (Map<Object, Object>)valueObject;
						for(java.util.Map.Entry<Object, Object> entry : map.entrySet()){
							if(entry.getKey() != null && entry.getValue() != null && StringUtils.isNotNullAndEmpty(entry.getKey().toString(), entry.getValue().toString())){
								//添加请求参数
								httpRequest.addParameter(entry.getKey().toString(), entry.getValue().toString());
							}
						}
					}else{
						paramValue = valueObject != null?valueObject.toString():null;
						if(StringUtils.isNotNullAndEmpty(paramValue)){
							//初始化参数名
							SerializedName serializedName = field.getAnnotation(SerializedName.class);
							paramName = (serializedName != null && StringUtils.isNotNullAndEmpty(serializedName.value()))?serializedName.value():field.getName();
							
							//添加请求参数
							httpRequest.addParameter(paramName, paramValue);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return httpRequest;
	}
}