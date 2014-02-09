package evoter.share.model;

import java.io.Serializable;
import java.sql.Timestamp;

import org.json.simple.JSONObject;

import evoter.share.dao.QuestionDAO;

/**
 * <br> Updated by @author luongnv89 on 09-Feb-2014: <br>
 * <li>Update {@link Question#Question(long, String, long, long, Timestamp, long, long, String, String)}
 * <br>add: <br> if(answerColumn1.equals("null")) 
			this.answerColumn1="";
		else
			this.answerColumn1 = answerColumn1;
 * <br> Updated by @author luongnv89 on 18-Jan-2014: <br>
 * <li>Updated for eVoterMobile
 * 
 * 
 * Created by btDiem
 *
 */
public class Question extends ItemData implements Serializable {

	private static final long serialVersionUID = -5892190903011268048L;

	private long questionTypeId;
	private long userId;
	private Timestamp creationDate;
	private long parentId;
	private long sessionID;
	public static final String COL1 = "column1";
	public static final String COL2 = "column2";
	private String answerColumn1;
	private String answerColumn2;

	/**
	 * @param questionTypeId
	 * @param userId
	 * @param creationDate
	 * @param parentId
	 * @param answerColumn1
	 * @param answerColumn2
	 */
	public Question(long id, String questionText, long questionTypeId,
			long userId, Timestamp creationDate, long parentId,long sessionID,
			String answerColumn1, String answerColumn2) {
		
		super(id, questionText);
		this.questionTypeId = questionTypeId;
		this.userId = userId;
		this.creationDate = creationDate;
		this.sessionID = sessionID;
		this.parentId = parentId;
		if(answerColumn1.equals("null")) 
			this.answerColumn1="";
		else
			this.answerColumn1 = answerColumn1;
		if(answerColumn2.equals("null")) 
			this.answerColumn2="";
		else
			this.answerColumn2 = answerColumn2;
	}

	public Question() {
	}

	public Question(long questionTypeId, long userId, String questionText,
			Timestamp creationDate, long parentId) {

		this.questionTypeId = questionTypeId;
		this.userId = userId;
		this.title = questionText;
		this.creationDate = creationDate;
		this.parentId = parentId;

	}

	
	/**
	 * 
	 * @param cp
	 */
	public Question(Question cp) {
		super(cp);
		this.questionTypeId = cp.getQuestionTypeId();
		this.userId = cp.getUserId();
		this.creationDate = cp.getCreationDate();
		this.sessionID = cp.getSessionID();
		this.parentId = cp.getParentId();
		this.answerColumn1 = cp.getAnswerColumn1();
		this.answerColumn2 = cp.getAnswerColumn2();
	}

	
	
	/**
	 * @return the sessionID
	 */
	public long getSessionID() {
		return sessionID;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(long questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public String getQuestionText() {
		return super.getTitle();
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public void setQuestionText(String questionText) {
		this.title = questionText;
	}

	/**
	 * @return the answerColumn1
	 */
	public String getAnswerColumn1() {
		return answerColumn1;
	}

	/**
	 * @return the answerColumn2
	 */
	public String getAnswerColumn2() {
		return answerColumn2;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", questionTypeId=" + questionTypeId
				+ ", userId=" + userId + ", questionText=" + title
				+ ", creationDate=" + creationDate + ", parentId=" + parentId
				+ "]";
	}

	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject object = new JSONObject();
		object.put(QuestionDAO.ID, id);
		object.put(QuestionDAO.QUESTION_TEXT, title);
		object.put(QuestionDAO.QUESTION_TYPE_ID, questionTypeId);
		object.put(QuestionDAO.USER_ID, userId);
		object.put(QuestionDAO.CREATION_DATE, "'" + creationDate + "'");
		object.put(QuestionDAO.PARENT_ID, parentId);
		return object;
	}

}
