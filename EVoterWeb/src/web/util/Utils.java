package web.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.Context;
import javax.swing.ImageIcon;

import web.gui.secretary.MainPanel;

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

//	public static JSONArray getJSONArray(String response) throws JSONException {
//		// String reFormat = "{\"data\":" + response + "}";
//		// JSONObject data = new JSONObject(reFormat);
//		// return data.getJSONArray("data");
//		return new JSONArray(response);
//	}

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
	
	/**
	 * Record the result test to output file
	 * 
	 * @param solutionName
	 * @param cubeSize
	 * @param wordLength
	 * @param dicSize
	 * @param result
	 * @param totalTime
	 * @param resultfile
	 */
	public static void writeLog(String msg, String resultfile) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(resultfile, true));
			out.write(msg+"\n");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * @param path of image
	 * @return
	 */
	public static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = MainPanel.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}
