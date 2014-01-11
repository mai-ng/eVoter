package web.gui.secretary;


/**
 * @author maint<br> 
 * a JFrame to add a new teacher.
 * extends {@link GUITeacherAbstract} class.
 */
public class AddTeacher extends GUITeacherAbstract {

	private static final long serialVersionUID = 1L;

	/**
	 * set the title of the frame, and initialize its components
	 */
	public AddTeacher() {
		this.setTitle("Add new teacher");
		initComponents();
	}

	/**
	 * setText="ID" for {@link GUITeacherAbstract#lbl1}.
	 * setText="PAssword" for {@link GUITeacherAbstract#lbl2}.
	 */
	public void initComponents() {
		super.initComponents();
		getLbl1().setText("ID");
		getLbl2().setText("Password");
	}

}
