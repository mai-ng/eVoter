package evoter.share.model;

import java.io.Serializable;

import org.json.simple.JSONObject;

import evoter.share.dao.AnswerDAO;


/**
 * <br>Update by @author luongnv89 on 09-Feb-2014:<br>
 * <li> Add {@link Answer#Answer(long, long, String)} constructor
 *
 */
public class Answer extends ItemData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 160378011848917151L;
	private long questionId;
	
	/**
	 * @param cp
	 */
	public Answer(ItemData cp) {
		super(cp);
		// TODO Auto-generated constructor stub
	}
	public Answer(){}
	
	public Answer(long id, long questionId, String answerText){
		super(id, answerText);
		this.questionId = questionId;
	}
	
	public Answer(long questionId, String answerText){
		this.questionId = questionId;
		this.title = answerText;
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
		return super.getTitle();
	}
	public void setAnswerText(String answerText) {
		this.title = answerText;
	}
	@Override
	public String toString() {
		return "Answer [id=" + id + ", questionId=" + questionId
				+ ", answerText=" + title + "]";
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject object = new JSONObject();
		object.put(AnswerDAO.ID, id);
		object.put(AnswerDAO.QUESTION_ID, questionId);
		object.put(AnswerDAO.ANSWER_TEXT, title);
		return object;
	}
	

}
