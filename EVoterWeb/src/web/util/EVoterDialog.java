/**
 * 
 */
package web.util;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author louisnguyen
 *
 */
public class EVoterDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
    private JButton btYes;
    private JButton btNo;
    private JLabel lbMsg;

    public EVoterDialog() {
    	contentPanel = new JPanel();
        getContentPane().add(contentPanel);
        btYes = new JButton("Yes");
        contentPanel.add(btYes); 
        btNo = new JButton("No");
        contentPanel.add(btNo);  
        setSize(new Dimension(400, 200));
        setModal(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

	/**
	 * @return the btYes
	 */
	public JButton getBtYes() {
		return btYes;
	}

	/**
	 * @return the btNo
	 */
	public JButton getBtNo() {
		return btNo;
	}

	/**
	 * @return the lbMsg
	 */
	public JLabel getLbMsg() {
		return lbMsg;
	}
}
