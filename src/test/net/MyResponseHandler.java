package test.net;

import java.lang.reflect.Type;

import me.xiaopan.androidlibrary.net.Response;
import me.xiaopan.androidlibrary.net.ResponseHandler;
import me.xiaopan.javalibrary.net.HttpResponse;

import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;

/**
 * 响应处理器
 * @author xiaopan
 *
 */
public class MyResponseHandler implements ResponseHandler{
	private JSONObject responseJsonObject;
	private Class<? extends Response> responseClass;
	private Type responseType;
	
	public MyResponseHandler(Class<? extends Response> responseClass){
		this.responseClass = responseClass;
	}
	
	public MyResponseHandler(Type responseType){
		this.responseType = responseType;
	}
	
	@Override
	public boolean onIsSuccess(HttpResponse httpResponse) throws Exception {
		//获取JSON字符串
		String responseJson = httpResponse.getString();
		//打印LOG
		Log.i("RESPONSE_JSON", responseJson);
		//创建JSON对象
		responseJsonObject = new JSONObject(responseJson);
		//返回是否成功
		return "correct".equals(responseJsonObject.getString("response"));
	}

	@Override
	public Object onGetSuccessResult(HttpResponse httpResponse) throws Exception {
		if(responseClass != null){
			return new Gson().fromJson(responseJsonObject.getString("QuestionList"), responseClass);
		}else if(responseType != null){
			return new Gson().fromJson(responseJsonObject.getString("QuestionList"), responseType);
		}else{
			return null;
		}
	}

	@Override
	public String onGetFailStateCode(HttpResponse httpResponse) throws Exception {
		String failStateCode = null;
		try{
			failStateCode = responseJsonObject.getString("errorFlag");
		}catch(Exception e){
			try{
				failStateCode = responseJsonObject.getString("error");
			}catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return failStateCode;
	}
}