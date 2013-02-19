package test.net.response;

public class Option {
	private String optionId;//选项ID -->
    private String optionTitle;//选项标题 -->
    private String inputType;//
    
    public String getOptionId() {
		return optionId;
	}
    
	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}
	
	public String getOptionTitle() {
		return optionTitle;
	}
	
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}
	
	public String getInputType() {
		return inputType;
	}
	
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	
	public String toString(){
		return optionTitle;
	}
}