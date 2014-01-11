/**
 * 
 */
package evoter.mobile.activities;

/**
 * {@link RuntimeEVoterManager} manage the parameters of eVoter application 
 * @author luongnv89
 * 
 */
public class RuntimeEVoterManager {
	/**
	 * To show title at subject list screen
	 */
	public static String currentUserName;

	/**
	 * User_key of user
	 */
	private static String USER_KEY;

	/**
	 * To show title at session list view screen
	 */
	public static String currentSubjectName;

	/**
	 * To show title at session list view screen
	 */
	public static long currentSubjectID;

	/**
	 * To show title at question list view screen
	 */
	public static String currentSessionName;

	/**
	 * Parameter to get list question
	 */
	private static long currentSessionID;

	/**
	 * Parameter to show status of current session
	 */
	private static boolean currentSessionStatus;

	/**
	 * @return the currentUserName
	 */
	public static String getCurrentUserName() {
		return currentUserName;
	}

	/**
	 * @param currentUserName
	 *            the currentUserName to set
	 */
	public static void setCurrentUserName(String currentUserName) {
		RuntimeEVoterManager.currentUserName = currentUserName;
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
		return currentSubjectName;
	}

	/**
	 * @param currentSubjectName
	 *            the currentSubjectName to set
	 */
	public static void setCurrentSubjectName(String currentSubjectName) {
		RuntimeEVoterManager.currentSubjectName = currentSubjectName;
	}

	/**
	 * @return the currentSessionName
	 */
	public static String getCurrentSessionName() {
		return currentSessionName;
	}

	/**
	 * @param currentSessionName
	 *            the currentSessionName to set
	 */
	public static void setCurrentSessionName(String currentSessionName) {
		RuntimeEVoterManager.currentSessionName = currentSessionName;
	}

	/**
	 * @return the currentSessionID
	 */
	public static long getCurrentSessionID() {
		return currentSessionID;
	}

	/**
	 * @param currentSessionID
	 *            the currentSessionID to set
	 */
	public static void setCurrentSessionID(long currentSessionID) {
		RuntimeEVoterManager.currentSessionID = currentSessionID;
	}

	/**
	 * @return the currentSessionStatus
	 */
	public static boolean getCurrentSessionStatus() {
		return currentSessionStatus;
	}

	/**
	 * @param currentSessionStatus
	 *            the currentSessionStatus to set
	 */
	public static void setCurrentSessionStatus(boolean currentSessionStatus) {
		RuntimeEVoterManager.currentSessionStatus = currentSessionStatus;
	}

	/**
	 * @return the currentSubjectID
	 */
	public static long getCurrentSubjectID() {
		return currentSubjectID;
	}

	/**
	 * @param currentSubjectID
	 *            the currentSubjectID to set
	 */
	public static void setCurrentSubjectID(long currentSubjectID) {
		RuntimeEVoterManager.currentSubjectID = currentSubjectID;
	}

}
