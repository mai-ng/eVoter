package csc7326.utils;

import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by luongnv89 on 05/12/13.
 */
public class Utils {
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

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
//		String reFormat = "{\"data\":" + response + "}";
//		JSONObject data = new JSONObject(reFormat);
//		return data.getJSONArray("data");
		return new JSONArray(response);
	}
}
