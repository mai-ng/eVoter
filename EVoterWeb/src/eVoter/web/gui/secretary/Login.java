package eVoter.web.gui.secretary;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Login extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final String SIGNIN = "Sign in";
	public static final String REGISTER = "New account";

	public JTextField txtEmail;
	public JTextField txtPassword;
	

	private JButton btnSignIn;
	private JButton btnRegister;

	GridBagConstraints c;

	/**
	 * Create the panel.
	 */
	public Login() {

		initComponents();
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		GridBagLayout gridbag = new GridBagLayout();
		c = new GridBagConstraints();
		this.setLayout(gridbag);

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(1, 1, 1, 1);

		
		// Row 0: Password
		c.weightx = 0;
		this.add(new JLabel("Email"),c);
		
		c.weightx = 0.5;
		this.add(txtEmail, c);
		
		// Row 2: Email
		c.gridy = 1;
		this.add(new JLabel(" "),c);
		c.gridy = 2;
		
		c.weightx = 0;
		this.add(new JLabel("Password"),c);
		
		c.weightx = 0.5;
		this.add(txtPassword, c);
		
		

		// Row 3,4: Create panel Buttons with BorderLayout
		c.gridy = 3;
		this.add(new JLabel(" "),c);
		c.gridy = 4;
		c.gridx = 1;
			
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BorderLayout());
		btnPanel.add(btnSignIn, BorderLayout.PAGE_START);
		btnPanel.add(new JLabel(" "));
		btnPanel.add(btnRegister, BorderLayout.PAGE_END);
		this.add(btnPanel, c);
	}

	public void initComponents() {
		//create text fields
		txtPassword = new JTextField();
		txtEmail = new JTextField();
		
		//create buttons
		btnSignIn = new JButton(SIGNIN);
		btnRegister = new JButton(REGISTER);
		
	}
}
