package evoter.share.model;

import java.io.Serializable;
import java.sql.Date;
import org.json.simple.JSONObject;

import evoter.share.dao.QuestionDAO;

public class Question implements Serializable {

	private static final long serialVersionUID = -5892190903011268048L;

	private long id;
	private long questionTypeId;
	private long userId;
	private String questionText;
	private Date creationDate;
	private long parentId;
	public static final String COL1 = "column1";
	public static final String COL2 = "column2";
	
	public Question(){}
	public Question(long questionTypeId, 
					long userId, 
					String questionText, 
					Date creationDate, 
					long parentId){
		
		this.questionTypeId = questionTypeId;
		this.userId = userId;
		this.questionText = questionText;
		this.creationDate = creationDate;
		this.parentId = parentId;
		
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
		return questionText;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}


	@Override
	public String toString() {
		return "Question [id=" + id + ", questionTypeId=" + questionTypeId
				+ ", userId=" + userId + ", questionText=" + questionText
				+ ", creationDate=" + creationDate + ", parentId=" + parentId
				+ "]";
	}
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject object = new JSONObject();
		object.put(QuestionDAO.ID, id);
		object.put(QuestionDAO.QUESTION_TEXT, questionText);
		object.put(QuestionDAO.QUESTION_TYPE_ID, questionTypeId);
		object.put(QuestionDAO.USER_ID, userId);
		object.put(QuestionDAO.CREATION_DATE, creationDate);
		object.put(QuestionDAO.PARENT_ID, parentId);
		return object;
	}
	
	
}
