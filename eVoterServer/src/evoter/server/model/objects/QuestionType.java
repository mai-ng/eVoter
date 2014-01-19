package evoter.server.model.objects;

import java.io.Serializable;

public class QuestionType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6752289493987393945L;


	private long id;
	private String questionTypeValue;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getQuestionTypeValue() {
		return questionTypeValue;
	}
	public void setQuestionTypeValue(String questionTypeValue) {
		this.questionTypeValue = questionTypeValue;
	}
	@Override
	public String toString() {
		return "QuestionType [id=" + id + ", questionTypeValue="
				+ questionTypeValue + "]";
	}
	
	
}
