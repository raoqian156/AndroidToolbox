package me.xiaopan.androidlibrary.net;

import me.xiaopan.javalibrary.net.HttpResponse;


/**
 * 响应处理器接口
 * @author xiaopan
 *
 */
public interface ResponseHandler {
	/**
	 * 当需要判断访问是否成功，如果成功，接下来会调用onGetSuccessResult()获取成功结果，否则调用onGetFailStateCode()获取失败状态码
	 * @param httpResponse
	 * @return
	 * @throws Exception 
	 */
	public boolean onIsSuccess(HttpResponse httpResponse) throws Exception;

	/**
	 * 当需要获取成功结果
	 * @param httpResponse
	 * @return
	 * @throws Exception 
	 */
	public Object onGetSuccessResult(HttpResponse httpResponse) throws Exception;

	/**
	 * 当需要获取失败状态码
	 * @param httpResponse
	 * @return
	 * @throws Exception 
	 */
	public String onGetFailStateCode(HttpResponse httpResponse) throws Exception;
}