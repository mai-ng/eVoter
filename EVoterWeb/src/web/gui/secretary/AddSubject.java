package web.gui.secretary;

import java.io.IOException;

import web.gui.secretary.spec.GUISubjectAbstract;

/**
 * Add a new subject by inserting information of a subject such as the title, teachers, students of it.<br> 
 * extends {@link GUISubjectAbstract} class.
 * @author maint<br>
 */
public class AddSubject extends GUISubjectAbstract {

	private static final long serialVersionUID = 1L;
	

	/**
	 * set the title of the frame, and set properties for its components.<br>
	 * @throws IOException 
	 */
	public AddSubject() {
		super();
		setTitle("Add new subject");
	}

	/**
	 * set text for button "Invite"
	 */
	protected void initComponents() {
		super.initComponents();
		btnUpdate.setText("Invite");
	}

}