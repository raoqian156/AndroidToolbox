package test.net.request;

import me.xiaopan.androidlibrary.net.Path;
import me.xiaopan.androidlibrary.net.Request;

@Path("s")
public class BaiduRequest extends Request{
	private String wd;

	public BaiduRequest(String wd){
		setWd(wd);
	}
	
	public String getWd() {
		return wd;
	}

	public void setWd(String wd) {
		this.wd = wd;
	}
}