package web.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ReadFileByClick extends JFrame {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private static JFileChooser fileChooser = new JFileChooser();
	static StringBuffer strBuff;


	public static void readFile(final JButton button, final JTextArea txtArea)
			throws IOException {

		/* Enabling Multiple Selection */
		fileChooser.setMultiSelectionEnabled(true);

		/* Setting Current Directory */
		fileChooser.setCurrentDirectory(new File("C:\\Documents and Settings"));

		/* Adding action listener to open file */
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();
				if (command.equals(button.getText())) {
					fileChooser.showDialog(new JFrame(), "File Chooser");
				}
				//Read file to a JTextArea
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
		});

	}

}
