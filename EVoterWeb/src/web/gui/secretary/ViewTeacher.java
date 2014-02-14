package web.gui.secretary;

import evoter.share.model.User;

/**
 * View detail information of full name, username and email of a teacher . <br>
 * Extends {@link EditTeacher} class.
 * @author maint
 */
public class ViewTeacher extends EditTeacher{

	private static final long serialVersionUID = 1L;

	public ViewTeacher(User u) {
		super(u);
		setTitle("View information of a teacher");		
	}

	/**
	 * set full name, user name, and email fields are not editable.<br>
	 * disable buttons of save and close.
	 */
	public void initComponents() {
		super.initComponents();
		txtFullName.setEditable(false);
		txtUserName.setEditable(false);
		txtEmail.setEditable(false);
		
		btnSave.setEnabled(false);
		btnSave.setVisible(false);
		btnClose.setEnabled(false);
		btnClose.setVisible(false);
	}
	
	
}
