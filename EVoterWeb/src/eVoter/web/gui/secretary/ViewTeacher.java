package eVoter.web.gui.secretary;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewTeacher extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final String CLOSE = "Close";

	public JLabel lblFullName;
	public JLabel lblEmail;
	public JLabel lblTel;

	private JButton btnClose;

	GridBagConstraints c;

	/**
	 * Create the panel.
	 */
	public ViewTeacher() {

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
		this.add(new JLabel("Full name "),c);
		
		c.weightx = 0.5;
		this.add(lblFullName, c);

		// Row 1: Password
		c.gridy = 1;
		this.add(new JLabel(" "),c);
		c.gridy = 2;
		
		c.weightx = 0;
		this.add(new JLabel("Email"),c);
		
		c.weightx = 0.5;
		this.add(lblEmail, c);

		// Row 2: Email
		c.gridy = 3;
		this.add(new JLabel(" "),c);
		c.gridy = 4;
		
		c.weightx = 0;
		this.add(new JLabel("Telephone"),c);
		
		c.weightx = 0.5;
		this.add(lblTel, c);

		// Row 3: Create Button
		c.gridy = 5;
		this.add(new JLabel(" "),c);
		c.gridy = 6;
	
		c.fill = 0;
		c.anchor = GridBagConstraints.EAST;
		this.add(btnClose, c);
	}

	public void initComponents() {
		//create text fields
		lblFullName = new JLabel("Full name of a Teacher");
		lblEmail = new JLabel("Email of a teacher");
		lblTel = new JLabel("Tel of a teacher");
		
		//create buttons
		btnClose = new JButton(CLOSE);
		
	}


}
