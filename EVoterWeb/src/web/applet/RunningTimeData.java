/**
 * 
 */
package web.applet;

/**
 * @author luongnv89
 *
 */
public class RunningTimeData {
	
	private static String currentUserName;
	
	private static String currentUserKey;
	
	/**
	 * @return the currentUserKey
	 */
	public static String getCurrentUserKey() {
		return currentUserKey;
	}

	/**
	 * @param currentUserKey the currentUserKey to set
	 */
	public static void setCurrentUserKey(String currentUserKey) {
		RunningTimeData.currentUserKey = currentUserKey;
	}

	/**
	 * @return the currentUserName
	 */
	public static String getCurrentUserName() {
		return currentUserName;
	}

	/**
	 * @param currentUserName the currentUserName to set
	 */
	public static void setCurrentUserName(String currentUserName) {
		RunningTimeData.currentUserName = currentUserName;
	}
	
	
	
}
