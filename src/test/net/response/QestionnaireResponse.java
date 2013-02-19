package test.net.response;

import java.util.List;

import me.xiaopan.androidlibrary.net.Response;

public class QestionnaireResponse extends Response {
    private String questionId;//问题ID -->
    private String questionTitle;//问题标题 -->
    private int answerType;//回答类型（1：单选；2：多选；3：自己输入；4：继承父问题的回答方式） -->
    private List<QestionnaireResponse> questionChildList;
    private List<Option> optionList;//选项列表 -->
	
    public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getQuestionTitle() {
		return questionTitle;
	}
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	public int getAnswerType() {
		return answerType;
	}
	public void setAnswerType(int answerType) {
		this.answerType = answerType;
	}
	public List<QestionnaireResponse> getQuestionChildList() {
		return questionChildList;
	}
	public void setQuestionChildList(List<QestionnaireResponse> questionChildList) {
		this.questionChildList = questionChildList;
	}
	public List<Option> getOptionList() {
		return optionList;
	}
	public void setOptionList(List<Option> optionList) {
		this.optionList = optionList;
	}
	
	public String toString(){
		return "{" + questionTitle + ", " + (optionList != null?optionList.toString():"null") + (questionChildList != null?questionChildList.toString():"null")+ "}";
	}
}