package evoter.share.model;

import java.io.Serializable;

public class QuestionSession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9023012714199409543L;
	
	private long questionId;
	private long sessionId;
	
	public QuestionSession(){}
	public QuestionSession(long questionId, long sessionId){
		this.questionId = questionId;
		this.sessionId = sessionId;
	}
	public long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	public long getSessionId() {
		return sessionId;
	}
	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}
	@Override
	public String toString() {
		return "QuestionSession [questionId=" + questionId + ", sessionId="
				+ sessionId + "]";
	}
	
	

}
