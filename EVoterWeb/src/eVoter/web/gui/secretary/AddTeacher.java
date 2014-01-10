package eVoter.web.gui.secretary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author maint<br> Add a new teacher
 */
public class AddTeacher extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final String SAVE = "Save";
	public static final String CLOSE = "Close";

	public JTextField txtID;
	public JTextField txtPassword;
	public JTextField txtEmail;

	private JButton btnSave;
	private JButton btnClose;

	GridBagConstraints c;

	/**
	 * Create the panel.
	 */
	public AddTeacher() {

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
		this.add(new JLabel("ID "),c);
		
		c.weightx = 0.5;
		this.add(txtID, c);

		// Row 1: Password
		c.gridy = 1;
		this.add(new JLabel(" "),c);
		c.gridy = 2;
		
		c.weightx = 0;
		this.add(new JLabel("Password "),c);
		
		c.weightx = 0.5;
		this.add(txtPassword, c);

		// Row 2: Email
		c.gridy = 3;
		this.add(new JLabel(" "),c);
		c.gridy = 4;
		
		c.weightx = 0;
		this.add(new JLabel("Email"),c);
		
		c.weightx = 0.5;
		this.add(txtEmail, c);

		// Row 3: Create Button
		c.gridy = 5;
		this.add(new JLabel(" "),c);
		c.gridy = 6;
		c.gridx = 1;
		
		c.fill = 0;
		c.gridx = 1;
		this.add(btnSave, c);

		
		c.fill = 0;
//		c.weighty = 0.2;
		c.anchor = GridBagConstraints.EAST;
		this.add(btnClose, c);
		
//		JPanel btnPanel = new JPanel();
//		btnPanel.setLayout(new BorderLayout());
//		btnPanel.add(btnSave, BorderLayout.CENTER);
//		btnPanel.add(new JLabel(" "));
//		btnPanel.add(btnClose, BorderLayout.LINE_END);
//		this.add(btnPanel, c);
	}

	public void initComponents() {
		//create text fields
		txtID = new JTextField("Enter ID");
		txtPassword = new JTextField();
		txtEmail = new JTextField();
		
		//create buttons
		btnSave = new JButton(SAVE);
		btnClose = new JButton(CLOSE);
		
	}

}
