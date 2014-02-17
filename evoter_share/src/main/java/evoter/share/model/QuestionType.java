package evoter.share.model;

import java.io.Serializable;

/**Updated by @author luongnv89 on 18-Jan-2014:<br>
 * <li>Add question types
 * 
 * Mapping columns of question_type table to properties of this class </br>
 * @author btdiem </br>
 *
 */

public class QuestionType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6752289493987393945L;

	public static final int YES_NO = 1;
	public static final int MULTI_RADIOBUTTON = 2;
	public static final int MULTI_CHECKBOX = 3;
	public static final int SLIDER = 4;
	public static final int INPUT_ANSWER = 5;
	public static final int MATCH = 6;

	private long id;
	private String questionTypeValue;
	
	public QuestionType(){}
	
	public QuestionType(String questionTypeValue){
		this.questionTypeValue = questionTypeValue;
	}
	
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
