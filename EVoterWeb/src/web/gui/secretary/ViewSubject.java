package web.gui.secretary;

import evoter.share.model.Subject;

/**
 * View detail information of title, list of students and list of teachers of a subject . <br>
 * Extends {@link EditSubject} class.
 * @author maint
 */
public class ViewSubject extends EditSubject {

	private static final long serialVersionUID = 1L;

	public ViewSubject(Subject subject) {
		super(subject);
		setTitle("View subject information");
	}

	/**
	 * Disable buttons for importing teachers and students.<br>
	 * Set title, student and teacher fields are not editable.
	 */
	public void initComponents() {
		super.initComponents();
		
		txtTitle.setEditable(false);	
		
		listTeacherView.setEditable(false);
		listStudentView.setEditable(false);
		
		btnAddStudent.setEnabled(false);
		btnAddStudent.setVisible(false);
		btnAddTeacher.setEnabled(false);
		btnAddTeacher.setVisible(false);
		btnSave.setEnabled(false);
		btnSave.setVisible(false);
	}
}
