package mai.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Login extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtUserName;
	private JPasswordField passwordField;
	JButton btnLogIn;

	/**
	 * Create the panel.
	 */
	public Login() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("User name");
		lblNewLabel.setBounds(22, 44, 76, 15);
		add(lblNewLabel);
		
		txtUserName = new JTextField();
		txtUserName.setBounds(146, 33, 266, 37);
		add(txtUserName);
		txtUserName.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(22, 114, 70, 15);
		add(lblPassword);
				
		btnLogIn = new JButton("Log in");
		btnLogIn.setBounds(232, 177, 117, 25);
		add(btnLogIn);
		btnLogIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Login.this.removeAll();
				Login.this.repaint();
				Welcome welcome = new Welcome();
				welcome.setLblUsername(getTxtUserName());
				Login.this.add(welcome);
				
			}

		});
		
		JButton btnNewButton = new JButton("Register");
		btnNewButton.setBounds(232, 214, 117, 25);
		add(btnNewButton);
		
		
		JButton btnForgot = new JButton("Forgot");
		btnForgot.setBounds(232, 250, 117, 25);
		add(btnForgot);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(146, 103, 266, 37);
		add(passwordField);

	}

	public String getTxtUserName() {
		return txtUserName.getText();
	}

	public JButton getBtnLogIn() {
		return btnLogIn;
	}
}
