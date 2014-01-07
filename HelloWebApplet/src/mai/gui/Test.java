package mai.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Test extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Test() {
		
		setSize(400, 400);
				
		JLabel lblNewLabel = new JLabel("Congratulation!");
		lblNewLabel.setBounds(154, 99, 140, 15);
		add(lblNewLabel);

	}

}
