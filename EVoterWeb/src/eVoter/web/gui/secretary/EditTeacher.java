package eVoter.web.gui.secretary;

import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class EditTeacher extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final String SAVE = "Save";
	public static final String CLOSE = "Close";

	public JTextField txtFirstName;
	public JTextField txtLastName;
	public JTextField txtEmail;
	public JTextField txtTel;

	private JButton btnSave;
	private JButton btnClose;

	GridBagConstraints c;

	/**
	 * Create the panel.
	 */
	public EditTeacher() {

		initComponents();
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		GridBagLayout gridbag = new GridBagLayout();
		c = new GridBagConstraints();
		this.setLayout(gridbag);

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(1, 1, 1, 1);

		// Row 0: ID
		c.gridy = 0;
		c.ipady = 5;
		c.weightx = 0;
		this.add(new JLabel("First name "), c);

		c.weightx = 0.5;
		this.add(txtFirstName, c);

		// Row 1: Password
		c.gridy = 1;
		this.add(new JLabel(" "), c);
		c.gridy = 2;

		c.weightx = 0;
		this.add(new JLabel("Last name "), c);

		c.weightx = 0.5;
		this.add(txtLastName, c);

		// Row 2: Email
		c.gridy = 3;
		this.add(new JLabel(" "), c);
		c.gridy = 4;

		c.weightx = 0;
		this.add(new JLabel("Email "), c);

		c.weightx = 0.5;
		this.add(txtEmail, c);

		// Row 3: Telephone
		c.gridy = 5;
		this.add(new JLabel(" "), c);
		c.gridy = 6;

		c.weightx = 0;
		this.add(new JLabel("Telephone "), c);

		c.weightx = 0.5;
		this.add(txtTel, c);

		// Row 3: Create Button
		c.gridy = 7;
		this.add(new JLabel(" "),c);
		c.gridy = 8;
		
		c.fill = 0;
		c.gridx = 1;
//		c.weighty = 0.2;
		this.add(btnSave, c);

		c.fill = 0;
//		c.weighty = 0.2;
		c.anchor = GridBagConstraints.EAST;
		this.add(btnClose, c);
	}

	/**
	 * initialize components
	 */
	public void initComponents() {
		// create text fields
		txtFirstName = new JTextField();
		txtLastName = new JTextField();
		txtEmail = new JTextField();
		txtTel = new JTextField();

		// create buttons
		btnSave = new JButton(SAVE);
		btnClose = new JButton(CLOSE);

	}

}
