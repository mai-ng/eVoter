package web.gui.secretary.spec;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import web.gui.secretary.AddTeacher;
import web.gui.secretary.EditTeacher;
import web.gui.secretary.ViewTeacher;

/**
 * extended by {@link AddTeacher}, {@link EditTeacher}, {@link ViewTeacher}. classes<br> 
 * Create common components, initialize them, and define a layout and user interface. <br>
 * @author maint<br>
 *         
 */
public abstract class GUITeacherAbstract extends JFrame {

	private static final long serialVersionUID = 1L;

	protected JLabel lblFullName;
	protected JLabel lblUserName;
	protected JLabel lblEmail;

	protected JTextField txtFullName;
	protected JTextField txtUserName;
	protected JTextField txtEmail;

	protected JButton btnSave;
	protected JButton btnClose;

	
	public GUITeacherAbstract() {
		initComponents();
		buildGUI();
	}

	/**
	 * design user interface for {@link AddTeacher},
	 * {@link EditTeacher}, {@link ViewTeacher}.
	 */
	public void buildGUI() {
		//show frame
		setSize(600, 250);
		setLocationRelativeTo(null);
		setVisible(true);

		//user interface
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 0, 5);

		// Row 0: full name
		c.gridy = 0;

		c.gridx = 0;
		c.weightx = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10, 10, 5, 10);
		this.add(lblFullName, c);

		c.gridx = 1;
		c.weightx = 0.5;
		c.gridwidth = 3;
		c.ipady = 10;
		c.insets = new Insets(10, 40, 5, 20);
		this.add(txtFullName, c);

		// Row 1: user name
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

		// Row 2: Email
		c.gridy = 2;

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
//
//		// Row 3: Telephone
//		c.gridy = 3;
//
//		c.gridx = 0;
//		c.weightx = 0;
//		c.gridwidth = 1;
//		c.insets = new Insets(10, 10, 5, 10);
//		this.add(lblPassword, c);
//
//		c.gridx = 1;
//		c.weightx = 0.5;
//		c.gridwidth = 3;
//		c.ipady = 10;
//		c.insets = new Insets(10, 40, 5, 20);
//		this.add(txtPassword, c);

		// Row 4: Buttons
		c.gridy = 4;
		c.ipady = 30;
		c.ipadx = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.PAGE_END;

		c.gridx = 2;
		c.insets = new Insets(10, 200, 5, 0);
		this.add(btnSave, c);

		c.gridx = 3;
		c.insets = new Insets(10, 50, 5, 20);
		this.add(btnClose, c);

	}

	/**
	 * initialize all components which are used in {@link AddTeacher},
	 * {@link EditTeacher}, {@link ViewTeacher}.
	 */
	public void initComponents() {
		// create labels
		lblFullName = new JLabel("Full name");
		lblUserName = new JLabel("Username");
		lblEmail = new JLabel("Email");

		// create text fields
		txtFullName = new JTextField("Full name");
		txtUserName = new JTextField("User name");
		txtEmail = new JTextField("Email");

		// create buttons
		btnSave = new JButton();
		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

}
