package evoter.mobile.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.Toast;

/**
 * <br>Update by @author luongnv89 on Sun - 09-Feb-2014:
 * <br>
 * <li> Change java.sql.Date to using java.sql.Timestamp
 * <br>Created by luongnv89 on 05/12/13.
 */
public class EVoterMobileUtils {
	
	public static JSONArray getJSONArray(String response) throws JSONException {
		return new JSONArray(response);
	}
	
	@SuppressLint("SimpleDateFormat")
	public static Timestamp convertToDate(String date) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date utilDate = dateFormat.parse(date);
		return new Timestamp(utilDate.getTime());
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String convertToString(Timestamp creationDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(creationDate);
	}
	
	/**
	 * Check internet connection
	 * 
	 * @param context
	 * @return true if the mobile phone has internet connection <br>
	 *         false otherwise
	 */
	public static boolean hasInternetConnection(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
	}
	
	/**
	 * Show a message
	 * 
	 * @param context
	 * @param message
	 */
	public static void showeVoterToast(Context context, String message) {
		Toast t = Toast.makeText(context, message, Toast.LENGTH_LONG);
		t.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		t.show();
	}
}
