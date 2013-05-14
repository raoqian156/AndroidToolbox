package test.net.request;

import me.xiaopan.easynetwork.android.Host;
import me.xiaopan.easynetwork.android.Path;
import me.xiaopan.easynetwork.android.Request;

@Host("http://www.weather.com.cn/data/cityinfo")
@Path("101010100.html")
public class WeatherRequest implements Request{
	
}