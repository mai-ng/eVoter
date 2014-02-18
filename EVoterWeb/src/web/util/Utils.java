package web.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import web.gui.secretary.MainPanel;

public class Utils {

	static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");

	/**
	 * Convert a string of date to {@link Timestamp}.
	 * @param date want to convert
	 * @return {@link Timestamp} of a date string.
	 */
	public static Timestamp convertToDate(String date) {
		java.util.Date utilDate;
		try {
			utilDate = dateFormat.parse(date);
			return new Timestamp(utilDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Record the result test to output file
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
	 * Find an icon
	 * @param path is path of the icon.
	 * @return a {@link ImageIcon}
	 */
	public static ImageIcon findImageIcon(String path) {
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
	 * A dialog is used for confirm a request.
	 * @param info is information of the selected object
	 */
	public static int confirmDialog(String info) {
		JDialog.setDefaultLookAndFeelDecorated(true);
		return JOptionPane.showConfirmDialog(null, "Do you really want to delete: \n"
				+info, "Confirm",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
}
