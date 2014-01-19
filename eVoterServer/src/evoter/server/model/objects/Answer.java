package evoter.server.model.objects;

import java.io.Serializable;

import org.json.simple.JSONObject;

import evoter.server.dao.interfaces.AnswerDAO;


public class Answer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 160378011848917151L;
	private long id;
	private long questionId;
	private String answerText;
	
	public Answer(){}
	public Answer(long questionId, String answerText){
		this.questionId = questionId;
		this.answerText = answerText;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	public String getAnswerText() {
		return answerText;
	}
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	@Override
	public String toString() {
		return "Answer [id=" + id + ", questionId=" + questionId
				+ ", answerText=" + answerText + "]";
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject object = new JSONObject();
		object.put(AnswerDAO.ID, id);
		object.put(AnswerDAO.QUESTION_ID, questionId);
		object.put(AnswerDAO.ANSWER_TEXT, answerText);
		return object;
	}
	

}
