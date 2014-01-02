package evoter.server.model;

import java.io.Serializable;

public class UserSubject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1686831910918959922L;
	private long userId;
	private long subjectId;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}
	@Override
	public String toString() {
		return "UserSubject [userId=" + userId + ", subjectId=" + subjectId
				+ "]";
	}

	
	
	
}
