package csc7326.mylectures;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

import csc7326.subject.SubjectActivity;

/**
 * Created by luongnv89 on 03/01/14.
 */
public class EVoterSessionManager {

    SharedPreferences preferences;

    Editor editor;

    Context contex;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "eVoterPreference";
    private static final String IS_LOGIN = "IsLoggedin";

    public static final String KEY_USERNAME = "username";

    public static final String KEY_PASSWORD = "email";

    public EVoterSessionManager(Context contex) {
        this.contex = contex;
        preferences = contex.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void createLoginSession(String name, String email) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, name);
        editor.putString(KEY_PASSWORD, email);

        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_USERNAME, preferences.getString(KEY_USERNAME, null));
        user.put(KEY_PASSWORD, preferences.getString(KEY_PASSWORD, null));

        return user;
    }

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
