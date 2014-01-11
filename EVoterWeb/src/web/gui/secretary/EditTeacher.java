package web.gui.secretary;


/**
 * @author maint<br>
 * a JFrame to edit teacher's information.
 * extends {@link GUITeacherAbstract} class.
 */
public class EditTeacher extends GUITeacherAbstract {

	private static final long serialVersionUID = 1L;

	/**
	 * set the title of the frame, and initialize its components
	 */
	public EditTeacher() {
		this.setTitle("Edit teacher's information");
		initComponents();

	}

	/**
	 * setText="First name" for {@link GUITeacherAbstract#lbl1}.
	 * setText="Last name" for {@link GUITeacherAbstract#lbl2}.
	 */
	public void initComponents() {
		super.initComponents();
		getLbl1().setText("First name");
		getLbl2().setText("Last name");
	}

}
