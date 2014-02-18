package evoter.mobile.objects;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import evoter.mobile.activities.EVoterActivity;
import evoter.mobile.activities.EVoterRequestManager;
import evoter.mobile.activities.LoginActivity;
import evoter.share.dao.UserDAO;

/**
 * {@link OfflineEVoterManager} manages user, database,.. for different session
 * of eVoterMobile Application
 * Created by luongnv89 on 03/01/14.
 */
public class OfflineEVoterManager {
	
	SharedPreferences preferences;
	
	Editor editor;
	
	EVoterActivity context;
	
	int PRIVATE_MODE = 0;
	
	private final String PREF_NAME = "eVoterPreference";
	
	private final String IS_LOGIN = "IsLoggedin";
	
	@SuppressLint("CommitPrefEdits")
	public OfflineEVoterManager(EVoterActivity contex) {
		this.context = contex;
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
	
	public void addAnswerQuestion(long questionID,String answer){
		editor.putString(String.valueOf(questionID), answer);
	}
	
	public String getAnswerForQuestion(long questionID){
		HashMap<String, String> answer = new HashMap<String, String>();
		answer.put(String.valueOf(questionID),
				preferences.getString(String.valueOf(questionID), null));
		return answer.get(String.valueOf(questionID));
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
		if (!isLoggedIn()) {
			Intent i = new Intent(context, LoginActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		} else {
			HashMap<String, String> user = getSavedUserDetail();
			EVoterRequestManager.doLogin(user.get(UserDAO.USER_NAME), user.get(UserDAO.PASSWORD),context);
			Log.i("Already login: ", "UserName: " + user.get(UserDAO.USER_NAME) + " | Userkey: " + user.get(UserDAO.USER_KEY));
		}
	}
	
	/**
	 * Logout current user. Don't remember to next session
	 */
	public void logoutUser() {
		editor.clear();
		editor.commit();
		
		Intent i = new Intent(context, LoginActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		
	}
	
	/**
	 * Check if there is an user already logged in to application
	 * 
	 * @return
	 *         true if there is an user already logged in to application <br>
	 *         false otherwise
	 */
	public boolean isLoggedIn() {
		return preferences.getBoolean(IS_LOGIN, false);
	}
	
}
