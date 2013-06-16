/*
 * Copyright 2013 Peng fei Pan
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
