package test.net;

import java.lang.reflect.Type;

import me.xiaopan.androidlibrary.net.ErrorInfo;
import me.xiaopan.androidlibrary.net.Response;
import me.xiaopan.androidlibrary.net.ResponseHandler;
import me.xiaopan.androidlibrary.util.Logger;

import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * 响应处理器
 * @author xiaopan
 *
 */
public class MyResponseHandler implements ResponseHandler{
//	private static final String STATUS = "status";
//	private static final String STATUS_VALUE_SUCCESS = "success";
	private static final String RESULT = "result";
	private Class<? extends Response> responseClass;
	private Type responseType;
	
	public MyResponseHandler(Class<? extends Response> responseClass){
		this.responseClass = responseClass;
	}
	
	public MyResponseHandler(Type responseType){
		this.responseType = responseType;
	}
	
	@Override
	public boolean onIsSuccess(JSONObject responseJsonObject) throws Exception {
		Logger.i("响应", responseJsonObject.toString());
//		return STATUS_VALUE_SUCCESS.equals(responseJsonObject.getString(STATUS));
		return true;
	}

	@Override
	public Object onGetSuccessResult(JSONObject responseJsonObject) throws Exception {
//		Object result = null;
//		if(responseClass != null){
//			result = new Gson().fromJson(responseJsonObject.getString(RESULT), responseClass);
//		}else if(responseType != null){
//			result = new Gson().fromJson(responseJsonObject.getString(RESULT), responseType);
//		}
//		return result;
		Object result = null;
		if(responseClass != null){
			result = new Gson().fromJson(responseJsonObject.getString("weatherinfo"), responseClass);
		}else if(responseType != null){
			result = new Gson().fromJson(responseJsonObject.getString("weatherinfo"), responseType);
		}
		return result;
	}

	@Override
	public ErrorInfo onGetError(JSONObject responseJsonObject) throws Exception {
		return new Gson().fromJson(responseJsonObject.getString(RESULT), ErrorInfo.class);
	}
}