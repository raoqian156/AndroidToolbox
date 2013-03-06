package test.net.response;

import me.xiaopan.androidlibrary.net.Response;

public class QestionnaireResponse extends Response {
	private int exhId;
	private String exhName;
	public int getExhId() {
		return exhId;
	}
	public void setExhId(int exhId) {
		this.exhId = exhId;
	}
	public String getExhName() {
		return exhName;
	}
	public void setExhName(String exhName) {
		this.exhName = exhName;
	}
	
	@Override
	public String toString(){
		return "展会ID="+getExhId()+"; 展会名称="+getExhName();
	}
}