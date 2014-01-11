package web.gui.secretary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import web.util.ReadFileByClick;

/**
 * @author maint<br>
 * extended by {@link AddSubject}, {@link EditSubject}, and {@link ViewSubject}.
 * create a framework for these classes with common components, and also define a layout for them.
 *
 */
public abstract class GUISubjectAbstract extends JFrame {

	private static final long serialVersionUID = 1L;

	public static final String INVITE = "Invite";
	public static final String SAVE = "Save";
	public static final String IMPORT_TEACHER = "Import";
	public static final String IMPORT_STUDENT = "Import";
	public static final String CLOSE = "Close";

	private JTextArea txtTeacher;
	private JTextArea txtStudent;

	protected JButton btnAddTeacher;
	protected JButton btnAddStudent;
	protected JButton btnClose;
	
	protected GridBagConstraints c;
	protected GridBagLayout gridbag;
	
	public GUISubjectAbstract(){
//		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		gridbag = new GridBagLayout();
		c = new GridBagConstraints();
		this.setLayout(gridbag);

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(1, 1, 1, 5);
	}

	/**
	 * import a file to {@link JTextArea} when click a button. 
	 * This function is used in {@link AddSubject}, {@link EditSubject} classes.
	 * @throws IOException
	 */
	public void importFile() throws IOException {
		ReadFileByClick.readFile(btnAddTeacher, txtTeacher);
		ReadFileByClick.readFile(btnAddStudent, txtStudent);
	}

	/**
	 * action of button "Close". When click it, return the page {@link ViewSubject}
	 */
	public void closeButton() {

	}

	/**
	 * initialize components which are used in {@link AddSubject}, {@link EditSubject},
	 *  {@link ViewSubject}. They are:
	 *  <li> a {@link JTextArea} for list of Teachers.
	 *  <li> a {@link JTextArea} for list of Students.
	 *  <li> button "Close"
	 */
	protected void initComponents() {
		btnClose = new JButton(CLOSE);

		txtTeacher = new JTextArea();
		txtTeacher.setEditable(true);

		txtStudent = new JTextArea();
		txtStudent.setEditable(true);
	}
	
	/**
	 * @return the txtTeacher
	 */
	public JTextArea getTxtTeacher() {
		return txtTeacher;
	}

	/**
	 * @return the txtStudent
	 */
	public JTextArea getTxtStudent() {
		return txtStudent;
	}
}