/**
 * 
 */
package evoter.share.model;

import java.io.Serializable;

/**
 * @author btdiem
 *
 */
public class Statistics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -453505672877863216L;
	
	private long sessionId;
	private long questionId;
	private String statisticValue;
	public long getSessionId() {
		return sessionId;
	}
	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}
	public long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	public String getStatisticValue() {
		return statisticValue;
	}
	public void setStatisticValue(String statisticValue) {
		this.statisticValue = statisticValue;
	}
	@Override
	public String toString() {
		return "Statistics [sessionId=" + sessionId + ", questionId="
				+ questionId + ", statisticValue=" + statisticValue + "]";
	}
	
	
	

}
