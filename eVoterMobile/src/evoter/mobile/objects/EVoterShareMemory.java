/**
 * 
 */
package evoter.mobile.objects;

import evoter.share.model.Question;
import evoter.share.model.Session;
import evoter.share.model.Subject;
import evoter.share.model.User;

/**
 * {@link EVoterShareMemory} manage the parameters of eVoter application
 * 
 * @author luongnv89
 */
public class EVoterShareMemory {
	
	/**
	 * User_key of user
	 */
	private static String USER_KEY;
	
	private static Question currentQuestion;
	
	private static Session currentSession;
	
	private static Subject currentSubject;
	
	private static String currentUserName;
	
	/**
	 * @param currentUserName the currentUserName to set
	 */
	public static void setCurrentUserName(String currentUserName) {
		EVoterShareMemory.currentUserName = currentUserName;
	}
	
	/**
	 * @return the currentSubject
	 */
	public static Subject getCurrentSubject() {
		return currentSubject;
	}
	
	/**
	 * @param currentSubject the currentSubject to set
	 */
	public static void setCurrentSubject(Subject currentSubject) {
		EVoterShareMemory.currentSubject = currentSubject;
	}
	
	/**
	 * @return the currentSession
	 */
	public static Session getCurrentSession() {
		return currentSession;
	}
	
	/**
	 * @param currentSession the currentSession to set
	 */
	public static void setCurrentSession(Session currentSession) {
		EVoterShareMemory.currentSession = currentSession;
	}
	
	/**
	 * @return the currentUserName
	 */
	public static String getCurrentUserName() {
		return currentUserName;
	}
	
	/**
	 * @return the uSER_KEY
	 */
	public static String getUSER_KEY() {
		return USER_KEY;
	}
	
	/**
	 * @param uSER_KEY
	 *            the uSER_KEY to set
	 */
	public static void setUSER_KEY(String uSER_KEY) {
		USER_KEY = uSER_KEY;
	}
	
	/**
	 * @return the currentSubjectName
	 */
	public static String getCurrentSubjectName() {
		return currentSubject.getTitle();
	}
	
	/**
	 * @return the currentSessionName
	 */
	public static String getCurrentSessionName() {
		return currentSession.getTitle();
	}
	
	/**
	 * @return the currentSessionID
	 */
	public static long getCurrentSessionID() {
		return currentSession.getId();
	}
	
	/**
	 * @return the currentSessionStatus
	 */
	public static boolean currentSessionIsActive() {
		return currentSession.isActive();
	}
	
	/**
	 * @return the currentSubjectID
	 */
	public static long getCurrentSubjectID() {
		return currentSubject.getId();
	}
	
	/**
	 * @return the currentQuestion
	 */
	public static Question getCurrentQuestion() {
		return currentQuestion;
	}
	
	/**
	 * @param currentQuestion the currentQuestion to set
	 */
	public static void setCurrentQuestion(Question auestion) {
		currentQuestion = auestion;
	}
	
	/**
	 * Extract userkey to get user type
	 * 
	 * @return the currentUserType
	 */
	public static long getCurrentUserType() {
		if(getUSER_KEY()==null || getUSER_KEY().equals("")) return 0;
		String[] array = getUSER_KEY().split("_");
		return Long.parseLong(array[array.length-1]);
	}
	
}
