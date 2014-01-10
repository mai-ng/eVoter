package evoter.mobile.main;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import evoter.mobile.subject.SubjectActivity;
import evoter.server.dao.UserDAO;

/**
 * Created by luongnv89 on 03/01/14.
 */
public class EVoterSessionManager {
	
	SharedPreferences preferences;

	Editor editor;

	Context contex;

	int PRIVATE_MODE = 0;
	
	private final String PREF_NAME = "eVoterPreference";
	
	private final String IS_LOGIN = "IsLoggedin";

	@SuppressLint("CommitPrefEdits")
	public EVoterSessionManager(Context contex) {
		this.contex = contex;
		preferences = contex.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = preferences.edit();
	}

	/**
	 * Save current user to next session
	 * 
	 * @param name
	 * @param password
	 */
	public void rememberCurrentUser(String name, String password) {
		editor.putBoolean(IS_LOGIN, true);
		editor.putString(UserDAO.USER_NAME, name);
		editor.putString(UserDAO.PASSWORD, password);

		editor.commit();
	}

	/**
	 * Get detail of saved user
	 * 
	 * @return
	 */
	public HashMap<String, String> getSavedUserDetail() {
		HashMap<String, String> user = new HashMap<String, String>();

		user.put(UserDAO.USER_NAME,
				preferences.getString(UserDAO.USER_NAME, null));
		user.put(UserDAO.PASSWORD,
				preferences.getString(UserDAO.PASSWORD, null));

		return user;
	}

	/**
	 * Check remember user
	 */
	public void checkLogin() {
		if (!this.isLoggedIn()) {
			Intent i = new Intent(contex, Login.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			contex.startActivity(i);
		} else {
			Intent i = new Intent(contex, SubjectActivity.class);
			contex.startActivity(i);
		}
	}

	/**
	 * Logout current user. Don't remember to next session
	 */
	public void logoutUser() {
		editor.clear();
		editor.commit();

		Intent i = new Intent(contex, Login.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		contex.startActivity(i);

	}

	public boolean isLoggedIn() {
		return preferences.getBoolean(IS_LOGIN, false);
	}

	// Static part
	
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
	 * @param currentUserName the currentUserName to set
	 */
	public static void setCurrentUserName(String currentUserName) {
		EVoterSessionManager.currentUserName = currentUserName;
	}

	/**
	 * @return the uSER_KEY
	 */
	public static String getUSER_KEY() {
		return USER_KEY;
	}

	/**
	 * @param uSER_KEY the uSER_KEY to set
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
	 * @param currentSubjectName the currentSubjectName to set
	 */
	public static void setCurrentSubjectName(String currentSubjectName) {
		EVoterSessionManager.currentSubjectName = currentSubjectName;
	}

	/**
	 * @return the currentSessionName
	 */
	public static String getCurrentSessionName() {
		return currentSessionName;
	}

	/**
	 * @param currentSessionName the currentSessionName to set
	 */
	public static void setCurrentSessionName(String currentSessionName) {
		EVoterSessionManager.currentSessionName = currentSessionName;
	}

	/**
	 * @return the currentSessionID
	 */
	public static long getCurrentSessionID() {
		return currentSessionID;
	}

	/**
	 * @param currentSessionID the currentSessionID to set
	 */
	public static void setCurrentSessionID(long currentSessionID) {
		EVoterSessionManager.currentSessionID = currentSessionID;
	}

	/**
	 * @return the currentSessionStatus
	 */
	public static boolean getCurrentSessionStatus() {
		return currentSessionStatus;
	}

	/**
	 * @param currentSessionStatus the currentSessionStatus to set
	 */
	public static void setCurrentSessionStatus(boolean currentSessionStatus) {
		EVoterSessionManager.currentSessionStatus = currentSessionStatus;
	}

	/**
	 * @return the currentSubjectID
	 */
	public static long getCurrentSubjectID() {
		return currentSubjectID;
	}

	/**
	 * @param currentSubjectID the currentSubjectID to set
	 */
	public static void setCurrentSubjectID(long currentSubjectID) {
		EVoterSessionManager.currentSubjectID = currentSubjectID;
	}
	
	
	

}
