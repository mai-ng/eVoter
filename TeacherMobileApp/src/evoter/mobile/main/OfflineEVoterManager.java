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
 * {@link OfflineEVoterManager} manages user, database,.. for different session of eVoter Application
 * Created by luongnv89 on 03/01/14.
 */
public class OfflineEVoterManager {
	
	SharedPreferences preferences;

	Editor editor;

	Context contex;

	int PRIVATE_MODE = 0;
	
	private final String PREF_NAME = "eVoterPreference";
	
	private final String IS_LOGIN = "IsLoggedin";

	@SuppressLint("CommitPrefEdits")
	public OfflineEVoterManager(Context contex) {
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
	public void rememberCurrentUser(String name,String userkey) {
		editor.putBoolean(IS_LOGIN, true);
		editor.putString(UserDAO.USER_NAME, name);
		editor.putString(UserDAO.USER_KEY, userkey);

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
		user.put(UserDAO.USER_KEY,
				preferences.getString(UserDAO.USER_KEY, null));

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
			HashMap<String, String> user = getSavedUserDetail();
			RuntimeEVoterManager.setCurrentUserName(user.get(UserDAO.USER_NAME));
			RuntimeEVoterManager.setUSER_KEY(user.get(UserDAO.USER_KEY));
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

}
