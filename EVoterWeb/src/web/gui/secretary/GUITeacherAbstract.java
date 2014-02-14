package web.gui.secretary;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author maint<br>
 * extended by {@link AddTeacher}, {@link EditTeacher}, and {@link ViewTeacher}.
 * create a framework for these classes with common components, and also define a layout for them. <br>
 * extends {@link GUIAbstract}.
 */
public abstract class GUITeacherAbstract extends GUIAbstract{

	private static final long serialVersionUID = 1L;
	
	/**
	 * be ID label in {@link AddTeacher}.
	 * Or be First-name label in {@link EditTeacher}
	 */
	protected JLabel lblFullName;
	
	/**
	 * be Password label in {@link AddTeacher}.
	 * Or be Last-name label in {@link EditTeacher}
	 */
	protected JLabel lblUserName;
	
	/**
	 * Email label
	 */
	protected JLabel lblEmail;
	
	/**
	 * Telephone label
	 */
	protected JLabel lblPassword;

	/**
	 * be ID field in {@link AddTeacher}.
	 * Or be First-name field in {@link EditTeacher}
	 */
	protected JTextField txtFullName;
	
	/**
	 * be Password field in {@link AddTeacher}.
	 * Or be Last-name field in {@link EditTeacher}
	 */
	protected JTextField txtUserName;
	
	/**
	 * Email field
	 */
	protected JTextField txtEmail;
	
	/**
	 * Telephone field
	 */
	protected JTextField txtPassword;

	/**
	 * button "Save"
	 */
	protected JButton btnSave;


	/**
	 * design user interface of teacher
	 */
	public GUITeacherAbstract() {
		super();
		initComponents();
		// Row 0: First name
		c.gridy = 0;
		
		c.gridx = 0;
		c.weightx = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10,10, 5, 10);
		this.add(lblFullName, c);

		c.gridx = 1;
		c.weightx = 0.5;
		c.gridwidth = 3;
		c.ipady = 10;
		c.insets = new Insets(10, 40, 5, 20);
		this.add(txtFullName, c);

		
		// Row 1: Last name
		c.gridy = 1;
		
		c.gridx = 0;
		c.weightx = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10, 10, 5, 10);
		this.add(lblUserName, c);

		c.gridx = 1;
		c.weightx = 0.5;
		c.gridwidth = 3;
		c.ipady = 10;
		c.insets = new Insets(10, 40, 5, 20);
		this.add(txtUserName, c);

		
		//Row 3: Email
		c.gridy = 3;

		c.gridx = 0;
		c.weightx = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10, 10, 5, 10);
		this.add(lblEmail, c);

		c.gridx = 1;
		c.weightx = 0.5;
		c.gridwidth = 3;
		c.ipady = 10;
		c.insets = new Insets(10, 40, 5, 20);
		this.add(txtEmail, c);
		
		//Row 5: Telephone
		c.gridy = 5;

		c.gridx = 0;
		c.weightx = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10, 10, 5, 10);
		this.add(lblPassword, c);

		c.gridx = 1;
		c.weightx = 0.5;
		c.gridwidth = 3;
		c.ipady = 10;
		c.insets = new Insets(10, 40, 5, 20);
		this.add(txtPassword, c);

		
		//Row 8: Buttons
		c.gridy = 8;
		c.ipady = 30;
		c.gridwidth = 1;	
		c.anchor = GridBagConstraints.PAGE_END;
		
		c.gridx = 1;				
		c.insets = new Insets(10, 100, 5, 30);			
		this.add(btnSave, c);

		c.gridx = 3;
		c.insets = new Insets(10, 30, 5, 100);
		this.add(btnClose, c);
		
	}

	/**
	 * initialize components: 4 {@link JLabel} and 4 {@link JTextField}.	 * 
	 */
	protected void initComponents() {
		super.initComponents();
		
		//create labels
		lblFullName = new JLabel("Full name");
		lblUserName = new JLabel("Username");
		lblEmail = new JLabel("Email");
		lblPassword = new JLabel("Password");
		
		// create text fields
		txtFullName = new JTextField("Full name");
		txtUserName = new JTextField("User name");
		txtEmail = new JTextField("Email");
		txtPassword = new JTextField("Password");

		// create buttons
		btnSave = new JButton(SAVE);
	}

//	/**
//	 * @return the lbl1
//	 */
//	public JLabel getLblFullName() {
//		return lblFullName;
//	}
//
//	/**
//	 * @return the lbl2
//	 */
//	public JLabel getLblUserName() {
//		return lblUserName;
//	}
//
//	/**
//	 * @return the txt1
//	 */
//	public JTextField getTxtFullName() {
//		return txtFullName;
//	}
//
//	/**
//	 * @return the txt2
//	 */
//	public JTextField getTxtUserName() {
//		return txtUserName;
//	}
//
//	/**
//	 * @return the txtEmail
//	 */
//	public JTextField getTxtEmail() {
//		return txtEmail;
//	}
//
//	/**
//	 * @return the txtTel
//	 */
//	public JTextField getTxtPassword() {
//		return txtPassword;
//	}

}
