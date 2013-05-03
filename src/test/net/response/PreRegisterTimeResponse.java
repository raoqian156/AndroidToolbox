package test.net.response;

import com.google.gson.annotations.SerializedName;

public class PreRegisterTimeResponse{
	@SerializedName("exhId")
	private String exhibitionId;
	
	@SerializedName("regSTime")
	private long startTime;
	
	@SerializedName("regETime")
	private long endTime;
	
	@SerializedName("regFlag")
	private long state;

	public String getExhibitionId() {
		return exhibitionId;
	}

	public void setExhibitionId(String exhibitionId) {
		this.exhibitionId = exhibitionId;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getState() {
		return state;
	}

	public void setState(long state) {
		this.state = state;
	}
	
	@Override
	public String toString(){
		return exhibitionId + ", " + startTime + ", " + endTime + ", " + state;
	}
}