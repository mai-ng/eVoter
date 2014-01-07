package mai.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

public class Welcome extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel lblUsername ;
	public Welcome() {
		setLayout(null);
		setSize(400, 400);
				
		JLabel lblNewLabel = new JLabel("Welcome to eVoter!");
		lblNewLabel.setBounds(154, 99, 140, 15);
		add(lblNewLabel);
		
		lblUsername = new JLabel();
		lblUsername.setBounds(192, 144, 70, 15);
		add(lblUsername);
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(166, 219, 117, 25);
		add(btnOk);
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Welcome.this.removeAll();
				Welcome.this.repaint();				
				Welcome.this.add(new Test());
				
			}
		});

	}
	public void setLblUsername(String lblUsername) {
		this.lblUsername.setText(lblUsername);
	}
}
