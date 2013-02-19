package test.net.request;

import me.xiaopan.androidlibrary.net.Request;
import me.xiaopan.androidlibrary.net.Path;

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