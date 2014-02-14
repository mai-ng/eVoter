package web.gui.secretary;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JTextArea;

import web.util.ReadFileByClick;

/**
 * @author maint<br>
 * extended by {@link AddSubject}, {@link EditSubject}, and {@link ViewSubject}.
 * create a framework for these classes with common components, and also define a layout for them. <br>
 * extends {@link GUIAbstract}
 */
public abstract class GUISubjectAbstract extends GUIAbstract {

	private static final long serialVersionUID = 1L;

	protected JTextArea listTeacherView;
	protected JTextArea listStudentView;

	protected JButton btnAddTeacher;
	protected JButton btnAddStudent;

	public GUISubjectAbstract(){
		super();
	}

	/**
	 * import a file to {@link JTextArea} when click a button. 
	 * This function is used in {@link AddSubject}, {@link EditSubject} classes.
	 * @throws IOException
	 */
	public void importFile() throws IOException {
		ReadFileByClick.readFile(btnAddTeacher, listTeacherView);
		ReadFileByClick.readFile(btnAddStudent, listStudentView);
	}

	/**
	 * initialize components which are used in {@link AddSubject}, {@link EditSubject},
	 *  {@link ViewSubject}. They are:
	 *  <li> a {@link JTextArea} for list of Teachers.
	 *  <li> a {@link JTextArea} for list of Students.
	 *  <li> button "Close"
	 */
	protected void initComponents() {
		super.initComponents();

		//text fields
		listTeacherView = new JTextArea();
		listTeacherView.setEditable(true);

		listStudentView = new JTextArea();
		listStudentView.setEditable(true);
	}
}