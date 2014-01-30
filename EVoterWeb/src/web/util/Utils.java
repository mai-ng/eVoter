package web.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.Context;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by luongnv89 on 05/12/13.
 */
public class Utils {

	public static boolean usrnameValid(String usrname) {
		return true;
	}

	public static boolean passwordValid(String pwd) {
		return true;
	}

	public static boolean emailValid(String email) {
		return true;
	}

	public static JSONArray getJSONArray(String response) throws JSONException {
		// String reFormat = "{\"data\":" + response + "}";
		// JSONObject data = new JSONObject(reFormat);
		// return data.getJSONArray("data");
		return new JSONArray(response);
	}

	public static Date convertToDate(String date) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		return dateFormat.parse(date);
	}

	public static String convertToString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	/**
	 * Check internet connection
	 * 
	 * @param context
	 * @return true if the mobile phone has internet connection <br>
	 *         false otherwise
	 */
	public static boolean hasInternetConnection(Context context) {
		return false;
	}
}