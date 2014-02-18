package web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * Read a file and write content to a {@link JTextArea}.<br>
 * Format of the file is defined at {@link FileFormat}.
 * Format of a file is defined in T
 * @author maint
 *
 */
public class ReadFileToTextArea extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JFileChooser fileChooser = new JFileChooser();
	static StringBuffer strBuff;

	/**
	 * Read a file which contains valid emails and only write these emails to {@link JTextArea}.
	 * @param txtArea where to put content of text file to.
	 * @throws IOException
	 */
	public static void readFile(final JTextArea txtArea) throws IOException {
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setFileFilter(new FileFormat());
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		/* Setting Current Directory */
		fileChooser.setCurrentDirectory(new File("C:\\Documents and Settings"));

		/* Adding action listener to open file */
		fileChooser.showDialog(new JFrame(), "File Chooser");
		String line;
		FileReader fr = null;

		try {
			fr = new FileReader(fileChooser.getSelectedFile());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			BufferedReader bf = new BufferedReader(fr);
			strBuff = new StringBuffer();

			while ((line = bf.readLine()) != null) {
				if (UserAccountValidation.isValidEmail(line)) {
					strBuff.append(line + "\n");
				}
			}

			txtArea.append(strBuff.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
