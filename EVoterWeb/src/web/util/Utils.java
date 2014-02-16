package web.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.naming.Context;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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

	// public static JSONArray getJSONArray(String response) throws
	// JSONException {
	// // String reFormat = "{\"data\":" + response + "}";
	// // JSONObject data = new JSONObject(reFormat);
	// // return data.getJSONArray("data");
	// return new JSONArray(response);
	// }
	static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");

	public static Timestamp convertToDate(String date) {
		java.util.Date utilDate;
		try {
			utilDate = dateFormat.parse(date);
			return new Timestamp(utilDate.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static String convertToString(Timestamp creationDate) {
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
			BufferedWriter out = new BufferedWriter(new FileWriter(resultfile,
					true));
			out.write(msg + "\n");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param path
	 *            of image
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
	
	
	/**
	 * a dialog to inform a message.
	 * @param txt is message want to inform
	 */
	public static void informDialog(String txt){
		JOptionPane.showMessageDialog(new JFrame(), txt);
	}
	
	/**
	 * @param info is information of the selected object
	 */
	public static int confirmDialog(String info) {
		JDialog.setDefaultLookAndFeelDecorated(true);
		return JOptionPane.showConfirmDialog(null, "Do you really want to delete: \n"
				+info, "Confirm",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//	    int response = JOptionPane.showConfirmDialog(null, "Do you really want to delete: \n"
//				+info, "Confirm",
//	        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//	    if (response == JOptionPane.NO_OPTION) {
//	      System.out.println("No button clicked");
//	    } else if (response == JOptionPane.YES_OPTION) {
//	      System.out.println("Yes button clicked");
//	    } else if (response == JOptionPane.CLOSED_OPTION) {
//	      System.out.println("JOptionPane closed");
//	    }
	}
}
