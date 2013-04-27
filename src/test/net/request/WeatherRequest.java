package test.net.request;

import me.xiaopan.androidlibrary.net.Host;
import me.xiaopan.androidlibrary.net.Path;
import me.xiaopan.androidlibrary.net.Request;

@Host("http://www.weather.com.cn/data/cityinfo")
@Path("101010100.html")
public class WeatherRequest extends Request {
	
}