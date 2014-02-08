package evoter.share.model;

import java.io.Serializable;
/**
 * 
 * @author btdiem
 *
 */
public class SessionUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7728617224560886258L;

	private long userId;
	private long sessionId;
	private boolean deleteIndicator;
	private boolean acceptSession;
	
	public SessionUser(){}
	
	public SessionUser(long userId, long sessionId, 
			boolean deleteIndicator, boolean acceptSession){
		this.userId = userId;
		this.sessionId = sessionId;
		this.deleteIndicator = deleteIndicator;
		this.acceptSession = acceptSession;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getSessionId() {
		return sessionId;
	}
	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}
	public boolean isDeleteIndicator() {
		return deleteIndicator;
	}
	public void setDeleteIndicator(boolean deleteIndicator) {
		this.deleteIndicator = deleteIndicator;
	}
	public boolean isAcceptSession() {
		return acceptSession;
	}
	public void setAcceptSession(boolean acceptSession) {
		this.acceptSession = acceptSession;
	}
	@Override
	public String toString() {
		return "SessionUser [userId=" + userId + ", sessionId=" + sessionId
				+ ", deleteIndicator=" + deleteIndicator + ", acceptSession="
				+ acceptSession + "]";
	}

}
