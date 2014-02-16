package web.gui.secretary.spec;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import web.gui.secretary.AddUser;
import web.gui.secretary.EditUser;
import web.gui.secretary.ViewUser;
import web.util.UserAccountValidation;
import web.util.Utils;

/**
 * extended by {@link AddUser}, {@link EditUser}, {@link ViewUser} classes<br>
 * Create common components, initialize them, and define a layout and user
 * interface. <br>
 * 
 * @author maint<br>
 * 
 */
public abstract class UserGUIAbstract extends GUIAbstract {

	private static final long serialVersionUID = 1L;

	protected JLabel lblFullName;
	protected JLabel lblUserName;
	protected JLabel lblEmail;

	protected JTextField txtFullName;
	protected JTextField txtUserName;
	protected JTextField txtEmail;

	protected JButton btnClose;

	/**
	 * initialize components.<br>
	 * create user interface for {@link AddUser}, {@link EditUser},
	 * {@link ViewUser}.
	 */
	public UserGUIAbstract() {
		super();
		buildGUI();
	}

	/**
	 * design user interface for {@link AddUser}, {@link EditUser},
	 * {@link ViewUser} frames.
	 */
	public void buildGUI() {
		// show frame
		setSize(600, 250);
		setLocationRelativeTo(null);
		setVisible(true);

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

		// Row 3: Buttons
		c.gridy = 3;
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
	 * initialize all components which are used in {@link AddUser},
	 * {@link EditUser}, {@link ViewUser}.
	 */
	public void initComponents() {
		super.initComponents();
		
		// create labels
		lblFullName = new JLabel("Full name");
		lblUserName = new JLabel("Username");
		lblEmail = new JLabel("Email");

		// create text fields
		txtFullName = new JTextField("Full name");
		txtUserName = new JTextField("User name");
		txtEmail = new JTextField("Email");

		// create buttons
		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	/**
	 * Check pre-condition to send request to server.<br>
	 * Used in {@link AddUser}, {@link EditUser} when click button "Add" or "Save".
	 * @return true if:
	 * <li> full name field is valid.
	 * <li> user name field is valid.
	 * <li> and email field is valid.<br>
	 * else false.
	 */
	protected boolean readyToSendRequest() {
		String fullName = txtFullName.getText();
		String email = txtEmail.getText();
		String username = txtUserName.getText();
		if (fullName.equals("")) {
			Utils.informDialog("\tFull name is empty! Please input again!");
			return false;
		} else if (!UserAccountValidation.isValidUserName(username)) {
			Utils.informDialog("\tUser name is not valid! Please input again!");
			return false;
		} else if (!UserAccountValidation.isValidEmail(email)) {
			Utils.informDialog("\tEmail is not valid! Please input again!");
			return false;
		}
		return true;
	}
	/**
	 * @return the lblFullName
	 */
	public JLabel getLblFullName() {
		return lblFullName;
	}

	/**
	 * @return the lblUserName
	 */
	public JLabel getLblUserName() {
		return lblUserName;
	}

	/**
	 * @return the lblEmail
	 */
	public JLabel getLblEmail() {
		return lblEmail;
	}

	/**
	 * @return the txtFullName
	 */
	public JTextField getTxtFullName() {
		return txtFullName;
	}

	/**
	 * @return the txtUserName
	 */
	public JTextField getTxtUserName() {
		return txtUserName;
	}

	/**
	 * @return the txtEmail
	 */
	public JTextField getTxtEmail() {
		return txtEmail;
	}

	/**
	 * @return the btnClose
	 */
	public JButton getBtnClose() {
		return btnClose;
	}

}
