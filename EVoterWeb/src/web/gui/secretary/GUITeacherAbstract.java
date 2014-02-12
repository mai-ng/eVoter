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
	private JLabel lbl1;
	
	/**
	 * be Password label in {@link AddTeacher}.
	 * Or be Last-name label in {@link EditTeacher}
	 */
	private JLabel lbl2;
	
	/**
	 * Email label
	 */
	private JLabel lblEmail;
	
	/**
	 * Telephone label
	 */
	private JLabel lblTel;

	/**
	 * be ID field in {@link AddTeacher}.
	 * Or be First-name field in {@link EditTeacher}
	 */
	private JTextField txt1;
	
	/**
	 * be Password field in {@link AddTeacher}.
	 * Or be Last-name field in {@link EditTeacher}
	 */
	private JTextField txt2;
	
	/**
	 * Email field
	 */
	private JTextField txtEmail;
	
	/**
	 * Telephone field
	 */
	private JTextField txtTel;

	/**
	 * button "Save"
	 */
	protected JButton btnSave;


	/**
	 * design user interface of teacher
	 */
	public GUITeacherAbstract() {
		super();

		// Row 0: First name
		c.gridy = 0;
		
		c.gridx = 0;
		c.weightx = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10,10, 5, 10);
		this.add(lbl1, c);

		c.gridx = 1;
		c.weightx = 0.5;
		c.gridwidth = 3;
		c.ipady = 10;
		c.insets = new Insets(10, 40, 5, 20);
		this.add(txt1, c);

		
		// Row 1: Last name
		c.gridy = 1;
		
		c.gridx = 0;
		c.weightx = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10, 10, 5, 10);
		this.add(lbl2, c);

		c.gridx = 1;
		c.weightx = 0.5;
		c.gridwidth = 3;
		c.ipady = 10;
		c.insets = new Insets(10, 40, 5, 20);
		this.add(txt2, c);

		
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
		this.add(lblTel, c);

		c.gridx = 1;
		c.weightx = 0.5;
		c.gridwidth = 3;
		c.ipady = 10;
		c.insets = new Insets(10, 40, 5, 20);
		this.add(txtTel, c);

		
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
	public void initComponents() {
		super.initComponents();
		
		//create labels
		lbl1 = new JLabel();
		lbl2 = new JLabel();
		lblEmail = new JLabel("Email");
		lblTel = new JLabel("Telephone");
		
		// create text fields
		txt1 = new JTextField();
		txt2 = new JTextField();
		txtEmail = new JTextField();
		txtTel = new JTextField();

		// create buttons
		btnSave = new JButton(SAVE);
	}

	/**
	 * @return the lbl1
	 */
	public JLabel getLbl1() {
		return lbl1;
	}

	/**
	 * @return the lbl2
	 */
	public JLabel getLbl2() {
		return lbl2;
	}

	/**
	 * @return the txt1
	 */
	public JTextField getTxt1() {
		return txt1;
	}

	/**
	 * @return the txt2
	 */
	public JTextField getTxt2() {
		return txt2;
	}

	/**
	 * @return the txtEmail
	 */
	public JTextField getTxtEmail() {
		return txtEmail;
	}

	/**
	 * @return the txtTel
	 */
	public JTextField getTxtTel() {
		return txtTel;
	}

}
