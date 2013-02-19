package test.net.request;

import me.xiaopan.androidlibrary.net.Path;
import me.xiaopan.androidlibrary.net.Request;

@Path("questions.ashx")
public class QestionnaireRequest extends Request {
	private int qstType;

	public int getQstType() {
		return qstType;
	}

	public void setQstType(int qstType) {
		this.qstType = qstType;
	}
}