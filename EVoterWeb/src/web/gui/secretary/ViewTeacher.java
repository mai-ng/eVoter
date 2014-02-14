package web.gui.secretary;

import evoter.share.model.User;

/**
 * @author maint<br>
 *         a JFrame to view a teacher's information. extends
 *         {@link GUITeacherAbstract} class.
 */
public class ViewTeacher extends GUITeacherAbstract {

	private static final long serialVersionUID = 1L;

	private User user;

	/**
	 * set the title of the frame, and initialize its components
	 */
	public ViewTeacher(User u) {
		super();
		user = u;
//		System.out.println("User: "+user.toString());
		txtEmail.setText(user.getEmail());
		txtFullName.setText(user.getFullName());
		txtUserName.setText(user.getUserName());
		txtPassword.setText(user.getPassWord());
		setTitle("View a teacher's information");
		txtFullName.setEditable(false);
		txtUserName.setEditable(false);
		txtEmail.setEditable(false);
		txtPassword.setEditable(false);

		// disable the button "Save"
//		btnSave.setEnabled(false);
		btnSave.setVisible(false);
		btnClose.setVisible(false);
//		initComponents();
//		setVisible(true);
	}

//	/**
//	 * setText="First name" for {@link GUITeacherAbstract#lbl1}.<br>
//	 * setText="Last name" for {@link GUITeacherAbstract#lbl2}.<br>
//	 * set editable = false for text fields.<br>
//	 * disable the button "Save"
//	 */
//	protected void initComponents() {
//		super.initComponents();
////		getLbl1().setText("First name");
////		getLbl2().setText("Last name");
//
//		// set editable = false for text fields
//		
//		
//	}
	//
	// public static final String CLOSE = "Close";
	//
	// public JLabel lblFullName;
	// public JLabel lblEmail;
	// public JLabel lblTel;
	//
	// private JButton btnClose;
	//
	// GridBagConstraints c;
	//
	// /**
	// * Create the panel.
	// */
	// public ViewTeacher() {
	//
	// initComponents();
	// this.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
	// GridBagLayout gridbag = new GridBagLayout();
	// c = new GridBagConstraints();
	// this.setLayout(gridbag);
	//
	// c.fill = GridBagConstraints.BOTH;
	// c.insets = new Insets(1, 1, 1, 1);
	//
	// // Row 0: ID
	// c.gridy = 0;
	// c.ipady = 5;
	// c.weightx = 0;
	// this.add(new JLabel("Full name "),c);
	//
	// c.weightx = 0.5;
	// this.add(lblFullName, c);
	//
	// // Row 1: Password
	// c.gridy = 1;
	// this.add(new JLabel(" "),c);
	// c.gridy = 2;
	//
	// c.weightx = 0;
	// this.add(new JLabel("Email"),c);
	//
	// c.weightx = 0.5;
	// this.add(lblEmail, c);
	//
	// // Row 2: Email
	// c.gridy = 3;
	// this.add(new JLabel(" "),c);
	// c.gridy = 4;
	//
	// c.weightx = 0;
	// this.add(new JLabel("Telephone"),c);
	//
	// c.weightx = 0.5;
	// this.add(lblTel, c);
	//
	// // Row 3: Create Button
	// c.gridy = 5;
	// this.add(new JLabel(" "),c);
	// c.gridy = 6;
	//
	// c.fill = 0;
	// c.anchor = GridBagConstraints.EAST;
	// this.add(btnClose, c);
	// }
	//
	// public void initComponents() {
	// //create text fields
	// lblFullName = new JLabel("Full name of a Teacher");
	// lblEmail = new JLabel("Email of a teacher");
	// lblTel = new JLabel("Tel of a teacher");
	//
	// //create buttons
	// btnClose = new JButton(CLOSE);
	//
	// }

}
